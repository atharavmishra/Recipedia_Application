package android.myapplication.myapplication;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.myapplication.modelClass.Result;
import android.myapplication.modelClass.Root;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    TextView txt;
    private RecyclerView recyclerview;
    Adapter adapter;
    ImageButton search;
    EditText edittext;
    private ArrayList<Result> arraylist = new ArrayList<Result>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        if(CheckNetwork.isInternetAvailable(this)) //returns true if internet available
        {edittext = findViewById(R.id.edittext);
            search = findViewById(R.id.search);
            recyclerview = findViewById(R.id.recyclerview);
            recyclerview.setHasFixedSize(true);
            recyclerview.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
            findrecipe();
            adapter = new Adapter(getApplicationContext(), arraylist);
            recyclerview.setAdapter(adapter);
            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String query = edittext.getText().toString().trim().toLowerCase();
                    if (query.isEmpty()) {

                        Toast.makeText(getApplicationContext(), "Please Enter something", Toast.LENGTH_LONG).show();
                    } else {
                        getrecipe(query);
                    }
                }
            });
        }

         else
        {
            Toast.makeText(this,"No Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }
    private void getrecipe(String query){
        APIutilities.getAPIinterface().getImage2("61a2ba9ae7794bbe914353e4ce889564", true, query).enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                arraylist.clear();
                arraylist.addAll(response.body().getResults());
                recyclerview.getRecycledViewPool().clear();
                adapter.notifyDataSetChanged();
                Log.d(TAG, Integer.toString(response.code()));
                Log.d("myTag", response.toString());
                Log.d(TAG, Integer.toString(response.code())+(response.raw().request().url()).toString());

            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {

            }
        });

    }


    private void findrecipe() {
        Log.d("myTag", "method invoked");

        APIutilities.getAPIinterface().getImage("61a2ba9ae7794bbe914353e4ce889564", true, "Vegetarian").enqueue(new Callback<Root>() {

            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {


                if(response.isSuccessful()){
                    arraylist.clear();
                    arraylist.addAll(response.body().getResults());
                    recyclerview.getRecycledViewPool().clear();
                    adapter.notifyDataSetChanged();
                    Log.d(TAG, Integer.toString(response.code()));
                    Log.d("myTag", response.toString());
                    Log.d(TAG, Integer.toString(response.code())+(response.raw().request().url()).toString());

                }
                else{
                    Log.d(TAG, Integer.toString(response.code())+(response.raw().request().url()).toString());
                    Toast.makeText(getApplicationContext(), "Couldn't get", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {

            }
        });
    }
}
