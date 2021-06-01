package main.swe4.data.Interfaces;

import main.swe4.data.Entities.Team;
import main.swe4.data.Repository;

import java.rmi.RemoteException;
import java.util.Collection;

public class TeamServiceImplementation implements TeamService {
    @Override
    public Collection<Team> getAllTeams() throws RemoteException {
        return Repository.Instance().GetAllTeams();
    }
}
