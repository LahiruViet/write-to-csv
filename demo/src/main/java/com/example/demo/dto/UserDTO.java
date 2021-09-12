package com.example.demo.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserDTO {

    private Long id;
    private String email;
    private String fullName;
    private boolean enabled;
    private Set<String> roles;

}
