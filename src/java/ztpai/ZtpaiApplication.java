package ztpai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class ZtpaiApplication {
	public static void main(String[] args) {
		SpringApplication.run(ZtpaiApplication.class, args);

		while (true) {
			new Scanner(System.in).nextLine();
		}
	}
}
