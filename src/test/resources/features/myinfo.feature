@myinfo
  Feature: Thông tin cá nhân
    Là một người dùng, tôi muốn quản lý thông tin cá nhân của tôi

  Background: người dùng đăng nhập hệ thống OrangeHRM thành công
    Given người dùng đang ở trang đăng nhập OranageHRM
    When người dùng đăng nhập với tài khoản "Admin" và mật khẩu "admin123"
    Then người dùng di chuyển đến trang My Info

  Scenario: người dùng upload avatar thành công
    When người dùng upload avatar "Unix_user_account.png"
    Then avatar được upload thành công

#  Scenario: người dùng upload avatar thất bại
#    When người dùng upload avatar "SampleJPGImage_5mb.png"
#    Then hệ thống báo lỗi upload thất bại