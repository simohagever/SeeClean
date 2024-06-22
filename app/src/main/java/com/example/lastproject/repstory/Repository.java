package com.example.lastproject.repstory;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;

import com.example.lastproject.DB.FireBaseHelper;
import com.example.lastproject.DB.MyDatabaseHelper;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;


public class Repository {

    Context context;
    User user;

    private MyDatabaseHelper myDatabaseHelper;
    FireBaseHelper myFireBaseHelper;


    public Repository(Context context)
    {
        this.context = context;
        myDatabaseHelper = new MyDatabaseHelper(this.context);
        myFireBaseHelper = new FireBaseHelper(this.context);
    }


    public void getMarkerInfo(String id, FireBaseHelper.MarkerFetched callback) { myFireBaseHelper.getMarkerInfo(id, callback);}
    public void PlaceMarkers(GoogleMap googleMap, FireBaseHelper.markerCreated callback) { myFireBaseHelper.PlaceMarkers(googleMap, callback);}
    public void AddMarker(String Description, String time, String date, Bitmap picture, LatLng latLng , FireBaseHelper.markerAdded callback){ myFireBaseHelper.AddMarker(Description, time, date, picture, latLng,callback);}


        public void addUser( String Fname, String Lname, String Password, String Email, String Address)
    { myDatabaseHelper.addItem(Fname, Lname,Password,Email,Address);}

 public void DeleteShare(){
     SharedPreferences sharedPreferences = context.getSharedPreferences("main",Context.MODE_PRIVATE);
     SharedPreferences.Editor simo = sharedPreferences.edit();
     simo.clear().apply();
 }
    public Cursor FindUserByEmail(String email) { return myDatabaseHelper.FindUserByEmail(email); }
    public int getUserRowNumberByEmail(String email){return myDatabaseHelper.getUserRowNumberByEmail(email);}
    public void deleteAllData() { myDatabaseHelper.deleteAllData();}

    public boolean isExistLogin(String email, String pass) { return myDatabaseHelper.isExistsLogin(email,pass);}
    public boolean  isExistsRegister(String mail,String password,String name, int num) {
        return myDatabaseHelper.isExistsRegister(mail,password,name,num);
    }
    public void deleteOneRow(String row_id){myDatabaseHelper.deleteOneRow(row_id);}
    public void deleteMarker(String id, FireBaseHelper.DeletedMarker callback) { myFireBaseHelper.deleteMarker(id, callback);}

    public void updateData(String row_id, String Fname, String Lname,String Password,String Email, String Address){myDatabaseHelper.updateData(row_id,Fname,Lname,Password,Email,Address);}
    public MyDatabaseHelper getMyDatabaseHelper() {
        return myDatabaseHelper;
    }

    public void deleteMarkerByID(String marker) { myFireBaseHelper.deleteMarkerByID(marker); }
    public void deleteAllMarkers() { myFireBaseHelper.deleteAllMarkers();}

//    public  void  getIsuserAlreadyRegister(){
//       this.myDatabaseHelper.isExistsRegister(this.user.getEmail(), this.user.getPassword(), this.user.getFirstName());
//   }



}
