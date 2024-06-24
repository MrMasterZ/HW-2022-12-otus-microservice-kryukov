package otus.student.kryukov.hw.microservicedocker.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import otus.student.kryukov.hw.microservicedocker.api.UserApi;
import otus.student.kryukov.hw.microservicedocker.model.User;
import otus.student.kryukov.hw.microservicedocker.service.UserService;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    public ResponseEntity<Void> createUser(@Parameter(name = "User",description = "Created user object",required = true) @RequestBody @Valid User user) {
        return userService.createUser(user);
    }

    @Override
    public ResponseEntity<Void> deleteUser(@Parameter(name = "userId", description = "ID of user", required = true) @PathVariable("userId") Long userId) {
        return userService.deleteUser(userId);
    }

    @Override
    public ResponseEntity<User> findUserById(@Parameter(name = "userId",description = "ID of user",required = true) @PathVariable("userId") Long userId) {
        return userService.findUserById(userId);
    }

    @Override
    public ResponseEntity<Void> updateUser(@Parameter(name = "userId",description = "ID of user",required = true) @PathVariable("userId") Long userId, @Parameter(name = "User",description = "") @RequestBody(required = false) @Valid User user) {
        return userService.updateUser(userId, user);
    }

}