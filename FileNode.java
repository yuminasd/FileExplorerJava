package FileExplorerJava;

import java.io.File;

//wrapper class for the root File, so you can only call getString and not be able to alter the 
// the data values. This is part of Object Oriented Design.
public class FileNode {

        private File file;

        public FileNode(File file) {
            this.file = file;
        }

        @Override
        public String toString() {
            String name = file.getName();
            if (name.equals("")) {
                return file.getAbsolutePath();
            } else {
                return name;
            }
        }
    }