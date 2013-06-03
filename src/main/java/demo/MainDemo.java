package demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author mingfai.ma
 */
public class MainDemo {
    static final Logger log = LoggerFactory.getLogger(MainDemo.class);


    public static void main(String[] args) throws InterruptedException {
        int n = 2;//Runtime.getRuntime().availableProcessors();
        ExecutorService pool = Executors.newFixedThreadPool(n);
        log.info("main() - run - n: {}, pool: {}", n, pool);
        pool.submit(new Sleeper(5, "I'm in, holding the slot for 5s"));
        pool.submit(new Sleeper(10, "I'm in, holding the slot for 10s"));
        pool.submit(new Sleeper(5, "I can get in now after the 5s guy is done"));
        Thread.sleep(20000);
        pool.shutdown();
    }


    static class Sleeper implements Runnable {
        final Logger log = LoggerFactory.getLogger(Sleeper.class);
        final int s;
        final String message;

        Sleeper(int second, String message) {
            this.s = second;
            this.message = message;
        }

        @Override public void run() {
            System.out.println(message);
            log.trace("run() - run - sleeping for {}s", s);
            try {
                Thread.sleep(s * 1000);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
            log.trace("run() - end");
        }
    }

}
