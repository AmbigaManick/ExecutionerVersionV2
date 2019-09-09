package anulom.executioner.com.anulom.fragment;


import android.content.Intent;
import android.content.SharedPreferences;

import android.graphics.Color;

import android.location.LocationManager;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.ListView;

import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;


import anulom.executioner.com.anulom.Details2;

import anulom.executioner.com.anulom.R;

import anulom.executioner.com.anulom.database.DBOperation;
import anulom.executioner.com.anulom.pendingwitness;




public class witnesspending extends Fragment {
    public static witnesspending thisadhoc = null;

    String value1 = "MyTask";

    Date date = null, date1 = null;

    String year, month, day, hour, min, id, sdate, owner, tenant, w1, w2, docid, timevalue, timevalue1 = "", not_id, from = "MyTask", biocontent = "", content = "", o_content = "", t_content = "", w1_content = "", w2_content = "",
            o_ = "", t_ = "", w1_ = "", w2_ = "", Date, poststatus, content1 = "Today", valu = "Sales", O_ = "", T_ = "", W_ = "", POA_ = "", AW_ = "", news = " My Task", A, nodetails1 = "No Today Appointments!!!";

    int year1, month1, day1, hour1, h2, h3, click, min1, id1, repeat, count = 0, flag, intervalvalue;

    ArrayList<HashMap<String, String>> getAlldataList = new ArrayList<>();
    ArrayList<HashMap<String, String>> getAlldataList1 = null;
    ArrayList<HashMap<String, String>> getCommentDatalist = null;
    String curdate1, appid, time1, docid1, currentcall, ID1;
    ListView tasklist1;
    DBOperation db, database;
    TextView comment;
    private Calendar mcurrenttime;
    Timer repeatTask = new Timer();
    long repeatInterval = 1800000;
    long startdelay = 10000;
    private SharedPreferences usermail;
    LocationManager locationManager;
    String provider;
    private String username2 = "", password2 = "";
    Calendar calendar, calander;
    SimpleDateFormat simpleDateFormat;
    ArrayList<HashMap<String, String>> paymentlist = null;
    ArrayList<String> reportkey = new ArrayList<>();
    ArrayList<String> reportkey0 = new ArrayList<>();
    ArrayList<String> reportkey1 = new ArrayList<>();
    ArrayList<String> reportkey2 = new ArrayList<>();

    ArrayList<String> documentidpay = new ArrayList<>();
    ArrayList<String> documentidcomment = new ArrayList<>();
    ArrayAdapter<String> adpt;
    ArrayAdapter<String> adpt1;
    double currentLat;
    double currentLong;
    Button time;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.viewwitdetails, container, false);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        curdate1 = df2.format(c.getTime());
        tasklist1 = rootView.findViewById(R.id.tasklist1);
        reFreshReload();

        return rootView;

    }

    private void createAdpt() {


        adpt = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.taskpendingwitness1, reportkey) {

            public View getView(final int position, final View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub
                final LayoutInflater inflater = LayoutInflater.from(getContext());
                final View v = inflater.inflate(R.layout.taskpendingwitness, parent,false);

                TextView R_key = v.findViewById(R.id.tvreportKey1);
                TextView createdat = v.findViewById(R.id.tvdate2);
                TextView assignby = v.findViewById(R.id.tvtenant2);
                TextView status1 = v.findViewById(R.id.tvtenantvalue);



                TextView comment1 = v.findViewById(R.id.tvtenant);
                   createdat.setText(getAlldataList.get(position).get("from_dt")+"-"+getAlldataList.get(position).get("to_dt"));

                assignby.setText(getAlldataList.get(position).get("witness_name"));
                comment1.setText(getAlldataList.get(position).get("witness_email"));

                R_key.setText(getAlldataList.get(position).get("env"));

                status1.setText("Update Biometric");
                status1.setTextColor(Color.parseColor("#006B3C"));



                status1.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getActivity().getApplicationContext(), pendingwitness.class);
                        i.putExtra("env", getAlldataList.get(position).get("env"));
                        i.putExtra("witnessname", getAlldataList.get(position).get("witness_name"));
                        i.putExtra("docid", getAlldataList.get(position).get("docid"));
                        i.putExtra("appid", getAlldataList.get(position).get("appid"));
                        i.putExtra("attid", getAlldataList.get(position).get("attid"));
                        i.putExtra("tokenno", getAlldataList.get(position).get("tokenno"));
                        i.putExtra("witnesssemil", getAlldataList.get(position).get("witness_email"));
                        i.putExtra("partytype", getAlldataList.get(position).get("partytype"));

                        startActivity(i);

                    }
                });

                R_key.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getActivity().getApplicationContext(), Details2.class);
                        i.putExtra("env", getAlldataList.get(position).get("env"));
                        i.putExtra("docid", getAlldataList.get(position).get("docid"));
                        i.putExtra("tokenno", getAlldataList.get(position).get("tokenno"));

                        startActivity(i);

                    }
                });


                return v;
            }

        };

        tasklist1.setAdapter(adpt);


    }




    private void viewDetails() {
        db = new DBOperation(getActivity());
        reportkey.clear();


            getAlldataList = db.getpendingwitness(db);

            for (int i = 0; i < getAlldataList.size(); i++) {


                reportkey.add(getAlldataList.get(i).get("env"));
            }
        }









    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        thisadhoc = this;
    }

    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        thisadhoc = null;
    }


    public void reFreshReload() {
        viewDetails();
        createAdpt();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        if (repeatTask != null) {

            repeatTask.cancel();
        }
    }


}