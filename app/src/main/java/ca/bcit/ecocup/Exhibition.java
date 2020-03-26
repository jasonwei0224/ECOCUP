package ca.bcit.ecocup;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * This class is to represent cafe vendors in Burnaby.
 * The data in data2.csv file will be shown each vendors.
 * It has name of the vendor, x for Latitude, and y for longitude.
 * Also, to transfer ArrayList of vendors, vendor class should implment Parcelable interfaces.
 */
public class Exhibition implements Parcelable {
    int no;
    String title;
    String date;
    String description;
    int point;

    public Exhibition() {

    }

    public Exhibition(Parcel src) {
        no=src.readInt();
        title=src.readString();
        date=src.readString();
        description=src.readString();
        point=src.readInt();
    }

    public static final Creator<Exhibition> CREATOR=new Creator<Exhibition>() {
        @Override
        public Exhibition createFromParcel(Parcel in) {
            return new Exhibition(in);
        }
        @Override
        public Exhibition[] newArray(int size) {
            return new Exhibition[size];
        }
    };

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description= description;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }


    public String toString() {
        return no+" "+title+" "+date+" "+point;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(no);
        parcel.writeString(title);
        parcel.writeString(date);
        parcel.writeString(description);
        parcel.writeInt(point);
    }
}
