package com.ola.olafriends;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;
import com.ola.olafriends.utils.Debug;

import java.util.HashMap;
import java.util.Map;

public class SelectedActivity extends AppCompatActivity implements ConnectionCallbacks,
        OnConnectionFailedListener, View.OnClickListener {
    String[] ids;
    String[] names;

    Button checkCabBtn;
    ProgressBar pb;

    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;

    private Location lastLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_result);

        Debug.check();
        float l = 12.9139624f;
        float ln = 77.6001238f;
        Map  mParams = new HashMap();
        mParams.put("pickup_lat", l);
        mParams.put("pickup_lng", ln);
        mParams.put("pickup_mode", "NOW");
        mParams.put("category", "sedan");





        Debug.check();

        checkCabBtn = (Button) findViewById(R.id.checkCabButton);


        checkCabBtn.setOnClickListener(this);

        Bundle b = getIntent().getExtras();
        ids = b.getStringArray("selectedItemsIds");

        names = b.getStringArray("selectedItemsName");

        //names = new ArrayList<String>();
        ListView lv = (ListView) findViewById(R.id.outputList);
        pb = (ProgressBar) findViewById(R.id.pbLoadingCab);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, names);
        lv.setAdapter(adapter);


        Debug.check();

        if (checkGooglePlayServices()) {
            buildGoogleClient();
            pb.setVisibility(ProgressBar.VISIBLE);
        }

        Debug.check();
    }


    private boolean checkGooglePlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        Debug.i("Checking google play services: " + resultCode);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil
                        .getErrorDialog(resultCode, this, AppConstants.PLAY_SERVICES_UID).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Oops!. This device doesnot support google play services.",
                        Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }

    private synchronized void buildGoogleClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_selected, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Debug.check();
        disconnect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Debug.check();
        connect();
    }

    private void connect() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    private void disconnect() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
    }


    @Override
    public void onConnected(Bundle bundle) {
        Debug.check();
        pb.setVisibility(ProgressBar.INVISIBLE);
        checkLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Debug.d("google client connection suspended");
        mGoogleApiClient.connect();
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Debug.check();
        pb.setVisibility(ProgressBar.INVISIBLE);
    }

    public boolean checkLocation() {
        lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (lastLocation != null) {
            double latitude = lastLocation.getLatitude();
            double longitude = lastLocation.getLongitude();
            double alt = lastLocation.getAltitude();
            double acc = lastLocation.getAccuracy();
            double speed = lastLocation.getSpeed();
            Debug.d("lat: " + latitude + " long: " + longitude + " alt: " + alt + " acc: " + acc + " speed: " + speed);
            Debug.toastShort("lat: " + latitude + " long: " + longitude, getApplicationContext(), true);
            return true;
        }
        Debug.e("Location is turned off!!");
        Debug.toastShort("Location is turned off!!", getApplicationContext(), true);
        return false;
    }

    @Override
    public void onClick(View v) {
        double latitude = lastLocation.getLatitude();
        double longitude = lastLocation.getLongitude();
        Bundle b = new Bundle();
        b.putDouble("lat", latitude);
        b.putDouble("long", longitude);
        Intent i = new Intent(getApplicationContext(), AvailableCab.class);
        i.putExtras(b);
        startActivity(i);
    }

}

