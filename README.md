# Desafio Técnico — IntegrAllTech 2026

API REST de gerenciamento de chamados de suporte, desenvolvida em Java com Spring Boot.

---

## Requisitos

* Java 21 (LTS)
> O projeto utiliza Lombok, que ainda não possui suporte estável para versões superiores do Java.
> Certifique-se de compilar e executar com Java 21.

> ⚠️ Este projeto utiliza o **Maven Wrapper**, portanto **não é necessário ter o Maven instalado** na máquina.

Para verificar se o Java está instalado:

```bash
java -version
```

---

## Como rodar o projeto

**1. Clone o repositório:**

```bash
git clone https://github.com/seu-usuario/desafio-integralltech-junior.git
cd desafio-integralltech-junior
```

**2. Instale as dependências e compile:**

No Windows:

```bash
.\mvnw.cmd clean install
```

No Linux/Mac:

```bash
./mvnw clean install
```

**3. Rode a aplicação:**

No Windows:

```bash
.\mvnw.cmd spring-boot:run
```

No Linux/Mac:

```bash
./mvnw spring-boot:run
```

A API estará disponível em: `http://localhost:8080`

---

## Console do banco H2

O H2 é um banco em memória — os dados são perdidos ao reiniciar a aplicação.

Para visualizar os dados enquanto a aplicação está rodando:
- Acesse: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:chamadosdb`
- Usuário: `sa`
- Senha: *(deixar em branco)*

---

## Endpoints da API

| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/api/chamados` | Criar um novo chamado |
| GET | `/api/chamados` | Listar todos os chamados |
| GET | `/api/chamados/{id}` | Buscar chamado por ID |
| PUT | `/api/chamados/{id}` | Atualizar um chamado |
| DELETE | `/api/chamados/{id}` | Cancelar um chamado (soft delete) |
| GET | `/api/chamados/setor/{setor}` | Filtrar chamados por setor |
| POST | `/api/chamados/{id}/analisar` | Analisar chamado com IA |

---

## Exemplos de uso

### Criar chamado
```bash
POST /api/chamados
Content-Type: application/json

{
  "titulo": "Computador desliga sozinho",
  "descricao": "O computador da sala 707 desliga sozinho após alguns minutos de uso.",
  "setor": "TI",
  "prioridade": "ALTA",
  "solicitante": "Marina Sena"
}
```

### Valores aceitos pelos Enums

**setor:** `TI`, `MANUTENCAO`, `RH`, `FINANCEIRO`

**prioridade:** `BAIXA`, `MEDIA`, `ALTA`, `CRITICA`

**status (somente no PUT):** `ABERTO`, `EM_ATENDIMENTO`, `RESOLVIDO`, `CANCELADO`

### Analisar chamado com IA
```bash
POST /api/chamados/1/analisar
```

---

## Regras de negócio

- Todo chamado nasce com status `ABERTO`
- O `DELETE` não remove o registro — muda o status para `CANCELADO` e preenche `dataFechamento`
- Não é possível alterar um chamado com status `CANCELADO` ou `RESOLVIDO`
- `dataFechamento` é preenchida automaticamente ao resolver ou cancelar um chamado

---

## Tecnologias utilizadas

- Java 21
- Spring Boot 3.2.5
- Spring Data JPA + Hibernate
- H2 Database (em memória)
- Lombok
- Bean Validation (Jakarta)
