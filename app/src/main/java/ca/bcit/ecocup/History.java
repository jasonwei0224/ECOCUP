package ca.bcit.ecocup;

import java.util.Date;

public class History {
    private String historyID;
    private Long points;
    private long date;

    public void setHistoryID(String historyID) {
        this.historyID = historyID;
    }

    public String getHistoryID() {
        return historyID;
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
