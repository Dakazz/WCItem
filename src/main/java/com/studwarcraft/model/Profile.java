package com.studwarcraft.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@NamedQuery(name = Profile.GET_ALL_PROFILES, query = "SELECT p FROM Profile p")
public class Profile {

    public static final String GET_ALL_PROFILES = "GetAllProfiles";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_seq")
    @SequenceGenerator(name = "profile_seq", sequenceName = "profile_seq", allocationSize = 1)
    private Long profileid;

    private String role;

    private int level;

    private int gold;

    @ManyToOne
    @JoinColumn(name = "playerid", insertable = false, updatable = false)
    private Player player;
}