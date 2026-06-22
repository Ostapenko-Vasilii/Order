# Authentication API Documentation

This document describes the API endpoints and data models for User Authentication, Registration, and Password Recovery.

## Base URL
The API base URL is resolved via `BackendUrlProvider.resolve()` (typically `http://localhost:8000` for local development).

---

## 1. Registration Flow

### Step 1: Check Invite Code
Verify the invite code provided by the administration.

**URL:** `/api/v1/auth/register/invite-code`  
**Method:** `POST`

#### Request Body:
```json
{ "code": "string" }
```

#### Response Body:
```json
{ "invite_token": "string" }
```

---

### Step 2: Send Registration Email
Submit user email to receive a verification code. Requires `invite_token` in the Authorization header.

**URL:** `/api/v1/auth/register/mail`  
**Method:** `POST`  
**Header:** `Authorization: Bearer <invite_token>`

#### Request Body:
```json
{ "email": "string" }
```

#### Response Body:
```json
{ 
  "temp_token": "string",
  "next_retry_after": 60 
}
```

---

### Step 3: Verify Registration Code
Verify the code sent to the email. Requires `temp_token` in the Authorization header.

**URL:** `/api/v1/auth/register/email-code`  
**Method:** `POST`  
**Header:** `Authorization: Bearer <temp_token>`

#### Request Body:
```json
{ "code": "string" }
```

#### Response Body:
```json
{ "final_register_token": "string" }
```

---

### Step 4: Set Password & Complete Registration
Set the user password. Requires `final_register_token` in the Authorization header. Returns final session tokens.

**URL:** `/api/v1/auth/register/set-password`  
**Method:** `POST`  
**Header:** `Authorization: Bearer <final_register_token>`

#### Request Body:
```json
{ "password": "string" }
```

#### Response Body:
```json
{ 
  "access_token": "string",
  "refresh_token": "string" 
}
```

---

## 2. Login

**URL:** `/api/v1/auth/login`  
**Method:** `POST`

#### Request Body:
```json
{ 
  "email": "string",
  "password": "string" 
}
```

#### Response Body:
```json
{ 
  "access_token": "string",
  "refresh_token": "string" 
}
```

---

## 3. Password Recovery Flow

### Step 1: Send Recovery Email
**URL:** `/api/v1/auth/forgot-pass/mail`  
**Method:** `POST`

#### Request Body:
```json
{ "email": "string" }
```

#### Response Body:
```json
{ 
  "temp_token": "string",
  "next_retry_after": 60 
}
```

---

### Step 2: Verify Recovery Code
**URL:** `/api/v1/auth/forgot-pass/email-code`  
**Method:** `POST`  
**Header:** `Authorization: Bearer <temp_token>`

#### Request Body:
```json
{ "code": "string" }
```

#### Response Body:
```json
{ "reset_token": "string" }
```

---

### Step 3: Set New Password
**URL:** `/api/v1/auth/forgot-pass/set-password`  
**Method:** `POST`  
**Header:** `Authorization: Bearer <reset_token>`

#### Request Body:
```json
{ "password": "string" }
```

#### Response Body: `200 OK (Empty)`

---

## Utility Endpoints

### Resend Email Code
Request a new verification code. Requires current `temp_token` in the Authorization header.

**URL:** `/api/v1/auth/send-email-code`  
**Method:** `POST`  
**Header:** `Authorization: Bearer <temp_token>`

#### Response Body:
```json
{ 
  "next_retry_after": 60,
  "message": "string (optional)" 
}
```
