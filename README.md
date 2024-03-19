
![PRO](https://github.com/shineMeoseuk/CarWash/assets/112597600/dfa71d70-9284-4f30-9700-c0fd10e265a6)

## 🚗 CarWash
- 셀프 세차 예약 서비스인 CarWash 입니다.
- 대규모 트래픽을 처리할 수 있는 서비스를 목표로 진행하였습니다.
- 성능 및 유지보수성을 고려하여 객체 지향적인 코드를 만들고자 노력하였습니다.
- 클라이언트는 프로토타입으로 대체하여 백엔드 개발의 관점에서 개발을 진행하였습니다.
- Jenkins를 이용하여 CI/CD 환경을 구축하였습니다.

## 사용 기술
- Java 11
- Spring Boot
- MySQL
- MyBatis
- Redis
- Jenkins

## Issue
- #1 Swagger 를 사용하여 API 문서 자동화 하기.
- [#2 분산 환경에서 발생하는 세션 불일치 문제 해결하기.](https://velog.io/@white0597/%EB%B6%84%EC%82%B0-%ED%99%98%EA%B2%BD%EC%97%90%EC%84%9C-%EB%B0%9C%EC%83%9D%ED%95%98%EB%8A%94-%EC%84%B8%EC%85%98-%EB%B6%88%EC%9D%BC%EC%B9%98%ED%95%98%EB%8A%94-%EC%9D%B4%EC%8A%88-%ED%95%B4%EA%B2%B0%ED%95%98%EA%B8%B0)
- #3 Jenkins를 통한 CI & CD 구축하기.

## 프로젝트 관리
commit 요청 시, 자동 Build 및 Test를 적용하였습니다.
jacoco 플로그인을 통해 Controller 레이어를 제외한 모든 영역의 Test Coverage 100%를 유지하도록 노력하였습니다.
코드 컨벤션은 Google Style을 준수하여 작성하였습니다.
checkstyle 플러그인을 적용해 코드 컨벤션을 유지하였습니다.

InteliJ Google Style 적용 방법
1. Preferences > Editor > Code Style > Java 메뉴
2. Scheme 우측 메뉴에서 Import Scheme > InteliJ IDEA code style XML
3. checkstyle/intellij-java-google-style.xml 파일 선택
4. 적용 후 Tab size 와 Indent 를 4로 변경

## Git-Flow branch 전략
![branch](https://github.com/shineMeoseuk/CarWash/assets/112597600/4fe45351-5ac0-47b5-92e7-fa3848c699a7)


✅ Master : 제품으로 출시될 수 있는 branch <br>
✅ Hotfix : 출시 버전에서 발생한 버그를 수정하는 branch <br>
✅ Develop : 다음 출시 버전을 개발하는 branch, Feature 에서 리뷰 완료한 branch를 Merge <br>
✅ Feature : 기능을 개발하는 branch <br>
✅ Release : 출시 버전을 준비하는 branch <br>

### 참고문헌
- 우아한 형제들 기술블로그 "우린 Git-flow를 사용하고 있어요"
  <a>https://techblog.woowahan.com/2553/

## Api Docs

## Prototyping
<a>https://github.com/shineMeoseuk/CarWash/wiki/Prototyping

## Use cases
<a>https://github.com/shineMeoseuk/CarWash/wiki/Use-cases
  
## ERD
<a>https://github.com/shineMeoseuk/CarWash/wiki/ERD
![CarWash (1)](https://github.com/shineMeoseuk/CarWash/assets/112597600/a0663432-b3ee-4bc8-bdbe-18b04d20a97a)
