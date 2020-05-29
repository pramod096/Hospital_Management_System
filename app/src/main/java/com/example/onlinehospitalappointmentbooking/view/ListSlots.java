package com.example.onlinehospitalappointmentbooking.view;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.onlinehospitalappointmentbooking.R;
import com.example.onlinehospitalappointmentbooking.dao.DAO;
import com.example.onlinehospitalappointmentbooking.form.Slot;
import com.example.onlinehospitalappointmentbooking.util.Constants;
import com.example.onlinehospitalappointmentbooking.util.MapUtil;
import com.example.onlinehospitalappointmentbooking.util.Session;

public class ListSlots extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_slots);

        listView=(ListView) findViewById(R.id.SlotList);

        Intent i=getIntent();
        savedInstanceState=i.getExtras();

        final Session s = new Session(getApplicationContext());

        final String doctorid = savedInstanceState.getString("doctorid");

        DAO dao=new DAO();
        dao.setDataToAdapterList(listView, Slot.class, Constants.SLOT_DB,doctorid);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String item = listView.getItemAtPosition(i).toString();
                item= MapUtil.stringToMap(s.getViewMap()).get(item);

                Intent intent= new Intent(getApplicationContext(), ViewSlot.class);
                intent.putExtra("slotid",item);
                startActivity(intent);
            }
        });
    }
}
