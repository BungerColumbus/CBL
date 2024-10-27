package core;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

public class FontManager {
    
    public static Font jersey15;

    //Using try catch we select the font from our res folder
    static {
        try {
            jersey15 = Font.createFont(Font.TRUETYPE_FONT,
                       new File("src/res/font/Jersey_15/Jersey15-Regular.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(jersey15);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }
    
    //The font in use is jersey15
    public static Font getCustomFont(float size) {
        return jersey15.deriveFont(size);
    }
}
