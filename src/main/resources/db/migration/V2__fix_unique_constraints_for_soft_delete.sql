-- Eliminar la restricción UNIQUE existente en la columna description de la tabla objectives
ALTER TABLE objectives DROP CONSTRAINT objectives_description_key;

-- Crear un índice único parcial que solo aplique a los registros activos
CREATE UNIQUE INDEX idx_objectives_description_active ON objectives (description) WHERE active = true;

-- Hacer lo mismo para otras tablas que tienen restricciones UNIQUE y borrado lógico (opcional pero recomendado)

-- Areas
ALTER TABLE areas DROP CONSTRAINT areas_name_key;
CREATE UNIQUE INDEX idx_areas_name_active ON areas (name) WHERE active = true;

-- Owners (email)
ALTER TABLE owners DROP CONSTRAINT owners_email_key;
CREATE UNIQUE INDEX idx_owners_email_active ON owners (email) WHERE active = true;

-- Periods
ALTER TABLE periods DROP CONSTRAINT periods_name_key;
CREATE UNIQUE INDEX idx_periods_name_active ON periods (name) WHERE active = true;

-- Pilares
ALTER TABLE pilares DROP CONSTRAINT pilares_name_key;
CREATE UNIQUE INDEX idx_pilares_name_active ON pilares (name) WHERE active = true;

-- Iniciativas
ALTER TABLE iniciativas DROP CONSTRAINT iniciativas_name_key;
CREATE UNIQUE INDEX idx_iniciativas_name_active ON iniciativas (name) WHERE active = true;

-- Users (email)
ALTER TABLE users DROP CONSTRAINT users_email_key;
CREATE UNIQUE INDEX idx_users_email_active ON users (email) WHERE active = true;
