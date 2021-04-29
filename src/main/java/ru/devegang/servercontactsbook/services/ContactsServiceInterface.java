package ru.devegang.servercontactsbook.services;

import ru.devegang.servercontactsbook.entities.Contact;
import ru.devegang.servercontactsbook.entities.User;

import java.util.List;
import java.util.Optional;

public interface ContactsServiceInterface {
    Optional<Contact> createContact(long user_id, Contact contact);
    boolean updateContact(Contact contact);
    boolean deleteContact(Long contact_id);
    Optional<Contact> getContact(Long id);
    List<Contact> getUserContacts(Long user_id);
    List<Contact> getUserContactByNumber(long user_id, String number);
}
