package com.studwarcraft.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import java.util.Objects;

@Entity
public class PlayerTimeZone {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "playertimezoneseq")
    @SequenceGenerator(name = "playertimezoneseq", sequenceName = "playertimezoneseq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "playerid", nullable = false)
    @JsonBackReference(value = "player-time-zones")
    private Player player;

    @Column(name = "tzyear")
    private int year;

    @Column(name = "tzmonth")
    private int month;

    @Column(name = "tzday")
    private int day;

    @Column(name = "tzhour")
    private int hour;

    @Column(name = "tzminute")
    private int minute;

    @Column(name = "tzseconds")
    private int seconds;

    @Column(name = "tzmilliseconds")
    private int milliSeconds;

    @Column(name = "tzdatetime")
    private String dateTime;

    @Column(name = "tzdate")
    private String date;

    @Column(name = "tztime")
    private String time;

    @Column(name = "tztimezone")
    private String timeZone;

    @Column(name = "tzdayofweek")
    private String dayOfWeek;
    private boolean dstActive;

    public PlayerTimeZone() {
    }

    public static PlayerTimeZone fromResponse(TimeResponse response) {
        PlayerTimeZone timeZone = new PlayerTimeZone();
        timeZone.setYear(response.getYear());
        timeZone.setMonth(response.getMonth());
        timeZone.setDay(response.getDay());
        timeZone.setHour(response.getHour());
        timeZone.setMinute(response.getMinute());
        timeZone.setSeconds(response.getSeconds());
        timeZone.setMilliSeconds(response.getMilliSeconds());
        timeZone.setDateTime(response.getDateTime());
        timeZone.setDate(response.getDate());
        timeZone.setTime(response.getTime());
        timeZone.setTimeZone(response.getTimeZone());
        timeZone.setDayOfWeek(response.getDayOfWeek());
        timeZone.setDstActive(response.isDstActive());
        return timeZone;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Player getPlayer() { return player; }
    public void setPlayer(Player player) { this.player = player; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    public int getMonth() { return month; }
    public void setMonth(int month) { this.month = month; }
    public int getDay() { return day; }
    public void setDay(int day) { this.day = day; }
    public int getHour() { return hour; }
    public void setHour(int hour) { this.hour = hour; }
    public int getMinute() { return minute; }
    public void setMinute(int minute) { this.minute = minute; }
    public int getSeconds() { return seconds; }
    public void setSeconds(int seconds) { this.seconds = seconds; }
    public int getMilliSeconds() { return milliSeconds; }
    public void setMilliSeconds(int milliSeconds) { this.milliSeconds = milliSeconds; }
    public String getDateTime() { return dateTime; }
    public void setDateTime(String dateTime) { this.dateTime = dateTime; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
    public String getTimeZone() { return timeZone; }
    public void setTimeZone(String timeZone) { this.timeZone = timeZone; }
    public String getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(String dayOfWeek) { this.dayOfWeek = dayOfWeek; }
    public boolean isDstActive() { return dstActive; }
    public void setDstActive(boolean dstActive) { this.dstActive = dstActive; }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PlayerTimeZone that)) return false;
        return year == that.year &&
                month == that.month &&
                day == that.day &&
                hour == that.hour &&
                minute == that.minute &&
                seconds == that.seconds &&
                milliSeconds == that.milliSeconds &&
                dstActive == that.dstActive &&
                Objects.equals(id, that.id) &&
                Objects.equals(dateTime, that.dateTime) &&
                Objects.equals(date, that.date) &&
                Objects.equals(time, that.time) &&
                Objects.equals(timeZone, that.timeZone) &&
                Objects.equals(dayOfWeek, that.dayOfWeek);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, year, month, day, hour, minute, seconds, milliSeconds, dateTime, date, time, timeZone, dayOfWeek, dstActive);
    }
}
