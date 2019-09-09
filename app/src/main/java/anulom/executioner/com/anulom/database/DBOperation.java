package anulom.executioner.com.anulom.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import anulom.executioner.com.anulom.GenericMethods;
import anulom.executioner.com.anulom.Login;

import static anulom.executioner.com.anulom.database.DBManager.TableInfo.APPOINTMENTSLOT;
import static anulom.executioner.com.anulom.database.DBManager.TableInfo.ATTENDEES;
import static anulom.executioner.com.anulom.database.DBManager.TableInfo.ATT_STATUS;
import static anulom.executioner.com.anulom.database.DBManager.TableInfo.DOCUMENT;
import static anulom.executioner.com.anulom.database.DBManager.TableInfo.MULTIWORK;
import static anulom.executioner.com.anulom.database.DBManager.TableInfo.NOT_APPLICABLE;

import static anulom.executioner.com.anulom.database.DBManager.TableInfo.PAYMENT;
import static anulom.executioner.com.anulom.database.DBManager.TableInfo.REASSIGN;

import static anulom.executioner.com.anulom.database.DBManager.TableInfo.TABLE_MARRIAGE;
import static anulom.executioner.com.anulom.database.DBManager.TableInfo.TABLE_MARRIAGE_APPOINTMENT;
import static anulom.executioner.com.anulom.database.DBManager.TableInfo.TABLE_MARRIAGE_ATTENDEES;
import static anulom.executioner.com.anulom.database.DBManager.TableInfo.TABLE_MARRIAGE_SINGLE_BIOMETRIC;
import static anulom.executioner.com.anulom.database.DBManager.TableInfo.TABLE_PAYMENT_LINK;
import static anulom.executioner.com.anulom.database.DBManager.TableInfo.TABLE_SALES;
import static anulom.executioner.com.anulom.database.DBManager.TableInfo.TABLE_SALES_APPOINTMENT;
import static anulom.executioner.com.anulom.database.DBManager.TableInfo.TABLE_SALES_ATTENDEES;
import static anulom.executioner.com.anulom.database.DBManager.TableInfo.TABLE_TASK;
import static anulom.executioner.com.anulom.database.DBManager.TableInfo.TABLE_UPDATE_PENDING_WITNESS;
import static anulom.executioner.com.anulom.database.DBManager.TableInfo.UPDATEPAYMENT1;
import static anulom.executioner.com.anulom.database.DBManager.TableInfo.WEEKEND;
import static anulom.executioner.com.anulom.database.DBManager.TableInfo.gen_distance;
import static anulom.executioner.com.anulom.database.DBManager.TableInfo.landmark;


public class DBOperation extends SQLiteOpenHelper {

    private SharedPreferences usermail;

    public static final int database_version = 183;

    public final String CREATE_QUERY1 = "CREATE TABLE " + DBManager.TableInfo.TABLE_NAME1 + " (" + DBManager.TableInfo.UserName + " TEXT , "
            + DBManager.TableInfo.UserPassword + " TEXT , " + DBManager.TableInfo.UserEmail + " TEXT PRIMARY KEY);";

    String DOCUMENTT3 = "CREATE TABLE " + DBManager.TableInfo.TABLE_NAME_DOCUMENT + " ("
            + DBManager.TableInfo.ReportKey + " TEXT  , "
            + DBManager.TableInfo.ReportToken + " TEXT , "
            + DBManager.TableInfo.Umail + " TEXT , "
            + DBManager.TableInfo.s_grn + " TEXT , "
            + DBManager.TableInfo.r_grn + " TEXT , "
            + DBManager.TableInfo.Uname + " TEXT , "
            + DBManager.TableInfo.Ucontact + " TEXT , "
            + DBManager.TableInfo.PropertyAddress + " TEXT , "
            + DBManager.TableInfo.OwnerName + " TEXT , "
            + DBManager.TableInfo.OwnerContact + " TEXT , "
            + DBManager.TableInfo.OwnerEmail + " TEXT , "
            + DBManager.TableInfo.OwnerAddress + " TEXT , "
            + DBManager.TableInfo.TenantName + " TEXT , "
            + DBManager.TableInfo.TenantContact + " TEXT , "
            + DBManager.TableInfo.TenantEmail + " TEXT , "
            + DBManager.TableInfo.TenantAddress + " TEXT , "
            + DBManager.TableInfo.Status + " TEXT , "
            + DBManager.TableInfo.UserEmail + " TEXT , "
            + DBManager.TableInfo.BiometricComp + " TEXT , "
            + DBManager.TableInfo.Appointment_Date + " TEXT , "
            + DBManager.TableInfo.BiometricComp1 + " TEXT , "
            + DBManager.TableInfo.Appointment_Date1 + " TEXT , "
            + DBManager.TableInfo.Reg_From_Comp + " TEXT , "
            + DBManager.TableInfo.Witness + " TEXT , "
            + DBManager.TableInfo.Ship_Address + " TEXT , "
            + DBManager.TableInfo.Ship_Diff_Address + " TEXT , "
            + DBManager.TableInfo.SYNCSTATUS + " TEXT , "
            + DBManager.TableInfo.APPTYPE + " TEXT , "
            + DBManager.TableInfo.updatelocation + " TEXT , "
            + DBManager.TableInfo.updatetime + " TEXT , "
            + DBManager.TableInfo.Doc_bio_status + " TEXT , "
            + DBManager.TableInfo.DocumentId + " TEXT PRIMARY KEY " + ");";


    String CREATE_QUERY_APP = "CREATE TABLE " + DBManager.TableInfo.TABLE_NAME_APPOINTMENT + " ("
            + DBManager.TableInfo.KEYID + " INTEGER PRIMARY KEY,"
            + DBManager.TableInfo.StartDate + " TEXT ,"
            + DBManager.TableInfo.StartTime1 + " TEXT ,"
            + DBManager.TableInfo.StartTime2 + " TEXT ,"
            + DBManager.TableInfo.DocumentId + " TEXT,"
            + DBManager.TableInfo.AppointmentAddress + " TEXT,"
            + DBManager.TableInfo.AppointmentId + " TEXT,"
            + DBManager.TableInfo.contactperson + " TEXT ,"
            + DBManager.TableInfo.landmark + " TEXT, "
            + DBManager.TableInfo.Executioner_id + " TEXT ,"
            + DBManager.TableInfo.DISTANCE + " TEXT ,"
            + DBManager.TableInfo.AMOUNT + " TEXT ,"
            + DBManager.TableInfo.TRANSPORTTYPE + " TEXT ,"
            + DBManager.TableInfo.SYNCSTATUSREPORT + " TEXT ,"
            + DBManager.TableInfo.att_status + " TEXT ,"
            + DBManager.TableInfo.post_status + " TEXT , "
            + DBManager.TableInfo.task_name + " TEXT , "
            + DBManager.TableInfo.LATITUDE + " TEXT ,"
            + DBManager.TableInfo.LONGITUDE + " TEXT , "
            + DBManager.TableInfo.App_status + " TEXT , "
            + DBManager.TableInfo.notification + " TEXT , "
            + DBManager.TableInfo.AppFor + " TEXT );";


    public final String CREATE_QUERY3 = "CREATE TABLE " + DBManager.TableInfo.TABLE_NAME3 + " ("
            + DBManager.TableInfo.Comment_id + " TEXT ,"
            + DBManager.TableInfo.Comments + " TEXT ,"
            + DBManager.TableInfo.Comment_user + " TEXT ,"
            + DBManager.TableInfo.Comment_owner + " TEXT ,"
            + DBManager.TableInfo.Comment_env + " TEXT,"
            + DBManager.TableInfo.Comments_contact + " TEXT,"
            + DBManager.TableInfo.Comment_is_done + " BOOLEAN,"
            + DBManager.TableInfo.SYNCSTATUS + " TEXT ,"
            + DBManager.TableInfo.Comments_type + " TEXT ,"
            + DBManager.TableInfo.Comments_area + " TEXT ,"
            + DBManager.TableInfo.REMINDER_DATE + " TEXT ,"
            + DBManager.TableInfo.task_name + " TEXT ,"
            + DBManager.TableInfo.Comment_date + " TEXT);";


    String sqlLocationDetails = " CREATE TABLE " + DBManager.TableInfo.TABLE_LOCATION_DETAILS
            + " (_id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL, "
            + DBManager.TableInfo.latitudeInput + " TEXT, " + DBManager.TableInfo.longitudeInput + " TEXT, " + DBManager.TableInfo.DATE
            + " TEXT, " + DBManager.TableInfo.UserName + " TEXT,"
            + DBManager.TableInfo.LAT_LONG_ADDRESS + " TEXT)";

    String PAYMENT_DETAILS = "CREATE TABLE " + PAYMENT + "("
            + DBManager.TableInfo.KEYID + " INTEGER PRIMARY KEY,"
            + DBManager.TableInfo.doc1 + " TEXT,"
            + DBManager.TableInfo.rep1 + " TEXT,"
            + DBManager.TableInfo.payamount + " TEXT,"
            + DBManager.TableInfo.KEY_LOGIN_USER + " TEXT)";
    String UPDATE_PAYMENT_DETAILS = "CREATE TABLE " + UPDATEPAYMENT1 + "("
            + DBManager.TableInfo.KEYID + " INTEGER PRIMARY KEY,"
            + DBManager.TableInfo.DOCID + " TEXT,"
            + DBManager.TableInfo.email1 + " TEXT,"
            + DBManager.TableInfo.amt + " TEXT,"
            + DBManager.TableInfo.date + " TEXT,"
            + DBManager.TableInfo.radiotype + " TEXT,"
            + DBManager.TableInfo.comment1 + " TEXT" + ")";




    String REM_PAYMENT = "CREATE TABLE " + DBManager.TableInfo.TABLE_REM_PAYMENT + "("
            + DBManager.TableInfo.KEYID + "INTEGER PRIMARY KEY,"
            + DBManager.TableInfo.DOCUMENT + " TEXT,"
            + DBManager.TableInfo.AMOUNT1 + " TEXT,"
            + DBManager.TableInfo.ENV + " TEXT,"
            + DBManager.TableInfo.KEY_LOGIN_USER + " TEXT" + ")";


    String CREATE_CHECKK_TABLE = "CREATE TABLE " + GenericMethods.TABLE_CHECK_JOB + "("
            + GenericMethods.KEY_ID + " INTEGER PRIMARY KEY,"
            + GenericMethods.R_KEY + " TEXT,"
            + GenericMethods.JOB + " TEXT,"
            + GenericMethods.KEY_LOGIN_USER + " TEXT" + ")";

    String CREATE_LAST_UPDATE_TABLE = "CREATE TABLE " + GenericMethods.TABLE_LAST_UPDATE + "("
            + GenericMethods.KEY_ID + " INTEGER PRIMARY KEY,"
            + GenericMethods.LAST_UPDATED + " TEXT,"
            + GenericMethods.KEY_LOGIN_USER + " TEXT" + ")";


    String PARTYUPDATE1 = "CREATE TABLE " + DBManager.TableInfo.UPDATEPARTY + "("
            + DBManager.TableInfo.KEYID + " INTEGER PRIMARY KEY,"
            + DBManager.TableInfo.DOCU + " TEXT,"
            + DBManager.TableInfo.ATTEND + " TEXT,"
            + DBManager.TableInfo.EMAIL + " TEXT,"
            + DBManager.TableInfo.PARTY + " TEXT,"
            + DBManager.TableInfo.BIO + " TEXT,"
            + DBManager.TableInfo.biotype + " TEXT,"
            + DBManager.TableInfo.updatelocation + " TEXT , "
            + DBManager.TableInfo.updatetime + " TEXT , "
            + DBManager.TableInfo.AppointmentId + " TEXT , "
            + DBManager.TableInfo.internal_witness1 + " TEXT,"
            + DBManager.TableInfo.internal_witness2 + " TEXT , "
            + DBManager.TableInfo.external_witness1 + " TEXT , "
            + DBManager.TableInfo.external_witness2 + " TEXT , "
            + DBManager.TableInfo.KEY_LOGIN_USER + " TEXT" + ")";

    String POSTUPDATE = "CREATE TABLE " + DBManager.TableInfo.POSTDOC + "("
            + DBManager.TableInfo.KEYID + " INTEGER PRIMARY KEY,"
            + DBManager.TableInfo.postdocument + " TEXT,"
            + DBManager.TableInfo.KEY_LOGIN_USER + " TEXT" + ")";


    String POSTTASK = "CREATE TABLE " + DBManager.TableInfo.POST_TASK + "("
            + DBManager.TableInfo.KEYID + " INTEGER PRIMARY KEY,"
            + DBManager.TableInfo.IS_DONE + " TEXT,"
            + DBManager.TableInfo.TYPE + " TEXT,"
            + DBManager.TableInfo.REASON + " TEXT,"
            + DBManager.TableInfo.comm1 + " TEXT,"
            + DBManager.TableInfo.TASK_ID + " TEXT,"
            + DBManager.TableInfo.status1 + " TEXT,"
            + DBManager.TableInfo.KEY_LOGIN_USER + " TEXT" + ")";


    String RESCHEDULE = "CREATE TABLE " + DBManager.TableInfo.RESCHEDULE + "("
            + DBManager.TableInfo.KEYID + " INTEGER PRIMARY KEY,"
            + DBManager.TableInfo.RESCHEDULEDATE + " TEXT,"
            + DBManager.TableInfo.RESCHEDULETIME + " TEXT,"
            + DBManager.TableInfo.RES_REASON + " TEXT,"
            + DBManager.TableInfo.DocumentId + " TEXT,"
            + DBManager.TableInfo.AppointmentId + " TEXT,"
            + DBManager.TableInfo.KEY_LOGIN_USER + " TEXT" + ")";

    String multiwork = "CREATE TABLE " + DBManager.TableInfo.MULTIWORK + "("
            + DBManager.TableInfo.KEYID + " INTEGER PRIMARY KEY,"
            + DBManager.TableInfo.DOCU + " TEXT,"
            + DBManager.TableInfo.AppointmentId + " TEXT,"
            + DBManager.TableInfo.STATUS + " TEXT,"
            + DBManager.TableInfo.reason + " TEXT ,"
            + DBManager.TableInfo.reach_time + " TEXT,"
            + DBManager.TableInfo.updatelocation + " TEXT,"
            + DBManager.TableInfo.updatetime + " TEXT ,"
            + DBManager.TableInfo.KEY_LOGIN_USER + " TEXT" + ")";

    String APPOINTMENT = "CREATE TABLE " + APPOINTMENTSLOT + "("
            + DBManager.TableInfo.KEYID + " INTEGER PRIMARY KEY,"
            + DBManager.TableInfo.slotid1 + " TEXT,"
            + DBManager.TableInfo.starttime1 + " TEXT,"
            + DBManager.TableInfo.endtime1 + " TEXT,"
            + DBManager.TableInfo.available1 + " TEXT,"
            + DBManager.TableInfo.block1 + " TEXT,"
            + DBManager.TableInfo.discount1 + " TEXT,"
            + DBManager.TableInfo.KEY_LOGIN_USER + " TEXT" + ")";

    String APPOINTMENTBOOKING = "CREATE TABLE " + DBManager.TableInfo.APPOINTMENTBOOKING + "("
            + DBManager.TableInfo.KEYID + " INTEGER PRIMARY KEY,"
            + DBManager.TableInfo.request_no + " TEXT,"
            + DBManager.TableInfo.slotid + " TEXT ,"
            + DBManager.TableInfo.app_date + " TEXT,"
            + DBManager.TableInfo.timenew + " TEXT,"
            + DBManager.TableInfo.division_id + " TEXT,"
            + DBManager.TableInfo.region_id + " TEXT,"
            + DBManager.TableInfo.attendees + " TEXT,"
            + DBManager.TableInfo.address + " TEXT,"
            + DBManager.TableInfo.free + " TEXT ,"
            + DBManager.TableInfo.free_reason + " TEXT,"
            + DBManager.TableInfo.attendeesemail + " TEXT ,"
            + DBManager.TableInfo.attendeescontact + " TEXT ,"
            + DBManager.TableInfo.contactpersonemail + " TEXT ,"
            + DBManager.TableInfo.contactpersoncont + " TEXT ,"
            + DBManager.TableInfo.landmark + " TEXT,"
            + DBManager.TableInfo.contactperson + " TEXT,"
            + DBManager.TableInfo.city + " TEXT ,"
            + DBManager.TableInfo.AppointmentId + " TEXT ,"
            + DBManager.TableInfo.KEY_LOGIN_USER + " TEXT" + ")";

    String CREATE_QUERY5 = "CREATE TABLE " + DBManager.TableInfo.TABLE_NAME5 + "("
            + DBManager.TableInfo.KEYID + " INTEGER PRIMARY KEY,"
            + DBManager.TableInfo.User_name + " TEXT,"
            + DBManager.TableInfo.Role + " TEXT,"
            + DBManager.TableInfo.User_id + " TEXT,"
            + DBManager.TableInfo.Email + " TEXT,"
            + DBManager.TableInfo.role_id + " TEXT,"
            + DBManager.TableInfo.idu + " TEXT,"
            + DBManager.TableInfo.platformid + " TEXT ,"
            + DBManager.TableInfo.KEY_LOGIN_USER + " TEXT" + ")";


    String attendees = "CREATE TABLE " + DBManager.TableInfo.ATTENDEES + "("
            + DBManager.TableInfo.KEYID + " INTEGER PRIMARY KEY,"
            + DBManager.TableInfo.nameattend + " TEXT,"
            + DBManager.TableInfo.emailattend + " TEXT,"
            + DBManager.TableInfo.contactattend + " TEXT,"
            + DBManager.TableInfo.appointattend + " TEXT,"
            + DBManager.TableInfo.KEY_LOGIN_USER + " TEXT" + ")";

    String WEEKENDOFF = "CREATE TABLE " + WEEKEND + "("
            + DBManager.TableInfo.KEYID + " INTEGER PRIMARY KEY,"
            + DBManager.TableInfo.from_date + " TEXT,"
            + DBManager.TableInfo.to_date + " TEXT,"
            + DBManager.TableInfo.statusoff + " TEXT,"
            + DBManager.TableInfo.reasonoff + " TEXT,"
            + DBManager.TableInfo.KEY_LOGIN_USER + " TEXT" + ")";

    String TASK = "CREATE TABLE " + DBManager.TableInfo.TABLE_TASK + "("
            + DBManager.TableInfo.KEYID + " INTEGER PRIMARY KEY,"
            + DBManager.TableInfo.WITID + " TEXT,"
            + DBManager.TableInfo.COMMENT + " TEXT,"
            + DBManager.TableInfo.IS_DONE + " TEXT,"
            + DBManager.TableInfo.DOC + " TEXT,"
            + DBManager.TableInfo.CREATE + " TEXT,"
            + DBManager.TableInfo.REMAINDER + " TEXT,"
            + DBManager.TableInfo.ASSIGN + " TEXT,"
            + DBManager.TableInfo.TASK_ID + " TEXT,"
            + DBManager.TableInfo.TASK_NAME + " TEXT ,"
            + DBManager.TableInfo.ADHOC + " TEXT ,"
            + DBManager.TableInfo.WITNESS + " TEXT ,"
            + DBManager.TableInfo.notification + " TEXT , "
            + DBManager.TableInfo.COMPLAINT + " TEXT ,"
            + DBManager.TableInfo.status1 + " TEXT ,"
            + DBManager.TableInfo.db_PW_doc_id + " TEXT , "
            + DBManager.TableInfo.db_PW_env + " TEXT ,"
            + DBManager.TableInfo.db_PW_token_no + " TEXT ,"
            + DBManager.TableInfo.KEY_LOGIN_USER + " TEXT" + ")";

    String attendees2 = "CREATE TABLE " + DBManager.TableInfo.REASSIGN + "("
            + DBManager.TableInfo.KEYID + " INTEGER PRIMARY KEY,"
            + DBManager.TableInfo.taskid1 + " TEXT,"
            + DBManager.TableInfo.Prev_owner + " TEXT,"
            + DBManager.TableInfo.new_owner + " TEXT,"
            + DBManager.TableInfo.comm1 + " TEXT,"
            + DBManager.TableInfo.reassign_reason + " TEXT,"
            + DBManager.TableInfo.status1 + " TEXT,"
            + DBManager.TableInfo.new_owner1 + " TEXT ,"
            + DBManager.TableInfo.KEY_LOGIN_USER + " TEXT" + ")";


    String attendees1 = "CREATE TABLE " + DBManager.TableInfo.NOT_APPLICABLE + "("
            + DBManager.TableInfo.KEYID + " INTEGER PRIMARY KEY,"
            + DBManager.TableInfo.task_id1 + " TEXT,"
            + DBManager.TableInfo.comment_applicable + " TEXT,"
            + DBManager.TableInfo.comm1 + " TEXT,"
            + DBManager.TableInfo.notapplicable_reason + " TEXT,"
            + DBManager.TableInfo.not_app + " TEXT,"
            + DBManager.TableInfo.status1 + " TEXT,"
            + DBManager.TableInfo.KEY_LOGIN_USER + " TEXT" + ")";

    String ACTUAL_TIME = "CREATE TABLE " + DBManager.TableInfo.ACTUAL_TIME + "("
            + DBManager.TableInfo.KEYID + " INTEGER PRIMARY KEY,"
            + DBManager.TableInfo.DocumentId + " TEXT,"
            + DBManager.TableInfo.AppointmentId + " TEXT,"
            + DBManager.TableInfo.actual_time + " TEXT,"
            + DBManager.TableInfo.call_time + " TEXT,"
            + DBManager.TableInfo.gen_distance + " TEXT,"
            + DBManager.TableInfo.KEY_LOGIN_USER + " TEXT" + ")";

    String CALL = "CREATE TABLE " + DBManager.TableInfo.CALL + "("
            + DBManager.TableInfo.KEYID + " INTEGER PRIMARY KEY,"
            + DBManager.TableInfo.DocumentId + " TEXT,"
            + DBManager.TableInfo.AppointmentId + " TEXT,"
            + DBManager.TableInfo.gen_distance + " TEXT,"
            + DBManager.TableInfo.call_time + " TEXT,"
            + DBManager.TableInfo.actual_time + " TEXT,"
            + DBManager.TableInfo.KEY_LOGIN_USER + " TEXT" + ")";


    String ATT = "CREATE TABLE " + DBManager.TableInfo.ATT_STATUS + "("
            + DBManager.TableInfo.KEYID + " INTEGER PRIMARY KEY,"
            + DBManager.TableInfo.DocumentId + " TEXT,"
            + DBManager.TableInfo.att_status + " TEXT,"
            + DBManager.TableInfo.KEY_LOGIN_USER + " TEXT" + ")";


    String SALES = "CREATE TABLE " + DBManager.TableInfo.TABLE_SALES + " ("
            + DBManager.TableInfo.db_sales_env + " TEXT  , "
            + DBManager.TableInfo.KEY_LOGIN_USER + " TEXT , "
            + DBManager.TableInfo.db_sales_syncstatus + " TEXT , "
            + DBManager.TableInfo.db_sales_acvrsyncstatus + " TEXT , "
            + DBManager.TableInfo.db_sales_doc_id + " TEXT PRIMARY KEY " + ");";


    String SALES_APP = "CREATE TABLE " + DBManager.TableInfo.TABLE_SALES_APPOINTMENT
            + " (" + DBManager.TableInfo.db_sales_starttime + " TEXT ,"
            + DBManager.TableInfo.db_sales_endtime + " TEXT ,"
            + DBManager.TableInfo.db_sales_startdate + " TEXT,"
            + DBManager.TableInfo.db_sales_doc_id + " TEXT ,"
            + DBManager.TableInfo.db_sales_app_city + " TEXT,"
            + DBManager.TableInfo.db_sales_appointment_id + " TEXT PRIMARY KEY,"
            + DBManager.TableInfo.db_sales_contactperson + " TEXT ,"
            + DBManager.TableInfo.db_sales_executioner_id + " TEXT ,"
            + DBManager.TableInfo.db_sales_app_ref + " TEXT ,"
            + DBManager.TableInfo.db_sales_create_dt + " TEXT ,"
            + DBManager.TableInfo.db_sales_created_at + " TEXT ,"
            + DBManager.TableInfo.db_sales_createdby + " TEXT ,"
            + DBManager.TableInfo.db_sales_logical_del + " TEXT ,"
            + DBManager.TableInfo.db_sales_u_flag + " TEXT ,"
            + DBManager.TableInfo.db_sales_approved_by + " TEXT ,"
            + DBManager.TableInfo.db_sales_app_type + " TEXT ,"
            + DBManager.TableInfo.db_sales_id + " TEXT ,"
            + DBManager.TableInfo.db_sales_poststatus + " TEXT , "
            + DBManager.TableInfo.db_sales_latitude + " TEXT ,"
            + DBManager.TableInfo.db_sales_longititude + " TEXT , "
            + DBManager.TableInfo.db_sales_address + " TEXT , "
            + DBManager.TableInfo.db_sales_landmark + " TEXT , "
            + DBManager.TableInfo.db_sales_syncstatus + " TEXT , "
            + DBManager.TableInfo.KEY_LOGIN_USER + " TEXT , "
            + DBManager.TableInfo.db_sales_distance + " TEXT , "
            + DBManager.TableInfo.db_sales_transtype + " TEXT , "
            + DBManager.TableInfo.db_sales_acvr_id + " TEXT , "
            + DBManager.TableInfo.task_name + " TEXT , "
            + DBManager.TableInfo.notification + " TEXT , "
            + DBManager.TableInfo.db_sales_app_status + " TEXT , "
            + DBManager.TableInfo.db_sales_acvrsyncstatus + " TEXT ,"
            + DBManager.TableInfo.db_sales_amount + " TEXT , "
            + DBManager.TableInfo.db_sales_app_for + " TEXT );";

    String attend = "CREATE TABLE " + DBManager.TableInfo.TABLE_SALES_ATTENDEES + "("
            + DBManager.TableInfo.KEYID + " INTEGER PRIMARY KEY,"
            + DBManager.TableInfo.db_sales_name + " TEXT,"
            + DBManager.TableInfo.db_sales_mail + " TEXT,"
            + DBManager.TableInfo.db_sales_contact + " TEXT,"
            + DBManager.TableInfo.db_sales_appointment_id + " TEXT ,"
            + DBManager.TableInfo.KEY_LOGIN_USER + " TEXT" + ")";


    String MARRIAGE = "CREATE TABLE " + DBManager.TableInfo.TABLE_MARRIAGE + " ("
            + DBManager.TableInfo.db_marriage_status + " TEXT  , "
            + DBManager.TableInfo.db_marriage_env + " TEXT  , "
            + DBManager.TableInfo.KEY_LOGIN_USER + " TEXT , "
            + DBManager.TableInfo.db_marriage_husbandname + " TEXT ,"
            + DBManager.TableInfo.db_marriage_wifename + " TEXT ,"
            + DBManager.TableInfo.db_marriage_husbandcontact + " TEXT ,"
            + DBManager.TableInfo.db_marriage_wifecontact + " TEXT ,"
            + DBManager.TableInfo.db_marriage_husbandbiometric + " TEXT ,"
            + DBManager.TableInfo.db_marriage_wifebiometric + " TEXT ,"
            + DBManager.TableInfo.db_marriage_tokenno + " TEXT ,"
            + DBManager.TableInfo.db_marriage_syncstatus + " TEXT , "
            + DBManager.TableInfo.db_marriage_acvrsyncstatus + " TEXT , "
            + DBManager.TableInfo.db_marriage_doc_id + " TEXT PRIMARY KEY " + ")";


    String marriage_app = "CREATE TABLE " + DBManager.TableInfo.TABLE_MARRIAGE_APPOINTMENT
            + " (" + DBManager.TableInfo.db_marriage_doc_id + " TEXT ,"
            + DBManager.TableInfo.db_marriage_contact_person + " TEXT ,"
            + DBManager.TableInfo.db_marriage_starttime + " TEXT ,"
            + DBManager.TableInfo.db_marriage_endtime + " TEXT,"
            + DBManager.TableInfo.db_marriage_startdate + " TEXT,"
            + DBManager.TableInfo.db_sales_appointment_id + " TEXT PRIMARY KEY,"
            + DBManager.TableInfo.db_marriage_address + " TEXT ,"
            + DBManager.TableInfo.db_marriage_landmark + " TEXT ,"
            + DBManager.TableInfo.db_marriage_app_for + " TEXT ,"
            + DBManager.TableInfo.db_marriage_executionerid + " TEXT ,"
            + DBManager.TableInfo.db_marriage_latitude + " TEXT ,"
            + DBManager.TableInfo.db_marriage_longititude + " TEXT ,"
            + DBManager.TableInfo.db_marriage_distance + " TEXT ,"
            + DBManager.TableInfo.db_marriage_transtype + " TEXT ,"
            + DBManager.TableInfo.db_marriage_acvr_amount + " TEXT ,"
            + DBManager.TableInfo.db_marriage_acvr_id + " TEXT ,"
            + DBManager.TableInfo.db_marriage_logical_del + " TEXT ,"
            + DBManager.TableInfo.db_marriage_poststatus + " TEXT , "
            + DBManager.TableInfo.db_marriage_franchise_id + " TEXT ,"
            + DBManager.TableInfo.db_marriage_co_id + " TEXT , "
            + DBManager.TableInfo.db_marriage_pay_verify + " TEXT ,"
            + DBManager.TableInfo.db_marriage_pay_pending + " TEXT ,"
            + DBManager.TableInfo.db_marriage_pay_flag + " TEXT ,"
            + DBManager.TableInfo.db_marriage_app_flag + " TEXT ,"
            + DBManager.TableInfo.db_marriage_ex_id + " TEXT , "
            + DBManager.TableInfo.db_marriage_app_type + " TEXT ,"
            + DBManager.TableInfo.db_marriage_app_status + " TEXT ,"
            + DBManager.TableInfo.notification + " TEXT , "
            + DBManager.TableInfo.db_marriage_syncstatus + " TEXT , "
            + DBManager.TableInfo.db_marriage_acvrsyncstatus + " TEXT , "
            + DBManager.TableInfo.db_marriage_created_at + " TEXT ,"
            + DBManager.TableInfo.db_marriage_status_id + " TEXT , "
            + DBManager.TableInfo.task_name + " TEXT , "
            + DBManager.TableInfo.KEY_LOGIN_USER + " TEXT , "
            + DBManager.TableInfo.db_marriage_type + " TEXT , "
            + DBManager.TableInfo.db_marriage_id + " TEXT" + ")";


    String marriageattend = "CREATE TABLE " + DBManager.TableInfo.TABLE_MARRIAGE_ATTENDEES + "("
            + DBManager.TableInfo.KEYID + " INTEGER PRIMARY KEY,"
            + DBManager.TableInfo.db_marriage_name + " TEXT,"
            + DBManager.TableInfo.db_marriage_contact + " TEXT,"
            + DBManager.TableInfo.db_marriage_mail + " TEXT,"
            + DBManager.TableInfo.db_marriage_appointment_id + " TEXT,"
            + DBManager.TableInfo.KEY_LOGIN_USER + " TEXT" + ")";

    String NOTIFICATION = "CREATE TABLE " + DBManager.TableInfo.TABLE_NOTIFICATION + " ("
            + DBManager.TableInfo.KEYID + " INTEGER PRIMARY KEY  , "
            + DBManager.TableInfo.notification_appid + " TEXT  , "
            + DBManager.TableInfo.notification_interval + " TEXT , "
            + DBManager.TableInfo.notification_repeat + " TEXT , "
            + DBManager.TableInfo.notification_starttime + " TEXT , "
            + DBManager.TableInfo.notification_type + " TEXT , "
            + DBManager.TableInfo.notification_time + " TEXT , "
            + DBManager.TableInfo.notificationvalue + " TEXT , "
            + DBManager.TableInfo.KEY_LOGIN_USER + " TEXT  " + ")";

    String witnesstable = "CREATE TABLE " + DBManager.TableInfo.TABLE_WITNESS + "("
            + DBManager.TableInfo.KEYID + " INTEGER PRIMARY KEY,"
            + DBManager.TableInfo.witness_docid + " TEXT,"
            + DBManager.TableInfo.internal_witness_userid1 + " TEXT,"
            + DBManager.TableInfo.internal_witness_email1 + " TEXT,"
            + DBManager.TableInfo.internal_witness_userid2 + " TEXT,"
            + DBManager.TableInfo.internal_witness_email2 + " TEXT,"
            + DBManager.TableInfo.internal_witness_type + " TEXT ,"
            + DBManager.TableInfo.external_witness_type + " TEXT ,"
            + DBManager.TableInfo.external_witness_email1 + " TEXT,"
            + DBManager.TableInfo.external_witness_email2 + " TEXT ,"
            + DBManager.TableInfo.internal_biometric1 + " TEXT ,"
            + DBManager.TableInfo.internal_biometric2 + " TEXT ,"
            + DBManager.TableInfo.external_biometric1 + " TEXT,"
            + DBManager.TableInfo.external_biometric2 + " TEXT ,"
            + DBManager.TableInfo.radiovalue + " TEXT ,"
            + DBManager.TableInfo.KEY_LOGIN_USER + " TEXT" + ")";

    String POSTUPDATE1 = "CREATE TABLE " + DBManager.TableInfo.POSTWITNESSDOC + "("
            + DBManager.TableInfo.KEYID + " INTEGER PRIMARY KEY,"
            + DBManager.TableInfo.postwitnessdocument + " TEXT,"
            + DBManager.TableInfo.KEY_LOGIN_USER + " TEXT" + ")";

    String MANAGER = "CREATE TABLE " + DBManager.TableInfo.TABLE_MANAGER + " ("
            + DBManager.TableInfo.KEYID + " TEXT PRIMARY KEY , "
            + DBManager.TableInfo.ReportKey + " TEXT  , "
            + DBManager.TableInfo.ReportToken + " TEXT , "
            + DBManager.TableInfo.Umail + " TEXT , "
            + DBManager.TableInfo.Uname + " TEXT , "
            + DBManager.TableInfo.Ucontact + " TEXT , "
            + DBManager.TableInfo.PropertyAddress + " TEXT , "
            + DBManager.TableInfo.OwnerName + " TEXT , "
            + DBManager.TableInfo.OwnerContact + " TEXT , "
            + DBManager.TableInfo.OwnerEmail + " TEXT , "
            + DBManager.TableInfo.OwnerAddress + " TEXT , "
            + DBManager.TableInfo.TenantName + " TEXT , "
            + DBManager.TableInfo.TenantContact + " TEXT , "
            + DBManager.TableInfo.TenantEmail + " TEXT , "
            + DBManager.TableInfo.TenantAddress + " TEXT , "
            + DBManager.TableInfo.Status + " TEXT , "
            + DBManager.TableInfo.UserEmail + " TEXT , "
            + DBManager.TableInfo.BiometricComp + " TEXT , "
            + DBManager.TableInfo.Appointment_Date + " TEXT , "
            + DBManager.TableInfo.BiometricComp1 + " TEXT , "
            + DBManager.TableInfo.Appointment_Date1 + " TEXT , "
            + DBManager.TableInfo.Reg_From_Comp + " TEXT , "
            + DBManager.TableInfo.Witness + " TEXT , "
            + DBManager.TableInfo.Ship_Address + " TEXT , "
            + DBManager.TableInfo.Ship_Diff_Address + " TEXT , "
            + DBManager.TableInfo.SYNCSTATUS + " TEXT , "
            + DBManager.TableInfo.APPTYPE + " TEXT , "
            + DBManager.TableInfo.updatelocation + " TEXT , "
            + DBManager.TableInfo.updatetime + " TEXT , "
            + DBManager.TableInfo.Doc_bio_status + " TEXT , "
            + DBManager.TableInfo.AppointmentId + " TEXT , "
            + DBManager.TableInfo.DocumentId + " TEXT " + ");";


    String manager__APP = "CREATE TABLE " + DBManager.TableInfo.TABLE_MANAGER_APPOINTMENT
            + " ("
            + DBManager.TableInfo.KEYID + " INTEGER PRIMARY KEY,"
            + DBManager.TableInfo.StartDate + " TEXT ,"
            + DBManager.TableInfo.StartTime1 + " TEXT ,"
            + DBManager.TableInfo.StartTime2 + " TEXT ,"
            + DBManager.TableInfo.DocumentId + " TEXT,"
            + DBManager.TableInfo.AppointmentAddress + " TEXT,"
            + DBManager.TableInfo.AppointmentId + " TEXT,"
            + DBManager.TableInfo.contactperson + " TEXT ,"
            + DBManager.TableInfo.landmark + " TEXT, "
            + DBManager.TableInfo.Executioner_id + " TEXT ,"
            + DBManager.TableInfo.DISTANCE + " TEXT ,"
            + DBManager.TableInfo.AMOUNT + " TEXT ,"
            + DBManager.TableInfo.TRANSPORTTYPE + " TEXT ,"
            + DBManager.TableInfo.SYNCSTATUSREPORT + " TEXT ,"
            + DBManager.TableInfo.att_status + " TEXT ,"
            + DBManager.TableInfo.post_status + " TEXT , "
            + DBManager.TableInfo.task_name + " TEXT , "
            + DBManager.TableInfo.LATITUDE + " TEXT ,"
            + DBManager.TableInfo.LONGITUDE + " TEXT , "
            + DBManager.TableInfo.notification + " TEXT , "
            + DBManager.TableInfo.AppFor + " TEXT );";


    String execlist = "CREATE TABLE " + DBManager.TableInfo.TABLE_EXECUTIONER + "("
            + DBManager.TableInfo.KEYID + " INTEGER PRIMARY KEY,"
            + DBManager.TableInfo.executioneremail + " TEXT,"
            + DBManager.TableInfo.execpassword + " TEXT,"
            + DBManager.TableInfo.KEY_LOGIN_USER + " TEXT" + ")";

    String PENDING_WITNESS = "CREATE TABLE " + DBManager.TableInfo.TABLE_PENDINGWITNESS + " ("
            + DBManager.TableInfo.KEYID + " INTEGER PRIMARY KEY,"
            + DBManager.TableInfo.db_PW_doc_id + " TEXT ,"
            + DBManager.TableInfo.db_PW_env + " TEXT ,"
            + DBManager.TableInfo.db_PW_attid + " TEXT ,"
            + DBManager.TableInfo.db_PW_partytype + " TEXT,"
            + DBManager.TableInfo.db_PW_witnessname + " TEXT,"
            + DBManager.TableInfo.db_PW_biometric + " TEXT,"
            + DBManager.TableInfo.db_PW_email + " TEXT ,"
            + DBManager.TableInfo.db_PW_contact + " TEXT, "
            + DBManager.TableInfo.db_PW_name + " TEXT ,"
            + DBManager.TableInfo.db_PW_ocontact + " TEXT ,"
            + DBManager.TableInfo.db_PW_oemail + " TEXT ,"
            + DBManager.TableInfo.db_PW_oname + " TEXT ,"
            + DBManager.TableInfo.db_PW_oaddress + " TEXT ,"
            + DBManager.TableInfo.db_PW_tcontact + " TEXT ,"
            + DBManager.TableInfo.db_PW_temail + " TEXT , "
            + DBManager.TableInfo.db_PW_tname + " TEXT , "
            + DBManager.TableInfo.db_PW_taddress + " TEXT ,"
            + DBManager.TableInfo.db_PW_Paddress + " TEXT , "
            + DBManager.TableInfo.db_PW_from_dt + " TEXT , "
            + DBManager.TableInfo.db_PW_rent + " TEXT ,"
            + DBManager.TableInfo.db_PW_deposit + " TEXT , "
            + DBManager.TableInfo.db_PW_refunddeposit + " TEXT , "
            + DBManager.TableInfo.db_PW_city_name + " TEXT ,"
            + DBManager.TableInfo.db_PW_status_id + " TEXT , "
            + DBManager.TableInfo.db_PW_token_no + " TEXT , "
            + DBManager.TableInfo.db_PW_status + " TEXT , "
            + DBManager.TableInfo.db_PW_agreement_cancle + " TEXT , "
            + DBManager.TableInfo.db_PW_id + " TEXT ,"
            + DBManager.TableInfo.KEY_LOGIN_USER + " TEXT ,"
            + DBManager.TableInfo.db_PW_to_dt + " TEXT );";

    String today_party = "CREATE TABLE " + DBManager.TableInfo.TABLE_TODAY_PARTIES + "("

            + DBManager.TableInfo.biometric + " TEXT,"
            + DBManager.TableInfo.docuuu + " TEXT" + ")";


    String older_party = "CREATE TABLE " + DBManager.TableInfo.TABLE_OLDER_PARTIES + "("

            + DBManager.TableInfo.biometric + " TEXT,"
            + DBManager.TableInfo.docuuu + " TEXT" + ")";


    String new_party = "CREATE TABLE " + DBManager.TableInfo.TABLE_NEW_PARTIES + "("

            + DBManager.TableInfo.biometric + " TEXT,"
            + DBManager.TableInfo.docuuu + " TEXT" + ")";


    String completed_party = "CREATE TABLE " + DBManager.TableInfo.TABLE_COMPLETED_PARTIES + "("

            + DBManager.TableInfo.biometric + " TEXT,"
            + DBManager.TableInfo.docuuu + " TEXT" + ")";


    String POSTTASK2 = "CREATE TABLE " + DBManager.TableInfo.TABLE_MARRIAGE_SINGLE_BIOMETRIC + "("
            + DBManager.TableInfo.db_marriage_doc_id + " TEXT,"
            + DBManager.TableInfo.KEYID + " INTEGER PRIMARY KEY,"
            + DBManager.TableInfo.db_marriage_appointment_id + " TEXT,"
            + DBManager.TableInfo.db_marriage_husbandbiometric + " TEXT,"
            + DBManager.TableInfo.db_marriage_wifebiometric + " TEXT,"
            + DBManager.TableInfo.KEY_LOGIN_USER + " TEXT " + ")";

    String PARTIES1 = "CREATE TABLE " + DBManager.TableInfo.PARTIES + "("
            + DBManager.TableInfo.KEYID + " INTEGER PRIMARY KEY,"
            + DBManager.TableInfo.DOCUMENT + " TEXT,"
            + DBManager.TableInfo.ATTENDANCE + " TEXT,"
            + DBManager.TableInfo.NAMENEW + " TEXT,"
            + DBManager.TableInfo.EMAILNEW + " TEXT,"
            + DBManager.TableInfo.PARTYTYPE + " TEXT,"
            + DBManager.TableInfo.POA + " TEXT,"
            + DBManager.TableInfo.CONTACT + " TEXT,"
            + DBManager.TableInfo.BIOMETRIC + " TEXT,"
            + DBManager.TableInfo.StartDate + " TEXT ,"
            + DBManager.TableInfo.PARTY_ADDRESS + " TEXT ,"
            + DBManager.TableInfo.KEY_LOGIN_USER + " TEXT" + ")";

    String UPDATE_PENDING_WITNESS = "CREATE TABLE " + TABLE_UPDATE_PENDING_WITNESS + "("
            + DBManager.TableInfo.KEYID + " INTEGER PRIMARY KEY,"
            + DBManager.TableInfo.execemail + " TEXT,"
            + DBManager.TableInfo.pw_docid + " TEXT,"
            + DBManager.TableInfo.pw_attid + " TEXT,"
            + DBManager.TableInfo.pw_email + " TEXT,"
            + DBManager.TableInfo.pw_partytype + " TEXT,"
            + DBManager.TableInfo.pw_biometric + " TEXT" + ")";
    String penalty = "CREATE TABLE " + DBManager.TableInfo.TABLE_PENALTY + "("
            + DBManager.TableInfo.KEYID + " INTEGER PRIMARY KEY,"
            + DBManager.TableInfo.db_penalty_id + " TEXT,"
            + DBManager.TableInfo.db_penalty_appointment_id + " TEXT,"
            + DBManager.TableInfo.db_penalty_doc_id + " TEXT,"
            + DBManager.TableInfo.db_penalty_exec_id + " TEXT,"
            + DBManager.TableInfo.db_penalty_amount + " TEXT,"
            + DBManager.TableInfo.db_penalty_verify + " TEXT,"
            + DBManager.TableInfo.db_penalty_system_reason + " TEXT,"
            + DBManager.TableInfo.db_penalty_manager_comment + " TEXT,"
            + DBManager.TableInfo.db_penalty_created_at + " TEXT ,"
            + DBManager.TableInfo.db_penalty_env + " TEXT ,"
            + DBManager.TableInfo.db_penalty_type + " TEXT ,"
            + DBManager.TableInfo.KEY_LOGIN_USER + " TEXT" + ")";

    String PAYMENT_DETAILS_LINK = "CREATE TABLE " + TABLE_PAYMENT_LINK + "("
            + DBManager.TableInfo.KEYID + " INTEGER PRIMARY KEY,"
            + DBManager.TableInfo.exec_email_payment + " TEXT,"
            + DBManager.TableInfo.docid_payment + " TEXT,"
            + DBManager.TableInfo.KEY_LOGIN_USER + " TEXT)";




    public DBOperation(Context context) {
        super(context, DBManager.TableInfo.DATABASE_NAME, null, database_version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

        db.execSQL(CREATE_QUERY1);
        db.execSQL(CREATE_QUERY_APP);
        db.execSQL(CREATE_QUERY3);
        db.execSQL(PAYMENT_DETAILS);
        db.execSQL(sqlLocationDetails);
        db.execSQL(CREATE_CHECKK_TABLE);
        db.execSQL(CREATE_LAST_UPDATE_TABLE);
        db.execSQL(POSTUPDATE);
        db.execSQL(REM_PAYMENT);
        db.execSQL(PARTYUPDATE1);
        db.execSQL(attendees2);
        db.execSQL(attendees1);
        db.execSQL(POSTTASK);
        db.execSQL(NOTIFICATION);
        db.execSQL(CREATE_QUERY5);
        db.execSQL(RESCHEDULE);
        db.execSQL(APPOINTMENT);
        db.execSQL(APPOINTMENTBOOKING);
        db.execSQL(multiwork);
        db.execSQL(attendees);
        db.execSQL(WEEKENDOFF);
        db.execSQL(ACTUAL_TIME);
        db.execSQL(SALES_APP);
        db.execSQL(TASK);
        db.execSQL(SALES);
        db.execSQL(CALL);
        db.execSQL(ATT);
        db.execSQL(MARRIAGE);
        db.execSQL(marriage_app);
        db.execSQL(attend);
        db.execSQL(marriageattend);
        db.execSQL(witnesstable);
        db.execSQL(POSTUPDATE1);
        db.execSQL(MANAGER);
        db.execSQL(manager__APP);
        db.execSQL(execlist);
        db.execSQL(PENDING_WITNESS);
        db.execSQL(DOCUMENTT3);
        db.execSQL(today_party);
        db.execSQL(older_party);
        db.execSQL(new_party);
        db.execSQL(completed_party);
        db.execSQL(POSTTASK2);
        db.execSQL(PARTIES1);
        db.execSQL(UPDATE_PENDING_WITNESS);
        db.execSQL(penalty);
        db.execSQL(PAYMENT_DETAILS_LINK);
        db.execSQL(UPDATE_PAYMENT_DETAILS);


    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

//        String ALTER_DOCUMENT_LATITUDE = "ALTER TABLE "
        //


//        String ALTER_ACVRREPORT_SYNCSTtt                                                                                                                                                                                                                                                                                                                                      ATUS = "ALTER TABLE "
//                + DBManager.TableInfo.TABLE_NAME + " ADD COLUMN " + DBManager.TableInfo.SYNCSTATUSREPORT + " TEXT";

//        String ALTER_DOCUMENT_APPTYPE = "ALTER TABLE "
//                + DBManager.TableInfo.TABLE_NAME2 + " ADD COLUMN " ;
//
//        System.out.println("Added successfully");
//
//
//String Call_time1 = "ALTER TABLE "
//                + DBManager.TableInfo.TABLE_NAME_DOCUMENT + " ADD COLUMN " + DBManager.TableInfo.s_grn + " TEXT";
//            db.execSQL(Call_time1);
//
//            String Call_time2= "ALTER TABLE "
//                    + DBManager.TableInfo.TABLE_NAME_DOCUMENT + " ADD COLUMN " + DBManager.TableInfo.r_grn + " TEXT";
//            db.execSQL(Call_time2);
//
//            System.out.println("added");
//


//
//        db.execSQL(ALTER_email);
//        System.out.println("ALTER_email Created Successfully");
//
//
//
//
//
//
//        db.execSQL(ALTER_appoint);
//        System.out.println("Witness Created Successfully");
//
//        String ALTER_appoint1 = "ALTER TABLE "
//                + DBManager.TableInfo.TABLE_TASK + " ADD COLUMN " +DBManager.TableInfo.COMPLAINT + " TEXT";
//
//        db.execSQL(ALTER_appoint1);

//        String ALTER_task1 = "ALTER TABLE "
//                + DBManager.TableInfo.REASSIGN + " ADD COLUMN " +DBManager.TableInfo.status1 + " TEXT";
//        db.execSQL(ALTER_task1);
//        System.out.println(" Created2 Successfully");
//        String ALTER_task2 = "ALTER TABLE "
//                + DBManager.TableInfo.NOT_APPLICABLE + " ADD COLUMN " +DBManager.TableInfo.status1 + " TEXT";
//        db.execSQL(ALTER_task2);
//

//        String ALTER_task = "ALTER TABLE "
//                + DBManager.TableInfo.TABLE_TASK + " ADD COLUMN " ;
//        db.execSQL(ALTER_task);
//        System.out.println(" Created1 Successfully");
//        String ALTER_email1 = "ALTER TABLE " + DBManager.TableInfo.TABLE_NAME + " ADD COLUMN "  + " TEXT";
//        db.execSQL(ALTER_email1);
//        System.out.println("ALTER_email1 Created Successfully");
//        System.out.println("added");
//
//        String ALTER_appoint1 = "ALTER TABLE "
//                + DBManager.TableInfo.APPOINTMENTBOOKING + " ADD COLUMN " +DBManager.TableInfo.AppointmentId + " TEXT";
//
//        String ALTER_FREE = "ALTER TABLE "
//                + DBManager.TableInfo.TABLE_NAME + " ADD COLUMN " ;
// String ALTER_REASON = "ALTER TABLE "
//        + DBManager.TableInfo.TABLE_NAME + " ADD COLUMN " ;
////S
//
//        db.execSQL(ALTER_FREE);
//        String landmark1 = "ALTER TABLE " + DBManager.TableInfo.TABLE_NAME + " ADD COLUMN " + DBManager.TableInfo.post_status + " TEXT";
//        db.execSQL(landmark1);
//        System.out.println("Added  successfully");
//       // System.out.println("Att added successfully");
//        System.out.println("Call Created Successfully");

//         String ALTER_appoint = "ALTER TABLE "
//        + DBManager.TableInfo.TABLE_NAME + " ADD COLUMN " +DBManager.TableInfo.LATITUDE + "TEXT" ;
//
//
//
//
//
//
//
//
//
//// String Call_time1 = "ALTER TABLE "
//        + DBManager.TableInfo.TABLE_SALES_APPOINTMENT + " ADD COLUMN " ;
//        db.execSQL(Call_time1);
//
//        String Call_time3 = "ALTER TABLE "
//                + DBManager.TableInfo.TABLE_SALES_APPOINTMENT + " ADD COLUMN " ;
//        db.execSQL(Call_time3);
//
//        String Call_time4 = "ALTER TABLE "
//                + DBManager.TableInfo.TABLE_SALES + " ADD COLUMN "
//        db.execSQL(Call_time4);
// String Call_time2 = "ALTER TABLE "
//        + DBManager.TableInfo.TABLE_SALES_ATTENDEES + " ADD COLUMN ";
//        db.execSQL(Call_time2);
//
//        String Call_time3 = "ALTER TABLE "
//                + DBManager.TableInfo.TABLE_MARRIAGE_APPOINTMENT + " ADD COLUMN "
//        db.execSQL(Call_time3);
// String Call_time2 = "ALTER TABLE "
//        + DBManager.TableInfo.TABLE_SALES_APPOINTMENT + " ADD COLUMN " + DBManager.TableInfo.db_sales_distance + " TEXT";
//        db.execSQL(Call_time2);
//        String Call_time3 = "ALTER TABLE "
//                + DBManager.TableInfo.TABLE_SALES_APPOINTMENT + " ADD COLUMN " ;
//        db.execSQL(Call_time3);
//        String Call_time4 = "ALTER TABLE "
//                + DBManager.TableInfo.TABLE_SALES_APPOINTMENT + " ADD COLUMN "
//        db.execSQL(Call_time4);
//        String Call_time5 = "ALTER TABLE "
//                + DBManager.TableInfo.TABLE_SALES_APPOINTMENT + " ADD COLUMN " + DBManager.TableInfo.db_sales_amount + " TEXT";
//        db.execSQL(Call_time5);
//        System.out.println("Parties table  Created Successfully");
// String ALTER_USER_TABLE1 = "ALTER TABLE "
//        + DBManager.TableInfo.TABLE_SALES + " ADD COLUMN " +DBManager.TableInfo.db_sales_acvrsyncstatus + " TEXT";
//        db.execSQL(ALTER_USER_TABLE1);
//
//
////  String Alter_apptable = "ALTER TABLE "
//        + DBManager.TableInfo.TABLE_NAME_APPOINTMENT + " ADD COLUMN " + DBManager.TableInfo.App_status + " TEXT";
//        db.execSQL(Alter_apptable);
//        String Alter_salesapptable = "ALTER TABLE "
//                + DBManager.TableInfo.TABLE_SALES_APPOINTMENT + " ADD COLUMN " + DBManager.TableInfo.db_sales_app_status + " TEXT";
//        db.execSQL(Alter_salesapptable);
//        String ALTER_marriageapptype = "ALTER TABLE "
//                + DBManager.TableInfo.TABLE_MARRIAGE_APPOINTMENT + " ADD COLUMN " + DBManager.TableInfo.db_marriage_app_status + " TEXT";
//        db.execSQL(ALTER_marriageapptype);
//        System.out.println("Added1 successfully");
//
//
//
//
//
//
//  String ALTER_USER_TABLE6 = "ALTER TABLE "
//        + DBManager.TableInfo.TABLE_NAME3 + " ADD COLUMN " ;
//        db.execSQL(ALTER_USER_TABLE6);
//
//
//
//
//
//
//
//        String ALTER_USER_TABLE11 = "ALTER TABLE "
//                + DBManager.TableInfo.TABLE_NAME3 + " ADD COLUMN "
//        db.execSQL(ALTER_USER_TABLE11);

//        String ALTER_USER_TABLE5 = "ALTER TABLE "
//                + DBManager.TableInfo.TABLE_NOTIFICATION + " ADD COLUMN " ;
//        db.execSQL(ALTER_USER_TABLE5);
//        String ALTER_USER_TABLE6 = "ALTER TABLE "
//                + DBManager.TableInfo.TABLE_NAME3 + " ADD COLUMN " +DBManager.TableInfo.Comments_area + " TEXT";
//        db.execSQL(ALTER_USER_TABLE6);
//
//
//
//   String ALTER_USER_TABLE10 = "ALTER TABLE "
//        + DBManager.TableInfo.PARTIES1 + " ADD COLUMN ";
//        db.execSQL(ALTER_USER_TABLE10);
//
//// String ALTER_USER_TABLE7 = "ALTER TABLE "
//        + DBManager.TableInfo.TABLE_SALES_APPOINTMENT + " ADD COLUMN " +DBManager.TableInfo.notification + " TEXT";
//        db.execSQL(ALTER_USER_TABLE7);
//
//
//
//
////  String ALTER_USER_TABLE2 = "ALTER TABLE "
//        String ALTER_USER_TABLE2 = "ALTER TABLE "
//                + DBManager.TableInfo.TABLE_WITNESS + " ADD COLUMN " +DBManager.TableInfo.internal_witness_type + " TEXT";
//
//        db.execSQL(ALTER_USER_TABLE2);
//
//  String ALTER_USER_TABLE4 = "ALTER TABLE "
//        + DBManager.TableInfo.TABLE_WITNESS + " ADD COLUMN " +DBManager.TableInfo.external_witness_email1 + " TEXT";
//        db.execSQL(ALTER_USER_TABLE4);
//
//        String ALTER_USER_TABLE5 = "ALTER TABLE "
//                + DBManager.TableInfo.TABLE_WITNESS + " ADD COLUMN " +DBManager.TableInfo.external_witness_email2 + " TEXT";
//        db.execSQL(ALTER_USER_TABLE5);
//        System.out.println("Added2 successfully");
//
//////  String ALTER_email = "ALTER TABLE "
//        + DBManager.TableInfo.TABLE_NOTIFICATION + " ADD COLUMN " +DBManager.TableInfo.notification_time + " TEXT";
//        db.execSQL(ALTER_email);
////
//        String ALTER_USER_TABLE = "ALTER TABLE "
//                + DBManager.TableInfo.PARTIES1 + " ADD COLUMN " +DBManager.TableInfo.PARTY_ADDRESS + " TEXT";
//
//
////
//
//        String ALTER_USER_TABLE4 = "ALTER TABLE "
//                + DBManager.TableInfo.UPDATEPARTY + " ADD COLUMN " +DBManager.TableInfo.external_witness1+ " TEXT";
//
//        db.execSQL(ALTER_USER_TABLE4);
//
//
//        String ALTER_USER_TABLE5 = "ALTER TABLE "
//                + DBManager.TableInfo.UPDATEPARTY + " ADD COLUMN " +DBManager.TableInfo.external_witness2+ " TEXT";
//
//        db.execSQL(ALTER_USER_TABLE5);
//
//  String ALTER_USER_TABLE1 = "ALTER TABLE "
//        + DBManager.TableInfo.TABLE_MARRIAGE + " ADD COLUMN " + DBManager.TableInfo.db_marriage_husbandname + " TEXT";
//        db.execSQL(ALTER_USER_TABLE1);
//
//        System.out.println("Added1 successfully");
//
//        String ALTER_USER_TABLE2 = "ALTER TABLE "
//                + DBManager.TableInfo.TABLE_MARRIAGE + " ADD COLUMN " + DBManager.TableInfo.db_marriage_husbandbiometric + " TEXT";
//        db.execSQL(ALTER_USER_TABLE2);
//
//        System.out.println("Added1 successfully");
//
//        String ALTER_USER_TABLE3 = "ALTER TABLE "
//                + DBManager.TableInfo.TABLE_MARRIAGE + " ADD COLUMN " + DBManager.TableInfo.db_marriage_husbandcontact + " TEXT";
//
//        db.execSQL(ALTER_USER_TABLE3);
//     String ALTER_contact12 = "ALTER TABLE "
//        + DBManager.TableInfo.TABLE_SALES_APPOINTMENT + " ADD COLUMN " + DBManager.TableInfo.db_sales_startdate + " TEXT ";
//
//        db.execSQL(ALTER_contact12);
//
//
//
//        String ALTER_contact1 = "ALTER TABLE "
//                + DBManager.TableInfo.TABLE_MARRIAGE_APPOINTMENT + " ADD COLUMN "+ DBManager.TableInfo.db_marriage_startdate + " TEXT ";
//
//        db.execSQL(ALTER_contact1);
//        System.out.println("adhoc Created Successfully");
//
//        String ALTER_USER_TABLE4 = "ALTER TABLE "
//                + DBManager.TableInfo.TABLE_MARRIAGE + " ADD COLUMN " + DBManager.TableInfo.db_marriage_wifename + " TEXT";
//        db.execSQL(ALTER_USER_TABLE4);
//
//        System.out.println("Added1 successfully");
//
//        String ALTER_appoint3 = "ALTER TABLE "
//                + DBManager.TableInfo.TABLE_MARRIAGE + " ADD COLUMN " + DBManager.TableInfo.db_marriage_wifebiometric + " TEXT";
//        db.execSQL(ALTER_appoint3);
//
//        String ALTER_appoint4 = "ALTER TABLE "
//                + DBManager.TableInfo.TABLE_MARRIAGE + " ADD COLUMN " + DBManager.TableInfo.db_marriage_wifecontact + " TEXT";
//        db.execSQL(ALTER_appoint4);
//
//        String ALTER_appoint5 = "ALTER TABLE "
//                + DBManager.TableInfo.TABLE_MARRIAGE + " ADD COLUMN " + DBManager.TableInfo.db_marriage_tokenno + " TEXT";
//        db.execSQL(ALTER_appoint5);
//String ALTER_USER_TABLE9 = "ALTER TABLE "
//        + DBManager.TableInfo.UPDATEPARTY + " ADD COLUMN " +DBManager.TableInfo.biotype + " TEXT";
//
//        String ALTER_appoint2 = "ALTER TABLE "
//                + DBManager.TableInfo.TABLE_TASK + " ADD COLUMN " +DBManager.TableInfo.db_PW_env + " TEXT";
//        db.execSQL(ALTER_appoint2);
//
// String ALTER_USER_TABLE6 = "ALTER TABLE "
//        + DBManager.TableInfo.POST_TASK + " ADD COLUMN " +DBManager.TableInfo.comm1 + " TEXT";
//        db.execSQL(ALTER_USER_TABLE6);
//        String ALTER_USER_TABLE7 = "ALTER TABLE "
//                + DBManager.TableInfo.REASSIGN + " ADD COLUMN "
//        db.execSQL(ALTER_USER_TABLE7);
//        String ALTER_USER_TABLE8 = "ALTER TABLE "
//                + DBManager.TableInfo.NOT_APPLICABLE + " ADD COLUMN "
//        db.execSQL(ALTER_USER_TABLE8);
// String ALTER_USER_TABLE3 = "ALTER TABLE "
//        + DBManager.TableInfo.TABLE_WITNESS + " ADD COLUMN " + DBManager.TableInfo.radiovalue + " TEXT ";
//        db.execSQL(ALTER_USER_TABLE3);
// String ALTER_USER_TABLE8 = "ALTER TABLE "
//        + DBManager.TableInfo.MULTIWORK + " ADD COLUMN " +DBManager.TableInfo.reach_time + " TEXT";
//        db.execSQL(ALTER_USER_TABLE8);
//
//        String ALTER_USER_TABLE9 = "ALTER TABLE "
//                + DBManager.TableInfo.MULTIWORK + " ADD COLUMN " +DBManager.TableInfo.updatelocation + " TEXT";
//        db.execSQL(ALTER_USER_TABLE9);
//
//        String ALTER_USER_TABLE10 = "ALTER TABLE "
//                + DBManager.TableInfo.MULTIWORK + " ADD COLUMN " +DBManager.TableInfo.updatetime + " TEXT";
//        db.execSQL(ALTER_USER_TABLE10);
//
//
//        String ALTER_appoint3 = "ALTER TABLE "
//                + DBManager.TableInfo.TABLE_MARRIAGE_APPOINTMENT + " ADD COLUMN " + DBManager.TableInfo.db_marriage_type + " TEXT";
//
//
//
//        if (6 >= oldVersion) {
//
//            db.execSQL(ALTER_DOCUMENT_LONGITUDE);
//            db.execSQL(ALTER_DOCUMENT_SYNCSTATUS);
//            db.execSQL(ALTER_COMMENT_SYNCSTATUS);
//            db.execSQL(ALTER_ACVRREPORT_SYNCSTATUS);
//////////////        }
//        if (newVersion > oldVersion) {
//
//
//        }
    }

    public void InsertRecord(DBOperation dop, String rtoken, String rkey, String username, String usermail,
                             String usercontact, String propertyadd, String oname, String ocontact, String omail, String oaddress,
                             String tname, String tcontact, String tmail, String taddress, String status, String umail1, String docid,
                             String appdate, String biocomp, String appdate1, String biocomp1, String regfromcomp, String witness,
                             String shipadd, String shdiffadd, String syncstatus, String apptype, String biostatus,String sgrn,String rgrn) {

        SQLiteDatabase sqlite = dop.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBManager.TableInfo.ReportToken, rtoken);
        cv.put(DBManager.TableInfo.ReportKey, rkey);
        cv.put(DBManager.TableInfo.Uname, username);
        cv.put(DBManager.TableInfo.Umail, usermail);
        cv.put(DBManager.TableInfo.Ucontact, usercontact);
        cv.put(DBManager.TableInfo.PropertyAddress, propertyadd);
        cv.put(DBManager.TableInfo.OwnerName, oname);
        cv.put(DBManager.TableInfo.OwnerContact, ocontact);
        cv.put(DBManager.TableInfo.OwnerEmail, omail);
        cv.put(DBManager.TableInfo.OwnerAddress, oaddress);
        cv.put(DBManager.TableInfo.TenantName, tname);
        cv.put(DBManager.TableInfo.TenantContact, tcontact);
        cv.put(DBManager.TableInfo.TenantEmail, tmail);
        cv.put(DBManager.TableInfo.TenantAddress, taddress);
        cv.put(DBManager.TableInfo.Status, status);
        cv.put(DBManager.TableInfo.UserEmail, umail1);
        cv.put(DBManager.TableInfo.DocumentId, docid);
        cv.put(DBManager.TableInfo.Appointment_Date, appdate);
        cv.put(DBManager.TableInfo.BiometricComp, biocomp);
        cv.put(DBManager.TableInfo.Appointment_Date1, appdate1);
        cv.put(DBManager.TableInfo.BiometricComp1, biocomp1);
        cv.put(DBManager.TableInfo.Reg_From_Comp, regfromcomp);
        cv.put(DBManager.TableInfo.Witness, witness);
        cv.put(DBManager.TableInfo.Ship_Address, shipadd);
        cv.put(DBManager.TableInfo.Ship_Diff_Address, shdiffadd);
        cv.put(DBManager.TableInfo.SYNCSTATUS, syncstatus);
        cv.put(DBManager.TableInfo.APPTYPE, apptype);
        cv.put(DBManager.TableInfo.Doc_bio_status, biostatus);
        cv.put(DBManager.TableInfo.s_grn, sgrn);
        cv.put(DBManager.TableInfo.r_grn, rgrn);
        long res = sqlite.insert(DBManager.TableInfo.TABLE_NAME_DOCUMENT, null, cv);

    }

    public void Insertbioattendees(DBOperation db, String ID1, String name, String email, String contact, String appid, String username2) {

        SQLiteDatabase db13 = db.getWritableDatabase();
        ContentValues values3 = new ContentValues();
        values3.put(DBManager.TableInfo.KEYID, ID1);
        values3.put(DBManager.TableInfo.nameattend, name);
        values3.put(DBManager.TableInfo.emailattend, email);
        values3.put(DBManager.TableInfo.contactattend, contact);
        values3.put(DBManager.TableInfo.appointattend, appid);
        values3.put(DBManager.TableInfo.KEY_LOGIN_USER, username2);
        String condition3 = DBManager.TableInfo.nameattend + " =?";
        Cursor cursor3 = db13.query(ATTENDEES, null, condition3, new String[]{name}, null, null, null);
        long status3 = db13.insert(ATTENDEES, null, values3);

        cursor3.close();






    }

    public void Insertbiopayment(DBOperation db, String ID1, String DocumentId, String ReportKey, String Amt, String username2) {


        SQLiteDatabase db3 = db.getWritableDatabase();
        ContentValues values3 = new ContentValues();
        values3.put(DBManager.TableInfo.KEYID, ID1);
        values3.put(DBManager.TableInfo.doc1, DocumentId);
        values3.put(DBManager.TableInfo.rep1, ReportKey);
        values3.put(DBManager.TableInfo.payamount, Amt);
        values3.put(DBManager.TableInfo.KEY_LOGIN_USER, username2);
        String condition3 = DBManager.TableInfo.doc1 + " =?";
        Cursor cursor3 = db3.query(PAYMENT, null, condition3, new String[]{DocumentId}, null, null, null);
        long status3 = db3.insert(PAYMENT, null, values3);

cursor3.close();

    }


    public void Insertbioparties(DBOperation db, String ID1, String Document1, String Attendance, String name, String startnewdate, String email,
                                 String partytype, String poa, String contact, String biometric, String address, String username2) {

        SQLiteDatabase db4 = db.getWritableDatabase();
        ContentValues values4 = new ContentValues();
        values4.put(DBManager.TableInfo.KEYID, ID1);
        values4.put(DBManager.TableInfo.DOCUMENT, Document1);
        values4.put(DBManager.TableInfo.ATTENDANCE, Attendance);
        values4.put(DBManager.TableInfo.NAMENEW, name);
        values4.put(DBManager.TableInfo.StartDate, startnewdate);
        values4.put(DBManager.TableInfo.EMAILNEW, email);
        values4.put(DBManager.TableInfo.PARTYTYPE, partytype);
        values4.put(DBManager.TableInfo.POA, poa);
        values4.put(DBManager.TableInfo.CONTACT, contact);
        values4.put(DBManager.TableInfo.BIOMETRIC, biometric);
        values4.put(DBManager.TableInfo.PARTY_ADDRESS, address);
        values4.put(DBManager.TableInfo.KEY_LOGIN_USER, username2);

        String condition4 = DBManager.TableInfo.ATTENDANCE + " =?";
        Cursor cursor4 = db4.query(DBManager.TableInfo.PARTIES, null, condition4, new String[]{Attendance}, null, null, null);
        long status4 = db4.insert(DBManager.TableInfo.PARTIES, null, values4);
        cursor4.close();

    }

    public void InsertPenaltyreports(DBOperation db, String ID1, String penaltyid,String penaly_appid,String penalty_docid, String penalty_execid,String penalty_amount,String penalty_verify,String penalty_type,String penalty_managercomment,String penalty_systemreason,String penalty_createdat,String penalty_env,String execemail) {

        SQLiteDatabase db4 = db.getWritableDatabase();
        ContentValues values4 = new ContentValues();
        values4.put(DBManager.TableInfo.KEYID, ID1);
        values4.put(DBManager.TableInfo.db_penalty_id, penaltyid);
        values4.put(DBManager.TableInfo.db_penalty_appointment_id, penaly_appid);
        values4.put(DBManager.TableInfo.db_penalty_doc_id, penalty_docid);
        values4.put(DBManager.TableInfo.db_penalty_exec_id, penalty_execid);
        values4.put(DBManager.TableInfo.db_penalty_amount, penalty_amount);
        values4.put(DBManager.TableInfo.db_penalty_verify, penalty_verify);
        values4.put(DBManager.TableInfo.db_penalty_type, penalty_type);
        values4.put(DBManager.TableInfo.db_penalty_manager_comment, penalty_managercomment);
        values4.put(DBManager.TableInfo.db_penalty_system_reason, penalty_systemreason);
        values4.put(DBManager.TableInfo.db_penalty_created_at, penalty_createdat);
        values4.put(DBManager.TableInfo.db_penalty_env, penalty_env);
        values4.put(DBManager.TableInfo.KEY_LOGIN_USER, execemail);
        String condition4 = DBManager.TableInfo.db_penalty_doc_id + " =?";
        Cursor cursor4 = db4.query(DBManager.TableInfo.TABLE_PENALTY, null, condition4, new String[]{penalty_docid}, null, null, null);
        long status4 = db4.insert(DBManager.TableInfo.TABLE_PENALTY, null, values4);
        cursor4.close();

        System.out.println("penalty insert:"+status4+penalty_docid);

    }

    public void InsertsalesattendeesRecord(DBOperation db, String ID1, String sales_name, String sales_email,
                                           String sales_contact, String sales_appointment_id, String username2) {

        SQLiteDatabase salesattend = db.getWritableDatabase();
        ContentValues values3 = new ContentValues();
        values3.put(DBManager.TableInfo.KEYID, ID1);
        values3.put(DBManager.TableInfo.db_sales_name, sales_name);
        values3.put(DBManager.TableInfo.db_sales_mail, sales_email);
        values3.put(DBManager.TableInfo.db_sales_contact, sales_contact);
        values3.put(DBManager.TableInfo.db_sales_appointment_id, sales_appointment_id);
        values3.put(DBManager.TableInfo.KEY_LOGIN_USER, username2);
        String condition3 = DBManager.TableInfo.db_sales_name + " =?";
        Cursor cursoratted = salesattend.query(TABLE_SALES_ATTENDEES, null, condition3, new String[]{sales_name}, null, null, null);
        long status3 = salesattend.insert(TABLE_SALES_ATTENDEES, null, values3);
        cursoratted.close();
    }

    public void InsertsalesappointmentRecord(DBOperation db, String sales_appointment_id, String sales_contactperson, String startnewtime1,
                                             String startnewtime2, String startnewdate, String sales_executioner_id, String sales_app_for, String sales_document_id
            , String sales_created, String sales_app_ref, String sales_app_city, String sales_u_flag, String sales_create_dt, String sales_approved_by,
                                             String sales_created_by, String sales_latitude, String sales_longititude, String sales_logical_del
            , String sales_poststatus, String sales_apptype, String sales_id, String username2, String sales_address, String sales_landmark, String sales_acvr_id, String sales_distance, String sales_trans_type, String sales_amount, String sync, String sales_acvrreport, String notification, String sales_app_status) {

        SQLiteDatabase salesappoint = db.getWritableDatabase();
        ContentValues values3 = new ContentValues();
        values3.put(DBManager.TableInfo.db_sales_appointment_id, sales_appointment_id);
        values3.put(DBManager.TableInfo.db_sales_contactperson, sales_contactperson);
        values3.put(DBManager.TableInfo.db_sales_starttime, startnewtime1);
        values3.put(DBManager.TableInfo.db_sales_endtime, startnewtime2);
        values3.put(DBManager.TableInfo.db_sales_startdate, startnewdate);
        values3.put(DBManager.TableInfo.db_sales_executioner_id, sales_executioner_id);
        values3.put(DBManager.TableInfo.db_sales_app_for, sales_app_for);
        values3.put(DBManager.TableInfo.db_sales_doc_id, sales_document_id);
        values3.put(DBManager.TableInfo.db_sales_created_at, sales_created);
        values3.put(DBManager.TableInfo.db_sales_app_ref, sales_app_ref);
        values3.put(DBManager.TableInfo.db_sales_app_city, sales_app_city);
        values3.put(DBManager.TableInfo.db_sales_u_flag, sales_u_flag);
        values3.put(DBManager.TableInfo.db_sales_create_dt, sales_create_dt);
        values3.put(DBManager.TableInfo.db_sales_approved_by, sales_approved_by);
        values3.put(DBManager.TableInfo.db_sales_createdby, sales_created_by);
        values3.put(DBManager.TableInfo.db_sales_latitude, sales_latitude);
        values3.put(DBManager.TableInfo.db_sales_longititude, sales_longititude);
        values3.put(DBManager.TableInfo.db_sales_logical_del, sales_logical_del);
        values3.put(DBManager.TableInfo.db_sales_poststatus, sales_poststatus);
        values3.put(DBManager.TableInfo.db_sales_app_type, sales_apptype);
        values3.put(DBManager.TableInfo.db_sales_id, sales_id);
        values3.put(DBManager.TableInfo.KEY_LOGIN_USER, username2);
        values3.put(DBManager.TableInfo.db_sales_address, sales_address);
        values3.put(DBManager.TableInfo.db_sales_landmark, sales_landmark);
        values3.put(DBManager.TableInfo.db_sales_acvr_id, sales_acvr_id);
        values3.put(DBManager.TableInfo.db_sales_distance, sales_distance);
        values3.put(DBManager.TableInfo.db_sales_transtype, sales_trans_type);
        values3.put(DBManager.TableInfo.db_sales_amount, sales_amount);
        values3.put(DBManager.TableInfo.db_sales_syncstatus, sync);
        values3.put(DBManager.TableInfo.db_sales_acvrsyncstatus, sales_acvrreport);
        values3.put(DBManager.TableInfo.notification, notification);
        values3.put(DBManager.TableInfo.db_sales_app_status, sales_app_status);

        String condition3 = DBManager.TableInfo.db_sales_appointment_id + " =?";
        Cursor cursorappoint = salesappoint.query(TABLE_SALES_APPOINTMENT, null, condition3, new String[]{sales_appointment_id}, null, null, null);
        long status3 = salesappoint.insert(TABLE_SALES_APPOINTMENT, null, values3);
 cursorappoint.close();


    }

    public void updatesalesappointment(DBOperation db, String sales_appointment_id, String sales_contactperson, String startnewtime1,
                                       String startnewtime2, String startnewdate, String sales_executioner_id, String sales_app_for, String sales_document_id
            , String sales_created, String sales_app_ref, String sales_app_city, String sales_u_flag, String sales_create_dt, String sales_approved_by,
                                       String sales_created_by, String sales_latitude, String sales_longititude, String sales_logical_del
            , String sales_poststatus, String sales_apptype, String sales_id, String username2, String sales_address, String sales_landmark, String sales_acvr_id, String sales_distance, String sales_trans_type, String sales_amount, String sync, String sales_acvrreport, String notification, String sales_app_status) {


        SQLiteDatabase salesappoint = db.getWritableDatabase();
        ContentValues values3 = new ContentValues();
        values3.put(DBManager.TableInfo.db_sales_appointment_id, sales_appointment_id);
        values3.put(DBManager.TableInfo.db_sales_contactperson, sales_contactperson);
        values3.put(DBManager.TableInfo.db_sales_starttime, startnewtime1);
        values3.put(DBManager.TableInfo.db_sales_endtime, startnewtime2);
        values3.put(DBManager.TableInfo.db_sales_startdate, startnewdate);
        values3.put(DBManager.TableInfo.db_sales_executioner_id, sales_executioner_id);
        values3.put(DBManager.TableInfo.db_sales_app_for, sales_app_for);
        values3.put(DBManager.TableInfo.db_sales_doc_id, sales_document_id);
        values3.put(DBManager.TableInfo.db_sales_created_at, sales_created);
        values3.put(DBManager.TableInfo.db_sales_app_ref, sales_app_ref);
        values3.put(DBManager.TableInfo.db_sales_app_city, sales_app_city);
        values3.put(DBManager.TableInfo.db_sales_u_flag, sales_u_flag);
        values3.put(DBManager.TableInfo.db_sales_create_dt, sales_create_dt);
        values3.put(DBManager.TableInfo.db_sales_approved_by, sales_approved_by);
        values3.put(DBManager.TableInfo.db_sales_createdby, sales_created_by);
        values3.put(DBManager.TableInfo.db_sales_latitude, sales_latitude);
        values3.put(DBManager.TableInfo.db_sales_longititude, sales_longititude);
        values3.put(DBManager.TableInfo.db_sales_logical_del, sales_logical_del);
        values3.put(DBManager.TableInfo.db_sales_poststatus, sales_poststatus);
        values3.put(DBManager.TableInfo.db_sales_app_type, sales_apptype);
        values3.put(DBManager.TableInfo.db_sales_id, sales_id);
        values3.put(DBManager.TableInfo.KEY_LOGIN_USER, username2);
        values3.put(DBManager.TableInfo.db_sales_address, sales_address);
        values3.put(DBManager.TableInfo.db_sales_landmark, sales_landmark);
        values3.put(DBManager.TableInfo.db_sales_acvr_id, sales_acvr_id);
        values3.put(DBManager.TableInfo.db_sales_distance, sales_distance);
        values3.put(DBManager.TableInfo.db_sales_transtype, sales_trans_type);
        values3.put(DBManager.TableInfo.db_sales_amount, sales_amount);
        values3.put(DBManager.TableInfo.db_sales_syncstatus, sync);
        values3.put(DBManager.TableInfo.db_sales_acvrsyncstatus, sales_acvrreport);
        values3.put(DBManager.TableInfo.notification, notification);
        values3.put(DBManager.TableInfo.db_sales_app_status, sales_app_status);

        int count4 = salesappoint.update(DBManager.TableInfo.TABLE_SALES_APPOINTMENT, values3, DBManager.TableInfo.db_sales_appointment_id + "=?", new String[]{String.valueOf(sales_appointment_id)});

    }

    public void Insertsalesdocuments(DBOperation db, String sales_env, String sales_document_id, String username2,
                                     String sync, String sales_acvrreport) {
        SQLiteDatabase salesdoc = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBManager.TableInfo.db_sales_env, sales_env);
        values.put(DBManager.TableInfo.db_sales_doc_id, sales_document_id);
        values.put(DBManager.TableInfo.KEY_LOGIN_USER, username2);
        values.put(DBManager.TableInfo.db_sales_syncstatus, sync);
        values.put(DBManager.TableInfo.db_sales_acvrsyncstatus, sales_acvrreport);
        String condition = DBManager.TableInfo.db_sales_doc_id + " =?";
        Cursor cursoratted = salesdoc.query(TABLE_SALES, null, condition, new String[]{sales_document_id}, null, null, null);
        long status = salesdoc.insert(TABLE_SALES, null, values);
cursoratted.close();

    }

    public void Updatesalesdocuments(DBOperation db, String sales_env, String sales_document_id, String username2,
                                     String sync, String sales_acvrreport) {

        SQLiteDatabase salesdoc = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBManager.TableInfo.db_sales_env, sales_env);
        values.put(DBManager.TableInfo.db_sales_doc_id, sales_document_id);
        values.put(DBManager.TableInfo.KEY_LOGIN_USER, username2);
        values.put(DBManager.TableInfo.db_sales_syncstatus, sync);
        values.put(DBManager.TableInfo.db_sales_acvrsyncstatus, sales_acvrreport);
        int count4 = salesdoc.update(DBManager.TableInfo.TABLE_SALES, values, DBManager.TableInfo.db_sales_doc_id + "=?", new String[]{String.valueOf(sales_document_id)});

    }


    public void InsertmarriageattendeesRecord(DBOperation db, String ID1, String marriage_name, String marriage_email,
                                              String marriage_contact, String marriage_appointid, String username2) {

        SQLiteDatabase marriageattend = db.getWritableDatabase();
        ContentValues values3 = new ContentValues();
        values3.put(DBManager.TableInfo.KEYID, ID1);
        values3.put(DBManager.TableInfo.db_marriage_name, marriage_name);
        values3.put(DBManager.TableInfo.db_marriage_mail, marriage_email);
        values3.put(DBManager.TableInfo.db_marriage_contact, marriage_contact);
        values3.put(DBManager.TableInfo.db_marriage_appointment_id, marriage_appointid);
        values3.put(DBManager.TableInfo.KEY_LOGIN_USER, username2);
        String condition3 = DBManager.TableInfo.db_marriage_name + " =?";
        Cursor cursoratted = marriageattend.query(TABLE_MARRIAGE_ATTENDEES, null, condition3, new String[]{marriage_name}, null, null, null);
        long status3 = marriageattend.insert(TABLE_MARRIAGE_ATTENDEES, null, values3);
cursoratted.close();

    }

    public void InsertmarriageappointmentRecord(DBOperation db, String marriage_appointid, String marriage_documentid, String marriage_contactperson,
                                                String startnewtime1, String startnewtime2, String startnewdate, String marriage_address, String marriage_landmark
            , String marriage_appfor, String marriage_execuid, String marriage_latitude, String marriage_longitiude, String marriage_dist, String marriage_trans_type,
                                                String marriage_acvr_amount, String marriage_acvr_id, String marriage_logical_del, String marriage_poststatus
            , String marriage_franchiseid, String marriage_coid, String marriage_statusid, String marriage_ex_id, String marriage_payverify, String marriage_createdat, String marriage_appflag, String marriage_payflag, String marriage_app_type, String marriage_id, String sync, String acvrSync, String marriage_app_status, String username2, String notification,String marriage_type) {

        SQLiteDatabase marriage_appoint = db.getWritableDatabase();
        ContentValues values3 = new ContentValues();
        values3.put(DBManager.TableInfo.db_marriage_appointment_id, marriage_appointid);
        values3.put(DBManager.TableInfo.db_marriage_doc_id, marriage_documentid);
        values3.put(DBManager.TableInfo.db_marriage_contact_person, marriage_contactperson);
        values3.put(DBManager.TableInfo.db_marriage_starttime, startnewtime1);
        values3.put(DBManager.TableInfo.db_marriage_endtime, startnewtime2);
        values3.put(DBManager.TableInfo.db_marriage_startdate, startnewdate);
        values3.put(DBManager.TableInfo.db_marriage_address, marriage_address);
        values3.put(DBManager.TableInfo.db_marriage_landmark, marriage_landmark);
        values3.put(DBManager.TableInfo.db_marriage_app_for, marriage_appfor);
        values3.put(DBManager.TableInfo.db_marriage_executionerid, marriage_execuid);
        values3.put(DBManager.TableInfo.db_marriage_latitude, marriage_latitude);
        values3.put(DBManager.TableInfo.db_marriage_longititude, marriage_longitiude);
        values3.put(DBManager.TableInfo.db_marriage_distance, marriage_dist);
        values3.put(DBManager.TableInfo.db_marriage_transtype, marriage_trans_type);
        values3.put(DBManager.TableInfo.db_marriage_acvr_amount, marriage_acvr_amount);
        values3.put(DBManager.TableInfo.db_marriage_acvr_id, marriage_acvr_id);
        values3.put(DBManager.TableInfo.db_marriage_logical_del, marriage_logical_del);
        values3.put(DBManager.TableInfo.db_marriage_poststatus, marriage_poststatus);
        values3.put(DBManager.TableInfo.db_marriage_franchise_id, marriage_franchiseid);
        values3.put(DBManager.TableInfo.db_marriage_co_id, marriage_coid);
        values3.put(DBManager.TableInfo.db_marriage_status_id, marriage_statusid);
        values3.put(DBManager.TableInfo.db_marriage_ex_id, marriage_ex_id);
        values3.put(DBManager.TableInfo.db_marriage_pay_verify, marriage_payverify);
        values3.put(DBManager.TableInfo.db_marriage_created_at, marriage_createdat);
        values3.put(DBManager.TableInfo.db_marriage_app_flag, marriage_appflag);
        values3.put(DBManager.TableInfo.db_marriage_pay_flag, marriage_payflag);
        values3.put(DBManager.TableInfo.db_marriage_app_type, marriage_app_type);
        values3.put(DBManager.TableInfo.db_marriage_id, marriage_id);
        values3.put(DBManager.TableInfo.db_marriage_syncstatus, sync);
        values3.put(DBManager.TableInfo.db_marriage_acvrsyncstatus, acvrSync);
        values3.put(DBManager.TableInfo.db_marriage_app_status, marriage_app_status);
        values3.put(DBManager.TableInfo.KEY_LOGIN_USER, username2);
        values3.put(DBManager.TableInfo.notification, notification);
        values3.put(DBManager.TableInfo.db_marriage_type, marriage_type);

        String condition3 = DBManager.TableInfo.db_marriage_appointment_id + " =?";
        Cursor cursorappoint = marriage_appoint.query(TABLE_MARRIAGE_APPOINTMENT, null, condition3, new String[]{marriage_appointid}, null, null, null);
        long status3 = marriage_appoint.insert(TABLE_MARRIAGE_APPOINTMENT, null, values3);
cursorappoint.close();
    }

    public void updatemarriageappointment(DBOperation db, String marriage_appointid, String marriage_documentid, String marriage_contactperson,
                                          String startnewtime1, String startnewtime2, String startnewdate, String marriage_address, String marriage_landmark
            , String marriage_appfor, String marriage_execuid, String marriage_latitude, String marriage_longitiude, String marriage_dist, String marriage_trans_type,
                                          String marriage_acvr_amount, String marriage_acvr_id, String marriage_logical_del, String marriage_poststatus
            , String marriage_franchiseid, String marriage_coid, String marriage_statusid, String marriage_ex_id, String marriage_payverify, String marriage_createdat, String marriage_appflag, String marriage_payflag, String marriage_app_type, String marriage_id, String sync, String acvrSync, String marriage_app_status, String username2, String notification,String marriage_type) {


        SQLiteDatabase marriage_appoint = db.getWritableDatabase();
        ContentValues values3 = new ContentValues();
        values3.put(DBManager.TableInfo.db_marriage_appointment_id, marriage_appointid);
        values3.put(DBManager.TableInfo.db_marriage_doc_id, marriage_documentid);
        values3.put(DBManager.TableInfo.db_marriage_contact_person, marriage_contactperson);
        values3.put(DBManager.TableInfo.db_marriage_starttime, startnewtime1);
        values3.put(DBManager.TableInfo.db_marriage_endtime, startnewtime2);
        values3.put(DBManager.TableInfo.db_marriage_startdate, startnewdate);
        values3.put(DBManager.TableInfo.db_marriage_address, marriage_address);
        values3.put(DBManager.TableInfo.db_marriage_landmark, marriage_landmark);
        values3.put(DBManager.TableInfo.db_marriage_app_for, marriage_appfor);
        values3.put(DBManager.TableInfo.db_marriage_executionerid, marriage_execuid);
        values3.put(DBManager.TableInfo.db_marriage_latitude, marriage_latitude);
        values3.put(DBManager.TableInfo.db_marriage_longititude, marriage_longitiude);
        values3.put(DBManager.TableInfo.db_marriage_distance, marriage_dist);
        values3.put(DBManager.TableInfo.db_marriage_transtype, marriage_trans_type);
        values3.put(DBManager.TableInfo.db_marriage_acvr_amount, marriage_acvr_amount);
        values3.put(DBManager.TableInfo.db_marriage_acvr_id, marriage_acvr_id);
        values3.put(DBManager.TableInfo.db_marriage_logical_del, marriage_logical_del);
        values3.put(DBManager.TableInfo.db_marriage_poststatus, marriage_poststatus);
        values3.put(DBManager.TableInfo.db_marriage_franchise_id, marriage_franchiseid);
        values3.put(DBManager.TableInfo.db_marriage_co_id, marriage_coid);
        values3.put(DBManager.TableInfo.db_marriage_status_id, marriage_statusid);
        values3.put(DBManager.TableInfo.db_marriage_ex_id, marriage_ex_id);
        values3.put(DBManager.TableInfo.db_marriage_pay_verify, marriage_payverify);
        values3.put(DBManager.TableInfo.db_marriage_created_at, marriage_createdat);
        values3.put(DBManager.TableInfo.db_marriage_app_flag, marriage_appflag);
        values3.put(DBManager.TableInfo.db_marriage_pay_flag, marriage_payflag);
        values3.put(DBManager.TableInfo.db_marriage_app_type, marriage_app_type);
        values3.put(DBManager.TableInfo.db_marriage_id, marriage_id);
        values3.put(DBManager.TableInfo.db_marriage_syncstatus, sync);
        values3.put(DBManager.TableInfo.db_marriage_acvrsyncstatus, acvrSync);
        values3.put(DBManager.TableInfo.db_marriage_app_status, marriage_app_status);
        values3.put(DBManager.TableInfo.KEY_LOGIN_USER, username2);
        values3.put(DBManager.TableInfo.notification, notification);
        values3.put(DBManager.TableInfo.db_marriage_type, marriage_type);
        int count4 = marriage_appoint.update(DBManager.TableInfo.TABLE_MARRIAGE_APPOINTMENT, values3, DBManager.TableInfo.db_marriage_appointment_id + "=?", new String[]{String.valueOf(marriage_appointid)});


    }

    public void Insertmarriagedocuments(DBOperation db, String marriage_documentid, String marriage_status, String marriage_env,
                                        String sync, String acvrSync, String username2, String marriage_husbandname, String marriage_husbandbiometric, String marriage_husbandcontact
            , String marriage_wifebiometric, String marriage_wifename, String marriage_wifecontact, String marriage_tokenno) {

        SQLiteDatabase marriage_doc = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBManager.TableInfo.db_marriage_doc_id, marriage_documentid);
        values.put(DBManager.TableInfo.db_marriage_status, marriage_status);
        values.put(DBManager.TableInfo.db_marriage_env, marriage_env);
        values.put(DBManager.TableInfo.db_marriage_syncstatus, sync);
        values.put(DBManager.TableInfo.db_marriage_acvrsyncstatus, acvrSync);
        values.put(DBManager.TableInfo.KEY_LOGIN_USER, username2);
        values.put(DBManager.TableInfo.db_marriage_husbandname, marriage_husbandname);
        values.put(DBManager.TableInfo.db_marriage_husbandbiometric, marriage_husbandbiometric);
        values.put(DBManager.TableInfo.db_marriage_husbandcontact, marriage_husbandcontact);
        values.put(DBManager.TableInfo.db_marriage_wifebiometric, marriage_wifebiometric);
        values.put(DBManager.TableInfo.db_marriage_wifename, marriage_wifename);
        values.put(DBManager.TableInfo.db_marriage_wifecontact, marriage_wifecontact);
        values.put(DBManager.TableInfo.db_marriage_tokenno, marriage_tokenno);

        String condition = DBManager.TableInfo.db_marriage_doc_id + " =?";
        Cursor cursoratted = marriage_doc.query(TABLE_MARRIAGE, null, condition, new String[]{marriage_documentid}, null, null, null);
        long status = marriage_doc.insert(TABLE_MARRIAGE, null, values);
cursoratted.close();

    }

    public void Updatemarriagedocuments(DBOperation db, String marriage_documentid, String marriage_status, String marriage_env,
                                        String sync, String acvrSync, String username2, String marriage_husbandname, String marriage_husbandbiometric, String marriage_husbandcontact
            , String marriage_wifebiometric, String marriage_wifename, String marriage_wifecontact, String marriage_tokenno) {


        SQLiteDatabase marriage_doc = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBManager.TableInfo.db_marriage_doc_id, marriage_documentid);
        values.put(DBManager.TableInfo.db_marriage_status, marriage_status);
        values.put(DBManager.TableInfo.db_marriage_env, marriage_env);
        values.put(DBManager.TableInfo.db_marriage_syncstatus, sync);
        values.put(DBManager.TableInfo.db_marriage_acvrsyncstatus, acvrSync);
        values.put(DBManager.TableInfo.KEY_LOGIN_USER, username2);
        values.put(DBManager.TableInfo.db_marriage_husbandname, marriage_husbandname);
        values.put(DBManager.TableInfo.db_marriage_husbandbiometric, marriage_husbandbiometric);
        values.put(DBManager.TableInfo.db_marriage_husbandcontact, marriage_husbandcontact);
        values.put(DBManager.TableInfo.db_marriage_wifebiometric, marriage_wifebiometric);
        values.put(DBManager.TableInfo.db_marriage_wifename, marriage_wifename);
        values.put(DBManager.TableInfo.db_marriage_wifecontact, marriage_wifecontact);
        values.put(DBManager.TableInfo.db_marriage_tokenno, marriage_tokenno);

        int count4 = marriage_doc.update(DBManager.TableInfo.TABLE_MARRIAGE, values, DBManager.TableInfo.db_marriage_doc_id + "=?", new String[]{(marriage_documentid)});

    }

    public void Insertpendingwitness(DBOperation db, String ID1, String docid, String env, String tokenno,
                                     String party_type, String att_id, String witness_name, String witness_biometric, String witness_email, String witness_contact
            , String ocontact, String oemail, String oname, String oaddress, String tcontact, String temail, String tname, String taddress, String Paddress,
                                     String from_dt, String to_dt, String rent, String deposit, String refunddeposit, String city_name, String status_id, String status, String id,
                                     String agreement_cancle, String username2) {


        SQLiteDatabase db4 = db.getWritableDatabase();
        ContentValues values4 = new ContentValues();
        values4.put(DBManager.TableInfo.KEYID, ID1);
        values4.put(DBManager.TableInfo.db_PW_doc_id, docid);
        values4.put(DBManager.TableInfo.db_PW_env, env);
        values4.put(DBManager.TableInfo.db_PW_token_no, tokenno);
        values4.put(DBManager.TableInfo.db_PW_partytype, party_type);
        values4.put(DBManager.TableInfo.db_PW_attid, att_id);
        values4.put(DBManager.TableInfo.db_PW_witnessname, witness_name);
        values4.put(DBManager.TableInfo.db_PW_biometric, witness_biometric);
        values4.put(DBManager.TableInfo.db_PW_email, witness_email);
        values4.put(DBManager.TableInfo.db_PW_contact, witness_contact);
        values4.put(DBManager.TableInfo.db_PW_name, witness_name);
        values4.put(DBManager.TableInfo.db_PW_ocontact, ocontact);
        values4.put(DBManager.TableInfo.db_PW_oemail, oemail);
        values4.put(DBManager.TableInfo.db_PW_oname, oname);
        values4.put(DBManager.TableInfo.db_PW_oaddress, oaddress);
        values4.put(DBManager.TableInfo.db_PW_tcontact, tcontact);
        values4.put(DBManager.TableInfo.db_PW_temail, temail);
        values4.put(DBManager.TableInfo.db_PW_tname, tname);
        values4.put(DBManager.TableInfo.db_PW_taddress, taddress);
        values4.put(DBManager.TableInfo.db_PW_Paddress, Paddress);
        values4.put(DBManager.TableInfo.db_PW_from_dt, from_dt);
        values4.put(DBManager.TableInfo.db_PW_to_dt, to_dt);
        values4.put(DBManager.TableInfo.db_PW_rent, rent);
        values4.put(DBManager.TableInfo.db_PW_deposit, deposit);
        values4.put(DBManager.TableInfo.db_PW_refunddeposit, refunddeposit);
        values4.put(DBManager.TableInfo.db_PW_city_name, city_name);
        values4.put(DBManager.TableInfo.db_PW_status_id, status_id);
        values4.put(DBManager.TableInfo.db_PW_status, status);
        values4.put(DBManager.TableInfo.db_PW_id, id);
        values4.put(DBManager.TableInfo.db_PW_agreement_cancle, agreement_cancle);
        values4.put(DBManager.TableInfo.KEY_LOGIN_USER, username2);
        String condition4 = DBManager.TableInfo.db_PW_doc_id + " =?";
        Cursor cursor4 = db4.query(DBManager.TableInfo.TABLE_PENDINGWITNESS, null, condition4, new String[]{docid}, null, null, null);
        long status4 = db4.insert(DBManager.TableInfo.TABLE_PENDINGWITNESS, null, values4);
        cursor4.close();



    }

    public void Inssertadhoctask(DBOperation db, String ID1, String witnessid, String comment,
                                 String isdone, String doc, String create, String taskname, String notification
            , String status1, String remainder, String assign, String taskid, String username2) {


        SQLiteDatabase adhoc = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBManager.TableInfo.KEYID, ID1);
        values.put(DBManager.TableInfo.WITID, witnessid);
        values.put(DBManager.TableInfo.COMMENT, comment);
        values.put(DBManager.TableInfo.IS_DONE, isdone);
        values.put(DBManager.TableInfo.DOC, doc);
        values.put(DBManager.TableInfo.CREATE, create);
        values.put(DBManager.TableInfo.TASK_NAME, taskname);
        values.put(DBManager.TableInfo.notification, notification);
        values.put(DBManager.TableInfo.status1, status1);
        values.put(DBManager.TableInfo.REMAINDER, remainder);
        values.put(DBManager.TableInfo.ASSIGN, assign);
        values.put(DBManager.TableInfo.TASK_ID, taskid);
        values.put(DBManager.TableInfo.KEY_LOGIN_USER, username2);
        String condition = DBManager.TableInfo.DOC + " =?";
        Cursor cursor = adhoc.query(TABLE_TASK, null, condition, new String[]{DBManager.TableInfo.TASK_ID}, null, null, null);
        long status = adhoc.insert(TABLE_TASK, null, values);

      cursor.close();


    }

    public void InsertWitnesstask(DBOperation db, String ID1, String witnessid, String comment,
                                  String isdone, String doc, String create, String taskname, String notification
            , String status1, String remainder, String assign, String taskid, String username2) {


        SQLiteDatabase witn = db.getWritableDatabase();
        ContentValues valuesw = new ContentValues();
        valuesw.put(DBManager.TableInfo.KEYID, ID1);
        valuesw.put(DBManager.TableInfo.WITID, witnessid);
        valuesw.put(DBManager.TableInfo.COMMENT, comment);
        valuesw.put(DBManager.TableInfo.IS_DONE, isdone);
        valuesw.put(DBManager.TableInfo.DOC, doc);
        valuesw.put(DBManager.TableInfo.CREATE, create);
        valuesw.put(DBManager.TableInfo.TASK_NAME, taskname);
        valuesw.put(DBManager.TableInfo.notification, notification);
        valuesw.put(DBManager.TableInfo.status1, status1);
        valuesw.put(DBManager.TableInfo.REMAINDER, remainder);
        valuesw.put(DBManager.TableInfo.ASSIGN, assign);
        valuesw.put(DBManager.TableInfo.TASK_ID, taskid);
        valuesw.put(DBManager.TableInfo.KEY_LOGIN_USER, username2);
        String conditionw = DBManager.TableInfo.DOC + " =?";
        Cursor cursorw = witn.query(TABLE_TASK, null, conditionw, new String[]{DBManager.TableInfo.TASK_ID}, null, null, null);
        long statusw = witn.insert(TABLE_TASK, null, valuesw);
             cursorw.close();
    }

    public void Insertcomplainttask(DBOperation db, String ID1, String witnessid, String comment,
                                    String isdone, String doc, String create, String taskname, String notification
            , String status1, String remainder, String assign, String taskid, String username2) {


        SQLiteDatabase complaint = db.getWritableDatabase();
        ContentValues valuesw = new ContentValues();
        valuesw.put(DBManager.TableInfo.KEYID, ID1);
        valuesw.put(DBManager.TableInfo.WITID, witnessid);
        valuesw.put(DBManager.TableInfo.COMMENT, comment);
        valuesw.put(DBManager.TableInfo.IS_DONE, isdone);
        valuesw.put(DBManager.TableInfo.DOC, doc);
        valuesw.put(DBManager.TableInfo.CREATE, create);
        valuesw.put(DBManager.TableInfo.TASK_NAME, taskname);
        valuesw.put(DBManager.TableInfo.notification, notification);
        valuesw.put(DBManager.TableInfo.status1, status1);
        valuesw.put(DBManager.TableInfo.REMAINDER, remainder);
        valuesw.put(DBManager.TableInfo.ASSIGN, assign);
        valuesw.put(DBManager.TableInfo.TASK_ID, taskid);
        valuesw.put(DBManager.TableInfo.KEY_LOGIN_USER, username2);
        String conditionw = DBManager.TableInfo.DOC + " =?";
        Cursor cursorw = complaint.query(TABLE_TASK, null, conditionw, new String[]{DBManager.TableInfo.TASK_ID}, null, null, null);
        long statusw = complaint.insert(TABLE_TASK, null, valuesw);
        cursorw.close();

    }


    public void InsertRecord1(DBOperation dop, String uname) {
        SQLiteDatabase sqlite = dop.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBManager.TableInfo.UserEmail, uname);
        long res = sqlite.insert(DBManager.TableInfo.TABLE_NAME1, null, cv);

    }

    public void InsertRecord2(DBOperation dop, String docid, String appid, String date, String startime, String endtime,
                              String appadd, String exid, String appfor, String distance, String amount, String transporttype, String sync, String contactperson, String land, String attendeesstatus, String poststatus, String strlatitude1, String strlongitude1, String notification, String app_status) {

        SQLiteDatabase sqlite = dop.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBManager.TableInfo.DocumentId, docid);
        cv.put(DBManager.TableInfo.AppointmentId, appid);
        cv.put(DBManager.TableInfo.StartDate, date);
        cv.put(DBManager.TableInfo.StartTime1, startime);
        cv.put(DBManager.TableInfo.StartTime2, endtime);
        cv.put(DBManager.TableInfo.AppointmentAddress, appadd);
        cv.put(DBManager.TableInfo.Executioner_id, exid);
        cv.put(DBManager.TableInfo.AppFor, appfor);
        cv.put(DBManager.TableInfo.DISTANCE, distance);
        cv.put(DBManager.TableInfo.AMOUNT, amount);
        cv.put(DBManager.TableInfo.TRANSPORTTYPE, transporttype);
        cv.put(DBManager.TableInfo.SYNCSTATUSREPORT, sync);
        cv.put(DBManager.TableInfo.contactperson, contactperson);
        cv.put(DBManager.TableInfo.landmark, land);
        cv.put(DBManager.TableInfo.att_status, attendeesstatus);
        cv.put(DBManager.TableInfo.post_status, poststatus);
        cv.put(DBManager.TableInfo.LATITUDE, strlatitude1);
        cv.put(DBManager.TableInfo.LONGITUDE, strlongitude1);
        cv.put(DBManager.TableInfo.notification, notification);
        cv.put(DBManager.TableInfo.App_status, app_status);


        long res = sqlite.insert(DBManager.TableInfo.TABLE_NAME_APPOINTMENT, null, cv);
        dop.close();

    }


    public long InsertRecord3(DBOperation dop, String envid, String custcomments, String user, String owner, String id,
                              String isdone, String cdate, String SyncStatus, String reminderdate, String taskname, String type, String area, String contact) {

        SQLiteDatabase sqlite = dop.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(DBManager.TableInfo.Comment_env, envid);
        cv.put(DBManager.TableInfo.Comments, custcomments);
        cv.put(DBManager.TableInfo.Comment_user, user);
        cv.put(DBManager.TableInfo.Comment_owner, owner);
        cv.put(DBManager.TableInfo.Comment_id, id);
        cv.put(DBManager.TableInfo.Comment_is_done, isdone);
        cv.put(DBManager.TableInfo.Comment_date, cdate);
        cv.put(DBManager.TableInfo.SYNCSTATUS, SyncStatus);
        cv.put(DBManager.TableInfo.REMINDER_DATE, reminderdate);
        cv.put(DBManager.TableInfo.task_name, taskname);
        cv.put(DBManager.TableInfo.Comments_type, type);
        cv.put(DBManager.TableInfo.Comments_area, area);
        cv.put(DBManager.TableInfo.Comments_contact, contact);
        long res = sqlite.insert(DBManager.TableInfo.TABLE_NAME3, null, cv);
     System.out.println("comment-insert"+res);
        dop.close();
return res;
    }


    public void InsertRecord5(DBOperation dop, String userid, String username, String email, String role, String platformid, String roleid, String idu) {

        SQLiteDatabase sqlite = dop.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(DBManager.TableInfo.User_id, userid);
        cv.put(DBManager.TableInfo.User_name, username);
        cv.put(DBManager.TableInfo.Email, email);
        cv.put(DBManager.TableInfo.Role, role);
        cv.put(DBManager.TableInfo.platformid, platformid);
        cv.put(DBManager.TableInfo.role_id, roleid);
        cv.put(DBManager.TableInfo.idu, idu);
        long res = sqlite.insert(DBManager.TableInfo.TABLE_NAME5, null, cv);

        dop.close();
    }


    public ArrayList<HashMap<String, String>> getAllData(DBOperation db) {

        ArrayList<HashMap<String, String>> idlist = new ArrayList<>();
        SQLiteDatabase sqlite = db.getWritableDatabase();

        String selectQuery = "SELECT  * FROM " + DBManager.TableInfo.TABLE_NAME_APPOINTMENT + " t1, " + DBManager.TableInfo.TABLE_NAME_DOCUMENT + " t2 WHERE "
                + "t1." + DBManager.TableInfo.DocumentId + " = " + "t2." + DBManager.TableInfo.DocumentId + " AND t1."
                + DBManager.TableInfo.Executioner_id + "='" + Login.umailid + "'" + " ORDER BY " + "t1." + DBManager.TableInfo.StartTime1;

        Cursor cr = sqlite.rawQuery(selectQuery, null);
        if (cr.moveToFirst()) {
            cr.moveToFirst();
            do {
                HashMap<String, String> idmap = new HashMap<>();
                String docidnewdb = cr.getString(cr.getColumnIndex("Did"));
                String appidnewdb = cr.getString(cr.getColumnIndex("Aid"));
                String onamedb = cr.getString(cr.getColumnIndex("Oname"));
                String exiddb = cr.getString(cr.getColumnIndex("Exid"));
                idmap.put("Did", docidnewdb);
                idmap.put("Aid", appidnewdb);
                idmap.put("Oname", onamedb);
                idmap.put("Exid", exiddb);
                idlist.add(idmap);
            } while (cr.moveToNext());
        }
        cr.close();
        return idlist;

    }

    public ArrayList<HashMap<String, String>> getAllsalesData(DBOperation db) {

        ArrayList<HashMap<String, String>> idlist = new ArrayList<>();
        SQLiteDatabase sqlite = db.getWritableDatabase();

        String selectQuery = "SELECT  * FROM " + DBManager.TableInfo.TABLE_SALES_APPOINTMENT + " t1, " + DBManager.TableInfo.TABLE_SALES + " t2 WHERE "
                + "t1." + DBManager.TableInfo.db_sales_doc_id + " = " + "t2." + DBManager.TableInfo.db_sales_doc_id + " AND t1."
                + DBManager.TableInfo.KEY_LOGIN_USER + "='" + Login.umailid + "'" + " ORDER BY " + "t1." + DBManager.TableInfo.db_sales_starttime;

        Cursor cr = sqlite.rawQuery(selectQuery, null);
        if (cr.moveToFirst()) {
            cr.moveToFirst();
            do {
                HashMap<String, String> idmap = new HashMap<>();
                String docidnewdb = cr.getString(cr.getColumnIndex("document_id"));
                String appidnewdb = cr.getString(cr.getColumnIndex("appointment_id"));

                idmap.put("document_id", docidnewdb);
                idmap.put("appointment_id", appidnewdb);

                idlist.add(idmap);
            } while (cr.moveToNext());
        }
        cr.close();
        return idlist;

    }

    public ArrayList<HashMap<String, String>> getAllmarriageData(DBOperation db) {

        ArrayList<HashMap<String, String>> idlist = new ArrayList<>();
        SQLiteDatabase sqlite = db.getWritableDatabase();

        String selectQuery = "SELECT  * FROM " + DBManager.TableInfo.TABLE_MARRIAGE_APPOINTMENT + " t1, " + DBManager.TableInfo.TABLE_MARRIAGE + " t2 WHERE "
                + "t1." + DBManager.TableInfo.db_marriage_doc_id + " = " + "t2." + DBManager.TableInfo.db_marriage_doc_id + " AND t1."
                + DBManager.TableInfo.KEY_LOGIN_USER + "='" + Login.umailid + "'" + " ORDER BY " + "t1." + DBManager.TableInfo.db_marriage_starttime;

        Cursor cr = sqlite.rawQuery(selectQuery, null);
        if (cr.moveToFirst()) {
            cr.moveToFirst();
            do {
                HashMap<String, String> idmap = new HashMap<>();
                String docidnewdb = cr.getString(cr.getColumnIndex("document_id"));
                String appidnewdb = cr.getString(cr.getColumnIndex("appointment_id"));

                idmap.put("document_id", docidnewdb);
                idmap.put("appointment_id", appidnewdb);

                idlist.add(idmap);
            } while (cr.moveToNext());
        }
        cr.close();
        return idlist;

    }

    public List<String> getAllLabels() {
        List<String> labels = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + DBManager.TableInfo.TABLE_NAME5;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        labels.add("");
        if (cursor.moveToFirst()) {
            do {

                labels.add(cursor.getString(cursor.getColumnIndex("email2")));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return labels;
    }

    public List<String> getAllexecutioner() {
        List<String> labels = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + DBManager.TableInfo.TABLE_NAME5;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                labels.add(cursor.getString(cursor.getColumnIndex("email2")));

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return labels;
    }

    public ArrayList<HashMap<String, String>> getuserid(DBOperation dop) {

        ArrayList<HashMap<String, String>> listUsername = new ArrayList<>();

        try {
            String query = "SELECT * from " + DBManager.TableInfo.TABLE_NAME5;
            SQLiteDatabase sqlite = dop.getWritableDatabase();
            Cursor c = sqlite.rawQuery(query, null);

            while (c.moveToNext()) {
                HashMap<String, String> username = new HashMap<>();

                username.put(DBManager.TableInfo.User_id, c.getString(c.getColumnIndex(DBManager.TableInfo.User_id)));

                listUsername.add(username);
            }

            closeCursor(c);

        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }
        dop.close();
        return listUsername;
    }

    public ArrayList<HashMap<String, String>> getusername(DBOperation dop) {

        ArrayList<HashMap<String, String>> listUsername = new ArrayList<>();

        try {
            String query = "SELECT * from " + DBManager.TableInfo.TABLE_NAME5;
            SQLiteDatabase sqlite = dop.getWritableDatabase();
            Cursor c = sqlite.rawQuery(query, null);

            while (c.moveToNext()) {
                HashMap<String, String> username = new HashMap<>();

                username.put(DBManager.TableInfo.User_name, c.getString(c.getColumnIndex(DBManager.TableInfo.User_name)));

                listUsername.add(username);
            }

            closeCursor(c);

        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }
        dop.close();
        return listUsername;
    }

    public boolean validateUser(String mailid) {

        Cursor c = getReadableDatabase().rawQuery(
                "SELECT * FROM " + DBManager.TableInfo.TABLE_NAME1 + " WHERE " + DBManager.TableInfo.UserEmail + "='" + mailid + "'", null);
        if (c.getCount() > 0)
            return true;
        c.close();
        return false;
    }

    public long UpdateAllRecord(DBOperation db, String docid, String bioSel, String editedbiocomp1, String regSel, String witSel, String localtime, String Locationstring, String Appointmentid) {
        // TODO Auto-generated method stub

        String selection = DBManager.TableInfo.DocumentId + " = ? ";
        String a[] = {docid};
        SQLiteDatabase sqlite = db.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBManager.TableInfo.BiometricComp1, bioSel);
        cv.put(DBManager.TableInfo.BiometricComp, editedbiocomp1);
        cv.put(DBManager.TableInfo.Reg_From_Comp, regSel);
        cv.put(DBManager.TableInfo.Witness, witSel);
        cv.put(DBManager.TableInfo.SYNCSTATUS, "ASYNC");
        cv.put(DBManager.TableInfo.updatetime, localtime);
        cv.put(DBManager.TableInfo.updatelocation, Locationstring);
        cv.put(DBManager.TableInfo.AppointmentId, Appointmentid);
        long result = sqlite.update(DBManager.TableInfo.TABLE_NAME_DOCUMENT, cv, selection, a);

        return result;


    }


    public void Update(DBOperation db, String docid, String rtoken1, String rkey1, String uname1, String email1,
                       String contact_number1, String paddress1, String oname1, String ocontact1, String oemail1, String oaddress1,
                       String tname1, String tcontact1, String temail1, String taddress1, String status1, String appdate,
                       String biocomp, String appdate1, String biocomp1, String regfromcomp, String witness, String shipadd,
                       String shipdiffadd, String apptype, String biostatus,String sgrn,String rgrn) {
        // TODO Auto-generated method stub

        String selection = DBManager.TableInfo.DocumentId + " = ? ";

        String a[] = {docid};

        SQLiteDatabase sqlite = db.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBManager.TableInfo.ReportToken, rtoken1);
        cv.put(DBManager.TableInfo.ReportKey, rkey1);
        cv.put(DBManager.TableInfo.Uname, uname1);
        cv.put(DBManager.TableInfo.Umail, email1);
        cv.put(DBManager.TableInfo.Ucontact, contact_number1);
        cv.put(DBManager.TableInfo.PropertyAddress, paddress1);
        cv.put(DBManager.TableInfo.OwnerName, oname1);
        cv.put(DBManager.TableInfo.OwnerContact, ocontact1);
        cv.put(DBManager.TableInfo.OwnerEmail, oemail1);
        cv.put(DBManager.TableInfo.OwnerAddress, oaddress1);
        cv.put(DBManager.TableInfo.TenantName, tname1);
        cv.put(DBManager.TableInfo.TenantContact, tcontact1);
        cv.put(DBManager.TableInfo.TenantEmail, temail1);
        cv.put(DBManager.TableInfo.TenantAddress, taddress1);
        cv.put(DBManager.TableInfo.Status, status1);
        cv.put(DBManager.TableInfo.Appointment_Date, appdate);
        cv.put(DBManager.TableInfo.BiometricComp, biocomp);
        cv.put(DBManager.TableInfo.Appointment_Date1, appdate1);
        cv.put(DBManager.TableInfo.BiometricComp1, biocomp1);
        cv.put(DBManager.TableInfo.Reg_From_Comp, regfromcomp);
        cv.put(DBManager.TableInfo.Witness, witness);
        cv.put(DBManager.TableInfo.Ship_Address, shipadd);
        cv.put(DBManager.TableInfo.Ship_Diff_Address, shipdiffadd);
        cv.put(DBManager.TableInfo.APPTYPE, apptype);
        cv.put(DBManager.TableInfo.Doc_bio_status, biostatus);
        cv.put(DBManager.TableInfo.s_grn, sgrn);
        cv.put(DBManager.TableInfo.r_grn, rgrn);

        long result = sqlite.update(DBManager.TableInfo.TABLE_NAME_DOCUMENT, cv, selection, a);

        db.close();
    }

    public void Update1(DBOperation db, String appid, String docid, String appaddress, String exeid,
                        String appfor, String distance, String amount, String transporttype, String acvrtstatuas, String value) {
        // TODO Auto-generated method stub

        String selection = DBManager.TableInfo.AppointmentId + " = ? ";

        String a[] = {appid};
        SQLiteDatabase sqlite = db.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBManager.TableInfo.DocumentId, docid);
        cv.put(DBManager.TableInfo.AppointmentAddress, appaddress);
        cv.put(DBManager.TableInfo.Executioner_id, exeid);
        cv.put(DBManager.TableInfo.AppFor, appfor);
        cv.put(DBManager.TableInfo.DISTANCE, distance);
        cv.put(DBManager.TableInfo.AMOUNT, amount);
        cv.put(DBManager.TableInfo.TRANSPORTTYPE, transporttype);
        cv.put(DBManager.TableInfo.SYNCSTATUSREPORT, acvrtstatuas);
        cv.put(DBManager.TableInfo.task_name, value);

        long result = sqlite.update(DBManager.TableInfo.TABLE_NAME_APPOINTMENT, cv, selection, a);

        db.close();
    }

    public void Update2(DBOperation db, String appid, String docid, String appaddress, String exeid,
                        String appfor, String distance, String amount, String transporttype, String acvrtstatuas, String value) {
        // TODO Auto-generated method stub

        String selection = DBManager.TableInfo.db_sales_appointment_id + " = ? ";

        String a[] = {appid};
        SQLiteDatabase sqlite = db.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBManager.TableInfo.db_sales_doc_id, docid);
        cv.put(DBManager.TableInfo.db_sales_address, appaddress);
        cv.put(DBManager.TableInfo.db_sales_executioner_id, exeid);
        cv.put(DBManager.TableInfo.db_sales_app_for, appfor);
        cv.put(DBManager.TableInfo.db_sales_distance, distance);
        cv.put(DBManager.TableInfo.db_sales_amount, amount);
        cv.put(DBManager.TableInfo.db_sales_transtype, transporttype);
        cv.put(DBManager.TableInfo.db_sales_acvrsyncstatus, acvrtstatuas);
        cv.put(DBManager.TableInfo.task_name, value);

        long result = sqlite.update(DBManager.TableInfo.TABLE_SALES_APPOINTMENT, cv, selection, a);

        db.close();
    }

    public void Update3(DBOperation db, String appid, String docid, String appaddress, String exeid,
                        String appfor, String distance, String amount, String transporttype, String acvrtstatuas, String value) {
        // TODO Auto-generated method stub

        String selection = DBManager.TableInfo.db_marriage_appointment_id + " = ? ";

        String a[] = {appid};
        SQLiteDatabase sqlite = db.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBManager.TableInfo.db_marriage_doc_id, docid);
        cv.put(DBManager.TableInfo.db_marriage_address, appaddress);
        cv.put(DBManager.TableInfo.db_marriage_executionerid, exeid);
        cv.put(DBManager.TableInfo.db_marriage_app_for, appfor);
        cv.put(DBManager.TableInfo.db_marriage_distance, distance);
        cv.put(DBManager.TableInfo.db_marriage_acvr_amount, amount);
        cv.put(DBManager.TableInfo.db_marriage_transtype, transporttype);
        cv.put(DBManager.TableInfo.db_marriage_acvrsyncstatus, acvrtstatuas);
        cv.put(DBManager.TableInfo.task_name, value);

        long result = sqlite.update(DBManager.TableInfo.TABLE_MARRIAGE_APPOINTMENT, cv, selection, a);

        db.close();
    }

    public void Updateuser(DBOperation db, String userid, String username, String email, String role, String platformid, String roleid, String idu) {
        String selection = DBManager.TableInfo.User_id + " = ? ";

        String a[] = {userid};

        SQLiteDatabase sqlite = db.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(DBManager.TableInfo.User_id, userid);
        cv.put(DBManager.TableInfo.User_name, username);
        cv.put(DBManager.TableInfo.Email, email);
        cv.put(DBManager.TableInfo.Role, role);
        cv.put(DBManager.TableInfo.platformid, platformid);
        cv.put(DBManager.TableInfo.role_id, roleid);
        cv.put(DBManager.TableInfo.idu, idu);


        long result = sqlite.update(DBManager.TableInfo.TABLE_NAME5, cv, selection, a);


        db.close();
    }


    public void insertLocationDetails(DBOperation dop, HashMap<String, String> map) {

        ContentValues cv = new ContentValues();
        SQLiteDatabase sqlite = dop.getWritableDatabase();
        cv.put(DBManager.TableInfo.latitudeInput, map.get(DBManager.TableInfo.latitudeInput));
        cv.put(DBManager.TableInfo.longitudeInput, map.get(DBManager.TableInfo.longitudeInput));
        cv.put(DBManager.TableInfo.DATE, map.get(DBManager.TableInfo.DATE));
        cv.put(DBManager.TableInfo.UserName, map.get(DBManager.TableInfo.UserName));

        try {
            sqlite.insert(DBManager.TableInfo.TABLE_LOCATION_DETAILS, null, cv);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        dop.close();
    }

    public ArrayList<HashMap<String, String>> getLocationDetailsUsername(DBOperation dop) {

        ArrayList<HashMap<String, String>> listUsername = new ArrayList<>();

        try {
            String query = "SELECT * from " + DBManager.TableInfo.TABLE_LOCATION_DETAILS;
            SQLiteDatabase sqlite = dop.getWritableDatabase();
            Cursor c = sqlite.rawQuery(query, null);
            while (c.moveToNext()) {
                HashMap<String, String> username = new HashMap<>();

                username.put(DBManager.TableInfo.UserName, c.getString(c.getColumnIndex(DBManager.TableInfo.UserName)));
                username.put(DBManager.TableInfo.LATITUDE, c.getString(c.getColumnIndex(DBManager.TableInfo.LATITUDE)));
                username.put(DBManager.TableInfo.LONGITUDE, c.getString(c.getColumnIndex(DBManager.TableInfo.LONGITUDE)));
                username.put(DBManager.TableInfo.DATE, c.getString(c.getColumnIndex(DBManager.TableInfo.DATE)));

                listUsername.add(username);
            }

            closeCursor(c);

        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }
        dop.close();
        return listUsername;
    }

    public void closeCursor(Cursor cur) {

        if (cur != null && !cur.isClosed()) {
            cur.close();
        }

    }

    public void delLocationDetails(DBOperation db) {
        try {
            SQLiteDatabase sqlite = db.getWritableDatabase();
            String query = "DELETE FROM " + DBManager.TableInfo.TABLE_LOCATION_DETAILS;
            sqlite.execSQL(query);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();
    }


    public void delAppointment(DBOperation db) {
        try {
            SQLiteDatabase sqlite = db.getWritableDatabase();
            String query = "DELETE FROM " + DBManager.TableInfo.TABLE_NAME_APPOINTMENT;
            sqlite.execSQL(query);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();
    }

    public void deluser(DBOperation db) {
        try {
            SQLiteDatabase sqlite = db.getWritableDatabase();
            String query = "DELETE FROM " + DBManager.TableInfo.TABLE_NAME1;
            sqlite.execSQL(query);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();
    }


    public void delDocument(DBOperation db) {
        try {
            SQLiteDatabase sqlite = db.getWritableDatabase();
            String query = "DELETE FROM " + DBManager.TableInfo.TABLE_NAME_DOCUMENT;
            sqlite.execSQL(query);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();
    }

    public void delComments(DBOperation db) {
        try {
            SQLiteDatabase sqlite = db.getWritableDatabase();
            String query = "DELETE FROM " + DBManager.TableInfo.TABLE_NAME3;
            sqlite.execSQL(query);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();
    }


    public void delUserRole(DBOperation db) {
        try {
            SQLiteDatabase sqlite = db.getWritableDatabase();
            String query = "DELETE FROM " + DBManager.TableInfo.TABLE_NAME5;
            sqlite.execSQL(query);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();
    }

    public ArrayList<HashMap<String, String>> getSelectedData(DBOperation db, String docid) {
        ArrayList<HashMap<String, String>> listUsername = new ArrayList<>();

        try {

            String query = "SELECT * from " + DBManager.TableInfo.TABLE_NAME_DOCUMENT + " where " + DBManager.TableInfo.DocumentId + "='" + docid + "'";
            SQLiteDatabase sqlite = db.getWritableDatabase();
            Cursor c = sqlite.rawQuery(query, null);

            while (c.moveToNext()) {
                HashMap<String, String> selectiondetails = new HashMap<>();
                selectiondetails.put("OwnBio", c.getString(c.getColumnIndex(DBManager.TableInfo.BiometricComp1)));
                selectiondetails.put("TenantBio", c.getString(c.getColumnIndex(DBManager.TableInfo.BiometricComp)));
                selectiondetails.put("Wit1", c.getString(c.getColumnIndex(DBManager.TableInfo.Reg_From_Comp)));
                selectiondetails.put("Wit2", c.getString(c.getColumnIndex(DBManager.TableInfo.Witness)));
                listUsername.add(selectiondetails);
            }

            closeCursor(c);

        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }
        db.close();
        return listUsername;
    }

    public ArrayList<HashMap<String, String>> getmarriageSelectedData(DBOperation db, String docid) {
        ArrayList<HashMap<String, String>> listUsername = new ArrayList<>();

        try {

            String query = "SELECT * from " + DBManager.TableInfo.TABLE_MARRIAGE + " where " + DBManager.TableInfo.db_marriage_doc_id + "='" + docid + "'";
            SQLiteDatabase sqlite = db.getWritableDatabase();
            Cursor c = sqlite.rawQuery(query, null);

            while (c.moveToNext()) {
                HashMap<String, String> selectiondetails = new HashMap<>();
                selectiondetails.put("husbio", c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_husbandbiometric)));
                selectiondetails.put("wifeybio", c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_wifebiometric)));

                listUsername.add(selectiondetails);
            }

            closeCursor(c);

        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }
        db.close();
        return listUsername;
    }

    public ArrayList<HashMap<String, String>> getSyncStatusReport(DBOperation db) {
        ArrayList<HashMap<String, String>> listUsername = new ArrayList<>();

        try {

            String query = "SELECT * from " + DBManager.TableInfo.TABLE_NAME_DOCUMENT + " where " + DBManager.TableInfo.SYNCSTATUS + "='ASYNC'";
            SQLiteDatabase sqlite = db.getWritableDatabase();
            Cursor c = sqlite.rawQuery(query, null);

            while (c.moveToNext()) {
                HashMap<String, String> selectiondetails = new HashMap<>();
                selectiondetails.put("DocId", c.getString(c.getColumnIndex(DBManager.TableInfo.DocumentId)));
                selectiondetails.put("OwnBio", c.getString(c.getColumnIndex(DBManager.TableInfo.BiometricComp1)));
                selectiondetails.put("TenantBio", c.getString(c.getColumnIndex(DBManager.TableInfo.BiometricComp)));
                selectiondetails.put("Wit1", c.getString(c.getColumnIndex(DBManager.TableInfo.Reg_From_Comp)));
                selectiondetails.put("Wit2", c.getString(c.getColumnIndex(DBManager.TableInfo.Witness)));
                selectiondetails.put("Location", c.getString(c.getColumnIndex(DBManager.TableInfo.updatelocation)));
                selectiondetails.put("currenttime", c.getString(c.getColumnIndex(DBManager.TableInfo.updatetime)));
                selectiondetails.put("appid", c.getString(c.getColumnIndex(DBManager.TableInfo.AppointmentId)));

                listUsername.add(selectiondetails);
            }

            closeCursor(c);

        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }
        db.close();
        return listUsername;
    }

    public ArrayList<HashMap<String, String>> getPaymentReport(DBOperation db) {
        ArrayList<HashMap<String, String>> listUsername = new ArrayList<HashMap<String, String>>();

        try {

            String query = "select * from  " + UPDATEPAYMENT1 + " where " + DBManager.TableInfo.email1 + " = '" + GenericMethods.email + "'";

            SQLiteDatabase sqlite = db.getWritableDatabase();
            Cursor c = sqlite.rawQuery(query, null);

            while (c.moveToNext()) {
                HashMap<String, String> selectiondetails = new HashMap<String, String>();
                selectiondetails.put("docid", c.getString(c.getColumnIndex(DBManager.TableInfo.DOCID)));
                selectiondetails.put("email", c.getString(c.getColumnIndex(DBManager.TableInfo.email1)));
                selectiondetails.put("amount", c.getString(c.getColumnIndex(DBManager.TableInfo.amt)));
                selectiondetails.put("date", c.getString(c.getColumnIndex(DBManager.TableInfo.date)));
                selectiondetails.put("radiotype", c.getString(c.getColumnIndex(DBManager.TableInfo.radiotype)));
                selectiondetails.put("comment", c.getString(c.getColumnIndex(DBManager.TableInfo.comment1)));
                listUsername.add(selectiondetails);
            }

            closeCursor(c);

        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }
        db.close();
        return listUsername;
    }



    public ArrayList<HashMap<String, String>> getpaymentlink(DBOperation db) {
        ArrayList<HashMap<String, String>> listUsername = new ArrayList<>();

        try {

            String query = "select * from  " + TABLE_PAYMENT_LINK + " where " + DBManager.TableInfo.KEY_LOGIN_USER + " = '" + GenericMethods.email + "'";

            SQLiteDatabase sqlite = db.getWritableDatabase();
            Cursor c = sqlite.rawQuery(query, null);

            while (c.moveToNext()) {
                HashMap<String, String> selectiondetails = new HashMap<>();
                selectiondetails.put("docid", c.getString(c.getColumnIndex(DBManager.TableInfo.docid_payment)));
                selectiondetails.put("exec_email", c.getString(c.getColumnIndex(DBManager.TableInfo.exec_email_payment)));
                selectiondetails.put("login_user", c.getString(c.getColumnIndex(DBManager.TableInfo.KEY_LOGIN_USER)));

                listUsername.add(selectiondetails);
            }

            closeCursor(c);

        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }
        db.close();
        return listUsername;
    }

    public ArrayList<HashMap<String, String>> getbiostatus(DBOperation db) {
        ArrayList<HashMap<String, String>> listUsername = new ArrayList<>();

        try {

            String query = "select * from  " + ATT_STATUS + " where " + DBManager.TableInfo.KEY_LOGIN_USER + " = '" + GenericMethods.email + "'";

            SQLiteDatabase sqlite = db.getWritableDatabase();
            Cursor c = sqlite.rawQuery(query, null);

            while (c.moveToNext()) {
                HashMap<String, String> selectiondetails = new HashMap<>();
                selectiondetails.put("documentId", c.getString(c.getColumnIndex(DBManager.TableInfo.DocumentId)));
                selectiondetails.put("bio_status", c.getString(c.getColumnIndex(DBManager.TableInfo.att_status)));

                listUsername.add(selectiondetails);
            }

            closeCursor(c);

        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }
        db.close();
        return listUsername;
    }


    public ArrayList<HashMap<String, String>> getPaymentReport1(DBOperation db) {
        ArrayList<HashMap<String, String>> listUsername = new ArrayList<>();

        try {

            String query = "select * from  " + PAYMENT + " where " + DBManager.TableInfo.KEY_LOGIN_USER + " = '" + GenericMethods.email + "'";

            SQLiteDatabase sqlite = db.getWritableDatabase();
            Cursor c = sqlite.rawQuery(query, null);
            while (c.moveToNext()) {
                HashMap<String, String> selectiondetails = new HashMap<>();
                selectiondetails.put("docid", c.getString(c.getColumnIndex(DBManager.TableInfo.doc1)));
                selectiondetails.put("amount", c.getString(c.getColumnIndex(DBManager.TableInfo.payamount)));
                selectiondetails.put("rkey", c.getString(c.getColumnIndex(DBManager.TableInfo.rep1)));
                listUsername.add(selectiondetails);
            }

            closeCursor(c);

        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }
        db.close();
        return listUsername;
    }

    public ArrayList<HashMap<String, String>> getreassign(DBOperation db) {
        ArrayList<HashMap<String, String>> listUsername = new ArrayList<>();

        try {

            String query = "select * from  " + REASSIGN + " where " + DBManager.TableInfo.KEY_LOGIN_USER + " = '" + GenericMethods.email + "'";

            SQLiteDatabase sqlite = db.getWritableDatabase();
            Cursor c = sqlite.rawQuery(query, null);
            while (c.moveToNext()) {
                HashMap<String, String> selectiondetails = new HashMap<>();
                selectiondetails.put("Prev_owner", c.getString(c.getColumnIndex(DBManager.TableInfo.Prev_owner)));
                selectiondetails.put("new_owner", c.getString(c.getColumnIndex(DBManager.TableInfo.new_owner)));
                selectiondetails.put("reassign_reason", c.getString(c.getColumnIndex(DBManager.TableInfo.reassign_reason)));
                selectiondetails.put("status", c.getString(c.getColumnIndex(DBManager.TableInfo.status1)));
                selectiondetails.put("comment", c.getString(c.getColumnIndex(DBManager.TableInfo.comm1)));
                selectiondetails.put("new_owner1", c.getString(c.getColumnIndex(DBManager.TableInfo.new_owner1)));
                selectiondetails.put("exec_email", c.getString(c.getColumnIndex(DBManager.TableInfo.KEY_LOGIN_USER)));
                selectiondetails.put("taskid", c.getString(c.getColumnIndex(DBManager.TableInfo.taskid1)));

                listUsername.add(selectiondetails);
            }

            closeCursor(c);

        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }
        db.close();
        return listUsername;
    }

    public ArrayList<HashMap<String, String>> getnot_applicable(DBOperation db) {
        ArrayList<HashMap<String, String>> listUsername = new ArrayList<>();

        try {

            String query = "select * from  " + NOT_APPLICABLE + " where " + DBManager.TableInfo.KEY_LOGIN_USER + " = '" + GenericMethods.email + "'";

            SQLiteDatabase sqlite = db.getWritableDatabase();
            Cursor c = sqlite.rawQuery(query, null);
            while (c.moveToNext()) {
                HashMap<String, String> selectiondetails = new HashMap<>();
                selectiondetails.put("task_id", c.getString(c.getColumnIndex(DBManager.TableInfo.task_id1)));
                selectiondetails.put("comment_applicable", c.getString(c.getColumnIndex(DBManager.TableInfo.comment_applicable)));
                selectiondetails.put("reason_applicable", c.getString(c.getColumnIndex(DBManager.TableInfo.notapplicable_reason)));
                selectiondetails.put("comment", c.getString(c.getColumnIndex(DBManager.TableInfo.comm1)));
                selectiondetails.put("not_app", c.getString(c.getColumnIndex(DBManager.TableInfo.not_app)));
                selectiondetails.put("status", c.getString(c.getColumnIndex(DBManager.TableInfo.status1)));
                selectiondetails.put("exec_email", c.getString(c.getColumnIndex(DBManager.TableInfo.KEY_LOGIN_USER)));

                listUsername.add(selectiondetails);
            }

            closeCursor(c);

        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }
        db.close();
        return listUsername;
    }

    public ArrayList<HashMap<String, String>> getattendees(DBOperation db) {
        ArrayList<HashMap<String, String>> listUsername = new ArrayList<>();

        try {

            String query = "select * from  " + DBManager.TableInfo.ATTENDEES + " where " + DBManager.TableInfo.KEY_LOGIN_USER + " = '" + GenericMethods.email + "'";

            SQLiteDatabase sqlite1 = db.getWritableDatabase();
            Cursor c = sqlite1.rawQuery(query, null);

            while (c.moveToNext()) {

                HashMap<String, String> selectiondetails = new HashMap<>();
                selectiondetails.put("name", c.getString(c.getColumnIndex("name")));
                selectiondetails.put("email", c.getString(c.getColumnIndex("email")));
                selectiondetails.put("contact", c.getString(c.getColumnIndex("contact")));
                selectiondetails.put("appoint", c.getString(c.getColumnIndex("appointment")));
                listUsername.add(selectiondetails);
            }

            closeCursor(c);
            sqlite1.close();

        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }
        db.close();
        return listUsername;
    }

    public ArrayList<HashMap<String, String>> getsalesattendees(DBOperation db) {
        ArrayList<HashMap<String, String>> listUsername = new ArrayList<>();

        try {

            String query = "select * from  " + DBManager.TableInfo.TABLE_SALES_ATTENDEES + " where " + DBManager.TableInfo.KEY_LOGIN_USER + " = '" + GenericMethods.email + "'";

            SQLiteDatabase sqlite1 = db.getWritableDatabase();
            Cursor c = sqlite1.rawQuery(query, null);

            while (c.moveToNext()) {

                HashMap<String, String> selectiondetails = new HashMap<>();
                selectiondetails.put("name", c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_name)));
                selectiondetails.put("email", c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_mail)));
                selectiondetails.put("contact", c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_contact)));
                selectiondetails.put("appoint", c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_appointment_id)));
                listUsername.add(selectiondetails);
            }

            closeCursor(c);
            sqlite1.close();

        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }
        db.close();
        return listUsername;
    }

    public ArrayList<HashMap<String, String>> getmarriageattendees(DBOperation db) {
        ArrayList<HashMap<String, String>> listUsername = new ArrayList<>();

        try {

            String query = "select * from  " + DBManager.TableInfo.TABLE_MARRIAGE_ATTENDEES + " where " + DBManager.TableInfo.KEY_LOGIN_USER + " = '" + GenericMethods.email + "'";

            SQLiteDatabase sqlite1 = db.getWritableDatabase();
            Cursor c = sqlite1.rawQuery(query, null);

            while (c.moveToNext()) {

                HashMap<String, String> selectiondetails = new HashMap<>();
                selectiondetails.put("name", c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_name)));
                selectiondetails.put("email", c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_mail)));
                selectiondetails.put("contact", c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_contact)));
                selectiondetails.put("appoint", c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_appointment_id)));
                listUsername.add(selectiondetails);
            }

            closeCursor(c);
            sqlite1.close();

        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }
        db.close();
        return listUsername;
    }

    public String getoldernotification(DBOperation db, String startdate, int position) {

        StringBuilder content = new StringBuilder();
        ArrayList<HashMap<String, String>> listParties = new ArrayList<>();

        try {


            SQLiteDatabase sqlite1 = db.getWritableDatabase();
            Calendar c1 = Calendar.getInstance();
            SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
            String curdate1 = df2.format(c1.getTime());
            int o_count = 0, t_count = 0, poa_count = 0, w_count = 0, AW_count = 0;



            for (int i = 0; i < db.getOlderPartiesReport(db, startdate).size(); i++) {

                if (db.getAllOlderist(db).get(position).get("docid").equals(db.getOlderPartiesReport(db, startdate).get(i).get("document_id"))) {

                    if (Integer.valueOf(db.getOlderPartiesReport(db, startdate).get(i).get("poa")) == 0) {


                        if (db.getAllOlderist(db).get(position).get("post_status").equals("null") || db.getAllOlderist(db).get(position).get("post_status").equals("1")) {

                            if (db.getOlderPartiesReport(db, startdate).get(i).get("biometric").equals("null") || db.getOlderPartiesReport(db, startdate).get(i).get("biometric").equals("false")) {


                                if (db.getOlderPartiesReport(db, startdate).get(i).get("party_type").equals("1")) {
                                    o_count += 1;

                                    String temp = "O" + String.valueOf(o_count);
                                    content.append(temp);


                                }
                                if (db.getOlderPartiesReport(db, startdate).get(i).get("party_type").equals("2")) {


                                    t_count += 1;
                                    String temp = "T" + String.valueOf(t_count);
                                    content.append(temp);


                                }
                                if (db.getOlderPartiesReport(db, startdate).get(i).get("party_type").equals("4")) {
                                    poa_count += 1;
                                    String temp = "POA" + String.valueOf(poa_count);

                                    content.append(temp);


                                }
                                if (db.getOlderPartiesReport(db, startdate).get(i).get("party_type").equals("3")) {
                                    w_count += 1;

                                    String temp = "W" + String.valueOf(w_count);

                                    content.append(temp);


                                }
                                if (db.getOlderPartiesReport(db, startdate).get(i).get("party_type").equals("5")) {
                                    AW_count += 1;
                                    String temp = "AW" + String.valueOf(AW_count);

                                    content.append(temp);


                                }


                                content.append(" Biometric Pending");


                            }

                        }


                    }


                }


            }


        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }
        db.close();

        return content.toString();
    }


    public ArrayList<HashMap<String, String>> gettodaynotification(DBOperation db, String startdate, int position) {


        ArrayList<HashMap<String, String>> listParties = new ArrayList<>();

        try {


            SQLiteDatabase sqlite1 = db.getWritableDatabase();
            Calendar c1 = Calendar.getInstance();
            SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
            String curdate1 = df2.format(c1.getTime());
            int o_count = 0, t_count = 0, poa_count = 0, w_count = 0, AW_count = 0;
            StringBuilder content = new StringBuilder();


            for (int i = 0; i < db.getTodayPartiesReport(db, startdate).size(); i++) {

                if (db.getTodayPartiesReport(db,startdate).get(position).get("docid").equals(db.getTodayPartiesReport(db, startdate).get(i).get("document_id"))) {

                    if (Integer.valueOf(db.getTodayPartiesReport(db, startdate).get(i).get("poa")) == 0) {


                        if (db.getAllTodayList(db).get(position).get("post_status").equals("null") || db.getAllTodayList(db).get(position).get("post_status").equals("1")) {

                            if (db.getTodayPartiesReport(db, startdate).get(i).get("biometric").equals("null") || db.getTodayPartiesReport(db, startdate).get(i).get("biometric").equals("false")) {


                                if (db.getTodayPartiesReport(db, startdate).get(i).get("party_type").equals("1")) {
                                    o_count += 1;

                                    String temp = "O" + String.valueOf(o_count);
                                    content.append(temp);


                                }
                                if (db.getTodayPartiesReport(db, startdate).get(i).get("party_type").equals("2")) {


                                    t_count += 1;
                                    String temp = "T" + String.valueOf(t_count);
                                    content.append(temp);


                                }
                                if (db.getTodayPartiesReport(db, startdate).get(i).get("party_type").equals("4")) {
                                    poa_count += 1;
                                    String temp = "POA" + String.valueOf(poa_count);

                                    content.append(temp);


                                }
                                if (db.getTodayPartiesReport(db, startdate).get(i).get("party_type").equals("3")) {
                                    w_count += 1;

                                    String temp = "W" + String.valueOf(w_count);

                                    content.append(temp);


                                }
                                if (db.getTodayPartiesReport(db, startdate).get(i).get("party_type").equals("5")) {
                                    AW_count += 1;
                                    String temp = "AW" + String.valueOf(AW_count);

                                    content.append(temp);


                                }


                                content.append(" Biometric Pending");


                            }

                        }


                    }


                }


            }


        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }
        db.close();

        return listParties;
    }


    public ArrayList<HashMap<String, String>> getTodayPartiesReport(DBOperation db, String startdate) {


        ArrayList<HashMap<String, String>> listParties = new ArrayList<>();

        try {

            String selectQuery = "SELECT  * FROM " + DBManager.TableInfo.TABLE_NAME_DOCUMENT + " t1, " + DBManager.TableInfo.PARTIES + " t2 WHERE "
                    + "t1." + DBManager.TableInfo.DocumentId + " = " + "t2." + DBManager.TableInfo.DOCUMENT;
            SQLiteDatabase sqlite1 = db.getWritableDatabase();
            Cursor c = sqlite1.rawQuery(selectQuery, null);
            Calendar c1 = Calendar.getInstance();
            SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
            String curdate1 = df2.format(c1.getTime());
            while (c.moveToNext()) {
                String bio = c.getString(c.getColumnIndex(DBManager.TableInfo.BIOMETRIC));
                String status = c.getString(c.getColumnIndex(DBManager.TableInfo.Doc_bio_status));

                String biometric = "", docid = "";
                if ((curdate1.equals(startdate))) {
                    HashMap<String, String> selectiondetails1 = new HashMap<>();

                    selectiondetails1.put("document_id", c.getString(c.getColumnIndex(DOCUMENT)));
                    docid = c.getString(c.getColumnIndex(DOCUMENT));
                    selectiondetails1.put("att_id", c.getString(c.getColumnIndex(DBManager.TableInfo.ATTENDANCE)));
                    selectiondetails1.put("name", c.getString(c.getColumnIndex(DBManager.TableInfo.NAMENEW)));
                    selectiondetails1.put("email", c.getString(c.getColumnIndex(DBManager.TableInfo.EMAILNEW)));
                    selectiondetails1.put("party_type", c.getString(c.getColumnIndex(DBManager.TableInfo.PARTYTYPE)));
                    selectiondetails1.put("poa", c.getString(c.getColumnIndex(DBManager.TableInfo.POA)));
                    selectiondetails1.put("contact_no", c.getString(c.getColumnIndex(DBManager.TableInfo.CONTACT)));
                    selectiondetails1.put("biometric", c.getString(c.getColumnIndex(DBManager.TableInfo.BIOMETRIC)));
                    biometric = c.getString(c.getColumnIndex(DBManager.TableInfo.BIOMETRIC));
                    selectiondetails1.put("party_address", c.getString(c.getColumnIndex(DBManager.TableInfo.PARTY_ADDRESS)));
                    listParties.add(selectiondetails1);


                }
            }

            closeCursor(c);

        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }
        db.close();

        return listParties;
    }

    public ArrayList<HashMap<String, String>> getOlderPartiesReport(DBOperation db, String startdate) {


        ArrayList<HashMap<String, String>> listParties = new ArrayList<>();

        try {

            String selectQuery = "SELECT  * FROM " + DBManager.TableInfo.TABLE_NAME_DOCUMENT + " t1, " + DBManager.TableInfo.PARTIES + " t2 WHERE "
                    + "t1." + DBManager.TableInfo.DocumentId + " = " + "t2." + DBManager.TableInfo.DOCUMENT;

            SQLiteDatabase sqlite1 = db.getWritableDatabase();
            Cursor c = sqlite1.rawQuery(selectQuery, null);

            Calendar c1 = Calendar.getInstance();
            SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
            String curdate1 = df2.format(c1.getTime());

            Date date1 = null, date2 = null;
            while (c.moveToNext()) {


                String bio = c.getString(c.getColumnIndex(DBManager.TableInfo.BIOMETRIC));
                String status = c.getString(c.getColumnIndex(DBManager.TableInfo.Doc_bio_status));
                String biometric = "", docid = "";
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    date1 = dateFormat.parse(startdate);

                    date2 = dateFormat.parse(curdate1);

                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if ((date1.before(date2))) {
                    HashMap<String, String> selectiondetails1 = new HashMap<>();

                    selectiondetails1.put("document_id", c.getString(c.getColumnIndex(DOCUMENT)));

                    selectiondetails1.put("att_id", c.getString(c.getColumnIndex(DBManager.TableInfo.ATTENDANCE)));
                    selectiondetails1.put("name", c.getString(c.getColumnIndex(DBManager.TableInfo.NAMENEW)));
                    selectiondetails1.put("email", c.getString(c.getColumnIndex(DBManager.TableInfo.EMAILNEW)));
                    selectiondetails1.put("party_type", c.getString(c.getColumnIndex(DBManager.TableInfo.PARTYTYPE)));
                    selectiondetails1.put("poa", c.getString(c.getColumnIndex(DBManager.TableInfo.POA)));
                    selectiondetails1.put("contact_no", c.getString(c.getColumnIndex(DBManager.TableInfo.CONTACT)));
                    selectiondetails1.put("biometric", c.getString(c.getColumnIndex(DBManager.TableInfo.BIOMETRIC)));

                    selectiondetails1.put("party_address", c.getString(c.getColumnIndex(DBManager.TableInfo.PARTY_ADDRESS)));


                    listParties.add(selectiondetails1);


                }


            }
            c.close();
        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }

        db.close();

        return listParties;
    }

    public ArrayList<HashMap<String, String>> getNewPartiesReport(DBOperation db, String startdate) {

        ArrayList<HashMap<String, String>> listParties = new ArrayList<>();

        try {

            String selectQuery = "SELECT  * FROM " + DBManager.TableInfo.TABLE_NAME_DOCUMENT + " t1, " + DBManager.TableInfo.PARTIES + " t2 WHERE "
                    + "t1." + DBManager.TableInfo.DocumentId + " = " + "t2." + DBManager.TableInfo.DOCUMENT;
            SQLiteDatabase sqlite1 = db.getWritableDatabase();
            Cursor c = sqlite1.rawQuery(selectQuery, null);
            Calendar c1 = Calendar.getInstance();
            SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");

            String curdate1 = df2.format(c1.getTime());
            Date date1 = null, date2 = null;
            while (c.moveToNext()) {
                String biometric = "", docid = "";
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    date1 = dateFormat.parse(startdate);
                    date2 = dateFormat.parse(curdate1);

                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (date1.after(date2)) {
                    HashMap<String, String> selectiondetails1 = new HashMap<>();
                    selectiondetails1.put("document_id", c.getString(c.getColumnIndex(DOCUMENT)));

                    selectiondetails1.put("att_id", c.getString(c.getColumnIndex(DBManager.TableInfo.ATTENDANCE)));
                    selectiondetails1.put("name", c.getString(c.getColumnIndex(DBManager.TableInfo.NAMENEW)));
                    selectiondetails1.put("email", c.getString(c.getColumnIndex(DBManager.TableInfo.EMAILNEW)));
                    selectiondetails1.put("party_type", c.getString(c.getColumnIndex(DBManager.TableInfo.PARTYTYPE)));
                    selectiondetails1.put("poa", c.getString(c.getColumnIndex(DBManager.TableInfo.POA)));
                    selectiondetails1.put("contact_no", c.getString(c.getColumnIndex(DBManager.TableInfo.CONTACT)));
                    selectiondetails1.put("biometric", c.getString(c.getColumnIndex(DBManager.TableInfo.BIOMETRIC)));

                    selectiondetails1.put("party_address", c.getString(c.getColumnIndex(DBManager.TableInfo.PARTY_ADDRESS)));

                    listParties.add(selectiondetails1);


                }
            }

            closeCursor(c);

        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }
        db.close();

        return listParties;
    }

    public ArrayList<HashMap<String, String>> getAllPartiesReport(DBOperation db, String startdate) {


        ArrayList<HashMap<String, String>> listParties = new ArrayList<>();

        try {

            String selectQuery = "SELECT  * FROM " + DBManager.TableInfo.TABLE_NAME_DOCUMENT + " t1, " + DBManager.TableInfo.PARTIES + " t2 WHERE "
                    + "t1." + DBManager.TableInfo.DocumentId + " = " + "t2." + DBManager.TableInfo.DOCUMENT;
            SQLiteDatabase sqlite1 = db.getWritableDatabase();
            Cursor c = sqlite1.rawQuery(selectQuery, null);
            Calendar c1 = Calendar.getInstance();
            SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
            String curdate1 = df2.format(c1.getTime());
            Date date = null, date1 = null;
            while (c.moveToNext()) {
                String bio = c.getString(c.getColumnIndex(DBManager.TableInfo.BIOMETRIC));
                String status = c.getString(c.getColumnIndex(DBManager.TableInfo.Doc_bio_status));
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String biometric = "", docid;
                try {
                    date = dateFormat.parse(startdate);

                    date1 = dateFormat.parse(curdate1);

                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if ((!status.equals("Agreement Preparation")) && (!status.equals("Ready For Biometric")) && (date1.after(date))) {


                    HashMap<String, String> selectiondetails1 = new HashMap<>();
                    selectiondetails1.put("document_id", c.getString(c.getColumnIndex(DOCUMENT)));
                    selectiondetails1.put("att_id", c.getString(c.getColumnIndex(DBManager.TableInfo.ATTENDANCE)));
                    selectiondetails1.put("name", c.getString(c.getColumnIndex(DBManager.TableInfo.NAMENEW)));
                    selectiondetails1.put("email", c.getString(c.getColumnIndex(DBManager.TableInfo.EMAILNEW)));
                    selectiondetails1.put("party_type", c.getString(c.getColumnIndex(DBManager.TableInfo.PARTYTYPE)));
                    selectiondetails1.put("poa", c.getString(c.getColumnIndex(DBManager.TableInfo.POA)));
                    selectiondetails1.put("contact_no", c.getString(c.getColumnIndex(DBManager.TableInfo.CONTACT)));
                    selectiondetails1.put("biometric", c.getString(c.getColumnIndex(DBManager.TableInfo.BIOMETRIC)));
                    selectiondetails1.put("party_address", c.getString(c.getColumnIndex(DBManager.TableInfo.PARTY_ADDRESS)));
                    listParties.add(selectiondetails1);


                }
            }

            closeCursor(c);

        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }
        db.close();

        return listParties;
    }


    public ArrayList<HashMap<String, String>> getinternalexternalpost(DBOperation db) {
        ArrayList<HashMap<String, String>> listParties11 = new ArrayList<>();

        try {

            String query = "select * from " + DBManager.TableInfo.TABLE_WITNESS + " where " + DBManager.TableInfo.KEY_LOGIN_USER + " = '" + GenericMethods.email + "'";

            SQLiteDatabase sqlite1 = db.getWritableDatabase();
            Cursor c = sqlite1.rawQuery(query, null);

            while (c.moveToNext()) {

                HashMap<String, String> selectiondetails10 = new HashMap<>();
                selectiondetails10.put("document_id", c.getString(c.getColumnIndex(DBManager.TableInfo.witness_docid)));
                selectiondetails10.put("user_id1", c.getString(c.getColumnIndex(DBManager.TableInfo.internal_witness_userid1)));
                selectiondetails10.put("email1", c.getString(c.getColumnIndex(DBManager.TableInfo.internal_witness_email1)));
                selectiondetails10.put("user_id2", c.getString(c.getColumnIndex(DBManager.TableInfo.internal_witness_userid2)));
                selectiondetails10.put("email2", c.getString(c.getColumnIndex(DBManager.TableInfo.internal_witness_email2)));
                selectiondetails10.put("type", c.getString(c.getColumnIndex(DBManager.TableInfo.internal_witness_type)));
                selectiondetails10.put("type1", c.getString(c.getColumnIndex(DBManager.TableInfo.external_witness_type)));
                selectiondetails10.put("witness_no1", c.getString(c.getColumnIndex(DBManager.TableInfo.external_witness_email1)));
                selectiondetails10.put("witness_no2", c.getString(c.getColumnIndex(DBManager.TableInfo.external_witness_email2)));
                selectiondetails10.put("internal_bio1", c.getString(c.getColumnIndex(DBManager.TableInfo.internal_biometric1)));
                selectiondetails10.put("internal_bio2", c.getString(c.getColumnIndex(DBManager.TableInfo.internal_biometric2)));
                selectiondetails10.put("external_bio1", c.getString(c.getColumnIndex(DBManager.TableInfo.external_biometric1)));
                selectiondetails10.put("external_bio2", c.getString(c.getColumnIndex(DBManager.TableInfo.external_biometric2)));
                selectiondetails10.put("value", c.getString(c.getColumnIndex(DBManager.TableInfo.radiovalue)));
                selectiondetails10.put("exec_email", c.getString(c.getColumnIndex(DBManager.TableInfo.KEY_LOGIN_USER)));

                listParties11.add(selectiondetails10);

            }

            closeCursor(c);

        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }
        db.close();

        return listParties11;
    }


    public ArrayList<HashMap<String, String>> getAllpenaltydocuments(DBOperation db) {

        ArrayList<HashMap<String, String>> getAllList = new ArrayList<>();
        SQLiteDatabase sqlite = db.getWritableDatabase();
        String query = "select * from " + DBManager.TableInfo.TABLE_PENALTY + " where " + DBManager.TableInfo.KEY_LOGIN_USER + " = '" + GenericMethods.email + "'";

        Cursor c = sqlite.rawQuery(query, null);
        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        String curdate1 = df2.format(c1.getTime());

        if (c.moveToFirst()) {

            c.moveToFirst();
            do {

                HashMap<String, String> getAllListMap = new HashMap<>();

                String penalty_id=c.getString(c.getColumnIndex(DBManager.TableInfo.db_penalty_id));
                String penalty_appid=c.getString(c.getColumnIndex(DBManager.TableInfo.db_penalty_appointment_id));
                String penalty_docid=c.getString(c.getColumnIndex(DBManager.TableInfo.db_penalty_doc_id));
                String penalty_exeid=c.getString(c.getColumnIndex(DBManager.TableInfo.db_penalty_exec_id));
                String penalty_amount=c.getString(c.getColumnIndex(DBManager.TableInfo.db_penalty_amount));
                String  penalty_verify=c.getString(c.getColumnIndex(DBManager.TableInfo.db_penalty_verify));
                String penalty_type=c.getString(c.getColumnIndex(DBManager.TableInfo.db_penalty_type));
                String penalty_manager_comment=c.getString(c.getColumnIndex(DBManager.TableInfo.db_penalty_manager_comment));
                String penalty_sysyte_reason=c.getString(c.getColumnIndex(DBManager.TableInfo.db_penalty_system_reason));
                String penalty_created_at=c.getString(c.getColumnIndex(DBManager.TableInfo.db_penalty_created_at));
                String penalty_env=c.getString(c.getColumnIndex(DBManager.TableInfo.db_penalty_env));

                getAllListMap.put("penaltyid", penalty_id);
                getAllListMap.put("penaltyappid", penalty_appid);
                getAllListMap.put("penaltydocid", penalty_docid);
                getAllListMap.put("penaltyexeid", penalty_exeid);
                getAllListMap.put("penaltyamount", penalty_amount);
                getAllListMap.put("penaltyverify", penalty_verify);
                getAllListMap.put("penaltytype", penalty_type);
                getAllListMap.put("penaltymanagercomment", penalty_manager_comment);
                getAllListMap.put("penaltysysytereason", penalty_sysyte_reason);
                getAllListMap.put("penaltycreatedat", penalty_created_at);
                getAllListMap.put("penaltyenv", penalty_env);


                getAllList.add(getAllListMap);


            } while (c.moveToNext());

        }
        c.close();
        db.close();

        return getAllList;
    }
    public ArrayList<HashMap<String, String>> getpendingwitness(DBOperation db) {

        ArrayList<HashMap<String, String>> getAllList = new ArrayList<>();
        SQLiteDatabase sqlite = db.getWritableDatabase();
        String query = "select * from " + DBManager.TableInfo.TABLE_PENDINGWITNESS + " where " + DBManager.TableInfo.KEY_LOGIN_USER + " = '" + GenericMethods.email + "'";
        Cursor c = sqlite.rawQuery(query, null);
        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        String curdate1 = df2.format(c1.getTime());

        if (c.moveToFirst()) {

            c.moveToFirst();
            do {
                HashMap<String, String> getAllListMap = new HashMap<>();
                String docid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_PW_doc_id));
                String env = c.getString(c.getColumnIndex(DBManager.TableInfo.db_PW_env));
                String tokenno = c.getString(c.getColumnIndex(DBManager.TableInfo.db_PW_token_no));
                String party_type = c.getString(c.getColumnIndex(DBManager.TableInfo.db_PW_partytype));
                String att_id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_PW_attid));
                String witness_name = c.getString(c.getColumnIndex(DBManager.TableInfo.db_PW_witnessname));
                String witness_biometric = c.getString(c.getColumnIndex(DBManager.TableInfo.db_PW_biometric));
                String witness_email = c.getString(c.getColumnIndex(DBManager.TableInfo.db_PW_email));
                String witness_contact = c.getString(c.getColumnIndex(DBManager.TableInfo.db_PW_contact));
                String name = c.getString(c.getColumnIndex(DBManager.TableInfo.db_PW_name));
                String ocontact = c.getString(c.getColumnIndex(DBManager.TableInfo.db_PW_ocontact));
                String oemail = c.getString(c.getColumnIndex(DBManager.TableInfo.db_PW_oemail));
                String oname = c.getString(c.getColumnIndex(DBManager.TableInfo.db_PW_oname));
                String oaddress = c.getString(c.getColumnIndex(DBManager.TableInfo.db_PW_oaddress));
                String tcontact = c.getString(c.getColumnIndex(DBManager.TableInfo.db_PW_tcontact));
                String temail = c.getString(c.getColumnIndex(DBManager.TableInfo.db_PW_temail));
                String tname = c.getString(c.getColumnIndex(DBManager.TableInfo.db_PW_tname));
                String taddress = c.getString(c.getColumnIndex(DBManager.TableInfo.db_PW_taddress));
                String Paddress = c.getString(c.getColumnIndex(DBManager.TableInfo.db_PW_Paddress));
                String from_dt = c.getString(c.getColumnIndex(DBManager.TableInfo.db_PW_from_dt));
                String to_dt = c.getString(c.getColumnIndex(DBManager.TableInfo.db_PW_to_dt));
                String rent = c.getString(c.getColumnIndex(DBManager.TableInfo.db_PW_rent));
                String deposit = c.getString(c.getColumnIndex(DBManager.TableInfo.db_PW_deposit));
                String refunddeposit = c.getString(c.getColumnIndex(DBManager.TableInfo.db_PW_refunddeposit));
                String city_name = c.getString(c.getColumnIndex(DBManager.TableInfo.db_PW_city_name));
                String status_id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_PW_status_id));
                String status = c.getString(c.getColumnIndex(DBManager.TableInfo.db_PW_status));
                String id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_PW_id));
                String agreement_cancle = c.getString(c.getColumnIndex(DBManager.TableInfo.db_PW_agreement_cancle));


                getAllListMap.put("docid", docid);
                getAllListMap.put("env", env);
                getAllListMap.put("tokenno", tokenno);
                getAllListMap.put("partytype", party_type);
                getAllListMap.put("attid", att_id);
                getAllListMap.put("witness_name", witness_name);
                getAllListMap.put("witness_biometric", witness_biometric);
                getAllListMap.put("witness_email", witness_email);
                getAllListMap.put("witness_contact", witness_contact);
                getAllListMap.put("name", name);
                getAllListMap.put("ocontact", ocontact);
                getAllListMap.put("oemail", oemail);
                getAllListMap.put("oname", oname);
                getAllListMap.put("oaddress", oaddress);
                getAllListMap.put("tcontact", tcontact);
                getAllListMap.put("temail", temail);
                getAllListMap.put("tname", tname);
                getAllListMap.put("taddress", taddress);
                getAllListMap.put("Paddress", Paddress);
                getAllListMap.put("from_dt", from_dt);
                getAllListMap.put("to_dt", to_dt);
                getAllListMap.put("rent", rent);
                getAllListMap.put("deposit", deposit);
                getAllListMap.put("refunddeposit", refunddeposit);
                getAllListMap.put("city_name", city_name);
                getAllListMap.put("status_id", status_id);
                getAllListMap.put("status", status);
                getAllListMap.put("id", id);
                getAllListMap.put("agreement_cancle", agreement_cancle);


                getAllList.add(getAllListMap);


            } while (c.moveToNext());

        }
        c.close();
        db.close();

        return getAllList;
    }
    public ArrayList<HashMap<String, String>> postpendingwitness(DBOperation db) {
        ArrayList<HashMap<String, String>> listParties11 = new ArrayList<>();

        try {

            String query = "select * from " + DBManager.TableInfo.TABLE_UPDATE_PENDING_WITNESS + " where " + DBManager.TableInfo.execemail + " = '" + GenericMethods.email + "'";

            SQLiteDatabase sqlite1 = db.getWritableDatabase();
            Cursor c = sqlite1.rawQuery(query, null);

            while (c.moveToNext()) {

                HashMap<String, String> selectiondetails10 = new HashMap<>();
                selectiondetails10.put("execemail", c.getString(c.getColumnIndex(DBManager.TableInfo.execemail)));
                selectiondetails10.put("docu_id", c.getString(c.getColumnIndex(DBManager.TableInfo.pw_docid)));
                selectiondetails10.put("att_id", c.getString(c.getColumnIndex(DBManager.TableInfo.pw_attid)));
                selectiondetails10.put("email", c.getString(c.getColumnIndex(DBManager.TableInfo.pw_email)));
                selectiondetails10.put("partytype", c.getString(c.getColumnIndex(DBManager.TableInfo.pw_partytype)));
                selectiondetails10.put("biometric", c.getString(c.getColumnIndex(DBManager.TableInfo.pw_biometric)));

                listParties11.add(selectiondetails10);

            }

            closeCursor(c);

        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }
        db.close();

        return listParties11;
    }

    public ArrayList<HashMap<String, String>> getPartypost(DBOperation db) {
        ArrayList<HashMap<String, String>> listParties11 = new ArrayList<>();

        try {

            String query = "select * from " + DBManager.TableInfo.UPDATEPARTY + " where " + DBManager.TableInfo.KEY_LOGIN_USER + " = '" + GenericMethods.email + "'";

            SQLiteDatabase sqlite1 = db.getWritableDatabase();
            Cursor c = sqlite1.rawQuery(query, null);

            while (c.moveToNext()) {
                HashMap<String, String> selectiondetails10 = new HashMap<>();
                selectiondetails10.put("document_id", c.getString(c.getColumnIndex(DBManager.TableInfo.DOCU)));
                selectiondetails10.put("att_id", c.getString(c.getColumnIndex(DBManager.TableInfo.ATTEND)));
                selectiondetails10.put("email", c.getString(c.getColumnIndex(DBManager.TableInfo.EMAIL)));
                selectiondetails10.put("party_type", c.getString(c.getColumnIndex(DBManager.TableInfo.PARTY)));
                selectiondetails10.put("biometric", c.getString(c.getColumnIndex(DBManager.TableInfo.BIO)));
                selectiondetails10.put("exec_email", c.getString(c.getColumnIndex(DBManager.TableInfo.KEY_LOGIN_USER)));
                selectiondetails10.put("location", c.getString(c.getColumnIndex(DBManager.TableInfo.updatelocation)));
                selectiondetails10.put("currenttime", c.getString(c.getColumnIndex(DBManager.TableInfo.updatetime)));
                selectiondetails10.put("appid", c.getString(c.getColumnIndex(DBManager.TableInfo.AppointmentId)));
                selectiondetails10.put("internal_wit1", c.getString(c.getColumnIndex(DBManager.TableInfo.internal_witness1)));
                selectiondetails10.put("internal_wit2", c.getString(c.getColumnIndex(DBManager.TableInfo.internal_witness2)));
                selectiondetails10.put("external_wit1", c.getString(c.getColumnIndex(DBManager.TableInfo.external_witness1)));
                selectiondetails10.put("external_wit2", c.getString(c.getColumnIndex(DBManager.TableInfo.external_witness2)));
                selectiondetails10.put("biotype", c.getString(c.getColumnIndex(DBManager.TableInfo.biotype)));

                listParties11.add(selectiondetails10);

            }

            closeCursor(c);

        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }
        db.close();

        return listParties11;
    }

    public ArrayList<HashMap<String, String>> postappointment(DBOperation db) {
        ArrayList<HashMap<String, String>> listParties11 = new ArrayList<>();

        try {

            String query = "select * from " + DBManager.TableInfo.APPOINTMENTBOOKING + " where " + DBManager.TableInfo.KEY_LOGIN_USER + " = '" + GenericMethods.email + "'";
            SQLiteDatabase sqlite1 = db.getWritableDatabase();
            Cursor c = sqlite1.rawQuery(query, null);
            while (c.moveToNext()) {
                HashMap<String, String> selectiondetails10 = new HashMap<>();

                selectiondetails10.put("user", c.getString(c.getColumnIndex(DBManager.TableInfo.KEY_LOGIN_USER)));
                selectiondetails10.put("request_no", c.getString(c.getColumnIndex(DBManager.TableInfo.request_no)));
                selectiondetails10.put("slot_id", c.getString(c.getColumnIndex(DBManager.TableInfo.slotid)));
                selectiondetails10.put("division_id", c.getString(c.getColumnIndex(DBManager.TableInfo.division_id)));
                selectiondetails10.put("app_date", c.getString(c.getColumnIndex(DBManager.TableInfo.app_date)));
                selectiondetails10.put("region_id", c.getString(c.getColumnIndex(DBManager.TableInfo.region_id)));
                selectiondetails10.put("free", c.getString(c.getColumnIndex(DBManager.TableInfo.free)));
                selectiondetails10.put("free_reason", c.getString(c.getColumnIndex(DBManager.TableInfo.free_reason)));
                selectiondetails10.put("attendees", c.getString(c.getColumnIndex(DBManager.TableInfo.attendees)));
                selectiondetails10.put("attendeesmail", c.getString(c.getColumnIndex(DBManager.TableInfo.attendeesemail)));
                selectiondetails10.put("attendeescontact", c.getString(c.getColumnIndex(DBManager.TableInfo.attendeescontact)));
                selectiondetails10.put("address", c.getString(c.getColumnIndex(DBManager.TableInfo.address)));
                selectiondetails10.put("landmark", c.getString(c.getColumnIndex(landmark)));
                selectiondetails10.put("contact_person", c.getString(c.getColumnIndex(DBManager.TableInfo.contactperson)));
                selectiondetails10.put("contact_personmail", c.getString(c.getColumnIndex(DBManager.TableInfo.contactpersonemail)));
                selectiondetails10.put("contact_personcontact", c.getString(c.getColumnIndex(DBManager.TableInfo.contactpersoncont)));
                selectiondetails10.put("city", c.getString(c.getColumnIndex(DBManager.TableInfo.city)));
                selectiondetails10.put("appointment_id", c.getString(c.getColumnIndex(DBManager.TableInfo.AppointmentId)));
                selectiondetails10.put("document_id", c.getString(c.getColumnIndex(DBManager.TableInfo.timenew)));


                listParties11.add(selectiondetails10);

            }

            closeCursor(c);

        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }
        db.close();
        return listParties11;
    }

    public List<String> getPostDoc(DBOperation db) {
        List<String> postlist = new ArrayList<>();

        try {

            String query = "select * from " + DBManager.TableInfo.POSTDOC + " where " + DBManager.TableInfo.KEY_LOGIN_USER + " = '" + GenericMethods.email + "'";

            SQLiteDatabase sqlite1 = db.getWritableDatabase();
            Cursor c = sqlite1.rawQuery(query, null);

            while (c.moveToNext()) {

                postlist.add(c.getString(c.getColumnIndex(DBManager.TableInfo.postdocument)));
            }
            closeCursor(c);

        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }
        db.close();
        return postlist;
    }

    public List<String> getPostwitnessDoc(DBOperation db) {
        List<String> postlist = new ArrayList<>();

        try {

            String query = "select * from " + DBManager.TableInfo.POSTWITNESSDOC + " where " + DBManager.TableInfo.KEY_LOGIN_USER + " = '" + GenericMethods.email + "'";

            SQLiteDatabase sqlite1 = db.getWritableDatabase();
            Cursor c = sqlite1.rawQuery(query, null);

            while (c.moveToNext()) {

                postlist.add(c.getString(c.getColumnIndex(DBManager.TableInfo.postwitnessdocument)));
            }
            closeCursor(c);

        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }
        db.close();

        return postlist;
    }


    public ArrayList<HashMap<String, String>> getadhocdetails(DBOperation db) {
        ArrayList<HashMap<String, String>> listadhoc = new ArrayList<>();

        try {


            String query = "select * from " + DBManager.TableInfo.TABLE_TASK + " where " + DBManager.TableInfo.TASK_NAME + " = '" + "MyTask" + "'";


            SQLiteDatabase sqlite1 = db.getWritableDatabase();
            Cursor c = sqlite1.rawQuery(query, null);

            Calendar c1 = Calendar.getInstance();
            SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
            String curdate1 = df2.format(c1.getTime());
            Date date = null, date1 = null;
            while (c.moveToNext()) {

                HashMap<String, String> selectiondetails1 = new HashMap<>();

                String id1 = c.getString(c.getColumnIndex(DBManager.TableInfo.WITID));
                String comment1 = c.getString(c.getColumnIndex(DBManager.TableInfo.COMMENT));
                String isdone = c.getString(c.getColumnIndex(DBManager.TableInfo.IS_DONE));
                String docid = c.getString(c.getColumnIndex(DBManager.TableInfo.DOC));
                String createdat = c.getString(c.getColumnIndex(DBManager.TableInfo.CREATE));
                String remainder;
                if (c.getString(c.getColumnIndex(DBManager.TableInfo.REMAINDER)).equals("null")) {
                    remainder = c.getString(c.getColumnIndex(DBManager.TableInfo.REMAINDER));
                } else {
                    remainder = c.getString(c.getColumnIndex(DBManager.TableInfo.REMAINDER)).substring(0, 10);

                }

                String status = c.getString(c.getColumnIndex(DBManager.TableInfo.status1));
                String assignby = c.getString(c.getColumnIndex(DBManager.TableInfo.ASSIGN));
                String taskid = c.getString(c.getColumnIndex(DBManager.TableInfo.TASK_ID));
                String notify = c.getString(c.getColumnIndex(DBManager.TableInfo.notification));
                String PW_docid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_PW_doc_id));
                String PW_env = c.getString(c.getColumnIndex(DBManager.TableInfo.db_PW_env));
                String PW_tokenno = c.getString(c.getColumnIndex(DBManager.TableInfo.db_PW_token_no));
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    if (!remainder.equals("null")) {
                        date = dateFormat.parse(remainder);

                        date1 = dateFormat.parse(curdate1);
                    }
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if ((remainder.equals("null")) || (date.before(date1)) || (date.equals(date1))) {

                    selectiondetails1.put("id1", id1);
                    selectiondetails1.put("comment", comment1);
                    selectiondetails1.put("is_done", isdone);
                    selectiondetails1.put("document_id", docid);
                    selectiondetails1.put("created_at", createdat);
                    selectiondetails1.put("reminder_dt", remainder);
                    selectiondetails1.put("status", status);
                    selectiondetails1.put("assign_by", assignby);
                    selectiondetails1.put("task_id", taskid);
                    selectiondetails1.put("notify", notify);
                    selectiondetails1.put("Pw_docid", PW_docid);
                    selectiondetails1.put("Pw_env", PW_env);
                    selectiondetails1.put("tokenno", PW_tokenno);
                    listadhoc.add(selectiondetails1);
                }
            }

            closeCursor(c);

        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }

        db.close();

        return listadhoc;

    }

    public ArrayList<HashMap<String, String>> getadhocdetails1(DBOperation db) {
        ArrayList<HashMap<String, String>> listadhoc = new ArrayList<>();

        try {


            String docid1 = "";
            String query = "select * from " + DBManager.TableInfo.TABLE_TASK + " where " + DBManager.TableInfo.TASK_NAME + " = '" + "MyTask" + "'";

            SQLiteDatabase sqlite1 = db.getWritableDatabase();
            Cursor c = sqlite1.rawQuery(query, null);

            Calendar c1 = Calendar.getInstance();
            SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
            String curdate1 = df2.format(c1.getTime());
            Date date = null, date1 = null;
            while (c.moveToNext()) {

                HashMap<String, String> selectiondetails1 = new HashMap<>();
                String id1 = c.getString(c.getColumnIndex(DBManager.TableInfo.WITID));
                String comment1 = c.getString(c.getColumnIndex(DBManager.TableInfo.COMMENT));
                String isdone = c.getString(c.getColumnIndex(DBManager.TableInfo.IS_DONE));
                String docid = c.getString(c.getColumnIndex(DBManager.TableInfo.DOC));
                String createdat = c.getString(c.getColumnIndex(DBManager.TableInfo.CREATE));
                String remainder;
                if (c.getString(c.getColumnIndex(DBManager.TableInfo.REMAINDER)).equals("null")) {
                    remainder = c.getString(c.getColumnIndex(DBManager.TableInfo.REMAINDER));
                } else {
                    remainder = c.getString(c.getColumnIndex(DBManager.TableInfo.REMAINDER)).substring(0, 10);

                }
                String status = c.getString(c.getColumnIndex(DBManager.TableInfo.status1));
                String assignby = c.getString(c.getColumnIndex(DBManager.TableInfo.ASSIGN));
                String taskid = c.getString(c.getColumnIndex(DBManager.TableInfo.TASK_ID));
                String notify = c.getString(c.getColumnIndex(DBManager.TableInfo.notification));
                String PW_docid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_PW_doc_id));
                String PW_env = c.getString(c.getColumnIndex(DBManager.TableInfo.db_PW_env));
                String PW_tokenno = c.getString(c.getColumnIndex(DBManager.TableInfo.db_PW_token_no));
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                try {
                    if (!remainder.equals("null")) {
                        date = dateFormat.parse(remainder);

                        date1 = dateFormat.parse(curdate1);
                    }
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (docid.substring(0, 1).toLowerCase().equals("a")) {


                    docid1 = "a" + docid.substring(2);

                } else if (docid.substring(0, 1).toLowerCase().equals("n")) {

                    docid1 = docid.substring(0, 3).toLowerCase() + docid.substring(4);

                }
                if ((docid.toLowerCase().equals(GenericMethods.rkeyfn) || docid1.contains(GenericMethods.rkeyfn) || createdat.substring(0, 10).contains(GenericMethods.rkeyfn)) && (remainder.equals("null") || date.before(date1) || date.equals(date1))) {

                    selectiondetails1.put("id1", id1);
                    selectiondetails1.put("comment", comment1);
                    selectiondetails1.put("is_done", isdone);
                    selectiondetails1.put("document_id", docid);
                    selectiondetails1.put("created_at", createdat);
                    selectiondetails1.put("reminder_dt", remainder);
                    selectiondetails1.put("status", status);
                    selectiondetails1.put("assign_by", assignby);
                    selectiondetails1.put("task_id", taskid);
                    selectiondetails1.put("notify", notify);
                    selectiondetails1.put("Pw_docid", PW_docid);
                    selectiondetails1.put("Pw_env", PW_env);
                    selectiondetails1.put("tokenno", PW_tokenno);
                    listadhoc.add(selectiondetails1);
                }
            }

            closeCursor(c);

        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }
        db.close();

        return listadhoc;
    }

    public ArrayList<HashMap<String, String>> getWitnessdetails(DBOperation db) {
        ArrayList<HashMap<String, String>> listadhoc = new ArrayList<>();

        try {

            String query = "select * from " + DBManager.TableInfo.TABLE_TASK + " where " + DBManager.TableInfo.TASK_NAME + " = '" + "Witness" + "'";

            SQLiteDatabase sqlite1 = db.getWritableDatabase();
            Cursor c = sqlite1.rawQuery(query, null);

            Calendar c1 = Calendar.getInstance();
            SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
            String curdate1 = df2.format(c1.getTime());
            Date date = null, date1 = null;
            while (c.moveToNext()) {
                //
                HashMap<String, String> selectiondetails1 = new HashMap<>();
                String id1 = c.getString(c.getColumnIndex(DBManager.TableInfo.WITID));
                String comment1 = c.getString(c.getColumnIndex(DBManager.TableInfo.COMMENT));
                String isdone = c.getString(c.getColumnIndex(DBManager.TableInfo.IS_DONE));
                String docid = c.getString(c.getColumnIndex(DBManager.TableInfo.DOC));
                String createdat = c.getString(c.getColumnIndex(DBManager.TableInfo.CREATE));
                String remainder;
                if (c.getString(c.getColumnIndex(DBManager.TableInfo.REMAINDER)).equals("null")) {
                    remainder = c.getString(c.getColumnIndex(DBManager.TableInfo.REMAINDER));
                } else {
                    remainder = c.getString(c.getColumnIndex(DBManager.TableInfo.REMAINDER)).substring(0, 10);

                }

                String status = c.getString(c.getColumnIndex(DBManager.TableInfo.status1));
                String assignby = c.getString(c.getColumnIndex(DBManager.TableInfo.ASSIGN));
                String taskid = c.getString(c.getColumnIndex(DBManager.TableInfo.TASK_ID));
                String notify = c.getString(c.getColumnIndex(DBManager.TableInfo.notification));
                String PW_docid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_PW_doc_id));
                String PW_env = c.getString(c.getColumnIndex(DBManager.TableInfo.db_PW_env));
                String PW_tokenno = c.getString(c.getColumnIndex(DBManager.TableInfo.db_PW_token_no));


                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {

                    if (!remainder.equals("null")) {
                        date = dateFormat.parse(remainder);

                        date1 = dateFormat.parse(curdate1);
                    }

                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if ((remainder.equals("null")) || (date.before(date1)) || (date.equals(date1))) {


                    selectiondetails1.put("id1", id1);
                    selectiondetails1.put("comment", comment1);
                    selectiondetails1.put("is_done", isdone);
                    selectiondetails1.put("document_id", docid);
                    selectiondetails1.put("created_at", createdat);
                    selectiondetails1.put("reminder_dt", remainder);
                    selectiondetails1.put("status", status);
                    selectiondetails1.put("assign_by", assignby);
                    selectiondetails1.put("task_id", taskid);
                    selectiondetails1.put("notify", notify);
                    selectiondetails1.put("Pw_docid", PW_docid);
                    selectiondetails1.put("Pw_env", PW_env);
                    selectiondetails1.put("tokenno", PW_tokenno);
                    listadhoc.add(selectiondetails1);
                }
            }

            closeCursor(c);

        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }
        db.close();

        return listadhoc;
    }


    public ArrayList<HashMap<String, String>> getbiometricpendingwork(DBOperation db) {

        ArrayList<HashMap<String, String>> getAllList = new ArrayList<>();
        SQLiteDatabase sqlite = db.getWritableDatabase();
//
//        String selectQuery = "SELECT  * FROM " + DBManager.TableInfo.TABLE_NAME_APPOINTMENT + " t1 " + " JOIN " + DBManager.TableInfo.TABLE_DOCUMENT + " t2 ON "
//                + "t1." + DBManager.TableInfo.DocumentId + " = " + "t2." + DBManager.TableInfo.DocumentId + " JOIN " +
//                 DBManager.TableInfo.PARTIES1 + " t3 ON "+ "t2."+DBManager.TableInfo.DocumentId + " = " + "t3." + DBManager.TableInfo.DOCUMENT + " AND t1."+DBManager.TableInfo.Executioner_id+" = '"+Login.umailid + "'" + " ORDER BY " + "t1." + DBManager.TableInfo.StartTime1;

        String selectQuery = "SELECT  * FROM " + DBManager.TableInfo.TABLE_NAME_DOCUMENT + " t1," + DBManager.TableInfo.TABLE_NAME_APPOINTMENT + " t2,"
                + DBManager.TableInfo.PARTIES + " t3 WHERE " + " t1." + DBManager.TableInfo.DocumentId + " = " + "t2." + DBManager.TableInfo.DocumentId + " AND t1." + DBManager.TableInfo.DocumentId + " = " + "t3." + DBManager.TableInfo.DOCUMENT + " AND t2." + DBManager.TableInfo.Executioner_id + " = '" + Login.umailid + "'" + " ORDER BY " + "t2." + DBManager.TableInfo.StartTime1;


        Cursor c = sqlite.rawQuery(selectQuery, null);

        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        String curdate1 = df2.format(c1.getTime());
        Date date = null, date1 = null;
        if (c.moveToFirst()) {

            c.moveToFirst();
            do {
                HashMap<String, String> getAllListMap = new HashMap<>();
                String rtoken = c.getString(c.getColumnIndex("Rtoken"));
                String rkey = c.getString(c.getColumnIndex("Rkey"));
                String name = c.getString(c.getColumnIndex("Uname"));
                String mail = c.getString(c.getColumnIndex("Umail"));
                String contact = c.getString(c.getColumnIndex("Ucontact"));
                String padd = c.getString(c.getColumnIndex("Paddress"));
                String oname = c.getString(c.getColumnIndex("Oname"));
                String ocontact = c.getString(c.getColumnIndex("Ocontact"));
                String omail = c.getString(c.getColumnIndex("Omail"));
                String oadd = c.getString(c.getColumnIndex("Oaddress"));
                String tname = c.getString(c.getColumnIndex("Tname"));
                String tcontact = c.getString(c.getColumnIndex("Tcontact"));
                String tmail = c.getString(c.getColumnIndex("Tmail"));
                String tadd = c.getString(c.getColumnIndex("Taddress"));
                String fmail = c.getString(c.getColumnIndex("uemail"));
                String tstatus = c.getString(c.getColumnIndex("status"));
                String syncstatus = c.getString(c.getColumnIndex(DBManager.TableInfo.SYNCSTATUS));
                String distance = c.getString(c.getColumnIndex(DBManager.TableInfo.DISTANCE));
                String amount = c.getString(c.getColumnIndex(DBManager.TableInfo.AMOUNT));
                String trasporttype = c.getString(c.getColumnIndex(DBManager.TableInfo.TRANSPORTTYPE));
                String apointmentfor = c.getString(c.getColumnIndex(DBManager.TableInfo.AppFor));
                String acvrreportstatus = c.getString(c.getColumnIndex(DBManager.TableInfo.SYNCSTATUSREPORT));
                String appdate = c.getString(c.getColumnIndex("Appdate"));
                String biocomp = c.getString(c.getColumnIndex("Biocomp"));
                String appdate1 = c.getString(c.getColumnIndex("Appdate1"));
                String biocom1 = c.getString(c.getColumnIndex("Biocomp1"));
                String regfromcomp = c.getString(c.getColumnIndex("reg_from_app"));
                String witness = c.getString(c.getColumnIndex("witness"));
                String shipadd = c.getString(c.getColumnIndex("ship_add"));
                String shipdiffadd = c.getString(c.getColumnIndex("ship_diff_add"));
                String stime1 = c.getString(c.getColumnIndex("Stime1"));
                String stime2 = c.getString(c.getColumnIndex("Stime2"));
                String docid = c.getString(c.getColumnIndex("Did"));
                String appadress = c.getString(c.getColumnIndex("Aaddress"));
                String appid = c.getString(c.getColumnIndex("Aid"));
                String exid = c.getString(c.getColumnIndex("Exid"));
                String appfor = c.getString(c.getColumnIndex("appfor"));
                String apptype = c.getString(c.getColumnIndex("app_type"));
                String landmark = c.getString(c.getColumnIndex("landmark"));
                String contactperson = c.getString(c.getColumnIndex(DBManager.TableInfo.contactperson));
                String post_status = c.getString(c.getColumnIndex("post_status"));
                String bio_status = c.getString(c.getColumnIndex(DBManager.TableInfo.att_status));
                String latitude = c.getString(c.getColumnIndex("latitude1"));
                String longitude = c.getString(c.getColumnIndex("longitude1"));
                String notify = c.getString(c.getColumnIndex(DBManager.TableInfo.notification));
                String biometric = c.getString(c.getColumnIndex(DBManager.TableInfo.BIOMETRIC));
                String biometric_email = c.getString(c.getColumnIndex(DBManager.TableInfo.NAMENEW));
                String sdate1 = c.getString(c.getColumnIndex(DBManager.TableInfo.StartDate));
                String[] contact_person = contactperson.split(",");


                String contactname = contact_person[0];


                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                try {
                    date = dateFormat.parse(sdate1);
                    date1 = dateFormat.parse(curdate1);

                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


                if (contactname.equals(biometric_email) && (biometric.equals("false") || biometric.equals("null")) && date.before(date1)) {
                    getAllListMap.put("Rtoken", rtoken);
                    getAllListMap.put("rkey", rkey);
                    getAllListMap.put("name", name);
                    getAllListMap.put("mail", mail);
                    getAllListMap.put("contact", contact);
                    getAllListMap.put("padd", padd);
                    getAllListMap.put("oname", oname);
                    getAllListMap.put("ocontact", ocontact);
                    getAllListMap.put("omail", omail);
                    getAllListMap.put("oadd", oadd);
                    getAllListMap.put("tname", tname);
                    getAllListMap.put("tcontact", tcontact);
                    getAllListMap.put("tmail", tmail);
                    getAllListMap.put("tadd", tadd);
                    getAllListMap.put("fmail", fmail);
                    getAllListMap.put("tstatus", tstatus);
                    getAllListMap.put("biocomp", biocomp);
                    getAllListMap.put("biocom1", biocom1);
                    getAllListMap.put("appdate", appdate);
                    getAllListMap.put("appdate1", appdate1);
                    getAllListMap.put("regfromcomp", regfromcomp);
                    getAllListMap.put("witness", witness);
                    getAllListMap.put("shipadd", shipadd);
                    getAllListMap.put("shipdiffadd", shipdiffadd);
                    getAllListMap.put("sdate", sdate1);
                    getAllListMap.put("stime1", stime1);
                    getAllListMap.put("stime2", stime2);
                    getAllListMap.put("docid", docid);
                    getAllListMap.put("appadress", appadress);
                    getAllListMap.put("latitude", latitude);
                    getAllListMap.put("longitude", longitude);
                    getAllListMap.put("appid", appid);
                    getAllListMap.put("appfor", appfor);
                    getAllListMap.put("exid", exid);
                    getAllListMap.put("syncstatus", syncstatus);
                    getAllListMap.put("distance", distance);
                    getAllListMap.put("amount", amount);
                    getAllListMap.put("trasporttype", trasporttype);
                    getAllListMap.put("apointmentfor", apointmentfor);
                    getAllListMap.put("acvrreportstatus", acvrreportstatus);
                    getAllListMap.put("apptype", apptype);
                    getAllListMap.put("post_status", post_status);
                    getAllListMap.put("att_status", bio_status);
                    getAllListMap.put("notify", notify);
                    if (landmark.equals("null")) {

                        getAllListMap.put("landmark", "No details");
                    } else {
                        getAllListMap.put("landmark", landmark);
                    }
                    String[] contact_array = contactperson.split(",");
                    if (contactperson.equals("null")) {
                        getAllListMap.put("contact_person", "No details");
                        getAllListMap.put("contact_name", "No details");
                        getAllListMap.put("contact_email", "No details");
                        getAllListMap.put("contact_no", "No details");
                    } else if (contact_array.length > 1) {
                        getAllListMap.put("contact_person", "Details");

                        getAllListMap.put("contact_name", contact_array[0]);
                        getAllListMap.put("contact_email", contact_array[1]);
                        getAllListMap.put("contact_no", contact_array[2]);
                    }
                    getAllList.add(getAllListMap);

                }


            } while (c.moveToNext());

        }
        c.close();
        db.close();
        return getAllList;
    }


    public ArrayList<HashMap<String, String>> getWitnessdetails1(DBOperation db) {
        ArrayList<HashMap<String, String>> listadhoc = new ArrayList<>();

        try {
            String docid1 = "";
            Date date = null, date1 = null;
            String query = "select * from " + DBManager.TableInfo.TABLE_TASK + " where " + DBManager.TableInfo.TASK_NAME + " = '" + "Witness" + "'";

            SQLiteDatabase sqlite1 = db.getWritableDatabase();
            Cursor c = sqlite1.rawQuery(query, null);

            Calendar c1 = Calendar.getInstance();
            SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
            String curdate1 = df2.format(c1.getTime());

            while (c.moveToNext()) {

                HashMap<String, String> selectiondetails1 = new HashMap<>();
                String id1 = c.getString(c.getColumnIndex(DBManager.TableInfo.WITID));
                String comment1 = c.getString(c.getColumnIndex(DBManager.TableInfo.COMMENT));
                String isdone = c.getString(c.getColumnIndex(DBManager.TableInfo.IS_DONE));
                String docid = c.getString(c.getColumnIndex(DBManager.TableInfo.DOC));
                String createdat = c.getString(c.getColumnIndex(DBManager.TableInfo.CREATE));
                String remainder;
                if (c.getString(c.getColumnIndex(DBManager.TableInfo.REMAINDER)).equals("null")) {
                    remainder = c.getString(c.getColumnIndex(DBManager.TableInfo.REMAINDER));
                } else {
                    remainder = c.getString(c.getColumnIndex(DBManager.TableInfo.REMAINDER)).substring(0, 10);

                }
                String status = c.getString(c.getColumnIndex(DBManager.TableInfo.status1));
                String assignby = c.getString(c.getColumnIndex(DBManager.TableInfo.ASSIGN));
                String taskid = c.getString(c.getColumnIndex(DBManager.TableInfo.TASK_ID));
                String PW_docid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_PW_doc_id));
                String PW_env = c.getString(c.getColumnIndex(DBManager.TableInfo.db_PW_env));
                String PW_tokenno = c.getString(c.getColumnIndex(DBManager.TableInfo.db_PW_token_no));
                String notify = c.getString(c.getColumnIndex(DBManager.TableInfo.notification));
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    if (!remainder.equals("null")) {
                        date = dateFormat.parse(remainder);

                        date1 = dateFormat.parse(curdate1);
                    }
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (docid.substring(0, 1).toLowerCase().equals("a")) {


                    docid1 = "a" + docid.substring(2);

                } else if (docid.substring(0, 1).toLowerCase().equals("n")) {

                    docid1 = docid.substring(0, 3).toLowerCase() + docid.substring(4);

                }
                if ((docid.toLowerCase().equals(GenericMethods.rkeyfn) || docid1.contains(GenericMethods.rkeyfn) || createdat.substring(0, 10).contains(GenericMethods.rkeyfn)) && (remainder.equals("null") || date.before(date1) || date.equals(date1))) {
                    selectiondetails1.put("id1", id1);
                    selectiondetails1.put("comment", comment1);
                    selectiondetails1.put("is_done", isdone);
                    selectiondetails1.put("document_id", docid);
                    selectiondetails1.put("created_at", createdat);
                    selectiondetails1.put("reminder_dt", remainder);
                    selectiondetails1.put("status", status);
                    selectiondetails1.put("assign_by", assignby);
                    selectiondetails1.put("task_id", taskid);
                    selectiondetails1.put("notify", notify);
                    selectiondetails1.put("Pw_docid", PW_docid);
                    selectiondetails1.put("Pw_env", PW_env);
                    selectiondetails1.put("tokenno", PW_tokenno);
                    listadhoc.add(selectiondetails1);
                }
            }

            closeCursor(c);

        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }
        db.close();

        return listadhoc;
    }

    public ArrayList<HashMap<String, String>> getComplaintdetails(DBOperation db) {
        ArrayList<HashMap<String, String>> listadhoc = new ArrayList<>();

        try {

            String query = "select * from " + DBManager.TableInfo.TABLE_TASK + " where " + DBManager.TableInfo.TASK_NAME + " = '" + "Complaint" + "'";
            SQLiteDatabase sqlite1 = db.getWritableDatabase();
            Cursor c = sqlite1.rawQuery(query, null);
            Calendar c1 = Calendar.getInstance();
            SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
            String curdate1 = df2.format(c1.getTime());
            Date date = null, date1 = null;
            while (c.moveToNext()) {

                HashMap<String, String> selectiondetails1 = new HashMap<>();
                String id1 = c.getString(c.getColumnIndex(DBManager.TableInfo.WITID));
                String comment1 = c.getString(c.getColumnIndex(DBManager.TableInfo.COMMENT));
                String isdone = c.getString(c.getColumnIndex(DBManager.TableInfo.IS_DONE));
                String docid = c.getString(c.getColumnIndex(DBManager.TableInfo.DOC));
                String createdat = c.getString(c.getColumnIndex(DBManager.TableInfo.CREATE));
                String remainder;
                if (c.getString(c.getColumnIndex(DBManager.TableInfo.REMAINDER)).equals("null")) {
                    remainder = c.getString(c.getColumnIndex(DBManager.TableInfo.REMAINDER));
                } else {
                    remainder = c.getString(c.getColumnIndex(DBManager.TableInfo.REMAINDER)).substring(0, 10);

                }
                String status = c.getString(c.getColumnIndex(DBManager.TableInfo.status1));
                String assignby = c.getString(c.getColumnIndex(DBManager.TableInfo.ASSIGN));
                String taskid = c.getString(c.getColumnIndex(DBManager.TableInfo.TASK_ID));
                String notify = c.getString(c.getColumnIndex(DBManager.TableInfo.notification));
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    if (!remainder.equals("null")) {
                        date = dateFormat.parse(remainder);

                        date1 = dateFormat.parse(curdate1);
                    }
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if ((remainder.equals("null")) || (date.before(date1)) || (date.equals(date1))) {
                    selectiondetails1.put("id1", id1);
                    selectiondetails1.put("comment", comment1);
                    selectiondetails1.put("is_done", isdone);
                    selectiondetails1.put("document_id", docid);
                    selectiondetails1.put("created_at", createdat);
                    selectiondetails1.put("reminder_dt", remainder);
                    selectiondetails1.put("status", status);
                    selectiondetails1.put("assign_by", assignby);
                    selectiondetails1.put("task_id", taskid);
                    selectiondetails1.put("notify", notify);
                    listadhoc.add(selectiondetails1);
                }
            }

            closeCursor(c);

        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }
        db.close();
        return listadhoc;

    }

    public ArrayList<HashMap<String, String>> getComplaintdetails1(DBOperation db) {
        ArrayList<HashMap<String, String>> listadhoc = new ArrayList<>();

        try {
            String docid1 = "";
            String query = "select * from " + DBManager.TableInfo.TABLE_TASK + " where " + DBManager.TableInfo.TASK_NAME + " = '" + "Complaint" + "'";
            SQLiteDatabase sqlite1 = db.getWritableDatabase();
            Cursor c = sqlite1.rawQuery(query, null);
            Calendar c1 = Calendar.getInstance();
            SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
            String curdate1 = df2.format(c1.getTime());
            Date date = null, date1 = null;
            while (c.moveToNext()) {

                HashMap<String, String> selectiondetails1 = new HashMap<>();
                String id1 = c.getString(c.getColumnIndex(DBManager.TableInfo.WITID));
                String comment1 = c.getString(c.getColumnIndex(DBManager.TableInfo.COMMENT));
                String isdone = c.getString(c.getColumnIndex(DBManager.TableInfo.IS_DONE));
                String docid = c.getString(c.getColumnIndex(DBManager.TableInfo.DOC));
                String createdat = c.getString(c.getColumnIndex(DBManager.TableInfo.CREATE));
                String remainder;
                if (c.getString(c.getColumnIndex(DBManager.TableInfo.REMAINDER)).equals("null")) {
                    remainder = c.getString(c.getColumnIndex(DBManager.TableInfo.REMAINDER));
                } else {
                    remainder = c.getString(c.getColumnIndex(DBManager.TableInfo.REMAINDER)).substring(0, 10);

                }
                String status = c.getString(c.getColumnIndex(DBManager.TableInfo.status1));
                String assignby = c.getString(c.getColumnIndex(DBManager.TableInfo.ASSIGN));
                String taskid = c.getString(c.getColumnIndex(DBManager.TableInfo.TASK_ID));
                String notify = c.getString(c.getColumnIndex(DBManager.TableInfo.notification));
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    if (!remainder.equals("null")) {
                        date = dateFormat.parse(remainder);

                        date1 = dateFormat.parse(curdate1);
                    }
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (docid.substring(0, 1).toLowerCase().equals("a")) {


                    docid1 = "a" + docid.substring(2);

                } else if (docid.substring(0, 1).toLowerCase().equals("n")) {

                    docid1 = docid.substring(0, 3).toLowerCase() + docid.substring(4);

                }
                if ((docid.toLowerCase().equals(GenericMethods.rkeyfn) || docid1.contains(GenericMethods.rkeyfn) || createdat.substring(0, 10).contains(GenericMethods.rkeyfn)) && (remainder.equals("null") || date.before(date1) || date.equals(date1))) {
                    selectiondetails1.put("id1", id1);
                    selectiondetails1.put("comment", comment1);
                    selectiondetails1.put("is_done", isdone);
                    selectiondetails1.put("document_id", docid);
                    selectiondetails1.put("created_at", createdat);
                    selectiondetails1.put("reminder_dt", remainder);
                    selectiondetails1.put("status", status);
                    selectiondetails1.put("assign_by", assignby);
                    selectiondetails1.put("task_id", taskid);
                    selectiondetails1.put("notify", notify);
                    listadhoc.add(selectiondetails1);
                }
            }

            closeCursor(c);

        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }
        db.close();

        return listadhoc;
    }

    public ArrayList<HashMap<String, String>> getmultipartycheck(DBOperation db) {
        ArrayList<HashMap<String, String>> listUsername = new ArrayList<>();

        try {

            String query = "select * from  " + MULTIWORK + " where " + DBManager.TableInfo.KEY_LOGIN_USER + " = '" + GenericMethods.email + "'";

            SQLiteDatabase sqlite = db.getWritableDatabase();
            Cursor c = sqlite.rawQuery(query, null);

            while (c.moveToNext()) {
                HashMap<String, String> selectiondetails = new HashMap<>();
                selectiondetails.put("exec_email", c.getString(c.getColumnIndex(DBManager.TableInfo.KEY_LOGIN_USER)));
                selectiondetails.put("docid", c.getString(c.getColumnIndex(DBManager.TableInfo.DOCU)));
                selectiondetails.put("appointment_id", c.getString(c.getColumnIndex(DBManager.TableInfo.AppointmentId)));
                selectiondetails.put("status", c.getString(c.getColumnIndex(DBManager.TableInfo.STATUS)));
                selectiondetails.put("reason", c.getString(c.getColumnIndex(DBManager.TableInfo.reason)));
                selectiondetails.put("reach_time", c.getString(c.getColumnIndex(DBManager.TableInfo.reach_time)));
                selectiondetails.put("location", c.getString(c.getColumnIndex(DBManager.TableInfo.updatelocation)));
                selectiondetails.put("update_time", c.getString(c.getColumnIndex(DBManager.TableInfo.updatetime)));

                listUsername.add(selectiondetails);
            }

            closeCursor(c);

        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }
        db.close();
        return listUsername;
    }

    public ArrayList<HashMap<String, String>> getactualtime(DBOperation db) {
        ArrayList<HashMap<String, String>> listUsername = new ArrayList<>();

        try {

            String query = "select * from  " + DBManager.TableInfo.ACTUAL_TIME + " where " + DBManager.TableInfo.KEY_LOGIN_USER + " = '" + GenericMethods.email + "'";

            SQLiteDatabase sqlite = db.getWritableDatabase();
            Cursor c = sqlite.rawQuery(query, null);

            while (c.moveToNext()) {
                HashMap<String, String> selectiondetails = new HashMap<>();
                selectiondetails.put("exec_email", c.getString(c.getColumnIndex(DBManager.TableInfo.KEY_LOGIN_USER)));
                selectiondetails.put("actual_time", c.getString(c.getColumnIndex(DBManager.TableInfo.actual_time)));
                selectiondetails.put("docid", c.getString(c.getColumnIndex(DBManager.TableInfo.DocumentId)));
                selectiondetails.put("call_time", c.getString(c.getColumnIndex(DBManager.TableInfo.call_time)));
                selectiondetails.put("gen_distance", c.getString(c.getColumnIndex(gen_distance)));
                selectiondetails.put("appointment_id", c.getString(c.getColumnIndex(DBManager.TableInfo.AppointmentId)));

                listUsername.add(selectiondetails);
            }

            closeCursor(c);

        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }
        db.close();
        return listUsername;
    }

    public ArrayList<HashMap<String, String>> getposttaskdetails(DBOperation db) {
        ArrayList<HashMap<String, String>> listwitness = new ArrayList<>();

        try {

            String query = "select * from " + DBManager.TableInfo.POST_TASK + " where " + DBManager.TableInfo.KEY_LOGIN_USER + " = '" + GenericMethods.email + "'";
            SQLiteDatabase sqlite1 = db.getWritableDatabase();
            Cursor c = sqlite1.rawQuery(query, null);
            while (c.moveToNext()) {
                HashMap<String, String> selectiondetails1 = new HashMap<>();
                selectiondetails1.put("is_done", c.getString(c.getColumnIndex(DBManager.TableInfo.IS_DONE)));
                selectiondetails1.put("reason", c.getString(c.getColumnIndex(DBManager.TableInfo.REASON)));
                selectiondetails1.put("task_id", c.getString(c.getColumnIndex(DBManager.TableInfo.TASK_ID)));
                selectiondetails1.put("status", c.getString(c.getColumnIndex(DBManager.TableInfo.status1)));
                selectiondetails1.put("comment", c.getString(c.getColumnIndex(DBManager.TableInfo.comm1)));
                selectiondetails1.put("exec_email", c.getString(c.getColumnIndex(DBManager.TableInfo.KEY_LOGIN_USER)));
                listwitness.add(selectiondetails1);
            }

            closeCursor(c);

        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }
        db.close();

        return listwitness;
    }


    public ArrayList<HashMap<String, String>> calldetails(DBOperation db) {
        ArrayList<HashMap<String, String>> listwitness = new ArrayList<>();

        try {

            String query = "select * from " + DBManager.TableInfo.CALL + " where " + DBManager.TableInfo.KEY_LOGIN_USER + " = '" + GenericMethods.email + "'";
            SQLiteDatabase sqlite1 = db.getWritableDatabase();
            Cursor c = sqlite1.rawQuery(query, null);
            while (c.moveToNext()) {
                HashMap<String, String> selectiondetails1 = new HashMap<>();
                selectiondetails1.put("docid", c.getString(c.getColumnIndex(DBManager.TableInfo.DocumentId)));
                selectiondetails1.put("appid", c.getString(c.getColumnIndex(DBManager.TableInfo.AppointmentId)));
                selectiondetails1.put("gen_distance", c.getString(c.getColumnIndex(gen_distance)));
                selectiondetails1.put("actual_time", c.getString(c.getColumnIndex(DBManager.TableInfo.actual_time)));
                selectiondetails1.put("call_time", c.getString(c.getColumnIndex(DBManager.TableInfo.call_time)));
                selectiondetails1.put("exec_email", c.getString(c.getColumnIndex(DBManager.TableInfo.KEY_LOGIN_USER)));
                listwitness.add(selectiondetails1);
            }

            closeCursor(c);

        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }
        db.close();

        return listwitness;
    }

    public ArrayList<HashMap<String, String>> getAppointmentslot(DBOperation db) {
        ArrayList<HashMap<String, String>> listUsername = new ArrayList<>();

        try {

            String query = "SELECT * from " + DBManager.TableInfo.APPOINTMENTSLOT + " where " + DBManager.TableInfo.KEY_LOGIN_USER + " = '" + GenericMethods.email + "'";
            SQLiteDatabase sqlite = db.getWritableDatabase();
            Cursor c = sqlite.rawQuery(query, null);

            while (c.moveToNext()) {
                HashMap<String, String> selectiondetails = new HashMap<>();
                selectiondetails.put("slot_id", c.getString(c.getColumnIndex(DBManager.TableInfo.slotid1)));
                selectiondetails.put("start_time", c.getString(c.getColumnIndex(DBManager.TableInfo.starttime1)));
                selectiondetails.put("End_time", c.getString(c.getColumnIndex(DBManager.TableInfo.endtime1)));
                selectiondetails.put("Available", c.getString(c.getColumnIndex(DBManager.TableInfo.available1)));
                selectiondetails.put("Block", c.getString(c.getColumnIndex(DBManager.TableInfo.block1)));
                selectiondetails.put("Discount", c.getString(c.getColumnIndex(DBManager.TableInfo.discount1)));

                listUsername.add(selectiondetails);
            }

            closeCursor(c);

        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }
        db.close();

        return listUsername;
    }


    public ArrayList<HashMap<String, String>> getrescheduledetails(DBOperation db) {
        ArrayList<HashMap<String, String>> listreschedule = new ArrayList<>();

        try {

            String query = "select * from " + DBManager.TableInfo.RESCHEDULE + " where " + DBManager.TableInfo.KEY_LOGIN_USER + " = '" + GenericMethods.email + "'";
            SQLiteDatabase sqlite1 = db.getWritableDatabase();
            Cursor c = sqlite1.rawQuery(query, null);
            Calendar c1 = Calendar.getInstance();
            SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
            String curdate1 = df2.format(c1.getTime());

            while (c.moveToNext()) {

                HashMap<String, String> selectiondetails1 = new HashMap<>();
                selectiondetails1.put("exec_email", c.getString(c.getColumnIndex(DBManager.TableInfo.KEY_LOGIN_USER)));
                selectiondetails1.put("docid", c.getString(c.getColumnIndex(DBManager.TableInfo.DocumentId)));
                selectiondetails1.put("appointment_id", c.getString(c.getColumnIndex(DBManager.TableInfo.AppointmentId)));
                selectiondetails1.put("rescheduledate", c.getString(c.getColumnIndex(DBManager.TableInfo.RESCHEDULEDATE)));
                selectiondetails1.put("rescheduletime", c.getString(c.getColumnIndex(DBManager.TableInfo.RESCHEDULETIME)));
                selectiondetails1.put("reason1", c.getString(c.getColumnIndex(DBManager.TableInfo.RES_REASON)));
                listreschedule.add(selectiondetails1);
            }


            closeCursor(c);

        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }
        db.close();

        return listreschedule;
    }


    public ArrayList<HashMap<String, String>> getweekendoffdetails(DBOperation db) {
        ArrayList<HashMap<String, String>> listreschedule = new ArrayList<>();

        try {

            String query = "select * from " + DBManager.TableInfo.WEEKEND + " where " + DBManager.TableInfo.KEY_LOGIN_USER + " = '" + GenericMethods.email + "'";
            SQLiteDatabase sqlite1 = db.getWritableDatabase();
            Cursor c = sqlite1.rawQuery(query, null);
            Calendar c1 = Calendar.getInstance();
            SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
            String curdate1 = df2.format(c1.getTime());

            while (c.moveToNext()) {

                HashMap<String, String> selectiondetails1 = new HashMap<>();
                selectiondetails1.put("exec_email", c.getString(c.getColumnIndex(DBManager.TableInfo.KEY_LOGIN_USER)));
                selectiondetails1.put("from_date", c.getString(c.getColumnIndex(DBManager.TableInfo.from_date)));
                selectiondetails1.put("to_date", c.getString(c.getColumnIndex(DBManager.TableInfo.to_date)));
                selectiondetails1.put("status_off", c.getString(c.getColumnIndex(DBManager.TableInfo.statusoff)));
                selectiondetails1.put("reason_off", c.getString(c.getColumnIndex(DBManager.TableInfo.reasonoff)));

                listreschedule.add(selectiondetails1);
            }


            closeCursor(c);

        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }
        db.close();

        return listreschedule;
    }


    public void UpdateAppointmentStatus(DBOperation db, String docid) {

        String selection = DBManager.TableInfo.DocumentId + " = ?";

        String a[] = {docid};

        SQLiteDatabase sqlite = db.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(DBManager.TableInfo.SYNCSTATUS, "SYNC");

        long result = sqlite.update(DBManager.TableInfo.TABLE_NAME_DOCUMENT, cv, selection, a);

        db.close();
    }

    public ArrayList<HashMap<String, String>> getcomment(DBOperation db) {
        ArrayList<HashMap<String, String>> listComment = new ArrayList<>();

        try {

            String query = "SELECT * from " + DBManager.TableInfo.TABLE_NAME3 + " where " + DBManager.TableInfo.SYNCSTATUS + "='ASYNC'" + " AND " + DBManager.TableInfo.task_name + "='Biometric'";
            SQLiteDatabase sqlite = db.getWritableDatabase();
            Cursor c = sqlite.rawQuery(query, null);


            while (c.moveToNext()) {
                HashMap<String, String> selectiondetails = new HashMap<>();
                selectiondetails.put("DocId", c.getString(c.getColumnIndex(DBManager.TableInfo.Comment_env)));
                selectiondetails.put("CustomerComment", c.getString(c.getColumnIndex(DBManager.TableInfo.Comments)));
                selectiondetails.put("CommentUser", c.getString(c.getColumnIndex(DBManager.TableInfo.Comment_user)));
                selectiondetails.put("Commentowner", c.getString(c.getColumnIndex(DBManager.TableInfo.Comment_owner)));
                selectiondetails.put("ComentID", c.getString(c.getColumnIndex(DBManager.TableInfo.Comment_id)));
                selectiondetails.put("CommentIdDone", c.getString(c.getColumnIndex(DBManager.TableInfo.Comment_is_done)));
                selectiondetails.put("CommentDate", c.getString(c.getColumnIndex(DBManager.TableInfo.Comment_date)));
                selectiondetails.put("ReminderDate", c.getString(c.getColumnIndex(DBManager.TableInfo.REMINDER_DATE)));
                selectiondetails.put("Commenttype", c.getString(c.getColumnIndex(DBManager.TableInfo.Comments_type)));
                selectiondetails.put("Commentarea", c.getString(c.getColumnIndex(DBManager.TableInfo.Comments_area)));
                selectiondetails.put("Commentcontact", c.getString(c.getColumnIndex(DBManager.TableInfo.Comments_contact)));

                listComment.add(selectiondetails);


            }

            closeCursor(c);

        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }

        db.close();
        GenericMethods.listofcommment = String.valueOf(listComment.size());
        return listComment;

    }

    public ArrayList<HashMap<String, String>> getsalescomment(DBOperation db) {
        ArrayList<HashMap<String, String>> listComment = new ArrayList<>();

        try {

            String query = "SELECT * from " + DBManager.TableInfo.TABLE_NAME3 + " where " + DBManager.TableInfo.SYNCSTATUS + "='ASYNC'" + " AND " + DBManager.TableInfo.task_name + "='Sales'";
            SQLiteDatabase sqlite = db.getWritableDatabase();
            Cursor c = sqlite.rawQuery(query, null);

            while (c.moveToNext()) {
                HashMap<String, String> selectiondetails = new HashMap<>();
                selectiondetails.put("DocId", c.getString(c.getColumnIndex(DBManager.TableInfo.Comment_env)));
                selectiondetails.put("CustomerComment", c.getString(c.getColumnIndex(DBManager.TableInfo.Comments)));
                selectiondetails.put("CommentUser", c.getString(c.getColumnIndex(DBManager.TableInfo.Comment_user)));
                selectiondetails.put("Commentowner", c.getString(c.getColumnIndex(DBManager.TableInfo.Comment_owner)));
                selectiondetails.put("ComentID", c.getString(c.getColumnIndex(DBManager.TableInfo.Comment_id)));
                selectiondetails.put("CommentIdDone", c.getString(c.getColumnIndex(DBManager.TableInfo.Comment_is_done)));
                selectiondetails.put("CommentDate", c.getString(c.getColumnIndex(DBManager.TableInfo.Comment_date)));
                selectiondetails.put("ReminderDate", c.getString(c.getColumnIndex(DBManager.TableInfo.REMINDER_DATE)));
                selectiondetails.put("Commenttype", c.getString(c.getColumnIndex(DBManager.TableInfo.Comments_type)));
                selectiondetails.put("Commentarea", c.getString(c.getColumnIndex(DBManager.TableInfo.Comments_area)));
                selectiondetails.put("Commentcontact", c.getString(c.getColumnIndex(DBManager.TableInfo.Comments_contact)));

                listComment.add(selectiondetails);
            }

            closeCursor(c);

        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }
        db.close();
        return listComment;

    }

    public ArrayList<HashMap<String, String>> getmarriagecomment(DBOperation db) {
        ArrayList<HashMap<String, String>> listComment = new ArrayList<>();

        try {

            String query = "SELECT * from " + DBManager.TableInfo.TABLE_NAME3 + " where " + DBManager.TableInfo.SYNCSTATUS + "='ASYNC'" + " AND " + DBManager.TableInfo.task_name + "='Marriage'";
            SQLiteDatabase sqlite = db.getWritableDatabase();
            Cursor c = sqlite.rawQuery(query, null);

            while (c.moveToNext()) {
                HashMap<String, String> selectiondetails = new HashMap<>();
                selectiondetails.put("DocId", c.getString(c.getColumnIndex(DBManager.TableInfo.Comment_env)));
                selectiondetails.put("CustomerComment", c.getString(c.getColumnIndex(DBManager.TableInfo.Comments)));
                selectiondetails.put("CommentUser", c.getString(c.getColumnIndex(DBManager.TableInfo.Comment_user)));
                selectiondetails.put("Commentowner", c.getString(c.getColumnIndex(DBManager.TableInfo.Comment_owner)));
                selectiondetails.put("ComentID", c.getString(c.getColumnIndex(DBManager.TableInfo.Comment_id)));
                selectiondetails.put("CommentIdDone", c.getString(c.getColumnIndex(DBManager.TableInfo.Comment_is_done)));
                selectiondetails.put("CommentDate", c.getString(c.getColumnIndex(DBManager.TableInfo.Comment_date)));
                selectiondetails.put("ReminderDate", c.getString(c.getColumnIndex(DBManager.TableInfo.REMINDER_DATE)));
                selectiondetails.put("Commenttype", c.getString(c.getColumnIndex(DBManager.TableInfo.Comments_type)));
                selectiondetails.put("Commentarea", c.getString(c.getColumnIndex(DBManager.TableInfo.Comments_area)));
                selectiondetails.put("Commentcontact", c.getString(c.getColumnIndex(DBManager.TableInfo.Comments_contact)));

                listComment.add(selectiondetails);
            }

            closeCursor(c);

        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }
        db.close();
        return listComment;

    }


    public boolean checkUserId(DBOperation db, String userid) {
        SQLiteDatabase sqlite = db.getWritableDatabase();
        String count = "SELECT count(*) FROM " + DBManager.TableInfo.TABLE_NAME5 + " where userid='" + userid + "'";
        Cursor mcursor = sqlite.rawQuery(count, null);
        mcursor.moveToFirst();

        int icount = mcursor.getInt(0);
        return icount > 0;

    }

    public void deleteSyncComment(DBOperation db) {
        try {
            SQLiteDatabase sqlite = db.getWritableDatabase();

            sqlite.delete(DBManager.TableInfo.TABLE_NAME3, "syncstatus=?", new String[]{"SYNC"});
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkCommentID(DBOperation db, String commentID) {
        SQLiteDatabase sqlite = db.getWritableDatabase();
        String count = "SELECT count(*) FROM " + DBManager.TableInfo.TABLE_NAME3 + " where Cid='" + commentID + "'";
        Cursor mcursor = sqlite.rawQuery(count, null);
        mcursor.moveToFirst();

        int icount = mcursor.getInt(0);
        return icount > 0;
    }

    public void UpdateCommentStatus(DBOperation db, String strCommentID) {
        String selection = DBManager.TableInfo.Comment_id + " = ?";
        String a[] = {strCommentID};

        SQLiteDatabase sqlite = db.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBManager.TableInfo.SYNCSTATUS, "SYNC");
        long result = sqlite.update(DBManager.TableInfo.TABLE_NAME3, cv, selection, a);

        db.close();
    }

    public String getUserId(DBOperation db, String userName) {
        String strUserId = "";
        try {

            String query = "SELECT * from " + DBManager.TableInfo.TABLE_NAME5 + " where " + DBManager.TableInfo.Email + "='" + userName + "'";
            SQLiteDatabase sqlite = db.getWritableDatabase();
            Cursor c = sqlite.rawQuery(query, null);


            while (c.moveToNext()) {
                HashMap<String, String> selectiondetails = new HashMap<>();
                strUserId = c.getString(c.getColumnIndex(DBManager.TableInfo.User_id));
            }
            closeCursor(c);

        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }
        db.close();
        return strUserId;

    }

    public ArrayList<HashMap<String, String>> getAcvrReport(DBOperation db) {
        ArrayList<HashMap<String, String>> listUsername = new ArrayList<>();
        try {
            String query = "SELECT * from " + DBManager.TableInfo.TABLE_NAME_APPOINTMENT + " where " + DBManager.TableInfo.SYNCSTATUSREPORT + "='ASYNC'" + " AND " + DBManager.TableInfo.task_name + "='Biometric'";
            SQLiteDatabase sqlite = db.getWritableDatabase();
            Cursor c = sqlite.rawQuery(query, null);

            while (c.moveToNext()) {
                HashMap<String, String> selectiondetails = new HashMap<>();
                selectiondetails.put("exec_email", c.getString(c.getColumnIndex(DBManager.TableInfo.Executioner_id)));
                selectiondetails.put("DocId", c.getString(c.getColumnIndex(DBManager.TableInfo.DocumentId)));
                selectiondetails.put("Distance", c.getString(c.getColumnIndex(DBManager.TableInfo.DISTANCE)));
                selectiondetails.put("Amount", c.getString(c.getColumnIndex(DBManager.TableInfo.AMOUNT)));
                selectiondetails.put("TransportType", c.getString(c.getColumnIndex(DBManager.TableInfo.TRANSPORTTYPE)));
                selectiondetails.put("Appointmentid", c.getString(c.getColumnIndex(DBManager.TableInfo.AppointmentId)));
                listUsername.add(selectiondetails);

            }
            closeCursor(c);

        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }
        db.close();
        return listUsername;
    }

    public ArrayList<HashMap<String, String>> getsalesAcvrReport(DBOperation db) {
        ArrayList<HashMap<String, String>> listUsername = new ArrayList<>();
        try {
            String query = "SELECT * from " + DBManager.TableInfo.TABLE_SALES_APPOINTMENT + " where " + DBManager.TableInfo.db_sales_acvrsyncstatus + "='ASYNC'" + " AND " + DBManager.TableInfo.task_name + "='Sales'";

            SQLiteDatabase sqlite = db.getWritableDatabase();
            Cursor c = sqlite.rawQuery(query, null);

            while (c.moveToNext()) {

                HashMap<String, String> selectiondetails = new HashMap<>();

                selectiondetails.put("exec_email", c.getString(c.getColumnIndex(DBManager.TableInfo.KEY_LOGIN_USER)));
                selectiondetails.put("DocId", c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_doc_id)));
                selectiondetails.put("Distance", c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_distance)));
                selectiondetails.put("Amount", c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_amount)));
                selectiondetails.put("TransportType", c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_transtype)));
                selectiondetails.put("Appointmentid", c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_appointment_id)));
                listUsername.add(selectiondetails);
            }
            closeCursor(c);

        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }
        db.close();
        return listUsername;
    }






    public ArrayList<HashMap<String, String>> getmarriageAcvrReport(DBOperation db) {
        ArrayList<HashMap<String, String>> listUsername = new ArrayList<>();
        try {
            String query = "SELECT * from " + DBManager.TableInfo.TABLE_MARRIAGE_APPOINTMENT + " where " + DBManager.TableInfo.db_marriage_acvrsyncstatus + "='ASYNC'" + " AND " + DBManager.TableInfo.task_name + "='Marriage'";

            SQLiteDatabase sqlite = db.getWritableDatabase();
            Cursor c = sqlite.rawQuery(query, null);

            while (c.moveToNext()) {
                HashMap<String, String> selectiondetails = new HashMap<>();
                selectiondetails.put("exec_email", c.getString(c.getColumnIndex(DBManager.TableInfo.KEY_LOGIN_USER)));
                selectiondetails.put("DocId", c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_doc_id)));
                selectiondetails.put("Distance", c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_distance)));
                selectiondetails.put("Amount", c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_acvr_amount)));
                selectiondetails.put("TransportType", c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_transtype)));
                selectiondetails.put("Appointmentid", c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_appointment_id)));
                listUsername.add(selectiondetails);
            }
            closeCursor(c);

        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }
        db.close();
        return listUsername;
    }

    public ArrayList<HashMap<String, String>> getmarriagebiometricpost(DBOperation db) {
        ArrayList<HashMap<String, String>> listUsername = new ArrayList<>();
        try {
            String query = "select * from  " + TABLE_MARRIAGE_SINGLE_BIOMETRIC + " where " + DBManager.TableInfo.KEY_LOGIN_USER + " = '" + GenericMethods.email + "'";

            SQLiteDatabase sqlite = db.getWritableDatabase();
            Cursor c = sqlite.rawQuery(query, null);

            while (c.moveToNext()) {
                HashMap<String, String> selectiondetails = new HashMap<>();
                selectiondetails.put("ID", c.getString(c.getColumnIndex(DBManager.TableInfo.KEYID)));
                selectiondetails.put("exec_email", c.getString(c.getColumnIndex(DBManager.TableInfo.KEY_LOGIN_USER)));
                selectiondetails.put("docid", c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_doc_id)));
                selectiondetails.put("appointment_id", c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_appointment_id)));
                selectiondetails.put("husband_biometric", c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_husbandbiometric)));
                selectiondetails.put("wife_biometric", c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_wifebiometric)));
                listUsername.add(selectiondetails);
            }
            closeCursor(c);

        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }
        db.close();
        System.out.println("biometric size:" + listUsername.size());
        return listUsername;
    }


    public ArrayList<HashMap<String, String>> getnotificationtable(DBOperation db) {
        ArrayList<HashMap<String, String>> listUsername = new ArrayList<>();
        try {
            String query = "SELECT * from " + DBManager.TableInfo.TABLE_NOTIFICATION + " where " + DBManager.TableInfo.KEY_LOGIN_USER + "='" + GenericMethods.email + "'";

            SQLiteDatabase sqlite = db.getWritableDatabase();
            Cursor c = sqlite.rawQuery(query, null);

            while (c.moveToNext()) {
                HashMap<String, String> selectiondetails = new HashMap<>();
                selectiondetails.put("appid", c.getString(c.getColumnIndex(DBManager.TableInfo.notification_appid)));
                selectiondetails.put("repeat", c.getString(c.getColumnIndex(DBManager.TableInfo.notification_repeat)));
                selectiondetails.put("interval", c.getString(c.getColumnIndex(DBManager.TableInfo.notification_interval)));
                selectiondetails.put("time", c.getString(c.getColumnIndex(DBManager.TableInfo.notification_time)));
                selectiondetails.put("starttime", c.getString(c.getColumnIndex(DBManager.TableInfo.notification_starttime)));
                selectiondetails.put("email", c.getString(c.getColumnIndex(DBManager.TableInfo.KEY_LOGIN_USER)));
                selectiondetails.put("value", c.getString(c.getColumnIndex(DBManager.TableInfo.notificationvalue)));

                listUsername.add(selectiondetails);
            }
            closeCursor(c);

        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }
        db.close();
        return listUsername;
    }

    public ArrayList<HashMap<String, String>> getadhocnotificationtable(DBOperation db) {
        ArrayList<HashMap<String, String>> listUsername = new ArrayList<>();
        try {
            String query = "SELECT * from " + DBManager.TableInfo.TABLE_NOTIFICATION + " where " + DBManager.TableInfo.notification_type + " = '" + "Mytask" + "'";

            SQLiteDatabase sqlite = db.getWritableDatabase();
            Cursor c = sqlite.rawQuery(query, null);

            while (c.moveToNext()) {
                HashMap<String, String> selectiondetails = new HashMap<>();
                selectiondetails.put("id1", c.getString(c.getColumnIndex(DBManager.TableInfo.notification_appid)));
                selectiondetails.put("repeat", c.getString(c.getColumnIndex(DBManager.TableInfo.notification_repeat)));
                selectiondetails.put("interval", c.getString(c.getColumnIndex(DBManager.TableInfo.notification_interval)));
                selectiondetails.put("time", c.getString(c.getColumnIndex(DBManager.TableInfo.notification_time)));
                selectiondetails.put("starttime", c.getString(c.getColumnIndex(DBManager.TableInfo.notification_starttime)));
                selectiondetails.put("email", c.getString(c.getColumnIndex(DBManager.TableInfo.KEY_LOGIN_USER)));
                listUsername.add(selectiondetails);
            }
            closeCursor(c);

        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }
        db.close();
        return listUsername;
    }

    public ArrayList<HashMap<String, String>> getwitnessnotificationtable(DBOperation db) {
        ArrayList<HashMap<String, String>> listUsername = new ArrayList<>();
        try {
            String query = "SELECT * from " + DBManager.TableInfo.TABLE_NOTIFICATION + " where " + DBManager.TableInfo.notification_type + " = '" + "Witness" + "'";

            SQLiteDatabase sqlite = db.getWritableDatabase();
            Cursor c = sqlite.rawQuery(query, null);

            while (c.moveToNext()) {
                HashMap<String, String> selectiondetails = new HashMap<>();
                selectiondetails.put("id1", c.getString(c.getColumnIndex(DBManager.TableInfo.notification_appid)));
                selectiondetails.put("repeat", c.getString(c.getColumnIndex(DBManager.TableInfo.notification_repeat)));
                selectiondetails.put("interval", c.getString(c.getColumnIndex(DBManager.TableInfo.notification_interval)));
                selectiondetails.put("time", c.getString(c.getColumnIndex(DBManager.TableInfo.notification_time)));
                selectiondetails.put("starttime", c.getString(c.getColumnIndex(DBManager.TableInfo.notification_starttime)));
                selectiondetails.put("email", c.getString(c.getColumnIndex(DBManager.TableInfo.KEY_LOGIN_USER)));
                listUsername.add(selectiondetails);
            }
            closeCursor(c);

        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }
        db.close();
        return listUsername;
    }

    public ArrayList<HashMap<String, String>> getcomplaintnotificationtable(DBOperation db) {
        ArrayList<HashMap<String, String>> listUsername = new ArrayList<>();
        try {
            String query = "SELECT * from " + DBManager.TableInfo.TABLE_NOTIFICATION + " where " + DBManager.TableInfo.notification_type + " = '" + "Complaint" + "'";

            SQLiteDatabase sqlite = db.getWritableDatabase();
            Cursor c = sqlite.rawQuery(query, null);

            while (c.moveToNext()) {
                HashMap<String, String> selectiondetails = new HashMap<>();
                selectiondetails.put("id1", c.getString(c.getColumnIndex(DBManager.TableInfo.notification_appid)));
                selectiondetails.put("repeat", c.getString(c.getColumnIndex(DBManager.TableInfo.notification_repeat)));
                selectiondetails.put("interval", c.getString(c.getColumnIndex(DBManager.TableInfo.notification_interval)));
                selectiondetails.put("time", c.getString(c.getColumnIndex(DBManager.TableInfo.notification_time)));
                selectiondetails.put("starttime", c.getString(c.getColumnIndex(DBManager.TableInfo.notification_starttime)));
                selectiondetails.put("email", c.getString(c.getColumnIndex(DBManager.TableInfo.KEY_LOGIN_USER)));
                listUsername.add(selectiondetails);
            }
            closeCursor(c);

        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }
        db.close();
        return listUsername;
    }

    public void UpdateacvrStatus(DBOperation db, String apointmentid) {

        String selection = DBManager.TableInfo.AppointmentId + " = ?";

        String a[] = {apointmentid};

        SQLiteDatabase sqlite = db.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(DBManager.TableInfo.SYNCSTATUSREPORT, "SYNC");

        long result = sqlite.update(DBManager.TableInfo.TABLE_NAME_APPOINTMENT, cv, selection, a);

        db.close();
    }

    public void Updateacvr(DBOperation db, String appid, String docid, String startdate, String startnewtime1,
                           String startnewtime2, String appaddress, String exeid, String appfor, String distance, String amount, String transporttype, String contactperson, String land, String attendeesstatus, String poststatus, String strlatitude1, String strlongitude1, String notification, String app_status) {
        // TODO Auto-generated method stub

        String selection = DBManager.TableInfo.AppointmentId + " = ? ";
        String a[] = {appid};
        SQLiteDatabase sqlite = db.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBManager.TableInfo.DocumentId, docid);
        cv.put(DBManager.TableInfo.StartDate, startdate);
        cv.put(DBManager.TableInfo.StartTime1, startnewtime1);
        cv.put(DBManager.TableInfo.StartTime2, startnewtime2);
        cv.put(DBManager.TableInfo.AppointmentAddress, appaddress);
        cv.put(DBManager.TableInfo.Executioner_id, exeid);
        cv.put(DBManager.TableInfo.AppFor, appfor);
        cv.put(DBManager.TableInfo.AppointmentId, appid);
        cv.put(DBManager.TableInfo.DISTANCE, distance);
        cv.put(DBManager.TableInfo.AMOUNT, amount);
        cv.put(DBManager.TableInfo.TRANSPORTTYPE, transporttype);
        cv.put(DBManager.TableInfo.contactperson, contactperson);
        cv.put(DBManager.TableInfo.landmark, land);
        cv.put(DBManager.TableInfo.att_status, attendeesstatus);
        cv.put(DBManager.TableInfo.post_status, poststatus);
        cv.put(DBManager.TableInfo.LATITUDE, strlatitude1);
        cv.put(DBManager.TableInfo.LONGITUDE, strlongitude1);
        cv.put(DBManager.TableInfo.notification, notification);
        cv.put(DBManager.TableInfo.App_status, app_status);
        System.out.println("notify"+notification);

        long result = sqlite.update(DBManager.TableInfo.TABLE_NAME_APPOINTMENT, cv, selection, a);
        db.close();
    }


    public boolean DocumentID(DBOperation db, String docid) {

        SQLiteDatabase sqlite = db.getWritableDatabase();
        String count = "SELECT count(*) FROM " + DBManager.TableInfo.TABLE_NAME_DOCUMENT + " where " + DBManager.TableInfo.DocumentId + "='" + docid + "'";
        Cursor mcursor = sqlite.rawQuery(count, null);
        mcursor.moveToFirst();

        int icount = mcursor.getInt(0);
        return icount > 0;
    }

    public boolean DocumentsalesID(DBOperation db, String docid) {

        SQLiteDatabase sqlite = db.getWritableDatabase();
        String count = "SELECT count(*) FROM " + DBManager.TableInfo.TABLE_SALES + " where " + DBManager.TableInfo.db_sales_doc_id + "='" + docid + "'";
        Cursor mcursor = sqlite.rawQuery(count, null);
        mcursor.moveToFirst();

        int icount = mcursor.getInt(0);
        return icount > 0;
    }

    public boolean DocumentmarriageID(DBOperation db, String docid) {

        SQLiteDatabase sqlite = db.getWritableDatabase();
        String count = "SELECT count(*) FROM " + DBManager.TableInfo.TABLE_MARRIAGE + " where " + DBManager.TableInfo.db_marriage_doc_id + "='" + docid + "'";
        Cursor mcursor = sqlite.rawQuery(count, null);
        mcursor.moveToFirst();

        int icount = mcursor.getInt(0);
        return icount > 0;
    }

    public boolean LoginID(DBOperation db, String umailid) {
        SQLiteDatabase sqlite = db.getWritableDatabase();
        String count = "SELECT count(*) FROM " + DBManager.TableInfo.TABLE_NAME1 + " where " + DBManager.TableInfo.UserEmail + "='" + umailid + "'";
        Cursor mcursor = sqlite.rawQuery(count, null);
        mcursor.moveToFirst();

        int icount = mcursor.getInt(0);
        return icount > 0;


    }

    public ArrayList<HashMap<String, String>> getAllList(DBOperation db) {

        ArrayList<HashMap<String, String>> getAllList = new ArrayList<>();
        SQLiteDatabase sqlite = db.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + DBManager.TableInfo.TABLE_NAME_APPOINTMENT + " t1, " + DBManager.TableInfo.TABLE_NAME_DOCUMENT + " t2 WHERE "
                + "t1." + DBManager.TableInfo.DocumentId + " = " + "t2." + DBManager.TableInfo.DocumentId + " AND t1."
                + DBManager.TableInfo.Executioner_id + "='" + Login.umailid + "'" + " ORDER BY " + "t1." + DBManager.TableInfo.StartDate;

        Cursor c = sqlite.rawQuery(selectQuery, null);
        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        String curdate1 = df2.format(c1.getTime());
        Date date, date1;
        if (c.moveToFirst()) {

            c.moveToFirst();
            do {
                HashMap<String, String> getAllListMap = new HashMap<>();
                String rtoken = c.getString(c.getColumnIndex("Rtoken"));
                String rkey = c.getString(c.getColumnIndex("Rkey"));
                String name = c.getString(c.getColumnIndex("Uname"));
                String mail = c.getString(c.getColumnIndex("Umail"));
                String contact = c.getString(c.getColumnIndex("Ucontact"));
                String padd = c.getString(c.getColumnIndex("Paddress"));
                String oname = c.getString(c.getColumnIndex("Oname"));
                String ocontact = c.getString(c.getColumnIndex("Ocontact"));
                String omail = c.getString(c.getColumnIndex("Omail"));
                String oadd = c.getString(c.getColumnIndex("Oaddress"));
                String tname = c.getString(c.getColumnIndex("Tname"));
                String tcontact = c.getString(c.getColumnIndex("Tcontact"));
                String tmail = c.getString(c.getColumnIndex("Tmail"));
                String tadd = c.getString(c.getColumnIndex("Taddress"));
                String fmail = c.getString(c.getColumnIndex("uemail"));
                String tstatus = c.getString(c.getColumnIndex("status"));
                String syncstatus = c.getString(c.getColumnIndex(DBManager.TableInfo.SYNCSTATUS));
                String distance = c.getString(c.getColumnIndex(DBManager.TableInfo.DISTANCE));
                String amount = c.getString(c.getColumnIndex(DBManager.TableInfo.AMOUNT));
                String trasporttype = c.getString(c.getColumnIndex(DBManager.TableInfo.TRANSPORTTYPE));
                String apointmentfor = c.getString(c.getColumnIndex(DBManager.TableInfo.AppFor));
                String acvrreportstatus = c.getString(c.getColumnIndex(DBManager.TableInfo.SYNCSTATUSREPORT));
                String latitude = c.getString(c.getColumnIndex("latitude1"));
                String longitude = c.getString(c.getColumnIndex("longitude1"));

                String appdate = c.getString(c.getColumnIndex("Appdate"));
                String biocomp = c.getString(c.getColumnIndex("Biocomp"));
                String appdate1 = c.getString(c.getColumnIndex("Appdate1"));
                String biocom1 = c.getString(c.getColumnIndex("Biocomp1"));
                String regfromcomp = c.getString(c.getColumnIndex("reg_from_app"));
                String witness = c.getString(c.getColumnIndex("witness"));
                String shipadd = c.getString(c.getColumnIndex("ship_add"));
                String shipdiffadd = c.getString(c.getColumnIndex("ship_diff_add"));
                String sdate = c.getString(c.getColumnIndex("Sdate"));
                String stime1 = c.getString(c.getColumnIndex("Stime1"));
                String stime2 = c.getString(c.getColumnIndex("Stime2"));
                String docid = c.getString(c.getColumnIndex("Did"));
                String appadress = c.getString(c.getColumnIndex("Aaddress"));
                String appid = c.getString(c.getColumnIndex("Aid"));
                String exid = c.getString(c.getColumnIndex("Exid"));
                String appfor = c.getString(c.getColumnIndex("appfor"));
                String apptype = c.getString(c.getColumnIndex("app_type"));
                String landmark = c.getString(c.getColumnIndex("landmark"));
                String contactperson = c.getString(c.getColumnIndex(DBManager.TableInfo.contactperson));

                String notify = c.getString(c.getColumnIndex(DBManager.TableInfo.notification));

                String post_status = c.getString(c.getColumnIndex("post_status"));

                String bio_status = c.getString(c.getColumnIndex(DBManager.TableInfo.att_status));
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    date = dateFormat.parse(sdate);
                    date1 = dateFormat.parse(curdate1);

                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                if (biocomp.equals("1") && biocom1.equals("1") && regfromcomp.equals("1") && witness.equals("1")) {
                    getAllListMap.put("Rtoken", rtoken);
                    getAllListMap.put("rkey", rkey);
                    getAllListMap.put("name", name);
                    getAllListMap.put("mail", mail);
                    getAllListMap.put("contact", contact);
                    getAllListMap.put("padd", padd);
                    getAllListMap.put("oname", oname);
                    getAllListMap.put("ocontact", ocontact);
                    getAllListMap.put("omail", omail);
                    getAllListMap.put("oadd", oadd);
                    getAllListMap.put("tname", tname);
                    getAllListMap.put("tcontact", tcontact);
                    getAllListMap.put("tmail", tmail);
                    getAllListMap.put("tadd", tadd);
                    getAllListMap.put("fmail", fmail);
                    getAllListMap.put("tstatus", tstatus);
                    getAllListMap.put("biocomp", biocomp);
                    getAllListMap.put("biocom1", biocom1);
                    getAllListMap.put("appdate", appdate);
                    getAllListMap.put("appdate1", appdate1);
                    getAllListMap.put("regfromcomp", regfromcomp);
                    getAllListMap.put("witness", witness);
                    getAllListMap.put("shipadd", shipadd);
                    getAllListMap.put("shipdiffadd", shipdiffadd);
                    getAllListMap.put("sdate", sdate);
                    getAllListMap.put("stime1", stime1);
                    getAllListMap.put("stime2", stime2);
                    getAllListMap.put("latitude", latitude);
                    getAllListMap.put("longitude", longitude);
                    getAllListMap.put("docid", docid);
                    getAllListMap.put("appadress", appadress);
                    getAllListMap.put("appid", appid);
                    getAllListMap.put("appfor", appfor);
                    getAllListMap.put("exid", exid);
                    getAllListMap.put("syncstatus", syncstatus);
                    getAllListMap.put("distance", distance);
                    getAllListMap.put("amount", amount);
                    getAllListMap.put("trasporttype", trasporttype);
                    getAllListMap.put("apointmentfor", apointmentfor);
                    getAllListMap.put("acvrreportstatus", acvrreportstatus);
                    getAllListMap.put("apptype", apptype);
                    getAllListMap.put("post_status", post_status);
                    getAllListMap.put("att_status", bio_status);
                    getAllListMap.put("notify", notify);
                    if (landmark.equals("null")) {

                        getAllListMap.put("landmark", "No details");
                    } else {
                        getAllListMap.put("landmark", landmark);
                    }
                    String[] contact_array = contactperson.split(",");
                    if (contactperson.equals("null")) {
                        getAllListMap.put("contact_person", "No details");
                        getAllListMap.put("contact_name", "No details");
                        getAllListMap.put("contact_email", "No details");
                        getAllListMap.put("contact_no", "No details");
                    } else if (contact_array.length > 1) {
                        getAllListMap.put("contact_person", "Details");
                        getAllListMap.put("contact_name", contact_array[0]);
                        getAllListMap.put("contact_email", contact_array[1]);
                        getAllListMap.put("contact_no", contact_array[2]);
                    }

                    getAllList.add(getAllListMap);
                }


            } while (c.moveToNext());

        }
        c.close();
        db.close();

        return getAllList;
    }

    public ArrayList<HashMap<String, String>> getAllList1(DBOperation db) {
        String rkey1 = "";

        ArrayList<HashMap<String, String>> getAllList = new ArrayList<>();
        SQLiteDatabase sqlite = db.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + DBManager.TableInfo.TABLE_NAME_APPOINTMENT + " t1, " + DBManager.TableInfo.TABLE_NAME_DOCUMENT + " t2 WHERE "
                + "t1." + DBManager.TableInfo.DocumentId + " = " + "t2." + DBManager.TableInfo.DocumentId + " AND t1."
                + DBManager.TableInfo.Executioner_id + "='" + Login.umailid + "'" + " ORDER BY " + "t1." + DBManager.TableInfo.StartDate;

        Cursor c = sqlite.rawQuery(selectQuery, null);
        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        String curdate1 = df2.format(c1.getTime());
        Date date, date1;
        if (c.moveToFirst()) {

            c.moveToFirst();
            do {
                HashMap<String, String> getAllListMap = new HashMap<>();
                String rtoken = c.getString(c.getColumnIndex("Rtoken"));
                String rkey = c.getString(c.getColumnIndex("Rkey"));
                String name = c.getString(c.getColumnIndex("Uname"));
                String mail = c.getString(c.getColumnIndex("Umail"));
                String contact = c.getString(c.getColumnIndex("Ucontact"));
                String padd = c.getString(c.getColumnIndex("Paddress"));
                String oname = c.getString(c.getColumnIndex("Oname"));
                String ocontact = c.getString(c.getColumnIndex("Ocontact"));
                String omail = c.getString(c.getColumnIndex("Omail"));
                String oadd = c.getString(c.getColumnIndex("Oaddress"));
                String tname = c.getString(c.getColumnIndex("Tname"));
                String tcontact = c.getString(c.getColumnIndex("Tcontact"));
                String tmail = c.getString(c.getColumnIndex("Tmail"));
                String tadd = c.getString(c.getColumnIndex("Taddress"));
                String fmail = c.getString(c.getColumnIndex("uemail"));
                String tstatus = c.getString(c.getColumnIndex("status"));
                String syncstatus = c.getString(c.getColumnIndex(DBManager.TableInfo.SYNCSTATUS));
                String distance = c.getString(c.getColumnIndex(DBManager.TableInfo.DISTANCE));
                String amount = c.getString(c.getColumnIndex(DBManager.TableInfo.AMOUNT));
                String trasporttype = c.getString(c.getColumnIndex(DBManager.TableInfo.TRANSPORTTYPE));
                String apointmentfor = c.getString(c.getColumnIndex(DBManager.TableInfo.AppFor));
                String acvrreportstatus = c.getString(c.getColumnIndex(DBManager.TableInfo.SYNCSTATUSREPORT));
                String latitude = c.getString(c.getColumnIndex("latitude1"));
                String longitude = c.getString(c.getColumnIndex("longitude1"));
                String appdate = c.getString(c.getColumnIndex("Appdate"));
                String biocomp = c.getString(c.getColumnIndex("Biocomp"));
                String appdate1 = c.getString(c.getColumnIndex("Appdate1"));
                String biocom1 = c.getString(c.getColumnIndex("Biocomp1"));
                String regfromcomp = c.getString(c.getColumnIndex("reg_from_app"));
                String witness = c.getString(c.getColumnIndex("witness"));
                String shipadd = c.getString(c.getColumnIndex("ship_add"));
                String shipdiffadd = c.getString(c.getColumnIndex("ship_diff_add"));
                String sdate = c.getString(c.getColumnIndex("Sdate"));
                String stime1 = c.getString(c.getColumnIndex("Stime1"));
                String stime2 = c.getString(c.getColumnIndex("Stime2"));
                String docid = c.getString(c.getColumnIndex("Did"));
                String appadress = c.getString(c.getColumnIndex("Aaddress"));
                String appid = c.getString(c.getColumnIndex("Aid"));
                String exid = c.getString(c.getColumnIndex("Exid"));
                String appfor = c.getString(c.getColumnIndex("appfor"));
                String apptype = c.getString(c.getColumnIndex("app_type"));
                String landmark = c.getString(c.getColumnIndex("landmark"));
                String post_status = c.getString(c.getColumnIndex("post_status"));
                String contactperson = c.getString(c.getColumnIndex(DBManager.TableInfo.contactperson));
                String bio_status = c.getString(c.getColumnIndex(DBManager.TableInfo.att_status));
                String notify = c.getString(c.getColumnIndex(DBManager.TableInfo.notification));

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    date = dateFormat.parse(sdate);
                    date1 = dateFormat.parse(curdate1);

                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (rkey.substring(0, 1).toLowerCase().equals("a")) {


                    rkey1 = "a" + rkey.substring(2);
                    if (rkey1.equals(GenericMethods.rkeyfn)) {

                    }
                } else if (rkey.substring(0, 1).toLowerCase().equals("n")) {

                    rkey1 = rkey.substring(0, 3).toLowerCase() + rkey.substring(4);
                    if (rkey1.equals(GenericMethods.rkeyfn)) {

                    }
                }
//
                if ((rkey.toLowerCase().equals(GenericMethods.rkeyfn) || rkey1.contains(GenericMethods.rkeyfn) || oname.toLowerCase().contains(GenericMethods.rkeyfn) || tname.toLowerCase().contains(GenericMethods.rkeyfn) || sdate.contains(GenericMethods.rkeyfn)) && (biocomp.equals("1") && biocom1.equals("1") && regfromcomp.equals("1") && witness.equals("1"))) {
                    getAllListMap.put("Rtoken", rtoken);
                    getAllListMap.put("rkey", rkey);
                    getAllListMap.put("name", name);
                    getAllListMap.put("mail", mail);
                    getAllListMap.put("contact", contact);
                    getAllListMap.put("padd", padd);
                    getAllListMap.put("oname", oname);
                    getAllListMap.put("ocontact", ocontact);
                    getAllListMap.put("omail", omail);
                    getAllListMap.put("oadd", oadd);
                    getAllListMap.put("tname", tname);
                    getAllListMap.put("tcontact", tcontact);
                    getAllListMap.put("tmail", tmail);
                    getAllListMap.put("tadd", tadd);
                    getAllListMap.put("fmail", fmail);
                    getAllListMap.put("tstatus", tstatus);
                    getAllListMap.put("biocomp", biocomp);
                    getAllListMap.put("biocom1", biocom1);
                    getAllListMap.put("appdate", appdate);
                    getAllListMap.put("appdate1", appdate1);
                    getAllListMap.put("regfromcomp", regfromcomp);
                    getAllListMap.put("witness", witness);
                    getAllListMap.put("shipadd", shipadd);
                    getAllListMap.put("shipdiffadd", shipdiffadd);
                    getAllListMap.put("sdate", sdate);
                    getAllListMap.put("stime1", stime1);
                    getAllListMap.put("stime2", stime2);
                    getAllListMap.put("latitude", latitude);
                    getAllListMap.put("longitude", longitude);
                    getAllListMap.put("docid", docid);
                    getAllListMap.put("appadress", appadress);
                    getAllListMap.put("appid", appid);
                    getAllListMap.put("appfor", appfor);
                    getAllListMap.put("exid", exid);
                    getAllListMap.put("syncstatus", syncstatus);
                    getAllListMap.put("distance", distance);
                    getAllListMap.put("amount", amount);
                    getAllListMap.put("trasporttype", trasporttype);
                    getAllListMap.put("apointmentfor", apointmentfor);
                    getAllListMap.put("acvrreportstatus", acvrreportstatus);
                    getAllListMap.put("apptype", apptype);
                    getAllListMap.put("post_status", post_status);
                    getAllListMap.put("att_status", bio_status);
                    getAllListMap.put("notify", notify);
                    if (landmark.equals("null")) {

                        getAllListMap.put("landmark", "No details");
                    } else {
                        getAllListMap.put("landmark", landmark);
                    }
                    String[] contact_array = contactperson.split(",");

                    if (contactperson.equals("null")) {
                        getAllListMap.put("contact_person", "No details");
                        getAllListMap.put("contact_name", "No details");
                        getAllListMap.put("contact_email", "No details");
                        getAllListMap.put("contact_no", "No details");
                    } else if (contact_array.length > 1) {
                        getAllListMap.put("contact_person", "Details");
                        getAllListMap.put("contact_name", contact_array[0]);
                        getAllListMap.put("contact_email", contact_array[1]);
                        getAllListMap.put("contact_no", contact_array[2]);
                    }
                    getAllList.add(getAllListMap);
                }


            } while (c.moveToNext());

        }
        c.close();
        db.close();
        return getAllList;
    }


    public ArrayList<HashMap<String, String>> getCommentlist(DBOperation db) {
        ArrayList<HashMap<String, String>> getAllCommentList = new ArrayList<>();
        SQLiteDatabase sqlite = db.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + DBManager.TableInfo.TABLE_NAME3 + " where " + DBManager.TableInfo.task_name + " = '" + "Biometric" + "'";
        Cursor cursor = sqlite.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            do {
                HashMap<String, String> getAllCommentListMap = new HashMap<>();
                String envid = cursor.getString(cursor.getColumnIndex("Env"));
                String cid = cursor.getString(cursor.getColumnIndex("Cid"));
                String cisdone = cursor.getString(cursor.getColumnIndex("Cisdone"));
                String cowner = cursor.getString(cursor.getColumnIndex("Cowner"));
                String cuser = cursor.getString(cursor.getColumnIndex("Cuser"));
                String ccomments = cursor.getString(cursor.getColumnIndex("Ccomments"));
                String cdate = cursor.getString(cursor.getColumnIndex("Cdate"));
                String commentstatus = cursor.getString(cursor.getColumnIndex(DBManager.TableInfo.SYNCSTATUS));
                getAllCommentListMap.put("Did", envid);
                getAllCommentListMap.put("Cid", cid);
                getAllCommentListMap.put("Cisdone", cisdone);
                getAllCommentListMap.put("Cowner", cowner);
                getAllCommentListMap.put("Cuser", cuser);
                getAllCommentListMap.put("Ccomments", ccomments);
                getAllCommentListMap.put("Cdate", cdate);
                getAllCommentListMap.put("CommentStatus", commentstatus);
                getAllCommentList.add(getAllCommentListMap);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return getAllCommentList;
    }

    public ArrayList<HashMap<String, String>> getsalesCommentlist(DBOperation db) {
        ArrayList<HashMap<String, String>> getAllCommentList = new ArrayList<>();
        SQLiteDatabase sqlite = db.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + DBManager.TableInfo.TABLE_NAME3 + " where " + DBManager.TableInfo.task_name + " = '" + "Sales" + "'";
        Cursor cursor = sqlite.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            do {
                HashMap<String, String> getAllCommentListMap = new HashMap<>();
                String envid = cursor.getString(cursor.getColumnIndex("Env"));
                String cid = cursor.getString(cursor.getColumnIndex("Cid"));
                String cisdone = cursor.getString(cursor.getColumnIndex("Cisdone"));
                String cowner = cursor.getString(cursor.getColumnIndex("Cowner"));
                String cuser = cursor.getString(cursor.getColumnIndex("Cuser"));
                String ccomments = cursor.getString(cursor.getColumnIndex("Ccomments"));
                String cdate = cursor.getString(cursor.getColumnIndex("Cdate"));
                String commentstatus = cursor.getString(cursor.getColumnIndex(DBManager.TableInfo.SYNCSTATUS));
                getAllCommentListMap.put("Did", envid);
                getAllCommentListMap.put("Cid", cid);
                getAllCommentListMap.put("Cisdone", cisdone);
                getAllCommentListMap.put("Cowner", cowner);
                getAllCommentListMap.put("Cuser", cuser);
                getAllCommentListMap.put("Ccomments", ccomments);
                getAllCommentListMap.put("Cdate", cdate);
                getAllCommentListMap.put("CommentStatus", commentstatus);
                getAllCommentList.add(getAllCommentListMap);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return getAllCommentList;
    }

    public ArrayList<HashMap<String, String>> getmarriageCommentlist(DBOperation db) {
        ArrayList<HashMap<String, String>> getAllCommentList = new ArrayList<>();
        SQLiteDatabase sqlite = db.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + DBManager.TableInfo.TABLE_NAME3 + " where " + DBManager.TableInfo.task_name + " = '" + "Marriage" + "'";
        Cursor cursor = sqlite.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            do {
                HashMap<String, String> getAllCommentListMap = new HashMap<>();
                String envid = cursor.getString(cursor.getColumnIndex("Env"));
                String cid = cursor.getString(cursor.getColumnIndex("Cid"));
                String cisdone = cursor.getString(cursor.getColumnIndex("Cisdone"));
                String cowner = cursor.getString(cursor.getColumnIndex("Cowner"));
                String cuser = cursor.getString(cursor.getColumnIndex("Cuser"));
                String ccomments = cursor.getString(cursor.getColumnIndex("Ccomments"));
                String cdate = cursor.getString(cursor.getColumnIndex("Cdate"));
                String commentstatus = cursor.getString(cursor.getColumnIndex(DBManager.TableInfo.SYNCSTATUS));
                getAllCommentListMap.put("Did", envid);
                getAllCommentListMap.put("Cid", cid);
                getAllCommentListMap.put("Cisdone", cisdone);
                getAllCommentListMap.put("Cowner", cowner);
                getAllCommentListMap.put("Cuser", cuser);
                getAllCommentListMap.put("Ccomments", ccomments);
                getAllCommentListMap.put("Cdate", cdate);
                getAllCommentListMap.put("CommentStatus", commentstatus);
                getAllCommentList.add(getAllCommentListMap);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return getAllCommentList;
    }

    public ArrayList<HashMap<String, String>> getdataList(DBOperation db) {
        ArrayList<HashMap<String, String>> getDataList = new ArrayList<>();
        SQLiteDatabase sqlite = db.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + DBManager.TableInfo.TABLE_NAME5;
        Cursor cursor1 = sqlite.rawQuery(selectQuery, null);

        if (cursor1.moveToFirst()) {
            int count = cursor1.getCount();


            cursor1.moveToFirst();
            do {
                HashMap<String, String> getdataListMap = new HashMap<>();
                String userid = cursor1.getString(cursor1.getColumnIndex(DBManager.TableInfo.User_id));
                String username = cursor1.getString(cursor1.getColumnIndex(DBManager.TableInfo.User_name));
                String email = cursor1.getString(cursor1.getColumnIndex(DBManager.TableInfo.Email));
                String role = cursor1.getString(cursor1.getColumnIndex(DBManager.TableInfo.Role));
                getdataListMap.put("UserId", userid);
                getdataListMap.put("username", username);
                getdataListMap.put("email", email);
                getdataListMap.put("role", role);

                getDataList.add(getdataListMap);
            } while (cursor1.moveToNext());


        }
        cursor1.close();
        db.close();
        return getDataList;
    }


    public ArrayList<HashMap<String, String>> getAllOlderist(DBOperation db) {

        ArrayList<HashMap<String, String>> getAllList = new ArrayList<>();
        SQLiteDatabase sqlite = db.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + DBManager.TableInfo.TABLE_NAME_APPOINTMENT + " t1, " + DBManager.TableInfo.TABLE_NAME_DOCUMENT + " t2 WHERE "
                + "t1." + DBManager.TableInfo.DocumentId + " = " + "t2." + DBManager.TableInfo.DocumentId + " AND t1."
                + DBManager.TableInfo.Executioner_id + "='" + Login.umailid + "'" + " ORDER BY " + "t1." + DBManager.TableInfo.StartTime1;

        Cursor c = sqlite.rawQuery(selectQuery, null);
        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        String curdate1 = df2.format(c1.getTime());
        Date date = null, date1 = null;
        if (c.moveToFirst()) {

            c.moveToFirst();
            do {
                HashMap<String, String> getAllListMap = new HashMap<>();
                String rtoken = c.getString(c.getColumnIndex("Rtoken"));
                String rkey = c.getString(c.getColumnIndex("Rkey"));
                String sgrn = c.getString(c.getColumnIndex("sgrn"));
                String rgrn = c.getString(c.getColumnIndex("rgrn"));
                String name = c.getString(c.getColumnIndex("Uname"));
                String mail = c.getString(c.getColumnIndex("Umail"));
                String appstatus=c.getString(c.getColumnIndex("app_status"));
                String contact = c.getString(c.getColumnIndex("Ucontact"));
                String padd = c.getString(c.getColumnIndex("Paddress"));
                String oname = c.getString(c.getColumnIndex("Oname"));
                String ocontact = c.getString(c.getColumnIndex("Ocontact"));
                String omail = c.getString(c.getColumnIndex("Omail"));
                String oadd = c.getString(c.getColumnIndex("Oaddress"));
                String tname = c.getString(c.getColumnIndex("Tname"));
                String tcontact = c.getString(c.getColumnIndex("Tcontact"));
                String tmail = c.getString(c.getColumnIndex("Tmail"));
                String tadd = c.getString(c.getColumnIndex("Taddress"));
                String fmail = c.getString(c.getColumnIndex("uemail"));
                String tstatus = c.getString(c.getColumnIndex("status"));
                String syncstatus = c.getString(c.getColumnIndex(DBManager.TableInfo.SYNCSTATUS));
                String distance = c.getString(c.getColumnIndex(DBManager.TableInfo.DISTANCE));
                String amount = c.getString(c.getColumnIndex(DBManager.TableInfo.AMOUNT));
                String trasporttype = c.getString(c.getColumnIndex(DBManager.TableInfo.TRANSPORTTYPE));
                String apointmentfor = c.getString(c.getColumnIndex(DBManager.TableInfo.AppFor));
                String acvrreportstatus = c.getString(c.getColumnIndex(DBManager.TableInfo.SYNCSTATUSREPORT));
                String latitude = c.getString(c.getColumnIndex("latitude1"));
                String longitude = c.getString(c.getColumnIndex("longitude1"));
                String appdate = c.getString(c.getColumnIndex("Appdate"));
                String biocomp = c.getString(c.getColumnIndex("Biocomp"));
                String appdate1 = c.getString(c.getColumnIndex("Appdate1"));
                String biocom1 = c.getString(c.getColumnIndex("Biocomp1"));
                String regfromcomp = c.getString(c.getColumnIndex("reg_from_app"));
                String witness = c.getString(c.getColumnIndex("witness"));
                String shipadd = c.getString(c.getColumnIndex("ship_add"));
                String shipdiffadd = c.getString(c.getColumnIndex("ship_diff_add"));

                String sdate = c.getString(c.getColumnIndex("Sdate"));
                String stime1 = c.getString(c.getColumnIndex("Stime1"));
                String stime2 = c.getString(c.getColumnIndex("Stime2"));

                String docid = c.getString(c.getColumnIndex("Did"));
                String appadress = c.getString(c.getColumnIndex("Aaddress"));

                String appid = c.getString(c.getColumnIndex("Aid"));
                String exid = c.getString(c.getColumnIndex((DBManager.TableInfo.Executioner_id)));
                String appfor = c.getString(c.getColumnIndex("appfor"));
                String apptype = c.getString(c.getColumnIndex("app_type"));
                String landmark = c.getString(c.getColumnIndex("landmark"));
                String contactperson = c.getString(c.getColumnIndex(DBManager.TableInfo.contactperson));
                String bio_status = c.getString(c.getColumnIndex(DBManager.TableInfo.att_status));
                String notify = c.getString(c.getColumnIndex(DBManager.TableInfo.notification));


                String post_status = c.getString(c.getColumnIndex("post_status"));


                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    date = dateFormat.parse(sdate);
                    date1 = dateFormat.parse(curdate1);
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


                if (date.before(date1) && (!biocomp.equals("1") || !biocom1.equals("1") || !regfromcomp.equals("1")
                        || (!witness.equals("1")) &&  ((appstatus.equals("2")) || (appstatus.equals("3")) || (appstatus.equals("6")) || (appstatus.equals("7"))))) {

                    getAllListMap.put("Rtoken", rtoken);
                    getAllListMap.put("rkey", rkey);
                    getAllListMap.put("s_grn", sgrn);
                    getAllListMap.put("r_grn", rgrn);
                    getAllListMap.put("appstatus",appstatus);
                    getAllListMap.put("name", name);
                    getAllListMap.put("mail", mail);
                    getAllListMap.put("contact", contact);
                    getAllListMap.put("padd", padd);
                    getAllListMap.put("oname", oname);
                    getAllListMap.put("ocontact", ocontact);
                    getAllListMap.put("omail", omail);
                    getAllListMap.put("oadd", oadd);
                    getAllListMap.put("tname", tname);
                    getAllListMap.put("tcontact", tcontact);
                    getAllListMap.put("tmail", tmail);
                    getAllListMap.put("tadd", tadd);
                    getAllListMap.put("fmail", fmail);
                    getAllListMap.put("tstatus", tstatus);
                    getAllListMap.put("biocomp", biocomp);
                    getAllListMap.put("biocom1", biocom1);
                    getAllListMap.put("appdate", appdate);
                    getAllListMap.put("appdate1", appdate1);
                    getAllListMap.put("regfromcomp", regfromcomp);
                    getAllListMap.put("witness", witness);
                    getAllListMap.put("shipadd", shipadd);
                    getAllListMap.put("shipdiffadd", shipdiffadd);
                    getAllListMap.put("sdate", sdate);
                    getAllListMap.put("stime1", stime1);
                    getAllListMap.put("stime2", stime2);
                    getAllListMap.put("latitude", latitude);
                    getAllListMap.put("longitude", longitude);
                    getAllListMap.put("docid", docid);
                    getAllListMap.put("appadress", appadress);
                    getAllListMap.put("appid", appid);
                    getAllListMap.put("appfor", appfor);
                    getAllListMap.put("exid", exid);
                    getAllListMap.put("syncstatus", syncstatus);
                    getAllListMap.put("distance", distance);
                    getAllListMap.put("amount", amount);
                    getAllListMap.put("trasporttype", trasporttype);
                    getAllListMap.put("apointmentfor", apointmentfor);
                    getAllListMap.put("acvrreportstatus", acvrreportstatus);
                    getAllListMap.put("apptype", apptype);
                    getAllListMap.put("post_status", post_status);
                    getAllListMap.put("att_status", bio_status);
                    getAllListMap.put("notify", notify);


                    if (landmark.equals("null")) {

                        getAllListMap.put("landmark", "No details");

                    } else {
                        getAllListMap.put("landmark", landmark);

                    }

                    String[] contact_array = contactperson.split(",");
                    if (contactperson.equals("null")) {
                        getAllListMap.put("contact_person", "No details");
                        getAllListMap.put("contact_name", "No details");
                        getAllListMap.put("contact_email", "No details");
                        getAllListMap.put("contact_no", "No details");
                    } else if (contact_array.length > 1) {
                        getAllListMap.put("contact_person", "Details");
                        getAllListMap.put("contact_name", contact_array[0]);
                        getAllListMap.put("contact_email", contact_array[1]);
                        getAllListMap.put("contact_no", contact_array[2]);
                    }

                    getAllList.add(getAllListMap);

                }


            } while (c.moveToNext());

        }
        if (c != null) {
            c.close();
            db.close();
        }

        return getAllList;
    }


    public ArrayList<HashMap<String, String>> getAllOlderist1(DBOperation db) {

        ArrayList<HashMap<String, String>> getAllList = new ArrayList<>();
        SQLiteDatabase sqlite = db.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + DBManager.TableInfo.TABLE_NAME_APPOINTMENT + " t1, " + DBManager.TableInfo.TABLE_NAME_DOCUMENT + " t2 WHERE "
                + "t1." + DBManager.TableInfo.DocumentId + " = " + "t2." + DBManager.TableInfo.DocumentId + " AND t1."
                + DBManager.TableInfo.Executioner_id + "='" + Login.umailid + "'" + " ORDER BY " + "t1." + DBManager.TableInfo.StartTime1;

        Cursor c = sqlite.rawQuery(selectQuery, null);
        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        String curdate1 = df2.format(c1.getTime());
        String rkey1 = "";
        Date date = null, date1 = null;
        if (c.moveToFirst()) {

            c.moveToFirst();
            do {
                HashMap<String, String> getAllListMap = new HashMap<>();
                String rtoken = c.getString(c.getColumnIndex("Rtoken"));
                String rkey = c.getString(c.getColumnIndex("Rkey"));
                String sgrn = c.getString(c.getColumnIndex("sgrn"));
                String rgrn = c.getString(c.getColumnIndex("rgrn"));
                String name = c.getString(c.getColumnIndex("Uname"));
                String mail = c.getString(c.getColumnIndex("Umail"));
                String contact = c.getString(c.getColumnIndex("Ucontact"));
                String padd = c.getString(c.getColumnIndex("Paddress"));
                String oname = c.getString(c.getColumnIndex("Oname"));
                String ocontact = c.getString(c.getColumnIndex("Ocontact"));
                String omail = c.getString(c.getColumnIndex("Omail"));
                String oadd = c.getString(c.getColumnIndex("Oaddress"));
                String tname = c.getString(c.getColumnIndex("Tname"));
                String tcontact = c.getString(c.getColumnIndex("Tcontact"));
                String tmail = c.getString(c.getColumnIndex("Tmail"));
                String tadd = c.getString(c.getColumnIndex("Taddress"));
                String fmail = c.getString(c.getColumnIndex("uemail"));
                String tstatus = c.getString(c.getColumnIndex("status"));
                String syncstatus = c.getString(c.getColumnIndex(DBManager.TableInfo.SYNCSTATUS));
                String distance = c.getString(c.getColumnIndex(DBManager.TableInfo.DISTANCE));
                String amount = c.getString(c.getColumnIndex(DBManager.TableInfo.AMOUNT));
                String trasporttype = c.getString(c.getColumnIndex(DBManager.TableInfo.TRANSPORTTYPE));
                String apointmentfor = c.getString(c.getColumnIndex(DBManager.TableInfo.AppFor));
                String acvrreportstatus = c.getString(c.getColumnIndex(DBManager.TableInfo.SYNCSTATUSREPORT));
                String latitude = c.getString(c.getColumnIndex("latitude1"));
                String longitude = c.getString(c.getColumnIndex("longitude1"));
                String appdate = c.getString(c.getColumnIndex("Appdate"));
                String biocomp = c.getString(c.getColumnIndex("Biocomp"));
                String appdate1 = c.getString(c.getColumnIndex("Appdate1"));
                String biocom1 = c.getString(c.getColumnIndex("Biocomp1"));
                String regfromcomp = c.getString(c.getColumnIndex("reg_from_app"));
                String witness = c.getString(c.getColumnIndex("witness"));
                String shipadd = c.getString(c.getColumnIndex("ship_add"));
                String shipdiffadd = c.getString(c.getColumnIndex("ship_diff_add"));

                String sdate = c.getString(c.getColumnIndex("Sdate"));
                String stime1 = c.getString(c.getColumnIndex("Stime1"));
                String stime2 = c.getString(c.getColumnIndex("Stime2"));

                String docid = c.getString(c.getColumnIndex("Did"));
                String appadress = c.getString(c.getColumnIndex("Aaddress"));

                String appid = c.getString(c.getColumnIndex("Aid"));
                String exid = c.getString(c.getColumnIndex("Exid"));
                String appfor = c.getString(c.getColumnIndex("appfor"));
                String apptype = c.getString(c.getColumnIndex("app_type"));
                String landmark = c.getString(c.getColumnIndex("landmark"));
                String contactperson = c.getString(c.getColumnIndex(DBManager.TableInfo.contactperson));
                String post_status = c.getString(c.getColumnIndex("post_status"));
                String bio_status = c.getString(c.getColumnIndex(DBManager.TableInfo.att_status));
                String notify = c.getString(c.getColumnIndex(DBManager.TableInfo.notification));

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    date = dateFormat.parse(sdate);
                    date1 = dateFormat.parse(curdate1);

                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                if (rkey.substring(0, 1).toLowerCase().equals("a")) {


                    rkey1 = "a" + rkey.substring(2);
                    if (rkey1.equals(GenericMethods.rkeyfn)) {
                    }
                } else if (rkey.substring(0, 1).toLowerCase().equals("n")) {

                    rkey1 = rkey.substring(0, 3).toLowerCase() + rkey.substring(4);
                    if (rkey1.equals(GenericMethods.rkeyfn)) {
                    }
                }


                if ((rkey.toLowerCase().equals(GenericMethods.rkeyfn) || rkey1.contains(GenericMethods.rkeyfn) || oname.toLowerCase().contains(GenericMethods.rkeyfn) || tname.toLowerCase().contains(GenericMethods.rkeyfn) || sdate.contains(GenericMethods.rkeyfn)) && (date.before(date1) && (!biocomp.equals("1") || !biocom1.equals("1") || !regfromcomp.equals("1")
                        || !witness.equals("1")))) {

                    getAllListMap.put("Rtoken", rtoken);
                    getAllListMap.put("rkey", rkey);
                    getAllListMap.put("s_grn", sgrn);
                    getAllListMap.put("r_grn", rgrn);
                    getAllListMap.put("name", name);
                    getAllListMap.put("mail", mail);
                    getAllListMap.put("contact", contact);
                    getAllListMap.put("padd", padd);
                    getAllListMap.put("oname", oname);
                    getAllListMap.put("ocontact", ocontact);
                    getAllListMap.put("omail", omail);
                    getAllListMap.put("oadd", oadd);
                    getAllListMap.put("tname", tname);
                    getAllListMap.put("tcontact", tcontact);
                    getAllListMap.put("tmail", tmail);
                    getAllListMap.put("tadd", tadd);
                    getAllListMap.put("fmail", fmail);
                    getAllListMap.put("tstatus", tstatus);
                    getAllListMap.put("biocomp", biocomp);
                    getAllListMap.put("biocom1", biocom1);
                    getAllListMap.put("appdate", appdate);
                    getAllListMap.put("appdate1", appdate1);
                    getAllListMap.put("regfromcomp", regfromcomp);
                    getAllListMap.put("witness", witness);
                    getAllListMap.put("shipadd", shipadd);
                    getAllListMap.put("shipdiffadd", shipdiffadd);
                    getAllListMap.put("sdate", sdate);
                    getAllListMap.put("stime1", stime1);
                    getAllListMap.put("stime2", stime2);
                    getAllListMap.put("latitude", latitude);
                    getAllListMap.put("longitude", longitude);
                    getAllListMap.put("docid", docid);
                    getAllListMap.put("appadress", appadress);
                    getAllListMap.put("appid", appid);
                    getAllListMap.put("appfor", appfor);
                    getAllListMap.put("exid", exid);
                    getAllListMap.put("syncstatus", syncstatus);
                    getAllListMap.put("distance", distance);
                    getAllListMap.put("amount", amount);
                    getAllListMap.put("trasporttype", trasporttype);
                    getAllListMap.put("apointmentfor", apointmentfor);
                    getAllListMap.put("acvrreportstatus", acvrreportstatus);
                    getAllListMap.put("apptype", apptype);
                    getAllListMap.put("post_status", post_status);
                    getAllListMap.put("att_status", bio_status);
                    getAllListMap.put("notify", notify);
                    if (landmark.equals("null")) {

                        getAllListMap.put("landmark", "No details");
                    } else {
                        getAllListMap.put("landmark", landmark);
                    }
                    String[] contact_array = contactperson.split(",");
                    if (contactperson.equals("null")) {
                        getAllListMap.put("contact_person", "No details");
                        getAllListMap.put("contact_name", "No details");
                        getAllListMap.put("contact_email", "No details");
                        getAllListMap.put("contact_no", "No details");
                    } else if (contact_array.length > 1) {
                        getAllListMap.put("contact_person", "Details");
                        getAllListMap.put("contact_name", contact_array[0]);
                        getAllListMap.put("contact_email", contact_array[1]);
                        getAllListMap.put("contact_no", contact_array[2]);
                    }
                    getAllList.add(getAllListMap);
                }


            } while (c.moveToNext());

        }
        c.close();
        db.close();

        return getAllList;
    }


    public ArrayList<HashMap<String, String>> getAllNewList(DBOperation db) {

        ArrayList<HashMap<String, String>> getAllList = new ArrayList<>();
        SQLiteDatabase sqlite = db.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + DBManager.TableInfo.TABLE_NAME_APPOINTMENT + " t1, " + DBManager.TableInfo.TABLE_NAME_DOCUMENT + " t2 WHERE "
                + "t1." + DBManager.TableInfo.DocumentId + " = " + "t2." + DBManager.TableInfo.DocumentId + " AND t1."
                + DBManager.TableInfo.Executioner_id + "='" + Login.umailid + "'" + " ORDER BY " + "t1." + DBManager.TableInfo.StartTime1;

        Cursor c = sqlite.rawQuery(selectQuery, null);
        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        String curdate1 = df2.format(c1.getTime());
        Date date = null, date1 = null;
        if (c.moveToFirst()) {

            c.moveToFirst();
            do {
                HashMap<String, String> getAllListMap = new HashMap<>();
                String rtoken = c.getString(c.getColumnIndex("Rtoken"));
                String rkey = c.getString(c.getColumnIndex("Rkey"));
                String sgrn = c.getString(c.getColumnIndex("sgrn"));
                String rgrn = c.getString(c.getColumnIndex("rgrn"));
                String name = c.getString(c.getColumnIndex("Uname"));
                String appstatus=c.getString(c.getColumnIndex("app_status"));
                String mail = c.getString(c.getColumnIndex("Umail"));
                String contact = c.getString(c.getColumnIndex("Ucontact"));
                String padd = c.getString(c.getColumnIndex("Paddress"));
                String oname = c.getString(c.getColumnIndex("Oname"));
                String ocontact = c.getString(c.getColumnIndex("Ocontact"));
                String omail = c.getString(c.getColumnIndex("Omail"));
                String oadd = c.getString(c.getColumnIndex("Oaddress"));
                String tname = c.getString(c.getColumnIndex("Tname"));
                String tcontact = c.getString(c.getColumnIndex("Tcontact"));
                String tmail = c.getString(c.getColumnIndex("Tmail"));
                String tadd = c.getString(c.getColumnIndex("Taddress"));
                String fmail = c.getString(c.getColumnIndex("uemail"));
                String tstatus = c.getString(c.getColumnIndex("status"));
                String syncstatus = c.getString(c.getColumnIndex(DBManager.TableInfo.SYNCSTATUS));
                String distance = c.getString(c.getColumnIndex(DBManager.TableInfo.DISTANCE));
                String amount = c.getString(c.getColumnIndex(DBManager.TableInfo.AMOUNT));
                String trasporttype = c.getString(c.getColumnIndex(DBManager.TableInfo.TRANSPORTTYPE));
                String apointmentfor = c.getString(c.getColumnIndex(DBManager.TableInfo.AppFor));
                String acvrreportstatus = c.getString(c.getColumnIndex(DBManager.TableInfo.SYNCSTATUSREPORT));
                String appdate = c.getString(c.getColumnIndex("Appdate"));
                String biocomp = c.getString(c.getColumnIndex("Biocomp"));
                String appdate1 = c.getString(c.getColumnIndex("Appdate1"));
                String biocom1 = c.getString(c.getColumnIndex("Biocomp1"));
                String regfromcomp = c.getString(c.getColumnIndex("reg_from_app"));
                String witness = c.getString(c.getColumnIndex("witness"));
                String shipadd = c.getString(c.getColumnIndex("ship_add"));
                String shipdiffadd = c.getString(c.getColumnIndex("ship_diff_add"));
                String latitude = c.getString(c.getColumnIndex("latitude1"));
                String longitude = c.getString(c.getColumnIndex("longitude1"));
                String sdate = c.getString(c.getColumnIndex("Sdate"));
                String stime1 = c.getString(c.getColumnIndex("Stime1"));
                String stime2 = c.getString(c.getColumnIndex("Stime2"));

                String docid = c.getString(c.getColumnIndex("Did"));
                String appadress = c.getString(c.getColumnIndex("Aaddress"));

                String appid = c.getString(c.getColumnIndex("Aid"));
                String exid = c.getString(c.getColumnIndex("Exid"));
                String appfor = c.getString(c.getColumnIndex("appfor"));
                String apptype = c.getString(c.getColumnIndex("app_type"));
                String landmark = c.getString(c.getColumnIndex("landmark"));
                String contactperson = c.getString(c.getColumnIndex(DBManager.TableInfo.contactperson));

                String post_status = c.getString(c.getColumnIndex("post_status"));
                String bio_status = c.getString(c.getColumnIndex(DBManager.TableInfo.att_status));
                String notify = c.getString(c.getColumnIndex(DBManager.TableInfo.notification));

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    date = dateFormat.parse(sdate);
                    date1 = dateFormat.parse(curdate1);

                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


                if ((date.after(date1)) && ((appstatus.equals("2")) || (appstatus.equals("3")) || (appstatus.equals("6")) || (appstatus.equals("7")))) {
                    getAllListMap.put("Rtoken", rtoken);
                    getAllListMap.put("rkey", rkey);
                    getAllListMap.put("s_grn", sgrn);
                    getAllListMap.put("r_grn", rgrn);
                    getAllListMap.put("appstatus",appstatus);
                    getAllListMap.put("name", name);
                    getAllListMap.put("mail", mail);
                    getAllListMap.put("contact", contact);
                    getAllListMap.put("padd", padd);
                    getAllListMap.put("oname", oname);
                    getAllListMap.put("ocontact", ocontact);
                    getAllListMap.put("omail", omail);
                    getAllListMap.put("oadd", oadd);
                    getAllListMap.put("tname", tname);
                    getAllListMap.put("tcontact", tcontact);
                    getAllListMap.put("tmail", tmail);
                    getAllListMap.put("tadd", tadd);
                    getAllListMap.put("fmail", fmail);
                    getAllListMap.put("latitude", latitude);
                    getAllListMap.put("longitude", longitude);
                    getAllListMap.put("tstatus", tstatus);
                    getAllListMap.put("biocomp", biocomp);
                    getAllListMap.put("biocom1", biocom1);
                    getAllListMap.put("appdate", appdate);
                    getAllListMap.put("appdate1", appdate1);
                    getAllListMap.put("regfromcomp", regfromcomp);
                    getAllListMap.put("witness", witness);
                    getAllListMap.put("shipadd", shipadd);
                    getAllListMap.put("shipdiffadd", shipdiffadd);
                    getAllListMap.put("sdate", sdate);
                    getAllListMap.put("stime1", stime1);
                    getAllListMap.put("stime2", stime2);
                    getAllListMap.put("docid", docid);
                    getAllListMap.put("appadress", appadress);
                    getAllListMap.put("appid", appid);
                    getAllListMap.put("appfor", appfor);
                    getAllListMap.put("exid", exid);
                    getAllListMap.put("syncstatus", syncstatus);
                    getAllListMap.put("distance", distance);
                    getAllListMap.put("amount", amount);
                    getAllListMap.put("trasporttype", trasporttype);
                    getAllListMap.put("apointmentfor", apointmentfor);
                    getAllListMap.put("acvrreportstatus", acvrreportstatus);
                    getAllListMap.put("apptype", apptype);
                    getAllListMap.put("post_status", post_status);
                    getAllListMap.put("att_status", bio_status);
                    getAllListMap.put("notify", notify);
                    if (landmark.equals("null")) {

                        getAllListMap.put("landmark", "No details");
                    } else {
                        getAllListMap.put("landmark", landmark);
                    }
                    String[] contact_array = contactperson.split(",");
                    if (contactperson.equals("null")) {
                        getAllListMap.put("contact_person", "No details");
                        getAllListMap.put("contact_name", "No details");
                        getAllListMap.put("contact_email", "No details");
                        getAllListMap.put("contact_no", "No details");
                    } else if (contact_array.length > 1) {
                        getAllListMap.put("contact_person", "Details");
                        getAllListMap.put("contact_name", contact_array[0]);
                        getAllListMap.put("contact_email", contact_array[1]);
                        getAllListMap.put("contact_no", contact_array[2]);
                    }
                    getAllList.add(getAllListMap);
                }


            } while (c.moveToNext());

        }
        c.close();
        db.close();

        return getAllList;
    }

    public ArrayList<HashMap<String, String>> getAllNewList1(DBOperation db) {
        String rkey1 = "";

        ArrayList<HashMap<String, String>> getAllList = new ArrayList<>();
        SQLiteDatabase sqlite = db.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + DBManager.TableInfo.TABLE_NAME_APPOINTMENT + " t1, " + DBManager.TableInfo.TABLE_NAME_DOCUMENT + " t2 WHERE "
                + "t1." + DBManager.TableInfo.DocumentId + " = " + "t2." + DBManager.TableInfo.DocumentId + " AND t1."
                + DBManager.TableInfo.Executioner_id + "='" + Login.umailid + "'" + " ORDER BY " + "t1." + DBManager.TableInfo.StartTime1;

        Cursor c = sqlite.rawQuery(selectQuery, null);
        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        String curdate1 = df2.format(c1.getTime());
        Date date = null, date1 = null;
        if (c.moveToFirst()) {

            c.moveToFirst();
            do {
                HashMap<String, String> getAllListMap = new HashMap<>();
                String rtoken = c.getString(c.getColumnIndex("Rtoken"));
                String rkey = c.getString(c.getColumnIndex("Rkey"));
                String sgrn = c.getString(c.getColumnIndex("sgrn"));
                String rgrn = c.getString(c.getColumnIndex("rgrn"));
                String name = c.getString(c.getColumnIndex("Uname"));
                String mail = c.getString(c.getColumnIndex("Umail"));
                String contact = c.getString(c.getColumnIndex("Ucontact"));
                String padd = c.getString(c.getColumnIndex("Paddress"));
                String oname = c.getString(c.getColumnIndex("Oname"));
                String ocontact = c.getString(c.getColumnIndex("Ocontact"));
                String omail = c.getString(c.getColumnIndex("Omail"));
                String oadd = c.getString(c.getColumnIndex("Oaddress"));
                String tname = c.getString(c.getColumnIndex("Tname"));
                String tcontact = c.getString(c.getColumnIndex("Tcontact"));
                String tmail = c.getString(c.getColumnIndex("Tmail"));
                String tadd = c.getString(c.getColumnIndex("Taddress"));
                String fmail = c.getString(c.getColumnIndex("uemail"));
                String tstatus = c.getString(c.getColumnIndex("status"));
                String syncstatus = c.getString(c.getColumnIndex(DBManager.TableInfo.SYNCSTATUS));
                String distance = c.getString(c.getColumnIndex(DBManager.TableInfo.DISTANCE));
                String amount = c.getString(c.getColumnIndex(DBManager.TableInfo.AMOUNT));
                String trasporttype = c.getString(c.getColumnIndex(DBManager.TableInfo.TRANSPORTTYPE));
                String apointmentfor = c.getString(c.getColumnIndex(DBManager.TableInfo.AppFor));
                String acvrreportstatus = c.getString(c.getColumnIndex(DBManager.TableInfo.SYNCSTATUSREPORT));
                String appdate = c.getString(c.getColumnIndex("Appdate"));
                String biocomp = c.getString(c.getColumnIndex("Biocomp"));
                String appdate1 = c.getString(c.getColumnIndex("Appdate1"));
                String biocom1 = c.getString(c.getColumnIndex("Biocomp1"));
                String regfromcomp = c.getString(c.getColumnIndex("reg_from_app"));
                String witness = c.getString(c.getColumnIndex("witness"));
                String shipadd = c.getString(c.getColumnIndex("ship_add"));
                String shipdiffadd = c.getString(c.getColumnIndex("ship_diff_add"));
                String latitude = c.getString(c.getColumnIndex("latitude1"));
                String longitude = c.getString(c.getColumnIndex("longitude1"));
                String sdate = c.getString(c.getColumnIndex("Sdate"));
                String stime1 = c.getString(c.getColumnIndex("Stime1"));
                String stime2 = c.getString(c.getColumnIndex("Stime2"));

                String docid = c.getString(c.getColumnIndex("Did"));
                String appadress = c.getString(c.getColumnIndex("Aaddress"));

                String appid = c.getString(c.getColumnIndex("Aid"));
                String exid = c.getString(c.getColumnIndex("Exid"));
                String appfor = c.getString(c.getColumnIndex("appfor"));
                String apptype = c.getString(c.getColumnIndex("app_type"));
                String landmark = c.getString(c.getColumnIndex("landmark"));
                String contactperson = c.getString(c.getColumnIndex(DBManager.TableInfo.contactperson));
                String post_status = c.getString(c.getColumnIndex("post_status"));
                String bio_status = c.getString(c.getColumnIndex(DBManager.TableInfo.att_status));
                String notify = c.getString(c.getColumnIndex(DBManager.TableInfo.notification));

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    date = dateFormat.parse(sdate);
                    date1 = dateFormat.parse(curdate1);

                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (rkey.substring(0, 1).toLowerCase().equals("a")) {


                    rkey1 = "a" + rkey.substring(2);
                    if (rkey1.equals(GenericMethods.rkeyfn)) {
                    }
                } else if (rkey.substring(0, 1).toLowerCase().equals("n")) {

                    rkey1 = rkey.substring(0, 3).toLowerCase() + rkey.substring(4);
                    if (rkey1.equals(GenericMethods.rkeyfn)) {
                    }
                }

                if ((rkey.toLowerCase().equals(GenericMethods.rkeyfn) || rkey1.contains(GenericMethods.rkeyfn) || oname.toLowerCase().contains(GenericMethods.rkeyfn) || tname.toLowerCase().contains(GenericMethods.rkeyfn) || sdate.contains(GenericMethods.rkeyfn)) && (date.after(date1))) {
                    getAllListMap.put("Rtoken", rtoken);
                    getAllListMap.put("rkey", rkey);
                    getAllListMap.put("s_grn", sgrn);
                    getAllListMap.put("r_grn", rgrn);
                    getAllListMap.put("name", name);
                    getAllListMap.put("mail", mail);
                    getAllListMap.put("contact", contact);
                    getAllListMap.put("padd", padd);
                    getAllListMap.put("oname", oname);
                    getAllListMap.put("ocontact", ocontact);
                    getAllListMap.put("omail", omail);
                    getAllListMap.put("oadd", oadd);
                    getAllListMap.put("tname", tname);
                    getAllListMap.put("tcontact", tcontact);
                    getAllListMap.put("tmail", tmail);
                    getAllListMap.put("tadd", tadd);
                    getAllListMap.put("fmail", fmail);
                    getAllListMap.put("tstatus", tstatus);
                    getAllListMap.put("biocomp", biocomp);
                    getAllListMap.put("biocom1", biocom1);
                    getAllListMap.put("appdate", appdate);
                    getAllListMap.put("appdate1", appdate1);
                    getAllListMap.put("regfromcomp", regfromcomp);
                    getAllListMap.put("witness", witness);
                    getAllListMap.put("latitude", latitude);
                    getAllListMap.put("longitude", longitude);
                    getAllListMap.put("shipadd", shipadd);
                    getAllListMap.put("shipdiffadd", shipdiffadd);
                    getAllListMap.put("sdate", sdate);
                    getAllListMap.put("stime1", stime1);
                    getAllListMap.put("stime2", stime2);
                    getAllListMap.put("docid", docid);
                    getAllListMap.put("appadress", appadress);
                    getAllListMap.put("appid", appid);
                    getAllListMap.put("appfor", appfor);
                    getAllListMap.put("exid", exid);
                    getAllListMap.put("syncstatus", syncstatus);
                    getAllListMap.put("distance", distance);
                    getAllListMap.put("amount", amount);
                    getAllListMap.put("trasporttype", trasporttype);
                    getAllListMap.put("apointmentfor", apointmentfor);
                    getAllListMap.put("acvrreportstatus", acvrreportstatus);
                    getAllListMap.put("apptype", apptype);
                    getAllListMap.put("post_status", post_status);
                    getAllListMap.put("att_status", bio_status);
                    getAllListMap.put("notify", notify);
                    if (landmark.equals("null")) {

                        getAllListMap.put("landmark", "No details");
                    } else {
                        getAllListMap.put("landmark", landmark);
                    }
                    String[] contact_array = contactperson.split(",");
                    if (contactperson.equals("null")) {
                        getAllListMap.put("contact_person", "No details");
                        getAllListMap.put("contact_name", "No details");
                        getAllListMap.put("contact_email", "No details");
                        getAllListMap.put("contact_no", "No details");
                    } else if (contact_array.length > 1) {
                        getAllListMap.put("contact_person", "Details");
                        getAllListMap.put("contact_name", contact_array[0]);
                        getAllListMap.put("contact_email", contact_array[1]);
                        getAllListMap.put("contact_no", contact_array[2]);
                    }
                    getAllList.add(getAllListMap);
                }


            } while (c.moveToNext());

        }
        c.close();
        db.close();
        return getAllList;
    }


    public ArrayList<HashMap<String, String>> getAllTodayList(DBOperation db) {

        ArrayList<HashMap<String, String>> getAllList = new ArrayList<>();
        SQLiteDatabase sqlite = db.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + DBManager.TableInfo.TABLE_NAME_APPOINTMENT + " t1, " + DBManager.TableInfo.TABLE_NAME_DOCUMENT + " t2 WHERE "
                + "t1." + DBManager.TableInfo.DocumentId + " = " + "t2." + DBManager.TableInfo.DocumentId + " AND t1."
                + DBManager.TableInfo.Executioner_id + "='" + Login.umailid + "'" + " ORDER BY " + "t1." + DBManager.TableInfo.StartTime1;

        Cursor c = sqlite.rawQuery(selectQuery, null);
        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        String curdate1 = df2.format(c1.getTime());
        Date date, date1;
        if (c.moveToFirst()) {

            c.moveToFirst();
            do {
                HashMap<String, String> getAllListMap = new HashMap<>();
                String rtoken = c.getString(c.getColumnIndex("Rtoken"));
                String rkey = c.getString(c.getColumnIndex("Rkey"));
                String name = c.getString(c.getColumnIndex("Uname"));
                String mail = c.getString(c.getColumnIndex("Umail"));
                String contact = c.getString(c.getColumnIndex("Ucontact"));
                String appstatus=c.getString(c.getColumnIndex("app_status"));
                System.out.println("appstats"+appstatus);
                String sgrn = c.getString(c.getColumnIndex("sgrn"));
                String rgrn = c.getString(c.getColumnIndex("rgrn"));
                String padd = c.getString(c.getColumnIndex("Paddress"));
                String oname = c.getString(c.getColumnIndex("Oname"));
                String ocontact = c.getString(c.getColumnIndex("Ocontact"));
                String omail = c.getString(c.getColumnIndex("Omail"));
                String oadd = c.getString(c.getColumnIndex("Oaddress"));
                String tname = c.getString(c.getColumnIndex("Tname"));
                String tcontact = c.getString(c.getColumnIndex("Tcontact"));
                String tmail = c.getString(c.getColumnIndex("Tmail"));
                String tadd = c.getString(c.getColumnIndex("Taddress"));
                String fmail = c.getString(c.getColumnIndex("uemail"));
                String tstatus = c.getString(c.getColumnIndex("status"));
                String syncstatus = c.getString(c.getColumnIndex(DBManager.TableInfo.SYNCSTATUS));
                String distance = c.getString(c.getColumnIndex(DBManager.TableInfo.DISTANCE));
                String amount = c.getString(c.getColumnIndex(DBManager.TableInfo.AMOUNT));
                String trasporttype = c.getString(c.getColumnIndex(DBManager.TableInfo.TRANSPORTTYPE));
                String apointmentfor = c.getString(c.getColumnIndex(DBManager.TableInfo.AppFor));
                String acvrreportstatus = c.getString(c.getColumnIndex(DBManager.TableInfo.SYNCSTATUSREPORT));
                String appdate = c.getString(c.getColumnIndex("Appdate"));
                String biocomp = c.getString(c.getColumnIndex("Biocomp"));
                String appdate1 = c.getString(c.getColumnIndex("Appdate1"));
                String biocom1 = c.getString(c.getColumnIndex("Biocomp1"));
                String regfromcomp = c.getString(c.getColumnIndex("reg_from_app"));
                String witness = c.getString(c.getColumnIndex("witness"));
                String shipadd = c.getString(c.getColumnIndex("ship_add"));
                String shipdiffadd = c.getString(c.getColumnIndex("ship_diff_add"));
                String sdate = c.getString(c.getColumnIndex("Sdate"));
                String stime1 = c.getString(c.getColumnIndex("Stime1"));
                String stime2 = c.getString(c.getColumnIndex("Stime2"));
                String docid = c.getString(c.getColumnIndex("Did"));
                String appadress = c.getString(c.getColumnIndex("Aaddress"));
                String appid = c.getString(c.getColumnIndex("Aid"));
                String exid = c.getString(c.getColumnIndex("Exid"));
                String appfor = c.getString(c.getColumnIndex("appfor"));
                String apptype = c.getString(c.getColumnIndex("app_type"));
                String landmark = c.getString(c.getColumnIndex("landmark"));
                String contactperson = c.getString(c.getColumnIndex(DBManager.TableInfo.contactperson));
                String post_status = c.getString(c.getColumnIndex("post_status"));
                String bio_status = c.getString(c.getColumnIndex(DBManager.TableInfo.att_status));
                String latitude = c.getString(c.getColumnIndex("latitude1"));
                String longitude = c.getString(c.getColumnIndex("longitude1"));
                String notify = c.getString(c.getColumnIndex(DBManager.TableInfo.notification));

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                try {
                    date = dateFormat.parse(sdate);
                    date1 = dateFormat.parse(curdate1);

                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


                if ((sdate.equals(curdate1)) && ((appstatus.equals("2")) || (appstatus.equals("3")) || (appstatus.equals("6")) || (appstatus.equals("7")))) {
                    getAllListMap.put("Rtoken", rtoken);
                    getAllListMap.put("rkey", rkey);
                    getAllListMap.put("appstatus",appstatus);
                    getAllListMap.put("s_grn", sgrn);
                    getAllListMap.put("r_grn", rgrn);
                    getAllListMap.put("name", name);
                    getAllListMap.put("mail", mail);
                    getAllListMap.put("contact", contact);
                    getAllListMap.put("padd", padd);
                    getAllListMap.put("oname", oname);
                    getAllListMap.put("ocontact", ocontact);
                    getAllListMap.put("omail", omail);
                    getAllListMap.put("oadd", oadd);
                    getAllListMap.put("tname", tname);
                    getAllListMap.put("tcontact", tcontact);
                    getAllListMap.put("tmail", tmail);
                    getAllListMap.put("tadd", tadd);
                    getAllListMap.put("fmail", fmail);
                    getAllListMap.put("tstatus", tstatus);
                    getAllListMap.put("biocomp", biocomp);
                    getAllListMap.put("biocom1", biocom1);
                    getAllListMap.put("appdate", appdate);
                    getAllListMap.put("appdate1", appdate1);
                    getAllListMap.put("regfromcomp", regfromcomp);
                    getAllListMap.put("witness", witness);
                    getAllListMap.put("shipadd", shipadd);
                    getAllListMap.put("shipdiffadd", shipdiffadd);
                    getAllListMap.put("sdate", sdate);
                    getAllListMap.put("stime1", stime1);
                    getAllListMap.put("stime2", stime2);
                    getAllListMap.put("docid", docid);
                    getAllListMap.put("appadress", appadress);
                    getAllListMap.put("latitude", latitude);
                    getAllListMap.put("longitude", longitude);
                    getAllListMap.put("appid", appid);
                    getAllListMap.put("appfor", appfor);
                    getAllListMap.put("exid", exid);
                    getAllListMap.put("syncstatus", syncstatus);
                    getAllListMap.put("distance", distance);
                    getAllListMap.put("amount", amount);
                    getAllListMap.put("trasporttype", trasporttype);
                    getAllListMap.put("apointmentfor", apointmentfor);
                    getAllListMap.put("acvrreportstatus", acvrreportstatus);
                    getAllListMap.put("apptype", apptype);
                    getAllListMap.put("post_status", post_status);
                    getAllListMap.put("att_status", bio_status);
                    getAllListMap.put("notify", notify);
                    if (landmark.equals("null")) {

                        getAllListMap.put("landmark", "No details");
                    } else {
                        getAllListMap.put("landmark", landmark);
                    }
                    String[] contact_array = contactperson.split(",");
                    if (contactperson.equals("null")) {
                        getAllListMap.put("contact_person", "No details");
                        getAllListMap.put("contact_name", "No details");
                        getAllListMap.put("contact_email", "No details");
                        getAllListMap.put("contact_no", "No details");
                    } else if (contact_array.length > 1) {
                        getAllListMap.put("contact_person", "Details");

                        getAllListMap.put("contact_name", contact_array[0]);
                        getAllListMap.put("contact_email", contact_array[1]);
                        getAllListMap.put("contact_no", contact_array[2]);
                    }
                    getAllList.add(getAllListMap);

                }


            } while (c.moveToNext());

        }
        c.close();
        db.close();

        return getAllList;
    }

    public ArrayList<HashMap<String, String>> getAllTodayList1(DBOperation db) {
        String rkey1 = "";

        ArrayList<HashMap<String, String>> getAllList = new ArrayList<>();
        SQLiteDatabase sqlite = db.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + DBManager.TableInfo.TABLE_NAME_APPOINTMENT + " t1, " + DBManager.TableInfo.TABLE_NAME_DOCUMENT + " t2 WHERE "
                + "t1." + DBManager.TableInfo.DocumentId + " = " + "t2." + DBManager.TableInfo.DocumentId + " AND t1."
                + DBManager.TableInfo.Executioner_id + "='" + Login.umailid + "'" + " ORDER BY " + "t1." + DBManager.TableInfo.StartTime1;

        Cursor c = sqlite.rawQuery(selectQuery, null);
        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        String curdate1 = df2.format(c1.getTime());
        Date date, date1;
        if (c.moveToFirst()) {

            c.moveToFirst();
            do {
                HashMap<String, String> getAllListMap = new HashMap<>();
                String rtoken = c.getString(c.getColumnIndex("Rtoken"));
                String rkey = c.getString(c.getColumnIndex("Rkey"));
                String sgrn = c.getString(c.getColumnIndex("sgrn"));
                String rgrn = c.getString(c.getColumnIndex("rgrn"));
                String name = c.getString(c.getColumnIndex("Uname"));
                String mail = c.getString(c.getColumnIndex("Umail"));
                String contact = c.getString(c.getColumnIndex("Ucontact"));
                String padd = c.getString(c.getColumnIndex("Paddress"));
                String oname = c.getString(c.getColumnIndex("Oname"));
                String ocontact = c.getString(c.getColumnIndex("Ocontact"));
                String omail = c.getString(c.getColumnIndex("Omail"));
                String oadd = c.getString(c.getColumnIndex("Oaddress"));
                String tname = c.getString(c.getColumnIndex("Tname"));
                String tcontact = c.getString(c.getColumnIndex("Tcontact"));
                String tmail = c.getString(c.getColumnIndex("Tmail"));
                String tadd = c.getString(c.getColumnIndex("Taddress"));
                String fmail = c.getString(c.getColumnIndex("uemail"));
                String tstatus = c.getString(c.getColumnIndex("status"));
                String syncstatus = c.getString(c.getColumnIndex(DBManager.TableInfo.SYNCSTATUS));
                String distance = c.getString(c.getColumnIndex(DBManager.TableInfo.DISTANCE));
                String amount = c.getString(c.getColumnIndex(DBManager.TableInfo.AMOUNT));
                String trasporttype = c.getString(c.getColumnIndex(DBManager.TableInfo.TRANSPORTTYPE));
                String apointmentfor = c.getString(c.getColumnIndex(DBManager.TableInfo.AppFor));
                String acvrreportstatus = c.getString(c.getColumnIndex(DBManager.TableInfo.SYNCSTATUSREPORT));
                String latitude = c.getString(c.getColumnIndex("latitude1"));
                String longitude = c.getString(c.getColumnIndex("longitude1"));
                String appdate = c.getString(c.getColumnIndex("Appdate"));
                String biocomp = c.getString(c.getColumnIndex("Biocomp"));
                String appdate1 = c.getString(c.getColumnIndex("Appdate1"));
                String biocom1 = c.getString(c.getColumnIndex("Biocomp1"));
                String regfromcomp = c.getString(c.getColumnIndex("reg_from_app"));
                String witness = c.getString(c.getColumnIndex("witness"));
                String shipadd = c.getString(c.getColumnIndex("ship_add"));
                String shipdiffadd = c.getString(c.getColumnIndex("ship_diff_add"));
                String sdate = c.getString(c.getColumnIndex("Sdate"));
                String stime1 = c.getString(c.getColumnIndex("Stime1"));
                String stime2 = c.getString(c.getColumnIndex("Stime2"));
                String docid = c.getString(c.getColumnIndex("Did"));
                String appadress = c.getString(c.getColumnIndex("Aaddress"));
                String appid = c.getString(c.getColumnIndex("Aid"));
                String exid = c.getString(c.getColumnIndex("Exid"));
                String appfor = c.getString(c.getColumnIndex("appfor"));
                String apptype = c.getString(c.getColumnIndex("app_type"));
                String landmark = c.getString(c.getColumnIndex("landmark"));
                String contactperson = c.getString(c.getColumnIndex(DBManager.TableInfo.contactperson));
                String post_status = c.getString(c.getColumnIndex("post_status"));
                String bio_status = c.getString(c.getColumnIndex(DBManager.TableInfo.att_status));
                String notify = c.getString(c.getColumnIndex(DBManager.TableInfo.notification));

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    date = dateFormat.parse(sdate);
                    date1 = dateFormat.parse(curdate1);

                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (rkey.substring(0, 1).toLowerCase().equals("a")) {


                    rkey1 = "a" + rkey.substring(2);
                    if (rkey1.equals(GenericMethods.rkeyfn)) {

                    }
                } else if (rkey.substring(0, 1).toLowerCase().equals("n")) {

                    rkey1 = rkey.substring(0, 3).toLowerCase() + rkey.substring(4);
                    if (rkey1.equals(GenericMethods.rkeyfn)) {
                    }
                }
                if ((rkey.toLowerCase().equals(GenericMethods.rkeyfn) || rkey1.contains(GenericMethods.rkeyfn) || oname.toLowerCase().contains(GenericMethods.rkeyfn) || tname.toLowerCase().contains(GenericMethods.rkeyfn) || sdate.contains(GenericMethods.rkeyfn)) && (sdate.equals(curdate1) && (!biocomp.equals("1") || !biocom1.equals("1") || !regfromcomp.equals("1")
                        || !witness.equals("1")))) {
                    getAllListMap.put("Rtoken", rtoken);
                    getAllListMap.put("rkey", rkey);
                    getAllListMap.put("s_grn", sgrn);
                    getAllListMap.put("r_grn", rgrn);
                    getAllListMap.put("name", name);
                    getAllListMap.put("mail", mail);
                    getAllListMap.put("contact", contact);
                    getAllListMap.put("padd", padd);
                    getAllListMap.put("oname", oname);
                    getAllListMap.put("ocontact", ocontact);
                    getAllListMap.put("omail", omail);
                    getAllListMap.put("oadd", oadd);
                    getAllListMap.put("tname", tname);
                    getAllListMap.put("tcontact", tcontact);
                    getAllListMap.put("tmail", tmail);
                    getAllListMap.put("tadd", tadd);
                    getAllListMap.put("fmail", fmail);
                    getAllListMap.put("tstatus", tstatus);
                    getAllListMap.put("biocomp", biocomp);
                    getAllListMap.put("biocom1", biocom1);
                    getAllListMap.put("appdate", appdate);
                    getAllListMap.put("appdate1", appdate1);
                    getAllListMap.put("regfromcomp", regfromcomp);
                    getAllListMap.put("witness", witness);
                    getAllListMap.put("shipadd", shipadd);
                    getAllListMap.put("shipdiffadd", shipdiffadd);
                    getAllListMap.put("sdate", sdate);
                    getAllListMap.put("stime1", stime1);
                    getAllListMap.put("stime2", stime2);
                    getAllListMap.put("docid", docid);
                    getAllListMap.put("appadress", appadress);
                    getAllListMap.put("appid", appid);
                    getAllListMap.put("appfor", appfor);
                    getAllListMap.put("exid", exid);
                    getAllListMap.put("syncstatus", syncstatus);
                    getAllListMap.put("distance", distance);
                    getAllListMap.put("amount", amount);
                    getAllListMap.put("latitude", latitude);
                    getAllListMap.put("longitude", longitude);
                    getAllListMap.put("trasporttype", trasporttype);
                    getAllListMap.put("apointmentfor", apointmentfor);
                    getAllListMap.put("acvrreportstatus", acvrreportstatus);
                    getAllListMap.put("apptype", apptype);
                    getAllListMap.put("post_status", post_status);
                    getAllListMap.put("att_status", bio_status);
                    getAllListMap.put("notify", notify);

                    if (landmark.equals("null")) {

                        getAllListMap.put("landmark", "No details");
                    } else {
                        getAllListMap.put("landmark", landmark);
                    }
                    String[] contact_array = contactperson.split(",");
                    if (contactperson.equals("null")) {
                        getAllListMap.put("contact_person", "No details");
                        getAllListMap.put("contact_name", "No details");
                        getAllListMap.put("contact_email", "No details");
                        getAllListMap.put("contact_no", "No details");
                    } else if (contact_array.length > 1) {
                        getAllListMap.put("contact_person", "Details");
                        getAllListMap.put("contact_name", contact_array[0]);
                        getAllListMap.put("contact_email", contact_array[1]);
                        getAllListMap.put("contact_no", contact_array[2]);
                    }
                    getAllList.add(getAllListMap);
                }


            } while (c.moveToNext());

        }
        c.close();
        db.close();
        return getAllList;
    }

    public List<String> getReportkey() {
        List<String> labels = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + DBManager.TableInfo.TABLE_NAME_DOCUMENT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(cursor.getColumnIndex("Rkey")));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return labels;
    }

    public List<String> getReportkey1() {
        List<String> labels = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + DBManager.TableInfo.TABLE_SALES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(cursor.getColumnIndex("env")));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return labels;
    }

    public ArrayList<HashMap<String, String>> getOwnerType(DBOperation db, String owner) {
        SQLiteDatabase sqlite = db.getWritableDatabase();
        ArrayList<HashMap<String, String>> listOfReference = new ArrayList<>();
        try {
            String query = "SELECT * from " + DBManager.TableInfo.TABLE_NAME5 + " where " + DBManager.TableInfo.Email + "='" + owner + "'";
            Cursor c = sqlite.rawQuery(query, null);

            while (c.moveToNext()) {
                HashMap<String, String> selectiondetails = new HashMap<>();
                selectiondetails.put(DBManager.TableInfo.User_name, c.getString(c.getColumnIndex(DBManager.TableInfo.User_name)));
                selectiondetails.put(DBManager.TableInfo.Role, c.getString(c.getColumnIndex(DBManager.TableInfo.Role)));
                selectiondetails.put(DBManager.TableInfo.User_id, c.getString(c.getColumnIndex(DBManager.TableInfo.User_id)));
                listOfReference.add(selectiondetails);
            }
            c.close();

        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }
        db.close();
        return listOfReference;
    }

    public ArrayList<HashMap<String, String>> getExecutionerType(DBOperation db, String username) {
        SQLiteDatabase sqlite = db.getWritableDatabase();
        ArrayList<HashMap<String, String>> listOfReference = new ArrayList<>();
        try {
            String query = "SELECT * from " + DBManager.TableInfo.TABLE_NAME5 + " where " + DBManager.TableInfo.Email + "='" + username + "'";
            Cursor c = sqlite.rawQuery(query, null);

            while (c.moveToNext()) {

                HashMap<String, String> selectiondetails = new HashMap<>();
                selectiondetails.put(DBManager.TableInfo.User_name, c.getString(c.getColumnIndex(DBManager.TableInfo.User_name)));
                selectiondetails.put(DBManager.TableInfo.Role, c.getString(c.getColumnIndex(DBManager.TableInfo.Role)));
                selectiondetails.put(DBManager.TableInfo.User_id, c.getString(c.getColumnIndex(DBManager.TableInfo.User_id)));
                listOfReference.add(selectiondetails);
            }
            c.close();

        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }
        db.close();
        return listOfReference;
    }


    public ArrayList<HashMap<String, String>> getDocumentUser(DBOperation db, String strDocId) {
        SQLiteDatabase sqlite = db.getWritableDatabase();
        ArrayList<HashMap<String, String>> listOfReference = new ArrayList<>();
        try {
            String query = "SELECT * from " + DBManager.TableInfo.TABLE_NAME_DOCUMENT + " where " + DBManager.TableInfo.ReportKey + "='" + strDocId + "'";
            Cursor c = sqlite.rawQuery(query, null);

            while (c.moveToNext()) {

                HashMap<String, String> selectiondetails = new HashMap<>();
                selectiondetails.put(DBManager.TableInfo.OwnerEmail, c.getString(c.getColumnIndex(DBManager.TableInfo.OwnerEmail)));
                listOfReference.add(selectiondetails);
            }
            c.close();

        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }

        db.close();
        return listOfReference;
    }

    public ArrayList<HashMap<String, String>> getAllsalesTodayList(DBOperation db) {
        Cursor c = null;


        ArrayList<HashMap<String, String>> getAllsalesList = new ArrayList<>();
        SQLiteDatabase sqlite = db.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + DBManager.TableInfo.TABLE_SALES_APPOINTMENT + " t1, " + DBManager.TableInfo.TABLE_SALES + " t2 WHERE "
                + "t1." + DBManager.TableInfo.db_sales_doc_id + " = " + "t2." + DBManager.TableInfo.db_sales_doc_id + " AND t1."
                + DBManager.TableInfo.KEY_LOGIN_USER + "='" + Login.umailid + "'" + " ORDER BY " + "t1." + DBManager.TableInfo.db_sales_starttime;


        c = sqlite.rawQuery(selectQuery, null);
        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        String curdate1 = df2.format(c1.getTime());

        Date date = null, date1 = null;


        if (c != null) {


            if (c.moveToFirst()) {
                String count = c.getString(0);

                c.moveToFirst();
                do {
                    HashMap<String, String> getAllListMap = new HashMap<>();
                    String contact_person = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_contactperson));
                    String starttime = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_starttime));
                    String endtime = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_endtime));
                    String startdate = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_startdate));
                    String executionerid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_executioner_id));
                    String app_for = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_app_for));
                    String documentid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_doc_id));
                    String created_at = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_created_at));
                    String appid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_appointment_id));
                    String app_ref = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_app_ref));
                    String app_city = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_app_city));
                    String u_flag = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_u_flag));
                    String create_dt = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_create_dt));
                    String approved_by = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_approved_by));
                    String created_by = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_createdby));
                    String latitude = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_latitude));
                    String longititude = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_longititude));
                    String logical_del = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_logical_del));
                    String post_status = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_poststatus));
                    String apptype = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_app_type));
                    String env = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_env));
                    String address = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_address));
                    String landmark = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_landmark));
                    String id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_id));
                    String syncstatus = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_syncstatus));
                    String distance = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_distance));
                    String amount = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_amount));
                    String transtype = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_transtype));
                    String acvr_id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_acvr_id));
                    String acvrsyncstatus = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_acvrsyncstatus));
                    String notify = c.getString(c.getColumnIndex(DBManager.TableInfo.notification));
                    String execemail = c.getString(c.getColumnIndex(DBManager.TableInfo.KEY_LOGIN_USER));

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                    try {
                        date = dateFormat.parse(startdate);
                        date1 = dateFormat.parse(curdate1);

                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                    if (date.equals(date1) && ((!post_status.equals("1") || post_status.equals("null")))) {

                        getAllListMap.put("contact_person", contact_person);
                        getAllListMap.put("startdate", startdate);
                        getAllListMap.put("starttime", starttime);
                        getAllListMap.put("endtime", endtime);
                        getAllListMap.put("executionerid", executionerid);
                        getAllListMap.put("app_for", app_for);
                        getAllListMap.put("document_id", documentid);
                        getAllListMap.put("created_at", created_at);
                        getAllListMap.put("appid", appid);
                        getAllListMap.put("app_ref", app_ref);
                        getAllListMap.put("app_city", app_city);
                        getAllListMap.put("u_flag", u_flag);
                        getAllListMap.put("create_dt", create_dt);
                        getAllListMap.put("approvedby", approved_by);
                        getAllListMap.put("createdby", created_by);
                        getAllListMap.put("lati", latitude);
                        getAllListMap.put("longi", longititude);
                        getAllListMap.put("logical_del", logical_del);
                        getAllListMap.put("poststatus", post_status);
                        getAllListMap.put("apptype", apptype);
                        getAllListMap.put("env", env);
                        getAllListMap.put("id", id);
                        getAllListMap.put("address", address);
                        getAllListMap.put("landmark", landmark);
                        getAllListMap.put("syncstatus", syncstatus);
                        getAllListMap.put("distance", distance);
                        getAllListMap.put("amount", amount);
                        getAllListMap.put("acvrsync", acvrsyncstatus);
                        getAllListMap.put("trans_type", transtype);
                        getAllListMap.put("acvr_id", acvr_id);
                        getAllListMap.put("notify", notify);
                        getAllListMap.put("exec_email", execemail);

                        if (landmark.equals("null")) {

                            getAllListMap.put("landmark", "No details");
                        } else {
                            getAllListMap.put("landmark", landmark);
                        }
                        String[] contact_array = contact_person.split(",");
                        if (contact_person.equals("null")) {
                            getAllListMap.put("contact_person", "No details");
                            getAllListMap.put("contact_name", "No details");
                            getAllListMap.put("contact_email", "No details");
                            getAllListMap.put("contact_no", "No details");
                        } else if (contact_array.length > 1) {

                            getAllListMap.put("contact_person", "Details");
                            getAllListMap.put("contact_name", contact_array[0]);
                            getAllListMap.put("contact_email", contact_array[1]);
                            getAllListMap.put("contact_no", contact_array[2]);
                        }
                        getAllsalesList.add(getAllListMap);

                    }


                } while (c.moveToNext());
            }
        }
        db.close();
        return getAllsalesList;
    }

    public ArrayList<HashMap<String, String>> getAllsalesTodayList1(DBOperation db) {
        Cursor c = null;


        ArrayList<HashMap<String, String>> getAllsalesList = new ArrayList<>();
        SQLiteDatabase sqlite = db.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + DBManager.TableInfo.TABLE_SALES_APPOINTMENT + " t1, " + DBManager.TableInfo.TABLE_SALES + " t2 WHERE "
                + "t1." + DBManager.TableInfo.db_sales_doc_id + " = " + "t2." + DBManager.TableInfo.db_sales_doc_id + " AND t1."
                + DBManager.TableInfo.KEY_LOGIN_USER + "='" + Login.umailid + "'" + " ORDER BY " + "t1." + DBManager.TableInfo.db_sales_starttime;

        c = sqlite.rawQuery(selectQuery, null);
        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        String curdate1 = df2.format(c1.getTime());
        Date date = null, date1 = null;
        String env1 = "";
        if (c != null) {
            if (c.moveToFirst()) {


                c.moveToFirst();
                do {
                    HashMap<String, String> getAllListMap = new HashMap<>();
                    String contact_person = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_contactperson));
                    String starttime = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_starttime));
                    String endtime = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_endtime));
                    String executionerid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_executioner_id));
                    String app_for = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_app_for));
                    String documentid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_doc_id));
                    String created_at = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_created_at));
                    String appid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_appointment_id));
                    String app_ref = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_app_ref));
                    String app_city = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_app_city));
                    String u_flag = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_u_flag));
                    String create_dt = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_create_dt));
                    String approved_by = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_approved_by));
                    String created_by = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_createdby));
                    String latitude = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_latitude));
                    String longititude = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_longititude));
                    String logical_del = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_logical_del));
                    String post_status = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_poststatus));
                    String apptype = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_app_type));
                    String env = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_env));
                    String address = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_address));
                    String landmark = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_landmark));
                    String id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_id));
                    String syncstatus = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_syncstatus));
                    String acvrsyncstatus = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_acvrsyncstatus));
                    String distance = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_distance));
                    String amount = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_amount));
                    String transtype = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_transtype));
                    String acvr_id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_acvr_id));
                    String notify = c.getString(c.getColumnIndex(DBManager.TableInfo.notification));
                    String execemail = c.getString(c.getColumnIndex(DBManager.TableInfo.KEY_LOGIN_USER));

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                    try {
                        date = dateFormat.parse(created_at);
                        date1 = dateFormat.parse(curdate1);

                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    if (env.substring(0, 1).toLowerCase().equals("o")) {


                        env1 = "o" + env.substring(2);
                        if (env1.equals(GenericMethods.rkeyfn)) {
                        }
                    }
                    if ((env.toLowerCase().equals(GenericMethods.rkeyfn) || env1.contains(GenericMethods.rkeyfn) || contact_person.toLowerCase().contains(GenericMethods.rkeyfn) || created_at.contains(GenericMethods.rkeyfn)) && (date.equals(date1) && ((!post_status.equals("1") || post_status.equals("null"))))) {
                        getAllListMap.put("contact_person", contact_person);
                        getAllListMap.put("starttime", starttime);
                        getAllListMap.put("endtime", endtime);
                        getAllListMap.put("executionerid", executionerid);
                        getAllListMap.put("app_for", app_for);
                        getAllListMap.put("document_id", documentid);
                        getAllListMap.put("created_at", created_at);
                        getAllListMap.put("appid", appid);
                        getAllListMap.put("app_ref", app_ref);
                        getAllListMap.put("app_city", app_city);
                        getAllListMap.put("u_flag", u_flag);
                        getAllListMap.put("create_dt", create_dt);
                        getAllListMap.put("approvedby", approved_by);
                        getAllListMap.put("createdby", created_by);
                        getAllListMap.put("lati", latitude);
                        getAllListMap.put("longi", longititude);
                        getAllListMap.put("logical_del", logical_del);
                        getAllListMap.put("poststatus", post_status);
                        getAllListMap.put("apptype", apptype);
                        getAllListMap.put("env", env);
                        getAllListMap.put("id", id);
                        getAllListMap.put("address", address);
                        getAllListMap.put("landmark", landmark);
                        getAllListMap.put("syncstatus", syncstatus);
                        getAllListMap.put("distance", distance);
                        getAllListMap.put("amount", amount);
                        getAllListMap.put("trans_type", transtype);
                        getAllListMap.put("acvr_id", acvr_id);
                        getAllListMap.put("acvrsync", acvrsyncstatus);
                        getAllListMap.put("notify", notify);
                        getAllListMap.put("exec_email", execemail);
                        if (landmark.equals("null")) {

                            getAllListMap.put("landmark", "No details");
                        } else {
                            getAllListMap.put("landmark", landmark);
                        }
                        String[] contact_array = contact_person.split(",");
                        if (contact_person.equals("null")) {
                            getAllListMap.put("contact_person", "No details");
                            getAllListMap.put("contact_name", "No details");
                            getAllListMap.put("contact_email", "No details");
                            getAllListMap.put("contact_no", "No details");
                        } else if (contact_array.length > 1) {

                            getAllListMap.put("contact_person", "Details");
                            getAllListMap.put("contact_name", contact_array[0]);
                            getAllListMap.put("contact_email", contact_array[1]);
                            getAllListMap.put("contact_no", contact_array[2]);
                        }
                        getAllsalesList.add(getAllListMap);

                    }


                } while (c.moveToNext());

            }
        }
        c.close();
        db.close();

        return getAllsalesList;
    }


    public ArrayList<HashMap<String, String>> getAllsalesOlderist(DBOperation db) {
        Cursor c = null;


        ArrayList<HashMap<String, String>> getAllsalesList = new ArrayList<>();
        SQLiteDatabase sqlite = db.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + DBManager.TableInfo.TABLE_SALES_APPOINTMENT + " t1, " + DBManager.TableInfo.TABLE_SALES + " t2 WHERE "
                + "t1." + DBManager.TableInfo.db_sales_doc_id + " = " + "t2." + DBManager.TableInfo.db_sales_doc_id + " AND t1."
                + DBManager.TableInfo.KEY_LOGIN_USER + "='" + Login.umailid + "'" + " ORDER BY " + "t1." + DBManager.TableInfo.db_sales_starttime;


        c = sqlite.rawQuery(selectQuery, null);
        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        String curdate1 = df2.format(c1.getTime());
        Date date = null, date1 = null;

        if (c != null) {

            if (c.moveToFirst()) {
                String count = c.getString(0);

                c.moveToFirst();
                do {

                    HashMap<String, String> getAllListMap = new HashMap<>();
                    String contact_person = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_contactperson));
                    String startdate = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_startdate));
                    String starttime = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_starttime));
                    String endtime = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_endtime));
                    String executionerid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_executioner_id));
                    String app_for = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_app_for));
                    String documentid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_doc_id));
                    String created_at = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_created_at));
                    String appid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_appointment_id));
                    String app_ref = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_app_ref));
                    String app_city = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_app_city));
                    String u_flag = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_u_flag));
                    String create_dt = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_create_dt));
                    String approved_by = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_approved_by));
                    String created_by = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_createdby));
                    String latitude = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_latitude));
                    String longititude = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_longititude));
                    String logical_del = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_logical_del));
                    String post_status = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_poststatus));
                    String apptype = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_app_type));
                    String env = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_env));
                    String address = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_address));
                    String landmark = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_landmark));
                    String id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_id));
                    String syncstatus = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_syncstatus));
                    String acvrsyncstatus = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_acvrsyncstatus));
                    String notify = c.getString(c.getColumnIndex(DBManager.TableInfo.notification));
                    String execemail = c.getString(c.getColumnIndex(DBManager.TableInfo.KEY_LOGIN_USER));

                    String distance = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_distance));
                    String amount = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_amount));
                    String transtype = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_transtype));
                    String acvr_id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_acvr_id));

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                    try {
                        date = dateFormat.parse(startdate);
                        date1 = dateFormat.parse(curdate1);

                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                    if (date.before(date1) && ((!post_status.equals("1") || post_status.equals("null")))) {

                        getAllListMap.put("startdate", startdate);
                        getAllListMap.put("contact_person", contact_person);
                        getAllListMap.put("starttime", starttime);
                        getAllListMap.put("endtime", endtime);
                        getAllListMap.put("executionerid", executionerid);
                        getAllListMap.put("app_for", app_for);
                        getAllListMap.put("document_id", documentid);
                        getAllListMap.put("created_at", created_at);
                        getAllListMap.put("appid", appid);
                        getAllListMap.put("app_ref", app_ref);
                        getAllListMap.put("app_city", app_city);
                        getAllListMap.put("u_flag", u_flag);
                        getAllListMap.put("create_dt", create_dt);
                        getAllListMap.put("approvedby", approved_by);
                        getAllListMap.put("createdby", created_by);
                        getAllListMap.put("lati", latitude);
                        getAllListMap.put("longi", longititude);
                        getAllListMap.put("logical_del", logical_del);
                        getAllListMap.put("poststatus", post_status);
                        getAllListMap.put("apptype", apptype);
                        getAllListMap.put("env", env);
                        getAllListMap.put("id", id);
                        getAllListMap.put("address", address);
                        getAllListMap.put("landmark", landmark);
                        getAllListMap.put("syncstatus", syncstatus);
                        getAllListMap.put("distance", distance);
                        getAllListMap.put("amount", amount);
                        getAllListMap.put("trans_type", transtype);
                        getAllListMap.put("acvr_id", acvr_id);
                        getAllListMap.put("acvrsync", acvrsyncstatus);
                        getAllListMap.put("notify", notify);
                        getAllListMap.put("exec_email", execemail);
                        if (landmark.equals("null")) {

                            getAllListMap.put("landmark", "No details");
                        } else {
                            getAllListMap.put("landmark", landmark);
                        }
                        String[] contact_array = contact_person.split(",");
                        if (contact_person.equals("null")) {
                            getAllListMap.put("contact_person", "No details");
                            getAllListMap.put("contact_name", "No details");
                            getAllListMap.put("contact_email", "No details");
                            getAllListMap.put("contact_no", "No details");
                        } else if (contact_array.length > 1) {

                            getAllListMap.put("contact_person", "Details");
                            getAllListMap.put("contact_name", contact_array[0]);
                            getAllListMap.put("contact_email", contact_array[1]);
                            getAllListMap.put("contact_no", contact_array[2]);
                        }
                        getAllsalesList.add(getAllListMap);


                    }


                } while (c.moveToNext());


            }

        }
        c.close();
        db.close();
        return getAllsalesList;
    }


    public ArrayList<HashMap<String, String>> getAllsalesOlderist1(DBOperation db) {


        Cursor c = null;
        ArrayList<HashMap<String, String>> getAllsalesList = new ArrayList<>();
        SQLiteDatabase sqlite = db.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + DBManager.TableInfo.TABLE_SALES_APPOINTMENT + " t1, " + DBManager.TableInfo.TABLE_SALES + " t2 WHERE "
                + "t1." + DBManager.TableInfo.db_sales_doc_id + " = " + "t2." + DBManager.TableInfo.db_sales_doc_id + " AND t1."
                + DBManager.TableInfo.KEY_LOGIN_USER + "='" + Login.umailid + "'" + " ORDER BY " + "t1." + DBManager.TableInfo.db_sales_starttime;
        c = sqlite.rawQuery(selectQuery, null);
        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        String curdate1 = df2.format(c1.getTime());
        Date date = null, date1 = null;
        String env1 = "";
        if (c != null) {
            if (c.moveToFirst()) {

                c.moveToFirst();
                do {
                    HashMap<String, String> getAllListMap = new HashMap<>();
                    String contact_person = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_contactperson));
                    String starttime = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_starttime));
                    String endtime = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_endtime));
                    String executionerid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_executioner_id));
                    String app_for = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_app_for));
                    String documentid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_doc_id));
                    String created_at = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_created_at));
                    String appid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_appointment_id));
                    String app_ref = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_app_ref));
                    String app_city = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_app_city));
                    String u_flag = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_u_flag));
                    String create_dt = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_create_dt));
                    String approved_by = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_approved_by));
                    String created_by = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_createdby));
                    String latitude = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_latitude));
                    String longititude = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_longititude));
                    String logical_del = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_logical_del));
                    String post_status = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_poststatus));
                    String apptype = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_app_type));
                    String env = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_env));
                    String address = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_address));
                    String landmark = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_landmark));
                    String id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_id));
                    String syncstatus = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_syncstatus));
                    String distance = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_distance));
                    String amount = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_amount));
                    String transtype = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_transtype));
                    String acvr_id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_acvr_id));
                    String acvrsyncstatus = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_acvrsyncstatus));
                    String notify = c.getString(c.getColumnIndex(DBManager.TableInfo.notification));
                    String execemail = c.getString(c.getColumnIndex(DBManager.TableInfo.KEY_LOGIN_USER));

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                    try {
                        date = dateFormat.parse(created_at);
                        date1 = dateFormat.parse(curdate1);

                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    if (env.substring(0, 1).toLowerCase().equals("o")) {


                        env1 = "o" + env.substring(2);
                        if (env1.equals(GenericMethods.rkeyfn)) {
                        }
                    }

                    if ((env.toLowerCase().equals(GenericMethods.rkeyfn) || env1.contains(GenericMethods.rkeyfn) || contact_person.toLowerCase().contains(GenericMethods.rkeyfn) || created_at.contains(GenericMethods.rkeyfn)) && (date.before(date1) && ((!post_status.equals("1") || post_status.equals("null"))))) {
                        getAllListMap.put("contact_person", contact_person);
                        getAllListMap.put("starttime", starttime);
                        getAllListMap.put("endtime", endtime);
                        getAllListMap.put("executionerid", executionerid);
                        getAllListMap.put("app_for", app_for);
                        getAllListMap.put("document_id", documentid);
                        getAllListMap.put("created_at", created_at);
                        getAllListMap.put("appid", appid);
                        getAllListMap.put("app_ref", app_ref);
                        getAllListMap.put("app_city", app_city);
                        getAllListMap.put("u_flag", u_flag);
                        getAllListMap.put("create_dt", create_dt);
                        getAllListMap.put("approvedby", approved_by);
                        getAllListMap.put("createdby", created_by);
                        getAllListMap.put("lati", latitude);
                        getAllListMap.put("longi", longititude);
                        getAllListMap.put("logical_del", logical_del);
                        getAllListMap.put("poststatus", post_status);
                        getAllListMap.put("apptype", apptype);
                        getAllListMap.put("env", env);
                        getAllListMap.put("id", id);
                        getAllListMap.put("address", address);
                        getAllListMap.put("landmark", landmark);
                        getAllListMap.put("syncstatus", syncstatus);
                        getAllListMap.put("distance", distance);
                        getAllListMap.put("amount", amount);
                        getAllListMap.put("trans_type", transtype);
                        getAllListMap.put("acvr_id", acvr_id);
                        getAllListMap.put("acvrsync", acvrsyncstatus);
                        getAllListMap.put("notify", notify);
                        getAllListMap.put("exec_email", execemail);
                        if (landmark.equals("null")) {

                            getAllListMap.put("landmark", "No details");
                        } else {
                            getAllListMap.put("landmark", landmark);
                        }
                        String[] contact_array = contact_person.split(",");
                        if (contact_person.equals("null")) {
                            getAllListMap.put("contact_person", "No details");
                            getAllListMap.put("contact_name", "No details");
                            getAllListMap.put("contact_email", "No details");
                            getAllListMap.put("contact_no", "No details");
                        } else if (contact_array.length > 1) {

                            getAllListMap.put("contact_person", "Details");
                            getAllListMap.put("contact_name", contact_array[0]);
                            getAllListMap.put("contact_email", contact_array[1]);
                            getAllListMap.put("contact_no", contact_array[2]);
                        }
                        getAllsalesList.add(getAllListMap);

                    }


                } while (c.moveToNext());
            }
        }
        db.close();
        return getAllsalesList;
    }

    public ArrayList<HashMap<String, String>> getAllsalesnewist(DBOperation db) {
        ArrayList<HashMap<String, String>> getAllsalesList = new ArrayList<>();
        SQLiteDatabase sqlite = db.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + DBManager.TableInfo.TABLE_SALES_APPOINTMENT + " t1, " + DBManager.TableInfo.TABLE_SALES + " t2 WHERE "
                + "t1." + DBManager.TableInfo.db_sales_doc_id + " = " + "t2." + DBManager.TableInfo.db_sales_doc_id + " AND t1."
                + DBManager.TableInfo.KEY_LOGIN_USER + "='" + Login.umailid + "'" + " ORDER BY " + "t1." + DBManager.TableInfo.db_sales_starttime;
        Cursor c = sqlite.rawQuery(selectQuery, null);
        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        String curdate1 = df2.format(c1.getTime());
        Date date = null, date1 = null;
        if (c != null) {
            if (c.moveToFirst()) {

                c.moveToFirst();
                do {
                    HashMap<String, String> getAllListMap = new HashMap<>();
                    String contact_person = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_contactperson));
                    String startdate = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_startdate));
                    String starttime = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_starttime));
                    String endtime = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_endtime));
                    String executionerid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_executioner_id));
                    String app_for = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_app_for));
                    String documentid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_doc_id));
                    String created_at = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_created_at));
                    String appid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_appointment_id));
                    String app_ref = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_app_ref));
                    String app_city = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_app_city));
                    String u_flag = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_u_flag));
                    String create_dt = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_create_dt));
                    String approved_by = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_approved_by));
                    String created_by = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_createdby));
                    String latitude = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_latitude));
                    String longititude = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_longititude));
                    String logical_del = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_logical_del));
                    String post_status = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_poststatus));
                    String apptype = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_app_type));
                    String env = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_env));
                    String address = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_address));
                    String landmark = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_landmark));
                    String id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_id));
                    String syncstatus = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_syncstatus));
                    String distance = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_distance));
                    String amount = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_amount));
                    String transtype = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_transtype));
                    String acvr_id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_acvr_id));
                    String acvrsyncstatus = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_acvrsyncstatus));
                    String notify = c.getString(c.getColumnIndex(DBManager.TableInfo.notification));
                    String execemail = c.getString(c.getColumnIndex(DBManager.TableInfo.KEY_LOGIN_USER));

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                    try {
                        date = dateFormat.parse(startdate);
                        date1 = dateFormat.parse(curdate1);

                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                    if (date.after(date1)) {

                        getAllListMap.put("contact_person", contact_person);
                        getAllListMap.put("startdate", startdate);
                        getAllListMap.put("starttime", starttime);
                        getAllListMap.put("endtime", endtime);
                        getAllListMap.put("executionerid", executionerid);
                        getAllListMap.put("app_for", app_for);
                        getAllListMap.put("document_id", documentid);
                        getAllListMap.put("created_at", created_at);
                        getAllListMap.put("appid", appid);
                        getAllListMap.put("app_ref", app_ref);
                        getAllListMap.put("app_city", app_city);
                        getAllListMap.put("u_flag", u_flag);
                        getAllListMap.put("create_dt", create_dt);
                        getAllListMap.put("approvedby", approved_by);
                        getAllListMap.put("createdby", created_by);
                        getAllListMap.put("lati", latitude);
                        getAllListMap.put("longi", longititude);
                        getAllListMap.put("logical_del", logical_del);
                        getAllListMap.put("poststatus", post_status);
                        getAllListMap.put("apptype", apptype);
                        getAllListMap.put("env", env);
                        getAllListMap.put("id", id);
                        getAllListMap.put("address", address);
                        getAllListMap.put("landmark", landmark);
                        getAllListMap.put("syncstatus", syncstatus);
                        getAllListMap.put("distance", distance);
                        getAllListMap.put("amount", amount);
                        getAllListMap.put("trans_type", transtype);
                        getAllListMap.put("acvrsync", acvrsyncstatus);
                        getAllListMap.put("acvr_id", acvr_id);
                        getAllListMap.put("notify", notify);
                        getAllListMap.put("exec_email", execemail);
                        if (landmark.equals("null")) {

                            getAllListMap.put("landmark", "No details");
                        } else {
                            getAllListMap.put("landmark", landmark);
                        }
                        String[] contact_array = contact_person.split(",");
                        if (contact_person.equals("null")) {
                            getAllListMap.put("contact_person", "No details");
                            getAllListMap.put("contact_name", "No details");
                            getAllListMap.put("contact_email", "No details");
                            getAllListMap.put("contact_no", "No details");
                        } else if (contact_array.length > 1) {

                            getAllListMap.put("contact_person", "Details");
                            getAllListMap.put("contact_name", contact_array[0]);
                            getAllListMap.put("contact_email", contact_array[1]);
                            getAllListMap.put("contact_no", contact_array[2]);
                        }
                        getAllsalesList.add(getAllListMap);

                    }


                } while (c.moveToNext());

            }
        }
        c.close();
        db.close();
        return getAllsalesList;
    }

    public ArrayList<HashMap<String, String>> getAllsalesnewist1(DBOperation db) {


        ArrayList<HashMap<String, String>> getAllsalesList = new ArrayList<>();
        SQLiteDatabase sqlite = db.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + DBManager.TableInfo.TABLE_SALES_APPOINTMENT + " t1, " + DBManager.TableInfo.TABLE_SALES + " t2 WHERE "
                + "t1." + DBManager.TableInfo.db_sales_doc_id + " = " + "t2." + DBManager.TableInfo.db_sales_doc_id + " AND t1."
                + DBManager.TableInfo.KEY_LOGIN_USER + "='" + Login.umailid + "'" + " ORDER BY " + "t1." + DBManager.TableInfo.db_sales_starttime;
        Cursor c = sqlite.rawQuery(selectQuery, null);
        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        String curdate1 = df2.format(c1.getTime());
        Date date = null, date1 = null;
        String env1 = "";
        if (c != null) {
            if (c.moveToFirst()) {

                c.moveToFirst();
                do {
                    HashMap<String, String> getAllListMap = new HashMap<>();
                    String contact_person = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_contactperson));

                    String starttime = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_starttime));
                    String endtime = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_endtime));
                    String executionerid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_executioner_id));
                    String app_for = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_app_for));
                    String documentid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_doc_id));
                    String created_at = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_created_at));
                    String appid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_appointment_id));
                    String app_ref = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_app_ref));
                    String app_city = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_app_city));
                    String u_flag = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_u_flag));
                    String create_dt = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_create_dt));
                    String approved_by = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_approved_by));
                    String created_by = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_createdby));
                    String latitude = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_latitude));
                    String longititude = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_longititude));
                    String logical_del = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_logical_del));
                    String post_status = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_poststatus));
                    String apptype = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_app_type));
                    String env = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_env));
                    String address = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_address));
                    String landmark = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_landmark));
                    String id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_id));
                    String syncstatus = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_syncstatus));
                    String distance = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_distance));
                    String amount = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_amount));
                    String transtype = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_transtype));
                    String acvr_id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_acvr_id));
                    String acvrsyncstatus = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_acvrsyncstatus));
                    String notify = c.getString(c.getColumnIndex(DBManager.TableInfo.notification));
                    String execemail = c.getString(c.getColumnIndex(DBManager.TableInfo.KEY_LOGIN_USER));

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//
                    try {
                        date = dateFormat.parse(created_at);
                        date1 = dateFormat.parse(curdate1);

                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    if (env.substring(0, 1).toLowerCase().equals("o")) {


                        env1 = "o" + env.substring(2);
                        if (env1.equals(GenericMethods.rkeyfn)) {
                        }
                    }

                    if ((env.toLowerCase().equals(GenericMethods.rkeyfn) || env1.contains(GenericMethods.rkeyfn) || contact_person.toLowerCase().contains(GenericMethods.rkeyfn) || created_at.contains(GenericMethods.rkeyfn)) && (date.after(date1))) {
                        getAllListMap.put("contact_person", contact_person);
                        getAllListMap.put("starttime", starttime);
                        getAllListMap.put("endtime", endtime);
                        getAllListMap.put("executionerid", executionerid);
                        getAllListMap.put("app_for", app_for);
                        getAllListMap.put("document_id", documentid);
                        getAllListMap.put("created_at", created_at);
                        getAllListMap.put("appid", appid);
                        getAllListMap.put("app_ref", app_ref);
                        getAllListMap.put("app_city", app_city);
                        getAllListMap.put("u_flag", u_flag);
                        getAllListMap.put("create_dt", create_dt);
                        getAllListMap.put("approvedby", approved_by);
                        getAllListMap.put("createdby", created_by);
                        getAllListMap.put("lati", latitude);
                        getAllListMap.put("longi", longititude);
                        getAllListMap.put("logical_del", logical_del);
                        getAllListMap.put("poststatus", post_status);
                        getAllListMap.put("apptype", apptype);
                        getAllListMap.put("env", env);
                        getAllListMap.put("id", id);
                        getAllListMap.put("address", address);
                        getAllListMap.put("landmark", landmark);
                        getAllListMap.put("syncstatus", syncstatus);
                        getAllListMap.put("distance", distance);
                        getAllListMap.put("amount", amount);
                        getAllListMap.put("trans_type", transtype);
                        getAllListMap.put("acvr_id", acvr_id);
                        getAllListMap.put("acvrsync", acvrsyncstatus);
                        getAllListMap.put("notify", notify);
                        getAllListMap.put("exec_email", execemail);
                        if (landmark.equals("null")) {

                            getAllListMap.put("landmark", "No details");
                        } else {
                            getAllListMap.put("landmark", landmark);
                        }
                        String[] contact_array = contact_person.split(",");
                        if (contact_person.equals("null")) {
                            getAllListMap.put("contact_person", "No details");
                            getAllListMap.put("contact_name", "No details");
                            getAllListMap.put("contact_email", "No details");
                            getAllListMap.put("contact_no", "No details");
                        } else if (contact_array.length > 1) {

                            getAllListMap.put("contact_person", "Details");
                            getAllListMap.put("contact_name", contact_array[0]);
                            getAllListMap.put("contact_email", contact_array[1]);
                            getAllListMap.put("contact_no", contact_array[2]);
                        }
                        getAllsalesList.add(getAllListMap);

                    }


                } while (c.moveToNext());

            }
        }
        c.close();
        db.close();
        return getAllsalesList;
    }

    //
    public ArrayList<HashMap<String, String>> getAllsaleslist(DBOperation db) {

        ArrayList<HashMap<String, String>> getAllsalesList = new ArrayList<>();
        SQLiteDatabase sqlite = db.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + DBManager.TableInfo.TABLE_SALES_APPOINTMENT + " t1, " + DBManager.TableInfo.TABLE_SALES + " t2 WHERE "
                + "t1." + DBManager.TableInfo.db_sales_doc_id + " = " + "t2." + DBManager.TableInfo.db_sales_doc_id + " AND t1."
                + DBManager.TableInfo.KEY_LOGIN_USER + "='" + Login.umailid + "'" + " ORDER BY " + "t1." + DBManager.TableInfo.db_sales_starttime;
        Cursor c = sqlite.rawQuery(selectQuery, null);
        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        String curdate1 = df2.format(c1.getTime());
        Date date = null, date1 = null;
        if (c != null) {
            if (c.moveToFirst()) {

                c.moveToFirst();
                do {
                    HashMap<String, String> getAllListMap = new HashMap<>();
                    String contact_person = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_contactperson));
                    String startdate = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_startdate));
                    String starttime = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_starttime));
                    String endtime = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_endtime));
                    String executionerid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_executioner_id));
                    String app_for = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_app_for));
                    String documentid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_doc_id));
                    String created_at = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_created_at));
                    String appid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_appointment_id));
                    String app_ref = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_app_ref));
                    String app_city = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_app_city));
                    String u_flag = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_u_flag));
                    String create_dt = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_create_dt));
                    String approved_by = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_approved_by));
                    String created_by = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_createdby));
                    String latitude = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_latitude));
                    String longititude = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_longititude));
                    String logical_del = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_logical_del));
                    String post_status = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_poststatus));
                    String apptype = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_app_type));
                    String env = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_env));
                    String address = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_address));
                    String landmark = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_landmark));
                    String id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_id));
                    String syncstatus = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_syncstatus));
                    String distance = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_distance));
                    String amount = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_amount));
                    String transtype = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_transtype));
                    String acvr_id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_acvr_id));
                    String acvrsyncstatus = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_acvrsyncstatus));
                    String notify = c.getString(c.getColumnIndex(DBManager.TableInfo.notification));
                    String execemail = c.getString(c.getColumnIndex(DBManager.TableInfo.KEY_LOGIN_USER));

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                    try {
                        date = dateFormat.parse(startdate);
                        date1 = dateFormat.parse(curdate1);

                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                    if ((post_status.equals("1"))) {

                        getAllListMap.put("contact_person", contact_person);
                        getAllListMap.put("startdate", startdate);
                        getAllListMap.put("starttime", starttime);
                        getAllListMap.put("endtime", endtime);
                        getAllListMap.put("executionerid", executionerid);
                        getAllListMap.put("app_for", app_for);
                        getAllListMap.put("document_id", documentid);
                        getAllListMap.put("created_at", created_at);
                        getAllListMap.put("appid", appid);
                        getAllListMap.put("app_ref", app_ref);
                        getAllListMap.put("app_city", app_city);
                        getAllListMap.put("u_flag", u_flag);
                        getAllListMap.put("create_dt", create_dt);
                        getAllListMap.put("approvedby", approved_by);
                        getAllListMap.put("createdby", created_by);
                        getAllListMap.put("lati", latitude);
                        getAllListMap.put("longi", longititude);
                        getAllListMap.put("logical_del", logical_del);
                        getAllListMap.put("poststatus", post_status);
                        getAllListMap.put("apptype", apptype);
                        getAllListMap.put("env", env);
                        getAllListMap.put("id", id);
                        getAllListMap.put("address", address);
                        getAllListMap.put("landmark", landmark);
                        getAllListMap.put("syncstatus", syncstatus);
                        getAllListMap.put("distance", distance);
                        getAllListMap.put("amount", amount);
                        getAllListMap.put("trans_type", transtype);
                        getAllListMap.put("acvr_id", acvr_id);
                        getAllListMap.put("acvrsync", acvrsyncstatus);
                        getAllListMap.put("notify", notify);
                        getAllListMap.put("exec_email", execemail);
                        if (landmark.equals("null")) {

                            getAllListMap.put("landmark", "No details");
                        } else {
                            getAllListMap.put("landmark", landmark);
                        }
                        String[] contact_array = contact_person.split(",");
                        if (contact_person.equals("null")) {
                            getAllListMap.put("contact_person", "No details");
                            getAllListMap.put("contact_name", "No details");
                            getAllListMap.put("contact_email", "No details");
                            getAllListMap.put("contact_no", "No details");
                        } else if (contact_array.length > 1) {

                            getAllListMap.put("contact_person", "Details");
                            getAllListMap.put("contact_name", contact_array[0]);
                            getAllListMap.put("contact_email", contact_array[1]);
                            getAllListMap.put("contact_no", contact_array[2]);
                        }
                        getAllsalesList.add(getAllListMap);
                    }


                } while (c.moveToNext());

            }
        }
        c.close();
        db.close();
        return getAllsalesList;
    }


    public ArrayList<HashMap<String, String>> getAllsaleslist1(DBOperation db) {

        ArrayList<HashMap<String, String>> getAllsalesList = new ArrayList<>();
        SQLiteDatabase sqlite = db.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + DBManager.TableInfo.TABLE_SALES_APPOINTMENT + " t1, " + DBManager.TableInfo.TABLE_SALES + " t2 WHERE "
                + "t1." + DBManager.TableInfo.db_sales_doc_id + " = " + "t2." + DBManager.TableInfo.db_sales_doc_id + " AND t1."
                + DBManager.TableInfo.KEY_LOGIN_USER + "='" + Login.umailid + "'" + " ORDER BY " + "t1." + DBManager.TableInfo.db_sales_starttime;
        Cursor c = sqlite.rawQuery(selectQuery, null);
        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        String curdate1 = df2.format(c1.getTime());
        Date date = null, date1 = null;
        String env1 = "";
        if (c != null) {
            if (c.moveToFirst()) {

                c.moveToFirst();
                do {
                    HashMap<String, String> getAllListMap = new HashMap<>();
                    String contact_person = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_contactperson));
                    String starttime = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_starttime));
                    String endtime = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_endtime));
                    String executionerid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_executioner_id));
                    String app_for = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_app_for));
                    String documentid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_doc_id));
                    String created_at = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_created_at));
                    String appid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_appointment_id));
                    String app_ref = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_app_ref));
                    String app_city = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_app_city));
                    String u_flag = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_u_flag));
                    String create_dt = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_create_dt));
                    String approved_by = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_approved_by));
                    String created_by = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_createdby));
                    String latitude = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_latitude));
                    String longititude = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_longititude));
                    String logical_del = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_logical_del));
                    String post_status = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_poststatus));
                    String apptype = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_app_type));
                    String env = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_env));
                    String address = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_address));
                    String landmark = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_landmark));
                    String id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_id));
                    String syncstatus = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_syncstatus));
                    String distance = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_distance));
                    String amount = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_amount));
                    String transtype = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_transtype));
                    String acvr_id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_acvr_id));
                    String acvrsyncstatus = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_acvrsyncstatus));
                    String notify = c.getString(c.getColumnIndex(DBManager.TableInfo.notification));
                    String execemail = c.getString(c.getColumnIndex(DBManager.TableInfo.KEY_LOGIN_USER));

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//

//
                    try {
                        date = dateFormat.parse(created_at);
                        date1 = dateFormat.parse(curdate1);

                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    if (env.substring(0, 1).toLowerCase().equals("o")) {


                        env1 = "o" + env.substring(2);
                        if (env1.equals(GenericMethods.rkeyfn)) {
                        }
                    }

                    if ((env.toLowerCase().equals(GenericMethods.rkeyfn) || env1.contains(GenericMethods.rkeyfn) || contact_person.toLowerCase().contains(GenericMethods.rkeyfn) || created_at.contains(GenericMethods.rkeyfn)) && (post_status.equals("1"))) {

                        getAllListMap.put("contact_person", contact_person);
                        getAllListMap.put("starttime", starttime);
                        getAllListMap.put("endtime", endtime);
                        getAllListMap.put("executionerid", executionerid);
                        getAllListMap.put("app_for", app_for);
                        getAllListMap.put("document_id", documentid);
                        getAllListMap.put("created_at", created_at);
                        getAllListMap.put("appid", appid);
                        getAllListMap.put("app_ref", app_ref);
                        getAllListMap.put("app_city", app_city);
                        getAllListMap.put("u_flag", u_flag);
                        getAllListMap.put("create_dt", create_dt);
                        getAllListMap.put("approvedby", approved_by);
                        getAllListMap.put("createdby", created_by);
                        getAllListMap.put("lati", latitude);
                        getAllListMap.put("longi", longititude);
                        getAllListMap.put("logical_del", logical_del);
                        getAllListMap.put("poststatus", post_status);
                        getAllListMap.put("apptype", apptype);
                        getAllListMap.put("env", env);
                        getAllListMap.put("id", id);
                        getAllListMap.put("address", address);
                        getAllListMap.put("landmark", landmark);
                        getAllListMap.put("syncstatus", syncstatus);
                        getAllListMap.put("distance", distance);
                        getAllListMap.put("amount", amount);
                        getAllListMap.put("trans_type", transtype);
                        getAllListMap.put("acvr_id", acvr_id);
                        getAllListMap.put("acvrsync", acvrsyncstatus);
                        getAllListMap.put("notify", notify);
                        getAllListMap.put("exec_email", execemail);
                        if (landmark.equals("null")) {

                            getAllListMap.put("landmark", "No details");
                        } else {
                            getAllListMap.put("landmark", landmark);
                        }
                        String[] contact_array = contact_person.split(",");
                        if (contact_person.equals("null")) {
                            getAllListMap.put("contact_person", "No details");
                            getAllListMap.put("contact_name", "No details");
                            getAllListMap.put("contact_email", "No details");
                            getAllListMap.put("contact_no", "No details");
                        } else if (contact_array.length > 1) {

                            getAllListMap.put("contact_person", "Details");
                            getAllListMap.put("contact_name", contact_array[0]);
                            getAllListMap.put("contact_email", contact_array[1]);
                            getAllListMap.put("contact_no", contact_array[2]);
                        }
                        getAllsalesList.add(getAllListMap);

                    }


                } while (c.moveToNext());
            }
        }
        c.close();
        db.close();
        return getAllsalesList;
    }


    public ArrayList<HashMap<String, String>> getAllmarriageTodayList(DBOperation db) {
        Cursor c = null;


        ArrayList<HashMap<String, String>> getAllsalesList = new ArrayList<>();
        SQLiteDatabase sqlite = db.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + DBManager.TableInfo.TABLE_MARRIAGE_APPOINTMENT + " t1, " + DBManager.TableInfo.TABLE_MARRIAGE + " t2 WHERE "
                + "t1." + DBManager.TableInfo.db_marriage_doc_id + " = " + "t2." + DBManager.TableInfo.db_marriage_doc_id + " AND t1."
                + DBManager.TableInfo.KEY_LOGIN_USER + "='" + Login.umailid + "'" + " ORDER BY " + "t1." + DBManager.TableInfo.db_marriage_starttime;


        c = sqlite.rawQuery(selectQuery, null);
        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        String curdate1 = df2.format(c1.getTime());
        Date date = null, date1 = null;

        if (c != null) {

            if (c.moveToFirst()) {
                String count = c.getString(0);

                c.moveToFirst();
                do {

                    HashMap<String, String> getAllListMap = new HashMap<>();
                    String documentid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_doc_id));
                    String env_marriage = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_env));
                    String startdate = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_startdate));
                    String husbandname = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_husbandname));
                    String wifename = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_wifename));
                    String husbandcontact = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_husbandcontact));
                    String wifecontact = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_wifecontact));
                    String husbandbiometric = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_husbandbiometric));
                    String wifebiometric = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_wifebiometric));
                    String tokenno = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_tokenno));
                    String contact_person = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_contact_person));
                    String start_time = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_starttime));
                    String end_time = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_endtime));
                    String address = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_address));
                    String landmark = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_landmark));
                    String appfor = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_app_for));
                    String executionerid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_executionerid));
                    String appid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_appointment_id));
                    String latitude = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_latitude));
                    String longititude = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_longititude));
                    String distance = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_distance));
                    String transtype = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_transtype));
                    String acvr_amount = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_acvr_amount));
                    String acvr_id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_acvr_id));
                    String logical_del = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_logical_del));
                    String post_status = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_poststatus));
                    String franchise_id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_franchise_id));
                    String co_id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_co_id));
                    String id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_id));
                    String status = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_status));
                    String payverify = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_pay_verify));
                    String paypending = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_pay_pending));
                    String ex_id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_ex_id));
                    String payflag = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_pay_flag));
                    String app_flag = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_app_flag));
                    String created_at = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_created_at));
                    String statusid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_status_id));
                    String apptype = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_app_type));
                    String syncstatusmarriage = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_syncstatus));
                    String acvrsyncstatusmarriage = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_acvrsyncstatus));
                    String notify = c.getString(c.getColumnIndex(DBManager.TableInfo.notification));
                    String execemail = c.getString(c.getColumnIndex(DBManager.TableInfo.KEY_LOGIN_USER));
                    String marriage_type = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_type));

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                    try {
                        date = dateFormat.parse(startdate);
                        date1 = dateFormat.parse(curdate1);

                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                    if (date.equals(date1)) {

                        getAllListMap.put("contact_person", contact_person);
                        getAllListMap.put("starttime", start_time);
                        getAllListMap.put("endtime", end_time);
                        getAllListMap.put("startdate", startdate);
                        getAllListMap.put("husbandname", husbandname);
                        getAllListMap.put("wifename", wifename);
                        getAllListMap.put("husbandcontact", husbandcontact);
                        getAllListMap.put("wifecontact", wifecontact);
                        getAllListMap.put("husbandbiometric", husbandbiometric);
                        getAllListMap.put("wifebiometric", wifebiometric);
                        getAllListMap.put("tokenno", tokenno);
                        getAllListMap.put("executionerid", executionerid);
                        getAllListMap.put("app_for", appfor);
                        getAllListMap.put("document_id", documentid);
                        getAllListMap.put("created_at", created_at);
                        getAllListMap.put("appid", appid);
                        getAllListMap.put("env", env_marriage);
                        getAllListMap.put("marriage_type", marriage_type);
                        getAllListMap.put("address", address);
                        getAllListMap.put("landmark", landmark);
                        getAllListMap.put("appid", appid);
                        getAllListMap.put("latitude", latitude);
                        getAllListMap.put("longititude", longititude);
                        getAllListMap.put("distance", distance);
                        getAllListMap.put("transtype", transtype);
                        getAllListMap.put("acvr_amount", acvr_amount);
                        getAllListMap.put("acvr_id", acvr_id);
                        getAllListMap.put("logical_del", logical_del);
                        getAllListMap.put("post_status", post_status);
                        getAllListMap.put("franchise_id", franchise_id);
                        getAllListMap.put("co_id", co_id);
                        getAllListMap.put("status", status);
                        getAllListMap.put("id", id);
                        getAllListMap.put("payverify", payverify);
                        getAllListMap.put("paypending", paypending);
                        getAllListMap.put("ex_id", ex_id);
                        getAllListMap.put("payflag", payflag);
                        getAllListMap.put("appflag", app_flag);
                        getAllListMap.put("status_id", statusid);
                        getAllListMap.put("app_type", apptype);
                        getAllListMap.put("notify", notify);
                        getAllListMap.put("exec_email", execemail);
                        getAllListMap.put("syncstatus", syncstatusmarriage);
                        getAllListMap.put("acvrsync", acvrsyncstatusmarriage);
                        if (landmark.equals("null")) {

                            getAllListMap.put("landmark", "No details");
                        } else {
                            getAllListMap.put("landmark", landmark);
                        }

                        String[] contact_array = contact_person.split(",");
                        if (contact_person.equals("null")) {
                            getAllListMap.put("contact_person", "No details");
                            getAllListMap.put("contact_name", "No details");
                            getAllListMap.put("contact_email", "No details");
                            getAllListMap.put("contact_no", "No details");
                        } else if (contact_array.length > 1) {

                            getAllListMap.put("contact_person", "Details");
                            getAllListMap.put("contact_name", contact_array[0]);
                            getAllListMap.put("contact_email", contact_array[1]);
                            getAllListMap.put("contact_no", contact_array[2]);
                        }
                        getAllsalesList.add(getAllListMap);


                    }


                } while (c.moveToNext());


            }

        }
        c.close();
        db.close();
        return getAllsalesList;
    }

    public ArrayList<HashMap<String, String>> getAllmarriageTodayList1(DBOperation db) {
        Cursor c = null;


        ArrayList<HashMap<String, String>> getAllsalesList = new ArrayList<>();
        SQLiteDatabase sqlite = db.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + DBManager.TableInfo.TABLE_MARRIAGE_APPOINTMENT + " t1, " + DBManager.TableInfo.TABLE_MARRIAGE + " t2 WHERE "
                + "t1." + DBManager.TableInfo.db_marriage_doc_id + " = " + "t2." + DBManager.TableInfo.db_marriage_doc_id + " AND t1."
                + DBManager.TableInfo.KEY_LOGIN_USER + "='" + Login.umailid + "'" + " ORDER BY " + "t1." + DBManager.TableInfo.db_marriage_starttime;


        c = sqlite.rawQuery(selectQuery, null);
        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        String curdate1 = df2.format(c1.getTime());
        Date date = null, date1 = null;
        String env_marriage1 = "";

        if (c != null) {
            if (c.moveToFirst()) {
                String count = c.getString(0);
                c.moveToFirst();
                do {
                    HashMap<String, String> getAllListMap = new HashMap<>();
                    String documentid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_doc_id));

                    String env_marriage = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_env));
                    String contact_person = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_contact_person));
                    String start_time = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_starttime));
                    String end_time = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_endtime));
                    String address = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_address));
                    String landmark = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_landmark));
                    String appfor = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_app_for));
                    String executionerid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_executionerid));
                    String appid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_appointment_id));
                    String latitude = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_latitude));
                    String longititude = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_longititude));
                    String distance = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_distance));
                    String transtype = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_transtype));
                    String acvr_amount = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_acvr_amount));
                    String acvr_id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_acvr_id));
                    String logical_del = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_logical_del));
                    String post_status = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_poststatus));
                    String franchise_id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_franchise_id));
                    String co_id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_co_id));
                    String id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_id));
                    String status = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_status));
                    String payverify = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_pay_verify));
                    String paypending = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_pay_pending));
                    String ex_id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_ex_id));
                    String payflag = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_pay_flag));
                    String app_flag = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_app_flag));
                    String created_at = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_created_at));
                    String statusid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_status_id));
                    String apptype = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_app_type));
                    String notify = c.getString(c.getColumnIndex(DBManager.TableInfo.notification));
                    String execemail = c.getString(c.getColumnIndex(DBManager.TableInfo.KEY_LOGIN_USER));
                    String marriage_type = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_type));

                    String syncstatusmarriage = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_syncstatus));
                    String acvrsyncstatusmarriage = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_acvrsyncstatus));


                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//
                    try {
                        date = dateFormat.parse(created_at);
                        date1 = dateFormat.parse(curdate1);

                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    if (env_marriage.substring(0, 1).toLowerCase().equals("m")) {


                        env_marriage1 = "m" + env_marriage.substring(2);
                        if (env_marriage1.equals(GenericMethods.rkeyfn)) {

                        }
                    }


                    if ((env_marriage.toLowerCase().equals(GenericMethods.rkeyfn) || env_marriage1.contains(GenericMethods.rkeyfn) || contact_person.toLowerCase().contains(GenericMethods.rkeyfn) || created_at.contains(GenericMethods.rkeyfn)) && (date.equals(date1) && (!post_status.equals("1") || post_status.equals("null")))) {
                        getAllListMap.put("contact_person", contact_person);
                        getAllListMap.put("starttime", start_time);
                        getAllListMap.put("endtime", end_time);

                        getAllListMap.put("executionerid", executionerid);
                        getAllListMap.put("app_for", appfor);
                        getAllListMap.put("document_id", documentid);
                        getAllListMap.put("created_at", created_at);
                        getAllListMap.put("appid", appid);
                        getAllListMap.put("env", env_marriage);
                        getAllListMap.put("address", address);
                        getAllListMap.put("landmark", landmark);
                        getAllListMap.put("appid", appid);
                        getAllListMap.put("marriage_type", marriage_type);
                        getAllListMap.put("latitude", latitude);
                        getAllListMap.put("longititude", longititude);
                        getAllListMap.put("distance", distance);
                        getAllListMap.put("transtype", transtype);
                        getAllListMap.put("acvr_amount", acvr_amount);
                        getAllListMap.put("acvr_id", acvr_id);
                        getAllListMap.put("logical_del", logical_del);
                        getAllListMap.put("post_status", post_status);
                        getAllListMap.put("franchise_id", franchise_id);
                        getAllListMap.put("co_id", co_id);
                        getAllListMap.put("status", status);
                        getAllListMap.put("id", id);
                        getAllListMap.put("payverify", payverify);
                        getAllListMap.put("paypending", paypending);
                        getAllListMap.put("ex_id", ex_id);
                        getAllListMap.put("payflag", payflag);
                        getAllListMap.put("appflag", app_flag);
                        getAllListMap.put("status_id", statusid);
                        getAllListMap.put("app_type", apptype);
                        getAllListMap.put("syncstatus", syncstatusmarriage);
                        getAllListMap.put("acvrsync", acvrsyncstatusmarriage);
                        getAllListMap.put("notify", notify);
                        getAllListMap.put("exec_email", execemail);
                        if (landmark.equals("null")) {

                            getAllListMap.put("landmark", "No details");
                        } else {
                            getAllListMap.put("landmark", landmark);
                        }

                        String[] contact_array = contact_person.split(",");
                        if (contact_person.equals("null")) {
                            getAllListMap.put("contact_person", "No details");
                            getAllListMap.put("contact_name", "No details");
                            getAllListMap.put("contact_email", "No details");
                            getAllListMap.put("contact_no", "No details");
                        } else if (contact_array.length > 1) {

                            getAllListMap.put("contact_person", "Details");
                            getAllListMap.put("contact_name", contact_array[0]);
                            getAllListMap.put("contact_email", contact_array[1]);
                            getAllListMap.put("contact_no", contact_array[2]);
                        }
                        getAllsalesList.add(getAllListMap);


                    }


                } while (c.moveToNext());


            }

        }
        c.close();
        db.close();
        return getAllsalesList;
    }

    public ArrayList<HashMap<String, String>> getAllmarriageOlderList(DBOperation db) {
        Cursor c = null;

        ArrayList<HashMap<String, String>> getAllsalesList = new ArrayList<>();
        SQLiteDatabase sqlite = db.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + DBManager.TableInfo.TABLE_MARRIAGE_APPOINTMENT + " t1, " + DBManager.TableInfo.TABLE_MARRIAGE + " t2 WHERE "
                + "t1." + DBManager.TableInfo.db_marriage_doc_id + " = " + "t2." + DBManager.TableInfo.db_marriage_doc_id + " AND t1."
                + DBManager.TableInfo.KEY_LOGIN_USER + "='" + Login.umailid + "'" + " ORDER BY " + "t1." + DBManager.TableInfo.db_marriage_starttime;

        c = sqlite.rawQuery(selectQuery, null);
        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        String curdate1 = df2.format(c1.getTime());
        Date date = null, date1 = null;

        if (c != null) {
            if (c.moveToFirst()) {
                String count = c.getString(0);
                c.moveToFirst();
                do {
                    HashMap<String, String> getAllListMap = new HashMap<>();
                    String documentid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_doc_id));
                    String startdate = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_startdate));
                    String husbandname = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_husbandname));
                    String wifename = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_wifename));
                    String husbandcontact = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_husbandcontact));
                    String wifecontact = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_wifecontact));
                    String husbandbiometric = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_husbandbiometric));
                    String wifebiometric = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_wifebiometric));
                    String tokenno = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_tokenno));
                    String env_marriage = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_env));
                    String contact_person = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_contact_person));
                    String start_time = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_starttime));
                    String end_time = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_endtime));
                    String address = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_address));
                    String landmark = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_landmark));
                    String appfor = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_app_for));
                    String executionerid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_executionerid));
                    String appid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_appointment_id));
                    String latitude = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_latitude));
                    String longititude = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_longititude));
                    String distance = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_distance));
                    String transtype = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_transtype));
                    String acvr_amount = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_acvr_amount));
                    String acvr_id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_acvr_id));
                    String logical_del = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_logical_del));
                    String post_status = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_poststatus));
                    String franchise_id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_franchise_id));
                    String co_id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_co_id));
                    String id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_id));
                    String status = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_status));
                    String payverify = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_pay_verify));
                    String paypending = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_pay_pending));
                    String ex_id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_ex_id));
                    String payflag = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_pay_flag));
                    String app_flag = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_app_flag));
                    String created_at = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_created_at));
                    String statusid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_status_id));
                    String apptype = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_app_type));
                    String syncstatusmarriage = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_syncstatus));
                    String acvrsyncstatusmarriage = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_acvrsyncstatus));
                    String execemail = c.getString(c.getColumnIndex(DBManager.TableInfo.KEY_LOGIN_USER));
                    String marriage_type = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_type));

                    String notify = c.getString(c.getColumnIndex(DBManager.TableInfo.notification));


                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//
                    try {
                        date = dateFormat.parse(startdate);
                        date1 = dateFormat.parse(curdate1);

                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                    if (date.before(date1) && (!post_status.equals("1") && !husbandbiometric.equals("1") || !wifebiometric.equals("1"))) {

                        getAllListMap.put("contact_person", contact_person);
                        getAllListMap.put("starttime", start_time);
                        getAllListMap.put("startdate", startdate);
                        getAllListMap.put("husbandname", husbandname);
                        getAllListMap.put("wifename", wifename);
                        getAllListMap.put("husbandcontact", husbandcontact);
                        getAllListMap.put("wifecontact", wifecontact);
                        getAllListMap.put("husbandbiometric", husbandbiometric);
                        getAllListMap.put("wifebiometric", wifebiometric);
                        getAllListMap.put("tokenno", tokenno);
                        getAllListMap.put("endtime", end_time);
                        getAllListMap.put("executionerid", executionerid);
                        getAllListMap.put("app_for", appfor);
                        getAllListMap.put("document_id", documentid);
                        getAllListMap.put("created_at", created_at);
                        getAllListMap.put("appid", appid);
                        getAllListMap.put("env", env_marriage);
                        getAllListMap.put("address", address);
                        getAllListMap.put("landmark", landmark);
                        getAllListMap.put("appid", appid);
                        getAllListMap.put("latitude", latitude);
                        getAllListMap.put("longititude", longititude);
                        getAllListMap.put("distance", distance);
                        getAllListMap.put("transtype", transtype);
                        getAllListMap.put("acvr_amount", acvr_amount);
                        getAllListMap.put("acvr_id", acvr_id);
                        getAllListMap.put("logical_del", logical_del);
                        getAllListMap.put("post_status", post_status);
                        getAllListMap.put("franchise_id", franchise_id);
                        getAllListMap.put("co_id", co_id);
                        getAllListMap.put("status", status);
                        getAllListMap.put("id", id);
                        getAllListMap.put("marriage_type", marriage_type);
                        getAllListMap.put("payverify", payverify);
                        getAllListMap.put("paypending", paypending);
                        getAllListMap.put("ex_id", ex_id);
                        getAllListMap.put("payflag", payflag);
                        getAllListMap.put("appflag", app_flag);
                        getAllListMap.put("status_id", statusid);
                        getAllListMap.put("app_type", apptype);
                        getAllListMap.put("syncstatus", syncstatusmarriage);
                        getAllListMap.put("acvrsync", acvrsyncstatusmarriage);
                        getAllListMap.put("notify", notify);
                        getAllListMap.put("exec_email", execemail);
                        if (landmark.equals("null")) {

                            getAllListMap.put("landmark", "No details");
                        } else {
                            getAllListMap.put("landmark", landmark);
                        }

                        String[] contact_array = contact_person.split(",");
                        if (contact_person.equals("null")) {
                            getAllListMap.put("contact_person", "No details");
                            getAllListMap.put("contact_name", "No details");
                            getAllListMap.put("contact_email", "No details");
                            getAllListMap.put("contact_no", "No details");
                        } else if (contact_array.length > 1) {

                            getAllListMap.put("contact_person", "Details");
                            getAllListMap.put("contact_name", contact_array[0]);
                            getAllListMap.put("contact_email", contact_array[1]);
                            getAllListMap.put("contact_no", contact_array[2]);
                        }
                        getAllsalesList.add(getAllListMap);


                    }


                } while (c.moveToNext());


            }

        }
        c.close();
        db.close();
        return getAllsalesList;
    }

    public ArrayList<HashMap<String, String>> getAllmarriageOlderList1(DBOperation db) {
        Cursor c = null;


        ArrayList<HashMap<String, String>> getAllsalesList = new ArrayList<>();
        SQLiteDatabase sqlite = db.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + DBManager.TableInfo.TABLE_MARRIAGE_APPOINTMENT + " t1, " + DBManager.TableInfo.TABLE_MARRIAGE + " t2 WHERE "
                + "t1." + DBManager.TableInfo.db_marriage_doc_id + " = " + "t2." + DBManager.TableInfo.db_marriage_doc_id + " AND t1."
                + DBManager.TableInfo.KEY_LOGIN_USER + "='" + Login.umailid + "'" + " ORDER BY " + "t1." + DBManager.TableInfo.db_marriage_starttime;

        c = sqlite.rawQuery(selectQuery, null);
        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        String curdate1 = df2.format(c1.getTime());
        Date date = null, date1 = null;
        String env_marriage1 = "";

        if (c != null) {

            if (c.moveToFirst()) {
                String count = c.getString(0);

                c.moveToFirst();
                do {

                    HashMap<String, String> getAllListMap = new HashMap<>();
                    String documentid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_doc_id));

                    String env_marriage = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_env));
                    String contact_person = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_contact_person));
                    String start_time = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_starttime));
                    String end_time = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_endtime));
                    String address = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_address));
                    String landmark = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_landmark));
                    String appfor = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_app_for));
                    String executionerid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_executionerid));
                    String appid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_appointment_id));
                    String latitude = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_latitude));
                    String longititude = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_longititude));
                    String distance = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_distance));
                    String transtype = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_transtype));
                    String acvr_amount = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_acvr_amount));
                    String acvr_id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_acvr_id));
                    String logical_del = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_logical_del));
                    String post_status = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_poststatus));
                    String franchise_id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_franchise_id));
                    String co_id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_co_id));
                    String id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_id));
                    String status = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_status));
                    String payverify = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_pay_verify));
                    String paypending = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_pay_pending));
                    String ex_id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_ex_id));
                    String payflag = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_pay_flag));
                    String app_flag = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_app_flag));
                    String created_at = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_created_at));
                    String statusid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_status_id));
                    String apptype = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_app_type));
                    String syncstatusmarriage = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_syncstatus));
                    String acvrsyncstatusmarriage = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_acvrsyncstatus));
                    String notify = c.getString(c.getColumnIndex(DBManager.TableInfo.notification));
                    String execemail = c.getString(c.getColumnIndex(DBManager.TableInfo.KEY_LOGIN_USER));
                    String marriage_type = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_type));


                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                    try {
                        date = dateFormat.parse(created_at);
                        date1 = dateFormat.parse(curdate1);

                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    if (env_marriage.substring(0, 1).toLowerCase().equals("m")) {


                        env_marriage1 = "m" + env_marriage.substring(2);
                        if (env_marriage1.equals(GenericMethods.rkeyfn)) {

                        }
                    }


                    if ((env_marriage.toLowerCase().equals(GenericMethods.rkeyfn) || env_marriage1.contains(GenericMethods.rkeyfn) || contact_person.toLowerCase().contains(GenericMethods.rkeyfn) || created_at.contains(GenericMethods.rkeyfn)) && (date.before(date1) && (!post_status.equals("1") || post_status.equals("null")))) {

                        getAllListMap.put("contact_person", contact_person);
                        getAllListMap.put("starttime", start_time);
                        getAllListMap.put("endtime", end_time);
                        getAllListMap.put("executionerid", executionerid);
                        getAllListMap.put("app_for", appfor);
                        getAllListMap.put("document_id", documentid);
                        getAllListMap.put("created_at", created_at);
                        getAllListMap.put("appid", appid);
                        getAllListMap.put("env", env_marriage);
                        getAllListMap.put("address", address);
                        getAllListMap.put("landmark", landmark);
                        getAllListMap.put("appid", appid);
                        getAllListMap.put("latitude", latitude);
                        getAllListMap.put("longititude", longititude);
                        getAllListMap.put("distance", distance);
                        getAllListMap.put("transtype", transtype);
                        getAllListMap.put("acvr_amount", acvr_amount);
                        getAllListMap.put("acvr_id", acvr_id);
                        getAllListMap.put("logical_del", logical_del);
                        getAllListMap.put("post_status", post_status);
                        getAllListMap.put("franchise_id", franchise_id);
                        getAllListMap.put("co_id", co_id);
                        getAllListMap.put("status", status);
                        getAllListMap.put("id", id);
                        getAllListMap.put("payverify", payverify);
                        getAllListMap.put("paypending", paypending);
                        getAllListMap.put("ex_id", ex_id);
                        getAllListMap.put("payflag", payflag);
                        getAllListMap.put("marriage_type", marriage_type);
                        getAllListMap.put("appflag", app_flag);
                        getAllListMap.put("status_id", statusid);
                        getAllListMap.put("app_type", apptype);
                        getAllListMap.put("syncstatus", syncstatusmarriage);
                        getAllListMap.put("acvrsync", acvrsyncstatusmarriage);
                        getAllListMap.put("notify", notify);
                        getAllListMap.put("exec_email", execemail);

                        if (landmark.equals("null")) {

                            getAllListMap.put("landmark", "No details");
                        } else {
                            getAllListMap.put("landmark", landmark);
                        }

                        String[] contact_array = contact_person.split(",");
                        if (contact_person.equals("null")) {
                            getAllListMap.put("contact_person", "No details");
                            getAllListMap.put("contact_name", "No details");
                            getAllListMap.put("contact_email", "No details");
                            getAllListMap.put("contact_no", "No details");
                        } else if (contact_array.length > 1) {

                            getAllListMap.put("contact_person", "Details");
                            getAllListMap.put("contact_name", contact_array[0]);
                            getAllListMap.put("contact_email", contact_array[1]);
                            getAllListMap.put("contact_no", contact_array[2]);
                        }
                        getAllsalesList.add(getAllListMap);


                    }


                } while (c.moveToNext());


            }

        }
        c.close();
        db.close();
        return getAllsalesList;
    }

    public ArrayList<HashMap<String, String>> getAllmarriagenewList(DBOperation db) {
        Cursor c = null;

        ArrayList<HashMap<String, String>> getAllsalesList = new ArrayList<>();
        SQLiteDatabase sqlite = db.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + DBManager.TableInfo.TABLE_MARRIAGE_APPOINTMENT + " t1, " + DBManager.TableInfo.TABLE_MARRIAGE + " t2 WHERE "
                + "t1." + DBManager.TableInfo.db_marriage_doc_id + " = " + "t2." + DBManager.TableInfo.db_marriage_doc_id + " AND t1."
                + DBManager.TableInfo.KEY_LOGIN_USER + "='" + Login.umailid + "'" + " ORDER BY " + "t1." + DBManager.TableInfo.db_marriage_starttime;

        c = sqlite.rawQuery(selectQuery, null);
        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        String curdate1 = df2.format(c1.getTime());
        Date date = null, date1 = null;
        if (c != null) {
            if (c.moveToFirst()) {
                String count = c.getString(0);
                c.moveToFirst();
                do {
                    HashMap<String, String> getAllListMap = new HashMap<>();
                    String documentid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_doc_id));
                    String startdate = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_startdate));
                    String husbandname = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_husbandname));
                    String wifename = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_wifename));
                    String husbandcontact = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_husbandcontact));
                    String wifecontact = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_wifecontact));
                    String husbandbiometric = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_husbandbiometric));
                    String wifebiometric = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_wifebiometric));
                    String tokenno = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_tokenno));
                    String env_marriage = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_env));
                    String contact_person = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_contact_person));
                    String start_time = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_starttime));
                    String end_time = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_endtime));
                    String address = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_address));
                    String landmark = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_landmark));
                    String appfor = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_app_for));
                    String executionerid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_executionerid));
                    String appid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_appointment_id));
                    String latitude = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_latitude));
                    String longititude = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_longititude));
                    String distance = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_distance));
                    String transtype = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_transtype));
                    String acvr_amount = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_acvr_amount));
                    String acvr_id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_acvr_id));
                    String logical_del = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_logical_del));
                    String post_status = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_poststatus));
                    String franchise_id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_franchise_id));
                    String co_id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_co_id));
                    String id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_id));
                    String status = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_status));
                    String payverify = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_pay_verify));
                    String paypending = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_pay_pending));
                    String ex_id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_ex_id));
                    String payflag = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_pay_flag));
                    String app_flag = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_app_flag));
                    String created_at = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_created_at));
                    String statusid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_status_id));
                    String apptype = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_app_type));
                    String syncstatusmarriage = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_syncstatus));
                    String acvrsyncstatusmarriage = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_acvrsyncstatus));
                    String execemail = c.getString(c.getColumnIndex(DBManager.TableInfo.KEY_LOGIN_USER));
                    String marriage_type = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_type));

                    String notify = c.getString(c.getColumnIndex(DBManager.TableInfo.notification));

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//
                    try {
                        date = dateFormat.parse(startdate);
                        date1 = dateFormat.parse(curdate1);

                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                    if (date.after(date1)) {

                        getAllListMap.put("contact_person", contact_person);
                        getAllListMap.put("starttime", start_time);
                        getAllListMap.put("endtime", end_time);
                        getAllListMap.put("startdate", startdate);
                        getAllListMap.put("husbandname", husbandname);
                        getAllListMap.put("wifename", wifename);
                        getAllListMap.put("husbandcontact", husbandcontact);
                        getAllListMap.put("wifecontact", wifecontact);
                        getAllListMap.put("husbandbiometric", husbandbiometric);
                        getAllListMap.put("wifebiometric", wifebiometric);
                        getAllListMap.put("tokenno", tokenno);
                        getAllListMap.put("executionerid", executionerid);
                        getAllListMap.put("app_for", appfor);
                        getAllListMap.put("document_id", documentid);
                        getAllListMap.put("created_at", created_at);
                        getAllListMap.put("appid", appid);
                        getAllListMap.put("env", env_marriage);
                        getAllListMap.put("address", address);
                        getAllListMap.put("landmark", landmark);
                        getAllListMap.put("appid", appid);
                        getAllListMap.put("latitude", latitude);
                        getAllListMap.put("longititude", longititude);
                        getAllListMap.put("distance", distance);
                        getAllListMap.put("transtype", transtype);
                        getAllListMap.put("acvr_amount", acvr_amount);
                        getAllListMap.put("acvr_id", acvr_id);
                        getAllListMap.put("logical_del", logical_del);
                        getAllListMap.put("post_status", post_status);
                        getAllListMap.put("franchise_id", franchise_id);
                        getAllListMap.put("co_id", co_id);
                        getAllListMap.put("status", status);
                        getAllListMap.put("id", id);
                        getAllListMap.put("marriage_type", marriage_type);
                        getAllListMap.put("payverify", payverify);
                        getAllListMap.put("paypending", paypending);
                        getAllListMap.put("ex_id", ex_id);
                        getAllListMap.put("payflag", payflag);
                        getAllListMap.put("appflag", app_flag);
                        getAllListMap.put("status_id", statusid);
                        getAllListMap.put("app_type", apptype);
                        getAllListMap.put("syncstatus", syncstatusmarriage);
                        getAllListMap.put("acvrsync", acvrsyncstatusmarriage);
                        getAllListMap.put("notify", notify);
                        getAllListMap.put("exec_email", execemail);
                        if (landmark.equals("null")) {

                            getAllListMap.put("landmark", "No details");
                        } else {
                            getAllListMap.put("landmark", landmark);
                        }

                        String[] contact_array = contact_person.split(",");
                        if (contact_person.equals("null")) {
                            getAllListMap.put("contact_person", "No details");
                            getAllListMap.put("contact_name", "No details");
                            getAllListMap.put("contact_email", "No details");
                            getAllListMap.put("contact_no", "No details");
                        } else if (contact_array.length > 1) {

                            getAllListMap.put("contact_person", "Details");
                            getAllListMap.put("contact_name", contact_array[0]);
                            getAllListMap.put("contact_email", contact_array[1]);
                            getAllListMap.put("contact_no", contact_array[2]);
                        }
                        getAllsalesList.add(getAllListMap);


                    }


                } while (c.moveToNext());


            }

        }
        c.close();
        db.close();
        return getAllsalesList;
    }

    public ArrayList<HashMap<String, String>> getAllmarriagenewlist1(DBOperation db) {
        Cursor c = null;


        ArrayList<HashMap<String, String>> getAllsalesList = new ArrayList<>();
        SQLiteDatabase sqlite = db.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + DBManager.TableInfo.TABLE_MARRIAGE_APPOINTMENT + " t1, " + DBManager.TableInfo.TABLE_MARRIAGE + " t2 WHERE "
                + "t1." + DBManager.TableInfo.db_marriage_doc_id + " = " + "t2." + DBManager.TableInfo.db_marriage_doc_id + " AND t1."
                + DBManager.TableInfo.KEY_LOGIN_USER + "='" + Login.umailid + "'" + " ORDER BY " + "t1." + DBManager.TableInfo.db_marriage_starttime;


        c = sqlite.rawQuery(selectQuery, null);
        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        String curdate1 = df2.format(c1.getTime());
        Date date = null, date1 = null;
        String env_marriage1 = "";

        if (c != null) {

            if (c.moveToFirst()) {
                String count = c.getString(0);

                c.moveToFirst();
                do {

                    HashMap<String, String> getAllListMap = new HashMap<>();
                    String documentid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_doc_id));

                    String env_marriage = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_env));
                    String contact_person = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_contact_person));
                    String start_time = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_starttime));
                    String end_time = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_endtime));
                    String address = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_address));
                    String landmark = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_landmark));
                    String appfor = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_app_for));
                    String executionerid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_executionerid));
                    String appid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_appointment_id));
                    String latitude = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_latitude));
                    String longititude = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_longititude));
                    String distance = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_distance));
                    String transtype = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_transtype));
                    String acvr_amount = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_acvr_amount));
                    String acvr_id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_acvr_id));
                    String logical_del = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_logical_del));
                    String post_status = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_poststatus));
                    String franchise_id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_franchise_id));
                    String co_id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_co_id));
                    String id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_id));
                    String status = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_status));
                    String payverify = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_pay_verify));
                    String paypending = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_pay_pending));
                    String ex_id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_ex_id));
                    String payflag = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_pay_flag));
                    String app_flag = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_app_flag));
                    String created_at = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_created_at));
                    String statusid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_status_id));
                    String apptype = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_app_type));
                    String notify = c.getString(c.getColumnIndex(DBManager.TableInfo.notification));
                    String execemail = c.getString(c.getColumnIndex(DBManager.TableInfo.KEY_LOGIN_USER));
                    String marriage_type = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_type));

                    String syncstatusmarriage = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_syncstatus));
                    String acvrsyncstatusmarriage = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_acvrsyncstatus));


                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//
                    try {
                        date = dateFormat.parse(created_at);
                        date1 = dateFormat.parse(curdate1);

                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    if (env_marriage.substring(0, 1).toLowerCase().equals("m")) {


                        env_marriage1 = "m" + env_marriage.substring(2);
                        if (env_marriage1.equals(GenericMethods.rkeyfn)) {
                        }
                    }


                    if ((env_marriage.toLowerCase().equals(GenericMethods.rkeyfn) || env_marriage1.contains(GenericMethods.rkeyfn) || contact_person.toLowerCase().contains(GenericMethods.rkeyfn) || created_at.contains(GenericMethods.rkeyfn)) && (date.after(date1))) {

                        getAllListMap.put("contact_person", contact_person);
                        getAllListMap.put("starttime", start_time);
                        getAllListMap.put("endtime", end_time);
                        getAllListMap.put("executionerid", executionerid);
                        getAllListMap.put("app_for", appfor);
                        getAllListMap.put("document_id", documentid);
                        getAllListMap.put("created_at", created_at);
                        getAllListMap.put("appid", appid);
                        getAllListMap.put("env", env_marriage);
                        getAllListMap.put("address", address);
                        getAllListMap.put("landmark", landmark);
                        getAllListMap.put("appid", appid);
                        getAllListMap.put("latitude", latitude);
                        getAllListMap.put("longititude", longititude);
                        getAllListMap.put("distance", distance);
                        getAllListMap.put("transtype", transtype);
                        getAllListMap.put("acvr_amount", acvr_amount);
                        getAllListMap.put("acvr_id", acvr_id);
                        getAllListMap.put("logical_del", logical_del);
                        getAllListMap.put("post_status", post_status);
                        getAllListMap.put("franchise_id", franchise_id);
                        getAllListMap.put("co_id", co_id);
                        getAllListMap.put("status", status);
                        getAllListMap.put("id", id);
                        getAllListMap.put("payverify", payverify);
                        getAllListMap.put("paypending", paypending);
                        getAllListMap.put("ex_id", ex_id);
                        getAllListMap.put("payflag", payflag);
                        getAllListMap.put("appflag", app_flag);
                        getAllListMap.put("status_id", statusid);
                        getAllListMap.put("app_type", apptype);
                        getAllListMap.put("syncstatus", syncstatusmarriage);
                        getAllListMap.put("acvrsync", acvrsyncstatusmarriage);
                        getAllListMap.put("notify", notify);
                        getAllListMap.put("marriage_type", marriage_type);
                        getAllListMap.put("exec_email", execemail);
                        if (landmark.equals("null")) {

                            getAllListMap.put("landmark", "No details");
                        } else {
                            getAllListMap.put("landmark", landmark);
                        }

                        String[] contact_array = contact_person.split(",");
                        if (contact_person.equals("null")) {
                            getAllListMap.put("contact_person", "No details");
                            getAllListMap.put("contact_name", "No details");
                            getAllListMap.put("contact_email", "No details");
                            getAllListMap.put("contact_no", "No details");
                        } else if (contact_array.length > 1) {

                            getAllListMap.put("contact_person", "Details");
                            getAllListMap.put("contact_name", contact_array[0]);
                            getAllListMap.put("contact_email", contact_array[1]);
                            getAllListMap.put("contact_no", contact_array[2]);
                        }
                        getAllsalesList.add(getAllListMap);


                    }


                } while (c.moveToNext());


            }

        }
        c.close();
        db.close();
        return getAllsalesList;
    }

    public ArrayList<HashMap<String, String>> getAllmarriageAllList(DBOperation db) {
        Cursor c = null;


        ArrayList<HashMap<String, String>> getAllsalesList = new ArrayList<>();
        SQLiteDatabase sqlite = db.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + DBManager.TableInfo.TABLE_MARRIAGE_APPOINTMENT + " t1, " + DBManager.TableInfo.TABLE_MARRIAGE + " t2 WHERE "
                + "t1." + DBManager.TableInfo.db_marriage_doc_id + " = " + "t2." + DBManager.TableInfo.db_marriage_doc_id + " AND t1."
                + DBManager.TableInfo.KEY_LOGIN_USER + "='" + Login.umailid + "'" + " ORDER BY " + "t1." + DBManager.TableInfo.db_marriage_starttime;


        c = sqlite.rawQuery(selectQuery, null);
        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        String curdate1 = df2.format(c1.getTime());
        Date date = null, date1 = null;

        if (c != null) {

            if (c.moveToFirst()) {
                String count = c.getString(0);

                c.moveToFirst();
                do {

                    HashMap<String, String> getAllListMap = new HashMap<>();
                    String documentid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_doc_id));
                    String startdate = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_startdate));
                    String husbandname = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_husbandname));
                    String wifename = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_wifename));
                    String husbandcontact = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_husbandcontact));
                    String wifecontact = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_wifecontact));
                    String husbandbiometric = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_husbandbiometric));
                    String wifebiometric = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_wifebiometric));
                    String tokenno = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_tokenno));
                    String env_marriage = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_env));
                    String contact_person = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_contact_person));
                    String start_time = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_starttime));
                    String end_time = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_endtime));
                    String address = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_address));
                    String landmark = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_landmark));
                    String appfor = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_app_for));
                    String executionerid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_executionerid));
                    String appid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_appointment_id));
                    String latitude = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_latitude));
                    String longititude = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_longititude));
                    String distance = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_distance));
                    String transtype = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_transtype));
                    String acvr_amount = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_acvr_amount));
                    String acvr_id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_acvr_id));
                    String logical_del = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_logical_del));
                    String post_status = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_poststatus));
                    String franchise_id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_franchise_id));
                    String co_id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_co_id));
                    String id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_id));
                    String status = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_status));
                    String payverify = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_pay_verify));
                    String paypending = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_pay_pending));
                    String ex_id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_ex_id));
                    String payflag = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_pay_flag));
                    String app_flag = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_app_flag));
                    String created_at = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_created_at));
                    String statusid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_status_id));
                    String apptype = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_app_type));
                    String syncstatusmarriage = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_syncstatus));
                    String acvrsyncstatusmarriage = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_acvrsyncstatus));
                    String execemail = c.getString(c.getColumnIndex(DBManager.TableInfo.KEY_LOGIN_USER));
                    String marriage_type = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_type));

                    String notify = c.getString(c.getColumnIndex(DBManager.TableInfo.notification));

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//
                    try {
                        date = dateFormat.parse(startdate);
                        date1 = dateFormat.parse(curdate1);

                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                    if ((post_status.equals("1")) && (husbandbiometric.equals("1")) && (wifebiometric.equals("1"))) {

                        getAllListMap.put("contact_person", contact_person);
                        getAllListMap.put("starttime", start_time);
                        getAllListMap.put("endtime", end_time);
                        getAllListMap.put("startdate", startdate);
                        getAllListMap.put("husbandname", husbandname);
                        getAllListMap.put("wifename", wifename);
                        getAllListMap.put("husbandcontact", husbandcontact);
                        getAllListMap.put("wifecontact", wifecontact);
                        getAllListMap.put("husbandbiometric", husbandbiometric);
                        getAllListMap.put("wifebiometric", wifebiometric);
                        getAllListMap.put("tokenno", tokenno);
                        getAllListMap.put("executionerid", executionerid);
                        getAllListMap.put("app_for", appfor);
                        getAllListMap.put("document_id", documentid);
                        getAllListMap.put("created_at", created_at);
                        getAllListMap.put("appid", appid);
                        getAllListMap.put("env", env_marriage);
                        getAllListMap.put("address", address);
                        getAllListMap.put("landmark", landmark);
                        getAllListMap.put("appid", appid);
                        getAllListMap.put("latitude", latitude);
                        getAllListMap.put("longititude", longititude);
                        getAllListMap.put("distance", distance);
                        getAllListMap.put("transtype", transtype);
                        getAllListMap.put("acvr_amount", acvr_amount);
                        getAllListMap.put("acvr_id", acvr_id);
                        getAllListMap.put("logical_del", logical_del);
                        getAllListMap.put("post_status", post_status);
                        getAllListMap.put("franchise_id", franchise_id);
                        getAllListMap.put("co_id", co_id);
                        getAllListMap.put("status", status);
                        getAllListMap.put("id", id);
                        getAllListMap.put("payverify", payverify);
                        getAllListMap.put("paypending", paypending);
                        getAllListMap.put("ex_id", ex_id);
                        getAllListMap.put("payflag", payflag);
                        getAllListMap.put("appflag", app_flag);
                        getAllListMap.put("status_id", statusid);
                        getAllListMap.put("app_type", apptype);
                        getAllListMap.put("syncstatus", syncstatusmarriage);
                        getAllListMap.put("acvrsync", acvrsyncstatusmarriage);
                        getAllListMap.put("notify", notify);
                        getAllListMap.put("marriage_type", marriage_type);
                        getAllListMap.put("exec_email", execemail);
                        if (landmark.equals("null")) {

                            getAllListMap.put("landmark", "No details");
                        } else {
                            getAllListMap.put("landmark", landmark);
                        }

                        String[] contact_array = contact_person.split(",");
                        if (contact_person.equals("null")) {
                            getAllListMap.put("contact_person", "No details");
                            getAllListMap.put("contact_name", "No details");
                            getAllListMap.put("contact_email", "No details");
                            getAllListMap.put("contact_no", "No details");
                        } else if (contact_array.length > 1) {

                            getAllListMap.put("contact_person", "Details");
                            getAllListMap.put("contact_name", contact_array[0]);
                            getAllListMap.put("contact_email", contact_array[1]);
                            getAllListMap.put("contact_no", contact_array[2]);
                        }
                        getAllsalesList.add(getAllListMap);


                    }


                } while (c.moveToNext());


            }

        }
        c.close();
        db.close();
        return getAllsalesList;
    }

    public ArrayList<HashMap<String, String>> getAllmarriageAllList1(DBOperation db) {
        Cursor c = null;


        ArrayList<HashMap<String, String>> getAllsalesList = new ArrayList<>();
        SQLiteDatabase sqlite = db.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + DBManager.TableInfo.TABLE_MARRIAGE_APPOINTMENT + " t1, " + DBManager.TableInfo.TABLE_MARRIAGE + " t2 WHERE "
                + "t1." + DBManager.TableInfo.db_marriage_doc_id + " = " + "t2." + DBManager.TableInfo.db_marriage_doc_id + " AND t1."
                + DBManager.TableInfo.KEY_LOGIN_USER + "='" + Login.umailid + "'" + " ORDER BY " + "t1." + DBManager.TableInfo.db_marriage_starttime;


        c = sqlite.rawQuery(selectQuery, null);
        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        String curdate1 = df2.format(c1.getTime());
        Date date = null, date1 = null;
        String env_marriage1 = "";

        if (c != null) {

            if (c.moveToFirst()) {
                String count = c.getString(0);

                c.moveToFirst();
                do {

                    HashMap<String, String> getAllListMap = new HashMap<>();
                    String documentid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_doc_id));

                    String env_marriage = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_env));
                    String contact_person = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_contact_person));
                    String start_time = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_starttime));
                    String end_time = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_endtime));
                    String address = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_address));
                    String landmark = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_landmark));
                    String appfor = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_app_for));
                    String executionerid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_executionerid));
                    String appid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_appointment_id));
                    String latitude = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_latitude));
                    String longititude = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_longititude));
                    String distance = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_distance));
                    String transtype = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_transtype));
                    String acvr_amount = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_acvr_amount));
                    String acvr_id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_acvr_id));
                    String logical_del = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_logical_del));
                    String post_status = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_poststatus));
                    String franchise_id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_franchise_id));
                    String co_id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_co_id));
                    String id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_id));
                    String status = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_status));
                    String payverify = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_pay_verify));
                    String paypending = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_pay_pending));
                    String ex_id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_ex_id));
                    String payflag = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_pay_flag));
                    String app_flag = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_app_flag));
                    String created_at = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_created_at));
                    String statusid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_status_id));
                    String apptype = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_app_type));
                    String syncstatusmarriage = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_syncstatus));
                    String acvrsyncstatusmarriage = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_acvrsyncstatus));
                    String execemail = c.getString(c.getColumnIndex(DBManager.TableInfo.KEY_LOGIN_USER));
                    String marriage_type = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_type));

                    String notify = c.getString(c.getColumnIndex(DBManager.TableInfo.notification));

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//
                    try {
                        date = dateFormat.parse(created_at);
                        date1 = dateFormat.parse(curdate1);

                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    if (env_marriage.substring(0, 1).toLowerCase().equals("m")) {


                        env_marriage1 = "m" + env_marriage.substring(2);
                        if (env_marriage1.equals(GenericMethods.rkeyfn)) {

                        }
                    }


                    if ((env_marriage.toLowerCase().equals(GenericMethods.rkeyfn) || env_marriage1.contains(GenericMethods.rkeyfn) || contact_person.toLowerCase().contains(GenericMethods.rkeyfn) || created_at.contains(GenericMethods.rkeyfn)) && (post_status.equals("1"))) {

                        getAllListMap.put("contact_person", contact_person);
                        getAllListMap.put("starttime", start_time);
                        getAllListMap.put("endtime", end_time);
                        getAllListMap.put("executionerid", executionerid);
                        getAllListMap.put("app_for", appfor);
                        getAllListMap.put("document_id", documentid);
                        getAllListMap.put("created_at", created_at);
                        getAllListMap.put("appid", appid);
                        getAllListMap.put("env", env_marriage);
                        getAllListMap.put("address", address);
                        getAllListMap.put("landmark", landmark);
                        getAllListMap.put("appid", appid);
                        getAllListMap.put("latitude", latitude);
                        getAllListMap.put("longititude", longititude);
                        getAllListMap.put("distance", distance);
                        getAllListMap.put("marriage_type", marriage_type);
                        getAllListMap.put("transtype", transtype);
                        getAllListMap.put("acvr_amount", acvr_amount);
                        getAllListMap.put("acvr_id", acvr_id);
                        getAllListMap.put("logical_del", logical_del);
                        getAllListMap.put("post_status", post_status);
                        getAllListMap.put("franchise_id", franchise_id);
                        getAllListMap.put("co_id", co_id);
                        getAllListMap.put("status", status);
                        getAllListMap.put("id", id);
                        getAllListMap.put("payverify", payverify);
                        getAllListMap.put("paypending", paypending);
                        getAllListMap.put("ex_id", ex_id);
                        getAllListMap.put("payflag", payflag);
                        getAllListMap.put("appflag", app_flag);
                        getAllListMap.put("status_id", statusid);
                        getAllListMap.put("app_type", apptype);
                        getAllListMap.put("syncstatus", syncstatusmarriage);
                        getAllListMap.put("acvrsync", acvrsyncstatusmarriage);
                        getAllListMap.put("notify", notify);
                        getAllListMap.put("exec_email", execemail);
                        if (landmark.equals("null")) {

                            getAllListMap.put("landmark", "No details");
                        } else {
                            getAllListMap.put("landmark", landmark);
                        }

                        String[] contact_array = contact_person.split(",");
                        if (contact_person.equals("null")) {
                            getAllListMap.put("contact_person", "No details");
                            getAllListMap.put("contact_name", "No details");
                            getAllListMap.put("contact_email", "No details");
                            getAllListMap.put("contact_no", "No details");
                        } else if (contact_array.length > 1) {

                            getAllListMap.put("contact_person", "Details");
                            getAllListMap.put("contact_name", contact_array[0]);
                            getAllListMap.put("contact_email", contact_array[1]);
                            getAllListMap.put("contact_no", contact_array[2]);
                        }
                        getAllsalesList.add(getAllListMap);


                    }


                } while (c.moveToNext());


            }

        }
        c.close();
        db.close();
        return getAllsalesList;
    }


    public ArrayList<HashMap<String, String>> getexecutionerlist(DBOperation db) {
        ArrayList<HashMap<String, String>> listUsername = new ArrayList<>();

        try {

            String query = "select * from  " + DBManager.TableInfo.TABLE_EXECUTIONER + " where " + DBManager.TableInfo.KEY_LOGIN_USER + " = '" + GenericMethods.email + "'";

            SQLiteDatabase sqlite = db.getWritableDatabase();
            Cursor c = sqlite.rawQuery(query, null);

            while (c.moveToNext()) {
                HashMap<String, String> selectiondetails = new HashMap<>();
                selectiondetails.put("executioneremail", c.getString(c.getColumnIndex(DBManager.TableInfo.executioneremail)));
                selectiondetails.put("execpassword", c.getString(c.getColumnIndex(DBManager.TableInfo.execpassword)));
                selectiondetails.put("execemail", c.getString(c.getColumnIndex(DBManager.TableInfo.KEY_LOGIN_USER)));

                listUsername.add(selectiondetails);
            }

            closeCursor(c);

        } catch (Exception e) {
            // TODO: handle exception.
            e.printStackTrace();
        }
        db.close();
        return listUsername;
    }


    public ArrayList<HashMap<String, String>> getAllbiometriccancelledappointments(DBOperation db) {

        ArrayList<HashMap<String, String>> getAllList = new ArrayList<>();
        SQLiteDatabase sqlite = db.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + DBManager.TableInfo.TABLE_NAME_APPOINTMENT + " t1, " + DBManager.TableInfo.TABLE_NAME_DOCUMENT + " t2 WHERE "
                + "t1." + DBManager.TableInfo.DocumentId + " = " + "t2." + DBManager.TableInfo.DocumentId + " AND t1."
                + DBManager.TableInfo.Executioner_id + "='" + Login.umailid + "'" + " ORDER BY " + "t1." + DBManager.TableInfo.StartTime1;

        Cursor c = sqlite.rawQuery(selectQuery, null);
        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        String curdate1 = df2.format(c1.getTime());
        Date date, date1;
        if (c.moveToFirst()) {

            c.moveToFirst();
            do {
                HashMap<String, String> getAllListMap = new HashMap<>();
                String rtoken = c.getString(c.getColumnIndex("Rtoken"));
                String rkey = c.getString(c.getColumnIndex("Rkey"));
                String name = c.getString(c.getColumnIndex("Uname"));
                String mail = c.getString(c.getColumnIndex("Umail"));
                String contact = c.getString(c.getColumnIndex("Ucontact"));
                String app_status = c.getString(c.getColumnIndex(DBManager.TableInfo.App_status));

                String padd = c.getString(c.getColumnIndex("Paddress"));
                String oname = c.getString(c.getColumnIndex("Oname"));
                String ocontact = c.getString(c.getColumnIndex("Ocontact"));
                String omail = c.getString(c.getColumnIndex("Omail"));
                String oadd = c.getString(c.getColumnIndex("Oaddress"));
                String tname = c.getString(c.getColumnIndex("Tname"));
                String tcontact = c.getString(c.getColumnIndex("Tcontact"));
                String tmail = c.getString(c.getColumnIndex("Tmail"));
                String tadd = c.getString(c.getColumnIndex("Taddress"));
                String fmail = c.getString(c.getColumnIndex("uemail"));
                String tstatus = c.getString(c.getColumnIndex("status"));
                String syncstatus = c.getString(c.getColumnIndex(DBManager.TableInfo.SYNCSTATUS));
                String distance = c.getString(c.getColumnIndex(DBManager.TableInfo.DISTANCE));
                String amount = c.getString(c.getColumnIndex(DBManager.TableInfo.AMOUNT));
                String trasporttype = c.getString(c.getColumnIndex(DBManager.TableInfo.TRANSPORTTYPE));
                String apointmentfor = c.getString(c.getColumnIndex(DBManager.TableInfo.AppFor));
                String acvrreportstatus = c.getString(c.getColumnIndex(DBManager.TableInfo.SYNCSTATUSREPORT));
                String appdate = c.getString(c.getColumnIndex("Appdate"));
                String biocomp = c.getString(c.getColumnIndex("Biocomp"));
                String appdate1 = c.getString(c.getColumnIndex("Appdate1"));
                String biocom1 = c.getString(c.getColumnIndex("Biocomp1"));
                String regfromcomp = c.getString(c.getColumnIndex("reg_from_app"));
                String witness = c.getString(c.getColumnIndex("witness"));
                String shipadd = c.getString(c.getColumnIndex("ship_add"));
                String shipdiffadd = c.getString(c.getColumnIndex("ship_diff_add"));
                String sdate = c.getString(c.getColumnIndex("Sdate"));
                String stime1 = c.getString(c.getColumnIndex("Stime1"));
                String stime2 = c.getString(c.getColumnIndex("Stime2"));
                String docid = c.getString(c.getColumnIndex("Did"));
                String appadress = c.getString(c.getColumnIndex("Aaddress"));
                String appid = c.getString(c.getColumnIndex("Aid"));
                String exid = c.getString(c.getColumnIndex("Exid"));
                String appfor = c.getString(c.getColumnIndex("appfor"));
                String apptype = c.getString(c.getColumnIndex("app_type"));
                String landmark = c.getString(c.getColumnIndex("landmark"));
                String contactperson = c.getString(c.getColumnIndex(DBManager.TableInfo.contactperson));
                String post_status = c.getString(c.getColumnIndex("post_status"));
                String bio_status = c.getString(c.getColumnIndex(DBManager.TableInfo.att_status));
                String latitude = c.getString(c.getColumnIndex("latitude1"));
                String longitude = c.getString(c.getColumnIndex("longitude1"));
                String notify = c.getString(c.getColumnIndex(DBManager.TableInfo.notification));

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                try {
                    date = dateFormat.parse(sdate);
                    date1 = dateFormat.parse(curdate1);

                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


                if (app_status.equals("4")) {
                    getAllListMap.put("Rtoken", rtoken);
                    getAllListMap.put("rkey", rkey);
                    getAllListMap.put("name", name);
                    getAllListMap.put("mail", mail);
                    getAllListMap.put("contact", contact);
                    getAllListMap.put("padd", padd);
                    getAllListMap.put("oname", oname);
                    getAllListMap.put("ocontact", ocontact);
                    getAllListMap.put("omail", omail);
                    getAllListMap.put("oadd", oadd);
                    getAllListMap.put("tname", tname);
                    getAllListMap.put("tcontact", tcontact);
                    getAllListMap.put("tmail", tmail);
                    getAllListMap.put("tadd", tadd);
                    getAllListMap.put("fmail", fmail);
                    getAllListMap.put("tstatus", tstatus);
                    getAllListMap.put("biocomp", biocomp);
                    getAllListMap.put("biocom1", biocom1);
                    getAllListMap.put("appdate", appdate);
                    getAllListMap.put("appdate1", appdate1);
                    getAllListMap.put("regfromcomp", regfromcomp);
                    getAllListMap.put("witness", witness);
                    getAllListMap.put("shipadd", shipadd);
                    getAllListMap.put("shipdiffadd", shipdiffadd);
                    getAllListMap.put("sdate", sdate);
                    getAllListMap.put("stime1", stime1);
                    getAllListMap.put("stime2", stime2);
                    getAllListMap.put("docid", docid);
                    getAllListMap.put("appadress", appadress);
                    getAllListMap.put("latitude", latitude);
                    getAllListMap.put("longitude", longitude);
                    getAllListMap.put("appid", appid);
                    getAllListMap.put("appfor", appfor);
                    getAllListMap.put("exid", exid);
                    getAllListMap.put("syncstatus", syncstatus);
                    getAllListMap.put("distance", distance);
                    getAllListMap.put("amount", amount);
                    getAllListMap.put("appstatus", app_status);
                    getAllListMap.put("trasporttype", trasporttype);
                    getAllListMap.put("apointmentfor", apointmentfor);
                    getAllListMap.put("acvrreportstatus", acvrreportstatus);
                    getAllListMap.put("apptype", apptype);
                    getAllListMap.put("post_status", post_status);
                    getAllListMap.put("att_status", bio_status);
                    getAllListMap.put("notify", notify);
                    if (landmark.equals("null")) {

                        getAllListMap.put("landmark", "No details");
                    } else {
                        getAllListMap.put("landmark", landmark);
                    }
                    String[] contact_array = contactperson.split(",");
                    if (contactperson.equals("null")) {
                        getAllListMap.put("contact_person", "No details");
                        getAllListMap.put("contact_name", "No details");
                        getAllListMap.put("contact_email", "No details");
                        getAllListMap.put("contact_no", "No details");
                    } else if (contact_array.length > 1) {
                        getAllListMap.put("contact_person", "Details");

                        getAllListMap.put("contact_name", contact_array[0]);
                        getAllListMap.put("contact_email", contact_array[1]);
                        getAllListMap.put("contact_no", contact_array[2]);
                    }
                    getAllList.add(getAllListMap);

                }


            } while (c.moveToNext());

        }
        c.close();
        db.close();

        return getAllList;
    }

    public ArrayList<HashMap<String, String>> getAllbiometricautocancelledappointments(DBOperation db) {

        ArrayList<HashMap<String, String>> getAllList = new ArrayList<>();
        SQLiteDatabase sqlite = db.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + DBManager.TableInfo.TABLE_NAME_APPOINTMENT + " t1, " + DBManager.TableInfo.TABLE_NAME_DOCUMENT + " t2 WHERE "
                + "t1." + DBManager.TableInfo.DocumentId + " = " + "t2." + DBManager.TableInfo.DocumentId + " AND t1."
                + DBManager.TableInfo.Executioner_id + "='" + Login.umailid + "'" + " ORDER BY " + "t1." + DBManager.TableInfo.StartTime1;

        Cursor c = sqlite.rawQuery(selectQuery, null);
        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        String curdate1 = df2.format(c1.getTime());
        Date date, date1;
        if (c.moveToFirst()) {

            c.moveToFirst();
            do {
                HashMap<String, String> getAllListMap = new HashMap<>();
                String rtoken = c.getString(c.getColumnIndex("Rtoken"));
                String rkey = c.getString(c.getColumnIndex("Rkey"));
                String name = c.getString(c.getColumnIndex("Uname"));
                String mail = c.getString(c.getColumnIndex("Umail"));
                String contact = c.getString(c.getColumnIndex("Ucontact"));
                String app_status = c.getString(c.getColumnIndex(DBManager.TableInfo.App_status));
                String padd = c.getString(c.getColumnIndex("Paddress"));
                String oname = c.getString(c.getColumnIndex("Oname"));
                String ocontact = c.getString(c.getColumnIndex("Ocontact"));
                String omail = c.getString(c.getColumnIndex("Omail"));
                String oadd = c.getString(c.getColumnIndex("Oaddress"));
                String tname = c.getString(c.getColumnIndex("Tname"));
                String tcontact = c.getString(c.getColumnIndex("Tcontact"));
                String tmail = c.getString(c.getColumnIndex("Tmail"));
                String tadd = c.getString(c.getColumnIndex("Taddress"));
                String fmail = c.getString(c.getColumnIndex("uemail"));
                String tstatus = c.getString(c.getColumnIndex("status"));
                String syncstatus = c.getString(c.getColumnIndex(DBManager.TableInfo.SYNCSTATUS));
                String distance = c.getString(c.getColumnIndex(DBManager.TableInfo.DISTANCE));
                String amount = c.getString(c.getColumnIndex(DBManager.TableInfo.AMOUNT));
                String trasporttype = c.getString(c.getColumnIndex(DBManager.TableInfo.TRANSPORTTYPE));
                String apointmentfor = c.getString(c.getColumnIndex(DBManager.TableInfo.AppFor));
                String acvrreportstatus = c.getString(c.getColumnIndex(DBManager.TableInfo.SYNCSTATUSREPORT));
                String appdate = c.getString(c.getColumnIndex("Appdate"));
                String biocomp = c.getString(c.getColumnIndex("Biocomp"));
                String appdate1 = c.getString(c.getColumnIndex("Appdate1"));
                String biocom1 = c.getString(c.getColumnIndex("Biocomp1"));
                String regfromcomp = c.getString(c.getColumnIndex("reg_from_app"));
                String witness = c.getString(c.getColumnIndex("witness"));
                String shipadd = c.getString(c.getColumnIndex("ship_add"));
                String shipdiffadd = c.getString(c.getColumnIndex("ship_diff_add"));
                String sdate = c.getString(c.getColumnIndex("Sdate"));
                String stime1 = c.getString(c.getColumnIndex("Stime1"));
                String stime2 = c.getString(c.getColumnIndex("Stime2"));
                String docid = c.getString(c.getColumnIndex("Did"));
                String appadress = c.getString(c.getColumnIndex("Aaddress"));
                String appid = c.getString(c.getColumnIndex("Aid"));
                String exid = c.getString(c.getColumnIndex("Exid"));
                String appfor = c.getString(c.getColumnIndex("appfor"));
                String apptype = c.getString(c.getColumnIndex("app_type"));
                String landmark = c.getString(c.getColumnIndex("landmark"));
                String contactperson = c.getString(c.getColumnIndex(DBManager.TableInfo.contactperson));
                String post_status = c.getString(c.getColumnIndex("post_status"));
                String bio_status = c.getString(c.getColumnIndex(DBManager.TableInfo.att_status));
                String latitude = c.getString(c.getColumnIndex("latitude1"));
                String longitude = c.getString(c.getColumnIndex("longitude1"));
                String notify = c.getString(c.getColumnIndex(DBManager.TableInfo.notification));

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                try {
                    date = dateFormat.parse(sdate);
                    date1 = dateFormat.parse(curdate1);

                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


                if (app_status.equals("5")) {
                    getAllListMap.put("Rtoken", rtoken);
                    getAllListMap.put("rkey", rkey);
                    getAllListMap.put("name", name);
                    getAllListMap.put("mail", mail);
                    getAllListMap.put("contact", contact);
                    getAllListMap.put("padd", padd);
                    getAllListMap.put("oname", oname);
                    getAllListMap.put("ocontact", ocontact);
                    getAllListMap.put("omail", omail);
                    getAllListMap.put("oadd", oadd);
                    getAllListMap.put("tname", tname);
                    getAllListMap.put("tcontact", tcontact);
                    getAllListMap.put("tmail", tmail);
                    getAllListMap.put("tadd", tadd);
                    getAllListMap.put("fmail", fmail);
                    getAllListMap.put("tstatus", tstatus);
                    getAllListMap.put("biocomp", biocomp);
                    getAllListMap.put("biocom1", biocom1);
                    getAllListMap.put("appdate", appdate);
                    getAllListMap.put("appdate1", appdate1);
                    getAllListMap.put("regfromcomp", regfromcomp);
                    getAllListMap.put("witness", witness);
                    getAllListMap.put("shipadd", shipadd);
                    getAllListMap.put("shipdiffadd", shipdiffadd);
                    getAllListMap.put("sdate", sdate);
                    getAllListMap.put("stime1", stime1);
                    getAllListMap.put("stime2", stime2);
                    getAllListMap.put("docid", docid);
                    getAllListMap.put("appstatus", app_status);
                    getAllListMap.put("appadress", appadress);
                    getAllListMap.put("latitude", latitude);
                    getAllListMap.put("longitude", longitude);
                    getAllListMap.put("appid", appid);
                    getAllListMap.put("appfor", appfor);
                    getAllListMap.put("exid", exid);
                    getAllListMap.put("syncstatus", syncstatus);
                    getAllListMap.put("distance", distance);
                    getAllListMap.put("amount", amount);
                    getAllListMap.put("trasporttype", trasporttype);
                    getAllListMap.put("apointmentfor", apointmentfor);
                    getAllListMap.put("acvrreportstatus", acvrreportstatus);
                    getAllListMap.put("apptype", apptype);
                    getAllListMap.put("post_status", post_status);
                    getAllListMap.put("att_status", bio_status);
                    getAllListMap.put("notify", notify);
                    if (landmark.equals("null")) {

                        getAllListMap.put("landmark", "No details");
                    } else {
                        getAllListMap.put("landmark", landmark);
                    }
                    String[] contact_array = contactperson.split(",");
                    if (contactperson.equals("null")) {
                        getAllListMap.put("contact_person", "No details");
                        getAllListMap.put("contact_name", "No details");
                        getAllListMap.put("contact_email", "No details");
                        getAllListMap.put("contact_no", "No details");
                    } else if (contact_array.length > 1) {
                        getAllListMap.put("contact_person", "Details");

                        getAllListMap.put("contact_name", contact_array[0]);
                        getAllListMap.put("contact_email", contact_array[1]);
                        getAllListMap.put("contact_no", contact_array[2]);
                    }
                    getAllList.add(getAllListMap);

                }


            } while (c.moveToNext());

        }
        c.close();
        db.close();

        return getAllList;
    }

    public ArrayList<HashMap<String, String>> getAllsalescancelledappointments(DBOperation db) {
        Cursor c = null;


        ArrayList<HashMap<String, String>> getAllsalesList = new ArrayList<>();
        SQLiteDatabase sqlite = db.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + DBManager.TableInfo.TABLE_SALES_APPOINTMENT + " t1, " + DBManager.TableInfo.TABLE_SALES + " t2 WHERE "
                + "t1." + DBManager.TableInfo.db_sales_doc_id + " = " + "t2." + DBManager.TableInfo.db_sales_doc_id + " AND t1."
                + DBManager.TableInfo.KEY_LOGIN_USER + "='" + Login.umailid + "'" + " ORDER BY " + "t1." + DBManager.TableInfo.db_sales_starttime;


        c = sqlite.rawQuery(selectQuery, null);
        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        String curdate1 = df2.format(c1.getTime());

        Date date = null, date1 = null;


        if (c != null) {


            if (c.moveToFirst()) {


                c.moveToFirst();
                do {
                    HashMap<String, String> getAllListMap = new HashMap<>();
                    String contact_person = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_contactperson));
                    String starttime = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_starttime));
                    String endtime = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_endtime));
                    String executionerid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_executioner_id));
                    String app_for = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_app_for));
                    String app_status = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_app_status));

                    String documentid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_doc_id));
                    String created_at = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_created_at));
                    String appid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_appointment_id));
                    String app_ref = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_app_ref));
                    String app_city = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_app_city));
                    String u_flag = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_u_flag));
                    String create_dt = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_create_dt));
                    String approved_by = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_approved_by));
                    String created_by = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_createdby));
                    String latitude = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_latitude));
                    String longititude = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_longititude));
                    String logical_del = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_logical_del));
                    String post_status = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_poststatus));
                    String apptype = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_app_type));
                    String env = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_env));
                    String address = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_address));
                    String landmark = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_landmark));
                    String id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_id));
                    String syncstatus = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_syncstatus));
                    String distance = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_distance));
                    String amount = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_amount));
                    String transtype = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_transtype));
                    String acvr_id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_acvr_id));
                    String acvrsyncstatus = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_acvrsyncstatus));
                    String notify = c.getString(c.getColumnIndex(DBManager.TableInfo.notification));
                    String execemail = c.getString(c.getColumnIndex(DBManager.TableInfo.KEY_LOGIN_USER));

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                    try {
                        date = dateFormat.parse(created_at);
                        date1 = dateFormat.parse(curdate1);

                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                    if (app_status.equals("4")) {

                        getAllListMap.put("contact_person", contact_person);
                        getAllListMap.put("starttime", starttime);
                        getAllListMap.put("endtime", endtime);
                        getAllListMap.put("executionerid", executionerid);
                        getAllListMap.put("app_for", app_for);
                        getAllListMap.put("document_id", documentid);
                        getAllListMap.put("created_at", created_at);
                        getAllListMap.put("appid", appid);
                        getAllListMap.put("app_ref", app_ref);
                        getAllListMap.put("app_city", app_city);
                        getAllListMap.put("u_flag", u_flag);
                        getAllListMap.put("create_dt", create_dt);
                        getAllListMap.put("approvedby", approved_by);
                        getAllListMap.put("createdby", created_by);
                        getAllListMap.put("lati", latitude);
                        getAllListMap.put("longi", longititude);
                        getAllListMap.put("logical_del", logical_del);
                        getAllListMap.put("poststatus", post_status);
                        getAllListMap.put("apptype", apptype);
                        getAllListMap.put("env", env);
                        getAllListMap.put("id", id);
                        getAllListMap.put("appstatus", app_status);
                        getAllListMap.put("address", address);
                        getAllListMap.put("landmark", landmark);
                        getAllListMap.put("syncstatus", syncstatus);
                        getAllListMap.put("distance", distance);
                        getAllListMap.put("amount", amount);
                        getAllListMap.put("acvrsync", acvrsyncstatus);
                        getAllListMap.put("trans_type", transtype);
                        getAllListMap.put("acvr_id", acvr_id);
                        getAllListMap.put("notify", notify);
                        getAllListMap.put("exec_email", execemail);

                        if (landmark.equals("null")) {

                            getAllListMap.put("landmark", "No details");
                        } else {
                            getAllListMap.put("landmark", landmark);
                        }
                        String[] contact_array = contact_person.split(",");
                        if (contact_person.equals("null")) {
                            getAllListMap.put("contact_person", "No details");
                            getAllListMap.put("contact_name", "No details");
                            getAllListMap.put("contact_email", "No details");
                            getAllListMap.put("contact_no", "No details");
                        } else if (contact_array.length > 1) {

                            getAllListMap.put("contact_person", "Details");
                            getAllListMap.put("contact_name", contact_array[0]);
                            getAllListMap.put("contact_email", contact_array[1]);
                            getAllListMap.put("contact_no", contact_array[2]);
                        }
                        getAllsalesList.add(getAllListMap);

                    }


                } while (c.moveToNext());
            }
        }
        c.close();
        db.close();
        return getAllsalesList;
    }

    public ArrayList<HashMap<String, String>> getAllsalesautocancelledappointments(DBOperation db) {
        Cursor c = null;


        ArrayList<HashMap<String, String>> getAllsalesList = new ArrayList<>();
        SQLiteDatabase sqlite = db.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + DBManager.TableInfo.TABLE_SALES_APPOINTMENT + " t1, " + DBManager.TableInfo.TABLE_SALES + " t2 WHERE "
                + "t1." + DBManager.TableInfo.db_sales_doc_id + " = " + "t2." + DBManager.TableInfo.db_sales_doc_id + " AND t1."
                + DBManager.TableInfo.KEY_LOGIN_USER + "='" + Login.umailid + "'" + " ORDER BY " + "t1." + DBManager.TableInfo.db_sales_starttime;


        c = sqlite.rawQuery(selectQuery, null);
        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        String curdate1 = df2.format(c1.getTime());

        Date date = null, date1 = null;


        if (c != null) {


            if (c.moveToFirst()) {
                String count = c.getString(0);

                c.moveToFirst();
                do {
                    HashMap<String, String> getAllListMap = new HashMap<>();
                    String contact_person = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_contactperson));
                    String starttime = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_starttime));
                    String endtime = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_endtime));
                    String executionerid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_executioner_id));
                    String app_for = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_app_for));
                    String appstatus = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_app_status));
                    String documentid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_doc_id));
                    String created_at = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_created_at));
                    String appid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_appointment_id));
                    String app_ref = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_app_ref));
                    String app_city = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_app_city));
                    String u_flag = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_u_flag));
                    String create_dt = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_create_dt));
                    String approved_by = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_approved_by));
                    String created_by = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_createdby));
                    String latitude = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_latitude));
                    String longititude = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_longititude));
                    String logical_del = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_logical_del));
                    String post_status = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_poststatus));
                    String apptype = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_app_type));
                    String env = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_env));
                    String address = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_address));
                    String landmark = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_landmark));
                    String id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_id));
                    String syncstatus = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_syncstatus));
                    String distance = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_distance));
                    String amount = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_amount));
                    String transtype = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_transtype));
                    String acvr_id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_acvr_id));
                    String acvrsyncstatus = c.getString(c.getColumnIndex(DBManager.TableInfo.db_sales_acvrsyncstatus));
                    String notify = c.getString(c.getColumnIndex(DBManager.TableInfo.notification));
                    String execemail = c.getString(c.getColumnIndex(DBManager.TableInfo.KEY_LOGIN_USER));

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                    try {
                        date = dateFormat.parse(created_at);
                        date1 = dateFormat.parse(curdate1);

                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                    if (appstatus.equals("5")) {

                        getAllListMap.put("contact_person", contact_person);
                        getAllListMap.put("starttime", starttime);
                        getAllListMap.put("endtime", endtime);
                        getAllListMap.put("executionerid", executionerid);
                        getAllListMap.put("app_for", app_for);
                        getAllListMap.put("document_id", documentid);
                        getAllListMap.put("created_at", created_at);
                        getAllListMap.put("appid", appid);
                        getAllListMap.put("app_ref", app_ref);
                        getAllListMap.put("app_city", app_city);
                        getAllListMap.put("u_flag", u_flag);
                        getAllListMap.put("create_dt", create_dt);
                        getAllListMap.put("approvedby", approved_by);
                        getAllListMap.put("createdby", created_by);
                        getAllListMap.put("lati", latitude);
                        getAllListMap.put("longi", longititude);
                        getAllListMap.put("logical_del", logical_del);
                        getAllListMap.put("poststatus", post_status);
                        getAllListMap.put("apptype", apptype);
                        getAllListMap.put("appstatus", appstatus);
                        getAllListMap.put("env", env);
                        getAllListMap.put("id", id);
                        getAllListMap.put("address", address);
                        getAllListMap.put("landmark", landmark);
                        getAllListMap.put("syncstatus", syncstatus);
                        getAllListMap.put("distance", distance);
                        getAllListMap.put("amount", amount);
                        getAllListMap.put("acvrsync", acvrsyncstatus);
                        getAllListMap.put("trans_type", transtype);
                        getAllListMap.put("acvr_id", acvr_id);
                        getAllListMap.put("notify", notify);
                        getAllListMap.put("exec_email", execemail);

                        if (landmark.equals("null")) {

                            getAllListMap.put("landmark", "No details");
                        } else {
                            getAllListMap.put("landmark", landmark);
                        }
                        String[] contact_array = contact_person.split(",");
                        if (contact_person.equals("null")) {
                            getAllListMap.put("contact_person", "No details");
                            getAllListMap.put("contact_name", "No details");
                            getAllListMap.put("contact_email", "No details");
                            getAllListMap.put("contact_no", "No details");
                        } else if (contact_array.length > 1) {

                            getAllListMap.put("contact_person", "Details");
                            getAllListMap.put("contact_name", contact_array[0]);
                            getAllListMap.put("contact_email", contact_array[1]);
                            getAllListMap.put("contact_no", contact_array[2]);
                        }
                        getAllsalesList.add(getAllListMap);

                    }


                } while (c.moveToNext());
            }
        }
        c.close();
        db.close();
        return getAllsalesList;
    }

    public ArrayList<HashMap<String, String>> getAllmarriagecancelledappointments(DBOperation db) {
        Cursor c = null;


        ArrayList<HashMap<String, String>> getAllsalesList = new ArrayList<>();
        SQLiteDatabase sqlite = db.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + DBManager.TableInfo.TABLE_MARRIAGE_APPOINTMENT + " t1, " + DBManager.TableInfo.TABLE_MARRIAGE + " t2 WHERE "
                + "t1." + DBManager.TableInfo.db_marriage_doc_id + " = " + "t2." + DBManager.TableInfo.db_marriage_doc_id + " AND t1."
                + DBManager.TableInfo.KEY_LOGIN_USER + "='" + Login.umailid + "'" + " ORDER BY " + "t1." + DBManager.TableInfo.db_marriage_starttime;


        c = sqlite.rawQuery(selectQuery, null);
        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        String curdate1 = df2.format(c1.getTime());
        Date date = null, date1 = null;

        if (c != null) {

            if (c.moveToFirst()) {
                String count = c.getString(0);

                c.moveToFirst();
                do {

                    HashMap<String, String> getAllListMap = new HashMap<>();
                    String documentid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_doc_id));

                    String env_marriage = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_env));
                    String app_status = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_app_status));

                    String contact_person = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_contact_person));
                    String start_time = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_starttime));
                    String end_time = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_endtime));
                    String address = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_address));
                    String landmark = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_landmark));
                    String appfor = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_app_for));
                    String executionerid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_executionerid));
                    String appid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_appointment_id));
                    String latitude = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_latitude));
                    String longititude = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_longititude));
                    String distance = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_distance));
                    String transtype = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_transtype));
                    String acvr_amount = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_acvr_amount));
                    String acvr_id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_acvr_id));
                    String logical_del = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_logical_del));
                    String post_status = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_poststatus));
                    String franchise_id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_franchise_id));
                    String co_id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_co_id));
                    String id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_id));
                    String status = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_status));
                    String payverify = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_pay_verify));
                    String paypending = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_pay_pending));
                    String ex_id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_ex_id));
                    String payflag = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_pay_flag));
                    String app_flag = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_app_flag));
                    String created_at = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_created_at));
                    String statusid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_status_id));
                    String apptype = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_app_type));
                    String syncstatusmarriage = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_syncstatus));
                    String acvrsyncstatusmarriage = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_acvrsyncstatus));
                    String notify = c.getString(c.getColumnIndex(DBManager.TableInfo.notification));
                    String execemail = c.getString(c.getColumnIndex(DBManager.TableInfo.KEY_LOGIN_USER));


                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                    try {
                        date = dateFormat.parse(created_at);
                        date1 = dateFormat.parse(curdate1);

                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                    if (app_status.equals("4")) {

                        getAllListMap.put("contact_person", contact_person);
                        getAllListMap.put("starttime", start_time);
                        getAllListMap.put("endtime", end_time);
                        getAllListMap.put("executionerid", executionerid);
                        getAllListMap.put("app_for", appfor);
                        getAllListMap.put("document_id", documentid);
                        getAllListMap.put("created_at", created_at);
                        getAllListMap.put("appid", appid);
                        getAllListMap.put("appstatus", app_status);
                        getAllListMap.put("env", env_marriage);
                        getAllListMap.put("address", address);
                        getAllListMap.put("landmark", landmark);
                        getAllListMap.put("latitude", latitude);
                        getAllListMap.put("longititude", longititude);
                        getAllListMap.put("distance", distance);
                        getAllListMap.put("transtype", transtype);
                        getAllListMap.put("acvr_amount", acvr_amount);
                        getAllListMap.put("acvr_id", acvr_id);
                        getAllListMap.put("logical_del", logical_del);
                        getAllListMap.put("post_status", post_status);
                        getAllListMap.put("franchise_id", franchise_id);
                        getAllListMap.put("co_id", co_id);
                        getAllListMap.put("status", status);
                        getAllListMap.put("id", id);
                        getAllListMap.put("payverify", payverify);
                        getAllListMap.put("paypending", paypending);
                        getAllListMap.put("ex_id", ex_id);
                        getAllListMap.put("payflag", payflag);
                        getAllListMap.put("appflag", app_flag);
                        getAllListMap.put("status_id", statusid);
                        getAllListMap.put("app_type", apptype);
                        getAllListMap.put("notify", notify);
                        getAllListMap.put("exec_email", execemail);
                        getAllListMap.put("syncstatus", syncstatusmarriage);
                        getAllListMap.put("acvrsync", acvrsyncstatusmarriage);
                        if (landmark.equals("null")) {

                            getAllListMap.put("landmark", "No details");
                        } else {
                            getAllListMap.put("landmark", landmark);
                        }

                        String[] contact_array = contact_person.split(",");
                        if (contact_person.equals("null")) {
                            getAllListMap.put("contact_person", "No details");
                            getAllListMap.put("contact_name", "No details");
                            getAllListMap.put("contact_email", "No details");
                            getAllListMap.put("contact_no", "No details");
                        } else if (contact_array.length > 1) {

                            getAllListMap.put("contact_person", "Details");
                            getAllListMap.put("contact_name", contact_array[0]);
                            getAllListMap.put("contact_email", contact_array[1]);
                            getAllListMap.put("contact_no", contact_array[2]);
                        }
                        getAllsalesList.add(getAllListMap);


                    }


                } while (c.moveToNext());


            }

        }
        c.close();
        db.close();
        return getAllsalesList;
    }

    public ArrayList<HashMap<String, String>> getAllmarriageautocancelledappointments(DBOperation db) {
        Cursor c = null;


        ArrayList<HashMap<String, String>> getAllsalesList = new ArrayList<>();
        SQLiteDatabase sqlite = db.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + DBManager.TableInfo.TABLE_MARRIAGE_APPOINTMENT + " t1, " + DBManager.TableInfo.TABLE_MARRIAGE + " t2 WHERE "
                + "t1." + DBManager.TableInfo.db_marriage_doc_id + " = " + "t2." + DBManager.TableInfo.db_marriage_doc_id + " AND t1."
                + DBManager.TableInfo.KEY_LOGIN_USER + "='" + Login.umailid + "'" + " ORDER BY " + "t1." + DBManager.TableInfo.db_marriage_starttime;


        c = sqlite.rawQuery(selectQuery, null);
        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        String curdate1 = df2.format(c1.getTime());
        Date date = null, date1 = null;

        if (c != null) {

            if (c.moveToFirst()) {
                String count = c.getString(0);

                c.moveToFirst();
                do {

                    HashMap<String, String> getAllListMap = new HashMap<>();
                    String documentid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_doc_id));

                    String env_marriage = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_env));
                    String app_status = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_app_status));
                    System.out.println("app-status:" + app_status);
                    String contact_person = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_contact_person));
                    String start_time = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_starttime));
                    String end_time = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_endtime));
                    String address = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_address));
                    String landmark = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_landmark));
                    String appfor = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_app_for));
                    String executionerid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_executionerid));
                    String appid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_appointment_id));
                    String latitude = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_latitude));
                    String longititude = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_longititude));
                    String distance = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_distance));
                    String transtype = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_transtype));
                    String acvr_amount = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_acvr_amount));
                    String acvr_id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_acvr_id));
                    String logical_del = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_logical_del));
                    String post_status = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_poststatus));
                    String franchise_id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_franchise_id));
                    String co_id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_co_id));
                    String id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_id));
                    String status = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_status));
                    String payverify = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_pay_verify));
                    String paypending = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_pay_pending));
                    String ex_id = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_ex_id));
                    String payflag = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_pay_flag));
                    String app_flag = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_app_flag));
                    String created_at = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_created_at));
                    String statusid = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_status_id));
                    String apptype = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_app_type));
                    String syncstatusmarriage = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_syncstatus));
                    String acvrsyncstatusmarriage = c.getString(c.getColumnIndex(DBManager.TableInfo.db_marriage_acvrsyncstatus));
                    String notify = c.getString(c.getColumnIndex(DBManager.TableInfo.notification));
                    String execemail = c.getString(c.getColumnIndex(DBManager.TableInfo.KEY_LOGIN_USER));


                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                    try {
                        date = dateFormat.parse(created_at);
                        date1 = dateFormat.parse(curdate1);

                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                    if (app_status.equals("5")) {

                        getAllListMap.put("contact_person", contact_person);
                        getAllListMap.put("starttime", start_time);
                        getAllListMap.put("endtime", end_time);
                        getAllListMap.put("executionerid", executionerid);
                        getAllListMap.put("app_for", appfor);
                        getAllListMap.put("document_id", documentid);
                        getAllListMap.put("created_at", created_at);
                        getAllListMap.put("appid", appid);
                        getAllListMap.put("env", env_marriage);
                        getAllListMap.put("address", address);
                        getAllListMap.put("landmark", landmark);
                        getAllListMap.put("appstatus", app_status);
                        getAllListMap.put("latitude", latitude);
                        getAllListMap.put("longititude", longititude);
                        getAllListMap.put("distance", distance);
                        getAllListMap.put("transtype", transtype);
                        getAllListMap.put("acvr_amount", acvr_amount);
                        getAllListMap.put("acvr_id", acvr_id);
                        getAllListMap.put("logical_del", logical_del);
                        getAllListMap.put("post_status", post_status);
                        getAllListMap.put("franchise_id", franchise_id);
                        getAllListMap.put("co_id", co_id);
                        getAllListMap.put("status", status);
                        getAllListMap.put("id", id);
                        getAllListMap.put("payverify", payverify);
                        getAllListMap.put("paypending", paypending);
                        getAllListMap.put("ex_id", ex_id);
                        getAllListMap.put("payflag", payflag);
                        getAllListMap.put("appflag", app_flag);
                        getAllListMap.put("status_id", statusid);
                        getAllListMap.put("app_type", apptype);
                        getAllListMap.put("notify", notify);
                        getAllListMap.put("exec_email", execemail);
                        getAllListMap.put("syncstatus", syncstatusmarriage);
                        getAllListMap.put("acvrsync", acvrsyncstatusmarriage);
                        if (landmark.equals("null")) {

                            getAllListMap.put("landmark", "No details");
                        } else {
                            getAllListMap.put("landmark", landmark);
                        }

                        String[] contact_array = contact_person.split(",");
                        if (contact_person.equals("null")) {
                            getAllListMap.put("contact_person", "No details");
                            getAllListMap.put("contact_name", "No details");
                            getAllListMap.put("contact_email", "No details");
                            getAllListMap.put("contact_no", "No details");
                        } else if (contact_array.length > 1) {

                            getAllListMap.put("contact_person", "Details");
                            getAllListMap.put("contact_name", contact_array[0]);
                            getAllListMap.put("contact_email", contact_array[1]);
                            getAllListMap.put("contact_no", contact_array[2]);
                        }
                        getAllsalesList.add(getAllListMap);


                    }


                } while (c.moveToNext());


            }

        }
        c.close();
        db.close();
        return getAllsalesList;
    }
}