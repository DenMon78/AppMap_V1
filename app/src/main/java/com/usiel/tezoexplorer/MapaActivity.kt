package com.usiel.tezoexplorer

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.snackbar.Snackbar
import com.usiel.tezoexplorer.databinding.ActivityMapaBinding

class MapaActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapaBinding

    // Aquí obtendrás la latitud y longitud del servicio desde el Intent
    private var serviceLat: Double = 0.0
    private var serviceLng: Double = 0.0

    // Ubicación del usuario
    private var userLat: Double = 0.0
    private var userLng: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMapaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recupera la latitud y longitud del servicio desde el Intent
        serviceLat = intent.getDoubleExtra("LATITUD_SERVICIO", 0.0)
        serviceLng = intent.getDoubleExtra("LONGITUD_SERVICIO", 0.0)

        // Imprime los valores recibidos de latitud y longitud
        println("Recibiendo latitud: $serviceLat, longitud: $serviceLng")

        // Configura insets para manejo de barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializa el mapa
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Asegúrate de que los valores de latitud y longitud son correctos
        if (serviceLat != 0.0 && serviceLng != 0.0) {
            val serviceLatLng = LatLng(serviceLat, serviceLng)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(serviceLatLng, 14f))

            // Coloca el marcador para el servicio
            mMap.addMarker(MarkerOptions().position(serviceLatLng).title("Ubicación del servicio"))
        } else {
            Snackbar.make(binding.root, "No se pudo obtener la ubicación del servicio", Snackbar.LENGTH_LONG).show()
        }

        // Verifica permisos de ubicación
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            enableUserLocation()
            getUserLocation()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
        }
    }

    private fun enableUserLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.isMyLocationEnabled = true
        }
    }

    private fun getUserLocation() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener { location ->
            if (location != null) {
                userLat = location.latitude
                userLng = location.longitude

                val userLatLng = LatLng(userLat, userLng)
                mMap.addMarker(MarkerOptions().position(userLatLng).title("Tu ubicación"))

                val distance = calculateDistance(userLat, userLng, serviceLat, serviceLng)
                Snackbar.make(binding.root, "Distancia al servicio: $distance km", Snackbar.LENGTH_LONG).show()

                drawRoute(LatLng(userLat, userLng), LatLng(serviceLat, serviceLng))
            } else {
                Snackbar.make(binding.root, "No se pudo obtener la ubicación", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun calculateDistance(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Float {
        val results = FloatArray(1)
        android.location.Location.distanceBetween(lat1, lng1, lat2, lng2, results)
        return results[0] / 1000  // Convertimos metros a kilómetros
    }

    private fun drawRoute(userLatLng: LatLng, serviceLatLng: LatLng) {
        val polylineOptions = PolylineOptions()
            .add(userLatLng, serviceLatLng)
            .width(10f)
            .color(ContextCompat.getColor(this, R.color.color_accent))
        mMap.addPolyline(polylineOptions)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                enableUserLocation()
                getUserLocation()
            } else {
                Snackbar.make(binding.root, "Permiso de ubicación denegado", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}
