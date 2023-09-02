package org.andi.dani.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.andi.dani.security.jwt.JwtUtils;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Slf4j
@Controller
public class ApiController {
    private final JwtUtils jwtUtils;
    @MessageMapping("login")
    public Mono<String> hello() {
        return Mono.fromSupplier(() -> {
            try {
                return jwtUtils.generateJwtToken("dani");
            }
            catch (Exception exception) {
                return "";
            }
        });
    }

    @MessageMapping("data")
    public Mono<String> data() {
        return Mono.just("Hello, - from secured method");
    }
}