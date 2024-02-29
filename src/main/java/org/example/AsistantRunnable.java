package org.example;

import org.example.model.GradedBy;
import org.example.model.Student;

import java.util.Random;

public class AsistantRunnable implements Runnable{

    public boolean running = true;

    @Override
    public void run(){
        while (running){ // asistent radi non stop dok ne prodje 5s i onda mu sibnemo interupt
            try {
                Integer indexInteger = Main.blockingQueueAsistant.take();

                int index = indexInteger.intValue();

                Student student = Main.students.get(index);

                double waitTimeD = student.getDefendTime()*1000L;
                long waitTime = (long)waitTimeD;
                Thread.sleep(waitTime);

                Random random = new Random();
                student.setGrade(random.nextInt(6)+5);
                student.setGradedBy(GradedBy.ASISTENT);

                // setujemo u glavnoj listi studentove stvari
                Main.students.set(index,student);

            } catch (InterruptedException e ) {
//                throw new RuntimeException("Gotove odbrane - asistent");
                running=false;
            }
        }
    }
}
