# add-merchants-registry

Serviço Spring Boot para cadastrar Estabelecimentos Comerciais.

## Versão
- `0.0.1-SNAPSHOT`

## Requisitos
- **Java:** 21
- **Maven:** 3.9+ (ou Maven Wrapper `./mvnw`)
- **Spring Boot:** 3.3.5

## Estrutura do Projeto
```text
add-merchants-registry/
├── pom.xml
├── .gitignore
├── HELP.md
├── README.md
├── mvnw
├── mvnw.cmd
├── .mvn/
│   └── wrapper/
│       └── maven-wrapper.properties
└── src/
    ├── main/
    │   ├── java/
    │   │   └── br/com/myproject/
    │   │       ├── AddMerchantsRegistryApplication.java
    │   │       ├── config/
    │   │       ├── controller/MerchantController.java
    │   │       ├── dto/MerchantRequest.java
    │   │       ├── exception/
    │   │       │   ├── GlobalExceptionHandler.java
    │   │       │   └── InvalidCnpjException.java
    │   │       ├── model/Merchant.java
    │   │       ├── repository/MerchantRepository.java
    │   │       ├── service/MerchantServiceImpl.java
    │   │       └── validation/MerchantRequestValidator.java
    │   └── resources/
    │       ├── application.properties
    │       ├── application-h2.properties
    │       ├── application-oracle.properties
    │       ├── static/
    │       └── templates/
    └── test/java/br/com/myproject/
        ├── AddMerchantsRegistryApplicationTests.java
        ├── controller/MerchantControllerValidationTest.java
        ├── repository/MerchantRepositoryTest.java
        ├── service/MerchantServiceImplTest.java
        └── validation/MerchantRequestValidatorTest.java
```
## API de Merchants

A API usa o prefixo `/merchants` e oferece estas operações:

| Método | Endpoint | Descrição |
| --- | --- | --- |
| `POST` | `/merchants` | Cadastra um merchant. Retorna `201 Created`. |
| `GET` | `/merchants/{cnpj}` | Busca um merchant pelo CNPJ. Retorna `404 Not Found` se não existir. |
| `GET` | `/merchants` | Lista todos os merchants. |

Exemplo de cadastro:

```http
POST /merchants
Content-Type: application/json
```

```json
{
   "cnpj": "11.222.333/0001-81",
   "razaoSocial": "Padaria Central Ltda"
}
```

A resposta contém `merchantId`, `cnpj` e `razaoSocial`. A validação aceita CNPJs numéricos e alfanuméricos, com ou sem a pontuação usual. Para a consulta, prefira informar o CNPJ sem pontuação no caminho:

```http
GET /merchants/11222333000181
GET /merchants
```

CNPJ inválido retorna `400 Bad Request` com uma resposta no formato Problem Details. A documentação interativa OpenAPI/Swagger fica disponível em:

- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- Especificação OpenAPI: `http://localhost:8080/v3/api-docs`

## Persistência e Suporte Multi-Banco (H2 / Oracle)

O projeto utiliza **Spring Data JPA** e abstração **Hibernate ORM**, desacoplando totalmente o código da aplicação do banco de dados relacional subjacente. A alternância entre bancos de dados é feita via **Spring Profiles** sem necessidade de alterar o código-fonte Java.

### 1. Perfil Padrão: H2 (`h2`)
Ideal para desenvolvimento local e testes rápidos em memória.
- **H2 Console:** `http://localhost:8080/h2-console`
  - JDBC URL: `jdbc:h2:mem:merchantsdb`
  - User: `sa`
  - Password: *(em branco)*

### 2. Perfil Oracle (`oracle`)
Configurado para conexão com Oracle Database (12c, 19c, 21c, 23ai) com driver oficial `ojdbc11` e pool de conexões HikariCP.
Parâmetros configuráveis via variáveis de ambiente:
- `ORACLE_HOST` (padrão: `localhost`)
- `ORACLE_PORT` (padrão: `1521`)
- `ORACLE_SERVICE` (padrão: `XEPDB1`)
- `ORACLE_USERNAME` (padrão: `merchants_app`)
- `ORACLE_PASSWORD` (padrão: `merchants_secret`)
- `ORACLE_DDL_AUTO` (padrão: `update`)

### Como Alternar o Banco de Dados

1. **Via `application.properties`:**
   ```properties
   spring.profiles.active=oracle
   ```

2. **Via Linha de Comando (Maven / Java):**
   ```bash
   ./mvnw spring-boot:run -Dspring-boot.run.profiles=oracle
   # ou com o JAR construído:
   java -jar target/add-merchants-registry-0.0.1-SNAPSHOT.jar --spring.profiles.active=oracle
   ```

3. **Via Variável de Ambiente (Docker / Kubernetes):**
   ```bash
   export SPRING_PROFILES_ACTIVE=oracle
   export ORACLE_HOST=meu-oracle-host.com
   export ORACLE_PORT=1521
   export ORACLE_SERVICE=ORCLPDB1
   export ORACLE_USERNAME=admin
   export ORACLE_PASSWORD=senha_segura
   ```

## Como Executar

### Utilizando Maven Wrapper (Windows PowerShell)
```powershell
.\mvnw.cmd spring-boot:run
```

### Utilizando Maven Wrapper (Bash / Linux / macOS)
```bash
./mvnw spring-boot:run
```

### Executar Testes

Windows PowerShell:
```powershell
.\mvnw.cmd test
```

Linux / macOS:
```bash
./mvnw test
```
