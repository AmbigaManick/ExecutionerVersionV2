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

import static anulom.executioner.com.anulom.database.DBManager.TableInfo.TABLE_PENDINGWITNESS;
import static anulom.executioner.com.anulom.database.DBManager.TableInfo.TABLE_UPDATE_PENDING_WITNESS;
import static com.google.android.gms.internal.zzir.runOnUiThread;

public class postpendingwitness extends Service {
    DBOperation db;

    public String umail = Login.umailid;
    GenericMethods mResponse;
    OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=UTF8");
    String username3, status = "1",biotype, attid, docid, location, time, appid, email, partytype, biometric, execemail, doc;
    JSONArray party = new JSONArray();

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

            ArrayList<HashMap<String, String>> postpendingwitnesslist = db.postpendingwitness(db);


            try {


                    for (int i = 0; i < postpendingwitnesslist.size(); i++) {



                            execemail = postpendingwitnesslist.get(i).get("execemail");
                            docid = postpendingwitnesslist.get(i).get("docu_id");
                            attid = postpendingwitnesslist.get(i).get("att_id");
                            email = postpendingwitnesslist.get(i).get("email");
                            partytype = postpendingwitnesslist.get(i).get("partytype");
                            biometric = postpendingwitnesslist.get(i).get("biometric");

                        if (biometric.equals("Yes")) {
                            biometric = "1";
                        } else if (biometric.equals("Thumb Not Match")) {
                            biometric = "0";
                        } else if(biometric.equals("Select")){
                            biometric = "";
                        }




                            // TODO Auto-generated catch block



                        postData(execemail,docid,attid,email,partytype,biometric);





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

        public void postData(String execemail,String docid,String attid,String email,String partytype,String biometric) {

            DBOperation db = new DBOperation(getApplicationContext());
            String token = "DOtUBMhv5pk51tl0D37uBcezq85cXNN7hZQ7";
            String URL =GenericMethods.MAIN_URL+"/api/v3/appointment_data/witness_biometric";


            try {


                JSONObject app = new JSONObject();
                app.put("exec_email", execemail);
                app.put("document_id", docid);
                JSONObject app1 = new JSONObject();
                app1.put("docid", docid);
                app1.put("att_id", attid);
                app1.put("email", email);
                app1.put("party_type", partytype);
                app1.put("biometric", biometric);
                JSONObject tokenjson = new JSONObject();
                tokenjson.put("token", token);
                JSONObject main = new JSONObject();
                main.put("appointment", app);
                main.put("parties", app1);
                main.put("auth_token", tokenjson);
                String json = "";
                json = main.toString();

                System.out.println("json"+main);
                String strResponsePost = mResponse.doPostRequest(URL, json);

                if (!strResponsePost.equals("")) {
                    JSONObject jResult = new JSONObject(strResponsePost);
                    String strStatus = jResult.getString("status");

                    System.out.println("status"+strStatus);
                    if (strStatus.equals("1")) {


                        SQLiteDatabase sqldb = db.getWritableDatabase();

                        int count = sqldb.delete(TABLE_UPDATE_PENDING_WITNESS, DBManager.TableInfo.pw_attid + "=?", new String[]{attid});

                        int count1 = sqldb.delete(TABLE_PENDINGWITNESS, DBManager.TableInfo.db_PW_attid + "=?", new String[]{attid});
                        System.out.println("Deleted:"+count+count1);

                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(postpendingwitness.this, "Pending Witness Updated  Successfully  ", Toast.LENGTH_LONG).show();
                            }
                        });


                    }if(strStatus.equals("0")){
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(postpendingwitness.this, "From Pending Witness,Please Contact Support Team ", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    if (strStatus.equals("-1")) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(postpendingwitness.this, "From Pending Witness,Contact support Team", Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Network Problem.", Toast.LENGTH_SHORT).show();
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
