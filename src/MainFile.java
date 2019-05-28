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
        printFileList(directory, "");

    }

    private static void printFileList(File dir, String space) {
        File[] fileArray = dir.listFiles();
        if (fileArray != null) {
            for (File f : fileArray) {
                if (f.isDirectory()) {
                    System.out.println(space + f.getName() );
                    printFileList(f, space + "\t");
                    System.out.println(); // to separate each folder`s file list
                } else {
                    System.out.println(space + f.getName());
                }
            }
        }
    }



}

