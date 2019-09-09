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
import anulom.executioner.com.anulom.fragment.marriagecompleted;
import anulom.executioner.com.anulom.fragment.marriagenew;
import anulom.executioner.com.anulom.fragment.marriageolder;
import anulom.executioner.com.anulom.fragment.marriagetoday;




import static anulom.executioner.com.anulom.database.DBManager.TableInfo.TABLE_MARRIAGE_SINGLE_BIOMETRIC;
import static com.google.android.gms.internal.zzir.runOnUiThread;

public class marriagebiometricpost extends Service {
    DBOperation db;

    public String umail = Login.umailid;
    GenericMethods mResponse;
    OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=UTF8");
    String username3, status = "1", exec_email, docid, appid, hus_bio, wife_bio, gen_distance = "";

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
            ArrayList<HashMap<String, String>> getmarriagebiolist = db.getmarriagebiometricpost(db);


            try {

                for (int i = 0; i < getmarriagebiolist.size(); i++) {


                    exec_email = getmarriagebiolist.get(i).get("exec_email");
                    docid = getmarriagebiolist.get(i).get("docid");
                    appid = getmarriagebiolist.get(i).get("appointment_id");
                    hus_bio = getmarriagebiolist.get(i).get("husband_biometric");
                    wife_bio = getmarriagebiolist.get(i).get("wife_biometric");


                    postData(exec_email, docid, appid, hus_bio, wife_bio);
                }


            } catch (Exception e) {

                // TODO Auto-generated catch block
                //     e.printStackTrace();
                // }

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

        public void postData(String exec_email, String docid, String appid, String husbandbometric, String wifebiometric) {

            DBOperation db = new DBOperation(getApplicationContext());
            String token = "DOtUBMhv5pk51tl0D37uBcezq85cXNN7hZQ7";
            String URL =GenericMethods.MAIN_URL+"/api/v3/appointment_data/single_biometric_update";



            try {


                JSONObject app = new JSONObject();
                app.put("exec_email", exec_email);
                app.put("docid", docid);
                app.put("appointment_id", appid);
                app.put("husband_biometric", husbandbometric);
                app.put("wife_biometric",wifebiometric);



                JSONObject tokenjson = new JSONObject();
                tokenjson.put("token", token);
                JSONObject main = new JSONObject();
                main.put("appointment", app);
                main.put("auth_token", tokenjson);
                String json = "";
                json = main.toString();

                      System.out.println("bio json"+json);
                String strResponsePost = mResponse.doPostRequest(URL, json);
                System.out.println("Response"+strResponsePost);
                if (!strResponsePost.equals("")) {
                    JSONObject jResult = new JSONObject(strResponsePost);
                    String strStatus = jResult.getString("status");

                    System.out.println(strStatus);
                    if (strStatus.equals(status)) {

                        SQLiteDatabase sqldb = db.getWritableDatabase();

                        int count = sqldb.delete(TABLE_MARRIAGE_SINGLE_BIOMETRIC, DBManager.TableInfo.db_marriage_doc_id + "=?", new String[]{docid});
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(marriagebiometricpost.this, "Marriage Biometric Updated  Successfully  ", Toast.LENGTH_LONG).show();
                            }
                        });

                    }if(strStatus.equals("0")){
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(marriagebiometricpost.this, "From Marriage Biometric,Please Contact Support Team ", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    if (strStatus.equals("-1")) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(marriagebiometricpost.this, "From Marriage Biometric,Contact support Team", Toast.LENGTH_LONG).show();
                            }
                        });

                    }

                }


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
