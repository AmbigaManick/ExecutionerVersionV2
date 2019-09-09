package anulom.executioner.com.anulom.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;

import android.graphics.Color;

import android.location.LocationManager;

import android.os.Bundle;

import android.support.design.widget.TabLayout;

import android.support.v4.app.FragmentPagerAdapter;

import android.support.v4.view.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.ListView;

import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.HashMap;
import java.util.List;
import java.util.Timer;

import anulom.executioner.com.anulom.GenericMethods;

import anulom.executioner.com.anulom.NextActivity;

import anulom.executioner.com.anulom.R;

import anulom.executioner.com.anulom.database.DBOperation;



public class pendingfragment extends Fragment {

    public static TodayDetails thisToday = null;
    String year, month, day, hour, min, id, owner, tenant, w1, w2, rkey, timevalue, timevalue1 = "", not_id, from = "Today", biocontent = "", content = "", o_content = "", t_content = "", w1_content = "", w2_content = "",
            o_ = "", t_ = "", w1_ = "", w2_ = "", Date, content1 = "Today", O_ = "", T_ = "", W_ = "", POA_ = "", AW_ = "", news = "Today's Appointments", A, nodetails1 = "No Today Appointments!!!";

    int year1, month1, day1, hour1, h2, h3, min1, id1, count = 0;

    ArrayList<HashMap<String, String>> getAlldataList = new ArrayList<>();
    ArrayList<HashMap<String, String>> getAlldataList1 = null;
    ArrayList<HashMap<String, String>> getCommentDatalist = null;
    String curdate1, appid, time1, docid1, currentcall, ID1;
    ListView tasklist1;
    DBOperation db;

    TextView comment;
    private Calendar mcurrenttime;
    Timer repeatTask = new Timer();
    long repeatInterval = 10800000;
    long startdelay = 3600000;
    private SharedPreferences usermail;
    LocationManager locationManager;
    String provider;
    private String username2 = "", password2 = "";
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    ArrayList<HashMap<String, String>> paymentlist = null;
    ArrayList<String> reportkey = new ArrayList<>();
    ListView tasklist;
    ArrayList<String> documentidpay = new ArrayList<>();
    ArrayList<String> documentidcomment = new ArrayList<>();
    ArrayAdapter<String> adpt;
    ArrayAdapter<String> adpt1;
    double currentLat;
    double currentLong;
    Button time;
    int position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        db = new DBOperation(getContext());

        View view = inflater.inflate(R.layout.next1, container, false);
        // Setting ViewPager for each Tabs
        //noinspection RedundantCast
        ViewPager viewPager = view.findViewById(R.id.viewpagerfragment);
        setupViewPager(viewPager);
        // Set Tabs inside Toolbar
        TabLayout tabs = view.findViewById(R.id.tabsfragment);
        tabs.setupWithViewPager(viewPager);
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        curdate1 = df2.format(c.getTime());
        time = view.findViewById(R.id.search_back);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GenericMethods.nextactivity = "true";
                Intent i = new Intent(getActivity(), NextActivity.class);
                startActivity(i);


            }
        });

        // AutoRefresh();
        tasklist = view.findViewById(R.id.tasklist);
        for (int i = 0; i < tabs.getTabCount(); i++) {

            TabLayout.Tab tab = tabs.getTabAt(i);
            RelativeLayout relativeLayout = (RelativeLayout)
                    LayoutInflater.from(getContext()).inflate(R.layout.tab_layout, tabs, false);
            TextView tabTextView = relativeLayout.findViewById(R.id.tab_title);
            tabTextView.setText(tab.getText());
            tabTextView.setTextColor(Color.parseColor("#ffffff"));
            tab.setCustomView(relativeLayout);
            tab.select();


        }
        viewPager.setCurrentItem(0, false);
        return view;


    }

    private void setupViewPager(ViewPager viewPager) {

        Adapter adapter = new Adapter(getChildFragmentManager());
        adapter.addFragment(new biometricpending(), "Biometric" + "(" + db.getbiometricpendingwork(db).size() + ")");
        adapter.addFragment(new marriageolder(), "Marriage" + "(" + db.getAllmarriageOlderList(db).size() + ")");

        viewPager.setAdapter(adapter);

    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<android.support.v4.app.Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();


        public Adapter(FragmentManager childFragmentManager) {
            super(childFragmentManager);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(android.support.v4.app.Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public void onBackPressed() {
        GenericMethods.nextactivity = "true";
        Intent i = new Intent(getActivity(), NextActivity.class);
        startActivity(i);

    }
}
