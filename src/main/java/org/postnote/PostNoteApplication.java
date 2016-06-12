package org.postnote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Configures and starts a PostIt-like REST applicaiton using SpringBoot.
 * 
 * @version 1.0 
 * @since 2014-06-12
 * 
 * @author Maurizio Nagni
 **/
@SpringBootApplication
public class PostNoteApplication extends SpringBootServletInitializer {

	final static Logger logger = LoggerFactory.getLogger(PostNoteApplication.class);

	public static void main(String[] args) throws Exception {
		ConfigurableApplicationContext context = SpringApplication.run(PostNoteApplication.class, args);
		logger.info("PostNote application started.");
		logger.info("Database URL String: " + context.getEnvironment().getProperty("spring.datasource.url"));
		logger.info("Logs in " + context.getEnvironment().getProperty("postnote.logDir"));
	}
}
