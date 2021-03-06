package br.com.giovanebribeiro.jigsaw_creator.util;

import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Messages properties<br>
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
public class Messages {
	/**
	 * The keys for messages<br>
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
	public enum MessageKey{
		VERSION,
		BUILD,
		DATE,
		LABEL_FILE_EXTENSION,
		LABEL_ROWS,
		LABEL_COLUMNS,
		LABEL_BUTTON_GENERATE,
		LABEL_BUTTON_ABOUT,
		LABEL_VERSION,
		LABEL_BUILD,
		LABEL_CREATED_BY,
		LABEL_LICENCE,
		MESSAGE_PIECE_SIZE,
		TITLE_FILE_CHOOSER,
		MESSAGE_ERROR_NUMBER_ROWS,
		MESSAGE_TITLE_PARAMETER_ERROR,
		MESSAGE_ERROR_MINIMUM_NUMBER_ROWS,
		MESSAGE_ERROR_WRONG_NUMBER_COLUMNS,
		MESSAGE_ERROR_MINIMUM_NUMBER_COLUMNS,
		MESSAGE_ERROR_EMPTY_FOLDER,
		MESSAGE_INFO_CREATING_THE_IMAGE,
		MESSAGE_INFO_SAVING_THE_IMAGE,
		MESSAGE_INFO_IMAGE_SAVED_IN,
		MESSAGE_INFO_IMAGE_CREATED
		;
		@Override
		public String toString(){
			switch(this){
			case BUILD:
				return "build";
			case DATE:
				return "date";
			case VERSION:
				return "version";
			case LABEL_FILE_EXTENSION:
				return "label.file_extension";
			case LABEL_ROWS:
				return "label.rows";
			case LABEL_COLUMNS:
				return "label.columns";
			case LABEL_BUTTON_GENERATE:
				return "label.button.generate";
			case LABEL_BUTTON_ABOUT:
				return "label.button.about";
			case MESSAGE_PIECE_SIZE:
				return "message.piece_size";
			case TITLE_FILE_CHOOSER:
				return "title.file_chooser";
			case LABEL_VERSION:
				return "label.version";
			case LABEL_BUILD:
				return "label.build";
			case LABEL_CREATED_BY:
				return "label.created_by";
			case LABEL_LICENCE:
				return "label.licence";
			case MESSAGE_ERROR_NUMBER_ROWS:
				return "message.error_number_rows";
			case MESSAGE_TITLE_PARAMETER_ERROR:
				return "message.title.parameter_error";
			case MESSAGE_ERROR_MINIMUM_NUMBER_ROWS:
				return "message.error_minimum_number_rows";
			case MESSAGE_ERROR_WRONG_NUMBER_COLUMNS:
				return "message.error_wrong_number_columns";
			case MESSAGE_ERROR_MINIMUM_NUMBER_COLUMNS:
				return "message.error_minimum_number_columns";
			case MESSAGE_ERROR_EMPTY_FOLDER:
				return "message.error_empty_folder";
			case MESSAGE_INFO_CREATING_THE_IMAGE:
				return "message.info_creating_the_image";
			case MESSAGE_INFO_SAVING_THE_IMAGE:
				return "message.info_saving_the_image";
			case MESSAGE_INFO_IMAGE_SAVED_IN:
				return "message.info_image_saved_in";
			case MESSAGE_INFO_IMAGE_CREATED:
				return "message.info_image_created";
			default:
				return "";
			}
		}
	}
	/*
	 * 
	 */
	private static Messages instance=null;
	private Properties prop;
	/*
	 * 
	 */
	private Messages(){
		this.prop=new Properties();
		ResourceBundle rb=ResourceBundle.getBundle("messages",Locale.getDefault());	
		Set<String> keySet=rb.keySet();
		for(Iterator<String> it=keySet.iterator();it.hasNext();){
			String key=it.next();
			this.prop.put(key, rb.getString(key));
		}
	}
	/**
	 * @return The unique instance of <code>Messages</code>
	 */
	public static Messages getInstance(){
		if(instance==null){
			instance=new Messages();
		}
		return instance;
	}
	/**
	 * @param key The key to message
	 * @return The message
	 */
	public String get(MessageKey key){
		return this.prop.getProperty(key.toString());
	}
}
