package ru.devegang.servercontactsbook.services;

import org.springframework.lang.Nullable;
import ru.devegang.servercontactsbook.entities.User;

import java.util.List;
import java.util.Optional;

public interface UsersServiceInterface {
    Optional<User> createUser(User user);
    boolean updateUser(User user);
    boolean deleteUser(Long user_id);
    Optional<User> getUser(Long id);
    List<User> getAllUsers();
    List<User> getAllByName(String name);
}
