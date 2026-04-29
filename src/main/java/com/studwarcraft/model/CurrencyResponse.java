package com.studwarcraft.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class CurrencyResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "currency_response_seq")
    @SequenceGenerator(name = "currency_response_seq", sequenceName = "currency_response_seq", allocationSize = 1)
    private Long id;

    @Column(name = "from_currency")
    private String from;

    @Column(name = "to_currency")
    private String to;
    private double rate;
    private String date;
    private String source;

    @Column(name = "\"value\"")
    private double value;
    private double convertedValue;

    @ManyToOne
    @JoinColumn(name = "playerid", nullable = false)
    @JsonBackReference(value = "currency")
    private Player player;

    public CurrencyResponse() {}

    public Long getId() { return id; }

    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }

    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }

    public double getRate() { return rate; }
    public void setRate(double rate) { this.rate = rate; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }

    public double getConvertedValue() { return convertedValue; }
    public void setConvertedValue(double convertedValue) { this.convertedValue = convertedValue; }

    public Player getPlayer() { return player; }
    public void setPlayer(Player player) { this.player = player; }
}
