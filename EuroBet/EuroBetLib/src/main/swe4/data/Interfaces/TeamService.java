package main.swe4.data.Interfaces;

import main.swe4.data.Entities.Team;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

public interface TeamService extends Remote, Serializable {
    Collection<Team> getAllTeams() throws RemoteException;
}
