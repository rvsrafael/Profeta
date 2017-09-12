package com.profetadabola.maps;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.profetadabola.R;
import com.profetadabola.tools.Constant;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double latitude;
    private double longitude;
    private String titlePin;
    private FragmentActivity myContext;

    public MapsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        SupportMapFragment mapFragment = (SupportMapFragment) myContext.getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
        //loadingArguments();
    }

    private void init() {
        setupMap();
    }

    private void setupMap() {

    }

    @Override
    public void onAttach(Activity activity) {
        myContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }

    private void loadingArguments() {
//
//        if (getIntent().getExtras() != null) {
//            latitude = getIntent().getExtras().getDouble(Constant.LATITUDE);
//            longitude = getIntent().getExtras().getDouble(Constant.LONGITUDE);
//            titlePin = getIntent().getExtras().getString(Constant.TITLE_PIN_MAPS);
//        } else {
//            Toast.makeText(getApplicationContext(), R.string.message_no_locality,Toast.LENGTH_LONG).show();
//        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng localization = new LatLng(latitude, longitude);
        LatLng localization = new LatLng(40.741895, -73.989308);
        mMap.addMarker(new MarkerOptions().position(localization).title(titlePin).visible(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(localization));

        CameraPosition cameraPosition = new CameraPosition.Builder().target(localization).zoom(16.0f).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mMap.moveCamera(cameraUpdate);
    }
}
