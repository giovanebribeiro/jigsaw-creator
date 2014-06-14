package br.com.giovanebribeiro.jigsaw_creator;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import br.com.giovanebribeiro.jigsaw_creator.facade.JigsawCreator;
import br.com.giovanebribeiro.jigsaw_creator.util.FileExtensions;
import br.com.giovanebribeiro.jigsaw_creator.util.Messages;
import br.com.giovanebribeiro.jigsaw_creator.util.Messages.MessageKey;
/**
 * Represents the main screen<br>
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
 * @since Jun 12, 2014
 */
public class FrameMain extends JFrame implements PropertyChangeListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1527108103757434822L;
	private static final String TITLE="Jigsaw Creator";
	private static final String AUTHOR="Giovane Boaviagem Ribeiro";
	private JPanel contentPane;
	private JTextField textFieldFolder;
	private File fileFolder;
	private JTextField textFieldRows;
	private JTextField textFieldColumns;
	private JProgressBar progressBar;
	private JComboBox<?> comboBox;
	
	static{
		
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameMain frame = new FrameMain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the frame.
	 */
	public FrameMain() {
		setResizable(false);
		
		setTitle(TITLE+" - "+Messages.getInstance().get(MessageKey.VERSION));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 385, 281);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textFieldFolder = new JTextField();
		textFieldFolder.setEditable(false);
		textFieldFolder.setBounds(12, 14, 293, 25);
		contentPane.add(textFieldFolder);
		textFieldFolder.setColumns(10);
		
		JButton buttonChooseFolder = new JButton("...");
		buttonChooseFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionGetFileFolder();
			}
		});
		buttonChooseFolder.setBounds(317, 14, 49, 25);
		contentPane.add(buttonChooseFolder);
		
		JLabel lblFileExtension = new JLabel(Messages.getInstance().get(MessageKey.LABEL_FILE_EXTENSION)+":");
		lblFileExtension.setBounds(12, 53, 112, 24);
		contentPane.add(lblFileExtension);
		
		comboBox = new JComboBox(FileExtensions.values());
		comboBox.setBounds(142, 53, 75, 24);
		contentPane.add(comboBox);
		
		progressBar = new JProgressBar(0,100);
		progressBar.setBounds(12, 206, 354, 25);
		contentPane.add(progressBar);
		
		JLabel lblRows = new JLabel(Messages.getInstance().get(MessageKey.LABEL_ROWS)+":");
		lblRows.setBounds(12, 91, 70, 24);
		contentPane.add(lblRows);
		
		JLabel lblColumns = new JLabel(Messages.getInstance().get(MessageKey.LABEL_COLUMNS)+":");
		lblColumns.setBounds(12, 129, 70, 24);
		contentPane.add(lblColumns);
		
		JButton buttonGenerate = new JButton(Messages.getInstance().get(MessageKey.LABEL_BUTTON_GENERATE));
		buttonGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionGenerate();
			}
		});
		buttonGenerate.setBounds(43, 167, 117, 25);
		contentPane.add(buttonGenerate);
		
		JButton buttonAbout = new JButton(Messages.getInstance().get(MessageKey.LABEL_BUTTON_ABOUT));
		buttonAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionButtonAbout();
			}
		});
		buttonAbout.setBounds(209, 167, 117, 25);
		contentPane.add(buttonAbout);
		
		JTextPane txtpnEachPieceHas = new JTextPane();
		txtpnEachPieceHas.setEditable(false);
		txtpnEachPieceHas.setText(Messages.getInstance().get(MessageKey.MESSAGE_PIECE_SIZE));
		txtpnEachPieceHas.setBounds(229, 51, 137, 102);
		contentPane.add(txtpnEachPieceHas);
		
		textFieldRows = new JTextField();
		textFieldRows.setBounds(142, 91, 75, 24);
		contentPane.add(textFieldRows);
		textFieldRows.setColumns(10);
		
		textFieldColumns = new JTextField();
		textFieldColumns.setColumns(10);
		textFieldColumns.setBounds(142, 129, 75, 24);
		contentPane.add(textFieldColumns);
	}
	
	private void actionGetFileFolder(){
		JFileChooser jfc=new JFileChooser();
		jfc.setLocale(Locale.getDefault());
		jfc.setDialogTitle(Messages.getInstance().get(MessageKey.TITLE_FILE_CHOOSER));
		jfc.setDialogType(JFileChooser.OPEN_DIALOG);
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		int n=jfc.showOpenDialog(this);
		if(n==JFileChooser.APPROVE_OPTION){
			this.fileFolder=jfc.getSelectedFile();
			this.textFieldFolder.setText(this.fileFolder.getAbsolutePath());
		}
	}
	
	private void actionButtonAbout(){
		StringBuilder message=new StringBuilder("");
		message.append("*** "+TITLE+" ***"+"\n");
		message.append(Messages.getInstance().get(MessageKey.LABEL_VERSION)+": "+Messages.getInstance().get(MessageKey.VERSION)+" ("+Messages.getInstance().get(MessageKey.LABEL_BUILD)+" "+Messages.getInstance().get(MessageKey.BUILD)+" - "+Messages.getInstance().get(MessageKey.DATE)+")\n");
		message.append(Messages.getInstance().get(MessageKey.LABEL_CREATED_BY)+": "+AUTHOR+"\n");
		message.append(Messages.getInstance().get(MessageKey.LABEL_LICENCE)+": GPL");
		
		JOptionPane.showMessageDialog(null, message.toString(), "", JOptionPane.PLAIN_MESSAGE);
	}
	
	private void actionGenerate(){
		/*
		 * Rows
		 */
		String sRows=this.textFieldRows.getText();
		int rows=-1;
		try{
			rows=Integer.parseInt(sRows);
		}catch(NumberFormatException e){
			JOptionPane.showMessageDialog(null, Messages.getInstance().get(MessageKey.MESSAGE_ERROR_NUMBER_ROWS), Messages.getInstance().get(MessageKey.MESSAGE_TITLE_PARAMETER_ERROR), JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(rows<=0){
			JOptionPane.showMessageDialog(null, Messages.getInstance().get(MessageKey.MESSAGE_ERROR_MINIMUM_NUMBER_ROWS), Messages.getInstance().get(MessageKey.MESSAGE_TITLE_PARAMETER_ERROR), JOptionPane.ERROR_MESSAGE);
			return;
		}
		/*
		 * Columns
		 */
		String sColumns=this.textFieldColumns.getText();
		int columns=-1;
		try{
			columns=Integer.parseInt(sColumns);
		}catch(NumberFormatException e){
			JOptionPane.showMessageDialog(null, Messages.getInstance().get(MessageKey.MESSAGE_ERROR_WRONG_NUMBER_COLUMNS), Messages.getInstance().get(MessageKey.MESSAGE_TITLE_PARAMETER_ERROR), JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(columns<=1){
			JOptionPane.showMessageDialog(null, Messages.getInstance().get(MessageKey.MESSAGE_ERROR_MINIMUM_NUMBER_COLUMNS), Messages.getInstance().get(MessageKey.MESSAGE_TITLE_PARAMETER_ERROR), JOptionPane.ERROR_MESSAGE);
			return;
		}
		/*
		 * File extension
		 */
		FileExtensions fe=(FileExtensions) this.comboBox.getSelectedItem();
		/*
		 * Folder file
		 */
		if(this.textFieldFolder.getText().equalsIgnoreCase("")){
			JOptionPane.showMessageDialog(null, Messages.getInstance().get(MessageKey.MESSAGE_ERROR_EMPTY_FOLDER), Messages.getInstance().get(MessageKey.MESSAGE_TITLE_PARAMETER_ERROR), JOptionPane.ERROR_MESSAGE);
			return;
		}
		File imageFolder=this.fileFolder;
		/*
		 * Execute the task.
		 */
		this.progressBar.setStringPainted(true);
		JigsawCreator jc=new JigsawCreator(rows, columns, imageFolder, fe, this.progressBar);
		jc.addPropertyChangeListener(this);
		jc.execute();
	}
	/**
     * Invoked when task's progress property changes.
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName()) {
            int progress = (Integer) evt.getNewValue();
            progressBar.setValue(progress);
            if(progress<99){
            	progressBar.setString(Messages.getInstance().get(MessageKey.MESSAGE_INFO_CREATING_THE_IMAGE));
            }else if(progress==99){
            	progressBar.setString(Messages.getInstance().get(MessageKey.MESSAGE_INFO_SAVING_THE_IMAGE));
            }else{
            	progressBar.setString(Messages.getInstance().get(MessageKey.MESSAGE_INFO_IMAGE_SAVED_IN)+" "+this.fileFolder.getAbsolutePath());
            }
        }
    }
}
