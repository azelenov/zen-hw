This is a example of BDD tests for Zendesk tickets API
==============================

##Configuration
For running tests please create admin.properties file in /src/test/resources/user/

Example can be found in example.properties.

Run script/smoke.sh to verify that configuration is correct


##Framework used:
 *  *Cucumber & Zucchini* for behavior-driven test
https://cukes.info/step-definitions.html

 * *rest-assured* for handling requests.
https://code.google.com/p/rest-assured/wiki/Usage


##How to run 

#Test runner
1. Run a file /src/test/java/org/zendesk/testing/TestRunner.java

#Intellij Idea
1. Open any feature file in  test/resources/feature
2. Do right-click on the scenario and select option 'Run scenario'

#Maven
Run 'mvn clean install' from project root


##Report
HTML report will be generated here:
**//target/zucchini-reports/feature-overview.html**
