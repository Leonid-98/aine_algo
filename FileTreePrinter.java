import java.io.File;

public class FileTreePrinter {
    public static void main(String[] args) {
        String folderPath = "/path/to/your/folder"; // Change this to the path of the folder you want to print
        File folder = new File(folderPath);

        if (folder.exists()) {
            printFileTree(folder, "");
        } else {
            System.err.println("Folder does not exist.");
        }
    }

    public static void printFileTree(File folder, String prefix) {
        if (folder.isDirectory()) {
            System.out.println(prefix + folder.getName() + "/");
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    printFileTree(file, prefix + "  ");
                }
            }
        } else {
            System.out.println(prefix + folder.getName());
        }
    }



    public static void failipuu(String tee) {

    }
}