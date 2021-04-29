package ru.zhulin.oleg.restsystem.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.zhulin.oleg.restsystem.model.User;

import java.util.Objects;
import java.util.Optional;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User {
    private User user;

    public AuthorizedUser(User user) {
        super(user.getName(), user.getPassword(), true, true, true, true, user.getRoleSet());
        this.user = user;
    }
    private static Optional<AuthorizedUser> safeGet(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null){
            return Optional.empty();
        }
        return Optional.of((AuthorizedUser)authentication.getPrincipal());
    }
    public static AuthorizedUser get(){
        return Objects.requireNonNull(safeGet()).orElseThrow();
    }
    public Long getId(){
        return user.getId();
    }
    public static Long id(){
        return get().user.getId();
    }
}
