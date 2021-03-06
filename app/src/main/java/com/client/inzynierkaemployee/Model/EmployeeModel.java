package com.client.inzynierkaemployee.Model;

public class EmployeeModel {

    public int id;
    public String email;
    public String password;
    public String name;
    public String lastName;
    public String phoneNumber;
    public String active;
    public double averageRating;
    public String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public double getAverageMark() {
        return averageRating;
    }

    public void setAverageMark(double averageRating) {
        this.averageRating = averageRating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFullName() {
        return new StringBuilder().append(name).append(" ").append(lastName).toString();
    }
}
