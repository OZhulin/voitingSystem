package ru.zhulin.oleg.restsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.zhulin.oleg.restsystem.model.User;
import ru.zhulin.oleg.restsystem.security.AuthorizedUser;
import ru.zhulin.oleg.restsystem.service.UserService;
import ru.zhulin.oleg.restsystem.to.UserTo;
import ru.zhulin.oleg.restsystem.validation.RestSystemValidationErrorBuilder;

import javax.validation.Valid;

import java.net.URI;

@Log4j2
@Tag(name = "User", description = "The user API")
@RestController
@RequestMapping("/api/v1/users")
public class UserController extends AbstractController<User> {
    private UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get user by id", tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))
                    }
            ),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> get(@Parameter(description = "Id of user", required = true)
                                    @PathVariable Long userId){
        log.info("Try find user by id {}", userId);
        return ResponseEntity.ok(userService.get(userId));
    }

    @Operation(summary = "Gets all users", tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the users",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = User.class)))
                    }
            ),
            @ApiResponse( responseCode = "404", description = "user not found")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<User>> getAll(){
        log.info("Getting all users");
        return ResponseEntity.ok(userService.getAll());
    }

    @Operation(summary = "Create user", tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "successful",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))
                    }
            ),
            @ApiResponse(responseCode = "400", description = "invalid input data"),
            @ApiResponse(responseCode = "409", description = "already exists")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@Parameter(description = "body of user", required = true,
                                                  schema = @Schema(implementation = User.class))
                                       @Valid @RequestBody User user,
                                       Errors errors){
        if(errors.hasErrors()){
            log.warn("Bad request. Request has errors: {}", errors.getAllErrors());
            return ResponseEntity.badRequest().body(RestSystemValidationErrorBuilder.fromBindingErrors(errors));
        }
        user.setId(null);
        log.info("Create user {}", user);
        User newUser = userService.create(user);
        URI uriOfNewUser = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/api/v1/users/{userId}")
                .buildAndExpand(newUser.getId()).toUri();
        return ResponseEntity.created(uriOfNewUser).body(newUser);
    }

    @Operation(summary = "Update user by id", tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))
                    }
            ),
            @ApiResponse(responseCode = "400", description = "invalid input data"),
            @ApiResponse(responseCode = "404", description = "user not found"),
            @ApiResponse(responseCode = "405", description = "validation exception")
    })
    @PutMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@Parameter(description = "id of user", required = true)
                                       @PathVariable Long userId,
                                       @Parameter(description = "body of user", required = true,
                                                  schema = @Schema(implementation = User.class))
                                       @Valid @RequestBody User user,
                                       Errors errors){
        if(errors.hasErrors()){
            log.warn("Bad request. Request has errors: {}", errors.getAllErrors());
            return ResponseEntity.badRequest().body(RestSystemValidationErrorBuilder.fromBindingErrors(errors));
        }
        user.setId(userId);
        log.info("Update user with id {} to body {}", userId, user);
        User updatedUser = userService.update(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .buildAndExpand(updatedUser.getId())
                .toUri();
        return ResponseEntity.ok().header("Location", location.toString()).build();
    }

    @Operation(summary = "delete user by id", tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "deleted successful"),
            @ApiResponse(responseCode = "404", description = "user not found")
    })
    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<User> delete(@Parameter(description = "Id of user", required = true)
                                       @PathVariable Long userId){
        log.info("Deleting user with id {}", userId);
        userService.delete(userId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get user by authorization", tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))
                    }
            ),
            @ApiResponse(responseCode = "404", description = "Authorized user not found")
    })
    @GetMapping(value = "/authorized", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getAuthorizedUser(@Parameter(description = "Authorized user", required = true,
                                                             schema = @Schema(implementation = AuthorizedUser.class))
                                                  @AuthenticationPrincipal AuthorizedUser authorizedUser){
        log.info("Getting authorized user {}", authorizedUser);
        return ResponseEntity.ok(userService.get(authorizedUser.getId()));
    }

    @Operation(summary = "Update user by authorization", tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))
                    }
            ),
            @ApiResponse(responseCode = "400", description = "invalid input data"),
            @ApiResponse(responseCode = "404", description = "Authorized user not found"),
            @ApiResponse(responseCode = "405", description = "validation exception")
    })
    @PutMapping(value = "/authorized", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateAuthorizedUser(@Parameter(description = "User transfer object", required = true,
                                                                schema = @Schema(implementation = UserTo.class))
                                                     @Valid @RequestBody UserTo userTo,
                                                     @Parameter(description = "Authorized user object", required = true,
                                                                schema = @Schema(implementation = AuthorizedUser.class))
                                                     @AuthenticationPrincipal AuthorizedUser authorizedUser,
                                                     Errors errors){
        if(errors.hasErrors()){
            log.warn("Bad request. Request has errors: {}", errors.getAllErrors());
            return ResponseEntity.badRequest().body(RestSystemValidationErrorBuilder.fromBindingErrors(errors));
        }
        userTo.setId(authorizedUser.getId());
        log.info("Update authorized user {} by body {}", authorizedUser, userTo);
        User user = userService.update(userService.fromTo(userTo));
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.ok().header("Location", location.toString()).build();
    }

    @Operation(summary = "delete user by authorization", tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "deleted successful"),
            @ApiResponse(responseCode = "404", description = "authorization user not found")
    })
    @DeleteMapping(value = "/authorized")
    public ResponseEntity<User> deleteAuthorizedUser(@Parameter(description = "Authorized user object", required = true,
                                                                schema = @Schema(implementation = AuthorizedUser.class))
                                                     @AuthenticationPrincipal AuthorizedUser authorizedUser){
        log.info("Deleting authorized user {}", authorizedUser);
        userService.delete(authorizedUser.getId());
        return ResponseEntity.noContent().build();
    }
}
