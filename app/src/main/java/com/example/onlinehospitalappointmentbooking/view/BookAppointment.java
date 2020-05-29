package com.example.onlinehospitalappointmentbooking.view;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.example.onlinehospitalappointmentbooking.R;
import com.example.onlinehospitalappointmentbooking.dao.DAO;
import com.example.onlinehospitalappointmentbooking.form.Appointment;
import com.example.onlinehospitalappointmentbooking.form.Slot;
import com.example.onlinehospitalappointmentbooking.util.Constants;
import com.example.onlinehospitalappointmentbooking.util.Session;

import java.util.ArrayList;
import java.util.List;

public class BookAppointment extends AppCompatActivity {

    private Spinner slot;
    private String slotString;

    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_book_appointment);
        final Session s=new Session(getApplicationContext());

        final Session session=new Session(getApplicationContext());

        Intent i=getIntent();
        savedInstanceState=i.getExtras();

        final String doctorid=savedInstanceState.getString("doctorid");
        final String hospitalid=savedInstanceState.getString("hospitalid");
        final String adate=savedInstanceState.getString("adate");

        slot=(Spinner) findViewById(R.id.spinnerslot);

        // spinners setting start
        final List<String> slots=new ArrayList<>();

        new DAO().getDBReference(Constants.SLOT_DB).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshotNode: dataSnapshot.getChildren()) {

                    Slot slot=(Slot)snapshotNode.getValue(Slot.class);

                    if(slot.getDoctorid().equals(doctorid))
                    {
                        slots.add(slot.getSlot());
                    }
                }

                new DAO().getDBReference(Constants.APPOINTMENT_DB).addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshotNode: dataSnapshot.getChildren()) {

                            Appointment appointment=snapshotNode.getValue(Appointment.class);

                            if(appointment.getAdate().equals(adate) && appointment.getDoctorid().equals(doctorid) && appointment.getHospitalid().equals(hospitalid))
                            {
                                slots.remove(appointment.getSlot());
                            }
                        }

                        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(slot.getContext(),
                                android.R.layout.simple_list_item_1, (slots.toArray(new String[slots.size()])));

                        slot.setAdapter(adapter1);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        // Spinner click listener
        slot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                slotString= adapterView.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        b1=(Button)findViewById(R.id.submitappointment);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(adate==null|| slotString==null)
                {
                    Toast.makeText(getApplicationContext(),"Please Enter Valid Data",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Appointment appointment=new Appointment();

                    appointment.setAppointmentid(DAO.getUnicKey(Constants.APPOINTMENT_DB));
                    appointment.setAdate(adate);
                    appointment.setDoctorid(doctorid);
                    appointment.setHospitalid(hospitalid);
                    appointment.setPatientid(s.getusername());
                    appointment.setSlot(slotString);

                    DAO dao=new DAO();

                    try
                    {
                        dao.addObject(Constants.APPOINTMENT_DB,appointment,appointment.getAppointmentid());

                        Toast.makeText(getApplicationContext(),"Your Appoint is Confirmed",Toast.LENGTH_SHORT).show();

                        Intent i=new Intent(getApplicationContext(),PatientHome.class);
                        startActivity(i);
                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(getApplicationContext(),"Appoint is Failed",Toast.LENGTH_SHORT).show();
                        Log.v("failed", ex.toString());
                        ex.printStackTrace();
                    }
                }
            }
        });
    }
}
