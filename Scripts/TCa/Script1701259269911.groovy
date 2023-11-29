import static my.TestObjectSupport.makeTestObject

import org.sikuli.api.DesktopScreenRegion
import org.sikuli.api.ScreenRegion

import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import my.ImageFinderInScreenRegion
import my.ImageUtil

File htmlFile = new File("./page1.html")
String xpathToImg = "//img[@id='apple']"

WebUI.openBrowser(htmlFile.toURI().toURL().toExternalForm())
WebUI.setViewPortSize(800, 600)
WebUI.waitForElementPresent(makeTestObject(xpathToImg), 10)

// take the screenshot of the display device
ScreenRegion mainScreen = new DesktopScreenRegion()
ImageUtil.saveImage(mainScreen, "tmp/mainScreen.png")

WebUI.closeBrowser()

ScreenRegion screenRegion = 
	ImageFinderInScreenRegion.findImage(screenRegion = mainScreen, 
										imagePath = "./a-bite-in-the-apple.png",
										similarity = 0.25)

if (screenRegion != null) {
	KeywordUtil.logInfo("Image was found")
} else {
	KeywordUtil.markFailedAndStop("Image was NOT found")
}