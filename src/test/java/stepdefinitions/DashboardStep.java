package stepdefinitions;

import com.sun.jna.WString;
import hooks.Hooks;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DashboardStep {
    private final Hooks hooks;
    private final WebDriver driver;

    public DashboardStep(Hooks hooks) {
        this.hooks = hooks;
        this.driver = hooks.getDriver();
    }

    @Then("Hệ thống hiển thị widget {string}")
    public void kiemTraWidgetHienThi(String tenWidget){
        By widgetLocator = By.xpath("//p[text()='" + tenWidget + "']");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(widgetLocator));

        boolean isDisplayed = driver.findElement(widgetLocator).isDisplayed();
        Assertions.assertTrue(isDisplayed, "không tìm thấy widget: " + tenWidget);
    }

    @And("Hệ thống hiển thị biểu đồ tròn {string}")
    public void kiemTraBieuDoTron(String tenBieuDo){
        By chartTitle = By.xpath("//p[text()='"+ tenBieuDo + "']");

        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(chartTitle));

        boolean isDisplayed = element.isDisplayed();

        Assertions.assertTrue(isDisplayed,"không tìm thấy biểu đồ: " + tenBieuDo);
    }

    @When("Người dùng nhấn vào icon {string} trong mục Quick Launch")
    public void nhanIconQuickLaunch(String tenIcon){
        By iconLocator = By.xpath("//button[@title='" +tenIcon+ "']");
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(iconLocator)).click();
    }

    @Then("Hệ thống chuyển hướng sang trang {string}")
    public void kiemTraChuyenTrang(String tenTrangDen){
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));

        By headerLocator = By.xpath("//h6[@class='oxd-text oxd-text--h6 orangehrm-main-title' and text()='" +tenTrangDen+ "']");
        boolean isHeaderDisplayed = wait.until(ExpectedConditions.visibilityOfElementLocated(headerLocator)).isDisplayed();
        Assertions.assertTrue(isHeaderDisplayed,"không chuyển hướng thành công tới trang: " + tenTrangDen);
    }
}
