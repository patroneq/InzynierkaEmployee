package com.client.inzynierkaemployee.Fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.client.inzynierkaemployee.LoginActivity;
import com.client.inzynierkaemployee.MainActivity;
import com.client.inzynierkaemployee.Model.EmployeeModel;
import com.client.inzynierkaemployee.Model.Message;
import com.client.inzynierkaemployee.R;
import com.client.inzynierkaemployee.Utils.Communication;
import com.client.inzynierkaemployee.Utils.Utils;

/**
 * Created by eztompa on 2017-12-20.
 */

public class ChangePassword extends Fragment {

    private ChangePasswordTask mAuthTask = null;
    Message msg;

    String MessagePassword;
    private EditText mPassword;
    private EditText mPasswordRepeat;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("List of tasks");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.change_password, container, false);

        mPassword = (EditText) view.findViewById(R.id.password_one);
        mPasswordRepeat = (EditText) view.findViewById(R.id.password_two);

        Button changePasswordButton = (Button) view.findViewById(R.id.change_password_button);
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptChangePassword();
            }
        });

        return view;
    }

    private void attemptChangePassword() {
        if (mAuthTask != null) {
            return;
        }
        // Reset errors.
        mPassword.setError(null);
        mPasswordRepeat.setError(null);

        // Store values at the time of the login attempt.
        String password = mPassword.getText().toString();
        String passwordRepeat = mPasswordRepeat.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password) && !isPasswordValid(passwordRepeat)) {
            mPassword.setError(getString(R.string.error_invalid_password));
            mPasswordRepeat.setError(getString(R.string.error_invalid_password));
            focusView = mPassword;
            cancel = true;
        }

        if (!password.equals(passwordRepeat)) {
            mPasswordRepeat.setError(getString(R.string.error_invalid_password_repeat));
            focusView = mPasswordRepeat;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            mAuthTask = new ChangePasswordTask(password);
            mAuthTask.execute((String) null);
        }
    }


    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    private void logOff()
    {
        Intent LogingIntent = new Intent(getActivity(), LoginActivity.class);
        startActivity(LogingIntent);
    }

    public class ChangePasswordTask extends AsyncTask<String, Void, String> {

        private final String mPassword;
        Communication communication = new Communication();

        ChangePasswordTask(String password) {
            mPassword = password;
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO: attempt authentication against a network service.

            EmployeeModel employee = new EmployeeModel();
            employee.password = mPassword;
            String userJson = Utils.getGsonInstance().toJson(employee);
            MessagePassword = communication.Receive("/employee/changepassword/" + MainActivity.employeeModel.getId(), userJson, "PATCH");

            if (MessagePassword.isEmpty()) {
                return null;
            }

            return MessagePassword;
        }

        @Override
        protected void onPostExecute(final String result) {
            super.onPostExecute(result);
            mAuthTask = null;

            Toast.makeText(getContext(), "Response sent", Toast.LENGTH_SHORT).show();
            msg = Utils.getGsonInstance().fromJson(result, Message.class);

            if (msg.getStatus() == 200) {
                Toast.makeText(getContext(), msg.getMessage(), Toast.LENGTH_LONG).show();
                logOff();
            }
        }
    }
}