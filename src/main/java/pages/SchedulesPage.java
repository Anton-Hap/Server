package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.BrowserUtils;

public class SchedulesPage {

    private WebElement studentsButton = BrowserUtils.getDriver().findElement(By.xpath("//a[contains(@href,'stud.php')]"));

    public void goToStudentsPage() {
        BrowserUtils.executeScript("arguments[0].click();", studentsButton);
    }
}
