
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.WebElement

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.util.KeywordUtil

import my.VerifyImgSrcURL


WebUI.openBrowser("https://kazurayam.github.io/ks_verifyImagePresent/page3.html")

WebElement imgElement = WebUI.findWebElement(findTestObject("img"), 10)

boolean ok = VerifyImgSrcURL.doVerify(imgElement)

WebUI.closeBrowser()

if (!ok) {
	KeywordUtil.markFailedAndStop("not OK")
}
