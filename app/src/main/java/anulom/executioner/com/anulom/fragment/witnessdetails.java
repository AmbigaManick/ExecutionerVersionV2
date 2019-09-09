package anulom.executioner.com.anulom.fragment;


import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import anulom.executioner.com.anulom.GenericMethods;
import anulom.executioner.com.anulom.MyReceiver;
import anulom.executioner.com.anulom.R;
import anulom.executioner.com.anulom.database.DBManager;
import anulom.executioner.com.anulom.database.DBOperation;
import anulom.executioner.com.anulom.taskcomment;

import static anulom.executioner.com.anulom.database.DBManager.TableInfo.TABLE_NOTIFICATION;
import static com.google.android.gms.internal.zzir.runOnUiThread;

public class witnessdetails extends Fragment {

    public static witnessdetails thiswitness = null;

    String value1 = "Witness";

    Date date = null, date1 = null;

    String year, month, day, hour, min, id, owner, sdate, tenant, w1, w2, docid, timevalue, timevalue1 = "", not_id, from = "Witness", biocontent = "", content = "", o_content = "", t_content = "", w1_content = "", w2_content = "",
            o_ = "", t_ = "", w1_ = "", w2_ = "", Date, poststatus, content1 = "Today", valu = "Sales", O_ = "", T_ = "", W_ = "", POA_ = "", AW_ = "", news = "Witness", A, nodetails1 = "No Today Appointments!!!";

    int year1, month1, day1,t_hour1=0,t_min1=0, hour1, h2, h3, click, min1, id1, count = 0, repeat, flag, intervalvalue;

    ArrayList<HashMap<String, String>> getAlldataList = new ArrayList<>();
    ArrayList<HashMap<String, String>> getAlldataList1 = null;
    ArrayList<HashMap<String, String>> getCommentDatalist = null;
    String curdate1, appid, time1, docid1, currentcall, ID1;
    ListView tasklist1;
    DBOperation db, database;
    TextView comment;
    private Calendar mcurrenttime;
    Timer repeatTask = new Timer();
    long repeatInterval = 1800000;
    long startdelay = 10000;
    private SharedPreferences usermail;
    LocationManager locationManager;
    String provider;
    private String password2 = "";
    Calendar calendar, calander;
    SimpleDateFormat simpleDateFormat;
    ArrayList<HashMap<String, String>> paymentlist = null;
    ArrayList<String> reportkey = new ArrayList<>();
    ArrayList<String> reportkey0 = new ArrayList<>();
    ArrayList<String> reportkey1 = new ArrayList<>();
    ArrayList<String> reportkey2 = new ArrayList<>();
    private ProgressDialog progressDialog;
    Timer singleTask = new Timer();
    ArrayList<String> documentidpay = new ArrayList<>();
    ArrayList<String> documentidcomment = new ArrayList<>();
    ArrayAdapter<String> adpt;
    ArrayAdapter<String> adpt1;
    double currentLat;
    double currentLong;
    Button time;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.viewwitdetails, container, false);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        curdate1 = df2.format(c.getTime());

        tasklist1 = rootView.findViewById(R.id.tasklist1);
        reFreshReload();

        return rootView;

    }


    private void createAdpt() {

        adpt = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.taskadwitnessdetails1, reportkey) {

            public View getView(final int position, final View convertView, final ViewGroup parent) {
                // TODO Auto-generated method stub
                final LayoutInflater inflater = LayoutInflater.from(getContext());
                final View v = inflater.inflate(R.layout.taskwitadhoc, parent,false);

                TextView R_key = v.findViewById(R.id.tvreportKey1);
                TextView createdat = v.findViewById(R.id.tvdate2);
                TextView assignby = v.findViewById(R.id.tvtenant2);


                TextView status1 = v.findViewById(R.id.tvtenantvalue);
                TextView comment1 = v.findViewById(R.id.tvtenant);


                assignby.setText(getAlldataList.get(position).get("assign_by"));
                comment1.setText(getAlldataList.get(position).get("comment"));

                createdat.setText(getAlldataList.get(position).get("created_at").substring(0, 10) + "\n" +
                        getAlldataList.get(position).get("created_at").substring(11, 16));

                R_key.setText(getAlldataList.get(position).get("document_id"));
                status1.setText(getAlldataList.get(position).get("status"));
                status1.setTextColor(Color.parseColor("#006B3C"));
                final Button but3 = v.findViewById(R.id.buttonnotify2);
                final Switch sw1 = v.findViewById(R.id.switch1);

                but3.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sw1.setChecked(true);


                    }
                });

                if ((getAlldataList.get(position).get("notify").equals("true"))) {

                    sw1.setChecked(true);


                } else if ((getAlldataList.get(position).get("notify").equals("false"))) {
                    sw1.setChecked(false);

                }

                sw1.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return event.getActionMasked() == MotionEvent.ACTION_MOVE;
                    }
                });
                sw1.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (sw1.isChecked()) {
                            final LayoutInflater inflater = LayoutInflater.from(getContext());
                            View popupView = inflater.inflate(R.layout.popup_layout1, parent,false);

                            final PopupWindow popupWindow = new PopupWindow(popupView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);


                            TextView tv = popupView.findViewById(R.id.textView7);
                            TextView tv1 = popupView.findViewById(R.id.textView8);

                            final TextView e2 = popupView.findViewById(R.id.editTime1);
                            final TextView e3 = popupView.findViewById(R.id.editTime2);
                            Button b1 = popupView.findViewById(R.id.buttontime1);
                            Button b2 = popupView.findViewById(R.id.buttontime2);
                            Button tv2 = popupView.findViewById(R.id.text8);
                            Button tv3 = popupView.findViewById(R.id.text7);
                            final TextView c1 = popupView.findViewById(R.id.checkBoxinterval);
                            final RadioButton btime = popupView.findViewById(R.id.button);
                            final RadioButton btime1 = popupView.findViewById(R.id.button2);
                            final RadioButton btime2 = popupView.findViewById(R.id.button3);
                            final TextView e4 = popupView.findViewById(R.id.textViewtimes);
                            final RadioButton btimer1 = popupView.findViewById(R.id.buttonr1);
                            final RadioButton btimer2 = popupView.findViewById(R.id.buttonr2);
                            final RadioButton btimer3 = popupView.findViewById(R.id.buttonr3);
                            final RadioButton btimer4 = popupView.findViewById(R.id.buttonr4);
                            final RadioButton btimer5 = popupView.findViewById(R.id.buttonr5);

                            b1.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    mcurrenttime = Calendar.getInstance();

                                    TimePickerDialog mTimePicker;
                                    mTimePicker = new TimePickerDialog(witnessdetails.this.getContext(), new TimePickerDialog.OnTimeSetListener() {
                                        @Override
                                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                            mcurrenttime.set(Calendar.HOUR_OF_DAY, selectedHour);
                                            mcurrenttime.set(Calendar.MINUTE, selectedMinute);
                                            timePicker.setIs24HourView(true);
                                            e2.setText(selectedHour + ":" + selectedMinute);
                                            timevalue1 = e2.getText().toString();
                                            t_hour1 = selectedHour;
                                            t_min1 = selectedMinute;
                                        }
                                    }, mcurrenttime.get(Calendar.HOUR_OF_DAY), mcurrenttime.get(Calendar.MINUTE), false);//Yes 24 hour time


                                    mTimePicker.show();
                                }
                            });

                            btime.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    intervalvalue = 30;

                                }
                            });
                            btime1.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    intervalvalue = 60;

                                }
                            });
                            btime2.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    intervalvalue = 120;

                                }
                            });

                            btimer1.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    repeat = 1;

                                }
                            });
                            btimer2.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    repeat = 2;

                                }
                            });
                            btimer3.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    repeat = 3;

                                }
                            });
                            btimer4.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    repeat = 4;

                                }
                            });
                            btimer5.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    repeat = 5;

                                }
                            });

                            tv2.setOnClickListener(new OnClickListener() {

                                public void onClick(View v) {

                                    click = 0;
                                    calander = Calendar.getInstance();
                                    simpleDateFormat = new SimpleDateFormat("HH:mm");

                                    String curr_time = simpleDateFormat.format(calander.getTime());
                                    if (e2.length() == 0) {
                                        e2.setText(curr_time);


                                    }

                                    if ((btime.isChecked() || btime1.isChecked() || btime2.isChecked()) && (btimer1.isChecked() || btimer2.isChecked() || btimer3.isChecked() || btimer4.isChecked() || btimer5.isChecked())) {

                                        viewDetails1(position);
                                        popupWindow.dismiss();
                                        sw1.setChecked(true);


                                    } else {
                                        Toast.makeText(getActivity(), "Please enter all fields", Toast.LENGTH_SHORT).show();

                                    }


                                }

                            });


                            tv3.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    sw1.setChecked(false);
                                    popupWindow.dismiss();


                                }
                            });

                            popupWindow.setFocusable(true);


                            popupWindow.setBackgroundDrawable(new ColorDrawable());

                            int location[] = new int[2];


                            v.getLocationOnScreen(location);


                            sw1.setChecked(false);
                            popupWindow.showAtLocation(v, Gravity.NO_GRAVITY,
                                    location[0], location[1] + v.getHeight());

                        } else {
                            click = 1;
                            viewDetails1(position);
                            sw1.setChecked(false);
                        }


                    }
                });

//                R_key.setOnClickListener(new OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//
//
//                        // TODO Auto-generated method stub
//                      //  Intent i = new Intent(getActivity().getApplicationContext(), Details2.class);
//
//                        //System.out.println(getAlldataList.get(position).get("Pw_docid")+" "+db.getpendingwitness(db).get(position).get("env"));
//
//
////                        if(getAlldataList.get(position).get("Pw_docid").equals(db.getpendingwitness(db).get(position).get("env"))) {
////
////
////                            i.putExtra("Document_id", db.getpendingwitness(db).get(position).get("documentid"));
////                            i.putExtra("ReportKey", db.getpendingwitness(db).get(position).get("env"));
////                            i.putExtra("Tokenno", db.getpendingwitness(db).get(position).get("tokenno"));
////
////                        }else{
////
////                            i.putExtra("Document_id", "null");
////                            i.putExtra("ReportKey", "null");
////                            i.putExtra("Tokenno","Draft is not Approved");
////
////                        }
////
////                        startActivity(i);
//                    }
//                });

                status1.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getActivity().getApplicationContext(), taskcomment.class);
                        i.putExtra("Value", value1);
                        i.putExtra("task_id", getAlldataList.get(position).get("task_id"));
                        i.putExtra("comment", getAlldataList.get(position).get("comment"));
                        startActivity(i);

                    }
                });
                return v;
            }

        };


        tasklist1.setAdapter(adpt);
        singleTask.schedule(new TimerTask() {
            @Override
            public void run() {
                if (GenericMethods.scrollposition != null) {
                    scrollposition(GenericMethods.scrollposition);
                    System.out.println("pos from size:" + GenericMethods.scrollposition);
                }
            }
        }, 2000);

    }
    public void scrollposition(int position) {


        System.out.println("pos from scroll fun:" + position);


        tasklist1.setSmoothScrollbarEnabled(true);
        tasklist1.smoothScrollToPosition(position);
        progressDialog.dismiss();
        singleTask.schedule(new TimerTask() {
            @Override
            public void run() {
                GenericMethods.scrollposition = null;
            }
        }, 2000);

    }
    private void notificationautomaticcancel(final int position) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (db.getwitnessnotificationtable(db).size() > 0) {

                    calander = Calendar.getInstance();
                    simpleDateFormat = new SimpleDateFormat("HH:mm");

                    String curr_time = simpleDateFormat.format(calander.getTime());

                    for (int i = 0; i < db.getwitnessnotificationtable(db).size(); i++) {



                            if (db.getwitnessnotificationtable(db).get(i).get("id1").equals(db.getWitnessdetails(db).get(position).get("id1"))) {

                                if (db.getwitnessnotificationtable(db).get(i).get("time").equals(curr_time)) {

                                    SQLiteDatabase sqldb2 = db.getWritableDatabase();
                                    ContentValues values2 = new ContentValues();
                                    values2.put(DBManager.TableInfo.notification, "false");
                                    int count4 = sqldb2.update(DBManager.TableInfo.TABLE_TASK, values2, DBManager.TableInfo.WITID + "=?", new String[]{db.getWitnessdetails(db).get(position).get("id1")});
                                    sqldb2.close();

                                    SQLiteDatabase marriagedocu = db.getWritableDatabase();
                                    marriagedocu.delete(TABLE_NOTIFICATION, DBManager.TableInfo.WITID + "=?", new String[]{db.getWitnessdetails(db).get(position).get("id1")});
                                    Toast.makeText(getActivity(), "Updating view.. notification gets removed for "+getAlldataList.get(position).get("document_id"), Toast.LENGTH_LONG).show();


                                }

                            }




                    }


                }
                GenericMethods.scrollposition = position;
                reFreshReload();

            }
        });


    }

    private void viewDetails() {
        db = new DBOperation(getActivity());
        reportkey.clear();
        documentidcomment.clear();
        documentidpay.clear();
        if (GenericMethods.taskwitness.equals("true")) {

            getAlldataList = GenericMethods.getwitnessdetails1;

            for (int i = 0; i < getAlldataList.size(); i++) {


                reportkey.add(getAlldataList.get(i).get("document_id"));


            }
        } else if (GenericMethods.taskwitness.equals("false")) {
            getAlldataList = db.getWitnessdetails(db);

            for (int i = 0; i < getAlldataList.size(); i++) {


                reportkey.add(getAlldataList.get(i).get("document_id"));

            }
        }
    }

    private void viewDetails1(final int position) {

        db = new DBOperation(getActivity());
        database = new DBOperation(getActivity());

        reportkey0.clear();
        getAlldataList1 = db.getWitnessdetails(db);

        reportkey0.add(getAlldataList1.get(position).get("document_id"));
        docid = reportkey0.get(0);

        id1 = Integer.valueOf(getAlldataList1.get(position).get("id1"));
        reportkey1.add(getAlldataList1.get(position).get("created_at").substring(0, 10));
        reportkey2.add(getAlldataList1.get(position).get("created_at").substring(11, 16));

        String date1 = reportkey1.get(0);
        year = date1.substring(0, 4);
        month = date1.substring(5, 7);
        day = date1.substring(8, 10);


        year1 = Integer.parseInt(year);
        month1 = Integer.valueOf(month);
        day1 = Integer.valueOf(day);
        calander = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("hh:mm");

        String curr_time = simpleDateFormat.format(calander.getTime());

        if (t_hour1 == 0 && t_min1 == 0) {
            hour = curr_time.substring(0, 2);
            min = curr_time.substring(3);
            hour1 = Integer.valueOf(hour);

            if (hour1 < 9) {

                hour1 = hour1 + 12;
            }

            min1 = Integer.valueOf(min);
        }else {
            hour1 = t_hour1;
            min1 = t_min1;
        }



        content = getAlldataList.get(position).get("comment") + "is Pending";
        if (getAlldataList1.get(position).get("status").equals("Not Completed")) {

            int temp_hour1 = 0, temp_min1 = 0, finalhour = 0, finalmin = 0, app;

            if (click == 0) {
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Updating View...");
                progressDialog.setIndeterminate(false);
                progressDialog.setCancelable(true);
                progressDialog.show();
                Toast.makeText(getActivity(), "Updating view..Notification setting for"+" "+getAlldataList.get(position).get("document_id"), Toast.LENGTH_LONG).show();


                for (int i = 0; i < repeat; i++) {


                    temp_min1 += intervalvalue;

                    if (temp_min1 == 60) {

                        temp_min1 = 0;
                        temp_hour1 += 1;

                    } else if (temp_min1 == 120) {

                        temp_min1 = 0;
                        temp_hour1 += 2;
                    }

                    finalhour = hour1 + temp_hour1;
                    finalmin = min1 + temp_min1;


                    int finalapp = id1 + i;
                    notification1(finalhour, finalmin, finalapp, docid, news, from, content, value1);

                }


                SQLiteDatabase sqldb1 = db.getWritableDatabase();
                ContentValues values1 = new ContentValues();
                values1.put(DBManager.TableInfo.KEYID, ID1);
                values1.put(DBManager.TableInfo.notification_appid, getAlldataList1.get(position).get("id1"));
                values1.put(DBManager.TableInfo.notification_interval, intervalvalue);
                values1.put(DBManager.TableInfo.notification_repeat, repeat);
                values1.put(DBManager.TableInfo.notification_time, finalhour + ":" + finalmin);
                values1.put(DBManager.TableInfo.notification_starttime, timevalue1);
                String username2 = "";
                values1.put(DBManager.TableInfo.KEY_LOGIN_USER, username2);
                values1.put(DBManager.TableInfo.notification_type, "Witness");

                String conditionw = DBManager.TableInfo.KEY_LOGIN_USER + " =?";
                Cursor cursorw = sqldb1.query(TABLE_NOTIFICATION, null, conditionw, new String[]{username2}, null, null, null);
                sqldb1.insert(TABLE_NOTIFICATION, null, values1);
                cursorw.close();
                sqldb1.close();

                SQLiteDatabase sqldb2 = db.getWritableDatabase();
                ContentValues values2 = new ContentValues();
                values2.put(DBManager.TableInfo.notification, "true");
                int count4 = sqldb2.update(DBManager.TableInfo.TABLE_TASK, values2, DBManager.TableInfo.TASK_ID + "=?", new String[]{String.valueOf(id1)});
                sqldb2.close();
                GenericMethods.scrollposition = position;
                System.out.println("pos from view:" + GenericMethods.scrollposition);
                reFreshReload();

                repeatTask.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        notificationautomaticcancel(position);


                    }


                }, startdelay, repeatInterval);

            } else if (click == 1) {
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Updating View...");
                progressDialog.setIndeterminate(false);
                progressDialog.setCancelable(true);
                progressDialog.show();
                Toast.makeText(getActivity(), "Updating view..Notification cancelled for"+" "+getAlldataList.get(position).get("document_id"), Toast.LENGTH_LONG).show();

                for (int j = 0; j < db.getwitnessnotificationtable(db).size(); j++) {
                    app = Integer.valueOf(db.getwitnessnotificationtable(db).get(j).get("id1"));

                    for (int i = 0; i < repeat; i++) {


                        int finalapp = app + i;
                        if (getAlldataList1.get(i).get("id1").equals(db.getwitnessnotificationtable(db).get(j).get("id1"))) {

                            cancelnotification1(finalapp, docid, news, from, content, value1);


                        }

                    }
                }
                SQLiteDatabase marriagedocu = db.getWritableDatabase();
                marriagedocu.delete(TABLE_NOTIFICATION, DBManager.TableInfo.notification_appid + "=?", new String[]{getAlldataList1.get(position).get("id1")});

                SQLiteDatabase sqldb2 = db.getWritableDatabase();
                ContentValues values2 = new ContentValues();
                values2.put(DBManager.TableInfo.notification, "false");
                int count4 = sqldb2.update(DBManager.TableInfo.TABLE_TASK, values2, DBManager.TableInfo.TASK_ID + "=?", new String[]{String.valueOf(id1)});
                sqldb2.close();
                GenericMethods.scrollposition = position;
                System.out.println("pos from view:" + GenericMethods.scrollposition);
                reFreshReload();
            }

        }


    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void notification1(int hour, int min, int id1, String docid, String news, String from, String content, String type) {

        not_id = String.valueOf(id1);
        AlarmManager alarmManager = (AlarmManager) getActivity().getApplicationContext().getSystemService(Context.ALARM_SERVICE);

        Intent notificationIntent = new Intent(getActivity().getApplicationContext(), MyReceiver.class);
        notificationIntent.putExtra("rkey", docid);
        notificationIntent.putExtra("news", news);
        notificationIntent.putExtra("task_id", not_id);
        notificationIntent.putExtra("from", from);
        notificationIntent.putExtra("content", content);
        notificationIntent.putExtra("type", type);

        notificationIntent.addCategory("android.intent.category.DEFAULT");

        PendingIntent broadcast = PendingIntent.getBroadcast(getActivity().getApplicationContext(), id1, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, min);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);


    }

    public void cancelnotification1(int id1, String docid, String news, String from, String content, String type) {

        not_id = String.valueOf(id1);
        AlarmManager alarmManager = (AlarmManager) getActivity().getApplicationContext().getSystemService(Context.ALARM_SERVICE);

        Intent notificationIntent = new Intent(getActivity().getApplicationContext(), MyReceiver.class);

        notificationIntent.putExtra("rkey", docid);
        notificationIntent.putExtra("news", news);
        notificationIntent.putExtra("task_id", not_id);
        notificationIntent.putExtra("from", from);
        notificationIntent.putExtra("content", content);
        notificationIntent.putExtra("type", type);
        notificationIntent.addCategory("android.intent.category.DEFAULT");


        PendingIntent broadcast = PendingIntent.getBroadcast(getActivity().getApplicationContext(), id1, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        broadcast.cancel();
        alarmManager.cancel(broadcast);

    }

    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        thiswitness = this;
    }

    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        thiswitness = null;
    }


    public void reFreshReload() {
        viewDetails();
        createAdpt();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        if (repeatTask != null) {

            repeatTask.cancel();
        }
    }


}