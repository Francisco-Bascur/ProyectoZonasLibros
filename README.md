Proyecto Zonas Libros

Este proyecto está compuesto por dos desarrollos:
BibliotecaBackend (Spring Boot): Microservicio encargado de gestionar libros mediante una API REST.
Zonas Libros App (Android – Kotlin Compose): Aplicación móvil que consume el backend para mostrar, crear, editar y eliminar libros.
Ambos forman parte de una solución completa orientada a demostrar integración entre frontend móvil y servicios web, aplicando buenas prácticas de desarrollo, arquitectura y pruebas.
1. Objetivo General
Construir una aplicación móvil funcional conectada a un servicio backend propio, que permita realizar un CRUD de libros, validar flujos de interacción, aplicar pruebas unitarias y generar un APK firmado listo para distribución.
2. Arquitectura General
La arquitectura está dividida en dos capas:
A. Backend – BibliotecaBackend
Implementado con Spring Boot.
Expone un conjunto de endpoints REST.
Gestiona datos de libros en una base H2 en memoria.
Permite operaciones CRUD completas.
Es consumido directamente por la app Android.
B. Frontend – App Android Zonas Libros
Desarrollada en Kotlin + Jetpack Compose.
Usa MVVM para gestionar estado y lógica.
Utiliza Retrofit para comunicarse con el backend.
Incluye pruebas unitarias del ViewModel.
Genera APK firmado en modo Release.
3. Backend: BibliotecaBackend
Tecnologías
Java 17
Spring Boot 3+
Spring Web
Spring Data JPA
H2 Database
Maven
Endpoints Disponibles
Método	Ruta	Descripción
GET	/libros	Obtiene todos los libros
POST	/libros	Crea un nuevo libro
PUT	/libros/{id}	Actualiza un libro existente
DELETE	/libros/{id}	Elimina un libro
Ejecución
mvn spring-boot:run
API disponible en:
http://localhost:8081
Consola H2:
http://localhost:8081/h2-console
4. App Android: Zonas Libros
Tecnologías
Kotlin
Jetpack Compose
MVVM
Retrofit + Gson
Coroutines
JUnit para pruebas unitarias
Funcionalidades Implementadas
Inicio de sesión y navegación básica.
Pantalla Home con lista de libros obtenida desde el backend.
Crear libros utilizando POST al backend.
Editar libros usando PUT.
Eliminar libros usando DELETE.
Vista de agregar y vista de editar.
Animaciones y diseño usando Compose.
Pruebas unitarias de ViewModel con Fake API Service.
APK firmado en modo Release para entrega formal.
Comunicación con Backend
La app utiliza Retrofit configurado así:
BASE_URL = "http://10.0.2.2:8081/"
10.0.2.2 permite a Android Studio comunicarse con el backend local desde el emulador.
5. Pruebas Unitarias
Backend
Las operaciones CRUD se validaron mediante solicitudes en Postman.
Android
Se implementaron pruebas unitarias del ViewModel, evaluando:
Carga inicial de libros con API falsa.
Creación de libros.
Actualización de libros.
Eliminación de libros.
Estas pruebas utilizan Dispatchers.setMain, un fake API y el Rule de coroutines.
6. Generación del APK
La aplicación móvil fue firmada y compilada en variante Release utilizando Android Studio.
El archivo final se encuentra en:
app/release/app-release.apk
Este APK está listo para entrega o instalación en dispositivos.
7. Estructura de Repositorios
 una aplicación móvil moderna utilizando MVVM y Jetpack Compose. Se implementaron operaciones     
