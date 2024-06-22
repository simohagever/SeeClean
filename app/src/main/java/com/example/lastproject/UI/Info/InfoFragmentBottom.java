package com.example.lastproject.UI.Info;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lastproject.R;
import com.example.lastproject.UI.Register.Register;
import com.example.lastproject.UI.homePage.HomePage;
import com.example.lastproject.repstory.CurrentUser;
import com.example.lastproject.repstory.User;

import org.checkerframework.checker.units.qual.A;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InfoFragmentBottom#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoFragmentBottom extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button returnToHome, btnSend;
    ModuleInfo moduleInfo;
    private EditText Fname,Lname,Password,IPassword,Email,Address;



    public InfoFragmentBottom() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InfoFragmentBottom.
     */
    // TODO: Rename and change types and number of parameters
    public static InfoFragmentBottom newInstance(String param1, String param2) {
        InfoFragmentBottom fragment = new InfoFragmentBottom();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_info_bottom, container, false);
        moduleInfo = new ModuleInfo(getContext());
        returnToHome = view.findViewById(R.id.btnBackToHomepage);
        Fname = view.findViewById(R.id.firstnameupdate);
        Fname.setText(moduleInfo.FindUserByEmail(CurrentUser.getEmail()).getString(1));
        Lname = view.findViewById(R.id.lastnameupdate);
        Lname.setText(moduleInfo.FindUserByEmail(CurrentUser.getEmail()).getString(2));
        Password = view.findViewById(R.id.passwordupdate);
        Password.setText(moduleInfo.FindUserByEmail(CurrentUser.getEmail()).getString(3));
        IPassword = view.findViewById(R.id.Ipasswordupdate);
        IPassword.setText(moduleInfo.FindUserByEmail(CurrentUser.getEmail()).getString(3));
        Email = view.findViewById(R.id.emailupdate);
        Email.setText(moduleInfo.FindUserByEmail(CurrentUser.getEmail()).getString(4));
        Address = view.findViewById(R.id.addressupdate);
        Address.setText(moduleInfo.FindUserByEmail(CurrentUser.getEmail()).getString(5));
        btnSend = view.findViewById(R.id.btnUpdate);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Fname.getText().toString().equals("")){
                    //Toast.makeText(this, "fill first name", Toast.LENGTH_SHORT).show();
                    Fname.setError("fill first name");
                    return;
                }

                if(Lname.getText().toString().equals("")){
                    Lname.setError("fill last name");
                    return;
                }

                if(Password.getText().toString().equals("")){
                    Password.setError("fill password");
                    return;
                }


                if(IPassword.getText().toString().equals("")){
                    IPassword.setError("fill password confirmation");
                    return;
                }


                if(Email.getText().toString().equals("")){
                    Email.setError("fill email confirmation");
                    return;
                }

                for (int i = 0; i < Email.length(); i++) {
                    if(!(Email.getText().toString().indexOf(".com") == Email.getText().toString().length()-4 || Email.getText().toString().indexOf(".co.") == Email.getText().toString().length()-6))
                    {
                        Email.setError("email format is invalid(.com)");
                        return;
                    }
                    if(Email.getText().toString().indexOf("@") ==-1  ){
                        Email.setError("email format is invalid(-@)");
                        return;

                    }
                    if(!(Email.getText().toString().indexOf("@") == Email.getText().toString().lastIndexOf("@") )){
                        Email.setError("email format is invalid(2@)");
                        return;

                    }
                    if(!(Email.getText().toString().indexOf(".") -  Email.getText().toString().indexOf("@") >=3 )){
                        Email.setError("email format is invalid(@.)");
                        return;

                    }



                }
                if(moduleInfo.CheckUps(Email.getText().toString(),Password.getText().toString(),Fname.getText().toString()))
                {
                    Email.setError("There is someone with this email in the system already");
                    return;
                }
                if(Address.getText().toString().equals("")){
                    Address.setError("fill address");
                    return;
                }
                if(!Password.getText().toString().equals(IPassword.getText().toString()))
                {
                    Password.setError("passwords do not match");
                    return;
                }

                moduleInfo.UpdateData(String.valueOf(moduleInfo.getUserRowNumberByEmail(CurrentUser.getEmail())),Fname.getText().toString(),Lname.getText().toString(),Password.getText().toString(),Email.getText().toString(),Address.getText().toString());
                CurrentUser.setEmail(Email.getText().toString());



                startActivity(new Intent(getContext(), HomePage.class));










            }
        });


        returnToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(v==btnSend){
                }

                Intent intent = new Intent(getContext(), HomePage.class);
                startActivity(intent);

            }
        });
        return view;
    }
}
