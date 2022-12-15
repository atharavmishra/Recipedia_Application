package android.myapplication.modelClass;

import android.os.Parcel;
import android.os.Parcelable;

public class Equipment implements Parcelable {
    public int id;
    public String name;
    public String localizedName;
    public String image;

    protected Equipment(Parcel in) {
        id = in.readInt();
        name = in.readString();
        localizedName = in.readString();
        image = in.readString();
    }

    public static final Creator<Equipment> CREATOR = new Creator<Equipment>() {
        @Override
        public Equipment createFromParcel(Parcel in) {
            return new Equipment(in);
        }

        @Override
        public Equipment[] newArray(int size) {
            return new Equipment[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocalizedName() {
        return localizedName;
    }

    public void setLocalizedName(String localizedName) {
        this.localizedName = localizedName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Equipment(int id, String name, String localizedName, String image) {
        this.id = id;
        this.name = name;
        this.localizedName = localizedName;
        this.image = image;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(localizedName);
        parcel.writeString(image);
    }
}
