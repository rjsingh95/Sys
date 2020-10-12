package `in`.sys.ranjeet.service.map

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn.requestPermissions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.tasks.OnCompleteListener
import java.util.*

class Map(mactivity: Activity, mmapInterface: MapInterface) {

    internal var location: Location? = null
    private var latitude: Double = 0.toDouble()
    private var longitude: Double = 0.toDouble()
    private var builder: LatLngBounds.Builder? = null
    private val PERMISSION_REQUEST_CODE = 100
    private val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 400000

    var context: Context? = null
    var activity: Activity? = null
    var mapInterface: MapInterface? = null

    init {
        context = mactivity.applicationContext
        activity = mactivity
        mapInterface = mmapInterface


        createLocationRequest()
        createTask()
        initializeMap()
    }

    /**
     * The fastest rate for active location updates. Exact. Updates will never be more frequent
     * than this value.
     */
    private val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2
    /**
     * Provides access to the Fused Location Provider API.
     */
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    /**
     * Provides access to the Location Settings API.
     */
    private var mSettingsClient: SettingsClient? = null
    /**
     * Stores parameters for requests to the FusedLocationProviderApi.
     */
    private var mLocationRequest: LocationRequest? = null
    /**
     * Stores the types of location services the client is interested in using. Used for checking
     * settings to determine if the device has optimal location settings.
     */
    private var mLocationSettingsRequest: LocationSettingsRequest? = null
    /**
     * Callback for Location events.
     */
    private var mLocationCallback: LocationCallback? = null
    /**
     * Represents a geographical location.
     */
    private val REQUEST_CHECK_SETTINGS = 109
    private var mLocationRequestHighAccuracy: LocationRequest? = null
    private var mCurrentLocation: Location? = null
    var activityLocation: Location? = null


    private fun initializeMap() {
        //  mMap = mapFragment.getMap();
        //mapFragment!!.getMapAsync(this)
        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        MapsInitializer.initialize(context)

    }
    // fun

    fun iniialise() {
        createLocationRequest()
        createTask()
        initializeMap()
    }

    fun IsLocationAvailable(): Boolean {
        //  Log.i("asdfg", "location updated here 1 ")
        return mCurrentLocation != null
    }

    fun Getcurrentlocation(): Location {

        return mCurrentLocation!!
    }

    private fun createLocationRequest() {
        mLocationRequest = LocationRequest()
        // Sets the desired interval for active location updates. This interval is
        // inexact. You may not receive updates at all if no location sources are available, or
        // you may receive them slower than requested. You may also receive updates faster than
        // requested if other applications are requesting location at a faster interval.
        mLocationRequest!!.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS)
        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        mLocationRequest!!.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS)
        mLocationRequest!!.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(mLocationRequest!!)
        mLocationSettingsRequest = builder.build()
    }

    fun createTask() {
        val builder = LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest!!)
        val result = LocationServices.getSettingsClient(context!!).checkLocationSettings(builder.build())


        result.addOnCompleteListener { task ->
            try {
                val response = task.getResult(ApiException::class.java!!)
                // All location settings are satisfied. The client can initialize location
                // requests here.
                mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context!!)
                mSettingsClient = LocationServices.getSettingsClient(context!!)
                createLocationCallback()
                createLocationRequest()

                CheckForPermission()

            } catch (exception: ApiException) {
                when (exception.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED ->
                        // Location settings are not satisfied. But could be fixed by showing the
                        // user a dialog.
                        try {
                            // Cast to a resolvable exception.
                            val resolvable = exception as ResolvableApiException
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            resolvable.startResolutionForResult(
                                    activity!!,
                                    REQUEST_CHECK_SETTINGS)
                        } catch (e: Exception) {
                            // Ignore the error.
                        } catch (e: ClassCastException) {
                            // Ignore, should be an impossible error.
                        }

                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                    }
                }// Location settings are not satisfied. However, we have no way to fix the
                // settings so we won't show the dialog.
            }
        }

    }

    private fun CheckForPermission() {
        if (checkPermission()) {
            //   Log.i(LOGTAG, "permissn check");
            //  Snackbar.make(view, "Permission already granted.", Snackbar.LENGTH_LONG).show();
            getLastLocation()


        } else {
            requestPermission()
            // Snackbar.make(view, "Please request permission.", Snackbar.LENGTH_LONG).show();
        }
    }
    private fun getLastLocation() {


        if ((ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.


            return
        }
        mFusedLocationClient!!.getLastLocation()
                .addOnCompleteListener(OnCompleteListener<Location> { task ->
                    if (task.isSuccessful && task.result != null) {
                        mCurrentLocation = task.result
                        latitude = mCurrentLocation!!.getLatitude()
                        longitude = mCurrentLocation!!.getLongitude()

                        if (mCurrentLocation != null) {
                            mapInterface!!.onCurrentLocstion(mCurrentLocation!!)
                        }
                        //  initializeMap();
                        //this is the last location
                        if (ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this as Activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return@OnCompleteListener
                        }
                        mFusedLocationClient!!.requestLocationUpdates(mLocationRequest, mLocationCallback, null)

                    } else {
                        Log.w("location service", "getLastLocation:exception", task.exception)
                        //showSnackbar(getString(R.string.no_location_detected));
                    }
                })
    }

    public fun requestPermission() {
        // Log.i(LOGTAG, "Resquset permission");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity!!.requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_REQUEST_CODE)
        }
    }

    private fun checkPermission(): Boolean {
        val result = ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION)
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun createLocationCallback() {
        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                super.onLocationResult(locationResult)
                //this is the updated location

                mCurrentLocation = locationResult!!.lastLocation

                if (mCurrentLocation != null) {
                    //  mapInterface!!.onCurrentLocstion(mCurrentLocation!!)
                }

                Log.i("location", "location updated " + mCurrentLocation!!.getLongitude() + "   " + mCurrentLocation!!.getLatitude())
                // mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
                //   updateLocationUI();


            }
        }
    }


    fun getCountryfromLatLong(latitude: Double, longitude: Double): String {

        var geocoder: Geocoder = Geocoder(context, Locale.getDefault());
        var addresses: List<Address>? = null;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (e: Exception) {
        }

        // String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        //  String city = addresses.get(0).getLocality();
        //  String state = addresses.get(0).getAdminArea();
        var country: String = addresses!!.get(0).getCountryName();
        //    String postalCode = addresses.get(0).getPostalCode();
        //   String knownName = addresses.get(0).getFeatureName();
        return country;
    }

    fun getAddressfromLatLong(latitude: Double, longitude: Double): String {

        var geocoder: Geocoder = Geocoder(context, Locale.getDefault());
        var addresses: List<Address>? = null;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (e: Exception) {
        }

        var address = addresses!!.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        //  String city = addresses.get(0).getLocality();
        //  String state = addresses.get(0).getAdminArea();
        //var country: String = addresses!!.get(0).getCountryName();
        //    String postalCode = addresses.get(0).getPostalCode();
        //   String knownName = addresses.get(0).getFeatureName();
        return address;
    }
}