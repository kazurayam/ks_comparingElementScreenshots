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

The original image in the `a-bite-in-the-apple.png` file has the width = 200px. But the page2.html specifies resizing the image by `style="width: 100px"`. So, in the browser's view port, the image looks different from the original PNG file. Therefore the `WebUI.verifyImagePresent` keyword failed to find the apple. How poor the keyword is!

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

The `WebUI.verifyImagePresent` keyword tries to find the target image in the screenshot of the current desktop. It does not automatically scroll the browser's view port to the target `<img>` element. Therefore `WebUI.verifyImagePresent` was tricked by the long paragraphs. Therefore the `WebUI.verifyImagePresent` keyword was unabled to find the concealed apple image. How poor the keyword is!

## Why the message from the keyword is so poor?

As the above samples show, the error message emitted by the `WebUI.verifyImagePresent` keyword is poor. It tells nothing about why the keyword failed to find the image you specified. Therefore when the keyword failed, almost always, the users will loose their way what to do next to fix the problem. I think that this poorness of diagnostics from the keyword is the most serious defect of the keyword.

Why the message is so poor?

You can study the source code and find the reason. You can find the source code of `WebUI.verifyImagePresent` keyword at the following:

- https://github.com/katalon-studio/katalon-studio-testing-framework/blob/master/Include/scripts/groovy/com/kms/katalon/core/webui/keyword/builtin/VerifyImagePresentKeyword.groovy

he `VerifyImagePresentKeyword` class delegates the task of "verifying if the image is present in the web page" to other classes. Ultimately the following class does the task.

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

The point is this single line:
```
            ScreenRegion reg = this.mainScreen.find(target);
```

Katalon's `WebUI.verifyImagePresent` keyword is a thin wrapper of the [org.sikuli.api.DefaultScreenRegion.find(ScreenRegion)](https://javadox.com/org.sikuli/sikuli-api/1.0.2/org/sikuli/api/DefaultScreenRegion.html#find(org.sikuli.api.Target)). The `DefaultScreenRegion.find()` method just returns null when it failed to find the "apple" image in the desktop screenshot. It does not give any diagnostics. The `WebUI.verifyImagePresent` keyword checks if the result is null or not. When null, the keyword fails. It does no more than that.

## My Conclusion

The `WebUI.verifyImagePresent` keywords quite often fails due to many causes. I just tell you some reasons:

1. Your test code does not wait the page to load completely
2. The `<img>` element can be resized by CSS, therefore the image is displayed diffent from the image file
3. The `<img>` element may be located outside the view port of the browser. You may need to scroll the view port to the target `<img>` element explicitly.

When `WebUI.verifyImagePresent` keyword failed, Katalon Studio won't given any diagnostics why it failed. Therefore it is very difficult to fix the failure.

I personally would never use the `WebUI.verifyImagePresent` keyword. It just annoys me.
