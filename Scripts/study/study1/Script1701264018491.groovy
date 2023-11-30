import static my.TestObjectSupport.makeTestObject

import org.sikuli.api.DesktopScreenRegion
import org.sikuli.api.ScreenRegion

import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import my.ImageFinderInScreenRegion
import my.ImageUtil
import org.apache.commons.io.FileUtils

FileUtils.deleteDirectory(new File("tmp"));

File htmlFile = new File("./page1.html")
String xpathToImg = "//img[@id='apple']"

WebUI.openBrowser(htmlFile.toURI().toURL().toExternalForm())
WebUI.setViewPortSize(800, 600)
WebUI.verifyElementPresent(makeTestObject(xpathToImg), 10)

// take the screenshot of the display device
ScreenRegion mainScreen = new DesktopScreenRegion()
ImageUtil.saveImage(mainScreen, "tmp/mainScreen.png")

WebUI.closeBrowser()

ScreenRegion targetRegion = 
	ImageFinderInScreenRegion.findImage(screenRegion = mainScreen, 
										imagePath = "./a-bite-in-the-apple.png",
										similarity = 0.75)

if (targetRegion != null) {
	ImageUtil.saveImage(targetRegion, "tmp/targetRegion.png")
	KeywordUtil.logInfo("Image was found")
} else {
	KeywordUtil.markFailedAndStop("Image was NOT found")
}