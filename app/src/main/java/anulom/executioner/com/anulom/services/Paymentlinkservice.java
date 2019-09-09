//package anulom.executioner5.com3.anulom.services;
//
//import android.app.Service;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.database.sqlite.SQLiteDatabase;
//import android.os.AsyncTask;
//import android.os.Build;
//import android.os.IBinder;
//import android.preference.PreferenceManager;
//import android.support.annotation.Nullable;
//import android.widget.Toast;
//
//import com.squareup.okhttp.MediaType;
//import com.squareup.okhttp.OkHttpClient;
//
//import org.apache.http.client.ClientProtocolException;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//
//import anulom.executioner5.com3.anulom.GenericMethods;
//import anulom.executioner5.com3.anulom.Login;
//import anulom.executioner5.com3.anulom.database.DBManager;
//import anulom.executioner5.com3.anulom.database.DBOperation;
//import anulom.executioner5.com3.anulom.fragment.CompletedDetails;
//import anulom.executioner5.com3.anulom.fragment.NewDetails;
//import anulom.executioner5.com3.anulom.fragment.OlderDetails;
//import anulom.executioner5.com3.anulom.fragment.TodayDetails;
//import anulom.executioner5.com3.anulom.fragment.marriagecompleted;
//import anulom.executioner5.com3.anulom.fragment.marriagenew;
//import anulom.executioner5.com3.anulom.fragment.marriageolder;
//import anulom.executioner5.com3.anulom.fragment.marriagetoday;
//
//
//import static anulom.executioner5.com3.anulom.database.DBManager.TableInfo.TABLE_PAYMENT_LINK;
//import static anulom.executioner5.com3.anulom.database.DBManager.TableInfo.UPDATEPAYMENT1;
//import static com.google.android.gms.internal.zzir.runOnUiThread;
//
//
//public class Paymentlinkservice extends Service {
//    DBOperation db;
//
//    public String umail = Login.umailid;
//    GenericMethods mResponse;
//    OkHttpClient client = new OkHttpClient();
//    public static final MediaType JSON = MediaType.parse("application/json; charset=UTF8");
//    String username3, status = "1";
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//
//        return null;
//    }
//
//
//    public int onStartCommand(Intent intent, int flags, int startId) {
//
//        mResponse = new GenericMethods();
//
//        if (GenericMethods.isConnected(getApplicationContext())) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//                new MyAsyncTask()
//                        .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//
//            } else {
//                new MyAsyncTask().execute();
//            }
//        }
//        return START_STICKY;
//
//
//    }
//
//    private class MyAsyncTask extends AsyncTask<String, Integer, Double> {
//        DBOperation db = new DBOperation(getApplicationContext());
//
//        String token = "DOtUBMhv5pk51tl0D37uBcezq85cXNN7hZQ7";
//
//        @Override
//        protected Double doInBackground(String... params) {
//            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//            String username2 = sharedPreferences.getString("username", "");
//            username3 = username2;
//
//            ArrayList<HashMap<String, String>> paymentlist1 = db.getpaymentlink(db);
//
//            for (int i = 0; i < paymentlist1.size(); i++) {
//                String docid = paymentlist1.get(i).get("docid");
//                String email = paymentlist1.get(i).get("exec_email");
//                postData(docid,email);
//            }
//
//            return null;
//        }
//
//        protected void onPostExecute(Double result) {
//
//            stopSelf();
//
//            if (TodayDetails.thisToday != null) {
//                TodayDetails.thisToday.reFreshReload();
//            }
//            if (OlderDetails.thisOlderDetails != null) {
//                OlderDetails.thisOlderDetails.reFreshReload();
//            }
//            if (NewDetails.thisnewDetails != null) {
//                NewDetails.thisnewDetails.reFreshReload();
//            }
//            if (CompletedDetails.thiscompleteDetails != null) {
//                CompletedDetails.thiscompleteDetails.reFreshReload();
//            }
//
//            if (marriagetoday.thisToday != null) {
//                marriagetoday.thisToday .reFreshReload();
//            }
//            if (marriageolder.thisToday  != null) {
//                marriageolder.thisToday .reFreshReload();
//            }
//            if (marriagenew.thisToday != null) {
//                marriagenew.thisToday .reFreshReload();
//            }
//            if (marriagecompleted.thisToday != null) {
//                marriagecompleted.thisToday.reFreshReload();
//            }
//        }
//
//        protected void onProgressUpdate(Integer... progress) {
//
//        }
//
//        public void postData(String docid, String email) {
//
//            DBOperation db = new DBOperation(getApplicationContext());
//
//
//            String URL = GenericMethods.MAIN_URL+"/api/v3/appointment_data/payment_link";
//
//            try {
//                JSONObject app = new JSONObject();
//                app.put("exec_email", email);
//                app.put("document_id", docid);
//
//                JSONObject tokenjson = new JSONObject();
//                tokenjson.put("token", token);
//
//                JSONObject main = new JSONObject();
//                main.put("document", app);
//                main.put("auth_token", tokenjson);
//
//                String json = "";
//                json = main.toString();
//                System.out.println("jsom:"+json);
//
//                String strResponsePost = mResponse.doPostRequest(URL, json);
//
//
//                if (!strResponsePost.equals("")) {
//                    JSONObject jResult = new JSONObject(strResponsePost);
//                    String strStatus = jResult.getString("status");
//                    System.out.println("status:"+strStatus+strResponsePost);
//                    if (strStatus.equals(status)) {
//                        SQLiteDatabase sqldb = db.getWritableDatabase();
//                        int count = sqldb.delete(TABLE_PAYMENT_LINK, DBManager.TableInfo.docid_payment + "=?", new String[]{docid});
//                        runOnUiThread(new Runnable() {
//                            public void run() {
//                                Toast.makeText(Paymentlinkservice.this, " Payment Link Sent Successfully!! ", Toast.LENGTH_LONG).show();
//                            }
//                        });
//                    }if(strStatus.equals("0")){
//                        runOnUiThread(new Runnable() {
//                            public void run() {
//                                Toast.makeText(Paymentlinkservice.this, "From Payment Link,Please Contact Support Team ", Toast.LENGTH_LONG).show();
//                            }
//                        });
//                    }
//                    if (strStatus.equals("-1")) {
//                        runOnUiThread(new Runnable() {
//                            public void run() {
//                                Toast.makeText(Paymentlinkservice.this, "From Payment Link,Contact support Team", Toast.LENGTH_SHORT).show();
//                            }
//                        });
////
//                    }
//
//                }
//
//            } catch (ClientProtocolException e) {
//
//                // TODO Auto-generated catch block
//            } catch (IOException e) {
//
//                // TODO Auto-generated catch block
//            } catch (JSONException e) {
//
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//        }
//
//
//    }
//}