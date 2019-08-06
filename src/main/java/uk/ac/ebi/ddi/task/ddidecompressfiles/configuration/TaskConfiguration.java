package uk.ac.ebi.ddi.task.ddidecompressfiles.configuration;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableTask
@EnableConfigurationProperties({ DecompressTaskProperties.class })
public class TaskConfiguration {

}
