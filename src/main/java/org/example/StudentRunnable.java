package org.example;

import org.example.model.StudentThreadIndex;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;

public class StudentRunnable implements Runnable{

    private int index;
    private ExecutorService professorThreadPool;
    private CyclicBarrier professorBarrier;
    private long currentTime;
    public StudentRunnable(int index, CyclicBarrier professorBarrier, ExecutorService professorThreadPool, long currentTime) {
        this.index = index;
        this.professorThreadPool = professorThreadPool;
        this.professorBarrier = professorBarrier;
        this.currentTime = currentTime;
    }

    @Override
    public void run() {
        // vec sam odradio cekanje u timeru ali moglao je i ovde thread sleep sto je i logicnije ako je studnet thread ali nisam znao da treba da bude thread
        Random random = new Random();

        if(random.nextDouble() < 0.5){
            Main.blockingQueueAsistant.add(new StudentThreadIndex(index++,Thread.currentThread(),currentTime));
        }
        else{
            professorThreadPool.submit(new ProfessorRunnable(professorBarrier,index++,Thread.currentThread(),currentTime));
        }

    }
}
