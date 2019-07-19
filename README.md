# Repo Maven Plugin

The plugin is created so you can search via terminal the search.maven.org, you can list versions, search for artifacts and even install an artifact into your current folder pom.xml. 

All of this without opening your Browser or IDE.

## How to use

### Not yet in maven central, so install it locally

``git clone https://github.com/bgjug/repo-maven-plugin.git``

``cd repo-maven-plugin``

``mvn clean install``

Now you have it in your local repository and you can start using it.


### Search for an artifact

``mvn bg.jug:repo-maven-plugin:1.0-SNAPSHOT:search -Dquery=jstl``

This will list all artifact containing jstl with their group id artifact id and latest version, limiting the results count to 20.



````
...
[INFO] Executing Search Query: 'jstl'
[INFO] Results:
jstl:jstl:1.2
============================================
javax.servlet.jsp.jstl:jstl:1.2
============================================
javax.servlet:jstl:1.2
============================================
org.glassfish.web:jstl:1.2
============================================
org.apache.geronimo.bundles:jstl:1.2_1
============================================
com.github.demidenko05:a-apache-jstl:1.0.5
============================================
marmalade:marmalade-tags-jstl-core:1.0-alpha2
============================================
jakarta.servlet.jsp.jstl:jakarta.servlet.jsp.jstl-api:1.2.5
============================================
javax.servlet.jsp.jstl:javax.servlet.jsp.jstl-api:1.2.2
============================================
javax.servlet.jsp.jstl:jstl-api:1.2-rev-1
============================================
com.liferay:javax.servlet.jsp.jstl:1.2.3.LIFERAY-PATCHED-2
============================================
org.glassfish.web:jstl-connector:10.0-b28
============================================
org.glassfish:javax.servlet.jsp.jstl:10.0-b28
============================================
org.jboss.spec.javax.servlet.jstl:jboss-jstl-api_1.2_spec:1.1.4.Final
============================================
org.eclipse.jetty:apache-jstl:10.0.0-alpha0
============================================
org.netbeans.modules:org-netbeans-libs-jstl:RELEASE110
============================================
org.glassfish.main.web:jstl-connector:5.1.0
============================================
org.glassfish.web:jakarta.servlet.jsp.jstl:1.2.6
============================================
com.github.slugify:slugify-integration-jstl:2.3
============================================
org.glassfish.web:javax.servlet.jsp.jstl:1.2.5
============================================
```` 

You can also pass the -Dfull parameter for better looking and more information.


``mvn bg.jug:repo-maven-plugin:1.0-SNAPSHOT:search -Dquery=jstl -Dfull``

Result :

````
[INFO] Executing Search Query: 'jstl'
[INFO] Results:
jstl:jstl:1.2
g:jstl
a:jstl
latestVersion:1.2
repositoryId:central
p:jar
mvn bg.jug:repo-maven-plugin:1.0-SNAPSHOT:install -Dgav=jstl:jstl:1.2
============================================
javax.servlet.jsp.jstl:jstl:1.2
g:javax.servlet.jsp.jstl
a:jstl
latestVersion:1.2
repositoryId:central
p:N/A
mvn bg.jug:repo-maven-plugin:1.0-SNAPSHOT:install -Dgav=javax.servlet.jsp.jstl:jstl:1.2
============================================
javax.servlet:jstl:1.2
g:javax.servlet
a:jstl
latestVersion:1.2
repositoryId:central
p:jar
mvn bg.jug:repo-maven-plugin:1.0-SNAPSHOT:install -Dgav=javax.servlet:jstl:1.2
============================================
org.glassfish.web:jstl:1.2
g:org.glassfish.web
a:jstl
latestVersion:1.2
repositoryId:central
p:pom
mvn bg.jug:repo-maven-plugin:1.0-SNAPSHOT:install -Dgav=org.glassfish.web:jstl:1.2
============================================
org.apache.geronimo.bundles:jstl:1.2_1
g:org.apache.geronimo.bundles
a:jstl
latestVersion:1.2_1
repositoryId:central
p:bundle
mvn bg.jug:repo-maven-plugin:1.0-SNAPSHOT:install -Dgav=org.apache.geronimo.bundles:jstl:1.2_1
============================================
com.github.demidenko05:a-apache-jstl:1.0.5
g:com.github.demidenko05
a:a-apache-jstl
latestVersion:1.0.5
repositoryId:central
p:pom
mvn bg.jug:repo-maven-plugin:1.0-SNAPSHOT:install -Dgav=com.github.demidenko05:a-apache-jstl:1.0.5
============================================
marmalade:marmalade-tags-jstl-core:1.0-alpha2
g:marmalade
a:marmalade-tags-jstl-core
latestVersion:1.0-alpha2
repositoryId:central
p:jar
mvn bg.jug:repo-maven-plugin:1.0-SNAPSHOT:install -Dgav=marmalade:marmalade-tags-jstl-core:1.0-alpha2
============================================
jakarta.servlet.jsp.jstl:jakarta.servlet.jsp.jstl-api:1.2.5
g:jakarta.servlet.jsp.jstl
a:jakarta.servlet.jsp.jstl-api
latestVersion:1.2.5
repositoryId:central
p:jar
mvn bg.jug:repo-maven-plugin:1.0-SNAPSHOT:install -Dgav=jakarta.servlet.jsp.jstl:jakarta.servlet.jsp.jstl-api:1.2.5
============================================
javax.servlet.jsp.jstl:javax.servlet.jsp.jstl-api:1.2.2
g:javax.servlet.jsp.jstl
a:javax.servlet.jsp.jstl-api
latestVersion:1.2.2
repositoryId:central
p:jar
mvn bg.jug:repo-maven-plugin:1.0-SNAPSHOT:install -Dgav=javax.servlet.jsp.jstl:javax.servlet.jsp.jstl-api:1.2.2
============================================
javax.servlet.jsp.jstl:jstl-api:1.2-rev-1
g:javax.servlet.jsp.jstl
a:jstl-api
latestVersion:1.2-rev-1
repositoryId:central
p:jar
mvn bg.jug:repo-maven-plugin:1.0-SNAPSHOT:install -Dgav=javax.servlet.jsp.jstl:jstl-api:1.2-rev-1
============================================
com.liferay:javax.servlet.jsp.jstl:1.2.3.LIFERAY-PATCHED-2
g:com.liferay
a:javax.servlet.jsp.jstl
latestVersion:1.2.3.LIFERAY-PATCHED-2
repositoryId:central
p:jar
mvn bg.jug:repo-maven-plugin:1.0-SNAPSHOT:install -Dgav=com.liferay:javax.servlet.jsp.jstl:1.2.3.LIFERAY-PATCHED-2
============================================
org.glassfish.web:jstl-connector:10.0-b28
g:org.glassfish.web
a:jstl-connector
latestVersion:10.0-b28
repositoryId:central
p:hk2-jar
mvn bg.jug:repo-maven-plugin:1.0-SNAPSHOT:install -Dgav=org.glassfish.web:jstl-connector:10.0-b28
============================================
org.glassfish:javax.servlet.jsp.jstl:10.0-b28
g:org.glassfish
a:javax.servlet.jsp.jstl
latestVersion:10.0-b28
repositoryId:central
p:jar
mvn bg.jug:repo-maven-plugin:1.0-SNAPSHOT:install -Dgav=org.glassfish:javax.servlet.jsp.jstl:10.0-b28
============================================
org.jboss.spec.javax.servlet.jstl:jboss-jstl-api_1.2_spec:1.1.4.Final
g:org.jboss.spec.javax.servlet.jstl
a:jboss-jstl-api_1.2_spec
latestVersion:1.1.4.Final
repositoryId:central
p:jar
mvn bg.jug:repo-maven-plugin:1.0-SNAPSHOT:install -Dgav=org.jboss.spec.javax.servlet.jstl:jboss-jstl-api_1.2_spec:1.1.4.Final
============================================
org.eclipse.jetty:apache-jstl:10.0.0-alpha0
g:org.eclipse.jetty
a:apache-jstl
latestVersion:10.0.0-alpha0
repositoryId:central
p:jar
mvn bg.jug:repo-maven-plugin:1.0-SNAPSHOT:install -Dgav=org.eclipse.jetty:apache-jstl:10.0.0-alpha0
============================================
org.netbeans.modules:org-netbeans-libs-jstl:RELEASE110
g:org.netbeans.modules
a:org-netbeans-libs-jstl
latestVersion:RELEASE110
repositoryId:central
p:jar
mvn bg.jug:repo-maven-plugin:1.0-SNAPSHOT:install -Dgav=org.netbeans.modules:org-netbeans-libs-jstl:RELEASE110
============================================
org.glassfish.main.web:jstl-connector:5.1.0
g:org.glassfish.main.web
a:jstl-connector
latestVersion:5.1.0
repositoryId:central
p:glassfish-jar
mvn bg.jug:repo-maven-plugin:1.0-SNAPSHOT:install -Dgav=org.glassfish.main.web:jstl-connector:5.1.0
============================================
org.glassfish.web:jakarta.servlet.jsp.jstl:1.2.6
g:org.glassfish.web
a:jakarta.servlet.jsp.jstl
latestVersion:1.2.6
repositoryId:central
p:jar
mvn bg.jug:repo-maven-plugin:1.0-SNAPSHOT:install -Dgav=org.glassfish.web:jakarta.servlet.jsp.jstl:1.2.6
============================================
com.github.slugify:slugify-integration-jstl:2.3
g:com.github.slugify
a:slugify-integration-jstl
latestVersion:2.3
repositoryId:central
p:jar
mvn bg.jug:repo-maven-plugin:1.0-SNAPSHOT:install -Dgav=com.github.slugify:slugify-integration-jstl:2.3
============================================
org.glassfish.web:javax.servlet.jsp.jstl:1.2.5
g:org.glassfish.web
a:javax.servlet.jsp.jstl
latestVersion:1.2.5
repositoryId:central
p:jar
mvn bg.jug:repo-maven-plugin:1.0-SNAPSHOT:install -Dgav=org.glassfish.web:javax.servlet.jsp.jstl:1.2.5
============================================
````

As you may notice the last line for each result is the command to install it in your local project

### List artifact versions

``mvn bg.jug:repo-maven-plugin:1.0-SNAPSHOT:versions -Dquery=jstl:jstl``

This will list all versions for the group:artifact you specified.
Result:

````
[INFO] Executing Versions Query: 'jstl:jstl' with size of 20
[INFO] use -Dsize=number if you want to see less results (max 20)
[INFO] Versions:
jstl:jstl
1.2
1.1.1
1.1.0
1.0.2
1.0
1.0.6
1.0.1
1.0.5
1.0.4
1.1.2
1.0.3
============================================
````

### Install dependency in your current project


