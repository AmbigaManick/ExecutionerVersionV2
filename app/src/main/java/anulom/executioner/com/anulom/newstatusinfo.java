package anulom.executioner.com.anulom;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.Polyline;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import anulom.executioner.com.anulom.database.DBManager;
import anulom.executioner.com.anulom.database.DBOperation;
import anulom.executioner.com.anulom.services.AppointmentstatusPost;
import anulom.executioner.com.anulom.services.InternalExternalWitness;
import anulom.executioner.com.anulom.services.SendmultiPartyReport;

import static android.view.View.GONE;
import static anulom.executioner.com.anulom.database.DBManager.TableInfo.MULTIWORK;

import static anulom.executioner.com.anulom.database.DBManager.TableInfo.POSTDOC;
import static anulom.executioner.com.anulom.database.DBManager.TableInfo.POSTWITNESSDOC;
import static anulom.executioner.com.anulom.database.DBManager.TableInfo.TABLE_WITNESS;


/**
 * Created by anulom on 11/7/17.
 */

public class newstatusinfo extends AppCompatActivity implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public static final int i = 0;
    String item = "", document, locationstring, valueradio, startdate, localTime, content, rkey, email, value, status = "", appointment, reason = "";
    int count = 0;
    int pos;
    int ID, i1, i2, a;
    String wit_user_ID = "";
    String wit_user_ID1 = "";
    int n_parties = 0;
    int ctr1 = 0;
    int k, x, y;
    String call="false";
    DBOperation db;
    RadioGroup rg, rg1;
    String flag, postStatus, payamount1, username = "", username1 = "", flag_witness, radiovalue, wit_email1 = "", awitness1value = "", awitness2value = "", cwitness1value = "", cwitness2value = "", wit_email2 = "", witness_type = "", witness_type1 = "", witness_no1 = "", witness_no2 = "";
    EditText slottime, slotime1, biov1, biov2;
    Spinner switness1, switness2, awitness1, awitness2, cwitness1, cwitness2;
    TextView tv2, e2, withess1, witness2, biow1, biow2, Anulomwitness, Anulomwitness1, customerwitness, customerwitness1;
    Location mLocation;
    protected GoogleMap map;
    List<String> spinnervalues = new ArrayList<String>();
    List<String> BIOMETRICVALUES = new ArrayList<String>();
    private final static int REQUEST_CHECK_SETTINGS_GPS = 0x1;
    private final static int REQUEST_ID_MULTIPLE_PERMISSIONS = 0x2;
    private List<Polyline> polylines;
    protected GoogleApiClient mGoogleApiClient;
    private static final String LOG_TAG = "newstatusinfo";
    Double currentlat, currentlong;
    CheckBox check1, check2, check3, check4, check5;
    private String username2 = "";
    String ID1;
    Button add;
    int fla;
    ArrayList<HashMap<String, String>> getAlldataList = null;

    List<String> itemlist = new ArrayList<String>();
    List<Integer> poslist = new ArrayList<Integer>();
    List<Integer> IDlist = new ArrayList<Integer>();
    List<String> attendancelist = new ArrayList<String>();
    List<String> partytypelist = new ArrayList<String>();
    List<String> emaillist = new ArrayList<String>();

    @Override
    public void onLocationChanged(final Location location) {

        if (location != null) {
            Log.d(LOG_TAG, String.format("%f, %f", location.getLatitude(), location.getLongitude()));


        } else {
            Log.d(LOG_TAG, "Location is null");
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        db = new DBOperation(this);
        document = getIntent().getStringExtra("document_id");
        rkey = getIntent().getStringExtra("rkey");

        final LinearLayout rootView = new LinearLayout(this);
        SharedPreferences usermail = PreferenceManager.getDefaultSharedPreferences(this);
        username2 = usermail.getString("username", "");
        String password2 = usermail.getString("password", "");
        content = getIntent().getStringExtra("content");
        payamount1 = getIntent().getStringExtra("amount");

        appointment = getIntent().getStringExtra("appointment_id");
        postStatus = getIntent().getStringExtra("post_status");
        fla = getIntent().getIntExtra("flag", fla);
        startdate = getIntent().getStringExtra("StartDate");
        System.out.println("content:"+content+"document:"+postStatus+document);

        if (fla == 0 && GenericMethods.isConnected(getApplicationContext())) {
            mGoogleApiClient = new GoogleApiClient.Builder(newstatusinfo.this)
                    .addApi(LocationServices.API)
                    .addApi(Places.GEO_DATA_API)
                    .addConnectionCallbacks(newstatusinfo.this)
                    .addOnConnectionFailedListener(this)
                    .build();
            MapsInitializer.initialize(newstatusinfo.this);
            mGoogleApiClient.connect();
            getCurrentLocation();
        }
        if (content.equals("Today")) {
            getAlldataList = db.getTodayPartiesReport(db, startdate);
        } else if (content.equals("Older")) {
            System.out.println("older db");
            getAlldataList = db.getOlderPartiesReport(db, startdate);
        } else if (content.equals("New")) {
            getAlldataList = db.getNewPartiesReport(db, startdate);
        } else {
            getAlldataList = db.getAllPartiesReport(db, startdate);
        }
        Calendar cal = Calendar.getInstance();
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("HH:mm a");
        localTime = date.format(currentLocalTime);


        check1 = new CheckBox(this);
        check1.setText("Visit Not Done");
        check1.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimension(R.dimen.font_size_medium));

        check2 = new CheckBox(this);
        check2.setText("Door Step Visit Done");
        check2.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimension(R.dimen.font_size_medium));

        check3 = new CheckBox(this);
        check3.setText("No Show");
        check3.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimension(R.dimen.font_size_medium));
        check4 = new CheckBox(this);
        check4.setText("Cancelled By Customer");
        check4.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimension(R.dimen.font_size_medium));

        check5 = new CheckBox(this);
        check5.setText("Tick here if Witness are on Customer Side");
        check5.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimension(R.dimen.font_size_medium));


        witness2 = new TextView(this);
        witness2.setText(" Witness 2:");
        witness2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        LinearLayout.LayoutParams params1witness2 = (LinearLayout.LayoutParams) witness2.getLayoutParams();
        params1witness2.setMargins(40, 20, 110, 10);
        witness2.setLayoutParams(params1witness2);
        witness2.setTextColor(Color.parseColor("#000080"));
        witness2.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimension(R.dimen.font_size_medium));


        withess1 = new TextView(this);
        withess1.setText("   Witness 1:");
        withess1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        LinearLayout.LayoutParams params1withess1 = (LinearLayout.LayoutParams) withess1.getLayoutParams();
        params1withess1.setMargins(40, 20, 110, 10);
        withess1.setLayoutParams(params1withess1);
        withess1.setTextColor(Color.parseColor("#000080"));
        withess1.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimension(R.dimen.font_size_medium));

        biow1 = new TextView(this);
        biow1.setText("   Witness 1:");
        biow1.setTextColor(Color.parseColor("#000080"));
        biow1.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimension(R.dimen.font_size_medium));
        biow1.setVisibility(GONE);


        biow2 = new TextView(this);
        biow2.setText(" Witness 2:");
        biow2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        LinearLayout.LayoutParams params1withess2 = (LinearLayout.LayoutParams) biow2.getLayoutParams();
        params1withess2.setMargins(40, 20, 110, 10);
        biow2.setLayoutParams(params1withess2);
        biow2.setTextColor(Color.parseColor("#000080"));
        biow2.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimension(R.dimen.font_size_medium));
        biow2.setVisibility(GONE);


        biov1 = new EditText(this);
        biov1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        LinearLayout.LayoutParams params1biov1 = (LinearLayout.LayoutParams) biov1.getLayoutParams();
        params1biov1.setMargins(40, 20, 110, 10);
        biov1.setLayoutParams(params1biov1);
        biov1.setVisibility(GONE);

        biov2 = new EditText(this);
        biov2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        LinearLayout.LayoutParams paramstimebiov2 = (LinearLayout.LayoutParams) biov2.getLayoutParams();
        paramstimebiov2.setMargins(40, 20, 110, 10);
        biov2.setLayoutParams(paramstimebiov2);
        biov2.setVisibility(GONE);
        spinnervalues = db.getAllexecutioner();

        switness1 = new Spinner(this);
        switness1.setBackgroundResource(R.drawable.ic_down);
        switness1.setVisibility(GONE);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.witnesslay, spinnervalues);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        switness1.setAdapter(adapter);
        switness1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                wit_user_ID = db.getuserid(db).get(position).get("userid");
                wit_email1 = parent.getItemAtPosition(position).toString();
                username = db.getusername(db).get(position).get("nameuser1");
                Anulomwitness.setText("Anulom Witness(1)-" + username);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        switness2 = new Spinner(this);
        switness2.setBackgroundResource(R.drawable.ic_down);
        switness2.setVisibility(GONE);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, R.layout.witnesslay, spinnervalues);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        switness2.setAdapter(adapter1);
        switness2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                wit_user_ID1 = db.getuserid(db).get(position).get("userid");
                wit_email2 = parent.getItemAtPosition(position).toString();
                username1 = db.getusername(db).get(position).get("nameuser1");

                Anulomwitness1.setText("Anulom Witness(2)-" + username1);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        rg = new RadioGroup(getApplicationContext());
        rg.setOrientation(RadioGroup.HORIZONTAL);
        ColorStateList colorStateList = new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_enabled} //enabled
                },
                new int[]{getResources().getColor(R.color.colorPrimaryDark)}
        );
        rg.setVisibility(GONE);
        final RadioButton apartment = new RadioButton(getApplicationContext());
        apartment.setText("Internal Witness");
        apartment.setTextColor(Color.BLACK);
        apartment.setButtonDrawable(R.drawable.radiobutton);
        apartment.setTextSize(18);

        rg.addView(apartment);

        // Create another Radio Button for RadioGroup
        final RadioButton Individual_house = new RadioButton(getApplicationContext());
        Individual_house.setText("External Witness");
        Individual_house.setButtonDrawable(R.drawable.radiobutton);
        Individual_house.setTextColor(Color.BLACK);

        Individual_house.setTextSize(18);

        rg.addView(Individual_house);


        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (apartment.isChecked()) {

                    valueradio = "internal_wit1";
                    flag_witness = "0";
                    witness_type = "2";
                    witness_no1 = "1";

                    switness1.setVisibility(View.VISIBLE);
                    Anulomwitness.setVisibility(View.VISIBLE);
                    awitness1.setVisibility(View.VISIBLE);
                    customerwitness.setVisibility(GONE);
                    cwitness1.setVisibility(GONE);

                } else if (Individual_house.isChecked()) {

                    flag_witness = "0";
                    witness_type1 = "1";
                    radiovalue = "external";
                    witness_no2 = "1";
                    valueradio = "external_wit1";
                    customerwitness.setVisibility(View.VISIBLE);
                    cwitness1.setVisibility(View.VISIBLE);
                    switness1.setVisibility(View.VISIBLE);
                    Anulomwitness.setVisibility(View.VISIBLE);
                    awitness1.setVisibility(GONE);

                }

                if (!apartment.isChecked()) {

                    switness1.setVisibility(GONE);
                    Anulomwitness.setVisibility(GONE);
                    awitness1.setVisibility(GONE);


                } else if (!Individual_house.isChecked()) {

                    customerwitness.setVisibility(GONE);
                    cwitness1.setVisibility(GONE);
//
                }

            }
        });

        rg1 = new RadioGroup(getApplicationContext());
        rg1.setOrientation(RadioGroup.HORIZONTAL);
        ColorStateList colorStateList1 = new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_enabled} //enabled
                },
                new int[]{getResources().getColor(R.color.colorPrimaryDark)}
        );

        rg1.setVisibility(GONE);

        final RadioButton internalwitness1 = new RadioButton(getApplicationContext());
        internalwitness1.setText("Internal Witness");
        internalwitness1.setTextColor(Color.BLACK);
        internalwitness1.setButtonDrawable(R.drawable.radiobutton);
        internalwitness1.setTextSize(18);

        rg1.addView(internalwitness1);

        // Create another Radio Button for RadioGroup
        final RadioButton externalwitness1 = new RadioButton(getApplicationContext());
        externalwitness1.setText("External Witness");
        externalwitness1.setButtonDrawable(R.drawable.radiobutton);
        externalwitness1.setTextColor(Color.BLACK);
        externalwitness1.setTextSize(18);

        rg1.addView(externalwitness1);


        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (internalwitness1.isChecked()) {
                    flag_witness = "0";
                    witness_type = "2";
                    witness_no1 = "2";
                    valueradio = "internal_wit2";
                    flag="true";
                    switness2.setVisibility(View.VISIBLE);
                    Anulomwitness1.setVisibility(View.VISIBLE);
                    awitness2.setVisibility(View.VISIBLE);
                    customerwitness1.setVisibility(GONE);
                    cwitness2.setVisibility(GONE);

                } else if (externalwitness1.isChecked()) {
                    flag="true";
                    flag_witness = "0";
                    witness_type1 = "1";
                    witness_no2 = "2";
                    valueradio = "external_wit2";
                    switness2.setVisibility(GONE);
                    customerwitness1.setVisibility(View.VISIBLE);
                    cwitness2.setVisibility(View.VISIBLE);
                    Anulomwitness1.setVisibility(View.VISIBLE);
                    awitness2.setVisibility(View.VISIBLE);

                }

                if (!internalwitness1.isChecked()) {

                    switness2.setVisibility(GONE);
                    Anulomwitness1.setVisibility(GONE);
                    awitness2.setVisibility(GONE);


                } else if (!externalwitness1.isChecked()) {


                    customerwitness1.setVisibility(GONE);
                    cwitness2.setVisibility(GONE);

                }

            }
        });


        Anulomwitness = new TextView(this);
        Anulomwitness.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        LinearLayout.LayoutParams params1Anulomwit = (LinearLayout.LayoutParams) Anulomwitness.getLayoutParams();
        params1Anulomwit.setMargins(40, 20, 110, 10);
        Anulomwitness.setLayoutParams(params1Anulomwit);
        Anulomwitness.setTextColor(Color.parseColor("#545454"));
        Anulomwitness.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimension(R.dimen.font_size_medium));
        Anulomwitness.setVisibility(GONE);
        Anulomwitness.setCompoundDrawablesWithIntrinsicBounds(R.drawable.clearcircle, 0, 0, 0);


        customerwitness = new TextView(this);
        customerwitness.setText("Witness 1");
        customerwitness.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        LinearLayout.LayoutParams params1customerwit = (LinearLayout.LayoutParams) customerwitness.getLayoutParams();
        params1customerwit.setMargins(40, 20, 110, 10);
        customerwitness.setLayoutParams(params1customerwit);
        customerwitness.setTextColor(Color.parseColor("#545454"));
        customerwitness.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimension(R.dimen.font_size_medium));
        customerwitness.setVisibility(GONE);
        customerwitness.setCompoundDrawablesWithIntrinsicBounds(R.drawable.clearcircle, 0, 0, 0);


        Anulomwitness1 = new TextView(this);
        Anulomwitness1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        LinearLayout.LayoutParams params1Anulomwit1 = (LinearLayout.LayoutParams) Anulomwitness1.getLayoutParams();
        params1Anulomwit1.setMargins(40, 20, 110, 10);
        Anulomwitness1.setLayoutParams(params1Anulomwit1);
        Anulomwitness1.setTextColor(Color.parseColor("#545454"));
        Anulomwitness1.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimension(R.dimen.font_size_medium));
        Anulomwitness1.setVisibility(GONE);
        Anulomwitness1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.clearcircle, 0, 0, 0);


        customerwitness1 = new TextView(this);
        customerwitness1.setText("Witness 2");
        customerwitness1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        LinearLayout.LayoutParams params1customerwit1 = (LinearLayout.LayoutParams) customerwitness1.getLayoutParams();
        params1customerwit1.setMargins(40, 20, 110, 10);
        customerwitness1.setLayoutParams(params1customerwit);
        customerwitness1.setTextColor(Color.parseColor("#545454"));
        customerwitness1.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimension(R.dimen.font_size_medium));
        customerwitness1.setVisibility(GONE);
        customerwitness1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.clearcircle, 0, 0, 0);


        Anulomwitness1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                flag="false";
                Anulomwitness1.setVisibility(GONE);
                awitness2.setVisibility(GONE);
                internalwitness1.setChecked(false);



            }
        });

        customerwitness1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                flag="false";
                customerwitness1.setVisibility(GONE);
                cwitness2.setVisibility(GONE);
                externalwitness1.setChecked(false);


            }
        });

        Anulomwitness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                flag="false";
                Anulomwitness.setVisibility(GONE);
                awitness1.setVisibility(GONE);
                apartment.setChecked(false);

            }
        });

        customerwitness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag="false";
                customerwitness.setVisibility(GONE);
                cwitness1.setVisibility(GONE);
                Individual_house.setChecked(false);



            }
        });


        TextView t = new TextView(this);
        TextView t1 = new TextView(this);
        TextView t2 = new TextView(this);
        final TextView tv1 = new TextView(this);
        final TextView reason1 = new TextView(this);
        final TextView reason2 = new TextView(this);
        tv1.setText("Request No:" + rkey);
        tv1.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimension(R.dimen.font_size_medium));
        tv1.setGravity(Gravity.CENTER);
        tv1.setTextColor(Color.parseColor("#000080"));

        e2 = new TextView(this);
        e2.setTextColor(Color.parseColor("#000080"));
        e2.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimension(R.dimen.font_size_medium));

        tv2 = new TextView(this);
        tv2.setText(" Appointment Status:");
        tv2.setTextColor(Color.parseColor("#000080"));
        tv2.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimension(R.dimen.font_size_medium));


        final TextView tv3 = new TextView(this);
        tv3.setText(" Biometric Status:");
        tv3.setTextColor(Color.parseColor("#000080"));
        tv3.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimension(R.dimen.font_size_medium));
        tv3.setVisibility(GONE);


        slottime = new EditText(this);
        slottime.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) slottime.getLayoutParams();
        params1.setMargins(40, 20, 110, 10);
        slottime.setLayoutParams(params1);
        slottime.setVisibility(GONE);

        slotime1 = new EditText(this);
        slotime1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        LinearLayout.LayoutParams paramstime = (LinearLayout.LayoutParams) slotime1.getLayoutParams();
        paramstime.setMargins(40, 20, 110, 10);
        slotime1.setLayoutParams(paramstime);
        slotime1.setVisibility(GONE);

        BIOMETRICVALUES.add("Select");
        BIOMETRICVALUES.add("Yes");
        BIOMETRICVALUES.add("No");


        reason1.setText(" Reason:");
        reason1.setTextColor(Color.parseColor("#000080"));
        reason1.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimension(R.dimen.font_size_medium));
        reason1.setVisibility(GONE);

        reason2.setText(" Reason:");
        reason2.setTextColor(Color.parseColor("#000080"));
        reason2.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimension(R.dimen.font_size_medium));
        reason2.setVisibility(GONE);

        final Button update1 = new Button(this);
        update1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        update1.setGravity(Gravity.CENTER);
        update1.setText("UPDATE");
        update1.setBackgroundColor(Color.parseColor("#3b5998"));
        update1.setTextColor(Color.WHITE);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) update1.getLayoutParams();
        params.setMargins(50, 20, 50, 10);
        update1.setLayoutParams(params);


        update1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (check1.isChecked()) {

                    status = "0";

                    reason = slotime1.getText().toString();

                } else if (check2.isChecked()) {
                    status = "1";


                } else if (check3.isChecked()) {
                    status = "2";

                    reason = slottime.getText().toString();
                } else if (check4.isChecked()) {
                    status = "3";


                }


                if (check1.isChecked() || check2.isChecked() || check3.isChecked() || check4.isChecked()) {
                    if (!status.equals(postStatus)) {


                        SQLiteDatabase sqldb2 = db.getWritableDatabase();
                        ContentValues values2 = new ContentValues();
                        values2.put(DBManager.TableInfo.post_status, status);
                        int count4 = sqldb2.update(DBManager.TableInfo.TABLE_NAME_APPOINTMENT, values2, DBManager.TableInfo.AppointmentId + "=?", new String[]{appointment});
                        sqldb2.close();



                        SQLiteDatabase db0 = db.getWritableDatabase();
                        ContentValues values5 = new ContentValues();
                        values5.put(DBManager.TableInfo.KEYID, ID1);
                        values5.put(DBManager.TableInfo.DOCU, document);
                        values5.put(DBManager.TableInfo.AppointmentId, appointment);
                        values5.put(DBManager.TableInfo.STATUS, status);
                        values5.put(DBManager.TableInfo.reason, reason);
                        values5.put(DBManager.TableInfo.KEY_LOGIN_USER, username2);
                        String condition5 = DBManager.TableInfo.AppointmentId + " =?";
                        Cursor cursor5 = db0.query(MULTIWORK, null, condition5, new String[]{appointment}, null, null, null);
                        long status5 = db0.insert(MULTIWORK, null, values5);
                        cursor5.close();
                        db0.close();

                        if (status.equals("0") || status.equals("2")) {

                            if (!reason.equals("")) {
                                if (GenericMethods.isConnected(getApplicationContext())) {

                                    if (GenericMethods.isOnline()) {


                                        Toast.makeText(newstatusinfo.this, "System is trying to connect One Platform..Make Sure Internet Connect is Available", Toast.LENGTH_LONG).show();
                                        Intent i2 = new Intent(newstatusinfo.this, AppointmentstatusPost.class);
                                        startService(i2);
                                        finish();
                                    } else {
                                        Toast.makeText(newstatusinfo.this, " Appointment Data Saved Offline,App is Waiting for active network connection", Toast.LENGTH_LONG).show();

                                    }
                                } else {
                                    Toast.makeText(newstatusinfo.this, " Appointment Data Saved Offline,App is Waiting for active network connection", Toast.LENGTH_LONG).show();


                                }
                            } else {
                                Toast.makeText(newstatusinfo.this, " Enter the reason and update", Toast.LENGTH_LONG).show();

                            }


                        } else {


                            if (GenericMethods.isConnected(getApplicationContext())) {

                                if (GenericMethods.isOnline()) {

                                    if (status5 > 0) {

                                        Toast.makeText(newstatusinfo.this, "System is trying to connect One Platform..Make Sure Internet Connect is Available", Toast.LENGTH_LONG).show();
                                        Intent i2 = new Intent(newstatusinfo.this, AppointmentstatusPost.class);
                                        startService(i2);
                                        finish();
                                    } else {
                                        Toast.makeText(newstatusinfo.this, "Enter the reason and update", Toast.LENGTH_LONG).show();

                                    }

                                } else {
                                    Toast.makeText(newstatusinfo.this, " Appointment Data Saved Offline,App is Waiting for active network connection", Toast.LENGTH_LONG).show();

                                }
                            } else {
                                Toast.makeText(newstatusinfo.this, " Appointment Data Saved Offline", Toast.LENGTH_LONG).show();

                            }


                        }


                    } else {
                        Toast.makeText(newstatusinfo.this, "Change any status to Update!!", Toast.LENGTH_LONG).show();

                    }
                }
            }
        });

        ScrollView m_Scroll = new ScrollView(this);
        m_Scroll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        Toolbar toolbar = new Toolbar(this);
        toolbar.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        toolbar.setBackgroundColor(Color.parseColor("#3b5998"));
        toolbar.setTitle("Biometric Status");
        toolbar.setTitleTextColor(Color.WHITE);
        rootView.addView(toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        rootView.setOrientation(LinearLayout.VERTICAL);
        rootView.addView(tv1);
        rootView.addView(t);
        rootView.addView(tv2);
        rootView.addView(check2);
        rootView.addView(check1);
        rootView.addView(check3);
        rootView.addView(check4);
        rootView.addView(reason1);
        rootView.addView(slottime);
        rootView.addView(reason2);
        rootView.addView(slotime1);
        rootView.addView(biow1);
        rootView.addView(rg);
        rootView.addView(switness1);
        rootView.addView(biow2);
        rootView.addView(rg1);
        rootView.addView(switness2);
        rootView.addView(tv3);
        rootView.addView(t1);
        rootView.addView(update1);
        m_Scroll.addView(rootView);
        setContentView(m_Scroll);

        ArrayList<HashMap<String, String>> statuslist = db.getmultipartycheck(db);
        for (int i = 0; i < statuslist.size(); i++) {
            if (document.equals(statuslist.get(i).get("docid"))) {
                postStatus = statuslist.get(i).get("status");

            }
        }
        if (postStatus.equals("null")) {


            check1.setVisibility(View.VISIBLE);
            check2.setVisibility(View.VISIBLE);
            check3.setVisibility(View.VISIBLE);
            check4.setVisibility(View.VISIBLE);

        }

        if (postStatus.equals("0")) {

            check1.setChecked(true);

        }
        if (postStatus.equals("1")) {
            check2.setChecked(true);
            tv3.setVisibility(View.VISIBLE);
            biow1.setVisibility(View.VISIBLE);
            rg.setVisibility(View.VISIBLE);
            biow2.setVisibility(View.VISIBLE);
            rg1.setVisibility(View.VISIBLE);
            update1.setVisibility(GONE);
            displayfunction(rootView);
        }
        if (postStatus.equals("2")) {
            check3.setChecked(true);

        }
        if (postStatus.equals("3")) {
            check4.setChecked(true);

        }
        if (postStatus.equals("99")) {

            check1.setVisibility(GONE);
            check2.setVisibility(GONE);
            check3.setVisibility(GONE);
            check4.setVisibility(GONE);
            tv2.setVisibility(GONE);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(newstatusinfo.this, "Document is too old,Please Contact Operation team!!", Toast.LENGTH_LONG).show();


                }
            });
        }
        if (fla == 1) {
            update1.setVisibility(GONE);

        }
        check2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (check2.isChecked()) {

                    reason1.setVisibility(GONE);
                    reason2.setVisibility(GONE);
                    slottime.setVisibility(GONE);
                    slotime1.setVisibility(GONE);
                    check1.setChecked(false);
                    check3.setChecked(false);
                    check4.setChecked(false);
                    tv3.setVisibility(View.VISIBLE);
                    update1.setVisibility(GONE);
                    biow1.setVisibility(View.VISIBLE);
                    rg.setVisibility(View.VISIBLE);
                    biow2.setVisibility(View.VISIBLE);
                    rg1.setVisibility(View.VISIBLE);

                    displayfunction(rootView);

                } else {

                    tv3.setVisibility(GONE);
                    update1.setVisibility(View.VISIBLE);
                    removefunction(rootView);

                }
            }
        });


        check1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (check1.isChecked()) {
                    reason2.setVisibility(View.VISIBLE);
                    slotime1.setVisibility(View.VISIBLE);
                    slottime.setVisibility(GONE);
                    reason1.setVisibility(GONE);
                    check2.setChecked(false);
                    check3.setChecked(false);
                    check4.setChecked(false);
                    tv3.setVisibility(GONE);
                    update1.setVisibility(View.VISIBLE);
                    removefunction(rootView);

                } else {
                    reason2.setVisibility(GONE);
                    slotime1.setVisibility(GONE);
                }
            }
        });


        check3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (check3.isChecked()) {
                    reason1.setVisibility(View.VISIBLE);
                    slottime.setVisibility(View.VISIBLE);
                    slotime1.setVisibility(GONE);
                    reason2.setVisibility(GONE);
                    check1.setChecked(false);
                    check2.setChecked(false);
                    check4.setChecked(false);
                    //disp_flag="1";
                    tv3.setVisibility(GONE);
                    update1.setVisibility(View.VISIBLE);
                    removefunction(rootView);

                } else {
                    reason1.setVisibility(GONE);
                    slottime.setVisibility(GONE);

                }
            }
        });

        check4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (check4.isChecked()) {
                    reason1.setVisibility(GONE);
                    slottime.setVisibility(GONE);
                    slotime1.setVisibility(GONE);
                    reason2.setVisibility(GONE);
                    check1.setChecked(false);
                    check2.setChecked(false);
                    check3.setChecked(false);
                    //disp_flag="1";
                    tv3.setVisibility(GONE);
                    update1.setVisibility(View.VISIBLE);
                    removefunction(rootView);
                }
            }
        });

        // if (apartment.isChecked() || internalwitness1.isChecked() || externalwitness1.isChecked() || Individual_house.isChecked()) {

//            for (int i = 0; i < db.getPartypost(db).size(); i++) {
//
//                if (db.getPartypost(db).get(i).get("internal_wit1").equals("")) {
//
//                    apartment.setChecked(false);
//
//                } else if (!db.getPartypost(db).get(i).get("internal_wit1").equals("null")) {
//
//                    System.out.println("hiiiii2");
//
//                    apartment.setChecked(true);
//
//                }
//
//
//                if (db.getPartypost(db).get(i).get("internal_wit2").equals("")) {
//
//                    System.out.println("hiiiii");
//
//                    internalwitness1.setChecked(false);
//
//                } else if (!db.getPartypost(db).get(i).get("internal_wit2").equals("null")) {
//
//                    internalwitness1.setChecked(true);
//                }
//
//                if (db.getPartypost(db).get(i).get("external_wit1").equals("")) {
//
//                    System.out.println("hiiiii");
//
//                    Individual_house.setChecked(false);
//
//                } else if (!db.getPartypost(db).get(i).get("external_wit1").equals("null")) {
//
//                    Individual_house.setChecked(true);
//                }
//                if (db.getPartypost(db).get(i).get("external_wit2").equals("")) {
//
//                    System.out.println("hiiiii");
//
//                    externalwitness1.setChecked(false);
//
//                } else if (!db.getPartypost(db).get(i).get("external_wit2").equals("null")) {
//
//                    externalwitness1.setChecked(true);
//                }
//                SQLiteDatabase dbb = db.getWritableDatabase();
//                dbb.delete(DBManager.TableInfo.UPDATEPARTY, null, null);
//                System.out.println("deleted");
//
//            }

    }
    // }

    private void getCurrentLocation() {
        if (mGoogleApiClient != null) {
            if (mGoogleApiClient.isConnected()) {
                int permissionLocation = ContextCompat.checkSelfPermission(newstatusinfo.this,
                        Manifest.permission.ACCESS_FINE_LOCATION);
                if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                    mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                    LocationRequest locationRequest = new LocationRequest();
                    locationRequest.setInterval(3000);
                    locationRequest.setFastestInterval(3000);
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                            .addLocationRequest(locationRequest);
                    builder.setAlwaysShow(true);
                    LocationServices.FusedLocationApi
                            .requestLocationUpdates(mGoogleApiClient, locationRequest, newstatusinfo.this);
                    PendingResult<LocationSettingsResult> result =
                            LocationServices.SettingsApi
                                    .checkLocationSettings(mGoogleApiClient, builder.build());
                    result.setResultCallback(new ResultCallback<LocationSettingsResult>() {

                        @Override
                        public void onResult(LocationSettingsResult result) {
                            final Status status = result.getStatus();
                            switch (status.getStatusCode()) {
                                case LocationSettingsStatusCodes.SUCCESS:

                                    int permissionLocation = ContextCompat
                                            .checkSelfPermission(newstatusinfo.this,
                                                    Manifest.permission.ACCESS_FINE_LOCATION);
                                    if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                                        mLocation = LocationServices.FusedLocationApi
                                                .getLastLocation(mGoogleApiClient);


                                    }
                                    break;
                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                                    try {

                                        status.startResolutionForResult(newstatusinfo.this,
                                                REQUEST_CHECK_SETTINGS_GPS);
                                    } catch (IntentSender.SendIntentException e) {

                                    }
                                    break;
                                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:

                                    break;
                            }
                        }
                    });

                    if (mLocation != null) {
                        currentlat = mLocation.getLatitude();
                        currentlong = mLocation.getLongitude();
                        getLocationAddress(mLocation);

                    }
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS_GPS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        getCurrentLocation();
                        break;
                    case Activity.RESULT_CANCELED:
                        finish();
                        break;
                }
                break;
        }
    }

    private void checkPermissions() {
        int permissionLocation = ContextCompat.checkSelfPermission(newstatusinfo.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(this,
                        listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            }
        } else {
            getCurrentLocation();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        int permissionLocation = ContextCompat.checkSelfPermission(newstatusinfo.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        }
    }

    public String getLocationAddress(final Location location) {


        Geocoder geocoder = new Geocoder(newstatusinfo.this, Locale.getDefault());

        List<Address> addresses = null;

        try {
            addresses = geocoder.getFromLocation(currentlat, currentlong, 1);


        } catch (IOException e1) {
            e1.printStackTrace();

        } catch (IllegalArgumentException e2) {

            String errorString = "Illegal arguments "
                    + Double.toString(location.getLatitude()) + " , "
                    + Double.toString(location.getLongitude())
                    + " passed to address service";
            e2.printStackTrace();

        }
        if ((addresses != null && addresses.size() > 0)) {

            final Address address = addresses.get(0);


            String addressText = String.format(
                    "%s, %s, %s",

                    address.getMaxAddressLineIndex() > 0 ? address
                            .getAddressLine(0) : "",

                    address.getLocality(),

                    address.getCountryName());

            final List<Address> finalAddresses = addresses;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    e2.setText(finalAddresses.get(0).getAddressLine(0));
                    locationstring = e2.getText().toString();

                }
            });


            return addressText;
        } else {
            return "No address found by the service: Note to the developers, If no address is found by google itself, there is nothing you can do about it. :(";
        }


    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        checkPermissions();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void displayfunction(final LinearLayout rootView) {
        awitness2 = new Spinner(this);
        awitness2.setGravity(Gravity.CENTER);

        awitness2.setVisibility(GONE);
        ArrayAdapter<String> adapterawitness1 = new ArrayAdapter<>(this, R.layout.witnesslay, BIOMETRICVALUES);
        adapterawitness1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        awitness2.setAdapter(adapterawitness1);
        awitness2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                awitness2value = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cwitness1 = new Spinner(this);
        cwitness1.setGravity(Gravity.CENTER);

        cwitness1.setVisibility(GONE);
        ArrayAdapter<String> adaptercwitness1 = new ArrayAdapter<>(this, R.layout.witnesslay, BIOMETRICVALUES);
        adaptercwitness1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cwitness1.setAdapter(adaptercwitness1);
        cwitness1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cwitness1value = parent.getItemAtPosition(position).toString();
                System.out.println("value:" + cwitness1value);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cwitness2 = new Spinner(this);
        cwitness2.setGravity(Gravity.CENTER);
        cwitness2.setBackgroundResource(R.drawable.ic_down);
        cwitness2.setVisibility(GONE);
        ArrayAdapter<String> adaptercwitness2 = new ArrayAdapter<>(this, R.layout.witnesslay, BIOMETRICVALUES);
        adaptercwitness2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cwitness2.setAdapter(adaptercwitness2);
        cwitness2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cwitness2value = parent.getItemAtPosition(position).toString();
                System.out.println("value:" + cwitness2value);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        awitness1 = new Spinner(this);

        awitness1.setVisibility(GONE);
        ArrayAdapter<String> adapterawitness = new ArrayAdapter<>(this, R.layout.witnesslay, BIOMETRICVALUES);
        adapterawitness.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        awitness1.setAdapter(adapterawitness);
        awitness1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                awitness1value = parent.getItemAtPosition(position).toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        int o_count = 0, t_count = 0, w_count = 0, aw_count = 0, poa_count = 0;


        for (int j = 0; j < getAlldataList.size(); j++) {

            System.out.println("entered for loop");


            TextView tv = new TextView(this);
            tv.setId(j + 100);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.font_size_medium));
            tv.setTextColor(Color.parseColor("#545454"));


            List<String> values = new ArrayList<String>();

            if (getAlldataList.get(j).get("document_id").equals(document)) {

                System.out.println("entered doc comparison");

                if (Integer.valueOf(getAlldataList.get(j).get("poa")) ==0) {
                    System.out.println("entered poa==0");


                    n_parties = n_parties + 1;
                    Spinner spin = new Spinner(this);
                    spin.setId(j+200);
                    spin.setGravity(Gravity.CENTER);
                    spin.setBackgroundResource(R.drawable.ic_down);
                    if (getAlldataList.get(j).get("party_type").equals("1")) {
                        o_count += 1;

                        tv.setText("  Owner" + String.valueOf(o_count) + "-" + getAlldataList.get(j).get("name"));

                        if (getAlldataList.get(j).get("biometric").equals("null")) {
                            values.add("Select");
                            values.add("Yes");
                            values.add("Thumb Not Match");
                        }
                        if (getAlldataList.get(j).get("biometric").equals("false")) {
                            values.add("Thumb Not Match");
                            values.add("Yes");
                            values.add("Select");
                        }
                        if (getAlldataList.get(j).get("biometric").equals("true")) {
                            values.add("Yes");
                            values.add("Thumb Not Match");
                            values.add("Select");
                        }
                    }
                    if (getAlldataList.get(j).get("party_type").equals("2")) {

                        t_count += 1;


                        tv.setText("  Tenant" + String.valueOf(t_count) + "-" + getAlldataList.get(j).get("name"));
                        if (getAlldataList.get(j).get("biometric").equals("null")) {
                            values.add("Select");
                            values.add("Yes");
                            values.add("Thumb Not Match");
                        }
                        if (getAlldataList.get(j).get("biometric").equals("false")) {
                            values.add("Thumb Not Match");
                            values.add("Yes");
                            values.add("Select");
                        }
                        if (getAlldataList.get(j).get("biometric").equals("true")) {
                            values.add("Yes");
                            values.add("Thumb Not Match");
                            values.add("Select");
                        }
                    }
                    if (getAlldataList.get(j).get("party_type").equals("3")) {

                        w_count += 1;
                        if (w_count == 2) {

                            biow1.setVisibility(GONE);
                            rg.setVisibility(GONE);
                            biow2.setVisibility(GONE);
                            rg1.setVisibility(GONE);


                        } else if (w_count == 1 && aw_count == 1) {

                            biow1.setVisibility(GONE);
                            rg.setVisibility(GONE);
                            biow2.setVisibility(GONE);
                            rg1.setVisibility(GONE);

                        } else if (w_count == 1) {

                            biow2.setVisibility(View.VISIBLE);
                            rg1.setVisibility(View.VISIBLE);

                        } else if (w_count == 0) {

                            biow1.setVisibility(View.VISIBLE);
                            rg.setVisibility(View.VISIBLE);
                            biow2.setVisibility(View.VISIBLE);
                            rg1.setVisibility(View.VISIBLE);
                        } else if (w_count > 2) {
                            biow1.setVisibility(GONE);
                            rg.setVisibility(GONE);
                            biow2.setVisibility(GONE);
                            rg1.setVisibility(GONE);
                        }

                        if (getAlldataList.get(j).get("name").equals("") || getAlldataList.get(j).get("name").equals("null")) {

                            tv.setText("  Witness" + String.valueOf(w_count));

                        } else {
                            tv.setText("  Witness" + String.valueOf(w_count) + "-" + getAlldataList.get(j).get("name"));

                        }                        if (getAlldataList.get(j).get("biometric").equals("null")) {
                            values.add("Select");
                            values.add("Yes");
                            values.add("Thumb Not Match");
                        }
                        if (getAlldataList.get(j).get("biometric").equals("false")) {
                            values.add("Thumb Not Match");
                            values.add("Yes");
                            values.add("Select");
                        }
                        if (getAlldataList.get(j).get("biometric").equals("true")) {
                            values.add("Yes");
                            values.add("Thumb Not Match");
                            values.add("Select");
                        }
                    }
                    if (getAlldataList.get(j).get("party_type").equals("4")) {
                        poa_count += 1;
                        tv.setText("  POA" + String.valueOf(poa_count) + "-" + getAlldataList.get(j).get("name"));
                        if (getAlldataList.get(j).get("biometric").equals("null")) {
                            values.add("Select");
                            values.add("Yes");
                            values.add("Thumb Not Match");
                        }
                        if (getAlldataList.get(j).get("biometric").equals("false")) {
                            values.add("Thumb Not Match");
                            values.add("Yes");
                            values.add("Select");
                        }
                        if (getAlldataList.get(j).get("biometric").equals("true")) {
                            values.add("Yes");
                            values.add("Thumb Not Match");
                            values.add("Select");
                        }
                    }
                    if (getAlldataList.get(j).get("party_type").equals("5")) {


                        aw_count += 1;
                        if (aw_count == 2) {
                            biow1.setVisibility(GONE);
                            rg.setVisibility(GONE);
                            biow2.setVisibility(GONE);
                            rg1.setVisibility(GONE);
                        } else if (w_count == 1 && aw_count == 1) {

                            biow1.setVisibility(GONE);
                            rg.setVisibility(GONE);
                            biow2.setVisibility(GONE);
                            rg1.setVisibility(GONE);
                        } else if (aw_count == 1) {

                            biow2.setVisibility(View.VISIBLE);
                            rg1.setVisibility(View.VISIBLE);

                        } else if (aw_count == 0) {

                            biow1.setVisibility(View.VISIBLE);
                            rg.setVisibility(View.VISIBLE);
                            biow2.setVisibility(View.VISIBLE);
                            rg1.setVisibility(View.VISIBLE);
                        } else if (aw_count > 2) {
                            biow1.setVisibility(GONE);
                            rg.setVisibility(GONE);
                            biow2.setVisibility(GONE);
                            rg1.setVisibility(GONE);
                        }

                        if (getAlldataList.get(j).get("name").equals("") || getAlldataList.get(j).get("name").equals("null")) {

                            tv.setText("  Anulom Witness" + String.valueOf(aw_count));

                        } else {
                            tv.setText("  Anulom Witness" + String.valueOf(aw_count) + "-" + getAlldataList.get(j).get("name"));

                        }
                        if (getAlldataList.get(j).get("biometric").equals("null")) {
                            values.add("Select");
                            values.add("Yes");
                            values.add("No");
                        }
                        if (getAlldataList.get(j).get("biometric").equals("false")) {
                            values.add("No");
                            values.add("Yes");
                            values.add("Select");
                        }
                        if (getAlldataList.get(j).get("biometric").equals("true")) {
                            values.add("Yes");
                            values.add("No");
                            values.add("Select");
                        }
                    }


                    rootView.addView(tv);


                    ArrayAdapter<String> adapterspin = new ArrayAdapter<>(this, R.layout.witnesslay, values);
                    adapterspin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin.setAdapter(adapterspin);
                    rootView.addView(spin);

                }


            }


        }


        for (int j = 0; j < getAlldataList.size(); j++) {

            if (getAlldataList.get(j).get("document_id").equals(document)) {

                if (getAlldataList.get(j).get("party_type").equals("1") && Integer.valueOf(getAlldataList.get(j).get("poa")) > 0) {


                } else {

                    Spinner sp = findViewById(j+200);

                    sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

                    {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            count = count + 1;
                            value = "false";
                            item = parent.getSelectedItem().toString();
                            pos = position;
                            ID = parent.getId();
                            ID=ID-200;

                            if (count > n_parties) {

                                if (IDlist.size() == 0) {

                                    System.out.println("Size:"+IDlist.size());

                                    itemlist.add(item);
                                    poslist.add(pos);
                                    IDlist.add(ID);
                                    attendancelist.add(getAlldataList.get(ID).get("att_id"));
                                    emaillist.add(getAlldataList.get(ID).get("email"));
                                    partytypelist.add(getAlldataList.get(ID).get("party_type"));


                                } else if (IDlist.size() > 0) {

                                    for (int z = 0; z < IDlist.size(); z++) {
                                        if (ID == IDlist.get(z)) {
                                            IDlist.remove(z);
                                            itemlist.remove(z);
                                            poslist.remove(z);
                                            attendancelist.remove(z);
                                            emaillist.remove(z);
                                            partytypelist.remove(z);
                                            itemlist.add(z, item);
                                            poslist.add(z, pos);
                                            IDlist.add(z, ID);
                                            attendancelist.add(z, getAlldataList.get(ID).get("att_id"));
                                            emaillist.add(z, getAlldataList.get(ID).get("email"));
                                            partytypelist.add(z, getAlldataList.get(ID).get("party_type"));
                                            value = "true";
                                        }
                                    }
                                    if (value.equals("false") && (IDlist.size() > 0) && (IDlist.size() < getAlldataList.size())) {

                                        itemlist.add(item);
                                        poslist.add(pos);
                                        IDlist.add(ID);
                                        attendancelist.add(getAlldataList.get(ID).get("att_id"));
                                        emaillist.add(getAlldataList.get(ID).get("email"));
                                        partytypelist.add(getAlldataList.get(ID).get("party_type"));
                                    }
                                }
                            } else {

                                System.out.println("No insert required");

                            }
                        }


                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }
        }

        rootView.addView(Anulomwitness);
        rootView.addView(awitness1);
        rootView.addView(Anulomwitness1);
        rootView.addView(awitness2);
        rootView.addView(customerwitness);
        rootView.addView(cwitness1);
        rootView.addView(customerwitness1);
        rootView.addView(cwitness2);

        Button update2 = new Button(this);
        update2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        update2.setGravity(Gravity.CENTER);
        update2.setText("UPDATE");
        update2.setId(k);
        update2.setBackgroundColor(Color.parseColor("#3b5998"));
        update2.setTextColor(Color.WHITE);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) update2.getLayoutParams();
        params.setMargins(50, 20, 50, 10);
        update2.setLayoutParams(params);
        rootView.addView(update2);
        if (fla == 1) {
            update2.setVisibility(GONE);
        }

        if ((w_count == 1 && aw_count == 1) || (w_count == 1 || aw_count == 1) || (w_count == 0 && aw_count == 0)) {

            update2.setText("CONFIRM WITNESS AND UPDATE");

        }
        update2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SQLiteDatabase sqldb1 = db.getWritableDatabase();
                ContentValues values1 = new ContentValues();
                values1.put(DBManager.TableInfo.postdocument, document);
                int count = sqldb1.update(POSTDOC, values1, DBManager.TableInfo.postdocument + "=?", new String[]{document});
                sqldb1.close();
                finish();


                if (count == 0) {

                    SQLiteDatabase dbb = db.getWritableDatabase();
                    ContentValues valuess = new ContentValues();
                    valuess.put(DBManager.TableInfo.KEYID, ID1);
                    valuess.put(DBManager.TableInfo.postdocument, document);
                    valuess.put(DBManager.TableInfo.KEY_LOGIN_USER, username2);
                    String conditionn = DBManager.TableInfo.postdocument + " =?";
                    Cursor cursorr = dbb.query(DBManager.TableInfo.POSTDOC, null, conditionn, new String[]{DBManager.TableInfo.postdocument}, null, null, null);
                    long statuss = dbb.insert(DBManager.TableInfo.POSTDOC, null, valuess);
                    cursorr.close();
                    dbb.close();
                    finish();
                }
                long statusbio = 0;
                for (int i = 0; i < poslist.size(); i++) {
                    if (itemlist.get(i).equals("Yes")) {

                        itemlist.remove(i);
                        itemlist.add(i, "true");
                    } else if (itemlist.get(i).equals("Thumb Not Match") || itemlist.get(i).equals("No")) {

                        itemlist.remove(i);
                        itemlist.add(i, "false");
                    } else {

                        itemlist.remove(i);
                        itemlist.add(i, "null");
                    }

                    SQLiteDatabase sqldb = db.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put(DBManager.TableInfo.BIOMETRIC, itemlist.get(i));
                    int count1 = sqldb.update(DBManager.TableInfo.PARTIES, values, DBManager.TableInfo.ATTENDANCE + "=?", new String[]{attendancelist.get(i)});
                    sqldb.close();



                    SQLiteDatabase db7 = db.getWritableDatabase();
                    ContentValues values5 = new ContentValues();
                    values5.put(DBManager.TableInfo.KEYID, ID1);
                    values5.put(DBManager.TableInfo.DOCU, document);
                    values5.put(DBManager.TableInfo.ATTEND, attendancelist.get(i));
                    values5.put(DBManager.TableInfo.EMAIL, emaillist.get(i));
                    values5.put(DBManager.TableInfo.PARTY, partytypelist.get(i));
                    values5.put(DBManager.TableInfo.BIO, itemlist.get(i));
                    values5.put(DBManager.TableInfo.biotype, "1");
                    values5.put(DBManager.TableInfo.KEY_LOGIN_USER, username2);
                    values5.put(DBManager.TableInfo.updatelocation, locationstring);
                    values5.put(DBManager.TableInfo.updatetime, localTime);
                    values5.put(DBManager.TableInfo.AppointmentId, appointment);
                    String condition5 = DBManager.TableInfo.ATTEND + " =?";
                    Cursor cursor5 = db7.query(DBManager.TableInfo.UPDATEPARTY, null, condition5, new String[]{attendancelist.get(i)}, null, null, null);
                    statusbio = db7.insert(DBManager.TableInfo.UPDATEPARTY, null, values5);
                    cursor5.close();
                    db7.close();
                }

                if (statusbio > 0) {
                    if (GenericMethods.isConnected(getApplicationContext())) {

                        if (GenericMethods.isOnline()) {

                            Toast.makeText(newstatusinfo.this, "System is trying to connect One Platform..Make Sure Internet Connect is Available  ", Toast.LENGTH_LONG).show();
                            call = "true";
                            System.out.println("call" + call);
                            Intent i1 = new Intent(newstatusinfo.this, SendmultiPartyReport.class);
                            startService(i1);
                            finish();



                        } else {
                            Toast.makeText(newstatusinfo.this, "Biometric Data Saved Offline,App is Waiting for Active Network Connection", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Toast.makeText(newstatusinfo.this, "Biometric Data Saved Offline", Toast.LENGTH_LONG).show();

                    }
                } else {
                    Toast.makeText(newstatusinfo.this, "Change Any biometric,And then Update", Toast.LENGTH_LONG).show();

                }
                itemlist.clear();
                poslist.clear();
                IDlist.clear();
                attendancelist.clear();
                partytypelist.clear();
                emaillist.clear();


                if (check1.isChecked()) {

                    status = "0";

                    reason = slotime1.getText().toString();
                } else if (check2.isChecked()) {
                    status = "1";


                } else if (check3.isChecked()) {
                    status = "2";

                    reason = slottime.getText().toString();
                } else if (check4.isChecked()) {
                    status = "3";


                }

                if (check1.isChecked() || check2.isChecked() || check3.isChecked() || check4.isChecked()) {
                    if (!status.equals(postStatus)) {


                        SQLiteDatabase sqldb2 = db.getWritableDatabase();
                        ContentValues values2 = new ContentValues();
                        values2.put(DBManager.TableInfo.post_status, status);
                        int count4 = sqldb2.update(DBManager.TableInfo.TABLE_NAME_APPOINTMENT, values2, DBManager.TableInfo.AppointmentId + "=?", new String[]{appointment});
                        sqldb2.close();


                        SQLiteDatabase db0 = db.getWritableDatabase();
                        ContentValues values5 = new ContentValues();
                        values5.put(DBManager.TableInfo.KEYID, ID1);
                        values5.put(DBManager.TableInfo.DOCU, document);
                        values5.put(DBManager.TableInfo.AppointmentId, appointment);
                        values5.put(DBManager.TableInfo.STATUS, status);
                        values5.put(DBManager.TableInfo.reason, reason);
                        values5.put(DBManager.TableInfo.KEY_LOGIN_USER, username2);
                        String condition5 = DBManager.TableInfo.AppointmentId + " =?";
                        Cursor cursor5 = db0.query(MULTIWORK, null, condition5, new String[]{appointment}, null, null, null);
                        long status5 = db0.insert(MULTIWORK, null, values5);
                        cursor5.close();
                        db0.close();

                        if (status.equals("0") || status.equals("2")) {

                            if (!reason.equals("")) {
                                if (GenericMethods.isConnected(getApplicationContext())) {

                                    if (GenericMethods.isOnline()) {


                                        Toast.makeText(newstatusinfo.this, "System is trying to connect One Platform..Make Sure Internet Connect is Available", Toast.LENGTH_LONG).show();
                                        Intent i2 = new Intent(newstatusinfo.this, AppointmentstatusPost.class);
                                        startService(i2);
                                        finish();
                                    } else {
                                        Toast.makeText(newstatusinfo.this, " Appointment Data Saved Offline,App is Waiting for active network connection", Toast.LENGTH_LONG).show();

                                    }
                                } else {
                                    Toast.makeText(newstatusinfo.this, " Appointment Data Saved Offline,App is Waiting for active network connection", Toast.LENGTH_LONG).show();


                                }
                            } else {
                                Toast.makeText(newstatusinfo.this, " Enter the reason and update", Toast.LENGTH_LONG).show();

                            }


                        } else {


                            if (GenericMethods.isConnected(getApplicationContext())) {

                                if (GenericMethods.isOnline()) {

                                    if (status5 > 0) {

                                        Toast.makeText(newstatusinfo.this, "System is trying to connect One Platform..Make Sure Internet Connect is Available", Toast.LENGTH_LONG).show();
                                        Intent i2 = new Intent(newstatusinfo.this, AppointmentstatusPost.class);
                                        startService(i2);
                                        finish();
                                    } else {
                                        Toast.makeText(newstatusinfo.this, "Enter the reason and update", Toast.LENGTH_LONG).show();

                                    }

                                } else {
                                    Toast.makeText(newstatusinfo.this, " Appointment Data Saved Offline,App is Waiting for active network connection", Toast.LENGTH_LONG).show();

                                }
                            } else {
                                Toast.makeText(newstatusinfo.this, " Appointment Data Saved Offline", Toast.LENGTH_LONG).show();

                            }


                        }


                    }
                }else {
                    Toast.makeText(newstatusinfo.this, "Change any status to Update!!", Toast.LENGTH_LONG).show();

                }


                if (flag_witness == "0") {

                    SQLiteDatabase sqldbwit1 = db.getWritableDatabase();
                    ContentValues valueswit1 = new ContentValues();
                    valueswit1.put(DBManager.TableInfo.postwitnessdocument, document);
                    int count1 = sqldbwit1.update(POSTWITNESSDOC, valueswit1, DBManager.TableInfo.postwitnessdocument + "=?", new String[]{document});
                    sqldbwit1.close();
                    finish();


                    if (count1 == 0) {

                        SQLiteDatabase dbb = db.getWritableDatabase();
                        ContentValues valuess = new ContentValues();
                        valuess.put(DBManager.TableInfo.KEYID, ID1);
                        valuess.put(DBManager.TableInfo.postwitnessdocument, document);
                        valuess.put(DBManager.TableInfo.KEY_LOGIN_USER, username2);
                        String conditionn = DBManager.TableInfo.postwitnessdocument + " =?";
                        Cursor cursorr = dbb.query(DBManager.TableInfo.POSTWITNESSDOC, null, conditionn, new String[]{document}, null, null, null);
                        long statuss = dbb.insert(DBManager.TableInfo.POSTWITNESSDOC, null, valuess);

                        cursorr.close();
                        dbb.close();
                        finish();
                    }

                    if (flag == "true") {

                        SQLiteDatabase db0 = db.getWritableDatabase();
                        ContentValues values5 = new ContentValues();
                        values5.put(DBManager.TableInfo.KEYID, ID1);
                        values5.put(DBManager.TableInfo.witness_docid, document);
                        values5.put(DBManager.TableInfo.internal_witness_userid1, wit_user_ID);
                        values5.put(DBManager.TableInfo.internal_witness_email1, wit_email1);
                        values5.put(DBManager.TableInfo.internal_witness_userid2, wit_user_ID1);
                        values5.put(DBManager.TableInfo.internal_witness_email2, wit_email2);
                        values5.put(DBManager.TableInfo.internal_witness_type, witness_type);
                        values5.put(DBManager.TableInfo.external_witness_type, witness_type1);
                        values5.put(DBManager.TableInfo.external_witness_email1, witness_no1);
                        values5.put(DBManager.TableInfo.external_witness_email2, witness_no2);
                        values5.put(DBManager.TableInfo.internal_biometric1, awitness1value);
                        values5.put(DBManager.TableInfo.internal_biometric2, awitness2value);
                        values5.put(DBManager.TableInfo.external_biometric1, cwitness1value);
                        values5.put(DBManager.TableInfo.external_biometric2, cwitness2value);
                        values5.put(DBManager.TableInfo.KEY_LOGIN_USER, username2);
                        String condition5 = DBManager.TableInfo.witness_docid + " =?";
                        Cursor cursor5 = db0.query(TABLE_WITNESS, null, condition5, new String[]{document}, null, null, null);
                        long status5 = db0.insert(TABLE_WITNESS, null, values5);
                        cursor5.close();
                        db0.close();
                        if (GenericMethods.isConnected(getApplicationContext())) {

                            if (GenericMethods.isOnline()) {
                                if (status5 > 0) {

                                    Toast.makeText(newstatusinfo.this, "Internal Witness Updated  Successfully  ", Toast.LENGTH_SHORT).show();
                                    Intent i2 = new Intent(newstatusinfo.this, InternalExternalWitness.class);
                                    startService(i2);
                                    finish();
                                }
                            } else {
                                Toast.makeText(newstatusinfo.this, " Internal Witness Saved Offline,App is waiting for active network connection", Toast.LENGTH_LONG).show();

                            }

                        } else {
                            Toast.makeText(newstatusinfo.this, " Internal Witness Saved Offline", Toast.LENGTH_LONG).show();

                        }

                    }

                }




            }
        });


    }


    public void removefunction(LinearLayout rootView) {

      

        for (int j = 0; j < getAlldataList.size(); j++) {


            if (getAlldataList.get(j).get("document_id").equals(document)) {

                if (getAlldataList.get(j).get("party_type").equals("1") && Integer.valueOf(getAlldataList.get(j).get("poa")) > 0) {

                } else {
                    n_parties = n_parties + 1;

                    TextView tv = findViewById(j + 100);
                    rootView.removeView(tv);
                    Spinner spin = findViewById(j + 200);
                    rootView.removeView(spin);


                }
            }
        }
        rootView.removeView(Anulomwitness);
        rootView.removeView(awitness1);
        rootView.removeView(Anulomwitness1);
        rootView.removeView(awitness2);
        rootView.removeView(customerwitness);
        rootView.removeView(cwitness1);
        rootView.removeView(customerwitness1);
        rootView.removeView(cwitness2);

        Button update2 = findViewById(k);
        rootView.removeView(update2);

    }


}








