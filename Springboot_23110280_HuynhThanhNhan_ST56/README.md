

## Thông tin sinh viên
- **Họ tên:** Huỳnh Thanh Nhân
- **MSSV:** 23110280


## Mô tả dự án
Hệ thống quản lý danh mục (Category Management System) được xây dựng bằng Spring Boot, Thymeleaf, và SQL Server. Dự án thực hiện đầy đủ các chức năng CRUD (Create, Read, Update, Delete) và tìm kiếm phân trang cho entity Category.


## Cấu trúc dự án
```
src/
├── main/
│   ├── java/vn/iotstar/
│   │   ├── Controller/
│   │   │   ├── HomeController.java
│   │   │   └── admin/CategoryController.java
│   │   ├── Config/StaticResourceConfig.java
│   │   ├── Entity/
│   │   │   ├── CategoryEntity.java
│   │   │   └── UserEntity.java
│   │   ├── Model/CategoryModel.java
│   │   ├── Repository/CategoryRepository.java
│   │   ├── Service/
│   │   │   ├── ICategoryService.java
│   │   │   ├── IFileUploadService.java
│   │   │   └── Impl/
│   │   │       ├── CategoryServiceImpl.java
│   │   │       └── FileUploadServiceImpl.java
│   │   └── Springboot23110280HuynhThanhNhanSt56Application.java
│   └── resources/
│       ├── static/images/logo/avatar.jpg
│       ├── templates/
│       │   ├── index.html
│       │   └── admin/
│       │       ├── layout-admin.html
│       │       ├── fragments/
│       │       │   ├── header.html
│       │       │   ├── nav.html
│       │       │   └── footer.html
│       │       └── categories/
│       │           ├── list.html
│       │           ├── addOrEdit.html
│       │           └── searchpaginated.html
│       └── application.properties
├── uploads/ (thư mục lưu file upload)
```

## Cấu hình Database
Dự án sử dụng SQL Server. Cập nhật cấu hình trong `application.properties`:

## Hướng dẫn chạy ứng dụng

1. Tạo database `test_db` trong SQL Server
2. Cập nhật thông tin kết nối trong `application.properties`


### 4. Truy cập ứng dụng
- **Trang chủ:** http://localhost:8088
- **Admin Panel:** http://localhost:8088/admin/categories/searchpaginated
- **Thêm Category:** http://localhost:8088/admin/categories/add

## Các endpoint chính

| Method | URL | Mô tả |
|--------|-----|-------|
| GET | `/` | Trang chủ |
| GET | `/admin` | Redirect đến admin panel |
| GET | `/admin/categories/list` | Danh sách tất cả categories |
| GET | `/admin/categories/add` | Form thêm category mới |
| POST | `/admin/categories/saveOrUpdate` | Lưu/cập nhật category |
| GET | `/admin/categories/edit/{id}` | Form sửa category |
| GET | `/admin/categories/delete/{id}` | Xóa category |
| GET | `/admin/categories/search` | Tìm kiếm categories |
| GET/POST | `/admin/categories/searchpaginated` | Tìm kiếm và phân trang |


