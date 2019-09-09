package anulom.executioner.com.anulom.services;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.ContentValues;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

import android.os.AsyncTask;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import anulom.executioner.com.anulom.GenericMethods;


import anulom.executioner.com.anulom.NextActivity;
import anulom.executioner.com.anulom.database.DBManager;
import anulom.executioner.com.anulom.database.DBOperation;

import anulom.executioner.com.anulom.fragment.marriagecompleted;
import anulom.executioner.com.anulom.fragment.marriagenew;
import anulom.executioner.com.anulom.fragment.marriageolder;
import anulom.executioner.com.anulom.fragment.marriagetoday;

import static com.google.android.gms.internal.zzir.runOnUiThread;


public class Marriagedatalist extends Service {
    String startnewdate, startnewtime1, startnewtime2;

    ArrayList<String> documentmarriageid = new ArrayList<>();

    ArrayList<String> appointmentmarriageid = new ArrayList<>();

    ArrayList<HashMap<String, String>> getmarriagedataList = null;

    JSONArray ja22 = null;

    GenericMethods response;
    String APPOINTMENTURL = "";
    String ID1;


    DBOperation db;
    String jStrResult;
    private String username2 = "";
    private String TAG = "";
    ProgressDialog pDialog1;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        db = new DBOperation(getApplicationContext());


        response = new GenericMethods();


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {


            SharedPreferences usermail = PreferenceManager.getDefaultSharedPreferences(this);
            username2 = usermail.getString("username", "");
            String password2 = usermail.getString("password", "");
            APPOINTMENTURL = GenericMethods.MAIN_URL + "/api/v3/get_exec_data?get_auth_token=DOtUBMhv5pk51tl0D37uBcezq85cXNN7hZQ7&exec_email=" + username2 + "&password=" + password2 + "&version="+GenericMethods.apiversion;
            System.out.println("values from get all data service file");
            arraylistclear();
            db = new DBOperation(getApplicationContext());
            new GetContacts().execute();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void arraylistclear() {

        documentmarriageid.clear();

        appointmentmarriageid.clear();

    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... arg0) {


            String jsonStr = "";


            try {

                jsonStr = response.doGetRequest(APPOINTMENTURL);

            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null || !jsonStr.trim().isEmpty()) {
                try {
                    JSONObject jsonObject = null;

                    jsonObject = new JSONObject(jsonStr);

                     jStrResult = jsonObject.getString("status");
                    String jStrResult1 = jsonObject.getString("version");
                    GenericMethods.apiversion = jStrResult1;






                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


                getmarriagedataList = db.getAllmarriageData(db);
                for (int i = 0; i < getmarriagedataList.size(); i++) {

                    documentmarriageid.add(getmarriagedataList.get(i).get("document_id"));
                    appointmentmarriageid.add(getmarriagedataList.get(i).get("appointment_id"));

                }


                if (jsonStr != null) {


                    try {
                        JSONObject jsonObj = new JSONObject(jsonStr);


                        ja22 = jsonObj.getJSONArray(GenericMethods.TAG_NAME16);

                        for (int i = 0; i < ja22.length(); i++) {


                            JSONObject c = ja22.getJSONObject(i);
                            String marriage_documentid = c.getString(GenericMethods.marriage_doc_id);
                            String marriage_env = c.getString(GenericMethods.marriage_env);
                            String marriage_contactperson = c.getString(GenericMethods.marriage_contact_person);

                            String marriage_starttime = c.getString(GenericMethods.marriage_starttime);
                            String marriage_endtime = c.getString(GenericMethods.marriage_endtime);
                            String marriage_address = c.getString(GenericMethods.marriage_address);
                            String marriage_landmark = c.getString(GenericMethods.marriage_landmark);
                            String marriage_appfor = c.getString(GenericMethods.marriage_app_for);
                            String marriage_execuid = c.getString(GenericMethods.marriage_executionerid);
                            String marriage_appointid = c.getString(GenericMethods.marriage_appointment_id);
                            String marriage_dist = c.getString(GenericMethods.marriage_distance);
                            String marriage_trans_type = c.getString(GenericMethods.marriage_transtype);
                            String marriage_acvr_amount = c.getString(GenericMethods.marriage_acvr_amount);
                            String marriage_acvr_id = c.getString(GenericMethods.marriage_acvr_id);
                            String marriage_logical_del = c.getString(GenericMethods.marriage_logical_del);
                            String marriage_poststatus = c.getString(GenericMethods.marriage_poststatus);
                            String marriage_franchiseid = c.getString(GenericMethods.marriage_franchise_id);
                            String marriage_coid = c.getString(GenericMethods.marriage_co_id);
                            String marriage_payverify = c.getString(GenericMethods.marriage_pay_verify);
                            //  String marriage_paypending = c.getString(GenericMethods.marriage_pay_pending);
                            String marriage_ex_id = c.getString(GenericMethods.marriage_ex_id);
                            String marriage_payflag = c.getString(GenericMethods.marriage_pay_flag);
                            String marriage_appflag = c.getString(GenericMethods.marriage_app_flag);
                            String marriage_createdat = c.getString(GenericMethods.marriage_created_at);
                            String marriage_statusid = c.getString(GenericMethods.marriage_status_id);
                            String marriage_status = c.getString(GenericMethods.marriage_status);
                            String marriage_id = c.getString(GenericMethods.marriage_id);
                            String marriage_app_status = c.getString(GenericMethods.marriage_app_status);
                            String marriage_app_type = c.getString(GenericMethods.marriage_app_type);
                            String marriage_latitude;
                            String marriage_longitiude;
                            String sync = "SYNC";
                            String acvrSync = "SYNC";
                            String marriage_husbandname = c.getString(GenericMethods.marriage_husbandname);
                            String marriage_wifename = c.getString(GenericMethods.marriage_wifename);
                            String marriage_husbandcontact = c.getString(GenericMethods.marriage_husbandcontact);
                            String marriage_wifecontact = c.getString(GenericMethods.marriage_wifecontact);
                            String marriage_husbandbiometric = c.getString(GenericMethods.marriage_husbandbiometric);
                            String marriage_wifebiometric = c.getString(GenericMethods.marriage_wifebiometric);
                            String marriage_tokenno = c.getString(GenericMethods.marriage_tokenno);
                            String marriage_type = c.getString(GenericMethods.marriage_type);
                            System.out.println("type:" + marriage_type);


                            if (c.has(GenericMethods.marriage_latitude)) {
                                marriage_latitude = c.getString(GenericMethods.marriage_latitude);

                            } else marriage_latitude = "0";
                            if (c.has(GenericMethods.marriage_longititude)) {
                                marriage_longitiude = c.getString(GenericMethods.marriage_longititude);
                            } else marriage_longitiude = "0";


                            JSONObject jsonmarriagesatten = ja22.getJSONObject(i);

                            JSONArray jsonmarriageattendees = jsonmarriagesatten.getJSONArray(GenericMethods.TAG_NAME18);

                            if (jsonmarriageattendees != null) {


                                for (int j = 0; j < jsonmarriageattendees.length(); j++) {
                                    JSONObject marriageatten = jsonmarriageattendees.getJSONObject(j);

                                    if (marriageatten != null) {
                                        String marriage_name = marriageatten.getString(GenericMethods.marriage_name);
                                        String marriage_email = marriageatten.getString(GenericMethods.marriage_mail);
                                        String marriage_contact = marriageatten.getString(GenericMethods.marriage_contact);

                                        db.InsertmarriageattendeesRecord(db, ID1, marriage_name, marriage_email, marriage_contact, marriage_appointid, username2);


                                    }

                                }

                            }


                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                            SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
                            SimpleDateFormat output1 = new SimpleDateFormat("HH:mm");
                            SimpleDateFormat output2 = new SimpleDateFormat("HH:mm");

                            java.util.Date date, time1, time2;
                            try {
                                date = sdf.parse(marriage_starttime);
                                time1 = sdf.parse(marriage_starttime);
                                time2 = sdf.parse(marriage_endtime);
                                startnewdate = output.format(date);
                                startnewtime1 = output1.format(time1);
                                startnewtime2 = output2.format(time2);

                            } catch (ParseException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                            if (appointmentmarriageid.contains(marriage_appointid)) {

                                db.updatemarriageappointment(db, marriage_appointid, marriage_documentid, marriage_contactperson,
                                        startnewtime1, startnewtime2, startnewdate, marriage_address, marriage_landmark
                                        , marriage_appfor, marriage_execuid, marriage_latitude, marriage_longitiude, marriage_dist, marriage_trans_type,
                                        marriage_acvr_amount, marriage_acvr_id, marriage_logical_del, marriage_poststatus
                                        , marriage_franchiseid, marriage_coid, marriage_statusid, marriage_ex_id, marriage_payverify, marriage_createdat, marriage_appflag, marriage_payflag, marriage_app_type, marriage_id, sync, acvrSync, marriage_app_status, username2, "false",marriage_type);

                            } else {
                                db.InsertmarriageappointmentRecord(db, marriage_appointid, marriage_documentid, marriage_contactperson,
                                        startnewtime1, startnewtime2, startnewdate, marriage_address, marriage_landmark
                                        , marriage_appfor, marriage_execuid, marriage_latitude, marriage_longitiude, marriage_dist, marriage_trans_type,
                                        marriage_acvr_amount, marriage_acvr_id, marriage_logical_del, marriage_poststatus
                                        , marriage_franchiseid, marriage_coid, marriage_statusid, marriage_ex_id, marriage_payverify, marriage_createdat, marriage_appflag, marriage_payflag, marriage_app_type, marriage_id, sync, acvrSync, marriage_app_status, username2, "false",marriage_type);

                            }


                            if (documentmarriageid.contains(marriage_documentid)) {
                                db.Updatemarriagedocuments(db, marriage_documentid, marriage_status, marriage_env,
                                        sync, acvrSync, username2, marriage_husbandname, marriage_husbandbiometric, marriage_husbandcontact
                                        , marriage_wifebiometric, marriage_wifename, marriage_wifecontact, marriage_tokenno);
                            } else if (!db.DocumentmarriageID(db, marriage_documentid)) {

                                db.Insertmarriagedocuments(db, marriage_documentid, marriage_status, marriage_env,
                                        sync, acvrSync, username2, marriage_husbandname, marriage_husbandbiometric, marriage_husbandcontact
                                        , marriage_wifebiometric, marriage_wifename, marriage_wifecontact, marriage_tokenno);

                            }
                            for (int i1 = 0; i1 < db.getnotificationtable(db).size(); i1++) {

                                if (db.getnotificationtable(db).get(i1).get("appid").equals(marriage_appointid)) {


                                    SQLiteDatabase sqldb2 = db.getWritableDatabase();
                                    ContentValues values2 = new ContentValues();
                                    values2.put(DBManager.TableInfo.notification, "true");
                                    int count4 = sqldb2.update(DBManager.TableInfo.TABLE_MARRIAGE_APPOINTMENT, values2, DBManager.TableInfo.db_marriage_appointment_id + "=?", new String[]{marriage_appointid});
                                    sqldb2.close();


                                }

                            }


                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {
                    Log.e("ServiceHandler", "Couldn't get any data from the url");
                }


            return null;

        }


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);


            if (marriagetoday.thisToday != null) {
                marriagetoday.thisToday.reFreshReload();
            }
            if (marriageolder.thisToday != null) {
                marriageolder.thisToday.reFreshReload();
            }
            if (marriagenew.thisToday != null) {
                marriagenew.thisToday.reFreshReload();
            }
            if (marriagecompleted.thisToday != null) {
                marriagecompleted.thisToday.reFreshReload();
            }

           stopSelf();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(GenericMethods.isOnline()) {
                        Intent intent = new Intent(Marriagedatalist.this, NextActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                        startActivity(i);
                        Toast.makeText(Marriagedatalist.this, "Marriage Data Refresh  Completed", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                        startActivity(i);
                        Toast.makeText(Marriagedatalist.this, "Network Unavailable!!Refresh is not Completed", Toast.LENGTH_LONG).show();

                    }

                }
            });

        }
    }


}
