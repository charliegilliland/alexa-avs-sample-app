package com.amazon.alexa.avs;

import com.amazon.alexa.avs.config.DeviceConfig;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

/**
 * Automatically authenticate with Amazon
 */

public class AutoLogin {	
	
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
		WebDriver driver = new HtmlUnitDriver();
		driver.get(url);

		WebElement emailBox = driver.findElement(By.id("ap_email"));
		WebElement passwordBox = driver.findElement(By.id("ap_password"));
		WebElement loginButton = driver.findElement(By.id("signInSubmit"));

		emailBox.sendKeys(autoLoginUsername);
		passwordBox.sendKeys(autoLoginPassword);
		passwordBox.submit();

		driver.close();
	}

}