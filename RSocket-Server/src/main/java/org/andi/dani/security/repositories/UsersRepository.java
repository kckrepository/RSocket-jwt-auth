package org.andi.dani.security.repositories;

import org.andi.dani.security.entities.Users;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UsersRepository extends R2dbcRepository<Users, Long> {
    Mono<Users> findByUsername(String username);
}