ALTER TABLE pedido ADD CONSTRAINT FK_PEDIDO_CIDADE FOREIGN KEY (endereco_cidade_id) REFERENCES cidade (id);