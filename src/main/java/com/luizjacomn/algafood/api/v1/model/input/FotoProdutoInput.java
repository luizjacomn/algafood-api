package com.luizjacomn.algafood.api.v1.model.input;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.luizjacomn.algafood.core.validation.FileContentType;
import com.luizjacomn.algafood.core.validation.FileSize;
import com.luizjacomn.algafood.domain.model.Produto;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FotoProdutoInput {

    @ApiModelProperty(value = "Arquivo da foto do produto (máximo 750KB, apenas JPG e PNG)", required = true)
    @NotNull
    @FileSize(max = "750KB")
    @FileContentType(accept = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
    private MultipartFile arquivo;

    @ApiModelProperty(value = "Descrição da foto do produto", required = true)
    @NotBlank
    private String descricao;

    @ApiModelProperty(hidden = true)
    @JsonIgnore
    @Getter(AccessLevel.PRIVATE)
    private Produto produto;

}
