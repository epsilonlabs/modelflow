#!/bin/bash
# This script uses maven to download the dependencies and then copies them to the lib folder

# Use latest versions
# mvn versions:display-property-updates -Dversions.allowSnapshots=true -DallowMinorUpdates=false -DallowMajorUpdates=false -DallowIncrementalUpdates=false
# mvn versions:update-properties -Dversions.allowSnapshots=true -DallowMinorUpdates=false -DallowMajorUpdates=false -DallowIncrementalUpdates=false

rm -rf lib/
mvn clean dependency:copy-dependencies@dependencies dependency:copy-dependencies@sources

# Add the new dependencies to the classpath. Crate then entry list in a separate file since modifying the .classpath file breakes eclipse
rm -f jarEntries.tmp
suff=-sources.jar

echo "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" >> jarEntries.tmp
echo "<classpath>" >> jarEntries.tmp
echo "	<classpathentry kind=\"con\" path=\"org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/JavaSE-1.8\"/>" >> jarEntries.tmp
echo "	<classpathentry kind=\"con\" path=\"org.eclipse.pde.core.requiredPlugins\"/>" >> jarEntries.tmp

for i in lib/*.jar; do
    name="${i%.*}"
    fullname=$(cd "$(dirname "$i")"; pwd)/$(basename "$i")
    fullname="${fullname%.*}"
    if [[ $name != *"-sources" ]]; then
        #echo $fullname
        if [ -f "$fullname-sources.jar" ]; then
            echo "    <classpathentry exported=\"true\" kind=\"lib\" path=\"$name.jar\" sourcepath=\"$name-sources.jar\"/>" >> jarEntries.tmp
        else
            echo "    <classpathentry exported=\"true\" kind=\"lib\" path=\"$name.jar\" />" >> jarEntries.tmp
        fi
    fi
done

echo "	<classpathentry kind=\"output\" path=\"target/classes\"/>" >> jarEntries.tmp
echo "</classpath>" >> jarEntries.tmp

cp -f jarEntries.tmp .classpath
rm -f jarEntries.tmp