package anulom.executioner.com.anulom.database;

import android.provider.BaseColumns;

public class DBManager {

    public DBManager() {

    }

    public static abstract class TableInfo implements BaseColumns {

        public static String DATABASE_NAME = "TaskDetails";
        public static String TABLE_NAME_APPOINTMENT = "Appointment1";
        public static String TABLE_NAME1 = "user";
        public static String TABLE_NAME_DOCUMENT = "Document_table";
        public static String TABLE_NAME3 = "Comments";
        public static String TABLE_NAME4 = "Add_Payments";
        public static String TABLE_NAME5 = "usertable1";
        public static String RESCHEDULE = "reschedule";
        public static String MULTIWORK = "multiwork";
        public static String PAYMENT = "Pay1";
        public static String UPDATEPAYMENT1 = "Updatepayment4";
        public static String POSTDOC = "postdoc";
        public static String TABLE_TASK = "task";
        public static String ATTENDEES = "attendees";
        public static String POST_TASK = "posttask";
        public static String REASSIGN = "reassign";
        public static String NOT_APPLICABLE = "not_applicable";
        public static String ACTUAL_TIME = "actual_time";
        public static String CALL = "call";
        public static String ATT_STATUS = "bio_status";
        public static String APPOINTMENTSLOT = "time_slot1";
        public static String APPOINTMENTBOOKING = "app_book";
        public static String UPDATEPARTY = "updateparty11";
        public static String PARTIES = "party";
        public static String TABLE_REM_PAYMENT = "rem_payment1";
        public static String TABLE_DOCUMENT2 = "documentnew2";
        public static String WEEKEND = "weekend";
        public static final String TABLE_LOCATION_DETAILS = "LocationDetails";
        public static String TABLE_SALES = "sales";
        public static String TABLE_SALES_APPOINTMENT = "sales_app";
        public static String TABLE_SALES_ATTENDEES = "sales_attendees";
        public static String TABLE_MARRIAGE_APPOINTMENT = "marriage_app";
        public static String TABLE_MARRIAGE_ATTENDEES = "marriage_attendees";
        public static String TABLE_MARRIAGE = "marriage";
        public static String TABLE_NOTIFICATION = "notification";
        public static String TABLE_WITNESS = "witness";
        public static String POSTWITNESSDOC = "postwitnessdoc";
        public static String TABLE_MANAGER = "manager";
        public static String TABLE_MANAGER_APPOINTMENT = "manager_app";
        public static String TABLE_EXECUTIONER = "executioner";
        public static String TABLE_PENDINGWITNESS = "pending_witness";
        public static String TABLE_TODAY_PARTIES = "today_party1";
        public static String TABLE_OLDER_PARTIES = "older_party";
        public static String TABLE_NEW_PARTIES = "new_party";
        public static String TABLE_PENALTY = "penalty";
        public static String TABLE_COMPLETED_PARTIES = "completed_party";
        public static String TABLE_MARRIAGE_SINGLE_BIOMETRIC = "post_marriage_bio";
        public static String TABLE_UPDATE_PENDING_WITNESS = "post_pending_witness";
        public static String TABLE_PAYMENT_LINK = "Payment_link";
        //USER
        public static String UserID = "uId";
        public static String UserName = "uname1";
        public static String UserEmail = "uemail";
        public static String UserPassword = "upwd";

        //document
        public static String ReportToken = "Rtoken";
        public static String ReportKey = "Rkey";
        public static String Uname = "Uname";
        public static String Umail = "Umail";
        public static String Ucontact = "Ucontact";
        public static String PropertyAddress = "Paddress";
        public static String OwnerName = "Oname";
        public static String OwnerContact = "Ocontact";
        public static String OwnerEmail = "Omail";
        public static String OwnerAddress = "Oaddress";
        public static String TenantName = "Tname";
        public static String TenantContact = "Tcontact";
        public static String TenantEmail = "Tmail";
        public static String TenantAddress = "Taddress";
        public static String LATITUDE = "latitude1";
        public static String LONGITUDE = "longitude1";
        public static String SYNCSTATUS = "syncstatus";
        public static String SYNCSTATUSREPORT = "acvrreportsyncstatus";
        public static String COMMENTSYNCSTATUS = "commentsyncstatus";
        public static String Status = "status";
        public static String BiometricComp = "Biocomp";
        public static String BiometricComp1 = "Biocomp1";
        public static String Appointment_Date = "Appdate";
        public static String Appointment_Date1 = "Appdate1";
        public static String APPTYPE = "app_type";
        public static String Reg_From_Comp = "reg_from_app";
        public static String Witness = "witness";
        public static String Ship_Address = "ship_add";
        public static String Ship_Diff_Address = "ship_diff_add";
        public static String Doc_bio_status = "doc_status";
        public static String s_grn="sgrn";
        public static String r_grn="rgrn";

        //Appointment
        public static String ID1 = "Id1";
        public static String StartDate = "Sdate";
        public static String StartTime1 = "Stime1";
        public static String StartTime2 = "Stime2";
        public static String AppointmentId = "Aid";
        public static String DocumentId = "Did";
        public static String AppointmentAddress = "Aaddress";
        public static String Executioner_id = "Exid";
        public static String AppFor = "appfor";
        public static String post_status = "post_status";
        public static String notification = "value";
        public static String App_status="app_status";
        //Comments
        public static String Comment_id = "Cid";
        public static String Comment_is_done = "Cisdone";
        public static String Comment_user = "Cuser";
        public static String Comment_owner = "Cowner";
        public static String Comment_date = "Cdate";
        public static String Comment_env = "Env";
        public static String Comments = "Ccomments";
        public static String Comments_type = "type";
        public static String Comments_area = "area";
        public static String Comments_contact="contac1";
        public static String REMINDER_DATE = "reminder_date";
        public static String task_name = "taskname";

        //UserRole
        public static String User_id = "userid";
        public static String User_name = "nameuser1";
        public static String Role = "role2";
        public static String Email = "email2";
        public static final String platformid = "platformid2";
        public static final String role_id = "roleid2";
        public static final String idu = "identity2";

        public static final String latitudeInput = "latitude";
        public static final String longitudeInput = "longitude";
        public static final String DATE = "date";
        public static final String LAT_LONG_ADDRESS = "LatLongAddress";

        public static final String DISTANCE = "distance";
        public static final String AMOUNT = "amount";
        public static final String APOINTMENTFOR = "appointment";
        public static final String TRANSPORTTYPE = "tranporttype";
        //Payment
        public static final String DEPOSIT = "deposit";
        public static final String ID = "id";
        public static String KEYID = "ID";
        public static String rep1 = "env";
        public static String doc1 = "document_id";
        public static String payamount = "amount";
        public static String KEY_LOGIN_USER = "Login_user";
        public static String radiotype = "type";
        public static String comment1 = "comment";
        public static String date = "date";

        public static String DOCID = "document_id";
        public static String email1 = "email";
        public static String amt = "amt";
        public static String appid_payment = "appid_payment";
        public static String exec_email = "loginuser";
        public static String not_paid_comment1 = "not_paid_comment";
        public static String time = "payment_time";
        public static String paid_comment="paid_comment";
        // party table
        public static final String DOCUMENT = "document_id";
        public static final String ATTENDANCE = "att_id";
        public static final String NAMENEW = "name";
        public static final String EMAILNEW = "email";
        public static final String PARTYTYPE = "party_type";
        public static final String POA = "poa";
        public static final String CONTACT = "contact_no";
        public static final String BIOMETRIC = "biometric";
        public static final String AMOUNT1 = "amount";
        public static final String ENV = "env";
        public static final String PARTY_ADDRESS = "party_address";
        public static String internal_witness1 = "internal_wit1";
        public static String internal_witness2 = "internal_wit2";
        public static String external_witness1 = "external_wit1";
        public static String external_witness2 = "external_wit2";


        public static final String RESCHEDULEDATE = "rescheduledate";
        public static final String RESCHEDULETIME = "rescheduletime";
        public static final String RES_REASON = "reason1";

        public static final String DOCU = "document_id";
        public static final String ATTEND = "att_id";
        public static final String EMAIL = "email";
        public static final String PARTY = "party_type";
        public static final String BIO = "biometric";

        public static final String postdocument = "document";

        //for adhoc&witness&complaint

        public static final String WITID = "id1";
        public static final String COMMENT = "comment";
        public static final String IS_DONE = "is_done";
        public static final String DOC = "document_id";
        public static final String CREATE = "created_at";
        public static final String REMAINDER = "reminder_dt";
        public static final String EMOTION = "emotion";
        public static final String ASSIGN = "assign_by";
        public static final String TASK_ID = "task_id";
        public static final String TYPE = "type";
        public static final String TASK_NAME = "task_name";
        public static final String ADHOC = "Adhoc";
        public static final String WITNESS = "Witness";
        public static final String COMPLAINT = "Complaint";
        public static final String REASON = "reason";
        public static final String STATUS = "status1";

        //getappointmentslot
        public static final String slotid1 = "slot_id";
        public static final String starttime1 = "start_time";
        public static final String endtime1 = "end_time";
        public static final String available1 = "available";
        public static final String block1 = "block";
        public static final String discount1 = "discount";

        //postappointmentbooking

        public static final String request_no = "requestno";
        public static final String app_date = "appdate";
        public static final String timenew = "time";
        public static final String slotid = "slotid";
        public static final String division_id = "divisionid";
        public static final String region_id = "regionid";
        public static final String attendees = "attendees";
        public static final String address = "address";
        public static final String attendeesemail = "attendeesemail";
        public static final String attendeescontact = "attendeescontact";
        public static final String contactpersonemail = "contactemail";
        public static final String contactpersoncont = "contact";
        public static final String landmark = "landmark";
        public static final String contactperson = "person";
        public static final String free = "free";
        public static final String free_reason = "free_reason";
        public static final String city = "city";

        //attendees
        public static final String nameattend = "name";
        public static final String emailattend = "email";
        public static final String contactattend = "contact";
        public static final String appointattend = "appointment";

        //weekend/weekoff
        public static final String from_date = "from_date";
        public static final String to_date = "to_date";
        public static final String statusoff = "statusoff";
        public static final String reasonoff = "reasonoff";

        //reassign
        public static final String Prev_owner = "prev_owner";
        public static final String new_owner = "new_owner";
        public static final String reassign_reason = "reassign_reason";
        public static final String status1 = "status";
        public static final String new_owner1 = "new_owner1";
        public static final String comm1="comm1";
        public static final String taskid1="task";

        //not applicable
        public static final String task_id1 = "taskidapplicable";
        public static final String not_app = "not_app";
        public static final String comment_applicable = "comment_appplicable";
        public static final String notapplicable_reason = "notaqpplicable_reason";

        //status

        public static final String reason = "reason";

        //actual_time

        public static final String actual_time = "time";
        public static final String reach_time="reach_time";
        //call

        public static final String gen_distance = "gen_distance";
        public static final String call_time = "call_time";

        //bio_content

        public static final String att_status = "att_status";

        //sales

        public static String db_sales_contactperson = "contact_person";
        public static String db_sales_starttime = "start_time";
        public static String db_sales_endtime = "end_time";
        public static String db_sales_executioner_id = "executioner_id";
        public static String db_sales_app_for = "app_for";
        public static String db_sales_doc_id = "document_id";
        public static String db_sales_created_at = "created_at";
        public static String db_sales_appointment_id = "appointment_id";
        public static String db_sales_app_ref = "app_ref";
        public static String db_sales_app_city = "app_city";
        public static String db_sales_u_flag = "u_flag";
        public static String db_sales_create_dt = "create_dt";
        public static String db_sales_approved_by = "approved_by";
        public static String db_sales_createdby = "created_by";
        public static String db_sales_latitude = "latitude";
        public static String db_sales_longititude = "longitude";
        public static String db_sales_logical_del = "logical_del";
        public static String db_sales_poststatus = "post_status";
        public static String db_sales_app_type = "app_type";
        public static String db_sales_env = "env";
        public static String db_sales_id = "id";
        public static String db_sales_address = "address";
        public static String db_sales_landmark = "landmark";
        public static String db_sales_syncstatus = "synstatus";
        public static String db_sales_acvrsyncstatus = "acvrsynstatus";
        public static String db_sales_distance = "distance";
        public static String db_sales_amount = "acvr_amount";
        public static String db_sales_transtype = "trans_type";
        public static String db_sales_acvr_id = "acvr_id";
        public static String db_sales_app_status = "sales_app_status";
        public static String db_sales_startdate = "sales_date";
        //sales_attendees
        public static String db_sales_name = "name";
        public static String db_sales_contact = "contact";
        public static String db_sales_mail = "email";

//marriage

        public static String db_marriage_doc_id = "document_id";
        public static String db_marriage_env = "env";
        public static String db_marriage_contact_person = "contact_person";
        public static String db_marriage_starttime = "start_time";
        public static String db_marriage_endtime = "end_time";
        public static String db_marriage_address = "address";
        public static String db_marriage_landmark = "landmark";
        public static String db_marriage_app_for = "app_for";
        public static String db_marriage_executionerid = "executioner_id";
        public static String db_marriage_appointment_id = "appointment_id";
        public static String db_marriage_latitude = "latitude";
        public static String db_marriage_longititude = "longitude";
        public static String db_marriage_distance = "distance";
        public static String db_marriage_transtype = "trans_type";
        public static String db_marriage_acvr_amount = "acvr_amount";
        public static String db_marriage_acvr_id = "acvr_id";
        public static String db_marriage_logical_del = "logical_del";
        public static String db_marriage_poststatus = "post_status";
        public static String db_marriage_franchise_id = "franchisee_id";
        public static String db_marriage_co_id = "co_id";
        public static String db_marriage_status_id = "status_id";
        public static String db_marriage_ex_id = "ex_id";
        public static String db_marriage_pay_verify = "pay_verify";
        public static String db_marriage_pay_pending = "pay_pending";
        public static String db_marriage_created_at = "created_at";
        public static String db_marriage_app_flag = "app_flag";
        public static String db_marriage_pay_flag = "pay_flag";
        public static String db_marriage_status = "status";
        public static String db_marriage_id = "id";
        public static String db_marriage_app_type = "app_type";
        public static String db_marriage_app_status="marriage_app_status";
        public static String db_marriage_syncstatus = "syncstatus";
        public static String db_marriage_acvrsyncstatus = "acvrsyncstatus";
        public static String db_marriage_husbandname = "husband_name";
        public static String db_marriage_wifename = "wife_name";
        public static String db_marriage_husbandcontact = "husband_contact";
        public static String db_marriage_wifecontact = "wife_contact";
        public static String db_marriage_husbandbiometric = "husband_biometric";
        public static String db_marriage_wifebiometric = "wife_biometric";
        public static String db_marriage_tokenno = "token_no";
        public static String db_marriage_startdate = "marriage_sdate";
        public static String db_marriage_type = "marriage_type";
        //marriage attendees

        public static String db_marriage_name = "name";
        public static String db_marriage_contact = "contact";
        public static String db_marriage_mail = "email";

        //notification table

        public static String notification_starttime = "starttime";
        public static String notification_interval = "interval";
        public static String notification_repeat = "repeat";
        public static String notification_appid = "appid";
        public static String notification_time = "notificationtime";
        public static String notification_type = "type";
        public static String notificationvalue = "value";


        //biometric post field
        public static String updatelocation = "location";
        public static String updatetime = "time";
        public static String biotype = "biotype";



        //internal/external witness

        public static String witness_docid = "winess_docid";
        public static String internal_witness_userid1 = "winess_userid1";
        public static String internal_witness_email1 = "witness_email1";
        public static String internal_witness_userid2 = "winess_userid2";
        public static String internal_witness_email2 = "witness_email2";
        public static String internal_witness_type = "witness_type";
        public static String external_witness_email1 = "external_witness_email1";
        public static String external_witness_email2 = "external_witness_email2";
        public static String external_witness_type = "external_witness_type";
        public static String internal_biometric1 = "internal_bio1";
        public static String external_biometric1 = "external_bio1";
        public static String internal_biometric2 = "internal_bio2";
        public static String external_biometric2 = "external_bio2";
        public static String radiovalue = "radiovalue";
//post witness dov

        public static final String postwitnessdocument = "document";

        //manager role
        public static String executioneremail = "name";
        public static String execpassword = "contact";

        //pending witness

        public static String db_PW_doc_id = "document_id1";
        public static String db_PW_env = "env1";
        public static String db_PW_partytype = "party_type";
        public static String db_PW_attid = "att_id";
        public static String db_PW_witnessname = "witness_name";
        public static String db_PW_biometric = "witness_biometric";
        public static String db_PW_email = "witness_email";
        public static String db_PW_contact = "witness_contact";
        public static String db_PW_name = "pw_name";
        public static String db_PW_ocontact = "ocontact";
        public static String db_PW_oemail = "oemail";
        public static String db_PW_oname = "oname";
        public static String db_PW_oaddress = "oaddress";
        public static String db_PW_tcontact = "tcontact";
        public static String db_PW_temail = "temail";
        public static String db_PW_tname = "tname";
        public static String db_PW_taddress = "taddress";
        public static String db_PW_Paddress = "Paddress";
        public static String db_PW_from_dt = "from_dt";
        public static String db_PW_to_dt = "to_dt";
        public static String db_PW_rent = "rent";
        public static String db_PW_deposit = "deposit";
        public static String db_PW_refunddeposit = "refunddeposit";
        public static String db_PW_city_name = "city_name";
        public static String db_PW_status_id = "status_id";
        public static String db_PW_token_no = "token_no1";
        public static String db_PW_status = "status";
        public static String db_PW_agreement_cancle = "agreement_cancle";
        public static String db_PW_id = "pw_id";


        public static String biometric = "biometric";
        public static String docuuu = "docuuu";

        //pending winess post updation

        public static String execemail = "exec_email";
        public static String pw_docid = "docid";
        public static String pw_attid = "attid";
        public static String pw_email = "mail";
        public static String pw_partytype = "type";
        public static String pw_biometric = "bio";

        //penalty
        public static String db_penalty_id = "id5";
        public static String db_penalty_appointment_id = "appointment_id";
        public static String db_penalty_exec_id = "executioner_id";
        public static String db_penalty_doc_id = "document_id";
        public static String db_penalty_amount = "amount";
        public static String db_penalty_verify = "verify";
        public static String db_penalty_system_reason = "system_reason";
        public static String db_penalty_manager_comment = "manager_comment";
        public static String db_penalty_type = "penalty_type";
        public static String db_penalty_created_at = "created_at";
        public static String db_penalty_env = "env";

        //payment link
        public static String exec_email_payment = "id5";
        public static String docid_payment = "appointment_id";



    }
}
//techanulom@789