package ru.grigoryev.game;

/**
*Class represent the board in the chess game.
*@author vgrigoryev
*@since 11.09.2017
*@version 1
*/
public class Board {
	/**
	*Size of one side of the squared bord in the chess game.
	*/
	static final int BOARD_SIZE = 8;
	/**
	*Array of figures in the game.
	*/
	private Figure[] figures;
	/**
	*position in the array.
	*/
	private int position = 0;
	/**
	*Constructor with parameters.
	*/
	public Board() {
		figures = new Figure[32];
	}
	/**
	*Method is used for adding figures in the board.
	*@param figure Figue
	*@return true if figure is added
	*/
	public boolean addFigure(Figure figure) {
		boolean isAdded = false;
		if (position < 32) {
			figures[this.position++] = figure;
			isAdded = true;
		}
		return isAdded;
	}
	/**
	*This method moves the figure or throws exceptions if it is not possible.
	*@param source source cell from where to move
	*@param destination destination cell where to move
	*@throws ImpossibleMoveException .
	*@throws OccupiedWayException .
	*@throws FigureNotFoundException .
	*@return true if movement is available
	*/
	public boolean move(Cell source, Cell destination) throws ImpossibleMoveException, OccupiedWayException, FigureNotFoundException {
		Figure current = null;
		/*
		*Проверяем имеется ли фигура в клетке, указанной
		*как источник.
		*/
		boolean isValid = false;
		int sourceIndex = 0;
		for (int i = 0; i < this.position; i++) {
			if (figures[i].isPositionSame(source)) {
				isValid = true;
				current = figures[i];
				sourceIndex = i;
				break;
			}
		}
		if (!isValid) {
			throw new FigureNotFoundException("Figure is not found");
		}
		/*
		*Проверяем может ли фигура так ходить по правилам игры.
		*/
		Cell[] way = current.performMove(destination);
		/*
		*Проверяем свободна или занята клетка, указанная
		*как конечный пункт.
		*/
		for (int i = 0; i < this.position; i++) {
			if (figures[i].isPositionSame(destination)) {
				throw new OccupiedWayException("Destination cell is occupied");
			}
		}
		/*
		*Проверяем есть ли на пути другие фигуры,
		*/
		for (int i = 0; i < this.position; i++) {
			for (int j = 0; j < way.length; j++) {
				if (figures[i].isPositionSame(way[j])) {
					throw new OccupiedWayException("Other figure is on your way.");
				}
			}
		}
		figures[sourceIndex] = current.clone(destination, figures[sourceIndex].getColor()); // Записываем фигуру в ту же ячейку в массиве, но с новыми данными клетки на доске.
		return true;
	}
}