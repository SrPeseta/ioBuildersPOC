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
2. Desplegar el contrato inteligente en el servidor de Ganache. (el contrato ERC20 está [aquí](walletMsLibrary/walletMsLauncher/src/main/resources/contract) y se llama [<strong>EIP20.sol</strong>](walletMsLibrary/walletMsLauncher/src/main/resources/contract/EIP20.sol))
3. Levantar o tener levantada la base de datos.
4. Tener cuentas en Ganache con algo de Ether.
5. Modificar los datos presentados anteriormente en el application.properties.
6. Importar en Postman el [json](iobuilders.postman_collection.json)
7. Levantar el microservicio.
8. Probar peticiones con la API en el Postman.

## Endpoints de la API
Todos los endpoints dan la respuesta en formato JSON.
- <strong>/create</strong>
  
  Método: GET
  
  Crea un wallet con una clave privada aleatoria.
  Retorna un JSON con los campos de la clase <strong>WalletDto</strong>.
- <strong>/createWalletWithKey</strong>

  Método: POST
  Body: una cadena de caracteres con la clave privada de la wallet.
  
  Crea un wallet con la clave privada que le pases en el cuerpo de la petición.
  Retorna un JSON con los campos de la clase <strong>WalletDto</strong>.
- <strong>/get/{id}</strong>

  Método: GET
  
  Obtiene estadísticas o datos relevantes del wallet. El id se debe reemplazar por el id de la base de datos del wallet que quieres consultar las estadísticas.
  Retorna un JSON con los campos de la clase <strong>WalletStatsDto</strong>.
- <strong>/getContractBalance</strong>

  Método: GET
  
  Obtiene el balance del contrato (número de tokens).
  Retorna un <strong>BigInteger</strong>.
- <strong>/getAllWallets</strong>

  Método: GET
  
  Obtiene todos los wallets en la base de datos y los devuelve.
  Retorna un JSON con una lista de <strong>WalletDto</strong>.
- <strong>/deposit</strong>

  Método: GET
  
  Query params:
  - id: el id del wallet en la base de datos (se muestra cuando obtienes las estadísticas del wallet)
  - amount: cantidad de tokens a depositar

  Deposita un número de tokens en el wallet.
  Retorna un JSON con los campos de la clase <strong>TransactionReceipt</strong>.
- <strong>/withdraw</strong>

  Método: GET
  
  Query params:
  - id: el id del wallet en la base de datos (se muestra cuando obtienes las estadísticas del wallet)
  - amount: cantidad de tokens a sacar

  Saca un numero de tokens del wallet.
  Retorna un JSON con los campos de la clase <strong>TransactionReceipt</strong>.
- <strong>/transfer</strong>

  Método: GET
  
  Query params:
  - from: el id del wallet en la base de datos (se muestra cuando obtienes las estadísticas del wallet) desde el cuál quieres transferir los tokens.
  - to: la cuenta (en hexadecimal) a la que enviar los tokens.
  - amount:cantidad de tokens a transferir.
  
  Transferencia de tokens de un wallet a una cuenta.
  Retorna un JSON con los campos de la clase <strong>TransactionReceipt</strong>.
- <strong>/getWalletTransactions/{id}</strong>

  Método: GET

  Obtiene las transacciones de la wallet. El id se debe reemplazar por el id de la base de datos del wallet que quieres consultar las transacciones.
  Retorna un JSON con una lista de elementos de la clase <strong>TransactionResult</strong>.
