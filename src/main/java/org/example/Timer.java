package org.example;

import org.example.model.Student;

import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class Timer {
    private ScheduledExecutorService scheduler;
    private List<Student> students;
    private int studentNumber;
    private int index =0;
    private ExecutorService professorThreadPool;
    private CyclicBarrier professorBarrier;

    public Timer(List<Student> students, int studentNumber, ExecutorService professorThreadPool,CyclicBarrier professorBarrier) {
        this.students = students;
        this.scheduler = Executors.newScheduledThreadPool(1);
        this.studentNumber = studentNumber;
        this.professorThreadPool = professorThreadPool;
        this.professorBarrier = professorBarrier;
    }

    public void startClock(long intervalMilis) {
        long startTime = System.currentTimeMillis();
        Random random = new Random();

        scheduler.scheduleAtFixedRate(() -> {
            long currentTime = System.currentTimeMillis();
            long timePassed = currentTime-startTime;

            if(index<studentNumber){
                long arrivalTime = (long) (students.get(index).getArrivalTime() * 1000);

                if(timePassed>=arrivalTime){
                    if(random.nextDouble() < 0.5){
                        Main.blockingQueueAsistant.add(index++);
                    }
                    else{
                        professorThreadPool.submit(new ProfessorRunnable(professorBarrier,index++));
                    }
                }
            }
            else{
                scheduler.shutdown();
            }

        }, 0, intervalMilis, TimeUnit.MILLISECONDS);
    }

    public void stopClock() {
        scheduler.shutdown();
    }

    public void startDefence() throws InterruptedException {
        this.startClock(10L);
        Thread.sleep(5000L);
        this.stopClock();
    }

}