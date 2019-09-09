package anulom.executioner.com.anulom.fragment;

import android.content.Intent;
import android.content.SharedPreferences;

import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;

import android.view.View;

import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.ListView;

import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;

import anulom.executioner.com.anulom.Details3;
import anulom.executioner.com.anulom.R;

import anulom.executioner.com.anulom.database.DBOperation;

public class penaltydocuments extends Fragment {
    public static adhocdetails thisadhoc = null;

    String value1 = "MyTask";

    Date date = null, date1 = null;

    String year, month, day, d1,hour, min, id, sdate, owner, tenant, w1, w2, docid, timevalue, timevalue1 = "", not_id, from = "MyTask", biocontent = "", content = "", o_content = "", t_content = "", w1_content = "", w2_content = "",
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
        db = new DBOperation(getContext());
        View rootView = inflater.inflate(R.layout.viewpenaltydocuments, container, false);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        curdate1 = df2.format(c.getTime());
        tasklist1 = rootView.findViewById(R.id.tasklist5);
        reFreshReload();

        return rootView;

    }

    private void createAdpt() {


        adpt = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.taskpenalty, reportkey) {

            public View getView(final int position, final View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub
                final LayoutInflater inflater = LayoutInflater.from(getContext());
                final View v = inflater.inflate(R.layout.taskpenalty1, parent,false);

                TextView R_key = v.findViewById(R.id.tvreportKey1);
                TextView assignby = v.findViewById(R.id.tvtenant2);
                TextView status1 = v.findViewById(R.id.tvtenantvalue);
                final TextView date = v.findViewById(R.id.tvdate2);

                TextView comment1 = v.findViewById(R.id.tvtenant);

                String date1 = getAlldataList.get(position).get("penaltycreatedat");
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
                year = date1.substring(0, 4);
                month = date1.substring(5, 7);
                day = date1.substring(8);

                Date date11 = null;
                try {
                    date11 = format1.parse(date1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String datest = getAlldataList.get(position).get("penaltycreatedat");
                SimpleDateFormat formatst = new SimpleDateFormat("HH:mm");
                SimpleDateFormat formatst2 = new SimpleDateFormat("hh:mm");
                d1=datest.substring(11,16);
                System.out.println(d1);
                Date stdate11 = null;
                try {
                    stdate11 = formatst.parse(d1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                date.setText(format1.format(date11) + " " + "\n" + formatst2.format(stdate11));

                assignby.setText(getAlldataList.get(position).get("penaltysysytereason"));
                comment1.setText(getAlldataList.get(position).get("penaltyamount"));
                R_key.setText(getAlldataList.get(position).get("penaltyenv"));
                R_key.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i=new Intent(getActivity(), Details3.class);
                        i.putExtra("penaltyexecid",getAlldataList.get(position).get("penaltyexeid"));
                        i.putExtra("penaltyappid",getAlldataList.get(position).get("penaltyappid"));
                        i.putExtra("penaltytype",getAlldataList.get(position).get("penaltytype"));
                        i.putExtra("penaltydocid",getAlldataList.get(position).get("penaltydocid"));
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



        getAlldataList = db.getAllpenaltydocuments(db);
        System.out.println("size:"+db.getAllpenaltydocuments(db).size());

        for (int i = 0; i < getAlldataList.size(); i++) {


            reportkey.add(getAlldataList.get(i).get("penaltyenv"));
        }
    }


    public void reFreshReload() {
        viewDetails();
        createAdpt();
    }


}