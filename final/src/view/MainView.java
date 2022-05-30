package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MainView extends JFrame implements ActionListener {
	private static final long serialVersionUID = -1964236192794962979L;
	
	private static Color BACKGROUND_TEXTAREA = new Color(46, 64, 68);
	private static Color BACKGROUND_BUTTON = new Color(184, 202, 92);
	//private static Color FOREGROUND_BUTTON = new Color(207, 221, 134);
	private BugPanel panel;
	private JTextArea edit;
	private JButton button;
	private JTextArea console;
	
	private int cont;
	
	public MainView() {
		super("Proyecto Bichos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1024, 768);
		setLayout(new BorderLayout());
		
		panel = new BugPanel(724, 618);
		panel.setBackground(Color.white);
		getContentPane().add(panel, BorderLayout.CENTER);
		
		JPanel rightSide = new JPanel();
		rightSide.setLayout(new BorderLayout());
		
		edit = new JTextArea("Place your code here");
		edit.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
		edit.setBackground(BACKGROUND_TEXTAREA);
		edit.setForeground(Color.lightGray);
		edit.setLineWrap(true);
		edit.setWrapStyleWord(true);
		JScrollPane editScrollPane = new JScrollPane(edit);
		editScrollPane.setVerticalScrollBarPolicy(
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		editScrollPane.setHorizontalScrollBarPolicy(
						JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		editScrollPane.setPreferredSize(new Dimension(300, 100));
		rightSide.add(editScrollPane, BorderLayout.CENTER);
		
		button = new JButton("R U N");
		button.setBackground(BACKGROUND_BUTTON);
		//button.setForeground(FOREGROUND_BUTTON);
		button.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
		button.addActionListener(this);
		rightSide.add(button, BorderLayout.PAGE_END);
		
		getContentPane().add(rightSide, BorderLayout.LINE_END);
		
		console = new JTextArea();
		console.setEditable(false);
		console.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
		console.setBackground(BACKGROUND_TEXTAREA);
		JScrollPane consoleScrollPane = new JScrollPane(console);
		consoleScrollPane.setVerticalScrollBarPolicy(
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		consoleScrollPane.setHorizontalScrollBarPolicy(
						JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		consoleScrollPane.setPreferredSize(new Dimension(100, 150));
		getContentPane().add(consoleScrollPane, BorderLayout.PAGE_END);
		
		cont = 0;
	}
	
	public BugPanel getBugPanel() {
		return panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int max = 7;
		if (e.getSource().equals(button)) {
			switch(cont) {
			case 0	: panel.move(100); break;
			case 1  : panel.circle(100); break;
			case 2  : panel.right(45); break;
			case 3 	: panel.circle(300); break;
			case 4	: panel.arc(3600,  200); break;
			case 5	: panel.circle(100); break;
			case 6 	: panel.init(); break;
			}
			cont = (cont + 1) % max;
		}
		repaint();
	}
	
}
