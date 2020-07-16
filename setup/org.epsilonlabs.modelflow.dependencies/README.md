org.epsilonlabs.modelflow.engine.dependencies
===
This project downloads dependencies entered in the `pom.xml` into the `lib` folder and re-exports the dependencies for other ModelFlow plugins to use.

Run
-

	1. Update the `pom.xml` file with the dependencies to include
	2. Execute from the command line `download-dependencies.sh`. This will update the .classpath file
	3. Refresh the eclipse plugin
	4. Ensure that all dependencies are re-exported in the `MANIFEST.MF`
	
