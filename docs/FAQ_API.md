# FAQ API Documentation

This document describes the API endpoints and data models for the FAQ (Knowledge Base) feature.

## Base URL
The API base URL is resolved via `BackendUrlProvider.resolve()` (typically `http://localhost:8000` for local development).

## Authentication
All FAQ endpoints require a Bearer token in the `Authorization` header.
```
Authorization: Bearer <access_token>
```

---

## Endpoints

### 1. Get All FAQ Categories
Returns a list of all FAQ categories, each containing its respective questions.

**URL:** `/faq`  
**Method:** `GET`  
**Response Code:** `200 OK`

#### Response Body (JSON):
```json
[
  {
    "id": "string",
    "title": "string",
    "iconName": "string",
    "questions": [
      {
        "id": "string",
        "title": "string",
        "answerMarkdown": "string (Markdown formatted text)"
      }
    ]
  }
]
```

---

## Data Models

### FaqCategoryDto
Represents a group of related questions.
| Field | Type | Description |
| :--- | :--- | :--- |
| `id` | `String` | Unique identifier for the category. |
| `title` | `String` | Human-readable title of the category (e.g., "General Questions"). |
| `iconName` | `String` | Identifier for the icon to be displayed in the UI. |
| `questions` | `List<FaqQuestionDto>` | List of questions belonging to this category. |

### FaqQuestionDto
Represents an individual FAQ item.
| Field | Type | Description |
| :--- | :--- | :--- |
| `id` | `String` | Unique identifier for the question. |
| `title` | `String` | The question text. |
| `answerMarkdown` | `String` | The answer in Markdown format, supporting images, links, and formatting. |

---

## Implementation Details

- **DTOs:** Located in `ru.orderdorms.features.services.data.network.model.FaqDto`
- **Domain Models:** Located in `ru.orderdorms.features.services.domain.model.faq`
- **Ktor Client:** Uses `finalAuthorizedClient` with automatic Bearer token injection.
- **Error Handling:** Returns `Either.Left(DomainError)` in case of network failures or 4xx/5xx responses.
