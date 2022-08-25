# Card Game Chalenge Project

Implementação do Projeto no estilo de card game, onde o jogador deve acertar aquele que possui melhor avaliação no IMDB. 

<b>Requisitos:</b>
- Java 11
- Maven

<b>Dependências:</b>
- Spring Boot
- Spring Security
- Spring Test
- Spring Web
- Spring Cloud / Eureka
- Swagger
- Lombok
- H2 Database

<b>Considerações</b>
- Os dados iniciais sobre filmes são carregados em memória através do arquivo resource/data.sql. Os dados foram obtidos do site oficial: IMDb Top 250 Movies (https://www.imdb.com/chart/top/?ref_=nv_mv_250)
- Foi utilizada a solução básica de atutenticação, onde as credenciais estão configuradas em memória. Os endpoints estão protegidos por Spring Security, e para consumi-los via Postman, é preciso primeiro realizar "Login" com algum dos usuários configurados.
- cardgame-service: Projeto principal, responsável por:
	- Realizar autenticação
	- Disponibilizar end-points finais
	- Documentação OpenAPI
	- Disponibilizar testes de integração da API REST
	- Carregar em memória (H2) dados oficiais de filmes  

<b>End-ponits</b> 
- Os end-points podem ser consumidos via Swagger ou Postman. O arquivo com as configurações pode ser importado e encontra-se em: resources/postman_collection.json
- Principais end-points:
  - Login (/cardgame/authentication/login): realiza autenticação com um dos usuarios configurados em memória. 
  - Logout (/cardgame/authentication/logout): realiza logou e encerra sessão do usuário. 
  - Get username (/cardgame/authentication/username): retorna o nome usuário logado no momento. 
  - Get All Movies (/cardgame/all-movies): retorna todos os filmes inseridos em H2.
  - Get Two Movies to Play (/cardgame/movies-play): retorna 2 filmes distintos para início do card game.
  - Save User Choice (/cardgame/save-user-choice): verifica se a opção selecionada está correta e insere os dados da escolha em H2. Obtem o usuário logado previamente. 
  - Get All Results (/cardgame/all-results): retorna todos os resultados cadastrados para cada usuário.
  - Get Final Ranking (/cardgame/users-ranking): retorna a pontuação final para cada usuário.
  
  <b>Segurança</b>
  - Basic Authentication
  - Configuração de 2 usuários em memória:
  	- cinema/123
  	- imdb/123
  
  <b>Configuração Microserviços</b>
  - discovery-server: Eureka server
  - cardgame-service: Eureka client. Responsável pela integração com os demais clients (movie-server / result-server)
  - movie-server: Eureka client. Responsável por manter dados de Movies.
  - result-server: Eureka client. Responsável por manter dados dos resultados dos jogos.
