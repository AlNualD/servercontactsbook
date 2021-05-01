package ru.devegang.servercontactsbook;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import ru.devegang.servercontactsbook.controllers.ContactsController;
import ru.devegang.servercontactsbook.entities.Contact;
import ru.devegang.servercontactsbook.entities.User;
import ru.devegang.servercontactsbook.services.ContactsService;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ContactsControllerTests {

    @Mock
    ContactsService contactsService;

    @InjectMocks
    ContactsController contactsController;

    Contact contact = new Contact(1,"name", "89999999999",new User(1,"name", new ArrayList<>()));

    @Test
    void getUserContacts() {
        Mockito.when(contactsService.getUserContacts(Mockito.anyLong())).thenReturn(List.of(contact));
        assertEquals(new ResponseEntity<>(List.of(contact), HttpStatus.OK),contactsController.getUserContacts(Mockito.anyLong()));

    }

    @Test
    void getUserContactsIncorrectId() {
        Mockito.when(contactsService.getUserContacts(Mockito.anyLong())).thenReturn(null);

        assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND),contactsController.getUserContacts(Mockito.anyLong()));
    }

    @Test
    void getContactByIDCorrect() {
        Mockito.when(contactsService.getContact(Mockito.anyLong())).thenReturn(Optional.of(contact));
        assertEquals(new ResponseEntity<>(contact,HttpStatus.OK),contactsController.getContact(Mockito.anyLong()));

    }
    @Test
    void getContactByIDIncorrect() {
        Mockito.when(contactsService.getContact(Mockito.anyLong())).thenReturn(Optional.empty());
        assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND),contactsController.getContact(Mockito.anyLong()));

    }
    @Test
    void addNewContactCorrect() {
        long id  = 1;
        Mockito.when(contactsService.createContact(id,contact)).thenReturn(Optional.of(contact));
        assertEquals(new ResponseEntity<>(contact,HttpStatus.OK),contactsController.createContact(id,contact));

    }
    @Test
    void addNewContactIncorrect() {
        long id  = 1;
        Mockito.when(contactsService.createContact(id,contact)).thenReturn(Optional.empty());
        assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND),contactsController.createContact(id,contact));

    }
    @Test
    void updateContactCorrect() {
        Mockito.when(contactsService.updateContact(contact)).thenReturn(true);
        assertEquals(new ResponseEntity<>(HttpStatus.OK),contactsController.updateContact(contact));

    }
    @Test
    void updateContactIncorrect() {
        Mockito.when(contactsService.updateContact(contact)).thenReturn(false);
        assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND),contactsController.updateContact(contact));

    }

    @Test
    void deleteContactCorrect() {
        Mockito.when(contactsService.deleteContact(Mockito.anyLong())).thenReturn(true);
        assertEquals(new ResponseEntity<>(HttpStatus.OK),contactsController.deleteContact(Mockito.anyLong()));

    }
    @Test
    void deleteContactIncorrect() {
        Mockito.when(contactsService.deleteContact(Mockito.anyLong())).thenReturn(false);
        assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND),contactsController.deleteContact(Mockito.anyLong()));

    }

    @Test
    void findContacts() {
        long id  = 1;
        Mockito.when(contactsService.getUserContactByNumber(id,"number")).thenReturn(List.of(contact));
        Mockito.when(contactsService.getUserContactByNumber(id,"numberDoesNotExist")).thenReturn(List.of());

        assertEquals(new ResponseEntity<>(List.of(contact), HttpStatus.OK),contactsController.findUserContactsByNumber(id,"number"));
        assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND),contactsController.findUserContactsByNumber(id,"numberDoesNotExist"));

    }


}
