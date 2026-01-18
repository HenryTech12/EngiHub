package org.app.engihub.service;

import lombok.extern.slf4j.Slf4j;
import org.app.engihub.dto.UserDTO;
import org.app.engihub.mapper.UserMapper;
import org.app.engihub.model.UserModel;
import org.app.engihub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    public Map<String,Object> createUser(UserDTO userDTO) {
        UserModel userModel = userMapper.convertToModel(userDTO);
        userModel.setRole("STUDENT");
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        userRepository.save(userModel);

        log.info("user details saved to database");
        return Map.of("message", "User registered sucessfully", "userId", userModel.getId());
    }

    public UserDTO fetchUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::convertToDTO)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserDTO fetchUserByID(Long id) {
        return userRepository.findById(id)
                .map(userMapper::convertToDTO)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public Map<String,Object> fetchUserDetails(String email) {
        UserDTO userDTO = fetchUserByEmail(email);
        return Map.of("id",userDTO.getId(),"name",userDTO.getName(),"email",userDTO.getEmail(),"role",userDTO.getRole().name(),"forumPosts","");
    }

    public Map<String,Object> fetchUserDetails(Long id) {
        UserDTO userDTO = fetchUserByID(id);
        return Map.of("id",userDTO.getId(),"name",userDTO.getName(),"email",userDTO.getEmail(),"role",userDTO.getRole().name(),"forumPosts","");
    }
}
