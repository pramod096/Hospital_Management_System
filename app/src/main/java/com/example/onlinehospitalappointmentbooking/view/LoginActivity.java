package com.example.onlinehospitalappointmentbooking.view;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.example.onlinehospitalappointmentbooking.R;
import com.example.onlinehospitalappointmentbooking.dao.DAO;
import com.example.onlinehospitalappointmentbooking.form.Hospital;
import com.example.onlinehospitalappointmentbooking.form.Patient;
import com.example.onlinehospitalappointmentbooking.util.Constants;
import com.example.onlinehospitalappointmentbooking.util.Session;

public class LoginActivity extends AppCompatActivity {

    private Session session;
    EditText e1,e2;
    Button b1,b2,b3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new Session(getApplicationContext());

        setContentView(R.layout.activity_login);

        e1=(EditText)findViewById(R.id.loginPhone);
        e2=(EditText)findViewById(R.id.loginPass);
        b1=(Button)findViewById(R.id.adminlogin);
        b2=(Button)findViewById(R.id.hospitallogin);
        b3=(Button)findViewById(R.id.patientlogin);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String username=e1.getText().toString();
                final String password=e2.getText().toString();

                if(username==null|| password==null || username.length()<=0|| password.length()<=0)
                {
                    Toast.makeText(getApplicationContext(),"Please Enter UserName and Password",Toast.LENGTH_SHORT).show();
                }
                else {

                    if (username.equals("admin") && password.equals("admin")) {

                        session.setusername("admin");
                        session.setRole("admin");

                        Intent i = new Intent(getApplicationContext(), AdminHome.class);
                        startActivity(i);

                    } else {
                        Toast.makeText(getApplicationContext(),"Invalid Credentials",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String username=e1.getText().toString();
                final String password=e2.getText().toString();

                if(username==null|| password==null || username.length()<=0|| password.length()<=0)
                {
                    Toast.makeText(getApplicationContext(),"Please Enter UserName and Password",Toast.LENGTH_SHORT).show();
                }
                else {

                    DAO d = new DAO();

                    d.getDBReference(Constants.HOSPITAL_DB).child(username).addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            Hospital hospital = dataSnapshot.getValue(Hospital.class);

                            if (hospital == null) {
                                Toast.makeText(getApplicationContext(), "Invalid UserName ", Toast.LENGTH_SHORT).show();
                            } else if (hospital.getPassword().equals(password)) {

                                //global variable
                                session = new Session(getApplicationContext());
                                session.setusername(hospital.getHospitalid());
                                session.setRole("hospital");

                                Intent i = new Intent(getApplicationContext(),HospitalHome.class);
                                startActivity(i);

                            } else {
                                Toast.makeText(getApplicationContext(), "In valid Password", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                        }
                    });
                }
            }
        });


        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String username=e1.getText().toString();
                final String password=e2.getText().toString();

                if(username==null|| password==null || username.length()<=0|| password.length()<=0)
                {
                    Toast.makeText(getApplicationContext(),"Please Enter UserName and Password",Toast.LENGTH_SHORT).show();
                }
                else {

                    DAO d = new DAO();

                    d.getDBReference(Constants.PATIENT_DB).child(username).addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            Patient patient = dataSnapshot.getValue(Patient.class);

                            if (patient == null) {
                                Toast.makeText(getApplicationContext(), "Invalid UserName ", Toast.LENGTH_SHORT).show();
                            } else if (patient.getPassword().equals(password)) {

                                //global variable
                                session = new Session(getApplicationContext());
                                session.setusername(patient.getPatientid());
                                session.setRole("patient");

                                Intent i = new Intent(getApplicationContext(),PatientHome.class);
                                startActivity(i);

                            } else {
                                Toast.makeText(getApplicationContext(), "In valid Password", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                        }
                    });
                }
            }
        });
    }
}
