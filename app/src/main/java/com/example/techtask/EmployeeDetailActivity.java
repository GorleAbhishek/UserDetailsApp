package com.example.techtask;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.techtask.Modal.Employee;

public class EmployeeDetailActivity extends AppCompatActivity {
    private TextView nameTextView;
    private TextView emailTextView;
    private TextView phoneTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_detail);

        nameTextView = findViewById(R.id.detailNameTextView);
        emailTextView = findViewById(R.id.detailEmailTextView);
        phoneTextView = findViewById(R.id.detailPhoneTextView);

        // Get intent data
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("employee")) {
            Employee employee = intent.getParcelableExtra("employee");
            if (employee != null) {
                populateEmployeeDetails(employee);
            }
        }
    }

    private void populateEmployeeDetails(final Employee employee) {
        nameTextView.setText(employee.getName());
        emailTextView.setText(employee.getEmail());
        phoneTextView.setText(employee.getPhone());

        // Set click listener for emailTextView
        emailTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open Gmail with intent
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + employee.getEmail()));
                startActivity(emailIntent);
            }
        });

        // Set click listener for phoneTextView
        phoneTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open dialer with intent
                Intent dialerIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + employee.getPhone()));
                startActivity(dialerIntent);

            }
        });
    }



}
