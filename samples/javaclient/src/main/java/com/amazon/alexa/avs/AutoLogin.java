package com.amazon.alexa.avs;

import com.amazon.alexa.avs.config.DeviceConfig;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.chrome.ChromeDriver;
import com.gargoylesoftware.htmlunit.BrowserVersion;

import java.util.logging.*;


/**
 * Automatically authenticate with Amazon
 */

public class AutoLogin {	
	
	private static final int TIMEOUT = 10;
	
    private String autoLoginUsername;
    private String autoLoginPassword;
    
    /**
	 * @param deviceConfig
	 */
	public AutoLogin(DeviceConfig deviceConfig) {		
		autoLoginUsername = deviceConfig.getAutoLoginUsername();
		autoLoginPassword = deviceConfig.getAutoLoginPassword();
	}
	
	/**
	 * @param url
	 */
	public void login(String url) {
		
		Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF); 
	    Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);
			    
	    HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion.CHROME);
		//WebDriver driver = new ChromeDriver();
		
		driver.get(url);
		
		WebElement emailBox = driver.findElement(By.id("ap_email"));	
		//emailBox.click();
		emailBox.sendKeys(autoLoginUsername);
		
		WebElement passwordBox = driver.findElement(By.id("ap_password"));
		passwordBox.sendKeys(autoLoginPassword);

		driver.setJavascriptEnabled(true);
		passwordBox.submit();
		
		// wait for page to load
		try {
			(new WebDriverWait(driver, TIMEOUT)).until(stalenessOf(passwordBox));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			WebElement okayButton = driver.findElement(By.name("consentApproved"));
			okayButton.click();
			
			(new WebDriverWait(driver, TIMEOUT)).until(stalenessOf(okayButton));
		} catch (Exception e) {
			//e.printStackTrace();
		}
		
		driver.close();
	}
	
	private ExpectedCondition<Boolean> stalenessOf(final WebElement element) {
	    return new ExpectedCondition<Boolean>() {
	      public Boolean apply(WebDriver ignored) {
	        try {
	          element.isEnabled();
	          return false;
	        } catch (StaleElementReferenceException expected) {
	          return true;
	        }
	      }
	    };
	}

}