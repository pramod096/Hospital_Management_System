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
import com.example.onlinehospitalappointmentbooking.form.Slot;
import com.example.onlinehospitalappointmentbooking.util.Constants;
import com.example.onlinehospitalappointmentbooking.util.Session;

public class ViewDoctorProfile extends AppCompatActivity {

    TextView t1,t2,t3,t4,t5;

    Button cancel;
    Button delete;
    Button addslot;
    Button viewslots;
    String doctorid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_doctor_profile);

        cancel=(Button) findViewById(R.id.viewdoctorCanel1);
        delete=(Button) findViewById(R.id.deletedoctor);
        addslot=(Button) findViewById(R.id.addslot);
        viewslots=(Button) findViewById(R.id.viewslots);

        final Session session=new Session(getApplicationContext());
        final String role=session.getRole();

        Intent i=getIntent();
        savedInstanceState=i.getExtras();

        doctorid=savedInstanceState.getString("doctorid");

        t1=(TextView) findViewById(R.id.textviewdoctorname1);
        t2=(TextView)findViewById(R.id.textviewspeciality1);
        t3=(TextView)findViewById(R.id.textviewqualification1);
        t4=(TextView) findViewById(R.id.textviewExperience1);
        t5=(TextView)findViewById(R.id.textviewlanguage1);

        DAO d=new DAO();
        d.getDBReference(Constants.DOCTOR_DB).child(doctorid).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final Doctor doctor=dataSnapshot.getValue(Doctor.class);

                if(doctor!=null)
                {
                    t1.setText("Doctor Name : "+doctor.getName());
                    t2.setText("Speciality: "+doctor.getSpeciality());
                    t3.setText("Qualification: "+doctor.getQualification());
                    t4.setText("Experience: "+doctor.getExperience());
                    t5.setText("Languages Speak: "+doctor.getLanguage());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        addslot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), AddSlot.class);
                i.putExtra("doctorid",doctorid);
                startActivity(i);
            }
        });


        viewslots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), ListSlots.class);
                i.putExtra("doctorid",doctorid);
                startActivity(i);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final DAO d=new DAO();
                d.deleteObject(Constants.DOCTOR_DB,doctorid);

                new DAO().getDBReference(Constants.APPOINTMENT_DB).addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshotNode: dataSnapshot.getChildren()) {

                            Appointment appointment=snapshotNode.getValue(Appointment.class);

                            if(appointment.getDoctorid().equals(doctorid))
                            {
                                d.deleteObject(Constants.APPOINTMENT_DB,appointment.getAppointmentid());
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

                new DAO().getDBReference(Constants.SLOT_DB).addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshotNode: dataSnapshot.getChildren()) {

                            Slot slot =snapshotNode.getValue(Slot.class);

                            if(slot.getDoctorid().equals(doctorid))
                            {
                                d.deleteObject(Constants.SLOT_DB,slot.getSlotid());
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

                Intent i = new Intent(getApplicationContext(),HospitalHome.class);
                startActivity(i);
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
