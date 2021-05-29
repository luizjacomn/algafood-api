package com.luizjacomn.algafood.core.openapi;

import com.fasterxml.classmate.TypeResolver;
import com.luizjacomn.algafood.api.exceptionhandler.Problem;
import com.luizjacomn.algafood.api.model.output.CozinhaOutput;
import com.luizjacomn.algafood.api.openapi.model.CozinhasModel;
import com.luizjacomn.algafood.api.openapi.model.PageableModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.*;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.Example;
import springfox.documentation.schema.ModelSpecification;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@Configuration
@EnableOpenApi
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig implements WebMvcConfigurer {

    @Bean
    public Docket apiDocket() {
        TypeResolver typeResolver = new TypeResolver();

        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.luizjacomn.algafood.api"))
                .build()
                .useDefaultResponseMessages(false)
                .globalResponses(HttpMethod.GET, globalGetResponseMessages())
                .globalResponses(HttpMethod.POST, globalPostPutResponseMessages())
                .globalResponses(HttpMethod.PUT, globalPostPutResponseMessages())
                .globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
//                .globalRequestParameters(Arrays.asList(
//                        new RequestParameterBuilder()
//                                .name("campos")
//                                .description("Nomes das propriedades para filtrar na resposta, separados por vírgula")
//                                .in(ParameterType.QUERY)
//                                .required(false)
//                                .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
//                                .build()
//                ))
                .additionalModels(typeResolver.resolve(Problem.class))
                .ignoredParameterTypes(ServletWebRequest.class, InputStream.class, InputStreamResource.class, Sort.class)
                .directModelSubstitute(Pageable.class, PageableModel.class)
                .alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(Page.class, CozinhaOutput.class), CozinhasModel.class))
                .apiInfo(apiInfo())
                .tags(
                        new Tag("Cidades", "Gerenciar as cidades"),
                        new Tag("Pedidos", "Gerenciar os pedidos"),
                        new Tag("Estatísticas", "Estatísticas da AlgaFood")
                );
    }

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("AlgaFood API")
                .description("API para restaurantes e clientes")
                .version("1.0")
                .contact(new Contact("Luiz Jacó", "https://github.com/luizjacomn", "luizjacomn@gmail.com"))
                .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    private List<Response> globalGetResponseMessages() {
        return Arrays.asList(
                buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno do servidor", true),
                buildResponse(HttpStatus.NOT_ACCEPTABLE, "Recurso não possui representação aceita pelo consumidor", false)
        );
    }

    private List<Response> globalPostPutResponseMessages() {
        return Arrays.asList(
                buildResponse(HttpStatus.BAD_REQUEST, "Requisição inválida (erro do cliente)", true),
                buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor", true),
                buildResponse(HttpStatus.NOT_ACCEPTABLE, "Recurso não possui representação aceita pelo consumidor", false),
                buildResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Requisição recusada porque o corpo está em um formato não suportado", true)
        );
    }

    private List<Response> globalDeleteResponseMessages() {
        return Arrays.asList(
                buildResponse(HttpStatus.BAD_REQUEST, "Requisição inválida (erro do cliente)", true),
                buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor", true)
        );
    }

    private Response buildResponse(HttpStatus httpStatus, String description, boolean withModel) {
        ResponseBuilder builder = new ResponseBuilder()
                .code(String.valueOf(httpStatus.value()))
                .description(description);

        if (withModel) {
            builder.representation(MediaType.APPLICATION_JSON)
                    .apply(builderModelProblema());
        }

        return builder.build();
    }

    private Consumer<RepresentationBuilder> builderModelProblema() {
        return r -> r.model(m -> m.name("Problema")
                .referenceModel(ref -> ref.key(k -> k.qualifiedModelName(q ->
                        q.name("Problema")
                                .namespace("com.luizjacomn.algafood.api.exceptionhandler")
                ))));
    }
}
