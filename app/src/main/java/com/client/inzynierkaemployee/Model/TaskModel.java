package com.client.inzynierkaemployee.Model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskModel {
    public int id;
    public String title;
    public String departmentName;
    public String description;
    public int active;
    public Date creationDate;
    public String status;
    public int rating;
    public String problem;
    public String commentary;
    public String commentaryProblem;

    //temporary constructor for placeHolder
    public TaskModel(String taskName) {
        this.title = taskName;
    }

    public TaskModel() {}

    public String getFormattedDate() {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        return format.format(this.creationDate);
    }

}
