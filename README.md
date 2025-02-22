# 강신혁 프렌킷 과제

- Java 23 version
- Spring boot 3.4.3
- Gradle
- postgresql 14


---
## How to run
1. docker run (port - [postgresql-master : 5435, postgresql-slave : 5436])
```shell
docker compose -f ./.lezhin_test/docker-compose.yml up -d
```

2. spring 실행
```shell
./gradlew bootRun
```
---

## UBIQUITOUS LANGUAGE
| Domain    | Description |
|:----------|:------------|
| `User`    | 사용자         |
| `Product` | 상품          |

---
## 📌 API Documentation
## [Users]

> [POST] /users/signup - 사용자 생성

#### RequestBody
| Key         | Type   | Required | Description |
|:------------|:-------|:---------|:------------|
| `email`     | String | true     | 사용자 이메일     |
| `password`  | String | true     | 비밀번호        |

#### Status
| Code  | Description                |
|:------|:---------------------------|
| `201` | 사용자 생성 성공                  |
| `400` | email, password가 존재하지 않을 때 |
| `409` | 중복된 사용자 이메일 존재             |

> #### [POST] /users/signin - 사용자 로그인

##### RequestBody
| Key        | Type   | Required | Description |
|:-----------|:-------|:---------|:------------|
| `email`    | String | true     | 사용자 이메일     |
| `password` | String | true     | 비밀번호        |

##### RequestBody
| Key           | Type    | Required | Description |
|:--------------|:--------|:---------|:------------|
| `accessToken` | String  | true     | jwt 토큰      |
| `expiresIn`   | Integer | true     | 토큰 유효 시간    |

#### Status
| Code  | Description                |
|:------|:---------------------------|
| `200` | 토큰발급 성공                    |
| `400` | email, password가 존재하지 않을 때 |
| `404` | 사용자가 존재하지 않음               |





## [Product]
> #### [POST] /products - 상품 생성

##### RequestBody
| Key           | Type    | Required | Description |
|:--------------|:--------|:---------|:------------|
| `name`        | String  | true     | 상품명         |
| `description` | String  | true     | 상품 설명       |
| `price`       | Integer | true     | 가격          |
| `shippingFee` | Integer | false    | 배송비         |

##### RequestBody
| Key    | Type | Required | Description |
|:-------|:-----|:---------|:------------|
| `data` | UUID | true     | 상품 아이디      |

#### Status
| Code  | Description   |
|:------|:--------------|
| `201` | 상품 생성 성공      |
| `403` | user권한이 아닐 경우 |

