
package anulom.executioner.com.anulom;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import anulom.executioner.com.anulom.database.DBOperation;


public class GenericMethods {

    OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=UTF8");
    public static final String AsyncStatus = "ASYNC";
    DBOperation db;
    public static ArrayList<HashMap<String, String>> getAllTodaylist1 = new ArrayList<>();
    public static ArrayList<HashMap<String, String>> getAllOlderlist1 = new ArrayList<>();
    public static ArrayList<HashMap<String, String>> getAllnewlist1 = new ArrayList<>();
    public static ArrayList<HashMap<String, String>> getAllcompletedlist1 = new ArrayList<>();
    public static ArrayList<HashMap<String, String>> getAllsalestodaylist1 = new ArrayList<>();
    public static ArrayList<HashMap<String, String>> getAllsalesolderlist1 = new ArrayList<>();
    public static ArrayList<HashMap<String, String>> getAllsalesnewlist1 = new ArrayList<>();
    public static ArrayList<HashMap<String, String>> getAllsalescompletedlist1 = new ArrayList<>();
    public static ArrayList<HashMap<String, String>> getAllmarriagetodaylist1 = new ArrayList<>();
    public static ArrayList<HashMap<String, String>> getAllmarriageolderlist1 = new ArrayList<>();
    public static ArrayList<HashMap<String, String>> getAllmarriagenewlist1 = new ArrayList<>();
    public static ArrayList<HashMap<String, String>> getAllmarriagecompletedlist1 = new ArrayList<>();
    public static ArrayList<HashMap<String, String>> getadhocdetails1 = new ArrayList<>();
    public static ArrayList<HashMap<String, String>> getwitnessdetails1 = new ArrayList<>();
    public static ArrayList<HashMap<String, String>> getcomplaintdetails1 = new ArrayList<>();

    public static final String MAIN_URL = "https://www.oneplatform.anulom.com";
    public static final String MAIN_URL1 = "https://www.digitaldabbawala.com";
    public static final String MAIN_URL2 = "https://www.anulom.com";
    public static final String URL = MAIN_URL + "/api/v1/exec_status/get_location";
    public static final String LOGINURL = MAIN_URL + "/api/v2/biometric_data/login";


    public static final String TAG_NAME = "document";
    public static final String TAG_NAME1 = "comments";

    public static final String TAG_NAME3 = "users";
    public static final String TAG_NAME4 = "rem_payment";
    public static final String TAG_NAME5 = "parties";

    public static final String TAG_NAME9 = "adhoc_task";
    public static final String TAG_NAME10 = "witness_task";
    public static final String TAG_NAME11 = "complaint_task";
    public static final String TAG_NAME12 = "time_slot";
    public static final String TAG_NAME13 = "attendees";
    public static final String TAG_NAME14 = "bio_status";
    public static final String TAG_NAME15 = "sales_appointment";
    public static final String TAG_NAME16 = "marraige_appointment";
    public static final String TAG_NAME17 = "attendees";
    public static final String TAG_NAME18 = "attendees";
    public static final String TAG_NAME19="pending_witness";
    public static final String TAG_NAME20="penalty";

    public static String email = "email";
    public static String password = "password";
    public static String connection = "conn";
    //size varaibles
    public static Integer tabpos = 0;
    public static Integer todaybiometric;
    public static Integer olderbiometric;
    public static Integer newbiometric;
    public static Integer completedbionmetric;
    public static String pendingworks="false";
    public static String nextactivity;
    public static String pendingdisplay;
    public static String manageremail;
    public static String offline;
    public static String listofcommment;
    public static Integer scrollposition;
    public static String paymentlist;
    public static String currentversion="19.7.5";
    public static String apiversion="19.7.5";
    public static String splashscreen;
    public static String toastmsg1;
    //search variables
    public static String value = "false";

    public static String rkeyfn;

    public static String olderrr = "false";
    public static String todayyy = "false";
    public static String newwww = "false";
    public static String completeddd = "false";
    public static String salesolderrr = "false";
    public static String salestodayyy = "false";
    public static String salesnewwww = "false";
    public static String salescompleteddd = "false";
    public static String globaltabposition = "false";
    public static String marriageolderrr = "false";
    public static String marriagetodayyy = "false";
    public static String marriagenewwww = "false";
    public static String marriagecompleteddd = "false";
    public static String taskadhoc = "false";
    public static String taskcomplaint = "false";
    public static String taskwitness = "false";

    public static String pd_value = "null";
    public static String toastmsg = "false";

    //for work completed and last updated
    public static final String TABLE_CHECK_JOB = "check_jobb";
    public static final String KEY_ID = "id";
    public static final String KEY_LOGIN_USER = "login_user";
    public static final String R_KEY = "r_key";
    public static final String JOB = "job";

    public static final String TABLE_LAST_UPDATE = "last_update";
    public static final String LAST_UPDATED = "last_updated";

    public static final String TAG_REPORT_KEY = "env";
    public static final String TAG_RTOKEN = "token_no";

    public static final String TAG_UNAME = "uname";
    public static final String TAG_UEMAIL = "email";
    public static final String TAG_UCONTACT = "contact_number";

    public static final String TAG_ONAME = "oname";
    public static final String TAG_OCONTACT = "ocontact";
    public static final String TAG_OEMAIL = "oemail";
    public static final String TAG_OADDRESS = "oaddress";

    public static final String TAG_TNAME = "tname";
    public static final String TAG_TCONTACT = "tcontact";
    public static final String TAG_TEMAIL = "temail";
    public static final String TAG_TADDRESS = "taddress";

    public static final String TAG_PADDRESS = "Paddress";
    public static final String TAG_POST_STATUS = "post_status";

    public static final String TAG_CUSTOMER_LAT = "latitude";
    public static final String TAG_CUSTOMER_LONG = "longitude";

    public static final String TAG_CUSTOMER_DISTANCE = "distance";
    public static final String TAG_CUSTOMER_AMOUNT = "acvr_amount";
    public static final String TAG_CUSTOMER_TRANS_TYPE = "trans_type";
    public static final String TAG_APP_STATUS="app_status";

    public static final String TAG_STATUS = "status";

    public static final String TAG_APPDATE = "appointment_date";
    public static final String TAG_BIO_COMP = "biometric_comp";

    public static final String TAG_APPDATE1 = "appointment_date1";
    public static final String TAG_BIO_COMP1 = "biometric_comp1";

    public static final String TAG_REG_FROM_COMP = "reg_frm_comp";
    public static final String TAG_WITNESS = "witness";
    public static final String TAG_SHIP_ADDRESS = "ship_address";
    public static final String TAG_SHIP_DIFF_ADDRESS = "ship_diffadd";
    public static final String TAG_DOC_BIOMETRIC_STATUS = "status";
    public static final String TAG_DOCID = "document_id";
    public static final String TAG_APPID = "appointment_id";
    public static final String TAG_START1 = "start_time";
    public static final String TAG_START2 = "end_time";
    public static final String TAG_APPADDRESS = "address";
    public static final String TAG_EXECUTIONER_ID = "ex_email";
    public static final String TAG_APP_FOR = "app_for";

    public static final String TAG_COMMENTS = "cust_comment";
    public static final String TAG_CUSER = "email";
    public static final String TAG_COWNER = "owner_name";
    public static final String TAG_CID = "id";
    public static final String TAG_C_IS_DONE = "is_done";
    public static final String TAG_CDATE = "created_at";
    public static final String TAG_CMTTYPE = "is_done";
    public static final String TAG_CMTAREA = "created_at";
    public static final String TAG_ENV1 = "env";
    public static final String TAG_LANDMARK = "landmark";
    public static final String TAG_CONTACTPERSON = "contact_person";

    public static int i = 0;

    public static final String TAG_USERID = "user_id";
    public static final String TAG_USERNAME = "user_name";
    public static final String TAG_EMAIL = "email";
    public static final String TAG_ROLE = "role";
    public static final String TAG_PLATFORMID = "platform_id";
    public static final String TAG_ROLEID = "role_id";
    public static final String TAG_IDu = "id";


    public static final String TAG_KEYID = "ID";
    public static final String TAG_REPORTKEY = "env";
    public static final String TAG_DOCUMENTID = "document_id";
    public static final String TAG_PAYAMOUNT = "amount";
    public static final String TAG_KEY_LOGIN_USER = "Login_user";
    //new API
    public static final String TAG_DOCUMENT = "document_id";
    public static final String TAG_ATTENDANCE = "att_id";
    public static final String TAG_NAMENEW = "name";
    public static final String TAG_EMAILNEW = "email";
    public static final String TAG_PARTYTYPE = "party_type";
    public static final String TAG_POA = "poa";
    public static final String TAG_APPTYPE = "app_type";
    public static final String TAG_BIOMETRIC = "biometric";
    public static final String TAG_ENV = "env";
    public static final String TAG_PARTY_ADDRESS = "address";
    public static final String TAG_sgrn= "s_grn";
    public static final String TAG_rgrn = "r_grn";
    public static final String TAG_CONTACTNUMBER = "contact_no";


// for adhoc&witness&complaint

    public static final String TAG_ID = "id";
    public static final String TAG_COMMENT = "comment";
    public static final String TAG_IS_DONE = "is_done";
    public static final String TAG_DOC = "document_id";
    public static final String TAG_CREATE = "created_at";
    public static final String TAG_REMAINDER = "reminder_dt";
    public static final String TAG_EMOTION = "emotion";
    public static final String TAG_ASSIGN = "assign_by";
    public static final String TAG_TASK_ID = "task_id";


//appointmentslot

    public static final String TAG_SLOTID = "slot_id";
    public static final String TAG_STARTTIME = "c_start_time";
    public static final String TAG_ENDTIME = "c_end_time";
    public static final String TAG_AVAILABLE = "available";
    public static final String TAG_BLOCK = "block";
    public static final String TAG_DISCOUNT = "discount";

    //attendees
    public static final String TAG_NAMEATTEND = "name";
    public static final String TAG_EMAILATTEND = "email";
    public static final String TAG_CONTACTATTEND = "contact";

    //appointment_booking
    public static String app_value = "false";

    //bio_content
    public static String Tag_att_status = "att_status";

    //sales_appointment
    public static String sales_contactperson = "contact_person";
    public static String sales_starttime = "start_time";
    public static String sales_endtime = "end_time";
    public static String sales_executioner_id = "executioner_id";
    public static String sales_app_for = "app_for";
    public static String sales_doc_id = "document_id";
    public static String sales_created_at = "created_at";
    public static String sales_appointment_id = "appointment_id";
    public static String sales_app_ref = "app_ref";
    public static String sales_app_city = "app_city";
    public static String sales_u_flag = "u_flag";
    public static String sales_create_dt = "create_dt";
    public static String sales_approved_by = "approved_by";
    public static String sales_createdby = "created_by";
    public static String sales_latitude = "latitude";
    public static String sales_longititude = "longitude";
    public static String sales_logical_del = "logical_del";
    public static String sales_poststatus = "post_status";
    public static String sales_app_type = "app_type";
    public static String sales_env = "env";
    public static String sales_id = "id";
    public static String sales_address = "address";
    public static String sales_landmark = "landmark";
    public static String sales_distance = "distance";
    public static String sales_amount = "acvr_amount";
    public static String sales_transtype = "trans_type";
    public static String sales_acvr_id = "acvr_id";
    public static String sales_app_status = "app_status";
    //public static String sales_syncstatus = "SYNC";

    //sales_attendees
    public static String sales_name = "name";
    public static String sales_contact = "contact";
    public static String sales_mail = "email";

// marriage_appointment

    public static String marriage_doc_id = "document_id";
    public static String marriage_env = "env";
    public static String marriage_contact_person = "contact_person";
    public static String marriage_starttime = "start_time";
    public static String marriage_endtime = "end_time";
    public static String marriage_address = "address";
    public static String marriage_landmark = "landmark";
    public static String marriage_app_for = "app_for";
    public static String marriage_executionerid = "executioner_id";
    public static String marriage_appointment_id = "appointment_id";
    public static String marriage_latitude = "latitude";
    public static String marriage_longititude = "longitude";
    public static String marriage_distance = "distance";
    public static String marriage_transtype = "trans_type";
    public static String marriage_acvr_amount = "acvr_amount";
    public static String marriage_acvr_id = "acvr_id";
    public static String marriage_logical_del = "logical_del";
    public static String marriage_poststatus = "post_status";
    public static String marriage_franchise_id = "franchisee_id";
    public static String marriage_co_id = "co_id";
    public static String marriage_status_id = "status_id";
    public static String marriage_ex_id = "ex_id";
    public static String marriage_pay_verify = "pay_verify";
    public static String marriage_pay_pending = "pay_pending";
    public static String marriage_created_at = "created_at";
    public static String marriage_app_flag = "app_flag";
    public static String marriage_pay_flag = "pay_flag";
    public static String marriage_status = "status";
    public static String marriage_id = "id";
    public static String marriage_app_type = "app_type";
    public static String marriage_app_status = "app_status";
    public static String marriage_husbandname = "husband_name";
    public static String marriage_wifename = "wife_name";
    public static String marriage_husbandcontact = "husband_contact";
    public static String marriage_wifecontact = "wife_contact";
    public static String marriage_husbandbiometric = "husband_biometric";
    public static String marriage_wifebiometric = "wife_biometric";
    public static String marriage_tokenno = "token_no";
    public static String marriage_type = "marriage_type";

//marriage attendees

    public static String marriage_name = "name";
    public static String marriage_contact = "contact";
    public static String marriage_mail = "email";

    //pending witness

    public static String PW_doc_id = "document_id";
    public static String PW_env = "env";
    public static String PW_partytype = "party_type";
    public static String PW_attid = "att_id";
    public static String PW_witnessname = "witness_name";
    public static String PW_biometric = "witness_biometric";
    public static String PW_email = "witness_email";
    public static String PW_contact = "witness_contact";
    public static String PW_name = "name";
    public static String PW_ocontact = "ocontact";
    public static String PW_oemail = "oemail";
    public static String PW_oname = "oname";
    public static String PW_oaddress = "oaddress";
    public static String PW_tcontact = "tcontact";
    public static String PW_temail = "temail";
    public static String PW_tname = "tname";
    public static String PW_taddress = "taddress";
    public static String PW_Paddress = "Paddress";
    public static String PW_from_dt = "from_dt";
    public static String PW_to_dt = "to_dt";
    public static String PW_rent = "rent";
    public static String PW_deposit = "deposit";
    public static String PW_refunddeposit = "refunddeposit";
    public static String PW_city_name = "city_name";
    public static String PW_status_id = "status_id";
    public static String PW_token_no = "token_no";
    public static String PW_status = "status";
    public static String PW_agreement_cancle = "agreement_cancle";
    public static String PW_id = "id";

//penalty

    public static String penalty_id = "id";
    public static String penalty_appointment_id = "appointment_id";
    public static String penalty_exec_id = "executioner_id";
    public static String penalty_doc_id = "document_id";
    public static String penalty_amount = "amount";
    public static String penalty_verify = "verify";
    public static String penalty_system_reason = "system_reason";
    public static String penalty_manager_comment = "manager_comment";
    public static String penalty_type = "penalty_type";
    public static String penalty_created_at = "created_at";
    public static String penalty_env = "env";


    public static String getCurrentDate() {
        try {
            Date dNow = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
            return ft.format(dNow);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static boolean isConnected(Context applicationContext) {
        NetworkInfo networkInfo = null;
        if (applicationContext != null) {

            ConnectivityManager conMgr = (ConnectivityManager) applicationContext
                    .getSystemService(Activity.CONNECTIVITY_SERVICE);
            if (conMgr != null) {
                networkInfo = conMgr.getActiveNetworkInfo();
            }
        }

        return networkInfo != null && networkInfo.isConnected();

    }
    public static boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8 ");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean ServerDown() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 52.33.203.208");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }


    public String doGetRequest(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public String doPostRequest(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.setConnectTimeout(60, TimeUnit.SECONDS);  //Connect timeout
        client.setReadTimeout(60, TimeUnit.SECONDS);    //Socket timeout
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
