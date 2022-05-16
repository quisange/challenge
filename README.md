# Challange Backend Java

Proyecto con una arquitectura de microservicios, que realiza el manejo de clientes, cuentas y movimientos del tipo debitos y creditos entre ellos.


## Comenzando 🚀

_Estas instrucciones te permitirán obtener una copia del proyecto en funcionamiento en tu máquina local para propósitos de desarrollo y pruebas._

- Desde la raiz de este proyecto, que es donde se encuentra este archivo, se puede descargar en formato ZIP todo lo que contiene el mismo.

- Una vez descargado, debe ser descomprimido en un directorio cualquiera dentro del equipo.

- Tambien se puede clonar utilizando la direccion: https://github.com/quisange/challenge.git, a un directorio dentro del equipo de igual forma.

- El proyecto una vez abierto cuenta con 5 microservicios, 3 de ellos son propios del negocio (account, customer y transaction) y 2 nos sirven de configuracion (config-server y eureka), estan localizados bajo la siguiente ruta respectivamente:
    /challenge-main/servicesparent/businessdomain/
    /challenge-main/servicesparent/infraestructuredomain/

Mira **Despliegue** para conocer como desplegar el proyecto.


### Pre-requisitos 📋

_Que cosas necesitas para instalar el software y como instalarlas_


Contar con un equipo que tenga un sistema operativo Linux
```
El programa esta disenado para funcionar en un ambiente de Linux debido a la configuracion de los directorios, sobre los que esta creado el ambiente de ejecucion.
```

Contar con maven y java 8 o superior para la compilacion de los diferentes modulos.


Tener instalado tanto docker como docker-compose
```
Es el ambiente virtual sobre el que se corre la solucion planteada.
```


### Instalación 🔧

_Una serie de ejemplos paso a paso que te dice lo que debes ejecutar para tener un entorno de desarrollo ejecutandose_

- Ingresar a la ruta del pom de cada microservicio, tanto los de negocio como los de infraestructura: 
**cd ruta_especifica**
```
cd Descargas/challenge-main/servicesparent/businessdomain/account/
```

Crear las imagenes de docker de cada uno de los microservicios utilizados bajo el comando: 
**mvn clean install -Dmaven.test.skip**
```
cd Descargas/challenge-main/servicesparent/businessdomain/account/
mvn clean install -Dmaven.test.skip
```

Se repite las instrucciones anteriores para los cinco microservicios que maneja el proyecto:
```
cd Descargas/challenge-main/servicesparent/businessdomain/account/
mvn clean install -Dmaven.test.skip

cd Descargas/challenge-main/servicesparent/businessdomain/customer/
mvn clean install -Dmaven.test.skip

cd Descargas/challenge-main/servicesparent/businessdomain/transaction/
mvn clean install -Dmaven.test.skip

cd Descargas/challenge-main/servicesparent/infraestructuredomain/config-server
mvn clean install -Dmaven.test.skip

cd Descargas/challenge-main/servicesparent/infraestructuredomain/eurekaserver/
mvn clean install -Dmaven.test.skip
```

Al finalizar la compilacion de cada imagen nos dara un error, el mismo se produce porque se intenta cargar la imagen en un repositorio publico, sin embargo no existe dicho repositorio, el error no influye sin embargo en la correcta ejecucion del programa.

Finalmente para validar que todas las imagenes se crearon de manera correcta, podemos listar las imagenes creadas
**docker images**
```
develcorp/microservice-docker-customer
develcorp/microservice-docker-account
develcorp/microservice-docker-transaction
develcorp/microservice-docker-config-server
develcorp/microservice-docker-eurekaserver
```


## Despliegue 📦

Para desplegar finalmente el proyecto utilizamos docker compose, de forma que se ejecute de manera conjunta toda la orquestacion, esto lo realizamos dentro de la carpeta raiz del proyecto Descargas/challenge-main/servicesparent

**docker-compose up -d**
```
Starting id-eureka ... done
Starting postgres  ... done
Starting id-config ... done
Starting id-transaction ... done
Starting id-account     ... done
Starting id-customer    ... done
```

Despues de unos 20 segundos del despliegue podemos ver que servicios se han levantado 
**docker ps**
```
CONTAINER ID   IMAGE                                                        COMMAND                  CREATED        STATUS         PORTS                                       NAMES
305d24e40756   develcorp/microservice-docker-config-server:0.0.1-SNAPSHOT   "sh -c 'java $JAVA_O…"   19 hours ago   Up 2 minutes   0.0.0.0:9002->9002/tcp, :::9002->9002/tcp   id-config
00f363a55f78   postgres:latest                                              "docker-entrypoint.s…"   19 hours ago   Up 2 minutes   0.0.0.0:3432->5432/tcp, :::3432->5432/tcp   postgres
c977e280a7dc   develcorp/microservice-docker-eurekaserver:0.0.1-SNAPSHOT    "sh -c 'java $JAVA_O…"   4 days ago     Up 2 minutes   0.0.0.0:9000->9000/tcp, :::9000->9000/tcp   id-eureka
```

De las seis images levantadas podemos observar que unicamente tres se han quedado arriba, esto debido a la demora en la inicializacion de Eureka, necesaria para la ejecucion de los microservicios del negocio, por ello necesitamos levantarlos de forma posterior
**docker-compose start**
```
Starting postgres_db        ... done
Starting registry-discovery ... done
Starting configuration      ... done
Starting customer           ... done
Starting product-catalog    ... done
Starting transactions       ... done
```

Mediante un log al docker podemos observar que el microservicio se este levantando correctamente
**docker logs -f id-customer**
```
2022-05-14 16:12:57.295  INFO 1 --- [           main] o.d.s.customer.CustomerApplication       : Started CustomerApplication in 58.55 seconds (JVM running for 61.515)
```

Validamos nuevamente que esten levantadas todas las imagenes
**docker ps**
```
CONTAINER ID   IMAGE                                                        COMMAND                  CREATED        STATUS         PORTS                                       NAMES
CONTAINER ID   IMAGE                                                        COMMAND                  CREATED        STATUS         PORTS                                       NAMES
f2ab4cb05668   develcorp/microservice-docker-customer:0.0.1-SNAPSHOT        "sh -c 'java $JAVA_O…"   19 hours ago   Up 2 minutes   0.0.0.0:8081->8081/tcp, :::8081->8081/tcp   id-customer
79fa82d3d5c4   develcorp/microservice-docker-transaction:0.0.1-SNAPSHOT     "sh -c 'java $JAVA_O…"   19 hours ago   Up 2 minutes   0.0.0.0:8083->8083/tcp, :::8083->8083/tcp   id-transaction
736ab696d2c4   develcorp/microservice-docker-account:0.0.1-SNAPSHOT         "sh -c 'java $JAVA_O…"   19 hours ago   Up 2 minutes   0.0.0.0:8082->8082/tcp, :::8082->8082/tcp   id-account
305d24e40756   develcorp/microservice-docker-config-server:0.0.1-SNAPSHOT   "sh -c 'java $JAVA_O…"   19 hours ago   Up 6 minutes   0.0.0.0:9002->9002/tcp, :::9002->9002/tcp   id-config
00f363a55f78   postgres:latest                                              "docker-entrypoint.s…"   19 hours ago   Up 6 minutes   0.0.0.0:3432->5432/tcp, :::3432->5432/tcp   postgres
c977e280a7dc   develcorp/microservice-docker-eurekaserver:0.0.1-SNAPSHOT    "sh -c 'java $JAVA_O…"   4 days ago     Up 6 minutes   0.0.0.0:9000->9000/tcp, :::9000->9000/tcp   id-eureka
```


## Ejecutando las pruebas ⚙️

En primer lugar debemos validar que los servicios de configuracion estes desplegados correctamente, esto lo podemos hacer a traves de un navegador web en el sitio de cada servicio
http://localhost:9000/
http://localhost:9002/service-account/devel, con el usuario develcorp y la clave develcorppass.

Posterior a esta validacion podemos ejecutar ya pruebas de los microservicios de negocio a traves de Postman

```
Creacion de un cliente

localhost:8081/clientes
{
    "name": "Pedro Orlando",
    "gender": "Masculino",
    "age": 27,
    "identification": "0105847364",
    "address": "Autopista",
    "phone": "0903994124",
    "password": "5683",
    "status": true
}
```


```
Listado de clientes
http://localhost:8081/clientes

[
    {
        "personId": 1,
        "name": "Jose Lema",
        "gender": "Masculino",
        "age": 43,
        "identification": "0194827364",
        "address": "Otavalo sn y principal ",
        "phone": "098254785",
        "password": "1234",
        "status": true,
        "accounts": null
    },
    {
        "personId": 2,
        "name": "Marianela Montalvo",
        "gender": "Femenino",
        "age": 22,
        "identification": "0194872746",
        "address": "Amazonas y NNUU",
        "phone": "097548965",
        "password": "5678",
        "status": true,
        "accounts": null
    },
    {
        "personId": 3,
        "name": "Juan Osorio",
        "gender": "Masculino",
        "age": 67,
        "identification": "0192837465",
        "address": "13 junio y Equinoccial",
        "phone": "098874587",
        "password": "1245",
        "status": true,
        "accounts": null
    }
]
```

```
Creacion de una cuenta
localhost:8082/cuentas

{
    "accountId": 632930,
    "customerId": 2,
    "accountType": {
                    "accountTypeId": 2,
                    "description": "Ahorros"
                },            
    "initialBalance": 23
}
```

```
Listado de cuentas por id
localhost:8082/cuentas/496825

{
    "accountId": 496825,
    "customerId": 2,
    "accountType": {
        "accountTypeId": 2,
        "description": "Ahorros"
    },
    "initialBalance": 540.00,
    "status": true,
    "transactions": [
        {
            "transactionId": 5,
            "accountId": 496825,
            "date": "2022-05-08",
            "transactionType": "Apertura",
            "value": 540.0,
            "balance": 540.0,
            "status": true,
            "message": "Apertura de 540"
        },
        {
            "transactionId": 9,
            "accountId": 496825,
            "date": "2022-05-08",
            "transactionType": "Retiro",
            "value": 540.0,
            "balance": 0.0,
            "status": true,
            "message": "Retiro de 540"
        }
    ]
}
```

```
Creacion de un movimiento
localhost:8083/movimientos

{
    "accountId": 478758,
    "date": "2022-05-09",
    "transactionType": "Retiro",
    "value": "10"
}
```

```
Creacion 
localhost:8082/cuentas/reporte?cliente=1&desde=2022-05-08&hasta=2022-05-12

[
    {
        "date": "2022-05-08T00:00:00.000+00:00",
        "customer": "Jose Lema",
        "accountNumber": 585545,
        "accountType": "Corriente",
        "initialBalance": 2000.00,
        "status": true,
        "transactionValue": 2000.0,
        "transactionBalance": 2000.0
    },
    {
        "date": "2022-05-08T00:00:00.000+00:00",
        "customer": "Jose Lema",
        "accountNumber": 478758,
        "accountType": "Ahorros",
        "initialBalance": 1000.00,
        "status": true,
        "transactionValue": 1000.0,
        "transactionBalance": 1000.0
    },
    {
        "date": "2022-05-08T00:00:00.000+00:00",
        "customer": "Jose Lema",
        "accountNumber": 478758,
        "accountType": "Ahorros",
        "initialBalance": 1000.00,
        "status": true,
        "transactionValue": 575.0,
        "transactionBalance": 425.0
    }
]
```


## Construido con 🛠️

_Menciona las herramientas que utilizaste para crear tu proyecto_

* [Spring](https://spring.io/) - El framework web usado
* [Maven](https://maven.apache.org/) - Manejador de dependencias
* [PostgreSQL](https://www.postgresql.org/) - Manejador de dependencias
* [Docker](https://www.docker.com/) - Manejador de dependencias
* [Postman](https://www.postman.com/) - Usado para generar RSS

## Versionado 📌

Usamos [Git](https://git-scm.com/) para el versionado. 


## Autores ✒️

_Menciona a todos aquellos que ayudaron a levantar el proyecto desde sus inicios_

* **Angelica Pinos** - *Desarrollo completo* - [quisange](https://github.com/quisange/)
