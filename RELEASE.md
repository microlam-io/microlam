# Release

```bash.sh
mvn -X clean -DdryRun=false  -Dresume=false release:prepare release:perform
```

1. microlam-bom (using all expected future versions)

2. microlam

3. microlam-aws-devops

4. microlam-lambda-quickstart


## Update child module version

Modify only parent version then:

```
mvn versions:update-child-modules
```


## Dealing with expired gpg key

see [doc](https://central.sonatype.org/publish/requirements/gpg/#dealing-with-expired-keys)
 
