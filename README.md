# 🛒 Mini Shopee - E-Commerce Backend RESTful API

> Hệ thống backend mô phỏng sàn thương mại điện tử Shopee thu nhỏ, được xây dựng trên nền tảng **Spring Boot** và **PostgreSQL** theo chuẩn kiến trúc RESTful API hiện đại.

---

## 📌 Mục lục
- [Giới thiệu](#-giới-thiệu)
- [Công nghệ sử dụng (Tech Stack)](#-công-nghệ-sử-dụng-tech-stack)
- [Các tính năng chính](#-các-tính-năng-chính)
- [Cấu trúc dự án](#-cấu-trúc-dự-án)
- [Chuẩn hóa phản hồi API (API Contract)](#-chuẩn-hóa-phản-hồi-api-api-contract)
- [Hướng dẫn cài đặt & khởi chạy](#-hướng-dẫn-cài-đặt--khởi-chạy)
- [Tài liệu API với Swagger UI](#-tài-liệu-api-với-swagger-ui)
- [Danh sách API Endpoints](#-danh-sách-api-endpoints)
- [Lộ trình phát triển tiếp theo (Roadmap)](#-lộ-trình-phát-triển-tiếp-theo-roadmap)

---

## 📖 Giới thiệu
**Mini Shopee** là dự án backend cung cấp đầy đủ các nghiệp vụ cốt lõi của một sàn e-commerce: từ quản lý người dùng, danh mục sản phẩm, quản lý giỏ hàng đến xử lý luồng đặt hàng (Checkout & Order Status). Dự án được thiết kế theo mô hình kiến trúc phân lớp (Layered Architecture: Controller - Service - Repository) với cơ chế bọc phản hồi và xử lý ngoại lệ tập trung.

---

## 🛠 Công nghệ sử dụng (Tech Stack)

| Công nghệ / Thư viện | Phiên bản | Mục đích sử dụng |
| :--- | :--- | :--- |
| **Java** | 21 (LTS) | Ngôn ngữ lập trình chính |
| **Spring Boot** | 3.x / 4.x | Framework phát triển backend chính |
| **Spring Data JPA** | - | Tương tác cơ sở dữ liệu qua ORM (Hibernate) |
| **PostgreSQL** | 15+ | Hệ quản trị cơ sở dữ liệu quan hệ |
| **Spring Validation** | - | Kiểm thực dữ liệu đầu vào (Email, Password, Size,...) |
| **Lombok** | - | Giảm thiểu boilerplate code (Getter, Setter, Builder) |
| **Springdoc OpenAPI** | 2.8.5 | Tự động tạo tài liệu và giao diện test API tương tác (Swagger UI) |
| **Maven Wrapper** | - | Công cụ quản lý phụ thuộc và build dự án |

---

## ✨ Các tính năng chính

1. **User Management (Quản lý người dùng)**:
   - Đăng ký tài khoản với validation chặt chẽ (email chuẩn, password tối thiểu 8 ký tự).
   - Quản lý thông tin cá nhân, phân quyền (Role: `USER`, `ADMIN`).
2. **Category Management (Quản lý danh mục)**:
   - Thêm, sửa, xóa và truy vấn danh mục ngành hàng.
3. **Product Management (Quản lý sản phẩm)**:
   - Quản lý kho hàng, tên, giá bán, số lượng tồn kho và liên kết danh mục.
   - Lọc sản phẩm theo danh mục.
4. **Cart Management (Quản lý giỏ hàng)**:
   - Mỗi người dùng có một giỏ hàng riêng biệt.
   - Thêm sản phẩm vào giỏ, điều chỉnh số lượng mua, xóa từng món hàng.
5. **Order & Checkout Management (Xử lý đơn hàng)**:
   - Checkout đơn hàng tự động tính tổng tiền từ giỏ hàng.
   - Quản lý trạng thái đơn hàng: `PENDING` ➔ `CONFIRMED` ➔ `SHIPPING` ➔ `DELIVERED` (hoặc `CANCELLED`).
   - Cho phép hủy đơn và tra cứu lịch sử mua hàng theo User.
6. **Centralized Error & Response Handling**:
   - Toàn bộ kết quả (thành công hoặc thất bại) đều trả về một khuôn mẫu đồng nhất `ApiResponse<T>`.
   - Bắt lỗi tập trung qua `@RestControllerAdvice` kết hợp enum `ErrorCode` và custom `AppException`.

---

## 📂 Cấu trúc dự án

```text
mini-shopee/
├── src/main/java/com/mnhquan/minishopee/
│   ├── common/
│   │   ├── exception/        # Quản lý lỗi: AppException, ErrorCode, GlobalExceptionHandling
│   │   └── response/         # Chuẩn hóa response: ApiResponse<T>
│   ├── controller/           # Tầng tiếp nhận HTTP Request (User, Category, Product, Cart, Order)
│   ├── dto/
│   │   ├── request/          # Request DTOs (Validate data đầu vào)
│   │   └── response/         # Response DTOs (Dữ liệu trả về Frontend)
│   ├── entity/               # Các JPA Entity: User, Product, Category, Cart, CartItem, Order, OrderItem
│   ├── repository/           # Tầng truy vấn DB (Spring Data JPA Repositories)
│   ├── service/              # Tầng xử lý nghiệp vụ Business Logic
│   └── MiniShopeeApplication.java
└── src/main/resources/
    └── application.yaml      # Cấu hình Database & Swagger UI
```

---

## 📦 Chuẩn hóa phản hồi API (API Contract)

Toàn bộ API đều trả về cấu trúc JSON thống nhất:

### Phản hồi thành công (Success Response):
```json
{
  "code": 1000,
  "message": "Order placed successfully",
  "data": {
    "id": "8a80808a-8280...",
    "userId": "usr-01",
    "totalPrice": 350000.0,
    "status": "PENDING"
  },
  "timestamp": "2026-11-24T14:15:30"
}
```

### Phản hồi lỗi (Error Response):
```json
{
  "code": 1004,
  "message": "Password must be at least 8 characters",
  "timestamp": "2026-11-24T14:15:35"
}
```

---

## 🚀 Hướng dẫn cài đặt & Khởi chạy

### 1. Yêu cầu hệ thống (Prerequisites)
- **Java Development Kit (JDK)**: Phiên bản 21 trở lên.
- **PostgreSQL**: Đang chạy trên cổng mặc định `5432`.
- Tạo một database mới trong PostgreSQL:
  ```sql
  CREATE DATABASE mini_shopee_db;
  ```

### 2. Cấu hình cơ sở dữ liệu
Dự án sử dụng cơ chế **Spring Profile (`local`)** để bảo mật thông tin nhạy cảm. Tạo file `src/main/resources/application-local.yaml` (file này đã được đưa vào `.gitignore` để không bị đẩy lên Git) với nội dung:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/mini_shopee_db
    username: postgres
    password: <your_db_password>
```
*(File cấu hình chính `application.yaml` sẽ tự động kích hoạt profile `local` khi chạy ở môi trường phát triển)*.

### 3. Build và Chạy ứng dụng

- **Trên Windows (PowerShell / Command Prompt)**:
  ```powershell
  .\mvnw.cmd spring-boot:run
  ```

- **Trên Linux / macOS**:
  ```bash
  chmod +x mvnw
  ./mvnw spring-boot:run
  ```

Ứng dụng sẽ khởi động tại cổng mặc định: `http://localhost:8080`.

---

## 📑 Tài liệu API với Swagger UI

Dự án đã được tích hợp sẵn **Swagger UI** (OpenAPI 3). Bạn có thể trực tiếp kiểm thử (test) các endpoint, gửi payload JSON và xem kết quả mà không cần cài đặt thêm Postman.

👉 **Đường dẫn truy cập Swagger UI:**
```text
http://localhost:8080/swagger-ui.html
```

👉 **Đường dẫn xem OpenAPI Schema (JSON):**
```text
http://localhost:8080/v3/api-docs
```

![Swagger Preview Hint](https://raw.githubusercontent.com/swagger-api/swagger-ui/master/flavor-icon.png)

### Cách test nhanh trên Swagger UI:
1. Mở trình duyệt và truy cập: `http://localhost:8080/swagger-ui.html`.
2. Chọn một nhóm API (ví dụ: `user-controller` hoặc `product-controller`).
3. Bấm vào một endpoint bất kỳ ➔ Bấm **Try it out**.
4. Điền các trường tham số hoặc Request Body JSON cần thiết.
5. Bấm **Execute** để xem trực tiếp phản hồi từ Server.

---

## 🧭 Danh sách API Endpoints

### 1. User Endpoints (`/api/v1/users`)
| Method | Endpoint | Mô tả |
| :---: | :--- | :--- |
| `POST` | `/api/v1/users` | Đăng ký / Tạo người dùng mới (có validation) |
| `GET` | `/api/v1/users` | Lấy danh sách toàn bộ người dùng |
| `GET` | `/api/v1/users/{id}` | Lấy chi tiết người dùng theo ID |
| `PUT` | `/api/v1/users/{id}` | Cập nhật thông tin người dùng |
| `DELETE` | `/api/v1/users/{id}` | Xóa người dùng |

### 2. Category Endpoints (`/api/v1/categories`)
| Method | Endpoint | Mô tả |
| :---: | :--- | :--- |
| `POST` | `/api/v1/categories` | Tạo mới danh mục ngành hàng |
| `GET` | `/api/v1/categories` | Lấy danh sách tất cả danh mục |
| `GET` | `/api/v1/categories/{id}` | Lấy chi tiết danh mục theo ID |
| `PUT` | `/api/v1/categories/{id}` | Cập nhật thông tin danh mục |
| `DELETE` | `/api/v1/categories/{id}` | Xóa danh mục |

### 3. Product Endpoints (`/api/v1/products`)
| Method | Endpoint | Mô tả |
| :---: | :--- | :--- |
| `POST` | `/api/v1/products` | Thêm sản phẩm mới vào kho |
| `GET` | `/api/v1/products` | Lấy danh sách tất cả sản phẩm |
| `GET` | `/api/v1/products/{id}` | Lấy chi tiết sản phẩm |
| `GET` | `/api/v1/products/category/{categoryId}` | Lấy danh sách sản phẩm thuộc danh mục |
| `PUT` | `/api/v1/products/{id}` | Cập nhật thông tin & số lượng sản phẩm |
| `DELETE` | `/api/v1/products/{id}` | Xóa sản phẩm |

### 4. Cart Endpoints (`/api/v1/cart`)
| Method | Endpoint | Mô tả |
| :---: | :--- | :--- |
| `GET` | `/api/v1/cart?userId={userId}` | Lấy thông tin giỏ hàng của người dùng |
| `POST` | `/api/v1/cart/items?userId={userId}` | Thêm sản phẩm vào giỏ hàng |
| `PUT` | `/api/v1/cart/items/{itemId}?userId={userId}` | Cập nhật số lượng sản phẩm trong giỏ |
| `DELETE` | `/api/v1/cart/items/{itemId}?userId={userId}` | Xóa sản phẩm ra khỏi giỏ hàng |

### 5. Order Endpoints (`/api/v1/orders`)
| Method | Endpoint | Mô tả |
| :---: | :--- | :--- |
| `POST` | `/api/v1/orders/checkout` | Thực hiện thanh toán / Đặt đơn hàng |
| `GET` | `/api/v1/orders/{id}` | Tra cứu chi tiết đơn hàng |
| `GET` | `/api/v1/orders/user/{userId}` | Lấy lịch sử các đơn hàng của người dùng |
| `PUT` | `/api/v1/orders/{id}/status?status={STATUS}` | Cập nhật trạng thái đơn hàng |
| `POST` | `/api/v1/orders/{id}/cancel` | Hủy đơn hàng |

---

## 🗺 Lộ trình phát triển tiếp theo (Roadmap)
- [ ] **Spring Security & JWT**: Triển khai cơ chế xác thực Stateless Authentication với Access Token và Refresh Token.
- [ ] **Password Encryption**: Mã hóa mật khẩu lưu trữ trong Database bằng thuật toán `BCrypt`.
- [ ] **Role-Based Access Control (RBAC)**: Phân quyền API theo Role (`ADMIN` quản lý sản phẩm/danh mục, `USER` mua sắm).
- [ ] **Tích hợp cổng thanh toán**: VNPay / MoMo / Stripe.
- [ ] **Tối ưu hóa hiệu năng**: Caching với Redis, phân trang & tìm kiếm sản phẩm nâng cao (Pagination & Sorting).
