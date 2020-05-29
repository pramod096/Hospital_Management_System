package com.example.onlinehospitalappointmentbooking.view;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.onlinehospitalappointmentbooking.R;
import com.example.onlinehospitalappointmentbooking.dao.DAO;
import com.example.onlinehospitalappointmentbooking.form.Doctor;
import com.example.onlinehospitalappointmentbooking.util.Constants;
import com.example.onlinehospitalappointmentbooking.util.MapUtil;
import com.example.onlinehospitalappointmentbooking.util.Session;

public class ListDoctors extends AppCompatActivity {

    ListView listView;

    String adate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_doctors);

        listView=(ListView) findViewById(R.id.DoctorsList);

        Intent i=getIntent();
        savedInstanceState=i.getExtras();

        final Session s = new Session(getApplicationContext());

        if(s.getRole().equals("patient")) {

            final String query = savedInstanceState.getString("query");
            adate = savedInstanceState.getString("adate");

            DAO dao = new DAO();
            dao.setDataToAdapterList(listView, Doctor.class, Constants.DOCTOR_DB, query);

        }else if(s.getRole().equals("hospital"))
        {
            DAO dao=new DAO();
            dao.setDataToAdapterList(listView, Doctor.class, Constants.DOCTOR_DB,"");
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String item = listView.getItemAtPosition(i).toString();
                item= MapUtil.stringToMap(s.getViewMap()).get(item);

                if(s.getRole().equals("patient")) {
                    Intent intent= new Intent(getApplicationContext(), ViewDoctor.class);
                    intent.putExtra("doctorid",item);
                    intent.putExtra("adate",adate);
                    startActivity(intent);
                }

                if(s.getRole().equals("hospital")) {
                    Intent intent= new Intent(getApplicationContext(), ViewDoctorProfile.class);
                    intent.putExtra("doctorid",item);
                    startActivity(intent);
                }
            }
        });
    }
}
