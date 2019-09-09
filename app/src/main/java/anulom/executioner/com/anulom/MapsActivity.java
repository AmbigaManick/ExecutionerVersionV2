package anulom.executioner.com.anulom;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
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
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import anulom.executioner.com.anulom.library.AbstractRouting;
import anulom.executioner.com.anulom.library.Route;
import anulom.executioner.com.anulom.library.RouteException;
import anulom.executioner.com.anulom.library.Routing;
import anulom.executioner.com.anulom.library.RoutingListener;

import butterknife.InjectView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MapsActivity extends AppCompatActivity implements LocationListener, RoutingListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, OnMapReadyCallback {
    protected GoogleMap map;
    protected LatLng start;
    protected LatLng end;
    @InjectView(R.id.prev_add)
    TextView prev_add;
    @InjectView(R.id.start)
    AutoCompleteTextView starting = null;
    @InjectView(R.id.destination)
    AutoCompleteTextView destination;
    @InjectView(R.id.send)
    ImageView send;
    @InjectView(R.id.floatingActionButton)
    FloatingActionButton currrentaddress;
    @InjectView(R.id.floatingActionButton1)
    FloatingActionButton previousaddress;
    @InjectView(R.id.floatingActionButton3)
    FloatingActionButton navigation;
    Location mLocation;
    private static final String LOG_TAG = "MapsActivity";
    protected GoogleApiClient mGoogleApiClient;
    private PlaceAutoCompleteAdapter mAdapter;
    private ProgressDialog progressDialog;
    Timer singleTask = new Timer();
    int startdelay = 3000;
    private List<Polyline> polylines;

    double latitude, longititude, previouslatitude, previouslongititude, currentlat, currentlong;
    String appaddress = "", appaddress1 = "", value = "", latitude1 = "", longititude1 = "", previouslat1 = "", previouslong1 = "";
    int pos;
    @SuppressLint("PrivateResource")
    private static final int[] COLORS = new int[]{R.color.primary_dark, R.color.primary, R.color.primary_light, R.color.accent, R.color.primary_dark_material_light};

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATE = 10;
    private final static int REQUEST_CHECK_SETTINGS_GPS = 0x1;
    private final static int REQUEST_ID_MULTIPLE_PERMISSIONS = 0x2;
    private static final long MIN_TIME_BW_UPDATE = 1000 * 60;
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;


    /**
     * This activity loads a map and then displays the route and pushpins on it.
     */


    @Override
    public void onLocationChanged(final Location location) {

        if (location != null) {
            Log.d(LOG_TAG, String.format("%f, %f", location.getLatitude(), location.getLongitude()));


        } else {
            Log.d(LOG_TAG, "Location is null");
        }


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.inject(MapsActivity.this);


        appaddress = getIntent().getStringExtra("AppointmentAddress");
        appaddress1 = getIntent().getStringExtra("PreviousAddress");
        pos = getIntent().getIntExtra("position", pos);

        previouslat1 = getIntent().getStringExtra("lat");
        previouslong1 = getIntent().getStringExtra("long");
        latitude = Double.parseDouble(getIntent().getStringExtra("CustomerLatitude"));
        longititude = Double.parseDouble(getIntent().getStringExtra("CustomerLatlong"));


        initMap();
        polylines = new ArrayList<>();

        mGoogleApiClient = new GoogleApiClient.Builder(MapsActivity.this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        MapsInitializer.initialize(MapsActivity.this);
        mGoogleApiClient.connect();
        destination.setText(appaddress);


        if (isLocationTurnedOn()) {

            if (pos == 0) {

                progressDialog = ProgressDialog.show(MapsActivity.this, "Please wait.",
                        "Fetching Current Location.", true);

                singleTask.schedule(new TimerTask() {
                    @Override
                    public void run() {

                        getCurrentLocation();
                        getLocationAddress(mLocation);
                        progressDialog.dismiss();

                    }

                }, startdelay);


                previousaddress.setVisibility(View.INVISIBLE);
                prev_add.setVisibility(View.INVISIBLE);

            } else if (pos > 0) {

                if (!previouslat1.equals(null) && !previouslong1.equals("null")) {

                    previouslatitude = Double.parseDouble(getIntent().getStringExtra("lat"));
                    previouslongititude = Double.parseDouble(getIntent().getStringExtra("long"));

                } else {
                   getlatlng();
                }
                getLocationFromAddress();



                previousaddress.setVisibility(View.VISIBLE);
                prev_add.setVisibility(View.VISIBLE);
            }
        } else {
            checkPermissions();
        }

        previousaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value = "0";

                getLocationFromAddress();


            }
        });

        currrentaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value = "1";
                getCurrentLocation();
                getLocationAddress(mLocation);
                drawMarker(mLocation);

            }
        });

        navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pos == 0) {

                    Uri gmmIntentUri = Uri.parse("https://www.google.com/maps/dir/?api=1&origin=" + currentlat + "," + currentlong + "&destination=" + latitude + "," + longititude + "&travelmode=driving");
                    Intent intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    intent.setPackage("com.google.android.apps.maps");
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException ex) {
                        try {
                            Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                            startActivity(unrestrictedIntent);
                        } catch (ActivityNotFoundException innerEx) {
                            Toast.makeText(MapsActivity.this, "Please install a maps application", Toast.LENGTH_LONG).show();
                        }
                    }

                }
                if (pos > 0) {
                    if (value.equals("1")) {
                        Uri gmmIntentUri = Uri.parse("https://www.google.com/maps/dir/?api=1&origin=" + currentlat + "," + currentlong + "&destination=" + latitude + "," + longititude + "&travelmode=driving");
                        Intent intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        intent.setPackage("com.google.android.apps.maps");
                        try {
                            startActivity(intent);
                        } catch (ActivityNotFoundException ex) {
                            try {
                                Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                startActivity(unrestrictedIntent);
                            } catch (ActivityNotFoundException innerEx) {
                                Toast.makeText(MapsActivity.this, "Please install a maps application", Toast.LENGTH_LONG).show();
                            }
                        }

                    }
                    if (value.equals("0")) {

                        Uri gmmIntentUri = Uri.parse("https://www.google.com/maps/dir/?api=1&origin=" + latitude + "," + longititude + "&destination=" + previouslatitude + "," + previouslongititude + "&travelmode=driving");
                        Intent intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        intent.setPackage("com.google.android.apps.maps");
                        try {
                            startActivity(intent);
                        } catch (ActivityNotFoundException ex) {
                            try {
                                Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                startActivity(unrestrictedIntent);
                            } catch (ActivityNotFoundException innerEx) {
                                Toast.makeText(MapsActivity.this, "Please install a maps application", Toast.LENGTH_LONG).show();
                            }
                        }
                    } else {

                        Uri gmmIntentUri = Uri.parse("https://www.google.com/maps/dir/?api=1&origin=" + latitude + "," + longititude + "&destination=" + previouslatitude + "," + previouslongititude + "&travelmode=driving");
                        Intent intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        intent.setPackage("com.google.android.apps.maps");
                        try {
                            startActivity(intent);
                        } catch (ActivityNotFoundException ex) {
                            try {
                                Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                startActivity(unrestrictedIntent);
                            } catch (ActivityNotFoundException innerEx) {
                                Toast.makeText(MapsActivity.this, "Please install a maps application", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }

            }
        });


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();
        }
       mapFragment.getMapAsync(this);

        mAdapter = new PlaceAutoCompleteAdapter(this, android.R.layout.simple_list_item_1,
                mGoogleApiClient, null, null);


//        map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
//            @Override
//            public void onCameraChange(CameraPosition position) {
//                LatLngBounds bounds = map.getProjection().getVisibleRegion().latLngBounds;
//                mAdapter.setBounds(bounds);
//            }
//        });
//
//
//        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(11.0168, 76.9558));
//        CameraUpdate zoom = CameraUpdateFactory.zoomTo(10);
//
//        map.moveCamera(center);
//        map.animateCamera(zoom);


        starting.setAdapter(mAdapter);
        destination.setAdapter(mAdapter);


        starting.setOnItemClickListener(new AdapterView.OnItemClickListener() {

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
        destination.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final PlaceAutoCompleteAdapter.PlaceAutocomplete item = mAdapter.getItem(position);
                final String placeId = String.valueOf(item.placeId);

                Log.i(LOG_TAG, "Autocomplete item selected: " + item.description + item.placeId);


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


        starting.addTextChangedListener(new TextWatcher() {
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

        destination.addTextChangedListener(new TextWatcher() {
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

    }

    @Override
    public void onMapReady(GoogleMap googlemap) {

        map=googlemap;
        // DO WHATEVER YOU WANT WITH GOOGLEMAP
        googlemap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googlemap.setMyLocationEnabled(true);
        googlemap.setTrafficEnabled(true);
        googlemap.setIndoorEnabled(true);
        googlemap.setBuildingsEnabled(true);
        googlemap.getUiSettings().setZoomControlsEnabled(true);
    }
    public void getlatlng() {


        Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());


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
             route(start,end);


        } else {
        }


    }





    public void callmap() {

        if (pos == 0) {

            progressDialog = ProgressDialog.show(MapsActivity.this, "Please wait.",
                    "Fetching Current Location.", true);

            singleTask.schedule(new TimerTask() {
                @Override
                public void run() {

                    getCurrentLocation();
                    getLocationAddress(mLocation);
                    progressDialog.dismiss();

                }

            }, startdelay);


            previousaddress.setVisibility(View.INVISIBLE);
            prev_add.setVisibility(View.INVISIBLE);

        } else if (pos > 0) {


            previouslatitude = Double.parseDouble(getIntent().getStringExtra("lat"));
            previouslongititude = Double.parseDouble(getIntent().getStringExtra("long"));


            getLocationFromAddress();

            previousaddress.setVisibility(View.VISIBLE);
            prev_add.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    private boolean isLocationTurnedOn() {

        try {

            LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);


            // getting GPS status
            boolean isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            boolean isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (isGPSEnabled || isNetworkEnabled) {

                return true;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }




    @Override
    protected void onPause() {
        super.onPause();

        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected())
            mGoogleApiClient.disconnect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }

    }


    private void initMap() {

        GoogleApiAvailability apiAvailability=GoogleApiAvailability.getInstance();
        int googlePlayStatus =apiAvailability.isGooglePlayServicesAvailable(this);
        if (googlePlayStatus != ConnectionResult.SUCCESS) {
            apiAvailability.getErrorDialog(this, googlePlayStatus, -1).show();
            finish();
        } else {
            if (map != null) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                map.setMyLocationEnabled(true);
                map.getUiSettings().setMyLocationButtonEnabled(true);
                map.getUiSettings().setAllGesturesEnabled(true);
            }
        }
    }


    private void getCurrentLocation() {

        if (mGoogleApiClient != null) {
            if (mGoogleApiClient.isConnected()) {

                int permissionLocation = ContextCompat.checkSelfPermission(MapsActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION);
                if (permissionLocation == PackageManager.PERMISSION_GRANTED) {

                    mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                    LocationRequest locationRequest = new LocationRequest();
                    locationRequest.setInterval(10 * 1000);
                    locationRequest.setFastestInterval(1000);
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                            .addLocationRequest(locationRequest);
                    builder.setAlwaysShow(true);
                    LocationServices.FusedLocationApi
                            .requestLocationUpdates(mGoogleApiClient, locationRequest, MapsActivity.this);
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
                                            .checkSelfPermission(MapsActivity.this,
                                                    Manifest.permission.ACCESS_FINE_LOCATION);
                                    if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                                        mLocation = LocationServices.FusedLocationApi
                                                .getLastLocation(mGoogleApiClient);


                                    }
                                    break;
                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                                    try {

                                        status.startResolutionForResult(MapsActivity.this,
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
                        callmap();
                        break;
                    case Activity.RESULT_CANCELED:
                        finish();
                        break;
                }
                break;
        }
    }

    private void checkPermissions() {
        int permissionLocation = ContextCompat.checkSelfPermission(MapsActivity.this,
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
        int permissionLocation = ContextCompat.checkSelfPermission(MapsActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        }
    }

    public void getLocationAddress(final Location location) {


        Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());


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
                    // If there's a street address, add it
                    address.getMaxAddressLineIndex() > 0 ? address
                            .getAddressLine(0) : "",
                    address.getLocality(),
                    address.getCountryName());

            final List<Address> finalAddresses = addresses;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    starting.setText(finalAddresses.get(0).getAddressLine(0));
                    start = new LatLng(currentlat, currentlong);
                    end = new LatLng(latitude, longititude);

                    route(start, end);


                }
            });


        } else {
        }


    }

    public void getLocationFromAddress() {


        Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());

        List<Address> addlatlong = null;

        try {

            addlatlong = geocoder.getFromLocation(previouslatitude, previouslongititude, 1);

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

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                        starting.setText(appaddress1);



                    start = new LatLng(previouslatitude, previouslongititude);

                    end = new LatLng(latitude, longititude);
                    route(start, end);

                }
            });


        } else {
        }


    }


    private void drawMarker(Location location) {

        if (map != null) {
            map.clear();

            LatLng gps = new LatLng(location.getLatitude(), location.getLongitude());
            map.addMarker(new MarkerOptions()
                    .position(gps)
                    .title("Current Position"));
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(gps, 12));
        }

    }


    @OnClick(R.id.send)
    public void sendRequest() {

        if (Util.Operations.isOnline(getApplicationContext())) {


            route(start, end);
        } else {
            Toast.makeText(this, "No internet connectivity", Toast.LENGTH_SHORT).show();
        }
    }

    public void route(final LatLng start, final LatLng end) {

        if (start == null || end == null) {
            if (start == null) {
                if (starting.getText().length() > 0) {
                    starting.setError("Choose location from dropdown.");
                } else {
                    Toast.makeText(this, "Please choose a starting point.", Toast.LENGTH_SHORT).show();
                }
            }
            if (end == null) {
                if (destination.getText().length() > 0) {
                    destination.setError("Choose location from dropdown.");
                } else {
                    Toast.makeText(this, "Please choose a destination.", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!isFinishing()) {
                        progressDialog = ProgressDialog.show(MapsActivity.this, "Please wait.",
                                "Fetching route information.", true);


                    }

                }
            });
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Routing routing = new Routing.Builder()
                            .travelMode(AbstractRouting.TravelMode.DRIVING)
                            .withListener(MapsActivity.this)
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
    public void onRoutingFailure(RouteException e) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();

                }
            }
        });
        if (e != null) {
            Toast.makeText(this, "Please Turn on the Internet!!!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Something went wrong, Try again", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRoutingStart() {
    }

    @Override
    public void onRoutingSuccess(final List<Route> route, int shortestRouteIndex) {


        if (polylines.size() > 0) {
            for (Polyline poly : polylines) {
                poly.remove();
            }
        }

        polylines = new ArrayList<>();

        for (int i = 0; i < route.size(); i++) {
            final int finalI = i;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int colorIndex = finalI % COLORS.length;

                    PolylineOptions polyOptions = new PolylineOptions();
                    polyOptions.color(getResources().getColor(COLORS[colorIndex]));
                    polyOptions.width(10 + finalI * 3);
                    polyOptions.addAll(route.get(finalI).getPoints());
                    Polyline polyline = map.addPolyline(polyOptions);
                    polylines.add(polyline);
                    Toast.makeText(getApplicationContext(), "Route " + (finalI + 1) + ": distance - " + route.get(finalI).getDistanceText() + ": duration - " + route.get(finalI).getDurationText(), Toast.LENGTH_SHORT).show();


                    MarkerOptions options = new MarkerOptions();
                    options.position(start);
                    options.icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue));
                    map.addMarker(options);

                    options = new MarkerOptions();
                    options.position(end);
                    options.icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green));
                    map.addMarker(options);

                    LatLngBounds.Builder builder = new LatLngBounds.Builder();

                    builder.include(start);
                    builder.include(end);
                    CameraUpdate zoom = (CameraUpdateFactory.newLatLngBounds(builder.build(), 2));

                    map.animateCamera(zoom);


                }
            });


        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();

                }
            }
        });


    }


    @Override
    public void onRoutingCancelled() {
        Log.i(LOG_TAG, "Routing was cancelled.");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        Log.v(LOG_TAG, connectionResult.toString());
    }

    @Override
    public void onConnected(Bundle bundle) {
        checkPermissions();
    }


    @Override
    public void onConnectionSuspended(int i) {

    }
}
