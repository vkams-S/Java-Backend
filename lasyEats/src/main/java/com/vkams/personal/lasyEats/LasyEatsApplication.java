package com.vkams.personal.lasyEats;
import com.vkams.personal.lasyEats.Repository.RestaurantRepository;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@Log4j2
@EnableAutoConfiguration
@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = RestaurantRepository.class)
public class LasyEatsApplication {

	public static void main(String[] args) {
		SpringApplication.run(LasyEatsApplication.class, args);

		log.info("Congratulation! lasyEasts Server is Running !");

	}
	@Bean
	@Scope("prototype")
	// we want new object every time IOC
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}


}
