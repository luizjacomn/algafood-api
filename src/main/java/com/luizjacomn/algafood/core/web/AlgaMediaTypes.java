package com.luizjacomn.algafood.core.web;

import org.springframework.http.MediaType;

public class AlgaMediaTypes {

    public final static String V1_APPLICATION_JSON_VALUE = "application/vnd.algafood.v1+json";

    public final static MediaType V1_APPLICATION_JSON = MediaType.valueOf(V1_APPLICATION_JSON_VALUE);

    public final static String V2_APPLICATION_JSON_VALUE = "application/vnd.algafood.v2+json";

    public final static MediaType V2_APPLICATION_JSON = MediaType.valueOf(V2_APPLICATION_JSON_VALUE);
}
