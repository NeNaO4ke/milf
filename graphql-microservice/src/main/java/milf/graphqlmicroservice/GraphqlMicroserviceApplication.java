package milf.graphqlmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@SpringBootApplication
@EnableReactiveFeignClients
@EnableDiscoveryClient
public class GraphqlMicroserviceApplication {
    public static void main(String[] args) {
        SpringApplication.run(GraphqlMicroserviceApplication.class, args);
    }

}
