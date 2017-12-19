package com.client.inzynierkaemployee.Model;

public class EmployeeModel {

    public int id;
    public String email;
    public String password;
    public String name;
    public String lastName;
    public String phoneNumber;
    public String active;
    public double averageMark;
    public String description;

    public String getFullName() {
        return new StringBuilder().append(name).append(" ").append(lastName).toString();
    }
}
