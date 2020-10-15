[![CircleCI](https://circleci.com/gh/epsilonlabs/modelflow.svg?style=svg)](https://circleci.com/gh/epsilonlabs/modelflow)
# ModelFlow
A conservative model management workflow execution language.

:warning: This project is work in progress.

![Screenshot](img/workspace.png)

## Quick links
- [Wiki](https://github.com/epsilonlabs/modelflow/wiki)
- [MODELS2020](https://github.com/epsilonlabs/modelflow/tree/master/experiments/2020_MODELS)

## Installation

### Updatesite 
You can generate the updatesite by cloning the project and running:
```
mvn clean verify
```
Then finding the updatesite inside `releng/org.epsilonlabs.modelflow.updatesite/target/updatesite.zip`

#### Dependencies
Your eclipse must have installed plugins from the following updatesites:

- http://download.eclipse.org/emfatic/update
- http://download.eclipse.org/mmt/qvto/updates/releases
- https://download.eclipse.org/modeling/gmp/gmf-tooling/updates/releases
- https://download.eclipse.org/releases/2020-03
- https://download.eclipse.org/tools/orbit/downloads/drops/R20200831200620/repository
- https://download.eclipse.org/mmt/atl/updates/releases/4.2.1
- https://download.eclipse.org/epsilon/updates/2.1

## Project Structure

- **examples** - Contains Eclipse projects that demonstrate how to use the ModelFlow language
- **integrations** - Configuration of integrations e.g. Maven Plugins
- **experiments** - Experments used for research publications
- **features** - Eclipse features 
- **plugins** - Core development units of the language and the task/model extensions.
- **releng** - Updatesite (for downloading) and Targetplatform (for development)
- **setup** - Other dependencies
- **tests** - Integration and Unit tests of the language
