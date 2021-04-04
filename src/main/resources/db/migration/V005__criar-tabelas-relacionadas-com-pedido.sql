CREATE TABLE pedido (
	id BIGINT NOT NULL AUTO_INCREMENT,
	sub_total DECIMAL(10,2) NOT NULL,
	taxa_frete DECIMAL(10,2) NOT NULL,
	valor_total DECIMAL(10,2) NOT NULL,
	data_criacao DATETIME NOT NULL,
	data_confirmacao DATETIME,
	data_cancelamento DATETIME,
	data_entrega DATETIME,
	status VARCHAR(10) NOT NULL,

	endereco_bairro VARCHAR(60) NOT NULL,
	endereco_cep VARCHAR(9) NOT NULL,
	endereco_complemento VARCHAR(60),
	endereco_logradouro VARCHAR(100) NOT NULL,
	endereco_numero VARCHAR(20) NOT NULL,
	endereco_cidade_id BIGINT NOT NULL,

	restaurante_id BIGINT NOT NULL,
	cliente_id BIGINT NOT NULL,
	forma_pagamento_id BIGINT NOT NULL,

	PRIMARY KEY (id),

	CONSTRAINT FK_PEDIDO_RESTAURANTE FOREIGN KEY (restaurante_id) REFERENCES restaurante (id),

    CONSTRAINT FK_PEDIDO_CLIENTE FOREIGN KEY (cliente_id) REFERENCES usuario (id),

    CONSTRAINT FK_PEDIDO_FORMA_PAGAMENTO FOREIGN KEY (forma_pagamento_id) REFERENCES forma_pagamento (id)
) engine=InnoDB default charset=utf8;

CREATE TABLE item_pedido (
	id BIGINT NOT NULL AUTO_INCREMENT,
	quantidade SMALLINT(6) NOT NULL,
	preco_unitario DECIMAL(10,2) NOT NULL,
	preco_total DECIMAL(10,2) NOT NULL,
	observacao VARCHAR(100),

	produto_id BIGINT NOT NULL,
	pedido_id BIGINT NOT NULL,

	PRIMARY KEY (id),

	UNIQUE KEY UK_ITEM_PEDIDO_PRODUTO (pedido_id, produto_id),

	CONSTRAINT FK_ITEM_PEDIDO_PRODUTO FOREIGN KEY (produto_id) REFERENCES produto (id),

    CONSTRAINT FK_ITEM_PEDIDO_PEDIDO FOREIGN KEY (pedido_id) REFERENCES pedido (id)
) engine=InnoDB default charset=utf8;
