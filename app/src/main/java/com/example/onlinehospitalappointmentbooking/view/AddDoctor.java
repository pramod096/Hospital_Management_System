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
import com.example.onlinehospitalappointmentbooking.form.Doctor;
import com.example.onlinehospitalappointmentbooking.util.Constants;
import com.example.onlinehospitalappointmentbooking.util.Session;

public class AddDoctor extends AppCompatActivity {

    EditText e1,e2,e3,e4,e5,e6;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_doctor);

        final Session s=new Session(getApplicationContext());

        e1=(EditText)findViewById(R.id.adddoctorname);
        e3=(EditText)findViewById(R.id.adddoctorspeciality);
        e4=(EditText)findViewById(R.id.adddoctorqualification);
        e5=(EditText)findViewById(R.id.adddoctorexperience);
        e6=(EditText)findViewById(R.id.adddoctorlanguage);

        b1=(Button)findViewById(R.id.adddoctorbutton);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name=e1.getText().toString();
                String speciality=e3.getText().toString();
                String qualification=e4.getText().toString();
                String experience=e5.getText().toString();
                String language=e6.getText().toString();
                String hospitalid=s.getusername();

                if(name==null|| speciality==null|| qualification==null|| experience==null|| language==null|| hospitalid==null)
                {
                    Toast.makeText(getApplicationContext(),"Please Enter Valid Data",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Doctor doctor=new Doctor();

                    doctor.setDoctorid(DAO.getUnicKey(Constants.DOCTOR_DB));
                    doctor.setName(name);
                    doctor.setSpeciality(speciality);
                    doctor.setQualification(qualification);
                    doctor.setExperience(experience);
                    doctor.setLanguage(language);
                    doctor.setHospital(hospitalid);

                    DAO dao=new DAO();

                    try
                    {
                        dao.addObject(Constants.DOCTOR_DB,doctor,doctor.getDoctorid());

                        Toast.makeText(getApplicationContext(),"Doctor Added Success",Toast.LENGTH_SHORT).show();

                        Intent i=new Intent(getApplicationContext(),HospitalHome.class);
                        startActivity(i);
                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(getApplicationContext(),"Doctor Adding Failed",Toast.LENGTH_SHORT).show();
                        Log.v("Failed", ex.toString());
                        ex.printStackTrace();
                    }
                }
            }
        });
    }
}
