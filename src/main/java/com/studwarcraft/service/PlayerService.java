package com.studwarcraft.service;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import com.studwarcraft.exception.PlayerException;
import com.studwarcraft.model.Player;

import java.util.List;

@Dependent
public class PlayerService {

    @Inject
    private EntityManager em;

    @Transactional
    public Player createPlayer(Player player) throws PlayerException {
        if (player == null) throw new PlayerException("Player nije proslijeđen");
        if (player.getName() == null || player.getName().isEmpty()) throw new PlayerException("Ime je prazno");
        if (player.getLastname() == null || player.getLastname().isEmpty()) throw new PlayerException("Prezime je prazno");

        return em.merge(player);
    }

    public List<Player> getAllPlayers() throws PlayerException {
        List<Player> players = em.createNamedQuery(Player.GET_ALL_PLAYERS, Player.class).getResultList();
        if (players.isEmpty()) throw new PlayerException("Nema igrača.");
        return players;
    }
}