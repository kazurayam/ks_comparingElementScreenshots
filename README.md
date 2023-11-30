# ks_verifyImagePresent

This project is developed in the hope that I could answer to a question raised in the Katalon Forum:

- [Verify image and take screen shot keyword doesn't work](https://forum.katalon.com/t/verify-image-and-take-screen-shot-keyword-doesnt-work/108573)

The original poster tried the [`WebUI.verifyImagePresent`](https://docs.katalon.com/docs/katalon-studio/keywords/keyword-description-in-katalon-studio/web-ui-keywords/webui-verify-image-present) keyword in her/his Katalon test script. The poster found the keyword did not work as expected. The poster searched the Katlaon Forum for help but could not find any.

Here I would show some sample codes and explain.

You can see the target URLs to test from [here](https://kazurayam.github.io/ks_verifyImagePresent/)

## "Test Cases/test_page1"

- [test_page1](https://github.com/kazurayam/ks_verifyImagePresent/blob/develop/Scripts/test_page1/Script1701309121815.groovy)

```

```

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