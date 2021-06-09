package com.luizjacomn.algafood.core.openapi;

import com.fasterxml.classmate.TypeResolver;
import com.luizjacomn.algafood.api.exceptionhandler.Problem;
import com.luizjacomn.algafood.api.openapi.model.LinksModelOpenApi;
import com.luizjacomn.algafood.api.openapi.model.PageableModel;
import com.luizjacomn.algafood.api.v1.model.output.CidadeOutput;
import com.luizjacomn.algafood.api.v1.model.output.CozinhaOutput;
import com.luizjacomn.algafood.api.v1.model.output.PedidoResumeOutput;
import com.luizjacomn.algafood.api.v1.openapi.model.CidadesModel;
import com.luizjacomn.algafood.api.v1.openapi.model.CozinhasModel;
import com.luizjacomn.algafood.api.v1.openapi.model.PedidosResumeModel;
import com.luizjacomn.algafood.api.v2.model.output.CidadeOutputV2;
import com.luizjacomn.algafood.api.v2.model.output.CozinhaOutputV2;
import com.luizjacomn.algafood.api.v2.openapi.model.CidadesModelV2;
import com.luizjacomn.algafood.api.v2.openapi.model.CozinhasModelV2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.PagedModel;
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
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
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

    public ApiInfo apiInfoV1() {
        return new ApiInfoBuilder()
                .title("AlgaFood API")
                .description("API para restaurantes e clientes.<br>" +
                        "<strong>Esta versão da API está depreciada e deixará de existir a partir de 01/07/2021. " +
                        "Use a versão atual (V2) da API.</strong>")
                .version("V1")
                .contact(new Contact("Luiz Jacó", "https://github.com/luizjacomn", "luizjacomn@gmail.com"))
                .build();
    }

//    @Bean
    public Docket apiDocketV1() {
        TypeResolver typeResolver = new TypeResolver();

        return new Docket(DocumentationType.OAS_30)
                .groupName("V1")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.luizjacomn.algafood.api"))
                .paths(PathSelectors.ant("/v1/**"))
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
                .directModelSubstitute(Links.class, LinksModelOpenApi.class)
                .alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(PagedModel.class, CozinhaOutput.class), CozinhasModel.class))
                .alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(CollectionModel.class, CidadeOutput.class), CidadesModel.class))
                .alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(PagedModel.class, PedidoResumeOutput.class), PedidosResumeModel.class))
                .apiInfo(apiInfoV1())
                .tags(
                        new Tag("Cidades", "Gerenciar as cidades"),
                        new Tag("Pedidos", "Gerenciar os pedidos"),
                        new Tag("Estatísticas", "Estatísticas da AlgaFood")
                );
    }

    public ApiInfo apiInfoV2() {
        return new ApiInfoBuilder()
                .title("AlgaFood API")
                .description("API para restaurantes e clientes")
                .version("V2")
                .contact(new Contact("Luiz Jacó", "https://github.com/luizjacomn", "luizjacomn@gmail.com"))
                .build();
    }

    @Bean
    public Docket apiDocketV2() {
        TypeResolver typeResolver = new TypeResolver();

        return new Docket(DocumentationType.OAS_30)
                .groupName("V2")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.luizjacomn.algafood.api"))
                .paths(PathSelectors.ant("/v2/**"))
                .build()
                .useDefaultResponseMessages(false)
                .globalResponses(HttpMethod.GET, globalGetResponseMessages())
                .globalResponses(HttpMethod.POST, globalPostPutResponseMessages())
                .globalResponses(HttpMethod.PUT, globalPostPutResponseMessages())
                .globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
                .additionalModels(typeResolver.resolve(Problem.class))
                .ignoredParameterTypes(ServletWebRequest.class, InputStream.class, InputStreamResource.class, Sort.class)
                .directModelSubstitute(Pageable.class, PageableModel.class)
                .directModelSubstitute(Links.class, LinksModelOpenApi.class)
                .alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(PagedModel.class, CozinhaOutputV2.class), CozinhasModelV2.class))
                .alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(CollectionModel.class, CidadeOutputV2.class), CidadesModelV2.class))
                .apiInfo(apiInfoV2())
                .tags(
                        new Tag("Cidades", "Gerenciar as cidades"),
                        new Tag("Cozinhas", "Gerenciar as cozinhas")
                );
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
