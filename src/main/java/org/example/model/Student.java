package org.example.model;

public class Student implements Comparable<Student>{

    private String name;
    private double arrivalTime;
    private double defendTime;
    private int grade; //5-10
    private Object lock;
    private GradedBy gradedBy;

    public Student(String name, double arrivalTime, double defendTime) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.defendTime = defendTime;
        this.grade = 0;
    }

    public String getName() {
        return name;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public double getDefendTime() {
        return defendTime;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public Object getLock() {
        return lock;
    }

    public void setGradedBy(GradedBy gradedBy) {
        this.gradedBy = gradedBy;
    }

    public GradedBy getGradedBy() {
        return gradedBy;
    }

    @Override
    public int compareTo(Student o) {
        return Double.compare(this.arrivalTime, o.arrivalTime);
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", arrivalTime=" + arrivalTime +
                ", defendTime=" + defendTime +
                ", grade=" + grade +
                ", gradedBy=" + gradedBy +
                '}';
    }
}
