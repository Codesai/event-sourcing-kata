# event-sourcing-kata

El objetivo de esta kata es practicar Event Sourcing a través de un ejemplo con 
código dirigido por test para familiarizarnos con esta estrategía de persistencia y 
sus implicaciones en el diseño/arquitectura de nuestro código.

El ejemplo consiste en modelar una casa de subastas online, partiremos de un modelado
de eventos donde hemos descubierto los siguientes eventos:
    
- Creación de la subasta: Descripción del item a subastar y precio inicial de partida.
- Puja: Cantidad de dinero pujada
- Cierre de la subasta: Puede ser cerrada con un ganador o haber quedado desierta


