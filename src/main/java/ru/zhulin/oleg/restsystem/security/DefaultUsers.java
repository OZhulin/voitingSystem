package ru.zhulin.oleg.restsystem.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.zhulin.oleg.restsystem.model.Role;
import ru.zhulin.oleg.restsystem.model.User;
import ru.zhulin.oleg.restsystem.repository.UserRepository;

@Component
public class DefaultUsers implements CommandLineRunner {
    private UserRepository userRepository;
    private final String adminName = "admin";
    private final String adminPassword = "admin";
    private final String userName = "user";
    private final String userPassword = "user";

    @Autowired
    public DefaultUsers(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        User admin = new User(100000L, adminName, adminPassword, Role.ROLE_ADMIN, Role.ROLE_USER);
        User user = new User(100001L, userName, userPassword, Role.ROLE_USER);
        userRepository.save(admin);
        userRepository.save(user);

    }
}
