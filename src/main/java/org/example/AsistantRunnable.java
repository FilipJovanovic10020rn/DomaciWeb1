package org.example;

import org.example.model.GradedBy;
import org.example.model.Student;
import org.example.model.StudentThreadIndex;

import java.util.Random;

public class AsistantRunnable implements Runnable{

    public boolean running = true;
    private StudentThreadIndex studentThreadIndex;

    @Override
    public void run(){
        while (running){ // asistent radi non stop dok ne prodje 5s i onda mu sibnemo interupt
            try {
                StudentThreadIndex sti = Main.blockingQueueAsistant.take();
                studentThreadIndex = sti;

                int index = sti.getIndexInList().intValue();

                Student student = Main.students.get(index);

                double waitTimeD = student.getDefendTime()*1000L;
                long waitTime = (long)waitTimeD;
                Thread.sleep(waitTime);

                Random random = new Random();
                student.setGrade(random.nextInt(6)+5);
                student.setGradedBy(GradedBy.ASISTENT);

                // setujemo u glavnoj listi studentove stvari
                Main.students.set(index,student);


                long finishedTime = System.currentTimeMillis();

                System.out.println(
                        "Thread: " + sti.getStudentThread().getName()
                                + " Arrival: " + Main.students.get(index).getArrivalTime()
                                + " Assistant: " + Thread.currentThread().getName()
                                + " TTC: " + finishedTime
                                + ": " + sti.getCurrentTime()
                                + " Grade: " + student.getGrade());


            } catch (InterruptedException e ) {
//                throw new RuntimeException("Gotove odbrane - asistent");
                System.out.println(
                        "Stopped: " + studentThreadIndex.getStudentThread().getName()
                        + " Arrival: " + studentThreadIndex.getCurrentTime()
                        + " Assistant: " + Thread.currentThread().getName());
                running=false;
            }
        }
    }
}
