package stepdefinitions;

import hooks.Hooks;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.AdminPage;

public class AdminStep {

    private final Hooks hooks;
    private AdminPage adminPage;

    public AdminStep (Hooks hooks){
        this.hooks = hooks;
        this.adminPage = new AdminPage(hooks.getDriver(),hooks.getWait());
    }

    @Then("người dùng di chuyển đến trang Admin")
    public void nguoi_dung_di_chuyen_den_Admin_page() throws InterruptedException{
        adminPage.open();
        Thread.sleep(1000);
    }

    @When("người dùng tìm kiếm username {string} và userrole {string}")
    public void nguoi_dung_tim_kiem_user(String username, String userRole) throws InterruptedException{
        adminPage.filterUser(username,userRole);
        Thread.sleep(1000);
    }

    @Then("username và userole hiển thị ở phần kết quả search")
    public void kiem_tra_ket_qua_search(){
        Assertions.assertTrue(adminPage.checkNumberofRecords(),"Số lượng record đúng với dữ liệu");
    }
}
