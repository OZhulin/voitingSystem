package ru.zhulin.oleg.restsystem.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface PasswordEncoderComponent {
    @Bean
    PasswordEncoderComponent getPasswordEncoderComponent();

    PasswordEncoder getEncoder();

    String encode(String rawPassword);

    boolean isMatch(String rewPassword, String password);
}
