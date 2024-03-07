package com.learning.sessions.authservice.config.vault;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.vault.authentication.TokenAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.core.VaultTemplate;

@Configuration
public class VaultConfig {

    @Value("${spring.cloud.vault.host}")
    private String vaultHost;

    @Value("${spring.cloud.vault.port}")
    private int vaultPort;

    @Value("${spring.cloud.vault.token}")
    private String vaultToken;

    @Bean
    public VaultTemplate vaultTemplate() {
        VaultEndpoint vaultEndpoint = VaultEndpoint.create(vaultHost, vaultPort);
        vaultEndpoint.setScheme("http");
        return new VaultTemplate(vaultEndpoint, new TokenAuthentication(vaultToken));
    }
}
