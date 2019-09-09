package anulom.executioner.com.anulom.services;



import android.app.ProgressDialog;
import android.app.Service;
import android.content.ContentValues;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
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


import anulom.executioner.com.anulom.GenericMethods;


import anulom.executioner.com.anulom.NextActivity;
import anulom.executioner.com.anulom.database.DBManager;
import anulom.executioner.com.anulom.database.DBOperation;
import anulom.executioner.com.anulom.fragment.Complaintdetails;

import anulom.executioner.com.anulom.fragment.adhocdetails;

import anulom.executioner.com.anulom.fragment.witnessdetails;

import static anulom.executioner.com.anulom.database.DBManager.TableInfo.TABLE_TASK;
import static com.google.android.gms.internal.zzir.runOnUiThread;


public class Taskdatalist extends Service {


    JSONArray ja9 = null;
    JSONArray ja10 = null;
    JSONArray ja11 = null;

    GenericMethods  response2;
    String WITNESSURL = "";
    String ID1;


    DBOperation db;

    private String username2 = "";
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



        response2 = new GenericMethods();


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {


            SharedPreferences usermail = PreferenceManager.getDefaultSharedPreferences(this);
            username2 = usermail.getString("username", "");
            String password2 = usermail.getString("password", "");
            WITNESSURL = GenericMethods.MAIN_URL1 + "/communicator/api/v1/comment_data/get_task?get_auth_token=DOtUBMhv5pk51tl0D37uBcezq85cXNN7hZQ7&user=" + username2;
            System.out.println("values from get all data service file");

            db = new DBOperation(getApplicationContext());
            new GetContacts().execute();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }



    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... arg0) {



            String jsonStr2 = "";


            try {
                jsonStr2 = response2.doGetRequest(WITNESSURL);

            } catch (IOException e) {
                e.printStackTrace();
            }


            Log.d("Response2: ", "> " + jsonStr2);



            if (jsonStr2 != null) {

                try {

                    JSONObject jsonObj1 = new JSONObject(jsonStr2);
                    ja9 = jsonObj1.getJSONArray(GenericMethods.TAG_NAME9);
                    String TAG = "";
                    for (int n = 0; n < ja9.length(); n++) {
                        JSONObject c = ja9.getJSONObject(n);
                        String witnessid = c.getString(GenericMethods.TAG_ID);
                        String comment = c.getString(GenericMethods.TAG_COMMENT);
                        String isdone = c.getString(GenericMethods.TAG_IS_DONE);
                        String doc = c.getString(GenericMethods.TAG_DOC);
                        String create = c.getString(GenericMethods.TAG_CREATE);
                        String remainder = c.getString(GenericMethods.TAG_REMAINDER);
                        String assign = c.getString(GenericMethods.TAG_ASSIGN);
                        String taskid = c.getString(GenericMethods.TAG_TASK_ID);


//////db for adhoc
                        SQLiteDatabase adhoc = db.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put(DBManager.TableInfo.KEYID, ID1);
                        values.put(DBManager.TableInfo.WITID, witnessid);
                        values.put(DBManager.TableInfo.COMMENT, comment);
                        values.put(DBManager.TableInfo.IS_DONE, isdone);
                        values.put(DBManager.TableInfo.DOC, doc);
                        values.put(DBManager.TableInfo.CREATE, create);
                        values.put(DBManager.TableInfo.TASK_NAME, "MyTask");
                        values.put(DBManager.TableInfo.notification, "false");
                        values.put(DBManager.TableInfo.status1, "Not Completed");
                        values.put(DBManager.TableInfo.REMAINDER, remainder);
                        values.put(DBManager.TableInfo.ASSIGN, assign);
                        values.put(DBManager.TableInfo.TASK_ID, taskid);
                        values.put(DBManager.TableInfo.KEY_LOGIN_USER, username2);
                        String condition = DBManager.TableInfo.DOC + " =?";
                        Cursor cursor = adhoc.query(TABLE_TASK, null, condition, new String[]{DBManager.TableInfo.TASK_ID}, null, null, null);
                        long status = adhoc.insert(TABLE_TASK, null, values);
                        Log.d(TAG, "Adhoc insert : " + status);
                        cursor.close();
                        adhoc.close();


                        for (int i1 = 0; i1 < db.getadhocnotificationtable(db).size(); i1++) {

                            if (db.getadhocnotificationtable(db).get(i1).get("id1").equals(witnessid)) {


                                SQLiteDatabase sqldb2 = db.getWritableDatabase();
                                ContentValues values2 = new ContentValues();
                                values2.put(DBManager.TableInfo.notification, "true");
                                int count4 = sqldb2.update(DBManager.TableInfo.TABLE_TASK, values2, DBManager.TableInfo.WITID + "=?", new String[]{witnessid});

                                sqldb2.close();


                            }

                        }

                    }
//
//                        //witness

                    ja10 = jsonObj1.getJSONArray(GenericMethods.TAG_NAME10);
                    for (int n = 0; n < ja10.length(); n++) {
                        JSONObject c = ja10.getJSONObject(n);
                        String witnessid = c.getString(GenericMethods.TAG_ID);
                        String comment = c.getString(GenericMethods.TAG_COMMENT);
                        String isdone = c.getString(GenericMethods.TAG_IS_DONE);
                        String doc = c.getString(GenericMethods.TAG_DOC);
                        String create = c.getString(GenericMethods.TAG_CREATE);
                        String remainder = c.getString(GenericMethods.TAG_REMAINDER);
                        String assign = c.getString(GenericMethods.TAG_ASSIGN);
                        String taskid = c.getString(GenericMethods.TAG_TASK_ID);

                        SQLiteDatabase witn = db.getWritableDatabase();
                        ContentValues valuesw = new ContentValues();
                        valuesw.put(DBManager.TableInfo.KEYID, ID1);
                        valuesw.put(DBManager.TableInfo.WITID, witnessid);
                        valuesw.put(DBManager.TableInfo.COMMENT, comment);
                        valuesw.put(DBManager.TableInfo.IS_DONE, isdone);
                        valuesw.put(DBManager.TableInfo.DOC, doc);
                        valuesw.put(DBManager.TableInfo.CREATE, create);
                        valuesw.put(DBManager.TableInfo.notification, "false");

                        valuesw.put(DBManager.TableInfo.TASK_NAME, "Witness");
                        valuesw.put(DBManager.TableInfo.status1, "Not Completed");
                        valuesw.put(DBManager.TableInfo.REMAINDER, remainder);
                        valuesw.put(DBManager.TableInfo.ASSIGN, assign);
                        valuesw.put(DBManager.TableInfo.TASK_ID, taskid);
                        valuesw.put(DBManager.TableInfo.KEY_LOGIN_USER, username2);
                        String conditionw = DBManager.TableInfo.DOC + " =?";
                        Cursor cursorw = witn.query(TABLE_TASK, null, conditionw, new String[]{DBManager.TableInfo.TASK_ID}, null, null, null);
                        long statusw = witn.insert(TABLE_TASK, null, valuesw);
                        Log.d(TAG, "Witness insert : " + statusw);
                        cursorw.close();
                        witn.close();


                        for (int i1 = 0; i1 < db.getwitnessnotificationtable(db).size(); i1++) {

                            if (db.getwitnessnotificationtable(db).get(i1).get("id1").equals(witnessid)) {


                                SQLiteDatabase sqldb2 = db.getWritableDatabase();
                                ContentValues values2 = new ContentValues();
                                values2.put(DBManager.TableInfo.notification, "true");
                                int count4 = sqldb2.update(DBManager.TableInfo.TABLE_TASK, values2, DBManager.TableInfo.WITID + "=?", new String[]{witnessid});

                                sqldb2.close();


                            }

                        }
                    }
////complaint


                    ja11 = jsonObj1.getJSONArray(GenericMethods.TAG_NAME11);
                    for (int n = 0; n < ja11.length(); n++) {
                        JSONObject c = ja11.getJSONObject(n);
                        String witnessid = c.getString(GenericMethods.TAG_ID);
                        String comment = c.getString(GenericMethods.TAG_COMMENT);
                        String isdone = c.getString(GenericMethods.TAG_IS_DONE);
                        String doc = c.getString(GenericMethods.TAG_DOC);
                        String create = c.getString(GenericMethods.TAG_CREATE);
                        String remainder = c.getString(GenericMethods.TAG_REMAINDER);
                        String assign = c.getString(GenericMethods.TAG_ASSIGN);
                        String taskid = c.getString(GenericMethods.TAG_TASK_ID);

                        SQLiteDatabase complaint = db.getWritableDatabase();
                        ContentValues valuesw = new ContentValues();
                        valuesw.put(DBManager.TableInfo.KEYID, ID1);
                        valuesw.put(DBManager.TableInfo.WITID, witnessid);
                        valuesw.put(DBManager.TableInfo.COMMENT, comment);
                        valuesw.put(DBManager.TableInfo.IS_DONE, isdone);
                        valuesw.put(DBManager.TableInfo.DOC, doc);
                        valuesw.put(DBManager.TableInfo.notification, "false");
                        valuesw.put(DBManager.TableInfo.CREATE, create);
                        valuesw.put(DBManager.TableInfo.TASK_NAME, "Complaint");
                        valuesw.put(DBManager.TableInfo.status1, "Not Completed");
                        valuesw.put(DBManager.TableInfo.REMAINDER, remainder);
                        valuesw.put(DBManager.TableInfo.ASSIGN, assign);
                        valuesw.put(DBManager.TableInfo.TASK_ID, taskid);
                        valuesw.put(DBManager.TableInfo.KEY_LOGIN_USER, username2);
                        String conditionw = DBManager.TableInfo.DOC + " =?";
                        Cursor cursorw = complaint.query(TABLE_TASK, null, conditionw, new String[]{DBManager.TableInfo.TASK_ID}, null, null, null);
                        long statusw = complaint.insert(TABLE_TASK, null, valuesw);
                        Log.d(TAG, "Complaint insert : " + statusw);
                        cursorw.close();
                        complaint.close();


                        for (int i1 = 0; i1 < db.getcomplaintnotificationtable(db).size(); i1++) {

                            if (db.getcomplaintnotificationtable(db).get(i1).get("id1").equals(witnessid)) {


                                SQLiteDatabase sqldb2 = db.getWritableDatabase();
                                ContentValues values2 = new ContentValues();
                                values2.put(DBManager.TableInfo.notification, "true");
                                int count4 = sqldb2.update(DBManager.TableInfo.TABLE_TASK, values2, DBManager.TableInfo.WITID + "=?", new String[]{witnessid});

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


            if (adhocdetails.thisadhoc != null) {
                adhocdetails.thisadhoc.reFreshReload();
            }
            if (witnessdetails.thiswitness != null) {
                witnessdetails.thiswitness.reFreshReload();
            }
            if (Complaintdetails.thisComplaint != null) {
                Complaintdetails.thisComplaint.reFreshReload();
            }

            stopSelf();


            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(GenericMethods.isOnline()) {
                        Intent intent = new Intent(Taskdatalist.this, NextActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                        startActivity(i);
                        Toast.makeText(Taskdatalist.this, "Task Data Refresh  Completed", Toast.LENGTH_LONG).show();

                    }else{
                        Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                        startActivity(i);
                        Toast.makeText(Taskdatalist.this, "Network Unavailable!!Refresh is not Completed", Toast.LENGTH_LONG).show();

                    }
                }
            });


        }
    }


}
