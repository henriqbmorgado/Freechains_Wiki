# Freechains Wiki

Uma enciclopédia cooperativa baseada em um ambiente P2P distribuído e não permissionado. A aplicação utiliza o protocolo Freechains para garantir a integridade dos dados, consenso descentralizado e um sistema de reputação para curadoria de conteúdo sem autoridades centrais.

**Apresentação do Projeto**: [Google Slides](https://docs.google.com/presentation/d/13-jaegHthGZFACryue2mrLdbKu6y0ODoKv_jyDxLNDE/edit?usp=sharing)

## Ferramentas Utilizadas

* **Freechains**: Protocolo P2P para disseminação de conteúdo e consenso. [GitHub - Freechains](https://github.com/Freechains)
* **Google Diff-Match-Patch**: Biblioteca para cálculo de diffs e aplicação de patches de texto. [GitHub - Google Diff-Match-Patch](https://github.com/google/diff-match-patch)

## O que foi implementado

* **Gestão de Host**: Interface Java para gerenciar o ciclo de vida do daemon freechains-host.
* **Identidade Criptográfica**: Geração de chaves públicas/privadas baseadas em segredos do usuário.
* **Consenso de Texto**: Reconstrução do artigo final aplicando patches de texto sequencialmente, seguindo a ordem determinada pelo comando consensus do Freechains.
* **Edição por Patches**: O sistema calcula a diferença (diff) entre a versão atual e a nova versão, enviando apenas o patch para a rede em vez do texto completo.
* **Curadoria Descentralizada**: Funcionalidades de Like/Dislike integradas ao sistema de reputação nativo do protocolo.
* **Sincronização P2P**: Comandos para envio e recebimento manual de blocos entre endereços de rede (host:port).
* **Simulação de Rede**: Script automatizado (Simul.java) que demonstra a convergência do texto entre múltiplos peers.

## O que não foi implementado

* **Interface Gráfica (GUI)**: A interação é realizada exclusivamente via terminal (CLI).
* **Resolução Automática de Conflitos**: Edições simultâneas na mesma linha dependem da lógica padrão da biblioteca diff-match-patch.
* **Notificações em Tempo Real**: É necessário consultar manualmente o estado da chain para visualizar atualizações.
* **Persistência Segura de Chaves**: Chaves são mantidas em memória simples durante a execução.

## Manual de Utilização

### Pré-requisitos
1. Certifique-se de que os executáveis freechains e freechains-host estão na raiz do projeto.
2. Certifique-se de que as bibliotecas .jar estão configuradas no seu classpath.

### Execução da Aplicação Principal
No diretório raiz do projeto, compile e execute a interface interativa:

### Execução da Aplicação Principal
No diretório raiz do projeto, compile e execute a interface interativa:

```bash
javac -d . src/name/fraser/neil/plaintext/*.java
javac -d . src/*.java
java Main
```
### Guia de Operação (Interface Main)
1. **Configuração Inicial**: Informe o diretório onde os dados do host serão salvos (ex: ./meu_wiki/).
2. **Login**: Insira sua chave secreta para gerar suas chaves de assinatura.
3. **Ingresso**: Use a opção 1 para entrar ou criar um tópico (ex: #abc).
4. **Leitura/Escrita**:
   - Opção 5: Exibe o texto final consolidado após o consenso.
   - Opção 4: Permite colar um novo texto para gerar uma atualização incremental (patch).
5. **Votação**: Use a opção 7 para validar posts de terceiros (Like/Dislike).
6. **Conexão**: Use a opção 9 para sincronizar manualmente com outro peer via IP:PORTA.

### Execução da Simulação
Para rodar o cenário de teste automatizado que simula múltiplos usuários editando e sincronizando o tópico "#Ciclo_da_Agua":

```bash:w
java Simul
```
