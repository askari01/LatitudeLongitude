package com.kivajohn.latlong;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;


public class MainActivity extends ActionBarActivity implements LocationListener{

    public Location location;
    LocationManager locationManager;
    String provider;
    Geocoder geocoder;
    TextView addressText;
    TextView t1;
    // for thread
    private Handler handler = new Handler() {
        @Override
        public void close() {

        }

        @Override
        public void flush() {

        }

        @Override
        public void publish(LogRecord record) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            new Thread(new Runnable() {
                @Override
                public void run() {
                getParent().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,"in thread",Toast.LENGTH_SHORT).show();
                    }
                });

                /*locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                geocoder = new Geocoder(MainActivity.this);
                Criteria criteria = new Criteria();
                provider = locationManager.getBestProvider(criteria, false);
                //Toast.makeText(this,"Provider "+provider,Toast.LENGTH_LONG).show();


                locationManager.requestLocationUpdates(provider, 400, 1, MainActivity.this );
                location = locationManager.getLastKnownLocation(provider);
                if (location !=null) {
                onLocationChanged(location);
                }else{
                    location = locationManager.getLastKnownLocation(locationManager.PASSIVE_PROVIDER);
                    onLocationChanged(location);
                }*/

                }
            }).start();

    }


    public void onLocationChanged(Location location) {

        this.location = location;
        if(location!=null) {
            t1 = (TextView) findViewById(R.id.Location);

            t1.setText("Latitude: " + location.getLatitude() + " Longitude: " + location.getLongitude());
            Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
            try {
                //Toast.makeText(this,"olleH",Toast.LENGTH_SHORT).show();

                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 10);

                addressText = (TextView) findViewById(R.id.address);
                for (Address address : addresses) {
                    Toast.makeText(MainActivity.this, "Address: " + address.getAddressLine(0), Toast.LENGTH_LONG).show();
                    //Toast.makeText(MainActivity.this,"in address ",Toast.LENGTH_LONG).show();
                    //addressText.setText(address.getAddressLine(0));
                    this.t1.append(" " + address.getAddressLine(0) + " ");
                    //t1.append(address.getAddressLine(0)+"lol "+address.getLongitude());
                    //Log.d("Location","ABCDEF ");

                }
            } catch (IOException e) {
                Log.d("Location", "ABCDEF error " + e);
                //Toast.makeText(this,""+e,Toast.LENGTH_SHORT).show();
            }
        } else{
            Toast.makeText(this, "Error: NULL Pointer Location Error",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
