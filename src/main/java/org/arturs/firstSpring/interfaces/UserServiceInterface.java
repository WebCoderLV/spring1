package org.arturs.firstSpring.interfaces;

import org.arturs.firstSpring.models.UserModel;

public interface UserServiceInterface {

    Long findOrSaveUser(UserModel user);

    void deleteUser(Long userId);

}
