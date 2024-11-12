-- Create the 'test' schema if it doesn't exist
CREATE SCHEMA IF NOT EXISTS test;

-- Switch to the 'test' schema
SET search_path TO test;

DROP TABLE IF EXISTS test.test.user_info;
-- Create the 'users' table if it doesn't exist
CREATE TABLE IF NOT EXISTS user_info
(
    id    SERIAL  PRIMARY KEY,
    name  VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
);
