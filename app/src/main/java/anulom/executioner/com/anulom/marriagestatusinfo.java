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

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

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
import anulom.executioner.com.anulom.services.marriagebiometricpost;


import static anulom.executioner.com.anulom.R.id.spinnerO;
import static anulom.executioner.com.anulom.R.id.spinnerT;
import static anulom.executioner.com.anulom.database.DBManager.TableInfo.MULTIWORK;


public class marriagestatusinfo extends AppCompatActivity implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, OnItemSelectedListener {

    TextView tvreqNo, t1, t2, t3;
    EditText e1;
    Button update;
    DBOperation database;
    CheckBox check, check1, check2, check3;
    private SharedPreferences usermail;
    String ID, username2 = "", password2 = "", time, localTime, status, flag = "0", appointment, reason = "";
    int fla;
    String item, item1, locationstring, item2, item3, itemS = "", item1S = "", item2S = "", item3S = "";
    String rkey, biocomp, biocomp1, docid, regfromcomp, witness, postStatus;

    Location mLocation;
    protected GoogleMap map;
    private final static int REQUEST_CHECK_SETTINGS_GPS = 0x1;
    private final static int REQUEST_ID_MULTIPLE_PERMISSIONS = 0x2;

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
        setContentView(R.layout.salesmarriage);
        database = new DBOperation(this);

        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("Appointment Status");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
//		getSupportActionBar().setIcon(R.drawable.icon);
        usermail = PreferenceManager.getDefaultSharedPreferences(this);
        username2 = usermail.getString("username", "");
        password2 = usermail.getString("password", "");
        t1 = findViewById(R.id.text3);
        t2 = findViewById(R.id.status2);
        t3 = findViewById(R.id.textView12);
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
        localTime = date.format(currentLocalTime);
        time = getIntent().getStringExtra("time");
        rkey = getIntent().getStringExtra("rkey");
        docid = getIntent().getStringExtra("document_id");
        appointment = getIntent().getStringExtra("appointment_id");
        postStatus = getIntent().getStringExtra("post_status");
        fla = getIntent().getIntExtra("flag", fla);
        check = findViewById(R.id.wrkcomp);
        check1 = findViewById(R.id.wrkcomp1);
        check2 = findViewById(R.id.wrkcomp2);
        check3 = findViewById(R.id.wrkcomp3);
        final TextView biometric = findViewById(R.id.status1);
        final TextView Ownerbio = findViewById(R.id.tvownerbio);
        final TextView tenantbio = findViewById(R.id.tvtenantbio);
        final TextView witness1bio = findViewById(R.id.tvwitness1bio);
        final TextView witness2bio = findViewById(R.id.tvwitness2bio);
        if (fla == 0) {
            mGoogleApiClient = new GoogleApiClient.Builder(marriagestatusinfo.this)
                    .addApi(LocationServices.API)
                    .addApi(Places.GEO_DATA_API)
                    .addConnectionCallbacks(marriagestatusinfo.this)
                    .addOnConnectionFailedListener(this)
                    .build();
            MapsInitializer.initialize(marriagestatusinfo.this);
            mGoogleApiClient.connect();
            getCurrentLocation();
        }
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
            spinnerO.setVisibility(View.VISIBLE);
            spinnerT.setVisibility(View.VISIBLE);


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
                    Toast.makeText(marriagestatusinfo.this, "Document is too old,Please Contact Operation team!!", Toast.LENGTH_LONG).show();


                }
            });
        }

        final DBOperation db = new DBOperation(marriagestatusinfo.this);
        ArrayList<HashMap<String, String>> selStatus = db.getmarriageSelectedData(db, docid);
        if (selStatus.size() > 0) {
            biocomp1 = selStatus.get(0).get("husbio");
            biocomp = selStatus.get(0).get("wifeybio");

        }
        // Spinner element


        // Spinner click listener
        spinnerO.setOnItemSelectedListener(this);
        spinnerT.setOnItemSelectedListener(this);


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


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterO = new ArrayAdapter<>(this, R.layout.spinnertextdisplay,
                categoriesO);

        ArrayAdapter<String> dataAdapterT = new ArrayAdapter<>(this, R.layout.spinnertextdisplay,
                categoriesT);


        // Drop down layout style - list view with radio button
        dataAdapterO.setDropDownViewResource(R.layout.spinnertextdisplay);
        dataAdapterT.setDropDownViewResource(R.layout.spinnertextdisplay);

        // attaching data adapter to spinner
        spinnerO.setAdapter(dataAdapterO);
        spinnerT.setAdapter(dataAdapterT);

        update.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

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

                if (!status.equals(postStatus) && flag == "1") {


                    SQLiteDatabase db0 = database.getWritableDatabase();
                    ContentValues values5 = new ContentValues();
                    values5.put(DBManager.TableInfo.KEYID, ID);
                    values5.put(DBManager.TableInfo.DOCU, docid);
                    values5.put(DBManager.TableInfo.AppointmentId, appointment);
                    values5.put(DBManager.TableInfo.STATUS, status);
                    values5.put(DBManager.TableInfo.reach_time, time);
                    values5.put(DBManager.TableInfo.updatelocation, locationstring);
                    values5.put(DBManager.TableInfo.updatetime, localTime);
                    values5.put(DBManager.TableInfo.reason, reason);
                    values5.put(DBManager.TableInfo.KEY_LOGIN_USER, username2);
                    String condition5 = DBManager.TableInfo.DOCU + " =?";
                    Cursor cursor5 = db0.query(MULTIWORK, null, condition5, new String[]{DBManager.TableInfo.AppointmentId}, null, null, null);
                    long status5 = db0.insert(MULTIWORK, null, values5);
                    cursor5.close();
                    db0.close();
                    Intent i2 = new Intent(marriagestatusinfo.this, AppointmentstatusPost.class);
                    startService(i2);
                    flag = "0";
                }


                SQLiteDatabase db1 = database.getWritableDatabase();
                ContentValues values5 = new ContentValues();
                values5.put(DBManager.TableInfo.KEY_LOGIN_USER, username2);
                values5.put(DBManager.TableInfo.db_marriage_doc_id, docid);
                values5.put(DBManager.TableInfo.db_marriage_appointment_id, appointment);
                values5.put(DBManager.TableInfo.db_marriage_husbandbiometric, itemS);
                values5.put(DBManager.TableInfo.db_marriage_wifebiometric, item1S);
                String condition5 = DBManager.TableInfo.db_marriage_doc_id + " =?";
                Cursor cursor6 = db1.query(DBManager.TableInfo.TABLE_MARRIAGE_SINGLE_BIOMETRIC, null, condition5, new String[]{docid}, null, null, null);
                long status5 = db1.insert(DBManager.TableInfo.TABLE_MARRIAGE_SINGLE_BIOMETRIC, null, values5);
                System.out.println("marriage bio insert:" + status5 + docid + itemS + item1S);
                cursor6.close();
                db1.close();

                if (status5 > 0) {
                    System.out.println("added and inserted");
                    Intent i = new Intent(marriagestatusinfo.this, marriagebiometricpost.class);
                    startService(i);
                }
                finish();


            }
        });
        if (fla == 1) {
            update.setVisibility(View.INVISIBLE);

        }

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
                    spinnerO.setVisibility(View.VISIBLE);
                    spinnerT.setVisibility(View.VISIBLE);


                } else {

                    biometric.setVisibility(View.GONE);
                    Ownerbio.setVisibility(View.GONE);
                    tenantbio.setVisibility(View.GONE);
                    spinnerO.setVisibility(View.GONE);
                    spinnerT.setVisibility(View.GONE);


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

                    spinnerO.setVisibility(View.GONE);
                    spinnerT.setVisibility(View.GONE);


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

                    spinnerO.setVisibility(View.GONE);
                    spinnerT.setVisibility(View.GONE);


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

                    spinnerO.setVisibility(View.GONE);
                    spinnerT.setVisibility(View.GONE);


                }
            }
        });


    }

    private void getCurrentLocation() {
        if (mGoogleApiClient != null) {
            if (mGoogleApiClient.isConnected()) {
                int permissionLocation = ContextCompat.checkSelfPermission(marriagestatusinfo.this,
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
                            .requestLocationUpdates(mGoogleApiClient, locationRequest, marriagestatusinfo.this);
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
                                            .checkSelfPermission(marriagestatusinfo.this,
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
                                        status.startResolutionForResult(marriagestatusinfo.this,
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
        int permissionLocation = ContextCompat.checkSelfPermission(marriagestatusinfo.this,
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

    public void getLocationAddress(final Location location) {


        Geocoder geocoder = new Geocoder(marriagestatusinfo.this, Locale.getDefault());

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

                    t3.setText(finalAddresses.get(0).getAddressLine(0));
                    locationstring = t3.getText().toString();

                }
            });


        } else {
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        int permissionLocation = ContextCompat.checkSelfPermission(marriagestatusinfo.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
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


        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub

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


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}