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
import org.json.JSONArray;
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

import static anulom.executioner.com.anulom.database.DBManager.TableInfo.ACTUAL_TIME;
import static com.google.android.gms.internal.zzir.runOnUiThread;

public class fetchactualtime extends Service {
    DBOperation db;
    public String umail = Login.umailid;
    GenericMethods mResponse;
    OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=UTF8");
    String username3, status = "1", exec_email, docid, appid, time, call, distance;
    JSONArray appointment = new JSONArray();

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


        @Override
        protected Double doInBackground(String... params) {
            DBOperation db = new DBOperation(getApplicationContext());
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String username2 = sharedPreferences.getString("username", "");
            username3 = username2;
            ArrayList<HashMap<String, String>> getactualtimelist = db.getactualtime(db);


            try {

                for (int i = 0; i < getactualtimelist.size(); i++) {


                    exec_email = getactualtimelist.get(i).get("exec_email");
                    appid = getactualtimelist.get(i).get("appointment_id");
                    docid=getactualtimelist.get(i).get("docid");
                    time = getactualtimelist.get(i).get("actual_time");
                    call = getactualtimelist.get(i).get("call_time");
                    distance = getactualtimelist.get(i).get("gen_distance");
                    postData(exec_email, docid, appid, time, call, distance);
                }


            } catch (Exception e) {

                // TODO Auto-generated catch block


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

        public void postData(String exec_email, String docid, String appid, String time, String call, String distance) {

            DBOperation db = new DBOperation(getApplicationContext());
            String token = "DOtUBMhv5pk51tl0D37uBcezq85cXNN7hZQ7";
            String URL = GenericMethods.MAIN_URL+"/api/v2/biometric_data/app_update";


            try {


                JSONObject app = new JSONObject();
                app.put("Executioner", exec_email);
                app.put("Docid", docid);
                app.put("app_id", appid);
                app.put("actual_time", time);
                app.put("call_time", "");
                app.put("gen_distance", "");


                JSONObject tokenjson = new JSONObject();
                tokenjson.put("token", token);
                JSONObject main = new JSONObject();
                main.put("user", app);
                main.put("auth_token", tokenjson);
                String json = "";
                json = main.toString();
                String strResponsePost = mResponse.doPostRequest(URL, json);
                System.out.println("status"+strResponsePost);
                if (!strResponsePost.equals("")) {
                    JSONObject jResult = new JSONObject(strResponsePost);
                    String strStatus = jResult.getString("status");
                    System.out.println("status"+strStatus);

                    if (strStatus.equals(status)) {

                        SQLiteDatabase sqldb = db.getWritableDatabase();

                        int count = sqldb.delete(ACTUAL_TIME, DBManager.TableInfo.actual_time + "=?", new String[]{time});
                         System.out.println("deleted"+count);

                    }
                    if(strStatus.equals("0")){
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(fetchactualtime.this, "From Actual Reaching Time,Please Contact Support Team ", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    if (strStatus.equals("-1")) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(fetchactualtime.this, "From Actual Reaching Time,Contact support Team", Toast.LENGTH_LONG).show();
                            }
                        });

                    }

                }

            } catch (JSONException e) {

                // TODO Auto-generated catch block


            } catch (ClientProtocolException e) {

                // TODO Auto-generated catch block
            } catch (IOException e) {

            }
        }


    }


}
