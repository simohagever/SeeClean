package com.example.lastproject.UI.Camera;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lastproject.R;
import com.example.lastproject.UI.Admin.Admin;
import com.example.lastproject.UI.Info.Info;
import com.example.lastproject.UI.Main.MainActivity;
import com.example.lastproject.UI.Map.Map;
import com.example.lastproject.UI.homePage.HomePage;
import com.example.lastproject.repstory.CurrentUser;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Camera activity class that allows users to take pictures, add descriptions, and submit them.
 */
public class Camera extends AppCompatActivity implements View.OnClickListener{
    ImageView imageView;
    Bitmap picture;
    TextView textT, textD;
    EditText eDes;
    private NavigationView navigationView;
    boolean menuVisible = false;
    Button Bsend, btnCancle, navi;
    ModuleCamera moduleCamera;
    ActivityResultLauncher<Intent> ResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult o) {
            if (o.getResultCode() == RESULT_OK) {
                picture = (Bitmap) o.getData().getExtras().get("data");
                imageView.setImageBitmap(picture);
                imageView.setTag("pic");
                int height = Integer.parseInt(String.valueOf(Math.round(getBaseContext().getResources().getDisplayMetrics().density * 300)));
                int width = Integer.parseInt(String.valueOf(Math.round(getBaseContext().getResources().getDisplayMetrics().density * 200)));
                String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Calendar.getInstance().getTime());
                textD.setText("date: " + currentDate);

                String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Calendar.getInstance().getTime());
                textT.setText("time taking the picture: " + currentTime);

            }
        }
    });

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_camera);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        navi = findViewById(R.id.navigation);
        navi.setOnClickListener(this);

        navigationView = findViewById(R.id.btnNavigation);
        navigationView.setVisibility(View.GONE);
        setNavi();

        ActivityCompat.requestPermissions(Camera.this,new String[]{
                Manifest.permission.CAMERA},100);

        if (CurrentUser.getEmail().equals("rotemkorkus11@gmail.com")) {
            navigationView.getMenu().getItem(4).setVisible(true);
        } else {
            navigationView.getMenu().getItem(4).setVisible(false);
        }
        imageView = findViewById(R.id.Iview);
        imageView.setTag("noPic");
        Bsend = findViewById(R.id.btnSend);
        eDes = findViewById(R.id.description);
        Bsend.setOnClickListener(this);
        imageView.setClickable(true);
        imageView.setOnClickListener(this);
        btnCancle = findViewById(R.id.btnCancel);
        btnCancle.setOnClickListener(this);
        textT = findViewById(R.id.time);
        textD = findViewById(R.id.date);
        moduleCamera = new ModuleCamera(this);
    }

    /**
     * Sets up the navigation menu.
     */
    public void setNavi(){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.getOut) {
                    moduleCamera.forgetUser();
                    Intent intent = new Intent(Camera.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                if (item.getItemId() == R.id.info) {
                    Intent intent = new Intent(Camera.this, Info.class);
                    startActivity(intent);
                }
                if (item.getItemId() == R.id.settings) {
                    Intent intent = new Intent(Camera.this, Admin.class);
                    startActivity(intent);
                }
                if (item.getItemId() == R.id.location) {
                    Intent intent = new Intent(Camera.this, Map.class);
                    startActivity(intent);
                }
                if (item.getItemId() == R.id.homePage) {
                    Intent intent = new Intent(Camera.this, HomePage.class);
                    startActivity(intent);
                }
                return true;
            }
        });
    }

    /**
     * Prepares the options menu.
     */
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.btnNavigation).setEnabled(isFinishing());
        return true;
    }

    /**
     * Handles click events for various UI elements.
     */
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

        if (v == btnCancle) {
            Intent intent = new Intent(Camera.this, HomePage.class);
            startActivity(intent);
        }
        if (v == imageView) {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            ResultLauncher.launch(cameraIntent);
        }
        if (v == Bsend) {
            if (imageView.getTag().equals("noPic")) {
                Toast.makeText(this, "you have to take a picture before you submit", Toast.LENGTH_SHORT).show();
                return;
            }
            if (eDes.getText().toString().equals("")) {
                eDes.setError("you have to describe the picture");
                return;
            }

            Intent intent = new Intent(Camera.this, Map.class);
            intent.putExtra("Description", eDes.getText().toString().trim());
            intent.putExtra("Time", textT.getText().toString().trim());
            intent.putExtra("Date", textD.getText().toString().trim());
            intent.putExtra("Picture", picture);
            intent.putExtra("NewReport", true);
            startActivity(intent);
        }
    }
}
