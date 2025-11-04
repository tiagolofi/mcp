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

## Exemplo de um Meta Prompt

```
Você é um agente de IA que vai receber um prompt e um conjunto de dados (.json). Você deve utilizar o conjunto de dados para responder a solicitação do prompt. Caso não seja passado um conjunto de dados. Você deve responder apenas a solicitação do prompt. 

Prompt: {{prompt}} 

Dados: {{data}}
```

## `Tool`

É uma funcionalidade que vai ser exposta no serviço que se baseia em dois passos principais:

1. Executa uma requisição HTTP para uma API externa e em seguida;
2. Passa o prompt para o Client que faz requisição para a OpenAi com os dados necessários.

### Exemplo 1 - API de nomes do IBGE

```json
{
    "id": 1,
    "description": "calcula quantidade de registros de um nome em um período de tempo",
    "variables": {
        "prompt": "Quantas pessoas com o nome informado foram registradas entre 1980 e 2000?"
    },
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
  ]
}'
```

### Exemplo 2 - Casas do Harry Potter

```json
{
    "id": 2,
    "description": "faz um resumo de uma casa em Harry Potter",
    "variables": {
        "prompt": "Me dê um resumo sobre essa casa de Harry Potter"
    },
    "uri": "https://wizard-world-api.herokuapp.com/Houses",
    "method": "GET"
}
```

```sh
curl -X 'POST' \
  'http://localhost:8080/mcp?toolId=2' \
  -H 'accept: application/json' \
  -H 'Authorization: Bearer eyJjd...' \
  -H 'Content-Type: application/json' \
  -d '{
  "paths": [
    "805fd37a-65ae-4fe5-b336-d767b8b7c73a"
  ]
}'
```

OBS: headers e parâmetros (query e path) podem ser passados via requisição usando `POST /mcp`

## `Tools`

É o conjunto de tools que podem ser executadas. Esse objeto é serializado a partir de um arquivo .json. De forma que seja possível incluir novas functions sempre que possível.