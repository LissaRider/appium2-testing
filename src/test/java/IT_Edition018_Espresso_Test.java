import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.appmanagement.AndroidTerminateApplicationOptions;
import io.appium.java_client.appmanagement.ApplicationState;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class IT_Edition018_Espresso_Test {
    public static final String APP_PACKAGE = "io.cloudgrey.the_app";
    public static final String APP_ACTIVITY = "io.cloudgrey.the_app.MainActivity";
    private final String APP = "src/test/resources/apps/TheApp-v1.10.0.apk";
    private final By loginScreen = AppiumBy.accessibilityId("Login Screen");
    private final By username = AppiumBy.accessibilityId("username");
    private final By password = AppiumBy.accessibilityId("password");
    private final By loginBtn = AppiumBy.accessibilityId("loginBtn");
    private final By verificationTextEspresso = By.xpath(
            "//*[contains(@class,'Text') and contains(@text, 'You are logged in as alice')]");
    private final By verificationTextUiAuto2 = By.xpath(
            "//*[contains(@class,'Text') and contains(@text, 'alice')]");
    private final By logoutBtn = By.xpath("//*[@text='Logout']/ancestor::*[contains(@class,'Group')][last()]");

    private AndroidDriver getDriver(String automationName) throws IOException {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, automationName);
//        capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, APP_PACKAGE);
//        capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, APP_ACTIVITY);
        capabilities.setCapability(MobileCapabilityType.FULL_RESET, true);
        capabilities.setCapability(MobileCapabilityType.APP,  new File(APP).getCanonicalPath());

        return new AndroidDriver(new URL("http://localhost:4723/"), capabilities);
    }

    @Test
    public void loginEspresso() throws IOException, InterruptedException {
        AndroidDriver driver = getDriver("Espresso");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        ExpectedCondition<WebElement> loginScreenReady =
                ExpectedConditions.visibilityOfElementLocated(loginScreen);
        ExpectedCondition<WebElement> usernameReady =
                ExpectedConditions.visibilityOfElementLocated(username);

        try {
            Thread.sleep(3000);
            driver.findElement(loginScreen).click();
            driver.findElement(username).sendKeys("alice");
            driver.findElement(password).sendKeys("mypassword");
            driver.findElement(loginBtn).click();
            driver.findElement(verificationTextEspresso);
            driver.findElement(logoutBtn).click();
            wait.until(usernameReady);
            driver.terminateApp(APP_PACKAGE, new AndroidTerminateApplicationOptions().withTimeout(Duration.ofSeconds(30)));
//                        driver.closeApp();
//            System.out.println(driver.queryAppState(ApplicationState.NOT_RUNNING.name()));
//            Thread.sleep(5000);
            driver.activateApp(APP_PACKAGE);
//                        driver.startActivity(new Activity(APP_PACKAGE, APP_ACTIVITY));
//
//            driver.launchApp();
//            Thread.sleep(5000);
//            System.out.println(driver.getPageSource());
            Thread.sleep(3000);
            driver.findElement(loginScreen).click();
            driver.findElement(username).sendKeys("alice");
            driver.findElement(password).sendKeys("mypassword");
            driver.findElement(loginBtn).click();
            driver.findElement(verificationTextEspresso);
            driver.findElement(logoutBtn).click();
        } finally {
            driver.quit();
        }
    }

    @Test
    public void loginUiAutomator2() throws IOException {
        AndroidDriver driver = getDriver("UiAutomator2");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        ExpectedCondition<WebElement> loginScreenReady =
                ExpectedConditions.visibilityOfElementLocated(loginScreen);
        ExpectedCondition<WebElement> usernameReady =
                ExpectedConditions.visibilityOfElementLocated(username);
        ExpectedCondition<WebElement> verificationReady =
                ExpectedConditions.visibilityOfElementLocated(verificationTextUiAuto2);

        try {
            wait.until(loginScreenReady).click();
            wait.until(usernameReady).sendKeys("alice");
            driver.findElement(password).sendKeys("mypassword");
            driver.findElement(loginBtn).click();
            wait.until(verificationReady);
            driver.findElement(logoutBtn).click();
            wait.until(usernameReady);
            driver.terminateApp(APP_PACKAGE, new AndroidTerminateApplicationOptions().withTimeout(Duration.ofSeconds(30)));
//            driver.closeApp();
//            System.out.println(driver.queryAppState(ApplicationState.NOT_RUNNING.name()));
//            Thread.sleep(5000);
            driver.activateApp(APP_PACKAGE);
//            driver.startActivity(new Activity(APP_PACKAGE, APP_ACTIVITY));
//            Thread.sleep(5000);
//            driver.launchApp();
//            System.out.println(driver.getPageSource());
            wait.until(loginScreenReady).click();
            wait.until(usernameReady).sendKeys("alice");
            driver.findElement(password).sendKeys("mypassword");
            driver.findElement(loginBtn).click();
            wait.until(verificationReady);
            driver.findElement(logoutBtn).click();
            wait.until(usernameReady);
        } finally {
            driver.quit();
        }

    }
}
