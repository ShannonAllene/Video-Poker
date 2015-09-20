import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

//main
public class Project4
{
	public static void main(String [] args)
	{
		Board ourPanel = new Board();
		JFrame win = new JFrame("VIDEO POKER: JACKS OR BETTER");
		win.add(ourPanel);
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		win.pack();
		win.setVisible(true);
	}
}