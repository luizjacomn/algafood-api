CREATE TABLE restaurante_responsavel (
	restaurante_id BIGINT NOT NULL,
	usuario_id BIGINT NOT NULL,

	PRIMARY KEY(restaurante_id, usuario_id),

	CONSTRAINT FK_RESTAURANTE_RESPONSAVEL FOREIGN KEY (restaurante_id) REFERENCES restaurante (id),

	CONSTRAINT FK_RESPONSAVEL_RESTAURANTE FOREIGN KEY (usuario_id) REFERENCES usuario (id)
) engine=InnoDB default charset=utf8;