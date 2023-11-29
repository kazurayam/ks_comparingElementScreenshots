package my

import java.awt.image.BufferedImage
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import javax.imageio.ImageIO

import org.sikuli.api.ScreenRegion

public class ImageUtil {
	
	static void saveImage(ScreenRegion sr, String filePath) {
		Path file = Paths.get(filePath)
		Files.createDirectories(file.getParent())
		BufferedImage bi = sr.capture()
		ImageIO.write(bi, "png", file.toFile())
	}
	
}
