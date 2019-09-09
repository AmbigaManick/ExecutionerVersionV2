package anulom.executioner.com.anulom.fragment;

import android.Manifest;
import android.app.AlarmManager;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.net.Uri;

import android.os.Bundle;

import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import android.support.v4.content.ContextCompat;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;


import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


import anulom.executioner.com.anulom.Appointmentbooking;

import anulom.executioner.com.anulom.MapsActivity;
import anulom.executioner.com.anulom.Pay;



import anulom.executioner.com.anulom.database.DBManager;
import anulom.executioner.com.anulom.database.DBOperation;
import anulom.executioner.com.anulom.ACVReport;
import anulom.executioner.com.anulom.CommentInfo;
import anulom.executioner.com.anulom.Details;
import anulom.executioner.com.anulom.GenericMethods;
import anulom.executioner.com.anulom.MyReceiver;
import anulom.executioner.com.anulom.R;

import anulom.executioner.com.anulom.newstatusinfo;
import anulom.executioner.com.anulom.StatusInfo;
import anulom.executioner.com.anulom.reschedule;
import anulom.executioner.com.anulom.services.call;
import anulom.executioner.com.anulom.services.fetchactualtime;

import static anulom.executioner.com.anulom.database.DBManager.TableInfo.ACTUAL_TIME;
import static anulom.executioner.com.anulom.database.DBManager.TableInfo.CALL;

import static anulom.executioner.com.anulom.database.DBManager.TableInfo.PAYMENT;
import static anulom.executioner.com.anulom.database.DBManager.TableInfo.TABLE_NAME_APPOINTMENT;
import static anulom.executioner.com.anulom.database.DBManager.TableInfo.TABLE_NOTIFICATION;
import static com.google.android.gms.internal.zzir.runOnUiThread;


public class OlderDetails extends Fragment {

    String year, starting, month, day, hour,localTime, min, document, reportkeypay, startdate, id, owner, tenant, w1, w2, rkey, not_id, from = "Older", timevalue1 = "", poa_content = "", w_content = "", aw_content, news = "Biometric Older", content = "", o_content = "", t_content = "", w1_content = "", w2_content = "", o_ = "", t_ = "", w1_ = "", w2_ = "",
            content1 = "Older", valu = "Biometric", rkey1, payamount="",payamount1="", sdate, notifyvalue, curdate1, apptype, biometric, Date, A, ID1, poststatus;
    int year1, month1, day1, hour1, min1, id1, click, count = 0, flag, intervalvalue, repeat;

    private ProgressDialog progressDialog;
    public static OlderDetails thisOlderDetails = null;
    private SharedPreferences usermail;
    private String username2 = "", password2 = "";
    double latitude, currentlat, currentlong;
    ScrollView notificationscrollview;
    Date date = null, date1 = null;
    int temp_hour1 = 0;
    int temp_min1 = 0;
    int finalhour = 0;
    int finalmin = 0;
    int t_hour1 = 0, t_min1 = 0;
    ArrayList<HashMap<String, String>> getAlldataList = new ArrayList<>();
    ArrayList<HashMap<String, String>> getAlldataList1 = null;
    ArrayList<HashMap<String, String>> getCommentDatalist = null;
    ArrayList<HashMap<String, String>> paymentlist = null;
    private Scroller mScroller;
    ArrayList<String> reportkey = new ArrayList<>();
    ArrayList<String> reportkey0 = new ArrayList<>();
    ArrayList<String> reportkey1 = new ArrayList<>();
    ArrayList<String> reportkey2 = new ArrayList<>();

    ArrayList<String> documentidpay = new ArrayList<>();
    ArrayList<String> documentidcomment = new ArrayList<>();
    ListView tasklist;
    DBOperation db, database;
    TextView comment;
    Button time;
    Timer singleTask = new Timer();
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;

    private Calendar mcurrenttime, calander;

    Context context;
    ArrayAdapter<String> adpt;
    Timer repeatTask = new Timer();
    long repeatInterval = 1800000;
    long startdelay = 10000;
    String LOG_TAG = "OlderDetails";

    public OlderDetails() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        db = new DBOperation(getActivity());
        View rootView = inflater.inflate(R.layout.viewdetails, container, false);
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        curdate1 = df2.format(c.getTime());

        tasklist = rootView.findViewById(R.id.tasklist);

        //notificationscrollview=rootView.findViewById(R.id.viii);

        reFreshReload();

        return rootView;

    }


    @Override
    public void onResume() {
        super.onResume();
        thisOlderDetails = this;


    }

    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        thisOlderDetails = null;
    }


    private void createAdpt() {


        adpt = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.taskdetails1, reportkey) {

            public View getView(final int position, View convertView, final ViewGroup parent) {

                // TODO Auto-generated method stub
                final LayoutInflater inflater = LayoutInflater.from(getContext());
                final View v = inflater.inflate(R.layout.taskdetails, parent, false);

                TextView tokenno = v.findViewById(R.id.tvreportKey1);
                TextView grnnumber = v.findViewById(R.id.tvreportKey2);
                TextView grnnumber1 = v.findViewById(R.id.tvreportKey3);
                TextView biometricstatus = v.findViewById(R.id.bio1);
                TextView biometric = v.findViewById(R.id.bio2);
                TextView paymentstatus = v.findViewById(R.id.pay1);
                final TextView payam = v.findViewById(R.id.pay2);
                TextView R_key = v.findViewById(R.id.tvreportKey);
                TextView Oname = v.findViewById(R.id.tvowner1);
                TextView Tname = v.findViewById(R.id.tvtenant1);
                TextView status = v.findViewById(R.id.tvstatus1);
                final TextView date = v.findViewById(R.id.tvdate1);
                comment = v.findViewById(R.id.tvcomment1);
                TextView addpay = v.findViewById(R.id.tvAddpay1);
                TextView acvr = v.findViewById(R.id.tvACVR1);
                TextView map = v.findViewById(R.id.tvMap1);


//                TextView OwnerContact = (TextView) v.findViewById(R.id.image1);
//                TextView TenantContact = (TextView) v.findViewById(R.id.image2);
                TextView appoint = v.findViewById(R.id.appointment);
                LinearLayout contactperson = v.findViewById(R.id.contactperson);
                LinearLayout owner = v.findViewById(R.id.layout1);
                LinearLayout tenant = v.findViewById(R.id.layout2);
                final TextView contac1 = v.findViewById(R.id.contact1);
                //TextView Contactimage = (TextView) v.findViewById(R.id.image);
                time = v.findViewById(R.id.owner2);
                ImageView img1 = v.findViewById(R.id.buttonimg);
                ImageView img2 = v.findViewById(R.id.buttonimg1);
                ImageView img3 = v.findViewById(R.id.buttonimg2);
                final Button but3 = v.findViewById(R.id.buttonnotify1);
                final Switch sw1 = v.findViewById(R.id.switch1);
                sw1.setClickable(false);
                usermail = PreferenceManager.getDefaultSharedPreferences(getActivity());
                username2 = usermail.getString("username", "");
                password2 = usermail.getString("password", "");
                SharedPreferences pref = getActivity().getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("value", "Biometric");
                editor.apply();
                final SharedPreferences.Editor editor1 = pref.edit();
                editor1.putString("cust_contact_no", getAlldataList.get(position).get("ocontact"));
                editor1.apply();
                if(getAlldataList.get(position).get("Rtoken").equals("") || getAlldataList.get(position).get("Rtoken").equals("null")){

                    tokenno.setText("Token number:"+"Draft not Approved");

                }else {

                    tokenno.setText("Token number:"+getAlldataList.get(position).get("Rtoken"));

                }

                System.out.println("sgrn"+getAlldataList.get(position).get("r_grn"));
                System.out.println("rgrn"+getAlldataList.get(position).get("s_grn"));

                if(getAlldataList.get(position).get("r_grn").equals(null) || getAlldataList.get(position).get("r_grn").equals("") ||getAlldataList.get(position).get("r_grn").equals("null")  ){

                    grnnumber1.setText("Registration GRN:"+"Token is not created yet");

                }else{
                    grnnumber1.setText("Registration GRN:"+getAlldataList.get(position).get("r_grn"));
                }
                if(getAlldataList.get(position).get("s_grn").equals(null)|| getAlldataList.get(position).get("s_grn").equals("") || getAlldataList.get(position).get("s_grn").equals("null") ){

                    grnnumber.setText("Stampduty GRN:"+"Token is not created yet");

                }else{
                    grnnumber.setText("Stampduty GRN:"+getAlldataList.get(position).get("s_grn"));
                }
                System.out.println("notification set" + getAlldataList.get(position).get("notify"));
                if ((getAlldataList.get(position).get("notify").equals("true"))) {

                    sw1.setChecked(true);


                } else if ((getAlldataList.get(position).get("notify").equals("false"))) {
                    sw1.setChecked(false);

                }


                if (getAlldataList.get(position).get("contact_person").equals("No details")) {


                } else if (getAlldataList.get(position).get("contact_person").equals("Details")) {


                    if (getAlldataList.get(position).get("apptype").equals("1")) {

                        if (getAlldataList.get(position).get("ocontact").equals(getAlldataList.get(position).get("contact_no"))) {

                            Oname.setTextColor(Color.parseColor("#3b5998"));
                            Oname.setAllCaps(true);
                            img1.setVisibility(View.VISIBLE);

                            owner.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    calendar = Calendar.getInstance();
                                    simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                                    Date = simpleDateFormat.format(calendar.getTime());

                                    if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 123);
                                    } else if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {


                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                        intent.setData(Uri.parse("tel:" + getAlldataList.get(position).get("ocontact")));
                                        startActivity(intent);
                                        SQLiteDatabase sqldb1 = db.getWritableDatabase();
                                        ContentValues values1 = new ContentValues();
                                        values1.put(DBManager.TableInfo.KEYID, ID1);
                                        values1.put(DBManager.TableInfo.DocumentId, getAlldataList.get(position).get("docid"));
                                        values1.put(DBManager.TableInfo.AppointmentId, getAlldataList.get(position).get("appid"));
                                        values1.put(DBManager.TableInfo.actual_time, timevalue1);
                                        values1.put(DBManager.TableInfo.call_time, Date);
                                        values1.put(DBManager.TableInfo.KEY_LOGIN_USER, username2);
                                        String conditionw = DBManager.TableInfo.AppointmentId + " =?";
                                        Cursor cursorw = sqldb1.query(CALL, null, conditionw, new String[]{DBManager.TableInfo.actual_time}, null, null, null);
                                        sqldb1.insert(CALL, null, values1);
                                        cursorw.close();
                                        sqldb1.close();
                                        Intent intent1 = new Intent(getActivity().getApplicationContext(), call.class);
                                        getActivity().getApplicationContext().startService(intent1);
                                    }

                                }
                            });

                        } else if (getAlldataList.get(position).get("tcontact").equals(getAlldataList.get(position).get("contact_no"))) {

                            Tname.setTextColor(Color.parseColor("#3b5998"));
                            Tname.setAllCaps(true);
                            img2.setVisibility(View.VISIBLE);
                            tenant.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    calendar = Calendar.getInstance();
                                    simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                                    Date = simpleDateFormat.format(calendar.getTime());
                                    if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 123);
                                    } else if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {


                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                        intent.setData(Uri.parse("tel:" + getAlldataList.get(position).get("tcontact")));
                                        startActivity(intent);
                                        SQLiteDatabase sqldb1 = db.getWritableDatabase();
                                        ContentValues values1 = new ContentValues();
                                        values1.put(DBManager.TableInfo.KEYID, ID1);
                                        values1.put(DBManager.TableInfo.DocumentId, getAlldataList.get(position).get("docid"));
                                        values1.put(DBManager.TableInfo.AppointmentId, getAlldataList.get(position).get("appid"));
                                        values1.put(DBManager.TableInfo.actual_time, timevalue1);
                                        values1.put(DBManager.TableInfo.call_time, Date);
                                        values1.put(DBManager.TableInfo.KEY_LOGIN_USER, username2);
                                        String conditionw = DBManager.TableInfo.AppointmentId + " =?";
                                        Cursor cursorw = sqldb1.query(CALL, null, conditionw, new String[]{DBManager.TableInfo.actual_time}, null, null, null);
                                        sqldb1.insert(CALL, null, values1);
                                        cursorw.close();
                                        sqldb1.close();
                                        Intent intent1 = new Intent(getActivity().getApplicationContext(), call.class);
                                        getActivity().getApplicationContext().startService(intent1);
                                    }
                                }
                            });


                        } else {
                            contactperson.setVisibility(View.VISIBLE);
                            contac1.setText(getAlldataList.get(position).get("contact_name"));
                            contac1.setTextColor(Color.parseColor("#3b5998"));
                            contac1.setAllCaps(true);
                            img3.setVisibility(View.VISIBLE);
                            contactperson.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    calendar = Calendar.getInstance();
                                    simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                                    Date = simpleDateFormat.format(calendar.getTime());

                                    if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 123);
                                    } else if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {


                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                        intent.setData(Uri.parse("tel:" + getAlldataList.get(position).get("contact_no")));
                                        startActivity(intent);
                                        SQLiteDatabase sqldb1 = db.getWritableDatabase();
                                        ContentValues values1 = new ContentValues();
                                        values1.put(DBManager.TableInfo.KEYID, ID1);
                                        values1.put(DBManager.TableInfo.DocumentId, getAlldataList.get(position).get("docid"));
                                        values1.put(DBManager.TableInfo.AppointmentId, getAlldataList.get(position).get("appid"));
                                        values1.put(DBManager.TableInfo.actual_time, timevalue1);
                                        values1.put(DBManager.TableInfo.call_time, Date);
                                        values1.put(DBManager.TableInfo.KEY_LOGIN_USER, username2);
                                        String conditionw = DBManager.TableInfo.AppointmentId + " =?";
                                        Cursor cursorw = sqldb1.query(CALL, null, conditionw, new String[]{DBManager.TableInfo.actual_time}, null, null, null);
                                        sqldb1.insert(CALL, null, values1);
                                        cursorw.close();
                                        sqldb1.close();
                                        Intent intent1 = new Intent(getActivity().getApplicationContext(), call.class);
                                        getActivity().getApplicationContext().startService(intent1);
                                    }
                                }
                            });
                        }

                    } else if (getAlldataList.get(position).get("apptype").equals("2")) {

                        if (getAlldataList.get(position).get("ocontact").equals(getAlldataList.get(position).get("contact_no"))) {

                            Oname.setTextColor(Color.parseColor("#3b5998"));
                            Oname.setAllCaps(true);
                            img1.setVisibility(View.VISIBLE);
                            owner.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    calendar = Calendar.getInstance();
                                    simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                                    Date = simpleDateFormat.format(calendar.getTime());

                                    if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 123);
                                    } else if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {


                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                        intent.setData(Uri.parse("tel:" + getAlldataList.get(position).get("ocontact")));
                                        startActivity(intent);
                                        SQLiteDatabase sqldb1 = db.getWritableDatabase();
                                        ContentValues values1 = new ContentValues();
                                        values1.put(DBManager.TableInfo.KEYID, ID1);
                                        values1.put(DBManager.TableInfo.DocumentId, getAlldataList.get(position).get("docid"));
                                        values1.put(DBManager.TableInfo.AppointmentId, getAlldataList.get(position).get("appid"));
                                        values1.put(DBManager.TableInfo.actual_time, timevalue1);
                                        values1.put(DBManager.TableInfo.call_time, Date);
                                        values1.put(DBManager.TableInfo.KEY_LOGIN_USER, username2);
                                        String conditionw = DBManager.TableInfo.AppointmentId + " =?";
                                        Cursor cursorw = sqldb1.query(CALL, null, conditionw, new String[]{DBManager.TableInfo.actual_time}, null, null, null);
                                        sqldb1.insert(CALL, null, values1);
                                        cursorw.close();
                                        sqldb1.close();
                                        Intent intent1 = new Intent(getActivity().getApplicationContext(), call.class);
                                        getActivity().getApplicationContext().startService(intent1);
                                    }
                                }
                            });

                        } else if (getAlldataList.get(position).get("tcontact").equals(getAlldataList.get(position).get("contact_no"))) {


                            Tname.setTextColor(Color.parseColor("#3b5998"));
                            Tname.setAllCaps(true);
                            img2.setVisibility(View.VISIBLE);
                            tenant.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    calendar = Calendar.getInstance();
                                    simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                                    Date = simpleDateFormat.format(calendar.getTime());

                                    if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 123);
                                    } else if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {


                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                        intent.setData(Uri.parse("tel:" + getAlldataList.get(position).get("tcontact")));
                                        startActivity(intent);
                                        SQLiteDatabase sqldb1 = db.getWritableDatabase();
                                        ContentValues values1 = new ContentValues();
                                        values1.put(DBManager.TableInfo.KEYID, ID1);
                                        values1.put(DBManager.TableInfo.DocumentId, getAlldataList.get(position).get("docid"));
                                        values1.put(DBManager.TableInfo.AppointmentId, getAlldataList.get(position).get("appid"));
                                        values1.put(DBManager.TableInfo.actual_time, timevalue1);
                                        values1.put(DBManager.TableInfo.call_time, Date);
                                        values1.put(DBManager.TableInfo.KEY_LOGIN_USER, username2);
                                        String conditionw = DBManager.TableInfo.AppointmentId + " =?";
                                        Cursor cursorw = sqldb1.query(CALL, null, conditionw, new String[]{DBManager.TableInfo.actual_time}, null, null, null);
                                        sqldb1.insert(CALL, null, values1);
                                        cursorw.close();
                                        sqldb1.close();
                                        Intent intent1 = new Intent(getActivity().getApplicationContext(), call.class);
                                        getActivity().getApplicationContext().startService(intent1);
                                    }
                                }
                            });


                        } else {
                            contactperson.setVisibility(View.VISIBLE);
                            contac1.setText(getAlldataList.get(position).get("contact_name"));
                            contac1.setTextColor(Color.parseColor("#3b5998"));
                            contac1.setAllCaps(true);
                            img3.setVisibility(View.VISIBLE);
                            contactperson.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    calendar = Calendar.getInstance();
                                    simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                                    Date = simpleDateFormat.format(calendar.getTime());

                                    if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 123);
                                    } else if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {


                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                        intent.setData(Uri.parse("tel:" + getAlldataList.get(position).get("contact_no")));
                                        startActivity(intent);
                                        SQLiteDatabase sqldb1 = db.getWritableDatabase();
                                        ContentValues values1 = new ContentValues();
                                        values1.put(DBManager.TableInfo.KEYID, ID1);
                                        values1.put(DBManager.TableInfo.DocumentId, getAlldataList.get(position).get("docid"));
                                        values1.put(DBManager.TableInfo.AppointmentId, getAlldataList.get(position).get("appid"));
                                        values1.put(DBManager.TableInfo.actual_time, timevalue1);
                                        values1.put(DBManager.TableInfo.call_time, Date);
                                        values1.put(DBManager.TableInfo.KEY_LOGIN_USER, username2);
                                        String conditionw = DBManager.TableInfo.AppointmentId + " =?";
                                        Cursor cursorw = sqldb1.query(CALL, null, conditionw, new String[]{DBManager.TableInfo.actual_time}, null, null, null);
                                        sqldb1.insert(CALL, null, values1);
                                        cursorw.close();
                                        sqldb1.close();
                                        Intent intent1 = new Intent(getActivity().getApplicationContext(), call.class);
                                        getActivity().getApplicationContext().startService(intent1);
                                    }
                                }
                            });
                        }

                    }


                }


                TextView schedule = v.findViewById(R.id.tvdate2);


                Oname.setText(getAlldataList.get(position).get("oname"));
                Tname.setText(getAlldataList.get(position).get("tname"));

                String date1 = getAlldataList.get(position).get("sdate");
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
                Date date11 = null;
                try {
                    date11 = format1.parse(date1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Thread t = new Thread() {
                    public void run() {
                        sw1.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return event.getActionMasked() == MotionEvent.ACTION_MOVE;
                            }
                        });
                        sw1.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(final View v) {

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (sw1.isChecked()) {


                                            final LayoutInflater inflater = LayoutInflater.from(getContext());
                                            View popupView = inflater.inflate(R.layout.popup_layout1, parent, false);

                                            final PopupWindow popupWindow = new PopupWindow(popupView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);


                                            TextView tv = popupView.findViewById(R.id.textView7);
                                            TextView tv1 = popupView.findViewById(R.id.textView8);

                                            final TextView e2 = popupView.findViewById(R.id.editTime1);
                                            final TextView e3 = popupView.findViewById(R.id.editTime2);
                                            Button b1 = popupView.findViewById(R.id.buttontime1);
                                            Button b2 = popupView.findViewById(R.id.buttontime2);
                                            final Button tv2 = popupView.findViewById(R.id.text8);
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
                                            final RadioGroup r1 = popupView.findViewById(R.id.radioGroup2);
                                            final RadioGroup r2 = popupView.findViewById(R.id.radioGroup3);
                                            startdate = getAlldataList.get(position).get("sdate");
                                            content = getAlldataList.get(position).get("att_status");
                                            if (content.equals("")) {
                                                content = "All Party Biometrics are Pending";
                                            } else if (!content.equals("")) {
                                                content = getAlldataList.get(position).get("att_status") + " " + "done !!Other Biometrics are  Pending";
                                            }

                                            b1.setOnClickListener(new OnClickListener() {

                                                public void onClick(View v) {

                                                    mcurrenttime = Calendar.getInstance();

                                                    TimePickerDialog mTimePicker;
                                                    mTimePicker = new TimePickerDialog(OlderDetails.this.getContext(), new TimePickerDialog.OnTimeSetListener() {

                                                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                                            mcurrenttime.set(Calendar.HOUR_OF_DAY, selectedHour);
                                                            mcurrenttime.set(Calendar.MINUTE, selectedMinute);
                                                            timePicker.setIs24HourView(true);
                                                            e2.setText(selectedHour + ":" + selectedMinute);
                                                            timevalue1 = e2.getText().toString();
                                                            t_hour1 = selectedHour;
                                                            t_min1 = selectedMinute;

                                                            System.out.println("t_hour" + t_hour1 + "t_min1" + t_min1);
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

                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    tv2.setOnClickListener(new OnClickListener() {

                                                        public void onClick(View v) {

                                                            click = 0;
                                                            calander = Calendar.getInstance();
                                                            simpleDateFormat = new SimpleDateFormat("HH:mm");

                                                            String curr_time = simpleDateFormat.format(calander.getTime());
                                                            if (e2.length() == 0) {
                                                                e2.setText(curr_time);

                                                            }

                                                            System.out.println("length" + e2.getText().toString());

                                                            if ((btime.isChecked() || btime1.isChecked() || btime2.isChecked()) && (btimer1.isChecked() || btimer2.isChecked() || btimer3.isChecked() || btimer4.isChecked() || btimer5.isChecked())) {
                                                                runOnUiThread(new Runnable() {
                                                                    @Override
                                                                    public void run() {


                                                                        System.out.println("repeat:" + repeat + "interval value:" + intervalvalue + "content:" + content);
                                                                        viewDetails1(news, from, content, position);
                                                                        sw1.setChecked(true);
                                                                        popupWindow.dismiss();

                                                                    }
                                                                });


                                                            } else {
                                                                Toast.makeText(getActivity(), "Please enter all fields", Toast.LENGTH_SHORT).show();

                                                            }
                                                        }
                                                    });


                                                }

                                            });


                                            tv3.setOnClickListener(new OnClickListener() {

                                                public void onClick(View v) {

                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            click = 1;
                                                            viewDetails1(news, from, content, position);
                                                            sw1.setChecked(false);
                                                            popupWindow.dismiss();
                                                        }
                                                    });


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

                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    click = 1;
                                                    viewDetails1(news, from, content, position);
                                                    sw1.setChecked(false);
                                                }
                                            });

                                        }
                                    }
                                });


                            }
                        });
                    }
                };
                t.start();


                String datest = getAlldataList.get(position).get("stime1");
                SimpleDateFormat formatst = new SimpleDateFormat("HH:mm");
                SimpleDateFormat formatst2 = new SimpleDateFormat("hh:mm a");
                Date stdate11 = null;

                try {
                    stdate11 = formatst.parse(datest);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                String dateend = getAlldataList.get(position).get("stime2");
                SimpleDateFormat formatend = new SimpleDateFormat("HH:mm");
                SimpleDateFormat formatend2 = new SimpleDateFormat("hh:mm");


                Date enddate11 = null;
                try {
                    enddate11 = formatend.parse(dateend);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                date.setText(format1.format(date11) + " " + "\n" + formatst2.format(stdate11) + "-" + formatend2.format(enddate11));


                String strStatus = getAlldataList.get(position).get("syncstatus");
                String strAcvrReportStatus = getAlldataList.get(position).get("acvrreportstatus");


                boolean reportkeystatus = checkreportkeystatus(position);
                if (getAlldataList.get(position).get("apptype").equals("1")) {
                    A = "(S)";

                    if (getAlldataList.get(position).get("biocom1").equals("1")) {
                        o_ = "O";
                    } else {
                        o_ = "";
                    }
                    if (getAlldataList.get(position).get("biocomp").equals("1")) {
                        t_ = "T";
                    } else {
                        t_ = "";
                    }
                    if (getAlldataList.get(position).get("regfromcomp").equals("1")) {
                        w1_ = "W1";
                    } else {
                        w1_ = "";
                    }
                    if (getAlldataList.get(position).get("witness").equals("1")) {
                        w2_ = "W2";
                    } else {
                        w2_ = "";
                    }

                    biometric.setText(o_ + " " + t_ + " " + w1_ + " " + w2_);
                    biometric.setTextColor(Color.parseColor("#3b5998"));

                    if (getAlldataList.get(position).get("biocom1").equals("null") && getAlldataList.get(position).get("biocomp").equals("null") && getAlldataList.get(position).get("regfromcomp").equals("null") && getAlldataList.get(position).get("witness").equals("null")) {
                        biometric.setText("Biometric Not Updated");
                        biometric.setTextColor(Color.parseColor("#3b5998"));
                    }

                } else if (getAlldataList.get(position).get("apptype").equals("2")) {
                    A = "(M)";
                    biometric.setTextColor(Color.parseColor("#3b5998"));
                    biometric.setText(getAlldataList.get(position).get("att_status"));

                    if (getAlldataList.get(position).get("att_status").equals("")) {
                        biometric.setTextColor(Color.parseColor("#3b5998"));
                        biometric.setText("Biometric Not Updated");
                    }
                } else if (getAlldataList.get(position).get("apptype").equals("null")) {
                    A = "";

                }


                if (checkreportkeystatus(position)) {
                    R_key.setText(getAlldataList.get(position).get("rkey") + A);

                    R_key.setTextColor(Color.parseColor("#ff0000"));
                } else {
                    R_key.setText(getAlldataList.get(position).get("rkey") + A);
                    R_key.setTextColor(Color.parseColor("#3b5998"));
                }

                if (strStatus.equals("ASYNC")) {
                    status.setBackgroundResource(R.drawable.statusred);
                } else {
                    status.setBackgroundResource(R.drawable.statusblue);
                }

                if (strAcvrReportStatus.equals("ASYNC")) {
                    acvr.setBackgroundResource(R.drawable.reportred);
                } else {
                    acvr.setBackgroundResource(R.drawable.reportblue);
                }

                boolean commentStatus = CheckCommentStatus(position);
                if (commentStatus) {
                    comment.setBackgroundResource(R.drawable.commentblueee);
                } else {
                    comment.setBackgroundResource(R.drawable.commentsred);
                }
//                comment.setBackgroundResource(R.drawable.greenpaymentt);
                addpay.setBackgroundResource(R.drawable.payblue);
//                comment.setText("Comments");
//                addpay.setText("Payment");
//                acvr.setText("Report");
                map.setVisibility(View.VISIBLE);
                map.setBackgroundResource(R.drawable.mapiconblue);


                SQLiteDatabase base1 = db.getReadableDatabase();
                String query = "select * from  " + PAYMENT + " where " + DBManager.TableInfo.KEY_LOGIN_USER + " = '" + GenericMethods.email + "'";
                Cursor cursor = base1.rawQuery(query, null);
                while (cursor.moveToNext()) {
                    if (getAlldataList.get(position).get("rkey").equals(cursor.getString(cursor.getColumnIndex(DBManager.TableInfo.rep1)))) {
                        reportkeypay = cursor.getString(cursor.getColumnIndex(DBManager.TableInfo.rep1));
                        payamount = cursor.getString(cursor.getColumnIndex(DBManager.TableInfo.payamount));
                        System.out.println("payamount:"+payamount+reportkeypay);

                    }

                }
                cursor.close();
                base1.close();


                if (payamount.equals("") || payamount == null||payamount=="null") {
                    paymentstatus.setVisibility(View.GONE);
                    payam.setVisibility(View.GONE);

                } else if (!payamount.equals("") && Integer.valueOf(payamount) > 0 && Integer.valueOf(payamount) <= 50) {

                    paymentstatus.setVisibility(View.VISIBLE);
                    payam.setVisibility(View.VISIBLE);
                    payam.setText("Please Collect ₹" + payamount + "/-from Client At the time of Execution");
                    payamount = "";
                }
                R_key.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        reportkey(position);
                    }
                });


                comment.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        comments(position);


                    }

                });


                status.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        final LayoutInflater inflater = LayoutInflater.from(getContext());
                        View popupView = inflater.inflate(R.layout.popout_layout3, parent, false);

                        final PopupWindow popupWindow = new PopupWindow(popupView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);


                        Button b1 = popupView.findViewById(R.id.bSearch);
                        Button b2 = popupView.findViewById(R.id.bSearch1);

                        b1.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                flag = 0;
                                Calendar cal = Calendar.getInstance();
                                Date currentLocalTime = cal.getTime();
                                DateFormat date = new SimpleDateFormat("HH:mm a");
                                localTime = date.format(currentLocalTime);
                                checkin(position);
                                popupWindow.dismiss();


                            }
                        });

                        b2.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                flag = 1;

                                viewonly(position);
                                popupWindow.dismiss();
                            }

                        });


                        popupWindow.setFocusable(true);


                        popupWindow.setBackgroundDrawable(new ColorDrawable());

                        int location[] = new int[2];

                        v.getLocationOnScreen(location);

                        popupWindow.showAtLocation(v, Gravity.CENTER, 300, 300);


                    }

                });


                acvr.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        acvr(position);
                    }

                });
                map.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (GenericMethods.isConnected(getActivity())) {
                            Intent intent = new Intent(getActivity().getApplicationContext(), MapsActivity.class);

                            if (getAlldataList.get(position).get("latitude").equals("null") && getAlldataList.get(position).get("longitude").equals("null") || getAlldataList.get(position).get("latitude").equals("") && getAlldataList.get(position).get("longitude").equals("")) {
                                Toast.makeText(getActivity(), "Customer Location Not Available-Route Cannot be Generated For This Document", Toast.LENGTH_SHORT).show();

                                intent.putExtra("AppointmentAddress", getAlldataList.get(position).get("appadress"));
                                intent.putExtra("position", position);
                                intent.putExtra("CustomerLatitude", "null");
                                intent.putExtra("CustomerLatlong", "null");


                            } else {
                                intent.putExtra("AppointmentAddress", getAlldataList.get(position).get("appadress"));
                                if (position > 0) {

                                    if (getAlldataList.get(position - 1).get("latitude").equals("null") && getAlldataList.get(position - 1).get("longitude").equals("null") || getAlldataList.get(position - 1).get("latitude").equals("") && getAlldataList.get(position - 1).get("longitude").equals("")) {
                                        Toast.makeText(getActivity(), "Previous Location Not Available,Choose Other Location", Toast.LENGTH_SHORT).show();

                                        intent.putExtra("PreviousAddress", getAlldataList.get(position - 1).get("appadress"));
                                        intent.putExtra("lat", "null");
                                        intent.putExtra("long", "null");
                                    } else {
                                        intent.putExtra("PreviousAddress", getAlldataList.get(position - 1).get("appadress"));
                                        intent.putExtra("lat", getAlldataList.get(position - 1).get("latitude"));
                                        intent.putExtra("long", getAlldataList.get(position - 1).get("longitude"));
                                    }
                                }
                                intent.putExtra("position", position);
                                intent.putExtra("CustomerLatitude", getAlldataList.get(position).get("latitude"));
                                intent.putExtra("CustomerLatlong", getAlldataList.get(position).get("longitude"));

                                startActivity(intent);

                            }
                        } else {
                            Toast.makeText(getActivity(), "Please Turn on the Internet!!", Toast.LENGTH_SHORT).show();

                        }

                    }
                });

                addpay.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent1 = new Intent(getActivity().getApplicationContext(), Pay.class);
                        intent1.putExtra("DocumentId", getAlldataList.get(position).get("docid"));
                        intent1.putExtra("ReportKey", getAlldataList.get(position).get("rkey"));
//                        intent1.putExtra("Amount",)
                        startActivityForResult(intent1, 12345);
                    }
                });



                schedule.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent1 = new Intent(getActivity().getApplicationContext(), reschedule.class);
                        intent1.putExtra("DocumentId", getAlldataList.get(position).get("docid"));
                        intent1.putExtra("AppointmentId", getAlldataList.get(position).get("appid"));

                        startActivityForResult(intent1, 12345);
                    }
                });
                appoint.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (GenericMethods.isConnected(getActivity())) {

                            Intent i = new Intent(getActivity().getApplicationContext(), Appointmentbooking.class);
                            i.putExtra("DocumentId", getAlldataList.get(position).get("docid"));
                            i.putExtra("apptype", getAlldataList.get(position).get("apptype"));
                            i.putExtra("content", content1);
                            i.putExtra("StartDate", getAlldataList.get(position).get("sdate"));

                            i.putExtra("value", valu);
                            i.putExtra("ReportKey", getAlldataList.get(position).get("rkey"));
                            i.putExtra("AppointmentId", getAlldataList.get(position).get("appid"));


                            startActivity(i);
                        } else {
                            Toast.makeText(getActivity(), "Please Turn On the Internet", Toast.LENGTH_SHORT).show();

                        }
                    }
                });


                time.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final LayoutInflater inflater = LayoutInflater.from(getContext());
                        View popupView = inflater.inflate(R.layout.popup_layout, parent, false);

                        final PopupWindow popupWindow = new PopupWindow(popupView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);


                        TextView tv = popupView.findViewById(R.id.textView7);
                        TextView tv1 = popupView.findViewById(R.id.textView8);

                        final TextView e2 = popupView.findViewById(R.id.editTime1);
                        final TextView e3 = popupView.findViewById(R.id.editTime2);
                        Button b1 = popupView.findViewById(R.id.buttontime1);
                        Button b2 = popupView.findViewById(R.id.buttontime2);
                        Button tv2 = popupView.findViewById(R.id.text8);
                        Button tv3 = popupView.findViewById(R.id.text7);
                        b1.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                mcurrenttime = Calendar.getInstance();

                                TimePickerDialog mTimePicker;
                                mTimePicker = new TimePickerDialog(OlderDetails.this.getContext(), new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                        mcurrenttime.set(Calendar.HOUR_OF_DAY, selectedHour);
                                        mcurrenttime.set(Calendar.MINUTE, selectedMinute);
                                        timePicker.setIs24HourView(true);
                                        e2.setText(selectedHour + ":" + selectedMinute);
                                        timevalue1 = e2.getText().toString();


                                    }
                                }, mcurrenttime.get(Calendar.HOUR_OF_DAY), mcurrenttime.get(Calendar.MINUTE), false);//Yes 24 hour time


                                mTimePicker.show();
                            }
                        });

                        tv2.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (timevalue1.length() > 0) {

                                    SQLiteDatabase sqldb1 = db.getWritableDatabase();
                                    ContentValues values1 = new ContentValues();
                                    values1.put(DBManager.TableInfo.KEYID, ID1);
                                    values1.put(DBManager.TableInfo.AppointmentId, getAlldataList.get(position).get("appid"));
                                    values1.put(DBManager.TableInfo.DocumentId, getAlldataList.get(position).get("docid"));
                                    values1.put(DBManager.TableInfo.actual_time, timevalue1);
                                    values1.put(DBManager.TableInfo.KEY_LOGIN_USER, username2);
                                    String conditionw = DBManager.TableInfo.AppointmentId + " =?";
                                    Cursor cursorw = sqldb1.query(ACTUAL_TIME, null, conditionw, new String[]{DBManager.TableInfo.actual_time}, null, null, null);
                                    sqldb1.insert(ACTUAL_TIME, null, values1);

                                    cursorw.close();
                                    sqldb1.close();
                                    reFreshReload();

                                    Intent intent = new Intent(getActivity().getApplicationContext(), fetchactualtime.class);
                                    getActivity().getApplicationContext().startService(intent);
                                    popupWindow.dismiss();

                                } else {
                                    Toast.makeText(getActivity(), "Please Enter the Time", Toast.LENGTH_SHORT).show();
                                    popupWindow.dismiss();
                                }

                            }

                        });

                        tv3.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                popupWindow.dismiss();
                            }
                        });

                        popupWindow.setFocusable(true);

                        popupWindow.setBackgroundDrawable(new ColorDrawable());

                        int location[] = new int[2];

                        v.getLocationOnScreen(location);


                        popupWindow.showAtLocation(v, Gravity.NO_GRAVITY,
                                location[0], location[1] + v.getHeight());

                    }
//
//

                });


                return v;
            }

        };

        tasklist.setAdapter(adpt);

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

    public void reportkey(int position) {
        Intent i = new Intent(getActivity().getApplicationContext(), Details.class);
        i.putExtra("ReportToken", getAlldataList.get(position).get("Rtoken"));
        i.putExtra("ReportKey", getAlldataList.get(position).get("rkey"));
        i.putExtra("Uname", getAlldataList.get(position).get("name"));
        i.putExtra("Umail", getAlldataList.get(position).get("mail"));
        i.putExtra("Ucontact", getAlldataList.get(position).get("contact"));
        i.putExtra("PropertyAddress", getAlldataList.get(position).get("padd"));
        i.putExtra("OwnerName", getAlldataList.get(position).get("oname"));
        i.putExtra("OwnerContact", getAlldataList.get(position).get("ocontact"));
        i.putExtra("OwnerEmail", getAlldataList.get(position).get("omail"));
        i.putExtra("OwnerAddress", getAlldataList.get(position).get("oadd"));
        i.putExtra("TenantName", getAlldataList.get(position).get("tname"));
        i.putExtra("TenantContact", getAlldataList.get(position).get("tcontact"));
        i.putExtra("TenantEmail", getAlldataList.get(position).get("tmail"));
        i.putExtra("TenantAddress", getAlldataList.get(position).get("tadd"));
        i.putExtra("AppointmentAddress", getAlldataList.get(position).get("appadress"));
        i.putExtra("StartDate", getAlldataList.get(position).get("sdate"));
        i.putExtra("StartTime1", getAlldataList.get(position).get("stime1"));
        i.putExtra("StartTime2", getAlldataList.get(position).get("stime2"));
        i.putExtra("apptype", getAlldataList.get(position).get("apptype"));
        i.putExtra("docid", getAlldataList.get(position).get("docid"));
        i.putExtra("landmark", getAlldataList.get(position).get("landmark"));
        i.putExtra("contactname", getAlldataList.get(position).get("contact_name"));
        i.putExtra("contactemail", getAlldataList.get(position).get("contact_email"));
        i.putExtra("contactno", getAlldataList.get(position).get("contact_no"));
        i.putExtra("appoint", getAlldataList.get(position).get("appid"));
        i.putExtra("Role", getAlldataList.get(position).get("role"));
        startActivity(i);
    }


    public void checkin(int position) {
        if (getAlldataList.get(position).get("apptype").equals("2")) {


            Intent i1 = new Intent(getActivity().getApplicationContext(), newstatusinfo.class);

            i1.putExtra("document_id", getAlldataList.get(position).get("docid"));
            i1.putExtra("appointment_id", getAlldataList.get(position).get("appid"));
            i1.putExtra("rkey", getAlldataList.get(position).get("rkey"));
            i1.putExtra("flag", flag);
            i1.putExtra("content", content1);
            i1.putExtra ("paymentflag","nopayment");
            i1.putExtra("StartDate", getAlldataList.get(position).get("sdate"));
            i1.putExtra("amount", payamount);
            i1.putExtra("time",localTime);
            i1.putExtra("post_status", getAlldataList.get(position).get("post_status"));

            startActivityForResult(i1, 12345);

        } else {
            Intent intent = new Intent(getActivity().getApplicationContext(), StatusInfo.class);
            intent.putExtra("ReportKey", getAlldataList.get(position).get("rkey"));
            intent.putExtra("DocumentId", getAlldataList.get(position).get("docid"));
            intent.putExtra("flag", flag);
            intent.putExtra("amount", payamount);
            intent.putExtra ("paymentflag","nopayment");
            intent.putExtra("BiometricComp", getAlldataList.get(position).get("biocomp"));
            intent.putExtra("BiometricComp1", getAlldataList.get(position).get("biocom1"));
            intent.putExtra("Reg_From_Comp", getAlldataList.get(position).get("regfromcomp"));
            intent.putExtra("Witness", getAlldataList.get(position).get("witness"));
            intent.putExtra("appointment_id", getAlldataList.get(position).get("appid"));
            intent.putExtra("post_status", getAlldataList.get(position).get("post_status"));

            startActivityForResult(intent, 12345);

        }

    }

    public void viewonly(int position) {
        if (getAlldataList.get(position).get("apptype").equals("2")) {


            Intent i1 = new Intent(getActivity().getApplicationContext(), newstatusinfo.class);

            i1.putExtra("document_id", getAlldataList.get(position).get("docid"));
            i1.putExtra("appointment_id", getAlldataList.get(position).get("appid"));
            i1.putExtra("rkey", getAlldataList.get(position).get("rkey"));
            i1.putExtra("flag", flag);
            i1.putExtra ("paymentflag","nopayment");
            i1.putExtra("StartDate", getAlldataList.get(position).get("sdate"));
            i1.putExtra("amount", payamount);
            i1.putExtra("content", content1);
            i1.putExtra("post_status", getAlldataList.get(position).get("post_status"));

            startActivityForResult(i1, 12345);
        } else {
            Intent intent = new Intent(getActivity().getApplicationContext(), StatusInfo.class);
            intent.putExtra("ReportKey", getAlldataList.get(position).get("rkey"));
            intent.putExtra("DocumentId", getAlldataList.get(position).get("docid"));
            intent.putExtra("BiometricComp", getAlldataList.get(position).get("biocomp"));
            intent.putExtra("BiometricComp1", getAlldataList.get(position).get("biocom1"));
            intent.putExtra("Reg_From_Comp", getAlldataList.get(position).get("regfromcomp"));
            intent.putExtra("Witness", getAlldataList.get(position).get("witness"));
            intent.putExtra("flag", flag);
            intent.putExtra ("paymentflag","nopayment");
            intent.putExtra("amount", payamount);
            intent.putExtra("appointment_id", getAlldataList.get(position).get("appid"));
            intent.putExtra("post_status", getAlldataList.get(position).get("post_status"));

            startActivityForResult(intent, 12345);

        }
    }

    public void comments(int position) {

        Intent intent = new Intent(getActivity().getApplicationContext(), CommentInfo.class);
        intent.putExtra("DocumentId", getAlldataList.get(position).get("docid"));
        intent.putExtra("Position", position);
        intent.putExtra("cust_contact_no", getAlldataList.get(position).get("ocontact"));
        intent.putExtra("value", "Biometric");
        intent.putExtra("ReportKey", getAlldataList.get(position).get("rkey"));
        startActivityForResult(intent, 12345);
    }




    public void acvr(int position) {
        if (GenericMethods.isConnected(getActivity())) {

            if ((getAlldataList.get(position).get("latitude").equals("null") && getAlldataList.get(position).get("longitude").equals("null")) || getAlldataList.get(position).get("latitude").equals("") && getAlldataList.get(position).get("longitude").equals("")) {
                Toast.makeText(getActivity(), "Customer Location Not Available-ACVR cannot be Updated", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(getActivity().getApplicationContext(), ACVReport.class);
                intent.putExtra("ReportKey", getAlldataList.get(position).get("rkey"));
                intent.putExtra("DocumentId", getAlldataList.get(position).get("docid"));
                intent.putExtra("AppointmentAddress", getAlldataList.get(position).get("appadress"));
                intent.putExtra("AppointmentId", getAlldataList.get(position).get("appid"));
                intent.putExtra("Distance", getAlldataList.get(position).get("distance"));
                intent.putExtra("Amount", getAlldataList.get(position).get("amount"));
                intent.putExtra("TransportType", getAlldataList.get(position).get("trasporttype"));
                intent.putExtra("ApointmentFor", getAlldataList.get(position).get("apointmentfor"));
                intent.putExtra("Executionerid", getAlldataList.get(position).get("exid"));
                if (position > 0) {

                    if (getAlldataList.get(position - 1).get("latitude").equals("null") && getAlldataList.get(position - 1).get("longitude").equals("null") || getAlldataList.get(position - 1).get("latitude").equals("") && getAlldataList.get(position - 1).get("longitude").equals("")) {
                        Toast.makeText(getActivity(), "Previous Location Not Available,Choose Other Location", Toast.LENGTH_SHORT).show();

                        intent.putExtra("PreviousAddress", getAlldataList.get(position - 1).get("appadress"));
                        intent.putExtra("lat", "null");
                        intent.putExtra("long", "null");
                    } else {
                        intent.putExtra("PreviousAddress", getAlldataList.get(position - 1).get("appadress"));
                        intent.putExtra("lat", getAlldataList.get(position - 1).get("latitude"));
                        intent.putExtra("long", getAlldataList.get(position - 1).get("longitude"));
                    }
                }
                intent.putExtra("position", position);
                intent.putExtra("CustomerLatitude", getAlldataList.get(position).get("latitude"));
                intent.putExtra("CustomerLatlong", getAlldataList.get(position).get("longitude"));
                intent.putExtra("value", "Biometric");

                startActivityForResult(intent, 12345);


            }


        } else {
            Toast.makeText(getActivity(), "Please turn on Internet!!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkreportkeystatus(int position) {

        boolean commentFlag = true;

        String doc_id = getAlldataList.get(position).get("docid");

        paymentlist = db.getPaymentReport1(db);
        for (int i = 0; i < paymentlist.size(); i++) {

            if (doc_id.equals(paymentlist.get(i).get("docid"))) {


                commentFlag = false;

            }


        }


        return commentFlag;
    }

    private boolean CheckCommentStatus(int position) {

        boolean commentFlag = true;

        String doc_id = getAlldataList.get(position).get("rkey");
        if (documentidcomment.contains(doc_id)) {

            ArrayList<Integer> positionlistcomments = new ArrayList<>();

            for (int i = 0; i < documentidcomment.size(); i++) {
                String temp1 = documentidcomment.get(i);

                if (doc_id != null && temp1.equalsIgnoreCase(doc_id)) {
                    positionlistcomments.add(i);
                }

            }
            for (int j = 0; j < positionlistcomments.size(); j++) {

                String strComment = getCommentDatalist.get(positionlistcomments.get(j)).get("CommentStatus");
                if (strComment.equals(GenericMethods.AsyncStatus)) {
                    commentFlag = false;
                    break;
                }
            }

        }
        return commentFlag;


    }

    private void notificationautomaticcancel(final int position) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (db.getnotificationtable(db).size() > 0) {

                    System.out.println("entered notification for loop");


                    calander = Calendar.getInstance();
                    simpleDateFormat = new SimpleDateFormat("HH:mm");

                    String curr_time = simpleDateFormat.format(calander.getTime());
                    System.out.println("curr_time:" + curr_time);
                    for (int i = 0; i < db.getnotificationtable(db).size(); i++) {
                        sdate = db.getnotificationtable(db).get(i).get("time");
                        System.out.println("notify_time:" + sdate);
                        String appid = db.getnotificationtable(db).get(i).get("appid");
                        SimpleDateFormat inputParser = new SimpleDateFormat("HH:mm", Locale.getDefault());
                        try {
                            date = inputParser.parse(sdate);
                            date1 = inputParser.parse(curr_time);
                        } catch (ParseException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        if (appid.equals(db.getAllOlderist(db).get(position).get("appid"))) {


                            if (date.equals(date1) || date.before(date1)) {

                                SQLiteDatabase sqldb = db.getWritableDatabase();

                                ContentValues values2 = new ContentValues();
                                values2.put(DBManager.TableInfo.notification, "false");
                                long result = sqldb.update(TABLE_NAME_APPOINTMENT, values2, DBManager.TableInfo.AppointmentId + "=?", new String[]{appid});
                                sqldb.close();

                                System.out.println(db.getAllOlderist(db).get(position).get("notify"));
                                SQLiteDatabase marriagedocu = db.getWritableDatabase();
                                marriagedocu.delete(TABLE_NOTIFICATION, DBManager.TableInfo.notification_appid + "=?", new String[]{appid});
                                System.out.println("deleted" + result);

                                Toast.makeText(getActivity(), "Updating view.. notification gets removed for " + " " + getAlldataList.get(position).get("rkey"), Toast.LENGTH_LONG).show();

                            }

                        }


                    }


                }
                GenericMethods.scrollposition = position;
                reFreshReload();
            }
        });


    }

    public void scrollposition(int position) {


        System.out.println("pos from scroll fun:" + position);


        tasklist.setSmoothScrollbarEnabled(true);
        tasklist.smoothScrollToPosition(position);
        progressDialog.dismiss();

        singleTask.schedule(new TimerTask() {
            @Override
            public void run() {
                GenericMethods.scrollposition = null;
            }
        }, 2000);

    }

    private void viewDetails() {


        db = new DBOperation(getActivity());
        reportkey.clear();
        documentidcomment.clear();
        documentidpay.clear();
        getAlldataList.clear();

        if (GenericMethods.olderrr.equals("true")) {

            getAlldataList = GenericMethods.getAllOlderlist1;
            System.out.println("getolder search" + GenericMethods.getAllOlderlist1);

            for (int i = 0; i < getAlldataList.size(); i++) {

                reportkey.add(getAlldataList.get(i).get("rkey"));

            }
        } else if (GenericMethods.olderrr.equals("false")) {

            getAlldataList = db.getAllOlderist(db);

            for (int i = 0; i < getAlldataList.size(); i++) {

                reportkey.add(getAlldataList.get(i).get("rkey"));

            }
        }
        getCommentDatalist = db.getCommentlist(db);

        for (int i = 0; i < getCommentDatalist.size(); i++) {

            documentidcomment.add(getCommentDatalist.get(i).get("Did"));

        }


    }

    private void viewDetails1(final String news, final String from, String content, final int position) {


        reportkey0.clear();
        reportkey1.clear();

        reportkey0.add(getAlldataList.get(position).get("rkey"));

        rkey = reportkey0.get(0);
        apptype = getAlldataList.get(position).get("apptype");
        System.out.println("apptype:" + apptype + rkey);
        id1 = Integer.valueOf(getAlldataList.get(position).get("appid"));
        poststatus = getAlldataList.get(position).get("post_status");
        reportkey1.add(getAlldataList.get(position).get("sdate"));
        reportkey1.add(getAlldataList.get(position).get("stime1"));
        reportkey1.add(getAlldataList.get(position).get("docid"));

        id = reportkey1.get(0);
        document = reportkey1.get(2);


        String date1 = reportkey1.get(0);
        year = date1.substring(0, 4);
        month = date1.substring(5, 7);
        day = date1.substring(8);


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
        } else {
            hour1 = t_hour1;
            min1 = t_min1;
        }

        if (click == 0) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Updating View...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();
            Toast.makeText(getActivity(), "Updating view..Notification setting for" + " " + getAlldataList.get(position).get("rkey"), Toast.LENGTH_LONG).show();


            for (int j = 0; j < repeat; j++) {


                temp_min1 += intervalvalue;


                if (temp_min1 == 60) {

                    temp_min1 = 0;
                    temp_hour1 += 1;

                }
                if (temp_min1 == 120) {

                    temp_min1 = 0;
                    temp_hour1 += 2;
                }
                System.out.println("repeat:" + repeat);


                finalhour = hour1 + temp_hour1;
                finalmin = min1 + temp_min1;
                final int finalapp = id1 + j;
                final int finalHour = finalhour;
                final int finalMin = finalmin;
                final String finalContent1 = content;
                System.out.println("final hour:" + finalhour + "final min:" + finalmin);
                System.out.println("final app:" + finalapp);

                notification1(finalHour, finalMin, finalapp, rkey, document, id, news, from, finalContent1, apptype);


            }

            repeat = 0;
            intervalvalue = 0;


            System.out.println("apptype1:" + apptype);
            final int finalHour1 = finalhour;
            final int finalMin1 = finalmin;

            SQLiteDatabase sqldb1 = db.getWritableDatabase();
            ContentValues values1 = new ContentValues();
            values1.put(DBManager.TableInfo.KEYID, ID1);
            values1.put(DBManager.TableInfo.notification_appid, id1);
            values1.put(DBManager.TableInfo.notification_interval, intervalvalue);
            values1.put(DBManager.TableInfo.notification_repeat, repeat);
            values1.put(DBManager.TableInfo.notification_time, finalHour1 + ":" + finalMin1);
            values1.put(DBManager.TableInfo.notification_starttime, timevalue1);
            values1.put(DBManager.TableInfo.notificationvalue, "true");
            values1.put(DBManager.TableInfo.KEY_LOGIN_USER, username2);
            String conditionw = DBManager.TableInfo.notification_appid + " =?";
            Cursor cursorw = sqldb1.query(TABLE_NOTIFICATION, null, conditionw, new String[]{String.valueOf(id1)}, null, null, null);
            sqldb1.insert(TABLE_NOTIFICATION, null, values1);
            cursorw.close();
            sqldb1.close();
            System.out.println("appid:" + getAlldataList.get(position).get("appid"));
            SQLiteDatabase sqldb = db.getWritableDatabase();

            ContentValues values2 = new ContentValues();
            values2.put(DBManager.TableInfo.notification, "true");
            long result = sqldb.update(TABLE_NAME_APPOINTMENT, values2, DBManager.TableInfo.AppointmentId + "=?", new String[]{String.valueOf(id1)});
            sqldb.close();




            o_content = "";
            t_content = "";
            w_content = "";
            aw_content = "";
            poa_content = "";

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
            Toast.makeText(getActivity(), "Updating view..Notification cancelled for" + " " + getAlldataList.get(position).get("rkey"), Toast.LENGTH_LONG).show();

            final String finalContent = content;

            for (int k = 0; k < db.getnotificationtable(db).size(); k++) {

                int app = Integer.valueOf(db.getnotificationtable(db).get(k).get("appid"));

                for (int j = 0; j < repeat; j++) {


                    final int finalapp = app + j;
                    if (getAlldataList.get(position).get("appid").equals(db.getnotificationtable(db).get(k).get("appid"))) {


                        cancelnotification1(finalapp, rkey, document, id, news, from, finalContent, apptype);


                    }

                }

            }


            SQLiteDatabase marriagedocu = db.getWritableDatabase();
            marriagedocu.delete(TABLE_NOTIFICATION, DBManager.TableInfo.notification_appid + "=?", new String[]{String.valueOf(id1)});


            SQLiteDatabase sqldb = db.getWritableDatabase();
            ContentValues values2 = new ContentValues();
            values2.put(DBManager.TableInfo.notification, "false");
            long result = sqldb.update(TABLE_NAME_APPOINTMENT, values2, DBManager.TableInfo.AppointmentId + "=?", new String[]{getAlldataList.get(position).get("appid")});
            sqldb.close();

            o_content = "";
            t_content = "";
            poa_content = "";
            w_content = "";
            aw_content = "";
            GenericMethods.scrollposition = position;
            System.out.println("pos from view:" + GenericMethods.scrollposition);
            reFreshReload();

        }


    }


    @Override
    public void onDestroy() {

        super.onDestroy();
        if (repeatTask != null) {

            repeatTask.cancel();
        }
    }


    public void reFreshReload() {
        viewDetails();

        createAdpt();
    }


    public void notification1(int hour, int min, int id1, String rkey, String document, String id, String news, String from, String content, String type) {
        System.out.println("notification:");

        not_id = String.valueOf(id1);
        AlarmManager alarmManager = (AlarmManager) getActivity().getApplicationContext().getSystemService(Context.ALARM_SERVICE);

        Intent notificationIntent = new Intent(getActivity().getApplicationContext(), MyReceiver.class);

        notificationIntent.putExtra("rkey", rkey);
        notificationIntent.putExtra("document_id", document);
        notificationIntent.putExtra("news", news);
        notificationIntent.putExtra("appointment_id", not_id);
        notificationIntent.putExtra("from", from);
        notificationIntent.putExtra("content", content);
        notificationIntent.putExtra("post_status", poststatus);
        notificationIntent.putExtra("type", type);
        notificationIntent.putExtra("con", content1);
        notificationIntent.putExtra("StartDate", startdate);
        notificationIntent.putExtra("payamount",payamount);
        notificationIntent.addCategory("android.intent.category.DEFAULT");
        System.out.println("apptype_notify" + apptype);
        System.out.println("apptype_notify" + startdate);
        PendingIntent broadcast = PendingIntent.getBroadcast(getActivity().getApplicationContext(), id1, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, min);


        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);


    }


    public void cancelnotification1(int id1, String rkey, String document, String id, String news, String from, String content, String type) {

        not_id = String.valueOf(id1);
        AlarmManager alarmManager = (AlarmManager) getActivity().getApplicationContext().getSystemService(Context.ALARM_SERVICE);

        Intent notificationIntent = new Intent(getActivity().getApplicationContext(), MyReceiver.class);

        notificationIntent.putExtra("rkey", rkey);
        notificationIntent.putExtra("document_id", document);
        notificationIntent.putExtra("news", news);
        notificationIntent.putExtra("appointment_id", not_id);
        notificationIntent.putExtra("from", from);
        notificationIntent.putExtra("content", content);
        notificationIntent.putExtra("post_status", poststatus);
        notificationIntent.putExtra("type", type);
        notificationIntent.putExtra("con", content1);
        notificationIntent.putExtra("StartDate", startdate);
        notificationIntent.putExtra("payamount",payamount);
        notificationIntent.addCategory("android.intent.category.DEFAULT");


        PendingIntent broadcast = PendingIntent.getBroadcast(getActivity().getApplicationContext(), id1, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        broadcast.cancel();
        alarmManager.cancel(broadcast);

    }

}