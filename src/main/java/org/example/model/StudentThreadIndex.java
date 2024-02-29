package org.example.model;

public class StudentThreadIndex {

    private Integer indexInList;
    private Thread studentThread;

    private long currentTime;

    public StudentThreadIndex(Integer indexInList, Thread studentThread, long currentTime) {
        this.indexInList = indexInList;
        this.studentThread = studentThread;
        this.currentTime = currentTime;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public Integer getIndexInList() {
        return indexInList;
    }

    public Thread getStudentThread() {
        return studentThread;
    }
}
