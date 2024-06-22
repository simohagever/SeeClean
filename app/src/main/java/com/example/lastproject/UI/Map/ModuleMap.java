package com.example.lastproject.UI.Map;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.lastproject.DB.FireBaseHelper;
import com.example.lastproject.repstory.Repository;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

/**
 * The ModuleMap class handles operations related to placing markers on the map.
 */
public class ModuleMap {

    Repository repository;
    Context context;

    /**
     * Constructor for ModuleMap.
     *
     * @param context the context in which the module operates
     */
    public ModuleMap(Context context) {
        repository = new Repository(context);
        this.context = context;
    }

    /**
     * PlaceMarkers method places markers on the map.
     *
     * @param googleMap the GoogleMap object representing the map
     * @param callback  a callback function invoked after markers are placed
     */
    public void PlaceMarkers(GoogleMap googleMap, FireBaseHelper.markerCreated callback) {
        repository.PlaceMarkers(googleMap, callback);
    }

    /**
     * AddMarker method adds a marker to the map.
     *
     * @param Description the description of the marker
     * @param time        the time associated with the marker
     * @param date        the date associated with the marker
     * @param picture     the picture associated with the marker
     * @param latLng      the LatLng object representing the position of the marker
     * @param callback    a callback function invoked after the marker is added
     */
    public void AddMarker(String Description, String time, String date, Bitmap picture, LatLng latLng, FireBaseHelper.markerAdded callback) {
        repository.AddMarker(Description, time, date, picture, latLng, callback);
    }

}
