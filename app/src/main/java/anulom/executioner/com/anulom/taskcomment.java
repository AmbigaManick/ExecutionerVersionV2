package anulom.executioner.com.anulom;


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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import anulom.executioner.com.anulom.database.DBManager;
import anulom.executioner.com.anulom.database.DBOperation;

import anulom.executioner.com.anulom.services.Posttask;


import anulom.executioner.com.anulom.services.reassignpost;


import static anulom.executioner.com.anulom.database.DBManager.TableInfo.POST_TASK;

import static anulom.executioner.com.anulom.database.DBManager.TableInfo.REASSIGN;

import static anulom.executioner.com.anulom.database.DBManager.TableInfo.TABLE_TASK;



public class taskcomment extends AppCompatActivity {

    Toolbar toolbar;
    int checked1;
    RadioButton rb1, rb2, rb3, rb4;
    TextView t1, t2, t3, t5, t6, t7, t8;
    EditText e1, e2, e4;
    AutoCompleteTextView e5;
    Button b1;
    RelativeLayout lay1;
    Spinner sp1;
    DBOperation db;
    String mowner, comment = "";
    private String username2 = "";
    String task, ID2, taskid = "", docid, TAG = "", option, status = "";

    List<String> lables = new ArrayList<>();


    public void radio(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        checked1 = view.getId();
        if (checked1 == R.id.rp1) {

            status = "Completed";
            option = "1";
            t1.setVisibility(View.GONE);
            e1.setVisibility(View.GONE);
            t2.setVisibility(View.GONE);
            t3.setVisibility(View.GONE);
            e2.setVisibility(View.GONE);
            sp1.setVisibility(View.GONE);
            t5.setVisibility(View.GONE);
            e4.setVisibility(View.GONE);
            t6.setVisibility(View.GONE);
            t7.setVisibility(View.GONE);
            t8.setVisibility(View.GONE);
            e5.setVisibility(View.GONE);

        }  else if (checked1 == R.id.rp2) {
            status = "Reassign";
            t1.setVisibility(View.GONE);
            e1.setVisibility(View.GONE);
            t2.setVisibility(View.VISIBLE);
            t3.setVisibility(View.VISIBLE);
            e2.setVisibility(View.VISIBLE);
            t6.setVisibility(View.VISIBLE);
            sp1.setVisibility(View.VISIBLE);
            t7.setVisibility(View.VISIBLE);
            t8.setVisibility(View.VISIBLE);
            e5.setVisibility(View.VISIBLE);
            t5.setVisibility(View.GONE);
            e4.setVisibility(View.GONE);

        }


    }

    public void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.taskcomments);
        db = new DBOperation(getApplicationContext());
        rb1 = findViewById(R.id.rp);
        rb2 = findViewById(R.id.sd);
        rb3 = findViewById(R.id.rp2);
        lay1 = findViewById(R.id.relativelayout1);
        t1 = findViewById(R.id.textView9);
        e1 = findViewById(R.id.editText1);
        e2 = findViewById(R.id.edit);
        e4 = findViewById(R.id.edit3);
        t2 = findViewById(R.id.text1);
        t3 = findViewById(R.id.textView1);
        t5 = findViewById(R.id.text3);
        t6 = findViewById(R.id.texttype);
        t7 = findViewById(R.id.texttype1);
        t8 = findViewById(R.id.texttype2);
        e5 = findViewById(R.id.edittype);
        b1 = findViewById(R.id.btnupdate2);
        sp1 = findViewById(R.id.spinner4);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Status");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        task = getIntent().getStringExtra("Value");
        taskid = getIntent().getStringExtra("task_id");
        comment = getIntent().getStringExtra("comment");
        SharedPreferences usermail = PreferenceManager.getDefaultSharedPreferences(this);
        username2 = usermail.getString("username", "");
        String password2 = usermail.getString("password", "");

        lables = db.getAllLabels();

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, lables);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(dataAdapter2);
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mowner = parent.getItemAtPosition(position).toString();
                System.out.println("region:" + mowner);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter myAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, lables);
        e5.setAdapter(myAdapter);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((checked1 == R.id.rp1)) {

                    SQLiteDatabase db3 = db.getWritableDatabase();
                    ContentValues values3 = new ContentValues();
                    values3.put(DBManager.TableInfo.KEYID, ID2);
                    values3.put(DBManager.TableInfo.IS_DONE, option);
                    values3.put(DBManager.TableInfo.TASK_ID, taskid);
                    values3.put(DBManager.TableInfo.comm1,comment);
                    values3.put(DBManager.TableInfo.REASON, e1.getText().toString());
                    values3.put(DBManager.TableInfo.KEY_LOGIN_USER, username2);
                    String condition3 = DBManager.TableInfo.TASK_ID + " =?";
                    Cursor cursor3 = db3.query(POST_TASK, null, condition3, new String[]{DBManager.TableInfo.IS_DONE}, null, null, null);
                    long status3 = db3.insert(POST_TASK, null, values3);
                    Log.d(TAG, "DB insert : " + status3);
                    cursor3.close();
                    db3.close();

                    SQLiteDatabase sqldb = db.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put(DBManager.TableInfo.status1, status);
                    sqldb.update(TABLE_TASK, values, DBManager.TableInfo.TASK_ID + "=?", new String[]{taskid});
                    sqldb.close();


                    if (task.equals("MyTask")) {
                        GenericMethods.pendingworks = "true";
                        Intent i4 = new Intent(taskcomment.this, NextActivity.class);
                        i4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i4);
                    } else if (task.equals("Witness")) {
                        GenericMethods.pendingworks = "true";
                        Intent i4 = new Intent(taskcomment.this, NextActivity.class);

                        i4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i4);
                    } else if (task.equals("Complaint")) {
                        GenericMethods.pendingworks = "true";
                        Intent i4 = new Intent(taskcomment.this, NextActivity.class);
                        i4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i4);
                    }
                    if (GenericMethods.isConnected(getApplicationContext())) {
                        if(GenericMethods.isOnline()) {
                            Intent i3 = new Intent(taskcomment.this, Posttask.class);
                            startService(i3);
                        }else{
                            Toast.makeText(taskcomment.this, " Task Status Saved Offline,App is waiting for active network connection", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Toast.makeText(taskcomment.this, " Task Status Saved Offline!!", Toast.LENGTH_LONG).show();

                    }


//

                } else if (checked1 == R.id.rp2) {


                    if (mowner.length() > 0 && e5.getText().toString().length() > 0) {
                        Toast.makeText(taskcomment.this, "Either Choose Owner ID or Type ID,Not Both Pls", Toast.LENGTH_LONG).show();


                    } else if (mowner.length() > 0 || e5.getText().toString().length() > 0 && e2.getText().toString()!="") {
                        SQLiteDatabase db4 = db.getWritableDatabase();
                        ContentValues values4 = new ContentValues();
                        values4.put(DBManager.TableInfo.KEYID, ID2);
                        values4.put(DBManager.TableInfo.Prev_owner, username2);
                        if (mowner != "") {
                            values4.put(DBManager.TableInfo.new_owner, mowner);
                        } else if (e5.getText().toString() != "") {
                            values4.put(DBManager.TableInfo.new_owner, e5.getText().toString());
                        }
                        values4.put(DBManager.TableInfo.comm1,comment);
                        values4.put(DBManager.TableInfo.taskid1,taskid);
                        values4.put(DBManager.TableInfo.reassign_reason, e2.getText().toString());
                        values4.put(DBManager.TableInfo.KEY_LOGIN_USER, username2);
                        String condition3 = DBManager.TableInfo.new_owner + " =?";
                        Cursor cursor4 = db4.query(REASSIGN, null, condition3, new String[]{DBManager.TableInfo.Prev_owner}, null, null, null);
                        long status3 = db4.insert(REASSIGN, null, values4);
                        Log.d(TAG, "DB insert : " + status3);
                        cursor4.close();
                        db4.close();

                        SQLiteDatabase sqldb = db.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put(DBManager.TableInfo.status1, status);
                        sqldb.update(TABLE_TASK, values, DBManager.TableInfo.TASK_ID + "=?", new String[]{taskid});
                        sqldb.close();



                        if (task.equals("MyTask")) {
                            GenericMethods.pendingworks = "true";
                            Intent i4 = new Intent(taskcomment.this, NextActivity.class);
                            i4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i4);
                        } else if (task.equals("Witness")) {
                            GenericMethods.pendingworks = "true";
                            Intent i4 = new Intent(taskcomment.this, NextActivity.class);
                            i4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i4);
                        } else if (task.equals("Complaint")) {
                            GenericMethods.pendingworks = "true";
                            Intent i4 = new Intent(taskcomment.this, NextActivity.class);
                            i4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i4);
                        }
                        if (GenericMethods.isConnected(getApplicationContext())) {
                            if(GenericMethods.isOnline()){
                            Intent i3 = new Intent(taskcomment.this, reassignpost.class);
                            startService(i3);
                            }else{
                                Toast.makeText(taskcomment.this, " Task Status Saved Offline,App is waiting for active network connection", Toast.LENGTH_LONG).show();

                            }
                        } else {
                            Toast.makeText(taskcomment.this, " Task Status Saved Offline!!", Toast.LENGTH_LONG).show();

                        }

                    }




                }


            }

        });


    }


}