package anulom.executioner.com.anulom;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import anulom.executioner.com.anulom.database.DBOperation;
import anulom.executioner.com.anulom.services.Marriagecommentservice;
import anulom.executioner.com.anulom.services.Salescommentservice;
import anulom.executioner.com.anulom.services.SendCommentService;


public class MyComment extends AppCompatActivity implements OnItemSelectedListener {

    Spinner spinner, spinner_remimnder, spinnertype, spinnerarea;
    DBOperation db;
    AutoCompleteTextView complete;
    EditText etcomment, edt_on_date;
    String docid = "", owner = "",ownerselection="", comment = "", reminder = "", value = "", type = "", area = "";
    Button submit, btn_date;
    CheckBox c1, c2;
    TextView t1, t2;
    String isdone = "0",contact;

    String valuefromspinner="";

    String username = Login.umailid;
    private String current_Date = "";
    private Calendar myCalendar;
    SharedPreferences pref;
    private String commentID = "1011";
    private String reportkey = "";
    List<String> lables = new ArrayList<>();

    DatePickerDialog.OnDateSetListener date1;
    private String reminderdate = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mycomment);


        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("Owner Comment");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        spinner = findViewById(R.id.spinner);
        spinner_remimnder = findViewById(R.id.spinner_remimnder);
        spinnertype = findViewById(R.id.spinner_type);
        spinnerarea = findViewById(R.id.spinner_area);
        c1 = findViewById(R.id.chck1);
        c2 = findViewById(R.id.chck2);
        t1 = findViewById(R.id.tvcommenttype);
        t2 = findViewById(R.id.tvcommentarea);

        etcomment = findViewById(R.id.etcomment);
        submit = findViewById(R.id.btnsubmit);


        TextView requestno = findViewById(R.id.text_requestno);

        btn_date = findViewById(R.id.btn_date);
        edt_on_date = findViewById(R.id.edt_on_date);
        myCalendar = Calendar.getInstance();
        complete = findViewById(R.id.autocomplete);
        docid = getIntent().getStringExtra("DocumentId");
        String position = getIntent().getStringExtra("Position");
        pref = this.getSharedPreferences("MyPref", 0); // 0 - for private mode
        value = pref.getString("value", "");
        contact=pref.getString("cust_contact_no","");
        System.out.println("contactno"+contact);
        reportkey = getIntent().getStringExtra("reportkey");
        String rpotkey = "Request No: " + reportkey;
        requestno.setText(rpotkey);

        spinner.setOnItemSelectedListener(this);
        spinner_remimnder.setOnItemSelectedListener(this);
        spinnertype.setOnItemSelectedListener(this);
        spinnerarea.setOnItemSelectedListener(this);


        loadSpinnerData();
        loadspinnerRemimnderData();
        loadspinnerareadata();
        loadspinnertypedata();

        c1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (c1.isChecked()) {
                    c2.setChecked(false);
                    t1.setVisibility(View.VISIBLE);
                    spinnertype.setVisibility(View.VISIBLE);
                } else {
                    t1.setVisibility(View.GONE);
                    spinnertype.setVisibility(View.GONE);
                }
            }
        });

        c2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (c2.isChecked()) {
                    c1.setChecked(false);
                    t2.setVisibility(View.VISIBLE);
                    spinnerarea.setVisibility(View.VISIBLE);
                } else {
                    t2.setVisibility(View.GONE);
                    spinnerarea.setVisibility(View.GONE);
                }
            }
        });

        btn_date.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                date1 = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String myFormat = "dd/MM/yyyy"; //In which you need put here
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                        edt_on_date.setText(sdf.format(myCalendar.getTime()));

                    }

                };
                new DatePickerDialog(MyComment.this, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        ArrayAdapter myAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, lables);
        complete.setAdapter(myAdapter);
        ownerselection=complete.getText().toString();

    }


    /**
     * Function to load the spinner data from SQLite database
     */
    private void loadSpinnerData() {
        DBOperation db = new DBOperation(getApplicationContext());

        lables = db.getAllLabels();


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, R.layout.spinnertextdisplay, lables);

        dataAdapter.setDropDownViewResource(R.layout.spinnertextdisplay);


        spinner.setAdapter(dataAdapter);
    }

    private void loadspinnerRemimnderData() {


//         Spinner Drop down elements
        List<String> spinnerItem = new ArrayList<>();
        spinnerItem.add("");
        spinnerItem.add("Morning");
        spinnerItem.add("Afternoon");
        spinnerItem.add("Evening");
        // Creating SearchItemAdapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, R.layout.spinnertextdisplay, spinnerItem);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.spinnertextdisplay);

        // attaching data SearchItemAdapter to spinner
        spinner_remimnder.setAdapter(dataAdapter);
    }

    private void loadspinnertypedata() {


//         Spinner Drop down elements
        List<String> spinnerItem = new ArrayList<>();
        spinnerItem.add("");
        spinnerItem.add("Appointments-Missed Appointments");
        spinnerItem.add("Appointments-Witness Pending");
        spinnerItem.add("Appointments-Status Not Updated");
        spinnerItem.add("Draft");
        spinnerItem.add("Shipping");
        spinnerItem.add("Tele-Support");
        spinnerItem.add("E-mail Support");
        spinnerItem.add("Remote");
        spinnerItem.add("Govt issues");
        spinnerItem.add("Payment");
        spinnerItem.add("System issues");
        spinnerItem.add("Others");
        // Creating SearchItemAdapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, R.layout.spinnertextdisplay, spinnerItem);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.spinnertextdisplay);

        // attaching data SearchItemAdapter to spinner
        spinnertype.setAdapter(dataAdapter);
    }

    private void loadspinnerareadata() {


//         Spinner Drop down elements
        List<String> spinnerItem = new ArrayList<>();
        spinnerItem.add("");
        spinnerItem.add("Appointments");
        spinnerItem.add("Draft");
        spinnerItem.add("Shipping");
        spinnerItem.add("Remote");
        spinnerItem.add("Payment");
        spinnerItem.add("Data Collection");
        spinnerItem.add("Others");

        // Creating SearchItemAdapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, R.layout.spinnertextdisplay, spinnerItem);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.spinnertextdisplay);

        // attaching data SearchItemAdapter to spinner
        spinnerarea.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()) {
            case R.id.spinner:
                owner = parent.getItemAtPosition(position).toString();
                ownerselection=owner;
                break;
            case R.id.spinner_remimnder:
                reminder = parent.getItemAtPosition(position).toString();
                break;
            case R.id.spinner_type:
                valuefromspinner="complaint";
                area = parent.getItemAtPosition(position).toString();
                break;

            case R.id.spinner_area:
                valuefromspinner="service_request";
                area = parent.getItemAtPosition(position).toString();
                break;

        }

        update();

    }

    private void update() {
        // TODO Auto-generated method stub
        submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                comment = etcomment.getText().toString();
                String edittextDate = edt_on_date.getText().toString();
                String remindertype = "";
long status=0;
                if (comment.equals("")) {
                    Toast.makeText(MyComment.this, "Please enter comment", Toast.LENGTH_LONG).show();
                } else {

                    current_Date = GenericMethods.getCurrentDate();

                    if (reminder.equals("Morning")) {
                        remindertype = "09:00";
                    }
                    if (reminder.equals("Afternoon")) {
                        remindertype = "12:00";
                    }
                    if (reminder.equals("Evening")) {
                        remindertype = "16:00";
                    }
                    if (!edittextDate.equals("")) {

                        reminderdate = edittextDate + " " + remindertype;
                    }

                    if (valuefromspinner.equals("complaint")) {
                        type = "Complaint";
                    }
                    if (valuefromspinner.equals("service_request")) {
                        type = "Service_request";
                    }

                    DBOperation db = new DBOperation(MyComment.this);
                    String commentID = randomInteger(0, 999999);

                    while (db.checkCommentID(db, commentID)) {
                        commentID = randomInteger(0, 999999);
                    }


                    if (owner.length() > 0 && complete.getText().toString().length() > 0) {
                        Toast.makeText(MyComment.this, "Either Choose Owner ID or Type ID,Not Both Pls", Toast.LENGTH_LONG).show();

                    } else if (!owner.equals("")) {
                        status = db.InsertRecord3(db, reportkey, comment, username, owner, commentID, isdone, current_Date, GenericMethods.AsyncStatus, reminderdate, value, type, area, contact);
                    } else if (!complete.getText().toString().equals("")) {
                        status = db.InsertRecord3(db, reportkey, comment, username, complete.getText().toString(), commentID, isdone, current_Date, GenericMethods.AsyncStatus, reminderdate, value, type, area, contact);

                    } else {
                        Toast.makeText(MyComment.this, "Enter the Required fields", Toast.LENGTH_LONG).show();

                    }

                    if (status > 0) {
                        if (GenericMethods.isConnected(getApplicationContext())) {

                            if (GenericMethods.isOnline()) {

                                if (value.equals("Biometric")) {
                                    System.out.println("biometric");
                                    Intent intent = new Intent(MyComment.this, SendCommentService.class);
                                    startService(intent);
                                    finish();
                                } else if (value.equals("Sales")) {
                                    System.out.println("Sales");
                                    Intent intent = new Intent(MyComment.this, Salescommentservice.class);
                                    startService(intent);
                                    finish();
                                } else if (value.equals("Marriage")) {
                                    System.out.println("Marriage");
                                    Intent intent = new Intent(MyComment.this, Marriagecommentservice.class);
                                    startService(intent);
                                    finish();
                                }

                            } else {
                                Toast.makeText(MyComment.this, "Comments Data Saved Offline,App is Waiting for active network connection ", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        } else {
                            Toast.makeText(MyComment.this, "Comments saved Offline  ", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }

                }
                }


        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    public String randomInteger(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum + "";
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}