
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Calendar;

import javax.swing.*;

public class BackgroundFrame extends JPanel {

	private int month;
	private int height;
	private int width;
	
	public BackgroundFrame(int theMonth, int width, int height)
	{
		month = theMonth;
		this.width = width;
		this.height =height;
	}
	
	
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		super.paintComponents(g);
		Image bgi = null;
		if(month <= 8 && month >= 6)
			bgi = new ImageIcon(getClass().getResource("/summer.jpg")).getImage();
		else if(month <= 11 && month >= 9)
			bgi = new ImageIcon(getClass().getResource("/fall.jpg")).getImage();
		else if(month <= 5 && month >= 3)
			bgi = new ImageIcon(getClass().getResource("/spring.jpg")).getImage();
		else if(month == 12 || month <= 2)
			bgi = new ImageIcon(getClass().getResource("/winter.jpg")).getImage();
		
		g.drawImage(bgi, 0, 0, width, height, this);
	}
}
