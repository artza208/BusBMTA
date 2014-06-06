package com.busbmta.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by MeePoohz on 6/5/2557.
 */
public class Location_Manager extends Activity {
    LocationManager lm;
    TextView textLatitude,textLongitude,myAddress;
    double dlat,dlng;

    public void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
    }

    public void onResume() {
        super.onResume();
        setup();
    }

    public void onStart() {
        super.onStart();
        boolean gpsEnabled, networkEnabled;
        gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if(!gpsEnabled) {
            networkEnabled =
                    lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            Intent intent =
                    new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
            if(!networkEnabled) {
                Intent intent2 =
                        new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent2);
            }
        }
    }

    public void onStop() {
        super.onStop();
        lm.removeUpdates(listener);
    }

    public void setup() {
        lm.removeUpdates(listener);
        String latitude = "Unknown";
        String longitude = "Unknown";

        Location networkLocation = requestUpdatesFromProvider(
                LocationManager.NETWORK_PROVIDER, "Network not supported");
        if(networkLocation != null) {
            latitude = String.format("%.7f", networkLocation.getLatitude());
            longitude = String.format("%.7f", networkLocation.getLongitude());
        }

        Location gpsLocation = requestUpdatesFromProvider(
                LocationManager.GPS_PROVIDER, "GPS not supported");

        if(gpsLocation != null) {
            latitude = String.format("%.7f", gpsLocation.getLatitude());
            longitude = String.format("%.7f", gpsLocation.getLongitude());
        }

        dlat = Double.parseDouble(latitude);
        dlng = Double.parseDouble(longitude);
    }

    public Location requestUpdatesFromProvider(final String provider
            , String error) {
        Location location = null;
        if (lm.isProviderEnabled(provider)) {
            lm.requestLocationUpdates(provider, 1000, 10, listener);
            location = lm.getLastKnownLocation(provider);
        } else {
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
        return location;
    }

    public void getMylocationAddress(){
        Locale tLocale = new Locale("th");
        Geocoder geocoder = new Geocoder(this, tLocale);

        try {
            List<Address> addresses = geocoder.getFromLocation(dlat, dlng, 1);

            if(addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder();
                    strReturnedAddress.append(returnedAddress.getAddressLine(0));
                String temp = strReturnedAddress.toString();
                temp = temp.replaceAll("^[// 0-9]*","");
                temp = temp.replaceAll("ถนน","");
                temp = temp.replaceAll("ซอย","");
                temp = temp.replaceAll("ที่","");

                Intent intent = new Intent();
                intent.putExtra("addressGPS",temp);
                setResult(RESULT_OK, intent);
                finish();
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public final LocationListener listener = new LocationListener() {
        public void onLocationChanged(Location location) {

            getMylocationAddress();
        }

        public void onProviderDisabled(String provider) { }

        public void onProviderEnabled(String provider) { }

        public void onStatusChanged(String provider
                , int status, Bundle extras) { }
    };
}
