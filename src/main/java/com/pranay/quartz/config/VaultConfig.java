package com.pranay.quartz.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.vault.config.EnvironmentVaultConfiguration;

@Configuration
@Import(value = EnvironmentVaultConfiguration.class)
@PropertySource(value = "classpath:application-" + "${spring.profiles.active}" + ".properties")
public class VaultConfig  {


}
