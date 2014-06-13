package br.com.giovanebribeiro.jigsaw_creator.pieces;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * Represents a jigsaw piece. A piece have 4 edges:<br>
 * <ul>
 * <li> left (1) </li>
 * <li> top (2)</li>
 * <li> right (3)</li>
 * <li> down (4)</li>
 * </ul> 
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
public class Piece {
	private Edge edgeLeft;
	private Edge edgeTop;
	private Edge edgeRight;
	private Edge edgeDown;
	private BufferedImage image;
	
	private static final Random rand=new Random(6438162837627364L);
	private static final String PIECES_FOLDER="pieces";
	public static final String EXT="png";

	/**
	 * @return the edgeLeft
	 */
	public Edge getEdgeLeft() {
		return edgeLeft;
	}

	/**
	 * @param edgeLeft the edgeLeft to set
	 */
	public void setEdgeLeft(Edge edgeLeft) {
		this.edgeLeft = edgeLeft;
	}

	/**
	 * @return the edgeTop
	 */
	public Edge getEdgeTop() {
		return edgeTop;
	}

	/**
	 * @param edgeTop the edgeTop to set
	 */
	public void setEdgeTop(Edge edgeTop) {
		this.edgeTop = edgeTop;
	}

	/**
	 * @return the edgeRight
	 */
	public Edge getEdgeRight() {
		return edgeRight;
	}

	/**
	 * @param edgeRight the edgeRight to set
	 */
	public void setEdgeRight(Edge edgeRight) {
		this.edgeRight = edgeRight;
	}

	/**
	 * @return the edgeDown
	 */
	public Edge getEdgeDown() {
		return edgeDown;
	}

	/**
	 * @param edgeDown the edgeDown to set
	 */
	public void setEdgeDown(Edge edgeDown) {
		this.edgeDown = edgeDown;
	}
	
	private void makeFilename() throws IOException{
		StringBuilder builder=new StringBuilder("piece");
		builder.append("_");
		builder.append(getEdgeLeft().name().toLowerCase());
		builder.append("-");
		builder.append(getEdgeTop().name().toLowerCase());
		builder.append("-");
		builder.append(getEdgeRight().name().toLowerCase());
		builder.append("-");
		builder.append(getEdgeDown().name().toLowerCase());
		
		String path=PIECES_FOLDER+"/"+builder.toString()+"."+EXT;
		System.out.println(path);
		this.image=ImageIO.read(new File(path));
	}
	/**
	 * 
	 * @return
	 */
	public BufferedImage getImage(){
		return this.image;
	}

	/**
	 * Mount a random piece based on previous pieces
	 *  
	 * @param previousPieceLine
	 * @param pieceColumnTop
	 * @param pieceColumnDown
	 * @param line
	 * @param column
	 * @param totalLines
	 * @param totalColumns
	 * @return a random piece
	 * @throws IOException 
	 */
	public static Piece getRandomPiece(Piece previousPieceLine, Piece pieceColumnTop,int line, int column, int totalLines, int totalColumns) throws IOException{
		/*
		 * get the edges
		 */
		Edge leftEdge=Edge.EXPLODED;
		Edge rightEdge=Edge.EXPLODED;
		Edge topEdge=Edge.EXPLODED;
		
		//the down edge is random.
		Edge downEdge=null;
		boolean randomBoolean=rand.nextBoolean();
		downEdge=(randomBoolean)?Edge.EXPLODED:Edge.IMPLODED;
		
		// closed edges
		if(line==0){
			topEdge=Edge.CLOSED;
		}
		
		if(line==totalLines-1){
			downEdge=Edge.CLOSED;
		}
		
		if(column==0){
			leftEdge=Edge.CLOSED;
		}
		
		if(column==totalColumns-1){
			rightEdge=Edge.CLOSED;
		}
		
		//left edge
		if(!leftEdge.equals(Edge.CLOSED) && previousPieceLine!=null){
			if(previousPieceLine.getEdgeRight().equals(Edge.IMPLODED)){
				leftEdge=Edge.EXPLODED;
			}
			
			if(previousPieceLine.getEdgeRight().equals(Edge.EXPLODED)){
				leftEdge=Edge.IMPLODED;
			}
		}
		
		//right edge
		if(!rightEdge.equals(Edge.CLOSED)/* && previousPieceLine!=null*/){
			Edge[] edges=Edge.values();
			rightEdge=edges[rand.nextInt(edges.length)];
			while(true){
				if(rightEdge.equals(Edge.CLOSED)){
					rightEdge=edges[rand.nextInt(edges.length)];
				}else{
					break;
				}
			}
//			if(previousPieceLine.getEdgeLeft().equals(Edge.IMPLODED)){
//				rightEdge=Edge.EXPLODED;
//			}
//			
//			if(previousPieceLine.getEdgeLeft().equals(Edge.EXPLODED)){
//				rightEdge=Edge.IMPLODED;
//			}
		}
		
		//top edge
		if(!topEdge.equals(Edge.CLOSED) && pieceColumnTop!=null){
			if(pieceColumnTop.getEdgeDown().equals(Edge.EXPLODED)){
				topEdge=Edge.IMPLODED;
			}
			
			if(pieceColumnTop.getEdgeDown().equals(Edge.IMPLODED)){
				topEdge=Edge.EXPLODED;
			}
		}
		
		Piece ret=new Piece();
		ret.setEdgeDown(downEdge);
		ret.setEdgeLeft(leftEdge);
		ret.setEdgeRight(rightEdge);
		ret.setEdgeTop(topEdge);
		ret.makeFilename();
		
		return ret;
	}
}
