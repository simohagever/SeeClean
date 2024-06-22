package com.example.lastproject.UI.homePage;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.lastproject.UI.Admin.Admin;
import com.example.lastproject.UI.Camera.Camera;
import com.example.lastproject.UI.Info.Info;
import com.example.lastproject.UI.Main.MainActivity;
import com.example.lastproject.R;
import com.example.lastproject.UI.Map.Map;
import com.example.lastproject.repstory.CurrentUser;
import com.example.lastproject.repstory.Repository;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

/**
 * HomePage class handles the main functionalities of the home page of the application.
 */
public class HomePage extends AppCompatActivity implements View.OnClickListener {
    private TextView txName;
    private Button back, navi;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    moduleHomePage md;
    private NavigationView navigationView;
    boolean menuVisible = false;

    ActivityResultLauncher<String[]> locationPermissionRequest =
            registerForActivityResult(new ActivityResultContracts
                            .RequestMultiplePermissions(), result -> {
                        Boolean fineLocationGranted = result.getOrDefault(
                                Manifest.permission.ACCESS_FINE_LOCATION, false);
                        Boolean coarseLocationGranted = result.getOrDefault(
                                Manifest.permission.ACCESS_COARSE_LOCATION, false);
                        if (fineLocationGranted != null && fineLocationGranted && coarseLocationGranted != null && coarseLocationGranted) {
                            // Location access granted.
                        } else {
                            // No location access granted.
                        }
                    }
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        txName = findViewById(R.id.name);
        // String n = myDatabaseHelper.

        navi = findViewById(R.id.navigation);
        navi.setOnClickListener(this);

        md = new moduleHomePage(this);

        navigationView = findViewById(R.id.btnNavigation);

        if (CurrentUser.getEmail() != null) {
            if (CurrentUser.getEmail().equals("rotemkorkus11@gmail.com")) {
                navigationView.getMenu().getItem(4).setVisible(true);
            }
            else navigationView.getMenu().getItem(4).setVisible(false);
        }
        locationPermissionRequest.launch(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION});

        navigationView.setVisibility(View.GONE);
        setNavi();
    }

    /**
     * Sets up the navigation menu.
     */
    public void setNavi() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.getOut) {
                    md.DeleteShare();
                    Intent intent = new Intent(HomePage.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                if (item.getItemId() == R.id.info) {
                    Intent intent = new Intent(HomePage.this, Info.class);
                    startActivity(intent);
                }
                if (item.getItemId() == R.id.settings) {
                    Intent intent = new Intent(HomePage.this, Admin.class);
                    startActivity(intent);
                }
                if (item.getItemId() == R.id.location) {
                    Intent intent = new Intent(HomePage.this, Map.class);
                    startActivity(intent);
                }
                if (item.getItemId() == R.id.picture) {
                    Intent intent = new Intent(HomePage.this, Camera.class);
                    startActivity(intent);
                }
                return true;
            }
        });
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.btnNavigation).setEnabled(isFinishing());
        return true;
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
    }
}
