package com.luizjacomn.algafood.api.model.converter;

public interface RelationshipAttributes {

    default String[] getRelationshipAttributes() {
        return new String[0];
    }
}
