CREATE TABLE estado (
	id bigint NOT NULL AUTO_INCREMENT,
	nome VARCHAR(80) NOT NULL,

	PRIMARY KEY (id)
) engine=InnoDB default charset=utf8;

ALTER TABLE cidade ADD COLUMN estado_id BIGINT NOT NULL;

ALTER TABLE cidade ADD CONSTRAINT fk_cidade_estado
FOREIGN KEY (estado_id) REFERENCES estado (id);