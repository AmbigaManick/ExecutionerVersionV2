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

public class Details2 extends AppCompatActivity {
    String rkey, rtoken, docid,rkey1, rtoken1,docid1;


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
        docid1 = getIntent().getStringExtra("docid");
        rkey = getIntent().getStringExtra("env");
        rtoken = getIntent().getStringExtra("tokenno");






        rtoken1 = "IGR Token Number: " + rtoken;
        docid = "Document Id: " + docid1;
        rkey1 = "Request No: " + rkey;





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

        listDataHeader.add("Report:");



        List<String> report = new ArrayList<>();

        report.add(rtoken1);
        report.add(docid);
        report.add(rkey1);



        List<String> attendees = new ArrayList<>();
        listDataChild.put(listDataHeader.get(0), report); // Header, Child data


    }

}






