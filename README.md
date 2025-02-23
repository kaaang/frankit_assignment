# 강신혁 프렌킷 과제

- Java 23 version
- Spring boot 3.4.3
- Gradle
- postgresql 14


---
### API 문서는 하단에 작성 해두었습니다.


### 코드 구조
본 과제는 레이어드 아키텍처를 기반으로 설계하였습니다.

presentation - application - domain(core) 크게 3가지의 레이어를 가지고 있습니다.

또한 DDD 기반의 설계를 유지하도록 노력하였습니다.

추가적으로 CQRS 패턴을 적용하여서 읽기와 쓰기를 분리하였고 이를 위해 database를 리플리케이션 하여서 사용하였습니다.

### 서비스
- 상품 생성, 수정, 삭제, 상세, 리스트 api를 작성하였습니다.
- 상품 옵션 생성, 수정, 삭제, 리스트 api를 작성하였습니다.
- 상품 생성시 옵션을 같이 생성할 수 있게 고려하였지만 요구사항에 존재하지 않았기 때문에 해당 부분은 작성하지 않았지만 command chaining 을 통해서 옵션 생성을 바로 사용할 수 있도록 커멘드들을 분리해두었습니다.


### 선택 사항
- 주요 서비스 흐름에 따른 에러처리 등은 @ControllerAdvice 를 통하여 기본적인 에러들을 핸들링 할 수 있도록 작성해 두었습니다.
- 테스트는 유닛테스트로 행위에 대한 검증과 통합 테스트를 위주로 작성하였습니다.
- 확장 가능한 아키텍쳐로 프랜킷에서 사용한다고 예상되는 클린 아키텍쳐를 적용하여 리팩토링 할려고 하였지만 시간과 지식 부족으로 완성하지 못하여 기존의 레이어드 아키텍쳐 구성으로 제출하게 되었습니다.

### 추가 내용
- repository 내의 issues, project 통해서 개발 과정을 기록할 수 있게 해두었습니다.
- git은 trunk-based 기반으로 활용하였습니다. 사용하였던 브렌치들은 모두 삭제하지 않고 유지해두었습니다.
- spotless를 사용하여서 코드 포멧을 유지하고 있습니다.
- liqubase를 통하여 db를 관리하도록 구성되어 있으며, 과제 테스트 용이성을 위하여 더미 데이터를 삽입하도록 되어있습니다.
- | Key         | value          |
  |:------------|:---------------|
  | `email`     | test@gmail.com |
  | `password`  | password       |
- 위의 계정으로 signin 하여서 사용가능하도록 삽입하여 두었습니다.
- 상품은 20개를 더미데이터로 삽입해두었습니다.

---
## How to run
해당 프로젝트는 docker 환경이 필요합니다.
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

##### ResponseBody
| Key    | Type | Required | Description |
|:-------|:-----|:---------|:------------|
| `data` | UUID | true     | 상품 아이디      |

#### Status
| Code  | Description   |
|:------|:--------------|
| `201` | 상품 생성 성공      |
| `403` | user권한이 아닐 경우 |

> #### [PUT] /products/:id - 상품 수정

##### PathVariable
| Key  | Type | Required | Description |
|:-----|:-----|:---------|:------------|
| `id` | UUID | true     | 상품 아이디      |

##### RequestBody
| Key           | Type    | Required | Description |
|:--------------|:--------|:---------|:------------|
| `name`        | String  | true     | 상품명         |
| `description` | String  | true     | 상품 설명       |
| `price`       | Integer | true     | 가격          |
| `shippingFee` | Integer | false    | 배송비         |


#### Status
| Code  | Description                 |
|:------|:----------------------------|
| `204` | 상품 수정 성공                    |
| `403` | user권한이 아닐 경우, 상품 생성자가 아닐경우 |

> #### [DELETE] /products/:id - 상품 삭제

##### PathVariable
| Key  | Type | Required | Description |
|:-----|:-----|:---------|:------------|
| `id` | UUID | true     | 상품 아이디      |


#### Status
| Code  | Description                 |
|:------|:----------------------------|
| `200` | 상품 삭제 성공                    |
| `403` | user권한이 아닐 경우, 상품 생성자가 아닐경우 |


> #### [GET] /products/:id - 상품 검색

##### PathVariable
| Key  | Type | Required | Description |
|:-----|:-----|:---------|:------------|
| `id` | UUID | true     | 상품 아이디      |

##### ResponseBody
| Key           | Type    | Required | Description |
|:--------------|:--------|:---------|:------------|
| `id`          | UUID    | true     | 상품 아이디      |
| `name`        | String  | true     | 상품 이름       |
| `description` | String  | true     | 상품 설명       |
| `createdBy`   | UUID    | true     | 상품 생성자 아이디  |
| `shippingFee` | Integer | true     | 배송비         |
| `price`       | Integer | true     | 가격          |
| `createdAt`   | Date    | true     | 생성일         |
| `updatedAt`   | Date    | true     | 수정일         |

#### Status
| Code  | Description    |
|:------|:---------------|
| `200` | 상품 조회 성공       |
| `403` | user권한이 아닐 경우  |
| `404` | 상품을 찾을 수 없을 경우 |

> #### [GET] /products - 상품 리스트 검색

##### QueryParams
| Key            | Type    | Required | Description |
|:---------------|:--------|:---------|:------------|
| `size`         | Integer | true     | 조회할 페이지 사이즈 |
| `lastViewedId` | UUID    | false    | 마지막 조회 아이디  |
| `lastViewedAt` | Date    | false    | 마지막 조회 생성일  |

##### ResponseBody - data -> list
| Key           | Type    | Required | Description |
|:--------------|:--------|:---------|:------------|
| `id`          | UUID    | true     | 상품 아이디      |
| `name`        | String  | true     | 상품 이름       |
| `createdBy`   | UUID    | true     | 상품 생성자 아이디  |
| `shippingFee` | Integer | true     | 배송비         |
| `price`       | Integer | true     | 가격          |
| `createdAt`   | Date    | true     | 생성일         |

#### Status
| Code  | Description |
|:------|:------------|
| `200` | 상품리스트 조회 성공 |

> #### [POST] /products/:id/options - 상품 옵션 생성

##### PathVariable
| Key        | Type | Required | Description |
|:-----------|:-----|:---------|:------------|
| `id`       | UUID | true     | 상품 아이디      |

##### RequestBody
| Key          | Type        | Required | Description         |
|:-------------|:------------|:---------|:--------------------|
| `name`       | String      | true     | 옵션 이름               |
| `type`       | Enum        | true     | 옵션 타입(TEXT, SELECT) |
| `values`     | List-String | false    | SELECT 타입일 경우 선택 옵션 |
| `extraPrice` | Integer     | true     | 옵션 비용               |

#### Status
| Code  | Description     |
|:------|:----------------|
| `201` | 옵션 생성 성공        |
| `403` | 내가 생성한 상품이 아닐경우 |
| `404` | 상품을 찾을 수 없는 경우  |

> #### [PUT] /products/:id/options/:optionId - 상품 옵션 수정
 
##### PathVariable
| Key        | Type | Required | Description |
|:-----------|:-----|:---------|:------------|
| `id`       | UUID | true     | 상품 아이디      |
| `optionId` | UUID | true     | 상품 옵션 아이디   |

##### RequestBody
| Key          | Type        | Required | Description         |
|:-------------|:------------|:---------|:--------------------|
| `name`       | String      | true     | 옵션 이름               |
| `type`       | Enum        | true     | 옵션 타입(TEXT, SELECT) |
| `values`     | List-String | false    | SELECT 타입일 경우 선택 옵션 |
| `extraPrice` | Integer     | true     | 옵션 비용               |

#### Status
| Code  | Description     |
|:------|:----------------|
| `204` | 옵션 수정 성공        |
| `403` | 내가 생성한 상품이 아닐경우 |
| `404` | 상품을 찾을 수 없는 경우  |

> #### [DELETE] /products/:id/options/:optionId - 상품 옵션 삭제

##### PathVariable
| Key        | Type | Required | Description |
|:-----------|:-----|:---------|:------------|
| `id`       | UUID | true     | 상품 아이디      |
| `optionId` | UUID | true     | 상품 옵션 아이디   |


#### Status
| Code  | Description     |
|:------|:----------------|
| `200` | 옵션 삭제 성공        |
| `403` | 내가 생성한 상품이 아닐경우 |
| `404` | 상품을 찾을 수 없는 경우  |

> #### [GET] /products/:id/options - 상품 옵션 리스트 검색

##### PathVariable
| Key  | Type | Required | Description |
|:-----|:-----|:---------|:------------|
| `id` | UUID | true     | 상품 아이디      |

##### ResponseBody - data -> list
| Key          | Type           | Required | Description         |
|:-------------|:---------------|:---------|:--------------------|
| `id`         | UUID           | true     | 옵션 아이디              |
| `name`       | String         | true     | 옵션 이름               |
| `type`       | Enum           | true     | 옵션 타입(TEXT, SELECT) |
| `values`     | List -> String | false    | 옵션 값(SELECT일 경우)    |
| `extraPrice` | Integer        | true     | 가격                  |

#### Status
| Code  | Description  |
|:------|:-------------|
| `200` | 옵션 리스트 조회 성공 |