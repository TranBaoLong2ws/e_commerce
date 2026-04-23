# 🛒 E-Commerce Backend (Spring Boot)

Đây là hệ thống máy chủ (backend) cho một ứng dụng thương mại điện tử được xây dựng bằng Spring Boot.

Hệ thống hỗ trợ xác thực người dùng, quản lý sản phẩm, giỏ hàng và xử lý đơn hàng.

## 🚀 Tech Stack

- Java 23
- Spring Boot
- Spring Security + JWT
- Spring Data JPA (Hibernate)
- MySQL
- Maven
- Swagger UI

## ✨ Features

### 👤 Authentication & Authorization
- Register / Login
- JWT-based authentication
- Role-based access (USER / ADMIN)

### 📦 Product
- Create / Update / Delete product (ADMIN)
- Get all products with pagination
- Filter products (Name, price)

### 🛒 Cart
- Add product to cart
- Xem giỏ hàng
- Remove item

### 📑 Order
- Checkout from cart
- Create order with order items
- Tính tổng giá
- Xem tất cả đơn hàng
- Tìm đơn hàng (theo id)

### Category
- Create/Update/Delete Category (ADMIN)
- Get all category with pagination
- Filter category (Name)

### Images 
- Thêm ảnh cho product

## 🔄 Quy trình thanh toán

1. Người dùng đăng nhập và nhận mã thông báo JWT
2. Thêm sản phẩm vào giỏ hàng
3. Gọi API thanh toán
4. Hệ thống:
- Lấy giỏ hàng của người dùng
- Tạo đơn hàng
- Tạo các mặt hàng trong đơn hàng (chụp ảnh dữ liệu sản phẩm)
- Tính tổng giá tiền
- Xóa giỏ hàng


## ⚙️ Cách chạy

### 1. Clone project
git clone https://github.com/TranBaoLong2ws/e_commerce

### 2. Config database
Update application.properties:

spring.datasource.url=jdbc:mysql://localhost:3307/ecommerce_db?serverTimezone=UTC&allowPublicKeyRetrieval=true&useSSL=false
spring.datasource.username=root
spring.datasource.password=123456


### 3. Run project
Using IntelliJ or:
mvn spring-boot:run

## 📡 Ví dụ API

### Register
POST /api/auth/register

### Login
POST /api/auth/login

### Get Products
GET /api/product?page=0&size=5

### Checkout
POST /api/orders/checkout

Authorization: Bearer <token>

## 🚧 Cải thiện trong tương lai

- Thêm phương thức thanh toán 
- Cải thiện xử lý ngoại lệ

## 🏗️ Cấu trúc dự án

- Controller: Xử lý các yêu cầu HTTP
- Service: Logic nghiệp vụ
- Repository: Lớp truy cập dữ liệu
- Entity: Ánh xạ cơ sở dữ liệu
- DTO: Đối tượng yêu cầu/phản hồi
- Security: Bộ lọc và cấu hình JWT

## 🔐 Security Flow

1. User login → receive JWT token  
2. Client sends token in Authorization header  
3. JWT filter validates token  
4. If valid → set user in SecurityContext  
5. Access API based on role (USER / ADMIN)  

