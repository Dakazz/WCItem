package com.studwarcraft.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(
                name = Player.GET_ALL_PLAYERS,
                query = "Select distinct p from Player p " +
                        "left join fetch p.details " +
                        "left join fetch p.profile " +
                        "left join fetch p.timeZones"
        ),
        @NamedQuery(
                name = Player.GET_PLAYER_BY_ID,
                query = "Select distinct p from Player p " +
                        "left join fetch p.details " +
                        "left join fetch p.profile " +
                        "left join fetch p.timeZones " +
                        "where p.playerid = :idP"
        ),
        @NamedQuery(
                name = Player.GET_PLAYERS_BY_NAME,
                query = "Select distinct p from Player p " +
                        "left join fetch p.details " +
                        "left join fetch p.profile " +
                        "left join fetch p.timeZones " +
                        "where p.name = :nameP"
        ),
        @NamedQuery(
                name = Player.GET_PLAYERS_BY_ROLE,
                query = "Select distinct p from Player p " +
                        "left join fetch p.profile " +
                        "left join fetch p.details " +
                        "left join fetch p.timeZones " +
                        "where p.profile.role = :roleP"
        )
})
public class Player {

    public static final String GET_ALL_PLAYERS = "GetAllPlayers";
    public static final String GET_PLAYER_BY_ID = "GetPlayerById";
    public static final String GET_PLAYERS_BY_NAME = "GetPlayersByName";
    public static final String GET_PLAYERS_BY_ROLE = "GetPlayersByRole";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "player_seq")
    @SequenceGenerator(name = "player_seq", sequenceName = "player_seq", allocationSize = 1)
    private Long playerid;

    private String name;
    private String lastname;

    @OneToOne(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private PlayerDetails details;

    @OneToOne(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Profile profile;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "player-time-zones")
    private List<PlayerTimeZone> timeZones = new ArrayList<>();

    public Player() {}

    public Player(Long playerid, String name, String lastname) {
        this.playerid = playerid;
        this.name = name;
        this.lastname = lastname;
    }

    public Long getPlayerid() { return playerid; }
    public void setPlayerid(Long playerid) { this.playerid = playerid; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLastname() { return lastname; }
    public void setLastname(String lastname) { this.lastname = lastname; }
    public PlayerDetails getDetails() { return details; }
    public void setDetails(PlayerDetails details) { this.details = details; }
    public Profile getProfile() { return profile; }
    public void setProfile(Profile profile) { this.profile = profile; }
    public List<PlayerTimeZone> getTimeZones() { return timeZones; }
    public void setTimeZones(List<PlayerTimeZone> timeZones) { this.timeZones = timeZones; }

    public void addTimeZone(PlayerTimeZone timeZone) {
        timeZones.add(timeZone);
        timeZone.setPlayer(this);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Player p)) return false;
        return Objects.equals(playerid, p.playerid) &&
                Objects.equals(name, p.name) &&
                Objects.equals(lastname, p.lastname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerid, name, lastname);
    }
}
