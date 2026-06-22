# Booking API Documentation

This document describes the API endpoints and data models for Booking facilities/slots.

## Base URL
The API base URL is resolved via `BackendUrlProvider.resolve()` (typically `http://localhost:8000` for local development).

## Error Handling
In case of failure (e.g., slot already taken, server error), the API returns an error object:
```json
{
  "code": "string",
  "message": "string (human-readable error description)"
}
```

---

## Booking Endpoints

### 1. Get Available Slots
Returns a list of available booking slots grouped by date.

**URL:** `/booking/slots`  
**Method:** `GET`  
**Response Code:** `200 OK`

#### Response Body (JSON):
```json
[
  {
    "date": "YYYY-MM-DD",
    "slots": [
      {
        "id": "string",
        "timeRange": "HH:mm-HH:mm",
        "isAvailable": boolean
      }
    ]
  }
]
```

### 2. Book a Slot
Submit a booking for a specific slot.

**URL:** `/booking/book`  
**Method:** `POST`  
**Response Code:** `201 Created`

#### Request Body (JSON):
```json
{
  "slotId": "string"
}
```

### 3. Get User Bookings
Returns a list of bookings made by the authenticated user.

**URL:** `/booking/my`  
**Method:** `GET`  
**Response Code:** `200 OK`

#### Response Body (JSON):
```json
[
  {
    "id": "string",
    "slotId": "string",
    "date": "YYYY-MM-DD",
    "timeRange": "HH:mm-HH:mm",
    "status": "CONFIRMED | CANCELLED | COMPLETED",
    "createdAt": "ISO-8601 string"
  }
]
```

---

## Data Models

### BookingStatus (Enum)
- `CONFIRMED`: Бронирование подтверждено
- `CANCELLED`: Бронирование отменено
- `COMPLETED`: Завершено
