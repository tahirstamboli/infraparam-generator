# infraparam-generator
Convert terraform.tfstate to infraParam.json 

## How to use this utility ?

### How to build ?

Execute **build.sh** file to build this utility which will generate fat jar named **infraparam-generator-0.0.1.jar**. You can use this jar to convert terraform.tfstate file to infraParam.json.

### How to run ?

Use following command to run utility jar

```
java -jar infraparam-generator-0.0.1.jar --tfstate-file-path=/tmp/terraform.tfstate --infraparam-output-path=/tmp
```
After successful execution of above command **infraParam.json** file is generated at --infraparam-output-path
