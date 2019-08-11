Foi utilizado
Postgres 10
Spring(boot, restapi, security, data, test)
FlywayDB
JsonWebToken
ProjectLombok

porta de utilizada "8080"
criar banco "teste"
criar banco "teste-test"
usuario "postgres"
senha "1234"
caso deseje mudar alterar os arquivos:
- /src/main/resources/application.properties
- /src/test/resources/application.properties

usuário "udsmanutencao" com senha "password" tem acesso a todos end-points
caso queria pode se cadastrar um usurios, grupo de acesso e atribulir permição para controle dos recursos

end-points pricipais do teste são:
- /tamanhos (get)
- /sabores (get)
- /personalizacoes (get)
- /pedidos/{1} (get)
- /pedidos (post)
- /pedidos/montarResumo (put)