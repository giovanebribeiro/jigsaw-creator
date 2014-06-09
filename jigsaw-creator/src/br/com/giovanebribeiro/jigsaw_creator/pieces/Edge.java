package br.com.giovanebribeiro.jigsaw_creator.pieces;

/**
 * Represents the edge of a piece.<br>
 * <br>
 * <br>
 * Copyright (C) 2014  Giovane Boaviagem<br>
 * <br>
 * This program is free software: you can redistribute it and/or modify<br>
 * it under the terms of the GNU General Public License as published by<br>
 * the Free Software Foundation, either version 3 of the License, or<br>
 * (at your option) any later version.<br>
 * <br>
 * This program is distributed in the hope that it will be useful,<br>
 * but WITHOUT ANY WARRANTY; without even the implied warranty of<br>
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the<br>
 * GNU General Public License for more details.<br>
 * <br>
 * You should have received a copy of the GNU General Public License<br>
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.<br>
 * <br>
 * @author giovane
 * @since Jun 8, 2014
 */
public enum Edge {
	/**
	 * A closed edge. Without connections.<br>
	 * <br>
	 */
	CLOSED,
	/**
	 * A exploded edge. With a connector.<br>
	 * <br>
	 *        #####
	 *       #     #
	 * ######       ######	
	 */
	EXPLODED,
	/**
	 * A imploded edge. With a connector
	 * 
	 * #####       #######
	 *      #     #
	 *       #####
	 */
	IMPLODED
}
