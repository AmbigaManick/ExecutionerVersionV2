package anulom.executioner.com.anulom;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;

import android.widget.TimePicker;


import java.text.SimpleDateFormat;

import java.util.Calendar;


import android.view.View.OnClickListener;
import android.widget.Toast;

import anulom.executioner.com.anulom.database.DBManager;
import anulom.executioner.com.anulom.database.DBOperation;

import anulom.executioner.com.anulom.services.rescheduledetails;

import static anulom.executioner.com.anulom.database.DBManager.TableInfo.RESCHEDULE;



public class reschedule extends AppCompatActivity {


    private Calendar myCalendar, mcurrenttime;
    EditText edt_on_date, edt_on_time;
    Button btn_date, btn_time, update;
    Toolbar toolbar;
    String option1 = "", datevalue = " ", timevalue="", ID2, document, appointmentid, starttime, endtime, finaltime;
    RadioButton rb1, rb2, rb3, rb4;
    DBOperation db;

    private String username2 = "";

    DatePickerDialog  date1;


    public void radio1(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        int checked1 = view.getId();
        if (checked1 == R.id.govt) {
            option1 = "Govt IGR/UID Issue";
        } else if (checked1 == R.id.cust) {
            option1 = "Request by Customer";
        } else if (checked1 == R.id.issue) {
            option1 = "Technical Issue";
        } else if (checked1 == R.id.appoin) {
            option1 = "Appointment Missed by Executioner";
        }


    }

    public void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.reschedule);
        db = new DBOperation(getApplicationContext());
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Reschedule");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        appointmentid = getIntent().getStringExtra("AppointmentId");
        document = getIntent().getStringExtra("DocumentId");
        SharedPreferences usermail = PreferenceManager.getDefaultSharedPreferences(this);
        username2 = usermail.getString("username", "");
        String password2 = usermail.getString("password", "");

        rb1 = findViewById(R.id.govt);
        rb2 = findViewById(R.id.cust);
        rb3 = findViewById(R.id.issue);
        rb4 = findViewById(R.id.appoin);

        update = findViewById(R.id.btnupdate2);
        btn_date = findViewById(R.id.btn_date);
        edt_on_date = findViewById(R.id.edt_on_date);
        btn_time = findViewById(R.id.btn_time);
        edt_on_time = findViewById(R.id.edt_on_time);
        myCalendar = Calendar.getInstance();
        btn_date.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                date1 = new DatePickerDialog(reschedule.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


                        edt_on_date.setText(sdf.format(myCalendar.getTime()));
                        datevalue = edt_on_date.getText().toString();


                    }

                },myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                date1.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                date1.show();
            }
        });
        btn_time.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mcurrenttime = Calendar.getInstance();

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(reschedule.this, new TimePickerDialog.OnTimeSetListener() {

                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        mcurrenttime.set(Calendar.HOUR_OF_DAY, selectedHour);
                        mcurrenttime.set(Calendar.MINUTE, selectedMinute);
                        timePicker.setIs24HourView(true);
                        edt_on_time.setText(selectedHour+" : "+selectedMinute);
                        timevalue = edt_on_time.getText().toString();

                    }
                }, mcurrenttime.get(Calendar.HOUR_OF_DAY), mcurrenttime.get(Calendar.MINUTE), false);//Yes 24 hour time

                mTimePicker.show();

            }
        });
        update.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


if(datevalue!="" && timevalue!="" && option1!="") {
    SQLiteDatabase reschedule = db.getWritableDatabase();
    ContentValues values1 = new ContentValues();
    values1.put(DBManager.TableInfo.KEYID, ID2);
    values1.put(DBManager.TableInfo.KEY_LOGIN_USER, username2);
    values1.put(DBManager.TableInfo.RESCHEDULEDATE, datevalue);
    values1.put(DBManager.TableInfo.RESCHEDULETIME, timevalue);
    values1.put(DBManager.TableInfo.RES_REASON, option1);
    values1.put(DBManager.TableInfo.DocumentId, document);
    values1.put(DBManager.TableInfo.AppointmentId, appointmentid);
    long status = reschedule.insert(RESCHEDULE, null, values1);
    reschedule.close();

    System.out.println(datevalue+ " "+timevalue+" "+option1);
    if (!datevalue.equals(" ") && !timevalue.equals(" ") && !option1.equals("")) {
    if (GenericMethods.isConnected(getApplicationContext())) {
        if (GenericMethods.isOnline()) {

            if(status>0) {

                Toast.makeText(reschedule.this, "Reschedule Successfull!!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(reschedule.this, rescheduledetails.class);
                startService(i);
                finish();
            }
        } else {
            Toast.makeText(reschedule.this, " Reschedule Data Saved Offline,App is waiting for active network connection", Toast.LENGTH_LONG).show();

        }
    } else {
        Toast.makeText(reschedule.this, "Reschedule Saved Offline!!!!", Toast.LENGTH_SHORT).show();
        finish();
    }
}else {
    Toast.makeText(reschedule.this, "Fill Required Details and Update", Toast.LENGTH_SHORT).show();
}
} else {
    Toast.makeText(reschedule.this, "Fill Required Details and Update", Toast.LENGTH_SHORT).show();

}


            }


        });


    }


    @Override
    public void onBackPressed() {


        finish();
    }


}
