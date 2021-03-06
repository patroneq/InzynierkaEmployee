package com.client.inzynierkaemployee;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.client.inzynierkaemployee.Model.EmployeeModel;
import com.client.inzynierkaemployee.Model.TaskModel;
import com.client.inzynierkaemployee.Utils.Utils;

public class WaitingTaskActivity extends AppCompatActivity {

    TextView mTitleText;
    TextView mDepartmentNameText;
    TextView mDescriptionText;
    TextView mCreationDateText;

    TextView mCommentaryProblem;
    TextView mCommentary;

    TaskModel taskModel;
    EmployeeModel mEmployeeModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_task);
        mTitleText = (TextView) findViewById(R.id.waiting_task_title_text);
        mDepartmentNameText = (TextView) findViewById(R.id.waiting_task_department_name);
        mDescriptionText = (TextView) findViewById(R.id.waiting_task_description_label);
        mCreationDateText = (TextView) findViewById(R.id.waiting_task_created_date_label);

        mCommentaryProblem = (TextView) findViewById(R.id.waiting_task_commentary_problem);
        mCommentary = (TextView) findViewById(R.id.waiting_task_commentary);

        taskModel = Utils.getGsonInstance().fromJson(getIntent().getStringExtra("WAITINGTASK"), TaskModel.class);
        mEmployeeModel = Utils.getGsonInstance().fromJson(getIntent().getStringExtra("user_profile"), EmployeeModel.class);

        mTitleText.setText("Title: " + taskModel.title);
        mDepartmentNameText.setText("Department name: " + taskModel.departmentName);
        mDescriptionText.setText("Description: " + taskModel.description);
        mCreationDateText.setText("Date created: " + taskModel.getFormattedDate());

        if(taskModel.problem.equals("YES")) {
            mCommentaryProblem.setText("Problem: " + taskModel.commentaryProblem);
        } else {
            mCommentaryProblem.setText("Problem: No reported problems");
        }
        if (taskModel.commentary != null) {
            mCommentary.setText("Commentary: " + taskModel.commentary);
        } else {
            mCommentary.setText("Commentary: No comments");
        }
    }
}