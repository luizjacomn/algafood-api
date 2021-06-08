package com.luizjacomn.algafood.api.v1.model.output;

import java.io.Serializable;

public interface OutputIdentifier<O> {

    Serializable getIdentifier(O output);

}
