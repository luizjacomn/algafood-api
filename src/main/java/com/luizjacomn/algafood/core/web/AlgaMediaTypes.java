package com.luizjacomn.algafood.core.web;

import org.springframework.http.MediaType;

public class AlgaMediaTypes {

    public final static String V1_APPLICATION_JSON_VALUE = "application/vnd.algafood.v1+json";

    public final static MediaType V1_APPLICATION_JSON = MediaType.valueOf(V1_APPLICATION_JSON_VALUE);
}
