package jogo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

class Deck
{
	static private Deck _deck;
	
	private List<Card> _cards;
	
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
		String[] weapons = {"Cano","Castical","ChaveInglesa","Corda","Faca","Revolver"};
		String[] rooms = {"Biblioteca","Cozinha","Entrada","Escritorio","Jardim De Inverno","Sala De Estar","Sala De Jantar","Sala De Musica","Salao De Jogos"};
		String[] suspects = {"Green","Mustard","Peacock","Plum","Scarlet","White"};
		
		_cards = new ArrayList<Card>();
		
		for(int i = 0; i < 6; i++)
		{
			Card weapon = new Card(weapons[i], Card.CardType.Weapon);
			_cards.add(weapon);
		}
		
		for(int i = 0; i < 9; i++)
		{
			Card room = new Card(rooms[i], Card.CardType.Room);
			_cards.add(room);
		}
		
		for(int i = 0; i < 6; i++)
		{
			Card suspect = new Card(suspects[i], Card.CardType.Suspect);
			_cards.add(suspect);
		}
	}
	
	public void shuffleDeck()
	{
		Collections.shuffle(_cards);
	}

	public List<Card> getKeyCards()
	{
		List<Card> keyCards = new ArrayList<Card>();
		boolean foundWeapon = false;
		boolean foundRoom = false;
		boolean foundSuspect = false;
		
		for (Iterator<Card> iterator = _cards.iterator(); iterator.hasNext();) 
		{
		    Card c = iterator.next();
		    
		    if(c.GetType() == Card.CardType.Weapon && !foundWeapon)
			{
				keyCards.add(c);
				iterator.remove();
				foundWeapon = true;
			}
			else if(c.GetType() == Card.CardType.Room && !foundRoom)
			{
				keyCards.add(c);
				iterator.remove();
				foundRoom = true;
			}
			else if(c.GetType() == Card.CardType.Suspect && !foundSuspect)
			{
				keyCards.add(c);
				iterator.remove();
				foundSuspect = true;
			}
			
			if(foundWeapon && foundRoom && foundSuspect)
			{
				break;
			}
		}
		
		return keyCards;
	}
	
	public List<ArrayList<Card>> getPlayersCards(int numPlayers)
	{
		List<ArrayList<Card>> playersCards = new ArrayList<ArrayList<Card>>();
		
		for(int i = 0; i < numPlayers; i++)
		{
			ArrayList<Card> cards = new ArrayList<Card>();
			playersCards.add(cards);
		}
		
		int j = 0; 
		for (Iterator<Card> iterator = _cards.iterator(); iterator.hasNext(); j++) 
		{
			if(j >= playersCards.size())
			{
				j = 0;
			}
			
		    Card c = iterator.next();
		    
		    playersCards.get(j).add(c);
		    iterator.remove();
		}
		
		return playersCards;
	}
	
	public void resetDeck()
	{
		_deck = null;
	}
}
