package swe4.gui.data;

import swe4.gui.Exceptions.UserAlreadyExistsException;
import swe4.gui.Exceptions.UserNotFoundException;
import swe4.gui.data.Entities.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Repository {

    public static Repository Instance = new Repository();
    private ArrayList<User> mockUsers = null;

    private Repository() {
        mockUsers = new ArrayList<>();
        InitMockUsers();
    }

    private void InitMockUsers() {
        mockUsers.add(new User("admin", "admin"));
        mockUsers.add(new User("Test1", "test"));
        mockUsers.add(new User("Test2", "test"));
    }



    public boolean isValidLogin(String userName, String password) throws UserNotFoundException {
        for (User user : mockUsers) {
            if (user.getUserName().equals(userName)) {
                return user.checkPassword(password);
            }
        }
        throw new UserNotFoundException();
    }

    public void createNewUser(String name, String pw) throws UserAlreadyExistsException {
        for (User user : mockUsers) {
            if (user.getUserName().equals(name)) {
                throw new UserAlreadyExistsException();
            }
        }

        mockUsers.add(new User(name, pw));
    }
}
