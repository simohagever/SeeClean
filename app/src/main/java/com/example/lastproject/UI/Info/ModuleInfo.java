package com.example.lastproject.UI.Info;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.widget.Toast;

import com.example.lastproject.repstory.Repository;

/**
 * This class handles the information module.
 */
public class ModuleInfo {

    Context context;
    Repository repository;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    /**
     * Constructor for ModuleInfo class.
     * @param context The context of the application.
     */
    public ModuleInfo(Context context){
        this.context= context;
        repository = new Repository(context);
        sharedPreferences = context.getSharedPreferences("main", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    /**
     * Retrieves the user row number by email.
     * @param email The email of the user.
     * @return The row number of the user.
     */
    public int getUserRowNumberByEmail(String email){
        return repository.getUserRowNumberByEmail(email);
    }

    /**
     * Finds user information by email.
     * @param email The email of the user.
     * @return The cursor containing user information.
     */
    public Cursor FindUserByEmail(String email) {
        return repository.FindUserByEmail(email);
    }

    /**
     * Checks if credentials exist.
     * @return True if credentials exist, otherwise false.
     */
    public boolean CredentialsExist(){
        return sharedPreferences.contains("email");
    }

    /**
     * Retrieves stored credentials.
     * @return The stored credentials.
     */
    public String getCredentials(){
        return sharedPreferences.getString("RememberEmail","");
    }

    /**
     * Retrieves stored password.
     * @return The stored password.
     */
    public String getCredentials2(){
        return sharedPreferences.getString("RememberPass","");
    }

    /**
     * Updates user data.
     * @param row_id The row ID of the user.
     * @param Fname The first name of the user.
     * @param Lname The last name of the user.
     * @param Password The password of the user.
     * @param Email The email of the user.
     * @param Address The address of the user.
     */
    public void UpdateData(String row_id, String Fname, String Lname, String Password, String Email, String Address){
        repository.updateData(row_id, Fname, Lname, Password, Email, Address);
    }

    /**
     * Checks user sign up credentials.
     * @param mail The email entered by the user.
     * @param pass The password entered by the user.
     * @param userName The username entered by the user.
     * @return True if sign up credentials are valid, otherwise false.
     */
    public boolean CheckUps(String mail, String pass, String userName){
        return repository.isExistsRegister(mail, pass, userName, 1);
    }
}
