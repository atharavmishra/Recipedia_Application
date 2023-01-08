package recipedia.myapplication.modelClass;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Root implements Parcelable {
    public ArrayList<Result> results = new ArrayList<Result>();

    public Root(ArrayList<Result> results) {
        this.results = results;
    }

    protected Root(Parcel in) {
        in.readList(results, this.getClass().getClassLoader());    }

    public static final Creator<Root> CREATOR = new Creator<Root>() {
        @Override
        public Root createFromParcel(Parcel in) {

            return new Root(in);
        }

        @Override
        public Root[] newArray(int size) {
            return new Root[size];
        }
    };

    public ArrayList<Result> getResults() {
        return results;
    }

    public void setResults(ArrayList<Result> results) {
        this.results = results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(results);
    }

}
