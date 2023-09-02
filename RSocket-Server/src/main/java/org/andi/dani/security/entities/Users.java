package org.andi.dani.security.entities;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;

@Builder
@Getter
public class Users {
    @Id
    private Long id;
    private String username;
    private String password;
    private String role = "ROLE_USER";
}