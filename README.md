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