package ru.didorenko.bookstore.repository;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.didorenko.bookstore.common.BaseRepository;
import ru.didorenko.bookstore.common.error.NotFoundException;
import ru.didorenko.bookstore.model.User;
import java.util.Optional;

import static ru.didorenko.bookstore.app.config.SecurityConfig.PASSWORD_ENCODER;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends BaseRepository<User> {

    @Cacheable(value = "users", key = "#email")
    @Query("SELECT u FROM User u WHERE u.email = LOWER(:email)")
    Optional<User> findByEmailIgnoreCase(String email);

    @Transactional
    @CacheEvict(value = "users", key = "#user.email")
    default User prepareAndSave(User user) {
        user.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        return save(user);
    }

    default User getExistedByEmail(String email) {
        return findByEmailIgnoreCase(email).orElseThrow(() -> new NotFoundException("User with email=" + email + " not found"));
    }
}