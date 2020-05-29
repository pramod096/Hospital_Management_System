package com.example.onlinehospitalappointmentbooking.view;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.onlinehospitalappointmentbooking.R;
import com.example.onlinehospitalappointmentbooking.dao.DAO;
import com.example.onlinehospitalappointmentbooking.form.Slot;
import com.example.onlinehospitalappointmentbooking.util.Constants;
import com.example.onlinehospitalappointmentbooking.util.Session;

public class AddSlot extends AppCompatActivity {

    EditText e1;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_slot);

        final Session s=new Session(getApplicationContext());

        e1=(EditText)findViewById(R.id.addslotslot);
        b1=(Button)findViewById(R.id.addslotsubmitbutton);

        Intent i=getIntent();
        savedInstanceState=i.getExtras();

        final String doctorid=savedInstanceState.getString("doctorid");

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String slot=e1.getText().toString();

                if(slot==null)
                {
                    Toast.makeText(getApplicationContext(),"Please Enter Valid Data",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Slot slot1=new Slot();

                    slot1.setSlotid(DAO.getUnicKey(Constants.SLOT_DB));
                    slot1.setSlot(slot);
                    slot1.setDoctorid(doctorid);

                    DAO dao=new DAO();

                    try
                    {
                        dao.addObject(Constants.SLOT_DB,slot1,slot1.getSlotid());

                        Toast.makeText(getApplicationContext(),"Slot Added Success",Toast.LENGTH_SHORT).show();

                        Intent i=new Intent(getApplicationContext(),HospitalHome.class);
                        startActivity(i);
                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(getApplicationContext(),"Slot Adding Failed",Toast.LENGTH_SHORT).show();
                        Log.v("Failed", ex.toString());
                        ex.printStackTrace();
                    }
                }
            }
        });
    }
}
