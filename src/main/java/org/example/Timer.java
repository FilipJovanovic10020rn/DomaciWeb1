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
    private ExecutorService studentThreadPool;

    private Thread asistantThread;

    public Timer(List<Student> students, ExecutorService studentThreadPool, int studentNumber,
                 ExecutorService professorThreadPool, CyclicBarrier professorBarrier, Thread asistantThread) {
        this.students = students;
        this.scheduler = Executors.newScheduledThreadPool(1);
        this.studentNumber = studentNumber;
        this.professorThreadPool = professorThreadPool;
        this.professorBarrier = professorBarrier;
        this.studentThreadPool = studentThreadPool;
        this.asistantThread = asistantThread;
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
                    studentThreadPool.submit(new StudentRunnable(index++,professorBarrier,professorThreadPool,currentTime));
//                    if(random.nextDouble() < 0.5){
//                        Main.blockingQueueAsistant.add(index++);
//                    }
//                    else{
//                        professorThreadPool.submit(new ProfessorRunnable(professorBarrier,index++));
//                    }
                }
            }
            else{
                scheduler.shutdown();
            }

        }, 0, intervalMilis, TimeUnit.MILLISECONDS);
    }

    public void stopClock() {
        scheduler.shutdown();
        studentThreadPool.shutdownNow();
        asistantThread.interrupt();
        professorThreadPool.shutdownNow();
    }

    public void startDefence() throws InterruptedException {
        this.startClock(10L);
        Thread.sleep(5000L);
        this.stopClock();
        Thread.sleep(500L); // cekam jos pola sekudne zbog redosleda ispisa
    }

}