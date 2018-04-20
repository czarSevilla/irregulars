package czar.english.irregulars;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class IrregularsApplication {

	public static void main(String[] args) {
		SpringApplication.run(IrregularsApplication.class, args);
	}
}
