This is an example of BDD tests for Zendesk tickets API
==============================

# Configuration
For running tests please create *admin.properties* file in /src/test/resources/user/

Example can be found in example.properties.
More details here: https://developer.zendesk.com/rest_api/docs/core/introduction

Please run script/smoke.sh to verify that configuration is correct.

# Requrements
 * Java 8
 * mvn (Apache Maven)

# Framework used:
 *  *Cucumber* for behavior-driven tests
https://cukes.info/step-definitions.html

 * *Rest-Assured* for handling requests.
https://code.google.com/p/rest-assured/wiki/Usage

 * *Apache Maven* as build tool
 https://maven.apache.org/

# How to run 

## Intellij Idea
1. Open any feature file in  test/resources/feature
2. Do right-click on the scenario and select option 'Run scenario'

## Maven
Run 'mvn clean install' from project root
The tests will be paralleled by scenarios.


## Report
For viewing the report please run:
`scripts/report.sh`

or open it from here:
**/cucumber-reports/cucumber-html-reports/overview-features.html **
