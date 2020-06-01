import java.util.Scanner;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import java.text.DecimalFormat;
import java.awt.FileDialog;
public class IMGCOMP
{
    //creates Images and ComparedImages and draws using JFrame
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("IMGCOMP");
        FileDialog picker = new FileDialog(frame, "Select files to compare", FileDialog.LOAD); //native file picker interface
        File[] files;
        do{
            picker.setMultipleMode(true);
            picker.setVisible(true);
            files = picker.getFiles();
        }while(files.length < 2); // in case user selects less than two files
        try //File class requires exception catching, so everything goes in here
        {
            BaseImage img1 = new BaseImage(ImageIO.read(files[0]));
            BaseImage img2 = new BaseImage(ImageIO.read(files[1]));
            ComparedImage resultImg = new ComparedImage(img1, img2); //does all the math on construction
            JLabel img1Label = new JLabel(
                new ImageIcon(img1.getImgData().getScaledInstance(250,250,Image.SCALE_SMOOTH)) //new icons using image data scaled down
                );
            JLabel img2Label = new JLabel(
                new ImageIcon(img2.getImgData().getScaledInstance(250,250,Image.SCALE_SMOOTH))
                );
            JLabel resultLabel = new JLabel(new ImageIcon(resultImg.getImgData()));
            DecimalFormat df = new DecimalFormat("##.##");                         //this class creates regular number formatting -- no long trailing 0s
            //here's the text label. it uses HTML because \n doesn't work in a label, so I have to use <br/>
            JLabel textLabel = new JLabel("<html>" + df.format(resultImg.getMatch()*100.0) + "% match <br/>" + resultImg.getMatchedPixels() + "/" + resultImg.getTotalPixels() + " exact pixels</html>");
            JPanel panel = new JPanel();
            panel.add(img1Label);
            panel.add(img2Label);
            panel.add(resultLabel);
            panel.add(textLabel);
            frame.add(panel);
            frame.setSize(1000, 300);
            frame.setVisible(true);
        }
        catch (IOException e)
        {
            System.out.println("File not found");
        }
    }
}
