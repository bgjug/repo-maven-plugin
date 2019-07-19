# Repo Maven Plugin

The plugin is created so you can search via terminal the search.maven.org, you can list versions, search for artifacts and even install an artifact into your current folder pom.xml. 

All of this without opening your Browser or IDE.

## How to use

### Not yet in maven central, so install it locally

git clone https://github.com/bgjug/repo-maven-plugin.git

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
