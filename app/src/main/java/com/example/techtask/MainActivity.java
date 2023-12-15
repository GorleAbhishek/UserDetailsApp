package com.example.techtask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import com.example.techtask.Modal.Employee;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EmployeeAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (isNetworkAvailable()) {
            fetchEmployeeList();
        } else {
            // Show a notification to the user that there is no internet connection
            // You can use a Snackbar or any other UI component for this purpose
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
        }
    }


    private void openEmployeeDetailActivity(Employee employee) {
        Intent intent = new Intent(this, EmployeeDetailActivity.class);
        intent.putExtra("employee", employee);
        startActivity(intent);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void fetchEmployeeList() {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<List<Employee>> call = apiService.getEmployees();


        call.enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Employee> employees = response.body();
                    adapter = new EmployeeAdapter(employees);
                    recyclerView.setAdapter(adapter);
                    // Set click listener for RecyclerView items
                    adapter.setOnItemClickListener(new EmployeeAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Employee employee) {
                            openEmployeeDetailActivity(employee);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {
                // Handle failure, e.g., show an error message
                showNoInternetSnackbar();
            }
        });

    }

    private void showNoInternetSnackbar() {
        Snackbar.make(findViewById(android.R.id.content), "No internet connection", Snackbar.LENGTH_LONG).show();
    }


}
