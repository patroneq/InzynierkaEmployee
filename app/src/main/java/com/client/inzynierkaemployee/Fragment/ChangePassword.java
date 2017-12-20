package com.client.inzynierkaemployee.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.client.inzynierkaemployee.MainActivity;
import com.client.inzynierkaemployee.R;

/**
 * Created by eztompa on 2017-12-20.
 */

public class ChangePassword extends Fragment {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("List of tasks");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.list_of_tasks, container, false);

        String fullName = MainActivity.employeeModel.getFullName();

        TextView name = (TextView) view.findViewById(R.id.name);
        name.setText(fullName);

        return view;
    }
}
