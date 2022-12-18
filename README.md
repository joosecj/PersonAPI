<h1 align="center">PersonAPI</h1>

<p align='center'> 
    <img src="https://img.shields.io/badge/Spring_Boot  V3.0M5-F2F4F9?style=for-the-badge&logo=spring-boot"/>
    <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white"/>  
    <img src="https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white"/>
    <img src="https://img.shields.io/badge/Junit5-25A162?style=for-the-badge&logo=junit5&logoColor=white"/>
</p>    


Projeto desenvolvido teve o objetivo de criar um API Rest, utilizando os padrões de arquitetura em camadas, onde é possível cadastrar uma pessoa, atualizar, deletar, realizar consultas por id e paginadas, e com devido tratamento de suas exceções, além de realização de teste automatizados na camada de services, respositories e controllers. 

- Projeto implementado na nuvem com heroku.

<h2>Veja o projeto</h2>

Experimente live demo.

![](https://i.imgur.com/QMgdvJ6.gif)


<h2>Como criar e executar o PersonAPI localmente</h2>

Criar e executar o projeto em seu ambiente de desenvolvimento local é muito fácil. Certifique-se de ter o Git, JDK17 e banco PostgreSQL instalados e siga as instruções abaixo. Precisa de informações adicionais? entre em contato no e-mail josecarloscjj@gmail.com 
(Estas instruções pressupõem que você esteja instalando como um usuário root.)

1. Clone o código fonte

   ```bash
   git@github.com:joosecj/PersonAPI.git
   ```

2. Em sua IDE de preferência(utilizei Intellij), importe a pasta **backend** e faça o update das dependências do **maven**.

3. Antes de executar o projeto, verificar se o arquivo dentro da pasta **RESOURCES** chamado **application.properties** esteja configurado com sua variável de ambiente como **test**, assim o projeto será rodado no bando H2, sem necessidade de configurar seu postgreSQL localmente, porém caso decida utilizar será necessário alterar o arquivo **application-dev.properties** com as configurações do banco de dados local. 
Exemplo abaixo de como deve ficar o arquivo: 

   ```java
   spring.profiles.active=${APP_PROFILE:test}
   ```


4. Ao executar o projeto, pode ser acessado no navegador da Web em http://localhost:8080/ ou poderá usar Api implementada no heroku através do link: https://joosecj-personapi.herokuapp.com

5. Collections do postman para fazer as requisições GET/PUT/DELETE E UPDATE - Onde pode ser trocada a variável de ambiente, que inicialmente está configurada com link da api no heroku.

   Link da Collections do postman: https://www.getpostman.com/collections/e575eec425356a984f4a

## Requisições (Endpoints)

#### Obs: Para testar as requisições, poderá usar o URL na nuvem ou local que e http://localhost:8080.

- *Pessoa By Id* - **GET**

   ```bash
   https://joosecj-personapi.herokuapp.com/pessoa/1
   ```
   ##

- *Pessoa By Pagead* - **GET**

   ```bash
   https://joosecj-personapi.herokuapp.com/pessoa?size=10&page=0&sort=name,asc
   ```

   ##

- *Nova Pessoa -* **POST**

   ```bash
   https://joosecj-personapi.herokuapp.com/pessoa
   ```

   ##

- *Pessoa -* **PUT**

   ```bash
   https://joosecj-personapi.herokuapp.com/pessoa/1
   ```

   ##

- *Pessoa -* **DELETE**

   ```bash
   https://joosecj-personapi.herokuapp.com/pessoa/1
   ```

   ##

- *Corpo da Requisição(Body) -* **JSON** - **PUT** e **POST**

   ```bash
   {
    "name": "Jose Carlos",
    "email": "josecj@gmail.com",
    "birthDate": "1994-07-20"
   }
   ```
   ##

   <h2>Tecnologias utlizadas</h2>

   - [Java](https://docs.oracle.com/en/java/javase/17/)
   - [Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
   - [JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
   - [Maven](https://maven.apache.org/guides/)
   - [H2 Database](https://www.h2database.com/html/main.html)
   - [PostgreSQL](https://www.postgresql.org/docs/)
   - [Spring Security](https://docs.spring.io/spring-security/reference/index.html)
   - Teste Automatizados com [JUnit](https://junit.org/junit5/docs/current/api/)
   - [Postman](https://www.postman.com/api-documentation-tool/)

   ##

   
   <div align="center">
   <h2>Autor: José Carlos</h2>
      <img align="center" alt="Jose-Js" height="190" width="190" src="https://avatars.githubusercontent.com/u/100246121?s=400&u=b15a545fb2c49f97f84e25aa0520b8b525631384&v=4"
   </div>
   </br> 
   </br>
   <div align="center">
      <a href="https://instagram.com/joosecj" target="_blank"><img src="https://img.shields.io/badge/-Instagram-%23E4405F?style=for-the-badge&logo=instagram&logoColor=white" target="_blank"></a>
      <a href = "mailto:josecarloscjj@gmail.com"><img src="https://img.shields.io/badge/-Gmail-%23333?style=for-the-badge&logo=gmail&logoColor=white" target="_blank"></a>
      <a href="https://www.linkedin.com/in/jos%C3%A9-carlos-a79736a0/" target="_blank"><img src="https://img.shields.io/badge/-LinkedIn-%230077B5?style=for-the-badge&logo=linkedin&logoColor=white" target="_blank"></a> 
   </div>
