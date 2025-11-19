package org.arturs.firstSpring.interfaces;

import org.arturs.firstSpring.models.UserDTO;
import org.arturs.firstSpring.models.UserModel;

public interface UserServiceInterface {

    UserDTO findOrSaveUser(UserModel user);

    void deleteUser(Long userId);

}
