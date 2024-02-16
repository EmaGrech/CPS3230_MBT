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

        //Get expected new page title
        String expectedPageName = expectedCategoryPageName(category);

        //Get actual new page title
        String actualPageName = actualPageName();

        if (expectedPageName.equals(actualPageName))
        {
            home = false;
            cat = true;
            details = false;
            search = false;
            bag = false;
            bagEdit = false;
        }
        else
        {
            throw new IllegalStateException();
        }



        return cat;
    }

    public boolean goToProductDetails()
    {
        //Make sure products are first loaded
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("product-list")));

        //Locating first result
        WebElement firstProduct = driver.findElement(By.className("product-item"));
        WebElement expectedFirstProduct = firstProduct.findElement(By.className("product-item-name"));
        String expectedFirstProductName = expectedFirstProduct.getText();

        //Accessing first result's details page
        WebElement firstProductLink = firstProduct.findElement(By.className("cider-link"));
        firstProductLink.click();

        WebElement actualFirstProduct = driver.findElement(By.className("product-detail-title"));
        String actualFirstProductName = actualFirstProduct.getText();

        if (expectedFirstProductName.equals(actualFirstProductName))
        {
            home = false;
            cat = false;
            details = true;
            search = false;
            bag = false;
            bagEdit = false;
        }
        else
        {
            throw new IllegalStateException();
        }


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

        String actualPageName = actualPageName();

        if (actualPageName.equals("Results for\n“" + inputText + "”"))
        {
            home = false;
            cat = false;
            details = false;
            search = true;
            bag = false;
            bagEdit = false;
        }
        else
        {
            throw new IllegalStateException();
        }

        return search;
    }

    public boolean goToBag()
    {
        WebElement bagIcon = driver.findElement(By.className("cider-header-bag"));
        bagIcon.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".cart-drawer-header-container")));

        boolean isPresent = (element != null);

        if (isPresent == true)
        {
            home = false;
            cat = true;
            details = false;
            search = false;
            bag = true;
            bagEdit = false;
        }
        else
        {
            throw new IllegalStateException();
        }

        return bag;
    }

    public boolean goToEditBag()
    {
        WebElement bagIcon = driver.findElement(By.className("cider-header-bag"));
        bagIcon.click();
        WebElement bagEditIcon = driver.findElement(By.className("edit-icon"));
        bagEditIcon.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("check-icon")));

        boolean isPresent = (element != null);

        if (isPresent == true)
        {
            home = false;
            cat = false;
            details = false;
            search = false;
            bag = false;
            bagEdit = true;
        }
        else
        {
            throw new IllegalStateException();
        }

        return bagEdit;
    }

    public String expectedCategoryPageName(String category)
    {
        String pageName = null;

        switch(category)
        {
            case "New In": pageName = "All New In"; break;
            case "Bestsellers": pageName = "All Bestsellers"; break;
            case "Sale": pageName = "Up to 70% Off"; break;
            case "Clothing": pageName = "Everything"; break;
            case "Swimwear": pageName = "Swimwear\uD83D\uDC59"; break;
            case "Curve & Plus": pageName = "Curve & Plus"; break;
            case "Acc & Shoes": pageName = "ACC & Shoes"; break;
        }

        return pageName;
    }

    //Actual Page Name for the shown Category Page
    public String actualPageName()
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement pageNameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("sort-title-body")));
        String actualPageName = pageNameElement.getText();

        return actualPageName;
    }
}
