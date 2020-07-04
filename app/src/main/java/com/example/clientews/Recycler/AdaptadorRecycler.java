package com.example.clientews.Recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clientews.R;

import java.util.ArrayList;

public class AdaptadorRecycler extends RecyclerView.Adapter<AdaptadorRecycler.ViewHolder> {
    private static ClickListener clickListener;
    ArrayList <DatosVo> ListaMenu = new ArrayList<>();


    public AdaptadorRecycler (ArrayList<DatosVo> ListaMenu){
        this.ListaMenu=ListaMenu;
    }

    @NonNull
    @Override
    public AdaptadorRecycler.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_personalizado,null,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull AdaptadorRecycler.ViewHolder holder, int position) {
        holder.texto1.setText(ListaMenu.get(position).getTitulo());
        holder.imagen.setImageResource(ListaMenu.get(position).getImagen());
    }

    @Override
    public int getItemCount() {
        return this.ListaMenu.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView texto1;
        public ImageView imagen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            texto1=itemView.findViewById(R.id.txt1Rec);
            imagen=itemView.findViewById(R.id.imgRec);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnClickListener (ClickListener clickListener){
        AdaptadorRecycler.clickListener = clickListener;
    }

    public interface ClickListener {
        public void onItemClick (int position, View v);
    }
}
