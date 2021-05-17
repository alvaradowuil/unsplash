# Unsplash Test
Este proyecto utiliza el servicio API de unsplasy https://unsplash.com/.
Especificamente utiliza los servicios para
- Obtener lista de fotos en bloques de 10 en 10
- Obtener información de una foto
- Obtener información de usuario

En la aplicación puedes guardar fotos como favoritos. Esta se guarda localmente con Room 

- Guarda información de la foto, datos de la cámara y su configuración, links, usuario, links de foto de usuario, links de información del usuario. Esto en diferentes tablas relacionadas. 
Es posible eliminar una foto de favorito, en esta acción hace eliminación en cascada.

Para desarrollar esta aplicación se utilizaron las siguientes herramientas / tecnologías
- [MVVM](https://developer.android.com/jetpack/guide?hl=es-419)
- [Coroutines](https://developer.android.com/kotlin/coroutines?gclid=Cj0KCQjwvr6EBhDOARIsAPpqUPGeGr-bwb6gtn3A4I-k5mqHpO7j3LsRqy2zPeKC2xVRcU4Rce_BFwIaAkZ_EALw_wcB&gclsrc=aw.ds)
- [Retrofit](https://github.com/square/retrofit)
- [Room](https://developer.android.com/jetpack/androidx/releases/room)
- [Glide](https://github.com/bumptech/glide)
- [Bottom Navigation](https://material.io/components/bottom-navigation/android#using-bottom-navigation)
- [SearchView](https://developer.android.com/reference/androidx/appcompat/widget/SearchView)
- [ViewBinding](https://developer.android.com/topic/libraries/view-binding?hl=es-419)
- [LiveData](https://developer.android.com/reference/androidx/lifecycle/LiveData)

[Descargar apk de prueba](https://drive.google.com/file/d/11vx_xsfuyUkQLHjQnTwgNLq4r4uWGyks/view?usp=sharing)
