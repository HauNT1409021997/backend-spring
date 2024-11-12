pipeline {
    agent none  // Disable the global agent definition

    environment {
        DOCKER_REGISTRY = 'haunt14'  // Your Docker registry (without https://)
        DOCKER_IMAGE_NAME_JAVA = 'jenkin-docker-java-backend'  // Your Docker image name
        DOCKER_IMAGE_NAME_JENKIN_MASTER = 'jenkin-docker-jenkins-master'  // Your Docker image name
        DOCKER_IMAGE_NAME_POSTGRES = 'postgres'  // Your Docker image name
        DOCKER_CREDENTIALS = '0385934297'  // Jenkins credentials ID for Docker login
    }

    parameters {
        string(name: 'AGENT_LABEL', defaultValue: 'java-slave-d1c2e3e2', description: 'The label of the Jenkins agent to use')
    }

    stages {
        stage('Checkout') {
            agent { label "${params.AGENT_LABEL}" }  // Dynamically select agent based on parameter
            steps {
                // Checkout the code from the Git repository
                sh 'echo test 3'
                sh 'git checkout master'
                sh 'sudo gpasswd -a jenkins docker'
                sh 'sudo usermod -aG docker jenkins'
            }
        }

        stage('Get Commit ID') {
            agent { label "${params.AGENT_LABEL}" }  // Dynamically select agent based on parameter
            steps {
                script {
                    // Get the commit ID using `git log --oneline` (first 7 characters of the hash)
                    env.GIT_COMMIT_ID = sh(script: 'git log --oneline -n 1 | awk \'{print $1}\'', returnStdout: true).trim()
                    echo "Commit ID: ${env.GIT_COMMIT_ID}"
                }
            }
        }

        stage('Login to Docker Registry') {
            agent { label "${params.AGENT_LABEL}" }  // Dynamically select agent based on parameter
            steps {
                script {
                    // Use docker.withRegistry to handle login with credentials securely
                    docker.withRegistry('https://index.docker.io/v1/', "${DOCKER_CREDENTIALS}") {
                        // This block will run within the context of the login to the Docker registry
                        echo 'Successfully logged in to Docker registry'
                    }
                }
            }
        }

        stage('Build, Tag & Push Docker Image') {
            agent { label "${params.AGENT_LABEL}" }  // Dynamically select agent based on parameter
            steps {
                script {
                    // Use parallel to run these steps concurrently
                    def buildAndPushImages = [
                        "Java Image": {
                            docker.withRegistry('https://index.docker.io/v1/', "${DOCKER_CREDENTIALS}") {
                                def customImageJava = docker.build("${DOCKER_REGISTRY}/${DOCKER_IMAGE_NAME_JAVA}:${env.GIT_COMMIT_ID}")
                                customImageJava.push("${env.GIT_COMMIT_ID}")  // Push commit ID tagged image
                                customImageJava.push("latest")  // Optionally push "latest" tagged image
                                echo 'Successfully pushed Java image'
                            }
                        },
                        "Jenkins Master Image": {
                            docker.withRegistry('https://index.docker.io/v1/', "${DOCKER_CREDENTIALS}") {
                                def customImageJenkinMaster = docker.build("${DOCKER_REGISTRY}/${DOCKER_IMAGE_NAME_JENKIN_MASTER}:${env.GIT_COMMIT_ID}")
                                customImageJenkinMaster.push("${env.GIT_COMMIT_ID}")  // Push commit ID tagged image
                                customImageJenkinMaster.push("latest")  // Optionally push "latest" tagged image
                                echo 'Successfully pushed Jenkins Master image'
                            }
                        },
                        "Postgres Image": {
                            docker.withRegistry('https://index.docker.io/v1/', "${DOCKER_CREDENTIALS}") {
                                def customImageJenkinPostgres = docker.build("${DOCKER_REGISTRY}/${DOCKER_IMAGE_NAME_POSTGRES}:${env.GIT_COMMIT_ID}")
                                customImageJenkinPostgres.push("${env.GIT_COMMIT_ID}")  // Push commit ID tagged image
                                customImageJenkinPostgres.push("latest")  // Optionally push "latest" tagged image
                                echo 'Successfully pushed Postgres image'
                            }
                        }
                    ]
                    // Execute build and push for all images in parallel
                    parallel buildAndPushImages
                }
            }
        }

        // Optional stage for cleanup
//         stage('Clean Up') {
//             agent { label "${params.AGENT_LABEL}" }  // Dynamically select agent based on parameter
//             steps {
//                 script {
//                     // Remove the local Docker images to free up space
//                     def customImage = docker.image("${DOCKER_REGISTRY}/${DOCKER_IMAGE_NAME}:${env.GIT_COMMIT_ID}")
//                     customImage.remove()
//                 }
//             }
//         }
    }

    post {
        always {
            // Cleanup or any other final steps can be placed here
            echo "Pipeline completed"
        }
        success {
            echo 'Pipeline succeeded'
        }
        failure {
            echo 'Pipeline failed'
        }
    }
}
