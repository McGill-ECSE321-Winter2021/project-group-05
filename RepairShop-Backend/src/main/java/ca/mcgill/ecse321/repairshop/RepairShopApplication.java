package ca.mcgill.ecse321.repairshop;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.SpringApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("ca.mcgill.ecse321.repairshop.dao")
@EntityScan("ca.mcgill.ecse321.repairshop.model")
public class RepairShopApplication {

	/**
	 * run application
	 *
	 * @param args main
	 */
	public static void main(String[] args) {
		SpringApplication.run(RepairShopApplication.class, args);
	}

}
