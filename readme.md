Todos os comandos a seguir devem ser executados na linha de comando. Não é necessário permissão de administrador.

# Criação de Usuário e DB (Postgresql)

$POSTGRES_HOME é o diretório de instalação do banco de dados Postgresql.

Ao executar o comando a seguir a primeira senha solicitada será a do usuário sendo criado. A segunda senha solicitada é a do usuário postgres (administrador).

    $POSTGRES_HOME/bin/createuser.exe --interactive -U postgres -P

O nome do usuário criado e a senha cadastrada serão referenciados como $NOME_USUARIO e $SENHA_USUARIO, respectivamente, nas configurações seguintes.


# Criação do banco de dados (Postgresql)

O nome do banco de dados criado será referenciado como $NOME_DATABASE nas configurações seguintes.

    $POSTGRES_HOME/bin/createdb.exe -U postgres -O $NOME_USUARIO $NOME_DATABASE


# Configuração de Usuário (Wildfly)

Management users tem acesso ao console web. Aplications users não.

$WILDFLY_HOME é o diretório de instalação do Wildfly.

    $WILDFLY_HOME/bin/add-user.[bat|sh]


# Configuração de DataSource (Wildfly)

Fazer o download do driver jdbc postgresql. O diretório onde esse arquivo ``.jar`` foi salvo será referenciado como $DIRETORIO.

$VERSAO é versão númerica do driver jdbc postgresql.

Os comandos a seguir servem para configurar o wildfly:

    # POSTGRES_DRIVER_PATH= DB_BAME= DB_USER= DB_PASS= sh jboss-cli.sh --connect --file=jboss-config.cli

    # copia o driver postgresql.jar para o wildfly
    module add --name=org.postgresql --resources=${env.POSTGRES_DRIVER_PATH} --dependencies=javax.api,javax.transaction.api

    # registra o driver no arquivo de configuração standalone-full.xml
    /subsystem=datasources/jdbc-driver=postgres:add(driver-name="postgres",driver-module-name="org.postgresql",driver-class-name="org.postgresql.Driver")

    # registra o datasource no arquivo de configuração standalone-full.xml
    data-source add --jndi-name=java:jboss/datasources/PostgresDS --name=PostgresDS --connection-url=jdbc:postgresql://localhost:5432/${env.DB_BAME} --driver-name=postgres --user-name=${env.DB_USER} --password=${env.DB_PASS}

    # Desativa 'integrated-jaspi'
    /subsystem=undertow/application-security-domain=other:write-attribute(name=integrated-jaspi, value=false)

    # ativa leitura de variáveis de ambiente
    /subsystem=ee:write-attribute(name=spec-descriptor-property-replacement, value=true)

    :reload


O valor do atributo ``jndi-name`` é utilizado no arquivo ``persistence.xml``:

    <jta-data-source>java:jboss/datasources/PostgresDS</jta-data-source>
