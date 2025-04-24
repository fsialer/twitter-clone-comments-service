# MICROSERVICIO DE COMMENTS
> Este microservicio se encarga de gestionar los comentarios, realizados por el usuario  pro cada post existente y asi 
> pueda ser visualizado por los demas integrantes de la plataforma.

## Variables de entorno
```
DB_HOST=mongodb://localhost:27017/comments_db?authSource=admin
```
```
DB_USERNAME=admin
```
```
DB_PASSWORD=*****
```
```
POST_URL=http://localhost:8082/v1/comments
```

## Tabla de recursos
| NOMBRE                                         | RUTA                       | PETICION | PARAMETROS  | CUERPO                                                                       | 
|------------------------------------------------|----------------------------|----------|-------------|------------------------------------------------------------------------------|
| Actuator                                       | /actuator       | GET      | NINGUNO     | NINGUNO                                                                      |
| Circuit breaker                                | /actuator/circuitbreakers    | GET      | NINGUNO    | NINGUNO                                                                      |
| Documentacion                                 | /swagger-ui/index.html  | GET      | NINGUNO    | NINGUNO                                                                                                                                      |
| Listar comentarios general                     | /v1/comments               | GET      | NINGUNO     | NINGUNO                                                                      |
| Obtener comentario por id                      | /v1/comments/{id}          | GET      | NINGUNO     | NINGUNO                                                                      |
| Crear comentario por usuario                   | /v1/comments               | POST     | NINGUNO     | {<br/>"content":"Nuevo Post"<br/>"postId":"678318b2c8dda45d9a6c300d"br/>}    |
| Actualizar comentario del usuario              | /v1/comments/{id}          | PUT      | NINGUNO     | {<br/>"content":"Post editado"<br/>}                                         |
| Eliminar comentario del usuario                | /v1/comments/{id}          | DELETE   | NINGUNO     | NINGUNO                                                                      |
| Verificar existencia de comentario del usuario | /v1/comments/{id}/verify   | GET      | NINGUNO     | NINGUNO                                                                      |
| Listar todos los comentarios por post          | /v1/comments/{idPost}/post | GET      | NINGUNO     |                                                                              |
| Crear Tipo de objeto (Reaccion) del usuario    | /v1/comments/data          | POST     | NINGUNO    | {<br/>"commentId":"6804498d871f48237c0f5e40",<br/> "typeTarget":"LIKE"<br/>} |
| Eliminar Tipo de objeto (Reaccion) del usuario | /v1/comments/data/{id}     | DELETE   | NINGUNO    | NINGUNO                                                                      |

## Stack
* SPRING BOOT 3
* SPRING WEBFLUX
* SPRING DATA
* MONGODB
* LOMBOK
* MAPSTRUCT
* MOCKITO
* JUNIT
* ACTUATOR
* RESILIENCE4J