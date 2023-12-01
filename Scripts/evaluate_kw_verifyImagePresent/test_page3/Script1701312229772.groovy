import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

WebUI.openBrowser("https://kazurayam.github.io/ks_verifyImagePresent/page3.html")
WebUI.verifyImagePresent(findTestObject("apple"))
WebUI.closeBrowser()
