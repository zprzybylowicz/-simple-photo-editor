# -simple-photo-editor
 simple photo editor
This is a simple bitmap image editor written in Java. The program uses the Swing and AWT libraries to display the user interface. It allows you to open bitmap images in basic formats such as JPG, TIFF, and BMP. The application reads the image from disk and stores it as a BufferedImage object, which represents the image as an array of attributes corresponding to individual pixels. The RGB values of each coordinate are retrieved and stored in a triple array for applying filters and masks.

# FUNCTIONS:
Opening Images: You can open bitmap images in BMP, JPG, and TIFF formats from your computer. Simply click the "Open Image" button and select a file from the file explorer.

Saving Images: After making edits, you can save the image in BMP, JPG, or TIFF formats. Just select the appropriate format and click the "Save Image" button.

Editing the Entire Image: You can perform various operations on the entire image, such as rotation, brightness adjustment, etc.

Rotation: You can rotate the image by 90 degrees. This operation swaps the x and y components, where the height becomes the width and the width becomes the height. The RGB components at position x, y are moved to position y, x.

Brightness Adjustment: You can change the brightness of the image by adding (brightening) or subtracting (darkening) values to the RGB components. The program ensures that the color values do not exceed critical values.

Editing Image Fragments: You can also perform edits on selected fragments of the image.

Selection and Cropping: Select the desired area by clicking and dragging the mouse. When you release the mouse button, a rectangular area for cropping or editing will be determined. The modified image fragment will be displayed on the screen.

Brush Drawing: You can select the "Brush" tool and click on the image to draw black squares around the clicked point. Each square has a size of 20x20 pixels, and the RGB values of the pixels will be set to black (000000).

Edge Detection: You can use the Sobel operator for edge detection. The operator consists of 8 masks in a 3x3 matrix form, which allows for edge detection at different angles. The resulting image will have smoothed edges.

Undoing Edits: The program allows you to undo the performed operations. You can undo the last change on the image or cancel multiple moves until reaching the initial state.

# Running the Program
To run the program, follow these steps:

Compile the Java source code using the Java compiler .

Run the program using the Java Virtual Machine (JVM).

After launching the application, a window with the image editor interface will appear.

Select the "Open Image" button to load an image from your disk.

Perform various operations on the image using the available buttons and tools.

You can save the modified image by selecting the format (BMP, JPG, TIFF) and clicking the "Save Image" button.

To undo the last operation, click the "Undo" button.

To exit the program, close the application window.
