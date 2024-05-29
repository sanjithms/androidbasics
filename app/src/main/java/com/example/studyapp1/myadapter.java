package com.example.studyapp1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class myadapter extends RecyclerView.Adapter<myviewholder> {
    private Context context;
    private List<Dataclass> datalist;

    public myadapter(Context context, List<Dataclass> datalist) {
        this.context = context;
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recyle_view,parent,false);

        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        Glide.with(context).load(datalist.get(position).getDataimage()).into(holder.recimage);
        holder.rectitle.setText(datalist.get(position).getDatatitle());
        holder.recdesc.setText(datalist.get(position).getDatadescription());
        holder.reclang.setText(datalist.get(position).getDatajan());

        holder.reccard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,detailed_view_activity.class);
                intent.putExtra("Image",datalist.get(holder.getAdapterPosition()).getDataimage());
                intent.putExtra("Title",datalist.get(holder.getAdapterPosition()).getDatatitle());
                intent.putExtra("Description",datalist.get(holder.getAdapterPosition()).getDatadescription());
                context.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }
}
class myviewholder extends  RecyclerView.ViewHolder{
    ImageView recimage;
    TextView rectitle,recdesc,reclang;
    CardView reccard;

    public myviewholder(@NonNull View itemView) {
        super(itemView);
        recimage=itemView.findViewById(R.id.recimage);
        rectitle=itemView.findViewById(R.id.rectitle);
        recdesc=itemView.findViewById(R.id.recdesc);
        reclang=itemView.findViewById(R.id.reclang);
        reccard = itemView.findViewById(R.id.redcard);

    }
}
