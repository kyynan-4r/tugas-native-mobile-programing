package com.example.uas_risky.activity.produk;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.example.uas_risky.Config;
import com.example.uas_risky.R;
import com.example.uas_risky.Utils;
import com.example.uas_risky.response.GetAllResponse;
import com.example.uas_risky.response.GetResponse;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Activity extends AppCompatActivity {
    private Retrofit retrofit = Config.retrofit;
    private RecyclerView recyclerView;
    private Adapter adapter;
    private Handler handler;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud);
        context = this;

        // TODO Edit This Scarlet
        TextView titleActivity = findViewById(R.id.titleActivity);
        titleActivity.setText("produk");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        handler = retrofit.create(Handler.class);

        getAll();

        FloatingActionButton createButton = findViewById(R.id.createButton);
        createButton.setOnClickListener(v -> showCreateModal());
    }

    private void showCreateModal() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Activity.this);
        LayoutInflater inflater = LayoutInflater.from(Activity.this);
        View dialogView = inflater.inflate(R.layout.crud_create, null);
        builder.setView(dialogView);

        LinearLayout inputContainer = dialogView.findViewById(R.id.createContainer);
        Button createButton = dialogView.findViewById(R.id.submitButton);

        // TODO Edit This Scarlet
        EditText nama = Utils.createEditText(this, "nama");
        inputContainer.addView(nama);
        EditText stok = Utils.createEditText(this, "stok");
        inputContainer.addView(stok);
        EditText harga = Utils.createEditText(this, "harga");
        inputContainer.addView(harga);


        AlertDialog dialog = builder.create();

        createButton.setOnClickListener(v -> {
            Model createdModel = new Model(
                    // TODO Edit This Scarlet
                    nama.getText().toString(),
                    Integer.parseInt(stok.getText().toString()),
                    Integer.parseInt(harga.getText().toString())

            );
            create(createdModel, dialog);
        });

        dialog.show();
    }

    private void getAll() {
        Call<GetAllResponse<Model>> call = handler.getAllData();

        call.enqueue(new Callback<GetAllResponse<Model>>() {
            @Override
            public void onResponse(Call<GetAllResponse<Model>> call, Response<GetAllResponse<Model>> response) {
                GetAllResponse<Model> sumberResponse = response.body();
                if (response.isSuccessful()) {
                    List<Model> datas = sumberResponse.getData();
                    adapter = new Adapter(datas, Activity.this, handler);
                    recyclerView.setAdapter(adapter);
                }
                Toast.makeText(context, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<GetAllResponse<Model>> call, Throwable t) {
                Toast.makeText(context, "Error Failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void create(Model model, AlertDialog dialog) {
        Call<GetResponse<Model>> call = handler.createData(model);

        call.enqueue(new Callback<GetResponse<Model>>() {
            @Override
            public void onResponse(Call<GetResponse<Model>> call, Response<GetResponse<Model>> response) {
                if (response.isSuccessful()) {
                    GetResponse<Model> createResponse = response.body();
                    Model createdData = createResponse.getData();
                    adapter.addItem(createdData);
                    dialog.dismiss();
                    Toast.makeText(Activity.this, "Data created successfully", Toast.LENGTH_SHORT).show();
                } else {
                    if (response.code() == 400) {
                        Gson gson = new Gson();
                        String responseBodyJson = null;
                        try {
                            responseBodyJson = response.errorBody().string();
                            TextView errorLog = dialog.findViewById(R.id.createErrorLog);
                            errorLog.setText(responseBodyJson);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(Activity.this, "Failed to create Data", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Activity.this, "Failed to create Data. Error code: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetResponse<Model>> call, Throwable t) {
                Toast.makeText(Activity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateData(Model model, AlertDialog dialog) {
        adapter.updateItem(model);
        dialog.dismiss();
        Toast.makeText(Activity.this, "Data updated successfully", Toast.LENGTH_SHORT).show();
    }

    public void deleteData(Model model, AlertDialog dialog) {
        adapter.deleteItem(model);
        dialog.dismiss();
        Toast.makeText(Activity.this, "Data deleted successfully", Toast.LENGTH_SHORT).show();
    }
}
