-- Table: centropobladotipo

-- DROP TABLE centropobladotipo;

CREATE TABLE centropobladotipo
(
  id serial NOT NULL,
  nombre character varying(255),
  adminentidad_id bigint,
  CONSTRAINT centropobladotipo_pkey PRIMARY KEY (id),
  CONSTRAINT fk_centropobladotipo_adminentidad_id FOREIGN KEY (adminentidad_id)
      REFERENCES adminentidad (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE centropobladotipo
  OWNER TO postgres;