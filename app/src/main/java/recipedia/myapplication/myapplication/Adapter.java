package recipedia.myapplication.myapplication;

import android.content.Context;
import android.content.Intent;
import recipedia.myapplication.modelClass.Result;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{
    Context context;
    ArrayList <Result> arraylist;
    public Adapter(Context context, ArrayList<Result> arraylist) {
        this.context = context;
        this.arraylist = arraylist;
    }
    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {

        Glide.with(context).load(arraylist.get(position).getImage()).into(holder.imageview);
        holder.textview.setText(arraylist.get(position).getTitle().toString());
        holder.time.setText("Ready in "+String.valueOf(arraylist.get(position).getReadyInMinutes())+" minutes");
        holder.servings.setText("No. Of servings = "+ arraylist.get(position).getServings());
        if(arraylist.get(holder.getAbsoluteAdapterPosition()).isVegetarian()){
            holder.veg.setImageResource(R.drawable.veg);
        }
        else{
            holder.veg.setImageResource(R.drawable.nonveg);
        }
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RecipeActivity2.class);
                intent.putParcelableArrayListExtra("Data",arraylist);
                intent.putExtra("position",holder.getAbsoluteAdapterPosition());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });




    }


    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageview;
        TextView textview;
        TextView time;
        CardView cardview;
        TextView servings;
        ImageView veg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageview= itemView.findViewById(R.id.image);
            textview = itemView.findViewById(R.id.heading);
            time = itemView.findViewById(R.id.minutes);
            veg = itemView.findViewById(R.id.type);
            cardview = itemView.findViewById(R.id.cardview);
            servings = itemView.findViewById(R.id.servings);

        }
    }
}
