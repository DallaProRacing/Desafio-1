# Desafio-1
Primeiro desafio da bolsa SP - Back-end Journey, usando Java com JBDC My SQL.

# Introdução

  O Sistema de Carrinho de Compras é uma aplicação desenvolvida para gerenciar compras de forma simples e eficiente, utilizando uma interface de linha de comando. O software permite que os clientes insiram dados diretamente no console, proporcionando uma experiência interativa e acessível.
  Neste sistema, os usuários podem realizar ações como cadastrar produtos, adicionar itens ao carrinho e finalizar compras. A interação é feita por meio de comandos diretos, tornando o processo rápido e direto, sem a necessidade de uma interface gráfica complexa.
  O sistema utiliza MySQL com JDBC para gerenciar os dados, garantindo que todas as informações sobre produtos, vendas e clientes sejam armazenadas de forma segura e organizada. Essa abordagem foca na simplicidade e na funcionalidade, permitindo que usuários interajam com o sistema de maneira prática, mesmo em um ambiente sem interface visual.
  Com este projeto, buscamos oferecer uma solução eficiente para o gerenciamento de compras, com uma experiência de usuário que valoriza a interação direta e a agilidade nas operações.

# Instalação do MySQL, Eclipse, Driver JDBC e Git Bash: 
### 1. Instalação do MySQL
  - Baixe o instalador do MySQL em [MySQL Downloads](https://dev.mysql.com/downloads/mysql/).
  - Siga as instruções do instalador para concluir a instalação. Durante a instalação, você será solicitado a configurar uma senha para o usuário root. Anote essa senha, pois será necessária para conectar-se ao banco de dados.
  - (Opcional) Para aprender a instalar o MySQL, você pode assistir a este vídeo: https://www.youtube.com/watch?v=s0YoPLbox40 .

### 2. Instalação do Eclipse
  - Baixe o Eclipse IDE em [Eclipse Downloads](https://www.eclipse.org/downloads/).
  - Escolha a versão apropriada para seu sistema operacional e siga as instruções de instalação.
  - Após a instalação, inicie o Eclipse e configure seu espaço de trabalho.

### 3. Baixe o Driver JDBC
   - Acesse o site oficial do MySQL Connector/J: [MySQL Connector/J Downloads](https://dev.mysql.com/downloads/connector/j/).
   - Clique em "Download" e selecione a versão mais recente do MySQL Connector/J.
   - Após o download, extraia o arquivo ZIP para um diretório de sua escolha.

### 4. Instalação do Git Bash
  Para facilitar o desenvolvimento e o uso do Git, é recomendável instalar o Git Bash:
  - Baixe o Git Bash em [Git for Windows](https://gitforwindows.org/).
  - Siga as instruções do instalador para concluir a instalação.
  - O Git Bash oferece um terminal que suporta comandos Unix, facilitando a interação com o Git e o gerenciamento do seu projeto.

# Criando banco MySQL:
### 1. Inicie o MySQL Workbench
 - Clique em File > New Query Tab para abrir um novo script.
### 2. Colando o Script do Banco de Dados:
 - Acesse o link do documento do Google com o script (https://docs.google.com/document/d/1bTmhJPZfp22or9hXeEtd87_O6zbCP3j8W2ryN7T_Tkk/edit?usp=sharing) ou localize o script no repositório.
 - Copie o conteúdo do script.
 - Cole o script na nova aba de consulta que você abriu no MySQL Workbench.
### 3. Executando o Script:
- Após colar o script, clique no botão Execute (ou pressione Ctrl + Shift + Enter) para executar o código SQL.
Isso criará as tabelas necessárias e configurará o banco de dados para o seu sistema de carrinho de compras.
 
# Criando o Projeto e adicionando a biblioteca:
### 1. Criando um Novo Projeto:
   - Abra o Eclipse.
   - Clique em `File` > `New` > `Java Project`.
   - Dê um nome ao seu projeto, por exemplo, `CarrinhoDeCompras`, e clique em `Finish`.

### 2. Adicionando a Biblioteca JDBC:
   - Clique com o botão direito no nome do seu projeto no painel "Package Explorer".
   - Selecione `Build Path` > `Add External Archives`.
   - Navegue até a pasta onde você extraiu o driver JDBC e selecione o arquivo `.jar` correspondente (por exemplo, `mysql-connector-java-x.x.x.jar`).
   - Clique em `Open` para adicionar a biblioteca ao seu projeto.

  Agora você está pronto para desenvolver seu projeto utilizando o MySQL com JDBC!

# Clonando o Projeto
  Para clonar o projeto do repositório no GitHub, siga estas etapas:

### 1. Abra o Git Bash:
   - Navegue até a pasta onde você criou o projeto no Eclipse. Você pode abrir o Git Bash diretamente nessa pasta:
     - Clique com o botão direito na pasta do projeto (por exemplo, `CarrinhoDeCompras`) e selecione a opção "Git Bash Here".

### 2. Clonando o Repositório:
   - Execute o seguinte comando no Git Bash para clonar o projeto:    
     "git clone https://github.com/DallaProRacing/Desafio-1.git"
        
   - Isso criará uma cópia do repositório em sua máquina local, permitindo que você trabalhe no projeto.

# Configurando a Conexão com o Banco de Dados
  Após clonar o projeto, você precisa configurar o arquivo db.properties (sublinhado na imagem) para conectar o sistema ao seu banco de dados MySQL. Siga as instruções abaixo para inserir as informações corretas:
- Localize o arquivo db.properties no diretório do projeto. Este arquivo contém as configurações de conexão com o banco de dados.
- Edite o arquivo db.properties e preencha os seguintes campos com as informações do seu banco de dados:
  - db.url: URL do banco de dados (por exemplo, jdbc:mysql://localhost:3306/nome_do_banco).
  - db.username: Nome de usuário do MySQL (geralmente root).
  - db.password: Senha do MySQL.

# Valores de Entrada para Testes
Para acessar valores de entrada que podem ser utilizados para testar o programa principal, acesse este link: https://docs.google.com/document/d/1fTp79TX26XtHOv3h6WRDtFG3MWErPfvu9XPrSOIukGI/edit?usp=sharing
