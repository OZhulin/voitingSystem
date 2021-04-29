package ru.zhulin.oleg.restsystem.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordEncoderComponent implements PasswordEncoderComponent {
    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

    private BCryptPasswordEncoderComponent() {
    }

    @Override
    @Bean
    public PasswordEncoderComponent getPasswordEncoderComponent(){
        return new BCryptPasswordEncoderComponent();
    }

    @Override
    public PasswordEncoder getEncoder(){
        return ENCODER;
    }

    @Override
    public String encode(String rawPassword){
        return ENCODER.encode(rawPassword);
    }

    @Override
    public boolean isMatch(String rawPassword, String password){
        return ENCODER.matches(rawPassword, password);
    }


}
