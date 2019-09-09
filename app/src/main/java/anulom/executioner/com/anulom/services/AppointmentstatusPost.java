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

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import anulom.executioner.com.anulom.GenericMethods;
import anulom.executioner.com.anulom.Login;
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


/**
 * Created by Admin on 7/12/2016.
 */
public class AppointmentstatusPost extends Service {
    private SharedPreferences sharedPreferences;
    DBOperation db;

    public String umail = Login.umailid;
    GenericMethods mResponse;
    OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=UTF8");
    String username3, status1 = "1";
    private String username2 = "";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }


    public int onStartCommand(Intent intent, int flags, int startId) {

        mResponse = new GenericMethods();

        if (GenericMethods.isConnected(getApplicationContext())) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                new MyAsyncTask()
                        .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

            } else {
                new MyAsyncTask().execute();
            }
        }
        return START_STICKY;


    }

    public void onCreate() {


        // TODO Auto-generated method stub
        super.onCreate();


    }


    private class MyAsyncTask extends AsyncTask<String, Integer, Double> {
        DBOperation db = new DBOperation(getApplicationContext());

        String token = "DOtUBMhv5pk51tl0D37uBcezq85cXNN7hZQ7";

        @Override
        protected Double doInBackground(String... params) {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            username2 = sharedPreferences.getString("username", "");
            username3 = username2;

            ArrayList<HashMap<String, String>> multipartychecklist = db.getmultipartycheck(db);
            for (int i = 0; i < multipartychecklist.size(); i++) {
                String exec_email = multipartychecklist.get(i).get("exec_email");
                String docid = multipartychecklist.get(i).get("docid");
                String appointmentid = multipartychecklist.get(i).get("appointment_id");
                String status = multipartychecklist.get(i).get("status");
                String reason = multipartychecklist.get(i).get("reason");

                postData(exec_email, docid, appointmentid, status, reason, token);


            }


            return null;
        }

        protected void onPostExecute(Double result) {

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
                marriagetoday.thisToday .reFreshReload();
            }
            if (marriageolder.thisToday  != null) {
                marriageolder.thisToday .reFreshReload();
            }
            if (marriagenew.thisToday != null) {
                marriagenew.thisToday .reFreshReload();
            }
            if (marriagecompleted.thisToday != null) {
                marriagecompleted.thisToday.reFreshReload();
            }

        }

        protected void onProgressUpdate(Integer... progress) {

        }

        public void postData(String exec_email, String docid, String appointmentid, String status, String reason, String token) {

            DBOperation db = new DBOperation(getApplicationContext());


            String URL = GenericMethods.MAIN_URL+"/api/v2/biometric_data/appointment_status";
            try {
                JSONObject app = new JSONObject();
                app.put("exec_email", exec_email);
                app.put("docid", docid);
                app.put("appointment_id", appointmentid);
                app.put("status", status);
                app.put("reason", reason);

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


//                        SQLiteDatabase sqldb = db.getWritableDatabase();
//
//                        int count = sqldb.delete(MULTIWORK, DBManager.TableInfo.DOCU + "=?", new String[]{docid});
//
//                        System.out.println("Deleted:"+count);
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(AppointmentstatusPost.this, "Appointment Data Updated  Successfully  ", Toast.LENGTH_SHORT).show();
                            }
                        });



                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Network Problem.", Toast.LENGTH_SHORT).show();
                }




            } catch (ClientProtocolException e) {

                // TODO Auto-generated catch block
            } catch (IOException e) {

                // TODO Auto-generated catch block
            } catch (JSONException e) {

                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }


    }
}