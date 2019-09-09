//package anulom.executioner5.com3.anulom.services;
//
//
//import android.app.Service;
//import android.content.ContentValues;
//
//import android.content.Intent;
//import android.content.SharedPreferences;
//
//import android.database.sqlite.SQLiteDatabase;
//
//import android.os.AsyncTask;
//import android.os.IBinder;
//import android.preference.PreferenceManager;
//import android.support.annotation.Nullable;
//import android.util.Log;
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
//import anulom.executioner5.com3.anulom.MyDialog;
//
//
//import anulom.executioner5.com3.anulom.database.DBManager;
//import anulom.executioner5.com3.anulom.database.DBOperation;
//import anulom.executioner5.com3.anulom.fragment.Complaintdetails;
//import anulom.executioner5.com3.anulom.fragment.CompletedDetails;
//import anulom.executioner5.com3.anulom.fragment.NewDetails;
//import anulom.executioner5.com3.anulom.fragment.OlderDetails;
//import anulom.executioner5.com3.anulom.fragment.TodayDetails;
//import anulom.executioner5.com3.anulom.fragment.adhocdetails;
//import anulom.executioner5.com3.anulom.fragment.marriagecompleted;
//import anulom.executioner5.com3.anulom.fragment.marriagenew;
//import anulom.executioner5.com3.anulom.fragment.marriageolder;
//import anulom.executioner5.com3.anulom.fragment.marriagetoday;
//import anulom.executioner5.com3.anulom.fragment.salesnew;
//import anulom.executioner5.com3.anulom.fragment.salesolder;
//import anulom.executioner5.com3.anulom.fragment.salestoday;
//import anulom.executioner5.com3.anulom.fragment.witnessdetails;
//
//import static anulom.executioner5.com3.anulom.database.DBManager.TableInfo.ATTENDEES;
//import static anulom.executioner5.com3.anulom.database.DBManager.TableInfo.ATT_STATUS;
//
//import static anulom.executioner5.com3.anulom.database.DBManager.TableInfo.PAYMENT;
//import static anulom.executioner5.com3.anulom.database.DBManager.TableInfo.TABLE_MARRIAGE;
//import static anulom.executioner5.com3.anulom.database.DBManager.TableInfo.TABLE_MARRIAGE_APPOINTMENT;
//import static anulom.executioner5.com3.anulom.database.DBManager.TableInfo.TABLE_MARRIAGE_ATTENDEES;
//import static anulom.executioner5.com3.anulom.database.DBManager.TableInfo.TABLE_NAME_APPOINTMENT;
//import static anulom.executioner5.com3.anulom.database.DBManager.TableInfo.TABLE_SALES;
//import static anulom.executioner5.com3.anulom.database.DBManager.TableInfo.TABLE_SALES_APPOINTMENT;
//import static anulom.executioner5.com3.anulom.database.DBManager.TableInfo.TABLE_SALES_ATTENDEES;
//import static anulom.executioner5.com3.anulom.database.DBManager.TableInfo.TABLE_TASK;
//
//import static com.google.android.gms.internal.zzir.runOnUiThread;
//
//
//public class GetAllDataList extends Service {
//    String startnewdate, startnewtime1, startnewtime2;
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
//    ArrayList<HashMap<String, String>> getCommentDatalist = null;
//    ArrayList<HashMap<String, String>> getAlldataList = null;
//    ArrayList<HashMap<String, String>> getsalesdataList = null;
//    ArrayList<HashMap<String, String>> getmarriagedataList = null;
//    ArrayList<HashMap<String, String>> getuserdataList = null;
//    JSONArray ja = null;
//    JSONArray ja3 = null;
//    JSONArray ja4 = null;
//    JSONArray ja5 = null;
//    JSONArray ja9 = null;
//    JSONArray ja10 = null;
//    JSONArray ja11 = null;
//    JSONArray ja20 = null;
//    JSONArray ja21 = null;
//    JSONArray ja22 = null;
//    JSONArray ja23 = null;
//
//
//    GenericMethods response, response2;
//    String APPOINTMENTURL = "", WITNESSURL = "";
//    String ID1;
//
//
//    DBOperation db;
//
//    private String username2 = "";
//
//
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
//        response2 = new GenericMethods();
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
//            APPOINTMENTURL = GenericMethods.MAIN_URL + "/api/v3/get_exec_data?get_auth_token=DOtUBMhv5pk51tl0D37uBcezq85cXNN7hZQ7&exec_email=" + username2 + "&password=" + password2 + "&version=19.7.5";
//            WITNESSURL = GenericMethods.MAIN_URL1 + "/cmt/api/v1/comment_data/get_task?get_auth_token=DOtUBMhv5pk51tl0D37uBcezq85cXNN7hZQ7&user=" + username2;
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
//        ownername.clear();
//        ownercontact.clear();
//        documentid.clear();
//        documentsalesid.clear();
//        documentmarriageid.clear();
//        appointmentaddress.clear();
//        executionerid.clear();
//        appointmentid.clear();
//        appointmentsalesid.clear();
//        appointmentmarriageid.clear();
//        commentid.clear();
//        Addpayid.clear();
//        userlist.clear();
//    }
//
//    private class GetContacts extends AsyncTask<Void, Void, Void> {
//
//
//        @Override
//        protected Void doInBackground(Void... arg0) {
//
//
//            String jsonStr = "";
//            String jsonStr2 = "";
//
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
//                    String jStrResult = jsonObject.getString("status");
//
//                    String jStrResult1 = jsonObject.getString("version");
//                    GenericMethods.apiversion = jStrResult1;
//
//
//                    if (!GenericMethods.currentversion.equals(GenericMethods.apiversion)) {
//
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//
//                                Intent i=new Intent(GetAllDataList.this, MyDialog.class);
//                                startActivity(i);
//
//
//                            }
//                        });
//
//
//                    }
//
//                    System.out.println("version:" + jStrResult1 + GenericMethods.apiversion);
//
//                    if (jStrResult.equals("OK")) {
//                        db.delLocationDetails(db);
//                        db.delAppointment(db);
//                        db.deluser(db);
//                        db.delDocument(db);
//                        db.delComments(db);
//                        db.delUserRole(db);
//
//                        SQLiteDatabase base1 = db.getWritableDatabase();
//                        base1.delete(ATTENDEES, null, null);
//
//                        SQLiteDatabase baseattendees = db.getWritableDatabase();
//                        baseattendees.delete(TABLE_SALES_ATTENDEES, null, null);
//
//                        SQLiteDatabase baseappoint = db.getWritableDatabase();
//                        baseappoint.delete(TABLE_SALES_APPOINTMENT, null, null);
//
//                        SQLiteDatabase basedocu = db.getWritableDatabase();
//                        basedocu.delete(TABLE_SALES, null, null);
//
//
//                        SQLiteDatabase marriageattendees = db.getWritableDatabase();
//                        marriageattendees.delete(TABLE_MARRIAGE_ATTENDEES, null, null);
//
//                        SQLiteDatabase marriageappoint = db.getWritableDatabase();
//                        marriageappoint.delete(TABLE_MARRIAGE_APPOINTMENT, null, null);
//
//                        SQLiteDatabase marriagedocu = db.getWritableDatabase();
//                        marriagedocu.delete(TABLE_MARRIAGE, null, null);
//
//
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//            getAlldataList = db.getAllData(db);
//
//            for (int i = 0; i < getAlldataList.size(); i++) {
//                documentid.add(getAlldataList.get(i).get("Did"));
//                appointmentid.add(getAlldataList.get(i).get("Aid"));
//                ownername.add(getAlldataList.get(i).get("Oname"));
//                executionerid.add(getAlldataList.get(i).get("Exid"));
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
//            getmarriagedataList = db.getAllmarriageData(db);
//            for (int i = 0; i < getmarriagedataList.size(); i++) {
//
//                documentmarriageid.add(getmarriagedataList.get(i).get("document_id"));
//                appointmentmarriageid.add(getmarriagedataList.get(i).get("appointment_id"));
//
//            }
//
//            getCommentDatalist = db.getCommentlist(db);
//
//            for (int i = 0; i < getCommentDatalist.size(); i++) {
//                commentid.add(getCommentDatalist.get(i).get("Cid"));
//            }
//
//            getuserdataList = db.getdataList(db);
//
//            for (int i = 0; i < getuserdataList.size(); i++) {
//                userlist.add(getuserdataList.get(i).get("UserId"));
//            }
//
//            if (jsonStr != null) {
//
//
//                try {
//                    JSONObject jsonObj = new JSONObject(jsonStr);
//
//
//                    ja = jsonObj.getJSONArray(GenericMethods.TAG_NAME);
//                    ja3 = jsonObj.getJSONArray(GenericMethods.TAG_NAME3);
//                    ja4 = jsonObj.getJSONArray(GenericMethods.TAG_NAME4);
//                    ja20 = jsonObj.getJSONArray(GenericMethods.TAG_NAME14);
//                    ja21 = jsonObj.getJSONArray(GenericMethods.TAG_NAME15);
//                    ja22 = jsonObj.getJSONArray(GenericMethods.TAG_NAME16);
//                    ja23 = jsonObj.getJSONArray(GenericMethods.TAG_NAME19);
//
//
//                    for (int i = 0; i < ja.length(); i++) {
//                        JSONObject c = ja.getJSONObject(i);
//                        String rtoken1 = c.getString(GenericMethods.TAG_RTOKEN);
//                        String rkey1 = c.getString(GenericMethods.TAG_REPORT_KEY);
//                        String uname1 = c.getString(GenericMethods.TAG_UNAME);
//                        String email1 = c.getString(GenericMethods.TAG_UEMAIL);
//                        String contact_number1 = c.getString(GenericMethods.TAG_UCONTACT);
//                        String paddress1 = c.getString(GenericMethods.TAG_PADDRESS);
//                        String oname1 = c.getString(GenericMethods.TAG_ONAME);
//                        String ocontact1 = c.getString(GenericMethods.TAG_OCONTACT);
//                        String oemail1 = c.getString(GenericMethods.TAG_OEMAIL);
//                        String oaddress1 = c.getString(GenericMethods.TAG_OADDRESS);
//                        String tname1 = c.getString(GenericMethods.TAG_TNAME);
//                        String tcontact1 = c.getString(GenericMethods.TAG_TCONTACT);
//                        String temail1 = c.getString(GenericMethods.TAG_TEMAIL);
//                        String taddress1 = c.getString(GenericMethods.TAG_TADDRESS);
//                        String status1 = c.getString(GenericMethods.TAG_STATUS);
//                        String appdate = c.getString(GenericMethods.TAG_APPDATE);
//                        String biocomp = c.getString(GenericMethods.TAG_BIO_COMP);
//                        String appdate1 = c.getString(GenericMethods.TAG_APPDATE1);
//                        String biocomp1 = c.getString(GenericMethods.TAG_BIO_COMP1);
//                        String regfromcomp = c.getString(GenericMethods.TAG_REG_FROM_COMP);
//                        String witness = c.getString(GenericMethods.TAG_WITNESS);
//                        String shipadd = c.getString(GenericMethods.TAG_SHIP_ADDRESS);
//                        String shipdiffadd = c.getString(GenericMethods.TAG_SHIP_DIFF_ADDRESS);
//                        String strlatitude, strlongitude;
//                        if (c.has(GenericMethods.TAG_CUSTOMER_LAT)) {
//                            strlatitude = c.getString(GenericMethods.TAG_CUSTOMER_LAT);
//                        } else strlatitude = "0";
//                        if (c.has(GenericMethods.TAG_CUSTOMER_LONG)) {
//                            strlongitude = c.getString(GenericMethods.TAG_CUSTOMER_LONG);
//                        } else strlongitude = "0";
//                        String docid = c.getString(GenericMethods.TAG_DOCID);
//
//                        String app_status = c.getString(GenericMethods.TAG_APP_STATUS);
//
//                        String appid = c.getString(GenericMethods.TAG_APPID);
//                        String start1 = c.getString(GenericMethods.TAG_START1);
//                        String start2 = c.getString(GenericMethods.TAG_START2);
//                        String appaddress = c.getString(GenericMethods.TAG_APPADDRESS);
//                        String exeid = c.getString(GenericMethods.TAG_EXECUTIONER_ID);
//                        String appfor = c.getString(GenericMethods.TAG_APP_FOR);
//                        String apptype = c.getString(GenericMethods.TAG_APPTYPE);
//                        String post_status = c.getString(GenericMethods.TAG_POST_STATUS);
//
//                        String landmark = c.getString(GenericMethods.TAG_LANDMARK);
//                        String contact_person = c.getString(GenericMethods.TAG_CONTACTPERSON);
//
//                        String distance1 = c.getString(GenericMethods.TAG_CUSTOMER_DISTANCE);
//                        String amount1 = c.getString(GenericMethods.TAG_CUSTOMER_AMOUNT);
//                        String transporttype1 = c.getString(GenericMethods.TAG_CUSTOMER_TRANS_TYPE);
//                        String biostatus = c.getString(GenericMethods.TAG_DOC_BIOMETRIC_STATUS);
//
//
//                        JSONObject jsonatten = ja.getJSONObject(i);
//
//                        JSONArray jsonattendees = jsonatten.getJSONArray(GenericMethods.TAG_NAME13);
//
//                        if (jsonattendees != null) {
//
//
//                            for (int j = 0; j < jsonattendees.length(); j++) {
//                                JSONObject atten = jsonattendees.getJSONObject(j);
//
//                                if (atten != null) {
//                                    String name = atten.getString(GenericMethods.TAG_NAMEATTEND);
//                                    String email = atten.getString(GenericMethods.TAG_EMAILATTEND);
//                                    String contact = atten.getString(GenericMethods.TAG_CONTACTATTEND);
//
//                                      db.Insertbioattendees(db,ID1,name,email,contact,appid,username2);
//
//
//
//                                }
//
//                            }
//
//                        }
//
//
//                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//                        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
//                        SimpleDateFormat output1 = new SimpleDateFormat("HH:mm");
//                        SimpleDateFormat output2 = new SimpleDateFormat("HH:mm");
//
//                        java.util.Date date, time1, time2;
//                        try {
//                            date = sdf.parse(start1);
//                            time1 = sdf.parse(start1);
//                            time2 = sdf.parse(start2);
//                            startnewdate = output.format(date);
//                            startnewtime1 = output1.format(time1);
//                            startnewtime2 = output2.format(time2);
//
//                        } catch (ParseException e) {
//                            // TODO Auto-generated catch block
//                            e.printStackTrace();
//                        }
//
//                        if (documentid.contains(docid)) {
//
//
//                            db.Update(db, docid, rtoken1, rkey1, uname1, email1, contact_number1, paddress1, oname1,
//                                    ocontact1, oemail1, oaddress1, tname1, tcontact1, temail1, taddress1, status1,
//                                    appdate, biocomp, appdate1, biocomp1, regfromcomp, witness, shipadd, shipdiffadd, apptype, biostatus);
//                        } else {
//                            if (!db.DocumentID(db, docid)) {
//
//
//                                db.InsertRecord(db, rtoken1, rkey1, uname1, email1, contact_number1, paddress1, oname1,
//                                        ocontact1, oemail1, oaddress1, tname1, tcontact1, temail1, taddress1, status1,
//                                        username2, docid, appdate, biocomp, appdate1, biocomp1, regfromcomp, witness, shipadd,
//                                        shipdiffadd, "SYNC", apptype, biostatus);
//                            }
//                        }
//
//                        if (appointmentid.contains(appid)) {
//
//                            db.Updateacvr(db, appid, docid, startnewdate, startnewtime1, startnewtime2, appaddress, exeid,
//                                    appfor, distance1, amount1, transporttype1, contact_person, landmark, "", post_status, strlatitude, strlongitude, "false", app_status);
//
//                        } else {
//                            db.InsertRecord2(db, docid, appid, startnewdate, startnewtime1, startnewtime2, appaddress,
//                                    exeid, appfor, distance1, amount1, transporttype1, "SYNC", contact_person, landmark, "", post_status, strlatitude, strlongitude, "false", app_status);
//
//                        }
//
//                        for (int i1 = 0; i1 < db.getnotificationtable(db).size(); i1++) {
//
//
//                            if (db.getnotificationtable(db).get(i1).get("appid").equals(appid)) {
//
//
//                                db.Updateacvr(db, appid, docid, startnewdate, startnewtime1, startnewtime2, appaddress, exeid,
//                                        appfor, distance1, amount1, transporttype1, contact_person, landmark, "", post_status, strlatitude, strlongitude, "true", app_status);
//
//
//                            }
//
//                        }
//                    }
//
//
//                    for (int l = 0; l < ja3.length(); l++) {
//                        JSONObject c = ja3.getJSONObject(l);
//                        String userid = c.getString(GenericMethods.TAG_USERID);
//                        String username = c.getString(GenericMethods.TAG_USERNAME);
//                        String email = c.getString(GenericMethods.TAG_EMAIL);
//                        String role = c.getString(GenericMethods.TAG_ROLE);
//                        String platformid = c.getString(GenericMethods.TAG_PLATFORMID);
//                        String roleid = c.getString(GenericMethods.TAG_ROLEID);
//                        String idu = c.getString(GenericMethods.TAG_IDu);
//
//                        if (userlist.contains(userid)) {
//
//                            db.Updateuser(db, userid, username, email, role, platformid, roleid, idu);
//
//
//                        } else {
//                            if (!db.checkUserId(db, userid)) {
//                                db.InsertRecord5(db, userid, username, email, role, platformid, roleid, idu);
//                            }
//                        }
//                    }
//
//
//                    SQLiteDatabase base = db.getWritableDatabase();
//                    base.delete(PAYMENT, null, null);
//
//                    for (int n = 0; n < ja4.length(); n++) {
//                        JSONObject c = ja4.getJSONObject(n);
//                        String DocumentId = c.getString(GenericMethods.TAG_DOCUMENTID);
//                        String Amt = c.getString(GenericMethods.TAG_PAYAMOUNT);
//                        String ReportKey = c.getString(GenericMethods.TAG_REPORTKEY);
//
//                            db.Insertbiopayment(db,ID1,DocumentId,ReportKey,Amt,username2);
//
//                    }
//
//
//                    SQLiteDatabase basestatus = db.getWritableDatabase();
//                    basestatus.delete(ATT_STATUS, null, null);
//
//                    for (int n = 0; n < ja20.length(); n++) {
//                        JSONObject c = ja20.getJSONObject(n);
//                        String DocumentId = c.getString(GenericMethods.TAG_DOCUMENTID);
//                        String att_status = c.getString(GenericMethods.Tag_att_status);
//
//
//                        SQLiteDatabase sqldb = db.getWritableDatabase();
//                        ContentValues values = new ContentValues();
//                        values.put(DBManager.TableInfo.att_status, att_status);
//                        sqldb.update(TABLE_NAME_APPOINTMENT, values, DBManager.TableInfo.DocumentId + "=?", new String[]{DocumentId});
//                        sqldb.close();
//
//
//                    }
//
//
//                    SQLiteDatabase base3 = db.getWritableDatabase();
//                    base3.delete(DBManager.TableInfo.PARTIES, null, null);
//
//                    ja5 = jsonObj.getJSONArray(GenericMethods.TAG_NAME5);
//
//                    for (int n = 0; n < ja5.length(); n++) {
//
//                        JSONObject c = ja5.getJSONObject(n);
//
//
//                        String Document1 = c.getString(GenericMethods.TAG_DOCUMENT);
//                        String Attendance = c.getString(GenericMethods.TAG_ATTENDANCE);
//                        String name = c.getString(GenericMethods.TAG_NAMENEW);
//                        String email = c.getString(GenericMethods.TAG_EMAILNEW);
//                        String partytype = c.getString(GenericMethods.TAG_PARTYTYPE);
//                        String poa = c.getString(GenericMethods.TAG_POA);
//                        String contact = c.getString(GenericMethods.TAG_CONTACTNUMBER);
//                        String biometric = c.getString(GenericMethods.TAG_BIOMETRIC);
//                        String address = c.getString(GenericMethods.TAG_PARTY_ADDRESS);
//
//                        db.Insertbioparties(db,ID1,Document1,Attendance,name,startnewdate,email,partytype,poa,contact,biometric,address,username2);
//
//                    }
//
//
//                    for (int i = 0; i < ja21.length(); i++) {
//
//
//                        JSONObject c = ja21.getJSONObject(i);
//                        String sales_contactperson = c.getString(GenericMethods.sales_contactperson);
//                        String sales_start_time = c.getString(GenericMethods.sales_starttime);
//                        String sales_end_time = c.getString(GenericMethods.sales_endtime);
//                        String sales_executioner_id = c.getString(GenericMethods.sales_executioner_id);
//                        String sales_app_for = c.getString(GenericMethods.sales_app_for);
//                        String sales_document_id = c.getString(GenericMethods.sales_doc_id);
//                        String sales_created = c.getString(GenericMethods.sales_created_at);
//                        String sales_appointment_id = c.getString(GenericMethods.sales_appointment_id);
//                        String sales_app_ref = c.getString(GenericMethods.sales_app_ref);
//                        String sales_app_city = c.getString(GenericMethods.sales_app_city);
//                        String sales_u_flag = c.getString(GenericMethods.sales_u_flag);
//                        String sales_create_dt = c.getString(GenericMethods.sales_create_dt);
//                        String sales_approved_by = c.getString(GenericMethods.sales_approved_by);
//                        String sales_created_by = c.getString(GenericMethods.sales_createdby);
//                        String sales_logical_del = c.getString(GenericMethods.sales_logical_del);
//                        String sales_poststatus = c.getString(GenericMethods.sales_poststatus);
//                        String sales_apptype = c.getString(GenericMethods.sales_app_type);
//                        String sales_env = c.getString(GenericMethods.sales_env);
//                        String sales_id = c.getString(GenericMethods.sales_id);
//                        String sales_address = c.getString(GenericMethods.sales_address);
//                        String sales_landmark = c.getString(GenericMethods.sales_landmark);
//                        String sales_distance = c.getString(GenericMethods.sales_distance);
//                        String sales_trans_type = c.getString(GenericMethods.sales_transtype);
//                        String sales_amount = c.getString(GenericMethods.sales_amount);
//                        String sales_acvr_id = c.getString(GenericMethods.sales_acvr_id);
//                        String sales_app_status = c.getString(GenericMethods.sales_app_status);
//                        String sales_latitude, sales_longititude;
//                        String sync = "SYNC";
//                        String sales_acvrreport = "SYNC";
//
//
//                        if (c.has(GenericMethods.sales_latitude)) {
//                            sales_latitude = c.getString(GenericMethods.sales_latitude);
//
//                        } else sales_latitude = "0";
//                        if (c.has(GenericMethods.sales_longititude)) {
//                            sales_longititude = c.getString(GenericMethods.sales_longititude);
//                        } else sales_longititude = "0";
//
//
//                        JSONObject jsonsalesatten = ja21.getJSONObject(i);
//
//                        JSONArray jsonsalesattendees = jsonsalesatten.getJSONArray(GenericMethods.TAG_NAME17);
//
//                        if (jsonsalesattendees != null) {
//
//
//                            for (int j = 0; j < jsonsalesattendees.length(); j++) {
//                                JSONObject salesatten = jsonsalesattendees.getJSONObject(j);
//
//                                if (salesatten != null) {
//                                    String sales_name = salesatten.getString(GenericMethods.sales_name);
//                                    String sales_email = salesatten.getString(GenericMethods.sales_mail);
//                                    String sales_contact = salesatten.getString(GenericMethods.sales_contact);
//
//
//                                  db.InsertsalesattendeesRecord(db,ID1,sales_name,sales_email,sales_contact,sales_appointment_id,username2);
//
//
//                                }
//
//                            }
//
//                        }
//
//
//                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//                        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
//                        SimpleDateFormat output1 = new SimpleDateFormat("HH:mm");
//                        SimpleDateFormat output2 = new SimpleDateFormat("HH:mm");
//
//                        java.util.Date date, time1, time2;
//                        try {
//                            date = sdf.parse(sales_start_time);
//                            time1 = sdf.parse(sales_start_time);
//                            time2 = sdf.parse(sales_end_time);
//                            startnewdate = output.format(date);
//                            startnewtime1 = output1.format(time1);
//                            startnewtime2 = output2.format(time2);
//
//                        } catch (ParseException e) {
//                            // TODO Auto-generated catch block
//                            e.printStackTrace();
//                        }
//
//
//
//
//                        if (appointmentsalesid.contains(sales_appointment_id)) {
//
//                            db.updatesalesappointment(db, sales_appointment_id,  sales_contactperson,  startnewtime1,
//                                     startnewtime2,  startnewdate,  sales_executioner_id,  sales_app_for,  sales_document_id,sales_created,  sales_app_ref,  sales_app_city,  sales_u_flag,  sales_create_dt,  sales_approved_by,
//                                     sales_created_by,  sales_latitude,  sales_longititude,  sales_logical_del,  sales_poststatus,  sales_apptype,  sales_id,  username2,  sales_address,  sales_landmark,  sales_acvr_id,  sales_distance,  sales_trans_type,  sales_amount,  sync,  sales_acvrreport, "false",  sales_app_status);
//
//                        } else {
//                            db.InsertsalesappointmentRecord(db, sales_appointment_id,  sales_contactperson,  startnewtime1,
//                                    startnewtime2,  startnewdate,  sales_executioner_id,  sales_app_for,  sales_document_id,sales_created,  sales_app_ref,  sales_app_city,  sales_u_flag,  sales_create_dt,  sales_approved_by,
//                                    sales_created_by,  sales_latitude,  sales_longititude,  sales_logical_del,  sales_poststatus,  sales_apptype,  sales_id,  username2,  sales_address,  sales_landmark,  sales_acvr_id,  sales_distance,  sales_trans_type,  sales_amount,  sync,  sales_acvrreport, "false",  sales_app_status);
//
//                        }
//
//
//
//                        if (documentsalesid.contains(sales_document_id)) {
//                            db.Updatesalesdocuments( db, sales_env, sales_document_id, username2,
//                                    sync,  sales_acvrreport);
//
//                        } else if (!db.DocumentsalesID(db, sales_document_id)) {
//                            db.Insertsalesdocuments( db, sales_env, sales_document_id,  username2,
//                                    sync,  sales_acvrreport);
//                        }
//
//                        for (int i1 = 0; i1 < db.getnotificationtable(db).size(); i1++) {
//
//                            if (db.getnotificationtable(db).get(i1).get("appid").equals(sales_appointment_id)) {
//
//
//                                SQLiteDatabase sqldb2 = db.getWritableDatabase();
//                                ContentValues values2 = new ContentValues();
//                                values2.put(DBManager.TableInfo.notification, "true");
//                                int count4 = sqldb2.update(DBManager.TableInfo.TABLE_SALES_APPOINTMENT, values2, DBManager.TableInfo.db_sales_appointment_id + "=?", new String[]{sales_appointment_id});
//                                sqldb2.close();
//
//
//                            }
//
//                        }
//
//                    }
//
//                    for (int i = 0; i < ja22.length(); i++) {
//
//
//                        JSONObject c = ja22.getJSONObject(i);
//                        String marriage_documentid = c.getString(GenericMethods.marriage_doc_id);
//                        String marriage_env = c.getString(GenericMethods.marriage_env);
//                        String marriage_contactperson = c.getString(GenericMethods.marriage_contact_person);
//
//                        String marriage_starttime = c.getString(GenericMethods.marriage_starttime);
//                        String marriage_endtime = c.getString(GenericMethods.marriage_endtime);
//                        String marriage_address = c.getString(GenericMethods.marriage_address);
//                        String marriage_landmark = c.getString(GenericMethods.marriage_landmark);
//                        String marriage_appfor = c.getString(GenericMethods.marriage_app_for);
//                        String marriage_execuid = c.getString(GenericMethods.marriage_executionerid);
//                        String marriage_appointid = c.getString(GenericMethods.marriage_appointment_id);
//                        String marriage_dist = c.getString(GenericMethods.marriage_distance);
//                        String marriage_trans_type = c.getString(GenericMethods.marriage_transtype);
//                        String marriage_acvr_amount = c.getString(GenericMethods.marriage_acvr_amount);
//                        String marriage_acvr_id = c.getString(GenericMethods.marriage_acvr_id);
//                        String marriage_logical_del = c.getString(GenericMethods.marriage_logical_del);
//                        String marriage_poststatus = c.getString(GenericMethods.marriage_poststatus);
//                        String marriage_franchiseid = c.getString(GenericMethods.marriage_franchise_id);
//                        String marriage_coid = c.getString(GenericMethods.marriage_co_id);
//                        String marriage_payverify = c.getString(GenericMethods.marriage_pay_verify);
//                      //  String marriage_paypending = c.getString(GenericMethods.marriage_pay_pending);
//                        String marriage_ex_id = c.getString(GenericMethods.marriage_ex_id);
//                        String marriage_payflag = c.getString(GenericMethods.marriage_pay_flag);
//                        String marriage_appflag = c.getString(GenericMethods.marriage_app_flag);
//                        String marriage_createdat = c.getString(GenericMethods.marriage_created_at);
//                        String marriage_statusid = c.getString(GenericMethods.marriage_status_id);
//                        String marriage_status = c.getString(GenericMethods.marriage_status);
//                        String marriage_id = c.getString(GenericMethods.marriage_id);
//                        String marriage_app_status = c.getString(GenericMethods.marriage_app_status);
//                        String marriage_app_type = c.getString(GenericMethods.marriage_app_type);
//                        String marriage_latitude;
//                        String marriage_longitiude;
//                        String sync = "SYNC";
//                        String acvrSync = "SYNC";
//                        String marriage_husbandname = c.getString(GenericMethods.marriage_husbandname);
//                        String marriage_wifename = c.getString(GenericMethods.marriage_wifename);
//                        String marriage_husbandcontact = c.getString(GenericMethods.marriage_husbandcontact);
//                        String marriage_wifecontact = c.getString(GenericMethods.marriage_wifecontact);
//                        String marriage_husbandbiometric = c.getString(GenericMethods.marriage_husbandbiometric);
//                        String marriage_wifebiometric = c.getString(GenericMethods.marriage_wifebiometric);
//                        String marriage_tokenno = c.getString(GenericMethods.marriage_tokenno);
//
//
//                        if (c.has(GenericMethods.marriage_latitude)) {
//                            marriage_latitude = c.getString(GenericMethods.marriage_latitude);
//
//                        } else marriage_latitude = "0";
//                        if (c.has(GenericMethods.marriage_longititude)) {
//                            marriage_longitiude = c.getString(GenericMethods.marriage_longititude);
//                        } else marriage_longitiude = "0";
//
//
//                        JSONObject jsonmarriagesatten = ja22.getJSONObject(i);
//
//                        JSONArray jsonmarriageattendees = jsonmarriagesatten.getJSONArray(GenericMethods.TAG_NAME18);
//
//                        if (jsonmarriageattendees != null) {
//
//
//                            for (int j = 0; j < jsonmarriageattendees.length(); j++) {
//                                JSONObject marriageatten = jsonmarriageattendees.getJSONObject(j);
//
//                                if (marriageatten != null) {
//                                    String marriage_name = marriageatten.getString(GenericMethods.marriage_name);
//                                    String marriage_email = marriageatten.getString(GenericMethods.marriage_mail);
//                                    String marriage_contact = marriageatten.getString(GenericMethods.marriage_contact);
//
//
//                                   db.InsertmarriageattendeesRecord(db,ID1,marriage_name,marriage_email,marriage_contact,marriage_appointid,username2);
//
//
//                                }
//
//                            }
//
//                        }
//
//
//                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//                        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
//                        SimpleDateFormat output1 = new SimpleDateFormat("HH:mm");
//                        SimpleDateFormat output2 = new SimpleDateFormat("HH:mm");
//
//                        java.util.Date date, time1, time2;
//                        try {
//                            date = sdf.parse(marriage_starttime);
//                            time1 = sdf.parse(marriage_starttime);
//                            time2 = sdf.parse(marriage_endtime);
//                            startnewdate = output.format(date);
//                            startnewtime1 = output1.format(time1);
//                            startnewtime2 = output2.format(time2);
//
//                        } catch (ParseException e) {
//                            // TODO Auto-generated catch block
//                            e.printStackTrace();
//                        }
//
//
//
//
//                        if (appointmentmarriageid.contains(marriage_appointid)) {
//
//                            db.updatemarriageappointment( db,  marriage_appointid,  marriage_documentid,  marriage_contactperson,
//                                     startnewtime1,  startnewtime2,  startnewdate,  marriage_address,  marriage_landmark
//                                    ,  marriage_appfor,  marriage_execuid,  marriage_latitude,  marriage_longitiude,  marriage_dist,  marriage_trans_type,
//                                     marriage_acvr_amount,  marriage_acvr_id,  marriage_logical_del,  marriage_poststatus
//                                    ,  marriage_franchiseid,  marriage_coid,  marriage_statusid,  marriage_ex_id,  marriage_payverify,  marriage_createdat,  marriage_appflag,  marriage_payflag,  marriage_app_type,  marriage_id,  sync,  acvrSync,  marriage_app_status, username2, "false");
//
//                        } else {
//                            db.InsertmarriageappointmentRecord( db,  marriage_appointid,  marriage_documentid,  marriage_contactperson,
//                                    startnewtime1,  startnewtime2,  startnewdate,  marriage_address,  marriage_landmark
//                                    ,  marriage_appfor,  marriage_execuid,  marriage_latitude,  marriage_longitiude,  marriage_dist,  marriage_trans_type,
//                                    marriage_acvr_amount,  marriage_acvr_id,  marriage_logical_del,  marriage_poststatus
//                                    ,  marriage_franchiseid,  marriage_coid,  marriage_statusid,  marriage_ex_id,  marriage_payverify,  marriage_createdat,  marriage_appflag,  marriage_payflag,  marriage_app_type,  marriage_id,  sync,  acvrSync,  marriage_app_status, username2, "false");
//
//                        }
//
//
//                        if (documentmarriageid.contains(marriage_documentid)) {
//                            db.Updatemarriagedocuments( db,  marriage_documentid,  marriage_status,  marriage_env,
//                                    sync,  acvrSync, username2, marriage_husbandname, marriage_husbandbiometric, marriage_husbandcontact
//                                    , marriage_wifebiometric, marriage_wifename, marriage_wifecontact, marriage_tokenno);
//                        } else if (!db.DocumentmarriageID(db, marriage_documentid)) {
//
//                            db.Insertmarriagedocuments( db,  marriage_documentid,  marriage_status,  marriage_env,
//                                    sync,  acvrSync, username2, marriage_husbandname, marriage_husbandbiometric, marriage_husbandcontact
//                                    , marriage_wifebiometric, marriage_wifename, marriage_wifecontact, marriage_tokenno);
//
//                        }
//                        for (int i1 = 0; i1 < db.getnotificationtable(db).size(); i1++) {
//
//                            if (db.getnotificationtable(db).get(i1).get("appid").equals(marriage_appointid)) {
//
//
//                                SQLiteDatabase sqldb2 = db.getWritableDatabase();
//                                ContentValues values2 = new ContentValues();
//                                values2.put(DBManager.TableInfo.notification, "true");
//                                int count4 = sqldb2.update(DBManager.TableInfo.TABLE_MARRIAGE_APPOINTMENT, values2, DBManager.TableInfo.db_marriage_appointment_id + "=?", new String[]{marriage_appointid});
//                                sqldb2.close();
//
//
//                            }
//
//                        }
//
//
//                    }
//
//
//                   SQLiteDatabase base1 = db.getWritableDatabase();
//                   base1.delete(DBManager.TableInfo.TABLE_PENDINGWITNESS, null, null);
//                    for (int n = 0; n < ja23.length(); n++) {
//
//                        JSONObject c = ja23.getJSONObject(n);
//
//
//
//
//                        String docid = c.getString(GenericMethods.PW_doc_id);
//                        String env = c.getString(GenericMethods.PW_env);
//                        String tokenno = c.getString(GenericMethods.PW_token_no);
//                        String party_type = c.getString(GenericMethods.PW_partytype);
//                        String att_id = c.getString(GenericMethods.PW_attid);
//                        String witness_name = c.getString(GenericMethods.PW_witnessname);
//                        String witness_biometric = c.getString(GenericMethods.PW_biometric);
//                        String witness_email = c.getString(GenericMethods.PW_email);
//                        String witness_contact=c.getString(GenericMethods.PW_contact);
//
//                        String ocontact = c.getString(GenericMethods.PW_ocontact);
//                        String oemail = c.getString(GenericMethods.PW_oemail);
//                        String oname = c.getString(GenericMethods.PW_oname);
//                        String oaddress = c.getString(GenericMethods.PW_oaddress);
//                        String tcontact = c.getString(GenericMethods.PW_tcontact);
//                        String temail = c.getString(GenericMethods.PW_temail);
//                        String tname = c.getString(GenericMethods.PW_tname);
//                        String taddress=c.getString(GenericMethods.PW_taddress);
//                        String Paddress = c.getString(GenericMethods.PW_Paddress);
//                        String from_dt = c.getString(GenericMethods.PW_from_dt);
//                        String to_dt = c.getString(GenericMethods.PW_to_dt);
//                        String rent = c.getString(GenericMethods.PW_rent);
//                        String deposit = c.getString(GenericMethods.PW_deposit);
//                        String refunddeposit = c.getString(GenericMethods.PW_refunddeposit);
//                        String city_name = c.getString(GenericMethods.PW_city_name);
//                        String status_id = c.getString(GenericMethods.PW_status_id);
//                        String status=c.getString(GenericMethods.PW_status);
//                        String id = c.getString(GenericMethods.PW_id);
//                        String agreement_cancle=c.getString(GenericMethods.PW_agreement_cancle);
//
//
//                        db.Insertpendingwitness( db, ID1,  docid,  env,  tokenno,
//                                 party_type,  att_id,  witness_name,  witness_biometric,  witness_email,  witness_contact
//                                ,  ocontact,  oemail,  oname,  oaddress, tcontact, temail, tname, taddress, Paddress,
//                                 from_dt, to_dt, rent, deposit, refunddeposit, city_name, status_id, status, id,
//                                 agreement_cancle, username2 );
//
//
//
//                    }
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            } else {
//                Log.e("ServiceHandler", "Couldn't get any data from the url");
//            }
//
//
//            try {
//                jsonStr2 = response2.doGetRequest(WITNESSURL);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
////////adhoc & witness
//            Log.d("Response2: ", "> " + jsonStr2);
//
//            SQLiteDatabase base1 = db.getWritableDatabase();
//            base1.delete(TABLE_TASK, null, null);
//
//            if (jsonStr2 != null) {
//
//                try {
//
//                    JSONObject jsonObj1 = new JSONObject(jsonStr2);
//                    ja9 = jsonObj1.getJSONArray(GenericMethods.TAG_NAME9);
//                    for (int n = 0; n < ja9.length(); n++) {
//                        JSONObject c = ja9.getJSONObject(n);
//                        String witnessid = c.getString(GenericMethods.TAG_ID);
//                        String comment = c.getString(GenericMethods.TAG_COMMENT);
//                        String isdone = c.getString(GenericMethods.TAG_IS_DONE);
//                        String doc = c.getString(GenericMethods.TAG_DOC);
//                        String create = c.getString(GenericMethods.TAG_CREATE);
//                        String remainder = c.getString(GenericMethods.TAG_REMAINDER);
//                        String assign = c.getString(GenericMethods.TAG_ASSIGN);
//                        String taskid = c.getString(GenericMethods.TAG_TASK_ID);
//
//
////////db for adhoc
//                        db.Inssertadhoctask( db,  ID1,  witnessid,  comment,
//                                 isdone,  doc,  create,  "adhoc", "false",  "Not Completed",  remainder,  assign,  taskid, username2);
//
//
//                        for (int i1 = 0; i1 < db.getadhocnotificationtable(db).size(); i1++) {
//
//                            if (db.getadhocnotificationtable(db).get(i1).get("id1").equals(witnessid)) {
//
//
//                                SQLiteDatabase sqldb2 = db.getWritableDatabase();
//                                ContentValues values2 = new ContentValues();
//                                values2.put(DBManager.TableInfo.notification, "true");
//                                int count4 = sqldb2.update(DBManager.TableInfo.TABLE_TASK, values2, DBManager.TableInfo.WITID + "=?", new String[]{witnessid});
//
//                                sqldb2.close();
//
//
//                            }
//
//                        }
//
//                    }
////
////                        //witness
//
//                    ja10 = jsonObj1.getJSONArray(GenericMethods.TAG_NAME10);
//                    for (int n = 0; n < ja10.length(); n++) {
//                        JSONObject c = ja10.getJSONObject(n);
//                        String witnessid = c.getString(GenericMethods.TAG_ID);
//                        String comment = c.getString(GenericMethods.TAG_COMMENT);
//                        String isdone = c.getString(GenericMethods.TAG_IS_DONE);
//                        String doc = c.getString(GenericMethods.TAG_DOC);
//                        String create = c.getString(GenericMethods.TAG_CREATE);
//                        String remainder = c.getString(GenericMethods.TAG_REMAINDER);
//                        String assign = c.getString(GenericMethods.TAG_ASSIGN);
//                        String taskid = c.getString(GenericMethods.TAG_TASK_ID);
//
//
//                        db.InsertWitnesstask( db,  ID1,  witnessid,  comment,
//                                isdone,  doc,  create,  "Witness", "false",  "Not Completed",  remainder,  assign,  taskid, username2);
//
//                        for (int i1 = 0; i1 < db.getwitnessnotificationtable(db).size(); i1++) {
//
//                            if (db.getwitnessnotificationtable(db).get(i1).get("id1").equals(witnessid)) {
//
//
//                                SQLiteDatabase sqldb2 = db.getWritableDatabase();
//                                ContentValues values2 = new ContentValues();
//                                values2.put(DBManager.TableInfo.notification, "true");
//                                int count4 = sqldb2.update(DBManager.TableInfo.TABLE_TASK, values2, DBManager.TableInfo.WITID + "=?", new String[]{witnessid});
//
//                                sqldb2.close();
//
//
//                            }
//
//                        }
//                    }
//////complaint
//
//
//                    ja11 = jsonObj1.getJSONArray(GenericMethods.TAG_NAME11);
//                    for (int n = 0; n < ja11.length(); n++) {
//                        JSONObject c = ja11.getJSONObject(n);
//                        String witnessid = c.getString(GenericMethods.TAG_ID);
//                        String comment = c.getString(GenericMethods.TAG_COMMENT);
//                        String isdone = c.getString(GenericMethods.TAG_IS_DONE);
//                        String doc = c.getString(GenericMethods.TAG_DOC);
//                        String create = c.getString(GenericMethods.TAG_CREATE);
//                        String remainder = c.getString(GenericMethods.TAG_REMAINDER);
//                        String assign = c.getString(GenericMethods.TAG_ASSIGN);
//                        String taskid = c.getString(GenericMethods.TAG_TASK_ID);
//
//                        db.Insertcomplainttask( db,  ID1,  witnessid,  comment,
//                                isdone,  doc,  create,  "Complaint", "false",  "Not Completed",  remainder,  assign,  taskid, username2);
//
//
//                        for (int i1 = 0; i1 < db.getcomplaintnotificationtable(db).size(); i1++) {
//
//                            if (db.getcomplaintnotificationtable(db).get(i1).get("id1").equals(witnessid)) {
//
//
//                                SQLiteDatabase sqldb2 = db.getWritableDatabase();
//                                ContentValues values2 = new ContentValues();
//                                values2.put(DBManager.TableInfo.notification, "true");
//                                int count4 = sqldb2.update(DBManager.TableInfo.TABLE_TASK, values2, DBManager.TableInfo.WITID + "=?", new String[]{witnessid});
//
//                                sqldb2.close();
//
//
//                            }
//
//                        }
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//            } else {
//                Log.e("ServiceHandler", "Couldn't get any data from the url");
//            }
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
//            if (TodayDetails.thisToday != null) {
//                TodayDetails.thisToday.reFreshReload();
//            }
//            if (OlderDetails.thisOlderDetails != null) {
//                OlderDetails.thisOlderDetails.reFreshReload();
//            }
//            if (NewDetails.thisnewDetails != null) {
//                NewDetails.thisnewDetails.reFreshReload();
//            }
//            if (CompletedDetails.thiscompleteDetails != null) {
//                CompletedDetails.thiscompleteDetails.reFreshReload();
//            }
//            if (salestoday.thisToday != null) {
//                salestoday.thisToday.reFreshReload();
//            }
//            if (salesolder.thisToday != null) {
//                salesolder.thisToday.reFreshReload();
//            }
//            if (salesnew.thisToday != null) {
//                salesnew.thisToday.reFreshReload();
//            }
////            if (salescompleted.thisToday != null) {
////                salescompleted.thisToday.reFreshReload();
////            }
//            if (marriagetoday.thisToday != null) {
//                marriagetoday.thisToday.reFreshReload();
//            }
//            if (marriageolder.thisToday != null) {
//                marriageolder.thisToday.reFreshReload();
//            }
//            if (marriagenew.thisToday != null) {
//                marriagenew.thisToday.reFreshReload();
//            }
//            if (marriagecompleted.thisToday != null) {
//                marriagecompleted.thisToday.reFreshReload();
//            }
//            if (adhocdetails.thisadhoc != null) {
//                adhocdetails.thisadhoc.reFreshReload();
//            }
//            if (witnessdetails.thiswitness != null) {
//                witnessdetails.thiswitness.reFreshReload();
//            }
//            if (Complaintdetails.thisComplaint != null) {
//                Complaintdetails.thisComplaint.reFreshReload();
//            }
//
//
//            Intent inte = new Intent(getApplicationContext(), GetCommentService.class);
//            startService(inte);
//
//
//        }
//    }
//
//
//}
