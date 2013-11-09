package com.esir.sr.sweetsnake.game.engine;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.esir.sr.sweetsnake.api.IElement;
import com.esir.sr.sweetsnake.constants.GameConstants;
import com.esir.sr.sweetsnake.element.Snake;
import com.esir.sr.sweetsnake.element.Sweet;
import com.esir.sr.sweetsnake.enumeration.MoveDirection;
import com.esir.sr.sweetsnake.game.map.Board;
import com.esir.sr.sweetsnake.session.Player;

@Service
public class Engine {

	private Board board;
	private Map<Player, IElement> mapPlayers;
	
	
	public void update(MoveDirection direction, Player player) {
		IElement snake = mapPlayers.get(player);
		int x = (snake.getX() + direction.getValue()[0] + GameConstants.GRID_SIZE) % GameConstants.GRID_SIZE;
		int y = (snake.getY() + direction.getValue()[1] + GameConstants.GRID_SIZE) % GameConstants.GRID_SIZE;
		IElement element = board.get(x, y);
		
		board.remove(snake);
		
		if(element == null) {
			snake.move(direction);
			board.set(snake);
		} else if (element.getClass() == Snake.class) {
			board.set(snake);
		} else if (element.getClass() == Sweet.class) {
			snake.move(direction);
			board.set(snake);
			//TODO ajouter score au joueur.
		}
	}


	public Board getBoard() {
		return board;
	}


	public void setBoard(Board board) {
		this.board = board;
	}


	public Map<Player, IElement> getMapPlayers() {
		return mapPlayers;
	}


	public void setMapPlayers(Map<Player, IElement> mapPlayers) {
		this.mapPlayers = mapPlayers;
	}
}