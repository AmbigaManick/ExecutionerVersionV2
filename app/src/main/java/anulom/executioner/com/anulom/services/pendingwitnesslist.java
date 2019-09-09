package anulom.executioner.com.anulom.services;


import android.app.ProgressDialog;
import android.app.Service;

import android.content.Intent;
import android.content.SharedPreferences;

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

import anulom.executioner.com.anulom.database.DBOperation;
import anulom.executioner.com.anulom.fragment.witnesspending;

import static com.google.android.gms.internal.zzir.runOnUiThread;

public class pendingwitnesslist extends Service {


    JSONArray ja23 = null;

    GenericMethods response;
    String APPOINTMENTURL = "";
    String ID1;

    String jStrResult;
    DBOperation db;

    private String username2 = "";
    private String TAG = "";
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


        response = new GenericMethods();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {


            SharedPreferences usermail = PreferenceManager.getDefaultSharedPreferences(this);
            username2 = usermail.getString("username", "");
            String password2 = usermail.getString("password", "");
            APPOINTMENTURL = GenericMethods.MAIN_URL + "/api/v3/get_exec_data?get_auth_token=DOtUBMhv5pk51tl0D37uBcezq85cXNN7hZQ7&exec_email=" + username2 + "&password=" + password2 + "&version="+ GenericMethods.apiversion;
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


            String jsonStr = "";


            try {

                jsonStr = response.doGetRequest(APPOINTMENTURL);

            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null || !jsonStr.trim().isEmpty()) {
                try {
                    JSONObject jsonObject = null;

                    jsonObject = new JSONObject(jsonStr);

                     jStrResult = jsonObject.getString("status");

                    String jStrResult1 = jsonObject.getString("version");
                    GenericMethods.apiversion = jStrResult1;


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }



                if (jsonStr != null) {


                    try {
                        JSONObject jsonObj = new JSONObject(jsonStr);


                        ja23 = jsonObj.getJSONArray(GenericMethods.TAG_NAME19);


                        for (int n = 0; n < ja23.length(); n++) {

                            JSONObject c = ja23.getJSONObject(n);


                            String docid = c.getString(GenericMethods.PW_doc_id);
                            String env = c.getString(GenericMethods.PW_env);
                            String tokenno = c.getString(GenericMethods.PW_token_no);
                            String party_type = c.getString(GenericMethods.PW_partytype);
                            String att_id = c.getString(GenericMethods.PW_attid);
                            String witness_name = c.getString(GenericMethods.PW_witnessname);
                            String witness_biometric = c.getString(GenericMethods.PW_biometric);
                            String witness_email = c.getString(GenericMethods.PW_email);
                            String witness_contact = c.getString(GenericMethods.PW_contact);
                            String name = c.getString(GenericMethods.PW_name);
                            String ocontact = c.getString(GenericMethods.PW_ocontact);
                            String oemail = c.getString(GenericMethods.PW_oemail);
                            String oname = c.getString(GenericMethods.PW_oname);
                            String oaddress = c.getString(GenericMethods.PW_oaddress);
                            String tcontact = c.getString(GenericMethods.PW_tcontact);
                            String temail = c.getString(GenericMethods.PW_temail);
                            String tname = c.getString(GenericMethods.PW_tname);
                            String taddress = c.getString(GenericMethods.PW_taddress);
                            String Paddress = c.getString(GenericMethods.PW_Paddress);
                            String from_dt = c.getString(GenericMethods.PW_from_dt);
                            String to_dt = c.getString(GenericMethods.PW_to_dt);
                            String rent = c.getString(GenericMethods.PW_rent);
                            String deposit = c.getString(GenericMethods.PW_deposit);
                            String refunddeposit = c.getString(GenericMethods.PW_refunddeposit);
                            String city_name = c.getString(GenericMethods.PW_city_name);
                            String status_id = c.getString(GenericMethods.PW_status_id);
                            String status = c.getString(GenericMethods.PW_status);
                            String id = c.getString(GenericMethods.PW_id);
                            String agreement_cancle = c.getString(GenericMethods.PW_agreement_cancle);


                            db.Insertpendingwitness(db, ID1, docid, env, tokenno,
                                    party_type, att_id, witness_name, witness_biometric, witness_email, witness_contact
                                    , ocontact, oemail, oname, oaddress, tcontact, temail, tname, taddress, Paddress,
                                    from_dt, to_dt, rent, deposit, refunddeposit, city_name, status_id, status, id,
                                    agreement_cancle, username2);


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

            if (witnesspending.thisadhoc != null) {
                witnesspending.thisadhoc.reFreshReload();
            }

            stopSelf();
            System.out.println("refresh completed");


            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                 if(GenericMethods.isOnline()) {
                     Intent intent = new Intent(pendingwitnesslist.this, NextActivity.class);
                     intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                     startActivity(intent);
                     Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                     startActivity(i);
                     Toast.makeText(pendingwitnesslist.this, "Pending Witness Data Refresh  Completed", Toast.LENGTH_LONG).show();
                 }
                 else{
                     Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                     startActivity(i);
                     Toast.makeText(pendingwitnesslist.this, "Network Unavailable!!Refresh is not Completed", Toast.LENGTH_LONG).show();

                 }

                }
            });
            stopSelf();

        }
    }


}
