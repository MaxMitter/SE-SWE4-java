package main.swe4.data.Interfaces;

import main.swe4.data.Entities.Team;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

public interface TeamService extends Remote {
    Collection<Team> getAllTeams() throws RemoteException;
}
