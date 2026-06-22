# Requests and Complaints API Documentation

This document describes the API endpoints and data models for Maintenance Requests and Resident Complaints.

## Base URL
The API base URL is resolved via `BackendUrlProvider.resolve()` (typically `http://localhost:8000` for local development).

## Authentication
All endpoints require a Bearer token in the `Authorization` header.
```
Authorization: Bearer <access_token>
```

---

## Maintenance Requests

### 1. Create Maintenance Request
Submit a new request for room maintenance.

**URL:** `/requests`  
**Method:** `POST`  
**Response Code:** `201 Created`

#### Request Body (JSON):
```json
{
  "roomNumber": "string",
  "description": "string",
  "note": "string (optional)"
}
```

### 2. Get User Requests
Returns a list of maintenance requests submitted by the authenticated user.

**URL:** `/requests`  
**Method:** `GET`  
**Response Code:** `200 OK`

#### Response Body (JSON):
```json
[
  {
    "id": "string",
    "roomNumber": "string",
    "description": "string",
    "note": "string",
    "status": "PENDING | ACCEPTED | COMPLETED | REJECTED",
    "comment": "string (optional)",
    "createdAt": "ISO-8601 string"
  }
]
```

---

## Complaints

### 1. Submit Complaint
Submit a new complaint.

**URL:** `/complaints`  
**Method:** `POST`  
**Response Code:** `201 Created`

#### Request Body (JSON):
```json
{
  "text": "string"
}
```

### 2. Get User Complaints
Returns a list of complaints submitted by the authenticated user.

**URL:** `/complaints`  
**Method:** `GET`  
**Response Code:** `200 OK`

#### Response Body (JSON):
```json
[
  {
    "id": "string",
    "text": "string",
    "status": "PENDING | REVIEWED | RESOLVED | REJECTED",
    "answer": "string (optional)",
    "createdAt": "ISO-8601 string"
  }
]
```

---

## Data Models

### RequestStatus (Enum)
- `PENDING`: Заявка подана
- `ACCEPTED`: Принято
- `COMPLETED`: Выполнено
- `REJECTED`: Отклонено

### ComplaintStatus (Enum)
- `PENDING`: На рассмотрении
- `REVIEWED`: Рассмотрено
- `RESOLVED`: Решено
- `REJECTED`: Отклонено
