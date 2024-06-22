package com.example.lastproject.UI.SelectReport;

import android.content.Context;

import com.example.lastproject.DB.FireBaseHelper;
import com.example.lastproject.repstory.Repository;

/**
 * The ModuleSelect class provides methods to interact with marker information in the repository.
 */
public class ModuleSelect {

    Repository repository;
    Context context;

    /**
     * Constructor for ModuleSelect class.
     * @param context The context in which the ModuleSelect is created.
     */
    public ModuleSelect(Context context) {
        repository = new Repository(context);
        this.context = context;
    }

    /**
     * Retrieves marker information from the repository.
     * @param id The ID of the marker.
     * @param callback Callback for handling the fetched marker data.
     */
    public void getMarkerInfo(String id, FireBaseHelper.MarkerFetched callback) {
        repository.getMarkerInfo(id, callback);
    }

    /**
     * Deletes a marker from the repository.
     * @param id The ID of the marker to be deleted.
     * @param callback Callback for handling the result of marker deletion.
     */
    public void deleteMarker(String id, FireBaseHelper.DeletedMarker callback) {
        repository.deleteMarker(id, callback);
    }
}
