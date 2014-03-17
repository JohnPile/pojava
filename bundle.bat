rem My sincere apologies for this brutish Windows-centric hack.
rem This creates an incomplete bundle file, then re-creates all the jars and signs them.
rem Finally, it updates the bundle file with all the resources and their signatures.
call mvn clean repository:bundle-create install
cd target
for /f "delims=" %%a in ('ls *bundle.jar') do @set bundle=%%a
jar -uf %bundle% *-javadoc.jar* *-sources.jar* *-3.?.?.*
cd