<h1 align="center">
My Music 2022
   <br>
B A Y G O N
</h1>

<h3 align="center"> 
<img src="https://media.giphy.com/media/lqSDx8SI1916ysr4eq/giphy.gif">
</h3>
<p align="center">
 <a href="#-sobre-o-projeto">Sobre</a> •
 <a href="#-funcionalidades">Funcionalidades</a> •
 <a href="#-layout">Layout</a> • 
 <a href="#-tecnologias-e-ferramentas">Tecnologias e Ferramentas</a> • 
 <a href="#-contribuidores">Contribuidores</a> • 
 <a href="#user-content--licença">Licença</a>
</p>
<br>

## 💻 Sobre o projeto
Afim de aplicar todo conteúdo estudado no BootCamp da CI&T nosso time teve como desafio desenvolver novos serviços para substituir a camada de APIs legado utilizando o banco de dados já existente. 

<h4>API responsável por gerenciar as musicas favoritas do usuário. Possui como principais funcionalidades:</h4>

● Permitir o usuário buscar novas músicas:

1. O serviço deve validar se o usuário informou ao menos 2 caracteres, retornando um HTTP 400
   caso a consulta tenha menos de 2 caracteres.
2. A busca deve ser realizada através do nome de artista e nome da música.
3. A busca por música não deve ser case sensitive.
4. A busca deve retornar valores contendo o filtro, não necessitando de ser informado o nome
   completo de música ou artista.
5. O retorno deve estar ordenado pelo nome do artista e depois pelo nome da música. 

● Permitir adicionar as músicas favoritas do usuário na playlist:

1. Deve receber um request contendo o identificador da música e o identificador da playlist.

2. Deve validar se o identificador da música e o identificador da playlist existem.

● Permitir o usuário remover músicas de sua playlist:

3. Deve receber um request contendo o identificador da música e o identificador da playlist.

4. Deve validar se o identificador da música e o identificador da playlist existem.

Todos os endpoints devem possuir uma camada de segurança para proteger o dominio de dados. Para implementar
essa segurança os endpoints criados devem exigir que as requisições recebidas possuam o header "authorization",
contendo um token válido para responder a requisição. Para realizar a criação e geração do token, utilizar o serviço 
disponbilizado junto com estrutura do projeto: token-provider-0.0.1-SNAPSHOT.jar.

# token-provider

Para criação de token válidos utilizar o endpoint a seguir:

```
ENDPOINT: /api/v1/token
METODO: POST
BODY: 
{ 
    "data": {
        "name": "fulano"
    }
}
RETORNO: 201 Created
{
    "12321312321312"
}
```

Para validação de token utilizar o endpoint a seguir:


```
ENDPOINT: /api/v1/token/authorizer
METODO: POST
BODY: 
{ 
    "data": {
        "name": "fulano",
        "token": "12321312321312"
    }
}
RETORNO: 201 Created
{
    "ok"
}
```

# Banco de dados

Para auxiliar o desenvolvimento do API, a estrutura inicial conta com uma base de dados pré-definida e populada

Modelagem: 
<div align="center"><img src="https://i.imgur.com/yfMGrur.png" title="source:modelagem imgur" /></div>

Atenção:
Os campos Id que utilizam GUID mapear como string devido à complexidade na compatibilidade com o UUID nativo do Java.

Dica:
Não é necessário, porém é possível utilizar uma ferramenta para abrir e visualizar o arquivo MyMusic.db de maneira mais fácil, como:

https://sqlitestudio.pl/index.rvt

