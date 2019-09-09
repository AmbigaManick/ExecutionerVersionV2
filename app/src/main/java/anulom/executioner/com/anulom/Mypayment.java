package anulom.executioner.com.anulom;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import anulom.executioner.com.anulom.database.DBOperation;


public class Mypayment extends Activity {
    DBOperation db;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBOperation(this);
        progressDialog = new ProgressDialog(Mypayment.this);

        AlertDialog alertbox = new AlertDialog.Builder(Mypayment.this).setMessage("Payment Link Sent!!")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        Intent intent = new Intent(Mypayment.this, newstatusinfo.class);
//                        i1.putExtra("document_id", getAlldataList.get(position).get("docid"));
//                        i1.putExtra("appointment_id", getAlldataList.get(position).get("appid"));
//                        i1.putExtra("rkey", getAlldataList.get(position).get("rkey"));
//                        i1.putExtra("flag", flag);
//                        i1.putExtra("content", content1);
//                        i1.putExtra("StartDate", getAlldataList.get(position).get("sdate"));
//                        i1.putExtra("amount", payamount);
//                        i1.putExtra("time",localTime);
//                        i1.putExtra("post_status", getAlldataList.get(position).get("post_status"))
                        startService(intent);

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