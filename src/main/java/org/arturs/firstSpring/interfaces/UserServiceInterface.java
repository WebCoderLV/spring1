package org.arturs.firstSpring.interfaces;

import java.util.List;

import org.arturs.firstSpring.models.UserModel;

public interface UserServiceInterface {

    Boolean getUserByName(String name);

    List<UserModel> getAllUsers();

    int addUser(UserModel user);

    UserModel getUserById(long id);

    void deleteUser(long id);

    void updateUser(UserModel user);

}
