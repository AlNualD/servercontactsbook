package ru.devegang.servercontactsbook;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

import ru.devegang.servercontactsbook.entities.Contact;
import ru.devegang.servercontactsbook.entities.User;
import ru.devegang.servercontactsbook.repositories.ContactsRepository;
import ru.devegang.servercontactsbook.repositories.UsersRepository;
import ru.devegang.servercontactsbook.services.ContactsService;

import java.util.ArrayList;
import java.util.List;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ContactsServiceTests {
    @Mock
    private ContactsRepository contactsRepository;

    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private ContactsService contactsService;

    private User user = new User(1,"name",new ArrayList<>());




    private Contact contact = new Contact(1,"ContactName","89203033445",user);


    @Test
    public void getContactWithCorrectID() {
        long id = 1;
        Mockito.when(contactsRepository.findById(id)).thenReturn(Optional.of(contact));
        assertEquals(contact,contactsService.getContact(id).get());
    }

    @Test
    public void getContactWithIncorrectID() {
        Mockito.when(contactsRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        assertFalse(contactsRepository.findById(Mockito.anyLong()).isPresent());
    }

    @Test
    public void getUserContactsWithCorrectID() {
        User userWithContacts = user;
        userWithContacts.getContacts().add(contact);
        Mockito.when(usersRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
        assertEquals(List.of(contact),contactsService.getUserContacts(Mockito.anyLong()));
    }

    @Test
    public void getUserContactsWithIncorrectID() {
        Mockito.when(usersRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        assertNull(contactsService.getUserContacts(Mockito.anyLong()));
    }

    @Test
    public void getUserContactByNumber() {
        Mockito.when(usersRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
        Mockito.when(contactsRepository.findAllByUserAndNumberContaining(user, "number")).thenReturn(List.of(contact));

        assertEquals(List.of(contact),contactsService.getUserContactByNumber(Mockito.anyLong(),"number"));

    }


    @Test void  getUserContactsByNumberWhichNotExist() {
        Mockito.when(usersRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
        Mockito.when(contactsRepository.findAllByUserAndNumberContaining(user, "notExist")).thenReturn(List.of());
        assertEquals(List.of(),contactsService.getUserContactByNumber(Mockito.anyLong(),"notExist"));

    }


    @Test
    public void addContactWithCorrectNumber(){
        Contact contact = new Contact();
        contact.setId(1);
        contact.setName("correct name");
        contact.setNumber("+70000000000");
        contact.setUser(new User());
        Mockito.when(usersRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));

        Mockito.when(contactsRepository.saveAndFlush(contact)).thenReturn(contact);

        assertEquals(contact,contactsService.createContact(Mockito.anyLong(),contact).get());

        contact.setNumber("80000000000");
        assertEquals(contact,contactsService.createContact(Mockito.anyLong(),contact).get());


        contact.setNumber("70000000000");
        assertEquals(contact,contactsService.createContact(Mockito.anyLong(),contact).get());

        contact.setNumber("0000000000");
        assertEquals(contact,contactsService.createContact(Mockito.anyLong(),contact).get());




    }

    @Test
    public void addContactWithIncorrectNumber(){

        Contact contact = new Contact();
        contact.setId(1);
        contact.setName("correct name");
        contact.setNumber("+7000000000");
        contact.setUser(new User());

        Mockito.when(usersRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
//        Mockito.when(contactsRepository.saveAndFlush(contact)).thenReturn(contact);

        assertEquals(Optional.empty(),contactsService.createContact(Mockito.anyLong(),contact));


        contact.setNumber("+80000000000");
        assertEquals(Optional.empty(),contactsService.createContact(Mockito.anyLong(),contact));


    }

    @Test
    public void addContactWithoutCorrectUser() {


        Mockito.when(usersRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        assertEquals(Optional.empty(),contactsService.createContact(Mockito.anyLong(),contact));


    }

    @Test
    public void updateContactCorrectly() {
        Contact contact = new Contact();
        contact.setId(1);
        contact.setName("correct name");
        contact.setNumber("+70000000000");
        contact.setUser(new User());

        Mockito.when(contactsRepository.existsById(Mockito.anyLong())).thenReturn(true);
        Mockito.when(contactsRepository.saveAndFlush(contact)).thenReturn(contact);

        assertTrue(contactsService.updateContact(contact));

    }

    @Test
    public void updateContactWithIncorrectId() {
        Mockito.when(contactsRepository.existsById(Mockito.anyLong())).thenReturn(false);
        assertFalse(contactsService.updateContact(contact));
    }
    @Test
    public void updateContactWithIncorrectNumber() {

        Contact contact = new Contact();
        contact.setId(1);
        contact.setName("correct name");
        contact.setNumber("+7000000000");
        contact.setUser(new User());

        Mockito.when(contactsRepository.existsById(Mockito.anyLong())).thenReturn(true);
        assertFalse(contactsService.updateContact(contact));

    }

    @Test void updateContactWithEmptyName () {
        Contact contact = new Contact();
        contact.setId(1);
        contact.setName("");
        contact.setNumber("70000000000");
        contact.setUser(new User());
        Mockito.when(contactsRepository.existsById(Mockito.anyLong())).thenReturn(true);
        assertFalse(contactsService.updateContact(contact));

    }

}
