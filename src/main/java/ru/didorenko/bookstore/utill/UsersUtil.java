package ru.didorenko.bookstore.utill;

import lombok.experimental.UtilityClass;
import ru.didorenko.bookstore.model.Role;
import ru.didorenko.bookstore.model.User;
import ru.didorenko.bookstore.to.UserTo;

@UtilityClass
public class UsersUtil {

    public static User createNewFromTo(UserTo userTo) {
        return new User(null, userTo.getName(), userTo.getEmail().toLowerCase(), userTo.getPassword(), Role.USER);
    }

    public static User updateFromTo(User user, UserTo userTo) {
        user.setName(userTo.getName());
        user.setEmail(userTo.getEmail().toLowerCase());
        user.setPassword(userTo.getPassword());
        return user;
    }
}