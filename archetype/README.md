# Step to run archetype
- navigate to /archetype
- run ```mvn install```
- then run ```
           mvn archetype:generate \
             -DarchetypeGroupId=com.wk \
             -DarchetypeArtifactId=sample-ms-archetype \
             -DarchetypeVersion=0.0.2-SNAPSHOT \
             -DgroupId=com.wk \
              -DartifactId=casa-service
           ```