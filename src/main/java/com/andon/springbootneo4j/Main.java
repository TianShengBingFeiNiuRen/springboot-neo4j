package com.andon.springbootneo4j;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author Andon
 * 2021/7/23
 */
@Component
public class Main implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) {
        for (int i = 0; i < 10; i++) {
            System.out.println("Run!!");
        }
    }
}
