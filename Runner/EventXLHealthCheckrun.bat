set ProjectPath=N:\QA\LiveWorkSpace\HealthCheck
echo %ProjectPath%
set classpath=%ProjectPath%\bin;%ProjectPath%\lib\*
echo %classpath%
java org.testng.TestNG %ProjectPath%\ALLAPPS.xml

