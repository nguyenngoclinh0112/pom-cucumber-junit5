@dashboard
  Feature: Quản lý giao diện trang chủ (Dashboard)
    Background: Người dùng đã đăng nhập thành công vào OrangeHRM
      Given người dùng đang ở trang đăng nhập OranageHRM
      When người dùng đăng nhập với tài khoản "Admin" và mật khẩu "admin123"
      Then người dùng được chuyển đến trang Dashboard

    Scenario: Kiểm tra các widget hiển thị đầy đủ trên Dashboard
      Then Hệ thống hiển thị widget "Time at Work"
      And Hệ thống hiển thị widget "My Actions"
      And Hệ thống hiển thị widget "Quick Launch"
      And Hệ thống hiển thị widget "Buzz Latest Posts"
      And Hệ thống hiển thị widget "Employees on Leave Today"
      And Hệ thống hiển thị biểu đồ tròn "Employee Distribution by Sub Unit"

#    Scenario Outline: Kiểm tra chuyển hướng khi nhấn các icon trong Quick Launch
#      When Người dùng nhấn vào icon "<ten_icon>" trong mục Quick Launch
#      Then Hệ thống chuyển hướng sang trang "<ten_trang_den>"
#
#      Examples:
#        | ten_icon          | ten_trang_den     |
#        | Assign Leave      | Assign Leave      |
#        | Leave List        | Leave List        |
#        | Timesheets        | Select Employee   |
#        | Apply Leave       | Apply Leave       |
#        | My Leave          | My Leave List     |
#        | My Timesheet      | My Timesheet      |
