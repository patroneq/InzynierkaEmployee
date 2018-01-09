package com.client.inzynierkaemployee;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    TextView mCommentaryText;
    EditText mCommentary;
    Button mSendResponseButton, mSetProblemButton;

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
        mCommentaryText = (TextView) findViewById(R.id.my_task_commentary_text);
        mCommentary = (EditText) findViewById(R.id.my_task_commentary);

        mSendResponseButton = (Button) findViewById(R.id.my_SendResponseButton);
        mSetProblemButton = (Button) findViewById(R.id.my_set_problem_button);

        taskModel = Utils.getGsonInstance().fromJson(getIntent().getStringExtra("MYTASK"), TaskModel.class);
        mEmployeeModel = Utils.getGsonInstance().fromJson(getIntent().getStringExtra("user_profile"), EmployeeModel.class);
        Boolean requestButtonVisible = getIntent().getBooleanExtra(IS_TASK_BTN_VISIBLE, false);

        mTitleText.setText("Title: " + taskModel.title);
        mDepartmentNameText.setText("Department name: " + taskModel.departmentName);
        mDescriptionText.setText("Description: " + taskModel.description);
        mCreationDateText.setText("Date created: " + taskModel.getFormattedDate());
        if (taskModel.problem.equals("YES")) {
            mCommentaryText.setText("Problem: "+taskModel.commentaryProblem);
        } else {
            mCommentaryText.setText("Problem: No reported problems");
        }

        if (requestButtonVisible) {
            mSendResponseButton.setVisibility(View.VISIBLE);
            mSendResponseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String commentary = mCommentary.getText().toString();
                    new SendResponseTask().execute(String.valueOf(taskModel.id),String.valueOf(mEmployeeModel.id), commentary);
                }
            });
        }

        mSetProblemButton.setVisibility(View.VISIBLE);
        mSetProblemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commentary = mCommentary.getText().toString();
                new ReportProblemTask().execute(String.valueOf(taskModel.id),String.valueOf(mEmployeeModel.id), commentary);
            }
        });
    }

    private class SendResponseTask extends AsyncTask<String, Void, String>
    {

        @Override
        protected String doInBackground(String... params) {
            TaskModel task = new TaskModel();
            task.commentary = params[2];
            String userJson = Utils.getGsonInstance().toJson(task);
            return new Communication().Receive("/employee/completetask/" + params[1] + "/" + params[0] + "",userJson,"PATCH");
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

    private class ReportProblemTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            TaskModel task = new TaskModel();
            task.commentaryProblem = params[2];
            System.out.println("PARAMS: "+params[2]);
            String userJson = Utils.getGsonInstance().toJson(task);
            return new Communication().Receive("/employee/problem/" + params[1] + "/" + params[0] + "",userJson,"PATCH");
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
