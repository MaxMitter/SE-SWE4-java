package main.swe4.data.Interfaces;

import main.swe4.data.Entities.User;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

public interface UserService extends Remote, Serializable {
    public void createUser(User user) throws RemoteException;
    public void updateUser(User user) throws RemoteException;
    public void updatePassword(User user, String password) throws RemoteException;
    public void deleteUser(User user) throws RemoteException;
    public Collection<User> getAllUsers() throws RemoteException;
    public User getUserByUsername(String userName) throws RemoteException;
    public User login(String userName, String password) throws RemoteException;
}
