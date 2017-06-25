
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
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
	public boolean deleting = false;
	private final Color[] colorList = {new Color(220,20,60), new Color(30,144,255), new Color(0,206,209), new Color(148,0,211), new Color(210,105,30)};
	private String theme;
	public CalendarView()
	{
		m = new Model();
		m.addEvent(2017, 7, 4, 1200, 1145, "Independence Day", "A.M.", "P.M.", 0);
		m.addEvent(2017, 11, 5, 1200, 1145, "Day Light Saving Ends", "A.M.", "P.M.", 0);
		m.addEvent(2017, 11, 23, 1200, 1145, "Thanksgiving", "A.M.", "P.M.", 0);
		m.addEvent(2017, 12, 25, 1200, 1145, "Christmas", "A.M.", "P.M.", 0);
		m.addEvent(2018, 1, 1, 1200, 1145, "New Year", "A.M.", "P.M.", 0);
		calendarFrame = new JFrame("Calendar");
		Dimension DimMax = Toolkit.getDefaultToolkit().getScreenSize();
		calendarFrame.setSize(DimMax);
		calendarFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				
		// Getting all available names for fonts in Java library
//		String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
//
//		for (int i = 0; i < fonts.length; i++) {
//		    System.out.println(fonts[i]);
//		}
	}
	
	/**
	 * Create a frame where users are able to select the theme of calendar
	 */
	public void themeFrame()
	{
		JFrame themeFrame = new JFrame("Theme");
		themeFrame.setSize(600,700);
		
		JLabel chooseLabel = new JLabel("Please Choose one of the following themes to display your calendar: ");
		chooseLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
		chooseLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel buttonPanel = new JPanel();
		FlowLayout fl = new FlowLayout();
		fl.setHgap(50);
		buttonPanel.setLayout(fl);
		JButton theme1 = new JButton("Seasons");
		JButton theme2 = new JButton("Black And White");
		JButton theme3 = new JButton("Sunset");
		JButton theme4 = new JButton("Plain And Simple");
		Font buttonFont = new Font("SansSerif", Font.PLAIN, 22);
		theme1.setFont(buttonFont);
		theme2.setFont(buttonFont);
		theme3.setFont(buttonFont);
		theme4.setFont(buttonFont);
		buttonPanel.add(theme1);
		buttonPanel.add(theme2);
		buttonPanel.add(theme3);
		buttonPanel.add(theme4);
		
		theme1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				theme = "Seasons";
				setSeasonBackground(); 
				paintMonthView();
				themeFrame.dispose();
			}
		});
		
		theme2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				theme = "Black And White";
				calendarFrame.setContentPane(setBackground("/img/BlackAndWhite.jpg"));
				paintMonthView();
				themeFrame.dispose();
			}			
		});
		
		theme3.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				theme = "Sunset";
				calendarFrame.setContentPane(setBackground("/img/Sunset.jpg"));
				paintMonthView();
				themeFrame.dispose();
			
			}
			
		});
		
		theme4.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				theme = "Plain And Simple";
				paintMonthView();	
				themeFrame.dispose();
			}
		});
		
		themeFrame.add(chooseLabel, BorderLayout.NORTH);
		themeFrame.add(buttonPanel, BorderLayout.CENTER);
		
		themeFrame.pack();
		themeFrame.setVisible(true);
		themeFrame.setLocationRelativeTo(null);
	}
	
	public Model getModel()
	{
		return m;
	}
	/**
	 * paint day view of calendar
	 */
	public void paintDayView()
	{
		String[] months = {"January", "February", "March", "April","May", "June", "July", "August", "September", "October", "November", "December"};
		JPanel menuPanel = new JPanel();
		JPanel timePanel = new JPanel();
		JPanel lastAndNextPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		
		//Create the label of recent day
		String recentDay = months[m.getMovedAroundCal().get(Calendar.MONTH)] + "  " + m.getMovedAroundCal().get(Calendar.DAY_OF_MONTH);
		JLabel dayLabel = new JLabel(recentDay);
		dayLabel.setFont(new Font("Elephant", Font.BOLD,36));
		dayLabel.setHorizontalAlignment(JLabel.CENTER);
		dayLabel.setOpaque(false);
		
		//Create last day and next day button so users can go over every day
		JButton lastDayButton = new JButton(new ImageIcon(new ImageIcon(this.getClass().getResource("/img/last.png")).getImage()));
		beautifyNextAndLastButton(lastDayButton);
		JButton nextDayButton = new JButton(new ImageIcon(new ImageIcon(this.getClass().getResource("/img/next.png")).getImage()));
		beautifyNextAndLastButton(nextDayButton);		
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
		
		JButton todayButton = beautifyButton("Today", "/img/today.png");
		Dimension d = new Dimension(150,40);
		todayButton.setPreferredSize(d);
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
		JButton createButton = beautifyButton("Create", "/img/create.png");
		createButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				createEventBox();
			}
		});
				
	

		
		//below is implementation for switching to month view
		JButton monthView = beautifyButton("Month", "/img/monthView.png");
		monthView.setPreferredSize(d);
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
		
		menuPanel.setOpaque(false);

		lastAndNextPanel.setOpaque(false);
		dayLabel.setOpaque(false);
		buttonPanel.setOpaque(false);
		BorderLayout bl = new BorderLayout();
		bl.setHgap(130);
		menuPanel.setLayout(bl);
		menuPanel.add(lastAndNextPanel, BorderLayout.WEST);
		menuPanel.add(dayLabel, BorderLayout.NORTH);
		menuPanel.add(buttonPanel, BorderLayout.EAST);
		menuPanel.setPreferredSize(new Dimension(1900,(int) menuPanel.getPreferredSize().getHeight()));
	
		JPanel contentPanel = new JPanel();
		
		Box scrollBox = Box.createVerticalBox();
		Box box = Box.createVerticalBox();
		
		//Make scroll bar show and working
		for(int i = 0; i < 25; i++)
		{
			JLabel l = new JLabel(" ");
			l.setFont(new Font("SansSerif",Font.PLAIN, 80));
			scrollBox.createVerticalStrut(60);
			scrollBox.add(l);
		}
		
		//create delete button and modified its look
		JButton deleteButton = beautifyButton("Delete", "/img/delete.png");
		deleteButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event) 
			{
				deleting = true;
				if(event.getActionCommand().equals("Cancel"))
				{
					deleting = false;
				}
				calendarFrame.getContentPane().removeAll();
				paintDayView();
			}
		});
		
		//Making block where contains an event's name and time
		if(!deleting)
		{
			int numOfNoEventBlock = 0;
			boolean hasNoEventBlock = true;
			for(int i = 0; i < m.getDaysArr().size(); i++)
			{
				//Check if there is any events on selected day.
				boolean hasEventOnTheDay = m.getMovedAroundCal().get(Calendar.DAY_OF_MONTH) == m.getDaysArr().get(i).getDay() && m.getMovedAroundCal().get(Calendar.MONTH) + 1  == m.getDaysArr().get(i).getMonth() && m.getMovedAroundCal().get(Calendar.YEAR) == m.getDaysArr().get(i).getYear();
				if(hasEventOnTheDay)
				{
					for(int j = 0; j < m.getDaysArr().get(i).getEventsArr().size(); j++)
					{
						//if noEventBox is created, and try to insert new block, delete noEventBox first
						if(numOfNoEventBlock != 0)
						{
							box.remove(0);
							numOfNoEventBlock = 0;
						}
						Event currentEvent =  m.getDaysArr().get(i).getEventsArr().get(j);
						String desc = currentEvent.getDescription();
						String strTime = currentEvent.getStrTime();
						if(strTime.equals("12:00 A.M.  -  11:45 P.M."))
							strTime = "All Day";
						Color color = colorList[currentEvent.getColor()];
						Block block = new Block(false,0,40,1000,200,desc, strTime, color);
						block.setMaximumSize(new Dimension(1000, 250));
						box.add(block);
						block.addActionListener(new ActionListener()
						{
							public void actionPerformed(ActionEvent e) 
							{
								updateEvent(currentEvent);
							}					
						});
					}
					hasNoEventBlock = false;
					continue;
				}
				
				//If there is no events and no event box has created, create one.
				else
				{
					if(hasNoEventBlock && numOfNoEventBlock < 1)
					{
						Block noEventblock = new Block(false,0,40,1400,800,"No Event Has Scheduled Yet", "", new Color(255, 128, 8));
						noEventblock.setMaximumSize(new Dimension(1400, 850));
						box.add(noEventblock);
						hasNoEventBlock = true;
						numOfNoEventBlock++;
					}
				}
			}
		}
		
		else
		{
			Image cancellmg = new ImageIcon(this.getClass().getResource("/img/cancel.png")).getImage();
			for(int i = 0; i < m.getDaysArr().size(); i++)
			{
				if(m.getMovedAroundCal().get(Calendar.DAY_OF_MONTH) == m.getDaysArr().get(i).getDay() && m.getMovedAroundCal().get(Calendar.MONTH) + 1  == m.getDaysArr().get(i).getMonth() && m.getMovedAroundCal().get(Calendar.YEAR) == m.getDaysArr().get(i).getYear())
				{
					for(int j = 0; j < m.getDaysArr().get(i).getEventsArr().size(); j++)
					{						
						deleteButton.setText("Cancel");
						deleteButton.setIcon(new ImageIcon(cancellmg));
						Day currentDay = m.getDaysArr().get(i);
						Event currentEvent =  currentDay.getEventsArr().get(j);
						String desc = currentEvent.getDescription();
						String strTime = currentEvent.getStrTime();
						if(strTime.equals("12:00 A.M.  -  11:45 P.M."))
							strTime = "All Day";
						Color color = colorList[currentEvent.getColor()];
						Block block = new Block(true,0,40,1000,200,desc, strTime, color);
						Image deleteEventImg = new ImageIcon(getClass().getResource("/img/deleteEvent.png")).getImage();
						JButton deleteEventButton = new JButton(new ImageIcon(deleteEventImg));
						deleteEventButton.setBorderPainted(false);
						deleteEventButton.setOpaque(false);
						deleteEventButton.setContentAreaFilled(false);
						deleteEventButton.addActionListener(new ActionListener()
						{
							public void actionPerformed(ActionEvent arg0) 
							{
								currentDay.getEventsArr().remove(currentEvent);
								calendarFrame.getContentPane().removeAll();
								paintDayView();
							}						
						});
						block.add(deleteEventButton);
						deleteEventButton.setAlignmentX(Component.LEFT_ALIGNMENT);
						block.setMaximumSize(new Dimension(1000, 250));
						box.add(block);
					}
				}
			}
		}

		
		buttonPanel.add(deleteButton);
		contentPanel.setLayout(new BorderLayout());
		contentPanel.add(scrollBox, BorderLayout.WEST);
		contentPanel.add(box, BorderLayout.CENTER);
		JScrollPane scrollPane = new JScrollPane(contentPanel);
		contentPanel.setOpaque(false);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		
		

		calendarFrame.setLayout(new BorderLayout());
		calendarFrame.add(menuPanel, BorderLayout.NORTH);
		calendarFrame.add(scrollPane,BorderLayout.CENTER);

		calendarFrame.setVisible(true);
		calendarFrame.setLocationRelativeTo(null);
		calendarFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * paint agenda view of calendar
	 */
	public void paintAgendaView()
	{
		JFrame agendaFrame = new JFrame("Calendar (Agenda View)");
		agendaFrame.setSize(1200, 600);
		agendaFrame.setVisible(true);
		agendaFrame.setLocationRelativeTo(null);
		
		JTable agenda = new JTable();
		String[] category = {"Date", "Time", "Description"};
		
		//Adding header to table
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(category);
		JTableHeader header = agenda.getTableHeader();
		header.setFont(new Font("SansSerif", Font.BOLD, 36));
		header.setEnabled(false);
		header.setBackground(new Color(205,133,63));
		agenda.setTableHeader(header);
			
		agenda.setBackground(new Color(255,255,153));
		agenda.setModel(model);
		
		//Make text in table cell align center
		((DefaultTableCellRenderer)agenda.getDefaultRenderer(Object.class)).setHorizontalAlignment(JLabel.CENTER);
		
		//Create a table without grid
		agenda.setShowGrid(false);
		agenda.setGridColor(new Color(255,99,71));
		
		//Not allow people to edit the text in each cell
		agenda.setEnabled(false);
		

		agenda.setFont(new Font("SansSerif", Font.BOLD, 24));
		agenda.setRowHeight(60);

		// Go through every event in model and print out every event in order of its date, time and name.
		if(m.getDaysArr().size() > 0)
		{
			for(int i = 0; i < m.getDaysArr().size(); i++)
			{
				for(int j = 0; j < m.getDaysArr().get(i).getEventsArr().size(); j++)
				{
					Object[] row = new Object[3];
					Event testEvent = m.getDaysArr().get(i).getEventsArr().get(j);
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
					
					if(time.equals("12:00 A.M. - 11:45 P.M."))
							time = "All Day";
					
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
	
	/**
	 * paint month view of calendar
	 */
	public void paintMonthView()
	{		
		String[] months = {"January", "February", "March", "April","May", "June", "July", "August", "September", "October", "November", "December"};
		
		JPanel weekDayPanel = new JPanel();
		JPanel monthAndYearPanel = new JPanel();
		JPanel lastAndNextPanel = new JPanel();
		JPanel tabPanel = new JPanel();
		JPanel daysPanel = new JPanel();
		daysPanel = paintDays();
		
		
		daysPanel.setLayout(new GridLayout(0, 7));
		int intRecentYear = m.getMovedAroundCal().get(Calendar.YEAR);
		String strRecentYear = String.valueOf(intRecentYear);
		int intRecentMonth = m.getMovedAroundCal().get(Calendar.MONTH);
		String strRecentMonth = months[intRecentMonth];
		
		//Trying
		JLabel yearLabel = new JLabel(strRecentYear);
		JLabel strMonthLabel = new JLabel(strRecentMonth);
		
		if(strRecentMonth.length() > 5)
		{
			strMonthLabel.setText(strRecentMonth.substring(0, 3) + ".");
		}
		
		//Create look that record month number, month name and year
		Box strMonthAndYear = Box.createVerticalBox();
		yearLabel.setFont(new Font("Elephant", Font.BOLD, 30));
		strMonthLabel.setFont(new Font("Elephant", Font.BOLD, 30));
		strMonthAndYear.add(strMonthLabel);
		strMonthAndYear.add(yearLabel);
		
		JLabel intMonthLabel = new JLabel();
		
		if(intRecentMonth + 1 < 10)
			intMonthLabel = new JLabel("0" + String.valueOf(intRecentMonth + 1));
		else
			intMonthLabel = new JLabel(String.valueOf(intRecentMonth + 1));
		intMonthLabel.setFont(new Font("Elephant", Font.BOLD, 45));
		
		JPanel timePanel = new JPanel();		
		FlowLayout f = new FlowLayout();
		f.setHgap(40);
		
		timePanel.setLayout(f);
		timePanel.add(intMonthLabel);
		timePanel.add(strMonthAndYear);
		strMonthAndYear.setFont(new Font("Elephant", Font.BOLD, 40));
				
		//Make last and next Month button with images
		JButton lastMonthButton = new JButton(new ImageIcon(new ImageIcon(this.getClass().getResource("/img/last.png")).getImage()));
		beautifyNextAndLastButton(lastMonthButton);
		JButton nextMonthButton = new JButton(new ImageIcon(new ImageIcon(this.getClass().getResource("/img/next.png")).getImage()));
		beautifyNextAndLastButton(nextMonthButton);	
		lastAndNextPanel.add(lastMonthButton);
		lastAndNextPanel.add(nextMonthButton);
		
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
		JButton todayButton = beautifyButton("Today", "/img/today.png");
		Dimension d = new Dimension(140,40);
		todayButton.setPreferredSize(d);
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
		JButton dayView = beautifyButton("Day", "/img/dayView.png");
		dayView.setPreferredSize(d);
		dayView.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				calendarFrame.getContentPane().removeAll();
				paintDayView();
			}
		});
		
		//Create agenda button which makes users able to see their events in agenda form
		
		JButton agendaButton = beautifyButton("Agenda", "/img/agenda.png");
		agendaButton.setPreferredSize(d);
		agendaButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				paintAgendaView();
			}
		});
	
		// Make "Create" button and connect it to functional "create" method
		JButton createButton = beautifyButton("Create", "/img/create.png");
		createButton.setPreferredSize(d);
		createButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				createEventBox();
			}
		});
		
		//Arrange different components in monthAndYearPanel (The top panel of the frame)
		lastAndNextPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		tabPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		BorderLayout bl = new BorderLayout();
		monthAndYearPanel.setLayout(bl);		
		monthAndYearPanel.add(lastAndNextPanel,BorderLayout.WEST);
		monthAndYearPanel.add(timePanel,BorderLayout.NORTH);
		monthAndYearPanel.add(tabPanel, BorderLayout.EAST);
					
		lastAndNextPanel.setOpaque(false);
		timePanel.setOpaque(false);
		tabPanel.setOpaque(false);
		
		//Paint the weekDay of the calendar
		String[] weekDay = {"Sunday", "Monday", "Tuesday", "Wednesday",  "Thursday", "Friday" , "Saturday"};
		for(int i = 0; i < weekDay.length; i++)
		{
			JLabel weekDayLabel = new JLabel(weekDay[i]);
			FlowLayout fl = new FlowLayout();
			fl.setAlignment(FlowLayout.CENTER);
			fl.setHgap(160);
			weekDayPanel.setLayout(fl);
			weekDayLabel.setFont(new Font("Elephant", Font.PLAIN, 24));
			weekDayPanel.add(weekDayLabel);
		}
	
		//Add every button in tabPanel
		tabPanel.add(createButton);
		tabPanel.add(todayButton);
		tabPanel.add(dayView);
		tabPanel.add(agendaButton);
		
		Box box = Box.createVerticalBox();
		monthAndYearPanel.setOpaque(false);
		weekDayPanel.setOpaque(false);
		box.add(monthAndYearPanel);
		box.add(weekDayPanel);
		box.setOpaque(false);
		if(theme.equals("Seasons"))
		{	
			setSeasonBackground();
		}

		calendarFrame.setLayout(new BorderLayout());
		calendarFrame.add(box, BorderLayout.NORTH);
		calendarFrame.add(daysPanel, BorderLayout.CENTER);

		calendarFrame.setLocationRelativeTo(null);
		calendarFrame.setVisible(true);
		calendarFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * create a panel and paint day buttons in calendar month view
	 * @return the panel with painted day buttons
	 */
	public JPanel paintDays()
	{
		JPanel daysPanel = new JPanel();
		
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
				JButton dayButton = new JButton();				
				JLabel dayLabel = new JLabel(String.valueOf(day));
				dayLabel.setFont(new Font("Cooper Black", Font.PLAIN, 36));
				if(theme.equals("Black And White"))
				{
					JGradientColor gradientButton = new JGradientColor(Color.BLACK, Color.WHITE);
					dayButton = gradientButton;
					dayLabel.setForeground(Color.WHITE);
					dayButton.setOpaque(true);
					daysPanel.setOpaque(true);
					dayButton.setContentAreaFilled(false);
				}
				
				else if(theme.equals("Sunset"))
				{
						JGradientColor gradientButton = new JGradientColor(new Color(255, 126, 95), new Color(254, 180, 123));
						dayButton = gradientButton;
						dayLabel.setForeground(Color.WHITE);
						dayButton.setOpaque(true);
						daysPanel.setOpaque(true);
						dayButton.setContentAreaFilled(false);
				}
				
				else if(theme.equals("Seasons"))
				{
					// Change the color of day in calendar according to specified seasons
					int recentMonth = m.getMovedAroundCal().get(Calendar.MONTH) + 1;
					boolean isSpring = recentMonth >= 3 && recentMonth <= 5;
					boolean isSummer = recentMonth >= 6 && recentMonth <= 8;
					boolean isFall = recentMonth >= 9 && recentMonth <= 11;					
					if(isSpring)
						dayLabel.setForeground(new Color(160,82,45));
					else if(isSummer)
						dayLabel.setForeground(new Color(205,133,63));
					else if(isFall)
						dayLabel.setForeground(new Color(255,69,0));
					else
						dayLabel.setForeground(new Color(105,105,105));
				}
				
				dayButton.setLayout(new BorderLayout());
				dayButton.add(dayLabel, BorderLayout.NORTH);
				
				//Check condition for Holiday				
				JPanel textAndImagePanel = new JPanel();
				
				boolean isToday = day == m.getLocalCal().get(Calendar.DAY_OF_MONTH) && m.getLocalCal().get(Calendar.MONTH) == m.getMovedAroundCal().get(Calendar.MONTH) &&m.getLocalCal().get(Calendar.YEAR) == m.getMovedAroundCal().get(Calendar.YEAR);
				boolean isIndependentDay = m.getMovedAroundCal().get(Calendar.MONTH) == 6 && day == 4;
				boolean isThanksGiving = m.getMovedAroundCal().get(Calendar.MONTH) == 10 && day == 23;
				boolean isChristmas = m.getMovedAroundCal().get(Calendar.MONTH) == 11 && day == 25;
				boolean isDayLightSaving = m.getMovedAroundCal().get(Calendar.MONTH) == 10 && day == 5;
				boolean isNewYear = m.getMovedAroundCal().get(Calendar.MONTH) == 0 && day == 1;
				
				dayButton.setOpaque(false);
				dayButton.setContentAreaFilled(false);
				
				if(isIndependentDay)
				{
					Image img = new ImageIcon(this.getClass().getResource("/img/independence.png")).getImage();
					JLabel imgLabel = new JLabel(new ImageIcon(img));
					markHolidy(textAndImagePanel, dayLabel, imgLabel, dayButton);
				}
				
				if(isDayLightSaving)
				{
					Image img = new ImageIcon(this.getClass().getResource("/img/dayLight.png")).getImage();
					JLabel imgLabel = new JLabel(new ImageIcon(img));
					markHolidy(textAndImagePanel, dayLabel, imgLabel, dayButton);
				}
				
				if(isThanksGiving)
				{
					Image img = new ImageIcon(this.getClass().getResource("/img/turkey.png")).getImage();
					JLabel imgLabel = new JLabel(new ImageIcon(img));
					markHolidy(textAndImagePanel, dayLabel, imgLabel, dayButton);
				}
				
				if(isChristmas)
				{
					Image img = new ImageIcon(this.getClass().getResource("/img/Christmas.png")).getImage();
					JLabel imgLabel = new JLabel(new ImageIcon(img));
					markHolidy(textAndImagePanel, dayLabel, imgLabel, dayButton);
				}
				
				if(isNewYear)
				{
					Image img = new ImageIcon(this.getClass().getResource("/img/newYear.png")).getImage();
					JLabel imgLabel = new JLabel(new ImageIcon(img));
					markHolidy(textAndImagePanel, dayLabel, imgLabel, dayButton);
				}
				
				
				//Find and highlight today's date
				if(isToday)
				{
					dayButton.setBorder(BorderFactory.createLineBorder(Color.RED, 10, true));
					dayLabel.setForeground(Color.WHITE);
				}
							
				int numOfAllEvents = m.getDaysArr().size();
				int previewCount = 0;

				if(numOfAllEvents > 0)
				{
					 Box box = Box.createVerticalBox();
					for(int i = 0; i < numOfAllEvents; i++)
					{
						//Check year, month and day for every event to make it viewable in corresponding time
						// The reason for "+1" is that get(Calendar.MONTH) starts with index 0, which represents January
						if(day == m.getDaysArr().get(i).getDay() && m.getMovedAroundCal().get(Calendar.MONTH) + 1  == m.getDaysArr().get(i).getMonth() && m.getMovedAroundCal().get(Calendar.YEAR) == m.getDaysArr().get(i).getYear())
						{

							for(int j = 0; j < m.getDaysArr().get(i).getEventsArr().size(); j++)
							{
								// if there are over 3 event in a day, event preview features in month view will only show up to 3,
								// and users can click into that day into day view to discover more events.
								if(previewCount < 3)
								{
									EventPreviewMark mark = new EventPreviewMark(m.getDaysArr().get(i).getEventsArr().get(j));
									mark.setText((m.getDaysArr().get(i).getEventsArr().get(j).getDescription()));
									mark.setFont(new Font("SansSerif", Font.PLAIN, 18));
									mark.setForeground(Color.WHITE);
									mark.setMaximumSize(new Dimension(200,20));
									box.add(mark);
									dayButton.add(box,BorderLayout.CENTER);
									previewCount++;
								}
								else
								{
									JButton moreButton = new JButton("More Event: ");
									moreButton.setForeground(Color.DARK_GRAY);
									moreButton.setFont(new Font("SansSerif", Font.BOLD, 16));
									moreButton.setOpaque(false);
									moreButton.setContentAreaFilled(false);
									moreButton.setBorderPainted(false);
									dayButton.add(moreButton,BorderLayout.SOUTH);
									moreButton.addActionListener(new ActionListener()
									{
										public void actionPerformed(ActionEvent e) 
										{
											calendarFrame.getContentPane().removeAll();
											paintDayView();
										}									
									});
									break;
								}
							}
						}
					}
				}
				Dimension sizeOfButton = new Dimension(250,130);
				dayButton.setOpaque(false);
				daysPanel.setOpaque(false);
				dayButton.setPreferredSize(sizeOfButton);

				
				daysPanel.add(dayButton);
				day++;
				dayButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e) 
					{
						m.getMovedAroundCal().set(Calendar.DAY_OF_MONTH, Integer.valueOf(dayLabel.getText()));
						calendarFrame.getContentPane().removeAll();
						paintDayView();
					}
				});
			}
		}	
		return daysPanel;
	}
	/**
	 * Make event box that allows users to enter basic information about an event and add it to database
	 */
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
		Image img = new ImageIcon(this.getClass().getResource("/img/save.png")).getImage();
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
				int compareStartTime = 0;
				int compareEndTime = 0;
				compareStartTime = intStartTime;
				compareEndTime = intEndTime;
				if(startTod.equals("A.M."))
					if(compareStartTime >= 1200)
						compareStartTime = compareStartTime - 1200;
				if(endTod.equals("A.M."))
					if(compareEndTime >= 1200)
						compareEndTime = compareEndTime - 1200;
				if(startTod.equals("P.M."))
					if(compareStartTime < 1200)
						compareStartTime = compareStartTime + 1200;
				if(endTod.equals("P.M."))
					if(compareEndTime < 1200)
						compareEndTime = compareEndTime + 1200;
				startLaterThanEnd = compareStartTime - compareEndTime > 0;
				
				//check if there is any error like leaving description blank or didn't choose between AM and PM for start time or end time
				if(blankDesc || wrongStartTimeInput || wrongEndTimeInput || startLaterThanEnd)
				{
					//Construct a basic error message window
					JFrame errorFrame = new JFrame("Error");
					errorFrame.setSize(450,150);
					JPanel errorPanel = new JPanel();
						
					JLabel imageLabel = new JLabel();
					Image img = new ImageIcon(this.getClass().getResource("/img/errorIcon.png")).getImage();
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
	
				m.addEvent(year, intMonth, day, intStartTime,intEndTime, description, startTod, endTod, color);
				

				// Repaint the calendar after an event is added
				eventFrame.dispose();		
				calendarFrame.getContentPane().removeAll();
				paintDayView();
				
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
		Image cancelImg = new ImageIcon(this.getClass().getResource("/img/cancel.png")).getImage();
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
	 * Allows users to update event information by clicking a specific event
	 * @param eventToUpdate the event users arae trying to update
	 */
	public void updateEvent(Event eventToUpdate)
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
		
		
		
		//add labels and set its fonts.
		JLabel descLabel = new JLabel("Description: ");
		JLabel dateLabel = new JLabel("Date: ");
		JLabel startTimeLabel = new JLabel("Start Time:");
		JLabel endTimeLabel = new JLabel("End Time: ");
		descLabel.setFont(labelFont);
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
		
		descText.setText(eventToUpdate.getDescription());
		for(int i = 0; i < startTime.getItemCount(); i++)
		{
			if(eventToUpdate.getStrStartTime().equals(startTime.getItemAt(i)))
			{
				startTime.setSelectedIndex(i);
				break;
			}
		}
		endTime.setSelectedItem(eventToUpdate.getStrEndTime());
		colorBox.setSelectedIndex(eventToUpdate.getColor());
		
		
		Font buttonFont = new Font("Comic Sans MS", Font.BOLD, 18);
		Image img = new ImageIcon(this.getClass().getResource("/img/update.png")).getImage();
		JButton updateButton = new JButton("Update", new ImageIcon(img));
		updateButton.setBackground(new Color(30,144,255));
		updateButton.setForeground(Color.WHITE);
		updateButton.setFont(buttonFont);
		updateButton.addActionListener(new ActionListener()
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
				int compareStartTime = 0;
				int compareEndTime = 0;
				compareStartTime = intStartTime;
				compareEndTime = intEndTime;
				if(startTod.equals("A.M."))
					if(compareStartTime >= 1200)
						compareStartTime = compareStartTime - 1200;
				if(endTod.equals("A.M."))
					if(compareEndTime >= 1200)
						compareEndTime = compareEndTime - 1200;
				if(startTod.equals("P.M."))
					if(compareStartTime < 1200)
						compareStartTime = compareStartTime + 1200;
				if(endTod.equals("P.M."))
					if(compareEndTime < 1200)
						compareEndTime = compareEndTime + 1200;
				startLaterThanEnd = compareStartTime - compareEndTime > 0;
				
				//check if there is any error like leaving description blank or didn't choose between AM and PM for start time or end time
				if(blankDesc || wrongStartTimeInput || wrongEndTimeInput || startLaterThanEnd)
				{
					//Construct a basic error message window
					JFrame errorFrame = new JFrame("Error");
					errorFrame.setSize(450,150);
					JPanel errorPanel = new JPanel();
						
					JLabel imageLabel = new JLabel();
					Image img = new ImageIcon(this.getClass().getResource("/img/errorIcon.png")).getImage();
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
				
				eventToUpdate.updateEvent(year, intMonth, day, intStartTime,intEndTime, description, startTod, endTod, color);
				// Repaint the calendar after an event is added
				eventFrame.dispose();
				calendarFrame.getContentPane().removeAll();
				paintDayView();
				
			System.out.println("There is(are) " + m.getDaysArr().size() + " events in database(model): ");
			for(int i  = 0; i < m.getDaysArr().size(); i++)
			{
				for(int j = 0; j < m.getDaysArr().get(i).getEventsArr().size(); j++)
				{
					System.out.print(m.getDaysArr().get(i) + "   ");
					System.out.println(m.getDaysArr().get(i).getEventsArr().get(j));
				}
					
			}
			
			}
			
		});
		
		//create cancel button and modified its look
		Image cancelImg = new ImageIcon(this.getClass().getResource("/img/cancel.png")).getImage();
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
		buttonPanel.add(updateButton);
		buttonPanel.add(cancelButton);
		box.add(buttonPanel);
		
		eventFrame.add(box);
		eventFrame.setLocationRelativeTo(eventFrame);
		eventFrame.setVisible(true);
	}
	
		
/**
 * modifies functional buttons' look by importing images and settting their background and foreground
 * @param buttonName the button that is trying to be modified
 * @param path the path where the image can be found
 * @return the button that has been already beautified
 */
public JButton beautifyButton(String buttonName, String path)
{
	Image Img = new ImageIcon(this.getClass().getResource(path)).getImage();
	JButton button = new JButton(buttonName, new ImageIcon(Img));
	button.setForeground(Color.WHITE);
	button.setBackground(Color.GRAY);
	button.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
	return button;
}

/**
 * mark holiday by adding the small icon beside the specific day
 * @param textAndImagePanel the panel that all components are being added into
 * @param dayLabel the number of the day
 * @param imgLabel the label with image
 * @param dayButton the button that contains dayLabel and imgLabel
 */
public void markHolidy(JPanel textAndImagePanel, JLabel dayLabel, JLabel imgLabel, JButton dayButton)
{
	textAndImagePanel.add(dayLabel);
	textAndImagePanel.add(imgLabel);
	textAndImagePanel.setOpaque(false);
	textAndImagePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
	dayButton.add(textAndImagePanel, BorderLayout.NORTH);
	dayButton.setContentAreaFilled(false);
}
/**
 * get the background Image according to months
 * @return the image that is selected based on the months
 */
public ImageIcon getBackgroundimage()
{
	String imagePath = "";	
	int month = m.getMovedAroundCal().get(Calendar.MONTH) + 1;
	if(month <= 8 && month >= 6)
		imagePath = "/img/summer.jpg";
	else if(month <= 11 && month >= 9)
		imagePath = "/img/fall.jpg";
	else if(month <= 5 && month >= 3)
		imagePath = "/img/spring.jpg";
	else if(month == 12 || month <= 2)
		imagePath = "/img/winter.jpg";
	return new ImageIcon(getClass().getResource(imagePath));
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
colorBox.addItem("Brown");
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

public void beautifyNextAndLastButton(JButton button)
{
	button.setOpaque(false);
	button.setContentAreaFilled(false);
	button.setBorderPainted(false);
	button.setRolloverEnabled(true);
}
/**
 * return JLabel background according to the given path
 * @param path the image path where the image is
 * @return the JLabel with image
 */
public JLabel setBackground(String path)
{
	JLabel backgroundLabel = new JLabel();
	Image img = new ImageIcon(getClass().getResource(path)).getImage().getScaledInstance(calendarFrame.getWidth(), calendarFrame.getHeight(), Image.SCALE_DEFAULT);
	backgroundLabel.setIcon(new ImageIcon(img));
	backgroundLabel.setHorizontalAlignment(SwingConstants.CENTER);
	return backgroundLabel;
}

public void setSeasonBackground()
{
	JLabel backgroundLabel = new JLabel();
	Image img = getBackgroundimage().getImage().getScaledInstance(calendarFrame.getWidth(), calendarFrame.getHeight(), Image.SCALE_DEFAULT);
	backgroundLabel.setIcon(new ImageIcon(img));
	backgroundLabel.setHorizontalAlignment(SwingConstants.CENTER);
	calendarFrame.setContentPane(backgroundLabel);
}
}

