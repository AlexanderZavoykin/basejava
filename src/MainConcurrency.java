public class MainConcurrency {
    private static int counter;
    private static final int THREAD_NUMBER = 10000;

    public static void main(String[] args) {
/*        System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState());

        // first thread
        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState());
                //throw new IllegalStateException();
            }
        };
        thread0.start();

        // second thread
        new Thread(() -> System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState())).start();
        System.out.println(thread0.getState());


        // 10000 threads more
        final MainConcurrency mainConcurrency = new MainConcurrency();
        List<Thread> threadList = new ArrayList<>(THREAD_NUMBER);
        for (int i = 0; i < THREAD_NUMBER; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 100; j++) {
                        mainConcurrency.inc();
                    }
                }
            });
            thread.start();
            threadList.add(thread);
        }

        threadList.forEach((thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));

        System.out.println(counter);
*/

        Person person_1 = new Person("Bob");
        Person person_2 = new Person("Joe");

        Thread threadOne = new Thread(() -> {
            person_1.sayHello(person_2);
        });

        Thread threadTwo = new Thread(() -> {
            person_2.sayHello(person_1);
        });

        threadOne.start();
        threadTwo.start();

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
            System.out.println(name + " says hello to " + person.getName());
            person.sayHi(this);
        }

        synchronized void sayHi(Person person) {
            System.out.println(name + " says hi to " + person.getName());
        }
    }


}
