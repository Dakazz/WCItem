package com.studwarcraft.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_seq")
    @SequenceGenerator(name = "profile_seq", sequenceName = "profile_seq", allocationSize = 1)
    private Long profileid;

    @OneToOne
    @JoinColumn(name = "playerid", unique = true)
    @JsonBackReference
    private Player player;

    private String role;
    private int level;
    private int gold;

    public Profile() {}

    public Profile(Player player, String role, int level, int gold) {
        this.player = player;
        this.role = role;
        this.level = level;
        this.gold = gold;
    }

    public Long getProfileid() { return profileid; }
    public void setProfileid(Long profileid) { this.profileid = profileid; }
    public Player getPlayer() { return player; }
    public void setPlayer(Player player) { this.player = player; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }
    public int getGold() { return gold; }
    public void setGold(int gold) { this.gold = gold; }
}