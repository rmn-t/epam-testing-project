Steps to create a project in IntelliJ IDEA Community Edition

1. Open IntelliJ IDEA

2. File > New > Project from Existing Sources...

3. Pick up pom.xml in the tree > Ok

4. In the dialog: Import Maven Project, check up:
	+ Search for project recursively
	+ Import project automatically
	Automatically download
	+ Documentation
	? Sources
  Press: Next

5. Follow wizard and check up all CheckBoxes

6. In the last dialog type: <Your Project Name>

7. Press: Finish

8. See: Maven Standard Directory Layout 
   https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html

   See: Tomcat 7 Goals
   http://tomcat.apache.org/maven-plugin-2.0/tomcat7-maven-plugin/plugin-info.html

9. Press: Add configuration

10. In the dialog "Run/Debug Configuration":
	Press: +
	Choice: Maven
	In the "Command Line" type: tomcat7:run
	(this will run project in internal Tomcat 7)

	In the "Before lunch" group of this window press: + 
	Choice: Run Maven Goal
	Type: compiler:compile
	Press: Ok

	In the "Before lunch" group of this window press: +
	Choice: Run Maven Goal
	Type: war:war
	Press: Ok

11. Ok

Enjoy

P.S.
--------------
Any changes, except JSP, require restarting the application.
