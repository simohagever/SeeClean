package com.example.lastproject.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * A helper class for managing SQLite database operations.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "BookLibrary.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_library";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_FNAME = "FirstName";
    private static final String COLUMN_LNAME = "LastName";
    private static final String COLUMN_PASSWORD = "Password";
    private static final String COLUMN_EMAIL = "Email";
    private static final String COLUMN_ADDRESS = "Address";

    /**
     * Constructor for MyDatabaseHelper.
     *
     * @param context The application context.
     */
    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the database table
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FNAME + " TEXT, " +
                COLUMN_LNAME + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_ADDRESS + " )";
        db.execSQL(query);
    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // Drop existing table and recreate on upgrade
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * Check if a user with the provided email and password exists in the database.
     *
     * @param email    The email of the user.
     * @param password The password of the user.
     * @return True if the user exists, false otherwise.
     */
    public boolean isExistsLogin(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_EMAIL + " FROM " + TABLE_NAME +
                " WHERE " + COLUMN_EMAIL + " = ? AND " + COLUMN_PASSWORD + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{email, password});
        boolean isExistLogin = cursor.getCount() == 1;
        cursor.close();
        db.close();

        return isExistLogin;
    }

    /**
     * Check if a user with the provided email, password, or name exists in the database.
     *
     * @param email    The email of the user.
     * @param password The password of the user.
     * @param name     The name of the user.
     * @param which    Determines which field to check (email, password, or name).
     * @return True if a user with the specified field value exists, false otherwise.
     */
    public boolean isExistsRegister(String email, String password, String name, int which) {
        SQLiteDatabase db = this.getReadableDatabase();
        String queryEmail = "SELECT " + COLUMN_EMAIL + " FROM " + TABLE_NAME + " WHERE " + COLUMN_EMAIL + " = ?";
        String queryPassword = "SELECT " + COLUMN_PASSWORD + " FROM " + TABLE_NAME + " WHERE " + COLUMN_PASSWORD + " = ?";
        String queryName = "SELECT " + COLUMN_FNAME + " FROM " + TABLE_NAME + " WHERE " + COLUMN_FNAME + " = ?";
        Cursor cursor;
        boolean isExist = false;

        switch (which) {
            case 1:
                cursor = db.rawQuery(queryEmail, new String[]{email});
                isExist = cursor.getCount() > 0;
                cursor.close();
                break;
            case 2:
                cursor = db.rawQuery(queryPassword, new String[]{password});
                isExist = cursor.getCount() > 0;
                cursor.close();
                break;
            case 3:
                cursor = db.rawQuery(queryName, new String[]{name});
                isExist = cursor.getCount() > 0;
                cursor.close();
                break;
        }

        db.close();
        return isExist;
    }

    /**
     * Add a new user to the database.
     *
     * @param Fname    The first name of the user.
     * @param Lname    The last name of the user.
     * @param Password The password of the user.
     * @param Email    The email of the user.
     * @param Address  The address of the user.
     */
    public void addItem(String Fname, String Lname, String Password, String Email, String Address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_FNAME, Fname);
        cv.put(COLUMN_LNAME, Lname);
        cv.put(COLUMN_PASSWORD, Password);
        cv.put(COLUMN_EMAIL, Email);
        cv.put(COLUMN_ADDRESS, Address);

        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Get the row number of a user based on their email.
     *
     * @param email The email of the user.
     * @return The row number of the user in the database.
     */
    public int getUserRowNumberByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT ROWID FROM " + TABLE_NAME +
                " WHERE " + COLUMN_EMAIL + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});
        int rowNumber = -1; // Default value if user is not found
        if (cursor.moveToFirst()) {
            rowNumber = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return rowNumber;
    }

    /**
     * Find a user in the database based on their email.
     *
     * @param email The email of the user.
     * @return A cursor pointing to the user's data.
     */
    public Cursor FindUserByEmail(String email)
    {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_EMAIL + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{email});
        cursor.moveToFirst();
        return cursor;
    }

    /**
     * Read all data from the database.
     *
     * @return A cursor pointing to the result set of all data.
     */
    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    /**
     * Update user data in the database.
     *
     * @param row_id   The ID of the row to be updated.
     * @param Fname    The first name of the user.
     * @param Lname    The last name of the user.
     * @param Password The password of the user.
     * @param Email    The email of the user.
     * @param Address  The address of the user.
     */
    public void updateData(String row_id, String Fname, String Lname, String Password, String Email, String Address){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_FNAME, Fname);
        cv.put(COLUMN_LNAME, Lname);
        cv.put(COLUMN_PASSWORD, Password);
        cv.put(COLUMN_EMAIL, Email);
        cv.put(COLUMN_ADDRESS, Address);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Delete a row from the database.
     *
     * @param row_id The ID of the row to be deleted.
     */
    public void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Delete all data from the database except for the admin user.
     */
    public void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = readAllData();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            if(!cursor.getString(4).equals("rotemkorkus11@gmail.com")) {
                db.delete(TABLE_NAME, "_id=?", new String[]{cursor.getString(0)});
            }
        }
        Toast.makeText(context, "Deleted all data", Toast.LENGTH_SHORT).show();
    }
}
