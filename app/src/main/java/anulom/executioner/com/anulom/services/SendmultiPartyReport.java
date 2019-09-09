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
import java.util.List;

import anulom.executioner.com.anulom.GenericMethods;
import anulom.executioner.com.anulom.Login;
import anulom.executioner.com.anulom.database.DBManager;
import anulom.executioner.com.anulom.database.DBOperation;
import anulom.executioner.com.anulom.fragment.CompletedDetails;
import anulom.executioner.com.anulom.fragment.NewDetails;
import anulom.executioner.com.anulom.fragment.OlderDetails;
import anulom.executioner.com.anulom.fragment.TodayDetails;

import static anulom.executioner.com.anulom.database.DBManager.TableInfo.POSTDOC;
import static anulom.executioner.com.anulom.database.DBManager.TableInfo.UPDATEPARTY;
import static com.google.android.gms.internal.zzir.runOnUiThread;

public class SendmultiPartyReport extends Service {
    DBOperation db;

    public String umail = Login.umailid;
    GenericMethods mResponse;
    OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=UTF8");
    String username3, status = "1",biotype, document, attendance, location, time, appid, email, partytype, biometric, execemail, doc;


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


        @Override
        protected Double doInBackground(String... params) {

            DBOperation db = new DBOperation(getApplicationContext());
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String username2 = sharedPreferences.getString("username", "");
            username3 = username2;

            ArrayList<HashMap<String, String>> partieslist = db.getPartypost(db);
            List<String> postdoclist = db.getPostDoc(db);

            try {

                for (int j = 0; j < postdoclist.size(); j++) {
                    System.out.println("background process....");
                    System.out.println("pos doc:"+postdoclist.size());
                    System.out.println("j:"+j);
                    JSONArray party = new JSONArray();
                    doc = postdoclist.get(j);
                    for (int i = 0; i < partieslist.size(); i++) {

                        document = partieslist.get(i).get("document_id");

                        if (document.equals(doc)) {
                            System.out.println("j:"+j+" "+"i:"+i);


                            attendance = partieslist.get(i).get("att_id");
                            email = partieslist.get(i).get("email");
                            partytype = partieslist.get(i).get("party_type");
                            biometric = partieslist.get(i).get("biometric");
                            execemail = partieslist.get(i).get("exec_email");
                            location = partieslist.get(i).get("location");
                            time = partieslist.get(i).get("currenttime");
                            biotype = partieslist.get(i).get("biotype");
                            if(biotype.equals("1")){
                                appid = partieslist.get(i).get("appid");
                            }else{
                                appid = "";
                            }


                            if (biometric.equals("true")) {
                                biometric = "1";
                            } else if (biometric.equals("false")) {
                                biometric = "0";
                            } else {
                                biometric = "";
                            }
                            JSONObject parties = new JSONObject();

                            parties.put("docid", document);
                            parties.put("att_id", attendance);
                            parties.put("email", email);
                            parties.put("party_type", partytype);
                            parties.put("biometric", biometric);
                            party.put(parties);



                            // TODO Auto-generated catch block

                        }


                        if (i == (partieslist.size() - 1)) {
                            System.out.println("i:"+i+"j:"+j+"call size:"+partieslist.size());

                            postData(execemail, party, doc, location, time, appid);



                        }

                    }


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

        public void postData(String execemail, JSONArray party, String doc, String location, String time, String appid) {

            DBOperation db = new DBOperation(getApplicationContext());
            String token = "DOtUBMhv5pk51tl0D37uBcezq85cXNN7hZQ7";
            String URL = GenericMethods.MAIN_URL+"/api/v2/biometric_data/biometric_update";


            try {


                JSONObject app = new JSONObject();
                app.put("exec_email", execemail);
                app.put("docid", doc);
                app.put("bio_type", biotype);
                app.put("update_location", location);
                app.put("update_time", time);
                app.put("appointment_id", appid);
                JSONObject tokenjson = new JSONObject();
                tokenjson.put("token", token);
                JSONObject main = new JSONObject();
                main.put("appointment", app);
                main.put("parties", party);
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

                        int count = sqldb.delete(UPDATEPARTY, DBManager.TableInfo.DOCU + "=?", new String[]{doc});

                        int count1 = sqldb.delete(POSTDOC, DBManager.TableInfo.postdocument + "=?", new String[]{doc});
                        System.out.println("Deleted:"+count+count1);

                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(SendmultiPartyReport.this, "Biometric Data Updated  Successfully  ", Toast.LENGTH_SHORT).show();
                            }
                        });


                    }if(strStatus.equals("0")){
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(SendmultiPartyReport.this, "From Biometric,Please Contact Support Team ", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    if (strStatus.equals("-1")) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(SendmultiPartyReport.this, "From Biometric,Contact support Team", Toast.LENGTH_SHORT).show();
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