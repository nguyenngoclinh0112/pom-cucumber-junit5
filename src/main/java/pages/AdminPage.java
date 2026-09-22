package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AdminPage extends BasePage{

    private static final By inputUsername = By.xpath("//div[@class='oxd-form-row']//input[@class='oxd-input oxd-input--active']");
    private static final By selectUserRole = By.xpath("//div[@class='oxd-input-group oxd-input-field-bottom-space'][.//label[text()='User Role']]//div[@class='oxd-select-text oxd-select-text--active']");
    private static final By searchBtn = By.xpath("//button[@type ='submit']");
    private static final By Record_Count = By.xpath("//div[@class = 'orangehrm-horizontal-padding orangehrm-vertical-padding']//span");
    private static final By dataRows = By.xpath("//div[@class = 'oxd-table-card']");

    public AdminPage(WebDriver driver, WebDriverWait wait){
        super(driver, wait);
    }

    public void open(){
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/admin/viewSystemUsers");
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputUsername));
    }

//    steps: enter username -> select role -> click Search
//    => function chung: filter user(enter username, select role, click Search)
    public void setInputUsername(String username) throws InterruptedException {
        WebElement userInput =  driver.findElement(inputUsername);
        userInput.sendKeys(username);
        Thread.sleep(1000);
    }

    public void selectUserRole(String userRole) throws InterruptedException{
        WebElement userRoleSelect = driver.findElement(selectUserRole);
        userRoleSelect.click();

//        chọn dropdown tương ứng
        String xpath = String.format("//div[@role='option']//span[text()='" + userRole + "']");
//        String xpath = String.format("//div[@role='option']//span[text()='%s']", role);
        WebElement roleOption = driver.findElement(By.xpath(xpath));
        roleOption.click();
        Thread.sleep(1000);
    }

    public void clickSearchBtn() throws InterruptedException {
        WebElement search = driver.findElement(searchBtn);
        search.click();
        Thread.sleep(1000);
    }

    public void filterUser(String userName, String userRole) throws InterruptedException{
       setInputUsername(userName);
       selectUserRole(userRole);
       clickSearchBtn();
    }

    public boolean checkNumberofRecords(){
        WebElement recordCount = wait.until(ExpectedConditions.visibilityOfElementLocated(Record_Count));
        String text = recordCount.getText();
//        apply regular expression cheat sheet
//        (1) Record found -> chỉ lấy số 1 -> thay hết chữ bằng ""
        int recordCnt = Integer.parseInt(text.replaceAll("\\D+",""));

        int countDataRows = driver.findElements(dataRows).size();
//        System.out.println("record count được parse " + recordCnt);
//        System.out.println("record count được đếm bằng element " + countDataRows);
        return recordCnt == countDataRows;
    }
}
