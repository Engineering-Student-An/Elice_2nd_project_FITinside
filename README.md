## 프로젝트 소개

<img src="https://github.com/user-attachments/assets/098762e0-b166-4a4a-8e7c-687eda22015a" alt="image" width="300px"/>



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


- Java 17 <img src="https://github.com/user-attachments/assets/161d65aa-8263-46bf-971f-48d959c67647" alt="image" width="30"/>
  - 최신 LTS(Long-Term Support) 버전으로 안정성과 성능 제공
 
- Spring Boot 3.3.4 <img src="https://github.com/user-attachments/assets/a91d4ce4-9b8d-48c3-9b35-bd0e1a2791f1" alt="image" width="30"/>
  - 경량화된 자바 프레임워크, RESTful API 구축 및 서버 사이드 로직 처리하는 역할을 수행하고 이번 프로젝트 표준 기술로 채택
    
- Spring Security 3.3.4 <img src="https://github.com/user-attachments/assets/53381f33-ffd7-4e04-a76e-c0ba256d9730" alt="image" width="30"/>
  - Spring Boot를 사용하여 개발하는 과정에 높은 연동성을 제공하고 인증, 권한 부여와 엑세스 제어등의 여러 기능을 편리하게 이용하여 사용자 데이터를 안전하게 보호
    
- JWT 0.12.1 <img src="https://github.com/user-attachments/assets/d7bbf167-a8b6-467b-8f19-afdce3680a26" alt="image" width="30"/>
  - 무상태성(State-Less)를 지향하는 HTTP의 특성에 적합하고 로그인 정보가 필요한 부분의 확장을 용이하게하는 장점을 이용하고자 선택
    
- JPA 3.3.4 <img src="https://github.com/user-attachments/assets/531ea7c3-6a16-4fbc-80b6-0d31beff4d7d" alt="image" width="30"/>
  - 개발 과정에서 여러 데이터베이스 사용하고 연동하기 위한 ORM 기술을 사용해 객체지향 언어를 사용한 Spring Boot 프로젝트에 적합, 효율적인 데이터베이스 처리 구현, SQL Mapping 기술보다 단순한 DB조작을 많이 하는 서비스를 구현하는 이번 프로젝트에 적합
    
- MySQL(AWS RDS) <img src="https://github.com/user-attachments/assets/84ddb6f6-501c-45d7-8b98-6352a754eab8" alt="image" width="30"/> <img src="https://github.com/user-attachments/assets/86c252e0-21b9-42c5-a092-3dc51c508f84" alt="image" width="30"/>
  - 관계형 데이터베이스, 쇼핑몰의 데이터를 저장 및 관리

- JUnit 5.9.3 <img src="https://github.com/user-attachments/assets/0ceacd84-6a5e-40f0-9df6-386b56018370" alt="image" width="30"/>
  - Java 기반의 단위 테스트 프레임워크로, 테스트 작성 및 실행을 위한 다양한 기능을 제공. 테스트 케이스를 작성하고 관리하는 데 있어 유연성을 높이며, 새로운 기능들과 다양한 확장성을 통해 효율적인 테스트 환경을 구현할 수 있도록 지원

- Jakarta Mail 2.0.3 <img src="https://github.com/user-attachments/assets/8c27da01-0445-4a85-9487-f1e80557e28f" alt="image" width="30"/>
  - 이메일 발송을 위한 라이브러리로, Spring Boot와 통합하여 비동기적으로 메일을 전송

- AWS S3 2.2.6 <img src="https://github.com/user-attachments/assets/11222770-397f-4f3a-9585-b6937973484e" alt="image" width="30"/>
    - Amazon의 객체 저장 서비스, 대용량 데이터 저장 및 관리에 최적화, 이미지 파일을 효율적으로 저장하고, 쉽게 접근할 수 있도록 지원

### Front-end


- HTML, CSS <img src="https://github.com/user-attachments/assets/e41f8dc5-6635-4da4-aef3-d756a9c4d739" alt="image" width="30"/>
  - 웹 표준을 준수한 마크업 및 스타일링

- React <img src="https://github.com/user-attachments/assets/85d92f1a-14de-4f58-9c98-1b13cd9a19cd" alt="image" width="30"/>
  - 컴포넌트 기반의 UI 라이브러리를 활용하여 효율적인 상태 관리 및 인터랙티브한 사용자 인터페이스 구현

- Axios <img src="https://github.com/user-attachments/assets/7ee7973a-9f9d-4657-bb51-3df0e1473d43" alt="image" width="30"/>
  - 백엔드와의 HTTP 통신을 위한 비동기 요청 처리 라이브러리

### 서비스 배포 환경

- 배포 url
  - [FITinside](https://fitinside.netlify.app)

- 프론트엔드 배포
    - Netlify <img src="https://github.com/user-attachments/assets/f27b8b87-b475-4207-b59a-51bb594ea6e7"  alt="image" width="30"/>

- 백엔드 배포
    - 엘리스 클라우드 VM
        - GNU/Linux 5.15.0-91-generic <img src="https://github.com/user-attachments/assets/d3687e29-4258-431b-a16f-31c7dc7b667f"  alt="image" width="30"/>
        - Ubuntu 22.04.5 LTS <img src="https://github.com/user-attachments/assets/3e98b096-6490-4eb4-bcd4-18787e812c25"  alt="image" width="30"/>

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
