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
openai.prompt.id=promptId
openai.prompt.version=3
```

- Um arquivo com uma lista de Tool: [tools](tools.json)

## Exemplo de um Meta Prompt

```
Você é um agente de IA que vai receber um prompt e um conjunto de dados (.json). Você deve utilizar o conjunto de dados para responder a solicitação do prompt. Caso não seja passado um conjunto de dados. Você deve responder apenas a solicitação do prompt. 

Prompt: {{prompt}} 

Dados: {{data}}
```

## `Tool`

1. Executa uma requisição HTTP para uma API externa e em seguida;
2. Passa o prompt para o Client que faz requisição para a OpenAi com os dados necessários.

```json
{
    "id": 1,
    "description": "calcula quantidade de registros de um nome em um período de tempo",
    "variables": {
        "prompt": "Quantas pessoas de nome josé foram registradas entre 1980 e 2000?"
    },
    "uri": "https://servicodados.ibge.gov.br/api/v2/censos/nomes/jose",
    "token": "",
    "method": "GET"
}
```

OBS: o token (Bearer) deve ser passado caso necessário autenticação do recurso.

## `Tools`

É o conjunto de tools que podem ser executadas. Esse objeto é serializado a partir de um arquivo .json. De forma que seja possível incluir novas functions sempre que possível.