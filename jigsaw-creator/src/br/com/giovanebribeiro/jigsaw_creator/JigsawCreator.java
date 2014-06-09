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
	
	private static final String PIECES_FOLDER="pieces";
	private static final String EXT="png";
	

	/**
	 * 
	 * @param countLines
	 * @param countColumns
	 */
	public JigsawCreator(int countColumns,int countLines, File jigsawImage) {
		super();
		this.countColumns=countColumns;
		this.countLines=countLines;
		this.jigsawImage=jigsawImage;
		this.jigsaw=new LinkedList<LinkedList<Piece>>();
	}
	
	/**
	 * 
	 * @param countLines
	 * @param countColumns
	 */
	private void makePieces(){
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
		//each piece has 2cm x 2cm
		BufferedImage jigsaw=new BufferedImage(countColumns*2,countLines*2,BufferedImage.TYPE_INT_ARGB);
		Graphics gJigsaw=jigsaw.getGraphics();
		
		for(int i=0;i<3;i++){
			LinkedList<Piece> line=this.jigsaw.get(i);
			for(int j=0;j<4;j++){
				Piece p=line.get(j);
				String pieceFilename=p.toFilename();
				
				String path=PIECES_FOLDER+"/"+pieceFilename+"."+EXT;
				BufferedImage imagePiece=ImageIO.read(new File(path));
				gJigsaw.drawImage(imagePiece,0,0,null);
				//System.out.println("("+i+","+j+") - "+line.get(j).toFilename());
			}
		}
		
		ImageIO.write(jigsaw, EXT.toUpperCase(), this.jigsawImage);
	}
	
	public void create() throws IOException{
		this.makePieces();
		this.mountFigure();
	}
	
	public static void main(String[] args){
		JigsawCreator jc=new JigsawCreator(3,4,new File("teste.png"));
		try {
			jc.create();
			System.out.println("Acabou!");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		for(int i=0;i<3;i++){
//			LinkedList<Piece> line=jc.jigsaw.get(i);
//			for(int j=0;j<4;j++){
//				System.out.println("("+i+","+j+") - "+line.get(j).toFilename());
//			}
//		}
	}
}
