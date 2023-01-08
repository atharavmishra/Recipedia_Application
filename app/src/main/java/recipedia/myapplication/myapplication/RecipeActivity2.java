package recipedia.myapplication.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.fonts.Font;
import recipedia.myapplication.modelClass.AnalyzedInstruction;
import recipedia.myapplication.modelClass.Result;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecipeActivity2 extends AppCompatActivity {
    ArrayList<Result> arraylist = new ArrayList<Result>();
    ArrayList <AnalyzedInstruction> Instructions = new ArrayList<AnalyzedInstruction>();
    TextView textview ;
    ImageView imageView;
    TextView name;
    TextView ing_list;
    Font font;
    String ingredients ="Ingredients required for below step\n";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe2);
        Intent intent = getIntent();
        textview = findViewById(R.id.text);
        imageView = findViewById(R.id.image);
        name = findViewById(R.id.name);
        ing_list= findViewById(R.id.ingredients);
        getSupportActionBar().hide();
        arraylist=intent.getParcelableArrayListExtra("Data");
        int pos = intent.getIntExtra("position", 0);
        Instructions = arraylist.get(pos).getAnalyzedInstructions();
        textview.setText(Html.fromHtml(Html.fromHtml(arraylist.get(pos).getSummary()).toString()));
        Glide.with(this).load(arraylist.get(pos).getImage()).into(imageView);
        name.setText(arraylist.get(pos).getTitle().toString());
        ImageButton back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if(Instructions.size()==0){
            ing_list.setText("Sorry There are no steps available");
        }
        else {
            for (int i = 0; i < Instructions.get(0).getSteps().size(); i++) {
                for (int j = 0; j < Instructions.get(0).getSteps().get(i).getIngredients().size(); j++) {

                    ingredients = ingredients + Instructions.get(0).getSteps().get(i).getIngredients().get(j).getName() + ",";
                    Log.d("Tag", String.valueOf(i));
                    Log.d("Tag", String.valueOf(j));

                }
                ingredients = ingredients + "\nStep" + String.valueOf(i+1) +" - "+ Instructions.get(0).getSteps().get(i).getStep() + "\n\n\nIngredients Required for below step\n";

            }

            ing_list.setText(ingredients);
        }




    }
}