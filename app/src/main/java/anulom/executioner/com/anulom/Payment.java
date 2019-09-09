//package anulom.executioner5.com3.anulom;
//
//import android.support.v7.app.AlertDialog;
//import android.content.ContentValues;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.os.Bundle;
//import android.preference.PreferenceManager;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.CompoundButton;
//import android.widget.RadioButton;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import java.util.Timer;
//import java.util.TimerTask;
//
//import anulom.executioner5.com3.anulom.database.DBManager;
//import anulom.executioner5.com3.anulom.database.DBOperation;
//import anulom.executioner5.com3.anulom.services.Paymentlinkservice;
//import anulom.executioner5.com3.anulom.services.SendPaymentService;
//import anulom.executioner5.com3.anulom.services.postpendingwitness;
//
//import static anulom.executioner5.com3.anulom.database.DBManager.TableInfo.TABLE_PAYMENT_LINK;
//import static anulom.executioner5.com3.anulom.database.DBManager.TableInfo.TABLE_UPDATE_PENDING_WITNESS;
//import static com.google.android.gms.internal.zzir.runOnUiThread;
//
//
//public class Payment extends AppCompatActivity {
//    RadioButton link,cash;
//    Button update;
//    String rkey,documentid,ID2,content,poststatus,startdate,appid,amount;
//    DBOperation db;
//    private String username2 = "";
//    Timer singleTask = new Timer();
//    public void onCreate(Bundle SavedInstanceState) {
//        super.onCreate(SavedInstanceState);
//        db = new DBOperation(getApplicationContext());
//        setContentView(R.layout.paymentinfo1);
//        link=findViewById(R.id.radioButton);
//        cash=findViewById(R.id.radioButton2);
//        update=findViewById(R.id.button9);
//        final Timer singleTask = new Timer();
//        rkey=getIntent().getStringExtra("ReportKey");
//        documentid=getIntent().getStringExtra("DocumentId");
//        appid=getIntent().getStringExtra ("appointmentid");
//        content=getIntent().getStringExtra("content");
//        poststatus=getIntent().getStringExtra ("post_status");
//        amount=getIntent ().getStringExtra ("amount");
//        startdate=getIntent().getStringExtra ("StartDate");
//        final SharedPreferences usermail = PreferenceManager.getDefaultSharedPreferences(this);
//        username2 = usermail.getString("username", "");
//        String password2 = usermail.getString("password", "");
//        cash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(cash.isChecked()){
//                    Intent i=new Intent(Payment.this,Pay.class);
//                    i.putExtra("DocumentId",documentid);
//                    i.putExtra("ReportKey",rkey);
//                    i.putExtra ("appointmentid",appid);
//                    i.putExtra("content", content);
//                    i.putExtra("StartDate", startdate);
//                    i.putExtra("post_status",poststatus);
//                    i.putExtra ("amount",amount);
//                    i.putExtra ("paymentflag","paymentlink");
//                    startActivity(i);
//                }
//            }
//        });
//
//        update.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(link.isChecked()){
//                    SQLiteDatabase db3 = db.getWritableDatabase();
//                    ContentValues values3 = new ContentValues();
//                    values3.put(DBManager.TableInfo.KEYID, ID2);
//                    values3.put(DBManager.TableInfo.docid_payment, documentid);
//                    values3.put(DBManager.TableInfo.exec_email_payment, username2);
//                    values3.put(DBManager.TableInfo.KEY_LOGIN_USER, username2);
//                    String condition3 = DBManager.TableInfo.docid_payment + " =?";
//                    Cursor cursor3 = db3.query(TABLE_PAYMENT_LINK, null, condition3, new String[]{documentid}, null, null, null);
//                    long status3 = db3.insert(TABLE_PAYMENT_LINK, null, values3);
//                    cursor3.close();
//                    db3.close();
//                    System.out.println("docid"+documentid+"exec_email:"+username2);
//
//                    if (GenericMethods.isConnected(getApplicationContext())) {
//                        if(GenericMethods.isOnline()) {
//                            Intent i3 = new Intent(Payment.this, Paymentlinkservice.class);
//                            startService(i3);
//
//                        }else{
//                            Toast.makeText(Payment.this, " Payment Link Saved Offline,App is waiting for active network connection", Toast.LENGTH_LONG).show();
//
//                        }
//                    } else {
//                        Toast.makeText(Payment.this, " Payment Link Details saved offline!!", Toast.LENGTH_LONG).show();
//
//                   }
//                }
//                else{
//                    Toast.makeText(Payment.this, "Choose Payment mode and then update", Toast.LENGTH_LONG).show();
//                }
//
//                Payment.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
////                        Payment.this.singleTask.schedule(new TimerTask () {
////                            @Override
////                            public void run() {
//                             AlertDialog alertbox = new AlertDialog.Builder(Payment.this).setMessage("Payment Link Sent!!")
//                                      .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
//                                          public void onClick(DialogInterface arg0, int arg1) {
//                                              arg0.dismiss();
//
//                                          }
//
//                                      }).setNegativeButton("OK", new DialogInterface.OnClickListener() {
//                                          public void onClick(DialogInterface arg0, int arg1) {
//
//                                              Intent i=new Intent(Payment.this,newstatusinfo.class);
//                                              i.putExtra("document_id",documentid);
//                                              i.putExtra("rkey", rkey);
//                                              i.putExtra("appointment_id", appid);
//                                              i.putExtra("content", content);
//                                              i.putExtra("StartDate", startdate);
//                                              i.putExtra("post_status",poststatus);
//                                              i.putExtra ("amount",amount);
//                                              i.putExtra ("paymentflag","paymentlink");
//                                              startActivity(i);
//                                          }
//                                      }).show();
//
////                            }
////                        }, 2000);
//                    }
//                });
//
//
//            }
//        });
//    }
//}