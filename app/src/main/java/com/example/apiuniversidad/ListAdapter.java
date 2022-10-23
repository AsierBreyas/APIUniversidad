package com.example.apiuniversidad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<ListUniversidad> universidades;
    private LayoutInflater mInflater;
    private Context context;
    private WebView webView;

    public ListAdapter(List<ListUniversidad> universidades, Context context, WebView webView){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.universidades = universidades;
        this.webView = webView;
    }

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.universidad_list, null);
        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position) {
        holder.bindData(universidades.get(position));
    }

    @Override
    public int getItemCount() {
        return universidades.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        Button boton;
        TextView nombre;

        ViewHolder(View itemView){
            super(itemView);
            boton = itemView.findViewById(R.id.botonIrWeb);
            nombre = itemView.findViewById(R.id.nombreUniversidad);
        }
        void bindData(final ListUniversidad universidad){
            nombre.setText(universidad.getNombre());
            boton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    webView.loadUrl(universidad.web);
                }
            });
        }
    }
}
