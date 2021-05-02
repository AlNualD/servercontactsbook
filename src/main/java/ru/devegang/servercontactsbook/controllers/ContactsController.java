package ru.devegang.servercontactsbook.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.devegang.servercontactsbook.entities.Contact;
import ru.devegang.servercontactsbook.entities.User;
import ru.devegang.servercontactsbook.services.ContactsService;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Контакты", description = "Контроллер для работы с контактами пользователя")
public class ContactsController {
    @Autowired
    ContactsService contactsService;


    @Operation(summary = "Получить список контактов пользователя")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешно",
                    content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Contact.class)))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ошибка: нет такого пользователя или у пользователя нет контактов"
            )
    })
    @GetMapping("/{user_id}/contacts")
    public ResponseEntity<?> getUserContacts(@PathVariable(name = "user_id")long user_id) {
    List<Contact> contacts = contactsService.getUserContacts(user_id);
    return contacts!= null && !contacts.isEmpty() ? new ResponseEntity<>(contacts, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Получить контакт по ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешно",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Contact.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ошибка: нет контакта с таким ID"
            )
    })
    @GetMapping("/contacts/{id}")
    public ResponseEntity<?> getContact(@PathVariable(name = "id")long id) {
        Optional<Contact> opContact = contactsService.getContact(id);
        return opContact.isPresent() ? new ResponseEntity<>(opContact.get(),HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Добавление нового контакта", description = "Добавление нового контакта в книгу контактов пользователя. Номер должен быть в виде +7dddddddddd или 8dddddddddd или dddddddddd")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешно",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Contact.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Ошибка: Некорректное id пользвателя, имя контакта или номер телефона"
            )
    })
    @PostMapping("/{user_id}/contacts")
    public  ResponseEntity<?> createContact(@PathVariable(name = "user_id") long user_id, @RequestBody Contact contact) {
        Optional<Contact> opContact = contactsService.createContact(user_id, contact);
        return opContact.isPresent() ? new ResponseEntity<>(opContact.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Обновление контакта", description = "Обновление существующего контакта. Номер должен быть в виде +7dddddddddd или 7dddddddddd или 8dddddddddd или dddddddddd")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешно"
            ),
            @ApiResponse(
                    responseCode = "304",
                    description = "Ошибка: Некорректное имя контакта или номер телефона"
            )
    })
    @PutMapping("/contacts")
    public ResponseEntity<?> updateContact(@RequestBody Contact contact) {
        return contactsService.updateContact(contact) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @Operation(summary = "Удалить контакт по номеру")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешно"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ошибка: нет контакта с таким ID"
            )
    })
    @DeleteMapping("/contacts")
    public ResponseEntity<?> deleteContact(@RequestParam(name = "id") long id) {
        return contactsService.deleteContact(id) ? new ResponseEntity<>(HttpStatus.OK)  : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Найти контакт пользователя по номеру", description = "Можно ввести часть номера")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешно",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Contact.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ошибка: нет такого пользователя или у данного пользователя нет контактов, содержащих номер с такой последовтельностью цифр"
            )
    })
    @GetMapping("/{user_id}/contacts/find")
    public ResponseEntity<?> findUserContactsByNumber(@PathVariable(name = "user_id") long user_id, @RequestParam(name = "number_part") String numberPart) {
       List<Contact> contacts = contactsService.getUserContactByNumber(user_id, numberPart);

        return contacts!= null && !contacts.isEmpty() ? new ResponseEntity<>(contacts, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }




}
