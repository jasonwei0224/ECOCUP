package ca.bcit.ecocup;

import java.util.Date;

public class History {
    private String id;
    private Long points;
    private long date;

    public void setId(String historyID) {
        this.id = historyID;
    }

    public String getId() {
        return id;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
