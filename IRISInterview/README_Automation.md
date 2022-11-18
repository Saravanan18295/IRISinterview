# IRISinterview

**Java:** Implemented JDK 11 and OOPS concepts playing a huge role in this framework.

**Implemented Browsers:**
Implemented webdrivermanager it supports the following browsers and automatically downloads OS specific binaries for. I have also executed and  the reports are available in the repo.

Chrome ./gradlew clean test -Dbrowser=chrome
Firefox ./gradlew clean test -Dbrowser=firefox
Opera ./gradlew clean test -Dbrowser=opera
Edge ./gradlew clean test -Dbrowser=edge (will work on windows machines only)
Phantomjs ./gradlew clean test -Dbrowser=phantom (headless)

**Page Object Model:**
Used Page Object Model to handle the locators in separate java classes based on the web page.

Data Driven and Keyword Driven: 
Have implemented Data Driven and Keyword framework. Main scope of this combination is, if we want to change the scenario, we can simply change the datasheet and non-technical folks can easily handle this automation.

**Screenshots:**
On test passes/failures screenshots will automatically be taken and stored under Screenshots folder. The screenshot files will be named with time and the date. It will place in the different folders based on the status.

**Synchronize:**
We have four types of selenium waits and her i used Explicit wait to check presence, visibility, locate and interact.

**Maven:**
Maven is written in Java and is used as a build management tool. So that anyone can easily use the project without downloading any  dependency manually.

**TestNG:**
Also implemented the TestNG (TDD) as it has lot of features like Cross browser, Annotations, Reports and etc.

**Assertions:**
Used Assertions to verify the conditions with minimal code development.

**Console Output:**
To get an even more clear overview of the test execution the project uses the TestLoggerPlugin to pretty print executed tests and the logs will capture in a text file to share with clients.
Also used Extend Report in the framework.

Note:: Haven't used cucumber (BDD) as i'm not sure whether you will have cucumber feature and editor plugins. It has some issues to configure with Eclipse nowadays.