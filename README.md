# mcp

Esta aplicação provê comunicação entre a OpenAI e qualquer API que utilize JWT ou seja aberta. Sendo um serviço híbrido para Client e Server MCP (Model Context Protocol).

## Pré-requisitos

- Java 21
- Maven

## Configurações necessárias

- Variáveis de ambiente

```sh
quarkus.rest.client.openai_url=https://api.openai.com
openai.model=gpt-4.1
openai.api.key=sk-proj-...
openai.prompt.id=pmpt_6904952...
openai.prompt.version=3

# apenas para segurança mínima
mcp.username=tiagolofi
mcp.password=********
mcp.roles=admin,user

# chaves RSA
private.key=MIIEvAIB...
public.key=MIIBIjANB...
```

- Um arquivo com uma lista de Tool: [tools](tools.json)

## `Meta Prompt`

O Meta Prompt é um prompt criado em https://platform.openai.com/chat que vai ser responsável por processar as ordens enviadas pelo serviço.

Exemplo:

```
Você é um agente de IA que vai receber um prompt e um conjunto de dados (.json). Você deve utilizar o conjunto de dados para responder a solicitação do prompt. Caso não seja passado um conjunto de dados. Você deve responder apenas a solicitação do prompt. 

Prompt: {{prompt}} 

Dados: {{data}}
```

## `Tools`

É o conjunto de tools que podem ser executadas. Esse objeto é serializado a partir de um arquivo `.json`. De forma que seja possível incluir novas functions sempre que possível.

## `Tool`

É uma funcionalidade que vai ser exposta no serviço que se baseia em dois passos principais:

1. Executa uma requisição HTTP para uma API externa e em seguida;
2. Passa o prompt para o Client que faz requisição para a OpenAi com os dados necessários.

## `Resquest`

O objeto `Request` carrega as informações necessárias de headers, parâmetros e caminhos da URL alvo e é passado no endpoint `POST /mcp`.

```json
{
  "headers": [
    {
      "name": "Authentication",
      "value": "Bearer eyJjd..."
    }
  ],
  "params": [
    {
      // ?campo=valor
      "name": "campo", 
      "value": "valor"
    }
  ],
  "paths": [
    // base_url/teste
    "teste"
  ],
  "prompt": "string", // a solicitação para a OpenAI
  "type": "PROMPT", // se for WEB_SEARCH, os demais parâmetros não devem ser passados.
  "data": {} // Objeto genérico, pode ser qualquer coisa. Deve ser usado como coringa para executar `POST` na URL desejada
}
```
## Exemplos - Tool e Curl

### Exemplo 1 - API de nomes do IBGE

```json
{
    "id": 1,
    "description": "calcula quantidade de registros de um nome em um período de tempo",
    "uri": "https://servicodados.ibge.gov.br/api/v2/censos/nomes",
    "method": "GET"
}
```

```sh
curl -X 'POST' \
  'http://localhost:8080/mcp?toolId=1' \
  -H 'accept: application/json' \
  -H 'Authorization: Bearer eyJjd...' \
  -H 'Content-Type: application/json' \
  -d '{
  "paths": [
    "moises"
  ],
  "prompt": "Quantas pessoas com o nome informado foram registradas entre 1980 e 2000?",
  "type": "PROMPT"
}'
```

### Exemplo 2 - Casas do Harry Potter

```json
{
    "id": 2,
    "description": "faz um resumo de uma casa em Harry Potter",
    "uri": "https://wizard-world-api.herokuapp.com/Houses",
    "method": "GET"
}
```

```sh
curl -X 'POST' \
  'http://localhost:7777/mcp?toolId=2' \
  -H 'accept: application/json' \
  -H 'Authorization: Bearer eyJjd...' \
  -H 'Content-Type: application/json' \
  -d '{
  "paths": [
    "805fd37a-65ae-4fe5-b336-d767b8b7c73a"
  ],
  "prompt": "Use os dados para descrever a casa Corvinal",
  "type": "PROMPT"
}'
```

### Exemplo 4 - Pergunta direta sobre algum assunto

```json
{
    "id": 3,
    "description": "Análise psicológica do personagem Bayek de Siuá, de Assassins Creed Origins",
    "uri": null,
    "method": null
},
```

```sh
curl -X 'POST' \
  'http://localhost:7777/mcp?toolId=3' \
  -H 'accept: application/json' \
  -H 'Authorization: Bearer eyJjdH...' \
  -H 'Content-Type: application/json' \
  -d '{
  "prompt": "O que levou o Bayek a sair em sua jornada?",
  "type": "PROMPT"
}'
```

### Exemplo 3 - Pesquisa na Web

```json
{
    "id": 4,
    "description": "Últimas notícias sobre o futebol nordestino",
    "uri": null,
    "method": null
}
```

```sh
curl -X 'POST' \
  'http://localhost:7777/mcp?toolId=4' \
  -H 'accept: application/json' \
  -H 'Authorization: Bearer eyJjdH...' \
  -H 'Content-Type: application/json' \
  -d '{
  "prompt": "Me dê as últimas notícias sobre o Moto Clube do Maranhão",
  "type": "WEB_SEARCH"
}'
```

## Autenticação de um serviço utilizado em uma Tool

Utilize o enpoint `/external/login` para se autenticar nos serviços de interesse, por exemplo:

```sh
curl -X 'POST' \
  'http://localhost:8080/external/login' \
  -H 'accept: */*' \
  -H 'Authorization: Bearer eyJjdH...' \
  -H 'Content-Type: application/json' \
  -d '{
  "uri": "http://localhost:8080/login",
  "headers": null,
  "data": {"username": "tiagolofi", "password": "*******"}
}'
```

A resposta dessa requisição é a resposta da URI para esse serviço. Depois disso você deve utilizar o token imediatamente ou armazená-lo em um local seguro para que possa ser reutilizado.