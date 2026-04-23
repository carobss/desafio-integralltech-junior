# Decisões de Projeto

Este documento detalha as escolhas técnicas, desafios enfrentados e o processo de aprendizado durante o desenvolvimento da API.

---

## 1. Arquitetura e Organização 
Ao pesquisar sobre os tipos de estrutura de projeto com Spring Boot, escolhi a Arquitetura em Camadas, um padrão fortemente recomendado pela comunidade. A divisão de responsabilidades facilita a leitura e manutenção do código:
- Model/Entity: representa o banco de dados com JPA.
- Repository: interface para abstração do banco de dados.
- Service: centraliza as regras de negócio.
- Controller: ponto de entrada da API.
- DTOs: garante apenas que os dados necessários sejam trafegados, como um relatório controlado.

---

## 2. Tecnologias
- Java 21 e Spring Boot 3.2.5: versões mais recentes para aproveitar recursos modernos.
- H2 Database: banco em memória, o que facilita a execução do projeto sem a necessidade de configurar um banco externo.
- Bean Validation: uso de anotações como @NotBlank e @NotNull para garantir a qualidade dos dados na entrada da API.

---

## 3. Uso de Inteligência Artificial
Utilizei o Claude Code como parceiro de programação, e por ter sido meu primeiro contato com Spring Boot, foi fundamental em:
- Demonstrar a implementação de padrões de projeto em Java.
- Criar o mock de análise de chamados com IA.

Também utilizei o ChatGPT para consultas mais simples, que variaram entre:
- Dúvidas sobre as dependências e suas configurações.
- Uso e comandos do Maven.
- Explicação sobre termos técnicos e sintaxe da linguagem Java.

---

## 4. Desafios e Aprendizados
- As Anotações (@): Um ponto que chamou bastante minha atenção foi compreender como o Spring utiliza as anotações para injetar dependências e configurar comportamentos automaticamente.
- Lombok: Foi um ponto positivo, pois reduziu o código repetitivo (getters/setters), mantendo os arquivos limpos.
- Curva de Aprendizado: Aprender uma linguagem robusta como Java junto ao framework Spring Boot foi e está sendo melhor do que imaginava, a arquitetura escolhida ajudou na leitura e interpretação do código, validando as práticas aplicadas e comparando com o uso no mercado.