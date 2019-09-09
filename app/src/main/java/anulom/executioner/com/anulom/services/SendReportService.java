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


public class SendReportService extends Service {


    public String umail = Login.umailid;
    GenericMethods mResponse;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public int onStartCommand(Intent intent, int flags, int startId) {

        mResponse = new GenericMethods();
        SharedPreferences pref = this.getSharedPreferences("MyPref", 0);


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
            String password2 = sharedPreferences.getString("password", "");
            ArrayList<HashMap<String, String>> acvrreportlist = db.getAcvrReport(db);

            for (int i = 0; i < acvrreportlist.size(); i++) {
                    String exec_email = acvrreportlist.get(i).get("exec_email");
                    String docid = acvrreportlist.get(i).get("DocId");
                    String distance = acvrreportlist.get(i).get("Distance");
                    String amount = acvrreportlist.get(i).get("Amount");
                    String itemtransport = acvrreportlist.get(i).get("TransportType");
                    String app_id = acvrreportlist.get(i).get("Appointmentid");

                    postData(exec_email, docid, distance, amount, itemtransport, umail, token, app_id, db, password2);


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

        protected void onProgressUpdate(Integer... progress) {

        }

        public void postData(String execemail,String did, String distance, String amount, String transport, String executioner, String token, String app_id, DBOperation db, String password) {
            String URL = GenericMethods.MAIN_URL + "/api/v2/biometric_data/update_acvr";
            try {

                JSONObject app = new JSONObject();
                app.put("Executioner",execemail);
                app.put("Docid", did);
                app.put("app_id", app_id);
                app.put("Distance", distance);
                app.put("Amount", amount);
                app.put("Transport", transport);
                app.put("Executioner", executioner);
                app.put("password", password);

                JSONObject tokenjson = new JSONObject();
                tokenjson.put("token", token);


                JSONObject main = new JSONObject();
                main.put("user", app);
                main.put("auth_token", tokenjson);
                System.out.println("Token JSON:" + main);


                String json = "";
                json = main.toString();

                String strResponsePost = mResponse.doPostRequest(URL, json);


                if (!strResponsePost.equals("")) {
                    JSONObject jResult = new JSONObject(strResponsePost);
                    String strStatus = jResult.getString("status");
                        System.out.println("status"+strStatus);
                    if (strStatus.equals("1")) {
                        db.UpdateacvrStatus(db, app_id);

                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getApplicationContext(), "ACVR Data Updation Successfull", Toast.LENGTH_LONG).show();
                            }
                        });
                    }if(strStatus.equals("0")){
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(SendReportService.this, "From ACVR,Please Contact Support Team ", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    if (strStatus.equals("-1")) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(SendReportService.this, "From ACVR,Contact support Team", Toast.LENGTH_LONG).show();
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
