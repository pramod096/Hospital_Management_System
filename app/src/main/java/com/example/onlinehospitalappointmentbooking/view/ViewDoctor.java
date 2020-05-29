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
import com.example.onlinehospitalappointmentbooking.form.Doctor;
import com.example.onlinehospitalappointmentbooking.form.Hospital;
import com.example.onlinehospitalappointmentbooking.util.Constants;
import com.example.onlinehospitalappointmentbooking.util.Session;

public class ViewDoctor extends AppCompatActivity {

    TextView t1,t2,t3,t4,t5,t6,t7,t8;

    Button cancel;
    Button proceed;

    String doctorid;
    String hospitalid;
    String adate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_doctor);

        cancel=(Button) findViewById(R.id.viewdoctorCanel);
        proceed=(Button) findViewById(R.id.bookingappointmentproceed);

        final Session session=new Session(getApplicationContext());
        final String role=session.getRole();

        Intent i=getIntent();
        savedInstanceState=i.getExtras();

        doctorid=savedInstanceState.getString("doctorid");
        adate=savedInstanceState.getString("adate");

        t1=(TextView) findViewById(R.id.textviewdoctorname);
        t2=(TextView)findViewById(R.id.textviewspeciality);
        t3=(TextView)findViewById(R.id.textviewqualification);
        t4=(TextView) findViewById(R.id.textviewExperience);
        t5=(TextView)findViewById(R.id.textviewlanguage);
        t6=(TextView)findViewById(R.id.textviewhospitalname);
        t7=(TextView) findViewById(R.id.textviewhospitalmobile);
        t8=(TextView)findViewById(R.id.textviewhospitaladdress);

        DAO d=new DAO();
        d.getDBReference(Constants.DOCTOR_DB).child(doctorid).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final Doctor doctor=dataSnapshot.getValue(Doctor.class);

                if(doctor!=null)
                {
                    DAO d=new DAO();
                    d.getDBReference(Constants.HOSPITAL_DB).child(doctor.getHospital()).addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            Hospital hospital=dataSnapshot.getValue(Hospital.class);

                            if(hospital!=null)
                            {
                                t1.setText("Doctor Name : "+doctor.getName());
                                t2.setText("Speciality: "+doctor.getSpeciality());
                                t3.setText("Qualification: "+doctor.getQualification());
                                t4.setText("Experience: "+doctor.getExperience());
                                t5.setText("Languages Speak: "+doctor.getLanguage());
                                t6.setText("Hospital Name: "+hospital.getName());
                                t7.setText("Hospital Mobile: "+hospital.getMobile());
                                t8.setText("Hosipital Address: "+hospital.getAddress());

                                hospitalid=hospital.getHospitalid();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),PatientHome.class);
                startActivity(i);
            }
        });

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),BookAppointment.class);

                i.putExtra("doctorid",doctorid);
                i.putExtra("hospitalid",hospitalid);
                i.putExtra("adate",adate);

                startActivity(i);
            }
        });
    }
}
