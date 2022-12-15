package android.myapplication.modelClass;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Step implements Parcelable {
    public int number;
    public String step;
    public ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
    public ArrayList<Equipment> equipment = new ArrayList<Equipment>();

    protected Step(Parcel in) {
        number = in.readInt();
        step = in.readString();
        in.readList(ingredients,this.getClass().getClassLoader());
        in.readList(equipment,this.getClass().getClassLoader());


    }

    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(number);
        parcel.writeString(step);
        parcel.writeList(ingredients);
        parcel.writeList(equipment);
    }
}
