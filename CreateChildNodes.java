package FileExplorerJava;

import java.io.File;

import javax.swing.tree.DefaultMutableTreeNode;

//Starts from the given file root and then generates the tree
public class CreateChildNodes implements Runnable {

    private DefaultMutableTreeNode root;

    private File fileRoot;

    public CreateChildNodes(File fileRoot, 
            DefaultMutableTreeNode root) {
        this.fileRoot = fileRoot;
        this.root = root;
    }

    @Override
    public void run() {
        //recursion calls itself until it can't keep going deeper (files ==null)
        createChildren(fileRoot, root);
    }

    private void createChildren(File fileRoot, 
            DefaultMutableTreeNode node) {
                //lists the files at the current node
        File[] files = fileRoot.listFiles();
        if (files == null) return;
                //for each file that gets listed, add it to the tree and then if it is a directory, call this function with the node as the root
        for (File file : files) {
            DefaultMutableTreeNode childNode = 
                    new DefaultMutableTreeNode(new FileNode(file));
            node.add(childNode);
            if (file.isDirectory()) {
                createChildren(file, childNode);
            }
        }
    }

}