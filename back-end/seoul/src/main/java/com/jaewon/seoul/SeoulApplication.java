package com.jaewon.seoul;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileReader;
import java.io.Reader;

@SpringBootApplication
public class SeoulApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SeoulApplication.class, args);
	}

}
