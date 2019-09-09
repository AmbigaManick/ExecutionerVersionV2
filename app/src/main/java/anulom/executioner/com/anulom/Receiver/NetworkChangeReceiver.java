package anulom.executioner.com.anulom.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;


import anulom.executioner.com.anulom.GenericMethods;

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


public class NetworkChangeReceiver extends BroadcastReceiver {
    int count = 0;

    @Override

    public void onReceive(Context context, Intent intent) {

        System.out.println("entered network change reciver");


        GenericMethods.connection = "true";
        SharedPreferences usermail = PreferenceManager.getDefaultSharedPreferences(context);
        String username2 = usermail.getString("username", "");
        System.out.println("entered network change reciver1");

        if (!username2.isEmpty()) {

            if (GenericMethods.isConnected(context)) {


                if (GenericMethods.isOnline()) {


                    if (GenericMethods.ServerDown()) {


                        Intent SendCommentService1 = new Intent(context, SendCommentService.class);
                        SendCommentService1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startService(SendCommentService1);

                        Intent SendReportService1 = new Intent(context, SendReportService.class);
                        SendReportService1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startService(SendReportService1);


                        Intent StatusReportService1 = new Intent(context, StatusReportService.class);
                        StatusReportService1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startService(StatusReportService1);


                        Intent SendPaymentService1 = new Intent(context, SendPaymentService.class);
                        SendPaymentService1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startService(SendPaymentService1);

                        Intent SendPartyService1 = new Intent(context, SendmultiPartyReport.class);
                        SendPartyService1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startService(SendPartyService1);


                        Intent appointment = new Intent(context, postappointmentbooking.class);
                        appointment.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startService(appointment);

                        Intent callservice = new Intent(context, call.class);
                        callservice.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startService(callservice);

                        Intent resche = new Intent(context, rescheduledetails.class);
                        resche.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startService(resche);

                        Intent actualtime = new Intent(context, fetchactualtime.class);
                        actualtime.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startService(actualtime);

                        Intent task = new Intent(context, Posttask.class);
                        task.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startService(task);


                        Intent reassign = new Intent(context, reassignpost.class);
                        reassign.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startService(reassign);

                        Intent appstatus = new Intent(context, AppointmentstatusPost.class);
                        appstatus.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startService(appstatus);

                        Intent witness = new Intent(context, InternalExternalWitness.class);
                        witness.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startService(witness);

                        Intent marriageacvr1 = new Intent(context, Marriageacvrservice.class);
                        marriageacvr1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startService(marriageacvr1);

                        Intent salesacvr1 = new Intent(context, Salesacvrservice.class);
                        salesacvr1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startService(salesacvr1);

                        Intent marriagecomm1 = new Intent(context, Marriagecommentservice.class);
                        marriagecomm1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startService(marriagecomm1);

                        Intent salescomm1 = new Intent(context, Salescommentservice.class);
                        salescomm1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startService(salescomm1);
                    } else {

                        Toast.makeText(context, "Not Able to Connect One platform,Please contact RnD team", Toast.LENGTH_LONG).show();

                    }


                } else {


                    Toast.makeText(context, "Your phone is not connected to the internet", Toast.LENGTH_LONG).show();

                }

            }

        }
    }
}
