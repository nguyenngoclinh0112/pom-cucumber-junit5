@admin
  Feature: Quản lý giao diện Admin (System User)
    Là một Admin account, tôi muốn lọc danh sách người dùng theo username và role để quản lý tài khoản hệ thống

  Background: Người dùng đăng nhập hệ thống OrangeHRM thành công
    Given người dùng đang ở trang đăng nhập OranageHRM
    When người dùng đăng nhập với tài khoản "Admin" và mật khẩu "admin123"
    Then người dùng di chuyển đến trang Admin

  Scenario: Tìm kiếm user dựa trên Username và UserRole
    When người dùng tìm kiếm username "Admin" và userrole "Admin"
    Then username và userole hiển thị ở phần kết quả search
