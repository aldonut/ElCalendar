import java.awt.*;

import javax.swing.*;

public class EventPreviewMark extends JLabel
{
	Polygon bookMark;
	Event e;
	private final Color[] colorList = {new Color(220,20,60), new Color(30,144,255), new Color(0,206,209), new Color(148,0,211), new Color(210,105,30)};
	public EventPreviewMark(Event e)
	{
		this.e  = e;
	}
	@Override
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		Color color = colorList[e.getColor()];
		bookMark = new Polygon();
		bookMark.addPoint(0, 0);
		bookMark.addPoint(0, getHeight());
		bookMark.addPoint(getWidth() - getHeight(), getHeight());
		bookMark.addPoint(getWidth(), getHeight()/ 2);
		bookMark.addPoint(getWidth() - getHeight(), 0);
		g2.draw(bookMark);
		g2.setColor(color);
		g2.fill(bookMark);
		super.paintComponent(g);
	}
	
}
