import java.awt.image.BufferedImage
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import org.openqa.selenium.By

import com.kazurayam.ashotwrapper.AShotWrapper
import com.kazurayam.compareimage.ImageComparator
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable


TestObject makeTestObject(String xpath) {
	TestObject tObj = new TestObject(xpath)
	tObj.addProperty("xpath", ConditionType.EQUALS, xpath)
	return tObj
}

String url = "https://kazurayam.github.io/ks_comparingElementScreenshots/page1.html"

WebUI.openBrowser(url)
WebUI.verifyElementPresent(makeTestObject("//img[@id='apple']"), 10)

Path outfile = Paths.get(GlobalVariable.OUTPUT_DIR_compareElementScreenshots).resolve("page1/expected_apple.png")
Files.createDirectories(outfile.getParent())

// take screenshot of the <img id="apple"> element, save it into file
BufferedImage image = AShotWrapper.saveElementImage(
	DriverFactory.getWebDriver(),
	By.xpath("//img[@id='apple']"),
	outfile.toFile())

WebUI.closeBrowser()


