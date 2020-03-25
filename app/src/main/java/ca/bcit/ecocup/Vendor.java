package ca.bcit.ecocup;

import android.os.Parcel;
import android.os.Parcelable;

public class Vendor implements Parcelable {
    String name;
    double x;
    double y;

    public Vendor() {

    }

    public Vendor(Parcel src) {
        name=src.readString();
        x=src.readDouble();
        y=src.readDouble();
    }

    public static final Creator<Vendor> CREATOR=new Creator<Vendor>() {
        @Override
        public Vendor createFromParcel(Parcel in) {
            return new Vendor(in);
        }
        @Override
        public Vendor[] newArray(int size) {
            return new Vendor[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
    public String toString() {
        return name+" "+x+" "+y;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeDouble(x);
        parcel.writeDouble(y);
    }
}
