package com.example.onlinehospitalappointmentbooking.view;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.example.onlinehospitalappointmentbooking.R;
import com.example.onlinehospitalappointmentbooking.dao.DAO;
import com.example.onlinehospitalappointmentbooking.form.Patient;
import com.example.onlinehospitalappointmentbooking.util.Constants;
import com.example.onlinehospitalappointmentbooking.util.Session;

public class ViewPatient extends AppCompatActivity {

    TextView t1,t2,t3,t4;

    Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient);

        cancel=(Button) findViewById(R.id.viewpatientCanel);

        final Session session=new Session(getApplicationContext());
        final String role=session.getRole();

        Intent i=getIntent();
        savedInstanceState=i.getExtras();

        final String patientid=savedInstanceState.getString("patientid");

        t1=(TextView) findViewById(R.id.textviewpatientname);
        t2=(TextView)findViewById(R.id.textviewpatientemail);
        t3=(TextView)findViewById(R.id.textviewpatientmobile);
        t4=(TextView)findViewById(R.id.textviewpatientgender);

        DAO d=new DAO();
        d.getDBReference(Constants.PATIENT_DB).child(patientid).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Patient patient =dataSnapshot.getValue(Patient.class);

                if(patient!=null)
                {
                    t1.setText("Patient Name :"+patient.getName());
                    t2.setText("Email :"+patient.getEmail());
                    t3.setText("Mobile :"+patient.getMobile());
                    t4.setText("Address :"+patient.getGender());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), HospitalHome.class);
                startActivity(i);
            }
        });
    }
}
