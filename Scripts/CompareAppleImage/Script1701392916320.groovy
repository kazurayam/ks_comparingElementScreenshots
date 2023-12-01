import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.awt.image.BufferedImage
import java.math.RoundingMode;
import java.nio.file.Files
import java.nio.file.Paths

import javax.imageio.ImageIO

import org.openqa.selenium.By

import com.kazurayam.ashotwrapper.AShotWrapper
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import ru.yandex.qatools.ashot.Screenshot
import ru.yandex.qatools.ashot.comparison.ImageDiff
import ru.yandex.qatools.ashot.comparison.ImageDiffer
import ru.yandex.qatools.ashot.comparison.ImageMarkupPolicy


assert targetName != null

ImageDiff makeImgDiff(String img1, String img2) {
	Screenshot ss1 = toScreenshot(img1)
	Screenshot ss2 = toScreenshot(img2)
	ImageDiffer differ = new ImageDiffer().withDiffMarkupPolicy(new ImageMarkupPolicy())
	ImageDiff diff = differ.makeDiff(ss1, ss2)
	return diff
}

Screenshot toScreenshot(String filePath) {
	File f = new File(filePath)
	BufferedImage bi = ImageIO.read(f)
	return new Screenshot(bi)
}

/**
 * Calculate the ratio of diff-size against the whole page size.
 * <p>
 * The result is rounded up.  E.g. 0.0001 to 0.01
 */
Double calculateDiffRatioPercent(ImageDiff diff) {
	boolean hasDiff = diff.hasDiff();
	if (!hasDiff) {
		return 0.0;
	}
	int diffSize = diff.getDiffSize();
	int area = diff.getMarkedImage().getWidth() * diff.getMarkedImage().getHeight();
	Double diffRatio = diffSize * 1.0D / area * 100;
	return roundUpTo2DecimalPlaces(diffRatio);
}

Double roundUpTo2DecimalPlaces(Double diffRatio) {
	BigDecimal bd = new BigDecimal(diffRatio);
	BigDecimal bdUP = bd.setScale(2, RoundingMode.HALF_EVEN); // 0.001 -> 0.01
	return bdUP.doubleValue();
}



Files.createDirectories(Paths.get("tmp/${targetName}"));

String url = "https://kazurayam.github.io/ks_verifyImagePresent/${targetName}.html"

WebUI.openBrowser(url)
WebUI.verifyElementPresent(findTestObject("img_apple"), 10)

// take screenshot of the <img id="apple"> element, save it into file
BufferedImage image = AShotWrapper.saveElementImage(
	DriverFactory.getWebDriver(),
	By.xpath("//img[@id='apple']"),
	new File("tmp/${targetName}/apple_in_page.png"))

WebUI.closeBrowser()

ImageDiff imageDiff = makeImgDiff("docs/a-bite-in-the-apple.png", "tmp/${targetName}/apple_in_page.png")

// save the diff image into a file
ImageIO.write(imageDiff.getMarkedImage(),"png", new File("tmp/${targetName}/comparison_marked.png"))

double diffRatio = diffRatio = calculateDiffRatioPercent(imageDiff)
println targetName + " diffRatio: " + diffRatio + "%"

