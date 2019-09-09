package anulom.executioner.com.anulom;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;

import anulom.executioner.com.anulom.database.DBOperation;
import anulom.executioner.com.anulom.services.AppointmentstatusPost;
import anulom.executioner.com.anulom.services.InternalExternalWitness;
import anulom.executioner.com.anulom.services.Marriageacvrservice;
import anulom.executioner.com.anulom.services.Marriagecommentservice;
import anulom.executioner.com.anulom.services.Posttask;
import anulom.executioner.com.anulom.services.Salesacvrservice;
import anulom.executioner.com.anulom.services.Salescommentservice;
import anulom.executioner.com.anulom.services.SendCommentService;
import anulom.executioner.com.anulom.services.SendPaymentService;
import anulom.executioner.com.anulom.services.SendReportService;
import anulom.executioner.com.anulom.services.SendmultiPartyReport;
import anulom.executioner.com.anulom.services.StatusReportService;
import anulom.executioner.com.anulom.services.call;
import anulom.executioner.com.anulom.services.fetchactualtime;
import anulom.executioner.com.anulom.services.postappointmentbooking;
import anulom.executioner.com.anulom.services.reassignpost;
import anulom.executioner.com.anulom.services.rescheduledetails;


public class MyDialog extends Activity {
    DBOperation db;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBOperation(this);
        progressDialog = new ProgressDialog(MyDialog.this);

        AlertDialog alertbox = new AlertDialog.Builder(MyDialog.this).setMessage("Your App is not Updated!!Please Update Here!!" +
                "NOTE:Unless or Until you Update your app,You cannot use it Further!!")
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        progressDialog.setMessage("Syncing offline Data,Please have active internet connection to update");
                        progressDialog.setIndeterminate(false);
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        ArrayList<HashMap<String, String>> listofcommment = db.getcomment(db);
                        ArrayList<HashMap<String, String>> acvrreportlist = db.getAcvrReport(db);
                        ArrayList<HashMap<String, String>> statusreportlist = db.getSyncStatusReport(db);

                        if (listofcommment.size() > 0) {
                            Intent intent = new Intent(MyDialog.this, SendCommentService.class);
                            startService(intent);

                        }
                        if (acvrreportlist.size() > 0) {
                            Intent intent = new Intent(MyDialog.this, SendReportService.class);
                            startService(intent);

                        }
                        if (statusreportlist.size() > 0) {
                            Intent intent = new Intent(MyDialog.this, StatusReportService.class);
                            startService(intent);

                        }
                        ArrayList<HashMap<String, String>> paymentlist = db.getPaymentReport(db);

                        if (paymentlist.size() > 0) {
                            Intent intent = new Intent(MyDialog.this, SendPaymentService.class);
                            startService(intent);

                        }

                        ArrayList<HashMap<String, String>> partieslist = db.getPartypost(db);

                        if (partieslist.size() > 0) {
                            Intent intent = new Intent(MyDialog.this, SendmultiPartyReport.class);
                            startService(intent);

                        }

                        ArrayList<HashMap<String, String>> appointmentlist = db.postappointment(db);

                        if (appointmentlist.size() > 0) {
                            Intent intent = new Intent(MyDialog.this, postappointmentbooking.class);
                            startService(intent);

                        }

                        ArrayList<HashMap<String, String>> calllist = db.calldetails(db);

                        if (calllist.size() > 0) {
                            Intent intent = new Intent(MyDialog.this, call.class);
                            startService(intent);

                        }

                        ArrayList<HashMap<String, String>> reschedulelist = db.getrescheduledetails(db);

                        if (reschedulelist.size() > 0) {
                            Intent intent = new Intent(MyDialog.this, rescheduledetails.class);
                            startService(intent);

                        }

                        ArrayList<HashMap<String, String>> fetchactualtimelist = db.getactualtime(db);

                        if (fetchactualtimelist.size() > 0) {
                            Intent intent = new Intent(MyDialog.this, fetchactualtime.class);
                            startService(intent);

                        }

                        ArrayList<HashMap<String, String>> appointmentstatuspost = db.getmultipartycheck(db);

                        if (appointmentstatuspost.size() > 0) {
                            Intent intent = new Intent(MyDialog.this, AppointmentstatusPost.class);
                            startService(intent);

                        }

                        ArrayList<HashMap<String, String>> internalwitness = db.getinternalexternalpost(db);

                        if (internalwitness.size() > 0) {
                            Intent intent = new Intent(MyDialog.this, InternalExternalWitness.class);
                            startService(intent);

                        }
                        ArrayList<HashMap<String, String>> salesacvr = db.getsalesAcvrReport(db);

                        if (salesacvr.size() > 0) {
                            Intent intent = new Intent(MyDialog.this, Salesacvrservice.class);
                            startService(intent);

                        }

                        ArrayList<HashMap<String, String>> salescomm = db.getsalescomment(db);

                        if (salescomm.size() > 0) {
                            Intent intent = new Intent(MyDialog.this, Salescommentservice.class);
                            startService(intent);

                        }
                        ArrayList<HashMap<String, String>> marriagecomm = db.getmarriagecomment(db);

                        if (marriagecomm.size() > 0) {
                            Intent intent = new Intent(MyDialog.this, Marriagecommentservice.class);
                            startService(intent);

                        }


                        ArrayList<HashMap<String, String>> marriageacvr = db.getmarriageAcvrReport(db);

                        if (marriageacvr.size() > 0) {
                            Intent intent = new Intent(MyDialog.this, Marriageacvrservice.class);
                            startService(intent);

                        }
                        ArrayList<HashMap<String, String>> posttasklist = db.getposttaskdetails(db);

                        if (posttasklist.size() > 0) {
                            Intent intent = new Intent(MyDialog.this, Posttask.class);
                            startService(intent);

                        }

                        ArrayList<HashMap<String, String>> reassignlist = db.getreassign(db);

                        if (reassignlist.size() > 0) {
                            Intent intent = new Intent(MyDialog.this, reassignpost.class);
                            startService(intent);

                        }

                         progressDialog.dismiss();


                           Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.addCategory(Intent.CATEGORY_BROWSABLE);
                            intent.setData(Uri.parse("https://s3-us-west-2.amazonaws.com/anulom.release/executionerapp/app-debug.apk"));
                            startActivity(intent);



                    }

                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent startMain = new Intent(Intent.ACTION_MAIN);
                        startMain.addCategory(Intent.CATEGORY_HOME);
                        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(startMain);
                    }
                }).create();
        alertbox.show();

    }
}