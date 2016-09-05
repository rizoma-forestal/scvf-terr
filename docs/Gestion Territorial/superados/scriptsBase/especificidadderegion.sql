-- Table: especificidadderegion

-- DROP TABLE especificidadderegion;

CREATE TABLE especificidadderegion
(
  id serial NOT NULL,
  nombre character varying(255),
  adminentidad_id bigint,
  CONSTRAINT especificidadderegion_pkey PRIMARY KEY (id),
  CONSTRAINT fk_especificidadderegion_adminentidad_id FOREIGN KEY (adminentidad_id)
      REFERENCES adminentidad (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE especificidadderegion
  OWNER TO postgres;