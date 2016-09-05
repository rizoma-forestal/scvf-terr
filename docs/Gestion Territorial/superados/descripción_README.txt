Descripci�n de la aplicaci�n:
-----------------------------

Esta aplicaci�n gestiona la informaci�n correspondiente a la organizaci�n territorial de nuestro pa�s. Contiene una API de servicios web para ser consumidos tanto por apliaciones internas como externas, de manera de proveer un registro �nico de Provincias, departamientos, localidades, municipios y regiones tem�ticas.

Se preve proximamente desacoplear el backend en un m�dulo EJB que proporcione la API de servicios web y una fachada que ofresca los mismos m�todos para consumo mediante JNDI.

La acreditaci�n de esta aplicaci�n es gestionada mediante el servicio brindado por gestionAplicaciones.

Disitingue dos roles de usuarios, el administrador que gestiona los contenidos y el seguridad, que gestiona usuarios y roles. Para asignar usuarios a la aplicaci�n, previamente deben ser vinculados mediante gestionAplicaciones.	

Para m�s datos se recomienda acceder a la documentaci�n de la aplicaci�n en \\vmfs\Desarrollo\Servicios\Gestion Territorial


Ambiente:
---------

Para conocer las caracter�sticas del ambiente para el despliegue de la aplicaci�n se recomiendo leer la documentaci�n alojada en \\vmfs\Desarrollo\Aplicaciones\javaApp

Dado que la aplicaci�n requiere el servicio de acreditaci�n de usuarios de gestionAplicaciones, es condici�n para el ambiente local de esta aplicaci�n tener desplegada previamente gestionAplicaciones. Una vez levantado el proyecto en el IDE, con gestionAplicaciones despleagada, deber� actualizarse la referencia a http://localhost:8080/AccesoAppWebService/AccesoAppWebService?WSDL para que se creen las entidades necesarias para el consumo del servicio.


Configuraciones:
----------------

Dado que todas las dependencias de las aplicaciones desarrolladas en java est�n gestionadas por Maven, una vez levantado el proyecto, deber� actualizarse todas las dependencias mediante el comando respectivo del IDE para que pueda compilar sin inconvenientes.

Deber� generarse el recurso JDBC correspondiente al acceso a datos, seg�n los jndi-name que se especifican en el archivo glassfish-resources.xml. El jta-data-source de la unidad de persistencia que se encuentra en el archivo persistence.xml deber� ser centrosPobladosNDI

Las credenciales de acceso al servidor de base de datos, por defecto ser�n: us=postgres, pass=root, en cualquier otro caso, estos datos deber�n sobreescribirse en el archivo glassfish-resource.xml.

El archivo Bundle.properties, contiene los datos de server, la ruta de la aplicaci�n para autenticar y nombres de las cookies a leer.


Datos:
------

Deber� crearse la base de datos gestionTerritorial en el servidor local de Postgres y los permisos seg�n se especifica en el archivo glassfish-resource.xml
	
Los backup de la base se encuentran en \\vmfs\Desarrollo\Servicios\Gestion Territorial\bkBase y los scripts en \\vmfs\Desarrollo\Servicios\Gestion Territorial\scriptsBase
	
Se recomienda crear, luego de la base, primero las tablas y luego las restricciones de cada una.


Servicios brindados:
--------------------
	
Los archivos xml correspondientes al contrato del servicio web que brinda la aplicaci�n para gestionar el logeo de otras aplicaciones del mismo entorno, se encuentran en \\vmfs\Desarrollo\Servicios\Gestion Territorial\contratoServicios


Servicios consumidos:
---------------------
	
Como se antici�, la aplicaci�n consume los servicios de acreditaci�n de usuarios de gestionAplicaciones.
	