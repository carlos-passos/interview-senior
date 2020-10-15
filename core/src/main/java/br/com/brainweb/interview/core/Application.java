package br.com.brainweb.interview.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.brainweb.interview.core.configuration.LoadBalancingConfiguration;
import springfox.documentation.annotations.ApiIgnore;

@SpringBootApplication
@EnableTransactionManagement
@EntityScan("br.com.brainweb.interview.*")
@EnableCaching
@RibbonClient(name = "server", configuration = LoadBalancingConfiguration.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
}

@Controller
@ApiIgnore
class SwaggerDocumentationController {
	@RequestMapping(path= {"/"})
	public String goToSwagger(final Model model) {
		return "redirect:/swagger-ui.html#/";
	}
}