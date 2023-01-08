package recipedia.myapplication.myapplication;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;

import recipedia.myapplication.modelClass.Result;
import recipedia.myapplication.modelClass.Root;

import android.myapplication.myapplication.R;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
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

public class MainActivity extends AppCompatActivity{
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
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        if(CheckNetwork.isInternetAvailable(this)) //returns true if internet available
        {
            ProgressDialog pd = new ProgressDialog(MainActivity.this);
            edittext = findViewById(R.id.edittext);
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
                        pd.setMessage("loading");
                        pd.show();
                        getrecipe(query);
                        pd.hide();
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

        APIutilities.getAPIinterface().getImage("61a2ba9ae7794bbe914353e4ce889564", true).enqueue(new Callback<Root>() {

            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {


                if(response.isSuccessful()){
                    arraylist.clear();
                    arraylist.addAll(response.body().getResults());
                    recyclerview.getRecycledViewPool().clear();
                    adapter.notifyDataSetChanged();
                    findViewById(R.id.progressBar1).setVisibility(View.GONE);
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


}
