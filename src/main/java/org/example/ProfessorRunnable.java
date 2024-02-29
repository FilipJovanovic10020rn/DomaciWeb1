package org.example;

import org.example.model.GradedBy;
import org.example.model.Student;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class ProfessorRunnable implements Runnable{

    private CyclicBarrier cyclicBarrier;
    private Integer indexInteger;

    public ProfessorRunnable(CyclicBarrier cyclicBarrier, Integer indexInteger) {

        this.cyclicBarrier = cyclicBarrier;
        this.indexInteger = indexInteger;
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


        } catch (InterruptedException | BrokenBarrierException e) {
//            throw new RuntimeException("Gotove odbrane - professor");
        }
    }
}
