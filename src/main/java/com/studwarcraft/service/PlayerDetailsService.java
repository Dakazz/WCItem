package com.studwarcraft.service;

import com.studwarcraft.exception.PlayerException;
import com.studwarcraft.model.PlayerDetails;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;

@Dependent
public class PlayerDetailsService {

    @Inject
    private EntityManager em;

    @Transactional
    public PlayerDetails createPlayerDetails(PlayerDetails details) throws PlayerException {
        if(details == null) {
            throw new PlayerException("PlayerDetails nije prosleđen");
        }
        if(details.getPlayer() == null) {
            throw new PlayerException("PlayerDetails mora imati povezanog igrača");
        }
        if(details.getAvatar() == null || details.getAvatar().isEmpty()) {
            throw new PlayerException("Avatar je prazan");
        }

        return em.merge(details);
    }

    public List<PlayerDetails> getAllPlayerDetails() throws PlayerException {
        List<PlayerDetails> details = em.createQuery("SELECT d FROM PlayerDetails d", PlayerDetails.class).getResultList();
        if(details.isEmpty()) {
            throw new PlayerException("Nema PlayerDetails.");
        }
        return details;
    }

    public PlayerDetails getDetailsByPlayerId(Long playerId) {
        List<PlayerDetails> details = em.createQuery(
                        "SELECT d FROM PlayerDetails d WHERE d.player.id = :id", PlayerDetails.class)
                .setParameter("id", playerId)
                .getResultList();
        return details.isEmpty() ? null : details.get(0);
    }
}