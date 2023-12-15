package com.example.techtask;

import com.example.techtask.Modal.Employee;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("users")
    Call<List<Employee>> getEmployees();

    @GET("users/{id}")
    Call<Employee> getEmployee(@Path("id") int userId);
}

