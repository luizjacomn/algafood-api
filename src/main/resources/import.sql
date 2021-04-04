insert into cozinha (id, nome) values (1, 'Tailandeza');
insert into cozinha (id, nome) values (2, 'Indiana');
insert into cozinha (id, nome) values (3, 'Japonesa');

insert into forma_pagamento (id, descricao) values (1, 'À vista');
insert into forma_pagamento (id, descricao) values (2, 'Cartão (crédito)');
insert into forma_pagamento (id, descricao) values (3, 'Cartão (débito)');

insert into estado (id, nome) values (1, 'Ceará');
insert into estado (id, nome) values (2, 'Rio Grande do Norte');

insert into cidade (id, nome, estado_id) values (1, 'Morada Nova', 1);
insert into cidade (id, nome, estado_id) values (2, 'Fortaleza', 1);
insert into cidade (id, nome, estado_id) values (3, 'Apodi', 2);

insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values (1, 'Thai Gourmet', 5.0, 1, utc_timestamp, utc_timestamp);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values (2, 'Thai Delivery', 3.99, 1, utc_timestamp, utc_timestamp);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values (3, 'Tuk Tuk Foods', 0.0, 2, utc_timestamp, utc_timestamp);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro) values (4, 'Sushi da Hora', 14.99, 3, utc_timestamp, utc_timestamp, 1, '62940-000', 'Avenida das Pizzarias', 'S/N', 'Vizinho ao Hotel Casa Grande', 'Nova Morada');

insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1, 1);
insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1, 2);
insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (2, 1);
insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (3, 1);
insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (3, 2);
insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (4, 1);
insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (4, 2);
insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (4, 3);

insert into produto (id, nome, descricao, preco, ativo, restaurante_id) values (1, 'Macarronada', 'Macarrão ao molho branco', 29.99, 1, 3);
insert into produto (id, nome, descricao, preco, ativo, restaurante_id) values (2, 'Baião com iscas de frango', null, 19.99, 1, 3);
insert into produto (id, nome, descricao, preco, ativo, restaurante_id) values (3, 'Combo hot', '10 peças sortidas', 16.99, 1, 4);
insert into produto (id, nome, descricao, preco, ativo, restaurante_id) values (4, 'Sashimi', null, 29.99, 1, 4);
