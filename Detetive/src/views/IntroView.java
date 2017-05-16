package views;

import javax.swing.JButton;

import java.awt.Component;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import views.ViewObservable;
import views.NewGameView;

public class IntroView extends JPanel 
{	
	private static final long serialVersionUID = 1L;

	private final Observer mainWindow;
	private final ViewObservable observable;
	
	public IntroView(Observer mainWindow)
	{
		super();
		
		this.observable = new ViewObservable();
		this.observable.addObserver(mainWindow);
		this.mainWindow = mainWindow;
		
		setupUI();
		
		this.observable.changePanel(this);
	}

	public void setupUI()
	{
		JButton newButton = new JButton("Novo Jogo");
		newButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		newButton.addActionListener(e -> {
			this.observable.changePanel(new NewGameView(this.mainWindow));
		});
		

		JButton continueButton = new JButton("Continuar");
		continueButton.setAlignmentX(Component.CENTER_ALIGNMENT);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));
		
		add(newButton);
		add(continueButton);
	}
}
