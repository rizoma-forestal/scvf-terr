gestionTerritorial
==================

Descripción de la aplicación:
-----------------------------

Esta aplicación gestiona la información correspondiente a la organización territorial de nuestro país. Contiene una API de servicios web para ser consumidos tanto por apliaciones internas como externas, de manera de proveer un registro único de Provincias, departamientos, localidades, municipios y regiones temáticas. Se implementó una nueva API de servicios REST creada especialmente para consumir su información por SACVeFor y SIIF

La acreditación de esta aplicación es gestionada mediante el servicio brindado por gestionAplicaciones.

Disitingue dos roles de usuarios, el administrador que gestiona los contenidos y el seguridad, que gestiona usuarios y roles. Para asignar usuarios a la aplicación, previamente deben ser vinculados mediante gestionAplicaciones.	


Primera versión con servicios WSDL sobre glassFish
==================================================

Ambiente:
---------

Para conocer las características del ambiente para el despliegue de la aplicación se recomienda leer la documentación alojada en `doc/entornoLocal.txt` y `doc/frameworks_tecnologias.txt`

Dado que la aplicación requiere el servicio de acreditación de usuarios de gestionAplicaciones, es condición para el ambiente local de esta aplicación tener desplegada previamente gestionAplicaciones. Una vez levantado el proyecto en el IDE, con gestionAplicaciones despleagada, deberá actualizarse la referencia a http://localhost:8080/AccesoAppWebService/AccesoAppWebService?WSDL para que se creen las entidades necesarias para el consumo del servicio.


Configuraciones:
----------------

Dado que todas las dependencias de las aplicaciones desarrolladas en java están gestionadas por Maven, una vez levantado el proyecto, deberá actualizarse todas las dependencias mediante el comando respectivo del IDE para que pueda compilar sin inconvenientes.

Deberá generarse el recurso JDBC correspondiente al acceso a datos, según los jndi-name que se especifican en el archivo glassfish-resources.xml. El jta-data-source de la unidad de persistencia que se encuentra en el archivo persistence.xml deberá ser centrosPobladosNDI

Las credenciales de acceso al servidor de base de datos, por defecto serán: us=postgres, pass=root, en cualquier otro caso, estos datos deberán sobreescribirse en el archivo glassfish-resource.xml.

El archivo Bundle.properties, contiene los datos de server, la ruta de la aplicación para autenticar y nombres de las cookies a leer.


Datos:
------

Deberá crearse la base de datos gestionTerritorial en el servidor local de Postgres y los permisos según se especifica en el archivo glassfish-resource.xml
	
Los backup de la base se encuentran en \\vmfs\Desarrollo\Servicios\Gestion Territorial\bkBase y los scripts `docs\scriptsBase`. Se recomienda crear, luego de la base, primero las tablas y luego las restricciones de cada una.

Otra alternativa es cargar el archivo gestionTerritorial.tar que contiene toda la informacion (esquema y datos)


Servicios brindados:
--------------------
	
Los archivos xml correspondientes al contrato del servicio web que brinda la aplicación para gestionar el logeo de otras aplicaciones del mismo entorno, se encuentran en `docs\Gestion Territorial\contratoServicios`


Servicios consumidos:
---------------------
	
La aplicación consume los servicios de acreditación de usuarios de gestionAplicaciones. En netbeans se ejecuta el `wsimport` automáticamente, pero si se usa Eclipse, hay que ejecutarlo manualmente, mediante el comando:


`wsimport -keep -p ar.gob.ambiente.servicios.gestionterritorial.wsExt -d src/main/java/  http://localhost:8080/AccesoAppWebService/AccesoAppWebService?WSDL`