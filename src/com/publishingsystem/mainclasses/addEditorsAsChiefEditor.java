package com.publishingsystem.mainclasses;
import com.publishingsystem.mainclasses.Role;
import com.publishingsystem.gui.RegistrationWindow;
import com.publishingsystem.mainclasses.Academic;
import com.publishingsystem.mainclasses.Editor;
import com.publishingsystem.mainclasses.EditorOfJournal;
import com.publishingsystem.mainclasses.Journal;
import java.util.ArrayList;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class addEditorsAsChiefEditor {
	
	private Journal journal;
	private Editor editor;
	
	public addEditorsAsChiefEditor(Journal j) {
		journal = j;
	}
	
	public void setJournal(Journal j) {
		journal = j;
	}
	
	public Journal getJournal() {
		return journal;
	}
	
	public void setEditor(Editor e) {
		editor = e;
	}
	
	public Editor getEditor() {
		return editor;
	}
	
	public void addEditorAsChiefEditor() {
		System.out.println(journal);
		System.out.println(editor);
		System.out.println("Hello this is both the editor and the journal");
		ArrayList<Editor> editors = new ArrayList<Editor>();
		editors.add(editor);
		Database.registerEditors(editors);
	}
}