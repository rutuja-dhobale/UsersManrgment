package com.app.payload;

import com.app.entity.Manager;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @NotEmpty(message = "Full name cannot be empty")
    private String fullName;

    @Pattern(regexp = "(^[6-9]\\d{9}$)", message = "Invalid mobile number")
    private String mobNum;

    @Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-Z]{1}", message = "Invalid PAN number")
    private String panNum;

    private Manager manager;
}
