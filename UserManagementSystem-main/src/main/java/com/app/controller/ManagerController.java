package com.app.controller;

import com.app.entity.Manager;
import com.app.payload.ManagerDTO;
import com.app.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/managers")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @PostMapping("/create")
    public ResponseEntity<ManagerDTO> createManager(@RequestBody ManagerDTO managerDTO) {
        ManagerDTO manager = managerService.createManager(managerDTO);
        return new ResponseEntity<>(manager, HttpStatus.CREATED);
    }

    @GetMapping("/get_all")
    public ResponseEntity<List<ManagerDTO>> getAllManagers() {
        return ResponseEntity.ok(managerService.getAllManagers());
    }

    @GetMapping("/get/{managerId}")
    public ResponseEntity<ManagerDTO> getManagerById(@PathVariable Long managerId) {
        return ResponseEntity.ok(managerService.getManagerById(managerId));
    }

    @DeleteMapping("/delete/{managerId}")
    public ResponseEntity<String> deleteManager(@PathVariable Long managerId) {
        managerService.deleteManager(managerId);
        return ResponseEntity.ok("Manager deleted successfully.");
    }
}
