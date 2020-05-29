package com.example.onlinehospitalappointmentbooking.view;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.onlinehospitalappointmentbooking.MainActivity;
import com.example.onlinehospitalappointmentbooking.R;
import com.example.onlinehospitalappointmentbooking.util.Session;

public class AdminHome extends AppCompatActivity {

    Button addHospital;
    Button adminLogout;
    Button viewHospitals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        addHospital=(Button) findViewById(R.id.addhospital);
        viewHospitals=(Button) findViewById(R.id.adminviewhospitals);
        adminLogout=(Button) findViewById(R.id.adminlogout);


        final Session s = new Session(getApplicationContext());

        addHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddHospital.class);
                startActivity(i);
            }
        });

        adminLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                s.loggingOut();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        viewHospitals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.v("in list view action ","");
                Intent i = new Intent(getApplicationContext(),AdminListHospital.class);
                startActivity(i);
            }
        });
    }
}