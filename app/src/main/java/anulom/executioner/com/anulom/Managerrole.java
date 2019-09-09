package anulom.executioner.com.anulom;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import anulom.executioner.com.anulom.database.DBManager;
import anulom.executioner.com.anulom.database.DBOperation;
import anulom.executioner.com.anulom.services.getexecutionerlist;



public class Managerrole extends AppCompatActivity {

    Toolbar toolbar;
    Spinner txt1;
    TextView t1;

    public String umail = Login.umailid;
    GenericMethods responseslot;
    String startnewdate, startnewtime1, startnewtime2,currentdate;

    String ID1;
    String jsonStr3 = "";
    private JSONArray ja12 = null;
    private String EXECUTIONERURL = "";
    String email = "", value;
    DBOperation db;
    Button b1, b2,b3,b4,b5,b6,b7,b8,b9,b10,b11,b12,b13,b14,b15,b16,b17,b18;
    LinearLayout L1,L2;
    public static ProgressDialog pDialog;
    private String username2 = "";
    List<String> lables = new ArrayList<>();
    List<String> password = new ArrayList<>();

    public void onCreate(Bundle SavedInstanceState) {

        super.onCreate(SavedInstanceState);
        setContentView(R.layout.manager);
        db = new DBOperation(getApplicationContext());
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Manager Role");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        t1 = findViewById(R.id.textmanager1);
        L1= findViewById(R.id.linearLayout);
        L2= findViewById(R.id.linearLayout1);
        b3= findViewById(R.id.button01);
        b4= findViewById(R.id.button02);
        b5= findViewById(R.id.button03);
        b6= findViewById(R.id.button04);
        b7= findViewById(R.id.button05);
        b8= findViewById(R.id.button06);
        b9= findViewById(R.id.button07);
        b10= findViewById(R.id.button08);
        b11= findViewById(R.id.button01s2);
        b12= findViewById(R.id.button02s4);
        b13= findViewById(R.id.button03s3);
        b14= findViewById(R.id.button04s5);
        b15= findViewById(R.id.button05s1);
        b16= findViewById(R.id.button06s6);
        b17= findViewById(R.id.button07s7);
        b18= findViewById(R.id.button08s8);
        txt1 = findViewById(R.id.autocomplete);
        b1 = findViewById(R.id.button4);
        b2 = findViewById(R.id.button5);



        SharedPreferences usermail = PreferenceManager.getDefaultSharedPreferences(this);

        username2 = usermail.getString("username", "");
        String password2 = usermail.getString("password", "");

        lables.add("");
        lables.add("pravindevkate.anulom@gmail.com");
        lables.add("sachin.anulom@gmail.com");
        lables.add("sagar.anulom@gmail.com");
        lables.add("amol.thorat.anulom@gmail.com");
        lables.add("subhash.anulom@gmail.com");
        lables.add("yogesh.anulom@gmail.com");
        lables.add("vaibhavnikam7489@gmail.com");
        lables.add("ajitanulom@gmail.com");
        lables.add("bhoslenitin007@gmail.com");
        lables.add("khanderaoakshay02@gmail.com");
        lables.add("pravin.anulom@gmail.com");
        lables.add("darpan.anulom@gmail.com");
        lables.add("jdpingle08@gmail.com");
        lables.add("jayram.anulom@gmail.com");
        lables.add("gopale.anulom@gmail.com");
        lables.add("mauli.anulom@gmail.com");
        lables.add("sunil.anulom@gmail.com");
        lables.add("ashok.anulom@gmail.com");
        lables.add("ramdas.anulom@gmail.com");
        lables.add("dinesh.anulom@gmail.com");
        lables.add("megha.anulom@gmail.com");
        lables.add("abhijit.anulom@gmail.com");
        lables.add("divyasree.anulom@gmail.com");

        password.add("");
        password.add("anulom123");
        password.add("sachin@123");
        password.add("sagar112");
        password.add("rohits143");
        password.add("anu@1234");

        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<>(Managerrole.this, R.layout.witnesslay, lables);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        txt1.setAdapter(dataAdapter3);
        txt1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                email = parent.getItemAtPosition(position).toString();
                GenericMethods.manageremail=email;
                System.out.println("email:" + email);
                System.out.println("email1:" + GenericMethods.email);

                if (email.length() > 0) {
                    value="all-select";
                    SQLiteDatabase db3 = db.getWritableDatabase();
                    ContentValues values3 = new ContentValues();
                    values3.put(DBManager.TableInfo.KEYID, ID1);
                    values3.put(DBManager.TableInfo.executioneremail, email);
                    values3.put(DBManager.TableInfo.execpassword, "password123");
                    values3.put(DBManager.TableInfo.KEY_LOGIN_USER, username2);
                    String condition3 = DBManager.TableInfo.executioneremail + " =?";
                    Cursor cursor3 = db3.query(DBManager.TableInfo.TABLE_EXECUTIONER, null, condition3, new String[]{email}, null, null, null);
                    long status3 = db3.insert(DBManager.TableInfo.TABLE_EXECUTIONER, null, values3);
                    System.out.println("insert:" + status3+" "+db.getexecutionerlist(db).size());
                    cursor3.close();
                    db3.close();
                    Intent i = new Intent(Managerrole.this, getexecutionerlist.class);
                    i.putExtra("email", email);
                    i.putExtra("value",value);
                    i.putExtra("password", "password123");
                    startService(i);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value = "all";
                L1.setVisibility(View.VISIBLE);
for(int i=0;i<db.getexecutionerlist(db).size();i++){

    //email=db.getexecutionerlist(db).get(i);
    EXECUTIONERURL = GenericMethods.MAIN_URL + "/api/v3/get_exec_data?get_auth_token=DOtUBMhv5pk51tl0D37uBcezq85cXNN7hZQ7&exec_email=" + email + "&password=" + "password123" + "&version=17.4.2";

    try {
        jsonStr3 = responseslot.doGetRequest(EXECUTIONERURL);

    } catch (IOException e) {
        e.printStackTrace();
    }
    Log.d("Response from slot: ", "> " + EXECUTIONERURL);

    SQLiteDatabase base = db.getWritableDatabase();
    base.delete(DBManager.TableInfo.TABLE_MANAGER, null, null);
    SQLiteDatabase base1 = db.getWritableDatabase();
    base1.delete(DBManager.TableInfo.TABLE_MANAGER_APPOINTMENT, null, null);

    if (jsonStr3 != null)

        try {


            JSONObject jsonObj1 = new JSONObject(jsonStr3);
            ja12 = jsonObj1.getJSONArray(GenericMethods.TAG_NAME);

            for (int n = 0; n < ja12.length(); n++) {

                JSONObject c = ja12.getJSONObject(n);
                String rtoken1 = c.getString(GenericMethods.TAG_RTOKEN);
                String rkey1 = c.getString(GenericMethods.TAG_REPORT_KEY);
                String uname1 = c.getString(GenericMethods.TAG_UNAME);
                String email1 = c.getString(GenericMethods.TAG_UEMAIL);
                String contact_number1 = c.getString(GenericMethods.TAG_UCONTACT);
                String paddress1 = c.getString(GenericMethods.TAG_PADDRESS);
                String oname1 = c.getString(GenericMethods.TAG_ONAME);
                String ocontact1 = c.getString(GenericMethods.TAG_OCONTACT);
                String oemail1 = c.getString(GenericMethods.TAG_OEMAIL);
                String oaddress1 = c.getString(GenericMethods.TAG_OADDRESS);
                String tname1 = c.getString(GenericMethods.TAG_TNAME);
                String tcontact1 = c.getString(GenericMethods.TAG_TCONTACT);
                String temail1 = c.getString(GenericMethods.TAG_TEMAIL);
                String taddress1 = c.getString(GenericMethods.TAG_TADDRESS);
                String status1 = c.getString(GenericMethods.TAG_STATUS);
                String appdate = c.getString(GenericMethods.TAG_APPDATE);
                String biocomp = c.getString(GenericMethods.TAG_BIO_COMP);
                String appdate1 = c.getString(GenericMethods.TAG_APPDATE1);
                String biocomp1 = c.getString(GenericMethods.TAG_BIO_COMP1);
                String regfromcomp = c.getString(GenericMethods.TAG_REG_FROM_COMP);
                String witness = c.getString(GenericMethods.TAG_WITNESS);
                String shipadd = c.getString(GenericMethods.TAG_SHIP_ADDRESS);
                String shipdiffadd = c.getString(GenericMethods.TAG_SHIP_DIFF_ADDRESS);
                String strlatitude, strlongitude;
                if (c.has(GenericMethods.TAG_CUSTOMER_LAT)) {
                    strlatitude = c.getString(GenericMethods.TAG_CUSTOMER_LAT);
                } else strlatitude = "0";
                if (c.has(GenericMethods.TAG_CUSTOMER_LONG)) {
                    strlongitude = c.getString(GenericMethods.TAG_CUSTOMER_LONG);
                } else strlongitude = "0";
                String docid = c.getString(GenericMethods.TAG_DOCID);

                String appid = c.getString(GenericMethods.TAG_APPID);
                String start1 = c.getString(GenericMethods.TAG_START1);
                String start2 = c.getString(GenericMethods.TAG_START2);
                String appaddress = c.getString(GenericMethods.TAG_APPADDRESS);
                String exeid = c.getString(GenericMethods.TAG_EXECUTIONER_ID);
                String appfor = c.getString(GenericMethods.TAG_APP_FOR);
                String apptype = c.getString(GenericMethods.TAG_APPTYPE);
                String post_status = c.getString(GenericMethods.TAG_POST_STATUS);

                String landmark = c.getString(GenericMethods.TAG_LANDMARK);
                String contact_person = c.getString(GenericMethods.TAG_CONTACTPERSON);

                String distance1 = c.getString(GenericMethods.TAG_CUSTOMER_DISTANCE);
                String amount1 = c.getString(GenericMethods.TAG_CUSTOMER_AMOUNT);
                String transporttype1 = c.getString(GenericMethods.TAG_CUSTOMER_TRANS_TYPE);
                String biostatus = c.getString(GenericMethods.TAG_DOC_BIOMETRIC_STATUS);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat output1 = new SimpleDateFormat("HH:mm");
                SimpleDateFormat output2 = new SimpleDateFormat("HH:mm");

                java.util.Date date, time1, time2;
                try {
                    date = sdf.parse(start1);
                    time1 = sdf.parse(start1);
                    time2 = sdf.parse(start2);
                    startnewdate = output.format(date);
                    System.out.println(startnewdate);
                    startnewtime1 = output1.format(time1);
                    startnewtime2 = output2.format(time2);

                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                System.out.println("email:" + exeid + rkey1);

                SQLiteDatabase db3 = db.getWritableDatabase();
                ContentValues values3 = new ContentValues();

                values3.put(DBManager.TableInfo.KEYID, ID1);
                values3.put(DBManager.TableInfo.ReportKey, rkey1);
                values3.put(DBManager.TableInfo.ReportToken, rtoken1);
                values3.put(DBManager.TableInfo.Umail, email1);
                values3.put(DBManager.TableInfo.Uname, uname1);
                values3.put(DBManager.TableInfo.Ucontact, contact_number1);
                values3.put(DBManager.TableInfo.PropertyAddress, paddress1);
                values3.put(DBManager.TableInfo.OwnerName, oname1);
                values3.put(DBManager.TableInfo.OwnerContact, ocontact1);
                values3.put(DBManager.TableInfo.OwnerEmail, oemail1);
                values3.put(DBManager.TableInfo.OwnerAddress, oaddress1);
                values3.put(DBManager.TableInfo.TenantName, tname1);
                values3.put(DBManager.TableInfo.TenantContact, tcontact1);
                values3.put(DBManager.TableInfo.TenantEmail, temail1);
                values3.put(DBManager.TableInfo.TenantAddress, taddress1);
                values3.put(DBManager.TableInfo.Status, status1);
                values3.put(DBManager.TableInfo.UserEmail, username2);
                values3.put(DBManager.TableInfo.BiometricComp, biocomp);
                values3.put(DBManager.TableInfo.Appointment_Date, appdate);
                values3.put(DBManager.TableInfo.BiometricComp1, biocomp1);
                values3.put(DBManager.TableInfo.Appointment_Date1, appdate1);
                values3.put(DBManager.TableInfo.Witness, witness);
                values3.put(DBManager.TableInfo.Ship_Address, shipadd);
                values3.put(DBManager.TableInfo.Reg_From_Comp,regfromcomp);
                values3.put(DBManager.TableInfo.SYNCSTATUS,"SYNC");
                values3.put(DBManager.TableInfo.Ship_Diff_Address, shipdiffadd);
                values3.put(DBManager.TableInfo.APPTYPE, apptype);
                values3.put(DBManager.TableInfo.Doc_bio_status, biostatus);
                values3.put(DBManager.TableInfo.AppointmentId, appid);
                values3.put(DBManager.TableInfo.DocumentId, docid);


                String condition3 = DBManager.TableInfo.DocumentId + " =?";
                Cursor cursor3 = db3.query(DBManager.TableInfo.TABLE_MANAGER, null, condition3, new String[]{docid}, null, null, null);
                long status3 = db3.insert(DBManager.TableInfo.TABLE_MANAGER, null, values3);
                System.out.println("insert doc" + status3 + docid);
                cursor3.close();
                db3.close();


                SQLiteDatabase db4 = db.getWritableDatabase();
                ContentValues values4 = new ContentValues();
                values4.put(DBManager.TableInfo.KEYID,ID1);
                values4.put(DBManager.TableInfo.StartDate,startnewdate);
                values4.put(DBManager.TableInfo.StartTime1, startnewtime1);
                values4.put(DBManager.TableInfo.StartTime2, startnewtime2);
                values4.put(DBManager.TableInfo.DocumentId, docid);
                values4.put(DBManager.TableInfo.AppointmentAddress, appaddress);
                values4.put(DBManager.TableInfo.AppointmentId, appid);
                values4.put(DBManager.TableInfo.contactperson, contact_person);
                values4.put(DBManager.TableInfo.landmark, landmark);
                values4.put(DBManager.TableInfo.Executioner_id, exeid);
                values4.put(DBManager.TableInfo.DISTANCE, distance1);
                values4.put(DBManager.TableInfo.AMOUNT, amount1);
                values4.put(DBManager.TableInfo.TRANSPORTTYPE, transporttype1);
                values4.put(DBManager.TableInfo.SYNCSTATUSREPORT,"SYNC");
                values4.put(DBManager.TableInfo.att_status,"" );
                values4.put(DBManager.TableInfo.post_status, post_status);
                values4.put(DBManager.TableInfo.task_name, taddress1);
                values4.put(DBManager.TableInfo.LATITUDE, strlatitude);
                values4.put(DBManager.TableInfo.LONGITUDE, strlongitude);
                values4.put(DBManager.TableInfo.notification,"false" );
                values4.put(DBManager.TableInfo.AppFor, appfor);



                String condition4 = DBManager.TableInfo.AppointmentId + " =?";
                Cursor cursor4 = db4.query(DBManager.TableInfo.TABLE_MANAGER_APPOINTMENT, null, condition4, new String[]{appid}, null, null, null);
                long status4 = db4.insert(DBManager.TableInfo.TABLE_MANAGER_APPOINTMENT, null, values4);
                System.out.println("insert doc" + status4 + appid);
                cursor4.close();
                db4.close();


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
}


            }
        });

//
//            b3.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    value="A1";
//                    new manual_search3().execute();
//                }
//            });
//
//        b11.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                value="A";
//                new manual_search2().execute();
//            }
//        });
//
//
//        b4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                value="NOB1";
//                new manual_search4().execute();
//            }
//        });
//
//        b12.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                value="NOB";
//                new manual_search5().execute();
//            }
//        });
//
//        b5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                value="NAM1";
//                new manual_search6().execute();
//            }
//        });
//
//        b13.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                value="NAM";
//                new manual_search7().execute();
//            }
//        });
//
//        b7.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                value="NAP1";
//                new manual_search8().execute();
//            }
//        });
//
//        b15.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                value="NAP";
//                new manual_search9().execute();
//            }
//        });
//        b6.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                value="GSH1";
//                new manual_search10().execute();
//            }
//        });
//
//        b14.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                value="GSH";
//                new manual_search11().execute();
//            }
//        });
//
//        b9.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                value="LH1";
//                new manual_search12().execute();
//            }
//        });
//
//        b17.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                value="LH";
//                new manual_search13().execute();
//            }
//        });
//
//        b8.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                value="LD1";
//                new manual_search14().execute();
//            }
//        });
//
//        b16.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                value="LD";
//                new manual_search15().execute();
//            }
//        });
//
//        b10.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                value="MGS1";
//                new manual_search16().execute();
//            }
//        });
//
//        b18.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                value="MGS";
//                new manual_search17().execute();
//            }
//        });
//
//        b1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                value = "select";
//                L2.setVisibility(View.VISIBLE);
//
//            }
//        });
//
//    }
//
//
//    class manual_search extends AsyncTask<String, Integer, Double> {
//
//
//        @Override
//        protected void onPreExecute() {
//            // TODO Auto-generated method stub
//            super.onPreExecute();
//
//
//        }
//
//        @Override
//        protected Double doInBackground(String... params) {
//
//
//            String rkey1 = "";
//            db = new DBOperation(Managerrole.this);
//
//
//            for (int i = 0; i < db.getallexectodayappoint(db).size(); i++) {
////
//                if (db.getallexectodayappoint(db).get(i).get("rkey").substring(0, 1).toLowerCase().equals("a")) {
//                    rkey1 = "a" + db.getallexectodayappoint(db).get(i).get("rkey").substring(2);
//                    if (rkey1.equals(GenericMethods.rkeyfn)) {
//
//                    }
//                } else if (db.getallexectodayappoint(db).get(i).get("rkey").substring(0, 1).toLowerCase().equals("n")) {
//
//                    rkey1 = db.getallexectodayappoint(db).get(i).get("rkey").substring(0, 3).toLowerCase() + db.getallexectodayappoint(db).get(i).get("rkey").substring(4);
//                    if (rkey1.equals(GenericMethods.rkeyfn)) {
//
//                    }
//                }
//                else if (db.getallexectodayappoint(db).get(i).get("rkey").substring(0, 1).toLowerCase().equals("g")) {
//
//                    rkey1 = db.getallexectodayappoint(db).get(i).get("rkey").substring(0, 3).toLowerCase() + db.getallexectodayappoint(db).get(i).get("rkey").substring(4);
//                    if (rkey1.equals(GenericMethods.rkeyfn)) {
//
//                    }
//                }
//                else if (db.getallexectodayappoint(db).get(i).get("rkey").substring(0, 1).toLowerCase().equals("m")) {
//
//                    rkey1 = db.getallexectodayappoint(db).get(i).get("rkey").substring(0, 3).toLowerCase() + db.getallexectodayappoint(db).get(i).get("rkey").substring(4);
//                    if (rkey1.equals(GenericMethods.rkeyfn)) {
//
//                    }
//                }
//                else if (db.getallexectodayappoint(db).get(i).get("rkey").substring(0, 1).toLowerCase().equals("l")) {
//
//                    rkey1 = db.getallexectodayappoint(db).get(i).get("rkey").substring(0, 2).toLowerCase() + db.getallexectodayappoint(db).get(i).get("rkey").substring(3);
//                    if (rkey1.equals(GenericMethods.rkeyfn)) {
//
//                    }
//                }
//
//            }
//            Intent i = new Intent(Managerrole.this, executionerActivity.class);
//            i.putExtra("value", value);
//            startActivity(i);
//
//            return null;
//
//        }
//    }
//        class manual_search1 extends AsyncTask<String, Integer, Double> {
//            @Override
//            protected void onPreExecute() {
//                // TODO Auto-generated method stub
//                super.onPreExecute();
//
//
//            }
//
//            @Override
//            protected Double doInBackground(String... params) {
//
//
//                String rkey1 = "";
//                db = new DBOperation(Managerrole.this);
//
//                for (int i = 0; i < db.getselectexectodayappoint(db,email).size(); i++) {
//
//                    if (db.getselectexectodayappoint(db,email).get(i).get("rkey").substring(0, 1).toLowerCase().equals("a")) {
//                        rkey1 = "a" + db.getselectexectodayappoint(db,email).get(i).get("rkey").substring(2);
//                        if (rkey1.equals(GenericMethods.rkeyfn)) {
//
//                        }
//                    } else if (db.getselectexectodayappoint(db,email).get(i).get("rkey").substring(0, 1).toLowerCase().equals("n")) {
//
//                        rkey1 = db.getselectexectodayappoint(db,email).get(i).get("rkey").substring(0, 3).toLowerCase() + db.getselectexectodayappoint(db,email).get(i).get("rkey").substring(4);
//                        if (rkey1.equals(GenericMethods.rkeyfn)) {
//
//                        }
//                    }
//                    else if (db.getselectexectodayappoint(db,email).get(i).get("rkey").substring(0, 1).toLowerCase().equals("g")) {
//
//                        rkey1 = db.getselectexectodayappoint(db,email).get(i).get("rkey").substring(0, 3).toLowerCase() + db.getselectexectodayappoint(db,email).get(i).get("rkey").substring(4);
//                        if (rkey1.equals(GenericMethods.rkeyfn)) {
//
//                        }
//                    }
//                    else if (db.getselectexectodayappoint(db,email).get(i).get("rkey").substring(0, 1).toLowerCase().equals("m")) {
//
//                        rkey1 = db.getselectexectodayappoint(db,email).get(i).get("rkey").substring(0, 3).toLowerCase() + db.getselectexectodayappoint(db,email).get(i).get("rkey").substring(4);
//                        if (rkey1.equals(GenericMethods.rkeyfn)) {
//
//                        }
//                    }
//                    else if (db.getselectexectodayappoint(db,email).get(i).get("rkey").substring(0, 1).toLowerCase().equals("l")) {
//
//                        rkey1 = db.getselectexectodayappoint(db,email).get(i).get("rkey").substring(0, 2).toLowerCase() + db.getselectexectodayappoint(db,email).get(i).get("rkey").substring(3);
//                        if (rkey1.equals(GenericMethods.rkeyfn)) {
//
//                        }
//                    }
//                }
//
//
//
//                Intent i = new Intent(Managerrole.this, executionerActivity.class);
//                i.putExtra("value", value);
//                startActivity(i);
//
//                return null;
//
//
//            }
//
//        }
//    class manual_search2 extends AsyncTask<String, Integer, Double> {
//        @Override
//        protected void onPreExecute() {
//            // TODO Auto-generated method stub
//            super.onPreExecute();
//
//
//        }
//
//        @Override
//        protected Double doInBackground(String... params) {
//
//
//            String rkey1 = "";
//            db = new DBOperation(Managerrole.this);
//
//            for (int i = 0; i < db.getselectAdetails(db,email).size(); i++) {
//
//                if (db.getselectAdetails(db,email).get(i).get("rkey").substring(0, 1).toLowerCase().equals("a")) {
//                    rkey1 = "a" + db.getselectAdetails(db,email).get(i).get("rkey").substring(2);
//                    if (rkey1.equals(GenericMethods.rkeyfn)) {
//
//                    }
//
//                }
//            }
//
//
//
//            Intent i = new Intent(Managerrole.this, executionerActivity.class);
//            i.putExtra("value", value);
//            startActivity(i);
//
//            return null;
//
//
//        }
//
//    }
//
//    class manual_search3 extends AsyncTask<String, Integer, Double> {
//        @Override
//        protected void onPreExecute() {
//            // TODO Auto-generated method stub
//            super.onPreExecute();
//
//
//        }
//
//        @Override
//        protected Double doInBackground(String... params) {
//
//
//            String rkey1 = "";
//            db = new DBOperation(Managerrole.this);
//
//            for (int i = 0; i < db.getAdetails(db).size(); i++) {
//
//                if (db.getAdetails(db).get(i).get("rkey").substring(0, 1).toLowerCase().equals("a")) {
//                    rkey1 = "a" + db.getAdetails(db).get(i).get("rkey").substring(2);
//                    if (rkey1.equals(GenericMethods.rkeyfn)) {
//
//                    }
//
//                }
//            }
//
//
//
//            Intent i = new Intent(Managerrole.this, executionerActivity.class);
//            i.putExtra("value", value);
//            startActivity(i);
//
//            return null;
//
//
//        }
//
//    }
//
//
//    class manual_search4 extends AsyncTask<String, Integer, Double> {
//        @Override
//        protected void onPreExecute() {
//            // TODO Auto-generated method stub
//            super.onPreExecute();
//
//
//        }
//
//        @Override
//        protected Double doInBackground(String... params) {
//
//
//            String rkey1 = "";
//            db = new DBOperation(Managerrole.this);
//
//            for (int i = 0; i < db.getnobdetails(db).size(); i++) {
//
//                if (db.getnobdetails(db).get(i).get("rkey").substring(0, 3).toLowerCase().equals("nob")) {
//                    rkey1 = "nob" + db.getnobdetails(db).get(i).get("rkey").substring(4);
//                    if (rkey1.equals(GenericMethods.rkeyfn)) {
//
//                    }
//
//                }
//            }
//
//
//
//            Intent i = new Intent(Managerrole.this, executionerActivity.class);
//            i.putExtra("value", value);
//            startActivity(i);
//
//            return null;
//
//
//        }
//
//    }
//
//    class manual_search5 extends AsyncTask<String, Integer, Double> {
//        @Override
//        protected void onPreExecute() {
//            // TODO Auto-generated method stub
//            super.onPreExecute();
//
//
//        }
//
//        @Override
//        protected Double doInBackground(String... params) {
//
//
//            String rkey1 = "";
//            db = new DBOperation(Managerrole.this);
//
//            for (int i = 0; i < db.getselectnobdetails(db,email).size(); i++) {
//
//                if (db.getselectnobdetails(db,email).get(i).get("rkey").substring(0, 3).toLowerCase().equals("nob")) {
//                    rkey1 = "nob" + db.getselectnobdetails(db,email).get(i).get("rkey").substring(4);
//                    if (rkey1.equals(GenericMethods.rkeyfn)) {
//
//                    }
//
//                }
//            }
//
//
//
//            Intent i = new Intent(Managerrole.this, executionerActivity.class);
//            i.putExtra("value", value);
//            startActivity(i);
//
//            return null;
//
//
//        }
//
//    }
//
//    class manual_search6 extends AsyncTask<String, Integer, Double> {
//        @Override
//        protected void onPreExecute() {
//            // TODO Auto-generated method stub
//            super.onPreExecute();
//
//
//        }
//
//        @Override
//        protected Double doInBackground(String... params) {
//
//
//            String rkey1 = "";
//            db = new DBOperation(Managerrole.this);
//
//            for (int i = 0; i < db.getNAMdetails(db).size(); i++) {
//
//                if (db.getNAMdetails(db).get(i).get("rkey").substring(0, 3).toLowerCase().equals("nam")) {
//                    rkey1 = "nam" + db.getNAMdetails(db).get(i).get("rkey").substring(4);
//                    if (rkey1.equals(GenericMethods.rkeyfn)) {
//
//                    }
//
//                }
//            }
//
//
//
//            Intent i = new Intent(Managerrole.this, executionerActivity.class);
//            i.putExtra("value", value);
//            startActivity(i);
//
//            return null;
//
//
//        }
//
//    }
//
//    class manual_search7 extends AsyncTask<String, Integer, Double> {
//        @Override
//        protected void onPreExecute() {
//            // TODO Auto-generated method stub
//            super.onPreExecute();
//
//
//        }
//
//        @Override
//        protected Double doInBackground(String... params) {
//
//
//            String rkey1 = "";
//            db = new DBOperation(Managerrole.this);
//
//            for (int i = 0; i < db.getselectNAMdetails(db,email).size(); i++) {
//
//                if (db.getselectNAMdetails(db,email).get(i).get("rkey").substring(0, 3).toLowerCase().equals("nam")) {
//                    rkey1 = "nam" + db.getselectNAMdetails(db,email).get(i).get("rkey").substring(4);
//                    if (rkey1.equals(GenericMethods.rkeyfn)) {
//
//                    }
//
//                }
//            }
//
//
//
//            Intent i = new Intent(Managerrole.this, executionerActivity.class);
//            i.putExtra("value", value);
//            startActivity(i);
//
//            return null;
//
//
//        }
//
//    }
//
//    class manual_search8 extends AsyncTask<String, Integer, Double> {
//        @Override
//        protected void onPreExecute() {
//            // TODO Auto-generated method stub
//            super.onPreExecute();
//
//
//        }
//
//        @Override
//        protected Double doInBackground(String... params) {
//
//
//            String rkey1 = "";
//            db = new DBOperation(Managerrole.this);
//
//            for (int i = 0; i < db.getNAPdetails(db).size(); i++) {
//
//                if (db.getNAPdetails(db).get(i).get("rkey").substring(0, 3).toLowerCase().equals("nap")) {
//                    rkey1 = "nap" + db.getNAPdetails(db).get(i).get("rkey").substring(4);
//                    if (rkey1.equals(GenericMethods.rkeyfn)) {
//
//                    }
//
//                }
//            }
//
//
//
//            Intent i = new Intent(Managerrole.this, executionerActivity.class);
//            i.putExtra("value", value);
//            startActivity(i);
//
//            return null;
//
//
//        }
//
//    }
//
//    class manual_search9 extends AsyncTask<String, Integer, Double> {
//        @Override
//        protected void onPreExecute() {
//            // TODO Auto-generated method stub
//            super.onPreExecute();
//
//
//        }
//
//        @Override
//        protected Double doInBackground(String... params) {
//
//
//            String rkey1 = "";
//            db = new DBOperation(Managerrole.this);
//
//            for (int i = 0; i < db.getselectNAPdetails(db,email).size(); i++) {
//
//                if (db.getselectNAPdetails(db,email).get(i).get("rkey").substring(0, 3).toLowerCase().equals("nap")) {
//                    rkey1 = "nap" + db.getselectNAPdetails(db,email).get(i).get("rkey").substring(4);
//                    if (rkey1.equals(GenericMethods.rkeyfn)) {
//
//                    }
//
//                }
//            }
//
//
//
//            Intent i = new Intent(Managerrole.this, executionerActivity.class);
//            i.putExtra("value", value);
//            startActivity(i);
//
//            return null;
//
//
//        }
//
//    }
//
//    class manual_search10 extends AsyncTask<String, Integer, Double> {
//        @Override
//        protected void onPreExecute() {
//            // TODO Auto-generated method stub
//            super.onPreExecute();
//
//
//        }
//
//        @Override
//        protected Double doInBackground(String... params) {
//
//
//            String rkey1 = "";
//            db = new DBOperation(Managerrole.this);
//
//            for (int i = 0; i < db.getGSHdetails(db).size(); i++) {
//
//                if (db.getGSHdetails(db).get(i).get("rkey").substring(0, 3).toLowerCase().equals("gsh")) {
//                    rkey1 = "gsh" + db.getGSHdetails(db).get(i).get("rkey").substring(4);
//                    if (rkey1.equals(GenericMethods.rkeyfn)) {
//
//                    }
//
//                }
//            }
//
//
//
//            Intent i = new Intent(Managerrole.this, executionerActivity.class);
//            i.putExtra("value", value);
//            startActivity(i);
//
//            return null;
//
//
//        }
//
//    }
//
//    class manual_search11 extends AsyncTask<String, Integer, Double> {
//        @Override
//        protected void onPreExecute() {
//            // TODO Auto-generated method stub
//            super.onPreExecute();
//
//
//        }
//
//        @Override
//        protected Double doInBackground(String... params) {
//
//
//            String rkey1 = "";
//            db = new DBOperation(Managerrole.this);
//
//            for (int i = 0; i < db.getselectGSHdetails(db,email).size(); i++) {
//
//                if (db.getselectGSHdetails(db,email).get(i).get("rkey").substring(0, 3).toLowerCase().equals("gsh")) {
//                    rkey1 = "gsh" + db.getselectGSHdetails(db,email).get(i).get("rkey").substring(4);
//                    if (rkey1.equals(GenericMethods.rkeyfn)) {
//
//                    }
//
//                }
//            }
//
//
//
//            Intent i = new Intent(Managerrole.this, executionerActivity.class);
//            i.putExtra("value", value);
//            startActivity(i);
//
//            return null;
//
//
//        }
//
//    }
//
//    class manual_search12 extends AsyncTask<String, Integer, Double> {
//        @Override
//        protected void onPreExecute() {
//            // TODO Auto-generated method stub
//            super.onPreExecute();
//
//
//        }
//
//        @Override
//        protected Double doInBackground(String... params) {
//
//
//            String rkey1 = "";
//            db = new DBOperation(Managerrole.this);
//
//            for (int i = 0; i < db.getLHdetails(db).size(); i++) {
//
//                if (db.getLHdetails(db).get(i).get("rkey").substring(0, 2).toLowerCase().equals("lh")) {
//                    rkey1 = "lh" + db.getLHdetails(db).get(i).get("rkey").substring(3);
//                    if (rkey1.equals(GenericMethods.rkeyfn)) {
//
//                    }
//
//                }
//            }
//
//
//
//            Intent i = new Intent(Managerrole.this, executionerActivity.class);
//            i.putExtra("value", value);
//            startActivity(i);
//
//            return null;
//
//
//        }
//
//    }
//
//    class manual_search13 extends AsyncTask<String, Integer, Double> {
//        @Override
//        protected void onPreExecute() {
//            // TODO Auto-generated method stub
//            super.onPreExecute();
//
//
//        }
//
//        @Override
//        protected Double doInBackground(String... params) {
//
//
//            String rkey1 = "";
//            db = new DBOperation(Managerrole.this);
//
//            for (int i = 0; i < db.getselectLHdetails(db,email).size(); i++) {
//
//                if (db.getselectLHdetails(db,email).get(i).get("rkey").substring(0, 2).toLowerCase().equals("lh")) {
//                    rkey1 = "lh" + db.getselectLHdetails(db,email).get(i).get("rkey").substring(3);
//                    if (rkey1.equals(GenericMethods.rkeyfn)) {
//
//                    }
//
//                }
//            }
//
//
//
//            Intent i = new Intent(Managerrole.this, executionerActivity.class);
//            i.putExtra("value", value);
//            startActivity(i);
//
//            return null;
//
//
//        }
//
//    }
//
//    class manual_search14 extends AsyncTask<String, Integer, Double> {
//        @Override
//        protected void onPreExecute() {
//            // TODO Auto-generated method stub
//            super.onPreExecute();
//
//
//        }
//
//        @Override
//        protected Double doInBackground(String... params) {
//
//
//            String rkey1 = "";
//            db = new DBOperation(Managerrole.this);
//
//            for (int i = 0; i < db.getLDdetails(db).size(); i++) {
//
//                if (db.getLDdetails(db).get(i).get("rkey").substring(0, 2).toLowerCase().equals("ld")) {
//                    rkey1 = "ld" + db.getLDdetails(db).get(i).get("rkey").substring(3);
//                    if (rkey1.equals(GenericMethods.rkeyfn)) {
//
//                    }
//
//                }
//            }
//
//
//
//            Intent i = new Intent(Managerrole.this, executionerActivity.class);
//            i.putExtra("value", value);
//            startActivity(i);
//
//            return null;
//
//
//        }
//
//    }
//
//    class manual_search15 extends AsyncTask<String, Integer, Double> {
//        @Override
//        protected void onPreExecute() {
//            // TODO Auto-generated method stub
//            super.onPreExecute();
//
//
//        }
//
//        @Override
//        protected Double doInBackground(String... params) {
//
//
//            String rkey1 = "";
//            db = new DBOperation(Managerrole.this);
//
//            for (int i = 0; i < db.getselectLDdetails(db,email).size(); i++) {
//
//                if (db.getselectLDdetails(db,email).get(i).get("rkey").substring(0, 2).toLowerCase().equals("ld")) {
//                    rkey1 = "ld" + db.getselectLDdetails(db,email).get(i).get("rkey").substring(3);
//                    if (rkey1.equals(GenericMethods.rkeyfn)) {
//
//                    }
//
//                }
//            }
//
//
//
//            Intent i = new Intent(Managerrole.this, executionerActivity.class);
//            i.putExtra("value", value);
//            startActivity(i);
//
//            return null;
//
//
//        }
//
//    }
//
//    class manual_search16 extends AsyncTask<String, Integer, Double> {
//        @Override
//        protected void onPreExecute() {
//            // TODO Auto-generated method stub
//            super.onPreExecute();
//
//
//        }
//
//        @Override
//        protected Double doInBackground(String... params) {
//
//
//            String rkey1 = "";
//            db = new DBOperation(Managerrole.this);
//
//            for (int i = 0; i < db.getMGSdetails(db).size(); i++) {
//
//                if (db.getMGSdetails(db).get(i).get("rkey").substring(0, 3).toLowerCase().equals("mgs")) {
//                    rkey1 = "mgs" + db.getMGSdetails(db).get(i).get("rkey").substring(4);
//                    if (rkey1.equals(GenericMethods.rkeyfn)) {
//
//                    }
//
//                }
//            }
//
//
//
//            Intent i = new Intent(Managerrole.this, executionerActivity.class);
//            i.putExtra("value", value);
//            startActivity(i);
//
//            return null;
//
//
//        }
//
//    }
//
//    class manual_search17 extends AsyncTask<String, Integer, Double> {
//        @Override
//        protected void onPreExecute() {
//            // TODO Auto-generated method stub
//            super.onPreExecute();
//
//
//        }
//
//        @Override
//        protected Double doInBackground(String... params) {
//
//
//            String rkey1 = "";
//            db = new DBOperation(Managerrole.this);
//
//            for (int i = 0; i < db.getselectMGSdetails(db,email).size(); i++) {
//
//                if (db.getselectMGSdetails(db,email).get(i).get("rkey").substring(0, 3).toLowerCase().equals("mgs")) {
//                    rkey1 = "mgs" + db.getselectMGSdetails(db,email).get(i).get("rkey").substring(4);
//                    if (rkey1.equals(GenericMethods.rkeyfn)) {
//
//                    }
//
//                }
//            }
//
//
//
//            Intent i = new Intent(Managerrole.this, executionerActivity.class);
//            i.putExtra("value", value);
//            startActivity(i);
//
//            return null;
//
//
//        }

    }
        }







