package ru.devegang.servercontactsbook.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.devegang.servercontactsbook.entities.Contact;
import ru.devegang.servercontactsbook.entities.User;
import ru.devegang.servercontactsbook.repositories.ContactsRepository;
import ru.devegang.servercontactsbook.repositories.UsersRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ContactsService implements ContactsServiceInterface {

    @Autowired
    UsersRepository usersRepository;
    @Autowired
    ContactsRepository contactsRepository;

    @Override
    public Optional<Contact> createContact(long user_id, Contact contact) {
        Optional<User> opUser = usersRepository.findById(user_id);
        if(opUser.isPresent()) {
            contact.setUser(opUser.get());
            return Optional.of(contactsRepository.saveAndFlush(contact));
        }

        return Optional.empty();
    }

    @Override
    public boolean updateContact(Contact contact) {
        if(contactsRepository.existsById(contact.getId())) {
            contactsRepository.saveAndFlush(contact);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteContact(Long contact_id) {
        if(contactsRepository.existsById(contact_id)) {
            contactsRepository.deleteById(contact_id);
            return !contactsRepository.existsById(contact_id);
        }

        return false;
    }

    @Override
    public Optional<Contact> getContact(Long id) {
        return contactsRepository.findById(id);
    }

    @Override
    public List<Contact> getUserContacts(Long user_id) {

        Optional<User> opUser = usersRepository.findById(user_id);
        if(opUser.isPresent()) {
            User user = opUser.get();
            return user.getContacts();
        }
        return null;
    }

    @Override
    public List<Contact> getUserContactByNumber(long user_id, String number) {

        Optional<User> opUser = usersRepository.findById(user_id);
        if(opUser.isPresent()) {
            return contactsRepository.findAllByUserAndAndNumberContaining(opUser.get(),number);
        } else {
            return null;
        }

    }
}
