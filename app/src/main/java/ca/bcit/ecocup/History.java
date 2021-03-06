package ca.bcit.ecocup;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class History {
    private String id;
    private Long pointsRedeem;
    private Date date;
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

    public Long getPointsRedeem(){
        return pointsRedeem;
    }
    public void setPointsRedeem(Long pointsRedeem){
        this.pointsRedeem = pointsRedeem;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}