import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CiderOperator
{
    WebDriver driver;

    public void setup()
    {
        //driver setup
        System.setProperty("webdriver.chrome.driver", "C:/Users/emagr/webtesting/chromedriver.exe/");
        driver = new ChromeDriver();

        //accessing website
        driver.get("https://www.shopcider.com");
    }

    public void reset()
    {
        driver.quit();
        driver = new ChromeDriver();
        driver.get("https://www.shopcider.com");
    }

    public void goToCategory() {
        //Available category:
        //New In, Bestsellers, Sale, Clothing,
        //Swimwear, Curve & Plus, Acc & Shoes
        String category = "Sale";
        WebElement navigationName = driver.findElement(By.xpath ("//span[text()='" + category + "']"));
        navigationName.click();
    }

    public void goToProductDetailsPage()
    {
        //Make sure products are first loaded
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("product-list")));

        //Locating first result
        WebElement firstProduct = driver.findElement(By.className("product-item"));

        //Accessing first result's details page
        WebElement firstProductLink = firstProduct.findElement(By.className("cider-link"));
        firstProductLink.click();
    }

    public void goToSearchResultsPage()
    {
        //using the search bar
        String inputText = "shirt";

        //Accessing search bar
        WebElement searchBar = driver.findElement(By.xpath("//div[@class='header-search']//input"));
        searchBar.click();

        //wait for searchbar to load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("search-model-input_inner")));

        //Entering search request
        WebElement searchInput = driver.findElement(By.className("search-model-input_inner"));
        searchInput.sendKeys(inputText);
        searchInput.sendKeys(Keys.ENTER);
    }

    public void addToBag()
    {

    }

    public void removeFromBag()
    {

    }
}
