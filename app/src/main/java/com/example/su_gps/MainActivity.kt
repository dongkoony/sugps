package com.example.su_gps

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import android.widget.Button



class MainActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        findViewById<Button>(R.id.activateBtn).setOnClickListener {
            setMockLocationEnabled(true)
        }

        findViewById<Button>(R.id.deactivateBtn).setOnClickListener {
            setMockLocationEnabled(false)
        }

        findViewById<Button>(R.id.suwonBtn).setOnClickListener {
            setMockLocation(37.265622262180806, 127.00059751241832)
        }

        findViewById<Button>(R.id.seryuBtn).setOnClickListener {
            setMockLocation(37.243971644786676, 127.0137163121809)
        }

        findViewById<Button>(R.id.terminalBtn).setOnClickListener {
            setMockLocation(37.25103947576096, 127.01982118957986)
        }
    }

    private fun setMockLocationEnabled(enabled: Boolean) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // 위치 권한을 요청
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1234)
            return
        }

        try {
            fusedLocationClient.setMockMode(enabled)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Mock location setting failed: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun setMockLocation(latitude: Double, longitude: Double) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // 위치 권한을 요청
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1234)
            return
        }

        val mockLocation = Location("MockProvider")
        mockLocation.latitude = latitude
        mockLocation.longitude = longitude
        mockLocation.altitude = 0.0
        mockLocation.time = System.currentTimeMillis()
        mockLocation.elapsedRealtimeNanos = System.nanoTime()
        mockLocation.accuracy = 1.0f

        try {
            fusedLocationClient.setMockLocation(mockLocation)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Set mock location failed: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}
