package com.example.lastproject.UI.Register;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.lastproject.R;
import com.example.lastproject.UI.Main.MainActivity;
import com.example.lastproject.UI.homePage.HomePage;
import com.example.lastproject.UI.homePage.moduleHomePage;
import com.example.lastproject.repstory.CurrentUser;
import com.example.lastproject.repstory.Repository;
import com.example.lastproject.repstory.User;

/**
 * The Register class handles user registration.
 */
public class Register extends AppCompatActivity implements View.OnClickListener {

    private Button btnSendInformation, btnHomePage;
    private TextView btnBackToLogin;
    private SharedPreferences sharedPreferences;
    private EditText Epass, Eemail, Edate;
    private modleRegister mR;
    private CheckBox checkBox, RegisterRemember;

    private EditText EFname1, ELname, EPassword1, EIPassword, EEmail1, EAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        sharedPreferences = getSharedPreferences("main", Context.MODE_PRIVATE);

        btnHomePage = findViewById(R.id.btnBackToHomepage);
        btnHomePage.setOnClickListener(this);
        btnBackToLogin = findViewById(R.id.btnGoBackToLogin);
        btnBackToLogin.setOnClickListener(this);
        btnSendInformation = findViewById(R.id.btnSendRegister);
        btnSendInformation.setOnClickListener(this);
        EFname1 = findViewById(R.id.firstname);
        ELname = findViewById(R.id.lastname);
        EPassword1 = findViewById(R.id.password);
        RegisterRemember = findViewById(R.id.remember1);
        EIPassword = findViewById(R.id.Epassword);
        EEmail1 = findViewById(R.id.email);
        EAddress = findViewById(R.id.address);


        mR = new modleRegister(this);

        if (!sharedPreferences.getString("RememberEmail", "-1").equals("-1")) {
            CurrentUser.initilizeUser(sharedPreferences.getString("RememberEmail", ""));
            startActivity(new Intent(Register.this, HomePage.class));
        }
    }

    public void showCustomDialog(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.login, null);
        checkBox = view.findViewById(R.id.remember2);
        builder.setView(view);

        builder.setNeutralButton("login", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email = Eemail.getText().toString();
                String pass = Epass.getText().toString();

                if (!(mR.isExistLogin(email, pass))) {
                    Toast.makeText(Register.this, "the email or password doesn't found", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(Register.this, HomePage.class);
                startActivity(intent);

                if (checkBox.isChecked()) {
                    mR.SaveUser(Epass, Eemail);
                }
            }
        });

        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        Eemail = view.findViewById(R.id.emailEdit);
        Epass = view.findViewById(R.id.passEdit);

        builder.setCancelable(false);
        builder.show();
    }

    @Override
    public void onClick(View v) {
        if (v == btnSendInformation) {
            // Validation checks for user input fields
            User user = new User(EFname1.getText().toString(),ELname.getText().toString(),EPassword1.getText().toString(),EEmail1.getText().toString(), EAddress.getText().toString());
            if(mR.CheckUps(user ,EIPassword))
            {
                if (RegisterRemember.isChecked()) {
                    mR.SaveUser(EPassword1, EEmail1);
                }
                CurrentUser.initilizeUser(EEmail1.getText().toString());
                startActivity(new Intent(Register.this, HomePage.class));
            }
        }

        if (v == btnBackToLogin) {
            showCustomDialog(v);
        }



        if (v == btnHomePage) {
            Intent intent = new Intent(Register.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
