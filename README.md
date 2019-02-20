# App Sitios

Pequeña aplicación móvil para el registro de sitios (nombre, descripción, latitud, longitud), compuesta por una base de datos local, donde se guardarán los registros de los lugares creados por el usuario.
Cuenta con un pequeño backend realizado con el framework Django.

Pantalla Principal:
- Permite visualizar los sitios que se encuentran dentro de la base de datos local (Si los hay)
- Botón flotante que abre dialogo con formulario para el registro de nuevos sitios

Menú Principal:
  - Datos locales: Obtener la información sobre la cantidad de datos en la base de datos local
  - Limpiar BD Local: Permite vaciar toda la base de datos local
  - Sincronizar: Opción que realiza la carga de datos de la base de datos local, al servidor backend
  - Datos Servidor: Opción que permite la visualización de todos los sitios que se encuentran en el backend
  
 Presionar sobre un lugar (Sitios locales o del servidor):
- Ver la ubicación del lugar en el mapa haciendo uso del api de Google
- Se puede ver de dos maneras diferentes, tema claro u oscuro
- Se puede presionar en el mapa para dejar diferentes marcadores, y trazar un polígono
- Se puede limpiar las marcas
