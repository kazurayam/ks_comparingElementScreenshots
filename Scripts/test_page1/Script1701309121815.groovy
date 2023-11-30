import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

File page = new File("page1.html")

WebUI.openBrowser(page.toURI().toURL().toString())
WebUI.verifyImagePresent(findTestObject("apple"))
WebUI.closeBrowser()