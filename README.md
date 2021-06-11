# ioBuildersPOC

## Prerrequisitos
Para poder ejecutar la prueba de concepto necesitamos:
- El IDE Eclipse.
- La versión de Java 8.
- Tener instalado el plugin de Spring en Eclipse
- Incluir el [jar de PostgreSQL](postgresql-42.2.20.jar) de la raíz del repositorio en el classpath.
- Una base de datos PostgreSQL (versión 12.7).
- El programa de Ganache (versión 2.5.4) para simular una red blockchain.
- Tener el programa Postman para realizar las peticiones a la API.
- Cambiar los datos del application.properties del artefacto walletMsLauncher.
## Cambios en el application.properties del artefacto walletMsLauncher
```
spring.datasource.url=jdbc:postgresql://<HOST>:<puerto>/<DB>
spring.datasource.username=<DB_USERNAME>
spring.datasource.password=<DB_PASSWORD>
web3j.client-address=<GANACHE_URL>
contract.addr=<direccion donde se desplego el contrato 0x.....>
contract.privateKey=<clave privada de la cuenta que desplego el contrato>
gasLimit=<limite del gas en formato entero>
gasPrice=<precio del gas en hexadecimal (sin el 0x de delante)>
```
## Pasos
1. Levantar el servidor de Ganache.
2. Desplegar el contrato inteligente en el servidor de Ganache.
3. Levantar o tener levantada la base de datos.
4. Tener cuentas en Ganache con algo de Ether.
5. Modificar los datos presentados anteriormente en el application.properties.

## Endpoints de la API
- <strong>/create</strong>
  
  Método: GET
  
  Crea un wallet con una clave privada aleatoria.
- <strong>/createWalletWithKey</strong>

  Método: POST
  Body: una cadena de caracteres con la clave privada de la cuenta.
  
  Crea un wallet con la clave privada que le pases en el cuerpo de la petición.
- <strong>/get/{id}</strong>

  Método: GET
  
  Obtiene estadísticas o datos relevantes del wallet. El id se debe reemplazar por el id de la base de datos del wallet que quieres consultar las estadísticas.
- <strong>/getContractBalance</strong>

  Método: GET
  
  Obtiene el balance del contrato (número de tokens).
- <strong>/getAllWallets</strong>

  Método: GET
  
  Obtiene todos los wallets en la base de datos y los devuelve.
- <strong>/deposit</strong>

  Método: GET
  
  Query params:
  - id: el id del wallet en la base de datos (se muestra cuando obtienes las estadísticas del wallet)
  - amount: cantidad de tokens a depositar

  Deposita un número de tokens en el wallet.
- <strong>/withdraw</strong>

  Método: GET
  
  Query params:
  - id: el id del wallet en la base de datos (se muestra cuando obtienes las estadísticas del wallet)
  - amount: cantidad de tokens a sacar

  Saca un numero de tokens del wallet.
- <strong>/transfer</strong>

  Método: GET
  
  Query params:
  - from: el id del wallet en la base de datos (se muestra cuando obtienes las estadísticas del wallet) desde el cuál quieres transferir los tokens.
  - to: la cuenta (en hexadecimal) a la que enviar los tokens.
  - amount:cantidad de tokens a transferir.
  
  Transferencia de tokens de un wallet a una cuenta.
- <strong>/getWalletTransactions/{id}</strong>

  Método: GET

  Obtiene las transacciones de la wallet. El id se debe reemplazar por el id de la base de datos del wallet que quieres consultar las estadísticas.
