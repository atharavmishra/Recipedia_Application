package android.myapplication.modelClass;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class AnalyzedInstruction implements Parcelable {
    public String name;
    public ArrayList<Step> steps = new ArrayList<Step>();

    public AnalyzedInstruction(String name, ArrayList<Step> steps) {
        this.name = name;
        this.steps = steps;
    }

    protected AnalyzedInstruction(Parcel in) {
        name = in.readString();
        in.readList(steps,this.getClass().getClassLoader());

    }

    public static final Creator<AnalyzedInstruction> CREATOR = new Creator<AnalyzedInstruction>() {
        @Override
        public AnalyzedInstruction createFromParcel(Parcel in) {
            return new AnalyzedInstruction(in);
        }

        @Override
        public AnalyzedInstruction[] newArray(int size) {
            return new AnalyzedInstruction[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<Step> steps) {
        this.steps = steps;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeList(steps);
    }
}
