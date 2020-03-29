package ca.bcit.ecocup;

import java.util.Date;

public class History {
    private String id;
    private Long points;
    private long date;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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

    public Date getDate() {
        return new Date(date);
    }

    public void setDate(long date) {
        this.date = date;
    }
}
