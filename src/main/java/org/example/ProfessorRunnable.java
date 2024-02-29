package org.example;

import org.example.model.GradedBy;
import org.example.model.Student;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class ProfessorRunnable implements Runnable{

    private CyclicBarrier cyclicBarrier;
    private Integer indexInteger;
    private Thread studentThread;
    private long startTime;

    public ProfessorRunnable(CyclicBarrier cyclicBarrier, Integer indexInteger, Thread studentThread, long currentTime) {

        this.cyclicBarrier = cyclicBarrier;
        this.indexInteger = indexInteger;
        this.studentThread = studentThread;
        this.startTime = currentTime;
    }

    @Override
    public void run() {
        try {

            this.cyclicBarrier.await(); // ceka po 2
            int index = indexInteger.intValue();

            Student student = Main.students.get(index);

            double waitTimeD = student.getDefendTime()*1000L;
            long waitTime = (long)waitTimeD;
            Thread.sleep(waitTime);

            Random random = new Random();
            student.setGrade(random.nextInt(6)+5);
            student.setGradedBy(GradedBy.PROFESSOR);

            // setujemo u glavnoj listi studentove stvari
            this.cyclicBarrier.await(); // ceka drugog studenta da zavrsi pa im onda obojici use ocenu
            Main.students.set(index,student);

            long finishedTime = System.currentTimeMillis();

            System.out.println(
                    "Thread: " + studentThread.getName()
                    + " Arrival: " + Main.students.get(index).getArrivalTime()
                    + " Prof: " + Thread.currentThread().getName()
                    + " TTC: " + finishedTime
                    + ": " + startTime
                    + " Grade: " + student.getGrade());


        } catch (InterruptedException | BrokenBarrierException e) {
            System.out.println(
                    "Stopped: " + studentThread.getName()
                    + " Arrival: " + startTime
                    + " Prof: " + Thread.currentThread().getName());
//            throw new RuntimeException("Gotove odbrane - professor");
        }
    }
}
