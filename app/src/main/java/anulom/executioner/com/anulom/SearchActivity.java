package anulom.executioner.com.anulom;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import anulom.executioner.com.anulom.database.DBManager;
import anulom.executioner.com.anulom.database.DBOperation;
import anulom.executioner.com.anulom.fragment.Complaintdetails;
import anulom.executioner.com.anulom.fragment.CompletedDetails;
import anulom.executioner.com.anulom.fragment.NewDetails;
import anulom.executioner.com.anulom.fragment.OlderDetails;
import anulom.executioner.com.anulom.fragment.TodayDetails;
import anulom.executioner.com.anulom.fragment.adhocdetails;
import anulom.executioner.com.anulom.fragment.marriagecompleted;
import anulom.executioner.com.anulom.fragment.marriageolder;
import anulom.executioner.com.anulom.fragment.marriagetoday;

import anulom.executioner.com.anulom.fragment.witnessdetails;

import static anulom.executioner.com.anulom.database.DBManager.TableInfo.ATTENDEES;
import static anulom.executioner.com.anulom.database.DBManager.TableInfo.ATT_STATUS;
import static anulom.executioner.com.anulom.database.DBManager.TableInfo.PAYMENT;
import static anulom.executioner.com.anulom.database.DBManager.TableInfo.TABLE_MARRIAGE;
import static anulom.executioner.com.anulom.database.DBManager.TableInfo.TABLE_MARRIAGE_APPOINTMENT;
import static anulom.executioner.com.anulom.database.DBManager.TableInfo.TABLE_MARRIAGE_ATTENDEES;
import static anulom.executioner.com.anulom.database.DBManager.TableInfo.TABLE_SALES;
import static anulom.executioner.com.anulom.database.DBManager.TableInfo.TABLE_SALES_APPOINTMENT;
import static anulom.executioner.com.anulom.database.DBManager.TableInfo.TABLE_SALES_ATTENDEES;
import static anulom.executioner.com.anulom.database.DBManager.TableInfo.TABLE_TASK;

public class SearchActivity extends AppCompatActivity {


    DBOperation database;
    private ProgressDialog pDialog;
    String Date, ID, valuefromnext;

    private SharedPreferences usermail;
    private String username2 = "";
    public static NextActivity thisnext = null;
    DBOperation db;

    int i = 0, pos;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_view);
        //pDialog.show();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(SearchActivity.this, "SEARCH Completed", Toast.LENGTH_LONG).show();


            }
        });
        pos = getIntent().getIntExtra("val", pos);

        database = new DBOperation(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("  Anulom");
        setSupportActionBar(toolbar);
        // getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setIcon(R.drawable.icon2);

        ViewPager viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(viewPager);

        usermail = PreferenceManager.getDefaultSharedPreferences(this);
        username2 = usermail.getString("username", "");
        String password2 = usermail.getString("password", "");


        for (int i = 0; i < tabLayout.getTabCount(); i++) {

            TabLayout.Tab tab = tabLayout.getTabAt(i);
            RelativeLayout relativeLayout = (RelativeLayout)
                    LayoutInflater.from(this).inflate(R.layout.tab_layout, tabLayout, false);

            TextView tabTextView = relativeLayout.findViewById(R.id.tab_title);
            tabTextView.setText(tab.getText());
            tabTextView.setTextColor(Color.parseColor("#ffffff"));
            tab.setCustomView(relativeLayout);
            tab.select();
        }

        viewPager.setCurrentItem(0, false);

    }


    private void setupViewPager(ViewPager viewPager) {


        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        if (GenericMethods.tabpos == 0) {

            if (GenericMethods.todayyy.equals("true")) {
                adapter.addFragment(new TodayDetails(), "Today");
            } else if (GenericMethods.olderrr.equals("true")) {
                adapter.addFragment(new OlderDetails(), "Older");
            } else if (GenericMethods.completeddd.equals("true")) {
                adapter.addFragment(new CompletedDetails(), "Completed");
            } else if (GenericMethods.newwww.equals("true")) {
                adapter.addFragment(new NewDetails(), "New");
            }
        }  else if (GenericMethods.tabpos == 1) {
            if (GenericMethods.marriagetodayyy.equals("true")) {
                adapter.addFragment(new marriagetoday(), "Today");
            } else if (GenericMethods.marriageolderrr.equals("true")) {
                adapter.addFragment(new marriageolder(), "Older");
            } else if (GenericMethods.marriagecompleteddd.equals("true")) {
                adapter.addFragment(new marriagecompleted(), "Completed");
            } else if (GenericMethods.marriagenewwww.equals("true")) {
                adapter.addFragment(new marriagetoday(), "New");
            }
        } else if (GenericMethods.tabpos == 2) {

            if (GenericMethods.taskadhoc.equals("true")) {
                adapter.addFragment(new adhocdetails(), "MyTask");
            } else if (GenericMethods.taskcomplaint.equals("true")) {
                adapter.addFragment(new Complaintdetails(), "Complaint");
            } else if (GenericMethods.taskwitness.equals("true")) {
                adapter.addFragment(new witnessdetails(), "Witness");
            }

        }

        viewPager.setAdapter(adapter);
        // NextActivity.pDialog.dismiss();


    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public void search_back(View v) {
        GenericMethods.value = "false";
        GenericMethods.todayyy = "false";
        GenericMethods.olderrr = "false";
        GenericMethods.completeddd = "false";
        GenericMethods.newwww = "false";
        GenericMethods.salesolderrr = "false";
        GenericMethods.salestodayyy = "false";
        GenericMethods.salesnewwww = "false";
        GenericMethods.salescompleteddd = "false";
        GenericMethods.marriageolderrr = "false";
        GenericMethods.marriagetodayyy = "false";
        GenericMethods.marriagenewwww = "false";
        GenericMethods.marriagecompleteddd = "false";
        GenericMethods.taskadhoc = "false";
        GenericMethods.taskcomplaint = "false";
        GenericMethods. taskwitness = "false";
        GenericMethods.getAllTodaylist1 = new ArrayList<>();
        GenericMethods.getAllOlderlist1 = new ArrayList<>();
        GenericMethods.getAllnewlist1 = new ArrayList<>();
        GenericMethods.getAllcompletedlist1 = new ArrayList<>();
        GenericMethods.getAllsalestodaylist1 = new ArrayList<>();
        GenericMethods.getAllsalesolderlist1 = new ArrayList<>();
        GenericMethods.getAllsalesnewlist1 = new ArrayList<>();
        GenericMethods.getAllsalescompletedlist1 = new ArrayList<>();
        GenericMethods.getAllmarriagetodaylist1 = new ArrayList<>();
        GenericMethods.getAllmarriageolderlist1 = new ArrayList<>();
        GenericMethods.getAllmarriagenewlist1 = new ArrayList<>();
        GenericMethods.getAllmarriagecompletedlist1 = new ArrayList<>();
        GenericMethods.getadhocdetails1 = new ArrayList<>();
        GenericMethods.getwitnessdetails1 = new ArrayList<>();
        GenericMethods.getcomplaintdetails1 = new ArrayList<>();
        GenericMethods.nextactivity="true";
        Intent i = new Intent(SearchActivity.this, NextActivity.class);
        startActivity(i);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub

        // handleIntent(getIntent());
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);

        MenuItem refresh = menu.findItem(R.id.action_refresh);
        refresh.setVisible(false);



        MenuItem sh = menu.findItem(R.id.search);
        sh.setVisible(false);


        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));


        menu.findItem(R.id.login_id).setTitle(username2);
        return super.onCreateOptionsMenu(menu);


    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub

        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {


            case R.id.abt:

                Intent i3 = new Intent(getApplicationContext(), AppointmentActivity.class);
                startActivity(i3);
                return true;

            case R.id.faq:

                Intent i5 = new Intent(getApplicationContext(), informationtab.class);
                startActivity(i5);
                return true;


            case R.id.pending:


                if (GenericMethods.pendingdisplay == "true") {
                    GenericMethods.nextactivity = "false";
                    GenericMethods.pendingworks = "true";
                    Intent i4 = new Intent(getApplicationContext(), NextActivity.class);
                    startActivity(i4);
                } else {

                    Intent i = new Intent(getApplicationContext(), nodetails.class);
                    startActivity(i);
                }

                return true;


//            case R.id.weekend:
//
//                Date1 = this.getSharedPreferences("MyPref", 0); // 0 - for private mode
//                String currentdate = Date1.getString("Date", "");
//
//                return true;


            case R.id.log_out:
                if (GenericMethods.isConnected(getApplicationContext())) {
                    usermail = PreferenceManager.getDefaultSharedPreferences(this);
                    SharedPreferences.Editor editor = usermail.edit();
                    editor.remove("username");
                    editor.remove("password");
                    editor.apply();
                    db.delLocationDetails(db);
                    db.delAppointment(db);
                    db.deluser(db);
                    db.delDocument(db);
                    db.delComments(db);
                    db.delUserRole(db);

                    SQLiteDatabase base1 = db.getWritableDatabase();
                    base1.delete(ATTENDEES, null, null);
                    SQLiteDatabase basestatus = db.getWritableDatabase();
                    basestatus.delete(ATT_STATUS, null, null);

                    SQLiteDatabase base3 = db.getWritableDatabase();
                    base3.delete(DBManager.TableInfo.PARTIES, null, null);

                    SQLiteDatabase base = db.getWritableDatabase();
                    base.delete(PAYMENT, null, null);

                    SQLiteDatabase baseattendees = db.getWritableDatabase();
                    baseattendees.delete(TABLE_SALES_ATTENDEES, null, null);

                    SQLiteDatabase baseappoint = db.getWritableDatabase();
                    baseappoint.delete(TABLE_SALES_APPOINTMENT, null, null);

                    SQLiteDatabase basedocu = db.getWritableDatabase();
                    basedocu.delete(TABLE_SALES, null, null);
                    SQLiteDatabase marriageattendees = db.getWritableDatabase();
                    marriageattendees.delete(TABLE_MARRIAGE_ATTENDEES, null, null);

                    SQLiteDatabase marriageappoint = db.getWritableDatabase();
                    marriageappoint.delete(TABLE_MARRIAGE_APPOINTMENT, null, null);

                    SQLiteDatabase marriagedocu = db.getWritableDatabase();
                    marriagedocu.delete(TABLE_MARRIAGE, null, null);

                    SQLiteDatabase baset = db.getWritableDatabase();
                    baset.delete(TABLE_TASK, null, null);

                    SQLiteDatabase basep = db.getWritableDatabase();
                    basep.delete(DBManager.TableInfo.TABLE_PENDINGWITNESS, null, null);

                    GenericMethods.todaybiometric = 0;
                    GenericMethods.olderbiometric = 0;
                    GenericMethods.newbiometric = 0;
                    GenericMethods.completedbionmetric = 0;
                    GenericMethods.email = "";
                    Intent i = new Intent(getApplicationContext(), Login.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else {
                    Toast.makeText(SearchActivity.this, "Please Turn On Internet to Logout!!", Toast.LENGTH_LONG).show();
                }



        }
        return super.onOptionsItemSelected(item);
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

        }
        return super.onKeyDown(keyCode, event);
    }

    public void onBackPressed() {

        GenericMethods.value = "false";
        GenericMethods.todayyy = "false";
        GenericMethods.olderrr = "false";
        GenericMethods.completeddd = "false";
        GenericMethods.newwww = "false";
        GenericMethods.salesolderrr = "false";
        GenericMethods.salestodayyy = "false";
        GenericMethods.salesnewwww = "false";
        GenericMethods.salescompleteddd = "false";
        GenericMethods.marriageolderrr = "false";
        GenericMethods.marriagetodayyy = "false";
        GenericMethods.marriagenewwww = "false";
        GenericMethods.marriagecompleteddd = "false";
        GenericMethods.taskadhoc = "false";
        GenericMethods.taskcomplaint = "false";
        GenericMethods. taskwitness = "false";
        GenericMethods.getAllTodaylist1 = new ArrayList<>();
        GenericMethods.getAllOlderlist1 = new ArrayList<>();
        GenericMethods.getAllnewlist1 = new ArrayList<>();
        GenericMethods.getAllcompletedlist1 = new ArrayList<>();
        GenericMethods.getAllsalestodaylist1 = new ArrayList<>();
        GenericMethods.getAllsalesolderlist1 = new ArrayList<>();
        GenericMethods.getAllsalesnewlist1 = new ArrayList<>();
        GenericMethods.getAllsalescompletedlist1 = new ArrayList<>();
        GenericMethods.getAllmarriagetodaylist1 = new ArrayList<>();
        GenericMethods.getAllmarriageolderlist1 = new ArrayList<>();
        GenericMethods.getAllmarriagenewlist1 = new ArrayList<>();
        GenericMethods.getAllmarriagecompletedlist1 = new ArrayList<>();
        GenericMethods.getadhocdetails1 = new ArrayList<>();
        GenericMethods.getwitnessdetails1 = new ArrayList<>();
        GenericMethods.getcomplaintdetails1 = new ArrayList<>();
        GenericMethods.nextactivity="true";

    }


}
