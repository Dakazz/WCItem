package com.studwarcraft.service;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import com.studwarcraft.exception.ProfileException;
import com.studwarcraft.model.Profile;

import java.util.List;

@Dependent
public class ProfileService {

    @Inject
    private EntityManager em;

    @Transactional
    public Profile createProfile(Profile profile) throws ProfileException {
        if (profile == null) throw new ProfileException("Profile nije proslijeđen");
        if (profile.getRole() == null || profile.getRole().isEmpty()) throw new ProfileException("Role je prazno");

        return em.merge(profile);
    }

    public List<Profile> getAllProfiles() throws ProfileException {
        List<Profile> profiles = em.createNamedQuery(Profile.GET_ALL_PROFILES, Profile.class).getResultList();
        if (profiles.isEmpty()) throw new ProfileException("Nema profila.");
        return profiles;
    }
}