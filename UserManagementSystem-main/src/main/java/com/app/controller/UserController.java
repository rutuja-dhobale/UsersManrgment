package com.app.controller;

import com.app.entity.User;
import com.app.payload.UserDTO;
import com.app.payload.UserResponseDTO;
import com.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/create_user")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserDTO userDTO) {
        UserResponseDTO user = userService.createUser(userDTO);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/get_users")
    public ResponseEntity<List<UserResponseDTO>> getUsers(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String mobNum,
            @RequestParam(required = false) Long managerId) {
        return ResponseEntity.ok(userService.getUsers(userId, mobNum, managerId));
    }

    @DeleteMapping("/delete_user")
    public ResponseEntity<String> deleteUser(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String mobNum) {
        userService.deleteUser(userId, mobNum);
        return ResponseEntity.ok("User deleted successfully.");
    }

    @PutMapping("/update_user")
    public ResponseEntity<UserResponseDTO> updateUser(
            @RequestParam Long userId,
            @RequestBody User updatedData) {
        UserResponseDTO updatedUser = userService.updateUser(userId, updatedData);
        return ResponseEntity.ok(updatedUser);
    }
}
