package ru.devegang.servercontactsbook;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.devegang.servercontactsbook.controllers.UsersController;
import ru.devegang.servercontactsbook.entities.User;
import ru.devegang.servercontactsbook.services.UsersService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UsersControllerTests {
    @Mock
    UsersService usersService;

    @InjectMocks
    UsersController usersController;

    private User user = new User(1,"Correct name",new ArrayList<>());


    @Test
    void getUserByIdCorrect() {
        Mockito.when(usersService.getUser(Mockito.anyLong())).thenReturn(Optional.of(user));

        assertEquals(new ResponseEntity<>(user, HttpStatus.OK),usersController.getUserById(Mockito.anyLong()));

    }
    @Test
    void getUserByIdIncorrect() {
        Mockito.when(usersService.getUser(Mockito.anyLong())).thenReturn(Optional.empty());
        assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND),usersController.getUserById(Mockito.anyLong()));
    }

    @Test
    void addNewUserCorrect() {
        Mockito.when(usersService.createUser(user)).thenReturn(Optional.of(user));
        assertEquals(new ResponseEntity<>(user,HttpStatus.OK),usersController.createUser(user));

    }
    @Test
    void addNewUserIncorrect() {
        Mockito.when(usersService.createUser(user)).thenReturn(Optional.empty());
        assertEquals(new ResponseEntity<>(HttpStatus.BAD_REQUEST),usersController.createUser(user));
    }

    @Test
    void updateUserCorrect() {
        Mockito.when(usersService.updateUser(user)).thenReturn(true);
        assertEquals(new ResponseEntity<>(HttpStatus.OK),usersController.updateUser(user.getId(),user));

    }
    @Test
    void updateUserIncorrect() {
        Mockito.when(usersService.updateUser(user)).thenReturn(false);
        assertEquals(new ResponseEntity<>(HttpStatus.NOT_MODIFIED),usersController.updateUser(user.getId(),user));

    }

    @Test
    void deleteUserCorrect() {
        Mockito.when(usersService.deleteUser(Mockito.anyLong())).thenReturn(true);
        assertEquals(new ResponseEntity<>(HttpStatus.OK),usersController.deleteUser(Mockito.anyLong()));

    }
    @Test
    void deleteUserIncorrect() {
        Mockito.when(usersService.deleteUser(Mockito.anyLong())).thenReturn(false);
        assertEquals(new ResponseEntity<>(HttpStatus.BAD_REQUEST),usersController.deleteUser(Mockito.anyLong()));

    }

    @Test
    void getAllUsersExist() {
        Mockito.when(usersService.getAllUsers()).thenReturn(List.of(user));
        assertEquals(new ResponseEntity<>(List.of(user),HttpStatus.OK),usersController.getAllUsers());
    }

    @Test
    void getAllUsersNotExist() {
        Mockito.when(usersService.getAllUsers()).thenReturn(List.of());
        assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND),usersController.getAllUsers());
    }
    @Test
    void getAllUsersByNameExist() {
        Mockito.when(usersService.getAllByName(Mockito.anyString())).thenReturn(List.of(user));
        assertEquals(new ResponseEntity<>(List.of(user),HttpStatus.OK),usersController.findUserByName(Mockito.anyString()));
    }

    @Test
    void getAllUsersByNameNotExist() {
        Mockito.when(usersService.getAllByName(Mockito.anyString())).thenReturn(List.of());
        assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND),usersController.findUserByName(Mockito.anyString()));
    }
}
