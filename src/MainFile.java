import java.io.File;

public class MainFile {


    public static void main(String[] args) {
        /*
        File file = new File(".\\.gitignore");
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        File dir = new File("E:\\JAVA\\basejava\\");
        System.out.println(dir.isDirectory());
        String[] files = dir.list();
        if (files != null) {
            for (String s : files) {
                System.out.println(s);
            }
        }

        try (FileInputStream fis = new FileInputStream(".\\.gitignore")) {
            System.out.println(fis.read());
        } catch (IOException e) {
            e.printStackTrace();
        }
        */

        // print directory content with recursion
        File directory = new File(".\\");
        printFileList(directory);

    }

    private static void printFileList(File dir) {
        File[] fileArray = dir.listFiles();
        if (fileArray != null) {
            for (File f : fileArray) {
                if (f.isDirectory()) {
                    addSpaces(f);
                    System.out.println("Directory: " + f.getName());
                    printFileList(f);
                    System.out.println(); // to separate each folder`s file list
                } else {
                    addSpaces(f);
                    System.out.println(f.getName());
                }
            }
        }
    }

    private static void addSpaces(File f) {
        int spaces = (int) f.getPath().chars().filter(ch -> ch == '\\').count();
        for (int i = 0; i < spaces; i++) {
            System.out.print("\t");
        }
    }

}

