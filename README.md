# Tablero Online seguro y stateless
En este proyecto se realizó un tablero online, en el cual se implementaron protocolos de seguridad, autenticación y solicitud de un ticket para poderse conectar al webSocket,
además es un proyecto totalmente stateless, el cual guarda todo en un cache en redisLab.

## Getting Started

Para hacer uso del proyecto solo debe clonar el repositorio o descargarlo directamente, para ejectarlo lo hacemos por medio de la consola usando el siguiente comando.

```bash
mvn exec:java -Dexec.mainClass="co.edu.escuelaing.interactivebalckboardlife.BBAppStarter"
```

O puede jugar directamente desde la siguiente dirección:

https://fierce-bastion-32263.herokuapp.com/

### Prerequisites

Para usar el proyecto se necesitan tener las siguientes versiones de java o superior:

```
java version "1.8"
javac version "1.8"
Apache Maven 3.6.3
```

### Installing

Para usar el proyecto decargamos directamente el proyecto o lo clonamos de la siguiente manera:

En la consola nos vamos al directorio donde vamos a clonar el repositorio y ponemos el siguiente comando:

```bash
git clone https://github.com/anfegoca/ARSW-lab8.git

```
![clone](https://github.com/anfegoca/ARSW-lab8/blob/master/resources/1.png)

En el direcctorio nos quedará la carpeta del proyecto y dentro veremos las siguiente carpetas

![direc](https://github.com/anfegoca/ARSW-lab8/blob/master/resources/2.png)

Para modificar abrimos el proyecto con cualquier editor, en este caso usamos Visual Studio Code y ya podemos modificar cualquier clase del proyecto

![edit](https://github.com/anfegoca/ARSW-lab8/blob/master/resources/3.png)


## Running the tests

Para ver el funcionamiento, ejecutamos el programa, abrimos la siguiente pagina y ya podremos usar la aplicación

http://localhost:8080/

Se le pedirá al usuario autenticarse, para autenticarse puede usar los sisguientes dos usuarios.
* user - password
* user2 - password

![class](https://github.com/anfegoca/ARSW-lab8/blob/master/resources/4.png)

Una vez autenticado podrá ir a la dirección http://localhost:8080/bb2.html para usar el teclado

![class](https://github.com/anfegoca/ARSW-lab8/blob/master/resources/5.png)

![class](https://github.com/anfegoca/ARSW-lab8/blob/master/resources/6.png)

Ahora comprobaremos que si por alguna razón se llega a caer la aplicación los datos de la autenticación persistan en redis y cuando la aplicación vuelva a funcionar el usuario no
tenga que autenticarse otra vez

Detenemos la aplicación

![class](https://github.com/anfegoca/ARSW-lab8/blob/master/resources/7.png)

Ahora la volvemos a iniciar

![class](https://github.com/anfegoca/ARSW-lab8/blob/master/resources/8.png)

Iniciamos la aplicación en otro cliente y vemos que hay interacción entre ambos

![class](https://github.com/anfegoca/ARSW-lab8/blob/master/resources/9.png)

En el caso que la aplicación deje de funcionar tambien se guardará la información de las sesiones anteriores


Tambien podemos usar la aplicación usando la siguiente dirección

https://fierce-bastion-32263.herokuapp.com/


## Built With

* [Maven](https://maven.apache.org/) - Dependency Management
* [Spring](https://spring.io/projects/spring-boot) - Framework


## Versioning

El versionamiento se realizó a través de [github](https://github.com/anfegoca/ARSW-lab8.git)

## Authors

* **Andrés González** - [anfegoca](https://github.com/anfegoca)


## License

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details

## Design

![class](https://github.com/anfegoca/ARSW-Lab8/blob/master/resources/Class%20Diagram0.png)

Cuando un cliente se conecta pide un ticket al **DrawingServiceController**, este se lo solicita a **TicketRepository**, este genera un ticket aleatorio y lo guarda en redis, cuando
el cliente tiene el ticket lo envia para poderse conectar al webSocket, el **BBEndpoint** tiene injectado el **TicketRepository** y **SessionRepository**, valida que el ticket sea el
mismo dado por el repositorio y si coincide abre el web socket y agrega la sesion al **SessionRepository**, el cual tambien guarda las sesiones en redis.

## JavaDoc

La documentación del proyecto la puede encontrar [aquí](https://github.com/anfegoca/ARSW-lab8/tree/master/site/apidocs)
