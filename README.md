# Karaf Example for connecting to Database using datasource

This example uses Fuse 6.3 standalone installation.

## Database Pre-requisite
This example is created for connecting to Mysql db and fetching data from users table.
1. Create a users table in mysql db
```
CREATE TABLE users (
    username varchar(255),
    password varchar(255)
);
```
2. Insert a record in the users table
```
INSERT INTO users (username, password)
VALUES ('admin', 'admin');
```
## Exposing datasource
1.  Create datasource blueprint xml 
```
<blueprint xmlns="http://www.osgi.org/xmlns/blueprint/v1.0.0">
	<bean id="dataSource" class="com.mysql.cj.jdbc.MysqlDataSource">
	    <property name="url" value="jdbc:mysql://127.0.0.1:3306/testdb" />
	    <property name="user" value="dbuser1" />
	    <property name="password" value="dbuser1password" />
	</bean>

	<service ref="dataSource" interface="javax.sql.DataSource">
	    <service-properties>
		<entry key="osgi.jndi.service.name" value="jdbc/testdb" />
		<entry key="osgi.service.blueprint.compname" value="mysql-ds"/>
		<entry key="datasource.name" value="mysql-ds"/>
	    </service-properties>
	</service>
</blueprint>
```
2. Deploy this datasource blueprint in fuse

## Pre-requisite
1. Install camel-sql feature
2. Install camel-jetty feature

## Code compilation
Use the following command to package the code and create the bundle
```
mvn clean package
```
## Deploy the jar into Fuse 6.3
1. Go to the Karaf console
2. Use the following command to deploy the jar
```
osgi:install -s file:{{Path to the jar file}}/karaf-camel-db-configuration-1.0-SNAPSHOT.jar
```
3. Verify the log file that the jar is installed correctly and rest endpoint is exposed 
```
2022-03-01 12:07:25,273 | INFO  | nt Dispatcher: 1 | Server                           | 94 - org.eclipse.jetty.util - 9.2.23.v20171218 | Started @8198873ms
2022-03-01 12:07:25,273 | INFO  | nt Dispatcher: 1 | BlueprintCamelContext            | 232 - org.apache.camel.camel-core - 2.17.0.redhat-630515 | Route: route7 started and consuming from: Endpoint[jetty:http://localhost:9091/say/hello?httpMethodRestrict=GET]

```

## Verification

To verify that the DB is accessed succesfully, use the below command
```
curl http://localhost:9091/say/hello

```
Output should be like below
```
[{username=admin, password=admin}]
```
