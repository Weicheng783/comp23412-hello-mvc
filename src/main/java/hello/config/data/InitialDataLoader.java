package hello.config.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import hello.dao.GreetingService;
import hello.entities.Greeting;

@Configuration
@Profile("default")
public class InitialDataLoader {

	private final static Logger log = LoggerFactory.getLogger(InitialDataLoader.class);

	private final static String[] TEMPLATES = { "Hello, %s!", "Bonjour, %s!" };

	@Bean
	CommandLineRunner initDatabase(GreetingService greetingService) {
		if (greetingService.count() > 0) {
			return args -> {
				log.info("Database already populated. Skipping data initialization.");
			};
		}

		return args -> {
			for (String template : TEMPLATES) {
				log.info("Preloading: " + greetingService.save(new Greeting(template)));
			}
		};
	}
}
