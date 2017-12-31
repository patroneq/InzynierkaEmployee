package com.client.inzynierkaemployee;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.client.inzynierkaemployee.Model.EmployeeModel;
import com.client.inzynierkaemployee.Model.Message;
import com.client.inzynierkaemployee.Model.TaskModel;
import com.client.inzynierkaemployee.Utils.Communication;
import com.client.inzynierkaemployee.Utils.Utils;

public class MyTaskActivity extends AppCompatActivity {

    public final static String IS_TASK_BTN_VISIBLE = "myTaskBtnVisibility";

    TextView mTitleText;
    TextView mDepartmentNameText;
    TextView mDescriptionText;
    TextView mCreationDateText;
    Button mSendResponseButton;

    TaskModel taskModel;
    EmployeeModel mEmployeeModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_task);
        mTitleText = (TextView) findViewById(R.id.my_task_title_text);
        mDepartmentNameText = (TextView) findViewById(R.id.my_task_department_name);
        mDescriptionText = (TextView) findViewById(R.id.my_task_description_label);
        mCreationDateText = (TextView) findViewById(R.id.my_task_created_date_label);
        mSendResponseButton = (Button) findViewById(R.id.SendResponseButton);

        taskModel = Utils.getGsonInstance().fromJson(getIntent().getStringExtra("MYTASK"), TaskModel.class);
        mEmployeeModel = Utils.getGsonInstance().fromJson(getIntent().getStringExtra("user_profile"), EmployeeModel.class);
        Boolean requestButtonVisible = getIntent().getBooleanExtra(IS_TASK_BTN_VISIBLE, false);

        mTitleText.setText("Title: " + taskModel.title);
        mDepartmentNameText.setText("Department name: " + taskModel.departmentName);
        mDescriptionText.setText("Description: " + taskModel.description);
        mCreationDateText.setText("Date created: " + taskModel.getFormattedDate());

        if (requestButtonVisible) {
            mSendResponseButton.setVisibility(View.VISIBLE);
            mSendResponseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new SendResponseTask().execute(String.valueOf(taskModel.id),String.valueOf(mEmployeeModel.id));
                }
            });
        }
    }
    private class SendResponseTask extends AsyncTask<String, Void, String>
    {

        @Override
        protected String doInBackground(String... params) {
            return new Communication().Receive("/employee/completetask/" + params[1] + "/" + params[0] + "","","PATCH");
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Toast.makeText(getBaseContext(), "Response sent", Toast.LENGTH_SHORT).show();
            Message message = Utils.getGsonInstance().fromJson(result, Message.class);
            Toast.makeText(getBaseContext(), message.message, Toast.LENGTH_LONG).show();

            finish();
        }

    }

}
