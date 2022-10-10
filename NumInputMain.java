package hw5;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class NumInputMain {
	private static JButton inputBtn;
	private static JButton outputBtn;
	private static JButton computeBtn;
	private static JFrame jframeWindow;
	private static JPanel panel;
	private static File fileToRead;
	private static File fileToSave;

	static double sumOfSquares = 0;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		constructAppWindow();
		addListenerEvents();
	}

	private static void constructAppWindow() {
		jframeWindow = new JFrame();
		jframeWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// construct a panel container to store buttons, etc.
		panel = new JPanel(new GridLayout(3, 0)); // 3 ROWS NO COLUMNS
		panel.setPreferredSize(new Dimension(500, 150));
		panel.setBackground(Color.DARK_GRAY);

		// build buttons, etc. and add them to the panel
		inputBtn = new JButton("Specify Input Text File");
		outputBtn = new JButton("Specify Output Text File");
		computeBtn = new JButton("Perform Work");
		panel.add(inputBtn);
		panel.add(outputBtn);
		panel.add(computeBtn);

		// add panel to the application window
		jframeWindow.add(panel);

		// TASK 5: MAKE THE APPLICATION WINDOW VISIBLE TO THE USER
		jframeWindow.pack();
		jframeWindow.setVisible(true);
	}

	private static void addListenerEvents() {
		inputBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				requestInputFile();
			}
		});
		outputBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				requestSaveFile();
			}
		});
		computeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					computeSomething();
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

	}

	public static void requestSaveFile() {
		// parent component of the dialog
		JFrame parentFrame = new JFrame();

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Specify a file to save");
		int userSelection = fileChooser.showSaveDialog(parentFrame);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			fileToSave = fileChooser.getSelectedFile();
			System.out.println("Save as file: " + fileToSave.getAbsolutePath());
		}
	}

	public static void requestInputFile() {
		// parent component of the dialog
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
		jfc.setFileFilter(filter); // ONLY SHOW TEXT FILES FOR EASE OF USE
		int returnValue = jfc.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			fileToRead = jfc.getSelectedFile();
			System.out.println(fileToRead.getAbsolutePath());
		}
	}

	public static int size(Node head) { // FIND THE SIZE OF THE LINKEDLIST
		Node t = head;
		int count = 0;
		while (t != null) {
			count++;
			t = t.next;
		}
		return count;
	}

	static void findStd(Node value, double mean) { // RECURSIVE METHOD FOR SECOND STEP OF FINDING STANDARD DEVIATION
		if (value == null) {
			return;
		}

		findStd(value.next, mean);

		sumOfSquares = sumOfSquares + Math.pow((value.num - mean), 2); // RUNS THROUGH WHOLE LIST, ADDING ALL THE
																		// SQUARES OF VALUES' DISTANCE FROM MEAN

	}

	public static double findStdDriver(Node value, double mean, int size) { // USES SUM OF SQUARES TO FIND VARIANCE,
																			// WHICH IS SQUARE ROOTED TO FIND AVG
																			// STANDARD DEVIATIONS
		findStd(value, mean);
		double variance = sumOfSquares / (size - 1);
		return (Math.sqrt(variance));
	}

	public static void computeSomething() throws NumberFormatException, IOException {
		MathMachine linkedList = new MathMachine(); // INITIALIZE LINKED LIST

		Scanner scan = new Scanner(fileToRead);

		boolean valid = true;

		BufferedReader read = new BufferedReader(new FileReader(fileToRead));
		String text = null;
		
		while ((text = read.readLine()) != null) {
			if (!text.matches("[0-9]+")) { // INPUT VALIDATION: REMOVE ANY STRINGS
				text.replaceAll("[a-zA-Z]+", "");
			} else {
				linkedList = MathMachine.insert(linkedList, Integer.parseInt(text)); // USE VALUES FROM FILE TO FILL
																						// LIST

			}
		}

		PrintStream out = new PrintStream(new FileOutputStream(fileToSave));
		System.setOut(out); // SET OUTPUT TO SAVE TO NEW FILE THAT IS SELECTED

		MathMachine.printList(linkedList); // PRINTS LIST IN NEW FILE
		int listSize = size(linkedList.head);
		int total = Node.findTotal(linkedList.head); // SUM OF VALUES
		double mean = Node.findMean(total, listSize);// AVERAGE OF THE VALUES
		double std = findStdDriver(linkedList.head, mean, listSize);// AVERAGE STANDARD DEVIATION

		System.out.println("");
		System.out.println("Mean: " + mean);
		System.out.println("Standard Deviations: " + std);

	}

}