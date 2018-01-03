package com.client.inzynierkaemployee.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.client.inzynierkaemployee.MainActivity;
import com.client.inzynierkaemployee.R;

/**
 * Created by eztompa on 2017-12-20.
 */

public class MyProfile extends Fragment {

    TextView mEmailText;
    TextView mFullNameText;
    TextView mPhoneText;
    RatingBar mRatingBar;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("My Profile");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.my_profile, container, false);

        mEmailText = (TextView) view.findViewById(R.id.my_email);
        mFullNameText = (TextView) view.findViewById(R.id.my_fullname);
        mPhoneText = (TextView) view.findViewById(R.id.my_phone_number);
        mRatingBar = (RatingBar) view.findViewById(R.id.my_rating);

        mEmailText.setText(MainActivity.employeeModel.email);
        mFullNameText.setText(MainActivity.employeeModel.getFullName());
        mPhoneText.setText("Phone number: " + MainActivity.employeeModel.phoneNumber);
        mRatingBar.setRating((float) MainActivity.employeeModel.averageRating);

        return view;
    }
}