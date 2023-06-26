import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.Color;
import java.util.Stack;

/**
 * Klasa Obrazekpop przedstawia edytor obrazów
 */
public class Obrazekpop extends JFrame implements ActionListener, MouseListener{

    JButton Open = new JButton ("Open");
    JButton Savebmp = new JButton ("bmp");
    JButton Savejpg = new JButton ("jpg");
    JButton Savetiff = new JButton ("tiff");
    JButton lighter = new JButton ("Light + ");
    JButton darker = new JButton ("Light - ");
    JButton rotation = new JButton ("Rotate ");
    JButton sobel = new JButton ("Sobel ");
    JButton cut=new JButton("Cut");
    JButton undo=new JButton("Undo");
    JRadioButton pedzel=new JRadioButton("Marked");
    JToggleButton brush = new JToggleButton("Brush");
    //JTextField NameOfPicture = new JTextField(30);
    JPanel panel = new JPanel(new FlowLayout());
    JLabel label;
    BufferedImage editPhoto;
    Stack<int[][][]> historyPixel = new Stack<>();
    Stack<String> lastOption = new Stack<>();
    Stack<BufferedImage> lastCoordinate = new Stack<>();
    private int startX, startY, endX, endY;
    private int wspolrzedna_x, wspolrzedna_Y, szerokosc,wysokosc,k;

    /**
     * Kreaowanie okna aplikacji
     */
    public Obrazekpop(){
        super("Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000,2000);

        Open.addActionListener(this);
        Savebmp.addActionListener(this);
        Savejpg.addActionListener(this);
        Savetiff.addActionListener(this);
        lighter.addActionListener(this);
        darker.addActionListener(this);
        rotation.addActionListener(this);
        sobel.addActionListener(this);
        cut.addActionListener(this);
        undo.addActionListener(this);
        pedzel.addActionListener(this);
        brush.addActionListener(this);
        label = new JLabel();

        panel.add(Open);

        JLabel contrast = new JLabel("Contrast", SwingConstants.LEFT);
        panel.add(contrast);
        panel.add(lighter);
        panel.add(darker);
        panel.add(rotation);
        panel.add(sobel);
        panel.add(undo);
        panel.add(cut);
        panel.add(pedzel);
        panel.add(brush);
        JLabel saveAs = new JLabel("Save as:", SwingConstants.LEFT);
        panel.add(saveAs);
        panel.add(Savebmp);
        panel.add(Savejpg);
        panel.add(Savetiff);
        panel.add(label);
        setContentPane(panel);
        setVisible(true);
    }

    /**
     * Funckja obraca obraz o 90stopni, zamieniając współrzędne x,y
     * @param editPhoto
     * @return editPhoto
     */
    static BufferedImage rotation90(BufferedImage editPhoto) {
        int width = editPhoto.getWidth();
        int height = editPhoto.getHeight();

        BufferedImage photo90 = new BufferedImage(height, width, editPhoto.getType());

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgb = editPhoto.getRGB(i, j);
                photo90.setRGB( j, i, rgb);
            }
        }

        editPhoto=photo90;
        return editPhoto;
    }

    /**
     * Filtr nieliniowy sobel , uśrednia wartość pikseli poprzez nałozone maski , efekt :detekcja krawędzi
     * @param editPhoto
     * @return
     */
    private BufferedImage sobel1(BufferedImage editPhoto){
        int liczbamasek=8;
        int szerokosc1=3;
        int wysokosc1=3;
        int[][][]sobel = new int[szerokosc1][wysokosc1][liczbamasek];
        sobel[0][0][0] = -1;
        sobel[1][0][0] = -2;
        sobel[2][0][0] = -1;
        sobel[0][1][0] = 0;
        sobel[1][1][0] = 0;
        sobel[2][1][0] = 0;
        sobel[0][2][0] = 1;
        sobel[1][2][0] = 2;
        sobel[2][2][0] = 1;
        sobel[0][0][1] = 0;
        sobel[1][0][1] = -1;
        sobel[2][0][1] = -2;
        sobel[0][1][1] = 1;
        sobel[1][1][1] = 0;
        sobel[2][1][1] = -1;
        sobel[0][2][1] = 2;
        sobel[1][2][1] = 1;
        sobel[2][2][1] = 0;
        sobel[0][0][2] = 1;
        sobel[1][0][2] = 0;
        sobel[2][0][2] = -1;
        sobel[0][1][2] = 2;
        sobel[1][1][2] = 0;
        sobel[2][1][2] = -2;
        sobel[0][2][2] = 1;
        sobel[1][2][2] = 0;
        sobel[2][2][2] = -1;
        sobel[0][0][3] = 2;
        sobel[1][0][3] = 1;
        sobel[2][0][3] = 0;
        sobel[0][1][3] = 1;
        sobel[1][1][3] = 0;
        sobel[2][1][3] = -1;
        sobel[0][2][3] = 0;
        sobel[1][2][3] = -1;
        sobel[2][2][3] = -2;
        sobel[0][0][4] = 1;
        sobel[1][0][4] = 2;
        sobel[2][0][4] = 1;
        sobel[0][1][4] = 0;
        sobel[1][1][4] = 0;
        sobel[2][1][4] = 0;
        sobel[0][2][4] =-1;
        sobel[1][2][4] = -2;
        sobel[2][2][4] = -1;
        sobel[0][0][5] = 0;
        sobel[1][0][5] = 1;
        sobel[2][0][5] = 2;
        sobel[0][1][5] = -1;
        sobel[1][1][5] = 0;
        sobel[2][1][5] = 1;
        sobel[0][2][5] = -2;
        sobel[1][2][5] = -1;
        sobel[2][2][5] = 0;
        sobel[0][0][6] = -1;
        sobel[1][0][6] = 0;
        sobel[2][0][6] = 1;
        sobel[0][1][6] = -2;
        sobel[1][1][6] = 0;
        sobel[2][1][6] = 2;
        sobel[0][2][6] = -1;
        sobel[1][2][6] = 0;
        sobel[2][2][6] = 1;
        sobel[0][0][7] = -2;
        sobel[1][0][7] = -1;
        sobel[2][0][7] = 0;
        sobel[0][1][7] = -1;
        sobel[1][1][7] = 0;
        sobel[2][1][7] = 1;
        sobel[0][2][7] = 0;
        sobel[1][2][7] = 1;
        sobel[2][2][7] = 2;
        int[][][] pixels = new int[editPhoto.getWidth()][editPhoto.getHeight()][3];
        int w=0,w1=editPhoto.getWidth(),e=0,e2=editPhoto.getHeight();
        if(pedzel.isSelected()){ w=wspolrzedna_x;
            w1=szerokosc;
            e=wspolrzedna_Y;
            e2=wysokosc;}

        for (int x = w; x < w1; x++) {
            for (int y =e ; y < e2; y++) {
                int rgb = editPhoto.getRGB(x, y);

                int red = (rgb >> 16) & 0xFF;   // Pobranie składowej R z wartości piksela
                int green = (rgb >> 8) & 0xFF;  // Pobranie składowej G z wartości piksela
                int blue = rgb & 0xFF;          // Pobranie składowej B z wartości piksela
                pixels[x][y] = new int[]{red, green, blue};
            }
        }
        int kolorred;
        int kolorgreen;
        int kolorblue;
        int[][][] pixelos = new int[editPhoto.getWidth()][editPhoto.getHeight()][3];

        for (int x = w; x < w1; x++) {
            for (int y =e ; y < e2; y++) {

                kolorred=0;
                kolorgreen=0;
                kolorblue=0;
                if (x + 2 >= w1 || y + 2 >= e2) {}//zeby maska nie wyszla z obrazka
                else {
                    for (int h = 0; h < 8; h++) {
                        kolorred = kolorred + (sobel[0][0][h] * pixels[x][y][0]) +(sobel[0][1][h] * pixels[x][y+1][0]) +
                                (sobel[0][2][h] * pixels[x][y+2][0]) +(sobel[1][0][h] * pixels[x+1][y][0]) +
                                (sobel[1][1][h] * pixels[x+1][y+1][0]) +(sobel[1][2][h] * pixels[x+1][y+2][0]) +
                                (sobel[2][0][h] * pixels[x+2][y][0]) +(sobel[2][1][h] *pixels[x+2][y+1][0]) +
                                (sobel[2][2][h] * pixels[x+2][y+2][0]);
                        kolorgreen = kolorgreen + (sobel[0][0][h] * pixels[x][y][1]) +(sobel[0][1][h] * pixels[x][y+1][1]) +
                                (sobel[0][2][h] * pixels[x][y+2][1]) +(sobel[1][0][h] * pixels[x+1][y][1]) +
                                (sobel[1][1][h] * pixels[x+1][y+1][1]) +(sobel[1][2][h] * pixels[x+1][y+2][1]) +
                                (sobel[2][0][h] * pixels[x+2][y][1]) +(sobel[2][1][h] *pixels[x+2][y+1][1]) +
                                (sobel[2][2][h] * pixels[x+2][y+2][1]);
                        kolorblue = kolorblue + (sobel[0][0][h] * pixels[x][y][2]) +(sobel[0][1][h] * pixels[x][y+1][2]) +
                                (sobel[0][2][h] * pixels[x][y+2][2]) +(sobel[1][0][h] * pixels[x+1][y][2]) +
                                (sobel[1][1][h] * pixels[x+1][y+1][2]) +(sobel[1][2][h] * pixels[x+1][y+2][2]) +
                                (sobel[2][0][h] * pixels[x+2][y][2]) +(sobel[2][1][h] *pixels[x+2][y+1][2]) +
                                (sobel[2][2][h] * pixels[x+2][y+2][2]);

                        if (kolorgreen < 0) {
                            kolorgreen = 0;}
                        if(kolorblue< 0){
                            kolorblue = 0;}
                        if(kolorred< 0){
                            kolorred = 0;
                        }

                    }
                    kolorred = Math.min(255, Math.max(0, kolorred / 8));
                    kolorgreen   = Math.min(255, Math.max(0, kolorgreen / 8));
                    kolorblue = Math.min(255, Math.max(0, kolorblue / 8));

                    Color color = new Color(kolorred, kolorgreen, kolorblue);
                    int rgb=color.getRGB();
                    editPhoto.setRGB(x,y,rgb);
                }

            }
        }
        return editPhoto;
    }
    /**
     * Funckja zmieniają jasność poprzez dodanie wartości do składowych rgb, pojasneinie obrazu
     * @param editPhoto
     * @return editPhoto
     */

    private BufferedImage light(BufferedImage editPhoto) {

        int[][][] pixels = new int[editPhoto.getWidth()][editPhoto.getHeight()][3];

        int place_X=0,maxWidth=editPhoto.getWidth(),place_Y=0,maxHeight=editPhoto.getHeight();
        if(pedzel.isSelected()){ place_X=wspolrzedna_x;
            maxWidth=szerokosc;
            place_Y=wspolrzedna_Y;
            maxHeight=wysokosc;}

        for (int x = place_X; x < maxWidth; x++) {
            for (int y =place_Y ; y < maxHeight; y++) {
                int rgb = editPhoto.getRGB(x, y);

                int red = (rgb >> 16) & 0xFF;   // Pobranie składowej R z wartości piksela
                int green = (rgb >> 8) & 0xFF;  // Pobranie składowej G z wartości piksela
                int blue = rgb & 0xFF;          // Pobranie składowej B z wartości piksela
                pixels[x][y] = new int[]{red, green, blue};

                for(int i = 0; i < 3; i++){

                    if (pixels[x][y][i] + 10 < 255) {
                        pixels[x][y][i] = pixels[x][y][i] + 10 ;
                    }
                    else{
                        pixels[x][y][i] = 255;
                    }
                }
                Color color = new Color(pixels[x][y][0], pixels[x][y][1], pixels[x][y][2]);
                int rgbNew=color.getRGB();
                editPhoto.setRGB(x,y,rgbNew);
            }

        }

        return editPhoto;
    }
    /**
     *  * Funckja zmieniają jasność poprzez odjęcie wartości od składowych rgb, pociemnienie obrazu
     * @param editPhoto
     * @return
     */

    private BufferedImage dark(BufferedImage editPhoto) {
        int[][][] pixels = new int[editPhoto.getWidth()][editPhoto.getHeight()][3];
        int place_X=0,maxWidth=editPhoto.getWidth(),place_Y=0,maxHeight=editPhoto.getHeight();
        if(pedzel.isSelected()){ place_X=wspolrzedna_x;
            maxWidth=szerokosc;
            place_Y=wspolrzedna_Y;
            maxHeight=wysokosc;}

        for (int x = place_X; x < maxWidth; x++) {
            for (int y =place_Y ; y < maxHeight; y++) {

                int rgb = editPhoto.getRGB(x, y);

                int red = (rgb >> 16) & 0xFF;   // Pobranie składowej R z wartości piksela
                int green = (rgb >> 8) & 0xFF;  // Pobranie składowej G z wartości piksela
                int blue = rgb & 0xFF;          // Pobranie składowej B z wartości piksela
                pixels[x][y] = new int[]{red, green, blue};

                for(int i = 0; i < 3; i++){

                    if (pixels[x][y][i] - 10 > 0) {
                        pixels[x][y][i] = pixels[x][y][i] - 10 ;
                    }
                    else{
                        pixels[x][y][i] = 0;
                    }
                }
                Color color = new Color(pixels[x][y][0], pixels[x][y][1], pixels[x][y][2]);
                int rgbNew=color.getRGB();
                editPhoto.setRGB(x,y,rgbNew);
            }
        }

        return editPhoto;
    }
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * Funkcja pobiera współrzedne w momencie kliknięcia myszy, gdy nasz przycisk bruch jest wybrany rysuje kwadracik.
     * @param e the event to be processed
     */
    public void mousePressed(MouseEvent e) {
        startX = e.getX();
        startY = e.getY();
        if (brush.isSelected()) {
            int x = e.getX();
            int y = e.getY();
            for (int j = x; j < x + 20; j++) {
                for (int k = y; k < y + 20; k++) {
                    if (j >= 0 && j < editPhoto.getWidth() && k >= 0 && k < editPhoto.getHeight()) {
                        editPhoto.setRGB(j, k, 000000);
                    }
                }
            }
            ImageIcon modifiedIcon = new ImageIcon(editPhoto);
            label.setIcon(modifiedIcon);
            label.repaint();
        }
    }
    /**
     * Funckja pobiera skłądowe w momencie zwolnienia przycisku
     * następnie są przekazywane do kolejnej funckji selectedArea
     * @param e the event to be processed
     */

    public void mouseReleased(MouseEvent e) {
        endX = e.getX();
        endY = e.getY();
        processSelectedArea();
    }

    /**
     * Funckja oblicza prostokąt który jest wybrany do edytacji lub do wycięcia
     */
    private void processSelectedArea() {
        if(brush.isSelected()){}else {
            int width = Math.abs(endX - startX);
            int height = Math.abs(endY - startY);
            int x = Math.min(startX, endX);
            int y = Math.min(startY, endY);
            wspolrzedna_x=startX;
            wspolrzedna_Y=startY;
            wysokosc=endY;
            szerokosc=endX;
            if(pedzel.isSelected()){}else if(k==1){
                BufferedImage selectedArea = editPhoto.getSubimage(x, y, width, height);
                editPhoto = selectedArea;

                ImageIcon modifiedIcon = new ImageIcon(editPhoto);
                label.setIcon(modifiedIcon);
                label.repaint();
            }
            label.removeMouseListener(this);
        }}
    public void mouseEntered(MouseEvent e) {

    }
    public void mouseExited(MouseEvent e) {

    }

    /**
     * Funkcja zczytująca wszytskie piksele do tablicy trójwymiarowej o wymiarach obrazka , pobieramy po kolei skłądowe rgb należace do danej współrzednej x,y
     * @param editPhoto
     * @param pixels1
     * @return
     */
    public int[][][] load_pixel(BufferedImage editPhoto, int[][][] pixels1 ){

        for (int x = 0; x < editPhoto.getWidth(); x++) {
            for (int y = 0; y < editPhoto.getHeight(); y++) {
                int rgb = editPhoto.getRGB(x, y);

                int red = (rgb >> 16) & 0xFF;   // Pobranie składowej R z wartości piksela
                int green = (rgb >> 8) & 0xFF;  // Pobranie składowej G z wartości piksela
                int blue = rgb & 0xFF;          // Pobranie składowej B z wartości piksela
                pixels1[x][y] = new int[]{red, green, blue};
            }
        }
        return pixels1;
    }

    /**
     *Funkcja nasłuchiwająca zdarzenia , gdy jeden z przyciskó jest wybrany to dzieją się przypisane do niego funkcje
     * @param e the event to be processed
     */
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        try {

            if (source == Open) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File("C:\\Users\\Klaudia Kwaśniewska\\IdeaProjects\\Projekt"));
                int response = fileChooser.showOpenDialog(null);

                if(response == JFileChooser.APPROVE_OPTION) {

                    File file = new File(fileChooser.getSelectedFile().getCanonicalPath());

                    // Wczytanie obrazu z pliku
                    editPhoto = ImageIO.read(file);
                    System.out.println(editPhoto.getHeight());
                    lastCoordinate.push(editPhoto);
                    int[][][] pixels = new int[editPhoto.getWidth()][editPhoto.getHeight()][3];
                    load_pixel(editPhoto, pixels);

                    ImageIcon icon = new ImageIcon(editPhoto);

                    load_pixel(editPhoto, pixels);
                    historyPixel.push(pixels);
                    label.setIcon(icon);
                    label.repaint();
                    setVisible(true);
                }
            }
            if (source ==sobel) {
                lastOption.push("pixel");
                int[][][] pixels3 = new int[editPhoto.getWidth()][editPhoto.getHeight()][3];
                load_pixel( editPhoto,pixels3);
                historyPixel.push(pixels3);

                editPhoto=sobel1(editPhoto);

                ImageIcon icon = new ImageIcon(editPhoto);
                label.setIcon(icon);
                label.repaint();

            }
            if (source == rotation) {
                lastOption.push("coordination");
                lastCoordinate.push(editPhoto);

                editPhoto = rotation90(editPhoto);

                ImageIcon icon = new ImageIcon(editPhoto);
                label.setIcon(icon);
                label.repaint();

            }
            if (source == lighter) {
                lastOption.push("pixel");
                int[][][] pixels3 = new int[editPhoto.getWidth()][editPhoto.getHeight()][3];
                load_pixel( editPhoto,pixels3);
                historyPixel.push(pixels3);
                editPhoto=light(editPhoto);

                ImageIcon icon = new ImageIcon(editPhoto);
                label.setIcon(icon);
                label.repaint();

            }
            if (source == darker) {
                lastOption.push("pixel");
                int[][][] pixels3 = new int[editPhoto.getWidth()][editPhoto.getHeight()][3];
                load_pixel( editPhoto,pixels3);
                historyPixel.push(pixels3);

                editPhoto=dark(editPhoto);

                ImageIcon icon = new ImageIcon(editPhoto);
                label.setIcon(icon);
                label.repaint();

            }
            if (source == Savebmp) {
                //zapis do bmp
                ImageIO.write(editPhoto, "BMP", new File("new.bmp"));
            }
            if (source == Savetiff) {
                //zapis do tiff
                ImageIO.write(editPhoto, "TIFF", new File("new.tiff"));
            }
            if (source == Savejpg) {
                //zapis do jpg
                ImageIO.write(editPhoto, "JPG", new File("new.jpg"));

            }
            if(source==cut){
                k=1;
                lastOption.push("coordination");
                lastCoordinate.push(editPhoto);

                label.addMouseListener(this);
            }
            if(source==undo){
                if(!lastOption.isEmpty()){
                    if(lastOption.pop() == "pixel"){
                        if (!historyPixel.isEmpty()){
                            int[][][] array = historyPixel.pop();
                            for (int x = 0; x < editPhoto.getWidth(); x++) {
                                for (int y = 0; y < editPhoto.getHeight(); y++) {
                                    for (int k = 0; k < 3; k++) {

                                        Color color = new Color(array[x][y][0], array[x][y][1], array[x][y][2]);
                                        int rgbNew = color.getRGB();
                                        editPhoto.setRGB(x, y, rgbNew);
                                    }
                                }
                            }
                            ImageIcon icon = new ImageIcon(editPhoto);
                            label.setIcon(icon);
                            label.repaint();
                        }
                    }
                    else {
                        editPhoto = lastCoordinate.pop();
                        ImageIcon icon = new ImageIcon(editPhoto);
                        label.setIcon(icon);
                        label.repaint();
                    }
                }
            }
            if(source==pedzel){
                k=1;
                lastOption.push("coordination");
                lastCoordinate.push(editPhoto);

                label.addMouseListener(this);
            }
            if(brush.isSelected()){
                lastOption.push("pixel");
                int[][][] pixels3 = new int[editPhoto.getWidth()][editPhoto.getHeight()][3];
                load_pixel( editPhoto,pixels3);
                historyPixel.push(pixels3);
                label.addMouseListener(this);
                if(!brush.isSelected()){
                    label.removeMouseListener(this);
                }
            }

        } catch (IOException o) {
            o.printStackTrace();
        }
    }

    /**
     * Nasz Main , działanie programu
     * @param args
     */
    public static void main(String[] args) {

        new Obrazekpop();

    }
}
