## 프로젝트 소개


![image.png](https://img.notionusercontent.com/s3/prod-files-secure%2Fd85fd78d-de88-4966-9b27-62e3e2e8c2a0%2Feefc5b89-8ea4-47b3-b8b9-5118ce8cae56%2Fimage.png/size/w=2000?exp=1730215835&sig=EDCGUVa79ob5IPdHi5mJhuBdV54SzUFSh8DvoNS5p60)

FITinside는 LF몰, 무신사와 같은 온라인 쇼핑몰을 모티브로 한 웹사이트입니다.

사용자는 다양한 상품을 탐색하고, 원하는 상품을 장바구니에 담아 관리할 수 있습니다.

회원가입 및 로그인(구글 로그인 포함)을 통해 주문도 완료할 수 있으며 카테고리별 상품 분류 및 쿠폰 적용 기능 등을 추가해 사용자 경험을 향상시켰습니다.

---

## 팀원 구성 및 역할 분담

- 회원가입/로그인 : 박진영
- 카테고리/배너 : 유연주
- 상품관리 : 이하현
- 장바구니/쿠폰 : 안창민
- 주문/배송지 : 허수빈

---

## 개발 기간

2024.09.30 ~ 2024.10.25 (1개월)

---

## 기술 스택 

### Back-end

- Java 17 <img src="https://img.notionusercontent.com/s3/prod-files-secure%2Fd85fd78d-de88-4966-9b27-62e3e2e8c2a0%2F1f5af47a-5654-4ef8-a9e1-b34c3e22300d%2Fimage.png/size/w=2000?exp=1730254920&sig=Fb5I6oSXneCwoE72Dq5grD2FY43YZ3bq8MctfV7uM4U" alt="image" width="30"/>
  - 최신 LTS(Long-Term Support) 버전으로 안정성과 성능 제공
- Spring Boot 3.3.4 <img src="https://img.notionusercontent.com/s3/prod-files-secure%2Fd85fd78d-de88-4966-9b27-62e3e2e8c2a0%2F70fed49e-c6a4-4ab9-8fcf-9cf67008f50b%2Fimage.png/size/w=2000?exp=1730255133&sig=671xyAAlCwP8-5YSgrHJFcXRzgE3BsU0tOGVwc9W82A" alt="image" width="30"/>
  - 경량화된 자바 프레임워크, RESTful API 구축 및 서버 사이드 로직 처리하는 역할을 수행하고 이번 프로젝트 표준 기술로 채택
- Spring Security 3.3.4 <img src="https://img.notionusercontent.com/s3/prod-files-secure%2Fd85fd78d-de88-4966-9b27-62e3e2e8c2a0%2F17fef64a-bb8c-4c77-b5f6-284dfb8a8016%2Fimage.png/size/w=2000?exp=1730255210&sig=ooOoD3lMdrTPOWINJ1TKv3fhfF3BKMXw0zRslKlBJXo" alt="image" width="30"/>
  - Spring Boot를 사용하여 개발하는 과정에 높은 연동성을 제공하고 인증, 권한 부여와 엑세스 제어등의 여러 기능을 편리하게 이용하여 사용자 데이터를 안전하게 보호
- JWT 0.12.1 <img src="https://img.notionusercontent.com/s3/prod-files-secure%2Fd85fd78d-de88-4966-9b27-62e3e2e8c2a0%2Ff17bbb5b-84c0-4d14-9004-c03f94938586%2Fimage.png/size/w=2000?exp=1730255249&sig=sHixhCsZDNpKPFxGPcDSMAg_wJ0OAF2PPy7PfRAKFmY" alt="image" width="30"/>
  - 무상태성(State-Less)를 지향하는 HTTP의 특성에 적합하고 로그인 정보가 필요한 부분의 확장을 용이하게하는 장점을 이용하고자 선택
- JPA 3.3.4 <img src="https://img.notionusercontent.com/s3/prod-files-secure%2Fd85fd78d-de88-4966-9b27-62e3e2e8c2a0%2F611a3ef3-cb99-4308-aafb-cf471cd68973%2Fimage.png/size/w=2000?exp=1730255267&sig=qlsv_3-769FSH9KjQ6zBROeeKCXDVdWM0KkUzvbjgEE" alt="image" width="30"/>
  - 개발 과정에서 여러 데이터베이스 사용하고 연동하기 위한 ORM 기술을 사용해 객체지향 언어를 사용한 Spring Boot 프로젝트에 적합, 효율적인 데이터베이스 처리 구현, SQL Mapping 기술보다 단순한 DB조작을 많이 하는 서비스를 구현하는 이번 프로젝트에 적합
- MySQL(AWS RDS) <img src="https://img.notionusercontent.com/s3/prod-files-secure%2Fd85fd78d-de88-4966-9b27-62e3e2e8c2a0%2F88aa8ee2-1468-4231-a2d1-70aece8df7f4%2Fimage.png/size/w=2000?exp=1730255281&sig=c3U76F8T9Gl7RIC85Z7gY6YA2Cgjdk6WkfwKkEEnin0" alt="image" width="30"/> <img src="https://img.notionusercontent.com/s3/prod-files-secure%2Fd85fd78d-de88-4966-9b27-62e3e2e8c2a0%2F75bde23b-e19b-4239-8062-88d27aedaf83%2Fimage.png/size/w=2000?exp=1730255304&sig=J6Y-etCMvMDRbuWlUypMLaWApQ4x2ztnwR52haYaXow" alt="image" width="30"/>
  - 관계형 데이터베이스, 쇼핑몰의 데이터를 저장 및 관리
- JUnit 5.9.3 <img src="data:image/jpeg;base64,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" alt="image" width="30"/>
  - Java 기반의 단위 테스트 프레임워크로, 테스트 작성 및 실행을 위한 다양한 기능을 제공. 테스트 케이스를 작성하고 관리하는 데 있어 유연성을 높이며, 새로운 기능들과 다양한 확장성을 통해 효율적인 테스트 환경을 구현할 수 있도록 지원
- Jakarta Mail 2.0.3 <img src="https://img.notionusercontent.com/s3/prod-files-secure%2Fd85fd78d-de88-4966-9b27-62e3e2e8c2a0%2F3d078b5a-6d5d-4568-bbbd-115740fd5855%2Fimage.png/size/w=2000?exp=1730255360&sig=IU5c9XmfPIL0s0MDxJMq81PJMSxI9INSM2m0V_I_zVI" alt="image" width="30"/>
  - 이메일 발송을 위한 라이브러리로, Spring Boot와 통합하여 비동기적으로 메일을 전송
- AWS S3 2.2.6 <img src="data:image/png;base64,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" alt="image" width="30"/>
    - Amazon의 객체 저장 서비스, 대용량 데이터 저장 및 관리에 최적화, 이미지 파일을 효율적으로 저장하고, 쉽게 접근할 수 있도록 지원

### Front-end

- HTML, CSS <img src="data:image/png;base64,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" alt="image" width="30"/>
  - 웹 표준을 준수한 마크업 및 스타일링
- React <img src="data:image/png;base64,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" alt="image" width="30"/>
  - 컴포넌트 기반의 UI 라이브러리를 활용하여 효율적인 상태 관리 및 인터랙티브한 사용자 인터페이스 구현
- Axios <img src="data:image/png;base64,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" alt="image" width="30"/>
  - 백엔드와의 HTTP 통신을 위한 비동기 요청 처리 라이브러리

### 서비스 배포 환경

- 배포 url
  - https://fitinside.netlify.app

- 프론트엔드 배포
    - Netlify <img src="https://img.notionusercontent.com/s3/prod-files-secure%2Fd85fd78d-de88-4966-9b27-62e3e2e8c2a0%2F30b918a6-bd8f-41b1-be3c-6bcf1429f31d%2Fimage.png/size/w=2000?exp=1730261478&sig=lSI3yKLPs9490gDqBh-HBPNfH8zLgxAl9LsaBkP9piM"  alt="image" width="30"/>

- 백엔드 배포
    - 엘리스 클라우드 VM
        - GNU/Linux 5.15.0-91-generic <img src="https://img.notionusercontent.com/s3/prod-files-secure%2Fd85fd78d-de88-4966-9b27-62e3e2e8c2a0%2Faab9eb6c-319a-4f52-84f5-65c1b11f7294%2Fimage.png/size/w=2000?exp=1730261554&sig=FqsnE8B6oFSrlEwmXoFd16cQBCNFRNvVJn17_fN0khc"  alt="image" width="30"/>
        - Ubuntu 22.04.5 LTS <img src="https://img.notionusercontent.com/s3/prod-files-secure%2Fd85fd78d-de88-4966-9b27-62e3e2e8c2a0%2F1a8922ec-b0e7-494e-9dfd-a79218507d76%2Fimage.png/size/w=2000?exp=1730261567&sig=eQeYhi5C8e_YVs0bQ0s4bF9to3lUnK9Rz2ZqXjwv9EI"  alt="image" width="30"/>

### 버전 및 이슈관리

- GitLab Project
- GitLab Issues
- GitHub(Netlify 배포용)

### 협업 툴

- Discord
- Notion

### 기타

- Mapstruct 1.5.3
- Lombok 1.18.34
- Swagger 2.0.4

---

## 브랜치 전략

- Git-flow 전략을 기반으로 master, develop 브랜치와 featue 등의 기능 브랜치를 활용했습니다.
  - master : 배포 단계에서만 사용하는 브랜치입니다.
  - develop : 개발 단계에서의 master 역할을 하는 브랜치입니다.
  - review-develop : 기능 브랜치를 develop에 merge하기 전 코드 리뷰를 통해 정상적으로 동작을 하는지 확인하는 역할을 하는 브랜치입니다.
  - publish-develop : 배포 환경(배포된 서버 URL 반영, RDS 적용 등)을 적용해 놓은 브랜치입니다.
  - feature: 기능 단위로 독립적인 개발 환경을 위해 사용하고 merge 후 브랜치를 삭제해주었습니다.
  - refactor: 기존 코드를 개선하고 구조를 변경하는 브랜치입니다. review-develop으로 merge 후 브랜치를 삭제해 주었습니다.
  - test: 테스트 코드를 작성하거나 기존 테스트 코드를 리팩토링 하는 브랜치입니다. review-develop으로 merge 후 브랜치를 삭제해 주었습니다.

---

## ERD

<img src="https://img.notionusercontent.com/s3/prod-files-secure%2Fd85fd78d-de88-4966-9b27-62e3e2e8c2a0%2Fc2689960-5c8a-4f77-9f56-d283a3d1d3dd%2Fft.png/size/w=2000?exp=1730266617&sig=2nskIwLp0pwaCdt0yzxzo-k2qY5E1NVyB3Hc8Hqr5gg" alt="image" width="500px">

---


## 트러블 슈팅

- Oauth 로그인 시 회원 id가 Long 타입이 아닌 String타입으로 담기는 문제가 발생하여 SecurityContextHolder 디버깅을 통해 데이터 확인 후 로그인 방식에 따라 회원 id를 다르게 가져오도록 처리

  - [트러블슈팅 - Oauth 로그인 시 회원 id 문제](https://github.com/Engineering-Student-An/Elice_2nd_project_FITinside/wiki/%ED%8A%B8%EB%9F%AC%EB%B8%94%EC%8A%88%ED%8C%85-%E2%80%90-Oauth-%EB%A1%9C%EA%B7%B8%EC%9D%B8-%EC%8B%9C-%ED%9A%8C%EC%9B%90-id%EA%B0%80-Long-%ED%83%80%EC%9E%85%EC%9D%B4-%EC%95%84%EB%8B%8C-String%ED%83%80%EC%9E%85%EC%9C%BC%EB%A1%9C-%EB%8B%B4%EA%B8%B0%EB%8A%94-%EB%AC%B8%EC%A0%9C%EA%B0%80-%EB%B0%9C%EC%83%9D%ED%95%98%EC%97%AC-SecurityContextHolder-%EB%94%94%EB%B2%84%EA%B9%85%EC%9D%84-%ED%86%B5%ED%95%B4-%EB%8D%B0%EC%9D%B4%ED%84%B0-%ED%99%95%EC%9D%B8-%ED%9B%84-%EB%A1%9C%EA%B7%B8%EC%9D%B8-%EB%B0%A9%EC%8B%9D%EC%97%90-%EB%94%B0%EB%9D%BC-%ED%9A%8C%EC%9B%90-id%EB%A5%BC-%EB%8B%A4%EB%A5%B4%EA%B2%8C-%EA%B0%80%EC%A0%B8%EC%98%A4%EB%8F%84%EB%A1%9D-%EC%B2%98%EB%A6%AC)

- 쿠폰 도메인에서 여러 메서드에서 n+1 문제가 발생하여 레파지토리에 fetch join을 포함한 jpql을 적용하여 해결

  - [트러블슈팅 - n+1 문제 해결](https://github.com/Engineering-Student-An/Elice_2nd_project_FITinside/wiki/%ED%8A%B8%EB%9F%AC%EB%B8%94%EC%8A%88%ED%8C%85-%E2%80%90-%EC%BF%A0%ED%8F%B0-%EB%8F%84%EB%A9%94%EC%9D%B8%EC%97%90%EC%84%9C-%EC%97%AC%EB%9F%AC-%EB%A9%94%EC%84%9C%EB%93%9C%EC%97%90%EC%84%9C-n-1-%EB%AC%B8%EC%A0%9C%EA%B0%80-%EB%B0%9C%EC%83%9D%ED%95%98%EC%97%AC-%EB%A0%88%ED%8C%8C%EC%A7%80%ED%86%A0%EB%A6%AC%EC%97%90-fetch-join%EC%9D%84-%ED%8F%AC%ED%95%A8%ED%95%9C-jpql%EC%9D%84-%EC%A0%81%EC%9A%A9%ED%95%98%EC%97%AC-%ED%95%B4%EA%B2%B0)

- 토큰 인증 예외가 아닌 경우에도 401 에러가 반환되는 문제가 발생하여 CustomExceptionHandler에 유효성 검사 예외 처리 메서드를 추가하여 해결

  - [트러블슈팅 - 토큰 인증 예외가 아닌 경우에도 401에러 발생 문제 해결](https://github.com/Engineering-Student-An/Elice_2nd_project_FITinside/wiki/%ED%8A%B8%EB%9F%AC%EB%B8%94%EC%8A%88%ED%8C%85-%E2%80%90-%ED%86%A0%ED%81%B0-%EC%9D%B8%EC%A6%9D-%EC%98%88%EC%99%B8%EA%B0%80-%EC%95%84%EB%8B%8C-%EA%B2%BD%EC%9A%B0%EC%97%90%EB%8F%84-401-%EC%97%90%EB%9F%AC%EA%B0%80-%EB%B0%98%ED%99%98%EB%90%98%EB%8A%94-%EB%AC%B8%EC%A0%9C%EA%B0%80-%EB%B0%9C%EC%83%9D%ED%95%98%EC%97%AC-CustomExceptionHandler%EC%97%90-%EC%9C%A0%ED%9A%A8%EC%84%B1-%EA%B2%80%EC%82%AC-%EC%98%88%EC%99%B8-%EC%B2%98%EB%A6%AC-%EB%A9%94%EC%84%9C%EB%93%9C%EB%A5%BC-%EC%B6%94%EA%B0%80%ED%95%98%EC%97%AC-%ED%95%B4%EA%B2%B0)

---

## 프로젝트 구조

<details>
  <summary>프로젝트 구조</summary>
  <p>

  ```bash

# address : 배송지
# banner : 광고
# cart : 장바구니
# category : 상품 카테고리
# coupon : 상품 쿠폰
# global : 전역 예외
# member : 회원
# oath : 인증
# order : 주문
# produt : 상품

src
├── main
│   └── java
│       └── com
│           └── team2
│               └── fitinside
│                   ├── address
│                   │   ├── controller
│                   │   ├── dto
│                   │   ├── entity
│                   │   ├── mapper
│                   │   ├── repository
│                   │   └── service
│                   ├── banner
│                   │   ├── controller
│                   │   ├── dto
│                   │   ├── entity
│                   │   ├── mapper
│                   │   ├── repository
│                   │   └── service
│                   ├── cart
│                   │   ├── controller
│                   │   ├── dto
│                   │   ├── entity
│                   │   ├── mapper
│                   │   ├── repository
│                   │   └── service
│                   ├── category
│                   │   ├── controller
│                   │   ├── dto
│                   │   ├── entity
│                   │   ├── mapper
│                   │   ├── repository
│                   │   └── service
│                   ├── config
│                   ├── coupon
│                   │   ├── controller
│                   │   ├── dto
│                   │   ├── entity
│                   │   ├── mapper
│                   │   ├── repository
│                   │   └── service
│                   ├── global
│                   │   └── exception
│                   ├── jwt
│                   ├── member
│                   │   ├── controller
│                   │   ├── dto
│                   │   ├── entity
│                   │   ├── mapper
│                   │   ├── repository
│                   │   └── service
│                   ├── oath
│                   │   ├── controller
│                   │   ├── dto
│                   │   ├── entity
│                   │   ├── repository
│                   │   ├── service
│                   │   └── util
│                   ├── order
│                   │   ├── common
│                   │   ├── controller
│                   │   ├── dto
│                   │   ├── entity
│                   │   ├── mapper
│                   │   ├── repository
│                   │   └── service
│                   └── product
│                       ├── controller
│                       ├── dto
│                       ├── entity
│                       ├── image
│                       ├── mapper
│                       ├── repository
│                       └── service
└── test
    └── java
        └── com
            └── team2
                └── fitinside
                    ├── auth
                    ├── cart
                    │   ├── controller
                    │   └── service
                    ├── coupon
                    │   ├── controller
                    │   └── service
                    └── member
                        └── service

                 

```

  </p>
</details>

---

## 주요 기능

### 회원

- 이메일, 비밀번호 기반의 로그인 기능

  <img src="https://img.notionusercontent.com/s3/prod-files-secure%2Fd85fd78d-de88-4966-9b27-62e3e2e8c2a0%2F7aced914-ba72-46b9-8c49-b6cd0036a711%2FFITinside-Chrome-2024-10-24-15-54-44.gif/size/?exp=1730261954&sig=JiyUsIdKOIvR43AnQqAa3FCnkZ59j8LrFXqYxVRavPM" alt="image" width="300px">

- 이메일, 비밀번호 기반의 회원가입 기능

  <img src="https://img.notionusercontent.com/s3/prod-files-secure%2Fd85fd78d-de88-4966-9b27-62e3e2e8c2a0%2F7f0806ca-9b78-4f0c-b188-b791a7eb1c93%2FFITinside-Chrome-2024-10-24-15-20-15.gif/size/?exp=1730262062&sig=uhaTZ402mgJVnI_n_X1H8VMspB3hpBeIQ5Z7ioPUZ7A" alt="image" width="300px">

- Google API Cloud 연동한 로그인 기능

  <img src="https://img.notionusercontent.com/s3/prod-files-secure%2Fd85fd78d-de88-4966-9b27-62e3e2e8c2a0%2Fccb51f22-55e3-4c64-b291-45ebcaf3f66a%2FFITinside-Chrome-2024-10-24-15-54-44.gif/size/?exp=1730262074&sig=Q_ZLoXMWTdFwcteUBFbfhyn7T68Xx2gD5Hd84YEZBIs" alt="image" width="300px">

- 클라이언트에 저장된 AccessToken으로 로그인 유지 기능
- 토큰 탈취 대책으로 짧은 수명의 AccessToken, 자동 재발급을 위한 RefreshToken 쿠키 저장

### 배너

- 메인 화면에서 광고 기능
- 배너의 조회, 생성, 수정, 삭제 가능
- 배너를 통한 url 연결 기능

<img src="https://img.notionusercontent.com/s3/prod-files-secure%2Fd85fd78d-de88-4966-9b27-62e3e2e8c2a0%2Fce6cf0c4-bb7e-4911-8a40-f4e276cbd6f5%2F%EB%B0%B0%EB%84%881.gif/size/?exp=1730262095&sig=8pjDOnFSXwmbcTvYmAVX20Ya8tMKe7ivGzfjDBhvRrQ" alt="image" width="300px">

### 카테고리

- 공통(회원, 관리자)

  - 헤더

    부모 카테고리의 드롭 다운을 통해 자식 카테고리 조회 가능

  - 이미지 포함 카테고리

    이미지와 포함 카테고리 조회 가능

  <img src="https://img.notionusercontent.com/s3/prod-files-secure%2Fd85fd78d-de88-4966-9b27-62e3e2e8c2a0%2Fba53116d-33d8-4879-95be-62f62a014bba%2F%EC%B9%B4%ED%85%8C%EA%B3%A0%EB%A6%AC2.gif/size/?exp=1730262109&sig=Q5vksVPX_nfSv1k0MJxAChVACx-fNBjiY3IgwpAn9gk" alt="image" width="300px">

- 관리자
  
  - 카테고리 생성, 수정, 삭제 가능(이미지와 정렬 순서 추가 가능)

  <img src="https://img.notionusercontent.com/s3/prod-files-secure%2Fd85fd78d-de88-4966-9b27-62e3e2e8c2a0%2F4e0f1478-936a-4e65-9ae6-21d087747eb8%2F%EC%B9%B4%ED%85%8C%EA%B3%A0%EB%A6%AC1.gif/size/?exp=1730262132&sig=c3GT-C1TlzzSuvkKuJ9AWuofDk4rLS-eMZ94yXBbuCA" alt="image" width="300px">

### 상품

- 공통
  - 상품 조회 & 장바구니로 이동

<img src="https://private-user-images.githubusercontent.com/139744625/381397539-8b01435e-8189-4868-82b3-97e6b591a770.gif?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MzAyNjQ3ODUsIm5iZiI6MTczMDI2NDQ4NSwicGF0aCI6Ii8xMzk3NDQ2MjUvMzgxMzk3NTM5LThiMDE0MzVlLTgxODktNDg2OC04MmIzLTk3ZTZiNTkxYTc3MC5naWY_WC1BbXotQWxnb3JpdGhtPUFXUzQtSE1BQy1TSEEyNTYmWC1BbXotQ3JlZGVudGlhbD1BS0lBVkNPRFlMU0E1M1BRSzRaQSUyRjIwMjQxMDMwJTJGdXMtZWFzdC0xJTJGczMlMkZhd3M0X3JlcXVlc3QmWC1BbXotRGF0ZT0yMDI0MTAzMFQwNTAxMjVaJlgtQW16LUV4cGlyZXM9MzAwJlgtQW16LVNpZ25hdHVyZT01MWU2MjUyMGY3NWZhZWNiNTMyMzIxOWZkYTY0OTY3NjA4MTg5Zjc3ZTFiM2JkNWMxMTNiNTNiZTVhNzY2ZjUzJlgtQW16LVNpZ25lZEhlYWRlcnM9aG9zdCJ9.aOdZt9ngsYVRKXEUK6qx7Nc2acBOEDO1WFYxFpQD7ko" alt="image" width="300px">

- 관리자 페이지
  - 상품 조회 & 상품 등록

  <img src="https://private-user-images.githubusercontent.com/139744625/381398461-3dee9d31-ac9b-4524-9c5c-b3c8a48eb2bb.gif?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MzAyNjUxMjMsIm5iZiI6MTczMDI2NDgyMywicGF0aCI6Ii8xMzk3NDQ2MjUvMzgxMzk4NDYxLTNkZWU5ZDMxLWFjOWItNDUyNC05YzVjLWIzYzhhNDhlYjJiYi5naWY_WC1BbXotQWxnb3JpdGhtPUFXUzQtSE1BQy1TSEEyNTYmWC1BbXotQ3JlZGVudGlhbD1BS0lBVkNPRFlMU0E1M1BRSzRaQSUyRjIwMjQxMDMwJTJGdXMtZWFzdC0xJTJGczMlMkZhd3M0X3JlcXVlc3QmWC1BbXotRGF0ZT0yMDI0MTAzMFQwNTA3MDNaJlgtQW16LUV4cGlyZXM9MzAwJlgtQW16LVNpZ25hdHVyZT0zYmFhNDhlYjlhMmE4NDQ0MmIwZGMwZGU0NDIyYmM5YzAyOTE4NzZhNDdlNzYwNThhNjRhMGNkOGJkZTJiMmE1JlgtQW16LVNpZ25lZEhlYWRlcnM9aG9zdCJ9.uls38_DLM1icy52MrhgqN9pF9-kQlkqkqPW79kJPt8k" alt="image" width="300px">

  - 상품 관련 데이터를 수정

  <img src="https://private-user-images.githubusercontent.com/139744625/381399115-804f2c29-49f6-47fc-a0f6-2cf49bbd35a5.gif?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MzAyNjUyNzcsIm5iZiI6MTczMDI2NDk3NywicGF0aCI6Ii8xMzk3NDQ2MjUvMzgxMzk5MTE1LTgwNGYyYzI5LTQ5ZjYtNDdmYy1hMGY2LTJjZjQ5YmJkMzVhNS5naWY_WC1BbXotQWxnb3JpdGhtPUFXUzQtSE1BQy1TSEEyNTYmWC1BbXotQ3JlZGVudGlhbD1BS0lBVkNPRFlMU0E1M1BRSzRaQSUyRjIwMjQxMDMwJTJGdXMtZWFzdC0xJTJGczMlMkZhd3M0X3JlcXVlc3QmWC1BbXotRGF0ZT0yMDI0MTAzMFQwNTA5MzdaJlgtQW16LUV4cGlyZXM9MzAwJlgtQW16LVNpZ25hdHVyZT02OTZiZDI3YzdiZjhlMmVhNGE5NzcyYTE2NzlkMjllODYzNjZjNzgxZjEwMThjYzM3N2QxZGRmNjhmMjNmOWM0JlgtQW16LVNpZ25lZEhlYWRlcnM9aG9zdCJ9.GKUR7DaRjg2yHvHHyi1gW26-GaNYc7LqBd33Q_ZgB-U" alt="image" width="300px">  

  - 상품 삭제

  <img src="https://private-user-images.githubusercontent.com/139744625/381399961-2351f744-3d85-4459-8c4f-9c8bd9d9fd5c.gif?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MzAyNjUzODksIm5iZiI6MTczMDI2NTA4OSwicGF0aCI6Ii8xMzk3NDQ2MjUvMzgxMzk5OTYxLTIzNTFmNzQ0LTNkODUtNDQ1OS04YzRmLTljOGJkOWQ5ZmQ1Yy5naWY_WC1BbXotQWxnb3JpdGhtPUFXUzQtSE1BQy1TSEEyNTYmWC1BbXotQ3JlZGVudGlhbD1BS0lBVkNPRFlMU0E1M1BRSzRaQSUyRjIwMjQxMDMwJTJGdXMtZWFzdC0xJTJGczMlMkZhd3M0X3JlcXVlc3QmWC1BbXotRGF0ZT0yMDI0MTAzMFQwNTExMjlaJlgtQW16LUV4cGlyZXM9MzAwJlgtQW16LVNpZ25hdHVyZT01OTgyNDdmMzQ0M2NkMmIzYzBmYjEyZTNiNTk0MTVhOGZkY2YxM2ZhMjI2ZDM2MGYzNTBmNjAwZGFiY2YyNDgxJlgtQW16LVNpZ25lZEhlYWRlcnM9aG9zdCJ9.4VFTMN6ckOU_4f8r_GQu6Ff8mnl_TERp-pwTpMRHgmk" alt="image" width="300px">


### 장바구니

<img src="https://private-user-images.githubusercontent.com/139744625/381400553-151c1383-af0a-4cb2-9e9c-634892289fcd.gif?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MzAyNjU1MDMsIm5iZiI6MTczMDI2NTIwMywicGF0aCI6Ii8xMzk3NDQ2MjUvMzgxNDAwNTUzLTE1MWMxMzgzLWFmMGEtNGNiMi05ZTljLTYzNDg5MjI4OWZjZC5naWY_WC1BbXotQWxnb3JpdGhtPUFXUzQtSE1BQy1TSEEyNTYmWC1BbXotQ3JlZGVudGlhbD1BS0lBVkNPRFlMU0E1M1BRSzRaQSUyRjIwMjQxMDMwJTJGdXMtZWFzdC0xJTJGczMlMkZhd3M0X3JlcXVlc3QmWC1BbXotRGF0ZT0yMDI0MTAzMFQwNTEzMjNaJlgtQW16LUV4cGlyZXM9MzAwJlgtQW16LVNpZ25hdHVyZT1iNDBiNGQ2OTI3YzM1ODc3M2MzZTk2NGNlYTg1YjlkOTM5MzUyMDQ0MmM4ODQxZmUwZjg3Yzg5NTQ3ODk1ODgxJlgtQW16LVNpZ25lZEhlYWRlcnM9aG9zdCJ9.0sp9x4aajsCkIp0wn8Zis67rMfsyZiiIXzGht_omS-E" alt="image" width="300px">

- 비회원/회원 구분없이 장바구니 추가 가능
- 장바구니 정보가 비회원은 로컬스토리지, 회원은 db에 저장
- 장바구니에 담긴 상품 수량 수정, 삭제 기능
- 배송비, 쿠폰등이 적용된 결제 예정 금액 출력 기능

### 쿠폰

- 관리자

  <img src="https://private-user-images.githubusercontent.com/139744625/381400974-f5a734a7-72a4-4eb2-b5e0-f552273fcdab.gif?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MzAyNjU1ODAsIm5iZiI6MTczMDI2NTI4MCwicGF0aCI6Ii8xMzk3NDQ2MjUvMzgxNDAwOTc0LWY1YTczNGE3LTcyYTQtNGViMi1iNWUwLWY1NTIyNzNmY2RhYi5naWY_WC1BbXotQWxnb3JpdGhtPUFXUzQtSE1BQy1TSEEyNTYmWC1BbXotQ3JlZGVudGlhbD1BS0lBVkNPRFlMU0E1M1BRSzRaQSUyRjIwMjQxMDMwJTJGdXMtZWFzdC0xJTJGczMlMkZhd3M0X3JlcXVlc3QmWC1BbXotRGF0ZT0yMDI0MTAzMFQwNTE0NDBaJlgtQW16LUV4cGlyZXM9MzAwJlgtQW16LVNpZ25hdHVyZT1jZmE3MmEyN2Q5N2Q2ZGZjMjcwNGM1MGZjYjFiZjRiZGE4ZDVkMzg5NzZmMjU5ZDEyNjcyZTJkNDYzNmY4YzY2JlgtQW16LVNpZ25lZEhlYWRlcnM9aG9zdCJ9.9Pp84LvGeuoos-KwdcLBfmIC1IBw63tJsbZDm0oydBo" alt="image" width="300px">

  - 쿠폰 생성, 비활성화 가능
  - 쿠폰 목록 (전체 / 활성화만) 조회 (만료일 기준 오름차순 정렬, 페이지네이션 적용) 가능
  - 쿠폰 보유 (페이지네이션 적용), 미보유 회원 조회 가능
  - 미보유 회원에게 쿠폰 이메일 전송 가능

- 회원

  <img src="https://private-user-images.githubusercontent.com/139744625/381400996-438af493-3205-4944-b9cd-92075daff815.gif?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MzAyNjU1ODAsIm5iZiI6MTczMDI2NTI4MCwicGF0aCI6Ii8xMzk3NDQ2MjUvMzgxNDAwOTk2LTQzOGFmNDkzLTMyMDUtNDk0NC1iOWNkLTkyMDc1ZGFmZjgxNS5naWY_WC1BbXotQWxnb3JpdGhtPUFXUzQtSE1BQy1TSEEyNTYmWC1BbXotQ3JlZGVudGlhbD1BS0lBVkNPRFlMU0E1M1BRSzRaQSUyRjIwMjQxMDMwJTJGdXMtZWFzdC0xJTJGczMlMkZhd3M0X3JlcXVlc3QmWC1BbXotRGF0ZT0yMDI0MTAzMFQwNTE0NDBaJlgtQW16LUV4cGlyZXM9MzAwJlgtQW16LVNpZ25hdHVyZT01NzdmZTI2OGM1MjQwMDA1MDM5ZTAzZTJhNGRiOTliMWMyZGE0YmY4YjAxZWIzNzYxODhhODVjNWIwZTIyMjExJlgtQW16LVNpZ25lZEhlYWRlcnM9aG9zdCJ9.6sZWiGBPNITeo41IdNMFz-khuTwGOCHL6q8f1deKFyE" alt="image" width="300px">

  - 쿠폰 다운로드 가능
  - 보유한 쿠폰 목록 (전체 / 사용가능) 조회(만료일 기준 오름차순 정렬, 페이지네이션 적용) 가능
  - 쿠폰 사용 내역 (주문서) 조회 가능

### 주문

- 관리자

  <img src="https://private-user-images.githubusercontent.com/139744625/381401344-0df27771-e238-4eb1-a608-5f213731bf81.gif?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MzAyNjU2NjgsIm5iZiI6MTczMDI2NTM2OCwicGF0aCI6Ii8xMzk3NDQ2MjUvMzgxNDAxMzQ0LTBkZjI3NzcxLWUyMzgtNGViMS1hNjA4LTVmMjEzNzMxYmY4MS5naWY_WC1BbXotQWxnb3JpdGhtPUFXUzQtSE1BQy1TSEEyNTYmWC1BbXotQ3JlZGVudGlhbD1BS0lBVkNPRFlMU0E1M1BRSzRaQSUyRjIwMjQxMDMwJTJGdXMtZWFzdC0xJTJGczMlMkZhd3M0X3JlcXVlc3QmWC1BbXotRGF0ZT0yMDI0MTAzMFQwNTE2MDhaJlgtQW16LUV4cGlyZXM9MzAwJlgtQW16LVNpZ25hdHVyZT0xNWJiOWE0MjRiYzA2ZDliNjRjOGM5M2M5ZjEwMGEwNzVmNjBjOWZmNDAwMzI2ZTUyODNkNzhmZjRmYWU3ZTZlJlgtQW16LVNpZ25lZEhlYWRlcnM9aG9zdCJ9.p8dRwYbBOtA-V-NWTPnq5QS8hwkxZz1xct5mMwrjh7k" alt="image" width="300px">

  - 회원의 주문 관리 → 주문 상태 변경, 주문 삭제
  - 주문 상태, 날짜별 검색 기능

- 회원

  <img src="https://private-user-images.githubusercontent.com/139744625/381401395-ef0400e1-0025-487e-b517-d29e3327179e.gif?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MzAyNjU2NjgsIm5iZiI6MTczMDI2NTM2OCwicGF0aCI6Ii8xMzk3NDQ2MjUvMzgxNDAxMzk1LWVmMDQwMGUxLTAwMjUtNDg3ZS1iNTE3LWQyOWUzMzI3MTc5ZS5naWY_WC1BbXotQWxnb3JpdGhtPUFXUzQtSE1BQy1TSEEyNTYmWC1BbXotQ3JlZGVudGlhbD1BS0lBVkNPRFlMU0E1M1BRSzRaQSUyRjIwMjQxMDMwJTJGdXMtZWFzdC0xJTJGczMlMkZhd3M0X3JlcXVlc3QmWC1BbXotRGF0ZT0yMDI0MTAzMFQwNTE2MDhaJlgtQW16LUV4cGlyZXM9MzAwJlgtQW16LVNpZ25hdHVyZT1kOWMwYjNkMDkyYTUxYzlkNTA2MjUwMDU3MzVmNGQ1MmI4ZjMyODc5Y2ZmYjNiMzZlYjNiMjk3OTlkYjUxMTk0JlgtQW16LVNpZ25lZEhlYWRlcnM9aG9zdCJ9.f_YeMp1JeFQxTPvYBc5gy5BY6RswslMYRREa_kFGYm8" alt="image" width="300px">

  - 배송지 입력 후 주문 생성 가능
  - 주문 상세 조회
    - 배송 시작 전이면 배송지 수정 / 주문 취소 가능
    - 여기서 수정한 배송지는 주문에만 적용 (배송지 목록에 따로 반영 X)
  - 주문 목록 조회
    - 한 페이지에 총 5개씩 주문 확인 가능 (최신순 정렬)
    - 상품명 검색 기능


### 배송지

- 회원

  <img src="https://private-user-images.githubusercontent.com/139744625/381401625-597b1d75-94f2-43a0-a6e3-128247899455.gif?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MzAyNjU3NDUsIm5iZiI6MTczMDI2NTQ0NSwicGF0aCI6Ii8xMzk3NDQ2MjUvMzgxNDAxNjI1LTU5N2IxZDc1LTk0ZjItNDNhMC1hNmUzLTEyODI0Nzg5OTQ1NS5naWY_WC1BbXotQWxnb3JpdGhtPUFXUzQtSE1BQy1TSEEyNTYmWC1BbXotQ3JlZGVudGlhbD1BS0lBVkNPRFlMU0E1M1BRSzRaQSUyRjIwMjQxMDMwJTJGdXMtZWFzdC0xJTJGczMlMkZhd3M0X3JlcXVlc3QmWC1BbXotRGF0ZT0yMDI0MTAzMFQwNTE3MjVaJlgtQW16LUV4cGlyZXM9MzAwJlgtQW16LVNpZ25hdHVyZT04OWQ4ZjdmZjZiNTg0NGE4OTFhNzNiOTA4NjVhNDFkMTdkYzU5NTAzZTRkNmNiMDk3N2EyZWZmYzJhNTRiZDJjJlgtQW16LVNpZ25lZEhlYWRlcnM9aG9zdCJ9.kjtoaIP7ZnA6JcOFnLk9NnC93yhJxnJ0lE6AWkXPr2s" alt="image" width="300px">

  - 배송지는 회원당 최대 5개 저장 가능
    - 5개 제한 → 사용자 편의성 고려
    - 수령인 이름, 우편번호, 주소, 전화번호 중 하나라도 다르면 다른 배송지로 간주
  - 수정, 삭제
  - 기본 배송지 설정 / 해제
    - 기본 배송지 설정 → 주문서 작성 시 자동으로 입력


---

## 상세 기능
- https://github.com/Engineering-Student-An/Elice_2nd_project_FITinside/wiki/%EA%B8%B0%EB%8A%A5-%EB%AA%85%EC%84%B8%EC%84%9C

---

## 주요 API 명세서

### 로그인 / 회원가입 (`Member / Auth`)

| MVP       | Method | URI          | Description     | Request Body                                                                            | Request Params | Response                                                                                                                                                             |
|-----------|--------|--------------|-----------------|-----------------------------------------------------------------------------------------|----------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 사용자 정보 조회 | GET    | /api/user/me | 토큰 기반 사용자 정보 조회 | -                                                                                       | -              | 성공<br/>{ "username": "string",<br/> "email": "string",<br/> "phone": "string",<br/> "creatAt": "localDateTime"}                                                         |
| 회원가입 | POST | /api/auth | 회원가입            | `{ "userName": "string", "email": "string", "password": "string", "phone" : "string" }` | - | 성공<br/>200 :`{ "id": "long", "userName": "string", "email": "string", "phone" : "string", "createdAt": "datetime" }` <br/>실패 <br/>401 :`{ "error": "UNAUTHORIZED" }` |
| 로그인 | POST | /api/auth/login | 로그인             | `{ "email": "string", "password": "string" }` | - | 성공<br/>200 :`{ "token": "JWT_ACCESS_TOKEN" }`<br/>실패<br/>`401 :{ "error": "UNAUTHORIZED" }` |

### 배너 (`Banner`)

| MVP | Method | URI | Description    | Request body                                                                                         | Request Params | Response                                                                                                                       |
| --- | --- | --- |----------------|------------------------------------------------------------------------------------------------------|---------------|--------------------------------------------------------------------------------------------------------------------------------|
| 배너 목록 조회 | GET | /api/banners | 전체 배너 조회       | -                                                                                                    | -             | 성공<br/>200 OK :`[{ "id": "Long", "title": "string", "imageUrl": "string", "displayOrder": "integer", "targetUrl": "string" }]` |

### 카테고리 (`Category`)

| MVP | Method | URI                        | Description | Request body                                                                                                                                 | Request Params | Response |
| --- | --- |----------------------------| --- |----------------------------------------------------------------------------------------------------------------------------------------------|---------------| --- |
| 카테고리 목록 조회 | GET | /api/categories            | 전체 카테고리 조회 | -                                                                                                                                            | -             | 성공<br/>200 OK :`[{"id": "Long", "name": "string", "displayOrder": "Long", "mainDisplayOrder": "Long", "isDeleted": "Boolean", "parentId": "Long", "imageUrl": "string"}]` |


### 상품 (`Product`)

| MVP | Method | URI | Description | Request Body | Request Params                                                                                                          | Response                                                                                                                                                                                                                                                                                                                                                              |
| --- | --- | --- | --- | --- |-------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 상품 목록 조회 | GET | /api/products | 전체 상품 목록 조회 (페이지네이션, 정렬, 검색 적용) | - | `{"page": "int", "size": "int", "sortField": "string", "sortDir": "string", "keyword": "string"}`                       | 성공<br/>200 OK : 상품 목록 조회 성공`{ "id": "Long", "categoryId": "Long", "categoryName": "string", "productName": "string", "price": "int", "createdAt": "datetime", "soldOut": "boolean" }`<br/>실패<br/>400 BAD REQUEST: 잘못된 요청                                                                                                                                             |
| 상품 상세 조회 | GET | /api/products/{id} | 특정 상품 상세 조회 | - | `{"id": "Long"}`                                                                                                        | `- **200 OK** : 상품 상세 조회 성공 { "id": "Long", "categoryId": "Long", "categoryName": "string", "productName": "string", "price": "int", "info": "string", "manufacturer": "string", "productImgUrls": ["url1", "url2"], "createdAt": "datetime", "updatedAt": "datetime", "soldOut": "boolean" }`<br/>실패<br/>400 BAD REQUEST: 잘못된 요청<br/>404 Not Found: 상품을 찾을 수 없음 |


### 장바구니 (`Cart`)

| MVP | Method | URI | Description | Headers | Request Body                             | Response                                                                                                                                                                                                    |
|---| --- | --- |-------------| --- |------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 조회 | GET | /api/carts | 장바구니 목록 조회  | `Authorization: Bearer JWT_TOKEN` | -                                        | 성공<br/>200 OK:`{ ”message”: “장바구니 조회 완료했습니다!”, “carts”: [ { ”productId”: Long, “quantity”: int } ]}`<br/>실패<br/>403 FORBIDDEN: `“권한이 없는 사용자입니다.”`<br/>500 INTERNAL SERVER ERROR: `"서버 에러"`|

### 쿠폰 (`Coupon`)

- 관리자

  | MVP | Method | URI | Description | Headers | Request Body                                                                                                                           | Request Param                                        | Response                                                                                                                                                                                                                                                                                                                                                                                                                            |
  | --- | --- | --- | --- | --- |----------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
  | 쿠폰 목록 조회 | GET | /api/admin/coupons | 존재하는 쿠폰 전체 조회 (유효한 쿠폰만 조회 / 전체 쿠폰 조회) | `Authorization: Bearer JWT_TOKEN` | -                                                                                                                                      | `{ “page”: int, “includeInActiveCoupons”: boolean }` | 성공<br/>200 OK: `{ ”message”: “쿠폰 목록 조회 완료했습니다!”, “coupons”: [ { “id”: Long, “name”: String, “code”: String, “type”: CouponType, “value”: int, “percentage”: int, “minValue”: int, “active”: boolean, “expiredAt”: LocalDate, “categoryName”: String, “used”: boolean } ], “totalPages”: int}`<br/>실패<br/>403 FORBIDDEN: `“권한이 없는 사용자입니다.”`<br/>404 NOT FOUND: `“해당하는 정보의 사용자를 찾을 수 없습니다.”`<br/>500 INTERNAL SERVER ERROR: `“서버 에러”` |

- 회원

  | MVP | Method | URI | Description | Headers | Request Body    | RequestParam                                         | Response                                                                                                                                                                                                                                                                                                                                                                            |
  | --- | --- | --- | --- | --- |-----------------|------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
  | 보유 쿠폰 전체 조회 | GET | /api/coupons | 로그인 한 회원의 보유 쿠폰 전체 조회 (유효한 쿠폰만 조회 / 전체 쿠폰 조회) | `Authorization: Bearer JWT_TOKEN` | -               | `{ “page”: int, “includeInActiveCoupons”: boolean }` | 성공<br/>200 OK: `{ ”message”: “쿠폰 목록 조회 완료했습니다!” “coupons”: [ { “id”: Long, “name”: String, “code”: String, “type”: CouponType, “value”: int, “percentage”: int, “minValue”: int, “active”: boolean, “expiredAt”: LocalDate, “categoryName”: String, “used”: boolean } ], “totalPages”: int }`<br/>실패<br/>403 FORBIDDEN: `“권한이 없는 사용자입니다.”`<br/>500 INTERNAL SERVER ERROR: `“서버 에러”` |
  | 쿠폰 사용 | POST | /api/coupons/{couponMemberId} | 상품에 쿠폰 사용 | `Authorization: Bearer JWT_TOKEN` | -               | -                                                    | 성공<br/>200 OK: `“쿠폰이 사용되었습니다! couponMemberId: ” + couponMemberId`<br/>실패<br/>400 BAD REQUEST: `“쿠폰 정보가 유효하지 않습니다.”`<br/>403 FORBIDDEN: `“권한이 없는 사용자입니다.”`<br/>500 INTERNAL SERVER ERROR: `“서버 에러”`                                                                                                                                                                                  |

### 주문 (`Order`)

- 관리자

  | MVP | Method | URI | Description             | Request Body             | Request Params                                                                                         | Response                                                                                                                                                                                                                                                                                                                                                                       |
  | --- | --- | --- |-------------------------|--------------------------|--------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
  | 전체 주문 조회 | GET | /api/admin/orders | 모든 회원의 전체 주문 조회         | -                        | `{ "page": "int", "orderStatus": "string", "startDate": "localDateTime", "endDate": "localDateTime" }` | 성공<br/>200 OK:`[ { "orders": [ { "orderId": "int", "orderStatus": "string", "totalPrice": "int", "discountedTotalPrice": "int", "email": "string", "createdAt": "localDateTime", "coupons": [ { "couponId": "int", "name": "string", "discountPrice": "int" } ] } ], "totalPages": "int" } ]`</br>실패<br/>403 FORBIDDEN: `“권한이 없습니다.”`<br/>500 INTERNAL SERVER ERROR: `“서버 에러”` |

- 회원

  | MVP | Method | URI | Description                      | Request Body                                                                                                                                                                                                                                                                                                                                                                                                     | Request Params                            | Response                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          |
  | --- | --- | --- |----------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
  | 전체 주문 조회 | GET | /api/orders | 전체 주문 조회                         | -                                                                                                                                                                                                                                                                                                                                                                                                                | `{ "page":"int", "productName":"string"}` | 성공<br/>200 OK:`{ "orders": [ "orderId": "int", "orderStatus": "string", "totalPrice": "int", "discountedTotalPrice": "int", "deliveryAddress": "string", "productNames": [ "string" ], "productImgUrl": "string", "createdAt": "localDateTime" ], "totalPages": "int" }`<br/>실패<br/>403 FORBIDDEN: `“권한이 없습니다.”`<br/>500 INTERNAL SERVER ERROR: `“서버 에러”`                                                                                                                                                                                                                                                                                                                                         |
  | 주문 생성 | POST | /api/order | 사용자 주문 생성                        | `{ "postalCode": "string", "deliveryAddress": "string", "detailedAddress": "string", "deliveryReceiver": "string", "deliveryPhone": "string", "deliveryMemo": "string", "deliveryFee": "int", "orderItems": [ "productId": "int", "productName": "string", "quantity": "int", "itemPrice": "int", "originalTotalPrice": "int", "discountedTotalPrice": "int", "couponName": "string", "couponMemberId": "int"]}` | -                                         | 성공<br/>201 CREATED:`{ "orderId": "int", "orderStatus": "string", "totalPrice": "int", "discountedTotalPrice": "int", "deliveryFee": "int", "postalCode": "string", "deliveryAddress": "string", "detailedAddress": "string", "deliveryReceiver": "string", "deliveryPhone": "string", "deliveryMemo": "string", "createdAt": "localDateTime", "orderProducts": [ "productId": "int", "orderProductName": "string", "orderProductPrice": "int", "count": "int", "discountedPrice": "int", "couponName": "string", "productImgUrl": "stirng" ] }`<br/>실패<br/>400 BAD REQUEST: `“품절된 상품”`<br/>403 FORBIDDEN: `“권한이 없습니다.”`<br/>404 NOT FOUND: `“비어있는 장바구니”`<br/>500 INTERNAL SERVER ERROR: `“서버 에러”` |

---

## 상세 API 명세서
- https://github.com/Engineering-Student-An/Elice_2nd_project_FITinside/wiki/api-%EB%AA%85%EC%84%B8%EC%84%9C

---

## Exception Handling & HTTP Status Code


```markdown
## Exception Handling and HTTP Status Codes

### 1. **400 Bad Request**
클라이언트의 잘못된 요청으로 인해 발생하는 오류입니다.
- **POST_DELETED**: 삭제된 게시글입니다.
- **REVIEW_DELETED**: 삭제된 리뷰입니다.
- **COMMENT_DELETED**: 삭제된 댓글입니다.
- **EMPTY_FILE_EXCEPTION**: 빈 파일입니다.
- **NO_FILE_EXTENSION**: 파일 확장자가 없습니다.
- **INVALID_FILE_EXTENSION**: 허용되지 않는 파일 확장자입니다.
- **INVALID_FILE_FORMAT**: 허용되지 않는 파일 형식입니다.
- **AUTH_CODE_EXTENSION**: 로그인을 실패하였습니다(임시).
- **INVALID_PRODUCT_DATA**: 상품 정보가 유효하지 않습니다.
- **INVALID_PRODUCT_PRICE**: 가격은 0 이상이어야 합니다.
- **INVALID_PRODUCT_NAME_LENGTH**: 상품명은 100자 이하로 입력해야 합니다.
- **INVALID_PRODUCT_INFO_LENGTH**: 상품 설명은 500자 이하로 입력해야 합니다.
- **INVALID_MANUFACTURER_LENGTH**: 제조사는 100자 이하로 입력해야 합니다.
- **INVALID_COUPON_DATA**: 쿠폰 정보가 유효하지 않습니다.
- **INVALID_COUPON_CREATE_DATA**: 쿠폰 생성 정보가 유효하지 않습니다.
- **INVALID_EMAIL_DATA**: 이메일 정보가 유효하지 않습니다.
- **OUT_OF_STOCK**: 현재 주문 가능한 상품의 개수를 초과했습니다.
- **ORDER_MODIFICATION_NOT_ALLOWED**: 배송이 시작된 주문은 수정할 수 없습니다.
- **CART_OUT_OF_RANGE**: 상품 수량은 1개 이상 20개 이하여야 합니다.
- **INVALID_CATEGORY_DATA**: 카테고리 정보가 유효하지 않습니다.

### 2. **401 Unauthorized**
인증되지 않은 사용자가 접근을 시도할 때 발생하는 오류입니다.
- **INVALID_AUTH_TOKEN**: 권한 정보가 없는 토큰입니다.
- **USER_NOT_AUTHENTICATED**: 인증되지 않은 사용자입니다.

### 3. **403 Forbidden**
사용자가 권한이 없는 리소스에 접근하려 할 때 발생하는 오류입니다.
- **USER_NOT_AUTHORIZED**: 권한이 없는 사용자입니다.

### 4. **404 Not Found**
요청한 리소스가 존재하지 않을 때 발생하는 오류입니다.
- **USER_NOT_FOUND**: 해당하는 정보의 사용자를 찾을 수 없습니다.
- **OBJECT_NOT_FOUND**: 대상을 찾을 수 없습니다.
- **RESOURCE_NOT_FOUND**: 대상을 찾을 수 없습니다.
- **PRODUCT_NOT_FOUND**: 해당 상품을 찾을 수 없습니다.
- **CATEGORY_NOT_FOUND**: 해당 카테고리를 찾을 수 없습니다.
- **CART_NOT_FOUND**: 해당 장바구니를 찾을 수 없습니다.
- **COUPON_NOT_FOUND**: 해당 쿠폰을 찾을 수 없습니다.
- **ORDER_NOT_FOUND**: 주문을 찾을 수 없습니다.
- **CART_EMPTY**: 장바구니가 비어있습니다.
- **ORDER_PRODUCT_NOT_FOUND**: 해당 상품에 대한 주문을 찾을 수 없습니다.
- **ADDRESS_NOT_FOUND**: 해당 배송지를 찾을 수 없습니다.

### 5. **409 Conflict**
리소스의 현재 상태와 충돌할 때 발생하는 오류입니다.
- **DUPLICATE_RESOURCE**: 데이터가 이미 존재합니다.
- **DUPLICATE_COUPON**: 쿠폰 등록 이력이 존재합니다.
- **EXCEEDED_MAX_ADDRESS_LIMIT**: 배송지 최대 저장 개수를 초과했습니다.
- **DUPLICATE_ADDRESS**: 배송지가 이미 존재합니다.

### 6. **410 Gone**
리소스가 더 이상 유효하지 않을 때 발생하는 오류입니다.
- **USER_ALREADY_DELETED**: 탈퇴된 사용자입니다.
- **PLACE_DELETED**: 삭제된 장소입니다.
- **CATEGORY_ALREADY_DELETED**: 이미 삭제된 카테고리입니다.

### 7. **500 Internal Server Error**
서버 내부에서 예상치 못한 오류가 발생했을 때 반환하는 상태 코드입니다.
- **IO_EXCEPTION_ON_IMAGE_UPLOAD**: 이미지 업로드 중 입출력 오류가 발생했습니다.
- **PUT_OBJECT_EXCEPTION**: S3에 객체를 업로드하는 중 예외가 발생했습니다.
- **IO_EXCEPTION_ON_IMAGE_DELETE**: 이미지 삭제 중 입출력 오류가 발생했습니다.
- **PRODUCT_LIST_RETRIEVAL_ERROR**: 상품 목록 조회 중 서버 에러 발생.
- **PRODUCT_DETAIL_RETRIEVAL_ERROR**: 상품 상세 조회 중 서버 에러 발생.
- **PRODUCT_CREATION_ERROR**: 상품 등록 중 서버 에러 발생.
- **PRODUCT_UPDATE_ERROR**: 상품 수정 중 서버 에러 발생.
- **PRODUCT_DELETION_ERROR**: 상품 삭제 중 서버 에러 발생.

```