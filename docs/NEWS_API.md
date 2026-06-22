# News & Notifications API Documentation

This document describes the API endpoints and data models for the News and Notifications feature.

## Base URL
The API base URL is resolved via `BackendUrlProvider.resolve()` (typically `http://localhost:8000` for local development).

## Authentication
All endpoints require a Bearer token in the `Authorization` header.

---

## News Endpoints

### 1. Get All News
Returns a list of news items/notifications, optionally filtered by type.

**URL:** `/news`  
**Method:** `GET`  
**Response Code:** `200 OK`

#### Query Parameters:
- `type` (optional): `URGENT | ANNOUNCEMENT | EVENT | OTHER`

#### Response Body (JSON):
```json
[
  {
    "id": "string",
    "title": "string",
    "content": "string",
    "type": "URGENT | ANNOUNCEMENT | EVENT | OTHER",
    "timestamp": "ISO-8601 string",
    "details": "string (optional extra info like 'Today at 20:00')"
  }
]
```

---

## Data Models

### NewsType (Enum)
- `URGENT`: Срочно (Red style)
- `ANNOUNCEMENT`: Объявление (Default/Blue style)
- `EVENT`: Событие (Green/Grey style)
- `OTHER`: Прочее

---

## Implementation Details
- **Filtering**: Done client-side for better UX with chips.
- **Grouping**: Grouped by "Today" and "Earlier" in the UI based on `timestamp`.
