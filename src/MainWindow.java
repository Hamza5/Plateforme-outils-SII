import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.net.URISyntaxException;
import java.net.URL;

public class MainWindow extends JFrame {
    private JTabbedPane tabs; // The main tabs
    public MainWindow(){
        super();
        setTitle("Plateforme d'outils");
        tabs = new JTabbedPane();
        setPreferredSize(new Dimension(600, 400));
        setContentPane(tabs);
        // Add every component to the tabs automatically
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                return s.endsWith(".class");
            }
        };
        try {
            URL pluginsURL = loader.getResource("Plugins");
            if(pluginsURL != null){
                File tabsDirectory = new File(pluginsURL.toURI());
                File[] classFiles = tabsDirectory.listFiles(filter);
                for (File c : classFiles){
                    try {
                        String className = tabsDirectory.getName() + '.' + c.getName().substring(0, c.getName().length() - 6); // Package.Class
                        Class<?> tabClass = loader.loadClass(className);
                        if(Component.class.isAssignableFrom(tabClass)){
                            tabs.add((Component)tabClass.newInstance());
                        }
                    }
                    catch (ClassNotFoundException | InstantiationException | IllegalAccessException | LinkageError e){
                        JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else throw new FileNotFoundException("Can not find the Plugins package");
        }
        catch (FileNotFoundException | URISyntaxException e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
    public static void main(String[] args){ // Show the GUI
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            // This should never happen !
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainWindow mainWindow = new MainWindow();
                mainWindow.setDefaultCloseOperation(EXIT_ON_CLOSE);
                mainWindow.pack();
                mainWindow.setLocationRelativeTo(null);
                mainWindow.setVisible(true);
            }
        });
    }
}