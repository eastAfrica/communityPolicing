package com.example.nyismaw.communitypolicing.controller.maps;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nyismaw.communitypolicing.R;
import com.example.nyismaw.communitypolicing.AppInfo.CurrentLocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_2, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map33);
        Log.e("neba", "on create view " + mapFragment);
        mapFragment.getMapAsync(this);
        return view;
    }

    static GoogleMap mMap;
    static List<Marker> markers = new ArrayList();

    public static GoogleMap getmMap() {
        return mMap;
    }

    public static void setmMap(GoogleMap mMap) {
        MapFragment.mMap = mMap;
    }

    public static List<Marker> getMarkers() {
        return markers;
    }

    public static void setMarkers(List<Marker> markers) {
        MapFragment.markers = markers;
    }

    @Override
    public void onMapReady(GoogleMap mMap) {
        mMap.setPadding(0, 140, 0, 0);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        MapFragment.mMap = mMap;
        Log.e(" after that *****", "********************** Map ready    ");
        Location location = CurrentLocation.location;
        if(location!=null){
            LatLng latlong = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
            mMap.setMapType(5);
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15f));
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            }
        }


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
