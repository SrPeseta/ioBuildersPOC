# ioBuildersPOC

## Prerrequisitos
Para poder ejecutar la prueba de concepto necesitamos:
- El IDE Eclipse.
- La versión de Java 8.
- Tener instalado el plugin de Spring en Eclipse
- Incluir el jar de PostgreSQL de la raíz del repositorio en el classpath.
- Una base de datos PostgreSQL (versión 12.7).
- El programa de Ganache (versión 2.5.4) para simular una red blockchain.
## Cambios en el application.properties del artefacto walletMsLauncher
```
spring.datasource.url=jdbc:postgresql://<HOST>:<puerto>/postgres
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

## Endpoints de la API
