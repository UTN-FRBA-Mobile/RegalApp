# RegalApp
¿Querés crear una lista de regalos para tu fiesta, para que tus invitados puedan seleccionar algo de ahí sin pisarse entre ellos?
¿Alguna vez te pasó de organizar una reunión y que 2 personas traigan lo mismo por la mala comunicación?
¿o qué haya faltado algo porque todos pensaron que lo traia otra persona?


Con RegalApp no vas a tener mas este problema.  Esta aplicación te va a permitir crear un evento, agregar la lista de items para llevar poniendo precio, cantidad y hasta donde se puede comprar, e invitar a todos los que quieras para que puedan marcar que cosas se compraron y saber que falta.
Tambien te podemos notificar cuando algo se compro, para que todos puedan saber el estado del evento y poder contribuir mas fácil

<img width="354" alt="image" src="https://user-images.githubusercontent.com/15114011/204348021-911072fe-39a9-4f7d-bea6-0b6d84643c94.png">

<img width="351" alt="image" src="https://user-images.githubusercontent.com/15114011/204348603-035e173d-b81b-4f31-bfb6-d2fb8e16f3d1.png">

<img width="350" alt="image" src="https://user-images.githubusercontent.com/15114011/204348473-c61e4eaf-d06a-4936-9958-424bfb37bd8f.png">



<img width="356" alt="image" src="https://user-images.githubusercontent.com/15114011/204348934-1b0fed0a-7937-4e48-989b-d0c4a1c98c4a.png">

<img width="355" alt="image" src="https://user-images.githubusercontent.com/15114011/204348974-1c1db04d-cdca-40be-8b26-d1bb8ce0ab07.png">

---
## Consideraciones técnicas

### API GMAPS
Para correr la aplicación en el simulador de Android se tiene que configurar el archivo `local.properties` con una clave (pedir las claves al líder de proyecto)

> GEO_API_KEY=XXXXXXXXXXXX

### Deeplink invitaciones
Como de momento no poseemos un dominio https disponible para la aplicación vamos a utilizar la funcionalidad de deeplink que nos provee el android estudio, por lo tanto los links para compartir eventos los vamos a utilizar a través el emulador.

<img width="300" src="https://user-images.githubusercontent.com/11763032/204404826-d56bd2eb-2e5d-431d-8db5-8b069466f1ea.gif">


#### Configuración en Android Studio
<img alt="deeplink-config" src="https://user-images.githubusercontent.com/11763032/204404356-51f2cece-1874-4be3-bf65-a47145ad1393.png">
