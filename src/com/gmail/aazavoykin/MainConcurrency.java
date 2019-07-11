package com.gmail.aazavoykin;

import java.util.concurrent.*;

public class MainConcurrency {
    private static int counter;
    private static final int THREAD_NUMBER = 10000;

    public static void main(String[] args) {
        final MainConcurrency mainConcurrency = new MainConcurrency();
        CountDownLatch latch = new CountDownLatch(THREAD_NUMBER);
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
//        CompletionService completionService = new ExecutorCompletionService(executorService);

        for (int i = 0; i < THREAD_NUMBER; i++) {
            Future<Integer> future = executorService.submit(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                }
                latch.countDown();
                return 5;
            });
            System.out.println(future.isDone());
            try {
                System.out.println(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        try {
            latch.await(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
        System.out.println(counter);


        // homework
        /*Person person_1 = new Person("Bob");
        Person person_2 = new Person("Joe");

        Thread threadOne = new Thread(() -> {
            person_1.sayHello(person_2);
        });

        Thread threadTwo = new Thread(() -> {
            person_2.sayHello(person_1);
        });

        threadOne.start();
        threadTwo.start();*/

    }

    private synchronized void inc() {
        counter++;
    }

    private static class Person {
        private final String name;

        public Person(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        synchronized void sayHello(Person person) {
            //System.out.println(name + " says hello to " + person.getName());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            person.sayHi(this);
        }

        synchronized void sayHi(Person person) {
            //System.out.println(name + " says hi to " + person.getName());
        }
    }


}
