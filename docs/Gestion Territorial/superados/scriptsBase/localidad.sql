-- Table: localidad

-- DROP TABLE localidad;

CREATE TABLE localidad
(
  id serial NOT NULL,
  codigopostal character varying(10) NOT NULL,
  nombre character varying(50) NOT NULL,
  CONSTRAINT localidad_pkey PRIMARY KEY (id),
  CONSTRAINT localidad_codigopostal_key UNIQUE (codigopostal),
  CONSTRAINT localidad_nombre_key UNIQUE (nombre)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE localidad
  OWNER TO postgres;