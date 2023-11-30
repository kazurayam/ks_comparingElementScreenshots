package my

import java.awt.image.BufferedImage
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import com.kms.katalon.core.util.KeywordUtil

import javax.imageio.ImageIO

import org.sikuli.api.ScreenRegion

public class ImageUtil {

	static void saveImage(ScreenRegion sr, String filePath) {
		BufferedImage bi = sr.capture()
		saveImage(bi, filePath)
	}

	static void saveImage(BufferedImage bi, String filePath) {
		KeywordUtil.logInfo(filePath + " width:" + bi.getWidth() + ", height:" + bi.getHeight())
		Path file = Paths.get(filePath)
		Files.createDirectories(file)
		ImageIO.write(bi, "png", file.toFile())
	}
}
