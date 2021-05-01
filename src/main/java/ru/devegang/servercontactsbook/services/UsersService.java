package ru.devegang.servercontactsbook.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.devegang.servercontactsbook.entities.User;
import ru.devegang.servercontactsbook.repositories.UsersRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService implements UsersServiceInterface {

    @Autowired
    UsersRepository usersRepository;

    private boolean checkName(String name) {
        return name!=null&&!name.isBlank();
    }

    @Override
    public Optional<User> createUser(User user) {
        if(usersRepository.existsUserByName(user.getName()) || !checkName(user.getName())) {
            return Optional.empty();
        }

        user.setId(0);

        return Optional.of(usersRepository.saveAndFlush(user));
    }

    @Override
    public boolean updateUser(User user) {

        Optional<User> opOldUser = usersRepository.findById(user.getId());
        if(opOldUser.isPresent() && !usersRepository.existsUserByName(user.getName()) && checkName(user.getName())) {
            usersRepository.saveAndFlush(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteUser(Long user_id) {
        if(usersRepository.existsById(user_id)) {
            usersRepository.deleteById(user_id);
            return !usersRepository.existsById(user_id);
        }

        return false;
    }

    @Override
    public Optional<User> getUser(Long id) {

        return usersRepository.findById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }

    @Override
    public List<User> getAllByName(String name) {

        List<User> users = usersRepository.findAllByNameStartingWith(name);

        return users;
    }
}
