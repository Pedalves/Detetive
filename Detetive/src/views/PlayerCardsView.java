package views;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import jogo.Facade;

@SuppressWarnings("serial")
public class PlayerCardsView extends JFrame
{
	private Facade _facade;
	
	private List<BufferedImage> _images;
	
	public PlayerCardsView()
	{
		super();
		
		setSize(900, 950);
		setLocationRelativeTo(null);
		
		setVisible(true);
		
		_facade = Facade.getInstance();
		
		setupCardsImages();
		
		JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for(int i = 0; i < _images.size(); i++)
                {
                	if(i < 3)
                	{
                        g.drawImage(_images.get(i), 0 + i * _images.get(0).getWidth(), 0, null);
                	}
                	else
                	{
                        g.drawImage(_images.get(i), 0 + (i - 3) * _images.get(0).getWidth(), _images.get(0).getHeight(), null);
                	}
                }
            }
        };
        
        add(panel);
	}
	
	private void setupCardsImages()
	{
		_images = _facade.getCurrentPlayerCardsImages();
	}
}
