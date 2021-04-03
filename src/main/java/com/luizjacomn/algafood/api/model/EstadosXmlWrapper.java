package com.luizjacomn.algafood.api.model;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.luizjacomn.algafood.domain.model.Estado;

import lombok.Data;
import lombok.NonNull;

@Data
@JacksonXmlRootElement(localName = "estados")
public class EstadosXmlWrapper {
	
	@JacksonXmlProperty(localName = "estado")
	@JacksonXmlElementWrapper(useWrapping = false)
	@NonNull
	private List<Estado> estados;

}
