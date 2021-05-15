package com.luizjacomn.algafood.core.validation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class FileContentTypeValidator implements ConstraintValidator<FileContentType, MultipartFile> {

    private String[] acceptedTypes;

    @Override
    public void initialize(FileContentType constraintAnnotation) {
        this.acceptedTypes = constraintAnnotation.accept();
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        return value == null || Arrays.asList(acceptedTypes).contains(value.getContentType());
    }
}
