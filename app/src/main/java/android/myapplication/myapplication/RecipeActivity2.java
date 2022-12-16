package android.myapplication.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.myapplication.modelClass.AnalyzedInstruction;
import android.myapplication.modelClass.Result;
import android.os.Bundle;
import android.text.Html;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe2);
        Intent intent = getIntent();
        textview = findViewById(R.id.text);
        imageView = findViewById(R.id.image);
        name = findViewById(R.id.name);
        arraylist=intent.getParcelableArrayListExtra("Data");
        int pos = intent.getIntExtra("position", 0);
        textview.setText(Html.fromHtml(Html.fromHtml(arraylist.get(pos).getSummary()).toString()));
        Glide.with(this).load(arraylist.get(pos).getImage()).into(imageView);
        name.setText(arraylist.get(pos).getTitle().toString());



    }
}