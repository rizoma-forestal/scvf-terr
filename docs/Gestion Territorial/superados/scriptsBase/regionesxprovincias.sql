-- Table: regionesxprovincias

-- DROP TABLE regionesxprovincias;

CREATE TABLE regionesxprovincias
(
  provincia_fk bigint NOT NULL,
  region_fk bigint NOT NULL,
  CONSTRAINT regionesxprovincias_pkey PRIMARY KEY (provincia_fk, region_fk),
  CONSTRAINT fk_regionesxprovincias_provincia_fk FOREIGN KEY (provincia_fk)
      REFERENCES provincia (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_regionesxprovincias_region_fk FOREIGN KEY (region_fk)
      REFERENCES region (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE regionesxprovincias
  OWNER TO postgres;