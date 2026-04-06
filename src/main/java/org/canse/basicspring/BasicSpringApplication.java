package org.canse.basicspring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BasicSpringApplication implements ApplicationRunner {

    @Value("${test.value}")
    private String value;

    public static void main(String[] args) {
        SpringApplication.run(BasicSpringApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("----");
        System.out.println(args.containsOption("person.name"));
        System.out.println("----");
        System.out.println(args.getOptionValues("person.name"));
        System.out.println("----");
        System.out.println(value);
        System.out.println("----");
    }


    @Bean
    CommandLineRunner readXls(ApplicationArguments applicationArguments) {
        return args -> {
            System.out.println("----");
            System.out.println(applicationArguments.getOptionValues("file.format"));
            System.out.println("----");
            System.out.println(applicationArguments.getOptionValues("file.path"));
            System.out.println("----");
        };
    }

}
