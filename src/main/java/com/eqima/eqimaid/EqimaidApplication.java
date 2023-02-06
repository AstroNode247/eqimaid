package com.eqima.eqimaid;

import com.eqima.eqimaid.model.User;
import com.eqima.eqimaid.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableMongoAuditing
@SpringBootApplication
public class EqimaidApplication {

	public static void main(String[] args) {
		SpringApplication.run(EqimaidApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

//	@Bean
//	CommandLineRunner run(UserRepository userRepository) {
//		return args -> {
//			userRepository.save(new User(null, 1, "Alex", "Rakoto", "Sample", null));
//			userRepository.save(new User(null, 2, "Mike", "Rakoto", "Mega", null));
//			userRepository.save(new User(null, 3, "Alex", "Rajao", "Fig", null));
//			userRepository.save(new User(null, 4, "Jeanne", "Rameva", "Sample", null));
//		};
//	}
}
