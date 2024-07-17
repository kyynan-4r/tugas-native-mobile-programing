package com.example.uas_risky.activity.detailtransaksi;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uas_risky.R;
import com.example.uas_risky.Utils;
import com.example.uas_risky.response.GetResponse;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    // Don't Touch This
    private Activity activity;
    private List<Model> items;
    private Context context;
    private Handler handler;

    public Adapter(List<Model> items, Activity context, Handler handler) {
        // Don't Touch This
        this.items = items;
        this.context = context;
        this.handler = handler;
        this.activity = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout itemCountainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemCountainer = itemView.findViewById(R.id.itemContainer);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.crud_item, parent, false);

        // TODO Edit This Scarlet
        int AdapterIcon = R.drawable.baseline_account_circle_24;
        ImageView itemIcon = view.findViewById(R.id.itemIcon);
        itemIcon.setImageResource(AdapterIcon);

         ViewHolder viewHolder = new ViewHolder(view);

        // TODO Edit This Scarlet
        TextView transaksiid = Utils.createTextView(context);
        viewHolder.itemCountainer.addView(transaksiid);
        TextView produkid = Utils.createTextView(context);
        viewHolder.itemCountainer.addView(produkid);
        TextView hargasatuan = Utils.createTextView(context);
        viewHolder.itemCountainer.addView(hargasatuan);
        TextView jumlah = Utils.createTextView(context);
        viewHolder.itemCountainer.addView(jumlah);
        TextView subTotal = Utils.createTextView(context);
        viewHolder.itemCountainer.addView(subTotal);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Model data = items.get(position);

        // TODO Edit This Scarlet
        ((TextView) holder.itemCountainer.getChildAt(0)).setText(String.format("ID Transaksi: %s", data.transaksiid));
        ((TextView) holder.itemCountainer.getChildAt(1)).setText(String.format("ID Produk: %s", data.produkid));
        ((TextView) holder.itemCountainer.getChildAt(2)).setText(String.format("Harga Satuan: %s", data.hargasatuan));
        ((TextView) holder.itemCountainer.getChildAt(3)).setText(String.format("Jumlah: %s", data.jumlah));
        ((TextView) holder.itemCountainer.getChildAt(4)).setText(String.format("Sub Total: %s", data.subTotal));

        holder.itemView.setOnClickListener(v -> {
            showUpdateModal(data);
        });
    }

    private void showUpdateModal(Model model) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.crud_update, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        LinearLayout updateContainer = dialogView.findViewById(R.id.updateContainer);
        TextView itemId = dialogView.findViewById(R.id.itemId);
        itemId.setText(model.id);

        // TODO Edit This Scarlet
        EditText transaksiid = Utils.createEditText(context, "transaksiid");
        transaksiid.setText(model.transaksiid);
        updateContainer.addView(transaksiid);
        EditText produkid = Utils.createEditText(context, "produkid");
        produkid.setText(model.produkid);
        updateContainer.addView(produkid);
        EditText hargasatuan = Utils.createEditText(context, "hargasatuan");
        hargasatuan.setText(model.hargasatuan.toString());
        updateContainer.addView(hargasatuan);
        EditText jumlah = Utils.createEditText(context, "jumlah");
        jumlah.setText(model.jumlah.toString());
        updateContainer.addView(jumlah);
        EditText subTotal = Utils.createEditText(context, "subTotal");
        subTotal.setText(model.subTotal.toString());
        updateContainer.addView(subTotal);

        // Button
        Button updateButton = dialogView.findViewById(R.id.updateButton);
        Button deleteButton = dialogView.findViewById(R.id.deleteButton);
        Button copyIdButton = dialogView.findViewById(R.id.copyIdButton);

        copyIdButton.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Copied Text", model.id);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(context, "ID copied to clipboard", Toast.LENGTH_SHORT).show();
        });

        // Button Handler
        updateButton.setOnClickListener(v -> {
            Map<String, Object> updatedModel = new HashMap<>();
            // TODO Edit This Scarlet

            if (!Objects.equals(model.transaksiid, transaksiid.getText().toString())) {
                updatedModel.put("transaksiid", transaksiid.getText().toString());
            }
            if (!Objects.equals(model.produkid, produkid.getText().toString())) {
                updatedModel.put("produkid", produkid.getText().toString());
            }
            if (!Objects.equals(model.hargasatuan, hargasatuan.getText().toString())) {
                updatedModel.put("hargasatuan", hargasatuan.getText().toString());
            }
            if (!Objects.equals(model.jumlah, jumlah.getText().toString())) {
                updatedModel.put("jumlah", jumlah.getText().toString());
            }
            if (!Objects.equals(model.subTotal, subTotal.getText().toString())) {
                updatedModel.put("subTotal", subTotal.getText().toString());
            }

            // Do Your Job Handler
            handler.updateData(model.id, updatedModel).enqueue(new Callback<GetResponse<Model>>() {
                @Override
                public void onResponse(Call<GetResponse<Model>> call, Response<GetResponse<Model>> response) {
                    if (response.isSuccessful()) {
                        Model updatedData = response.body().getData();
                        activity.updateData(updatedData, dialog);
                    } else {
                        if (response.code() == 400) {
                            Gson gson = new Gson();
                            String responseBodyJson = null;
                            try {
                                responseBodyJson = response.errorBody().string();
                                TextView errorLog = dialog.findViewById(R.id.updateErrorLog);
                                errorLog.setText(responseBodyJson);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            Toast.makeText(context, "Failed to create Data", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Failed to create Data. Error code: " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<GetResponse<Model>> call, Throwable t) {
                    Toast.makeText(context, "Network error, please try again", Toast.LENGTH_SHORT).show();
                }
            });
        });

        // Button Handler
        deleteButton.setOnClickListener(v -> {
            // Do Your Job Handler
            handler.deleteData(model.id).enqueue(new Callback<GetResponse<Model>>() {
                @Override
                public void onResponse(Call<GetResponse<Model>> call, Response<GetResponse<Model>> response) {
                    if (response.isSuccessful()) {
                        Model updatedData = response.body().getData();
                        activity.deleteData(updatedData, dialog);
                    } else {
                        if (response.code() == 400) {
                            Gson gson = new Gson();
                            String responseBodyJson = null;
                            try {
                                responseBodyJson = response.errorBody().string();
                                TextView errorLog = dialog.findViewById(R.id.updateErrorLog);
                                errorLog.setText(responseBodyJson);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            Toast.makeText(context, "Failed to delete Data", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Failed to delete Data. Error code: " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<GetResponse<Model>> call, Throwable t) {
                    Toast.makeText(context, "Network error, please try again", Toast.LENGTH_SHORT).show();
                }
            });
        });

        // Show the fcking button
        dialog.show();
    }

    @Override
    public int getItemCount() {
        // Don't Touch This
        return items.size();
    }

    public void addItem(Model model) {
        // Don't Touch This
        items.add(model);
        notifyItemInserted(items.size() - 1);
    }

    public void updateItem(Model updatedItem) {
        // Don't Touch This
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).id.equals(updatedItem.id)) {
                items.set(i, updatedItem);
                notifyItemChanged(i);
                break;
            }
        }
    }

    public void deleteItem(Model updatedItem) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).id.equals(updatedItem.id)) {
                items.remove(i);
                notifyItemRemoved(i);
                break;
            }
        }
    }
}
