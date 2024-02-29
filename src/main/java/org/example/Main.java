package org.example;

import org.example.model.Student;

import java.util.*;
import java.util.concurrent.*;

public class Main {

    public static volatile CyclicBarrier professorBarrier = new CyclicBarrier(2);
    public static volatile BlockingQueue<Integer> blockingQueueAsistant = new LinkedBlockingQueue<>();
    public static volatile List<Student> students = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {

        // input values and generate students
        System.out.println("Input student number: ");
        Scanner scanner = new Scanner(System.in);
        int studentNumber = scanner.nextInt();

        if(studentNumber<=0){
            studentNumber = 20;
        }

        Random random = new Random();

        for(int i=0;i<studentNumber; i++){
            double arrivalTime = 0.0;
            while (arrivalTime == 0.0) { // So it cannot be 0
                arrivalTime = random.nextDouble();
            }

            students.add(new Student("Student"+i, arrivalTime, random.nextDouble() * 0.5 + 0.5));
        }

        // sort students with arrival time
        Collections.sort(students);


        // create asistant and professor threads
        Thread asistantThread = new Thread(new AsistantRunnable());
        ExecutorService professorThreadPool = Executors.newFixedThreadPool(2); // profesor ima 2 threda
        asistantThread.start();

        // start the clock
        Timer timer = new Timer(students,studentNumber, professorThreadPool, professorBarrier);
        timer.startDefence();

        // stop the professor and assistant threads - time passed

        asistantThread.interrupt();
        professorThreadPool.shutdownNow();


        // calculate the average grade of all passing students
        int gradedStudents = 0;
        int sum=0;
        for(Student s : students){
//            System.out.println(s.toString());
            if(s.getGradedBy()!=null){
                sum+=s.getGrade();
                gradedStudents++;
            }
        }
        double average = (sum*1.0D)/(gradedStudents*1.0D);
        System.out.println("Average grade: "+ average);


    }
}