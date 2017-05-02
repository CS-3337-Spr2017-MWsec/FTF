package cat.dev.cutit;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.maps.android.SphericalUtil;

public class GoogleMapsFragment extends Fragment implements OnMapReadyCallback, LocationListener {

    private static final String TAG = "GoogleMapsFragment";

    private GoogleMap mGoogleMap;
    private LocationManager mLocationManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mLocationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

        getActivity().setTitle("Home");

        return inflater.inflate(R.layout.fragment_google_maps, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map))
                .getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(0, 0), 1.0f));

        UiSettings settings = mGoogleMap.getUiSettings();
        settings.setCompassEnabled(true);
        settings.setMyLocationButtonEnabled(true);
        settings.setZoomControlsEnabled(true);

        enableMyLocation();
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i(TAG, "Your location updated.");

        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(
                new LatLng(location.getLatitude(), location.getLongitude())));
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != 1) // LOCATION_PERMISSION_REQUEST_CODE
            return;

        if (permissionWasGranted(permissions, grantResults, Manifest.permission.ACCESS_FINE_LOCATION))
            enableMyLocation();
        else
            Log.e(TAG, "Permission was not granted!");
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    getActivity(), new String[]{ Manifest.permission.ACCESS_FINE_LOCATION }, 1);

        } else if (mGoogleMap != null) {
            mGoogleMap.setMyLocationEnabled(true);

            if (mLocationManager != null) {
                // Update every minute
                mLocationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER, 60000, 0, this);

                Location location = mLocationManager.getLastKnownLocation(
                        LocationManager.GPS_PROVIDER);

                if (location != null) {
                    Log.i(TAG, location.getLatitude() + " " + location.getLongitude());

                    final LatLng currentLatLng = new LatLng(
                            location.getLatitude(), location.getLongitude());

                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15.0f));

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                    reference.child("vendors").orderByValue().addChildEventListener(
                            new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            Log.d("onChildAdded", dataSnapshot.toString());
                            Vendor vendor = dataSnapshot.getValue(Vendor.class);

                            LatLng vendorLatLng = new LatLng(
                                    vendor.getLocation().getLatitude(),
                                    vendor.getLocation().getLongitude());

                            if (vendor.isActive()) {
                                double distance = SphericalUtil.computeDistanceBetween(
                                        currentLatLng, vendorLatLng);

                                // 8046.72 meters is equivalent to 5 miles
                                if (distance <= 8046.72) {
                                    Log.i("onChildAdded", vendor.getBusinessName());

                                    createMarker(vendor.getLocation().getLatitude(),
                                            vendor.getLocation().getLongitude(),
                                            vendor.getBusinessName(), "");
                                }
                            }
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    createMarker(34.07357, -118.16394, "Angie's", "");
                    createMarker(34.059346, -118.172533, "Las Ranas", "");

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if (user != null) {
                        FirebaseDatabase.getInstance()
                                .getReference("/users/" + user.getUid() + "/location")
                                .setValue(location);
                    }
                }
            }
        }
    }

    private static boolean permissionWasGranted(String[] permissions, int[] results, String permission) {
        for (int i = 0; i < permissions.length; i++)
            if (permission.equals(permissions[i]))
                return results[i] == PackageManager.PERMISSION_GRANTED;
        return false;
    }

    private void createMarker(double latitude, double longitude, String title, String description) {
        mGoogleMap.addMarker(
            new MarkerOptions()
                    .position(new LatLng(latitude, longitude))
                    .anchor(0.5f, 0.5f)
                    .title(title)
                    .snippet(description)
        );
    }
}
