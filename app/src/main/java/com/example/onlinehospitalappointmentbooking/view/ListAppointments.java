package com.example.onlinehospitalappointmentbooking.view;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.onlinehospitalappointmentbooking.R;
import com.example.onlinehospitalappointmentbooking.dao.DAO;
import com.example.onlinehospitalappointmentbooking.form.Appointment;
import com.example.onlinehospitalappointmentbooking.util.Constants;
import com.example.onlinehospitalappointmentbooking.util.MapUtil;
import com.example.onlinehospitalappointmentbooking.util.Session;

public class ListAppointments extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_appointments);

        listView=(ListView) findViewById(R.id.AppointmentsList);

        final Session s=new Session(getApplicationContext());

        DAO dao=new DAO();
        dao.setDataToAdapterList(listView, Appointment.class, Constants.APPOINTMENT_DB,"");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String item = listView.getItemAtPosition(i).toString();
                item= MapUtil.stringToMap(s.getViewMap()).get(item);

                Intent intent= new Intent(getApplicationContext(),ViewAppointment.class);;
                intent.putExtra("appointmentid",item);
                startActivity(intent);
            }
        });
    }
}
