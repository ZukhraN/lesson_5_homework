package ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.io.File;
import java.util.List;

public class homeWorkTests {

    private static final String BASE_URL = "https://bonigarcia.dev/selenium-webdriver-java/web-form.html";
    private WebDriver driver;

    @BeforeEach
    void setUp(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(BASE_URL);
    }

    @AfterEach
    void tearDown(){
        driver.close();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/testFile.csv", numLinesToSkip = 1)
    void checkAllElementsOnPageTest(String nameOfLabel){
        List<WebElement> inputFields = driver. findElements(By.xpath("//label[normalize-space(text())='"+ nameOfLabel +"']/input"));
        for (WebElement inputField: inputFields){
            inputField.click();
        }
    }

    @Test
    void fillAllElementsTest() throws InterruptedException{
        //поле Text input
        WebElement inputTextField = driver.findElement(By.xpath("//label[normalize-space(text())='Text input']/input"));
        inputTextField.click();
        inputTextField.sendKeys("testText");
        WebElement titleChapter = driver.findElement(By.cssSelector(".display-6"));
        titleChapter.click();

        Assertions.assertEquals("testText", inputTextField.getAttribute("value"));

        //поле Password
        WebElement inputPassword = driver.findElement(By.xpath("//input[@type='password']"));
        inputPassword.click();
        inputPassword.sendKeys("test");
        titleChapter.click();

        Assertions.assertEquals("test", inputPassword.getAttribute("value"));

        //поле Textarea
        WebElement textareaText = driver.findElement(By.xpath("//textarea[@name='my-textarea']"));
        textareaText.click();
        textareaText.sendKeys("test");
        titleChapter.click();

        Assertions.assertEquals("test", textareaText.getAttribute("value"));

        //поле Disable input
        WebElement disableInput = driver.findElement(By.xpath("//input[@name='my-disabled' and @disabled]"));

        Assertions.assertEquals("Disabled input", disableInput.getAttribute("placeholder"));

        //поле Readonly input
        WebElement readonlyInput = driver.findElement(By.xpath("//input[@name='my-readonly' and @readonly]"));

        Assertions.assertEquals("Readonly input", readonlyInput.getAttribute("value"));

        //поле Dropdown (select)
        WebElement dropDownSelect = driver.findElement(By.cssSelector(".form-select"));
        dropDownSelect.click();
        WebElement optionDropDown = driver.findElement(By.xpath("//option[@value='1']"));
        optionDropDown.click();

        Assertions.assertEquals("One", optionDropDown.getText());

        //поле Dropdown (datalist)
        WebElement dropDownDataList = driver.findElement(By.xpath("//input[@name='my-datalist']"));
        dropDownDataList.sendKeys("New York");

        Assertions.assertEquals("New York", dropDownDataList.getAttribute("value"));

        //поле File input
        WebElement fileInput = driver.findElement(By.xpath("//input[@name='my-file']"));
        fileInput.sendKeys(new File("src/test/resources/pic.png").getAbsolutePath());
        String value = fileInput.getAttribute("value");
        String fileName = value.substring(value.lastIndexOf("\\") + 1);

        Assertions.assertEquals("pic.png", fileName);


        //поля Check boxes
        WebElement checkBox1 = driver.findElement(By.xpath("//div[@class='form-check']//input[@name='my-check' and not(@checked)]"));
        checkBox1.click();
        //Assertions.assertEquals("New York", optionDataList.getAttribute("value"));

        //поля Radio button
        WebElement radioButton = driver.findElement(By.xpath("//input[@name='my-radio' and not(@checked)]"));
        radioButton.click();
        //Assertions.assertEquals("New York", optionDataList.getAttribute("value"));

        // Color picker
        WebElement colorPicker = driver.findElement(By.xpath("//div//input[@name='my-colors' ]"));
        colorPicker.click();
        colorPicker.sendKeys("#198754");

        Assertions.assertEquals("#198754", colorPicker.getAttribute("value"));

        // Date picker
        WebElement datePicker = driver.findElement(By.xpath("//div//input[@name='my-date']"));
        datePicker.click();
        datePicker.sendKeys("08/27/2025");

        Assertions.assertEquals("08/27/2025", datePicker.getAttribute("value"));

        // Example range
        WebElement range = driver.findElement(By.name("my-range"));
        int target = 7;

        int current = Integer.parseInt(range.getAttribute("value"));
        while (current < target) {
            range.sendKeys(Keys.ARROW_RIGHT);
            current = Integer.parseInt(range.getAttribute("value"));
        }
        while (current > target) {
            range.sendKeys(Keys.ARROW_LEFT);
            current = Integer.parseInt(range.getAttribute("value"));
        }

        Assertions.assertEquals(target,
                Integer.parseInt(range.getAttribute("value")),
                "Слайдер не встал на нужное значение");


        //Send form
        WebElement submitButton = driver.findElement(By.xpath("//div//button[@type='submit']"));
        submitButton.click();
        WebElement message = driver.findElement(By.className("display-6"));

        Assertions.assertEquals("Form submitted", message.getText(), "Сообщение после сабмита не совпало");

    }
}
