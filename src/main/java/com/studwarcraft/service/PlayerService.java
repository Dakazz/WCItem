package com.studwarcraft.service;

import com.studwarcraft.exception.PlayerException;
import com.studwarcraft.model.Player;
import com.studwarcraft.model.PlayerTimeZone;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;

import java.util.List;

@Dependent
public class PlayerService {

    @Inject
    private EntityManager em;

    @Transactional
    public Player createPlayer(Player player) throws PlayerException {
        if (player == null) {
            throw new PlayerException("Igrač nije proslijeđen.");
        }
        if (player.getName() == null || player.getName().isEmpty()) {
            throw new PlayerException("Ime igrača je prazno.");
        }
        if (player.getLastname() == null || player.getLastname().isEmpty()) {
            throw new PlayerException("Prezime igrača je prazno.");
        }

        return em.merge(player);
    }

    @Transactional
    public List<Player> getAllPlayers() throws PlayerException {
        List<Player> players = em.createNamedQuery(Player.GET_ALL_PLAYERS, Player.class).getResultList();
        if (players.isEmpty()) {
            throw new PlayerException("Nema igrača.");
        }
        return players;
    }

    public Player getPlayerById(Long id) {
        try {
            return em.createNamedQuery(Player.GET_PLAYER_BY_ID, Player.class)
                    .setParameter("idP", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Player> getPlayersByName(String name) {
        return em.createNamedQuery(Player.GET_PLAYERS_BY_NAME, Player.class)
                .setParameter("nameP", name)
                .getResultList();
    }

    public List<Player> getPlayersByRole(String role) {
        return em.createNamedQuery(Player.GET_PLAYERS_BY_ROLE, Player.class)
                .setParameter("roleP", role)
                .getResultList();
    }

    @Transactional
    public PlayerTimeZone addTimeZoneToPlayer(Long userId, PlayerTimeZone playerTimeZone) throws PlayerException {
        Player player = em.find(Player.class, userId);
        if (player == null) {
            throw new PlayerException("Igrač sa id " + userId + " ne postoji.");
        }

        player.addTimeZone(playerTimeZone);
        em.persist(playerTimeZone);

        return playerTimeZone;
    }
}
