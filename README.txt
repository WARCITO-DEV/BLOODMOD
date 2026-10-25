BLOOD REAPER - mod para NeoForge 26.2 (Minecraft 26.2)

REQUISITOS
- JDK 25 instalado
- Gradle: copia de la plantilla oficial MDK-26.2-NeoGradle (github.com/NeoForgeMDKs) las
  carpetas/archivos:  gradlew, gradlew.bat y la carpeta gradle/  dentro de este proyecto
  (o, si tienes Gradle instalado, ejecuta una vez:  gradle wrapper)

COMPILAR
  Windows:  gradlew.bat build
  Linux/Mac: ./gradlew build
El .jar queda en build/libs/bloodmod-1.0.0.jar  -> ponlo en la carpeta mods de tu NeoForge 26.2.

PROBAR SIN INSTALAR:  gradlew runClient

USO EN EL JUEGO
- Creativo > pestaña Combate > "Arma de Sangre"  (o /give @s bloodmod:arma_sangre)
- Shift + clic derecho: cambia Katana -> Guadaña -> Arco
- Arco: mantén clic derecho para cargar (drena vida), suelta para disparar
