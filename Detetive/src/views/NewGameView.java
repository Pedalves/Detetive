package views;

import javax.swing.JButton;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;

@SuppressWarnings("serial")
public class NewGameView extends View implements ItemListener
{	
	private JButton startButton;
	
	private ArrayList<String> _selectedPlayers;
	
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
		
		_selectedPlayers = new ArrayList<>();
	}

	public void setupUI()
	{	
		greenButton = new JCheckBox("green");
		mustardButton = new JCheckBox("mustard");
		peacockButton = new JCheckBox("peacock");
		plumButton = new JCheckBox("plum");
		scarletButton = new JCheckBox("scarlet");
		whiteButton = new JCheckBox("white");
		
		greenButton.addItemListener(this);
		mustardButton.addItemListener(this);
		peacockButton.addItemListener(this);
		plumButton.addItemListener(this);
		scarletButton.addItemListener(this);
		whiteButton.addItemListener(this);		
		
		startButton = new JButton("Começar o jogo");
		startButton.addActionListener(e -> {
			observable.changePanel(new GameView(_selectedPlayers));
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
        	   
        	   _selectedPlayers.add(tmp.getText());
        	   if(numPlayers >= 3)
        	   {
        		   startButton.setEnabled(true);
        	   }
           }
           else
           {
        	   numPlayers--;
        	   
        	   _selectedPlayers.remove(tmp.getText());
        	   if(numPlayers < 3)
        	   {
        		   startButton.setEnabled(false);
        	   }
           }
        }
	}
}
