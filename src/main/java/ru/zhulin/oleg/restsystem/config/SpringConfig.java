package ru.zhulin.oleg.restsystem.config;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    @Bean
    public Hibernate5Module dataTypeHibernateModule(){
        return new Hibernate5Module();
    }
}
