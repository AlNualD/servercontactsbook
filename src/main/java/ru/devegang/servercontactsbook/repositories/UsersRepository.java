package ru.devegang.servercontactsbook.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.devegang.servercontactsbook.entities.User;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<User,Long> {

    boolean existsUserByName(String name);
    Optional<User> findByName(String name);
    List<User> findAllByNameStartingWith(String namePart);
}
