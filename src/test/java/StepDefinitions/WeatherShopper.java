package StepDefinitions;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class WeatherShopper {

	String projectPath = System.getProperty("user.dir");
	//System.out.println("Current path: "+ projectPath);
	WebDriver driver=null;
	String chromedriverpath =  projectPath+"/src/test/resources/drivers/chromedriver.exe";   // return driver folder path 
	String firefoxdriverpath = projectPath+"/src/test/resources/drivers/geckodriver.exe";   // return driver folder path
	String temp=null;
	List<WebElement> moisturizersName=null;
	List<WebElement> moisturizersPrice=null;
	List<WebElement> sunscreensName=null;
	List<WebElement> sunscreensPrice=null;
	ChromeOptions options = new ChromeOptions();
	Double tempreture;
	String scenarioName = null;
	//var scenarioTags   = Scenario.name;
	String browser="chrome";

	public void setup(String browser) throws Exception{


		if(browser.equalsIgnoreCase("firefox")){
			System.setProperty("webdriver.gecko.driver", firefoxdriverpath);
			driver = new FirefoxDriver();
		}

		else if(browser.equalsIgnoreCase("chrome")){

			System.setProperty("webdriver.chrome.driver",chromedriverpath);
			options.addArguments("enable-automation");
			options.addArguments("--headless");
			options.addArguments("--window-size=1920,1080");
			options.addArguments("--no-sandbox");
			options.addArguments("--disable-extensions");
			options.addArguments("--dns-prefetch-disable");
			options.addArguments("--disable-gpu");
			options.addArguments("--dns-prefetch-disable");
			options.setPageLoadStrategy(PageLoadStrategy.EAGER);
			options.addArguments("disable-features=NetworkService");
			options.addArguments("enable-features=NetworkServiceInProcess");

			driver = new ChromeDriver(options);
		}		
		else{
			throw new Exception("Browser is not correct");
		}
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(20,TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@AfterClass
	void closeBrowser() {       
		driver.quit();
	}
	@Given("user is on Home Page")
	public void user_is_on_home_page() throws Exception {
		// System.out.println("Inside Step:");
		//System.out.println("Inside Step:user is on Home Page");
		setup(browser);
		driver.navigate().to("https://weathershopper.pythonanywhere.com/");

	}
	@And("user reads the tempreture")
	public void user_reads_the_tempreture() {
		temp=driver.findElement(By.xpath("//*[@id=\"temperature\"]")).getText();
		temp=temp.replaceAll("[^\\d\\.]","");
		tempreture = Double.valueOf(temp);
		System.out.println("Current Temp: "+temp);		

		if(tempreture < 19) {
			scenarioName="Moisturizers";
			System.out.println(" We will select moisturizers");
		}else if (tempreture > 34){
			scenarioName="Sunscreens";
			System.out.println(" We will select sunscreens");
		}
	}
	@And("user reads if the Current temperature below {int}")
	public void user_reads_the_current_temperature_below(Integer int1) throws Exception {		
		temp=driver.findElement(By.xpath("//*[@id=\"temperature\"]")).getText();
		temp=temp.replaceAll("[^\\d\\.]","");
		double n = Double.valueOf(temp);
		System.out.println("Current Temp: "+temp);
		// int tempInt=Integer.parseInt(temp.trim());

		while(n >19) {
			System.out.println(" Temp >19 ");
			//closeBrowser();	
			driver.get("https://weathershopper.pythonanywhere.com/");
			//user_is_on_home_page();
			temp=driver.findElement(By.xpath("//*[@id=\"temperature\"]")).getText();
			temp=temp.replaceAll("[^\\d\\.]","");
			n = Double.valueOf(temp);
			System.out.println("New Current Temp: "+n);
		}
			System.out.println(" We will select moisturizers");

	}
	
	
	@Given("user start the test for {string}")
	public void user_start_the_test_for(String string) {
		driver.findElement(By.xpath("//*[text()=\'"+string+"\']"));
		if(tempreture<19) {
			user_should_see_moisturizers_section();
		}else if(tempreture > 34){
			user_should_see_sunscreens_section();
		}
	}

	@When("user clicks on info icon")
	public void user_clicks_on_info_icon() {
		System.out.println("Clicking on info icon");
		driver.findElement(By.xpath("//span[@class=\"octicon octicon-info\"]")).click();
	}

	@Then("user should see Your task info tip")
	public void user_should_see_your_task_info_tip() {
		System.out.println("Checking your task info");
		driver.findElement(By.xpath("//*[@class=\"popover-header\"]"));  
	}

	@And("user should see info tip text contains as {string}")
	public void user_should_see_info_tip_text_contains_as(String string) {
		System.out.println("Checking your task info text");
		driver.findElement(By.xpath("//*[starts-with(text(), string)]"));
	}

	@And("user should see Moisturizers section")
	public void user_should_see_moisturizers_section() {
		driver.findElement(By.xpath("//*[text()=\"Moisturizers\"]"));
	}

	@And("user should see Buy moisturizers button")
	public void user_should_see_buy_moisturizers_button() {
		driver.findElement(By.xpath("//button[text()=\"Buy moisturizers\"]"));
	}

	@When("user should click on Buy moisturizers button")
	public void user_should_click_on_buy_moisturizers_button() {
		driver.findElement(By.xpath("//button[text()=\"Buy moisturizers\"]")).click();
	}

	@Then("user should be on Moisturizers page")
	public void user_should_be_on_moisturizers_page() {
		String title="The Best Moisturizers in the World!";
		assertEquals(title, driver.getTitle());

	}

	@And("user should see info tip text contains {string}")
	public void user_should_see_info_tip_text_contains(String string) {
		System.out.println("Checking your task info text");
		driver.findElement(By.xpath("//*[starts-with(text(), string)]"));
	}
	ArrayList name = new ArrayList();
	ArrayList price = new ArrayList();
	@And("user should see moisturizers content")
	public void user_should_see_moisturizers_content() {
		moisturizersName=driver.findElements(By.xpath("//*[@class='font-weight-bold top-space-10']"));
		System.out.println(moisturizersName.size());	
		for (WebElement webElement : moisturizersName) {    	  
			name.add(webElement.getText());           
		} 
	}

	@And("user should see moisturizers price")
	public void user_should_see_moisturizers_price() {
		moisturizersPrice=driver.findElements(By.xpath("//*[contains(text(), 'Price: ')]"));
		System.out.println(moisturizersPrice.size());
		for (WebElement webElement : moisturizersPrice) {    	  
			price.add(webElement.getText());          
		}
	}

	Map<String, Double> aloeMoisturizer= new HashMap<String, Double>();
	Map<String, Double> almondMoisturizer= new HashMap<String, Double>();
	String leastExpensiveAloe=null;
	String leastExpensiveAlmond=null;
	@And("user should select least expensive moisturizer that contains Aloe")
	public void user_should_select_least_expensive_moisturizer_that_contains_aloe() throws InterruptedException {

		for(int i=0;i<moisturizersPrice.size();i++) {
			if(name.get(i).toString().contains("Aloe")) {
				String price1=price.get(i).toString();
				price1=price1.replaceAll("[^a-zA-Z0-9]", " "); 
				price1=price1.replaceAll("[^0-9.]", "");
				aloeMoisturizer.put(name.get(i).toString(),Double.valueOf(price1));
			} 
		}


		Map<String, Double> sortedMap = sortByValueAloe(aloeMoisturizer);
		for (Entry<String, Double> entry : sortedMap.entrySet()) {
			System.out.println("Key----- : " + entry.getKey()
			+ " Value :----- " + entry.getValue());
		}

		leastExpensiveAloe=sortedMap.keySet().stream().findFirst().get();
		System.out.println("Least Expensive aloe ----- : " + sortedMap.values().toArray()[0].toString()+ "---"+leastExpensiveAloe);
		Thread.sleep(3000);
		String tabxpath = "//*[contains(text(), '" + leastExpensiveAloe + "')]/following-sibling::button";		
		driver.findElement(By.xpath(tabxpath)).click(); 

		//driver.findElement(By.xpath("//*[contains(text(), leastExpensive)]//following-sibling::button")).click();)
	}  
	private static Map<String, Double> sortByValueAloe(Map<String, Double> aloeMoisturizer) {


		List<Map.Entry<String, Double>> list =
				new LinkedList<Map.Entry<String, Double>>(aloeMoisturizer.entrySet());

		Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
			public int compare(Map.Entry<String, Double> o1,
					Map.Entry<String, Double> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});
		Map<String, Double> sortedMap = new LinkedHashMap<String, Double>();
		for (Map.Entry<String, Double> entry : list) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}

		return sortedMap;
	}

	@And("user should select the least expensive moisturizer that contains Almond")
	public void user_should_select_the_least_expensive_moisturizer_that_contains_almond() throws InterruptedException {
		for(int i=0;i<moisturizersPrice.size();i++) {
			if(name.get(i).toString().contains("Almond")) {
				String price1=price.get(i).toString();
				price1=price1.replaceAll("[^a-zA-Z0-9]", " "); 
				price1=price1.replaceAll("[^0-9.]", "");
				almondMoisturizer.put(name.get(i).toString(),Double.valueOf(price1));
			} 
		}

		Map<String, Double> sortedMap = sortByValueAlmond(almondMoisturizer);
		for (Entry<String, Double> entry : sortedMap.entrySet()) {
			System.out.println("Key----- : " + entry.getKey()
			+ " Value :----- " + entry.getValue());
		}

		leastExpensiveAlmond=sortedMap.keySet().stream().findFirst().get();
		System.out.println("Least Expensive almond ----- : " + sortedMap.values().toArray()[0].toString()+ "---"+leastExpensiveAlmond);
		Thread.sleep(3000);
		String tabxpath = "//*[contains(text(), '" + leastExpensiveAlmond + "')]/following-sibling::button";

		driver.findElement(By.xpath(tabxpath)).click(); 

	}
	private static Map<String, Double> sortByValueAlmond(Map<String, Double> almondMoisturizer) {


		List<Map.Entry<String, Double>> list =
				new LinkedList<Map.Entry<String, Double>>(almondMoisturizer.entrySet());

		Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
			public int compare(Map.Entry<String, Double> o1,
					Map.Entry<String, Double> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});
		Map<String, Double> sortedMap = new LinkedHashMap<String, Double>();
		for (Map.Entry<String, Double> entry : list) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}

		return sortedMap;
	}
	@And("user clicks on cart")
	public void user_clicks_on_cart() {
		driver.findElement(By.xpath("//button[contains(text(), 'Cart')]")).click();
	}

	@And("user should be on Checkout page")
	public void user_should_be_on_checkout_page() {
		String title="Cart Items";
		assertEquals(title, driver.getTitle()); 
	}

	@And("user should click on info icon")
	public void user_should_click_on_info_icon() {
		System.out.println("Clicking on info icon");
		driver.findElement(By.xpath("//span[@class=\"octicon octicon-info\"]")).click();
	}

	@And("user should see Your task info")
	public void user_should_see_your_task_info() {
		System.out.println("Checking your task info");
		driver.findElement(By.xpath("//*[@class=\"popover-header\"]"));  
	}

	@And("user should see info text contains {string}")
	public void user_should_see_info_text_contains(String string) {
		System.out.println("Checking your task info text");
		driver.findElement(By.xpath("//*[starts-with(text(), string)]"));
	}
	@And("user should verify items")
	public void user_should_verify_items() {
		assertEquals(driver.findElement(By.xpath("//tbody/tr[1]/td[1]")).getText(),leastExpensiveAloe);
		assertEquals(driver.findElement(By.xpath("//tbody/tr[2]/td[1]")).getText(),leastExpensiveAlmond);
	}

	@And("user should verify Pay with Card button")
	public void user_should_verify_Pay_with_Card_button() {
		driver.findElement(By.xpath("//*[contains(text(), 'Pay with Card')]"));
	}

	@And("user should click on Pay with Card button")
	public void user_should_click_on_Pay_with_Card_button() {
		driver.findElement(By.xpath("//*[contains(text(), 'Pay with Card')]")).click();
	}

	@And("user should see Payment details form")
	public void user_should_see_Payment_details_form() {
		driver.manage().timeouts().pageLoadTimeout(5,TimeUnit.SECONDS);
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		driver.findElement(By.xpath("//*[@class='bodyView']"));
	}

	@And("user should enter email as {string}")
	public void user_should_enter_email_as(String string) {
		driver.findElement(By.xpath("//*[@type='email']")).sendKeys(string);
	}
	String windowHandle =null;
	ArrayList tabs=null;
	@And("user should click on Test Mode")
	public void user_should_click_on_test_mode() throws InterruptedException {

		windowHandle = driver.getWindowHandle();		
		driver.findElement(By.xpath("//*[contains(text(),'TEST MODE')]")).click();


		//Get the list of window handles
		tabs = new ArrayList (driver.getWindowHandles());
		System.out.println(" tabs "+tabs.size());
		//Use the list of window handles to switch between windows


	}
	String cardNo=null;
	@And("user should copy a card number")
	public void user_should_copy_a_card_number() throws InterruptedException {
		driver.switchTo().window(tabs.get(1).toString());
		driver.manage().timeouts().implicitlyWait(15000, TimeUnit.SECONDS);
		System.setProperty("webdriver.chrome.driver","driver_path");
		System.setProperty("webdriver.chrome.silentOutput", "true");
		List <WebElement> elements = driver.findElements(By.xpath("//*[@id=\"content\"]/div[2]/div[2]/div[2]/div/div/div/table/tbody/tr[1]/td[1]/button/span/span"));
		cardNo=elements.get(0).getText();
		for (int i = 1; i< elements.size(); i++) {
			cardNo+=elements.get(i).getText();
		}
		System.out.println("Card No :"+cardNo);
		Thread.sleep(3000);
	}

	@And("user should be back on Payment page")
	public void user_should_be_back_on_payment_page() {

		//Switch back to original window
		driver.switchTo().window(windowHandle);
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
	}

	@And("user should enter card number")
	public void user_should_enter_card_number(){
		System.out.println("Card No :"+cardNo.trim());
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement numElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"card_number\"]")));
		numElement.click();
		numElement.clear();
		JavascriptExecutor ex = (JavascriptExecutor) driver;
		ex.executeScript("arguments[0].value='"+ cardNo +"';", numElement);
	}


	@And("user should enter date as {string}")
	public void user_should_enter_date_as(String string) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement ccdate = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"cc-exp\"]")));
		ccdate.click();
		ccdate.clear();		
		JavascriptExecutor ex = (JavascriptExecutor) driver;
		ex.executeScript("arguments[0].value='"+ string +"';", ccdate);
	}

	@And("user should enter cvc as {string}")
	public void user_should_enter_cvc_as(String string) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement cvc = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"cc-csc\"]")));
		cvc.click();
		cvc.clear();
		JavascriptExecutor ex = (JavascriptExecutor) driver;
		ex.executeScript("arguments[0].value='"+ string +"';", cvc);
	}
	WebElement zip = null;
	@And("user should see zip code field")
	public void user_should_see_zip_code_field() {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		//WebElement zip = null;
		zip=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"billing-zip\"]")));
	}

	@And("user should enter zip code as {string}")
	public void user_should_enter_zip_code_as(String string) {
		JavascriptExecutor ex = (JavascriptExecutor) driver;
		ex.executeScript("arguments[0].value='"+ string +"';", zip);
	}

	@And("user clicks on Pay button")
	public void user_clicks_on_Pay_button() throws InterruptedException {
		driver.findElement(By.xpath("//*[@id=\"submitButton\"]")).click();
		Thread.sleep(10000);
		//driver.switchTo().frame(0);		
	}

	@And("user should be on Confirmation page")
	public void user_should_be_on_confirmation_page() {

		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/head/title")));

	}

	@And("user should see Payment Success")
	public void user_should_see_payment_success() {

		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div/div[1]/h2")));

	}
	@And("user should see Payment Success message as {string}")
	public void user_should_see_payment_success_message_as(String string) {
		driver.findElement(By.xpath("//*[starts-with(text(), string)]"));
		closeBrowser();
	}

	//-----Sunscreen functions
	@And("user reads if the Current temperature above {int}")
	public void user_reads_if_the_current_temperature_above(Integer int1) throws Exception {
		temp=driver.findElement(By.xpath("//*[@id=\"temperature\"]")).getText();

		temp=temp.replaceAll("[^\\d\\.]","");
		double n = Double.valueOf(temp);
		System.out.println("Current Temp: "+temp);
		// int tempInt=Integer.parseInt(temp.trim());
		while(n <34) {
			System.out.println(" Temp <34");
			driver.get("https://weathershopper.pythonanywhere.com/");
			//user_is_on_home_page();
			temp=driver.findElement(By.xpath("//*[@id=\"temperature\"]")).getText();
			temp=temp.replaceAll("[^\\d\\.]","");
			n = Double.valueOf(temp);
			System.out.println("New Current Temp: "+n);
		}
			System.out.println(" We will select Sunscreens");
	}


	@And("user should see Sunscreens section")
	public void user_should_see_sunscreens_section() {
		driver.findElement(By.xpath("//*[text()=\"Sunscreens\"]"));
	}

	@And("user should see Buy Sunscreens button")
	public void user_should_see_buy_sunscreens_button() {
		driver.findElement(By.xpath("//button[text()=\"Buy sunscreens\"]"));
	}

	@When("user should click on Buy sunscreens button")
	public void user_should_click_on_buy_sunscreens_button() {
		driver.findElement(By.xpath("//button[text()=\"Buy sunscreens\"]")).click();
	}

	@Then("user should be on Sunscreens page")
	public void user_should_be_on_sunscreens_page() {
		String title="The Best Sunscreens in the World!";
		assertEquals(title, driver.getTitle());   
	}

	ArrayList sunsname = new ArrayList();
	ArrayList sunsprice = new ArrayList();
	@And("user should see sunscreens content")
	public void user_should_see_sunscreens_content() {
		sunscreensName=driver.findElements(By.xpath("//*[@class='font-weight-bold top-space-10']"));
		System.out.println(sunscreensName.size());	
		for (WebElement webElement : sunscreensName) {    	  
			sunsname.add(webElement.getText());           
		} 
	}

	@And("user should see sunscreens price")
	public void user_should_see_sunscreens_price() {
		sunscreensPrice=driver.findElements(By.xpath("//*[contains(text(), 'Price: ')]"));
		System.out.println(sunscreensPrice.size());
		for (WebElement webElement : sunscreensPrice) {    	  
			sunsprice.add(webElement.getText());          
		}
	}
	Map<String, Double> spf50Sunscreen= new HashMap<String, Double>();
	Map<String, Double> spf30Sunscreen= new HashMap<String, Double>();
	String leastExpensiveSpf50=null;
	String leastExpensiveSpf30=null;
	@And("user should select least expensive sunscreen that contains SPF50")
	public void user_should_select_least_expensive_sunscreen_that_contains_spf50() throws InterruptedException {
		for(int i=0;i<sunscreensPrice.size();i++) {
			if(sunsname.get(i).toString().contains("SPF-50")) {
				String price1=sunsprice.get(i).toString();
				price1=price1.replaceAll("[^a-zA-Z0-9]", " "); 
				price1=price1.replaceAll("[^0-9.]", "");
				spf50Sunscreen.put(sunsname.get(i).toString(),Double.valueOf(price1));
			} 
		}


		Map<String, Double> sortedMap = sortByValueAloe(spf50Sunscreen);
		for (Entry<String, Double> entry : sortedMap.entrySet()) {
			System.out.println("Key----- : " + entry.getKey()
			+ " Value :----- " + entry.getValue());
		}

		leastExpensiveSpf50=sortedMap.keySet().stream().findFirst().get();
		System.out.println("Least Expensive SPF-50 ----- : " + sortedMap.values().toArray()[0].toString()+ "---"+leastExpensiveSpf50);
		Thread.sleep(3000);
		String tabxpath = "//*[contains(text(), '" + leastExpensiveSpf50 + "')]/following-sibling::button";		
		driver.findElement(By.xpath(tabxpath)).click();    
	}

	@And("user should select the least expensive sunscreen that contains SPF30")
	public void user_should_select_the_least_expensive_sunscreen_that_contains_spf30() throws InterruptedException {
		for(int i=0;i<sunscreensPrice.size();i++) {
			if(sunsname.get(i).toString().contains("SPF-30")) {
				String price1=sunsprice.get(i).toString();
				price1=price1.replaceAll("[^a-zA-Z0-9]", " "); 
				price1=price1.replaceAll("[^0-9.]", "");
				spf30Sunscreen.put(sunsname.get(i).toString(),Double.valueOf(price1));
			} 
		}

		Map<String, Double> sortedMap = sortByValueAlmond(spf30Sunscreen);
		for (Entry<String, Double> entry : sortedMap.entrySet()) {
			System.out.println("Key----- : " + entry.getKey()
			+ " Value :----- " + entry.getValue());
		}

		leastExpensiveSpf30=sortedMap.keySet().stream().findFirst().get();
		System.out.println("Least Expensive SPF-30 ----- : " + sortedMap.values().toArray()[0].toString()+ "---"+leastExpensiveSpf30);
		Thread.sleep(3000);
		String tabxpath = "//*[contains(text(), '" + leastExpensiveSpf30 + "')]/following-sibling::button";

		driver.findElement(By.xpath(tabxpath)).click(); 
	}
	@And("user should verify sunscreen items")
	public void user_should_verify_sunscreen_items() {
		assertEquals(driver.findElement(By.xpath("//tbody/tr[1]/td[1]")).getText(),leastExpensiveSpf50);
		assertEquals(driver.findElement(By.xpath("//tbody/tr[2]/td[1]")).getText(),leastExpensiveSpf30);
	}


}
