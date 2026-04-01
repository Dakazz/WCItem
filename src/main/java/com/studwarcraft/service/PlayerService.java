package com.studwarcraft.service;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import com.studwarcraft.model.Player;

import java.util.List;

@Dependent
public class PlayerService {

    @Inject
    private EntityManager em;

    @Transactional
    public Player createPlayer(Player player) {
        return em.merge(player);
    }

    public List<Player> getAllPlayers() {
        // fetch details i profile odmah
        return em.createQuery(
                        "SELECT p FROM Player p " +
                                "LEFT JOIN FETCH p.details " +
                                "LEFT JOIN FETCH p.profile", Player.class)
                .getResultList();
    }

    public Player getPlayerById(Long id) {
        try {
            return em.createQuery(
                            "SELECT p FROM Player p " +
                                    "LEFT JOIN FETCH p.details " +
                                    "LEFT JOIN FETCH p.profile " +
                                    "WHERE p.playerid = :id", Player.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Player> getPlayersByName(String name) {
        return em.createQuery(
                        "SELECT p FROM Player p " +
                                "LEFT JOIN FETCH p.details " +
                                "LEFT JOIN FETCH p.profile " +
                                "WHERE p.name = :name", Player.class)
                .setParameter("name", name)
                .getResultList();
    }

    public List<Player> getPlayersByRole(String role) {
        return em.createQuery(
                        "SELECT p FROM Player p " +
                                "LEFT JOIN FETCH p.profile " +
                                "LEFT JOIN FETCH p.details " +
                                "WHERE p.profile.role = :role", Player.class)
                .setParameter("role", role)
                .getResultList();
    }
}