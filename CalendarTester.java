
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.*;
import java.util.ArrayList;

public class CalendarTester {
public static void main(String[] args) 
{
	//CalendarView cv = new CalendarView();
	//cv.themeFrame();
	
	ArrayList<Event> all = new ArrayList(DB.loadAll());

	for(int i = 0; i < all.size(); i++)
	{
		System.out.println(all.get(i).toString());
	}
	
//	cv.paintDayView();
//	cv.paintAgendaView();
//	cv.paintMonthView();
//	cv.createEventBox();
}
}
