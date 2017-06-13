package config;

import java.awt.print.Pageable;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/*
 * CLASE DE  CONFIGURACION PARA  EL SWAGGER
 * 
 * URL DEL SWAGGER:  http://localhost:8080/SPRING.tpv.1.2.0-SNAPSHOT/api/swagger-ui.html
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /*
     * Metodo para configurar el swagger donde se define: que tipo de peticiones, que clases de parametros admite, que informacion generica
     * se quiere mostrar, etc
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build()
                .apiInfo(apiInfo()).ignoredParameterTypes(Pageable.class);
    }

    /*
     * Metodo en el cual se detalla la informacion generia que se quiere mostrar del swagger
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("SWAGGER DEL PROYECTO TPV. TECNOLOGÍA SPRING")
                .description("Swagger del proyecto tpv, donde se puede realizar llamadas a las distintas apis de la aplicación"
                        + " y poder tanto probar las apis como ver los resultados de las llamadas.")
                .version("1.2.0").build();
    }

}
