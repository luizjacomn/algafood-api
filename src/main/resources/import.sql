insert into cozinha (id, nome) values (1, 'Tailandeza');
insert into cozinha (id, nome) values (2, 'Indiana');

insert into restaurante (id, nome, taxa_frete, cozinha_id) values (1, 'Thai Gourmet', 5.0, 1);
insert into restaurante (id, nome, taxa_frete, cozinha_id) values (2, 'Thai Delivery', 3.99, 1);
insert into restaurante (id, nome, taxa_frete, cozinha_id) values (3, 'Tuk Tuk Foods', 0.0, 2);

insert into forma_pagamento (id, descricao) values (1, 'À vista');
insert into forma_pagamento (id, descricao) values (2, 'Cartão (crédito)');
insert into forma_pagamento (id, descricao) values (3, 'Cartão (débito)');

insert into estado (id, nome) values (1, 'Ceará');
insert into estado (id, nome) values (2, 'Rio Grande do Norte');

insert into cidade (id, nome, estado_id) values (1, 'Morada Nova', 1);
insert into cidade (id, nome, estado_id) values (2, 'Fortaleza', 1);
insert into cidade (id, nome, estado_id) values (3, 'Apodi', 2);
