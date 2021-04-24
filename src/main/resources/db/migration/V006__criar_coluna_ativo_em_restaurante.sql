ALTER TABLE restaurante ADD COLUMN ativo tinyint(1) NOT NULL DEFAULT 1;

UPDATE restaurante SET ativo = 1;