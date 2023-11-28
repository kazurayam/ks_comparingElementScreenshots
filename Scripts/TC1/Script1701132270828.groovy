import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

File page1 = new File("page1.html")

WebUI.openBrowser(page1.toURI().toURL().toString())
WebUI.verifyImagePresent(findTestObject("apple"))
WebUI.closeBrowser()