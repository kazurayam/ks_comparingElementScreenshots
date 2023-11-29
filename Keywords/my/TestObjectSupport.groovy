package my

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject

public class TestObjectSupport {

	public static TestObject makeTestObject(String xpath) {
		TestObject tObj = new TestObject(xpath)
		tObj.addProperty("xpath", ConditionType.EQUALS, xpath)
		return tObj
	}
}
