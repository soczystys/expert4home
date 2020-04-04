# Tygodniowe spotkanie

## Co się udało

- Nie zwariować

## Ustalenia formatu odpowiedzi API

Typy użytkowników

```js
export const UserType = {
    NONE: 'NONE',
    CLIENT: 'CLIENT',
    EXPERT: 'EXPERT'
};
```

Stany orderu

```js
const OrderState = {
    CREATED: 'CREATED',
    ACCEPTED: 'ACCEPTED',
    REJECTED: 'REJECTED',
    COMPLETED: 'COMPLETED',
    COMMENTED: 'COMMENTED'
};
```

## Zasoby rest

Wstępna wizja zasobów REST i innych endpointów.

### Expert

GET /api/expert?pageSize=10&pageNumber=3&name=John

[{"id": 54, "name":"John"}, {"id": 58, "name":"John"}]

GET /api/expert/54

{"name":"John"}

### Order

Powiązane z ekspertem  
Lista zgłoszeń do wykonania dla danego eksperta  
GET /api/expert/\<id\>/order

Powiązane z użytkownikiem  
Lista zgłoszeń utworzonych przez danego użytkownika  
GET /api/user/\<id\>/order

PUT /api/order/\<id\>  
{\<noweDane\>}

------

GET /api/order?expertId=23&userId=77

### Login

POST /api/user/login  
{"login": "Tomasz44", "password": "dafhui"}  
\+ cookie

Response:  
{"id": 329, "permissions": "USER", ...}

### Logout

POST /api/user/logout  
