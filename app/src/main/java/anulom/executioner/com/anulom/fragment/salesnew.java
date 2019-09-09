//package anulom.executioner5.com3.anulom.fragment;
//
//import android.Manifest;
//import android.app.Activity;
//import android.app.TimePickerDialog;
//import android.content.ContentValues;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentSender;
//import android.content.SharedPreferences;
//import android.content.pm.PackageManager;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.location.Location;
//import android.location.LocationManager;
//import android.net.Uri;
//import android.os.Bundle;
//import android.preference.PreferenceManager;
//import android.provider.Settings;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.app.Fragment;
//import android.support.v4.content.ContextCompat;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.PopupWindow;
//import android.widget.RelativeLayout;
//import android.widget.Switch;
//import android.widget.TextView;
//import android.widget.TimePicker;
//import android.widget.Toast;
//
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.common.api.PendingResult;
//import com.google.android.gms.common.api.ResultCallback;
//import com.google.android.gms.common.api.Status;
//import com.google.android.gms.location.LocationListener;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.location.LocationSettingsRequest;
//import com.google.android.gms.location.LocationSettingsResult;
//import com.google.android.gms.location.LocationSettingsStatusCodes;
//import com.google.android.gms.location.places.Places;
//import com.google.android.gms.maps.MapsInitializer;
//
//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Timer;
//
//import anulom.executioner5.com3.anulom.ACVReport;
//import anulom.executioner5.com3.anulom.CommentInfo;
//import anulom.executioner5.com3.anulom.Details1;
//import anulom.executioner5.com3.anulom.GenericMethods;
//import anulom.executioner5.com3.anulom.MapsActivity;
//import anulom.executioner5.com3.anulom.R;
//import anulom.executioner5.com3.anulom.database.DBManager;
//import anulom.executioner5.com3.anulom.database.DBOperation;
//import anulom.executioner5.com3.anulom.salesstatusinfo;
//import anulom.executioner5.com3.anulom.services.call;
//import anulom.executioner5.com3.anulom.services.fetchactualtime;
//
//import static anulom.executioner5.com3.anulom.database.DBManager.TableInfo.ACTUAL_TIME;
//import static anulom.executioner5.com3.anulom.database.DBManager.TableInfo.CALL;
//
//public class salesnew extends Fragment implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
//
//    public static salesnew thisToday = null;
//    String year, month, day, hour, min, id, owner, tenant, w1, w2, rkey, timevalue, timevalue1 = "", not_id, from = "Today", biocontent = "", content = "", o_content = "", t_content = "", w1_content = "", w2_content = "",
//            o_ = "", t_ = "", w1_ = "", w2_ = "", Date, content1 = "Today", valu = "Sales", O_ = "", T_ = "", W_ = "", POA_ = "", AW_ = "", news = "Today's Appointments", A, nodetails1 = "No Today Appointments!!!";
//
//    int year1, month1, day1, hour1, h2, h3, min1, id1, count = 0, flag;
//    protected GoogleApiClient mGoogleApiClient;
//    double latitude, currentlat, currentlong;
//    String LOG_TAG = "Salesnew";
//    Location mLocation;
//    private final static int REQUEST_CHECK_SETTINGS_GPS = 0x1;
//    private final static int REQUEST_ID_MULTIPLE_PERMISSIONS = 0x2;
//    ArrayList<HashMap<String, String>> getAlldataList = new ArrayList<>();
//    ArrayList<HashMap<String, String>> getAlldataList1 = null;
//    ArrayList<HashMap<String, String>> getCommentDatalist = null;
//    String curdate1, appid, time1, docid1, currentcall, ID1;
//    ListView tasklist1;
//    DBOperation db, database;
//    TextView comment;
//    private Calendar mcurrenttime;
//    Timer repeatTask = new Timer();
//    long repeatInterval = 10800000;
//    long startdelay = 3600000;
//    private SharedPreferences usermail;
//    LocationManager locationManager;
//    String provider;
//    private String username2 = "", password2 = "";
//    Calendar calendar;
//    SimpleDateFormat simpleDateFormat;
//    ArrayList<HashMap<String, String>> paymentlist = null;
//    ArrayList<String> reportkey = new ArrayList<>();
//
//    ArrayList<String> documentidpay = new ArrayList<>();
//    ArrayList<String> documentidcomment = new ArrayList<>();
//    ArrayAdapter<String> adpt;
//    ArrayAdapter<String> adpt1;
//    double currentLat;
//    double currentLong;
//    Button time;
//
//    @Override
//    public void onLocationChanged(final Location location) {
//
//        if (location != null) {
//            Log.d(LOG_TAG, String.format("%f, %f", location.getLatitude(), location.getLongitude()));
//
//
//        } else {
//            Log.d(LOG_TAG, "Location is null");
//        }
//
//
//    }
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
//        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
//                .addApi(LocationServices.API)
//                .addApi(Places.GEO_DATA_API)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .build();
//        MapsInitializer.initialize(getContext());
//        mGoogleApiClient.connect();
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
//                View v = inflater.inflate(R.layout.tasksalesdetails, parent,false);
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
//
//                TextView contac1 = v.findViewById(R.id.contact1);
//                TextView appoint = v.findViewById(R.id.appointment);
//
//                time = v.findViewById(R.id.owner2);
//
//                usermail = PreferenceManager.getDefaultSharedPreferences(getActivity());
//                username2 = usermail.getString("username", "");
//                password2 = usermail.getString("password", "");
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
//              ImageView img2 =  v.findViewById(R.id.buttonimg1);
//                ImageView img3 = v.findViewById(R.id.buttonimg2);
//                final Button but3 = v.findViewById(R.id.buttonnotify1);
//                but3.setVisibility(View.INVISIBLE);
//                Switch sw1 = v.findViewById(R.id.switch1);
//                sw1.setVisibility(View.INVISIBLE);
//
//                if (getAlldataList.get(position).get("contact_person").equals("No details")) {
//
//
//                } else if (getAlldataList.get(position).get("contact_person").equals("Details")) {
//
//                    contactperson.setVisibility(View.VISIBLE);
//                    contac1.setText(getAlldataList.get(position).get("contact_name"));
//                    contac1.setTextColor(Color.parseColor("#3b5998"));
//                    contac1.setAllCaps(true);
//                    img3.setVisibility(View.VISIBLE);
//                    contactperson.setOnClickListener(new OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
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
////
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
//                R_key.setText(getAlldataList.get(position).get("env"));
//
//                R_key.setTextColor(Color.parseColor("#3b5998"));
//
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
//////
////
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
//
//                        startActivity(i);
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
//                        intent.putExtra("value", "Sales");
//                        intent.putExtra("cust_contact_no",getAlldataList.get(position).get("contact_no"));
//
//                        intent.putExtra("DocumentId", getAlldataList.get(position).get("document_id"));
//                        intent.putExtra("ReportKey", getAlldataList.get(position).get("env"));
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
//                                Calendar cal = Calendar.getInstance();
//                                Date currentLocalTime = cal.getTime();
//                                DateFormat date = new SimpleDateFormat("HH:mm a");
//                                String localTime = date.format(currentLocalTime);
//                                flag = 0;
//
//
//                                Intent i1 = new Intent(getActivity().getApplicationContext(), salesstatusinfo.class);
////                            System.out.println("document_idnew" + getAlldataList.get(position).get("docid"));
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
////                            System.out.println("document_idnew" + getAlldataList.get(position).get("docid"));
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
//                        // If you need the PopupWindow to dismiss when when touched outside
//                        popupWindow.setBackgroundDrawable(new ColorDrawable());
//
//                        int location[] = new int[2];
//
//                        // Get the View's(the one that was clicked in the Fragment) location
//                        v.getLocationOnScreen(location);
//
//                        // Using location, the PopupWindow will be displayed right under anchorView
//                        popupWindow.showAtLocation(v, Gravity.CENTER,300,300);
//
////
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
//                        TextView tv = popupView.findViewById(R.id.textView7);
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
//                                mTimePicker = new TimePickerDialog(salesnew.this.getContext(), new TimePickerDialog.OnTimeSetListener() {
//
//                                    @Override
//                                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
//                                        mcurrenttime.set(Calendar.HOUR_OF_DAY, selectedHour);
//                                        mcurrenttime.set(Calendar.MINUTE, selectedMinute);
//                                        timePicker.setIs24HourView(true);
//                                        e2.setText(selectedHour + ":" + selectedMinute);
//                                        timevalue1 = e2.getText().toString();
//
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
//
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
//                        v.getLocationOnScreen(location);
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
//
//
//    }
//
//
//    protected void NavigateToLocationSetting() {
//        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//        startActivity(intent);
//
//    }
//
//
//    private boolean isLocationTurnedOn() {
//
//        try {
//
//            LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
//
//            boolean isGPSEnabled = locationManager
//                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
//
//            boolean isNetworkEnabled = locationManager
//                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//
//            if (isGPSEnabled || isNetworkEnabled) {
//
//                return true;
//
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
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
//        if (GenericMethods.salesnewwww.equals("true")) {
//
//            getAlldataList = GenericMethods.getAllsalesnewlist1;
//
//            for (int i = 0; i < getAlldataList.size(); i++) {
//
//
//                reportkey.add(getAlldataList.get(i).get("env"));
//
//
//            }
//        } else if (GenericMethods.salesnewwww.equals("false")) {
//            getAlldataList = db.getAllsalesnewist(db);
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
//    private boolean CheckCommentStatus(int position) {
//        boolean commentFlag = true;
//
//        String doc_id = getAlldataList.get(position).get("Did");
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
//    private void getCurrentLocation() {
//        if (mGoogleApiClient != null) {
//            if (mGoogleApiClient.isConnected()) {
//                int permissionLocation = ContextCompat.checkSelfPermission(getActivity(),
//                        Manifest.permission.ACCESS_FINE_LOCATION);
//                if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
//                    mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//                    LocationRequest locationRequest = new LocationRequest();
//                    locationRequest.setInterval(3000);
//                    locationRequest.setFastestInterval(3000);
//                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//                    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
//                            .addLocationRequest(locationRequest);
//                    builder.setAlwaysShow(true);
//                    LocationServices.FusedLocationApi
//                            .requestLocationUpdates(mGoogleApiClient, locationRequest, this);
//                    PendingResult<LocationSettingsResult> result =
//                            LocationServices.SettingsApi
//                                    .checkLocationSettings(mGoogleApiClient, builder.build());
//                    result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
//
//                        @Override
//                        public void onResult(LocationSettingsResult result) {
//                            final Status status = result.getStatus();
//                            switch (status.getStatusCode()) {
//                                case LocationSettingsStatusCodes.SUCCESS:
//
//                                    int permissionLocation = ContextCompat
//                                            .checkSelfPermission(getActivity(),
//                                                    Manifest.permission.ACCESS_FINE_LOCATION);
//                                    if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
//                                        mLocation = LocationServices.FusedLocationApi
//                                                .getLastLocation(mGoogleApiClient);
//
//
//                                    }
//                                    break;
//                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
//
//                                    try {
//
//                                        status.startResolutionForResult(getActivity(),
//                                                REQUEST_CHECK_SETTINGS_GPS);
//                                    } catch (IntentSender.SendIntentException e) {
//
//                                    }
//                                    break;
//                                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
//
//                                    break;
//                            }
//                        }
//                    });
//
//                    if (mLocation != null) {
//                        currentlat = mLocation.getLatitude();
//                        currentlong = mLocation.getLongitude();
//
//
//                    }
//                }
//            }
//        }
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        switch (requestCode) {
//            case REQUEST_CHECK_SETTINGS_GPS:
//                switch (resultCode) {
//                    case Activity.RESULT_OK:
//                        getCurrentLocation();
//                        break;
//                    case Activity.RESULT_CANCELED:
//                        getActivity().finish();
//                        break;
//                }
//                break;
//        }
//    }
//
//    private void checkPermissions() {
//        int permissionLocation = ContextCompat.checkSelfPermission(getActivity(),
//                android.Manifest.permission.ACCESS_FINE_LOCATION);
//        List<String> listPermissionsNeeded = new ArrayList<>();
//        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
//            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
//            if (!listPermissionsNeeded.isEmpty()) {
//                ActivityCompat.requestPermissions(getActivity(),
//                        listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
//            }
//        } else {
//            getCurrentLocation();
//        }
//
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        int permissionLocation = ContextCompat.checkSelfPermission(getActivity(),
//                Manifest.permission.ACCESS_FINE_LOCATION);
//        if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
//            getCurrentLocation();
//        }
//    }
//
//    @Override
//    public void onConnected(@Nullable Bundle bundle) {
//
//
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//    }
//}