package anulom.executioner.com.anulom.services;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import anulom.executioner.com.anulom.GenericMethods;
import anulom.executioner.com.anulom.Login;
import anulom.executioner.com.anulom.database.DBManager;
import anulom.executioner.com.anulom.database.DBOperation;


import static anulom.executioner.com.anulom.database.DBManager.TableInfo.REASSIGN;
import static anulom.executioner.com.anulom.database.DBManager.TableInfo.TABLE_TASK;
import static com.google.android.gms.internal.zzir.runOnUiThread;

public class reassignpost extends Service {
    DBOperation db;
    public String umail = Login.umailid;
    GenericMethods mResponse;
    OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=UTF8");
    String username3, status = "1", prev_owner, new_owner, reason;
    JSONArray appointment = new JSONArray();
    String  taskid , comment;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }


    public int onStartCommand(Intent intent, int flags, int startId) {



        mResponse = new GenericMethods();

        if (GenericMethods.isConnected(getApplicationContext())) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                new MyAsyncTask()
                        .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

            } else {
                new MyAsyncTask().execute();


            }
        }
        return START_STICKY;


    }



    private class MyAsyncTask extends AsyncTask<String, Integer, Double> {


        @Override
        protected Double doInBackground(String... params) {
            DBOperation db = new DBOperation(getApplicationContext());
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String username2 = sharedPreferences.getString("username", "");
            username3 = username2;
            ArrayList<HashMap<String, String>> reassigntask = db.getreassign(db);


            try {

                for (int i = 0; i < reassigntask.size(); i++) {


                    prev_owner = reassigntask.get(i).get("Prev_owner");
                    reason = reassigntask.get(i).get("reassign_reason");
                    new_owner = reassigntask.get(i).get("new_owner");
                    comment=reassigntask.get(i).get("comment");
                    taskid=reassigntask.get(i).get("taskid");
                    postData(taskid, comment, prev_owner, new_owner, reason);
                }


            } catch (Exception e) {

                // TODO Auto-generated catch block


            }
            return null;

        }

        protected void onPostExecute(Double result) {
            stopSelf();


        }

        protected void onProgressUpdate(Integer... progress) {

        }

        public void postData(String taskid, String comment, String prev_owner, String new_owner, String reason) {

            DBOperation db = new DBOperation(getApplicationContext());
            String token = "DOtUBMhv5pk51tl0D37uBcezq85cXNN7hZQ7";
            String URL = GenericMethods.MAIN_URL1+"/communicator/api/v1/comment_data/reassign_task";


            try {


                JSONObject app = new JSONObject();
                app.put("task_id", taskid);
                app.put("comment", comment);
                app.put("prev_owner", prev_owner);
                app.put("new_owner", new_owner);
                app.put("reason", reason);


                JSONObject tokenjson = new JSONObject();
                tokenjson.put("token", token);
                JSONObject main = new JSONObject();
                main.put("comment", app);
                main.put("auth_token", tokenjson);
                String json = "";
                json = main.toString();

                String strResponsePost = mResponse.doPostRequest(URL, json);

                if (!strResponsePost.equals("")) {
                    JSONObject jResult = new JSONObject(strResponsePost);
                    String strStatus = jResult.getString("status");

                    if (strStatus.equals("1")) {

                        SQLiteDatabase sqldb = db.getWritableDatabase();

                        int count = sqldb.delete(REASSIGN, DBManager.TableInfo.Prev_owner + "=?", new String[]{prev_owner});

                        int count1 = sqldb.delete(TABLE_TASK, DBManager.TableInfo.TASK_ID + "=?", new String[]{taskid});


                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(reassignpost.this, "Task Updated  Successfully  ", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    if(strStatus.equals("0")){
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(reassignpost.this, "From Reassign,Please Contact Support Team ", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    if (strStatus.equals("-1")) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(reassignpost.this, "From Reassign,Contact support Team", Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                }


            } catch (JSONException e) {

                // TODO Auto-generated catch block
                //     e.printStackTrace();
                // }

            } catch (ClientProtocolException e) {

                // TODO Auto-generated catch block
            } catch (IOException e) {

            }
        }


    }


}
