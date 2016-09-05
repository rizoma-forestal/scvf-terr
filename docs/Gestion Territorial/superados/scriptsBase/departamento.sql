-- Table: departamento

-- DROP TABLE departamento;

CREATE TABLE departamento
(
  id serial NOT NULL,
  nombre character varying(255),
  provincia_id bigint,
  adminentidad_id bigint,
  CONSTRAINT departamento_pkey PRIMARY KEY (id),
  CONSTRAINT fk_departamento_adminentidad_id FOREIGN KEY (adminentidad_id)
      REFERENCES adminentidad (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_departamento_provincia_id FOREIGN KEY (provincia_id)
      REFERENCES provincia (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE departamento
  OWNER TO postgres;
