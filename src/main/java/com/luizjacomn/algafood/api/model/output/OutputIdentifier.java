package com.luizjacomn.algafood.api.model.output;

import java.io.Serializable;

public interface OutputIdentifier<O> {

    Serializable getIdentifier(O output);

}
