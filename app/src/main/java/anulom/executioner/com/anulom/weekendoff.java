//package anulom.executioner5.com3.anulom;
//
//import android.graphics.Color;
//import android.os.Bundle;
//import android.support.design.widget.TabLayout;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.view.LayoutInflater;
//import android.view.View;
//
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import anulom.executioner5.com3.anulom.fragment.leavedetails;
//import anulom.executioner5.com3.anulom.fragment.workdetails;
//
//
///**
// * Created by anulom on 2/8/17.
// */
//
//public class weekendoff extends AppCompatActivity {
//    private Toolbar toolbar;
//    private TabLayout tabLayout;
//    private ViewPager viewPager;
//
//
//    public void onCreate(Bundle SavedInstanceState) {
//        super.onCreate(SavedInstanceState);
//        setContentView(R.layout.weekend);
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle("WeekEnd/WeekOff");
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                finish();
//            }
//        });
//
//
//        viewPager = (ViewPager) findViewById(R.id.viewpager);
//        setupViewPager(viewPager);
//        tabLayout = (TabLayout) findViewById(R.id.tabs);
//        tabLayout.setupWithViewPager(viewPager);
//
//        for (int i = 0; i < tabLayout.getTabCount(); i++) {
//
//            TabLayout.Tab tab = tabLayout.getTabAt(i);
//            RelativeLayout relativeLayout = (RelativeLayout)
//                    LayoutInflater.from(this).inflate(R.layout.tab_layout1, tabLayout, false);
//
//            TextView tabTextView = (TextView) relativeLayout.findViewById(R.id.tab_title);
//            tabTextView.setText(tab.getText());
//            tabTextView.setTextColor(Color.parseColor("#000000"));
//            tab.setCustomView(relativeLayout);
//
//            tab.select();
//        }
//        viewPager.setCurrentItem(0, false);
//    }
//
//    private void setupViewPager(ViewPager viewPager) {
//
//        weekendoff.ViewPagerAdapter adapter = new weekendoff.ViewPagerAdapter(getSupportFragmentManager());
//        adapter.addFragment(new workdetails(), "Work Details");
//        adapter.addFragment(new leavedetails(), "Leave Details");
//
//
//        viewPager.setAdapter(adapter);
//        //System.out.println("tab:"+tabLayout.getSelectedTabPosition());
//
//    }
//
//
//    class ViewPagerAdapter extends FragmentPagerAdapter {
//        private final List<Fragment> mFragmentList = new ArrayList<>();
//        private final List<String> mFragmentTitleList = new ArrayList<>();
//
//        public ViewPagerAdapter(FragmentManager manager) {
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
//}
//
//
