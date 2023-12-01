import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

WebUI.callTestCase(findTestCase("CompareAppleImage"), ["targetName": "page1"])
WebUI.callTestCase(findTestCase("CompareAppleImage"), ["targetName": "page2"])
WebUI.callTestCase(findTestCase("CompareAppleImage"), ["targetName": "page4"])
