package com.app.service;

import com.app.entity.User;
import com.app.exception.UserNotFound;
import com.app.payload.UserDTO;
import com.app.payload.UserResponseDTO;
import com.app.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final ModelMapper mapper;
    private final UserRepository userRepository;

    public UserService(ModelMapper mapper, UserRepository userRepository) {
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    public UserResponseDTO createUser(UserDTO userDTO) {
        User user = mapper.map(userDTO, User.class);
        user.setPanNum(userDTO.getPanNum().toUpperCase());
        user.setCreatedAt(LocalDateTime.now());
        User saved = userRepository.save(user);
        return mapper.map(saved, UserResponseDTO.class);
    }

    public void deleteUser(Long userId, String mobNum) {
        userRepository.findByUserId(userId)
                .or(() -> userRepository.findByMobNum(mobNum))
                .ifPresentOrElse(userRepository::delete,
                        () -> { throw new RuntimeException("User not found"); });
    }

    public List<UserResponseDTO> getUsers(Long userId, String mobNum, Long managerId) {
        List<User> users;
        if (userId != null) {
            users = userRepository.findByUserId(userId).map(List::of).orElse(List.of());
        } else if (mobNum != null) {
            users = userRepository.findByMobNum(mobNum).map(List::of).orElse(List.of());
        } else if (managerId != null) {
            users = userRepository.findByManagerId(managerId);
        } else {
            users = userRepository.findAll();
        }

        return users.stream().map(user -> mapper.map(user, UserResponseDTO.class)).collect(Collectors.toList());
    }

    public UserResponseDTO updateUser(Long userId, User updatedData) {
        return userRepository.findById(userId).map(existingUser -> {
            if (updatedData.getFullName() != null) {
                existingUser.setFullName(updatedData.getFullName());
            }
            if (updatedData.getMobNum() != null) {
                existingUser.setMobNum(updatedData.getMobNum());
            }
            if (updatedData.getPanNum() != null) {
                existingUser.setPanNum(updatedData.getPanNum().toUpperCase());
            }
            if (updatedData.getManager() != null) {
                existingUser.setManager(updatedData.getManager());
            }
            existingUser.setUpdatedAt(LocalDateTime.now());
            User saved = userRepository.save(existingUser);
            return mapper.map(saved, UserResponseDTO.class);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
