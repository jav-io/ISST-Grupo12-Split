-- Tabla de usuarios con roles
ALTER TABLE usuario ADD COLUMN IF NOT EXISTS roles text[];

-- Actualizar usuarios existentes con rol por defecto
UPDATE usuario SET roles = ARRAY['ROLE_USER'] WHERE roles IS NULL; 