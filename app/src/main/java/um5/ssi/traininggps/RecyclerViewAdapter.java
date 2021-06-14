package um5.ssi.traininggps;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{

    List<WayPoint> allWayPoints;
    Context context;

    public RecyclerViewAdapter(List<WayPoint> allWayPoints,Context context) {
        this.allWayPoints = allWayPoints;
        this.context = context;
    }



    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.one_line_way_point,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.tv_date.setText(allWayPoints.get(position).getDate());
        holder.tv_title.setText(allWayPoints.get(position).getTitle());
        holder.btn_delete_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,ShowSavedLocationsList.class);
                DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
                boolean success = dataBaseHelper.deleteOne(allWayPoints.get(position).getId());
                Toast.makeText(context, " Deleted!", Toast.LENGTH_SHORT).show();
                context.startActivity(i);
            }
        });

        holder.btn_location_focus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,MapsActivity.class);
                i.putExtra("id",allWayPoints.get(position).getId());
                context.startActivity(i);
            }
        });

        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,EditTitle.class);
                i.putExtra("id",allWayPoints.get(position).getId());
                i.putExtra("title",allWayPoints.get(position).getTitle());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allWayPoints.size(); //lines of table way_points
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_title;
        TextView tv_date;
        Button btn_edit;
        Button btn_delete_location;
        Button btn_location_focus;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_date = itemView.findViewById(R.id.tv_date);
            btn_edit = itemView.findViewById(R.id.btn_edit);
            btn_delete_location = itemView.findViewById(R.id.btn_delete_location);
            btn_location_focus = itemView.findViewById(R.id.btn_location_focus);
        }
    }
}
