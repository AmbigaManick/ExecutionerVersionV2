package anulom.executioner.com.anulom.services;



import android.app.ProgressDialog;
import android.app.Service;
import android.content.ContentValues;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

import android.os.AsyncTask;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import anulom.executioner.com.anulom.GenericMethods;


import anulom.executioner.com.anulom.database.DBManager;
import anulom.executioner.com.anulom.database.DBOperation;

import anulom.executioner.com.anulom.fragment.CompletedDetails;
import anulom.executioner.com.anulom.fragment.NewDetails;
import anulom.executioner.com.anulom.fragment.OlderDetails;
import anulom.executioner.com.anulom.fragment.TodayDetails;


import static anulom.executioner.com.anulom.database.DBManager.TableInfo.TABLE_NAME_APPOINTMENT;


public class Biometricdatalist extends Service {
    String startnewdate, startnewtime1, startnewtime2;
    ArrayList<String> ownername = new ArrayList<>();
    ArrayList<String> ownercontact = new ArrayList<>();
    ArrayList<String> documentid = new ArrayList<>();

    ArrayList<String> appointmentaddress = new ArrayList<>();
    ArrayList<String> executionerid = new ArrayList<>();
    ArrayList<String> appointmentid = new ArrayList<>();

    ArrayList<String> commentid = new ArrayList<>();
    ArrayList<String> Addpayid = new ArrayList<>();
    ArrayList<String> userlist = new ArrayList<>();
    ArrayList<HashMap<String, String>> getCommentDatalist = null;
    ArrayList<HashMap<String, String>> getAlldataList = null;
    ArrayList<HashMap<String, String>> getuserdataList = null;
    JSONArray ja = null;
    JSONArray ja3 = null;
    JSONArray ja4 = null;
    JSONArray ja5 = null;
    JSONArray ja20 = null;
    JSONArray ja25 = null;
    GenericMethods response;
    String APPOINTMENTURL = "";
    String ID1;


    DBOperation db;

    private String username2 = "";
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
            APPOINTMENTURL = GenericMethods.MAIN_URL + "/api/v3/get_exec_data?get_auth_token=DOtUBMhv5pk51tl0D37uBcezq85cXNN7hZQ7&exec_email=" + username2 + "&password=" + password2 + "&version="+GenericMethods.apiversion;
            System.out.println("values from get biometric  all data service file");
            arraylistclear();
            db = new DBOperation(getApplicationContext());
            new GetContacts().execute();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void arraylistclear() {
        ownername.clear();
        ownercontact.clear();
        documentid.clear();
        appointmentaddress.clear();
        executionerid.clear();
        appointmentid.clear();
        commentid.clear();
        Addpayid.clear();
        userlist.clear();
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

                    String jStrResult = jsonObject.getString("status");

                    String jStrResult1 = jsonObject.getString("version");
                    GenericMethods.apiversion = jStrResult1;


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }



                getAlldataList = db.getAllData(db);

                for (int i = 0; i < getAlldataList.size(); i++) {
                    documentid.add(getAlldataList.get(i).get("Did"));
                    appointmentid.add(getAlldataList.get(i).get("Aid"));
                    ownername.add(getAlldataList.get(i).get("Oname"));
                    executionerid.add(getAlldataList.get(i).get("Exid"));
                }

                getuserdataList = db.getdataList(db);

                for (int i = 0; i < getuserdataList.size(); i++) {
                    userlist.add(getuserdataList.get(i).get("UserId"));
                }

                getCommentDatalist = db.getCommentlist(db);

                for (int i = 0; i < getCommentDatalist.size(); i++) {
                    commentid.add(getCommentDatalist.get(i).get("Cid"));
                }


                if (jsonStr != null) {


                    try {
                        JSONObject jsonObj = new JSONObject(jsonStr);


                        ja = jsonObj.getJSONArray(GenericMethods.TAG_NAME);
                        ja3 = jsonObj.getJSONArray(GenericMethods.TAG_NAME3);
                        ja4 = jsonObj.getJSONArray(GenericMethods.TAG_NAME4);
                        ja20 = jsonObj.getJSONArray(GenericMethods.TAG_NAME14);
                        ja25 = jsonObj.getJSONArray(GenericMethods.TAG_NAME20);

                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject c = ja.getJSONObject(i);
                            String rtoken1 = c.getString(GenericMethods.TAG_RTOKEN);
                            String rkey1 = c.getString(GenericMethods.TAG_REPORT_KEY);
                            String sgrn = c.getString(GenericMethods.TAG_sgrn);
                            String rgrn = c.getString(GenericMethods.TAG_rgrn);
                            String uname1 = c.getString(GenericMethods.TAG_UNAME);
                            String email1 = c.getString(GenericMethods.TAG_UEMAIL);
                            String contact_number1 = c.getString(GenericMethods.TAG_UCONTACT);
                            String paddress1 = c.getString(GenericMethods.TAG_PADDRESS);
                            String oname1 = c.getString(GenericMethods.TAG_ONAME);
                            String ocontact1 = c.getString(GenericMethods.TAG_OCONTACT);
                            String oemail1 = c.getString(GenericMethods.TAG_OEMAIL);
                            String oaddress1 = c.getString(GenericMethods.TAG_OADDRESS);
                            String tname1 = c.getString(GenericMethods.TAG_TNAME);
                            String tcontact1 = c.getString(GenericMethods.TAG_TCONTACT);
                            String temail1 = c.getString(GenericMethods.TAG_TEMAIL);
                            String taddress1 = c.getString(GenericMethods.TAG_TADDRESS);
                            String status1 = c.getString(GenericMethods.TAG_STATUS);
                            String appdate = c.getString(GenericMethods.TAG_APPDATE);
                            String biocomp = c.getString(GenericMethods.TAG_BIO_COMP);
                            String appdate1 = c.getString(GenericMethods.TAG_APPDATE1);
                            String biocomp1 = c.getString(GenericMethods.TAG_BIO_COMP1);
                            String regfromcomp = c.getString(GenericMethods.TAG_REG_FROM_COMP);
                            String witness = c.getString(GenericMethods.TAG_WITNESS);
                            String shipadd = c.getString(GenericMethods.TAG_SHIP_ADDRESS);
                            String shipdiffadd = c.getString(GenericMethods.TAG_SHIP_DIFF_ADDRESS);
                            String strlatitude, strlongitude;
                            if (c.has(GenericMethods.TAG_CUSTOMER_LAT)) {
                                strlatitude = c.getString(GenericMethods.TAG_CUSTOMER_LAT);
                            } else strlatitude = "0";
                            if (c.has(GenericMethods.TAG_CUSTOMER_LONG)) {
                                strlongitude = c.getString(GenericMethods.TAG_CUSTOMER_LONG);
                            } else strlongitude = "0";
                            String docid = c.getString(GenericMethods.TAG_DOCID);

                            String app_status = c.getString(GenericMethods.TAG_APP_STATUS);

                            String appid = c.getString(GenericMethods.TAG_APPID);
                            String start1 = c.getString(GenericMethods.TAG_START1);
                            String start2 = c.getString(GenericMethods.TAG_START2);
                            String appaddress = c.getString(GenericMethods.TAG_APPADDRESS);
                            String exeid = c.getString(GenericMethods.TAG_EXECUTIONER_ID);
                            String appfor = c.getString(GenericMethods.TAG_APP_FOR);
                            String apptype = c.getString(GenericMethods.TAG_APPTYPE);
                            String post_status = c.getString(GenericMethods.TAG_POST_STATUS);

                            String landmark = c.getString(GenericMethods.TAG_LANDMARK);
                            String contact_person = c.getString(GenericMethods.TAG_CONTACTPERSON);

                            String distance1 = c.getString(GenericMethods.TAG_CUSTOMER_DISTANCE);
                            String amount1 = c.getString(GenericMethods.TAG_CUSTOMER_AMOUNT);
                            String transporttype1 = c.getString(GenericMethods.TAG_CUSTOMER_TRANS_TYPE);
                            String biostatus = c.getString(GenericMethods.TAG_DOC_BIOMETRIC_STATUS);


                            JSONObject jsonatten = ja.getJSONObject(i);

                            JSONArray jsonattendees = jsonatten.getJSONArray(GenericMethods.TAG_NAME13);

                            if (jsonattendees != null) {


                                for (int j = 0; j < jsonattendees.length(); j++) {
                                    JSONObject atten = jsonattendees.getJSONObject(j);

                                    if (atten != null) {
                                        String name = atten.getString(GenericMethods.TAG_NAMEATTEND);
                                        String email = atten.getString(GenericMethods.TAG_EMAILATTEND);
                                        String contact = atten.getString(GenericMethods.TAG_CONTACTATTEND);


                                        db.Insertbioattendees(db, ID1, name, email, contact, appid, username2);


                                    }

                                }

                            }


                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                            SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
                            SimpleDateFormat output1 = new SimpleDateFormat("HH:mm");
                            SimpleDateFormat output2 = new SimpleDateFormat("HH:mm");

                            java.util.Date date, time1, time2;
                            try {
                                date = sdf.parse(start1);
                                time1 = sdf.parse(start1);
                                time2 = sdf.parse(start2);
                                startnewdate = output.format(date);
                                startnewtime1 = output1.format(time1);
                                startnewtime2 = output2.format(time2);

                            } catch (ParseException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                            if (documentid.contains(docid)) {


                                db.Update(db, docid, rtoken1, rkey1, uname1, email1, contact_number1, paddress1, oname1,
                                        ocontact1, oemail1, oaddress1, tname1, tcontact1, temail1, taddress1, status1,
                                        appdate, biocomp, appdate1, biocomp1, regfromcomp, witness, shipadd, shipdiffadd, apptype, biostatus,sgrn,rgrn);
                            } else {
                                if (!db.DocumentID(db, docid)) {


                                    db.InsertRecord(db, rtoken1, rkey1, uname1, email1, contact_number1, paddress1, oname1,
                                            ocontact1, oemail1, oaddress1, tname1, tcontact1, temail1, taddress1, status1,
                                            username2, docid, appdate, biocomp, appdate1, biocomp1, regfromcomp, witness, shipadd,
                                            shipdiffadd, "SYNC", apptype, biostatus,sgrn,rgrn);
                                }
                            }

                            if (appointmentid.contains(appid)) {

                                db.Updateacvr(db, appid, docid, startnewdate, startnewtime1, startnewtime2, appaddress, exeid,
                                        appfor, distance1, amount1, transporttype1, contact_person, landmark, "", post_status, strlatitude, strlongitude, "false", app_status);

                            } else {
                                db.InsertRecord2(db, docid, appid, startnewdate, startnewtime1, startnewtime2, appaddress,
                                        exeid, appfor, distance1, amount1, transporttype1, "SYNC", contact_person, landmark, "", post_status, strlatitude, strlongitude, "false", app_status);

                            }

                            for (int i1 = 0; i1 < db.getnotificationtable(db).size(); i1++) {


                                if (db.getnotificationtable(db).get(i1).get("appid").equals(appid)) {


                                    db.Updateacvr(db, appid, docid, startnewdate, startnewtime1, startnewtime2, appaddress, exeid,
                                            appfor, distance1, amount1, transporttype1, contact_person, landmark, "", post_status, strlatitude, strlongitude, "true", app_status);


                                }

                            }
                        }


                        for (int l = 0; l < ja3.length(); l++) {
                            JSONObject c = ja3.getJSONObject(l);
                            String userid = c.getString(GenericMethods.TAG_USERID);
                            String username = c.getString(GenericMethods.TAG_USERNAME);
                            String email = c.getString(GenericMethods.TAG_EMAIL);
                            String role = c.getString(GenericMethods.TAG_ROLE);
                            String platformid = c.getString(GenericMethods.TAG_PLATFORMID);
                            String roleid = c.getString(GenericMethods.TAG_ROLEID);
                            String idu = c.getString(GenericMethods.TAG_IDu);

                            if (userlist.contains(userid)) {

                                db.Updateuser(db, userid, username, email, role, platformid, roleid, idu);


                            } else {
                                if (!db.checkUserId(db, userid)) {
                                    db.InsertRecord5(db, userid, username, email, role, platformid, roleid, idu);
                                }
                            }
                        }


                        for (int n = 0; n < ja4.length(); n++) {
                            JSONObject c = ja4.getJSONObject(n);
                            String DocumentId = c.getString(GenericMethods.TAG_DOCUMENTID);
                            String Amt = c.getString(GenericMethods.TAG_PAYAMOUNT);
                            String ReportKey = c.getString(GenericMethods.TAG_REPORTKEY);

                            db.Insertbiopayment(db, ID1, DocumentId, ReportKey, Amt, username2);


                        }


                        for (int n = 0; n < ja20.length(); n++) {
                            JSONObject c = ja20.getJSONObject(n);
                            String DocumentId = c.getString(GenericMethods.TAG_DOCUMENTID);
                            String att_status = c.getString(GenericMethods.Tag_att_status);


                            SQLiteDatabase sqldb = db.getWritableDatabase();
                            ContentValues values = new ContentValues();
                            values.put(DBManager.TableInfo.att_status, att_status);
                            sqldb.update(TABLE_NAME_APPOINTMENT, values, DBManager.TableInfo.DocumentId + "=?", new String[]{DocumentId});
                            sqldb.close();


                        }


                        ja5 = jsonObj.getJSONArray(GenericMethods.TAG_NAME5);

                        for (int n = 0; n < ja5.length(); n++) {

                            JSONObject c = ja5.getJSONObject(n);


                            String Document1 = c.getString(GenericMethods.TAG_DOCUMENT);
                            String Attendance = c.getString(GenericMethods.TAG_ATTENDANCE);
                            String name = c.getString(GenericMethods.TAG_NAMENEW);
                            String email = c.getString(GenericMethods.TAG_EMAILNEW);
                            String partytype = c.getString(GenericMethods.TAG_PARTYTYPE);
                            String poa = c.getString(GenericMethods.TAG_POA);
                            String contact = c.getString(GenericMethods.TAG_CONTACTNUMBER);
                            String biometric = c.getString(GenericMethods.TAG_BIOMETRIC);
                            String address = c.getString(GenericMethods.TAG_PARTY_ADDRESS);


                            db.Insertbioparties(db, ID1, Document1, Attendance, name, startnewdate, email, partytype, poa, contact, biometric, address, username2);


                        }

                        for (int n = 0; n < ja25.length(); n++) {

                            JSONObject c = ja25.getJSONObject(n);


                            String penaltyid = c.getString(GenericMethods.penalty_id);
                            String penaly_appid = c.getString(GenericMethods.penalty_appointment_id);
                            String penalty_docid = c.getString(GenericMethods.penalty_doc_id);
                            String penalty_execid = c.getString(GenericMethods.penalty_exec_id);
                            String penalty_amount = c.getString(GenericMethods.penalty_amount);
                            String penalty_verify = c.getString(GenericMethods.penalty_verify);
                            String penalty_type = c.getString(GenericMethods.penalty_type);
                            String penalty_managercomment = c.getString(GenericMethods.penalty_manager_comment);
                            String penalty_systemreason = c.getString(GenericMethods.penalty_system_reason);
                            String penalty_createdat = c.getString(GenericMethods.penalty_created_at);
                            String penalty_env = c.getString(GenericMethods.penalty_env);


                            db.InsertPenaltyreports(db, ID1, penaltyid, penaly_appid, penalty_docid, penalty_execid, penalty_amount, penalty_verify, penalty_type, penalty_managercomment, penalty_systemreason, penalty_createdat, penalty_env,username2);


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
              stopSelf();

        }
    }


}
