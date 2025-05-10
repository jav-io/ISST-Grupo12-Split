-- Renombrar columnas
ALTER TABLE gasto RENAME COLUMN id_gasto TO id;
ALTER TABLE gasto RENAME COLUMN id_grupo TO grupo_id;
ALTER TABLE gasto RENAME COLUMN id_pagador TO pagador_id;

-- Modificar tipos de datos
ALTER TABLE gasto ALTER COLUMN monto TYPE decimal(10,2);
ALTER TABLE gasto ALTER COLUMN fecha TYPE timestamp; 