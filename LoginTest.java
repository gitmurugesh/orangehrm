package com.orangehrm.login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LoginTest {
	WebDriver driver;

	@BeforeClass
	public void beforeClass() {

		System.setProperty("webdriver.chrome.driver", "C:\\seleni\\drivers\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
		// System.setProperty("webdriver.gecko.driver",
		// "D:\\Selenium\\Drivers\\geckodriver.exe");
		// driver = new FirefoxDriver();

		driver.manage().window().maximize();
		driver.get("https://opensource-demo.orangehrmlive.com/");

	}

	@AfterClass
	public void afterClass() {
		driver.findElement(By.id("welcome")).click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.findElement(By.linkText("Logout")).click();
		driver.quit();
	}

	/**
	 * TS_PIM_01	Check if the user is able to enter the Orange HRM system with a successful ESS-User account.
	 * 
	 */
	@Test(priority = 0)
	void loginTest() {
		driver.findElement(By.id("txtUsername")).sendKeys("Admin");
		driver.findElement(By.id("txtPassword")).sendKeys("admin123");
		driver.findElement(By.id("btnLogin")).click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String welcomemsg = driver.findElement(By.id("welcome")).getText();
		Assert.assertEquals(welcomemsg, "Welcome Admin");
	}

	/**
	 * TS_PIM_02	Check if the user is able to see the “General Information” in organization tab under admin on logging in the first time
	 * 
	 */
	@Test(priority=1)
	void checkGeneralInfoTest() {
		
		WebElement adminelem = driver.findElement(By.cssSelector("#menu_admin_viewAdminModule > b"));// *[@id="menu_admin_viewAdminModule"]/b
																										// //*[@id="menu_admin_viewAdminModule"]/b
		// WebElement adminelem = driver.findElement(By.id("menu_admin_viewAdminModule"));

		Actions action = new Actions(driver);
		action.moveToElement(adminelem).build().perform();

		driver.findElement(By.linkText("Organization")).click();
		driver.findElement(By.linkText("General Information")).click();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String geninfo = driver.findElement(By.id("genInfoHeading")).getText();
		Assert.assertEquals(geninfo, "General Information");
	}

	
	/**
	 * TS_PIM_04	Add new Employee under PIM, Employee list
	 * 
	 */
	@Test(priority=2)
	void addEmpTest() {

		driver.findElement(By.id("menu_pim_viewPimModule")).click();

		driver.findElement(By.id("menu_pim_addEmployee")).click();
		driver.findElement(By.id("firstName")).sendKeys("Test");
		driver.findElement(By.id("lastName")).sendKeys("User");

		String addempid = driver.findElement(By.id("employeeId")).getAttribute("value");
		System.out.println("LoginTest.addEmpTest() addempid=" + addempid);

		driver.findElement(By.id("btnSave")).click();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String viewempid = driver.findElement(By.id("personal_txtEmployeeId")).getAttribute("value");

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("LoginTest.addEmpTest() viewempid=" + viewempid);
		Assert.assertEquals(viewempid, addempid);
	}

	/**
	 * TS_PIM_06	"Goto Dashboard
 	 * 1.Mouse over on Employee Distribution by subunit graph	
	 * 
	 */
	@Test(priority = 3)
	void dashboardMouseHoverTest() {

		driver.findElement(By.id("menu_pim_viewPimModule")).click();
		driver.findElement(By.id("menu_dashboard_index")).click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		WebElement pie1 = driver.findElement(By.xpath("//*[@id=\"pieLabel4\"]/div"));
		WebElement pie2 = driver.findElement(By.xpath("//*[@id=\"pieLabel2\"]/div"));
		pie1.click();

		Actions action = new Actions(driver);
		action.moveToElement(pie1, 50, 50).perform();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		action.moveToElement(pie2, -50, 50).perform();

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
