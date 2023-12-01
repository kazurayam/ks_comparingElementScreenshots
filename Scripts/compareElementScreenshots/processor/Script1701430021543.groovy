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

assert targetName != null

TestObject makeTestObject(String xpath) {
	TestObject tObj = new TestObject(xpath)
	tObj.addProperty("xpath", ConditionType.EQUALS, xpath)
	return tObj
}

Path outDir = Paths.get(GlobalVariable.OUTPUT_DIR_compareElementScreenshots).resolve("${targetName}")
Files.createDirectories(outDir)

String url = "https://kazurayam.github.io/ks_comparingElementScreenshots/${targetName}.html"

WebUI.openBrowser(url)
WebUI.setViewPortSize(720, 720)
WebUI.verifyElementPresent(makeTestObject("//img[@id='apple']"), 10)

// take screenshot of the <img id="apple"> element, save it into file
BufferedImage image = AShotWrapper.saveElementImage(
	DriverFactory.getWebDriver(),
	By.xpath("//img[@id='apple']"),
	outDir.resolve("image_in_the_page.png").toFile())

WebUI.closeBrowser()

double difference = ImageComparator.getDiffDegree(
	Paths.get(GlobalVariable.OUTPUT_DIR_compareElementScreenshots).resolve("page1/expected_apple.png"),
	outDir.resolve("image_in_the_page.png"),
	outDir.resolve("image_marked.png")
	)

// save the diff image into a file
File report = outDir.resolve("difference.txt").toFile()
report.text = """{ "difference": ${difference} }"""

// if the difference% is greater than the criteria, the test should fail
double criteria = 30.0d

if (difference> criteria) {
	KeywordUtil.markFailed(targetName + " difference: " + difference + "% is greater than criteria:" + criteria + "%")
} else {
	KeywordUtil.logInfo(targetName + " difference: " + difference + "% is slower than criteria: " + criteria + "%")
}