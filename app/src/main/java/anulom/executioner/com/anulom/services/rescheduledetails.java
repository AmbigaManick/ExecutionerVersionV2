package anulom.executioner.com.anulom.services;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
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
import anulom.executioner.com.anulom.database.DBManager;
import anulom.executioner.com.anulom.database.DBOperation;
import anulom.executioner.com.anulom.fragment.CompletedDetails;
import anulom.executioner.com.anulom.fragment.NewDetails;
import anulom.executioner.com.anulom.fragment.OlderDetails;
import anulom.executioner.com.anulom.fragment.TodayDetails;

import static anulom.executioner.com.anulom.database.DBManager.TableInfo.RESCHEDULE;
import static com.google.android.gms.internal.zzir.runOnUiThread;


public class rescheduledetails extends Service {
    DBOperation db;

    public String umail = Login.umailid;
    GenericMethods mResponse;
    OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=UTF8");
    String username3, status = "1";

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




    private class MyAsyncTask extends AsyncTask<String, Integer, Double> {
        DBOperation db = new DBOperation(getApplicationContext());

        String token = "DOtUBMhv5pk51tl0D37uBcezq85cXNN7hZQ7";

        @Override
        protected Double doInBackground(String... params) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String username2 = sharedPreferences.getString("username", "");
            username3 = username2;

            ArrayList<HashMap<String, String>> reschedulelist = db.getrescheduledetails(db);

            for (int i = 0; i < reschedulelist.size(); i++) {
                String exec_email = reschedulelist.get(i).get("exec_email");
                String docid = reschedulelist.get(i).get("docid");
                String appointmentid = reschedulelist.get(i).get("appointment_id");
                String rescheduledate = reschedulelist.get(i).get("rescheduledate");
                String rescheduletime = reschedulelist.get(i).get("rescheduletime");
                String reason = reschedulelist.get(i).get("reason1");

                postData(exec_email, docid, appointmentid, rescheduledate, rescheduletime, reason, token);

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

        }

        protected void onProgressUpdate(Integer... progress) {

        }

        public void postData(String exec_email, String docid, String appointmentid, String rescheduledate, String rescheduletime, String reason, String token) {

            DBOperation db = new DBOperation(getApplicationContext());


            String URL = GenericMethods.MAIN_URL+"/api/v2/biometric_data/reschedule_appointment";

            try {

                JSONObject app = new JSONObject();
                app.put("exec_email", exec_email);
                app.put("docid", docid);
                app.put("appointment_id", appointmentid);
                app.put("date", rescheduledate);
                app.put("time", rescheduletime);
                app.put("res_reason", reason);
                app.put("slot_id", "");
                app.put("app_flag", 1);

                JSONObject tokenjson = new JSONObject();
                tokenjson.put("token", token);

                JSONObject main = new JSONObject();
                main.put("appointment", app);
                main.put("auth_token", tokenjson);
                String json = "";
                json = main.toString();

                System.out.println("hiii"+json);
                String strResponsePost = mResponse.doPostRequest(URL, json);

                if (!strResponsePost.equals("")) {
                    JSONObject jResult = new JSONObject(strResponsePost);
                    String strStatus = jResult.getString("status");

                    if (strStatus.equals(status)) {

                        SQLiteDatabase sqldb = db.getWritableDatabase();

                        int count = sqldb.delete(RESCHEDULE, DBManager.TableInfo.DocumentId + "=?", new String[]{docid});
                    System.out.println("hiii"+strStatus);


                    }if(strStatus.equals("0")){
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(rescheduledetails.this, "From Reschedule,Please Contact Support Team ", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    if (strStatus.equals("-1")) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(rescheduledetails.this, "From Reschedule,Contact support Team", Toast.LENGTH_LONG).show();
                            }
                        });

                    }

                }
                System.out.println("**********Post Completed************");


            } catch (JSONException e) {

                // TODO Auto-generated catch block
                //     e.printStackTrace();
                // }

            } catch (ClientProtocolException e) {

                // TODO Auto-generated catch block
            } catch (IOException e) {

            }

        }


    }
}