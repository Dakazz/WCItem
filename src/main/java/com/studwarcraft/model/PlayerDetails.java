package com.studwarcraft.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class PlayerDetails {

    @Id
    private Long detailsid;

    @OneToOne
    @MapsId
    @JoinColumn(name = "playerid")
    @JsonBackReference
    private Player player;

    private String avatar;
    private String bio;
    private String creationDate;

    public PlayerDetails() {}

    public PlayerDetails(Player player, String avatar, String bio, String creationDate) {
        this.player = player;
        this.avatar = avatar;
        this.bio = bio;
        this.creationDate = creationDate;
    }

    public Long getDetailsid() { return detailsid; }
    public void setDetailsid(Long detailsid) { this.detailsid = detailsid; }
    public Player getPlayer() { return player; }
    public void setPlayer(Player player) { this.player = player; }
    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    public String getCreationDate() { return creationDate; }
    public void setCreationDate(String creationDate) { this.creationDate = creationDate; }
}