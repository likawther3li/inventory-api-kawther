# Inventory & Order Management API

## How to Run
1. Make sure Java 17+ is installed
2. Clone or unzip the project
3. Open terminal in project folder and run:
4. 4. API will start on: http://localhost:8081

## Database
- H2 file-based SQL database (no installation needed)
- H2 Console: http://localhost:8081/h2-console
- JDBC URL: jdbc:h2:file:./data/inventorydb
- Username: sa / Password: (empty)

## API Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/categories | Create category |
| POST | /api/products | Create product |
| POST | /api/products/{id}/stock-adjustment | Adjust stock |
| POST | /api/customers | Create customer |
| POST | /api/customers/{id}/orders | Create order |
| POST | /api/orders/{id}/items | Add item to order |
| DELETE | /api/orders/{id}/items/{itemId} | Remove item |
| POST | /api/orders/{id}/confirm | Confirm order |
| POST | /api/orders/{id}/status | Update status |
| GET | /api/orders/{id} | Get order |
| GET | /api/products/low-stock?threshold= | Low stock report |

## Business Rules
- Stock is only reduced when order is CONFIRMED, not on DRAFT
- Confirmation fails if any item exceeds available stock
- Cancelling a CONFIRMED order restores stock automatically

## Assumptions
- No authentication required
- H2 file-mode used so data persists between restarts
- Order total calculated at confirmation time using current prices


- Stock conflict returns HTTP 409 Conflict (not 400)
- Order cancellation restores stock automatically
- Low-stock threshold is configurable via query parameter

## Race Condition / Oversell Prevention Test

Two orders (Order 36 and Order 37) both requested 6 units of iPhone 15.
- Order 36 confirmed successfully 
- Order 37 rejected with 409 Conflict 
- Proves the system prevents overselling atomically using @Transactional