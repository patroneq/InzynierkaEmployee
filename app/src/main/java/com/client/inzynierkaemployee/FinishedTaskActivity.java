package com.client.inzynierkaemployee;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RatingBar;
import android.widget.TextView;

import com.client.inzynierkaemployee.Model.EmployeeModel;
import com.client.inzynierkaemployee.Model.TaskModel;
import com.client.inzynierkaemployee.Utils.Utils;

public class FinishedTaskActivity extends AppCompatActivity {

    TextView mTitleText;
    TextView mDepartmentNameText;
    TextView mDescriptionText;
    TextView mCreationDateText;
    RatingBar mRatingBar;

    TextView mCommentaryProblem;
    TextView mCommentary;

    TaskModel taskModel;
    EmployeeModel mEmployeeModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished_task);
        mTitleText = (TextView) findViewById(R.id.finished_task_title_text);
        mDepartmentNameText = (TextView) findViewById(R.id.finished_task_department_name);
        mDescriptionText = (TextView) findViewById(R.id.finished_task_description_label);
        mCreationDateText = (TextView) findViewById(R.id.finished_task_created_date_label);
        mRatingBar = (RatingBar) findViewById(R.id.finished_rating);

        mCommentaryProblem = (TextView) findViewById(R.id.finished_task_commentary_problem);
        mCommentary = (TextView) findViewById(R.id.finished_task_commentary);

        taskModel = Utils.getGsonInstance().fromJson(getIntent().getStringExtra("FINISHEDTASK"), TaskModel.class);
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
            mCommentary.setText("Commentary: " + taskModel.commentary + String.valueOf(taskModel.rating));
        } else {
            mCommentary.setText("Commentary: No comments" + String.valueOf(taskModel.rating));
        }

        mRatingBar.setRating(taskModel.rating);
    }
}