package ru.devegang.servercontactsbook;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import ru.devegang.servercontactsbook.entities.User;
import ru.devegang.servercontactsbook.repositories.UsersRepository;
import ru.devegang.servercontactsbook.services.UsersService;
import static org.junit.jupiter.api.Assertions.*;


import java.util.ArrayList;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UsersServiceTests {
    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private UsersService usersService;

    private User user = new User(1,"Correct name",new ArrayList<>());

    @Test
    void createUserCorrect(){
        Mockito.when(usersRepository.existsUserByName("Correct name")).thenReturn(false);
        Mockito.when(usersRepository.saveAndFlush(user)).thenReturn(user);
        assertEquals(Optional.of(user),usersService.createUser(user));
    }

    @Test
    void createUserIncorrectName() {

        User user = new User(1,"Incorrect name",new ArrayList<>());

        Mockito.when(usersRepository.existsUserByName("Incorrect name")).thenReturn(true);
        assertEquals(Optional.empty(),usersService.createUser(user));
        user.setName("");
        assertEquals(Optional.empty(),usersService.createUser(user));
    }

    @Test
    void updateUserCorrect() {
        User user2 = new User(2,"new correct name",new ArrayList<>());

        Mockito.when(usersRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(user));
        Mockito.when(usersRepository.existsUserByName("new correct name")).thenReturn(false);
        Mockito.when(usersRepository.saveAndFlush(user)).thenReturn(user);
        Mockito.when(usersRepository.saveAndFlush(user2)).thenReturn(user2);
        assertTrue(usersService.updateUser(user));
        assertTrue(usersService.updateUser(user2));



    }

    @Test
    void updateUserIncorrect() {
        long correct_id = 1;
        long incorrect_id = 2;
        User newUser = new User(correct_id,"Incorrect name",new ArrayList<>());

        Mockito.when(usersRepository.findById(correct_id)).thenReturn(Optional.ofNullable(user));
        Mockito.when(usersRepository.findById(incorrect_id)).thenReturn(Optional.empty());

        Mockito.when(usersRepository.existsUserByName("Incorrect name")).thenReturn(true);
        assertFalse(usersService.updateUser(newUser));
        newUser.setName("");
        assertFalse(usersService.updateUser(newUser));
        newUser.setName("name");
        newUser.setId(incorrect_id);
        assertFalse(usersService.updateUser(newUser));
    }

    @Test
    void getUserCorrect() {
        Mockito.when(usersRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(user));
        assertEquals(Optional.of(user),usersService.getUser(Mockito.anyLong()));

    }

    @Test
    void getUserIncorrect() {
        Mockito.when(usersRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        assertEquals(Optional.empty(),usersService.getUser(Mockito.anyLong()));

    }


}
