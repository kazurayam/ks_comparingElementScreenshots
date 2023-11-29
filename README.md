# ks_verifyImagePresent

This project is developed in the hope that I could anser to a question raised in the Katalon Forum:

- [Verify image and take screen shot keyword doesn't work](https://forum.katalon.com/t/verify-image-and-take-screen-shot-keyword-doesnt-work/108573)

## How the verifyImagePresent keyword is implemented

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
