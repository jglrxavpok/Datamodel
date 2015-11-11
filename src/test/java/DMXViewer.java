import org.jglr.dmx.*;
import org.jglr.dmx.attributes.Attribute;
import org.jglr.dmx.codecs.BinaryCodec;
import org.jglr.dmx.element.Element;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DMXViewer {

    private static BinaryCodec codec;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        codec = new BinaryCodec();
        JFrame frame = new JFrame("DMXViewer");
        MenuBar bar = new MenuBar();
        Menu file = new Menu("File");
        MenuItem openFile = new MenuItem("Open file");
        DefaultMutableTreeNode root = new DefaultMutableTreeNode();
        DefaultTreeModel model = new DefaultTreeModel(root);
        JTree tree = new JTree(model);

        openFile.addActionListener(ev -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File("."));
            chooser.setMultiSelectionEnabled(true);
            chooser.showOpenDialog(frame);

            File[] files = chooser.getSelectedFiles();
            if(files.length > 0) {
                String title = "";
                for(File f : files) {
                    DefaultMutableTreeNode modelNode = new DefaultMutableTreeNode(f.getName());
                    try {
                        root.add(modelNode);
                        readDMX(f, modelNode);
                        title += f.getName()+" - ";
                    } catch (Exception e) {
                        e.printStackTrace();
                        modelNode.add(new DefaultMutableTreeNode(e.getClass().getCanonicalName()+": "+e.getMessage()));
                    }
                }
                tree.expandRow(0);
                tree.updateUI();
                frame.setTitle(title+"DMXViewer");
                frame.pack();
            }
        });

        file.add(openFile);
        bar.add(file);
        frame.setMenuBar(bar);

        frame.add(new JScrollPane(tree));

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private static void readDMX(File f, DefaultMutableTreeNode root) throws IOException, DMXException {
        Datamodel datamodel = codec.decode(5, new FileInputStream(f));

        for(Element e : datamodel.getElementList()) {
            DefaultMutableTreeNode elementNode = new DefaultMutableTreeNode(e.getName()+" ("+e.getClassName()+")");
            root.add(elementNode);

            for(Attribute attr : e) {
                DefaultMutableTreeNode attrNode = new DefaultMutableTreeNode(attr.getName());
                elementNode.add(attrNode);

                if(attr.getValue() != null) {
                    DefaultMutableTreeNode valueNode = new DefaultMutableTreeNode(attr.getValue().getType().name()+": "+DMX.toString(attr.getValue().getValue()));

                    attrNode.add(valueNode);
                }
            }
        }
    }
}
