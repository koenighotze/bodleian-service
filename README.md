# Bodleian Service

[![Build main](https://github.com/koenighotze/bodleian-service/actions/workflows/build-and-deploy.yml/badge.svg)](https://github.com/koenighotze/bodleian-service/actions/workflows/build-and-deploy.yml)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/e7009b9423674580a8f69c4561197580)](https://app.codacy.com/gh/koenighotze/bodleian-service/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade)
[![Codacy Badge](https://app.codacy.com/project/badge/Coverage/e7009b9423674580a8f69c4561197580)](https://app.codacy.com/gh/koenighotze/bodleian-service/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_coverage)
[![GitGuardian scan](https://github.com/koenighotze/bodleian-service/actions/workflows/git-guardian-scan.yml/badge.svg)](https://github.com/koenighotze/bodleian-service/actions/workflows/git-guardian-scan.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

The backend service for library management. It exposes a REST API for managing
library related resources.

## Bucket list Kotlin / SpringBoot Version

### Domain

* [ ] Integrate extra service for book images
* [ ] Fetch https://openlibrary.org/books/OL7353617M.json
* [ ] Wishlist Feature
* [ ] Owned Feature

### Tech

* [ ] Open Telemetry
* [ ] Deployment
* [ ] Database (pot. self managed as a container?)
* [ ] Auth
* [x] Testing
* [ ] Load testing
* [x] DB Setup
* [x] Dockerfile / not needed we use build packs
* [ ] API Testing
* [x] JSON Logging
* [x] Split Slow Tests (only in CI)
* [ ] Linting
* [ ] Deployment
* [ ] Swagger
* [ ] Flux / Coroutines / suspend
* [x] Auf Sha in CI umstellen
* [x] Sig check mit Gradle
* [x] Signed commit
* [ ] Transactions
* [ ] Rest assured
* [ ] Cosign
* [x] Distroless base image
* [ ] Use Webclient
* [ ] Mutation testing with https://medium.com/seat-code/mutation-testing-in-kotlin-a8834771e85e

### Reading

https://stackoverflow.com/questions/38516418/what-is-proper-workaround-for-beforeall-in-kotlin
https://www.baeldung.com/kotlin/exception-handling
https://blog.rockthejvm.com/functional-error-handling-in-kotlin/
https://docs.gradle.org/current/userguide/dependency_verification.html
https://docs.gradle.org/current/userguide/dependency_verification.html#sec:bootstrapping-signature-verification
https://www.baeldung.com/kotlin/gradle-dsl
https://www.baeldung.com/spring-response-status-exception
https://www.baeldung.com/kotlin/mockmvc-kotlin-dsl
https://code.haleby.se/2019/09/06/rest-assured-in-kotlin/
https://github.com/rest-assured/rest-assured/wiki/GettingStarted
https://reflectoring.io/objectmother-fluent-builder/
https://openlibrary.org/dev/docs/api/books
https://www.innoq.com/en/articles/2023/10/spring-boot-testing/
https://www.baeldung.com/kotlin/mockmvc-kotlin-dsl
https://code.haleby.se/2019/09/06/rest-assured-in-kotlin/
implementation("com.squareup.okhttp3:okhttp:4.9.0")

https://microcks.io/documentation/getting-started/
https://edu.chainguard.dev/chainguard/chainguard-images/reference/jdk/provenance_info/

Standard Commons Logging discovery in action with spring-jcl: please remove commons-logging.jar from classpath in order
to avoid potential conflicts





 

