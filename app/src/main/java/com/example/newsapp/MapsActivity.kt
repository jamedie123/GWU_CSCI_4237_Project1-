package com.example.newsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import android.app.ProgressDialog
import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.Models.NewsApiResponse
import com.example.newsapp.Models.NewsHeadlines
import com.squareup.picasso.Picasso

//import com.example.mygooglemap.data-binding.ActivityMyGoogleMapBinding

//import java.util.List;

    class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

        private lateinit var mMap: GoogleMap
//    private lateinit var binding: ActivityMyGoogleMapBinding

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
//            setContentView(R.layout.activity_my_google_map)
//        binding = ActivityMyGoogleMapBinding.inflate(layoutInflater)
//        setContentView(binding.root)

            val actionBar = supportActionBar
            actionBar!!.title = "MapsActivity"
            actionBar.setDisplayHomeAsUpEnabled(true)

            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
            mapFragment.getMapAsync(this)
        }

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera. In this case,
         * we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to install
         * it inside the SupportMapFragment. This method will only be triggered once the user has
         * installed Google Play services and returned to the app.
         */
        override fun onMapReady(googleMap: GoogleMap) {
            mMap = googleMap

            // Add a marker in Sydney and move the camera
            val sydney = LatLng(-34.0, 151.0)
            val newYork = LatLng(40.1278, -74.0060)
            val LA = LatLng(34.0549, -118.2426)
            mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
            mMap.addMarker(MarkerOptions().position(newYork).title("Marker in NewYork"))
            mMap.addMarker(MarkerOptions().position(LA).title("Marker in LOS ANGELES"))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LA, 10f))
        }
    }
