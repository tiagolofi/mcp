# mcp

Esta aplicação provê comunicação entre a OpenAI e qualquer API que utilize JWT ou seja aberta. Sendo um serviço híbrido para Client e Server MCP (Model Context Protocol).

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