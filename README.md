Importar la colección de Postman para facilitar las pruebas:
Descargar el archivo de nombre UserManagement.postman_coleccion que adjunté en el correo.
-> abrir Postman -> File -> Import ->  File -> Upload Files -> buscar en las descargas el archivo .json previamente descargado -> Import.


Después de compilar la API, abrir la consola de h2 mediante la ruta: http://localhost:8085/h2-console/

 Usuario y contraseña: h2

Una vez dentro de la consola, ejecutar estos scripts para poder inicializar correctamente la aplicación con sus roles:

INSERT INTO rol (id, nombre) VALUES (1, 'ROLE_ADMIN');

INSERT INTO usuarios (id, edad, email, name, password, rol_id)
VALUES (1, 25, 'cardonacarlos@gmail.com', 'Carlos', 'Carlos12', 1);

Token para acceder a las rutas: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJDYXJsb3MiLCJpYXQiOjE2ODA1OTA1NTQsImV4cCI6MTY4MTE5NTM1NH0.FMXUlarBIPMlOPGPYu8LnzZvGG_uNbg9shA-fj07jDt6hAc_RKQ_sBKfteszS32CuuigOtlmmyVUU7p5S-oWBg

Recomendable comenzar por la ruta: localhost:8085/auth/register y luego ir al controlador de Usuarios para probar la API RESTful. 




