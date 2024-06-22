package com.example.lastproject.UI.Info;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.lastproject.R;
import com.google.android.material.navigation.NavigationView;

/**
 * Info activity displays user information.
 */
public class Info extends AppCompatActivity implements View.OnClickListener {
    private NavigationView navigationView;
    private TextView pass;
    private TextView email;
    private ModuleInfo moduleInfo;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        fragmentManager = getSupportFragmentManager();

        // Initialize moduleInfo
        moduleInfo = new ModuleInfo(this);
    }

    @Override
    public void onClick(View v) {
        // onClick implementation goes here
    }
}
