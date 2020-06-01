import java.awt.image.BufferedImage;
import java.awt.Color;
public class ComparedImage
{
    double match;
    int totalPixels;
    int matchedPixels = 0;
    BufferedImage compared;
    int rDiff;
    int bDiff;
    int gDiff;
    int avgDiff;
    Color compPixel;
    double[][] matchedValues;
    int resultWidth;
    int resultHeight;
    /*constructor will compare the two images on ComparedImage creation and save the comparedImage as a
    member variable, while storing percent match and pixels as instance variables*/
    public ComparedImage(BaseImage i1, BaseImage i2)
    {
        //compare code
        int width1 = i1.getWidth();  //since images can be different heights and widths
        int height1 = i1.getHeight();//we need to create a final height and width that is the smallest of each
        int width2 = i2.getWidth();
        int height2 = i2.getHeight();
        resultWidth = (width1 < width2) ? width1 : width2;     //ternary expressions to do that
        resultHeight = (height1 < height2) ? height1 : height2;
        totalPixels = resultWidth * resultHeight;
        compared = new BufferedImage(resultWidth, resultHeight, BufferedImage.TYPE_3BYTE_BGR); //create new blank image with our dimensions that is RGBA
        matchedValues = new double[resultWidth][resultHeight]; //a 2D array representing the matched % of each pixel in the image
        for (int i = 0; i < resultHeight; i++)
        {
            for (int j = 0; j < resultWidth; j++)
            {
                rDiff = Math.abs(i1.getPixel(j,i).getRed() - i2.getPixel(j,i).getRed());     
                gDiff = Math.abs(i1.getPixel(j,i).getGreen() - i2.getPixel(j,i).getGreen());
                bDiff = Math.abs(i1.getPixel(j,i).getBlue() - i2.getPixel(j,i).getBlue());
                avgDiff = calcDiff(rDiff,gDiff,bDiff);
                compPixel = new Color(avgDiff,255-avgDiff,0);                         
                compared.setRGB(j,i,compPixel.getRGB());        //Color must be in int form for this method -- sets this pixel between red and green based on match
                matchedValues[j][i] = (double)(255-avgDiff)/255;  //fraction of perfect match. if avgDiff is 0, match would be 1.0
                //regardless of these calculations and their rounding errors, if the two images' colors are the same, increment perfect pixel counter
                if(i1.getPixel(j,i).equals(i2.getPixel(j,i)))    
                {
                    matchedPixels++;
                }
            }
        }
        double tempSumMatch = 0.0;
        for (int i = 0; i < resultHeight; i++)
        {
            for (int j = 0; j < resultWidth; j++)
            {
               tempSumMatch += matchedValues[j][i];
            }
        }
        match = tempSumMatch / totalPixels; //average of every difference between pixels
    }
    /* Method to calculate displayed color based on color differences
     * My algorithm considers each color difference independently. The difference between red and black looks almost as strong as white and black,
     * but the sum of white and black's RGB differences is much greater. My algorithm thus must be unbiased between colors that have fewer but stronger
     * color differences. It takes a weighted total of each color difference: 80% of the largest difference and 20% of the next. Colors can be 100% "different"
     * if their two biggest color differences are 100%.
     * This way contrasting pure red to white will be only slightly starker than pure red to black.
    */
    private static int calcDiff(int redDiff, int greenDiff, int blueDiff) {
        int temp;
        int[] clrArray = {redDiff, greenDiff, blueDiff};
        //organize by difference size. bubble sort except there's only three elements so it's not worth writing two loops.
        if (clrArray[0] > clrArray[1])
        {
            temp = clrArray[0];
            clrArray[0] = clrArray[1];
            clrArray[1] = temp;
        }
        if (clrArray[1] > clrArray[2])
        {
            temp = clrArray[1];
            clrArray[1] = clrArray[2];
            clrArray[2] = temp;
        }
        if (clrArray[0] > clrArray[1])
        {
            temp = clrArray[0];
            clrArray[0] = clrArray[1];
            clrArray[1] = temp;
        }
        return (int)Math.round(clrArray[2]*0.8 + clrArray[1]*0.2);
    }
    //return pure BufferedImage
    public BufferedImage getImgData()
    {
        return compared;
    }
    public double getMatch()
    {
        return match;
    }
    public int getTotalPixels()
    {
        return totalPixels;
    }
    public int getMatchedPixels()
    {
        return matchedPixels;
    }
}
