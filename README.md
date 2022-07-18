# Marvel API Test!

This is an Android app to test **Marvel API**.  It has been developed with:

- Kotlin
- Coroutines
- Compose
- MVVM
- Hilt
- Retrofit
- Moshi
- Coil
- MockK
- MockWebServer
- JaCoCo
- SonarQube

## Architecture
The app has three main layers

	 - data (API consuming)
	 - domain (status and repository interface)
	 - presentation (viewmodel and activity with compose UI)

## Screens
The app has two screens

1. Marvel Character List
2. Marvel Character Detail

## JaCoCo
Code coverage report is created only for Unit Test because of I'm facing an error with **testCoverageEnable** and UI Test runner.
## SonarQube
Continuos inspection is on the cloud. You can check the analysis by clicking [here](https://sonarcloud.io/project/overview?id=Garyfimo_MarvelApiTest).

## To be done
Generate report for UI test and export it to SonarQube