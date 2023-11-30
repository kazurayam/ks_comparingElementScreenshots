# [Katalon Studio] Why WebUI.verifyImagePresent keyword doesn't work?

This project is developed in the hope that I could answer to a question raised in the Katalon Forum:

- [Verify image and take screen shot keyword doesn't work](https://forum.katalon.com/t/verify-image-and-take-screen-shot-keyword-doesnt-work/108573)

The original poster tried the [`WebUI.verifyImagePresent`](https://docs.katalon.com/docs/katalon-studio/keywords/keyword-description-in-katalon-studio/web-ui-keywords/webui-verify-image-present) keyword in her/his Katalon test script. The poster found the keyword did not work as expected. The poster searched the Katalon Forum but could not find any helpful information.

Here I would show some sample codes and explain.

You can see the URLs as test target in [here](https://kazurayam.github.io/ks_verifyImagePresent/)

## "Test Cases/test_page1" passed

Please read the source code of "Test Cases/test_page1":

```
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

WebUI.openBrowser("https://kazurayam.github.io/ks_verifyImagePresent/page1.html")
WebUI.verifyImagePresent(findTestObject("apple"))
WebUI.closeBrowser()
```
[source](https://github.com/kazurayam/ks_verifyImagePresent/blob/develop/Scripts/test_page1/Script1701309121815.groovy)

This script queries the remote web page at https://kazuraaym.github.io/ks_verifyImagePresent/page1.html which looks like

![page1](https://kazurayam.github.io/ks_verifyImagePresent/image/page1.png)

The `test_page1` script tries to verify if an image of apple is displayed in it. The Test Object "apple" points the element `<img id="apple">` as follows:

![test object apple](https://kazurayam.github.io/ks_verifyImagePresent/image/test_object_apple.png)

When I ran this, it just passes as I expected.

## "Test Cases/test_page2" failed

Please read the source code of "Test Cases/test_page2":

```
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

WebUI.openBrowser("https://kazurayam.github.io/ks_verifyImagePresent/page2.html")
WebUI.verifyImagePresent(findTestObject("apple"))
WebUI.closeBrowser()
```
[source](https://github.com/kazurayam/ks_verifyImagePresent/blob/develop/Scripts/test_page2/Script1701309170129.groovy)

The only difference from the `test_page1` script is the URL to query. `page1.html` -> `page2.html`. The page looks like

![page1](https://kazurayam.github.io/ks_verifyImagePresent/image/page2.png)

When I ran the `test_page2`, it failed with the following message:

```
2023-11-30 12:52:57.648 DEBUG testcase.test_page2                      - 2: verifyImagePresent(findTestObject("apple"))
2023-11-30 12:52:58.477 ERROR c.k.k.core.keyword.internal.KeywordMain  - ❌ Unable to verify image present (Root cause: com.kms.katalon.core.exception.StepFailedException: Unable to verify image present
	at com.kms.katalon.core.webui.keyword.internal.WebUIKeywordMain.stepFailed(WebUIKeywordMain.groovy:64)
	at com.kms.katalon.core.webui.keyword.internal.WebUIKeywordMain.runKeyword(WebUIKeywordMain.groovy:26)
	at com.kms.katalon.core.webui.keyword.builtin.VerifyImagePresentKeyword.verifyImagePresent(VerifyImagePresentKeyword.groovy:94)
	at com.kms.katalon.core.webui.keyword.builtin.VerifyImagePresentKeyword.execute(VerifyImagePresentKeyword.groovy:67)
	at com.kms.katalon.core.keyword.internal.KeywordExecutor.executeKeywordForPlatform(KeywordExecutor.groovy:74)
	at com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords.verifyImagePresent(WebUiBuiltInKeywords.groovy:2794)
	at com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords$verifyImagePresent$0.call(Unknown Source)
	at test_page2.run(test_page2:6)
...
```

The message tell just the keyword `WebUI.verifyImagePresent` failed without any diagnostics.

But I can tell you the reason. The apple image was resized by CSS width property. You can find the following line in the [page2.html](https://kazuraaym.github.io/ks_verifyImagePresent/page2.html):

```
    <img id="apple" src="./a-bite-in-the-apple.png" alt="as_is" style="width: 100px">
```

I can tell you the reason why the keyword failed. The image in the `a-bite-in-the-apple.png` file has the original width = 200px. But the page2.html specifies resizing the image by `style="width: 100px"`. In the browser's view port, the image looks different from the original PNG file. Therefore the keyword failed.

## "Test Cases/test_page3" failed

Please read the source code of "Test Cases/test_page3":

```
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

WebUI.openBrowser("https://kazurayam.github.io/ks_verifyImagePresent/page3.html")
WebUI.verifyImagePresent(findTestObject("apple"))
WebUI.closeBrowser()
```
[source](https://github.com/kazurayam/ks_verifyImagePresent/blob/develop/Scripts/test_page3/Script1701312229772.groovy

The only difference from the `test_page1` script is the URL to query. `page1.html` -> `page3.html`. The page looks like

![page3](https://kazurayam.github.io/ks_verifyImagePresent/image/page3.png)

When I ran the `test_page3`, it failed with messages just like the `test_page2`The message tell just the keyword `WebUI.verifyImagePresent` failed without any diagnostics.

But I can tell you the reason. The [page2.html](https://kazuraaym.github.io/ks_verifyImagePresent/page2.html) contains a lot of long paragraphs before the `<img>` element so that the apple image is out of the browser's view port when the page is loaded.

```
    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. At tellus at urna condimentum mattis. Cras adipiscing enim eu turpis egestas. Lectus proin 
    ....
    <img id="apple" src="./a-bite-in-the-apple.png" alt="as_is">
```

The `WebUI.verifyImagePresent` keyword tries to find the target image in the screenshot of the current desktop. It does not automatically scroll the browser's view port to the target `<img>` element. Therefore `WebUI.verifyImagePresent` was tricked by the long paragraphs. The keword was unabled to find the concealed apple image.


## Conclusion







How the verifyImagePresent keyword is implemented

You can find the source of the `WebUI.verifyImagePresent` keyword at

- https://github.com/katalon-studio/katalon-studio-testing-framework/blob/master/Include/scripts/groovy/com/kms/katalon/core/webui/keyword/builtin/VerifyImagePresentKeyword.groovy

The `VerifyImagePresentKeyword` class delegates the task of "verifying if the image is present in the web page" to other classes. Ultimately the following class does the task.

- https://github.com/katalon-studio/katalon-studio-testing-framework/blob/master/Include/scripts/groovy/com/kms/katalon/core/webui/common/ScreenUtil.java

```
import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;

public class ScreenUtil {

    private ScreenRegion mainScreen;
    
    private double similarity = 0.75; // Default value

    public ScreenUtil() {
        mainScreen = new DesktopScreenRegion();
    }
    ....
    private ScreenRegion findImage(String imagePath) throws Exception {
        File imgFile = new File(imagePath);
        if (imgFile.exists()) {
            Target target = new ImageTarget(imgFile);
            target.setMinScore(this.similarity);
            ScreenRegion reg = this.mainScreen.find(target);
            return reg;
        } else {
            throw new Exception(StringConstants.COMM_EXC_IMG_FILE_DOES_NOT_EXIST);
        }
    }
```




http://doc.sikuli.org/region.html#finding-inside-a-region-and-waiting-for-a-visual-event

Sikulix Region
https://sikulix.github.io/docs/api/region/

Display resolution matters
https://stackoverflow.com/questions/47644402/getting-find-failed-errors-in-sikuli

```
	<classpathentry kind="lib" path="/Applications/Katalon Studio.app/Contents/Eclipse/configuration/resources/lib/repackaged-sikuli-api-1.0.2-standalone.jar"/>
```