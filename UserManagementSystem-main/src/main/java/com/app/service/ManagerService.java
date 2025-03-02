package com.app.service;

import com.app.entity.Manager;
import com.app.payload.ManagerDTO;
import com.app.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManagerService {

    @Autowired
    private ManagerRepository managerRepository;

    public ManagerDTO createManager(ManagerDTO managerDTO) {
        Manager manager = new Manager();
        manager.setManagerName(managerDTO.getManagerName());
        manager.setEmail(managerDTO.getEmail());
        manager.setActive(true);
        Manager savedManager = managerRepository.save(manager);
        return new ManagerDTO(savedManager.getManagerName(), savedManager.getEmail(), savedManager.isActive());
    }

    public List<ManagerDTO> getAllManagers() {
        return managerRepository.findAll().stream()
                .map(manager -> new ManagerDTO(manager.getManagerName(), manager.getEmail(), manager.isActive()))
                .collect(Collectors.toList());
    }

    public ManagerDTO getManagerById(Long managerId) {
        Manager manager = managerRepository.findById(managerId)
                .orElseThrow(() -> new RuntimeException("Manager not found"));
        return new ManagerDTO(manager.getManagerName(), manager.getEmail(), manager.isActive());
    }

    public void deleteManager(Long managerId) {
        Manager manager = managerRepository.findById(managerId)
                .orElseThrow(() -> new RuntimeException("Manager not found"));
        managerRepository.delete(manager);
    }
}