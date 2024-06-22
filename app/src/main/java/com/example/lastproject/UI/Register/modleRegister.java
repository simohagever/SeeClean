package com.example.lastproject.UI.Register;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lastproject.repstory.Repository;
import com.example.lastproject.repstory.User;

/**
 * The modleRegister class handles operations related to user registration.
 */
public class modleRegister {

    private Repository rep;
    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    /**
     * Constructor for modleRegister.
     *
     * @param context the context in which the module operates
     */
    public modleRegister(Context context) {
        this.context = context;
        rep = new Repository(this.context);
        sharedPreferences = context.getSharedPreferences("main", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    /**
     * Checks if a user login exists.
     *
     * @param email the email of the user
     * @param pass  the password of the user
     * @return true if the login exists, false otherwise
     */
    public boolean isExistLogin(String email, String pass) {
        return rep.isExistLogin(email, pass);
    }


    public boolean CheckUps(User user, EditText EIpassword) {
        if(user.getFirstName().toString().equals("")){
            //Toast.makeText(this, "fill first name", Toast.LENGTH_SHORT).show();

            Toast.makeText(context, "fill first name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(user.getFirstName().toString().length()<2){

            Toast.makeText(context, "first name need to be at least two letters", Toast.LENGTH_SHORT).show();
            return false;
        }
        for (int i = 0; i < user.getFirstName().length(); i++) {
            if(!(user.getFirstName().toString().charAt(i)>='a' && user.getFirstName().toString().charAt(i)<='z' || user.getFirstName().toString().charAt(i)>='A' && user.getFirstName().toString().charAt(i)<='Z')){
                Toast.makeText(context, "first name need to be only with English letters", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if(user.getLastName().toString().equals("")){

            Toast.makeText(context, "fill last name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(user.getLastName().toString().length()<2){

            Toast.makeText(context, "last name need to be at least two letters", Toast.LENGTH_SHORT).show();
            return false;
        }
        for (int i = 0; i < user.getLastName().length(); i++) {
            if(!(user.getLastName().toString().charAt(i)>='a' && user.getLastName().toString().charAt(i)<='z' || user.getLastName().toString().charAt(i)>='A' && user.getLastName().toString().charAt(i)<='Z')){

                Toast.makeText(context, "last name need to be only with English letters", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if(user.getPassword().toString().equals("")){

            Toast.makeText(context, "fill password", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(user.getPassword().toString().length()<4){
            Toast.makeText(context, "password need to be at least four letters", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(EIpassword.getText().toString().equals("")){

            Toast.makeText(context, "fill password confirmation", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!(user.getPassword().toString().equals(EIpassword.getText().toString()))){
            Toast.makeText(context, "password confirmation need to be the same as the password", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(user.getEmail().toString().equals("")){
            Toast.makeText(context, "fill email confirmation", Toast.LENGTH_SHORT).show();
            return false;
        }

//            for (int i = 0; i < EFname1.length(); i++) {
//                if(!(EFname1.getText().toString().charAt(i)>='a' && EFname1.getText().toString().charAt(i)<='a' )){
//                    Toast.makeText(this, " email to be only with English letters or numbers", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//            }

        for (int i = 0; i < user.getEmail().length(); i++) {
            if(!(user.getEmail().toString().indexOf(".com") == user.getEmail().toString().length()-4 || user.getEmail().toString().indexOf(".co.") == user.getEmail().toString().length()-6))
            {
                Toast.makeText(context, "email format is invalid(.com)", Toast.LENGTH_SHORT).show();
                return false;
            }
            if(user.getEmail().toString().indexOf("@") ==-1  ){
                Toast.makeText(context, "email format is invalid(-@)", Toast.LENGTH_SHORT).show();
                return false;


            }
            if(!(user.getEmail().toString().indexOf("@") == user.getEmail().toString().lastIndexOf("@") )){
                Toast.makeText(context, "email format is invalid(2@)", Toast.LENGTH_SHORT).show();
                return false;

            }
            if(!(user.getEmail().toString().indexOf(".") -  user.getEmail().toString().indexOf("@") >=3 )){
                Toast.makeText(context, "email format is invalid(@.)", Toast.LENGTH_SHORT).show();
                return false;

            }


        }
        String mail = user.getEmail().toString();
        String pass = user.getPassword().toString();
        String userName = user.getFirstName().toString();



        if(rep.isExistsRegister(mail,pass,userName,3)){

            Toast.makeText(context, "there is someone with this first name in the system already", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(rep.isExistsRegister(mail,pass,userName,1)){

            Toast.makeText(context, "there is someone with this email in the system already", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(user.getAddress().toString().equals("")){

            Toast.makeText(context, "fill address", Toast.LENGTH_SHORT).show();
            return false;
        }
        rep.addUser(userName, user.getLastName(), pass, mail, user.getAddress());
        return true;

    }

    /**
     * Adds a new user.
     *
     * @param username the username of the user
     * @param lastname the last name of the user
     * @param pass     the password of the user
     * @param mail     the email of the user
     * @param address  the address of the user
     */
    public void addUser(String username, String lastname, String pass, String mail, String address) {
        rep.addUser(username, lastname, pass, mail, address);
    }

    /**
     * Sets the "Remember" flag.
     *
     * @param flag the flag to be set
     */
    public void RememberMe(boolean flag) {
        editor.putBoolean("Remember", flag);
        editor.apply();
    }

    /**
     * Saves user credentials for future login.
     *
     * @param pass   the password entered by the user
     * @param Uemail the email entered by the user
     */
    public void SaveUser(EditText pass, EditText Uemail) {
        editor.putString("RememberPass", pass.getText().toString());
        editor.putString("RememberEmail", Uemail.getText().toString());
        editor.apply();
    }

}
