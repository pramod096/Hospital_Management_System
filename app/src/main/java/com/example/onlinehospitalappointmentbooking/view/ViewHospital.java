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

import com.example.onlinehospitalappointmentbooking.MainActivity;
import com.example.onlinehospitalappointmentbooking.R;
import com.example.onlinehospitalappointmentbooking.dao.DAO;
import com.example.onlinehospitalappointmentbooking.form.Hospital;
import com.example.onlinehospitalappointmentbooking.util.Constants;
import com.example.onlinehospitalappointmentbooking.util.Session;

public class ViewHospital extends AppCompatActivity {

    TextView t1,t2,t3,t4;

    Button cancel;
    Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_hospital);

        cancel=(Button) findViewById(R.id.viewHospitalCanel);
        delete=(Button) findViewById(R.id.viewHospitalDelete);

        final Session session=new Session(getApplicationContext());
        final String role=session.getRole();

        Intent i=getIntent();
        savedInstanceState=i.getExtras();

        final String hospitalId=savedInstanceState.getString("hospitalid");

        t1=(TextView) findViewById(R.id.textviewname);
        t2=(TextView)findViewById(R.id.textviewemail);
        t3=(TextView)findViewById(R.id.textviewmobile);
        t4=(TextView)findViewById(R.id.textviewaddress);

        DAO d=new DAO();
        d.getDBReference(Constants.HOSPITAL_DB).child(hospitalId).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Hospital hospital=dataSnapshot.getValue(Hospital.class);

                if(hospital!=null)
                {
                    t1.setText("Hospital Name :"+hospital.getName());
                    t2.setText("Email :"+hospital.getEmail());
                    t3.setText("Mobile :"+hospital.getMobile());
                    t4.setText("Address :"+hospital.getAddress());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i =null;

                if(role.equals("admin")) {
                    i = new Intent(getApplicationContext(),AdminHome.class);
                }else {

                    i = new Intent(getApplicationContext(),MainActivity.class);
                }
                startActivity(i);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DAO d=new DAO();
                d.deleteObject(Constants.HOSPITAL_DB,hospitalId);

                Intent i = new Intent(getApplicationContext(),AdminHome.class);
                startActivity(i);
            }
        });
    }
}
