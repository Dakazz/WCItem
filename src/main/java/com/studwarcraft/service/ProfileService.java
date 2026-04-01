package com.studwarcraft.service;

import com.studwarcraft.exception.PlayerException;
import com.studwarcraft.model.Profile;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;

@Dependent
public class ProfileService {

    @Inject
    private EntityManager em;

    @Transactional
    public Profile createProfile(Profile profile) throws PlayerException {
        if(profile == null) {
            throw new PlayerException("Profile nije prosleđen");
        }
        if(profile.getPlayer() == null) {
            throw new PlayerException("Profile mora imati povezanog igrača");
        }
        if(profile.getRole() == null || profile.getRole().isEmpty()) {
            throw new PlayerException("Role je prazna");
        }

        return em.merge(profile);
    }

    public List<Profile> getAllProfiles() throws PlayerException {
        List<Profile> profiles = em.createQuery("SELECT p FROM Profile p", Profile.class).getResultList();
        if(profiles.isEmpty()) {
            throw new PlayerException("Nema profila.");
        }
        return profiles;
    }

    public Profile getProfileByPlayerId(Long playerId) {
        List<Profile> profiles = em.createQuery(
                        "SELECT p FROM Profile p WHERE p.player.id = :id", Profile.class)
                .setParameter("id", playerId)
                .getResultList();
        return profiles.isEmpty() ? null : profiles.get(0);
    }
}