package com.esir.sr.sweetsnake.game.engine;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.esir.sr.sweetsnake.api.ISweetSnakeElement;
import com.esir.sr.sweetsnake.api.ISweetSnakePlayer;
import com.esir.sr.sweetsnake.constants.SweetSnakeGameConstants;
import com.esir.sr.sweetsnake.enumeration.SweetSnakeDirection;
import com.esir.sr.sweetsnake.game.SweetSnakeSnake;
import com.esir.sr.sweetsnake.game.SweetSnakeSweet;
import com.esir.sr.sweetsnake.game.map.Board;

@Service
public class Engine {

	private Board board;
	private Map<ISweetSnakePlayer, ISweetSnakeElement> mapPlayers;
	
	
	public void update(SweetSnakeDirection direction, ISweetSnakePlayer player) {
		ISweetSnakeElement snake = mapPlayers.get(player);
		int x = (snake.getX() + direction.getValue()[0] + SweetSnakeGameConstants.GRID_SIZE) % SweetSnakeGameConstants.GRID_SIZE;
		int y = (snake.getY() + direction.getValue()[1] + SweetSnakeGameConstants.GRID_SIZE) % SweetSnakeGameConstants.GRID_SIZE;
		ISweetSnakeElement element = board.get(x, y);
		
		board.remove(snake);
		
		if(element == null) {
			snake.move(direction);
			board.set(snake);
		} else if (element.getClass() == SweetSnakeSnake.class) {
			board.set(snake);
		} else if (element.getClass() == SweetSnakeSweet.class) {
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


	public Map<ISweetSnakePlayer, ISweetSnakeElement> getMapPlayers() {
		return mapPlayers;
	}


	public void setMapPlayers(Map<ISweetSnakePlayer, ISweetSnakeElement> mapPlayers) {
		this.mapPlayers = mapPlayers;
	}
}