package views;

import javax.swing.JButton;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JCheckBox;

import views.ViewObservable;

public class NewGameView extends JPanel 
{	
	private static final long serialVersionUID = 1L;

	private final ViewObservable observable;

	private JButton startButton;
	
	private int numPlayers = 0;

	public NewGameView(ViewObservable observable)
	{
		super();

		this.observable = observable;

		setupUI();
	}

	public void setupUI()
	{
		JCheckBox greenButton = new JCheckBox("greenButton");
		greenButton.addActionListener(e -> {
			if(greenButton.isSelected())
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
		});
		
		JCheckBox mustardButton = new JCheckBox("mustardButton");
		mustardButton.addActionListener(e -> {
			if(mustardButton.isSelected())
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
		});
		
		JCheckBox peacockButton = new JCheckBox("peacockButton");
		peacockButton.addActionListener(e -> {
			if(peacockButton.isSelected())
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
		});
		
		JCheckBox plumButton = new JCheckBox("plumButton");
		plumButton.addActionListener(e -> {
			if(plumButton.isSelected())
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
		});
		
		JCheckBox scarletButton = new JCheckBox("scarletButton");
		scarletButton.addActionListener(e -> {
			if(scarletButton.isSelected())
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
		});
		
		JCheckBox whiteButton = new JCheckBox("whiteButton");
		whiteButton.addActionListener(e -> {
			if(whiteButton.isSelected())
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
		});
		
		
		startButton = new JButton("Começar o jogo");
		startButton.addActionListener(e -> {
			this.observable.changePanel(new GameView());
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
}
