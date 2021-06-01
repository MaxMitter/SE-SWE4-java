package main.swe4.data.Interfaces;

import main.swe4.data.Entities.User;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

public interface UserService extends Remote, Serializable {
    void createUser(User user) throws RemoteException;
    void updateUser(User user) throws RemoteException;
    void updatePassword(User user, String password) throws RemoteException;
    void deleteUser(User user) throws RemoteException;
    Collection<User> getAllUsers() throws RemoteException;
    User getUserByUsername(String userName) throws RemoteException;
    User login(String userName, String password) throws RemoteException;
}
