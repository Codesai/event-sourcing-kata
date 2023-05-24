# event-sourcing-kata

El objetivo de esta kata es practicar Event Sourcing a través de un ejemplo con 
código dirigido por test para familiarizarnos con esta estrategía de persistencia y 
sus implicaciones en el diseño/arquitectura de nuestro código.

El ejemplo consiste en modelar una casa de subastas online, partiremos de un modelado
de eventos donde hemos descubierto los siguientes eventos:
    
- Creación de la subasta: Descripción del item a subastar y precio inicial de partida.
- Puja: Cantidad de dinero pujada
- Cierre de la subasta: Puede ser cerrada con un ganador o haber quedado desierta

Pasos:
- Crear un auction y guardar/recuperar el auction persistiendo en un almacen de eventos
- Permitir que se pueda pujar en el auction
- Permitir que se puede cerrar la subasta
- Versionado: añadir al evento de bid el nombre del usuario que gano la puja. en este caso tendremos varias
opciones para el diseño:
  - tratar todos los eventos en el agregado
  - convertir los eventos en el repositorio y tratar sólo la ultima versión en el agregado
- Snapshots: Crear una snapshot del agregado cada N eventos, a la hora de reconstruir el agregado a 
partir de los eventos partir del spanshot y luego aplicar los eventos siguientes.

