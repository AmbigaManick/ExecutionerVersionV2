//package anulom.executioner5.com3.anulom.fragment;
//
//import android.content.SharedPreferences;
//
//import android.location.LocationManager;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//
//import android.view.LayoutInflater;
//
//import android.view.View;
//
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//
//import android.widget.ListView;
//
//import android.widget.TextView;
//
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Timer;
//
//import anulom.executioner5.com3.anulom.R;
//
//import anulom.executioner5.com3.anulom.database.DBOperation;
//
//
//public class salescancelledAppointments extends Fragment {
//    public static adhocdetails thisadhoc = null;
//
//    String value1 = "MyTask";
//
//    Date date = null, date1 = null;
//
//    String year, month, day, hour, min, id, sdate, owner, tenant, w1, w2, docid, timevalue, timevalue1 = "", not_id, from = "MyTask", biocontent = "", content = "", o_content = "", t_content = "", w1_content = "", w2_content = "",
//            o_ = "", t_ = "", w1_ = "", w2_ = "", Date, poststatus, content1 = "Today", valu = "Sales", O_ = "", T_ = "", W_ = "", POA_ = "", AW_ = "", news = " My Task", A, nodetails1 = "No Today Appointments!!!";
//
//    int year1, month1, day1, hour1, h2, h3, click, min1, id1, repeat, count = 0, flag, intervalvalue;
//
//    ArrayList<HashMap<String, String>> getAlldataList = new ArrayList<>();
//    ArrayList<HashMap<String, String>> getAlldataList1 = null;
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
//    LocationManager locationManager;
//    String provider;
//    private String username2 = "", password2 = "";
//    Calendar calendar, calander;
//    SimpleDateFormat simpleDateFormat;
//    ArrayList<HashMap<String, String>> paymentlist = null;
//    ArrayList<String> reportkey = new ArrayList<>();
//    ArrayList<String> reportkey0 = new ArrayList<>();
//    ArrayList<String> reportkey1 = new ArrayList<>();
//    ArrayList<String> reportkey2 = new ArrayList<>();
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
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        db = new DBOperation(getContext());
//        View rootView = inflater.inflate(R.layout.viewcancelledappointments, container, false);
//        Calendar c = Calendar.getInstance();
//        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
//        curdate1 = df2.format(c.getTime());
//        tasklist1 = rootView.findViewById(R.id.tasklist4);
//        reFreshReload();
//
//        return rootView;
//
//    }
//
//    private void createAdpt() {
//
//
//        adpt = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.taskbiometriccancelled, reportkey) {
//
//            public View getView(final int position, final View convertView, ViewGroup parent) {
//                // TODO Auto-generated method stub
//                final LayoutInflater inflater = LayoutInflater.from(getContext());
//                final View v = inflater.inflate(R.layout.taskbiometric1, parent,false);
//
//                TextView R_key = v.findViewById(R.id.tvreportKey1);
//
//                TextView assignby = v.findViewById(R.id.tvtenant2);
//                TextView status1 = v.findViewById(R.id.tvtenantvalue);
//                final TextView date = v.findViewById(R.id.tvdate2);
//                TextView comment1 = v.findViewById(R.id.tvtenant);
//                comment1.setVisibility(View.GONE);
//                assignby.setText(getAlldataList.get(position).get("contact_person"));
//                String date1 = getAlldataList.get(position).get("created_at");
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
//                R_key.setText(getAlldataList.get(position).get("document_id"));
//
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
//
//    private void viewDetails() {
//        db = new DBOperation(getActivity());
//        reportkey.clear();
//
//
//            getAlldataList = db.getAllsalescancelledappointments(db);
//
//            for (int i = 0; i < getAlldataList.size(); i++) {
//
//
//                reportkey.add(getAlldataList.get(i).get("document_id"));
//            }
//        }
//
//
//    public void reFreshReload() {
//        viewDetails();
//        createAdpt();
//    }
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
//}