package tests;

import org.testng.annotations.Test;

import util.LocalDriverManager;

public class ThreadLocal {
	
	@Test
	public void testMethod1() {
		invokeBrowser("https://www.amazon.in");
	}
	
	@Test
	public void testMethod2() {
		invokeBrowser("https://www.flipkart.com");
	}
	
	public void invokeBrowser(String url) {
		System.out.println("Thread Id= "+Thread.currentThread().getId());
		System.out.println("Hashcode of WebDriver Instance= "+LocalDriverManager.getDriver().hashCode());
		LocalDriverManager.getDriver().get(url);
	}
}