package com.example.lastproject.UI.Main;

import android.app.appsearch.ReportSystemUsageRequest;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;

import com.example.lastproject.repstory.Repository;

/**
 * This class represents the module for login functionality.
 */
public class moduleLogin {

    Repository repository;
    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    /**
     * Constructor for the moduleLogin class.
     * @param context The context.
     */
    public moduleLogin(Context context)
    {
        this.context= context;
        repository = new Repository(context);
        sharedPreferences = context.getSharedPreferences("main",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    /**
     * Checks if login credentials exist in the repository.
     * @param email The email.
     * @param pass The password.
     * @return True if login credentials exist, false otherwise.
     */
    public boolean isExistLogin(String email, String pass)
    {
        return repository.isExistLogin(email,pass);
    }

    /**
     * Sets the Remember Me flag.
     * @param flag The flag value.
     */
    public void RememberMe(boolean flag){
        editor.putBoolean("Remember",flag);
        editor.apply();
    }

    /**
     * Saves the user credentials.
     * @param pass The password.
     * @param Uemail The email.
     */
    public void SaveUser(EditText pass, EditText Uemail){
        editor.putString("RememberPass",pass.getText().toString());
        editor.putString("RememberEmail",Uemail.getText().toString());
        editor.apply();
    }

    /**
     * Checks if credentials exist.
     * @return True if credentials exist, false otherwise.
     */
    public boolean CredentialsExist(){return sharedPreferences.contains("email");}

    /**
     * Gets the shared email.
     * @return The shared email.
     */
    public String getSharedEmail(){ return sharedPreferences.getString("RememberEmail","");}

}
