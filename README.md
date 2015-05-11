# PrestoBus
Aplicación desarrollada para la asignatura de Programación de Aplicaciones para Dispositivos Móviles de FDI (UCM).

Está desarollada para Android 4.1 o superior y usa el tema Material introducido en Android Lollipop.
Consulta la API de la [EMT](http://opendata.emtmadrid.es/) para obtener los tiempos reales de espera de los autobuses en tus paradas favoritas.

## Instalación
Para instalar PrestoBus, simplemente puedes bajar el [apk](PrestoBus.apk) y proceder normalmente. Tienes que tener activada la opción para instalar contenido que no proceda de la Play Store como se indica por ejemplo en la web de [amazon](https://www.amazon.es/gp/help/customer/display.html?nodeId=201482620). 
Requiere permisos para acceder a internet, pues necesita consultar la API de la EMT y también te muestra un mapa de Google con la localización de la parada.

## Configurar el proyecto
En caso de que quieras probar el proyecto y compilarlo localmente, hemos usado Android Studio, y hay dos campos que debes rellenar, después de registrarte en la [API de la EMT](http://opendata.emtmadrid.es/Formulario).
Estos campos están en la clase 'Rest' en el paquete 'utilidades' y son el String 'idClientValue' y 'passKeyValue'.

## Creditos
Aparte de la API REST de la EMT, hemos usado las siguientes librerias:
Para hacer peticiones http [Volley](https://github.com/mcxiaoke/android-volley).
Para convertir los objeto JSON a clases Java utilizamos [jackson](https://github.com/FasterXML/jackson).
Para poner en mayúsculas la primera letra de cada palabra y lo demás en minúsculas [commons.apache.org](https://commons.apache.org/proper/commons-lang/).
Como teníamos que hacer una WebView para cumplir con los requisitos del proyecto usamos [Bootstrap](http://getbootstrap.com/) y [jQuery](https://jquery.com/).

## Media
![imagen](media/PrestoBus%20(6).png)
### [Video con la demo de la aplicación](media/PrestoBus.mp4)
Hay mas imagenes en la carpeta [media](media).
