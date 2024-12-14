# Challenge ONE | Java | Back End - SpringBoot | API REST - Forum Alura

---

## 📜 Descrição do Projeto
O **Foru Alura** é uma plataforma onde os alunos da plataforma Alura podem postar suas dúvidas sobre diversos cursos. É um ambiente de aprendizado colaborativo entre alunos, professores e moderadores. 

Neste projeto, a proposta é replicar o funcionamento do **backend** deste sistema. Para isso, foi desenvolvida uma **API REST** utilizando o **Spring Boot**, com foco na gestão de tópicos. A API permite aos usuários:

- Criar um novo tópico
- Mostrar todos os tópicos criados
- Mostrar um tópico específico
- Atualizar um tópico
- Deletar um tópico

Além disso, o sistema inclui a implementação de **autenticação de usuários** para gerenciar recursos, e um banco de dados para armazenar as informações relacionadas a tópicos, respostas, cursos e usuários.

---

## 📝 Estado do Projeto
O projeto está em andamento, com a implementação das principais funcionalidades de CRUD (Criar, Ler, Atualizar, Deletar) de tópicos já concluída. O sistema de autenticação foi implementado, e a integração com o banco de dados MySQL está funcionando conforme esperado. 

---

## 🖥️ Características e Demonstração do Projeto

**Funcionalidades implementadas:**
- CRUD para **Tópicos** (Criação, exibição, atualização e exclusão)
- Sistema de **Autenticação de Usuários** com Spring Security
- **Token JWT** para autenticação segura
- Integração com banco de dados MySQL para persistência dos dados

**Banco de Dados:**
Foi criado um banco de dados com as tabelas necessárias para armazenar as informações de tópicos, respostas, usuários e cursos. Para melhor organização e desenvolvimento, recomenda-se primeiro criar o banco de dados e suas respectivas tabelas, e depois codificar o projeto no **Netbeans** ou **IntelliJ IDEA**.

**Funcionalidades demonstradas:**
- Criação e listagem de tópicos
- Atualização e remoção de tópicos
- Exibição de tópicos por usuários autenticados

---

## 📖 Acesso ao Projeto

**Passos principais para baixar o projeto:**

1. **Marcar o projeto com uma estrela** (opcional).
2. **Fazer o Fork do projeto**: No canto superior direito, clique no ícone de "fork" para criar uma cópia do repositório na sua conta pessoal do GitHub.
3. **Clonar o repositório**: 
   ```bash
   git clone https://github.com/fred-creator-creat/Forum_Alura.git

   
## 🖥️ Tecnologias Utilizadas

- **Java 17** (ou superior)
- **JPA (Hibernate)**: Para mapeamento de objetos para a base de dados
- **Spring Boot**: Framework utilizado para criação da API REST
- **Spring Security**: Para autenticação e segurança dos usuários
- **Token JWT**: Para gerenciamento de sessões e autenticação segura
- **MySQL**: Banco de dados relacional para persistência dos dados

---

## ⚠️ Importante!

- **Java versão**: Recomendamos usar Java versão 8 ou superior para garantir compatibilidade.
- **Editor de código**: Recomendamos o uso do **IntelliJ IDEA** para o desenvolvimento.

---

## 🚧 Desafios e Limitações Encontradas

Durante o desenvolvimento do projeto, algumas dificuldades foram enfrentadas devido a limitações de hardware e configuração do ambiente de desenvolvimento:

- **Problemas para executar testes**: Alguns testes não puderam ser realizados adequadamente devido ao desempenho do notebook utilizado, o que afetou a performance da aplicação durante o desenvolvimento.
- **Dificuldades na instalação de ferramentas**: A instalação de algumas ferramentas essenciais, como o IntelliJ IDEA, apresentou falhas devido às limitações de recursos do dispositivo. Isso dificultou a configuração inicial e a execução de algumas funcionalidades.
- **Caminhos de diretórios não encontrados**: Durante a configuração do ambiente de desenvolvimento, alguns caminhos esperados não foram localizados automaticamente, o que exigiu ajustes manuais na configuração do projeto.
- **Limitações de hardware**: O notebook utilizado é antigo e não possui um desempenho ideal para executar ferramentas modernas de desenvolvimento e execução. Isso resultou em alguns contratempos durante a implementação de algumas funcionalidades.

> **Nota**: Em breve, planejo adquirir um novo notebook com melhor desempenho, o que possibilitará a execução mais eficiente dos testes e a implementação das melhorias no projeto. Até lá, algumas funcionalidades poderão não ser testadas em sua totalidade.

---

## 💙 Colaboradores

- **Frederico Cavalheiro** - (com o auxílio do ChatGPT)

---

## 📄 Licença

Este projeto é distribuído sob a licença **MIT**.
