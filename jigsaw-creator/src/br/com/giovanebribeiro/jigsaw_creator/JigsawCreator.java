package br.com.giovanebribeiro.jigsaw_creator;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import br.com.giovanebribeiro.jigsaw_creator.pieces.Piece;

/**
 * Jigsaw creator.
 * 
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
 * 
 * @author giovane
 * @since Jun 8, 2014
 */
public class JigsawCreator {
	private LinkedList<LinkedList<Piece>> jigsaw;
	private int countColumns;
	private int countLines;
	private File jigsawImage;
	
	
	private static final int TAM_PIECE=2;
	private static final int CENTIMETER_TO_PIXEL=28; //1 centimeter has x pixels.
	private static final String jigsawFilename="jigsaw";

	/**
	 * 
	 * @param countLines
	 * @param countColumns
	 */
	public JigsawCreator(int countLines, int countColumns, File jigsawFolder, FileExtensions extension) {
		super();
		this.countColumns=countColumns;
		this.countLines=countLines;
		this.jigsawImage=new File(jigsawFolder,jigsawFilename+"."+extension.name().toLowerCase());
		this.jigsaw=new LinkedList<LinkedList<Piece>>();
	}
	
	/**
	 * 
	 * @param countLines
	 * @param countColumns
	 * @throws IOException 
	 */
	private void makePieces() throws IOException{
		LinkedList<Piece> line=null;
		for(int i=0;i<countLines;i++){
			line=new LinkedList<Piece>();
			if(i==0){
				for(int j=0;j<countColumns;j++){
					Piece piece=null;
					if(j==0){
						piece=Piece.getRandomPiece(null, null, i, j, countLines, countColumns);
					}else{
						Piece previousPiece=line.get(j-1);
						piece=Piece.getRandomPiece(previousPiece, null, i, j, countLines, countColumns);
					}
					line.add(piece);
				}
			}else{
				LinkedList<Piece> previousLine=this.jigsaw.get(i-1);
				for(int j=0;j<countColumns;j++){
					Piece piece=null;
					if(j==0){
						piece=Piece.getRandomPiece(null, previousLine.get(j), i, j, countLines, countColumns);
					}else{
						Piece previousPiece=line.get(j-1);
						piece=Piece.getRandomPiece(previousPiece, previousLine.get(j), i, j, countLines, countColumns);
					}
					line.add(piece);
				}
			}
			this.jigsaw.add(line);
		}
	}
	
	private void mountFigure() throws IOException{
		int width=countColumns*TAM_PIECE;
		int height=countLines*TAM_PIECE;
		
		//converting to pixels
		width*=CENTIMETER_TO_PIXEL;
		height*=CENTIMETER_TO_PIXEL;
		BufferedImage jigsaw=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		Graphics gJigsaw=jigsaw.getGraphics();
		
		
		for(int i=0;i<this.countLines;i++){
			LinkedList<Piece> line=this.jigsaw.get(i);
			for(int j=0;j<this.countColumns;j++){
				Piece p=line.get(j);
				
				int y=i*TAM_PIECE*CENTIMETER_TO_PIXEL;
				int x=j*TAM_PIECE*CENTIMETER_TO_PIXEL;
				gJigsaw.drawImage(p.getImage(),x,y,null);
				//System.out.println("("+i+","+j+") - "+line.get(j).toFilename());
			}
		}
		
		ImageIO.write(jigsaw, Piece.EXT.toUpperCase(), this.jigsawImage);
	}
	
	public void create() throws IOException{
		this.makePieces();
		this.mountFigure();
	}
	
	public static void main(String[] args){
		JigsawCreator jc=new JigsawCreator(1,2,new File("."),FileExtensions.PNG);
		try {
			jc.create();
			System.out.println("Acabou!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
