package anulom.executioner.com.anulom.services;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.widget.Toast;


import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import anulom.executioner.com.anulom.GenericMethods;
import anulom.executioner.com.anulom.database.DBOperation;
import anulom.executioner.com.anulom.fragment.CompletedDetails;
import anulom.executioner.com.anulom.fragment.NewDetails;
import anulom.executioner.com.anulom.fragment.OlderDetails;
import anulom.executioner.com.anulom.fragment.TodayDetails;
import anulom.executioner.com.anulom.fragment.marriagecompleted;
import anulom.executioner.com.anulom.fragment.marriagenew;
import anulom.executioner.com.anulom.fragment.marriageolder;
import anulom.executioner.com.anulom.fragment.marriagetoday;


import static com.google.android.gms.internal.zzir.runOnUiThread;

public class StatusReportService extends Service {
    GenericMethods mResponse;

    @Nullable

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mResponse = new GenericMethods();
        if (GenericMethods.isConnected(getApplicationContext())) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                new SyncStatusInfo()
                        .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

            } else {
                new SyncStatusInfo().execute();
            }
        }
        return START_STICKY;


    }




    private class SyncStatusInfo extends AsyncTask<String, Void, String> {
        DBOperation db = new DBOperation(getApplicationContext());

        @Override
        protected String doInBackground(String... params) {

            // TODO Auto-generated method stub
            String token = "DOtUBMhv5pk51tl0D37uBcezq85cXNN7hZQ7";
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String password2 = sharedPreferences.getString("password", "");
            ArrayList<HashMap<String, String>> statusreportlist = db.getSyncStatusReport(db);


            for (int i = 0; i < statusreportlist.size(); i++) {
                String docid = statusreportlist.get(i).get("DocId");
                String item = statusreportlist.get(i).get("OwnBio");
                String item1 = statusreportlist.get(i).get("TenantBio");
                String item2 = statusreportlist.get(i).get("Wit1");
                String item3 = statusreportlist.get(i).get("Wit2");
                String item4 = statusreportlist.get(i).get("Location");
                String item6 = statusreportlist.get(i).get("appid");


                postData(docid, item, item1, item2, item3, token, db, password2, item4, item4, item6);

            }


            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            stopSelf();
            if (TodayDetails.thisToday != null) {
                TodayDetails.thisToday.reFreshReload();
            }
            if (OlderDetails.thisOlderDetails != null) {
                OlderDetails.thisOlderDetails.reFreshReload();
            }
            if (NewDetails.thisnewDetails != null) {
                NewDetails.thisnewDetails.reFreshReload();
            }
            if (CompletedDetails.thiscompleteDetails != null) {
                CompletedDetails.thiscompleteDetails.reFreshReload();
            }

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


        }

    }

    public void postData(String did, String oc, String tc, String w1c, String w2c, String token, DBOperation db, String password, String location, String currenttime, String appid) {
        String URL = GenericMethods.MAIN_URL + "/api/v1/exec_status/appointment_details.json";
        try {
            // Execute HTTP Post Request
            JSONObject app = new JSONObject();
            app.put("docid", did);
            app.put("biocomp1", oc);
            app.put("biocomp", tc);
            app.put("reg_from_comp", w1c);
            app.put("witness", w2c);
            app.put("password", password);
            app.put("update_location", location);
            app.put("update_time", currenttime);
            app.put("appointment_id", appid);

            JSONObject tokenjson = new JSONObject();
            tokenjson.put("token", token);

            JSONObject main = new JSONObject();
            main.put("appointment", app);
            main.put("auth_token", tokenjson);

            String json = "";
            json = main.toString();
            String strResponsePost = mResponse.doPostRequest(URL, json);
            if (!strResponsePost.equals("")) {
                JSONObject jResult = new JSONObject(strResponsePost);
                String strStatus = jResult.getString("status");

                if (strStatus.equals("1")) {
                    db.UpdateAppointmentStatus(db, did);

                }if(strStatus.equals("0")){
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(StatusReportService.this, "Please Contact Support Team ", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                if (strStatus.equals("-1")) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(StatusReportService.this, "Contact support Team", Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }
        } catch (ClientProtocolException e) {
            stopSelf();
            // TODO Auto-generated catch block
        } catch (IOException e) {
            stopSelf();
            // TODO Auto-generated catch block
        } catch (JSONException e) {
            stopSelf();
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
