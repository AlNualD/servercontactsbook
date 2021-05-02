package ru.devegang.servercontactsbook.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешно",
                    content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = User.class)))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ошибка: нет пользователей"
            )
    })
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = usersService.getAllUsers();
        return users!=null && !users.isEmpty() ? new ResponseEntity<>(users, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Получить информацию о пользователе по ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешно",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ошибка: нет пользователя с таким ID"
            )
    })
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable(name = "id") long id) {
        Optional<User> opUser = usersService.getUser(id);
        return opUser.isPresent() ? new ResponseEntity<>(opUser.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @Operation(summary = "Создать нового пользователя", description = "Имя не должно повторяться.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешно",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Ошибка: Неккоректное имя или пользователь с таким именем уже существует"
            )
    })
    @PostMapping("/user")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        Optional<User> opUser = usersService.createUser(user);

        return opUser.isPresent() ? new ResponseEntity<>(opUser.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @Operation(summary = "Обновить информацию о пользователе", description = "Имя не должно повторяться")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешно"
            ),
            @ApiResponse(
                    responseCode = "304",
                    description = "Ошибка: Неккоректное id, имя или пользователь с таким именем уже существует"
            )
    })
    @PutMapping("/user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable(name = "id") long id, @RequestBody User user) {
        user.setId(id);
        return usersService.updateUser(user) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @Operation(summary = "Удалить пользователя")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешно"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ошибка: нет пользователя с таким ID"
            )
    })
    @DeleteMapping("/user")
    public ResponseEntity<?> deleteUser(@RequestParam(name = "id") long id) {
        return usersService.deleteUser(id) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Найти пользователя по имени", description = "Можно ввести часть имени, тогда выдаст список пользователей с именами начинающимися на эти символы")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешно",
                    content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = User.class)))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ошибка: нет пользователей, имя которых начинается на данную последовательность символов"
            )
    })
    @GetMapping("/user/find")
    public ResponseEntity<?> findUserByName(@RequestParam(name = "name_part") String name) {
        List<User> users = usersService.getAllByName(name);
        return users!=null && !users.isEmpty() ? new ResponseEntity<>(users, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



}
