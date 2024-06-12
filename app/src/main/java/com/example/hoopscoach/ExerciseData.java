package com.example.hoopscoach;

public class ExerciseData {

    String title;
    double number;

    public ExerciseData(String title, double number) {
        this.title = title;
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
