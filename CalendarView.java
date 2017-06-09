import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import java.util.Hashtable;
import javax.swing.*;
/**
 * 
 * @author richard
 */
public class CalendarView
{
	public Model m;
	public JFrame calendarFrame;
	
	public CalendarView()
	{
		m = new Model();
		calendarFrame = new JFrame("Calendar (Month View)");
		calendarFrame.setSize(1400, 900);
	}
	
	public void paintWeekView()
	{
		calendarFrame = new JFrame("Calendar (Week View)");
		calendarFrame.setVisible(true);
		calendarFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
	}
	public void paintMonthView()
	{		
		String[] months = {"January", "February", "March", "April","May", "June", "July", "August", "September", "October", "November", "December"};
		Font Sans18Bold = new Font("SansSerif", Font.BOLD, 18);
		
		JPanel weekDayPanel = new JPanel();
		JPanel monthAndYearPanel = new JPanel();
		JPanel daysPanel = new JPanel();
		daysPanel = paintDays();
		JPanel buttonPanel = new JPanel();
		
		
		daysPanel.setLayout(new GridLayout(0, 7));
		int intRecentYear = m.getMovedAroundCal().get(Calendar.YEAR);
		String strRecentYear = String.valueOf(intRecentYear);
		int intRecentMonth = m.getMovedAroundCal().get(Calendar.MONTH);
		String strRecentMonth = months[intRecentMonth];
		JLabel monthAndYearLabel = new JLabel(strRecentYear + "  " + strRecentMonth);
		monthAndYearLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
		JButton lastMonthButton = new JButton(new ImageIcon(new ImageIcon(this.getClass().getResource("/last.png")).getImage()));
		lastMonthButton.setOpaque(false);
		lastMonthButton.setContentAreaFilled(false);
		lastMonthButton.setBorderPainted(false);
		lastMonthButton.setRolloverEnabled(true);
		JButton nextMonthButton = new JButton(new ImageIcon(new ImageIcon(this.getClass().getResource("/next.png")).getImage()));
		nextMonthButton.setOpaque(false);
		nextMonthButton.setContentAreaFilled(false);
		nextMonthButton.setBorderPainted(false);
		nextMonthButton.setRolloverEnabled(true);
		lastMonthButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				m.prevMonth();
				calendarFrame.getContentPane().removeAll();
				paintMonthView();
			}
		});
		
		nextMonthButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				m.nextMonth();
				calendarFrame.getContentPane().removeAll();
				paintMonthView();
			}
		});
		monthAndYearPanel.setLayout(new BorderLayout());
		monthAndYearPanel.add(monthAndYearLabel,BorderLayout.CENTER);
		monthAndYearPanel.add(lastMonthButton,BorderLayout.WEST);
		monthAndYearPanel.add(nextMonthButton,BorderLayout.EAST);

		String[] weekDay = {"Sunday", "Monday", "Tuesday", "Wednesday",  "Thursday", "     Friday" , "        Saturday"};
		for(int i = 0; i < weekDay.length; i++)
		{
			JLabel weekDayLabel = new JLabel(weekDay[i]);
			FlowLayout fl = new FlowLayout();
			fl.setAlignment(FlowLayout.TRAILING);
			fl.setHgap(100);
			weekDayPanel.setLayout(fl);
			weekDayLabel.setFont(Sans18Bold);
			weekDayPanel.add(weekDayLabel);
		}
				
		Font buttonFont = new Font("SansSerif", Font.BOLD, 16);
		
		// Make "Create" button and connect it to functional "create" method
		JButton createButton = new JButton("Create");
		createButton.setForeground(Color.WHITE);
		createButton.setBackground(new Color(30,144,255));
		createButton.setFont(buttonFont);;
		createButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				createEventBox();
			}
		});
		buttonPanel.add(createButton);
		Box box = Box.createVerticalBox();
		box.add(monthAndYearPanel);
		box.add(weekDayPanel);
		
		
		calendarFrame.add(box, BorderLayout.NORTH);
		calendarFrame.add(daysPanel, BorderLayout.CENTER);
		calendarFrame.add(buttonPanel, BorderLayout.SOUTH);
		calendarFrame.setLocationRelativeTo(null);
		calendarFrame.setVisible(true);
		calendarFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public JPanel paintDays()
	{
		JPanel daysPanel = new JPanel();
		Color[] colorList = {new Color(220,20,60), new Color(65,105,225), new Color(0,206,209), new Color(148,0,211), new Color(255,140,0)};
		int firstWeekDay = m.getMovedAroundCal().get(Calendar.DAY_OF_WEEK);
		int daysInMonth = m.getMovedAroundCal().getActualMaximum(Calendar.DAY_OF_MONTH);
		System.out.println(firstWeekDay);
		
		//Java Consider if first day of week is Saturday, its value will be 1.
		//So it needs to be changed to 8 in order to make this algorithm works.
		if(firstWeekDay == 1)
		{
			firstWeekDay = 8;
		}
		int day = 1;
		while(day <= daysInMonth)
		{
			//print empty button to figure out what weekday it should start 
			if(firstWeekDay - 2 > 0)
			{
				firstWeekDay--;
				JButton b0 = new JButton("");
				b0.setOpaque(false);
				b0.setContentAreaFilled(false);
				b0.setBorderPainted(false);
				b0.setFont(new Font("SansSerif", Font.BOLD, 18));
				daysPanel.add(b0);
			}
			else
			{
				JButton dayButton = new JButton(String.valueOf(day));
				dayButton.setOpaque(false);
				dayButton.setContentAreaFilled(false);
				if(day == m.getLocalCal().get(Calendar.DAY_OF_MONTH) && m.getLocalCal().get(Calendar.MONTH) == m.getMovedAroundCal().get(Calendar.MONTH) &&m.getLocalCal().get(Calendar.YEAR) == m.getMovedAroundCal().get(Calendar.YEAR))
				{
					dayButton.setContentAreaFilled(true);
					dayButton.setOpaque(true);
					Color lightBlue = new Color(100,149,237);
					dayButton.setBackground(lightBlue);
					dayButton.setForeground(Color.WHITE);
				}
				
				int numOfAllEvents = m.getDaysArr().size();
				if(numOfAllEvents > 0)
				{
					 Box box = Box.createVerticalBox();
					for(int i = 0; i < numOfAllEvents; i++)
					{
						if(day == m.getDaysArr().get(i).getDay())
						{
							for(int j = 0; j < m.getDaysArr().get(i).getEventsArr().size(); j++)
							{
								JLabel eventPreview = new JLabel(m.getDaysArr().get(i).getEventsArr().get(j).getDescription());
								eventPreview.setOpaque(true);
								eventPreview.setBackground(colorList[m.getDaysArr().get(i).getEventsArr().get(j).getColor()]);
								eventPreview.setForeground(Color.WHITE);
								eventPreview.setFont(new Font("SansSerif", Font.BOLD, 16));
								box.add(eventPreview);
								dayButton.add(box);
							}
						}
					}
				}
				dayButton.setFont(new Font("SansSerif", Font.BOLD, 18));
				dayButton.setHorizontalAlignment(SwingConstants.LEFT);
				dayButton.setVerticalAlignment(SwingConstants.TOP);
				daysPanel.add(dayButton);
				day++;
			}
		}	
		return daysPanel;
	}
	
	
	public void createEventBox()
	{
	Hashtable<String, Integer> monthList = makeMonthList();
	JFrame eventFrame = new JFrame("Event Box");
	eventFrame.setSize(600, 450);
	
	Font labelFont = new Font("SanSerif", Font.BOLD, 20);
	Font contentFont = new Font("SanSerif", Font.BOLD, 16);
	JPanel descPanel = new JPanel();
	JPanel datePanel = new JPanel();
	JPanel startTimePanel = new JPanel();
	JPanel endTimePanel = new JPanel();
	JPanel colorPanel = new JPanel();
	
	//implement box layout
	Box box = Box.createVerticalBox();
	
	
	JLabel descLabel = new JLabel("Description: ");
	descLabel.setFont(labelFont);
	
	//add labels and set its fonts.
	JLabel dateLabel = new JLabel("Date: ");
	JLabel startTimeLabel = new JLabel("Start Time:");
	JLabel endTimeLabel = new JLabel("End Time: ");
	dateLabel.setFont(labelFont);
	startTimeLabel.setFont(labelFont);
	endTimeLabel.setFont(labelFont);
	JTextField descText = new JTextField(20);
	descText.setFont(new Font("SansSerif", Font.PLAIN, 16));
	JLabel colorLabel = new JLabel("Color: ");
	colorLabel.setFont(labelFont);
	
	
	// add components in description section and put it into the top of the window
	descPanel.add(descLabel);
	descPanel.add(descText);
	box.add(descPanel);
	
	//add components in date section
	JComboBox yearBox = createYearBox();
	JComboBox monthBox = createMonthBox();
	JComboBox dayBox = new JComboBox();
	datePanel.add(dateLabel);
	datePanel.add(yearBox);
	datePanel.add(monthBox);
	monthBox.setSelectedIndex(m.getLocalCal().get(Calendar.MONTH));
	Calendar temp = Calendar.getInstance();
	temp.set(Calendar.MONTH, monthBox.getSelectedIndex() + 1);
	for(int i = 0; i < temp.getActualMaximum(Calendar.DAY_OF_MONTH); i++)
		dayBox.addItem(i);
	
	dayBox.setFont(contentFont);
	dayBox.setSelectedIndex(m.getLocalCal().get(Calendar.DAY_OF_MONTH));
	datePanel.add(dayBox);
	
	// add components in start Date area and put it below description section (Not sure if it's needed but leave it there for future purpose)
//	startDatePanel.add(startDateLabel);
//	startDatePanel.add(createYearBox());
//	JComboBox startMonthBox = createMonthBox();
//	startDatePanel.add(startMonthBox);
//	JComboBox startDayBox = createDayBox();
//	startDatePanel.add(startDayBox);
	
//	 add components in end Date area and put it below start date section
//	endDatePanel.add(endDateLabel);
//	endDatePanel.add(createYearBox());
//	JComboBox endMonthBox = createMonthBox();
//	endDatePanel.add(endMonthBox);	
//	JComboBox endDayBox = createDayBox();
//	endDatePanel.add(endDayBox);
	
	// In start day section, adjust the maximum of the days according to the input from month section
	monthBox.addActionListener(new ActionListener()
	{
		public void actionPerformed(ActionEvent e) 
		{
			String selectedMonth = (String) monthBox.getSelectedItem(); // read input from the month section
			Calendar c = m.getMovedAroundCal();
			c.set(Calendar.MONTH, monthList.get(selectedMonth) - 1);
			int maxDayOfMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
			dayBox.removeAllItems();
			for(int i = 1; i <= maxDayOfMonth; i++)
			{
				dayBox.addItem(i);
			}
			datePanel.repaint();
		}		
	});
	
	// In end day section, adjust the maximum of the days according to the input from month section
//	endMonthBox.addActionListener(new ActionListener()
//	{
//		public void actionPerformed(ActionEvent e) 
//		{
//			String selectedMonth = (String) endMonthBox.getSelectedItem(); // read input from the month section
//			Calendar c = Calendar.getInstance();
//			c.set(Calendar.MONTH, monthList.get(selectedMonth) - 1);
//			int maxDayOfMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
//			endDayBox.removeAllItems();
//			for(int i = 1; i <= maxDayOfMonth; i++)
//			{
//				endDayBox.addItem(i);
//			}
//			endDatePanel.repaint();
//		}		
//	});
	
	//Create combo box for start time and end time
	startTimePanel.add(startTimeLabel);
	JComboBox startTime = createTimeBox();
	startTimePanel.add(startTime);
	endTimePanel.add(endTimeLabel);
	JComboBox endTime = createTimeBox();
	endTimePanel.add(endTime);
	
	//create radio button for AM and PM options
	JRadioButton startTimeAM = new JRadioButton("A.M.");
	startTimeAM.setFont(contentFont);
	JRadioButton startTimePM = new JRadioButton("P.M.");
	startTimePM.setFont(contentFont);
	JRadioButton endTimeAM = new JRadioButton("A.M.");
	endTimeAM.setFont(contentFont);
	JRadioButton endTimePM = new JRadioButton("P.M.");
	endTimePM.setFont(contentFont);
	
	//adding radio button in a group
	ButtonGroup startGroup = new ButtonGroup();
	startGroup.add(startTimeAM);
	startGroup.add(startTimePM);
	
	ButtonGroup endGroup = new ButtonGroup();
	endGroup.add(endTimeAM);
	endGroup.add(endTimePM);
	
	startTimePanel.add(startTimeAM);
	startTimePanel.add(startTimePM);
	endTimePanel.add(endTimeAM);
	endTimePanel.add(endTimePM);
	
	//Make a checkbox to check if an event lasts all day
	JCheckBox allDayCheck = new JCheckBox("All Day");
	allDayCheck.setFont(contentFont);
	datePanel.add(allDayCheck);
	allDayCheck.addItemListener(new ItemListener()
	{
		public void itemStateChanged(ItemEvent arg0) 
		{
			startTime.setEnabled(!allDayCheck.isSelected());
			endTime.setEnabled(!allDayCheck.isSelected());
			startTimeAM.setEnabled(!allDayCheck.isSelected());
			startTimePM.setEnabled(!allDayCheck.isSelected());
			endTimeAM.setEnabled(!allDayCheck.isSelected());
			endTimePM.setEnabled(!allDayCheck.isSelected());
		}		
	});
	
	
	//  add components in color area and put it below end date section
	colorPanel.add(colorLabel);
	JComboBox colorBox = createColorBox();
	colorPanel.add(colorBox);
	
	
	//create save button and make it functional
	JButton saveButton = new JButton("Save");
	saveButton.setBackground(new Color(50,205,50));
	saveButton.setForeground(Color.WHITE);
	saveButton.setFont(contentFont);
	saveButton.addActionListener(new ActionListener()
	{
		public void actionPerformed(ActionEvent event) 
		{
			//check if there is any error like leaving description blank or didn't choose between AM and PM for start time or end time
			if(descText.getText().equals("") || !startTimeAM.isSelected() && !startTimePM.isSelected() && !allDayCheck.isSelected() || !endTimeAM.isSelected() && !endTimePM.isSelected() && !allDayCheck.isSelected())
			{
				//Construct a basic error message window
				JFrame errorFrame = new JFrame("Error");
				errorFrame.setSize(450,150);
				JPanel errorPanel = new JPanel();
					
				JLabel imageLabel = new JLabel();
				Image img = new ImageIcon(this.getClass().getResource("/error.png")).getImage();
				imageLabel.setIcon(new ImageIcon(img));
				errorPanel.add(imageLabel);
				JLabel errorMessage = new JLabel("");
				
				//check which error it is to show different messages
				if(descText.getText().equals(""))
				{
					errorMessage.setText("Description cannot be left blank.");
				}
				
				else if(!startTimeAM.isSelected() && !startTimePM.isSelected())
				{
					errorMessage.setText("Start time should be either A.M. or P.M.");
					errorFrame.setSize(550, 150);
				}
				
				else if(!endTimeAM.isSelected() && !endTimePM.isSelected())
				{
					errorMessage.setText("End time should be either A.M. or P.M.");
					errorFrame.setSize(550, 150);
				}
				
				errorMessage.setFont(new Font("SanSerif", Font.BOLD, 24));
				JButton OkButton = new JButton("OK");
				OkButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e) 
					{
						errorFrame.dispose();			
					}
			});
				errorPanel.add(errorMessage);
				errorFrame.add(errorPanel);
				errorPanel.add(OkButton);
				errorFrame.setLocationRelativeTo(eventFrame);
				errorFrame.setVisible(true);
				return;
			}
			
			//read inputs and convert them into parameters that the Event constructor can take
			String description = descText.getText();
			int year = (int) yearBox.getSelectedItem();
			String strMonth = (String) monthBox.getSelectedItem();
			int intMonth = monthList.get(strMonth);
			int day = (int) dayBox.getSelectedItem();
			String strStartTime = (String) startTime.getSelectedItem();
			int intStartTime = Integer.valueOf(strStartTime.substring(0, 2)) * 100 + Integer.valueOf(strStartTime.substring(3, 5));
			String strEndTime = (String) endTime.getSelectedItem();
			int intEndTime = Integer.valueOf(strEndTime.substring(0, 2)) * 100 + Integer.valueOf(strEndTime.substring(3, 5));
			int color = (int) colorBox.getSelectedIndex();
			
//			if(strStartTime.substring(0,2).equals("12") && startTimeAM.isSelected())
//				strStartTime = "00" + strStartTime.substring(2,5);
//			else if(strEndTime.substring(0,2).equals("12") && endTimeAM.isSelected())
//				strEndTime = "00" + strStartTime.substring(2,5);
//			if(startTimePM.isSelected())
//			{
//				int intStartTime = Integer.valueOf(strStartTime.substring(0, 2));
//				intStartTime = intStartTime + 12;
//				strStartTime = String.valueOf(intStartTime) + strStartTime.substring(2,5);
//			}
//			
//			if(endTimePM.isSelected())
//			{
//				int intEndTime = Integer.valueOf(strEndTime.substring(0, 2));
//				intEndTime = intEndTime + 12;
//				strEndTime = String.valueOf(intEndTime) + strEndTime.substring(2,5);
//			}

			
			String startTod = "";
			if(startTimeAM.isSelected())
				startTod = startTimeAM.getText();
			else
				startTod = startTimePM.getText();
			
			String endTod = "";
			if(endTimeAM.isSelected())
				endTod = endTimeAM.getText();
			else
				endTod = endTimePM.getText();
						
//			System.out.println(intMonth + "/" + day + "/" + year + "  " + intStartTime + " " + startTod + " - " + intEndTime + " " + endTod + "    " + description);
			m.addEvent(year, intMonth, day, intStartTime,intEndTime, description, startTod, endTod, color);
			
			// Repaint the calendar after an event is added
			eventFrame.dispose();
			calendarFrame.getContentPane().removeAll();
			paintMonthView();
			
		System.out.println("There is(are) " + m.getDaysArr().size() + " events in database(model): ");
		for(int i  = 0; i < m.getDaysArr().size(); i++)
		{
			for(int j = 0; j < m.getDaysArr().get(i).getEventsArr().size(); j++)
			System.out.print(m.getDaysArr().get(i) + "   ");
			m.getDaysArr().get(i).returnEventList();
		}
		
		}
		
	});
	
	//create cancel button and modified its look
	JButton cancelButton = new JButton("Cancel");
	cancelButton.setBackground(new Color(255,60,60));
	cancelButton.setForeground(Color.WHITE);
	cancelButton.setFont(contentFont);
	cancelButton.addActionListener(new ActionListener()
	{
		public void actionPerformed(ActionEvent event) 
		{
			eventFrame.dispose();
		}
	});
	
	box.add(datePanel);
	
	box.add(startTimePanel);
	
	box.add(endTimePanel);

	box.add(colorPanel);
	
	// add buttons in the bottom of the event box
	JPanel buttonPanel = new JPanel();
	buttonPanel.setLayout(new FlowLayout());
	buttonPanel.add(saveButton);
	buttonPanel.add(cancelButton);
	box.add(buttonPanel);
	
	eventFrame.add(box);
	eventFrame.setLocationRelativeTo(eventFrame);
	eventFrame.setVisible(true);
	}
	
	/**
	 * Create a list of month in a form of Hashtable
	 * @return the hashtable that contain String names of month and the matching number
	 */
	public static Hashtable<String,Integer> makeMonthList()
	{
		Hashtable<String,Integer> ht = new Hashtable<>();
		ht.put("January", 1);
		ht.put("February", 2);
		ht.put("March", 3);
		ht.put("April", 4);
		ht.put("May", 5);
		ht.put("June", 6);
		ht.put("July", 7);
		ht.put("August", 8);
		ht.put("September", 9);
		ht.put("October", 10);
		ht.put("November", 11);
		ht.put("December", 12);
		return ht;
	}
	
	/**
	 * creates drop down menu(combo box) for year
	 * @return the ComboBox that identifies year
	 */
	public static JComboBox createYearBox()
	{
		JComboBox yearBox = new JComboBox();
		yearBox.setFont(new Font("Sanserif", Font.BOLD, 16));
		int yearToAdd = 2017;
		for(int i = 0; i < 20; i++)
		{
			yearBox.addItem(yearToAdd);
			yearToAdd++;
		}		
		return yearBox;
	}
/**
* create drop down menu(combo box) for month
* @return the combo box that has all the names of month
*/
public static JComboBox createMonthBox()
{
	JComboBox monthBox = new JComboBox();
	monthBox.setFont(new Font("Sanserif", Font.BOLD, 16));
	String months[] = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	for(int i = 0; i < 12; i++)
	{
		monthBox.addItem(months[i]);
	}

	return monthBox;
}

/**
 * Color selection where provide users to be able to select their color of event text to differentiate the types of events.
 * @return the Combo box for color selection
 */
public static JComboBox createColorBox()
{
	JComboBox colorBox = new JComboBox();
	colorBox.setFont(new Font("Sanserif", Font.BOLD, 16));
	colorBox.addItem("Red");
	colorBox.addItem("Blue");
	colorBox.addItem("Green");
	colorBox.addItem("Purple");
	colorBox.addItem("Orange");
	return colorBox;
}

/**
 * Create Time combo box use of start time and end time
 * @return the combo box has differnet time period from hour 1 - 12 with minutes 00, 15, 30 and 45
 */
public static JComboBox createTimeBox()
{
	JComboBox timeBox = new JComboBox();
	String[] minutesList = {"00","15","30","45"};
	timeBox.setFont(new Font("Sanserif", Font.BOLD, 16));
	for(int i = 0; i <= 11; i++)
	{
		for(int j = 0; j < 4; j++)
		{
			if(i == 0)
			{
				timeBox.addItem("12:" + minutesList[j]);
			}
			else
			{
				if(i < 10)
					timeBox.addItem("0" + i + ":" + minutesList[j]);
				else
					timeBox.addItem(i + ":" + minutesList[j]);
			}
		}
	}
	return timeBox;
}
}

