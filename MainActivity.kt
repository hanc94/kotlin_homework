package com.example.testgps

import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity(),LocationListener {

    // Location manager
    private var locationManager : LocationManager? = null

    //Views for widgets
    private lateinit var thetext: TextView
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Finds view of widgets by ID
        button=findViewById(R.id.button)
        thetext=findViewById(R.id.thetext)

        // Asks for location service
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?

        //Listener for button
        button.setOnClickListener { view ->
            try {
                // Request location updates from network and gps
                locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, this)
                locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0f, this);
            } catch(ex: SecurityException) {
                Log.d("myTag", "Security Exception, no location available")
            }
        }
    }

    //Overrides onLocationChanged
    override fun onLocationChanged(location: Location) {
        thetext.text = ("Longitude: " + location.longitude + "° , Latitude: " + location.latitude+"°")
    }

    //Remaining methods must be overridden even though they are not used
    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
    override fun onProviderEnabled(provider: String) {}
    override fun onProviderDisabled(provider: String) {}
}
