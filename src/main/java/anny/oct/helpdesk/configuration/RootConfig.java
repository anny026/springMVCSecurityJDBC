package anny.oct.helpdesk.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = {"anny.oct.helpdesk.service", "anny.oct.helpdesk.dao","anny.oct.helpdesk.model"})
public class RootConfig {



}
