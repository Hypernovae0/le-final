import java.awt.image.BufferedImage;
import java.awt.Color;
public class BaseImage
{
    BufferedImage imgData; 
    public BaseImage(BufferedImage img)
    {
        imgData = img;
    }
    //returns Color of given pixel so we don't have to convert aRGB to Color every time
    public Color getPixel(int x, int y)
    {
        return new Color(imgData.getRGB(x,y), true);
    }
    //returns pure BufferedImage
    public BufferedImage getImgData()
    {
        return imgData;
    }
    public void setImgData(BufferedImage data)
    {
        imgData = data;
    }
    public int getWidth()
    {
        return imgData.getWidth();
    }
    public int getHeight()
    {
        return imgData.getHeight();
    }
}
