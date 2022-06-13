import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.io.FileNotFoundException;
import java.time.Duration;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {

        System.setProperty("webdriver.chrome.driver", "C:\\SeleniumDocuments\\BrowserDriver\\chromedriver.exe");

        WebDriver dr = new ChromeDriver();

        dr.get("http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx");
        dr.manage().window().maximize();
        dr.manage().timeouts().implicitlyWait(Duration.ofSeconds(1000));


        dr.findElement(By.id("ctl00_MainContent_username")).sendKeys("Tester");
        dr.findElement(By.id("ctl00_MainContent_password")).sendKeys("test");
        Thread.sleep(2000);
        dr.findElement(By.id("ctl00_MainContent_login_button")).click();

        String expectedUrl = "http://secure.smartbearsoftware.com/samples/testcomplete12/weborders/";
        String actualUrl = dr.getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl);

        dr.findElement(By.linkText("Order")).click();
//
//        Select sel = new Select(dr.findElement(By.name("ctl00$MainContent$fmwOrder$ddlProduct")));
//        sel.selectByValue("FamilyAlbum");

        String quantity = "" + 1+(int)(Math.random()*100);
        dr.findElement(By.id("ctl00_MainContent_fmwOrder_txtQuantity")).sendKeys(quantity);

        dr.findElement(By.xpath("//input[@value = 'Calculate']")).click();



        String actualQuantity = dr.findElement(By.id("ctl00_MainContent_fmwOrder_txtQuantity")).getAttribute("value");
        Assert.assertEquals(actualQuantity, quantity);

        int intQuantity = Integer.parseInt(actualQuantity);
        int expectedTotal;
        if(intQuantity>9){
            expectedTotal = (int) (intQuantity*100 - (intQuantity*100 * 0.08));
        }else{
            expectedTotal = intQuantity * 100;
        }

        String actualTotal = dr.findElement(By.xpath("//input[@id= 'ctl00_MainContent_fmwOrder_txtTotal']")).getAttribute("value");
        Assert.assertEquals(actualTotal, String.valueOf(expectedTotal));


        List<String[]> listFake = Utility.readFromCSV("C:\\Users\\tarka\\AutomationAssignment_2\\src\\FakeData.csv");
        int row = 1 + (int)(Math.random()*(listFake.size()-1));

        Thread.sleep(1500);

        String name = listFake.get(row)[0];
        String street = listFake.get(row)[1];
        String city = listFake.get(row)[2];
        String state = listFake.get(row)[3];
        String zip = listFake.get(row)[4];

        dr.findElement(By.id("ctl00_MainContent_fmwOrder_txtName")).sendKeys(name);
        Thread.sleep(1000);
        dr.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox2")).sendKeys(street);
        Thread.sleep(1000);
        dr.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox3")).sendKeys(city);
        Thread.sleep(1000);
        dr.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox4")).sendKeys(state);
        Thread.sleep(1000);
        dr.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox5")).sendKeys(zip);
        Thread.sleep(1000);

        Random random = new Random();
        int cardSelect = random.nextInt(3);

        dr.findElement(By.id("ctl00_MainContent_fmwOrder_cardList_"+cardSelect)).click();
        String generatedCardNum = String.valueOf(Utility.generateCard(cardSelect));

        dr.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox6")).sendKeys(generatedCardNum);
        dr.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox1")).sendKeys("12/26");
        dr.findElement(By.xpath("//a[@ id = 'ctl00_MainContent_fmwOrder_InsertButton']")).click();

        String pageSource = dr.getPageSource();
        String expected = "New order has been successfully added";
        Assert.assertTrue(pageSource.contains(expected));


        Thread.sleep(2000);
        dr.findElement(By.xpath("//a[@ id = 'ctl00_logout']")).click();


    }
}
