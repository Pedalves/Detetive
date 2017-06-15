package jogo;

import java.util.ArrayList;

public class Deck
{
	static private Deck _deck;
	
	private ArrayList<Card> _cards;
	
	private Deck()
	{
		createDeck();
	}
	
	static public Deck getInstance()
	{
		if(_deck == null)
		{
			_deck = new Deck();
		}
		
		return _deck;
	}
	
	private void createDeck()
	{
		
		_cards.add();
	}
}
