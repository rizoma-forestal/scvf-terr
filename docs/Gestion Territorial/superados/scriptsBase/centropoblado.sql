-- Table: centropoblado

-- DROP TABLE centropoblado;

CREATE TABLE centropoblado
(
  id serial NOT NULL,
  nombre character varying(255),
  centropobladotipo_id bigint,
  departamento_id bigint,
  adminentidad_id bigint,
  CONSTRAINT centropoblado_pkey PRIMARY KEY (id),
  CONSTRAINT fk_centropoblado_adminentidad_id FOREIGN KEY (adminentidad_id)
      REFERENCES adminentidad (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_centropoblado_centropobladotipo_id FOREIGN KEY (centropobladotipo_id)
      REFERENCES centropobladotipo (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_centropoblado_departamento_id FOREIGN KEY (departamento_id)
      REFERENCES departamento (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE centropoblado
  OWNER TO postgres;
