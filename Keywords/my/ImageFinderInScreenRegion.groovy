package my

import org.sikuli.api.ImageTarget
import org.sikuli.api.ScreenRegion
import org.sikuli.api.Target
import static my.ImageUtil.saveImage;
import com.kms.katalon.core.util.KeywordUtil

public class ImageFinderInScreenRegion {

	public static ScreenRegion findImage(ScreenRegion screenRegion, String imagePath, double similarity = 0.75) throws Exception {
		assert screenRegion != null
		File imgFile = new File(imagePath);
		if (imgFile.exists()) {
			Target target = new ImageTarget(imgFile);
			saveImage(target.getImage(), "tmp/target.png");
			target.setMinScore(similarity);
			ScreenRegion found = screenRegion.find(target);
			KeywordUtil.logInfo("found = " + found.toString())
			return found
		} else {
			throw new Exception(imagePath + " was not found in the screen");
		}
	}
}
