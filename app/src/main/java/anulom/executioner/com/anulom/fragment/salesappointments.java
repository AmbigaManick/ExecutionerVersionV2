//package anulom.executioner5.com3.anulom.fragment;
//
//
//import android.content.SharedPreferences;
//
//import android.graphics.Color;
//
//import android.location.LocationManager;
//
//import android.os.Bundle;
//import android.support.design.widget.TabLayout;
//import android.support.v4.app.Fragment;
//
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
//import android.support.v4.view.ViewPager;
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
//import android.widget.RelativeLayout;
//
//import android.widget.TextView;
//
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Timer;
//
//
//import anulom.executioner5.com3.anulom.R;
//
//import anulom.executioner5.com3.anulom.database.DBOperation;
//
//
//public class salesappointments extends Fragment {
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
//        View rootView = inflater.inflate(R.layout.viewbiometriccancel, container, false);
//        Calendar c = Calendar.getInstance();
//        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
//        curdate1 = df2.format(c.getTime());
//        ViewPager viewPager = rootView.findViewById(R.id.frag2_viewpager1);
//        setupViewPager(viewPager);
//
//        TabLayout tabs = rootView.findViewById(R.id.frag2_tabs1);
//        tabs.setupWithViewPager(viewPager);
//
//        for (int i = 0; i < tabs.getTabCount(); i++) {
//
//            TabLayout.Tab tab = tabs.getTabAt(i);
//            RelativeLayout relativeLayout = (RelativeLayout)
//                    LayoutInflater.from(getContext()).inflate(R.layout.tab_layout, tabs, false);
//            TextView tabTextView = relativeLayout.findViewById(R.id.tab_title);
//            tabTextView.setText(tab.getText());
//            tabTextView.setTextColor(Color.parseColor("#3b5998"));
//            tab.setCustomView(relativeLayout);
//            tab.select();
//
//
//        }
//        viewPager.setCurrentItem(0, false);
//
//
//
//
//        return rootView;
//
//    }
//
//
//
//    private void setupViewPager(ViewPager viewPager) {
//
//
//        Adapter adapter = new Adapter(getChildFragmentManager());
//
//        System.out.println("size:"+db.getAllsalescancelledappointments(db).size());
//
//        if (db.getAllsalescancelledappointments(db).size()== 0) {
//            adapter.addFragment(new NoAppointmentdetails(), "Cancelled");
//
//        } else if (db.getAllsalescancelledappointments(db).size() > 0) {
//            adapter.addFragment(new salescancelledAppointments(), "Cancelled");
//
//        }
//        if (db.getAllsalesautocancelledappointments(db).size() == 0) {
//            adapter.addFragment(new NoAppointmentdetails(), "Auto-Cancelled");
//
//        } else if (db.getAllsalesautocancelledappointments(db).size() > 0) {
//            adapter.addFragment(new salesAutocancelledappointments(), "Auto-Cancelled");
//
//        }
//        viewPager.setAdapter(adapter);
//
//
//    }
//
//
//    static class Adapter extends FragmentPagerAdapter {
//        private final List<Fragment> mFragmentList = new ArrayList<>();
//        private final List<String> mFragmentTitleList = new ArrayList<>();
//
//        public Adapter(FragmentManager manager) {
//            super(manager);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return mFragmentList.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            return mFragmentList.size();
//        }
//
//        public void addFragment(Fragment fragment, String title) {
//            mFragmentList.add(fragment);
//            mFragmentTitleList.add(title);
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//
//            return mFragmentTitleList.get(position);
//        }
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//}