import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import edu.stanford.nlp.parser.lexparser.LexicalizedParser;


public class MainFrame extends JFrame{

	/**
	 * @param args
	 */
	public JPanel panel;
	public JPanel pane2;
	public JButton runButton;
	public JTextArea area ;
	public Graphics2D g;
	
	public MainFrame (){
		panel = new JPanel();
		pane2 = new JPanel();
		g = (Graphics2D) pane2.getGraphics();
		panel.setBackground(Color.white);
		area = new JTextArea();
		runButton = new JButton();
		this.add(panel);
		this.setSize(600, 635);
		panel.setLayout(null);
		runButton.setLabel("Run");
		panel.add(runButton);
		panel.add(area);
		panel.add(pane2);
		runButton.setBounds(460, 25, 70, 30);
		area.setBounds(20, 20, 430, 40);
		pane2.setBounds(5, 100, 570, 490);
		area.setBackground(Color.lightGray);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final MainFrame m = new MainFrame();
	    m.setVisible(true);
		m.runButton.disable();
	    final LexicalizedParser lp = LexicalizedParser.loadModel("edu/stanford/nlp/models/lexparser/arabicFactored.ser.gz");
		m.runButton.enable();
		m.runButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String text = m.area.getText();
				ParserDemo p=  new ParserDemo();
				Graphics2D gg = (Graphics2D) m.pane2.getGraphics();
				p.demoAPI(lp, text );
				//gg.drawString(p.s, 100, 150);
			}
		});
		

	}

}
