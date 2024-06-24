package otus.student.kryukov.hw.microservicedocker.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import otus.student.kryukov.hw.microservicedocker.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class UsersDaoJdbc implements UsersDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public void createByUser(User user) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("username", user.getUsername())
                .addValue("first_name", user.getFirstName())
                .addValue("last_name", user.getLastName())
                .addValue("email", user.getEmail())
                .addValue("phone", user.getPhone());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcOperations.update(
                "insert into USERS (username, first_name, last_name, email, phone) values (:username, :first_name, :last_name, :email, :phone)", params, keyHolder, new String[]{"user_id"}
        );
        System.out.println("user_id=" + keyHolder.getKey().longValue()); // для удобства тестирования
    }

    @Override
    public void deleteUser(Long userId) {
        Map<String, Long> params = Collections.singletonMap("user_id", userId);
        namedParameterJdbcOperations.update(
                "delete from users where user_id = :user_id", params
        );
    }

    @Override
    public User findUserById(Long userId) {
        Map<String, Long> params = Collections.singletonMap("user_id", userId);
        User user = namedParameterJdbcOperations.queryForObject(
                "select user_id, username, first_name, last_name, email, phone from users where user_id = :user_id", params, new UsersMapper()
        );
        return user;
    }

    @Override
    public void updateUser(Long userId, User user) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("user_id", userId)
                .addValue("username", user.getUsername())
                .addValue("first_name", user.getFirstName())
                .addValue("last_name", user.getLastName())
                .addValue("email", user.getEmail())
                .addValue("phone", user.getPhone());
        namedParameterJdbcOperations.update(
                "update users set username = :username, first_name = :first_name, last_name = :last_name, email = :email, phone = :phone where user_id = :user_id", params
        );
    }

    private static class UsersMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int i) throws SQLException {
            Long id = rs.getLong("user_id");
            String username = rs.getString("username");
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            String email = rs.getString("email");
            String phone = rs.getString("phone");
            return new User()
                    .id(id)
                    .username(username)
                    .firstName(firstName)
                    .lastName(lastName)
                    .email(email)
                    .phone(phone);
        }
    }
}