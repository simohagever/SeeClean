package com.example.lastproject.UI.Info;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lastproject.R;
import com.example.lastproject.repstory.CurrentUser;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView pass,firstName,lastName,address;
    private TextView email;
    private ModuleInfo moduleInfo;
    public InfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InfoFragment newInstance(String param1, String param2) {
        InfoFragment fragment = new InfoFragment();
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
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        pass = view.findViewById(R.id.bpass);
        email = view.findViewById(R.id.bemail);
        firstName = view.findViewById(R.id.bFirst);
        lastName = view.findViewById(R.id.bLast);
        address = view.findViewById(R.id.bAddress);

        moduleInfo = new ModuleInfo(getContext());


        firstName.setText("first name: " + moduleInfo.FindUserByEmail(CurrentUser.getEmail()).getString(1));
        lastName.setText("last name: " +moduleInfo.FindUserByEmail(CurrentUser.getEmail()).getString(2));
        pass.setText("password: " +moduleInfo.FindUserByEmail(CurrentUser.getEmail()).getString(3));
        email.setText("email: " +CurrentUser.getEmail());
        address.setText("address: " +moduleInfo.FindUserByEmail(CurrentUser.getEmail()).getString(5));




        // }
        return view;
    }
}