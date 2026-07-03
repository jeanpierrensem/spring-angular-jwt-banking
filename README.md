#  Projet Spring-Angular-JWT Spring Security

## Présentation du Projet
On souhaite créer une application qui permet de gérer des comptes bancaires basé sur 
un agent AI. chaque compte appartient à un client. 
- un compte peut subir plusieurs opérations de type DEBIT ou CREDIT. 
- Il existe deux types de comptes : Comptes courants et comptes épargnes.


## 1. STack technique
- Spring Data
- Spring Data JPA
- Hibernate 
- Spring web
- DevTools
- Spring Security
- ModelMapper (MapStruct-JMapper)
- Angular
- MySQL
- MongoDB(NoSQL)
- Swagger


## 2. Concept
- Entity
- DTOs 
- JWT
- Web & IoC containeur
- DAO
- Service
- Repository
- Rest API
- Frontend
- Backend
- JsonProperty
- SQL


## Architecture
<img src="captures/1.png">

## Use Case Diagram
<img src="captures/2.png">

- mapping d'héritage dans les bases de données relationnelles(Single Table->Jointable->TablePerClass)
- assoiation 1 * et sens * 1
### Simulation - Account and Operation details
<img src="captures/3.png">

### Simulation - BankAccountService
<img src="captures/3.png">

### Simulation - web layer & Jackson cyclic reference
<img src="captures/4.png">
 
   @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
<img src="captures/5.png">


### Simulation - Customer with DTO
<img src="captures/6.png">

### Swagger documentation(spring boot openapi doc maven dependency)
[http://localhost:9090/swagger-ui/index.html]
<img src="captures/11.png">


