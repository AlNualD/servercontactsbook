package ru.devegang.servercontactsbook.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.devegang.servercontactsbook.entities.Contact;
import ru.devegang.servercontactsbook.entities.User;

import java.util.List;
import java.util.Optional;

public interface ContactsRepository extends JpaRepository<Contact,Long> {
    Optional<Contact> findByNumber(String number);
    List<Contact> findAllByUserAndAndNumberContaining(User user, String numberPart);
    List<Contact> findAllBy
}
