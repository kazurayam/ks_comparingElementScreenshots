package my

import org.sikuli.api.ImageTarget
import org.sikuli.api.ScreenRegion
import org.sikuli.api.Target


public class ImageFinderInScreenRegion {

	public static ScreenRegion findImage(ScreenRegion screenRegion, String imagePath, double similarity = 0.75) throws Exception {
		File imgFile = new File(imagePath);
		if (imgFile.exists()) {
			Target target = new ImageTarget(imgFile);
			target.setMinScore(similarity);
			ScreenRegion reg = screenRegion.find(target);
			return reg
		} else {
			throw new Exception(imagePath + " was not found in the screen");
		}
	}
}
