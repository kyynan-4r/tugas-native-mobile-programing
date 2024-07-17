package com.example.uas_risky;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    private List<Model> items;
    private Context context;

    public MainAdapter(List<Model> items, Context context) {
        this.items = items;
        this.context = context;
    }

    public static class MainViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView subTitle;

        public MainViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.tableTitle);
            subTitle = view.findViewById(R.id.tableSubTitle);
        }
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.table_item, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        Model models = items.get(position);
        holder.title.setText(models.title);
        holder.subTitle.setText(models.subTitle);
        holder.itemView.setOnClickListener(v -> context.startActivity(models.intent));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
