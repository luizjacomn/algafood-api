CREATE TABLE forma_pagamento (
	id BIGINT NOT NULL AUTO_INCREMENT, descricao VARCHAR(60) NOT NULL,

	PRIMARY KEY (id)
) engine=InnoDB default charset=utf8;

CREATE TABLE grupo (
	id BIGINT NOT NULL AUTO_INCREMENT, nome VARCHAR(60) NOT NULL,

	PRIMARY KEY (id)
) engine=InnoDB default charset=utf8;

CREATE TABLE grupo_permissao (
	grupo_id BIGINT NOT NULL,
	permissao_id BIGINT NOT NULL
) engine=InnoDB default charset=utf8;

CREATE TABLE permissao (
	id BIGINT NOT NULL AUTO_INCREMENT,
	nome VARCHAR(100) NOT NULL,
	descricao VARCHAR(60),

	PRIMARY KEY (id)
) engine=InnoDB default charset=utf8;

CREATE TABLE produto (
	id BIGINT NOT NULL AUTO_INCREMENT,
	ativo TINYINT(1) NOT NULL,
	nome VARCHAR(80) NOT NULL,
	descricao TEXT,
	preco DECIMAL(10,2),
	restaurante_id BIGINT NOT NULL,

	PRIMARY KEY (id)
) engine=InnoDB default charset=utf8;

CREATE TABLE restaurante (
	id BIGINT NOT NULL AUTO_INCREMENT,
	data_atualizacao DATETIME NOT NULL,
	data_cadastro DATETIME NOT NULL,
	nome VARCHAR(80) NOT NULL,
	taxa_frete DECIMAL(10,2) NOT NULL,
	cozinha_id BIGINT NOT NULL,

	endereco_bairro VARCHAR(60),
	endereco_cep VARCHAR(9),
	endereco_complemento VARCHAR(60),
	endereco_logradouro VARCHAR(100),
	endereco_numero VARCHAR(20),
	endereco_cidade_id BIGINT,

	PRIMARY KEY (id)
) engine=InnoDB default charset=utf8;

CREATE TABLE restaurante_forma_pagamento (
	restaurante_id BIGINT NOT NULL,
	forma_pagamento_id BIGINT NOT NULL
) engine=InnoDB default charset=utf8;

CREATE TABLE usuario (
	id BIGINT NOT NULL AUTO_INCREMENT,
	data_atualizacao DATETIME NOT NULL,
	data_cadastro DATETIME NOT NULL,
	email VARCHAR(255) NOT NULL,
	nome VARCHAR(80) NOT NULL,
	senha VARCHAR(255) NOT NULL,

	PRIMARY KEY (id)
) engine=InnoDB default charset=utf8;

CREATE TABLE usuario_grupo (
	usuario_id BIGINT NOT NULL,
	grupo_id BIGINT NOT NULL
) engine=InnoDB default charset=utf8;

ALTER TABLE grupo_permissao ADD CONSTRAINT FK_GRUPO_PERMISSAO_PERMISSAO FOREIGN KEY (permissao_id) REFERENCES permissao (id);

ALTER TABLE grupo_permissao ADD CONSTRAINT FK_GRUPO_PERMISSAO_GRUPO FOREIGN KEY (grupo_id) REFERENCES grupo (id);

ALTER TABLE produto ADD CONSTRAINT FK_PRODUTO_RESTAURANTE FOREIGN KEY (restaurante_id) REFERENCES restaurante (id);

ALTER TABLE restaurante ADD CONSTRAINT FK_RESTAURANTE_COZINHA FOREIGN KEY (cozinha_id) REFERENCES cozinha (id);

ALTER TABLE restaurante ADD CONSTRAINT FK_RESTAURANTE_CIDADE FOREIGN KEY (endereco_cidade_id) REFERENCES cidade (id);

ALTER TABLE restaurante_forma_pagamento ADD CONSTRAINT FK_REST_FORMA_PAGTO_FORMA_PAGTO FOREIGN KEY (forma_pagamento_id) REFERENCES forma_pagamento (id);

ALTER TABLE restaurante_forma_pagamento ADD CONSTRAINT FK_REST_FORMA_PAGTO_RESTAURANTE FOREIGN KEY (restaurante_id) REFERENCES restaurante (id);

ALTER TABLE usuario_grupo ADD CONSTRAINT FK_USUARIO_GRUPO_GRUPO FOREIGN KEY (grupo_id) REFERENCES grupo (id);

ALTER TABLE usuario_grupo ADD CONSTRAINT FK_USUARIO_GRUPO_USUARIO FOREIGN KEY (usuario_id) REFERENCES usuario (id);
