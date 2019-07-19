# Repo Maven Plugin

The plugin is created so you can search for Maven dependencies in the terminal, list their versions and even add them to your current project's pom.xml. 

All of this without opening your Browser or IDE.

## How to use

### This plugin is not yet in maven central, so first install it locally

`git clone https://github.com/bgjug/repo-maven-plugin.git`

`cd repo-maven-plugin`

`mvn clean install`

Now as you have it in your local repository, you can start using it.


### Searching an artifact

`mvn bg.jug:repo-maven-plugin:1.0-SNAPSHOT:search -Dquery=jstl`

This will list all artifacts containing `jstl` with their group id, artifact id and latest version, limiting the results count to 20.


```
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
``` 

You can also pass the `-Dfull` parameter for better looks and for more information:

`mvn bg.jug:repo-maven-plugin:1.0-SNAPSHOT:search -Dquery=jstl -Dfull`

Result :

```
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
....
```
As you may notice, the last line of each result is the command to add that dependency to your local project.

### List artifact versions

`mvn bg.jug:repo-maven-plugin:1.0-SNAPSHOT:versions -Dquery=jstl:jstl`

This will list all versions for the `group:artifact` you specified.
Result:

```
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
```

### Add dependency to your current project

`mvn bg.jug:repo-maven-plugin:1.0-SNAPSHOT:install -Dgav=jstl:jstl:1.2`

Will add `jstl` to your current pom.xml

````
[INFO] --- repo-maven-plugin:1.0-SNAPSHOT:install (default-cli) @ repo-maven-plugin ---
[INFO] Dependency jstl:jstl:1.2 was successfully added
````

If you execute

`git status`

Result:

```
> git status                         

On branch master
Your branch is up to date with 'origin/master'.

Changes not staged for commit:
  (use "git add <file>..." to update what will be committed)
  (use "git checkout -- <file>..." to discard changes in working directory)

	modified:   pom.xml
```

> **NOTE**: The formatting of the pom.xml will be lost and all the comment :(
