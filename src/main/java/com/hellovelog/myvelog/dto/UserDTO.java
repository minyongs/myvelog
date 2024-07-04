package com.hellovelog.myvelog.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserDTO {

    private String username;
    private String password;
    private String name;
    private String email;
    private String userRole;
    private LocalDateTime registrationDate;

}
