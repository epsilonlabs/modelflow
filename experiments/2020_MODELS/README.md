# Performance Experiment

Experiment performed for MODELS 2020 which compares the performance of our Gradle implementation of conservative model management worklfows against ModelFlow.

## Results

The results directory contains two files: one for gradle and one for modelflow.
Each of these has the following headers 

- **approach**: The name of the approach i.e. Gradle or ModelFlow
- **scenarioId**: The id of the scenario see below
- **iteration**: the number of the iteration. The five warmup iterations are not captured
- **duration(nanosec)**: the total execution duration in nano seconds
- **evl**: the execution of the evl task in nano seconds. Only ModelFlow
- **etl**: the execution of the etl task in nano seconds. Only ModelFlow
- **egx**: the execution of the exg task in nano seconds. Only ModelFlow

### Scenarios
1. Clean
2. No changes
3. Source model modified
4. Intermediate model modified
5. Templates modified
6. Non Protected outputs modified
7. Protected outputs modeified

## Reproducing the experiment

### ModelFlow pre-requisites

- Java 1.8
- Maven
- Eclipse

To run the experiment with modelflow you'll need to be using an Eclipse instance where all ModelFlow plugins are either imported as project. 

To import them go to `File > import > existing project into workspace` and select all plugins from the plugins, setup and releng folders at the root of this repository. Ensure that the modelflow target platform is active to resolve all compilation errors.

In the root of the project installation, run 
```
mvn clean install
```

### Gradle pre-requisites

- Java 1.8
- Maven
- Eclipse
- Gradle

Import the plugin project `org.eclipse.epsilon.gradle`under `/integrations/plugins/`. 

To make these plugins available, using the gradle toolkit, execute the publishLocal task. 

To ensure your sample gradle workflow runs, update the `settings.gradle` and `build.gradle` of the `gradle.workflow` project. For the `settings.gradle` file copy the following contents replacing `${user.home}` with your user home :

```
pluginManagement {
  	repositories {
      	maven {
	        url '${user.home}/.m2/repository/'
    	}
    } 
}
```

For the build.gradle add the lines below at the start of the file (before the `plugins` environment). Once more, replace ${user.home} with the value of your user home. Do an examination of your local `${user.home}/.m2/repository/p2/osgi/bundle` directory, if you have any eclipse epsilon plugins that are not version 1.5.1 then add the exclude statement below with the version found in your local installation, otherwise leave these clause out.

```
buildscript{
	repositories {
	    flatDir {
	   		dirs '${user.home}/.m2/repository/p2/osgi/bundle'
		}
		mavenLocal()
	    jcenter()
	}
	dependencies{
		classpath fileTree(dir: '${user.home}/.m2/repository/p2/osgi/bundle', include: ['**/**/*.jar'] /*, exclude: ['org.eclipse.epsilon.*/1.6.0.202001152013/']) */ // The exclude is optional and should match the version in your installation
	}
}
```

### Running the experiments

The modelflow experiment is located in the `org.epsilonlabs.modelflow.experiment.models.modelflow` project, under `/experiments/2020_MODELS/plugins/`. The class to run is `gradle.modelflow.ModelFlowPerformanceExperiment`. It will need to be run as a `JUnit test`.

The gradle experiment is located in the `org.epsilonlabs.modelflow.experiment.models.gradle` project, under `/experiments/2020_MODELS/plugins/`. The class to run is `gradle.modelflow.GradlePerformanceExperiment`. It will need to be run as a `Plugin test`.

The results will be produced in the `results` folder of the project containing the test.