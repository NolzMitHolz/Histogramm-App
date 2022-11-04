import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.lang.*;

public class Main extends Frame {

    private int height;
    private int width;
    private String pathFile;
    private String destinationPath;
    private char[] alphabet;
    private int[] counter;
    private String rawText;


    public Main(){
        height = 800;
        width = 1200;
        alphabet = "0abcdefghijklmnopqrstuvwxyz".toCharArray();
        counter = new int[27];
    }

    public static void main(String[] args) {
        Main mainClass = new Main();

        mainClass.run();
    }

    private void run(){
        createWindow();

    }
    private void createWindow() {
        JFrame mainWindow = new JFrame("Test");
        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        JButton fileButtonSource = new JButton("select Source File");
        JButton fileButtonDestination = new JButton("select Destination File");
        JButton confirm = new JButton("confirm");

        JTextField pathText = new JTextField();
        JTextField destinationText = new JTextField();
        JTextField titleText = new JTextField("Histogramm");




        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 40;      //make this component tall
        c.insets = new Insets(0,200,100,200);
        c.weightx = 0.0;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(titleText, c);

        c.fill = GridBagConstraints.CENTER;
        c.ipady = 0;      //make this component tall
        c.insets = new Insets(25,200,0,200);
        c.weightx = 0.0;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 3;
        panel.add(confirm, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(20,200,0,0);
        c.weightx = 0.5;
        c.gridwidth = 1;
        c.ipady = 2;
        c.gridx = 0;
        c.gridy = 1;
        panel.add(fileButtonSource, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 2;
        panel.add(fileButtonDestination, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(20,20,0,200);
        c.weightx = 0.5;
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 2;
        panel.add(destinationText, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 1;
        panel.add(pathText, c);


        Border border1 = BorderFactory.createLineBorder(SystemColor.black, 2);

        titleText.setHorizontalAlignment(JTextField.CENTER);
        Font font = new Font("Calibri", Font.BOLD,40);
        titleText.setFont(font);
        titleText.setEditable(false);
        titleText.setBackground(Color.lightGray);
        titleText.setBorder(null);

        pathText.setHorizontalAlignment(JTextField.CENTER);
        Font fontText = new Font("Calibri", Font.BOLD, 14);
        pathText.setFont(fontText);
        pathText.setEditable(false);
        pathText.setBackground(Color.lightGray);
        pathText.setBorder(border1);

        destinationText.setHorizontalAlignment(JTextField.CENTER);
        destinationText.setFont(fontText);
        destinationText.setEditable(false);
        destinationText.setBackground(Color.lightGray);
        destinationText.setBorder(border1);

        fileButtonSource.setFont(fontText);
        fileButtonSource.setBackground(Color.lightGray);
        fileButtonSource.setBorder(border1);

        fileButtonDestination.setFont(fontText);
        fileButtonDestination.setBackground(Color.lightGray);
        fileButtonDestination.setBorder(border1);

        confirm.setFont(fontText);
        confirm.setBackground(Color.lightGray);
        confirm.setBorder(border1);

        confirm.setPreferredSize(new Dimension(100,  33));

        panel.setBackground(Color.lightGray);
        panel.setSize(width,height);


        pathText.setEditable(false);
        destinationText.setEditable(false);

        fileButtonSource.addActionListener(e -> {
            openFileExpSource();
            pathText.setText(getPathFile());

        });
        fileButtonDestination.addActionListener(e -> {
            openFileExpDestination();
            destinationText.setText(getDestinationPath());
        });
        confirm.addActionListener(e -> {
            leseDatei();
            try {
                histogramm();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        mainWindow.setSize(width,height);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.add(panel);
        mainWindow.setVisible(true);
    }

    private void histogramm() throws IOException {
        rawText = allesKlein(rawText);
        char[] textToChar = rawText.toCharArray();

        for (char c : textToChar){
            vergleichBuchstaben(c);
        }

        int maxBuchstabe = findMax();

        double prozent = berechneProzent(maxBuchstabe);

        gebeAus(prozent, maxBuchstabe);

        Desktop desktop = Desktop.getDesktop();
        File file = new File(getDestinationPath());
        desktop.open(file);
    }

    private void openFileExpSource(){
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        String someString = chooser.getSelectedFile().toString();
        setPathFile(someString);
    }
    private void openFileExpDestination(){
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        String someString = chooser.getSelectedFile().toString();
        setDestinationPath(someString);
    }

    private void leseDatei(){
        StringBuilder result = new StringBuilder();
        try {
            //Die Datei wird in myObj gespeichert. Das, was in Klammern steht, ist der Dateipfad der Datei.
            File myObj = new File(getPathFile());
            //myReader liest jede zeile aus und solange es eine weitere Zeile gibt, wird diese in result gespeichert

                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    result.append(myReader.nextLine());
                }
                setText(result.toString());
                myReader.close();

        } catch (FileNotFoundException ignored) {

        }
    }
    private String allesKlein(String finalText){
        StringBuilder result = new StringBuilder();
        char[] str =finalText.toCharArray();
        for(int i=0;i<str.length;i++)
        {
            if(str[i]>='A' && str[i]<='Z')
            {
                str[i]=(char)((int)str[i]+32);
            }
            result.append(str[i]);
        }
        return result.toString();
    }
    private void vergleichBuchstaben(char c) {
        for (int y = 0; y < alphabet.length; y++) {
            if (c == alphabet[y]) {
                counter[y]++;
            }
        }
        if (c >= 'a' && c <= 'z'){
            counter[0] = counter[0] + 1;
        }
    }
    private int findMax(){
        int nowNumber = 1;
        for (int i = 2; i<counter.length; i++){
            if (counter[nowNumber]<counter[i]){
                nowNumber = i;
            }
        }

        return nowNumber;
    }
    private double berechneProzent(int maxNumber){
        return (100.0/counter[maxNumber]);
    }
    private void gebeAus(double prozent, int maxNumber) {
        String[] result = new String[alphabet.length];
        Arrays.fill(result, "");
        for (int i = 1; i<alphabet.length; i++){
            double berechnePro = (int) (prozent*counter[i]);

            for (int j = 0; j<berechnePro; j++){
                result[i] += "*";
            }
        }
        schreibeDatei(result, maxNumber);
    }
    private void schreibeDatei(String[] sterne, int maxNumber){
        try {
            FileWriter myWriter = new FileWriter(getDestinationPath());
            for (int i=1; i< sterne.length;i++){
                myWriter.write(alphabet[i] + ": " + sterne[i] + "\n");
            }
            myWriter.write( "Es wurden " + counter[0] + " Buchstaben gezählt\n" +
                    "Der am meisten benutze Buchstabe ist: " + alphabet[maxNumber] + "\n" +
                    counter[maxNumber]/100.0 + " Buchstaben sind ein '*'");
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }




    private void setPathFile(String pathFile){
        this.pathFile = pathFile;
    }
    private String getPathFile(){
        return pathFile;
    }
    private void setText(String text){
        rawText = text;
    }
    private void setDestinationPath(String destinationPath){
        this.destinationPath = destinationPath;
    }
    private String getDestinationPath(){
        return destinationPath;
    }
}