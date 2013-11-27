package binaparts.util;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;

public class ProgressBarJPanel extends JPanel
implements ActionListener
{
private JProgressBar progressBar;
private Timer timer;
public Thread updateThread;
public final static int ONE_SECOND = 1000;
private JTextArea taskOutput;
private String newline = "\n";
int timeNow= 0;
int progress = 0;

public ProgressBarJPanel()
{
super(new BorderLayout());

progressBar = new JProgressBar(0, 100);
progressBar.setValue(0);
progressBar.setStringPainted(true);
taskOutput = new JTextArea(5, 20);
taskOutput.setMargin(new Insets(5,5,5,5));
taskOutput.setEditable(false);
taskOutput.setCursor(null);

JPanel panel = new JPanel();
panel.add(progressBar);

add(panel, BorderLayout.PAGE_START);
add(new JScrollPane(taskOutput), BorderLayout.CENTER);
setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

//Create a timer.
timer = new Timer(ONE_SECOND, new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent evt) {
        progressBar.setValue(progress);
        progress+=10;
        String s = "now at "+progress+"%";
        if (s != null) {
            taskOutput.append(s + newline);
            taskOutput.setCaretPosition(
                    taskOutput.getDocument().getLength());
        }
    }
});

}

public void end()
{
timer.stop();
}

public void startProgress()
{
timer.start();
}

/**
* Create the GUI and show it. For thread safety, this method should be
* invoked from the event-dispatching thread.
*/
private static void createAndShowGUI()
{
//Make sure we have nice window decorations.
JFrame.setDefaultLookAndFeelDecorated(true);

//Create and set up the window.
JFrame frame = new JFrame("ProgressBarDemo");
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//Create and set up the content pane.
JComponent newContentPane = new ProgressBarJPanel();
newContentPane.setOpaque(true); //content panes must be opaque
frame.setContentPane(newContentPane);

//Display the window.
frame.pack();
frame.setVisible(true);
}

public static void main(String[] args)
{
        createAndShowGUI();
}

@Override
public void actionPerformed(ActionEvent e)
{
throw new UnsupportedOperationException("Not supported yet.");
}
}