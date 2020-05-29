package com.example.onlinehospitalappointmentbooking.dao;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.onlinehospitalappointmentbooking.form.Appointment;
import com.example.onlinehospitalappointmentbooking.form.Doctor;
import com.example.onlinehospitalappointmentbooking.form.Hospital;
import com.example.onlinehospitalappointmentbooking.form.Patient;
import com.example.onlinehospitalappointmentbooking.form.Slot;
import com.example.onlinehospitalappointmentbooking.util.Constants;
import com.example.onlinehospitalappointmentbooking.util.MapUtil;
import com.example.onlinehospitalappointmentbooking.util.Session;

public class DAO
{
    public static DatabaseReference getDBReference(String dbName)
    {
        return GetFireBaseConnection.getConnection(dbName);
    }

    public static String getUnicKey(String dbName)
    {
        return getDBReference(dbName).push().getKey();
    }

    public int addObject(String dbName,Object obj,String key) {

        int result=0;

        try {

            getDBReference(dbName).child(key).setValue(obj);

            result=1;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return result;
    }

    public void setDataToAdapterList(final View view, final Class c, final String dbname,final String query) {

        Log.v("in list populated ","in list populated ");

        final Map<String,Object> map=new HashMap<String,Object>();
        final Map<String,String> viewMap=new HashMap<String,String>();

        final Session s=new Session(view.getContext());

        getDBReference(dbname).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshotNode: dataSnapshot.getChildren()) {

                    Log.v("in data found ","in data found ");

                    String id=snapshotNode.getKey();

                    Object object=snapshotNode.getValue(c);

                    if(dbname.equals(Constants.APPOINTMENT_DB)) {

                        final Appointment appointment = (Appointment) object;

                        Log.v("Appointment ","GOT");

                        getDBReference(Constants.HOSPITAL_DB).child(appointment.getHospitalid()).addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                final Hospital hospital=dataSnapshot.getValue(Hospital.class);

                                Log.v("Hospital ","GOT");

                                if(hospital!=null)
                                {
                                    if(s.getRole().equals("hospital"))
                                    {
                                        Log.v("in Role Hospital ","GOT");

                                        if(appointment.getHospitalid().equals(s.getusername()))
                                        {
                                            Log.v("appointment matched","GOT");

                                            viewMap.put(hospital.getName()+"-"+appointment.getAdate()+"-"+appointment.getSlot(),appointment.getAppointmentid());
                                        }
                                    }
                                    else
                                    {
                                        Log.v("in Role Patient ","GOT");

                                        if(appointment.getPatientid().equals(s.getusername()))
                                        {
                                            Log.v("appointment matched",hospital.getName()+"-"+appointment.getAdate()+"-"+appointment.getSlot()+"-"+appointment.getAppointmentid());

                                            viewMap.put(hospital.getName()+"-"+appointment.getAdate()+"-"+appointment.getSlot(),appointment.getAppointmentid());
                                        }
                                    }

                                    ArrayList<String> al=new ArrayList<String>(viewMap.keySet());

                                    Log.v("AL",al.toString());

                                    if(view instanceof ListView) {

                                        final ListView myView=(ListView)view;

                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(myView.getContext(),
                                                android.R.layout.simple_list_item_1, (al.toArray(new String[al.size()])));

                                        myView.setAdapter(adapter);

                                        Log.v("after adapter setting",al.size()+"-"+viewMap.size());
                                    }

                                    s.setViewMap(MapUtil.mapToString(viewMap));

                                    Log.v("after session","end");
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                    else if(dbname.equals(Constants.DOCTOR_DB)) {

                        final Doctor doctor = (Doctor) object;

                        if(s.getRole().equals("hospital"))
                        {
                            if(doctor.getHospital().equals(s.getusername()))
                            {
                                viewMap.put(doctor.getName()+"-"+doctor.getSpeciality()+"-"+doctor.getQualification()+"-"+doctor.getExperience(),doctor.getDoctorid());
                            }
                        }
                        else
                        {
                            if(doctor.getSpeciality().contains(query) || doctor.getName().contains(query))
                            {
                                viewMap.put(doctor.getName()+"-"+doctor.getSpeciality()+"-"+doctor.getQualification()+"-"+doctor.getExperience(),doctor.getDoctorid());
                            }
                        }

                    }else if(dbname.equals(Constants.HOSPITAL_DB)) {

                        final Hospital hospital = (Hospital) object;
                        viewMap.put(hospital.getName()+"-"+hospital.getAddress(),hospital.getHospitalid());

                    }else if(dbname.equals(Constants.PATIENT_DB)) {

                        final Patient patient = (Patient) object;
                        viewMap.put(patient.getName(),patient.getPatientid());
                    }
                    else if(dbname.equals(Constants.SLOT_DB)) {

                        final Slot slot = (Slot) object;

                        if(slot.getDoctorid().equals(query))
                        {
                            viewMap.put(slot.getSlot(),slot.getSlotid());
                        }
                    }
                }

                if(dbname!=Constants.APPOINTMENT_DB)
                {
                    ArrayList<String> al=new ArrayList<String>(viewMap.keySet());

                    Log.v("AL",al.toString());

                    if(view instanceof ListView) {

                        final ListView myView=(ListView)view;

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(myView.getContext(),
                                android.R.layout.simple_list_item_1, (al.toArray(new String[al.size()])));

                        myView.setAdapter(adapter);

                        Log.v("after adapter setting",al.size()+"-"+viewMap.size());
                    }

                    s.setViewMap(MapUtil.mapToString(viewMap));

                    Log.v("after session","end");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

    }

    public int deleteObject(String dbName, String key) {

        int result=0;

        try {

            getDBReference(dbName).child(key).removeValue();

            result=1;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return 0;
    }
}


