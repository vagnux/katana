# katana
API Gateway with JWT auth and foward ACL to microservices


# Descrição
Este gateway na versão atual é apenas para uso experimental. 


A autenticação é feita via java JWT via endpoint {{url}}/api/auth/login enviando o seguinte payload via POST: 

```json
{
    "username":"user",
    "password":"password"
    
}
```

Lembrando que o login do usuários bem como seus perfis de acesso devem ser inseridos nas entidades relacionais criadas após o boot no banco de dados mysql/mariadb. 
consequentemente o banco de dados deve ser devidamente configurado no arquivo application.properties
