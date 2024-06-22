package com.example.lastproject.UI.homePage;

import android.content.Context;

import com.example.lastproject.repstory.Repository;

import org.checkerframework.checker.units.qual.C;

/**
 * ModuleHomePage class provides functionalities related to the home page module.
 */
public class moduleHomePage {

    private Repository rp;
    private Context context;

    /**
     * Constructor for moduleHomePage.
     *
     * @param context the context of the activity or application
     */
    public moduleHomePage(Context context){
        this.context = context;
        rp = new Repository(context);
    }

    /**
     * Deletes the shared data in the repository.
     */
    public void DeleteShare() {
        rp.DeleteShare();
    }

}
