package com.luizjacomn.algafood.api.openapi.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public abstract class PageModel<T> {

    private List<T> content;

    @ApiModelProperty(example = "10", value = "Quantidade de resultados por página")
    private Long size;

    @ApiModelProperty(example = "5", value = "Total de páginas (totalElements / size)")
    private Long totalPages;

    @ApiModelProperty(example = "50", value = "Total de resultados encontrados")
    private Long totalElements;

    @ApiModelProperty(example = "0", value = "Página atual (começa em 0)")
    private Long currentPage;
}
