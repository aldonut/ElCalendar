import java.awt.*;
import java.awt.geom.Rectangle2D;

import javax.swing.*;

public class Block extends JButton{
	
	private boolean deletion;
	private int x;
	private int y;
	private int width;
	private int height;
	private String descText;
	private String timeText;
	private Color color;
	
	public Block(boolean deletion, int x, int y, int width, int height, String descText, String timeText, Color color)
	{
		this.deletion = deletion;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.descText = descText;
		this.color = color;
		this.timeText = timeText;
		this.setOpaque(false);
		this.setContentAreaFilled(false);
		this.setBorderPainted(false);
		this.setAlignmentX(Component.CENTER_ALIGNMENT);
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		if(!deletion)
		{
			super.paintComponent(g);
			g.setColor(color);
			Color fadingColor = new Color(255,250,205);
			g2.setPaint(new GradientPaint(0, 0, color, getWidth(), getHeight(), fadingColor));
			g.fillRoundRect(x, y, width, height, 100, 100 );
			g.setColor(Color.WHITE);
			g.setFont(new Font("Comic Sans MS", Font.BOLD, 46));
			g.drawString(descText, x + width/4 ,40 + height/2);
			g.setFont(new Font("Comic Sans MS", Font.PLAIN, 32));
			g.drawString(timeText, x + 20,y + 30 );
		}
		
		else
		{
			super.paintComponent(g);
			g.setColor(Color.GRAY);
			g.fillRoundRect(x, y, width, height, 100, 100 );
			g.setColor(Color.WHITE);
			g.setFont(new Font("Comic Sans MS", Font.BOLD, 46));
			g.drawString(descText, x + width/4 ,40 + height/2);
			g.setFont(new Font("Comic Sans MS", Font.PLAIN, 32));
			g.drawString(timeText, x + 20,y + 30 );
		}
	}
	
	public boolean getDeleteCondition()
	{
		return deletion;
	}
	
	public String getDesc()
	{
		return descText;
	}
	
	public String getStrTime()
	{
		return timeText;
	}
	
	public Color getColor()
	{
		return color;
	}
}
