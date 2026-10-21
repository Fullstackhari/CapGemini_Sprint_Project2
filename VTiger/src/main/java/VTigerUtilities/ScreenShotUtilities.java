package VTigerUtilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenShotUtilities {

    public static void captureScreenshot(WebDriver driver, String fileName) throws IOException {

        Path folderPath = Paths.get("screenshots");
        Files.createDirectories(folderPath);

        File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        Path destination = folderPath.resolve(fileName + ".png");

        Files.copy(source.toPath(), destination);
    }
}