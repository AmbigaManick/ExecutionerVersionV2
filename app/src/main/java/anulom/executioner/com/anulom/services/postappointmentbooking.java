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

import static com.google.android.gms.internal.zzir.runOnUiThread;

public class postappointmentbooking extends Service {
    DBOperation db;

    public String umail = Login.umailid;
    GenericMethods mResponse;
    OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=UTF8");
    String username3, status = "1", user, city, appid, document, request_no, slot_id, division_id, app_date, region_id, free, free_reason, attendees, attendeesemail, attendeescontact, address, landmark, contact_person;
    int count = 0;
    JSONArray appointment = new JSONArray();

    // ArrayList<HashMap<String, String>> party = new ArrayList<HashMap<String, String>>();

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
            db = new DBOperation(postappointmentbooking.this);
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String username2 = sharedPreferences.getString("username", "");
            username3 = username2;
            ArrayList<HashMap<String, String>> postappointmentlist = db.postappointment(db);


            try {

                for (int i = 0; i < postappointmentlist.size(); i++) {

                    document = postappointmentlist.get(i).get("document_id");
                    user = postappointmentlist.get(i).get("user");
                    request_no = postappointmentlist.get(i).get("request_no");
                    slot_id = postappointmentlist.get(i).get("slot_id");
                    division_id = postappointmentlist.get(i).get("division_id");
                    app_date = postappointmentlist.get(i).get("app_date");
                    region_id = postappointmentlist.get(i).get("region_id");
                    free = postappointmentlist.get(i).get("free");
                    free_reason = postappointmentlist.get(i).get("free_reason");
                    attendees = postappointmentlist.get(i).get("attendees");
                    attendeesemail = postappointmentlist.get(i).get("attendeesmail");
                    attendeescontact = postappointmentlist.get(i).get("attendeescontact");
                    address = postappointmentlist.get(i).get("address");
                    landmark = postappointmentlist.get(i).get("landmark");
                    city = postappointmentlist.get(i).get("city");
                    appid = postappointmentlist.get(i).get("appointment_id");
                    contact_person = postappointmentlist.get(i).get("contact_person") + "," + postappointmentlist.get(i).get("contact_personmail") + "," + postappointmentlist.get(i).get("contact_personcontact");


                    JSONObject details = new JSONObject();

                    details.put("name", attendees);
                    details.put("email", attendeesemail);
                    details.put("contact", attendeescontact);
                    appointment.put(details);
// TODO Auto-generated catch block

                    postData(user, request_no, slot_id, division_id, app_date, region_id, city, free, free_reason, appointment, address, landmark, contact_person);
                }


            } catch (Exception e) {


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

        public void postData(String user, String request_no, String slot_id, String division_id, String app_date, String region_id, String city, String free, String free_reason, JSONArray attendees, String address, String landmark, String contactperson) {

            db = new DBOperation(postappointmentbooking.this);
            String token = "DOtUBMhv5pk51tl0D37uBcezq85cXNN7hZQ7";
            String URL = GenericMethods.MAIN_URL2+"/plus/api/v1/appointment_data/book";


            try {


                JSONObject app = new JSONObject();
                app.put("user", user);
                app.put("request_no", request_no);
                app.put("slot_id", slot_id);
                app.put("app_date", app_date);
                app.put("division_id", division_id);
                app.put("region_id", region_id);
                app.put("city", city);
                app.put("free", free);
                app.put("free_reason", free_reason);
                app.put("attendees", attendees);
                app.put("address", address);
                app.put("landmark", landmark);
                app.put("contact_person", contactperson);
                app.put("app_flag", 1);

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
                    System.out.println("Book Appointment"+status+strResponsePost);

                    if (strStatus.equals(status)) {

                        SQLiteDatabase sqldb = db.getWritableDatabase();

                        int count = sqldb.delete(DBManager.TableInfo.APPOINTMENTBOOKING, DBManager.TableInfo.slotid + "=?", new String[]{slot_id});
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(postappointmentbooking.this, "Appointment Booked", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    if(strStatus.equals("0")){
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(postappointmentbooking.this, "From Appointment Booking,Please Contact Support Team ", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    if (strStatus.equals("-1")) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(postappointmentbooking.this, "From Appointment Booking,Contact support Team", Toast.LENGTH_LONG).show();
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
