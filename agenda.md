## Часть 1. Юнит тесты
* только 4 класса должны быть покрыты юнит тестами на > 70%
* как покрыть тестами enum

## Часть 2. API тесты
* доки API могут отставать от реализации
* часть ручек может быть закрыта авторизацией, а часть - открыта, и это нормально. 
  Например, список товаров - возможно публичная информация. 
  На реальных проектах стоит уточнить у разработчика и аналитика.

## Часть 3. Селениум и кросс-браузерное тестирование
* скроллинг страницы бургеров
* получение токена авторизации из Local Storage браузера после логина
* удаление юзера через rest assured: передать токен в header'е
* юзера следует удалять даже в негативных кейсах, т.к. система может создавать невалидных юзеров из-за бага
* в коде использовать фабрику или @Rule (JUnit4) / @RegisterExtension (JUnit5)
* mvn clean test -Dbrowser=yandex
  * для яндекс браузера искать подходящую версию драйвера через REST API:
    https://github.com/GoogleChromeLabs/chrome-for-testing#json-api-endpoints


## Портфолио++
* Инфраструктура/DevOps: прикрутить CI/CD
  * GitHub Actions: https://testautomationu.applitools.com/github-actions-for-testing/
  * 
  * GitLab
  * GitHub + CircleCI/TravisCI/...
  * Jenkins (найти хостинг) 
* Docker
  * JUnit: eclipse-temurin
  * RestAssured
  * [Selenium Grid](https://github.com/SeleniumHQ/docker-selenium)
  * Selenoid
* (желательно) Selenide
* (желательно) JUnit 5
