package otus.student.kryukov.hw.microservicedocker.service;

import org.springframework.http.ResponseEntity;
import otus.student.kryukov.hw.microservicedocker.model.User;

public interface UserService {

    ResponseEntity<Void> createUser(User user);

    ResponseEntity<Void> deleteUser(Long userId);

    ResponseEntity<User> findUserById(Long userId);

    ResponseEntity<Void> updateUser(Long userId, User user);

}
