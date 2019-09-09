package anulom.executioner.com.anulom.services;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import anulom.executioner.com.anulom.GenericMethods;
import anulom.executioner.com.anulom.database.DBManager;
import anulom.executioner.com.anulom.database.DBOperation;
import anulom.executioner.com.anulom.fragment.CompletedDetails;
import anulom.executioner.com.anulom.fragment.NewDetails;
import anulom.executioner.com.anulom.fragment.OlderDetails;
import anulom.executioner.com.anulom.fragment.TodayDetails;
import anulom.executioner.com.anulom.fragment.marriagecompleted;
import anulom.executioner.com.anulom.fragment.marriagenew;
import anulom.executioner.com.anulom.fragment.marriageolder;
import anulom.executioner.com.anulom.fragment.marriagetoday;

import static com.google.android.gms.internal.zzir.runOnUiThread;


public class SendCommentService extends Service {

    GenericMethods mResponse;
    String URL = GenericMethods.MAIN_URL1 + "/communicator/api/v1/comment_data/add_comment";
    private SharedPreferences pref;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {



        if (GenericMethods.isConnected(getApplicationContext())) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                new SendComment()
                        .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

            } else {
                new SendComment().execute();
            }
        }
        return START_STICKY;


    }



    private class SendComment extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {
            System.out.println("Entered comment background process");

            String strResponsePost = "", strCommenttype = "";
            String ownerTypeRole = "";
            // TODO Auto-generated method stub


            try {

                DBOperation db = new DBOperation(getBaseContext());
                ArrayList<HashMap<String, String>> listofcommment = db.getcomment(db);

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String password2 = sharedPreferences.getString("password", "");
                String username = sharedPreferences.getString("username", "");
                mResponse = new GenericMethods();
                for (int i = 0; i < listofcommment.size(); i++) {
                    System.out.println("list comment size:"+listofcommment.size());

                    String strOwner = listofcommment.get(i).get("Commentowner");
                    String strCustomerComment = listofcommment.get(i).get("CustomerComment");
                    String strCommentUser = listofcommment.get(i).get("CommentUser");
                    String strDocId = listofcommment.get(i).get("DocId");
                    String strCommentID = listofcommment.get(i).get("ComentID");
                    strCommenttype = listofcommment.get(i).get("Commenttype");
                    String Commentcontact=listofcommment.get(i).get("Commentcontact");
                    String strCommentarea = listofcommment.get(i).get("Commentarea");
//                    String strCommentdate = listofcommment.get(i).get("CommentDate");
                    String strReminderDate = listofcommment.get(i).get("ReminderDate");
                    System.out.println("commenttype:"+strCommenttype+"cooomenntarea:"+strCommentarea);


                    if (strOwner.equals("")) {
                        ownerTypeRole = "";
                    } else {
                        ArrayList<HashMap<String, String>> userlist = db.getOwnerType(db, strOwner);
                        //System.out.println("size:"+userlist.size());
//                        for(int j=0;j<userlist.size();j++){
//
//                            System.out.println("role:i:"+j+db.getOwnerType(db,strOwner).get(j).get("role2"));
//                        }
                        ownerTypeRole = userlist.get(0).get("role2");

                    }

                    ArrayList<HashMap<String, String>> userlist1 = db.getExecutionerType(db, username);
                    // System.out.println("size1:"+userlist1.size());
//
// for(int j=0;j<userlist1.size();j++){
//
//
//                        System.out.println("role2:i:"+j+db.getExecutionerType(db,username).get(j).get("role2"));
//
//                    }
                    String executionerTypeRole = userlist1.get(0).get(DBManager.TableInfo.Role);

                    ArrayList<HashMap<String, String>> userlist2 = db.getDocumentUser(db, strDocId);
                    String document_user = null;

                    if (userlist2 != null && userlist2.size() > 0) {

                        document_user = userlist2.get(0).get(DBManager.TableInfo.OwnerEmail);

                    }


                    String strJS = createServiceJson(strDocId, strCustomerComment, document_user,
                            strCommentUser, strReminderDate, executionerTypeRole, strOwner, ownerTypeRole,Commentcontact, strCommenttype, strCommentarea);

                    System.out.println("comment json:"+strJS);
                    strResponsePost = mResponse.doPostRequest(URL, strJS);
                    JSONObject jResult;
                    String strStatus = "";
                    if (!strResponsePost.equals("")) {

                        try {

                            jResult = new JSONObject(strResponsePost);
                            strStatus = jResult.getString("status");
                            Log.d("Status cmt", strStatus);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (strStatus.equals("1")) {
                            db.UpdateCommentStatus(db, strCommentID);
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(SendCommentService.this, "Comments added Successfully", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                        if(strStatus.equals("0")){
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(SendCommentService.this, "From Comments,Please Contact Support Team", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                        if(strStatus.equals("-1")){
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(SendCommentService.this, "From Comments,Contact Support Team", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }

                }

            } catch (IOException e) {
                stopSelf();
                e.printStackTrace();
            }


            return strResponsePost;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            stopSelf();

            if (TodayDetails.thisToday != null) {
                TodayDetails.thisToday.reFreshReload();
            }
            if (OlderDetails.thisOlderDetails != null) {
                OlderDetails.thisOlderDetails.reFreshReload();
            }
            if (NewDetails.thisnewDetails != null) {
                NewDetails.thisnewDetails.reFreshReload();
            }
            if (CompletedDetails.thiscompleteDetails != null) {
                CompletedDetails.thiscompleteDetails.reFreshReload();
            }

            if (marriagetoday.thisToday != null) {
                marriagetoday.thisToday.reFreshReload();
            }
            if (marriageolder.thisToday != null) {
                marriageolder.thisToday.reFreshReload();
            }
            if (marriagenew.thisToday != null) {
                marriagenew.thisToday.reFreshReload();
            }
            if (marriagecompleted.thisToday != null) {
                marriagecompleted.thisToday.reFreshReload();
            }
//            startActivity(new Intent(getApplicationContext(), NextActivity.class));
        }


    }



    private String createServiceJson(String strDocId, String strCustomerComment, String document_user,
                                     String strCommentUser, String strCommentdate,
                                     String executionerTypeRole, String strOwner, String ownerTypeRole,String contact, String Commentstype, String Commentsarea) {
        DBOperation db = new DBOperation(getBaseContext());
        try {

            JSONObject serviceJsn = new JSONObject();
            JSONObject obj = new JSONObject();
            try {
                obj.put("docid", strDocId);
                obj.put("comment", strCustomerComment);
                obj.put("document_user", document_user);
                obj.put("cust_contact_number",contact);
                obj.put("cmt_type", Commentstype);
                obj.put("executioner", strCommentUser);
                obj.put("reminder", strCommentdate);
                obj.put("complain_area", Commentsarea);
                obj.put("executioner_type", executionerTypeRole);
                obj.put("owner", strOwner);
                obj.put("owner_type", ownerTypeRole);
                obj.put("platform_id", "3");

            } catch (JSONException e) {


                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            serviceJsn.put("user", obj);
            JSONObject jsonauto_token = new JSONObject();
            jsonauto_token.put("token", "DOtUBMhv5pk51tl0D37uBcezq85cXNN7hZQ7");
            serviceJsn.put("auth_token", jsonauto_token);
            // String post="";
            return serviceJsn.toString();
            //String strResponsePost = mResponse.doPostRequest(URL, post);


//            if (!strResponsePost.equals("")) {
//                JSONObject jResult = new JSONObject(strResponsePost);
//                String strStatus = jResult.getString("status");
//                if (strStatus.equals("1")) {
//                    System.out.println("from comment:"+strStatus);
//                }
//
//            }


        } catch (Exception e) {

        }

        return null;
    }


}


