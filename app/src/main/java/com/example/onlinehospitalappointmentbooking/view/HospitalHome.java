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


public class HospitalHome extends AppCompatActivity {

    Button addDoctor;
    Button hospitalLogout;
    Button viewDoctors;
    Button viewappointments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_home);

        addDoctor=(Button) findViewById(R.id.adddoctor);
        viewDoctors=(Button) findViewById(R.id.viewdoctors);
        hospitalLogout=(Button) findViewById(R.id.hospitallogout);
        viewappointments=(Button) findViewById(R.id.hospitalviewappointments);


        final Session s = new Session(getApplicationContext());

        addDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddDoctor.class);
                startActivity(i);
            }
        });

        hospitalLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                s.loggingOut();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        viewDoctors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.v("in list view action ","");
                Intent i = new Intent(getApplicationContext(),ListDoctors.class);
                startActivity(i);
            }
        });

        viewappointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.v("in list view action ","");
                Intent i = new Intent(getApplicationContext(),ListAppointments.class);
                startActivity(i);
            }
        });
    }
}