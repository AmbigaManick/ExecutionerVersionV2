package anulom.executioner.com.anulom.services;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import anulom.executioner.com.anulom.GenericMethods;
import anulom.executioner.com.anulom.Login;
import anulom.executioner.com.anulom.NextActivity;
import anulom.executioner.com.anulom.database.DBOperation;

import static com.google.android.gms.internal.zzir.runOnUiThread;


public class GetCommentService extends Service {


    public String umail = Login.umailid;
    GenericMethods mResponse;
    private String Url = "", commentdate = "";
    DBOperation db;
    ProgressDialog pDialog1;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public int onStartCommand(Intent intent, int flags, int startId) {

        mResponse = new GenericMethods();
         db = new DBOperation(GetCommentService.this);
        Url = GenericMethods.MAIN_URL1 + "/communicator/api/v1/comment_data/fetch_comment";
        System.out.println("values from get comment service file");
        new GetCommentTask().execute();
        return START_STICKY;


    }

    private class GetCommentTask extends AsyncTask<Void, Void, Void> {
        String strResponsePost = "";
        private JSONArray ja = null;


        @Override
        protected Void doInBackground(Void... params) {
            try {




                List<String> reportleylist = db.getReportkey();

                String strJS = createServiceJson(reportleylist);

                strResponsePost = mResponse.doPostRequest(Url, strJS);
                JSONObject jsonObj = new JSONObject(strResponsePost);
                // Getting JSON Array node
                ja = jsonObj.getJSONArray(GenericMethods.TAG_NAME1);

                if (ja.length() > 0) {
                    db.deleteSyncComment(db);
                }
                for (int j = 0; j < ja.length(); j++) {
                    JSONObject c = ja.getJSONObject(j);
                    String env_id = c.getString(GenericMethods.TAG_ENV);
                    String cust_comments = c.getString(GenericMethods.TAG_COMMENTS);
                    String user = c.getString(GenericMethods.TAG_CUSER);
                    String owner = c.getString(GenericMethods.TAG_COWNER);
                    String cid = c.getString(GenericMethods.TAG_CID);
                    String isdone = c.getString(GenericMethods.TAG_C_IS_DONE);
                    String datecomment = c.getString(GenericMethods.TAG_CDATE);
                    String reminder_dt = c.getString("reminder_dt");
                    String commenttype = c.getString(GenericMethods.TAG_CMTTYPE);
                    String commentarea = c.getString(GenericMethods.TAG_CMTAREA);
//                    System.out.println("comments" + commenttype);

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");

                    java.util.Date cdate;
                    try {
                        cdate = sdf.parse(datecomment);

                        commentdate = output.format(cdate);


                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    String SyncStatus = "SYNC";
                    if (env_id.contains("O_")) {
                        db.InsertRecord3(db, env_id, cust_comments, user, owner, cid, isdone, commentdate, SyncStatus, reminder_dt, "Sales", commenttype, commentarea,"");

                    } else if (env_id.contains("M_")) {
                        db.InsertRecord3(db, env_id, cust_comments, user, owner, cid, isdone, commentdate, SyncStatus, reminder_dt, "Marriage", commenttype, commentarea,"");

                    } else {
                        db.InsertRecord3(db, env_id, cust_comments, user, owner, cid, isdone, commentdate, SyncStatus, reminder_dt, "Biometric", commenttype, commentarea,"");

                    }


                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            super.onPostExecute(result);


            if(GenericMethods.splashscreen=="login"){

                if(Login.pDialog.isShowing()||Login.pDialog!=null){
                    Login.pDialog.dismiss();
                }

                if( db.getbiometricpendingwork(db).size() > 0 || db.getAllmarriageOlderList(db).size() > 0){
                    GenericMethods.pendingworks="true";
                    GenericMethods.pendingdisplay="true";
                    GenericMethods.nextactivity="false";
                    Intent intent = new Intent(GetCommentService.this, NextActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }
                else{
                    GenericMethods.nextactivity="true";
                    GenericMethods.pendingworks="false";
                    GenericMethods.pendingdisplay="false";
                    Intent intent = new Intent(GetCommentService.this, NextActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }


            }else if(GenericMethods.splashscreen=="next"){
                if(NextActivity.pDialog.isShowing()||NextActivity.pDialog!=null){
                    NextActivity.pDialog.dismiss();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (GenericMethods.isOnline()) {

                            Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                            startActivity(i);
                            GenericMethods.pendingdisplay="true";
                            GenericMethods.nextactivity="false";
                            Toast.makeText(GetCommentService.this, "Biometric and Comment Refresh Completed", Toast.LENGTH_LONG).show();

                        }

                        else{
                            Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                            startActivity(i);
                            Toast.makeText(GetCommentService.this, "Network Unavailable!!Refresh is not Completed", Toast.LENGTH_LONG).show();

                        }


                    }
                });



            }

stopSelf();

        }
    }


    private String createServiceJson(List<String> reportleylist) {
        try {
            JSONObject serviceJsn = new JSONObject();
            JSONObject obj = new JSONObject();
            try {

                obj.put("key", reportleylist);
                Log.d("TAG", "Main json:" + reportleylist);

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            serviceJsn.put("document", obj);
            JSONObject jsonauto_token = new JSONObject();
            jsonauto_token.put("token", "DOtUBMhv5pk51tl0D37uBcezq85cXNN7hZQ7");
            serviceJsn.put("auth_token", jsonauto_token);
            return serviceJsn.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
