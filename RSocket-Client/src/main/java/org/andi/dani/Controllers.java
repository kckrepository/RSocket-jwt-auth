package org.andi.dani;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController()
@RequiredArgsConstructor
public class Controllers {
    private final RSocketRequester requester;
    private final MimeType mimeType =  new MediaType("message", "x.rsocket.authentication.bearer.v0");
    private String accessToken;

    @GetMapping("login")
    Mono<String> login() {
        return requester
                .route("login")
                .retrieveMono(String.class)
                .doOnNext(token -> {
                    accessToken = token;
                })
                .onErrorStop();
    }

    @GetMapping("data")
    Mono<String> data() {
        return requester
                .route("data")
                .metadata(this.accessToken, this.mimeType)
                .retrieveMono(String.class);
    }
}
