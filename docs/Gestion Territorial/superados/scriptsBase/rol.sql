-- Table: rol

-- DROP TABLE rol;

CREATE TABLE rol
(
  id serial NOT NULL,
  nombre character varying(255),
  adminentidad_id bigint,
  CONSTRAINT rol_pkey PRIMARY KEY (id),
  CONSTRAINT rol_nombre_key UNIQUE (nombre)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE rol
  OWNER TO postgres;