# modelflow-maven-plugin

This plugin allows the execution of modelFlow from Maven

## Installation

```
mvn clean install
```

## Usage

```
<build>
	<plugins>
		<plugin>
			<groupId>org.epsilonlabs.modelflow</groupId>
			<artifactId>maven-modelflow-plugin</artifactId>
			<version>1.0-SNAPSHOT</version>
			<executions>
				<execution>
					<configuration>
						<src>${project.basedir}/src/main/resources/</src>
					</configuration>
					<goals>
						<goal>build</goal>
					</goals>
				</execution>
			</executions>
		</plugin>
	</plugins>
</build>
```