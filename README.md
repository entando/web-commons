# Entando WEB Commons
Utilities for services built on spring boot

# Installation
Just add the dependency on your `pom.xml` file
```
<dependency>
    <groupId>com.entando</groupId>
    <artifactId>web-commons</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

# Configuration
Add these configurations to your `application.properties` file

### To enable Keycloak security
```
keycloak.enabled=true
keycloak.auth-server-url=${KEYCLOAK_AUTH_URL:http://keycloak.url.here/auth}
keycloak.realm=${KEYCLOAK_REALM:realm-here}
keycloak.resource=${KEYCLOAK_CLIENT_ID:client-id}
keycloak.credentials.secret=${KEYCLOAK_CLIENT_SECRET}
keycloak.ssl-required=external
keycloak.public-client=false
```

### To enable swagger generation and `swagger-ui`
```
swagger.enabled=true
swagger.info.title=${pom.name:Project Title Here}
swagger.info.description=${pom.description:Put the default description here}
swagger.info.version=${pom.version:Dev}
swagger.info.contact.name=
swagger.info.contact.email=
swagger.info.license=
swagger.info.licenseUrl=
swagger.info.termsOfServiceUrl=
swagger.info.basePackage=
```


### Exceptions
This project has some common exceptions to throw on your controllers and services.
There's no need to use ResponseEntity to control HTTP status unless you want to use Headers, etc.

#### NotFoundException
Throw this Exception to return 404.

```
throw new NotFoundException("com.entando.someThingNotFound");
```

#### HttpException
Throw this Exception if you want to return another HTTP status code.

```
throw new HttpException(HttpStatus.BAD_REQUEST, "com.entando.badRequest");
```

#### messages.properties
The message passed as argument to these exceptions are i18n in the `messages.properties` file.

```
com.entando.someThingNotFound=Something couldn't be found
com.entando.badRequest=Request is invalid, something is missing
```

Common codes
```
com.entando.error.validationError=Validation error
com.entando.error.permissionDenied=Permission denied
com.entando.error.unauthorized=Unauthorized
com.entando.error.internalServerError=Internal server error
```