package main.swe4.data.Interfaces;

import main.swe4.Exceptions.UserAlreadyExistsException;
import main.swe4.data.Entities.Role;
import main.swe4.data.Entities.User;
import main.swe4.data.Repository;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;

public class UserServiceImplementation implements UserService {
    @Override
    public void createUser(String firstName, String lastName, String userName, String password, Role role) throws RemoteException {
        try {
            Repository.Instance().CreateNewUser(firstName, lastName, userName, password);
        } catch (UserAlreadyExistsException ex) {
            throw new RemoteException("User already exists");
        }
    }

    @Override
    public void updateUser(User user) throws RemoteException {
        Repository.Instance().UpdateUser(user);
    }

    @Override
    public void updatePassword(User user, String password) throws RemoteException {
        Repository.Instance().UpdateUserPassword(user.getId(), password);
    }

    @Override
    public void deleteUser(User user) throws RemoteException {
        Repository.Instance().DeleteUser(user);
    }

    @Override
    public Collection<User> getAllUsers() throws RemoteException {
        return Repository.Instance().GetAllUsers();
    }

    @Override
    public User getUserByUsername(String userName) throws RemoteException {
        for (var u : Repository.Instance().GetAllUsers()) {
            if (u.getUserName().equals(userName)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public User getUserByUsernameAndPassword(String userName, String password) throws RemoteException {
        for (var u : Repository.Instance().GetAllUsers()) {
            if (u.isValidLogin(userName, password)) {
                return u;
            }
        }
        return null;
    }
}
