package com.iitj.cse;

import com.iitj.cse.analyser.FileStorageService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
		FileStorageService.class
})
public class AnalyserSdeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnalyserSdeApplication.class, args);
	}

}
