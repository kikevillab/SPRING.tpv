package config;

import java.awt.print.Pageable;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PagedResourcesAssembler;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.any())
          
            .build()
            .apiInfo(apiInfo())
            .ignoredParameterTypes(Pageable.class);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("SWAGGER DEL PROYECTO TPV. TECNOLOGÍA SPRING")
            .description("Swagger del proyecto tpv, donde se puede realizar llamadas a las distintas apis de la aplicación"
                    + " y poder tanto probar las apis como ver los resultados de las llamadas.")
            .version("1.2.0")
            .termsOfServiceUrl("http://terms-of-services.url")
            .licenseUrl("http://url-to-license.com")
            .build();
    }

}
