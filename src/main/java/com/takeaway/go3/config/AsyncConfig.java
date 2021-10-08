package com.takeaway.go3.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty("game.settings.async.brokerAddress")
public class AsyncConfig {
}
