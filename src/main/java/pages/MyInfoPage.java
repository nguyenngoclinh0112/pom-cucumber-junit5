package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;

public class MyInfoPage extends BasePage {

    private static final By AVATAR_LOC= By.xpath("//img[@class='employee-image']");
    private static final By ADD_AVAR_BTN = By.xpath("//button[@class='oxd-icon-button oxd-icon-button--solid-main employee-image-action']");
    private static final By FILE_INPUT = By.xpath("//input[@type='file']");
    private static final By SAVE_BTN = By.xpath("//button[@type='submit']");
    private static final By SUCCESS_TOAST = By.xpath("//div[contains(@class,'oxd-toast')]");

    public MyInfoPage(WebDriver driver, WebDriverWait wait){
        super(driver, wait);
    }

    public void open(){
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/pim/viewPersonalDetails/empNumber/7");
        wait.until(ExpectedConditions.elementToBeClickable(AVATAR_LOC));
    }

    public void uploadAvatar(String fileName) throws InterruptedException{
//        B1: Click vào avatar
        WebElement avatarLoc = wait.until(ExpectedConditions.elementToBeClickable(AVATAR_LOC));
        avatarLoc.click();
        Thread.sleep(1000);

//        B2: click nút + để upload avatar
        WebElement addAvaBtn = wait.until(ExpectedConditions.elementToBeClickable(ADD_AVAR_BTN));
        addAvaBtn.click();
        Thread.sleep(1000);

//        B3: đợi hiển thị file input -> truyền path image và file input (absolute path)
        WebElement fileInput = wait.until(ExpectedConditions.presenceOfElementLocated(FILE_INPUT));
//        <path system>/<path source code>
//        getAbsolutePath()/src/...
        String absolutePath = new File("src/test/resources/image/"+ fileName).getAbsolutePath();
        fileInput.sendKeys(absolutePath);
        Thread.sleep(1000);

//        B4: click nút Save
        WebElement saveBtn = wait.until(ExpectedConditions.elementToBeClickable(SAVE_BTN));
        saveBtn.click();
        Thread.sleep(1000);
    }

    public boolean isAvatarUploadSuccessfully(){
        WebElement successToast = wait.until(ExpectedConditions.visibilityOfElementLocated(SUCCESS_TOAST));
        return successToast.isDisplayed();
    }
}
