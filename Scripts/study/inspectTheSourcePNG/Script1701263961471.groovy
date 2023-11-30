import java.awt.image.BufferedImage
import javax.imageio.ImageIO

File applePNG = new File("a-bite-in-the-apple.png");
 
BufferedImage bi = ImageIO.read(applePNG)
int width = bi.getWidth()
int height = bi.getHeight()

println width + " x " + height   // 200 x 199
