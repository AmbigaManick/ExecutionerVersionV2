package anulom.executioner.com.anulom;

import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

import anulom.executioner.com.anulom.adapter.ExpandableListAdapter;
import anulom.executioner.com.anulom.database.DBOperation;

public class informationtab extends AppCompatActivity {
    String rkey, rtoken, mail, name, contact, padd, oname, ocontact, oadd, omail, tname, tcontact, tadd, tmail,
            appadd, biocomp, biocomp1, sdate, stime1, stime12, stime2, stime22, sdate2, apptype, docid;
    String url, url1, mail1, name1, contact1, padd1, oname1, ocontact1, oadd1, omail1, tname1, tcontact1, tadd1,
            tmail1, appadd1, sdatenew, app, role, Role1, stimenew, landmark1, contactname, contactemail, contactno, appointment_id;

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader, listDataHeader1;
    HashMap<String, List<String>> listDataChild;
    DBOperation db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information);
        db = new DBOperation(this);


        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("Details");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        SharedPreferences usermail = PreferenceManager.getDefaultSharedPreferences(this);
        String username2 = usermail.getString("username", "");
        String password2 = usermail.getString("password", "");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent intent = getIntent();


        expListView = findViewById(R.id.lvExp);

        prepareListData();
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        expListView.setAdapter(listAdapter);

        expListView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                return false;
            }
        });


        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });


        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {


            }
        });


        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition,
                                        long id) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " : "
                                + listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition),
                        Toast.LENGTH_SHORT).show();


                    return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<>();

        listDataChild = new HashMap<>();

        // Adding child data
        listDataHeader.add("About");
        listDataHeader.add("Post Calls");
        listDataHeader.add("List of Issues");
        listDataHeader.add("Additional Sources");


        //Adding child data


        // Adding child data
        List<String> report = new ArrayList<>();
        report.add("Version-19.7.5");
        report.add("1.Internal Witness");
        report.add("2.Cancelled/Auto-cancelled Appointments");
        report.add("3.In-app search");
        report.add("4.ACVR Offline Updation");
        report.add("5.Map");
        report.add("6.Single party-Marriage Appointments");
        report.add("7.Biometric Updation for Marriage");
        report.add("8.Information Tab");

        List<String> user = new ArrayList<>();
        user.add("1.Appointment Status");
        user.add("Step 1:Go to Biometric Page " +
                "Step 2:We have four options:1.Door Step visit Done 2.Visit not Done 3.Cancelled by Customer 4.No Show  " +
                "Step 3:Click on your option and click update button " + "Step 4:Toast will leave message as BIOMETRIC UPDATED SUCCESSFULLY and refresh to see the updations");
        user.add("2.Call");
        user.add("Click on call button to call the respective owner or tenant");
        user.add("3.Actual Reaching Time");
        user.add("Step 1:Click on time button and set the time for reaching time" +
                "Step 2:Click on OK button" +
                "Step 3:Reaching time will send to the customers via SMS");
        user.add("4.Appointment Booking");
        user.add("Step 1:In Appointment Booking,Select the city,region and date for appointment" +
                        "Step 2:u will get a available slots for particular date and you can book appointment by filling required fields"+
                "Step 3:Click on confirm button to book appointment");
        user.add("5.Internal Witness");
        user.add("Step 1:Go to Biometric Page--> u have options for selecting two internal and external witness" +
                "Step 2:Select any internal or external witness "+
                "Step 3:If u select internal witness you have to select the users from the spinners and you have to update the respective biometric.If you select external witness,you just can update their biometric alone"+
                "Step 4:Click on update button after witness gets updated,it will show toast message as WITNESS UPDATED SUCCESSFULLY");
        user.add("6.Payment");
        user.add("Step 1:Go to Payment Page,give the amount you are going to pay" +
                "Step 2:And fill the reasons for remaining payment/spot discount "+
                "Step 3:Click on confirm button to update payment");
        user.add("7.ACVR");
        user.add("Step 1:Go to ACVR Page,You will have two options 1.Current Location and Other Location for first appointment and 2.Previous Address and Other Location option for remaining Appointments" +
                "Step 2:In current location,route will be generated in background for current location and appointment address"+
                "In Previous address,route will be generated in background for previous address and appointment address"+
                "In other location,route will be generated in background for address you are typing and appointment address" +
                "NOTE:If latitude and Longititude is not accurate,ACVR cannot be generated for that address and distance");
        user.add("7.Reschedule");
        user.add("Step 1:Click on Reschedule Button,You will have four options for resheduling" +
                "Step 2:Select the option and select the date and time for rescheduling"+
                "Step 3:Click on Reschedule button");
        user.add("8.Comment");
        user.add("Step 1:Go to Comment Page->Click on + button to add comment" +
                "Step 2:Type the respective comments and assign comments to respective users"+
                "Step 3:If you have complaints,click on complations spinner and select the complaint or If you have service requests,Click on service request spinner and select the request" +
                "Step 4:Click on Add comment your comments will get added");


        List<String> property = new ArrayList<>();
        property.add("Anulom has  Stopped:");
        property.add("Solution:Uninstall the application and install new application by clearing cache");
        property.add("Appointments Not Displaying properly:");
        property.add("Solution:" +
                "1.First check your internet Connection" +
                " 2.Refresh Again" +
                " 3.Check in cancelled and Autocancelled appointments Tab" +
                " 4.Contact Support team");
        property.add("Need to Sync Offline Data:");
        property.add("Solution:1.Please Check Internet Connection" +
                "2.Try again" +
                "3.Contact Developer or Support Team");

        List<String> owner = new ArrayList<>();
        owner.add("Release Notes:");
        owner.add("Demo Video:");


        listDataChild.put(listDataHeader.get(0), report); // Header, Child data
        listDataChild.put(listDataHeader.get(1), user);
        listDataChild.put(listDataHeader.get(2), property);
        listDataChild.put(listDataHeader.get(3), owner);

    }

}






