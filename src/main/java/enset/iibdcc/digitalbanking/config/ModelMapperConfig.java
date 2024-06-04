package enset.iibdcc.digitalbanking.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ModelMapperConfig {

    @Bean
    @Primary
    ModelMapper getModelMapper(){
        return new ModelMapper();
    }
}
