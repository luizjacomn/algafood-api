package com.luizjacomn.algafood.core.validation.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

@AllArgsConstructor
@Getter
public class ValidacaoException extends RuntimeException {

    private static final long serialVersionUID = -2200069651260204047L;

    private BindingResult bindingResult;

}
