package org.postnote.test;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * Configures Springboot for unit-tests
 **/

/* Assigns the profile to the configuration */
@Profile("test")
@Configuration
@EnableAutoConfiguration
/* Loads the JPA entities */
@EntityScan(basePackages = { "org.postnote.entity" })
/* Loads the JPA repositories */
@EnableJpaRepositories(basePackages = { "org.postnote.repository" })
@EnableTransactionManagement
@ComponentScan({"org.postnote.*"})
@PropertySource("classpath:application-test.properties")
public class RepositoryConfiguration {

}
