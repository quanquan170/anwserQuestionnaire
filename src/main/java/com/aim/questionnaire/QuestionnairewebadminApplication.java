package com.aim.questionnaire;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.aim.questionnaire.mapper")
public class QuestionnairewebadminApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuestionnairewebadminApplication.class, args);
	}
}
