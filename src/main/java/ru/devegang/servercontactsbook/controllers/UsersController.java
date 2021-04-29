package ru.devegang.servercontactsbook.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.devegang.servercontactsbook.entities.User;
import ru.devegang.servercontactsbook.services.UsersService;
import ru.devegang.servercontactsbook.services.UsersServiceInterface;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1")
@Tag(name = "Пользователи", description = "Контроллер для управления пользователями")
public class UsersController {
    @Autowired
    UsersService usersService;


    @Operation(summary = "Получить список всех пользователей")
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = usersService.getAllUsers();
        return users!=null && !users.isEmpty() ? new ResponseEntity<>(users, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Получить информацию о пользователе по ID")
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable(name = "id") long id) {
        Optional<User> opUser = usersService.getUser(id);
        return opUser.isPresent() ? new ResponseEntity<>(opUser.get(),HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @Operation(summary = "Создать нового пользователя", description = "Имя не должно повторяться, иначе метод вернет код 304")
    @PostMapping("/user")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        Optional<User> opUser = usersService.createUser(user);

        return opUser.isPresent() ? new ResponseEntity<>(opUser.get(),HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @Operation(summary = "Обновить информацию о пользователе", description = "Имя не должно повторяться, иначе метод не обновит данные и вернет ошибку")
    @PutMapping("/user/{id}")
    public  ResponseEntity<?> updateUser(@PathVariable(name = "id") long id, @RequestBody User user) {
        user.setId(id);
        return usersService.updateUser(user) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @Operation(summary = "Удалить пользователя")
    @DeleteMapping("/user")
    public ResponseEntity<?> deleteUser(@RequestParam(name = "id") long id) {
        return usersService.deleteUser(id) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Найти пользователя по имени", description = "Можно ввести часть имени, тогда выдаст список пользователей с именами начинающимися на эти символы")
    @GetMapping("/user/find")
    public ResponseEntity<?> findUserByName(@RequestParam(name = "name_part") String name) {
        List<User> users = usersService.getAllByName(name);
        return users!=null && !users.isEmpty() ? new ResponseEntity<>(users, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



}
