package anulom.executioner.com.anulom.services;

import android.app.Service;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;

import android.database.Cursor;
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
import java.util.Calendar;

import anulom.executioner.com.anulom.GenericMethods;
import anulom.executioner.com.anulom.Login;

import anulom.executioner.com.anulom.database.DBManager;
import anulom.executioner.com.anulom.database.DBOperation;




public class getexecutionerlist extends Service {


    public String umail = Login.umailid;
    GenericMethods responseslot;
    String startnewdate, startnewtime1, startnewtime2,currentdate;
    private String EXECUTIONERURL = "";
    String ID1,valuefrommanager1;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public int onStartCommand(Intent intent, int flags, int startId) {


        responseslot = new GenericMethods();
        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        currentdate = df2.format(c1.getTime());
        System.out.println(currentdate);
        String email = intent.getStringExtra("email");
        valuefrommanager1=intent.getStringExtra("value");
        String password = intent.getStringExtra("password");
        EXECUTIONERURL = GenericMethods.MAIN_URL + "/api/v3/get_exec_data?get_auth_token=DOtUBMhv5pk51tl0D37uBcezq85cXNN7hZQ7&exec_email=" + email + "&password=" + password + "&version=17.4.2";

        new GetCommentTask().execute();

        return START_STICKY;


    }



    private class GetCommentTask extends AsyncTask<Void, Void, Void> {
        String jsonStr3 = "";
        private JSONArray ja12 = null;

        @Override
        protected Void doInBackground(Void... params) {
            DBOperation db = new DBOperation(getexecutionerlist.this);
            SharedPreferences usermail = PreferenceManager.getDefaultSharedPreferences(getexecutionerlist.this);
            String username2 = usermail.getString("username", "");
            String password2 = usermail.getString("password", "");


            try {
                jsonStr3 = responseslot.doGetRequest(EXECUTIONERURL);

            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("Response from slot: ", "> " + EXECUTIONERURL);
//
            SQLiteDatabase base = db.getWritableDatabase();
            base.delete(DBManager.TableInfo.TABLE_MANAGER, null, null);
            SQLiteDatabase base1 = db.getWritableDatabase();
            base1.delete(DBManager.TableInfo.TABLE_MANAGER_APPOINTMENT, null, null);

            if (jsonStr3 != null)

                try {


                    JSONObject jsonObj1 = new JSONObject(jsonStr3);
                    ja12 = jsonObj1.getJSONArray(GenericMethods.TAG_NAME);

                    for (int n = 0; n < ja12.length(); n++) {

                        JSONObject c = ja12.getJSONObject(n);
                        String rtoken1 = c.getString(GenericMethods.TAG_RTOKEN);
                        String rkey1 = c.getString(GenericMethods.TAG_REPORT_KEY);
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
                            System.out.println(startnewdate);
                            startnewtime1 = output1.format(time1);
                            startnewtime2 = output2.format(time2);

                        } catch (ParseException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        System.out.println("email:" + exeid + rkey1);

                        SQLiteDatabase db3 = db.getWritableDatabase();
                        ContentValues values3 = new ContentValues();

                        values3.put(DBManager.TableInfo.KEYID, ID1);
                        values3.put(DBManager.TableInfo.ReportKey, rkey1);
                        values3.put(DBManager.TableInfo.ReportToken, rtoken1);
                        values3.put(DBManager.TableInfo.Umail, email1);
                        values3.put(DBManager.TableInfo.Uname, uname1);
                        values3.put(DBManager.TableInfo.Ucontact, contact_number1);
                        values3.put(DBManager.TableInfo.PropertyAddress, paddress1);
                        values3.put(DBManager.TableInfo.OwnerName, oname1);
                        values3.put(DBManager.TableInfo.OwnerContact, ocontact1);
                        values3.put(DBManager.TableInfo.OwnerEmail, oemail1);
                        values3.put(DBManager.TableInfo.OwnerAddress, oaddress1);
                        values3.put(DBManager.TableInfo.TenantName, tname1);
                        values3.put(DBManager.TableInfo.TenantContact, tcontact1);
                        values3.put(DBManager.TableInfo.TenantEmail, temail1);
                        values3.put(DBManager.TableInfo.TenantAddress, taddress1);
                        values3.put(DBManager.TableInfo.Status, status1);
                        values3.put(DBManager.TableInfo.UserEmail, username2);
                        values3.put(DBManager.TableInfo.BiometricComp, biocomp);
                        values3.put(DBManager.TableInfo.Appointment_Date, appdate);
                        values3.put(DBManager.TableInfo.BiometricComp1, biocomp1);
                        values3.put(DBManager.TableInfo.Appointment_Date1, appdate1);
                        values3.put(DBManager.TableInfo.Witness, witness);
                        values3.put(DBManager.TableInfo.Ship_Address, shipadd);
                        values3.put(DBManager.TableInfo.Reg_From_Comp,regfromcomp);
                        values3.put(DBManager.TableInfo.SYNCSTATUS,"SYNC");
                        values3.put(DBManager.TableInfo.Ship_Diff_Address, shipdiffadd);
                        values3.put(DBManager.TableInfo.APPTYPE, apptype);
                        values3.put(DBManager.TableInfo.Doc_bio_status, biostatus);
                        values3.put(DBManager.TableInfo.AppointmentId, appid);
                        values3.put(DBManager.TableInfo.DocumentId, docid);


                            String condition3 = DBManager.TableInfo.DocumentId + " =?";
                            Cursor cursor3 = db3.query(DBManager.TableInfo.TABLE_MANAGER, null, condition3, new String[]{docid}, null, null, null);
                            long status3 = db3.insert(DBManager.TableInfo.TABLE_MANAGER, null, values3);
                            System.out.println("insert doc" + status3 + docid);
                            cursor3.close();
                            db3.close();


                        SQLiteDatabase db4 = db.getWritableDatabase();
                        ContentValues values4 = new ContentValues();
                        values4.put(DBManager.TableInfo.KEYID,ID1);
                        values4.put(DBManager.TableInfo.StartDate,startnewdate);
                        values4.put(DBManager.TableInfo.StartTime1, startnewtime1);
                        values4.put(DBManager.TableInfo.StartTime2, startnewtime2);
                        values4.put(DBManager.TableInfo.DocumentId, docid);
                        values4.put(DBManager.TableInfo.AppointmentAddress, appaddress);
                        values4.put(DBManager.TableInfo.AppointmentId, appid);
                        values4.put(DBManager.TableInfo.contactperson, contact_person);
                        values4.put(DBManager.TableInfo.landmark, landmark);
                        values4.put(DBManager.TableInfo.Executioner_id, exeid);
                        values4.put(DBManager.TableInfo.DISTANCE, distance1);
                        values4.put(DBManager.TableInfo.AMOUNT, amount1);
                        values4.put(DBManager.TableInfo.TRANSPORTTYPE, transporttype1);
                        values4.put(DBManager.TableInfo.SYNCSTATUSREPORT,"SYNC");
                        values4.put(DBManager.TableInfo.att_status,"" );
                        values4.put(DBManager.TableInfo.post_status, post_status);
                        values4.put(DBManager.TableInfo.task_name, taddress1);
                        values4.put(DBManager.TableInfo.LATITUDE, strlatitude);
                        values4.put(DBManager.TableInfo.LONGITUDE, strlongitude);
                        values4.put(DBManager.TableInfo.notification,"false" );
                        values4.put(DBManager.TableInfo.AppFor, appfor);



                            String condition4 = DBManager.TableInfo.AppointmentId + " =?";
                            Cursor cursor4 = db4.query(DBManager.TableInfo.TABLE_MANAGER_APPOINTMENT, null, condition4, new String[]{appid}, null, null, null);
                            long status4 = db4.insert(DBManager.TableInfo.TABLE_MANAGER_APPOINTMENT, null, values4);
                            System.out.println("insert doc" + status4 + appid);
                            cursor4.close();
                            db4.close();


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            return null;
        }
    }


}
