#  Projet Spring-Angular-JWT Spring Security

## Présentation du Projet
On souhaite créer une application qui permet de gérer des comptes bancaires basé sur 
un agent AI. chaque compte appartient à un client. 
- un compte peut subir plusieurs opérations de type DEBIT ou CREDIT. 
- Il existe deux types de comptes : Comptes courants et comptes épargnes.


## 1. STack technique (Spring Boot 4.1.0)
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
# Partie I -  Backend
####  Account and Operation details
<img src="captures/3.png">

####  BankAccountService
<img src="captures/3.png">

#### web layer & Jackson cyclic reference
<img src="captures/4.png">
 
   @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
<img src="captures/5.png">


####  Customer with DTO
<img src="captures/6.png">

#### Swagger documentation(spring boot openapi doc maven dependency)
[http://localhost:9090/swagger-ui/index.html]
<img src="captures/11.png">

###  BankAccountRestAPI with DTO
<img src="captures/12.png">

###  BankAccount Operations with DTO
<img src="captures/13.png">

<img src="captures/14.png">

<img src="captures/15.png">

<img src="captures/16.png">

# Partie II - Client Angular (angular 21.2.18)
le client Angular représente le frontend et est disponible à partir du dépôt : 
[https://github.com/jeanpierrensem/digital-banking-web-angular21]

- CRUD
- validator 
- httpClient
- FormGroup
- CORS

![img_2.png](img_2.png)

# Partie 3 : Sécuriser l'application avec un système d'authentification basé sur Spring Security et Json Web Token
- spring security 7 + SPring Boot 4
- JWT (Json Web Token)
- Authentification stateless
- base 64 Helper intellij plugging
- Http Client intellij plugging 

### Basic Authentication
- <img src="captures/18.png" >
- <img src="captures/19.png" >

### Jwt authentication
- <img src="captures/20.png" >
  Jwt Token Signature
- <img src="captures/21.png" >
- <img src="captures/22.png" >
- npm install jwt-decode

#### Authentication
 <img src="captures/23.png" >

#### admin login 
<img src="captures/24.png" >

![img.png](img.png)

#### user profile login 
![img_1.png](img_1.png)


# Partie 4 : Chat BOT AI


