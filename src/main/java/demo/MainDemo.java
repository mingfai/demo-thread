package demo;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author mingfai.ma
 */
public class MainDemo {
    static final Logger log = LoggerFactory.getLogger(MainDemo.class);

    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(10000);
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
        static final List<String> data = new ArrayList<String>();
        final int s;
        final String message;

        Sleeper(int second, String message) {
            this.s = second;
            this.message = message;
        }

        @Override public void run() {
            System.out.println(message);
            log.trace("run() - run - sleeping for {}s, and then do something", s);
            try {
                Thread.sleep(s * 1000);
                for (int i = 0; i < 100000; i++)
                    data.add(RandomStringUtils.random(1024));
                Collections.sort(data);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
            log.trace("run() - end");
        }
    }

}
