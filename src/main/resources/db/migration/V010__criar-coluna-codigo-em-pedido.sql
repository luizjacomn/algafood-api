ALTER TABLE pedido ADD codigo VARCHAR(36) NOT NULL AFTER id;
UPDATE pedido SET codigo = uuid();
ALTER TABLE pedido ADD CONSTRAINT UQ_PEDIDO_CODIGO UNIQUE (codigo);