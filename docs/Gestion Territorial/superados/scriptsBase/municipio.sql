-- Table: municipio

-- DROP TABLE municipio;

CREATE TABLE municipio
(
  id serial NOT NULL,
  nombre character varying(255),
  departamento_id bigint,
  provincia_id bigint,
  adminentidad_id bigint,
  CONSTRAINT municipio_pkey PRIMARY KEY (id),
  CONSTRAINT fk_municipio_adminentidad_id FOREIGN KEY (adminentidad_id)
      REFERENCES adminentidad (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_municipio_departamento_id FOREIGN KEY (departamento_id)
      REFERENCES departamento (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_municipio_provincia_id FOREIGN KEY (provincia_id)
      REFERENCES provincia (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE municipio
  OWNER TO postgres;