package otus.student.kryukov.hw.microservicedocker.dao;

import otus.student.kryukov.hw.microservicedocker.model.User;

public interface UsersDao {

    void createByUser(User user);

    void deleteUser(Long userId);

    User findUserById(Long userId);

    void updateUser(Long userId, User user);

}
