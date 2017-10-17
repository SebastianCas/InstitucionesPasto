package com.example.sebastian.institucionespasto.modelos;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.sebastian.institucionespasto.R;

import java.util.List;

/**
 * Created by sebastian on 12/10/17.
 */

public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolder> {

    public static class ViewHolder extends  RecyclerView.ViewHolder{
        private TextView institucion , municipio;
        private CardView card;


        public ViewHolder(View itemView) {
            super(itemView);

            institucion =(TextView)itemView.findViewById(R.id.inst);
            municipio =(TextView)itemView.findViewById(R.id.mun);
            card=(CardView)itemView.findViewById(R.id.card);
        }
    }

    public List<Institucion> lista;

    public Adaptador(List<Institucion> lista) {
        this.lista = lista;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_card_view,parent,false);
        ViewHolder viewHolder= new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.institucion.setText(lista.get(position).getNombreEstablecimiento());
        holder.municipio.setText(lista.get(position).getMunicipio());

        //RollIn Landing DropOut BounceIn FadeIn FlipInX RotateIn SlideInLeft ZoomIn
        YoYo.with(Techniques.ZoomIn).playOn(holder.card);

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}


