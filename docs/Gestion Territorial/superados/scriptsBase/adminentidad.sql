-- Table: adminentidad

-- DROP TABLE adminentidad;

CREATE TABLE adminentidad
(
  id serial NOT NULL,
  fechaalta date NOT NULL,
  fechabaja date,
  fechamodif date,
  habilitado boolean,
  usalta_id bigint NOT NULL,
  usbaja_id bigint,
  usmodif_id bigint,
  CONSTRAINT adminentidad_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE adminentidad
  OWNER TO postgres;
