ALTER TABLE forma_pagamento ADD COLUMN data_atualizacao DATETIME NULL;

UPDATE forma_pagamento SET data_atualizacao = UTC_TIMESTAMP;

ALTER TABLE forma_pagamento modify data_atualizacao DATETIME NOT NULL;