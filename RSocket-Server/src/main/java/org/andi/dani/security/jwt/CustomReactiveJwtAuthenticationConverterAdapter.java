package org.andi.dani.security.jwt;

import lombok.AllArgsConstructor;
import org.andi.dani.security.services.UserDetailService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Component
public class CustomReactiveJwtAuthenticationConverterAdapter implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {
    private final UserDetailService userDetailsService;

    @Override
    public Mono<AbstractAuthenticationToken> convert(Jwt source) {
        return userDetailsService.loadUserByUsername(source.getSubject())
                .switchIfEmpty(Mono.error(new JwtException("Invalid User")))
                .map(user -> {
                        return new UsernamePasswordAuthenticationToken(user, null,
                                user.getAuthorities());
                    });
    }

    @Override
    public <U> Converter<Jwt, U> andThen(Converter<? super Mono<AbstractAuthenticationToken>, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}
