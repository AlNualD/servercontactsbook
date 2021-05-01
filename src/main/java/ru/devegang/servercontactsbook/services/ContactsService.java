package ru.devegang.servercontactsbook.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.devegang.servercontactsbook.entities.Contact;
import ru.devegang.servercontactsbook.entities.User;
import ru.devegang.servercontactsbook.repositories.ContactsRepository;
import ru.devegang.servercontactsbook.repositories.UsersRepository;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ContactsService implements ContactsServiceInterface {


    private boolean checkNumber(String number) {
        Pattern pattern = Pattern.compile("^(8|(\\+?7))?\\d{10}$");
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }

    private boolean checkName(String name) {
        return name!=null&&!name.isBlank();
    }

    @Autowired
    UsersRepository usersRepository;
    @Autowired
    ContactsRepository contactsRepository;

    @Override
    public Optional<Contact> createContact(long user_id, Contact contact) {
        Optional<User> opUser = usersRepository.findById(user_id);
        if(opUser.isPresent() && checkNumber(contact.getNumber()) && checkName(contact.getName())) {
            contact.setUser(opUser.get());
            return Optional.of(contactsRepository.saveAndFlush(contact));
        }

        return Optional.empty();
    }

    @Override
    public boolean updateContact(Contact contact) {
        if(contactsRepository.existsById(contact.getId()) && checkNumber(contact.getNumber()) && checkName(contact.getName())) {
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

        return opUser.isPresent()? contactsRepository.findAllByUserAndNumberContaining(opUser.get(),number) : null;

    }
}
