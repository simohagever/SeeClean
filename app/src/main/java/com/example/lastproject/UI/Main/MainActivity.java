package com.example.lastproject.UI.Main;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lastproject.R;


import com.example.lastproject.UI.Register.Register;
import com.example.lastproject.UI.homePage.HomePage;
import com.example.lastproject.repstory.CurrentUser;
import com.example.lastproject.repstory.Repository;
import com.google.android.gms.location.CurrentLocationRequest;

/**
 * This activity represents the main screen of the application.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnlogin, btnMlogin;
    ActivityResultLauncher<String[]> locationPermissionRequest;
    moduleLogin md;
    private EditText Epass, Eemail;
    private moduleLogin module;
    SharedPreferences sharedPreferences;
    private CheckBox checkBox;
    SharedPreferences.Editor editor;

    /**
     * Called when the activity is starting.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("main", Context.MODE_PRIVATE);
        md = new moduleLogin(this);
        locationPermissionRequest = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
            Boolean fineLocationGranted = result.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false);
            Boolean coarseLocationGranted = result.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false);
            if (fineLocationGranted != null && fineLocationGranted && coarseLocationGranted != null && coarseLocationGranted) {
                //location access granted.
            } else {
                // No location access granted.
            }
        });
        locationPermissionRequest.launch(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION});

        btnlogin = findViewById(R.id.btnLog);
        btnMlogin = findViewById(R.id.MLogin);
        btnMlogin.setOnClickListener(this);
        btnlogin.setOnClickListener(this);
        module = new moduleLogin(this);

        if (!sharedPreferences.getString("RememberEmail", "-1").equals("-1")) {
            CurrentUser.initilizeUser(sharedPreferences.getString("RememberEmail", ""));
            startActivity(new Intent(MainActivity.this, HomePage.class));
        }
    }

    /**
     * Displays the custom dialog for login.
     * @param v The view.
     */
    public void showCustomDialog(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.login, null);
        builder.setView(view);
        Eemail = view.findViewById(R.id.emailEdit);
        Epass = view.findViewById(R.id.passEdit);
        checkBox = view.findViewById(R.id.remember2);
        builder.setNeutralButton("login", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog1, int which) {
                String email = Eemail.getText().toString();
                String pass = Epass.getText().toString();
                if (!(module.isExistLogin(email, pass))) {
                    Toast.makeText(MainActivity.this, "the email or password doesn't found", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(MainActivity.this, HomePage.class);
                if (checkBox.isChecked()) {
                    md.SaveUser(Epass, Eemail);
                } else {
                    intent.putExtra("Pass", pass);
                    intent.putExtra("Email", email);
                }
                CurrentUser.initilizeUser(Eemail.getText().toString());
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Log in successfully", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setPositiveButton("register", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    /**
     * Called when a view has been clicked.
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v == btnlogin) {
            showCustomDialog(v);
        }
        if (v == btnMlogin) {
            Intent intent = new Intent(MainActivity.this, Register.class);
            startActivity(intent);
        }
    }
}
