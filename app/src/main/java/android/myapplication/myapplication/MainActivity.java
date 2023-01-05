package android.myapplication.myapplication;

import static android.content.ContentValues.TAG;

import android.content.SharedPreferences;
import android.myapplication.modelClass.Result;
import android.myapplication.modelClass.Root;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{
    TextView txt;
    private RecyclerView recyclerview;
    Adapter adapter;
    ImageButton search;
    EditText edittext;
    CheckBox chck;
    private ArrayList<Result> arraylist = new ArrayList<Result>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        if(CheckNetwork.isInternetAvailable(this)) //returns true if internet available
        {
            edittext = findViewById(R.id.edittext);
            search = findViewById(R.id.search);
            recyclerview = findViewById(R.id.recyclerview);
            recyclerview.setHasFixedSize(true);
            chck = findViewById(R.id.checkbox);
            chck.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                    // TODO Auto-generated method stub
                    boolean ischecked = chck.isChecked();
                    SharedPreferences shrd = getSharedPreferences("chck", MODE_PRIVATE);
                    SharedPreferences.Editor editor = shrd.edit();
                    editor.putBoolean("val", ischecked);
                    editor.apply();
                    shrd.registerOnSharedPreferenceChangeListener(MainActivity.this);
                    Toast.makeText(getApplicationContext(), "Check box "+arg0.getText().toString()+" is "+String.valueOf(arg1) , Toast.LENGTH_LONG).show();
                }
            } );




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
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.d("tag","change invoked");

        if(sharedPreferences.getBoolean(key,false)){
            findvegrecipe();
            Log.d("tag","method invoked");


        }
        if(!sharedPreferences.getBoolean(key,false)){
            findrecipe();
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

        APIutilities.getAPIinterface().getImage("61a2ba9ae7794bbe914353e4ce889564", true).enqueue(new Callback<Root>() {

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
    private void findvegrecipe() {
        Log.d("myTag", "findvegrecipe invoked");

        APIutilities.getAPIinterface().getImageveg("61a2ba9ae7794bbe914353e4ce889564", true,"vegetarian").enqueue(new Callback<Root>() {

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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister VisualizerActivity as an OnPreferenceChangedListener to avoid any memory leaks.
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

}
