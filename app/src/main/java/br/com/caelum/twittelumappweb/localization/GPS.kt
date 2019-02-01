package br.com.caelum.twittelumappweb.localization

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.*

class GPS(context: Context) : LocationCallback(){

    private val client : FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    var lastLocation : Location? = null

    override fun onLocationResult(locationResult: LocationResult?) {
        super.onLocationResult(locationResult)
        lastLocation = locationResult?.lastLocation
    }

    @SuppressLint("MissingPermission")
    fun fazBusca(){
        val requisicao = LocationRequest()

        requisicao.apply {
            interval = 1000
            smallestDisplacement = 10.0f
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        client.requestLocationUpdates(requisicao,this, null)
    }

    fun cancela(){
        client.removeLocationUpdates(this)
    }

    fun coordenadas() : Pair<Double, Double>{
        val latitude = lastLocation?.latitude
        val longitude = lastLocation?.longitude

        return Pair(latitude ?: 0.0, latitude ?: 0.0);
    }

}