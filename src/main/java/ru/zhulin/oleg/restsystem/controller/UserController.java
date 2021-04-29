package ru.zhulin.oleg.restsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.zhulin.oleg.restsystem.model.User;
import ru.zhulin.oleg.restsystem.service.UserService;

import javax.validation.Valid;
import java.util.List;

import java.net.URI;

@RestController
@RequestMapping("/api/rest/admin/users")
public class UserAdminController {
    private UserService userService;
    @Autowired
    public UserAdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> get(@PathVariable Long userId){
        return ResponseEntity.ok(userService.get(userId));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<User>> getAll(){
        return ResponseEntity.ok(userService.getAll());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> create(@Valid @RequestBody User user){
        user.setId(null);
        User newUser = userService.create(user);
        URI uriOfNewUser = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/api/rest/admin/users/{userId}")
                .buildAndExpand(newUser.getId()).toUri();
        String str = "hello";
        return ResponseEntity.created(uriOfNewUser).body(newUser);
    }

    @PutMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> update(@PathVariable Long userId, @Valid @RequestBody User user){
        user.setId(userId);
        User updatedUser = userService.update(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .buildAndExpand(updatedUser.getId())
                .toUri();
        return ResponseEntity.ok().header("Location", location.toString()).build();
    }

    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<User> delete(@PathVariable Long userId){
        userService.delete(userId);
        return ResponseEntity.noContent().build();
    }
}
