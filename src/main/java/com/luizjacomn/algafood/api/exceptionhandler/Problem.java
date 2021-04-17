package com.luizjacomn.algafood.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class Problem {

    /* Campos da especificação RFC 7807 */
    private Integer status;

    private String type;

    private String title;

    private String detail;

    /* Campos extras */
    private LocalDateTime timestamp;

    private List<Field> fields;

    @Getter
    @lombok.Builder
    public static class Field {
        private String name;

        private String userMessage;
    }

    public static class Builder {
        private Problem problem;

        public Builder() {
            problem = new Problem();
            problem.timestamp = LocalDateTime.now();
        }

        /**
         * When using this method, {@code title, type and status} properties will be pre defined
         * according {@link ProblemType} specified.
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
