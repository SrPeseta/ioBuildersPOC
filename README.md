# ioBuildersPOC

## Prerrequisitos
Para poder ejecutar la prueba de concepto necesitamos:
- El IDE Eclipse.
- La versión de Java 8.
- Incluir el jar de PostgreSQL en la raíz del repositorio en el classpath.
- La versión de PostgreSQL 12.7.
- La versión de Ganache 2.5.4.
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

## Endpoints de la API
