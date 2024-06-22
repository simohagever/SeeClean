package com.example.lastproject.UI.Admin;

import android.content.Context;

import com.example.lastproject.repstory.Repository;

/**
 * ModuleAdmin class to manage admin-related functionalities and interaction with the Repository.
 */
public class ModuleAdmin {

    private Repository rp;
    private Context context;

    /**
     * Constructor to initialize ModuleAdmin with context and create a Repository instance.
     *
     * @param context the context of the application
     */
    public ModuleAdmin(Context context){
        this.context = context;
        rp = new Repository(context);
    }

    /**
     * Deletes all data from the repository.
     */
    public void deleteAllData() {
        rp.deleteAllData();
    }

    /**
     * Gets the user row number by email from the repository.
     *
     * @param email the email of the user
     * @return the row number of the user
     */
    public int getUserRowNumberByEmail(String email) {
        return rp.getUserRowNumberByEmail(email);
    }

    /**
     * Deletes a specific row from the repository by row ID.
     *
     * @param row_id the ID of the row to be deleted
     */
    public void deleteOneRow(String row_id) {
        rp.deleteOneRow(row_id);
    }
    public void deleteAllMarkers() { rp.deleteAllMarkers();}

    public void deleteMarkerByID(String marker) { rp.deleteMarkerByID(marker); }

    public void forgetUser()
    {
        rp.DeleteShare();
    }
}


