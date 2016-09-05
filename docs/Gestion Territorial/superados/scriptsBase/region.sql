-- Table: region

-- DROP TABLE region;

CREATE TABLE region
(
  id serial NOT NULL,
  nombre character varying(255),
  especificidadderegion_id bigint,
  adminentidad_id bigint,
  CONSTRAINT region_pkey PRIMARY KEY (id),
  CONSTRAINT fk_region_adminentidad_id FOREIGN KEY (adminentidad_id)
      REFERENCES adminentidad (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_region_especificidadderegion_id FOREIGN KEY (especificidadderegion_id)
      REFERENCES especificidadderegion (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE region
  OWNER TO postgres;