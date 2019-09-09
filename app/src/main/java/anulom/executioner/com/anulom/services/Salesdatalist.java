//package anulom.executioner5.com3.anulom.services;
//
//
//
//import android.app.ProgressDialog;
//import android.app.Service;
//import android.content.ContentValues;
//
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.database.sqlite.SQLiteDatabase;
//
//import android.os.AsyncTask;
//import android.os.IBinder;
//import android.preference.PreferenceManager;
//import android.support.annotation.Nullable;
//import android.util.Log;
//import android.widget.Toast;
//
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//
//import java.io.IOException;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.HashMap;
//
//import anulom.executioner5.com3.anulom.GenericMethods;
//
//
//import anulom.executioner5.com3.anulom.NextActivity;
//import anulom.executioner5.com3.anulom.database.DBManager;
//import anulom.executioner5.com3.anulom.database.DBOperation;
//
//import anulom.executioner5.com3.anulom.fragment.salescompleted;
//import anulom.executioner5.com3.anulom.fragment.salesnew;
//import anulom.executioner5.com3.anulom.fragment.salesolder;
//import anulom.executioner5.com3.anulom.fragment.salestoday;
//
//import static com.google.android.gms.internal.zzir.runOnUiThread;
//
//
//public class Salesdatalist extends Service {
//    String startnewdate, startnewtime1, startnewtime2, docidsales, appidsales, docidmarriage, docidmarriage1, appidmarriage1, appidmarriage;
//    ArrayList<String> ownername = new ArrayList<>();
//    ArrayList<String> ownercontact = new ArrayList<>();
//    ArrayList<String> documentid = new ArrayList<>();
//    ArrayList<String> documentsalesid = new ArrayList<>();
//    ArrayList<String> documentmarriageid = new ArrayList<>();
//    ArrayList<String> appointmentaddress = new ArrayList<>();
//    ArrayList<String> executionerid = new ArrayList<>();
//    ArrayList<String> appointmentid = new ArrayList<>();
//    ArrayList<String> appointmentsalesid = new ArrayList<>();
//    ArrayList<String> appointmentmarriageid = new ArrayList<>();
//    ArrayList<String> commentid = new ArrayList<>();
//    ArrayList<String> Addpayid = new ArrayList<>();
//    ArrayList<String> userlist = new ArrayList<>();
//    ArrayList<HashMap<String, String>> getsalesdataList = null;
//
//    JSONArray ja21 = null;
//
//    String jStrResult;
//    GenericMethods response;
//    String APPOINTMENTURL = "";
//    String ID1;
//
//
//    DBOperation db;
//
//    private String username2 = "";
//    private String TAG = "";
//    ProgressDialog pDialog1;
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    public void onCreate() {
//        // TODO Auto-generated method stub
//        super.onCreate();
//        db = new DBOperation(getApplicationContext());
//
//
//        response = new GenericMethods();
//
//
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        try {
//
//
//            SharedPreferences usermail = PreferenceManager.getDefaultSharedPreferences(this);
//            username2 = usermail.getString("username", "");
//            String password2 = usermail.getString("password", "");
//            APPOINTMENTURL = GenericMethods.MAIN_URL + "/api/v3/get_exec_data?get_auth_token=DOtUBMhv5pk51tl0D37uBcezq85cXNN7hZQ7&exec_email=" + username2 + "&password=" + password2 + "&version="+GenericMethods.apiversion;
//            System.out.println("values from get all data service file");
//            arraylistclear();
//            db = new DBOperation(getApplicationContext());
//            new GetContacts().execute();
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return super.onStartCommand(intent, flags, startId);
//    }
//
//    private void arraylistclear() {
//
//        documentsalesid.clear();
//        appointmentsalesid.clear();
//
//    }
//
//    private class GetContacts extends AsyncTask<Void, Void, Void> {
//
//
//
//        @Override
//        protected Void doInBackground(Void... arg0) {
//
//
//            String jsonStr = "";
//
//            try {
//
//                jsonStr = response.doGetRequest(APPOINTMENTURL);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            Log.d("Response: ", "> " + jsonStr);
//
//            if (jsonStr != null || !jsonStr.trim().isEmpty()) {
//                try {
//                    JSONObject jsonObject = null;
//
//                    jsonObject = new JSONObject(jsonStr);
//
//                     jStrResult = jsonObject.getString("status");
//                    System.out.println(jStrResult);
//
//                    String jStrResult1 = jsonObject.getString("version");
//                    GenericMethods.apiversion = jStrResult1;
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            getsalesdataList = db.getAllsalesData(db);
//
//            for (int i = 0; i < getsalesdataList.size(); i++) {
//
//                documentsalesid.add(getsalesdataList.get(i).get("document_id"));
//                appointmentsalesid.add(getsalesdataList.get(i).get("appointment_id"));
//
//            }
//
//
//
//                if (jsonStr != null) {
//
//
//                    try {
//                        JSONObject jsonObj = new JSONObject(jsonStr);
//
//
//                        ja21 = jsonObj.getJSONArray(GenericMethods.TAG_NAME15);
//
//
//                        for (int i = 0; i < ja21.length(); i++) {
//
//
//                            JSONObject c = ja21.getJSONObject(i);
//                            String sales_contactperson = c.getString(GenericMethods.sales_contactperson);
//                            String sales_start_time = c.getString(GenericMethods.sales_starttime);
//                            String sales_end_time = c.getString(GenericMethods.sales_endtime);
//                            String sales_executioner_id = c.getString(GenericMethods.sales_executioner_id);
//                            String sales_app_for = c.getString(GenericMethods.sales_app_for);
//                            String sales_document_id = c.getString(GenericMethods.sales_doc_id);
//                            String sales_created = c.getString(GenericMethods.sales_created_at);
//                            String sales_appointment_id = c.getString(GenericMethods.sales_appointment_id);
//                            String sales_app_ref = c.getString(GenericMethods.sales_app_ref);
//                            String sales_app_city = c.getString(GenericMethods.sales_app_city);
//                            String sales_u_flag = c.getString(GenericMethods.sales_u_flag);
//                            String sales_create_dt = c.getString(GenericMethods.sales_create_dt);
//                            String sales_approved_by = c.getString(GenericMethods.sales_approved_by);
//                            String sales_created_by = c.getString(GenericMethods.sales_createdby);
//                            String sales_logical_del = c.getString(GenericMethods.sales_logical_del);
//                            String sales_poststatus = c.getString(GenericMethods.sales_poststatus);
//                            String sales_apptype = c.getString(GenericMethods.sales_app_type);
//                            String sales_env = c.getString(GenericMethods.sales_env);
//                            String sales_id = c.getString(GenericMethods.sales_id);
//                            String sales_address = c.getString(GenericMethods.sales_address);
//                            String sales_landmark = c.getString(GenericMethods.sales_landmark);
//                            String sales_distance = c.getString(GenericMethods.sales_distance);
//                            String sales_trans_type = c.getString(GenericMethods.sales_transtype);
//                            String sales_amount = c.getString(GenericMethods.sales_amount);
//                            String sales_acvr_id = c.getString(GenericMethods.sales_acvr_id);
//                            String sales_app_status = c.getString(GenericMethods.sales_app_status);
//                            String sales_latitude, sales_longititude;
//                            String sync = "SYNC";
//                            String sales_acvrreport = "SYNC";
//
//
//                            if (c.has(GenericMethods.sales_latitude)) {
//                                sales_latitude = c.getString(GenericMethods.sales_latitude);
//
//                            } else sales_latitude = "0";
//                            if (c.has(GenericMethods.sales_longititude)) {
//                                sales_longititude = c.getString(GenericMethods.sales_longititude);
//                            } else sales_longititude = "0";
//
//
//                            JSONObject jsonsalesatten = ja21.getJSONObject(i);
//
//                            JSONArray jsonsalesattendees = jsonsalesatten.getJSONArray(GenericMethods.TAG_NAME17);
//
//                            if (jsonsalesattendees != null) {
//
//
//                                for (int j = 0; j < jsonsalesattendees.length(); j++) {
//                                    JSONObject salesatten = jsonsalesattendees.getJSONObject(j);
//
//                                    if (salesatten != null) {
//                                        String sales_name = salesatten.getString(GenericMethods.sales_name);
//                                        String sales_email = salesatten.getString(GenericMethods.sales_mail);
//                                        String sales_contact = salesatten.getString(GenericMethods.sales_contact);
//
//
//                                        db.InsertsalesattendeesRecord(db, ID1, sales_name, sales_email, sales_contact, sales_appointment_id, username2);
//
//
//                                    }
//
//                                }
//
//                            }
//
//
//                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//                            SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
//                            SimpleDateFormat output1 = new SimpleDateFormat("HH:mm");
//                            SimpleDateFormat output2 = new SimpleDateFormat("HH:mm");
//
//                            java.util.Date date, time1, time2;
//                            try {
//                                date = sdf.parse(sales_start_time);
//                                time1 = sdf.parse(sales_start_time);
//                                time2 = sdf.parse(sales_end_time);
//                                startnewdate = output.format(date);
//                                startnewtime1 = output1.format(time1);
//                                startnewtime2 = output2.format(time2);
//
//                            } catch (ParseException e) {
//                                // TODO Auto-generated catch block
//                                e.printStackTrace();
//                            }
//
//
//                            if (appointmentsalesid.contains(sales_appointment_id)) {
//
//                                db.updatesalesappointment(db, sales_appointment_id, sales_contactperson, startnewtime1,
//                                        startnewtime2, startnewdate, sales_executioner_id, sales_app_for, sales_document_id, sales_created, sales_app_ref, sales_app_city, sales_u_flag, sales_create_dt, sales_approved_by,
//                                        sales_created_by, sales_latitude, sales_longititude, sales_logical_del, sales_poststatus, sales_apptype, sales_id, username2, sales_address, sales_landmark, sales_acvr_id, sales_distance, sales_trans_type, sales_amount, sync, sales_acvrreport, "false", sales_app_status);
//
//                            } else {
//                                db.InsertsalesappointmentRecord(db, sales_appointment_id, sales_contactperson, startnewtime1,
//                                        startnewtime2, startnewdate, sales_executioner_id, sales_app_for, sales_document_id, sales_created, sales_app_ref, sales_app_city, sales_u_flag, sales_create_dt, sales_approved_by,
//                                        sales_created_by, sales_latitude, sales_longititude, sales_logical_del, sales_poststatus, sales_apptype, sales_id, username2, sales_address, sales_landmark, sales_acvr_id, sales_distance, sales_trans_type, sales_amount, sync, sales_acvrreport, "false", sales_app_status);
//
//                            }
//
//
//                            if (documentsalesid.contains(sales_document_id)) {
//                                db.Updatesalesdocuments(db, sales_env, sales_document_id, username2,
//                                        sync, sales_acvrreport);
//
//                            } else if (!db.DocumentsalesID(db, sales_document_id)) {
//                                db.Insertsalesdocuments(db, sales_env, sales_document_id, username2,
//                                        sync, sales_acvrreport);
//                            }
//
//                            for (int i1 = 0; i1 < db.getnotificationtable(db).size(); i1++) {
//
//                                if (db.getnotificationtable(db).get(i1).get("appid").equals(sales_appointment_id)) {
//
//
//                                    SQLiteDatabase sqldb2 = db.getWritableDatabase();
//                                    ContentValues values2 = new ContentValues();
//                                    values2.put(DBManager.TableInfo.notification, "true");
//                                    int count4 = sqldb2.update(DBManager.TableInfo.TABLE_SALES_APPOINTMENT, values2, DBManager.TableInfo.db_sales_appointment_id + "=?", new String[]{sales_appointment_id});
//                                    sqldb2.close();
//
//
//                                }
//
//                            }
//
//                        }
//
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    Log.e("ServiceHandler", "Couldn't get any data from the url");
//                }
//
//
//
//
//            return null;
//
//        }
//
//
//        @Override
//        protected void onPostExecute(Void result) {
//            super.onPostExecute(result);
//
//            stopSelf();
//            if (salestoday.thisToday != null) {
//                salestoday.thisToday.reFreshReload();
//            }
//            if (salesolder.thisToday != null) {
//                salesolder.thisToday.reFreshReload();
//            }
//            if (salesnew.thisToday != null) {
//                salesnew.thisToday.reFreshReload();
//            }
//            if (salescompleted.thisToday != null) {
//                salescompleted.thisToday.reFreshReload();
//            }
//
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Intent intent = new Intent(Salesdatalist.this, NextActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                    Toast.makeText(Salesdatalist.this, "Sales Data Refresh  Completed", Toast.LENGTH_LONG).show();
//
//
//                }
//            });
//        }
//    }
//
//
//}
