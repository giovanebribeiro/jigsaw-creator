package br.com.giovanebribeiro.jigsaw_creator.facade;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import br.com.giovanebribeiro.jigsaw_creator.pieces.Piece;
import br.com.giovanebribeiro.jigsaw_creator.util.FileExtensions;
import br.com.giovanebribeiro.jigsaw_creator.util.Messages;
import br.com.giovanebribeiro.jigsaw_creator.util.Messages.MessageKey;
/**
 * Task to execute the jigsaw creator
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
 * @since Jun 13, 2014
 */
public class JigsawCreator extends SwingWorker<Void,Void> {
	private int countColumns;
	private int countLines;
	private File jigsawImage;
	private JProgressBar jpb;
	
	private static final int TAM_PIECE=2;
	private static final int CENTIMETER_TO_PIXEL=28; //1 centimeter has x pixels.
	private static final String jigsawFilename="jigsaw";
	
	/**
	 * 
	 * @param countLines
	 * @param countColumns
	 */
	public JigsawCreator(int countLines, int countColumns, File jigsawFolder, FileExtensions extension, JProgressBar progressBar) {
		super();
		this.countColumns=countColumns;
		this.countLines=countLines;
		this.jigsawImage=new File(jigsawFolder,jigsawFilename+"_"+countLines+"_"+countColumns+"."+extension.name().toLowerCase());
		this.jpb=progressBar;
		this.jpb.setValue(0);
	}

	@Override
	protected Void doInBackground() throws Exception {
		int progress=0;
		setProgress(progress);
		
		int width=countColumns*TAM_PIECE;
		int height=countLines*TAM_PIECE;
		
		//converting to pixels
		width*=CENTIMETER_TO_PIXEL;
		height*=CENTIMETER_TO_PIXEL;
		BufferedImage jigsawImage=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		Graphics gJigsaw=jigsawImage.getGraphics();
		
		int totalIterations=this.countColumns*this.countLines;
		totalIterations++;
		
		LinkedList<LinkedList<Piece>> jigsaw=new LinkedList<LinkedList<Piece>>();
		
		LinkedList<Piece> line=null;
		for(int i=0;i<countLines;i++){
			line=new LinkedList<Piece>();
			
			for(int j=0;j<countColumns;j++){
				LinkedList<Piece> previousLine=null;
				if(i!=0){
					previousLine=jigsaw.get(i-1);
				}
				
				Piece piece=null;
				if(j==0){
					if(previousLine!=null){
						piece=Piece.getRandomPiece(null, previousLine.get(j), i, j, countLines, countColumns);
					}else{
						piece=Piece.getRandomPiece(null, null, i, j, countLines, countColumns);
					}
				}else{
					Piece previousPiece=line.get(j-1);
					if(previousLine!=null){
						piece=Piece.getRandomPiece(previousPiece, previousLine.get(j), i, j, countLines, countColumns);
					}else{
						piece=Piece.getRandomPiece(previousPiece, null, i, j, countLines, countColumns);
					}
				}
				line.add(piece);
				/*
				 * Inserting the image
				 */
				int y=i*TAM_PIECE*CENTIMETER_TO_PIXEL;
				int x=j*TAM_PIECE*CENTIMETER_TO_PIXEL;
				gJigsaw.drawImage(piece.getImage(),x,y,null);
				
				progress++;
				setProgress(Math.min(progress*100/totalIterations, 100));
			}
			jigsaw.add(line);
		}
		
		ImageIO.write(jigsawImage, Piece.EXT.toUpperCase(), this.jigsawImage);
		progress++;
		setProgress(Math.min(progress*100/totalIterations, 100));
		
		return null;
	}
	
	@Override
	protected void done() {
		Toolkit.getDefaultToolkit().beep();
		JOptionPane.showMessageDialog(null, Messages.getInstance().get(MessageKey.MESSAGE_INFO_IMAGE_CREATED),"",JOptionPane.INFORMATION_MESSAGE);
	}
}
