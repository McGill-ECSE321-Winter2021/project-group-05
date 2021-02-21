package ca.mcgill.ecse321.repairshop;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;




@RestController
@SpringBootApplication
@EnableJpaRepositories("ca.mcgill.ecse321.repairshop.dao")
@EntityScan("ca.mcgill.ecse321.repairshop.model")
public class RepairShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(RepairShopApplication.class, args);
	}

	@RequestMapping("/")
	public String greeting(){
	  return "Hello world!";
	}
  
}
