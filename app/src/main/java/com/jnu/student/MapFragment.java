package com.jnu.student;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jnu.student.data.DataDownload;
import com.jnu.student.data.ShopLocation;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.Marker;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment {


    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }
    private com.tencent.tencentmap.mapsdk.maps.MapView mapView = null;
    public class DataDownloadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return new DataDownload().download(urls[0]);
        }
        @Override
        protected void onPostExecute(String responseData) {
            super.onPostExecute(responseData);
            if (responseData != null) {
                ArrayList<ShopLocation> shopLocations= new DataDownload().parseJsonObjects(responseData);
                TencentMap tencentMap = mapView.getMap();
                for (ShopLocation shopLocation : shopLocations) {
                    LatLng point1 = new LatLng(shopLocation.getLatitude(), shopLocation.getLongitude());
                    MarkerOptions markerOptions = new MarkerOptions(point1)
                            .title(shopLocation.getName());
                    Marker marker = tencentMap.addMarker(markerOptions);


                }
            }
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = rootView.findViewById(R.id.mapView);

        TencentMap tencentMap = mapView.getMap();

        LatLng point1 = new LatLng(22.255453, 113.54145);
        tencentMap.moveCamera(CameraUpdateFactory.newLatLng(point1));

        new Thread(new Runnable() {
            @Override
            public void run() {
                String responseData=new DataDownload().download("http://file.nidama.net/class/mobile_develop/data/bookstore.json");
                ArrayList<ShopLocation> shopLocations= new DataDownload().parseJsonObjects(responseData);
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TencentMap tencentMap = mapView.getMap();
                        for (ShopLocation shopLocation : shopLocations) {
                            LatLng point1 = new LatLng(shopLocation.getLatitude(), shopLocation.getLongitude());
                            MarkerOptions markerOptions = new MarkerOptions(point1)
                                    .title(shopLocation.getName());
                            Marker marker = tencentMap.addMarker(markerOptions);


                        }
                    }
                });
            }
        }).start();

        return rootView;
    }
    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }
    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}