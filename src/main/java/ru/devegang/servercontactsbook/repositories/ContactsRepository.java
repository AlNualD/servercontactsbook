package ru.devegang.servercontactsbook.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.devegang.servercontactsbook.entities.Contact;
import ru.devegang.servercontactsbook.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactsRepository extends JpaRepository<Contact,Long> {
    Optional<Contact> findByNumber(String number);
    List<Contact> findAllByUserAndNumberContaining(User user, String numberPart);
}
