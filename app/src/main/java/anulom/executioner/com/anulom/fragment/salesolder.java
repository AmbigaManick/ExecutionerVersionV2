//package anulom.executioner5.com3.anulom.fragment;
//
//import android.Manifest;
//import android.app.AlarmManager;
//import android.app.PendingIntent;
//import android.app.ProgressDialog;
//import android.app.TimePickerDialog;
//import android.content.ContentValues;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.pm.PackageManager;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.net.Uri;
//import android.os.Bundle;
//import android.preference.PreferenceManager;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.app.Fragment;
//import android.support.v4.content.ContextCompat;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.PopupWindow;
//import android.widget.RadioButton;
//import android.widget.RelativeLayout;
//import android.widget.Switch;
//import android.widget.TextView;
//import android.widget.TimePicker;
//import android.widget.Toast;
//
//import com.google.android.gms.common.api.GoogleApiClient;
//
//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Locale;
//import java.util.Timer;
//import java.util.TimerTask;
//
//import anulom.executioner5.com3.anulom.ACVReport;
//import anulom.executioner5.com3.anulom.CommentInfo;
//import anulom.executioner5.com3.anulom.Details1;
//import anulom.executioner5.com3.anulom.GenericMethods;
//import anulom.executioner5.com3.anulom.MapsActivity;
//import anulom.executioner5.com3.anulom.MyReceiver;
//import anulom.executioner5.com3.anulom.R;
//import anulom.executioner5.com3.anulom.database.DBManager;
//import anulom.executioner5.com3.anulom.database.DBOperation;
//import anulom.executioner5.com3.anulom.salesstatusinfo;
//import anulom.executioner5.com3.anulom.services.call;
//import anulom.executioner5.com3.anulom.services.fetchactualtime;
//
//import static anulom.executioner5.com3.anulom.database.DBManager.TableInfo.ACTUAL_TIME;
//import static anulom.executioner5.com3.anulom.database.DBManager.TableInfo.CALL;
//import static anulom.executioner5.com3.anulom.database.DBManager.TableInfo.TABLE_NOTIFICATION;
//import static com.google.android.gms.internal.zzir.runOnUiThread;
//
//public class salesolder extends Fragment{
//    public static salesolder thisToday = null;
//    String year, month, day, hour, min, sdate, id, owner, tenant, w1, w2, rkey, timevalue, timevalue1 = "", not_id, from = "Older", biocontent = "", content = "", o_content = "", t_content = "", w1_content = "", w2_content = "",
//            o_ = "", t_ = "", w1_ = "", w2_ = "", poststatus, Date, content1 = "Today", valu = "Sales", O_ = "", T_ = "", W_ = "", POA_ = "", AW_ = "", news = "Sales Olders's Appointments", A, nodetails1 = "No Today Appointments!!!";
//    protected GoogleApiClient mGoogleApiClient;
//    double latitude, currentlat, currentlong;
//    String LOG_TAG = "SalesOlder";
//    private ProgressDialog progressDialog;
//    int year1, month1,t_hour1=0,t_min1=0, day1, hour1, h2, h3, click, min1, id1, count = 0, repeat, flag, intervalvalue;
//    Date date = null, date1 = null;
//    Timer singleTask = new Timer();
//    ArrayList<HashMap<String, String>> getAlldataList = new ArrayList<>();
//
//    ArrayList<HashMap<String, String>> getCommentDatalist = null;
//    String curdate1, appid, time1, docid1, currentcall, ID1;
//    ListView tasklist1;
//    DBOperation db, database;
//    TextView comment;
//    private Calendar mcurrenttime;
//    Timer repeatTask = new Timer();
//    long repeatInterval = 1800000;
//    long startdelay = 10000;
//    private SharedPreferences usermail;
//
//    String provider;
//    private String username2 = "", password2 = "";
//    Calendar calendar, calander;
//    SimpleDateFormat simpleDateFormat;
//    ArrayList<HashMap<String, String>> paymentlist = null;
//    ArrayList<String> reportkey = new ArrayList<>();
//    ArrayList<String> reportkey0 = new ArrayList<>();
//    ArrayList<String> reportkey1 = new ArrayList<>();
//
//
//    ArrayList<String> documentidpay = new ArrayList<>();
//    ArrayList<String> documentidcomment = new ArrayList<>();
//    ArrayAdapter<String> adpt;
//    ArrayAdapter<String> adpt1;
//
//    Button time;
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
//        View rootView = inflater.inflate(R.layout.viewsalesdetails, container, false);
//
//        Calendar c = Calendar.getInstance();
//        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
//        curdate1 = df2.format(c.getTime());
//
//        tasklist1 = rootView.findViewById(R.id.tasklist);
//
//        reFreshReload();
//
//        return rootView;
//
//    }
//
//
//    private void createAdpt() {
//
//
//        adpt = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.tasksalesdetails1, reportkey) {
//
//            public View getView(final int position, final View convertView, final ViewGroup parent) {
//                // TODO Auto-generated method stub
//                final LayoutInflater inflater = LayoutInflater.from(getContext());
//
//                final View v = inflater.inflate(R.layout.tasksalesdetails, parent,false);
//
//                TextView R_key = v.findViewById(R.id.tvreportKey);
//
//                TextView status = v.findViewById(R.id.tvstatus1);
//                final TextView date = v.findViewById(R.id.tvdate1);
//                TextView schedule = v.findViewById(R.id.tvdate2);
//                comment = v.findViewById(R.id.tvcomment1);
//                LinearLayout contactperson = v.findViewById(R.id.contactperson);
//
//                TextView addpay = v.findViewById(R.id.tvAddpay1);
//                TextView acvr = v.findViewById(R.id.tvACVR1);
//                TextView map = v.findViewById(R.id.tvMap1);
//                TextView biometric = v.findViewById(R.id.bio2);
//
//                TextView contac1 = v.findViewById(R.id.contact1);
//                TextView appoint = v.findViewById(R.id.appointment);
//
//                time = v.findViewById(R.id.owner2);
//
//                usermail = PreferenceManager.getDefaultSharedPreferences(getActivity());
//                username2 = usermail.getString("username", "");
//                password2 = usermail.getString("password", "");
//
//                SharedPreferences pref = getActivity().getSharedPreferences("MyPref", 0); // 0 - for private mode
//                final SharedPreferences.Editor editor = pref.edit();
//                editor.putString("value", "Sales");
//                editor.apply();
//                final SharedPreferences.Editor editor1 = pref.edit();
//                editor1.putString("cust_contact_no", getAlldataList.get(position).get("contact_no"));
//                editor1.apply();
//                LinearLayout owner = v.findViewById(R.id.layout1);
//                LinearLayout tenant = v.findViewById(R.id.layout2);
//                ImageView img1 = v.findViewById(R.id.buttonimg);
//                ImageView img2 = v.findViewById(R.id.buttonimg1);
//                ImageView img3 = v.findViewById(R.id.buttonimg2);
//                final Button but3 = v.findViewById(R.id.buttonnotify1);
//                final Switch sw1 = v.findViewById(R.id.switch1);
//
//
//                if ((getAlldataList.get(position).get("notify").equals("true"))) {
//
//                    sw1.setChecked(true);
//                    System.out.println("notification set");
//
//                } else if ((getAlldataList.get(position).get("notify").equals("false"))) {
//                    sw1.setChecked(false);
//                }
//
//                sw1.setOnTouchListener(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View v, MotionEvent event) {
//                        return event.getActionMasked() == MotionEvent.ACTION_MOVE;
//                    }
//                });
//
//                sw1.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (sw1.isChecked()) {
//                            final LayoutInflater inflater = LayoutInflater.from(getContext());
//                            View popupView = inflater.inflate(R.layout.popup_layout1, parent,false);
//
//                            final PopupWindow popupWindow = new PopupWindow(popupView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//
//
//                            TextView tv = popupView.findViewById(R.id.textView7);
//                            TextView tv1 = popupView.findViewById(R.id.textView8);
//
//                            final TextView e2 = popupView.findViewById(R.id.editTime1);
//                            final TextView e3 = popupView.findViewById(R.id.editTime2);
//                            Button b1 = popupView.findViewById(R.id.buttontime1);
//                            Button b2 = popupView.findViewById(R.id.buttontime2);
//                            Button tv2 = popupView.findViewById(R.id.text8);
//                            Button tv3 = popupView.findViewById(R.id.text7);
//                            final TextView c1 = popupView.findViewById(R.id.checkBoxinterval);
//                            final RadioButton btime = popupView.findViewById(R.id.button);
//                            final RadioButton btime1 = popupView.findViewById(R.id.button2);
//                            final RadioButton btime2 = popupView.findViewById(R.id.button3);
//                            final TextView e4 = popupView.findViewById(R.id.textViewtimes);
//                            final RadioButton btimer1 = popupView.findViewById(R.id.buttonr1);
//                            final RadioButton btimer2 = popupView.findViewById(R.id.buttonr2);
//                            final RadioButton btimer3 = popupView.findViewById(R.id.buttonr3);
//                            final RadioButton btimer4 = popupView.findViewById(R.id.buttonr4);
//                            final RadioButton btimer5 = popupView.findViewById(R.id.buttonr5);
//
//                            b1.setOnClickListener(new OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//
//                                    mcurrenttime = Calendar.getInstance();
//
//                                    TimePickerDialog mTimePicker;
//                                    mTimePicker = new TimePickerDialog(salesolder.this.getContext(), new TimePickerDialog.OnTimeSetListener() {
//                                        @Override
//                                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
//                                            mcurrenttime.set(Calendar.HOUR_OF_DAY, selectedHour);
//                                            mcurrenttime.set(Calendar.MINUTE, selectedMinute);
//                                            timePicker.setIs24HourView(true);
//                                            e2.setText(selectedHour + ":" + selectedMinute);
//                                            timevalue1 = e2.getText().toString();
//                                            t_hour1 = selectedHour;
//                                            t_min1 = selectedMinute;
//                                        }
//                                    }, mcurrenttime.get(Calendar.HOUR_OF_DAY), mcurrenttime.get(Calendar.MINUTE), false);//Yes 24 hour time
//
//
//                                    mTimePicker.show();
//                                }
//                            });
//
//                            btime.setOnClickListener(new OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//
//                                    intervalvalue = 30;
//
//                                }
//                            });
//                            btime1.setOnClickListener(new OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//
//                                    intervalvalue = 60;
//
//                                }
//                            });
//                            btime2.setOnClickListener(new OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//
//                                    intervalvalue = 120;
//
//                                }
//                            });
//
//                            btimer1.setOnClickListener(new OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//
//                                    repeat = 1;
//
//                                }
//                            });
//                            btimer2.setOnClickListener(new OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//
//                                    repeat = 2;
//
//                                }
//                            });
//                            btimer3.setOnClickListener(new OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//
//                                    repeat = 3;
//
//                                }
//                            });
//                            btimer4.setOnClickListener(new OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//
//                                    repeat = 4;
//
//                                }
//                            });
//                            btimer5.setOnClickListener(new OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//
//                                    repeat = 5;
//
//                                }
//                            });
//
//                            tv2.setOnClickListener(new OnClickListener() {
//
//                                public void onClick(View v) {
//
//                                    click = 0;
//                                    calander = Calendar.getInstance();
//                                    simpleDateFormat = new SimpleDateFormat("HH:mm");
//
//                                    String curr_time = simpleDateFormat.format(calander.getTime());
//                                    if (e2.length() == 0) {
//                                        e2.setText(curr_time);
//
//
//                                    }
//
//                                    if ((btime.isChecked() || btime1.isChecked() || btime2.isChecked()) && (btimer1.isChecked() || btimer2.isChecked() || btimer3.isChecked() || btimer4.isChecked() || btimer5.isChecked())) {
//
//                                        viewDetails1(position);
//                                        popupWindow.dismiss();
//                                        sw1.setChecked(true);
//
//
//                                    } else {
//                                        Toast.makeText(getActivity(), "Please enter all fields", Toast.LENGTH_SHORT).show();
//
//                                    }
//
//
//                                }
//
//                            });
//
//                            tv3.setOnClickListener(new OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//
//                                    sw1.setChecked(false);
//                                    popupWindow.dismiss();
//
//
//                                }
//                            });
//
//                            popupWindow.setFocusable(true);
//
//
//                            popupWindow.setBackgroundDrawable(new ColorDrawable());
//
//                            int location[] = new int[2];
//
//                            v.getLocationOnScreen(location);
//
//
//                            sw1.setChecked(false);
//                            popupWindow.showAtLocation(v, Gravity.NO_GRAVITY,
//                                    location[0], location[1] + v.getHeight());
//
//                        } else {
//                            click = 1;
//                            viewDetails1(position);
//                            sw1.setChecked(false);
//                        }
//
//
//                    }
//                });
//
//
//                if (getAlldataList.get(position).get("contact_person").equals("No details")) {
//
//
//                } else if (getAlldataList.get(position).get("contact_person").equals("Details")) {
//
//
//                    contactperson.setVisibility(View.VISIBLE);
//                    contac1.setText(getAlldataList.get(position).get("contact_name"));
//                    contac1.setTextColor(Color.parseColor("#3b5998"));
//                    contac1.setAllCaps(true);
//                    img3.setVisibility(View.VISIBLE);
//                    contactperson.setOnClickListener(new OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            calendar = Calendar.getInstance();
//                            simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
//                            Date = simpleDateFormat.format(calendar.getTime());
//
//                            if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 123);
//                            } else if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
//
//
//                                Intent intent = new Intent(Intent.ACTION_CALL);
//                                intent.setData(Uri.parse("tel:" + getAlldataList.get(position).get("contact_no")));
//                                startActivity(intent);
//                                SQLiteDatabase sqldb1 = db.getWritableDatabase();
//                                ContentValues values1 = new ContentValues();
//                                values1.put(DBManager.TableInfo.KEYID, ID1);
//                                values1.put(DBManager.TableInfo.DocumentId, getAlldataList.get(position).get("document_id"));
//                                values1.put(DBManager.TableInfo.AppointmentId, getAlldataList.get(position).get("appid"));
//                                values1.put(DBManager.TableInfo.actual_time, timevalue1);
//                                values1.put(DBManager.TableInfo.call_time, Date);
//                                values1.put(DBManager.TableInfo.KEY_LOGIN_USER, username2);
//                                String conditionw = DBManager.TableInfo.AppointmentId + " =?";
//                                Cursor cursorw = sqldb1.query(CALL, null, conditionw, new String[]{DBManager.TableInfo.actual_time}, null, null, null);
//                                sqldb1.insert(CALL, null, values1);
//                                cursorw.close();
//                                sqldb1.close();
//                                Intent intent1 = new Intent(getActivity().getApplicationContext(), call.class);
//                                getActivity().getApplicationContext().startService(intent1);
//                            }
//                        }
//                    });
//
//
//                }
//
//
//                String date1 = getAlldataList.get(position).get("startdate");
//
//                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
//                SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
//                year = date1.substring(0, 4);
//                month = date1.substring(5, 7);
//                day = date1.substring(8);
//
//                Date date11 = null;
//                try {
//                    date11 = format1.parse(date1);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                String datest = getAlldataList.get(position).get("starttime");
//                SimpleDateFormat formatst = new SimpleDateFormat("HH:mm");
//                SimpleDateFormat formatst2 = new SimpleDateFormat("hh:mm");
//                Date stdate11 = null;
//                try {
//                    stdate11 = formatst.parse(datest);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                String dateend = getAlldataList.get(position).get("endtime");
//                SimpleDateFormat formatend = new SimpleDateFormat("HH:mm");
//                SimpleDateFormat formatend2 = new SimpleDateFormat("hh:mm a");
//
//                Date enddate11 = null;
//                try {
//                    enddate11 = formatend.parse(dateend);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                date.setText(format1.format(date11) + " " + "\n" + formatst2.format(stdate11) + "-" + formatend2.format(enddate11));
//
//                String strStatus = getAlldataList.get(position).get("syncstatus");
//                String strAcvrReportStatus = getAlldataList.get(position).get("acvrsync");
//
//
//                R_key.setText(getAlldataList.get(position).get("env"));
//
//                R_key.setTextColor(Color.parseColor("#3b5998"));
//
//                if (strStatus.equals("ASYNC")) {
//                    status.setBackgroundResource(R.drawable.statusred);
//                } else {
//                    status.setBackgroundResource(R.drawable.statusblue);
//                }
//
//                if (strAcvrReportStatus.equals("ASYNC")) {
//                    acvr.setBackgroundResource(R.drawable.reportred);
//                } else {
//                    acvr.setBackgroundResource(R.drawable.reportblue);
//                }
//
//                boolean commentStatus = CheckCommentStatus(position);
//                if (commentStatus) {
//                    comment.setBackgroundResource(R.drawable.commentblueee);
//                } else {
//                    comment.setBackgroundResource(R.drawable.commentsred);
//                }
////                comment.setBackgroundResource(R.drawable.greenpaymentt);
//                //addpay.setBackgroundResource(R.drawable.payblue);
////                comment.setText("Comments");
////                addpay.setText("Payment");
////                acvr.setText("Report");
//                map.setVisibility(View.VISIBLE);
//                map.setBackgroundResource(R.drawable.mapiconblue);
//                if (getAlldataList.get(position).get("apptype").equals("3")) {
//
//                    if (getAlldataList.get(position).get("poststatus").equals("null")) {
//                        o_ = "Appointment Not Updated";
//                        biometric.setText(o_);
//                    }
//
//                    if (getAlldataList.get(position).get("poststatus").equals("1")) {
//                        o_ = "Door Step Visit Done";
//                        biometric.setText(o_);
//                    }
//                    if (getAlldataList.get(position).get("poststatus").equals("0")) {
//                        o_ = "Visit Not Done";
//                        biometric.setText(o_);
//                    }
//                    if (getAlldataList.get(position).get("poststatus").equals("2")) {
//                        o_ = "No Show";
//                        biometric.setText(o_);
//                    }
//                    if (getAlldataList.get(position).get("poststatus").equals("3")) {
//                        o_ = "Cancelled by Customer";
//                        biometric.setText(o_);
//                    }
//
//
//                    biometric.setTextColor(Color.parseColor("#3b5998"));
//                }
//                R_key.setOnClickListener(new OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//                        // TODO Auto-generated method stub
//                        Intent i = new Intent(getActivity().getApplicationContext(), Details1.class);
//                        i.putExtra("ReportKey", getAlldataList.get(position).get("env"));
//                        i.putExtra("StartTime1", getAlldataList.get(position).get("starttime"));
//                        i.putExtra("StartTime2", getAlldataList.get(position).get("endtime"));
//                        i.putExtra("contactname", getAlldataList.get(position).get("contact_name"));
//                        i.putExtra("contactemail", getAlldataList.get(position).get("contact_email"));
//                        i.putExtra("contactno", getAlldataList.get(position).get("contact_no"));
//                        i.putExtra("docid", getAlldataList.get(position).get("document_id"));
//                        i.putExtra("apptype", getAlldataList.get(position).get("apptype"));
//                        i.putExtra("appointment_id", getAlldataList.get(position).get("appid"));
//                        i.putExtra("StartDate", getAlldataList.get(position).get("created_at"));
//                        i.putExtra("AppointmentAddress", getAlldataList.get(position).get("address"));
//                        i.putExtra("landmark", getAlldataList.get(position).get("landmark"));
//                        i.putExtra("apptype", getAlldataList.get(position).get("apptype"));
//                        System.out.println("apptype:" + getAlldataList.get(position).get("apptype"));
//                        startActivity(i);
//
//                    }
//                });
//
//
//                comment.setOnClickListener(new OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//
//
//                        Intent intent = new Intent(getActivity().getApplicationContext(), CommentInfo.class);
//                        intent.putExtra("Position", position);
//                        intent.putExtra("cust_contact_no", getAlldataList.get(position).get("contact_no"));
//                        intent.putExtra("DocumentId", getAlldataList.get(position).get("document_id"));
//                        intent.putExtra("ReportKey", getAlldataList.get(position).get("env"));
//                        intent.putExtra("value", "Sales");
//                        System.out.println("val;" + valu + getAlldataList.get(position).get("env"));
//                        startActivityForResult(intent, 12345);
//                    }
//
//                });
//
//
//                status.setOnClickListener(new OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//                        final LayoutInflater inflater = LayoutInflater.from(getContext());
//                        View popupView = inflater.inflate(R.layout.popout_layout3, parent,false);
//
//                        final PopupWindow popupWindow = new PopupWindow(popupView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//
//
//                        Button b1 = popupView.findViewById(R.id.bSearch);
//                        Button b2 = popupView.findViewById(R.id.bSearch1);
//
//                        b1.setOnClickListener(new OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//
//                                Calendar cal = Calendar.getInstance();
//                                Date currentLocalTime = cal.getTime();
//                                DateFormat date = new SimpleDateFormat("HH:mm a");
//                                String localTime = date.format(currentLocalTime);
//                                flag = 0;
//
//
//                                Intent i1 = new Intent(getActivity().getApplicationContext(), salesstatusinfo.class);
//
//                                i1.putExtra("document_id", getAlldataList.get(position).get("document_id"));
//                                i1.putExtra("appointment_id", getAlldataList.get(position).get("appid"));
//                                i1.putExtra("rkey", getAlldataList.get(position).get("env"));
//                                i1.putExtra("post_status", getAlldataList.get(position).get("poststatus"));
//                                i1.putExtra("flag", flag);
//                                i1.putExtra("time",localTime);
//                                popupWindow.dismiss();
//                                startActivityForResult(i1, 12345);
//
//
//                            }
//                        });
//
//                        b2.setOnClickListener(new OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                flag = 1;
//
//                                Intent i1 = new Intent(getActivity().getApplicationContext(), salesstatusinfo.class);
//
//                                i1.putExtra("document_id", getAlldataList.get(position).get("document_id"));
//                                i1.putExtra("appointment_id", getAlldataList.get(position).get("appid"));
//                                i1.putExtra("rkey", getAlldataList.get(position).get("env"));
//                                i1.putExtra("post_status", getAlldataList.get(position).get("poststatus"));
//                                i1.putExtra("flag", flag);
//                                popupWindow.dismiss();
//                                startActivityForResult(i1, 12345);
//                            }
//
//                        });
//
//
//                        popupWindow.setFocusable(true);
//
//                        popupWindow.setBackgroundDrawable(new ColorDrawable());
//
//                        int location[] = new int[2];
//
//                        v.getLocationOnScreen(location);
//
//                        popupWindow.showAtLocation(v, Gravity.CENTER, 300, 300);
//
//
//                    }
//
//                });
//
//
//
//                acvr.setOnClickListener(new OnClickListener() {
//
//                    @Override
//
//
//                    public void onClick(View v) {
//
//                        if (GenericMethods.isConnected(getActivity())) {
//
//                            if (getAlldataList.get(position).get("lati").equals("null") && getAlldataList.get(position).get("longi").equals("null") || getAlldataList.get(position).get("lati").equals(" ") && getAlldataList.get(position).get("longi").equals(" ")) {
//                                Toast.makeText(getActivity(), "Customer Location Not Available-ACVR Cannot be Updated", Toast.LENGTH_SHORT).show();
//                            } else {
//
//
//                                Intent intent = new Intent(getActivity().getApplicationContext(), ACVReport.class);
//                                intent.putExtra("ReportKey", getAlldataList.get(position).get("env"));
//                                intent.putExtra("DocumentId", getAlldataList.get(position).get("document_id"));
//                                intent.putExtra("AppointmentAddress", getAlldataList.get(position).get("address"));
//                                intent.putExtra("AppointmentId", getAlldataList.get(position).get("appid"));
//                                intent.putExtra("ApointmentFor", getAlldataList.get(position).get("app_for"));
//                                intent.putExtra("Distance", getAlldataList.get(position).get("distance"));
//                                intent.putExtra("Amount", getAlldataList.get(position).get("amount"));
//                                intent.putExtra("TransportType", getAlldataList.get(position).get("trans_type"));
//                                intent.putExtra("Executionerid", getAlldataList.get(position).get("exec_email"));
//                                if (position > 0) {
//
//                                    if(getAlldataList.get(position-1).get("lati").equals("null") && getAlldataList.get(position-1).get("longi").equals("null") || getAlldataList.get(position-1).get("lati").equals(" ") && getAlldataList.get(position-1).get("longi").equals(" ")){
//                                        Toast.makeText(getActivity(), "Customer Location Not Available-ACVR cannot be Updated", Toast.LENGTH_SHORT).show();
//                                        intent.putExtra("PreviousAddress", getAlldataList.get(position - 1).get("address"));
//                                        intent.putExtra("lat","null");
//                                        intent.putExtra("long", "null");
//                                    }
//                                    else
//                                    {
//                                        intent.putExtra("PreviousAddress", getAlldataList.get(position - 1).get("address"));
//                                        intent.putExtra("lat", getAlldataList.get(position - 1).get("lati"));
//                                        intent.putExtra("long", getAlldataList.get(position - 1).get("longi"));
//                                    }
//                                }
//
//                                intent.putExtra("position", position);
//                                intent.putExtra("CustomerLatitude", getAlldataList.get(position).get("lati"));
//                                intent.putExtra("CustomerLatlong", getAlldataList.get(position).get("longi"));
//                                intent.putExtra("value", "Sales");
//
//                                startActivityForResult(intent, 12345);
//
//
//                            }
//
//
//                        } else {
//                            Toast.makeText(getActivity(), "Please turn on Internet!!", Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//
//                });
//
//
//
//
//                map.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if(GenericMethods.isConnected(getActivity())) {
//                            Intent intent = new Intent(getActivity().getApplicationContext(), MapsActivity.class);
//
//
//                            if (getAlldataList.get(position).get("lati").equals("null") && getAlldataList.get(position).get("longi").equals("null") || getAlldataList.get(position).get("lati").equals(" ") && getAlldataList.get(position).get("longi").equals(" ")) {
//                                Toast.makeText(getActivity(), "Customer Location Not Available-Route Cannot be Generated For This Document", Toast.LENGTH_SHORT).show();
//                                intent.putExtra("AppointmentAddress", getAlldataList.get(position).get("address"));
//                                intent.putExtra("position", position);
//                                intent.putExtra("CustomerLatitude", "null");
//                                intent.putExtra("CustomerLatlong", "null");
//
//                            } else {
//                                intent.putExtra("AppointmentAddress", getAlldataList.get(position).get("address"));
//                                if (position > 0) {
//                                    if (!getAlldataList.get(position - 1).get("lati").equals("null") && !getAlldataList.get(position - 1).get("longi").equals("null") || !getAlldataList.get(position - 1).get("lati").equals(" ") && !getAlldataList.get(position - 1).get("longi").equals(" ")) {
//
//                                        intent.putExtra("PreviousAddress", getAlldataList.get(position - 1).get("address"));
//                                        intent.putExtra("lat", getAlldataList.get(position - 1).get("lati"));
//                                        intent.putExtra("long", getAlldataList.get(position - 1).get("longi"));
//
//                                    } else {
//                                        Toast.makeText(getActivity(), "Customer Location Not Available-Route Cannot be Generated For This Document", Toast.LENGTH_SHORT).show();
//                                        intent.putExtra("PreviousAddress", getAlldataList.get(position - 1).get("address"));
//                                        intent.putExtra("lat", "null");
//                                        intent.putExtra("long", "null");
//                                    }
//                                }
//                                intent.putExtra("position", position);
//                                intent.putExtra("CustomerLatitude", getAlldataList.get(position).get("lati"));
//                                intent.putExtra("CustomerLatlong", getAlldataList.get(position).get("longi"));
//
//                                startActivity(intent);
//
//                            }
//                        }else{
//                            Toast.makeText(getActivity(), "Please Turn on the Internet", Toast.LENGTH_SHORT).show();
//
//                        }
//
//                    }
//
//
//                });
////                addpay.setOnClickListener(new OnClickListener() {
////
////                    @Override
////                    public void onClick(View v) {
////                        Intent intent1 = new Intent(getActivity().getApplicationContext(), Pay.class);
////                        intent1.putExtra("DocumentId", getAlldataList.get(position).get("docid"));
////                        intent1.putExtra("ReportKey", getAlldataList.get(position).get("rkey"));
//////                        intent1.putExtra("Amount",)
////                        startActivityForResult(intent1, 12345);
////                    }
////                });
//
//
////                schedule.setOnClickListener(new OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                        Intent intent1 = new Intent(getActivity().getApplicationContext(), reschedule.class);
////                        intent1.putExtra("DocumentId", getAlldataList.get(position).get("docid"));
////                        intent1.putExtra("AppointmentId", getAlldataList.get(position).get("appid"));
//////                        System.out.println("appointmentid:" + getAlldataList.get(position).get("appid"));
////                        startActivityForResult(intent1, 12345);
////                    }
////                });
//
////                appoint.setOnClickListener(new OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                        Toast.makeText(getActivity().getApplicationContext(), "Please Turn On The Internet To Book Appointment!!", Toast.LENGTH_SHORT).show();
////                        Intent i = new Intent(getActivity().getApplicationContext(), Appointmentbooking.class);
////                        i.putExtra("DocumentId", getAlldataList.get(position).get("docid"));
////                        i.putExtra("apptype", getAlldataList.get(position).get("apptype"));
////                        i.putExtra("content", content1);
////                        i.putExtra("ReportKey", getAlldataList.get(position).get("rkey"));
////                        i.putExtra("AppointmentId", getAlldataList.get(position).get("appid"));
//////                        System.out.println("doc:"+getAlldataList.get(position).get("docid")+"type:"+getAlldataList.get(position).get("apptype"));
////
////                        startActivity(i);
////                    }
////                });
//                time.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        final LayoutInflater inflater = LayoutInflater.from(getContext());
//                        View popupView = inflater.inflate(R.layout.popup_layout, parent,false);
//
//                        final PopupWindow popupWindow = new PopupWindow(popupView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//
//
//                      TextView tv =  popupView.findViewById(R.id.textView7);
//                        TextView tv1 = popupView.findViewById(R.id.textView8);
//
//                        final TextView e2 = popupView.findViewById(R.id.editTime1);
//                        final TextView e3 = popupView.findViewById(R.id.editTime2);
//                        Button b1 = popupView.findViewById(R.id.buttontime1);
//                        Button b2 = popupView.findViewById(R.id.buttontime2);
//                        Button tv2 = popupView.findViewById(R.id.text8);
//                        Button tv3 = popupView.findViewById(R.id.text7);
//                        b1.setOnClickListener(new OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//
//                                mcurrenttime = Calendar.getInstance();
//
//                                TimePickerDialog mTimePicker;
//                                mTimePicker = new TimePickerDialog(salesolder.this.getContext(), new TimePickerDialog.OnTimeSetListener() {
//
//                                    @Override
//                                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
//                                        mcurrenttime.set(Calendar.HOUR_OF_DAY, selectedHour);
//                                        mcurrenttime.set(Calendar.MINUTE, selectedMinute);
//                                        timePicker.setIs24HourView(true);
//                                        e2.setText(selectedHour + ":" + selectedMinute);
//                                        timevalue1 = e2.getText().toString();
//                                    }
//                                }, mcurrenttime.get(Calendar.HOUR_OF_DAY), mcurrenttime.get(Calendar.MINUTE), false);//Yes 24 hour time
//
//
//                                mTimePicker.show();
//                            }
//                        });
//
//                        tv2.setOnClickListener(new OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                if (timevalue1.length() > 0) {
//                                    SQLiteDatabase sqldb1 = db.getWritableDatabase();
//                                    ContentValues values1 = new ContentValues();
//                                    values1.put(DBManager.TableInfo.KEYID, ID1);
//                                    values1.put(DBManager.TableInfo.AppointmentId, getAlldataList.get(position).get("appid"));
//                                    values1.put(DBManager.TableInfo.DocumentId, getAlldataList.get(position).get("document_id"));
//                                    values1.put(DBManager.TableInfo.actual_time, timevalue1);
//                                    values1.put(DBManager.TableInfo.KEY_LOGIN_USER, username2);
//                                    String conditionw = DBManager.TableInfo.AppointmentId + " =?";
//                                    Cursor cursorw = sqldb1.query(ACTUAL_TIME, null, conditionw, new String[]{DBManager.TableInfo.actual_time}, null, null, null);
//                                    sqldb1.insert(ACTUAL_TIME, null, values1);
//                                    cursorw.close();
//                                    sqldb1.close();
//                                    reFreshReload();
//
//                                    Intent intent = new Intent(getActivity().getApplicationContext(), fetchactualtime.class);
//                                    getActivity().getApplicationContext().startService(intent);
//
//                                    popupWindow.dismiss();
//                                } else {
//                                    Toast.makeText(getActivity(), "Please Enter the Time", Toast.LENGTH_SHORT).show();
//
//                                }
//                            }
//
//                        });
//
//                        tv3.setOnClickListener(new OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                popupWindow.dismiss();
//                            }
//                        });
//
//                        popupWindow.setFocusable(true);
//
//
//                        popupWindow.setBackgroundDrawable(new ColorDrawable());
//
//                        int location[] = new int[2];
//
//
//                        v.getLocationOnScreen(location);
//
//
//                        popupWindow.showAtLocation(v, Gravity.NO_GRAVITY,
//                                location[0], location[1] + v.getHeight());
//
//                    }
////
////
//
//                });
//
//                return v;
//            }
//
//        };
//
//        tasklist1.setAdapter(adpt);
//        singleTask.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                if (GenericMethods.scrollposition != null) {
//                    scrollposition(GenericMethods.scrollposition);
//                    System.out.println("pos from size:" + GenericMethods.scrollposition);
//                }
//            }
//        }, 2000);
//
//    }
//
//
//
//
//    private void notificationautomaticcancel(final int position) {
//
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if (db.getnotificationtable(db).size() > 0) {
//
//                    calander = Calendar.getInstance();
//                    simpleDateFormat = new SimpleDateFormat("HH:mm");
//
//                    String curr_time = simpleDateFormat.format(calander.getTime());
//
//                    for (int i = 0; i < db.getnotificationtable(db).size(); i++) {
//                        sdate = db.getnotificationtable(db).get(i).get("time");
//                        String appid = db.getnotificationtable(db).get(i).get("appid");
//                        SimpleDateFormat inputParser = new SimpleDateFormat("HH:mm", Locale.US);
//                        try {
//                            date = inputParser.parse(sdate);
//                            date1 = inputParser.parse(curr_time);
//                        } catch (ParseException e) {
//                            // TODO Auto-generated catch block
//                            e.printStackTrace();
//                        }
//
//
//                        if (appid.equals(db.getAllsalesOlderist(db).get(position).get("appid"))) {
//
//                            if (date.equals(date1) || date.before(date1)) {
//
//                                SQLiteDatabase sqldb2 = db.getWritableDatabase();
//                                ContentValues values2 = new ContentValues();
//                                values2.put(DBManager.TableInfo.notification, "false");
//                                int count4 = sqldb2.update(DBManager.TableInfo.TABLE_SALES_APPOINTMENT, values2, DBManager.TableInfo.db_sales_appointment_id + "=?", new String[]{db.getAllsalesOlderist(db).get(position).get("appid")});
//                                sqldb2.close();
//
//                                SQLiteDatabase marriagedocu = db.getWritableDatabase();
//                                marriagedocu.delete(TABLE_NOTIFICATION, DBManager.TableInfo.notification_appid + "=?", new String[]{appid});
//                                Toast.makeText(getActivity(), "Updating view.. notification gets removed for "+getAlldataList.get(position).get("env"), Toast.LENGTH_LONG).show();
//
//
//
//                            }
//
//                        }
//
//
//
//
//                    }
//
//
//                }
//
//                GenericMethods.scrollposition = position;
//                reFreshReload();
//            }
//        });
//
//    }
//
//
//    private void viewDetails() {
//        db = new DBOperation(getActivity());
//        reportkey.clear();
//        documentidcomment.clear();
//        documentidpay.clear();
//        getAlldataList.clear();
//
//        if (GenericMethods.salesolderrr.equals("true")) {
//
//            getAlldataList = GenericMethods.getAllsalesolderlist1;
//
//            for (int i = 0; i < getAlldataList.size(); i++) {
//
//
//                reportkey.add(getAlldataList.get(i).get("env"));
//
//            }
//        } else if (GenericMethods.salesolderrr.equals("false")) {
//            getAlldataList = db.getAllsalesOlderist(db);
//
//            for (int i = 0; i < getAlldataList.size(); i++) {
//
//
//                reportkey.add(getAlldataList.get(i).get("env"));
//
//            }
//        }
//
//        getCommentDatalist = db.getsalesCommentlist(db);
//        for (int i = 0; i < getCommentDatalist.size(); i++) {
//            documentidcomment.add(getCommentDatalist.get(i).get("Did"));
//        }
//
//
//    }
//
//    private void viewDetails1(final int position) {
//
//        db = new DBOperation(getActivity());
//        database = new DBOperation(getActivity());
//
//        reportkey0.clear();
//        getAlldataList = db.getAllsalesOlderist(db);
//
//        reportkey0.add(getAlldataList.get(position).get("env"));
//        rkey = reportkey0.get(0);
//        poststatus = getAlldataList.get(position).get("poststatus");
//        id1 = Integer.valueOf(getAlldataList.get(position).get("appid"));
//        reportkey1.add(getAlldataList.get(position).get("created_at"));
//        reportkey1.add(getAlldataList.get(position).get("starttime"));
//        reportkey1.add(getAlldataList.get(position).get("document_id"));
//
//        id = reportkey1.get(2);
//        String date1 = reportkey1.get(0);
//        year = date1.substring(0, 4);
//        month = date1.substring(5, 7);
//        day = date1.substring(8, 10);
//
//
//        year1 = Integer.parseInt(year);
//        month1 = Integer.valueOf(month);
//        day1 = Integer.valueOf(day);
//        calander = Calendar.getInstance();
//        simpleDateFormat = new SimpleDateFormat("hh:mm");
//
//        String curr_time = simpleDateFormat.format(calander.getTime());
//
//        if (t_hour1 == 0 && t_min1 == 0) {
//            hour = curr_time.substring(0, 2);
//            min = curr_time.substring(3);
//            hour1 = Integer.valueOf(hour);
//
//            if (hour1 < 9) {
//
//                hour1 = hour1 + 12;
//            }
//
//            min1 = Integer.valueOf(min);
//        }else {
//            hour1 = t_hour1;
//            min1 = t_min1;
//        }
//
//
//
//        content = rkey+"Sales Appointment Status is Pending";
//        if (getAlldataList.get(position).get("poststatus").equals("null")) {
//
//            int temp_hour1 = 0, temp_min1 = 0, finalhour = 0, finalmin = 0, app;
//
//            if (click == 0) {
//
//                progressDialog = new ProgressDialog(getActivity());
//                progressDialog.setMessage("Updating View...");
//                progressDialog.setIndeterminate(false);
//                progressDialog.setCancelable(true);
//                progressDialog.show();
//                Toast.makeText(getActivity(), "Updating view..Notification setting for"+" "+getAlldataList.get(position).get("env"), Toast.LENGTH_LONG).show();
//
//                for (int i = 0; i < repeat; i++) {
//
//
//                    temp_min1 += intervalvalue;
//
//                    if (temp_min1 == 60) {
//
//                        temp_min1 = 0;
//                        temp_hour1 += 1;
//
//                    } else if (temp_min1 == 120) {
//
//                        temp_min1 = 0;
//                        temp_hour1 += 2;
//                    }
//
//                    finalhour = hour1 + temp_hour1;
//                    finalmin = min1 + temp_min1;
//
//
//                    int finalapp = id1 + i;
//                    notification1(finalhour, finalmin, finalapp, rkey, id, news, from, content, valu);
//
//                }
//                SQLiteDatabase sqldb1 = db.getWritableDatabase();
//                ContentValues values1 = new ContentValues();
//                values1.put(DBManager.TableInfo.KEYID, ID1);
//                values1.put(DBManager.TableInfo.notification_appid, getAlldataList.get(position).get("appid"));
//                values1.put(DBManager.TableInfo.notification_interval, intervalvalue);
//                values1.put(DBManager.TableInfo.notification_repeat, repeat);
//                values1.put(DBManager.TableInfo.notification_time, finalhour + ":" + finalmin);
//                values1.put(DBManager.TableInfo.notification_starttime, timevalue1);
//                values1.put(DBManager.TableInfo.KEY_LOGIN_USER, username2);
//                String conditionw = DBManager.TableInfo.KEY_LOGIN_USER + " =?";
//                Cursor cursorw = sqldb1.query(TABLE_NOTIFICATION, null, conditionw, new String[]{username2}, null, null, null);
//                sqldb1.insert(TABLE_NOTIFICATION, null, values1);
//                cursorw.close();
//                sqldb1.close();
//
//                SQLiteDatabase sqldb2 = db.getWritableDatabase();
//                ContentValues values2 = new ContentValues();
//                values2.put(DBManager.TableInfo.notification, "true");
//                int count4 = sqldb2.update(DBManager.TableInfo.TABLE_SALES_APPOINTMENT, values2, DBManager.TableInfo.db_sales_appointment_id + "=?", new String[]{String.valueOf(id1)});
//                sqldb2.close();
//                GenericMethods.scrollposition = position;
//                System.out.println("pos from view:" + GenericMethods.scrollposition);
//                reFreshReload();
//                repeatTask.scheduleAtFixedRate(new TimerTask() {
//                    @Override
//                    public void run() {
//
//                        notificationautomaticcancel(position);
//
//
//                    }
//
//
//                }, startdelay, repeatInterval);
//
//            } else if (click == 1) {
//
//                progressDialog = new ProgressDialog(getActivity());
//                progressDialog.setMessage("Updating View...");
//                progressDialog.setIndeterminate(false);
//                progressDialog.setCancelable(true);
//                progressDialog.show();
//                Toast.makeText(getActivity(), "Updating view..Notification removed for"+" "+getAlldataList.get(position).get("env"), Toast.LENGTH_LONG).show();
//
//
//                for (int j = 0; j < db.getnotificationtable(db).size(); j++) {
//
//                    app = Integer.valueOf(db.getnotificationtable(db).get(j).get("appid"));
//
//                    for (int i = 0; i < repeat; i++) {
//
//                        int finalapp = app + i;
//                        if (getAlldataList.get(position).get("appid").equals(db.getnotificationtable(db).get(j).get("appid"))) {
//
//                            cancelnotification1(finalapp, rkey, id, news, from, content, valu);
//
//
//                        }
//
//                    }
//                }
//
//
//                SQLiteDatabase marriagedocu = db.getWritableDatabase();
//                marriagedocu.delete(TABLE_NOTIFICATION, DBManager.TableInfo.notification_appid + "=?", new String[]{getAlldataList.get(position).get("appid")});
//
//                SQLiteDatabase sqldb2 = db.getWritableDatabase();
//                ContentValues values2 = new ContentValues();
//                values2.put(DBManager.TableInfo.notification, "false");
//                int count4 = sqldb2.update(DBManager.TableInfo.TABLE_SALES_APPOINTMENT, values2, DBManager.TableInfo.db_sales_appointment_id + "=?", new String[]{String.valueOf(id1)});
//                sqldb2.close();
//                GenericMethods.scrollposition = position;
//                System.out.println("pos from view:" + GenericMethods.scrollposition);
//                reFreshReload();
//            } else {
//                Toast.makeText(getActivity(), "Notification cannot be Created for this Appointment", Toast.LENGTH_SHORT).show();
//
//            }
//
//        }
//
//
//    }
//    public void scrollposition(int position) {
//
//
//        System.out.println("pos from scroll fun:" + position);
//
//
//        tasklist1.setSmoothScrollbarEnabled(true);
//        tasklist1.smoothScrollToPosition(position);
//        progressDialog.dismiss();
//        singleTask.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                GenericMethods.scrollposition = null;
//            }
//        }, 2000);
//
//    }
//
//    public void onResume() {
//        // TODO Auto-generated method stub
//        super.onResume();
//        thisToday = this;
//    }
//
//    public void onPause() {
//        // TODO Auto-generated method stub
//        super.onPause();
//        thisToday = null;
//    }
//
//
//    private boolean CheckCommentStatus(int position) {
//        boolean commentFlag = true;
//
//        String doc_id = getAlldataList.get(position).get("env");
//
//        if (documentidcomment.contains(doc_id)) {
//
//            ArrayList<Integer> positionlistcomments = new ArrayList<>();
//
//            for (int i = 0; i < documentidcomment.size(); i++) {
//                String temp1 = documentidcomment.get(i);
//
//                if (doc_id != null && temp1.equalsIgnoreCase(doc_id)) {
//                    positionlistcomments.add(i);
//                }
//            }
//
//            for (int j = 0; j < positionlistcomments.size(); j++) {
//
//                String strComment = getCommentDatalist.get(positionlistcomments.get(j)).get("CommentStatus");
//                if (strComment.equals(GenericMethods.AsyncStatus)) {
//                    commentFlag = false;
//                    break;
//                }
//            }
//
//        }
//        return commentFlag;
//    }
//
//    public void reFreshReload() {
//        viewDetails();
//        createAdpt();
//
//    }
//
//
//    @Override
//    public void onDestroy() {
//
//        super.onDestroy();
//        if (repeatTask != null) {
//
//            repeatTask.cancel();
//        }
//    }
//
//
//    public void notification1(int hour, int min, int id1, String rkey, String id, String news, String from, String content, String type) {
//
//
//        not_id = String.valueOf(id1);
//        AlarmManager alarmManager = (AlarmManager) getActivity().getApplicationContext().getSystemService(Context.ALARM_SERVICE);
//
//        Intent notificationIntent = new Intent(getActivity().getApplicationContext(), MyReceiver.class);
//
//        notificationIntent.putExtra("rkey", rkey);
//        notificationIntent.putExtra("document_id", id);
//        notificationIntent.putExtra("news", news);
//        notificationIntent.putExtra("appointment_id", not_id);
//        notificationIntent.putExtra("from", from);
//        notificationIntent.putExtra("content", content);
//        notificationIntent.putExtra("post_status", poststatus);
//        notificationIntent.putExtra("type", type);
//
//        notificationIntent.addCategory("android.intent.category.DEFAULT");
//
//        PendingIntent broadcast = PendingIntent.getBroadcast(getActivity().getApplicationContext(), id1, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        Calendar cal = Calendar.getInstance();
//        cal.set(Calendar.HOUR_OF_DAY, hour);
//        cal.set(Calendar.MINUTE, min);
//
//
//        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);
//
//
//    }
//
//    public void cancelnotification1(int id1, String rkey, String id, String news, String from, String content, String type) {
//
//        not_id = String.valueOf(id1);
//        AlarmManager alarmManager = (AlarmManager) getActivity().getApplicationContext().getSystemService(Context.ALARM_SERVICE);
//
//        Intent notificationIntent = new Intent(getActivity().getApplicationContext(), MyReceiver.class);
//
//        notificationIntent.putExtra("rkey", rkey);
//        notificationIntent.putExtra("document_id", id);
//        notificationIntent.putExtra("news", news);
//        notificationIntent.putExtra("appointment_id", not_id);
//        notificationIntent.putExtra("from", from);
//        notificationIntent.putExtra("content", content);
//        notificationIntent.putExtra("post_status", poststatus);
//        notificationIntent.putExtra("type", type);
//        notificationIntent.addCategory("android.intent.category.DEFAULT");
//
//
//        PendingIntent broadcast = PendingIntent.getBroadcast(getActivity().getApplicationContext(), id1, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        broadcast.cancel();
//        alarmManager.cancel(broadcast);
//
//    }
//
//
//
//}