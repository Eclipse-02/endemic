package com.di.endemic.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.di.endemic.R;
import com.di.endemic.model.Endemic;
import java.util.ArrayList;
import java.util.List;

public class EndemicAdapter extends RecyclerView.Adapter<EndemicAdapter.ViewHolder> {
    private List<Endemic> endemics = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Endemic endemic);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setEndemics(List<Endemic> endemics) {
        this.endemics = endemics;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_endemic, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Endemic endemic = endemics.get(position);
        holder.tvNama.setText(endemic.getNama());
        holder.tvTipe.setText(endemic.getTipe());
        holder.tvAsal.setText(endemic.getAsal());

        String imageUrl = endemic.getFoto();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            GlideUrl glideUrl = new GlideUrl(imageUrl, new LazyHeaders.Builder()
                    .addHeader("User-Agent", "Mozilla/5.0")
                    .build());

            Glide.with(holder.itemView.getContext())
                    .load(glideUrl)
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .error(android.R.drawable.stat_notify_error)
                    .into(holder.ivFoto);
        } else {
            holder.ivFoto.setImageResource(android.R.drawable.ic_menu_gallery);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(endemic);
            }
        });
    }

    @Override
    public int getItemCount() {
        return endemics.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFoto;
        TextView tvNama, tvTipe, tvAsal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFoto = itemView.findViewById(R.id.ivFoto);
            tvNama = itemView.findViewById(R.id.tvNama);
            tvTipe = itemView.findViewById(R.id.tvTipe);
            tvAsal = itemView.findViewById(R.id.tvAsal);
        }
    }
}
