package views;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.imageio.ImageIO;

import views.NewGameView;
import views.GameView;


@SuppressWarnings("serial")
public class IntroView extends View 
{	
	private BufferedImage bgImage;
	       	
	@Override
	protected void paintComponent(Graphics g)
	{
	    super.paintComponent(g);
	    g.drawImage(bgImage, 0, 0, null);
	}
	
	public IntroView(Observer mainWindow)
	{
		super();
		
		observable.addObserver(mainWindow);
		
		try
		{
			this.bgImage = ImageIO.read(new File("Images\\clue-box.jpg"));
		}
		catch(IOException e)
		{
			System.out.println("ERRO ao carregar imagem");
		}
		observable.changePanel(this);
	}

	public void setupUI()
	{		
		JButton newButton = new JButton("Novo Jogo");
		newButton.setAlignmentX(Component.CENTER_ALIGNMENT);		
		newButton.addActionListener(e -> {
			observable.changePanel(new NewGameView());
		});
		

		JButton continueButton = new JButton("Continuar");
		continueButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		continueButton.addActionListener(e -> {
			JFileChooser chooser = new JFileChooser();
		    FileNameExtensionFilter filter = new FileNameExtensionFilter("txt", "txt");
		    chooser.setFileFilter(filter);
		    
		    if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
		    {
		       observable.changePanel(new GameView(chooser.getSelectedFile().getName()));
		    }		
		});
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		setBorder(BorderFactory.createEmptyBorder(350, 0, 0, 0));
		
		add(newButton);
		add(Box.createRigidArea(new Dimension(0, 30)));
		add(continueButton);
	}
}
