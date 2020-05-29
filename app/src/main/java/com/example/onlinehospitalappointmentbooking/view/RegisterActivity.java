package com.example.onlinehospitalappointmentbooking.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.onlinehospitalappointmentbooking.R;
import com.example.onlinehospitalappointmentbooking.dao.DAO;
import com.example.onlinehospitalappointmentbooking.form.Patient;
import com.example.onlinehospitalappointmentbooking.util.Constants;
import com.example.onlinehospitalappointmentbooking.util.Session;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    EditText e1,e2,e3,e4,e5,e6;
    EditText dateofbirth;
    Calendar myCalendar;

    private Spinner gender;
    private String genderString;

    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        final Session s=new Session(getApplicationContext());

        e1=(EditText)findViewById(R.id.registerPhone);
        e2=(EditText)findViewById(R.id.registerPassword);
        e3=(EditText)findViewById(R.id.registerConPass);
        e4=(EditText)findViewById(R.id.registerEmail);
        e5=(EditText)findViewById(R.id.registerMobile);
        e6=(EditText)findViewById(R.id.registerName);

        gender=(Spinner) findViewById(R.id.spinnerGender);

        // spinners setting start

        List<String> al=new ArrayList<>();
        al.add("Male");
        al.add("Female");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(gender.getContext(),
                android.R.layout.simple_list_item_1, (al.toArray(new String[al.size()])));

        gender.setAdapter(adapter);


        // Spinner click listener
        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                genderString= adapterView.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        // calender setting start

        myCalendar = Calendar.getInstance();

        dateofbirth= (EditText) findViewById(R.id.dateofbirth);
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

        dateofbirth.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(RegisterActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        b1=(Button)findViewById(R.id.registerButton);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String patientname=e1.getText().toString();
                String password=e2.getText().toString();
                String conformPassword=e3.getText().toString();
                String email=e4.getText().toString();
                String mobile=e5.getText().toString();
                String name=e6.getText().toString();
                String dob=dateofbirth.getText().toString();

                if(patientname==null|| password==null|| conformPassword==null|| email==null|| mobile==null|| name==null|| dob==null)
                {
                    Toast.makeText(getApplicationContext(),"Please Enter Valid Data",Toast.LENGTH_SHORT).show();
                }
                else if(mobile.length()!=10)
                {
                    Toast.makeText(getApplicationContext(),"Invalid Mobile",Toast.LENGTH_SHORT).show();
                }
                else if(!password.equals(conformPassword))
                {
                    Toast.makeText(getApplicationContext(),"Password Mismatch",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Patient patient=new Patient();

                    patient.setPatientid(patientname);
                    patient.setPassword(password);
                    patient.setEmail(email);
                    patient.setMobile(mobile);
                    patient.setName(name);
                    patient.setDob(dob);
                    patient.setGender(genderString);

                    DAO dao=new DAO();

                    try
                    {
                        dao.addObject(Constants.PATIENT_DB,patient,patient.getPatientid());

                        Toast.makeText(getApplicationContext(),"Register Success",Toast.LENGTH_SHORT).show();

                        Intent i=new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(i);
                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(getApplicationContext(),"Register Error",Toast.LENGTH_SHORT).show();
                        Log.v("Patient Registration Ex", ex.toString());
                        ex.printStackTrace();
                    }

                }
            }
        });
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateofbirth.setText(sdf.format(myCalendar.getTime()));
    }
}
