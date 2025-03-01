package com.example.gustavioandroidstudio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gustavioandroidstudio.api.Game;

import java.util.ArrayList;
import java.util.List;

public class PopularGamesAdapter extends RecyclerView.Adapter<PopularGamesAdapter.ViewHolder> {

    private final Context context;
    private List<Game.Juegos> juegosOriginales; // Lista original de juegos
    private List<Game.Juegos> juegosFiltrados; // Lista que se muestra en el RecyclerView
    private final OnGameClickListener onGameClickListener;

    public PopularGamesAdapter(Context context, List<Game> juegos, OnGameClickListener listener) {
        this.context = context;
        this.juegosOriginales = new ArrayList<>(juegos); // Copia la lista original
        this.juegosFiltrados = new ArrayList<>(juegos); // Inicializa la lista filtrada
        this.onGameClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.popular_game_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Game.Juegos game = juegosFiltrados.get(position); // Usa la lista filtrada
        holder.gameTitle.setText(game.getName()); // Accede correctamente a getName()

        // Cargar la imagen desde la URL usando Glide
        Glide.with(holder.itemView.getContext())
                .load(game.getImageUrl()) // Ahora accede correctamente a la URL de la imagen
                .into(holder.gameImage);

        holder.itemView.setOnClickListener(v -> onGameClickListener.onGameClick(game));
    }

    @Override
    public int getItemCount() {
        return juegosFiltrados.size(); // Devuelve el tamaño de la lista filtrada
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView gameImage;
        TextView gameTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gameImage = itemView.findViewById(R.id.gameImage);
            gameTitle = itemView.findViewById(R.id.gameTitle);
        }
    }

    public interface OnGameClickListener {
        void onGameClick(Game.Juegos game);
    }

    public void filtrar(String texto) {
        juegosFiltrados.clear(); // Limpia la lista filtrada

        if (texto.isEmpty()) {
            juegosFiltrados.addAll(juegosOriginales); // Restaura todos los juegos si no hay texto
        } else {
            for (Game.Juegos game : juegosOriginales) {
                if (game.getName().toLowerCase().contains(texto.toLowerCase())) { // Accede correctamente al nombre
                    juegosFiltrados.add(game); // Agrega solo los juegos que coinciden
                }
            }
        }
        notifyDataSetChanged(); // Notifica que los datos han cambiado
    }
}
