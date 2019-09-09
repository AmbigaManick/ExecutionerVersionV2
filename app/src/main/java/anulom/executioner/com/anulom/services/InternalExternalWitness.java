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

import static anulom.executioner.com.anulom.database.DBManager.TableInfo.POSTWITNESSDOC;
import static anulom.executioner.com.anulom.database.DBManager.TableInfo.TABLE_WITNESS;
import static com.google.android.gms.internal.zzir.runOnUiThread;

public class InternalExternalWitness extends Service {

    DBOperation db;

    public String umail = Login.umailid;
    GenericMethods mResponse;
    OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=UTF8");
    String username3, status = "1", document = "", doc = "", internal_userid1 = "",value="", witness_type = "", witness_type1 = "", witness_no1 = "", witness_no2 = "", internal_email1 = "", internal_userid2 = "", internal_email2 = "", execemail = "", internal_bio1 = "", internal_bio2 = "", external_bio1 = "", external_bio2 = "";


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

            ArrayList<HashMap<String, String>> internalexternallist = db.getinternalexternalpost(db);
            List<String> postwitnessdoclist = db.getPostwitnessDoc(db);

            try {
                for (int j = 0; j < postwitnessdoclist.size(); j++) {
                    JSONArray witnessarray = new JSONArray();

                    doc = postwitnessdoclist.get(j);


                    for (int i = 0; i < internalexternallist.size(); i++) {
                        document = internalexternallist.get(i).get("document_id");
//                        /System.out.println("document:" + doc + " " + document);
                        if (document.equals(doc)) {

                            internal_userid1 = internalexternallist.get(i).get("user_id1");
                            internal_email1 = internalexternallist.get(i).get("email1");
                            internal_userid2 = internalexternallist.get(i).get("user_id2");
                            internal_email2 = internalexternallist.get(i).get("email2");
                            witness_type = internalexternallist.get(i).get("type");
                            witness_type1 = internalexternallist.get(i).get("type1");
                            witness_no1 = internalexternallist.get(i).get("witness_no1");
                            witness_no2 = internalexternallist.get(i).get("witness_no2");
                            internal_bio1 = internalexternallist.get(i).get("internal_bio1");
                            internal_bio2 = internalexternallist.get(i).get("internal_bio2");
                            external_bio1 = internalexternallist.get(i).get("external_bio1");
                            external_bio2 = internalexternallist.get(i).get("external_bio2");
                            value=internalexternallist.get(i).get("value");
                            execemail = internalexternallist.get(i).get("exec_email");

//                            System.out.println("value:" + external_bio1 + " " + external_bio2);

                            if (internal_bio1.equals("Yes")) {
                                internal_bio1 = "1";
                            } else if (internal_bio1.equals("Thumb Not Match")) {
                                internal_bio1 = "0";
                            } else if(internal_bio1.equals("Select")){
                                internal_bio1 = "";
                            }

                            if (internal_bio2.equals("Yes")) {
                                internal_bio2 = "1";
                            } else if (internal_bio2.equals("Thumb Not Match")) {
                                internal_bio2 = "0";
                            } else if(internal_bio2.equals("Select")){
                                internal_bio2 = "";
                            }

                            if (external_bio1.equals("Yes")) {
                                external_bio1 = "1";
                            } else if (external_bio1.equals("Thumb Not Match")) {
                                external_bio1 = "0";
                            } else if(external_bio1.equals("Select")){
                                external_bio1 = "";
                            }

                            if (external_bio2.equals("Yes")) {
                                external_bio2 = "1";
                            } else if (external_bio2.equals("Thumb Not Match")) {
                                external_bio2 = "0";
                            } else if(external_bio2.equals("Select")){
                                external_bio2 = "";
                            }

                            if (internal_userid1.length() > 0 && internal_email1.length() > 0) {
                                JSONObject witness = new JSONObject();
                                System.out.println("internal witness1");
                                witness.put("witness_no", witness_no1);
                                witness.put("witness_type", witness_type);
                                witness.put("user_id", internal_userid1);
                                witness.put("email", internal_email1);
                                witness.put("biometric", internal_bio1);
                                witnessarray.put(witness);

                            }
                            if (internal_userid2.length() > 0 && internal_email2.length() > 0) {
                                JSONObject witness1 = new JSONObject();
                                System.out.println("internal witness2");

                                witness1.put("witness_no", witness_no1);
                                witness1.put("witness_type", witness_type);
                                witness1.put("user_id", internal_userid2);
                                witness1.put("email", internal_email2);
                                witness1.put("biometric", internal_bio2);
                                witnessarray.put(witness1);
                                System.out.println(internal_userid2 + "" + internal_email2 + "" + internal_bio2);

                            }
                            if (witness_type1.equals("1") && value.equals("external1")) {
                                System.out.println("external witness1"+external_bio1+witness_type1+external_bio1);

                                JSONObject witness2 = new JSONObject();
                                witness2.put("witness_no", witness_no2);
                                witness2.put("witness_type", witness_type1);
                                witness2.put("biometric", external_bio1);
                                witnessarray.put(witness2);
                                System.out.println("external_bio1" + external_bio1);

                            }
                            if (witness_type1.equals("1") && value.equals("external2")) {
                                System.out.println("external witness2"+external_bio2+witness_type1+external_bio2);

                                JSONObject witness3 = new JSONObject();
                                witness3.put("witness_no", witness_no2);
                                witness3.put("witness_type", witness_type1);
                                witness3.put("biometric", external_bio2);
                                witnessarray.put(witness3);
                                System.out.println("external_bio2" + external_bio2);


                            }


                            System.out.println("witness_array:" + witnessarray);

                            // TODO Auto-generated catch block
                        }
                        if (i == (internalexternallist.size() - 1)) {


                            postData(execemail, document, witnessarray);


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

        public void postData(String execemail, String doc, JSONArray witnessarray) {

            DBOperation db = new DBOperation(getApplicationContext());
            String token = "DOtUBMhv5pk51tl0D37uBcezq85cXNN7hZQ7";
            String URL = GenericMethods.MAIN_URL + "/api/v2/biometric_data/add_witness";

            try {


                JSONObject app = new JSONObject();
                app.put("Executioner", execemail);
                app.put("Docid", doc);
                JSONObject tokenjson = new JSONObject();
                tokenjson.put("token", token);
                JSONObject main = new JSONObject();
                main.put("document", app);
                main.put("witness", witnessarray);
                main.put("auth_token", tokenjson);
                String json = "";
                json = main.toString();

                System.out.println("json:" + main);
                String strResponsePost = mResponse.doPostRequest(URL, json);
                System.out.println("status:" + status + strResponsePost);

                if (!strResponsePost.equals("")) {
                    JSONObject jResult = new JSONObject(strResponsePost);
                    String strStatus = jResult.getString("status");
                    System.out.println("status:" + strStatus);

                    if (strStatus.equals(status)) {

                        SQLiteDatabase sqldb = db.getWritableDatabase();

                        int count = sqldb.delete(TABLE_WITNESS, DBManager.TableInfo.witness_docid + "=?", new String[]{doc});
                        System.out.println("deleted" + count);

                        int count1 = sqldb.delete(POSTWITNESSDOC, DBManager.TableInfo.postwitnessdocument + "=?", new String[]{doc});
                        System.out.println("deleted" + count1);

                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(InternalExternalWitness.this, "Internal Witness Updated Successfully!! ", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    if(strStatus.equals("0")){
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(InternalExternalWitness.this, "From Internal Witness,Please Contact Support Team ", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    if (strStatus.equals("-1")) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(InternalExternalWitness.this, "From Internal Witness,Contact support Team", Toast.LENGTH_LONG).show();
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
