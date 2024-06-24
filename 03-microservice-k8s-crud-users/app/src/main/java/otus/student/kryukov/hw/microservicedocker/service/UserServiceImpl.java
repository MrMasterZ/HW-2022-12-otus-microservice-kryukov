package otus.student.kryukov.hw.microservicedocker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import otus.student.kryukov.hw.microservicedocker.dao.UsersDao;
import otus.student.kryukov.hw.microservicedocker.model.User;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UsersDao usersDao;
    @Transactional
    @Override
    public ResponseEntity<Void> createUser(User user) {
        usersDao.createByUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<Void> deleteUser(Long userId) {
        usersDao.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Transactional(readOnly = true)
    @Override
    public ResponseEntity<User> findUserById(Long userId) {
        User user = usersDao.findUserById(userId);
        return new ResponseEntity<User>(user,HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<Void> updateUser(Long userId, User user) {
        usersDao.updateUser(userId, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
