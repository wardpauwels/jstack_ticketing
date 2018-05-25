# Ticketing met Camunda
## Implementatie van Camunda in Spring Boot project
### Maven dependencies
Add the following dependencies in your .pom file
* Dependency Management
  * org.camunda.bpm:org.camunda.bpm.extension.springboot</groupId>  
  * org.camunda.bpm.extension.springboot:camunda-bpm-spring-boot-starter-bom  
  
* Dependencies
  * Starter voor de Camunda engine  
  org.camunda.bpm.extension.springboot:camunda-bpm-spring-boot-starter
  * Starter voor Camunda web GUI  
  org.camunda.bpm.extension.springboot:camunda-bpm-spring-boot-starter-webapp
 
### Starten van Camunda applicatie
Voeg de annotatie `@EnableProcessApplication` toe boven je mainklasse van je applicatie.
Run je main en camunda start op samen met je applicatie.  
Voorbeeld hieronder te vinden:

```java
@SpringBootApplication
@EnableProcessApplication
public class Application {
    public static void main(String... args) {
         SpringApplication.run(Application.class, args);
    }
}
```