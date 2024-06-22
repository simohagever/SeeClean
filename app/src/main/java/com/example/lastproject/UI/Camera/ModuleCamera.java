package com.example.lastproject.UI.Camera;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import com.example.lastproject.DB.FireBaseHelper;
import com.example.lastproject.repstory.Repository;

/**
 * ModuleCamera class that interacts with the repository for camera-related operations.
 */
public class ModuleCamera {
    Repository repository;
    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    /**
     * Constructor for ModuleCamera.
     *
     * @param context the context in which the ModuleCamera is being used.
     */
    public ModuleCamera(Context context) {
        repository = new Repository(context);
        this.context = context;
        sharedPreferences = context.getSharedPreferences("main", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void forgetUser()
    {
        editor.clear();
        editor.apply();
    }
}
