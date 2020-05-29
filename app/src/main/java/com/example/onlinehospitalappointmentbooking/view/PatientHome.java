package com.example.onlinehospitalappointmentbooking.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinehospitalappointmentbooking.MainActivity;
import com.example.onlinehospitalappointmentbooking.R;
import com.example.onlinehospitalappointmentbooking.util.Session;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class PatientHome extends AppCompatActivity {

    EditText searchbox;
    Button patientsearchButton;
    Button patientlogout;
    Button viewappointments;

    EditText dateofappointment;
    Calendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);

        searchbox = (EditText) findViewById(R.id.patientsearchbox);

        patientsearchButton = (Button) findViewById(R.id.patientsearchButton);
        patientlogout = (Button) findViewById(R.id.patientlogout);
        viewappointments = (Button) findViewById(R.id.patientviewappointments);


        // calender setting start

        myCalendar = Calendar.getInstance();

        dateofappointment= (EditText) findViewById(R.id.dateofappointment);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        dateofappointment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(PatientHome.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        final Session s = new Session(getApplicationContext());

        patientsearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String query = searchbox.getText().toString();
                String adate=dateofappointment.getText().toString();

                if(query!=null && adate!=null)
                {
                    Intent i = new Intent(getApplicationContext(), ListDoctors.class);
                    i.putExtra("query", query);
                    i.putExtra("adate", adate);

                    startActivity(i);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please Select Date and Enter Query",Toast.LENGTH_LONG).show();
                }
            }
        });

        viewappointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),ListAppointments.class);
                startActivity(i);
            }
        });

        patientlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                s.loggingOut();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateofappointment.setText(sdf.format(myCalendar.getTime()));
    }
}
