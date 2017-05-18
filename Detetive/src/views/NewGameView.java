package views;

import javax.swing.JButton;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;

@SuppressWarnings("serial")
public class NewGameView extends View implements ItemListener
{	
	private JButton startButton;
	
	JCheckBox greenButton;
	JCheckBox mustardButton;
	JCheckBox peacockButton;
	JCheckBox plumButton;
	JCheckBox scarletButton;
	JCheckBox whiteButton;
	
	private int numPlayers = 0;

	public NewGameView()
	{
		super();
	}

	public void setupUI()
	{
		greenButton = new JCheckBox("greenButton");
		mustardButton = new JCheckBox("mustardButton");
		peacockButton = new JCheckBox("peacockButton");
		plumButton = new JCheckBox("plumButton");
		scarletButton = new JCheckBox("scarletButton");
		whiteButton = new JCheckBox("whiteButton");
		
		greenButton.addItemListener(this);
		mustardButton.addItemListener(this);
		peacockButton.addItemListener(this);
		plumButton.addItemListener(this);
		scarletButton.addItemListener(this);
		whiteButton.addItemListener(this);		
		
		startButton = new JButton("Começar o jogo");
		startButton.addActionListener(e -> {
			observable.changePanel(new GameView());
		});
		startButton.setEnabled(false);
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		add(greenButton);
		add(mustardButton);
		add(peacockButton);
		add(plumButton);
		add(scarletButton);
		add(whiteButton);
		
		add(startButton);
	}

	@Override
	public void itemStateChanged(ItemEvent e) 
	{
		Object source = e.getItemSelectable();
		JCheckBox tmp = (JCheckBox)source;
		
		if ((source == greenButton) || (source == mustardButton) || (source == peacockButton) || (source == plumButton) || (source == scarletButton) || (source == whiteButton)) 
		{
           if(tmp.isSelected())
           {
        	   numPlayers++;
        	   if(numPlayers >= 3)
        	   {
        		   startButton.setEnabled(true);
        	   }
           }
           else
           {
        	   numPlayers--;
        	   if(numPlayers < 3)
        	   {
        		   startButton.setEnabled(false);
        	   }
           }
        }
	}
}
