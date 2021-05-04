package FileExplorerJava;

import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import java.awt.Desktop;

public class FileBrowser implements Runnable {

    private DefaultMutableTreeNode root;

    private DefaultTreeModel treeModel;

    private JTree tree;

    private Desktop desktop;

    private String value;

    @Override
    public void run() {
        // Creates a Jframe (UI stuff)
        JFrame frame = new JFrame("File Browser");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Instantiate java Desktop
        if (!Desktop.isDesktopSupported()) {
            System.out.println("Desktop not supported");
            // not sure if this is correct but delete the JFrame
            frame.setVisible(false); // you can't see me!
            frame.dispose(); // Destroy the JFrame object
            return;
        }
        desktop = Desktop.getDesktop();

        // get first directory(a node), then creates a tree from the node.
        File fileRoot = new File("C:/");
        root = new DefaultMutableTreeNode(new FileNode(fileRoot));
        treeModel = new DefaultTreeModel(root);

        // goes from a generic tree node into a JTree which is usable with the Jframe
        // (UI)
        tree = new JTree(treeModel);
        tree.setShowsRootHandles(true);

        // add listener for opening files
        tree.addTreeSelectionListener(new TreeSelectionListener() { // https://docs.oracle.com/javase/tutorial/uiswing/events/treeselectionlistener.html
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                // nothing is selected
                if (node == null)
                    return;

                // if node is a leaf and double click open
                if (node.isLeaf()) {

                    // get the path
                    TreePath treepath = e.getPath();
                    // System.out.println("Java: " + treepath.getLastPathComponent());
                    Object elements[] = treepath.getPath();
                    for (int i = 0, n = elements.length; i < n; i++) {
                        // System.out.print("->" + elements[i]);
                        value += elements[i] + "\\";
                    }
                    System.out.println(value.substring(4));
                    // do something with the above object
                    File test = new File(value.substring(4));
                    value =" C:\\";

                    try {
                        if (test.exists())
                            desktop.open(test);
                            
                    } catch (IOException sex) {
                        sex.printStackTrace();
                    }

                }

            }
        });
        JScrollPane scrollPane = new JScrollPane(tree);

        // Add UI elements
        frame.add(scrollPane);
        frame.setLocationByPlatform(true);
        frame.setSize(640, 480);
        frame.setVisible(true);

        // Instantiates a CreateChildNode
        CreateChildNodes ccn = new CreateChildNodes(fileRoot, root);
        new Thread(ccn).start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new FileBrowser());
    }

}