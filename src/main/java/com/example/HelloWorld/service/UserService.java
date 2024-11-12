package com.example.HelloWorld.service;

import com.example.HelloWorld.model.UserInfo;
import com.example.HelloWorld.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserInfo> getAllUsers() {
        return (List<UserInfo>) userRepository.findAll();
    }

    public Optional<UserInfo> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public UserInfo createUser(UserInfo user) {
        return userRepository.save(user);
    }

    public UserInfo updateUser(Long id, UserInfo userDetails) {
        return userRepository.findById(id).map(user -> {
            user.setName(userDetails.getName());
            user.setEmail(userDetails.getEmail());
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("UserInfo not found"));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
