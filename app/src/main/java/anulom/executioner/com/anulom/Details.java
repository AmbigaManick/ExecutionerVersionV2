
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import anulom.executioner.com.anulom.adapter.ExpandableListAdapter;
import anulom.executioner.com.anulom.database.DBOperation;

public class Details extends AppCompatActivity {
    String rkey, rtoken, mail, name, contact, padd, oname, ocontact, oadd, omail, tname, tcontact, tadd, tmail,
            appadd, biocomp, biocomp1, sdate, stime1, stime12, stime2, stime22, sdate2, apptype, docid;
    String rkey1, rtoken1, mail1, name1, contact1, padd1, oname1, ocontact1, oadd1, omail1, tname1, tcontact1, tadd1,
            tmail1, appadd1, sdatenew,app,role,Role1, stimenew, landmark1, contactname, contactemail, contactno, appointment_id;

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    DBOperation db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
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
        rtoken = getIntent().getStringExtra("ReportToken");
        rkey = getIntent().getStringExtra("ReportKey");
        name = getIntent().getStringExtra("Uname");
        mail = getIntent().getStringExtra("Umail");
        contact = getIntent().getStringExtra("Ucontact");
        padd = getIntent().getStringExtra("PropertyAddress");
        oname = getIntent().getStringExtra("OwnerName");
        ocontact = getIntent().getStringExtra("OwnerContact");
        omail = getIntent().getStringExtra("OwnerEmail");
        oadd = getIntent().getStringExtra("OwnerAddress");
        tname = getIntent().getStringExtra("TenantName");
        tcontact = getIntent().getStringExtra("TenantContact");
        tmail = getIntent().getStringExtra("TenantEmail");
        tadd = getIntent().getStringExtra("TenantAddress");
        appadd = getIntent().getStringExtra("AppointmentAddress");
        biocomp = getIntent().getStringExtra("BiometricComp");
        biocomp1 = getIntent().getStringExtra("BiometricComp1");
        apptype = getIntent().getStringExtra("apptype");
        docid = getIntent().getStringExtra("docid");
        landmark1 = getIntent().getStringExtra("landmark");
        contactname = getIntent().getStringExtra("contactname");
        contactemail = getIntent().getStringExtra("contactemail");
        contactno = getIntent().getStringExtra("contactno");
        sdate2 = getIntent().getStringExtra("StartDate");
        appointment_id = getIntent().getStringExtra("appoint");
        role=getIntent().getStringExtra("Role");

        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
        Date date11 = null;
        try {
            date11 = format1.parse(sdate2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sdate = format2.format(date11);

        stime12 = getIntent().getStringExtra("StartTime1");
        SimpleDateFormat formatst = new SimpleDateFormat("HH:mm");
        SimpleDateFormat formatst2 = new SimpleDateFormat("hh:mm a");
        Date stdate11 = null;
        try {
            stdate11 = formatst.parse(stime12);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        stime1 = formatst2.format(stdate11);


        stime22 = getIntent().getStringExtra("StartTime2");

        SimpleDateFormat formatend = new SimpleDateFormat("HH:mm");
        SimpleDateFormat formatend2 = new SimpleDateFormat("hh:mm a");
        Date enddate11 = null;
        try {
            enddate11 = formatend.parse(stime22);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        stime2 = formatend2.format(enddate11);

        if(rtoken.equals("")){

            rtoken1="IGR Token Number:"+"Draft is not Approved";

        }else {

            rtoken1 = "IGR Token Number: " + rtoken;
        }


        docid="Document Id: " + docid;
        rkey1 = "Request No: " + rkey;
        if (!name.equals("") && !name.equals("")) {
            name1 = "Name: " + name;
        }

        if (!mail.equals("") && !mail.equals("")) {
            mail1 = "Email Id: " + mail;
        }
        if (!contact.equals("") && !contact.equals("")) {
            contact1 = "Contact Number: " + contact;
        }
            Role1 = "Role: " + role;

        if (!padd.equals("") && !padd.equals("")) {
            padd1 = "Property Address: " + padd;
        }

        if (!oname.equals("") && !oname.equals("")) {
            oname1 = "Name: " + oname;
        }
        if (!ocontact.equals("") && !ocontact.equals("")) {
            ocontact1 = "Contact: " + ocontact;
        }
        if (!omail.equals("") && !omail.equals("")) {
            omail1 = "Mail: " + omail;
        }
        if (!oadd.equals("") && !oadd.equals("")) {
            oadd1 = "Address: " + oadd;
        }
        tname1 = "Name: " + tname;
        tcontact1 = "Contact: " + tcontact;
        tmail1 = "Mail: " + tmail;
        tadd1 = "Address: " + tadd;

        app="Appointment Id:"+appointment_id;
        appadd1 = "Appointment Address: " + appadd;
        sdatenew = "Appointment Date:" + sdate;
        stimenew = "Appointment time:" + stime1 + "-" + stime2;


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
        listDataHeader.add("Report:");
        listDataHeader.add("User Details");
        listDataHeader.add("Property Details");
        listDataHeader.add("Appointment's Details");
        listDataHeader.add("Landmark");
        listDataHeader.add("Contact-Person");
        listDataHeader.add("Attendees Details");


        // Adding child data
        List<String> report = new ArrayList<>();
        report.add(rtoken1);
        report.add(docid);
        report.add(rkey1);


        List<String> user = new ArrayList<>();
        user.add(name1);
        user.add(mail1);
        user.add(contact1);
        user.add(Role1);

        List<String> landmark = new ArrayList<>();
        landmark.add(landmark1);

        List<String> contactperson = new ArrayList<>();
        contactperson.add("Name: " + contactname);
        contactperson.add("Email: " + contactemail);
        contactperson.add("Phone No: " + contactno);

        List<String> property = new ArrayList<>();
        property.add(padd1);

        List<String> owner = new ArrayList<>();
        owner.add(oname1);
        if (!ocontact.equals("") || !ocontact.equals("")) {
            owner.add(ocontact1);
        }
        owner.add(oadd1);
        owner.add(omail1);

        for (int i = 0; i < owner.size(); i++) {
            System.out.println("owner:" + owner.get(i));

        }
        List<String> tenant = new ArrayList<>();
        tenant.add(tname1);
        tenant.add(tcontact1);
        tenant.add(tadd1);
        tenant.add(tmail1);

        List<String> appointment = new ArrayList<>();
        appointment.add(app);
        appointment.add(appadd1);
        appointment.add(sdatenew);
        appointment.add(stimenew);
        List<String> attendees = new ArrayList<>();
        listDataChild.put(listDataHeader.get(0), report); // Header, Child data
        listDataChild.put(listDataHeader.get(1), user);
        listDataChild.put(listDataHeader.get(2), property);
        listDataChild.put(listDataHeader.get(3), appointment);
        listDataChild.put(listDataHeader.get(4), landmark);
        listDataChild.put(listDataHeader.get(5), contactperson);

        for (int i = 0; i < db.getattendees(db).size(); i++) {

            if (appointment_id.equals(db.getattendees(db).get(i).get("appoint"))) {


                attendees.add("Name:" + db.getattendees(db).get(i).get("name"));
                attendees.add("Email:" + db.getattendees(db).get(i).get("email"));
                attendees.add("Contact:" + db.getattendees(db).get(i).get("contact"));

            }


        }

        if (attendees.size() == 0) {

            attendees.add("Name:" + "No details");
            attendees.add("Email:" + "No details");
            attendees.add("Contact:" + "No details");
        }

        listDataChild.put(listDataHeader.get(6), attendees);
    }

}






