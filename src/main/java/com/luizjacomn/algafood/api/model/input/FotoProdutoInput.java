package com.luizjacomn.algafood.api.model.input;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.luizjacomn.algafood.core.validation.FileContentType;
import com.luizjacomn.algafood.core.validation.FileSize;
import com.luizjacomn.algafood.domain.model.Produto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FotoProdutoInput {

    @NotNull
    @FileSize(max = "750KB")
    @FileContentType(accept = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
    private MultipartFile arquivo;

    @NotBlank
    private String descricao;

    @JsonIgnore
    private Produto produto;

}
