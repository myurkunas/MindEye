package edu.commonwealthu.mindeye;

/**
 * Represents a log entry with details such as feeling, journal text, time, and date.
 * @author myurkunas
 */
public class LogEntry {
    private int id;
    private float feeling;
    private String journalText;
    private int hour;
    private int minute;
    private int day;
    private int month;
    private int year;

    /**
     * Constructs a LogEntry object.
     */
    public LogEntry(int id, float feeling, String journalText, int hour, int minute, int month, int day, int year) {
        this.id = id;
        this.feeling = feeling;
        this.journalText = journalText;
        this.hour = hour;
        this.minute = minute;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getFeeling() {
        return feeling;
    }

    public void setFeeling(float feeling) {
        this.feeling = feeling;
    }

    public String getJournalText() {
        return journalText;
    }

    public void setJournalText(String journalText) {
        this.journalText = journalText;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public static String getFeelingDescription(float feeling) {
        if (feeling >= 0.0 && feeling < 0.1) {
            return "Very Unpleasant \uD83D\uDE22";
        } else if (feeling >= 0.1 && feeling < 0.3) {
            return "Unpleasant \uD83D\uDE41";
        } else if (feeling >= 0.3 && feeling < 0.7) {
            return "Neutral \uD83D\uDE10";
        } else if (feeling >= 0.7 && feeling < 1.0) {
            return "Pleasant \uD83D\uDE42";
        } else if (feeling == 1.0) {
            return "Very Pleasant \uD83D\uDE00";
        } else {
            return "Unknown \uD83D\uDE35\u200D\uD83D\uDCAB";
        }
    }

    @Override
    public String toString() {
        return "LogEntry{" +
                "id=" + id +
                ", feeling=" + feeling +
                ", journalText='" + journalText + '\'' +
                ", hour=" + hour +
                ", minute=" + minute +
                '}';
    }
}
