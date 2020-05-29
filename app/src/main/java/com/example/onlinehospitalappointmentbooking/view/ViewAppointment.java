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
import com.example.onlinehospitalappointmentbooking.form.Appointment;
import com.example.onlinehospitalappointmentbooking.form.Doctor;
import com.example.onlinehospitalappointmentbooking.form.Hospital;
import com.example.onlinehospitalappointmentbooking.form.Patient;
import com.example.onlinehospitalappointmentbooking.util.Constants;
import com.example.onlinehospitalappointmentbooking.util.Session;

public class ViewAppointment extends AppCompatActivity {

    TextView t1,t2,t3,t4,t5,t6,t7;

    Button cancel;
    Button delete;

    String appointmentid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appointment);

        cancel=(Button) findViewById(R.id.textviewappointmentcancel);
        delete=(Button) findViewById(R.id.textviewappointmentdelete);

        final Session session=new Session(getApplicationContext());

        Intent i=getIntent();
        savedInstanceState=i.getExtras();

        appointmentid=savedInstanceState.getString("appointmentid");

        t1=(TextView) findViewById(R.id.textviewappointmentpatientname);
        t2=(TextView)findViewById(R.id.textviewappointmentpatientmobile);
        t3=(TextView)findViewById(R.id.textviewappointmenthospitalname);
        t4=(TextView) findViewById(R.id.textviewappointmenthospitalmobile);
        t5=(TextView)findViewById(R.id.textviewappointmentdoctorname);
        t6=(TextView)findViewById(R.id.textviewappointmentslotname);
        t7=(TextView) findViewById(R.id.textviewappointmentslotdate);

        DAO d=new DAO();
        d.getDBReference(Constants.APPOINTMENT_DB).child(appointmentid).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final Appointment appointment=dataSnapshot.getValue(Appointment.class);

                if(appointment!=null)
                {
                    DAO d=new DAO();
                    d.getDBReference(Constants.HOSPITAL_DB).child(appointment.getHospitalid()).addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            final Hospital hospital=dataSnapshot.getValue(Hospital.class);

                            if(hospital!=null)
                            {
                                DAO d=new DAO();
                                d.getDBReference(Constants.PATIENT_DB).child(appointment.getPatientid()).addListenerForSingleValueEvent(new ValueEventListener() {

                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        final Patient  patient =dataSnapshot.getValue(Patient.class);

                                        if(patient!=null)
                                        {
                                            DAO d=new DAO();
                                            d.getDBReference(Constants.DOCTOR_DB).child(appointment.getDoctorid()).addListenerForSingleValueEvent(new ValueEventListener() {

                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {

                                                    final Doctor doctor =dataSnapshot.getValue(Doctor.class);

                                                    if(doctor!=null)
                                                    {
                                                        t1.setText("Patient Name : "+patient.getName());
                                                        t2.setText("Patient Mobile: "+patient.getMobile());
                                                        t3.setText("Hospital Name: "+hospital.getName());
                                                        t4.setText("Hospital Mobile: "+hospital.getMobile());
                                                        t5.setText("Doctor Name: "+doctor.getName());
                                                        t6.setText("Slot: "+appointment.getSlot());
                                                        t7.setText("Date: "+appointment.getAdate());
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

                if(session.getRole().equals("patient"))
                {
                    Intent i = new Intent(getApplicationContext(),PatientHome.class);
                    startActivity(i);

                }else if(session.getRole().equals("hospital"))
                {
                    Intent i = new Intent(getApplicationContext(),HospitalHome.class);
                    startActivity(i);
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DAO d=new DAO();
                d.deleteObject(Constants.APPOINTMENT_DB,appointmentid);

                if(session.getRole().equals("patient"))
                {
                    Intent i = new Intent(getApplicationContext(),PatientHome.class);
                    startActivity(i);

                }else if(session.getRole().equals("hospital"))
                {
                    Intent i = new Intent(getApplicationContext(),HospitalHome.class);
                    startActivity(i);
                }
            }
        });
    }
}
