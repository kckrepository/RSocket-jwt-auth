package org.andi.dani;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.util.MimeTypeUtils;

@Configuration
public class Config {
    @Bean
    public RSocketRequester rSocketRequester(RSocketRequester.Builder b, RSocketStrategies rSocketStrategies) {
        return b.dataMimeType(MimeTypeUtils.APPLICATION_JSON)
                .rsocketStrategies(rSocketStrategies)
                .setupRoute("connect")
                .setupData("user")
                .connectTcp("localhost", 7000)
                .block();
    }
}