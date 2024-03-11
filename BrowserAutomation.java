package Resolution;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.FileWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class BrowserAutomation{

    public static void main(String[] args) {
        // Provide the path to the WebDriver executables
        System.setProperty("webdriver.chrome.driver", "./driver_2/chromedriver.exe");
        System.setProperty("webdriver.gecko.driver", "./driver_2/geckodriver.exe");
        System.setProperty("webdriver.opera.driver", "./driver_2/operadriver.exe");
        
        // Safari does not require setting a system property

        // Test URLs
        String[] urls = {"https://www.getcalley.com/", "https://www.getcalley.com/calley-call-from-browser/", "https://www.getcalley.com/calley-pro-features/", "https://www.getcalley.com/best-auto-dialer-app/", "https://www.getcalley.com/how-calley-auto-dialer-app-works/"};

        // Desktop resolutions
        Dimension[] desktopResolutions = {new Dimension(1920, 1080), new Dimension(1366, 768), new Dimension(1536, 864)};

        // Mobile resolutions
        Dimension[] mobileResolutions = {new Dimension(360, 640), new Dimension(414, 896), new Dimension(375, 667)};
        
        String screenshotDir = "./src/Resolution/screenshot";
        
        File directory = new File(screenshotDir);
        if (!directory.exists()) {
            directory.mkdir();
        }
        
        // Loop through different browsers
        String[] browsers = {"chrome", "firefox", "opera"};
        for (String browser : browsers) {
            WebDriver driver = null;
            try {
                // Initialize WebDriver based on the browser
                if (browser.equals("chrome")) {
                    driver = new ChromeDriver();
                } else if (browser.equals("firefox")) {
                    driver = new FirefoxDriver();
                } else if (browser.equals("opera")) {
                    driver = new SafariDriver();
                }

                // Test each URL
                for (String url : urls) {
                   
					// Test on desktop resolutions
                    for (Dimension resolution : desktopResolutions) {
                        driver.manage().window().setSize(resolution);
                        driver.get(url);
                        captureScreenshot(driver, browser, url, resolution , screenshotDir);
                        // Perform validation of test case
                        // Example: Assert page title
                        String expectedTitle = "Expected Title";
                        String actualTitle = driver.getTitle();
                        assert actualTitle.equals(expectedTitle) : "Test case failed! Expected title: " + expectedTitle + ", Actual title: " + actualTitle;
                    }

                    // Test on mobile resolutions
                    for (Dimension resolution : mobileResolutions) {
                        driver.manage().window().setSize(resolution);
                        driver.get(url);
                        captureScreenshot(driver, browser, url, resolution , screenshotDir);
                        // Perform validation of test case
                        // Example: Assert page title
                        String expectedTitle = "Expected Title";
                        String actualTitle = driver.getTitle();
                        assert actualTitle.equals(expectedTitle) : "Test case failed! Expected title: " + expectedTitle + ", Actual title: " + actualTitle;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (driver != null) {
                    driver.quit();
                }
            }
        }
    }

    private static void captureScreenshot(WebDriver driver, String browser, String url, Dimension resolution,String screenshotDir) throws Exception {
        // Take a screenshot
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String folderName = browser + File.separator + resolution.getWidth() + "x" + resolution.getHeight();
        File folder = new File(screenshotDir + File.separator + folderName);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");
        String timestamp = sdf.format(new Date(0, 0, 0));
        String fileName = screenshotDir + File.separator + folderName + File.separator + "Screenshot-" + timestamp + ".png";
        FileUtils.copyFile(screenshot, new File(fileName));
    }

    private static void recordVideo() {
        // Use Monte Media Library (FML) or other libraries to record video
        // Implementation depends on the library being used
    }

    private static void validateTestCase() {
        // Your validation logic goes here
    }

    private static void writeValidationLog() {
        // Write validation log to a file
        // For demonstration, let's just print a message
        try {
            FileWriter writer = new FileWriter("validation.log", true);
            writer.write("Test case validation successful!\n");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
