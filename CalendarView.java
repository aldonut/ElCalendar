import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import java.util.Hashtable;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
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
		m.addEvent(2017, 7, 4, 1200, 1145, "Independence Day", "A.M.", "P.M.", 0);
		m.addEvent(2017, 11, 23, 1200, 1145, "Thanksgiving", "A.M.", "P.M.", 0);
		m.addEvent(2017, 12, 25, 1200, 1145, "Christmas", "A.M.", "P.M.", 0);
		calendarFrame = new JFrame("Calendar (Month View)");
		calendarFrame.setSize(1400, 900);
		
		
		// Getting all available names for fonts in Java library
//		String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
//
//		for (int i = 0; i < fonts.length; i++) {
//		    System.out.println(fonts[i]);
//		}
	}
	
	public void paintDayView()
	{
		String[] months = {"January", "February", "March", "April","May", "June", "July", "August", "September", "October", "November", "December"};
		
		JPanel menuPanel = new JPanel();
		JPanel lastAndNextPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		
		//Create the label of recent day
		String recentDay = months[m.getMovedAroundCal().get(Calendar.MONTH)] + "  " + m.getMovedAroundCal().get(Calendar.DAY_OF_MONTH);
		JLabel dayLabel = new JLabel(recentDay);
		dayLabel.setFont(new Font("Elephant", Font.BOLD,36));
		dayLabel.setHorizontalAlignment(JLabel.CENTER);
		
		//Create last day and next day button so users can go over every day
		JButton lastDayButton = new JButton(new ImageIcon(new ImageIcon(this.getClass().getResource("/last.png")).getImage()));
		lastDayButton.setOpaque(false);
		lastDayButton.setContentAreaFilled(false);
		lastDayButton.setBorderPainted(false);
		lastDayButton.setRolloverEnabled(true);
		JButton nextDayButton = new JButton(new ImageIcon(new ImageIcon(this.getClass().getResource("/next.png")).getImage()));
		nextDayButton.setOpaque(false);
		nextDayButton.setContentAreaFilled(false);
		nextDayButton.setBorderPainted(false);
		nextDayButton.setRolloverEnabled(true);
		
		lastDayButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				m.prevDay();;
				calendarFrame.getContentPane().removeAll();
				paintDayView();
			}
		});
		
		nextDayButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				m.nextDay();
				calendarFrame.getContentPane().removeAll();
				paintDayView();
			}
		});
		
		// Create tabs that are functional and useful for users in day view, including create, switching back to month view and switching back to today's date
		//below is implementation of today
		Font tabFont = new Font("Comic Sans MS", Font.BOLD, 20);
		Image img = new ImageIcon(this.getClass().getResource("/today.png")).getImage();
		JButton todayButton = new JButton("Today", new ImageIcon(img));
		Dimension d = new Dimension(150,40);
		todayButton.setPreferredSize(d);
		todayButton.setFont(tabFont);
		todayButton.setBackground(Color.GRAY);
		todayButton.setForeground(Color.WHITE);
		todayButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				m.getMovedAroundCal().setTime(Calendar.getInstance().getTime());
				calendarFrame.getContentPane().removeAll();
				paintDayView();
			}
		});
		
		// below is implementation for create
		Image createImg = new ImageIcon(this.getClass().getResource("/create.png")).getImage();
		JButton createButton = new JButton("Create", new ImageIcon(createImg));
		createButton.setForeground(Color.WHITE);
		createButton.setBackground(Color.GRAY);
		createButton.setFont(tabFont);
		createButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				createEventBox();
			}
		});
				
		//below is implementation for switching to month view
		Image monthViewImg = new ImageIcon(getClass().getResource("/monthView.png")).getImage();
		JButton monthView = new JButton("Month", new ImageIcon(monthViewImg));
		monthView.setPreferredSize(d);
		monthView.setFont(tabFont);
		monthView.setBackground(Color.GRAY);
		monthView.setForeground(Color.WHITE);
		monthView.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				calendarFrame.getContentPane().removeAll();
				paintMonthView();
				paintDays();
			}
		});
		
		// adding all components in corresponding panel
		//lastAndNextPanel holds last day and next day buttons
		//buttonPanel holds today button, month view button and create button
		// menuPanel holds lastAndNextPanel and buttonPanel
		lastAndNextPanel.add(lastDayButton);
		lastAndNextPanel.add(nextDayButton);
		buttonPanel.add(todayButton);
		buttonPanel.add(monthView);
		buttonPanel.add(createButton);
		menuPanel.setLayout(new BorderLayout());
		menuPanel.add(lastAndNextPanel, BorderLayout.WEST);
		menuPanel.add(dayLabel, BorderLayout.NORTH);
		menuPanel.add(buttonPanel, BorderLayout.EAST);
		
		//create a timetable for day view
		JTable timeTable = new JTable();
		DefaultTableModel model = new DefaultTableModel();
		
		// add column title to the table
		String[] title = {"Time", "Event"};
		model.setColumnIdentifiers(title);
		JTableHeader header = timeTable.getTableHeader();
		header.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
		timeTable.setTableHeader(header);
		timeTable.setModel(model);
		timeTable.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));
		timeTable.setRowHeight(40);
		timeTable.setEnabled(false);
		
		//Set texts in each table cell align on center and make every cell transparent
		timeTable.setOpaque(false);
		((DefaultTableCellRenderer) timeTable.getDefaultRenderer(Object.class)).setOpaque(false);
		((DefaultTableCellRenderer) timeTable.getDefaultRenderer(Object.class)).setHorizontalAlignment(SwingConstants.CENTER);
		
		//Adjust the table between column
		//Note: recommended to have all width sum to eqaul to the width of frame
		timeTable.getColumnModel().getColumn(0).setPreferredWidth(200);
		timeTable.getColumnModel().getColumn(1).setPreferredWidth(1000);
		
		//filling time table with every period of time
		Object row[] = new Object[2];
		for(int i = 0; i <= 23; i++)
		{
			String timeToPrint = "";
			if(i > 11)
			{
				if(i == 0)
				{
					timeToPrint = "12:00 P.M.";
				}
				else
				{
					if(i < 22)
						timeToPrint = "0" + (i - 12) + ":00 P.M.";
					else
						timeToPrint = (i - 12) + ":00 P.M.";
				}
			}
			else{
				if(i == 0)
				{
					timeToPrint = "12:00 A.M.";
				}
				else
				{
					if(i < 10)
						timeToPrint = "0" + i + ":00 A.M.";
					else
						timeToPrint = i + ":00 A.M.";
				}}
				row[0] = timeToPrint;
				row[1] = "";
				model.addRow(row);
		}
		
		
		JScrollPane scrollPane = new JScrollPane(timeTable);
		calendarFrame.setLayout(new BorderLayout());
		calendarFrame.add(menuPanel, BorderLayout.NORTH);
		calendarFrame.add(scrollPane, BorderLayout.CENTER);
		calendarFrame.setVisible(true);
		calendarFrame.setLocationRelativeTo(null);
		calendarFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void paintAgendaView()
	{
		JFrame agendaFrame = new JFrame("Calendar (Agenda View)");
		agendaFrame.setSize(800, 400);
		agendaFrame.setVisible(true);
		agendaFrame.setLocationRelativeTo(null);
		
		JTable agenda = new JTable();
		String[] category = {"Date", "Time", "Name"};
		
		//Adding header to table
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(category);
		JTableHeader header = agenda.getTableHeader();
		header.setFont(new Font("SansSerif", Font.BOLD, 22));
		agenda.setTableHeader(header);
			
		agenda.setModel(model);
		
		//Make table cell background transparent
		((DefaultTableCellRenderer)agenda.getDefaultRenderer(Object.class)).setOpaque(false);
		agenda.setOpaque(false);
		
		//Make text in table cell align center
		((DefaultTableCellRenderer)agenda.getDefaultRenderer(Object.class)).setHorizontalAlignment(JLabel.CENTER);
		
		//Create a table without grid
		agenda.setShowGrid(false);
		
		//Not allow people to edit the text in each cell
		agenda.setEnabled(false);
		
		
		agenda.setFont(new Font("SansSerif", Font.BOLD, 18));
		agenda.setRowHeight(30);

		// Go through every event in model and print out every event in order of its date, time and name.
		if(m.getDaysArr().size() > 0)
		{
			for(int i = 0; i < m.getDaysArr().size(); i++)
			{
				for(int j = 0; j < m.getDaysArr().get(i).getEventsArr().size(); j++)
				{
					Object[] row = new Object[3];
					Event testEvent = m.getDaysArr().get(i).getEventsArr().get(j);
					System.out.println(testEvent);	
					String time = "";
					String startMinute = String.valueOf(testEvent.getStartTime() % 100);
					String endMinute = String.valueOf(testEvent.getEndTime() % 100);
					
					//Converting from int time to String time
					// Checking if there is time end with :00
					if(startMinute.equals("0") && endMinute.equals("0"))
						time = testEvent.getStartTime()/100 + ":" +testEvent.getStartTime() % 100 + "0 " + testEvent.getStartTod() + " - " + testEvent.getEndTime()/100 + ":" + testEvent.getEndTime() % 100 + "0 " + testEvent.getendTod();
					else if(startMinute.equals("0") && !endMinute.equals("0"))
						time = testEvent.getStartTime()/100 + ":" +testEvent.getStartTime() % 100 + "0 " + testEvent.getStartTod() + " - " + testEvent.getEndTime()/100 + ":" + testEvent.getEndTime() % 100 + " " + testEvent.getendTod();
					else if(!startMinute.equals("0") && !endMinute.equals("0"))
						time = testEvent.getStartTime()/100 + ":" +testEvent.getStartTime() % 100 + testEvent.getStartTod() + " - " + testEvent.getEndTime()/100 + ":" + testEvent.getEndTime() % 100 + "0 " + testEvent.getendTod();
					else
						time = testEvent.getStartTime()/100 + ":" +testEvent.getStartTime() % 100 + " " + testEvent.getStartTod() + " - " + testEvent.getEndTime() + " " + testEvent.getendTod();
					
					//Create a new row with new data
					row[0] = testEvent.getStrDate();
					row[1] = time;
					row[2] = testEvent.getDescription();
					model.addRow(row);
				}
			}
		}
		
		//Attach scroll on table
		JScrollPane scrollPane = new JScrollPane(agenda);
		agendaFrame.add(scrollPane);		
	}
	public void paintMonthView()
	{		
		String[] months = {"January", "February", "March", "April","May", "June", "July", "August", "September", "October", "November", "December"};
		Font Sans18Bold = new Font("Elephant", Font.PLAIN, 20);
		
		JPanel weekDayPanel = new JPanel();
		JPanel monthAndYearPanel = new JPanel();
		JPanel lastAndNextPanel = new JPanel();
		JPanel tabPanel = new JPanel();
		JPanel daysPanel = new JPanel();
		daysPanel = paintDays();
		JPanel buttonPanel = new JPanel();
		
		
		daysPanel.setLayout(new GridLayout(0, 7));
		int intRecentYear = m.getMovedAroundCal().get(Calendar.YEAR);
		String strRecentYear = String.valueOf(intRecentYear);
		int intRecentMonth = m.getMovedAroundCal().get(Calendar.MONTH);
		String strRecentMonth = months[intRecentMonth];
		JLabel monthAndYearLabel = new JLabel(strRecentYear + "  " + strRecentMonth);
		monthAndYearLabel.setFont(new Font("Elephant", Font.BOLD, 36));
		
		//Make last and next Month button with images
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
		

		//Create today button which brings users to today's date as soon as it is clicked
		Font tabFont = new Font("Comic Sans MS", Font.BOLD, 20);
		Image img = new ImageIcon(this.getClass().getResource("/today.png")).getImage();
		JButton todayButton = new JButton("Today", new ImageIcon(img));
		Dimension d = new Dimension(140,40);
		todayButton.setPreferredSize(d);
		todayButton.setFont(tabFont);
		todayButton.setBackground(Color.GRAY);
		todayButton.setForeground(Color.WHITE);
		todayButton.addActionListener(new ActionListener()
		{
		public void actionPerformed(ActionEvent e) 
		{
			m.getMovedAroundCal().setTime(Calendar.getInstance().getTime());
			calendarFrame.getContentPane().removeAll();
			paintMonthView();
			paintDays();
		}
		});
		
		//Create Day button that can switch the window to day view
		Image dayViewImg = new ImageIcon(getClass().getResource("/dayView.png")).getImage();
		JButton dayView = new JButton("Day", new ImageIcon(dayViewImg));
		dayView.setPreferredSize(d);
		dayView.setFont(tabFont);
		dayView.setBackground(Color.GRAY);
		dayView.setForeground(Color.WHITE);
		dayView.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				calendarFrame.getContentPane().removeAll();
				paintDayView();
			}
		});
		
		//Create agenda button which makes users able to see their events in agenda form
		Image agendaImg = new ImageIcon(this.getClass().getResource("/agenda.png")).getImage();
		JButton agendaButton = new JButton("Agenda", new ImageIcon(agendaImg));
		agendaButton.setPreferredSize(d);
		agendaButton.setFont(tabFont);
		agendaButton.setBackground(Color.GRAY);
		agendaButton.setForeground(Color.WHITE);
		agendaButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				paintAgendaView();
			}
		});
		
		tabPanel.add(agendaButton);
		tabPanel.add(todayButton);
		tabPanel.add(dayView);
		
		lastAndNextPanel.add(lastMonthButton);
		lastAndNextPanel.add(nextMonthButton);

		//Arrange different components in monthAndYearPanel (The top panel of the frame)
		monthAndYearPanel.setLayout(new BorderLayout());
		monthAndYearLabel.setHorizontalAlignment(JLabel.CENTER);
		monthAndYearPanel.add(lastAndNextPanel,BorderLayout.WEST);
		monthAndYearPanel.add(monthAndYearLabel,BorderLayout.NORTH);
		monthAndYearPanel.add(tabPanel, BorderLayout.EAST);

		//Paint the weekDay of the calendar
		String[] weekDay = {"Sunday", "Monday", "Tuesday", "Wednesday",  "Thursday", "     Friday" , "        Saturday"};
		for(int i = 0; i < weekDay.length; i++)
		{
			JLabel weekDayLabel = new JLabel(weekDay[i]);
			FlowLayout fl = new FlowLayout();
			fl.setAlignment(FlowLayout.CENTER);
			fl.setHgap(90);
			weekDayPanel.setLayout(fl);
			weekDayLabel.setFont(Sans18Bold);
			weekDayPanel.add(weekDayLabel);
		}
				
		Font buttonFont = new Font("Comic Sans MS", Font.BOLD, 20);
		
		// Make "Create" button and connect it to functional "create" method
		Image createImg = new ImageIcon(this.getClass().getResource("/create.png")).getImage();
		JButton createButton = new JButton("Create", new ImageIcon(createImg));
		createButton.setForeground(Color.WHITE);
		createButton.setBackground(Color.GRAY);
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
		
		//Create a calendar object with same month and year as movedAroundCal. Then set the day to 1 so it can be checked what weekday is the  first day
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, m.getMovedAroundCal().get(Calendar.YEAR));
		c.set(Calendar.MONTH, m.getMovedAroundCal().get(Calendar.MONTH));
		c.set(Calendar.DAY_OF_MONTH, 1);
		int firstWeekDay = c.get(Calendar.DAY_OF_WEEK);
		int daysInMonth = m.getMovedAroundCal().getActualMaximum(Calendar.DAY_OF_MONTH);
		
		int day = 1;
		while(day <= daysInMonth)
		{
			//print empty button to figure out what weekday it should start 
			if(firstWeekDay - 1 > 0)
			{
				firstWeekDay--;
				JButton b0 = new JButton("");
				b0.setOpaque(false);
				b0.setContentAreaFilled(false);
				b0.setBorderPainted(false);
				daysPanel.add(b0);
			}
			else
			{
				JButton dayButton = new JButton(String.valueOf(day));
				boolean isIndependentDay = m.getMovedAroundCal().get(Calendar.MONTH) == 6 && day == 4;
				boolean isThanksGiving = m.getMovedAroundCal().get(Calendar.MONTH) == 10 && day == 23;
				boolean isChristmas = m.getMovedAroundCal().get(Calendar.MONTH) == 11 && day == 25;

				dayButton.setOpaque(false);
				dayButton.setContentAreaFilled(false);
				if(isIndependentDay)
				{
					Image img = new ImageIcon(this.getClass().getResource("/independence.png")).getImage();
					dayButton = new JButton(String.valueOf(day), new ImageIcon(img));
					dayButton.setVerticalTextPosition(SwingConstants.TOP);
					dayButton.setHorizontalTextPosition(SwingConstants.LEFT);
					dayButton.setContentAreaFilled(false);
				}
				
				if(isThanksGiving)
				{
					Image img = new ImageIcon(this.getClass().getResource("/turkey.png")).getImage();
					dayButton = new JButton(String.valueOf(day), new ImageIcon(img));
					dayButton.setVerticalTextPosition(SwingConstants.TOP);
					dayButton.setHorizontalTextPosition(SwingConstants.LEFT);
					dayButton.setContentAreaFilled(false);
				}
				
				if(isChristmas)
				{
					Image img = new ImageIcon(this.getClass().getResource("/Christmas.png")).getImage();
					dayButton = new JButton(String.valueOf(day), new ImageIcon(img));
					dayButton.setVerticalTextPosition(SwingConstants.TOP);
					dayButton.setHorizontalTextPosition(SwingConstants.LEFT);
					dayButton.setContentAreaFilled(false);
				}
				//Find and highlight today's date
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
						
						//Check year, month and day for every event to make it viewable in corresponding time
						// The reason for "+1" is that get(Calendar.MONTH) starts with index 0, which represents January
						if(day == m.getDaysArr().get(i).getDay() && m.getMovedAroundCal().get(Calendar.MONTH) + 1  == m.getDaysArr().get(i).getMonth() && m.getMovedAroundCal().get(Calendar.YEAR) == m.getDaysArr().get(i).getYear())
						{
//							System.out.println(m.getMovedAroundCal().get(Calendar.MONTH) + "=? " + m.getDaysArr().get(i).getMonth());
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
				dayButton.setFont(new Font("SansSerif", Font.BOLD, 20));
				dayButton.setHorizontalAlignment(SwingConstants.LEFT);
				dayButton.setVerticalAlignment(SwingConstants.TOP);
				daysPanel.add(dayButton);
				day++;
				dayButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e) 
					{
						m.getMovedAroundCal().set(Calendar.DAY_OF_MONTH, Integer.valueOf(e.getActionCommand()));
						calendarFrame.getContentPane().removeAll();
						paintDayView();
					}
				});
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
	Font contentFont = new Font("SansSerif", Font.BOLD, 16);
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
	monthBox.setSelectedIndex(m.getMovedAroundCal().get(Calendar.MONTH));
	Calendar temp = Calendar.getInstance();
	temp.set(Calendar.MONTH, monthBox.getSelectedIndex() + 1);
	for(int i = 0; i < temp.getActualMaximum(Calendar.DAY_OF_MONTH); i++)
		dayBox.addItem(i);
	dayBox.setFont(contentFont);
	dayBox.setSelectedIndex(m.getMovedAroundCal().get(Calendar.DAY_OF_MONTH));
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
	Font buttonFont = new Font("Comic Sans MS", Font.BOLD, 18);
	Image img = new ImageIcon(this.getClass().getResource("/save.png")).getImage();
	JButton saveButton = new JButton("Save", new ImageIcon(img));
	saveButton.setBackground(new Color(50,205,50));
	saveButton.setForeground(Color.WHITE);
	saveButton.setFont(buttonFont);
	saveButton.addActionListener(new ActionListener()
	{
		public void actionPerformed(ActionEvent event) 
		{		
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
			boolean blankDesc = descText.getText().equals("");
			boolean wrongStartTimeInput = !startTimeAM.isSelected() && !startTimePM.isSelected() && !allDayCheck.isSelected();
			boolean wrongEndTimeInput = !endTimeAM.isSelected() && !endTimePM.isSelected() && !allDayCheck.isSelected();
			boolean startLaterThanEnd = false;
			
			//Check if start time is later than end time
			if(intStartTime - 1200 < 100 && startTod.equals("A.M."))
				intStartTime = intStartTime - 1200;
			if(intEndTime - 1200 < 100 && endTod.equals("A.M."))
				intEndTime = intEndTime - 1200;
			if(startTod.equals("P.M."))
				if(intStartTime != 1200)
					intStartTime = intStartTime + 1200;
			if(endTod.equals("P.M."))
				if(intEndTime != 1200)
					intEndTime = intEndTime + 1200;
			startLaterThanEnd = intStartTime - intEndTime > 0;
			
			//check if there is any error like leaving description blank or didn't choose between AM and PM for start time or end time
			if(blankDesc || wrongStartTimeInput || wrongEndTimeInput || startLaterThanEnd)
			{
				//Construct a basic error message window
				JFrame errorFrame = new JFrame("Error");
				errorFrame.setSize(450,150);
				JPanel errorPanel = new JPanel();
					
				JLabel imageLabel = new JLabel();
				Image img = new ImageIcon(this.getClass().getResource("/errorIcon.png")).getImage();
				imageLabel.setIcon(new ImageIcon(img));
				errorPanel.add(imageLabel);
				JLabel errorMessage = new JLabel("");
				
				//check which error it is to show different messages
				if(blankDesc)
				{
					errorMessage.setText("Description cannot be left blank.");
				}
				
				else if(wrongStartTimeInput)
				{
					errorMessage.setText("Start time should be either A.M. or P.M.");
					errorFrame.setSize(550, 150);
				}
								
				else if(wrongEndTimeInput)
				{
					errorMessage.setText("End time should be either A.M. or P.M.");
					errorFrame.setSize(550, 150);
				}
				
				else if(startLaterThanEnd)
				{
					errorMessage.setText("Start time cannot be later than end time.");
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
	Image cancelImg = new ImageIcon(this.getClass().getResource("/cancel.png")).getImage();
	JButton cancelButton = new JButton("Cancel", new ImageIcon(cancelImg));
	cancelButton.setBackground(new Color(255,60,60));
	cancelButton.setForeground(Color.WHITE);
	cancelButton.setFont(buttonFont);
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

