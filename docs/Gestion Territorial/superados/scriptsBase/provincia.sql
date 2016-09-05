-- Table: provincia

-- DROP TABLE provincia;

CREATE TABLE provincia
(
  id serial NOT NULL,
  nombre character varying(255),
  adminentidad_id bigint,
  prov_id integer,
  CONSTRAINT provincia_pkey PRIMARY KEY (id),
  CONSTRAINT fk_provincia_adminentidad_id FOREIGN KEY (adminentidad_id)
      REFERENCES adminentidad (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE provincia
  OWNER TO postgres;
