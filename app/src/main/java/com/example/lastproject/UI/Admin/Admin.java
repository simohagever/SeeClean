package com.example.lastproject.UI.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lastproject.R;
import com.example.lastproject.UI.Camera.Camera;
import com.example.lastproject.UI.Info.Info;
import com.example.lastproject.UI.Main.MainActivity;
import com.example.lastproject.UI.Map.Map;

import com.example.lastproject.UI.homePage.HomePage;
import com.example.lastproject.repstory.CurrentUser;
import com.google.android.material.navigation.NavigationView;

/**
 * Admin activity class to handle admin functionalities such as navigation and user management.
 */
public class Admin extends AppCompatActivity implements View.OnClickListener {

    private NavigationView navigationView;
    private EditText delete, deleteMarker;
    private Button navi;
    private ModuleAdmin moduleAdmin;
    boolean menuVisible = false;
    private Button btnDeleteAll, btnDeleteOne, btnDeleteMarker, btnDeleteAllMarkers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        moduleAdmin = new ModuleAdmin(this);
        navi = findViewById(R.id.navigation);
        navi.setOnClickListener(this);
        btnDeleteAll = findViewById(R.id.btnDeleteUsers);
        btnDeleteAll.setOnClickListener(this);
        btnDeleteOne = findViewById(R.id.btnDeleteUser);
        btnDeleteOne.setOnClickListener(this);

        deleteMarker = findViewById(R.id.etDeleteMarker);
        btnDeleteMarker = findViewById(R.id.btnDeleteMarker);
        btnDeleteMarker.setOnClickListener(this);

        btnDeleteAllMarkers = findViewById(R.id.btnDeleteMarkers);
        btnDeleteAllMarkers.setOnClickListener(this);

        navigationView = findViewById(R.id.btnNavigation);
        navigationView.setVisibility(View.GONE);
        setNavi();
        delete = findViewById(R.id.editDeleteUser);
    }

    /**
     * Sets up the navigation drawer and handles item selections.
     */
    public void setNavi() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.getOut) {
                    moduleAdmin.forgetUser();
                    Intent intent = new Intent(Admin.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                if (item.getItemId() == R.id.info) {
                    Intent intent = new Intent(Admin.this, Info.class);
                    startActivity(intent);
                }
                if (item.getItemId() == R.id.location) {
                    Intent intent = new Intent(Admin.this, Map.class);
                    startActivity(intent);
                }
                if (item.getItemId() == R.id.homePage) {
                    Intent intent = new Intent(Admin.this, HomePage.class);
                    startActivity(intent);
                }
                if (item.getItemId() == R.id.picture) {
                    Intent intent = new Intent(Admin.this, Camera.class);
                    startActivity(intent);
                }

                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == navi) {
            if (menuVisible) {
                navigationView.setVisibility(View.GONE);
                menuVisible = false;
            } else {
                navigationView.setVisibility(View.VISIBLE);
                menuVisible = true;
            }
        }

        if(v == btnDeleteMarker)
        {
            moduleAdmin.deleteMarkerByID(deleteMarker.getText().toString());
        }
        if(v == btnDeleteAllMarkers)
        {
            moduleAdmin.deleteAllMarkers();
        }

        if (v == btnDeleteAll) {
            moduleAdmin.deleteAllData();
        }

        if (v == btnDeleteOne) {
            String st = delete.getText().toString();
            if(st.toString().equals("rotemkorkus11@gmail.com"))
            {
                Toast.makeText(this, "you cannot delete the admin", Toast.LENGTH_SHORT).show();
                return;
            }
            int num = moduleAdmin.getUserRowNumberByEmail(st.toString());
            moduleAdmin.deleteOneRow(String.valueOf(num));
        }
    }
}
