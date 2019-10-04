package view;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BoundedRangeModel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * This class renders a JProgressBar in a table cell.
 */
public class ProgressRenderer extends JProgressBar implements TableCellRenderer {
    
    
    /**
     * Constructor for ProgressRenderer.
     *
     * @param min
     * @param max
     */

    public ProgressRenderer(int min, int max) {
        super(min, max);
        setForeground(new java.awt.Color(189, 189, 189));
    }
    
    
    /**
     * Returns this JProgressBar as the renderer for the given table cell.
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {

        // set JProgressBar's percent complete value.
        setValue((int) ((Float) value).floatValue());
        return this;
    }
}
