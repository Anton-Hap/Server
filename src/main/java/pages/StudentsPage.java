package pages;

import models.Group;
import models.Speciality;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.BrowserUtils;

import java.util.ArrayList;
import java.util.List;

public class StudentsPage {

    private WebElement globalData = BrowserUtils.getDriver().findElement(By.xpath("//div[@class='form-group']"));

    private final String facultyFormat = "//select[@id='faculty']/option[@value='%s']";
    private final String specialityFormat = "//select[@id='speciality']/option[@value='%s']";
    private final String groupFormat = "//select[@id='groups']/option[@value='%s']";
    private final String weekFormat = "//select[@id='weekbegindate']/option[@value='%s']";

    private final String fieldLessonFormat = "//tbody/tr[%s]/td[2]";
    private final String fieldTimeFormat = "//tbody/tr[%s]/td[1]";
    private final String dayFormat = "//tbody/tr[%s]/td[1]";

    private WebElement showButton = BrowserUtils.getDriver().findElement(By.xpath("//input[@name='go']"));

    public String getTimeLesson(String numberField) {
        return getTime(numberField).getText();
    }

    public String getNameDay(String numberField) {
        return getDay(numberField).getText();
    }

    public ArrayList<String> getAllFaculty() {
        ArrayList<String> faculty = new ArrayList<>();
        List<WebElement> elements = getDataElements("faculty");

        for (int i = 1; i < elements.size(); i++) {
            faculty.add(elements.get(i).getAttribute("value"));
        }

        return faculty;
    }

    public Speciality getAllSpeciality() {
        Speciality list = new Speciality();

        ArrayList<String> speciality = new ArrayList<>();
        ArrayList<String> faculty = new ArrayList<>();

        List<WebElement> elements = getDataElements("speciality");

        for (int i = 1; i < elements.size(); i++) {
            speciality.add(elements.get(i).getAttribute("value"));
            faculty.add(elements.get(i).getAttribute("class"));
        }

        list.setSpeciality(speciality);
        list.setFaculty(faculty);

        return list;
    }

    public Group getAllGroup() {
        Group list = new Group();

        ArrayList<String> groups = new ArrayList<>();
        ArrayList<String> speciality = new ArrayList<>();

        List<WebElement> elements = getDataElements("groups");

        for (int i = 1; i < elements.size(); i++) {
            groups.add(elements.get(i).getAttribute("value"));
            speciality.add(elements.get(i).getAttribute("class"));
        }

        list.setGroup(groups);
        list.setSpeciality(speciality);

        return list;
    }

    private List<WebElement> getDataElements(String typeData) {
        return globalData.findElements(By.xpath("//select[@id='" + typeData +"']/option"));
    }

    public String getValue(String numberField) {
        return getField(numberField).getText();
    }

    public void showSchedule() {
        BrowserUtils.executeScript("arguments[0].click();", showButton);
    }

    public void selectFaculty(String faculty) {
        BrowserUtils.getDriver().findElement(By.xpath(String.format(facultyFormat, faculty))).click();
    }

    public void selectSpeciality(String speciality) {
        BrowserUtils.getDriver().findElement(By.xpath(String.format(specialityFormat, speciality))).click();
    }

    public void selectGroup(String group) {
        BrowserUtils.getDriver().findElement(By.xpath(String.format(groupFormat, group))).click();
    }

    public void selectWeek(String week) {
        BrowserUtils.getDriver().findElement(By.xpath(String.format(weekFormat, week))).click();
    }

    private WebElement getField(String numberField) {
        return BrowserUtils.getDriver().findElement(By.xpath(String.format(fieldLessonFormat, numberField)));
    }

    private WebElement getDay(String numberField) {
        return BrowserUtils.getDriver().findElement(By.xpath(String.format(dayFormat, numberField)));
    }

    private WebElement getTime(String numberField) {
        return BrowserUtils.getDriver().findElement(By.xpath(String.format(fieldTimeFormat, numberField)));
    }
}
