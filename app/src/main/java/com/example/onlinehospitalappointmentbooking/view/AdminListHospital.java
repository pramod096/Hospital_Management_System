package com.example.onlinehospitalappointmentbooking.view;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.onlinehospitalappointmentbooking.R;
import com.example.onlinehospitalappointmentbooking.dao.DAO;
import com.example.onlinehospitalappointmentbooking.form.Hospital;
import com.example.onlinehospitalappointmentbooking.util.Constants;
import com.example.onlinehospitalappointmentbooking.util.MapUtil;
import com.example.onlinehospitalappointmentbooking.util.Session;

public class AdminListHospital extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_list_hospital);

        listView=(ListView) findViewById(R.id.AdminHospitalList);

        DAO dao=new DAO();
        dao.setDataToAdapterList(listView, Hospital.class, Constants.HOSPITAL_DB,"");

        final Session s=new Session(getApplicationContext());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Log.v("in list action perform ","in list action perform");

                String item = listView.getItemAtPosition(i).toString();
                item= MapUtil.stringToMap(s.getViewMap()).get(item);

                Intent intent= new Intent(getApplicationContext(), ViewHospital.class);;
                intent.putExtra("hospitalid",item);
                startActivity(intent);
            }
        });
    }
}
