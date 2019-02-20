# App Sitios

Peque�a aplicaci�n m�vil para el registro de sitios (nombre, descripci�n, latitud, longitud), compuesta por una base de datos local, donde se guardar�n los registros de los lugares creados por el usuario.
Cuenta con un peque�o backend realizado con el framework Django.

Pantalla Principal:
- Permite visualizar los sitios que se encuentran dentro de la base de datos local (Si los hay)
- Bot�n flotante que abre dialogo con formulario para el registro de nuevos sitios

Men� Principal:
  - Datos locales: Obtener la informaci�n sobre la cantidad de datos en la base de datos local
  - Limpiar BD Local: Permite vaciar toda la base de datos local
  - Sincronizar: Opci�n que realiza la carga de datos de la base de datos local, al servidor backend
  - Datos Servidor: Opci�n que permite la visualizaci�n de todos los sitios que se encuentran en el backend
  
 Presionar sobre un lugar (Sitios locales o del servidor):
- Ver la ubicaci�n del lugar en el mapa haciendo uso del api de Google
- Se puede ver de dos maneras diferentes, tema claro u oscuro
- Se puede presionar en el mapa para dejar diferentes marcadores, y trazar un pol�gono
- Se puede limpiar las marcas
