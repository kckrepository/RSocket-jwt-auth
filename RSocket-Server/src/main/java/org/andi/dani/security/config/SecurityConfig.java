package org.andi.dani.security.config;

import io.r2dbc.spi.ConnectionFactory;
import lombok.AllArgsConstructor;
import org.andi.dani.security.jwt.CustomReactiveJwtAuthenticationConverterAdapter;
import org.andi.dani.security.jwt.JwtUtils;
import org.andi.dani.security.services.UserDetailService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.security.config.annotation.rsocket.EnableRSocketSecurity;
import org.springframework.security.config.annotation.rsocket.RSocketSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtReactiveAuthenticationManager;
import org.springframework.security.rsocket.core.PayloadSocketAcceptorInterceptor;

import java.security.interfaces.RSAPublicKey;

@AllArgsConstructor
@Configuration
@EnableRSocketSecurity
public class SecurityConfig {
    private final JwtUtils jwtUtils;
    private final UserDetailService userDetailService;
    private final CustomReactiveJwtAuthenticationConverterAdapter customReactiveJwtAuthenticationConverterAdapter;
    @Bean
    public PayloadSocketAcceptorInterceptor rsocketInterceptor(RSocketSecurity rsocket) {
        rsocket.authorizePayload(authorize ->
                        authorize
                                .route("login")
                                .permitAll()
                                .route("data")
                                .hasRole("USER")
                                .anyRequest()
                                .authenticated()
                                .anyExchange()
                                .permitAll()
                )
                .jwt(jwtSpec -> {
                    try {
                        jwtSpec.authenticationManager(jwtReactiveAuthenticationManager(reactiveJwtDecoder()));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });

        return rsocket.build();
    }
    @Bean
    public ReactiveJwtDecoder reactiveJwtDecoder() throws Exception {
        return NimbusReactiveJwtDecoder.withPublicKey((RSAPublicKey) jwtUtils.getPublicKey())
                .signatureAlgorithm(SignatureAlgorithm.RS256)
                .build();
    }
    @Bean
    public JwtReactiveAuthenticationManager jwtReactiveAuthenticationManager(ReactiveJwtDecoder reactiveJwtDecoder) {
        JwtReactiveAuthenticationManager jwtReactiveAuthenticationManager = new JwtReactiveAuthenticationManager(reactiveJwtDecoder);
        jwtReactiveAuthenticationManager.setJwtAuthenticationConverter(customReactiveJwtAuthenticationConverterAdapter);
        return jwtReactiveAuthenticationManager;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    ConnectionFactoryInitializer initializer(@Qualifier("connectionFactory") ConnectionFactory connectionFactory) {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        ResourceDatabasePopulator resource =
                new ResourceDatabasePopulator(new ClassPathResource("schema.sql"));
        initializer.setDatabasePopulator(resource);
        return initializer;
    }
}