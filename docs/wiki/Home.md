# Proyecto TPV. Tecnología Spring
#### Back-end con Tecnologías de Código Abierto (SPRING). [Máster en Ingeniería Web por la U.P.M.](http://miw.etsisi.upm.es)
Este proyecto es la práctica de Spring desarrollada de forma colaborativa por todos los alumnos. Se parte de la versión `core`, ya implementada, y se pretende ampliar con un conjunto de mejoras.

##Introducción
Un **T**erminal **P**unto de **V**enta es un sistema informático que gestiona el proceso de venta mediante una interfaz accesible para los vendedores o compradores.

Un único sistema informático permite la creación e impresión del recibo ticket o factura de venta —con los detalles de las referencias y precios— de los artículos vendidos, actualiza los cambios en el nivel de existencias de mercancías (STOCK) en la base de datos...

##Metodología de trabajo
A cada alumno se le asignará un conjunto de ampliaciones, se referenciarán por `feature-xx`. Se realizará mediante un ticket.

El alumno deberá realizar las siguientes fases:

###Fase 1. Preparar el IDE
* Utilización de espacios en lugar de tabuladores

 > Mediante el menú: `>>Windows > Preference`, en la sección: `General > Editors > Text Editors`, se activa la casilla: `Insert spaces for tabs`

* Codificación por defecto: UTF-8
 > Para configurar el workspace, menú: `>>Windows > Preferences`, en la sección: `General > Workspace`, en el apartado `Text file encoding`, se activa la opción: `Other: UTF-8`.
 > Para configurar la apariencia, menú: `>>Windows > Preferences`, en la sección: `General > Appearance > Content types`, en la sección de `Text`, en el apartado de `Default Encoding`, establecer `UTF-8` y pulsar el botón de `Update`

* Formateador de código

 > Es posible configurar Eclipse para que formatee automáticamente el código de forma que cumpla el máximo número posible de objetivos de las recomendaciones. Menú: `>>Windows > Preferences`, en la sección `Java > Code Style > Formatter`, pulsar el botón de `Import...` y seleccionar el fichero `MIW_formater.xml`, se podrá localizar en la carpeta `docs` del proyecto

* Limpieza de código
 > Además del formateador es posible configurar Eclipse para que al hacer una limpieza de código este haga los cambios de acuerdo con los objetivos de las recomendaciones. Menú: `>>Windows > Preferences`, en la sección `Java > Code Style > Clean up`, pulsar el botón de `Import...` y seleccionar el fichero `MIW_cleanup.xml`, se podrá localizar en la carpeta `docs`. Para realizar una limpieza, en el menú: `Source > Clean Up...`

* Habilitar los `assert` en la ejecución en el IDE
 > Mediante el menú contextual del proyecto: `>>Run As>Run configurations...`, en la pestaña `Arguments`, escribir el valor `-ea` dentro de `VM arguments:`

###Fase 2. Importar el proyecto
Importar el proyecto y comprender todo el código del mismo

###Fase 3. Documentar
Aclarar y detallar la documentación de la mejora en la Wiki.

###Fase 4. Dividir y documentar
Dividir la ampliación en un subconjunto de pequeñas ampliaciones, que puedan ser implementadas y probadas con los test necesarios. Documentar todo ello en la wiki. Cada una de ellas se referenciarán por ´feature-xx.y´. Cada una de estas sub-mejoras, deberá ir acompañada de una **estimación en _Horas/Programador_**, y cuando se finalice totalmente, **_Horas/Programador_ reales**.

###Fase 5. Implementar
Se recomienda un desarrollo `Top-down`.

Por cada sub-ampliación, crear una rama referenciada por `feature-xx.y` e implementar la mejora. Se debe tener en cuenta los siguientes aspectos:

1. Siempre, siempre y siempre, `formatear el código!!!`
1. Siempre, siempre y siempre, `realizar test!!!`. Seguir el estilo de nombres y situar sus referencias en el `All**tests` oportuno
1. Mantener la wiki actualizada con las decisiones tomadas
1. Realizar `commits` frecuentes, explicando la parte desarrollada
1. Cada vez que detectemos que la rama `develop` haya evolucionado, incorporar los cambios de la rama `develop` en nuestra rama, en un momento de nuestro desarrollo que tengamos el código estable. Se os recuerda que se realiza con nuestra rama `feature` activa, realizar un `merge` con `develop`
1. **Una vez terminada la sub-ampliación, asegurarse que se pasan todos los test adecuadamente:**
 1. `tpv.AllTests`
 1. Arrancar el servidor y ejecutar `AllFunctionalTesting`
1. Incorporar la ampliación a la rama `develop`. Se recuerda que se realiza activando la rama `develop` realizar un `merge` con nuestra rama de ampliación, se debe forzar siempre un nuevo commit para no perder la referencia de las ramas (activar la opción: `If a fast-forward,create a merge commit`).

**Cada vez que se rompa el código de `develop`... habrá una sanción de puntos en la nota.**

##Suerte chic@s!!!

