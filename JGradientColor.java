
import java.awt.*;

import javax.swing.*;

public class JGradientColor extends JButton
{
	@Override
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		Color c1 = new Color(48,67,82);
		Color c2 = new Color(215,210,204);
		g2.setPaint(new GradientPaint(0, 0, c1, getWidth(), getHeight(), c2));
		g2.fillRect(0, 0, getWidth(), getHeight());
		super.paintComponent(g);
	}
}
