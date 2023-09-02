package org.andi.dani.security.services;

import lombok.AllArgsConstructor;
import org.andi.dani.security.UserDetailImpl;
import org.andi.dani.security.repositories.UsersRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@AllArgsConstructor
@Service
public class UserDetailService {
    private final UsersRepository usersRepository;
    public Mono<UserDetails> loadUserByUsername(String username) {
        return usersRepository.findByUsername(username)
                .switchIfEmpty(Mono.empty())
                .map(users -> {
                    return UserDetailImpl.builder()
                            .id(users.getId())
                            .username(users.getUsername())
                            .password(users.getPassword())
                            .authorities(Arrays.asList(new SimpleGrantedAuthority(users.getRole())))
                            .build();
                });
    }
}