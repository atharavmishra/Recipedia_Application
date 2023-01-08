package recipedia.myapplication.modelClass;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Step implements Parcelable {
    public int number;
    public String step;
    public ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
    public ArrayList<Equipment> equipment = new ArrayList<Equipment>();

    public Step(int number, String step, ArrayList<Ingredient> ingredients, ArrayList<Equipment> equipment) {
        this.number = number;
        this.step = step;
        this.ingredients = ingredients;
        this.equipment = equipment;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<Equipment> getEquipment() {
        return equipment;
    }

    public void setEquipment(ArrayList<Equipment> equipment) {
        this.equipment = equipment;
    }

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
