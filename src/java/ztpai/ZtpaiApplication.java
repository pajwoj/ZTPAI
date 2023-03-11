package ztpai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Scanner;

@SpringBootApplication
public class ZtpaiApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(ZtpaiApplication.class, args);
		DBParser parser = context.getBean(DBParser.class);

		parser.parse();

		while (true) {
			new Scanner(System.in).nextLine();
		}
	}
}
