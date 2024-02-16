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

    private boolean home = true, cat = false, details = false, search = false, bag = false, bagEdit = false;

    public void setup()
    {
        //driver setup
        System.setProperty("webdriver.chrome.driver", "C:/Users/emagr/webtesting/chromedriver.exe/");
        driver = new ChromeDriver();

        //accessing website
        driver.get("https://www.shopcider.com");
    }

    public void teardown()
    {
        driver.quit();
        driver = new ChromeDriver();
        driver.get("https://www.shopcider.com");
    }

    public boolean goToCategory() {
        //Available category:
        //New In, Bestsellers, Sale, Clothing,
        //Swimwear, Curve & Plus, Acc & Shoes
        String category = "Sale";
        WebElement navigationName = driver.findElement(By.xpath ("//span[text()='" + category + "']"));
        navigationName.click();

        home = false;
        cat = true;
        details = false;
        search = false;
        bag = false;
        bagEdit = false;

        return cat;
    }

    public boolean goToProductDetails()
    {
        //Make sure products are first loaded
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("product-list")));

        //Locating first result
        WebElement firstProduct = driver.findElement(By.className("product-item"));

        //Accessing first result's details page
        WebElement firstProductLink = firstProduct.findElement(By.className("cider-link"));
        firstProductLink.click();

        home = false;
        cat = false;
        details = true;
        search = false;
        bag = false;
        bagEdit = false;

        return details;
    }

    public boolean goToSearchResults()
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

        home = false;
        cat = false;
        details = false;
        search = true;
        bag = false;
        bagEdit = false;

        return search;
    }

    public boolean goToBag()
    {
        WebElement bagIcon = driver.findElement(By.className("cider-header-bag"));
        bagIcon.click();

        home = false;
        cat = true;
        details = false;
        search = false;
        bag = true;
        bagEdit = false;

        return bag;
    }

    public boolean goToEditBag()
    {
        WebElement bagIcon = driver.findElement(By.className("cider-header-bag"));
        bagIcon.click();
        WebElement bagEditIcon = driver.findElement(By.className("edit-icon"));
        bagEditIcon.click();

        home = false;
        cat = false;
        details = false;
        search = false;
        bag = false;
        bagEdit = true;

        return bagEdit;
    }
}
