#📧 Notificacao-Service

Microserviço responsável por processar e disparar notificações de tarefas pendentes via e-mail. Este projeto faz parte do ecossistema de produtividade, garantindo que o usuário seja alertado sobre prazos e eventos importantes.

## 🛠️ Tecnologias e Ferramentas

* **Java 17 & Spring Boot 3**: Núcleo da aplicação.
* **Lombok**: Utilizado para reduzir código boilerplate através de `@Getter`, `@Setter` e `@Slf4j`.
* **Spring Mail (SMTP)**: Interface para comunicação com servidores de e-mail.
* **GitGuardian (ggshield)**: CLI de segurança utilizada para prevenir o vazamento de chaves SMTP e tokens de infraestrutura.
* **Thymeleaf**: Motor de template para e-mails em formato HTML.

---
## 🛡️ Segurança (GitGuardian)

Para garantir que credenciais sensíveis (como as senhas do `@Value`) não sejam expostas, este projeto utiliza o **GitGuardian** no modo local.

### Verificação de Integridade
O ambiente foi validado e encontra-se livre de segredos expostos:
> **Status:** `No secrets have been found`

Para rodar um scan manual antes de um commit, utilize:
```bash
python -m ggshield secret scan pre-commit
