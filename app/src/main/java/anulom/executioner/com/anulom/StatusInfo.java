package anulom.executioner.com.anulom;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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

import anulom.executioner.com.anulom.services.StatusReportService;
import anulom.executioner.com.anulom.services.AppointmentstatusPost;

import static anulom.executioner.com.anulom.R.id.spinnerO;
import static anulom.executioner.com.anulom.R.id.spinnerT;

import static anulom.executioner.com.anulom.database.DBManager.TableInfo.MULTIWORK;


public class StatusInfo extends AppCompatActivity implements LocationListener, OnItemSelectedListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    String rkey, biocomp, biocomp1, docid, regfromcomp, witness, postStatus;
    TextView tvreqNo, t1, t2;
    EditText e1, e2;
    Button update;
    DBOperation database;
    CheckBox check, check1, check2, check3;
    private SharedPreferences usermail;
    String ID, username2 = "", password2 = "", status, flag = "0", appointment, reason = "";
    String item, item1, locationstring, item2, item3, itemS = "", item1S = "", item2S = "", item3S = "";
    List<String> statuslist = new ArrayList<>();
    Location mLocation;
    protected GoogleMap map;
    int fla;
    private final static int REQUEST_CHECK_SETTINGS_GPS = 0x1;
    private final static int REQUEST_ID_MULTIPLE_PERMISSIONS = 0x2;
    private List<Polyline> polylines;
    protected GoogleApiClient mGoogleApiClient;
    private static final String LOG_TAG = "statusinfo";
    Double currentlat, currentlong;

    @Override
    public void onLocationChanged(final Location location) {

        if (location != null) {
            Log.d(LOG_TAG, String.format("%f, %f", location.getLatitude(), location.getLongitude()));


            //mLocationManager.removeUpdates(mLocationListener);
        } else {
            Log.d(LOG_TAG, "Location is null");
        }


    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statusinfo);
        database = new DBOperation(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Biometric Status");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
//		getSupportActionBar().setIcon(R.drawable.icon);
        usermail = PreferenceManager.getDefaultSharedPreferences(this);
        username2 = usermail.getString("username", "");
        password2 = usermail.getString("password", "");
        t1 = findViewById(R.id.text3);
        t2 = findViewById(R.id.status2);
        final Spinner spinnerO = findViewById(R.id.spinnerO);
        final Spinner spinnerT = findViewById(R.id.spinnerT);
        final Spinner spinnerW1 = findViewById(R.id.spinnerw1);
        final Spinner spinnerW2 = findViewById(R.id.spinnerw2);
        e1 = findViewById(R.id.edit3);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
//                GenericMethods.ctr2 = 0;
//                GenericMethods.ctr3 = 0;

            }
        });


        Calendar cal = Calendar.getInstance();
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("HH:mm a");
        final String localTime = date.format(currentLocalTime);


        rkey = getIntent().getStringExtra("ReportKey");
        docid = getIntent().getStringExtra("DocumentId");
        biocomp = getIntent().getStringExtra("BiometricComp");
        biocomp1 = getIntent().getStringExtra("BiometricComp1");
        regfromcomp = getIntent().getStringExtra("Reg_From_Comp");
        witness = getIntent().getStringExtra("Witness");
        appointment = getIntent().getStringExtra("appointment_id");
        postStatus = getIntent().getStringExtra("post_status");

        fla = getIntent().getIntExtra("flag", fla);
        if (fla == 0) {
            mGoogleApiClient = new GoogleApiClient.Builder(StatusInfo.this)
                    .addApi(LocationServices.API)
                    .addApi(Places.GEO_DATA_API)
                    .addConnectionCallbacks(StatusInfo.this)
                    .addOnConnectionFailedListener(this)
                    .build();
            MapsInitializer.initialize(StatusInfo.this);
            mGoogleApiClient.connect();
            getCurrentLocation();
        }
        check = findViewById(R.id.wrkcomp);
        check1 = findViewById(R.id.wrkcomp1);
        check2 = findViewById(R.id.wrkcomp2);
        check3 = findViewById(R.id.wrkcomp3);
        final TextView biometric = findViewById(R.id.status1);
        final TextView Ownerbio = findViewById(R.id.tvownerbio);
        final TextView tenantbio = findViewById(R.id.tvtenantbio);
        final TextView witness1bio = findViewById(R.id.tvwitness1bio);
        final TextView witness2bio = findViewById(R.id.tvwitness2bio);
        e2 = findViewById(R.id.edit4);
        String key1 = "Request No: " + rkey;
        tvreqNo = findViewById(R.id.tvreqno);
        tvreqNo.setText(key1);
        tvreqNo.setTextIsSelectable(true);
        update = findViewById(R.id.btnupdate);
        ArrayList<HashMap<String, String>> statuslist = database.getmultipartycheck(database);
        for (int i = 0; i < statuslist.size(); i++) {
            if (docid.equals(statuslist.get(i).get("docid"))) {
                postStatus = statuslist.get(i).get("status");

            }
        }
        if (postStatus.equals(null)) {

            check.setVisibility(View.VISIBLE);
            check1.setVisibility(View.VISIBLE);
            check2.setVisibility(View.VISIBLE);
            check3.setVisibility(View.VISIBLE);
        }
        if (postStatus.equals("1")) {
            check.setChecked(true);
            biometric.setVisibility(View.VISIBLE);
            Ownerbio.setVisibility(View.VISIBLE);
            tenantbio.setVisibility(View.VISIBLE);
            witness1bio.setVisibility(View.VISIBLE);
            witness2bio.setVisibility(View.VISIBLE);
            spinnerO.setVisibility(View.VISIBLE);
            spinnerT.setVisibility(View.VISIBLE);
            spinnerW1.setVisibility(View.VISIBLE);
            spinnerW2.setVisibility(View.VISIBLE);

        }
        if (postStatus.equals("0")) {
            check1.setChecked(true);

        }
        if (postStatus.equals("2")) {
            check2.setChecked(true);

        }
        if (postStatus.equals("3")) {
            check3.setChecked(true);

        }
        if (postStatus.equals("99")) {

            check.setVisibility(View.GONE);
            check1.setVisibility(View.GONE);
            check2.setVisibility(View.GONE);
            check3.setVisibility(View.GONE);
            t2.setVisibility(View.GONE);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(StatusInfo.this, "Document is too old,Please Contact Operation team!!", Toast.LENGTH_LONG).show();


                }
            });
        }


        if (fla == 1) {
            update.setVisibility(View.INVISIBLE);


        }
        update.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DBOperation db = new DBOperation(StatusInfo.this);
                long result = db.UpdateAllRecord(db, docid, itemS, item1S, item2S, item3S, localTime, locationstring, appointment);
                //Update Successfully
//				if(itemS==""||item1S==""||item2S==""||item3S==""){
//				}

                /*---------- Call  Appointment Status Web service -----------*/
                if (GenericMethods.isConnected(getApplicationContext())) {
                    if (result == 1) {
                        Toast.makeText(StatusInfo.this, "Biometric Data inserted Successfully", Toast.LENGTH_LONG).show();
                    }
                    Intent intent = new Intent(StatusInfo.this, StatusReportService.class);
                    startService(intent);
                } else {
                    Toast.makeText(StatusInfo.this, " Biometric Data Saved Offline", Toast.LENGTH_LONG).show();

                }
                finish();
//                GenericMethods.ctr2 = 0;
//                GenericMethods.ctr3 = 0;
                if (check.isChecked()) {

                    status = "1";
                    flag = "1";

                }
                if (check1.isChecked()) {


                    status = "0";
                    flag = "1";


                }

                if (check2.isChecked()) {

                    status = "2";
                    flag = "1";
                }

                if (check3.isChecked()) {

                    status = "3";
                    flag = "1";
                }

                if (flag == "1") {

                    SQLiteDatabase db0 = database.getWritableDatabase();
                    ContentValues values5 = new ContentValues();
                    values5.put(DBManager.TableInfo.KEYID, ID);
                    values5.put(DBManager.TableInfo.DOCU, docid);
                    values5.put(DBManager.TableInfo.AppointmentId, appointment);
                    values5.put(DBManager.TableInfo.STATUS, status);
                    values5.put(DBManager.TableInfo.reason, reason);
                    values5.put(DBManager.TableInfo.KEY_LOGIN_USER, username2);
                    String condition5 = DBManager.TableInfo.DOCU + " =?";
                    Cursor cursor5 = db0.query(MULTIWORK, null, condition5, new String[]{DBManager.TableInfo.AppointmentId}, null, null, null);
                    long status5 = db0.insert(MULTIWORK, null, values5);

                    cursor5.close();
                    db0.close();
                    if (GenericMethods.isConnected(getApplicationContext())) {
                        Toast.makeText(StatusInfo.this, "Appointment Data inserted Successfully  ", Toast.LENGTH_LONG).show();
                        Intent i2 = new Intent(StatusInfo.this, AppointmentstatusPost.class);
                        startService(i2);
                        flag = "0";
                    } else {
                        Toast.makeText(StatusInfo.this, " Appointment Data Saved Offline", Toast.LENGTH_LONG).show();

                    }


                }
                finish();


            }
        });
        final DBOperation db = new DBOperation(StatusInfo.this);
        ArrayList<HashMap<String, String>> selStatus = db.getSelectedData(db, docid);
        if (selStatus.size() > 0) {
            biocomp1 = selStatus.get(0).get("OwnBio");
            biocomp = selStatus.get(0).get("TenantBio");
            regfromcomp = selStatus.get(0).get("Wit1");
            witness = selStatus.get(0).get("Wit2");
        }
        // Spinner element


        // Spinner click listener
        spinnerO.setOnItemSelectedListener(this);
        spinnerT.setOnItemSelectedListener(this);
        spinnerW1.setOnItemSelectedListener(this);
        spinnerW2.setOnItemSelectedListener(this);

        List<String> categoriesT = new ArrayList<>();
        if (biocomp.equals("1")) {
            categoriesT.add("Yes");
            categoriesT.add("Thumb Not Match");
            categoriesT.add("");
        } else if (biocomp.equals("0")) {
            categoriesT.add("Thumb Not Match");
            categoriesT.add("Yes");
            categoriesT.add("");
        } else {
            categoriesT.add("");
            categoriesT.add("Thumb Not Match");
            categoriesT.add("Yes");
        }

        List<String> categoriesO = new ArrayList<>();
        if (biocomp1.equals("1")) {
            categoriesO.add("Yes");
            categoriesO.add("Thumb Not Match");
            categoriesO.add("");
        } else if (biocomp1.equals("0")) {
            categoriesO.add("Thumb Not Match");
            categoriesO.add("Yes");
            categoriesO.add("");
        } else {
            categoriesO.add("");
            categoriesO.add("Thumb Not Match");
            categoriesO.add("Yes");
        }

        List<String> categoriesW1 = new ArrayList<>();
        if (regfromcomp.equals("1")) {
            categoriesW1.add("Yes");
            categoriesW1.add("No");
            categoriesW1.add("");
        } else if (regfromcomp.equals("0")) {
            categoriesW1.add("No");
            categoriesW1.add("Yes");
            categoriesW1.add("");
        } else {
            categoriesW1.add("");
            categoriesW1.add("No");
            categoriesW1.add("Yes");
        }

        List<String> categoriesW2 = new ArrayList<>();
        if (witness.equals("1")) {
            categoriesW2.add("Yes");
            categoriesW2.add("No");
            categoriesW2.add("");

        } else if (witness.equals("0")) {
            categoriesW2.add("No");
            categoriesW2.add("Yes");
            categoriesW2.add("");
        } else {
            categoriesW2.add("");
            categoriesW2.add("No");
            categoriesW2.add("Yes");
        }

        List<String> categoriesPayments = new ArrayList<>();
        categoriesPayments.add("");
        categoriesPayments.add("Stampduty Shortfall");
        categoriesPayments.add("Extra Visit");
        categoriesPayments.add("Partial Payment");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterO = new ArrayAdapter<>(this, R.layout.spinnertextdisplay,
                categoriesO);

        ArrayAdapter<String> dataAdapterT = new ArrayAdapter<>(this, R.layout.spinnertextdisplay,
                categoriesT);

        ArrayAdapter<String> dataAdapterW1 = new ArrayAdapter<>(this, R.layout.spinnertextdisplay,
                categoriesW1);

        ArrayAdapter<String> dataAdapterW2 = new ArrayAdapter<>(this, R.layout.spinnertextdisplay,
                categoriesW2);

        ArrayAdapter<String> dataAdapterPayments = new ArrayAdapter<>(this, R.layout.spinnertextdisplay,
                categoriesPayments);

        // Drop down layout style - list view with radio button
        dataAdapterO.setDropDownViewResource(R.layout.spinnertextdisplay);
        dataAdapterT.setDropDownViewResource(R.layout.spinnertextdisplay);
        dataAdapterW1.setDropDownViewResource(R.layout.spinnertextdisplay);
        dataAdapterW2.setDropDownViewResource(R.layout.spinnertextdisplay);
        dataAdapterPayments.setDropDownViewResource(R.layout.spinnertextdisplay);

        // attaching data adapter to spinner
        spinnerO.setAdapter(dataAdapterO);
        spinnerT.setAdapter(dataAdapterT);
        spinnerW1.setAdapter(dataAdapterW1);
        spinnerW2.setAdapter(dataAdapterW2);
        // spinnerPayment.setAdapter(dataAdapterPayments);
//        SQLiteDatabase dbDetails_comm = database.getWritableDatabase();
//		dbDetails_comm.delete(TABLE_CHECK_JOB, null, null);
//


        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                username2 = usermail.getString("username", "");
                if (check.isChecked()) {
                    t1.setVisibility(View.GONE);
                    e1.setVisibility(View.GONE);

                    check1.setChecked(false);
                    check2.setChecked(false);
                    check3.setChecked(false);

                    biometric.setVisibility(View.VISIBLE);
                    Ownerbio.setVisibility(View.VISIBLE);
                    tenantbio.setVisibility(View.VISIBLE);
                    witness1bio.setVisibility(View.VISIBLE);
                    witness2bio.setVisibility(View.VISIBLE);
                    spinnerO.setVisibility(View.VISIBLE);
                    spinnerT.setVisibility(View.VISIBLE);
                    spinnerW1.setVisibility(View.VISIBLE);
                    spinnerW2.setVisibility(View.VISIBLE);


                } else {

                    biometric.setVisibility(View.INVISIBLE);
                    Ownerbio.setVisibility(View.INVISIBLE);
                    tenantbio.setVisibility(View.INVISIBLE);
                    witness1bio.setVisibility(View.INVISIBLE);
                    witness2bio.setVisibility(View.INVISIBLE);
                    spinnerO.setVisibility(View.INVISIBLE);
                    spinnerT.setVisibility(View.INVISIBLE);
                    spinnerW1.setVisibility(View.INVISIBLE);
                    spinnerW2.setVisibility(View.INVISIBLE);

                }

            }
        });


        check1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (check1.isChecked()) {

                    check.setChecked(false);
                    check2.setChecked(false);
                    check3.setChecked(false);
                    t1.setVisibility(View.VISIBLE);
                    e1.setVisibility(View.VISIBLE);
                    biometric.setVisibility(View.GONE);
                    Ownerbio.setVisibility(View.GONE);
                    tenantbio.setVisibility(View.GONE);
                    witness1bio.setVisibility(View.GONE);
                    witness2bio.setVisibility(View.GONE);
                    spinnerO.setVisibility(View.GONE);
                    spinnerT.setVisibility(View.GONE);
                    spinnerW1.setVisibility(View.GONE);
                    spinnerW2.setVisibility(View.GONE);

                } else {
                    t1.setVisibility(View.GONE);
                    e1.setVisibility(View.GONE);
                }
            }
        });


        check2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (check2.isChecked()) {
                    t1.setVisibility(View.VISIBLE);
                    e1.setVisibility(View.VISIBLE);

                    check.setChecked(false);
                    check1.setChecked(false);
                    check3.setChecked(false);

                    biometric.setVisibility(View.GONE);
                    Ownerbio.setVisibility(View.GONE);
                    tenantbio.setVisibility(View.GONE);
                    witness1bio.setVisibility(View.GONE);
                    witness2bio.setVisibility(View.GONE);
                    spinnerO.setVisibility(View.GONE);
                    spinnerT.setVisibility(View.GONE);
                    spinnerW1.setVisibility(View.GONE);
                    spinnerW2.setVisibility(View.GONE);

                } else {
                    t1.setVisibility(View.GONE);
                    e1.setVisibility(View.GONE);
                }
            }

        });
        check3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                //username2 = usermail.getString("username", "");
                if (check3.isChecked()) {


                    check.setChecked(false);
                    check1.setChecked(false);
                    check2.setChecked(false);

                    t1.setVisibility(View.GONE);
                    e1.setVisibility(View.GONE);
                    biometric.setVisibility(View.GONE);
                    Ownerbio.setVisibility(View.GONE);
                    tenantbio.setVisibility(View.GONE);
                    witness1bio.setVisibility(View.GONE);
                    witness2bio.setVisibility(View.GONE);
                    spinnerO.setVisibility(View.GONE);
                    spinnerT.setVisibility(View.GONE);
                    spinnerW1.setVisibility(View.GONE);
                    spinnerW2.setVisibility(View.GONE);

                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
//        GenericMethods.ctr2 = 0;
//        GenericMethods.ctr3 = 0;
//                    Intent i = new Intent(NextActivity.this, Login.class);
//
//                startActivity(i);


//
    }

    private void getCurrentLocation() {
        if (mGoogleApiClient != null) {
            if (mGoogleApiClient.isConnected()) {
                int permissionLocation = ContextCompat.checkSelfPermission(StatusInfo.this,
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
                            .requestLocationUpdates(mGoogleApiClient, locationRequest, StatusInfo.this);
                    PendingResult<LocationSettingsResult> result =
                            LocationServices.SettingsApi
                                    .checkLocationSettings(mGoogleApiClient, builder.build());
                    result.setResultCallback(new ResultCallback<LocationSettingsResult>() {

                        @Override
                        public void onResult(LocationSettingsResult result) {
                            final Status status = result.getStatus();
                            switch (status.getStatusCode()) {
                                case LocationSettingsStatusCodes.SUCCESS:
                                    // All location settings are satisfied.
                                    // You can initialize location requests here.
                                    int permissionLocation = ContextCompat
                                            .checkSelfPermission(StatusInfo.this,
                                                    Manifest.permission.ACCESS_FINE_LOCATION);
                                    if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                                        mLocation = LocationServices.FusedLocationApi
                                                .getLastLocation(mGoogleApiClient);


                                    }
                                    break;
                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                    // Location settings are not satisfied.
                                    // But could be fixed by showing the user a dialog.
                                    try {
                                        // Show the dialog by calling startResolutionForResult(),
                                        // and check the result in onActivityResult().
                                        // Ask to turn on GPS automatically
                                        status.startResolutionForResult(StatusInfo.this,
                                                REQUEST_CHECK_SETTINGS_GPS);
                                    } catch (IntentSender.SendIntentException e) {
                                        // Ignore the error.
                                    }
                                    break;
                                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                    // Location settings are not satisfied.
                                    // However, we have no way
                                    // to fix the
                                    // settings so we won't show the dialog.
                                    // finish();
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
        int permissionLocation = ContextCompat.checkSelfPermission(StatusInfo.this,
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
        int permissionLocation = ContextCompat.checkSelfPermission(StatusInfo.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        }
    }

    public void getLocationAddress(final Location location) {


        Geocoder geocoder = new Geocoder(StatusInfo.this, Locale.getDefault());
        // Get the current location from the input parameter list
        // Create a list to contain the result address


        List<Address> addresses = null;
        List<Address> addresses1 = null;

        try {
            addresses = geocoder.getFromLocation(currentlat, currentlong, 1);


        } catch (IOException e1) {
            e1.printStackTrace();

        } catch (IllegalArgumentException e2) {
            // Error message to post in the log
            String errorString = "Illegal arguments "
                    + Double.toString(location.getLatitude()) + " , "
                    + Double.toString(location.getLongitude())
                    + " passed to address service";
            e2.printStackTrace();

        }
        // If the reverse geocode returned an address
        if ((addresses != null && addresses.size() > 0)) {
            // Get the first address
            final Address address = addresses.get(0);


				/*
                 * Format the first line of address (if available), city, and
				 * country name.
				 */
            String addressText = String.format(
                    "%s, %s, %s",
                    // If there's a street address, add it
                    address.getMaxAddressLineIndex() > 0 ? address
                            .getAddressLine(0) : "",
                    // Locality is usually a city
                    address.getLocality(),
                    // The country of the address
                    address.getCountryName());
            //

            final List<Address> finalAddresses = addresses;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    e2.setText(finalAddresses.get(0).getAddressLine(0));
                    locationstring = e2.getText().toString();

                }
            });


        } else {
        }


    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // TODO Auto-generated method stub

        switch (parent.getId()) {
            // OWNER
            case spinnerO:
                item = parent.getItemAtPosition(position).toString();
                if (item.matches("Yes")) {
                    itemS = "1";

                } else {
                    if (item.matches("Thumb Not Match")) {
                        itemS = "0";
                    } else {
                        itemS = "";
                    }
                }
                break;
            // TENANT
            case spinnerT:
                item1 = parent.getItemAtPosition(position).toString();
                if (item1.matches("Yes")) {
                    item1S = "1";
                } else {
                    if (item1.matches("Thumb Not Match")) {
                        item1S = "0";
                    } else {
                        item1S = "";
                    }
                }
                break;
            // WITNESS1
            case R.id.spinnerw1:
                item2 = parent.getItemAtPosition(position).toString();

                if (item2.matches("Yes")) {
                    item2S = "1";
                } else {
                    if (item2.matches("No")) {
                        item2S = "0";
                    } else {
                        item2S = "";
                    }

                }
                break;
            // WITNESS2
            case R.id.spinnerw2:
                item3 = parent.getItemAtPosition(position).toString();
                if (item3.matches("Yes")) {
                    item3S = "1";
                } else {
                    if (item3.matches("No")) {
                        item3S = "0";
                    } else {
                        item3S = "";
                    }

                }
                break;
            default:

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub

    }


}