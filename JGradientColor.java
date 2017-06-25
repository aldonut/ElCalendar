
import java.awt.*;

import javax.swing.*;

public class JGradientColor extends JButton
{
	
	private Color c1;
	private Color c2;

	public JGradientColor(Color c1, Color c2)
	{
		this.c1 = c1;
		this.c2 = c2;
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setPaint(new GradientPaint(0, 0, c1, getWidth(), getHeight(), c2));
		g2.fillRect(0, 0, getWidth(), getHeight());
		super.paintComponent(g);
	}
}
