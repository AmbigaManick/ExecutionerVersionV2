package anulom.executioner.com.anulom;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
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
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import anulom.executioner.com.anulom.database.DBOperation;
import anulom.executioner.com.anulom.library.AbstractRouting;
import anulom.executioner.com.anulom.library.Route;
import anulom.executioner.com.anulom.library.RouteException;
import anulom.executioner.com.anulom.library.Routing;
import anulom.executioner.com.anulom.library.RoutingListener;
import anulom.executioner.com.anulom.services.Marriageacvrservice;
import anulom.executioner.com.anulom.services.Salesacvrservice;
import anulom.executioner.com.anulom.services.SendReportService;
import butterknife.InjectView;


public class ACVReport extends AppCompatActivity implements LocationListener, OnItemSelectedListener, RoutingListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    EditText etamount, etdistance, etappaddress;
    String rkey, docid, val, appadress, distance1,appaddress1, appaddress, amount, distance, itemtransport, itemapointment, app_id, transportType, apointmentFor = "", exeid;
    Button submit;
    LinearLayout l1, l2, l3, l4, l5, l6;
    CardView l7;
    Spinner spinnerA;
    Spinner spinnert;
    SharedPreferences pref;
    List<String> categoriest;
    List<String> appointmentlist;

    RadioButton rab1, rab2;
    protected LatLng start, end;
    @InjectView(R.id.send)
    ImageView sender;
    @InjectView(R.id.start1)
    AutoCompleteTextView atv;
    @InjectView(R.id.end1)
    AutoCompleteTextView atv1;
    double latitude, longititude, currentlat, currentlong, previouslatitude1, previouslongititude1;
    String previouslatitude, previouslongititude;
    Location mLocation;

    private final static int REQUEST_CHECK_SETTINGS_GPS = 0x1;
    private final static int REQUEST_ID_MULTIPLE_PERMISSIONS = 0x2;
    private List<Polyline> polylines;
    @SuppressLint("PrivateResource")
    private static final int[] COLORS = new int[]{R.color.primary_dark, R.color.primary, R.color.primary_light, R.color.accent, R.color.primary_dark_material_light};

    int pos;
    int amountvalue, timevalue;
    int noofroutes;

    float disvalue, largestdistancevalue, smallestdistancevalue;
    private PlaceAutoCompleteAdapter mAdapter;
    protected GoogleApiClient mGoogleApiClient;
    private static final String LOG_TAG = "ACVReport";


    @Override
    public void onLocationChanged(final Location location) {

        if (location != null) {
            Log.d(LOG_TAG, String.format("%f, %f", location.getLatitude(), location.getLongitude()));

        } else {
            Log.d(LOG_TAG, "Location is null");
        }


    }


    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acvreport);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Acvr Report");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mGoogleApiClient = new GoogleApiClient.Builder(ACVReport.this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addConnectionCallbacks(ACVReport.this)
                .addOnConnectionFailedListener(this)
                .build();
        MapsInitializer.initialize(ACVReport.this);
        mGoogleApiClient.connect();
        pref = this.getSharedPreferences("MyPref", 0); // 0 - for private mode
        val = pref.getString("value", "");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        sender = findViewById(R.id.send);
        rkey = getIntent().getStringExtra("ReportKey");
        pos = getIntent().getIntExtra("position", pos);
        docid = getIntent().getStringExtra("DocumentId");
        app_id = getIntent().getStringExtra("AppointmentId");
        appadress = getIntent().getStringExtra("AppointmentAddress");
        distance1=getIntent().getStringExtra("Distance");
        latitude = Double.parseDouble(getIntent().getStringExtra("CustomerLatitude"));
        longititude = Double.parseDouble(getIntent().getStringExtra("CustomerLatlong"));
        //distance = getIntent().getStringExtra("Distance");
        amount = getIntent().getStringExtra("Amount");
        transportType = getIntent().getStringExtra("TransportType");
        apointmentFor = getIntent().getStringExtra("ApointmentFor");
        appaddress = getIntent().getStringExtra("AppointmentAddress");
        appaddress1 = getIntent().getStringExtra("PreviousAddress");
        exeid = getIntent().getStringExtra("Executionerid");
        String key1 = "Request No: " + rkey;
        previouslatitude = getIntent().getStringExtra("lat");
        previouslongititude = getIntent().getStringExtra("long");
        System.out.println("prev.latitude" + previouslatitude + "prev.longititude" + previouslongititude+distance1+amount);



        etappaddress = findViewById(R.id.etaptadd);
        TextView reportkey = findViewById(R.id.reportkey_text);
        reportkey.setText(key1);
        etappaddress.setText(appadress);

        etdistance = findViewById(R.id.etdistance);
        if (distance1.equals("null")) {
            etdistance.setText("");
        } else {
            etdistance.setText(distance1);
        }

        etamount = findViewById(R.id.etamount);
        if (amount.equals("null")) {
            etamount.setText("");
        } else {
            etamount.setText(amount);
        }
        atv = findViewById(R.id.start1);
        atv1 = findViewById(R.id.end1);


        polylines = new ArrayList<>();

        l1 = findViewById(R.id.linear1);
        l2 = findViewById(R.id.linear2);
        l3 = findViewById(R.id.linear3);
        l4 = findViewById(R.id.linear4);
        l5 = findViewById(R.id.linear5);
        l6 = findViewById(R.id.linear6);
        l7 = findViewById(R.id.cardview);


        submit = findViewById(R.id.btnSubmit);
        rab1 = findViewById(R.id.previousaddress);
        rab2 = findViewById(R.id.otherlocation);
        spinnerA = findViewById(R.id.spinner1);
         spinnert = findViewById(R.id.spinner2);
        spinnerA.setOnItemSelectedListener(this);
        spinnert.setOnItemSelectedListener(this);

        atv1.setText(appaddress);

        if (pos == 0) {
            rab1.setText("CURRENT LOCATION");
        }
        if (pos > 0) {


            if (previouslatitude.equals("null") && previouslongititude.equals("null")) {


                rab1.setVisibility(View.GONE);

            } else if (!previouslatitude.equals("null") && !previouslongititude.equals("null")) {
                previouslatitude1 = Double.parseDouble(getIntent().getStringExtra("lat"));
                previouslongititude1 = Double.parseDouble(getIntent().getStringExtra("long"));

                rab1.setVisibility(View.VISIBLE);
            }


        }
        rab1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pos == 0) {

                    if (isLocationTurnedOn() && (Util.Operations.isOnline(getApplicationContext()))) {
                        atv1.setVisibility(View.INVISIBLE);

                        l1.setVisibility(View.VISIBLE);
                        l2.setVisibility(View.VISIBLE);
                        l3.setVisibility(View.VISIBLE);
                        l4.setVisibility(View.VISIBLE);
                        l5.setVisibility(View.VISIBLE);
                        l6.setVisibility(View.VISIBLE);
                        l7.setVisibility(View.GONE);


                        getCurrentLocation();
                        route(start, end);
                        }
                        else {

                            Toast.makeText(ACVReport.this, "Please Turn on Location and Internet", Toast.LENGTH_SHORT).show();


                        }




                } else if (pos > 0) {

                    if (Util.Operations.isOnline(getApplicationContext())) {
                    atv1.setVisibility(View.INVISIBLE);
                    l7.setVisibility(View.GONE);
                    l1.setVisibility(View.VISIBLE);
                    l2.setVisibility(View.VISIBLE);
                    l3.setVisibility(View.VISIBLE);
                    l4.setVisibility(View.VISIBLE);
                    l5.setVisibility(View.VISIBLE);
                    l6.setVisibility(View.VISIBLE);


                    atv.setText(appaddress1);



                        getLocationFromAddress();
                        route(start, end);
                    } else {
                        Toast.makeText(ACVReport.this, "No internet connectivity", Toast.LENGTH_SHORT).show();
                    }


                }


            }
        });


        rab2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pos == 0) {

                    atv.setText("");
                    atv1.setVisibility(View.INVISIBLE);
                    atv1.setText(appaddress);

                    l7.setVisibility(View.VISIBLE);

                    l1.setVisibility(View.INVISIBLE);
                    l2.setVisibility(View.INVISIBLE);
                    l3.setVisibility(View.INVISIBLE);
                    l4.setVisibility(View.INVISIBLE);
                    l5.setVisibility(View.INVISIBLE);
                    l6.setVisibility(View.INVISIBLE);
                } else if (pos > 0) {
                    atv1.setVisibility(View.INVISIBLE);
                    atv.setText("");
                    atv1.setText(appaddress);
                    l7.setVisibility(View.VISIBLE);
                    l1.setVisibility(View.INVISIBLE);
                    l2.setVisibility(View.INVISIBLE);
                    l3.setVisibility(View.INVISIBLE);
                    l4.setVisibility(View.INVISIBLE);
                    l5.setVisibility(View.INVISIBLE);
                    l6.setVisibility(View.INVISIBLE);
                }


            }


        });


        sender.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                l1.setVisibility(View.VISIBLE);
                l2.setVisibility(View.VISIBLE);
                l3.setVisibility(View.VISIBLE);
                l4.setVisibility(View.VISIBLE);
                l5.setVisibility(View.VISIBLE);
                l6.setVisibility(View.VISIBLE);

                if (Util.Operations.isOnline(getApplicationContext())) {
                    getlatlng();
                    route(start, end);
                } else {
                    Toast.makeText(ACVReport.this, "No internet connectivity", Toast.LENGTH_SHORT).show();
                }


            }
        });

        mAdapter = new PlaceAutoCompleteAdapter(this, android.R.layout.simple_list_item_1,
                mGoogleApiClient, null, null);

        atv.setAdapter(mAdapter);
        atv1.setAdapter(mAdapter);

        atv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final PlaceAutoCompleteAdapter.PlaceAutocomplete item = mAdapter.getItem(position);
                final String placeId = String.valueOf(item.placeId);
                Log.i(LOG_TAG, "Autocomplete item selected: " + item.description);
                PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                        .getPlaceById(mGoogleApiClient, placeId);
                placeResult.setResultCallback(new ResultCallback<PlaceBuffer>() {
                    @Override
                    public void onResult(PlaceBuffer places) {
                        if (!places.getStatus().isSuccess()) {
                            Log.e(LOG_TAG, "Place query did not complete. Error: " + places.getStatus().toString());
                            places.release();
                            return;
                        }
                        final Place place = places.get(0);

                        start = place.getLatLng();


                    }
                });

            }
        });


        atv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final PlaceAutoCompleteAdapter.PlaceAutocomplete item = mAdapter.getItem(position);
                final String placeId = String.valueOf(item.placeId);
                Log.i(LOG_TAG, "Autocomplete item selected: " + item.description);


                PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                        .getPlaceById(mGoogleApiClient, placeId);
                placeResult.setResultCallback(new ResultCallback<PlaceBuffer>() {
                    @Override
                    public void onResult(PlaceBuffer places) {
                        if (!places.getStatus().isSuccess()) {

                            Log.e(LOG_TAG, "Place query did not complete. Error: " + places.getStatus().toString());
                            places.release();
                            return;
                        }
                        final Place place = places.get(0);

                        end = place.getLatLng();


                    }
                });

            }
        });

        atv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int startNum, int before, int count) {
                if (start != null) {
                    start = null;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        atv1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if (end != null) {
                    end = null;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        if (apointmentFor.equals("null")) {

            appointmentlist = new ArrayList<>();
            appointmentlist.clear();
            appointmentlist.add("");
            appointmentlist.add("Owner");
            appointmentlist.add("Tenant");
            appointmentlist.add("Both");
            appointmentlist.add("Witness");
        } else if (apointmentFor.equals("1")) {
            appointmentlist = new ArrayList<>();
            appointmentlist.clear();
            appointmentlist.add("Owner");
            appointmentlist.add("Tenant");
            appointmentlist.add("Both");
            appointmentlist.add("Witness");
            appointmentlist.add("");
        } else if (apointmentFor.equals("2")) {
            appointmentlist = new ArrayList<>();
            appointmentlist.clear();
            appointmentlist.add("Tenant");
            appointmentlist.add("Owner");
            appointmentlist.add("Both");
            appointmentlist.add("Witness");
            appointmentlist.add("");
        } else if (apointmentFor.equals("3")) {
            appointmentlist = new ArrayList<>();
            appointmentlist.clear();
            appointmentlist.add("Both");
            appointmentlist.add("Owner");
            appointmentlist.add("Tenant");
            appointmentlist.add("Witness");
            appointmentlist.add("");
        } else if (apointmentFor.equals("4")) {
            appointmentlist = new ArrayList<>();
            appointmentlist.clear();
            appointmentlist.add("Witness");
            appointmentlist.add("Both");
            appointmentlist.add("Owner");
            appointmentlist.add("Tenant");
            appointmentlist.add("");
        }


        if (transportType.trim().equals("null") || transportType.isEmpty()) {

            categoriest = new ArrayList<>();
            categoriest.clear();
            categoriest.add("");
            categoriest.add("bus");
            categoriest.add("train");
            categoriest.add("two_wheeler");
            categoriest.add("combination");

        } else if (transportType.equals("bus")) {

            categoriest = new ArrayList<>();
            categoriest.clear();
            categoriest.add("bus");
            categoriest.add("train");
            categoriest.add("two_wheeler");
            categoriest.add("combination");
            categoriest.add("");

        } else if (transportType.equals("train")) {

            categoriest = new ArrayList<>();
            categoriest.clear();
            categoriest.add("train");
            categoriest.add("bus");
            categoriest.add("two_wheeler");
            categoriest.add("combination");
            categoriest.add("");
        } else if (transportType.equals("two_wheeler")) {

            categoriest = new ArrayList<>();
            categoriest.clear();
            categoriest.add("two_wheeler");
            categoriest.add("bus");
            categoriest.add("train");
            categoriest.add("combination");
            categoriest.add("");
        } else if (transportType.equals("combination")) {

            categoriest = new ArrayList<>();
            categoriest.clear();
            categoriest.add("combination");
            categoriest.add("two_wheeler");
            categoriest.add("bus");
            categoriest.add("train");
            categoriest.add("");
        }


        ArrayAdapter<String> dataAdaptert = new ArrayAdapter<>(this, R.layout.spinnertextdisplay,
                categoriest);


        ArrayAdapter<String> dataAdapterA = new ArrayAdapter<>(this, R.layout.spinnertextdisplay, appointmentlist);

        dataAdaptert.setDropDownViewResource(R.layout.spinnertextdisplay);
        dataAdapterA.setDropDownViewResource(R.layout.spinnertextdisplay);

        if (!dataAdapterA.equals("null") || !dataAdaptert.equals("null")) {
            spinnert.setAdapter(dataAdaptert);
            spinnerA.setAdapter(dataAdapterA);

        }


    }


    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected())
            mGoogleApiClient.disconnect();
    }


    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()) {
            case R.id.spinner1:
                itemapointment = parent.getItemAtPosition(position).toString();
                if (itemapointment.equals("")) {
                    itemapointment = "0";
                } else if (itemapointment.equals("Owner")) {
                    itemapointment = "1";
                } else if (itemapointment.equals("Tenant")) {
                    itemapointment = "2";
                } else if (itemapointment.equals("Both")) {
                    itemapointment = "3";
                } else if (itemapointment.equals("Witness")) {
                    itemapointment = "4";
                }
                break;
            case R.id.spinner2:
                itemtransport = parent.getItemAtPosition(position).toString();
                break;
            default:
        }
        update();
    }


    public void onNothingSelected(AdapterView<?> parent) {

    }


    private void update() {
        // TODO Auto-generated method stub
        submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                // TODO Auto-generated method stub

                distance = etdistance.getText().toString();
                amount = etamount.getText().toString();
                appadress = etappaddress.getText().toString();




                

                System.out.println("distance:" + distance);
                if (distance.equals("") || amount.equals("")) {
                    Toast.makeText(ACVReport.this, "Please update the valid Distance and Amount", Toast.LENGTH_SHORT).show();

                } else if ((Integer.valueOf(distance) <= largestdistancevalue)) {
                    DBOperation db = new DBOperation(ACVReport.this);
                    String synstatus1 = "ASYNC";

                    if (val.equals("Biometric")) {
                        db.Update1(db, app_id, docid, appadress, exeid,
                                itemapointment, distance, amount, itemtransport, synstatus1, val);
                    } else if (val.equals("Sales")) {
                        db.Update2(db, app_id, docid, appadress, exeid,
                                itemapointment, distance, amount, itemtransport, synstatus1, val);

                    } else if (val.equals("Marriage")) {
                        db.Update3(db, app_id, docid, appadress, exeid,
                                itemapointment, distance, amount, itemtransport, synstatus1, val);

                    }

                    if (GenericMethods.isConnected(getApplicationContext())) {
                        if (val.equals("Biometric")) {
                            Intent intent = new Intent(ACVReport.this, SendReportService.class);
                            startService(intent);
                            finish();
                        } else if (val.equals("Sales")) {
                            Intent intent = new Intent(ACVReport.this, Salesacvrservice.class);
                            startService(intent);
                            finish();
                        } else if (val.equals("Marriage")) {
                            Intent intent = new Intent(ACVReport.this, Marriageacvrservice.class);
                            startService(intent);
                            finish();
                        }

                    } else {
                        Toast.makeText(ACVReport.this, "ACVR Saved Offline!! ", Toast.LENGTH_LONG).show();
                        finish();
                    }
                } else if (Integer.valueOf(distance) > largestdistancevalue) {
                    Toast.makeText(ACVReport.this, "Please update the correct Distance", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    private void getCurrentLocation() {
        System.out.println("hooo");
        if (mGoogleApiClient != null) {
            if (mGoogleApiClient.isConnected()) {
                int permissionLocation = ContextCompat.checkSelfPermission(ACVReport.this,
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
                            .requestLocationUpdates(mGoogleApiClient, locationRequest, ACVReport.this);
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
                                            .checkSelfPermission(ACVReport.this,
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
                                        status.startResolutionForResult(ACVReport.this,
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
        int permissionLocation = ContextCompat.checkSelfPermission(ACVReport.this,
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
        int permissionLocation = ContextCompat.checkSelfPermission(ACVReport.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        }
    }


    public void getLocationAddress(final Location location) {

        System.out.println("hooo111");
        Geocoder geocoder = new Geocoder(ACVReport.this, Locale.getDefault());


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


            atv.setText(finalAddresses.get(0).getAddressLine(0));
            start = new LatLng(currentlat, currentlong);
            atv1.setText(appaddress1);
            end = new LatLng(latitude, longititude);


        } else {
        }


    }

    public void getLocationFromAddress() {


        Geocoder geocoder = new Geocoder(ACVReport.this, Locale.getDefault());


        List<Address> addlatlong = null;

        try {

            addlatlong = geocoder.getFromLocation(previouslatitude1, previouslongititude1, 1);

        } catch (IOException e1) {
            e1.printStackTrace();
            return;
        }

        if ((addlatlong != null && addlatlong.size() > 0)) {

            final Address address = addlatlong.get(0);

            String addressText = String.format(
                    "%s, %s, %s",

                    address.getMaxAddressLineIndex() > 0 ? address
                            .getAddressLine(0) : "",

                    address.getLocality(),

                    address.getCountryName());


            final List<Address> finalAddresses = addlatlong;


            start = new LatLng(previouslatitude1, previouslongititude1);
            end = new LatLng(latitude, longititude);


        } else {
        }


    }

    public void getlatlng() {


        Geocoder geocoder = new Geocoder(ACVReport.this, Locale.getDefault());


        List<Address> addlatlong = null;

        try {

            addlatlong = geocoder.getFromLocation(latitude, longititude, 1);

        } catch (IOException e1) {
            e1.printStackTrace();
            return;
        }
        if ((addlatlong != null && addlatlong.size() > 0)) {
            final Address address = addlatlong.get(0);

            String addressText = String.format(
                    "%s, %s, %s",
                    address.getMaxAddressLineIndex() > 0 ? address
                            .getAddressLine(0) : "",
                    address.getLocality(),
                    address.getCountryName());


            final List<Address> finalAddresses = addlatlong;


            end = new LatLng(latitude, longititude);


        } else {
        }


    }





    public void route(final LatLng start, final LatLng end) {

        if (start == null || end == null) {
            if (start == null) {
                if (atv.getText().length() > 0) {
                    atv.setError("Choose location from dropdown.");
                } else {
                    Toast.makeText(this, "Please choose a starting point.", Toast.LENGTH_SHORT).show();
                }
            }
            if (end == null) {
                if (atv1.getText().length() > 0) {
                    atv1.setError("Choose location from dropdown.");
                } else {
                    Toast.makeText(this, "Please choose a destination.", Toast.LENGTH_SHORT).show();
                }
            }
        } else {


            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Routing routing = new Routing.Builder()
                            .travelMode(AbstractRouting.TravelMode.DRIVING)
                            .withListener(ACVReport.this)
                            .alternativeRoutes(true)
                            .waypoints(start, end)
                            .key("AIzaSyDiBrtL_Aqufyo3l_hQ9lCKJOYoOwE2ryo")
                            .build();
                    routing.execute();

                }
            });


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
    public void onRoutingFailure(RouteException e) {
        if (e != null) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Something went wrong, Try again", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRoutingStart() {

    }

    @Override
    public void onRoutingSuccess(List<Route> route, int shortestRouteIndex) {

        if (polylines.size() > 0) {
            for (Polyline poly : polylines) {
                poly.remove();
            }
        }

        polylines = new ArrayList<>();
        //add route(s) to the map.
        for (int i = 0; i < route.size(); i++) {
            final int finalI = i;

            int colorIndex = finalI % COLORS.length;
            PolylineOptions polyOptions = new PolylineOptions();
            polyOptions.color(getResources().getColor(COLORS[colorIndex]));
            polyOptions.width(10 + finalI * 3);
            polyOptions.addAll(route.get(finalI).getPoints());

            String temp = "";
            String amount = "";
            temp = route.get(finalI).getDistanceText();
            amount = route.get(finalI).getDistanceText();

            StringBuilder sb = new StringBuilder(temp);
            StringBuilder sb1 = new StringBuilder(amount);
            if (temp.contains("km")) {

                sb.delete(temp.length() - 3, temp.length());
                sb1.delete(temp.length() - 3, temp.length());
            } else {

                sb.delete(temp.length() - 2, temp.length());
                sb1.delete(temp.length() - 2, temp.length());

            }

            if (temp.contains(",")) {

                sb.deleteCharAt(temp.indexOf(","));

                sb1.deleteCharAt(temp.indexOf(","));


            }


            if (temp.contains(".")) {

                sb.deleteCharAt(temp.indexOf("."));
                amount = sb.toString();
                amountvalue = Integer.parseInt(amount);
            } else {
                amount = sb.toString();
                amountvalue = Integer.parseInt(amount);
            }
            temp = sb1.toString();


            disvalue = Float.parseFloat(temp);
            noofroutes = (finalI + 1);
            timevalue = route.get(finalI).getDurationValue();


            if (disvalue > largestdistancevalue) {

                largestdistancevalue = disvalue;

            }

            if (disvalue < smallestdistancevalue) {

                smallestdistancevalue = disvalue;
            }


        }


    }


    @Override
    public void onRoutingCancelled() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private boolean isLocationTurnedOn() {

        try {

            LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);


            boolean isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            boolean isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (isGPSEnabled || isNetworkEnabled) {


                if(pos==0){

                    getCurrentLocation();
                }
                if(pos >0){

                    getLocationFromAddress();
                }

                return true;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}



