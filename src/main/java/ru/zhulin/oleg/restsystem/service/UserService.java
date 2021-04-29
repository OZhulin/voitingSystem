package ru.zhulin.oleg.restsystem.service;

import com.mysema.commons.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.zhulin.oleg.restsystem.model.Role;
import ru.zhulin.oleg.restsystem.model.User;
import ru.zhulin.oleg.restsystem.repository.UserRepository;
import ru.zhulin.oleg.restsystem.security.AuthorizedUser;
import ru.zhulin.oleg.restsystem.security.PasswordEncoderComponent;
import ru.zhulin.oleg.restsystem.to.UserTo;

import java.util.List;
import java.util.Locale;

@Service("userService")
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private PasswordEncoderComponent passwordEncoderComponent;
    @Autowired
    public UserService(UserRepository userRepository,
                       @Qualifier("getPasswordEncoderComponent") PasswordEncoderComponent passwordEncoderComponent) {
        this.userRepository = userRepository;
        this.passwordEncoderComponent = passwordEncoderComponent;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getByName(username.toLowerCase(Locale.ROOT));
        if(user == null){
            throw new UsernameNotFoundException(String.format("User %s not found", username));
        }
        return new AuthorizedUser(user);
    }

    public User get(Long userId){
        return userRepository.getById(userId);
    }

    public User getByName(String name){
        return userRepository.getByName(name);
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public User create(User user){
        Assert.notNull(user,
                "The [user] argument cannot be null");
        return userRepository.save(prepareToSave(user));
    }

    public User update(User user){
       return create(user);
    }

    public void delete(Long userId){
        userRepository.deleteById(userId);
    }

    public User fromTo(UserTo userTo){
        return new User(userTo.getId(), userTo.getName(), userTo.getPassword(), Role.ROLE_USER);
    }

    public User prepareToSave(User user){
        user.setPassword(passwordEncoderComponent.encode(user.getPassword()));
        user.setName(user.getName().toLowerCase(Locale.ROOT));
        return user;
    }

}
