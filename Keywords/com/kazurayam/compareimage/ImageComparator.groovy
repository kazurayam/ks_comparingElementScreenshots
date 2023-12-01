package com.kazurayam.compareimage

import java.awt.Color
import java.awt.image.BufferedImage
import java.math.RoundingMode;
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import javax.imageio.ImageIO

import ru.yandex.qatools.ashot.Screenshot
import ru.yandex.qatools.ashot.comparison.DiffMarkupPolicy
import ru.yandex.qatools.ashot.comparison.ImageDiff
import ru.yandex.qatools.ashot.comparison.ImageDiffer
import ru.yandex.qatools.ashot.comparison.ImageMarkupPolicy

public class ImageComparator {

	static double getDiffDegree(String expected, String actual, String diff) {
		return getDiffDegree(Paths.get(expected), Paths.get(actual), Paths.get(diff))
	}

	static double getDiffDegree(Path expected, Path actual, Path diff) {
		ImageDiff imageDiff = makeImageDiff(expected, actual, diff)
		return calculateDiffRatioPercent(imageDiff)
	}

	static ImageDiff makeImageDiff(Path expected, Path actual, Path diff) {
		Screenshot ss1 = toScreenshot(expected)
		Screenshot ss2 = toScreenshot(actual)
		DiffMarkupPolicy policy = new ImageMarkupPolicy().withDiffColor(Color.GRAY)
		ImageDiffer differ = new ImageDiffer().withDiffMarkupPolicy(policy)
		ImageDiff imageDiff = differ.makeDiff(ss1, ss2)
		Files.createDirectories(diff.getParent())
		ImageIO.write(imageDiff.getMarkedImage(), "png", diff.toFile())
		return imageDiff
	}

	private static Screenshot toScreenshot(Path imageFile) {
		BufferedImage bi = ImageIO.read(imageFile.toFile())
		return new Screenshot(bi)
	}

	/**
	 * Calculate the ratio of diff-size against the whole page size.
	 * <p>
	 * The result is rounded up.  E.g. 0.0001 to 0.01
	 */
	private static Double calculateDiffRatioPercent(ImageDiff diff) {
		boolean hasDiff = diff.hasDiff();
		if (!hasDiff) {
			return 0.0;
		}
		int diffSize = diff.getDiffSize();
		int area = diff.getMarkedImage().getWidth() * diff.getMarkedImage().getHeight();
		Double diffRatio = diffSize * 1.0D / area * 100;
		return roundUpTo2DecimalPlaces(diffRatio);
	}

	private static Double roundUpTo2DecimalPlaces(Double diffRatio) {
		BigDecimal bd = new BigDecimal(diffRatio);
		BigDecimal bdUP = bd.setScale(2, RoundingMode.HALF_EVEN); // 0.001 -> 0.01
		return bdUP.doubleValue();
	}
}
