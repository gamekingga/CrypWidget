package com.ggl.filebrowser.model;

import java.awt.Dimension;
import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
 
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
 
import com.ggl.filebrowser.view.renderer.DateRenderer;
 
 
public class FileTableModel extends AbstractTableModel {
 
    private static final long   serialVersionUID    = 
            1661290177236678656L;
     
    private List<List<Object>> rows;
     
    private String[] columns = {"Icon", "File", "Size",
            "Last Modified", "Read", "Write", "Execute", "Hidden", 
            "Directory", "File"};
     
    public FileTableModel() {
        this.rows = new ArrayList<List<Object>>();
        DateFormat.getDateInstance(DateFormat.DEFAULT);
    }
     
    @Override
    public int getColumnCount() {
        return columns.length;
    }
 
    @Override
    public int getRowCount() {
        return rows.size();
    }
 
    @Override
    public Object getValueAt(int row, int column) {
        return rows.get(row).get(column);
    }
     
    @Override
    public Class<?> getColumnClass(int column) {
        switch (column) {
            case 0:
                return ImageIcon.class;
            case 2:
                return Long.class;
            case 3:
                return Date.class;
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                return Boolean.class;
            default:
                return String.class;
        }
    }
     
    @Override
    public String getColumnName(int column) {
        return columns[column];
    }
 
    public void addRow(FileBrowserModel model, FileNode fileNode) {
        File file = fileNode.getFile();
         
        List<Object> list = new ArrayList<Object>();
        list.add(model.getFileIcon(file));
        list.add(model.getFileText(file));
        list.add(file.length());
        list.add(new Date(file.lastModified()));
        list.add(file.canRead());
        list.add(file.canWrite());
        list.add(file.canExecute());
        list.add(file.isHidden());
        list.add(file.isDirectory());
        list.add(file.isFile());
        list.add(fileNode);
         
        this.rows.add(list);
    }
     
    public void removeRows() {
        this.rows.clear();
    }
     
    public int setColumnWidths(JTable table) {
        DefaultTableCellRenderer centerRenderer = 
                new DateRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(3)
            .setCellRenderer(centerRenderer);
         
        int width = setColumnWidth(table, 0, 35);
        width += setColumnWidth(table, 1, 200);
        width += setColumnWidth(table, 2, 70);
        width += setColumnWidth(table, 3, 80);
        width += setColumnWidth(table, 4, 0);
        width += setColumnWidth(table, 5, 0);
        width += setColumnWidth(table, 6, 0);
        width += setColumnWidth(table, 7, 0);
        width += setColumnWidth(table, 8, 0);
        width += setColumnWidth(table, 9, 0);
         
        return width + 30;
    }
     
    private int setColumnWidth(JTable table, int column, int width) {
        TableColumn tableColumn = table.getColumnModel()
                .getColumn(column);
        JLabel label = new JLabel((String) tableColumn.getHeaderValue());
        Dimension preferred = label.getPreferredSize();
        width = Math.max(width, (int) preferred.getWidth() + 14);
        tableColumn.setPreferredWidth(width);
        tableColumn.setMinWidth(width);
         
        return width;
    }
 
}