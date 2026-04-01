package com.studwarcraft.schedulers;

import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import com.studwarcraft.model.Profile;
import org.jboss.logging.Logger;

import java.util.List;

@ApplicationScoped
public class PlayerScheduler {

    @Inject
    EntityManager em;

    private static final Logger LOG = Logger.getLogger(PlayerScheduler.class);

    @Scheduled(every = "10s")
    @Transactional
    public void addGoldToPlayers() {

        List<Profile> profiles = em
                .createQuery("SELECT p FROM Profile p", Profile.class)
                .getResultList();

        // Prvi red: Podaci i info o broju profila
        LOG.info("Scheduler pokrenut. Broj profila: " + profiles.size());

        // Drugi red: pregled svih gold vrijednosti prije i poslije
        StringBuilder goldLog = new StringBuilder("Profili: ");
        for (int i = 0; i < profiles.size(); i++) {
            Profile p = profiles.get(i);
            int oldGold = p.getGold();
            p.setGold(oldGold + 1);

            goldLog.append("[ID ").append(p.getProfileid())
                    .append(", Role: ").append(p.getRole())
                    .append(", Gold: ").append(oldGold).append("->").append(p.getGold())
                    .append("] ");
        }

        LOG.info(goldLog.toString());
    }
}