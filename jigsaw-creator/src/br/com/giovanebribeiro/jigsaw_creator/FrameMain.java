package br.com.giovanebribeiro.jigsaw_creator;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

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
public class FrameMain extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1527108103757434822L;
	private static String VERSION;
	private static String BUILD;
	private static String DATE;
	private static final String TITLE="Jigsaw Creator";
	private static final String AUTHOR="Giovane Boaviagem Ribeiro";
	private JPanel contentPane;
	private JTextField textFieldFolder;
	private JTextField textFieldRows;
	private JTextField textFieldColumns;
	private File fileFolder;
	
	static{
		InputStream is=null;
		
		Properties prop=new Properties();
		try {
			is = new FileInputStream(new File("config/VERSION"));
			prop.load(is);
			VERSION=prop.getProperty("version");
			BUILD=prop.getProperty("build");
			DATE=prop.getProperty("date");
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				if(is!=null){
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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
		setTitle(TITLE+" - "+VERSION);
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
		
		JLabel lblFileExtension = new JLabel(Messages.getInstance().get(MessageKey.LABEL_FILE_EXTENSION));
		lblFileExtension.setBounds(12, 53, 112, 24);
		contentPane.add(lblFileExtension);
		
		JComboBox<?> comboBox = new JComboBox(FileExtensions.values());
		comboBox.setBounds(142, 53, 75, 24);
		contentPane.add(comboBox);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(12, 206, 354, 25);
		contentPane.add(progressBar);
		
		JLabel lblRows = new JLabel(Messages.getInstance().get(MessageKey.LABEL_ROWS));
		lblRows.setBounds(12, 91, 70, 24);
		contentPane.add(lblRows);
		
		JLabel lblColumns = new JLabel(Messages.getInstance().get(MessageKey.LABEL_COLUMNS));
		lblColumns.setBounds(12, 129, 70, 24);
		contentPane.add(lblColumns);
		
		textFieldRows = new JTextField();
		textFieldRows.setBounds(142, 91, 75, 24);
		contentPane.add(textFieldRows);
		textFieldRows.setColumns(10);
		
		textFieldColumns = new JTextField();
		textFieldColumns.setBounds(142, 129, 75, 24);
		contentPane.add(textFieldColumns);
		textFieldColumns.setColumns(10);
		
		JButton buttonGenerate = new JButton(Messages.getInstance().get(MessageKey.LABEL_BUTTON_GENERATE));
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
		message.append("Version: "+VERSION+" (Build "+BUILD+" - "+DATE+")\n");
		message.append("Created by: "+AUTHOR+"\n");
		message.append("License: GPL");
		
		JOptionPane.showMessageDialog(null, message.toString(), "", JOptionPane.PLAIN_MESSAGE);
	}
}
