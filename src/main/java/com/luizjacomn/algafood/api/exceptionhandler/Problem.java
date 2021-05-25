package com.luizjacomn.algafood.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@ApiModel("Problema")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class Problem {

    /* Campos da especificação RFC 7807 */
    @ApiModelProperty(example = "400")
    private Integer status;

    @ApiModelProperty(example = "https://algafood.com.br/erro-negocio")
    private String type;

    @ApiModelProperty(example = "Violação de regra de negócio")
    private String title;

    @ApiModelProperty(example = "Estado com id 8 não foi encontrado")
    private String detail;

    /* Campos extras */
    @ApiModelProperty(example = "2000-01-01T18:00:00.000Z", dataType = "java.lang.String")
    private OffsetDateTime timestamp;

    @ApiModelProperty("Campos com problemas")
    private List<Field> fields;

    @ApiModel("CampoProblema")
    @Getter
    @lombok.Builder
    public static class Field {
        @ApiModelProperty(example = "taxaFrete")
        private String name;

        @ApiModelProperty(example = "Taxa Frete é obrigatório(a)")
        private String userMessage;
    }

    public static class Builder {
        private Problem problem;

        public Builder() {
            problem = new Problem();
            problem.timestamp = OffsetDateTime.now();
        }

        /**
         * When using this method, {@code title, type and status} properties will be pre defined
         * according {@link ProblemType} specified.
         *
         * @param problemType
         * @return Problem.Builder instance
         */
        public Builder withProblemType(ProblemType problemType) {
            problem.title = problemType.getTitle();
            problem.type = problemType.getUri();
            problem.status = problemType.getStatus();
            return this;
        }

        public Builder withStatus(Integer status) {
            problem.status = status;
            return this;
        }

        public Builder withType(String type) {
            problem.type = type;
            return this;
        }

        public Builder withTitle(String title) {
            problem.title = title;
            return this;
        }

        public Builder withDetail(String detail) {
            problem.detail = detail;
            return this;
        }

        public Builder withFields(List<Field> fields) {
            problem.fields = fields;
            return this;
        }

        public Problem build() {
            return problem;
        }
    }

}
