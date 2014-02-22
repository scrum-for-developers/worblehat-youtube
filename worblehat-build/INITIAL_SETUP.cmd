cd %~dp0%
copy hgrc.template ..\.hg\hgrc /y
call mvn clean install eclipse:eclipse
