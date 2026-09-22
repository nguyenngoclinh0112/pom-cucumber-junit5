package stepdefinitions;

import hooks.Hooks;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import pages.MyInfoPage;

public class MyInfoStep {
    private Hooks hooks;
//    TO DO: MyInfoPage
    private MyInfoPage myInfoPage;
    public MyInfoStep(Hooks hooks){
        this.hooks =hooks;
        myInfoPage = new MyInfoPage(hooks.getDriver(), hooks.getWait());
    }

    @Then("người dùng di chuyển đến trang My Info")
    public void di_chuyen_den_trang_MyInfo() throws InterruptedException{
        myInfoPage.open();
        Thread.sleep(1000);
    }

    @When("người dùng upload avatar {string}")
    public void upload_avatar(String avatarName)throws InterruptedException{
        myInfoPage.uploadAvatar(avatarName);
    }

    @Then("avatar được upload thành công")
    public boolean upload_avatar_thanh_cong(){
        Assertions.assertTrue(myInfoPage.isAvatarUploadSuccessfully(),"Upload that bai");
        return false;
    }
}
