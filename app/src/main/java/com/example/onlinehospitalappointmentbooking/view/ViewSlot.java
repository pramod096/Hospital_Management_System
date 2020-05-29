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
import com.example.onlinehospitalappointmentbooking.form.Slot;
import com.example.onlinehospitalappointmentbooking.util.Constants;
import com.example.onlinehospitalappointmentbooking.util.Session;

public class ViewSlot extends AppCompatActivity {

    TextView t1;

    Button cancel;
    Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_slot);

        cancel=(Button) findViewById(R.id.viewslotCanel);
        delete=(Button) findViewById(R.id.viewslotdelete);

        final Session session=new Session(getApplicationContext());
        final String role=session.getRole();

        Intent i=getIntent();
        savedInstanceState=i.getExtras();

        final String slotid=savedInstanceState.getString("slotid");

        t1=(TextView) findViewById(R.id.textviewslotname);

        DAO d=new DAO();
        d.getDBReference(Constants.SLOT_DB).child(slotid).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Slot slot=dataSnapshot.getValue(Slot.class);

                if(slot!=null)
                {
                    t1.setText("Slot:"+slot.getSlot());
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

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DAO d=new DAO();
                d.deleteObject(Constants.SLOT_DB,slotid);

                Intent i = new Intent(getApplicationContext(),HospitalHome.class);
                startActivity(i);
            }
        });
    }
}
