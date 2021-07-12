package com.marstechnologiesbrasil.personapi.swagger;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to configure the Swagger UI for the API, making easier to test, use and document it
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * Configure Swagger UI for the API
     * @return
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.marstechnologiesbrasil.personapi.controller"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, responseMessageForGET())
                .globalResponseMessage(RequestMethod.POST, responseMessageForPOST())
                .globalResponseMessage(RequestMethod.PUT, responseMessageForPUT())
                .globalResponseMessage(RequestMethod.DELETE, responseMessageForDELETE());
    }

    private List<ResponseMessage> responseMessageForGET() {
        return new ArrayList<ResponseMessage>() {{
            add(createResponseMessageBuilder(200, "Found", "string"));
            add(createResponseMessageBuilder(404, "Not Found", "string"));
        }};
    }

        private List<ResponseMessage> responseMessageForPOST() {
            return new ArrayList<ResponseMessage>() {{
                add(createResponseMessageBuilder(201, "Created", "string"));
                add(createResponseMessageBuilder(404, "Not Found", "string"));
            }};
    }

    private List<ResponseMessage> responseMessageForPUT() {
        return new ArrayList<ResponseMessage>() {{
            add(createResponseMessageBuilder(200, "Ok", "string"));
            add(createResponseMessageBuilder(404, "Not Found", "string"));
        }};
    }

    private List<ResponseMessage> responseMessageForDELETE() {
        return new ArrayList<ResponseMessage>() {{
            add(createResponseMessageBuilder(204, "No Content", "string"));
            add(createResponseMessageBuilder(404, "Not Found", "string"));
        }};
    }

    /**
     * Create a MessageResponse that is used to configure global messages for a given HTTP verb
     * @param i HTTP status code
     * @param s Message to be shown
     * @param responseModel The model for the message shown
     * @return A ResponseMessage
     */
    private ResponseMessage createResponseMessageBuilder(int i, String s, String responseModel) {
        return new ResponseMessageBuilder()
                .code(i)
                .message(s)
                .responseModel(new ModelRef(responseModel))
                .build();
    }
}
