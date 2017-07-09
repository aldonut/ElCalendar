
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.*;

public class CalendarTester {
public static void main(String[] args) 
{
	CalendarView cv = new CalendarView();
	cv.themeFrame();
	Event e = new Event(2017, 6, 30, 1200, 1200, "sdfsd", "P.M.", "P.M.",0);
	DB.delete(e);
//	cv.paintDayView();
//	cv.paintAgendaView();
//	cv.paintMonthView();
//	cv.createEventBox();
}
}
