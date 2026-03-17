package group18;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public class SpriteLoader {

    public static Image[] loadCharacterFrames() {
        try (InputStream stream = SpriteLoader.class.getClassLoader().getResourceAsStream("tiles/character.png")) {
            if (stream == null) {
                throw new IllegalArgumentException("Missing resource: character.png");
            }

            BufferedImage sheet = ImageIO.read(stream);
            int frameW = sheet.getWidth() / 2;
            int frameH = sheet.getHeight() / 2;

            Image[] frames = new Image[4];
            frames[0] = sheet.getSubimage(0, 0, frameW, frameH);
            frames[1] = sheet.getSubimage(frameW, 0, frameW, frameH);
            frames[2] = sheet.getSubimage(frameW, frameH, frameW, frameH);
            frames[3] = sheet.getSubimage(0, frameH, frameW, frameH);

            return frames;
        } catch (Exception e) {
            System.err.println("Failed to load player frames: " + e.getMessage());
            return null;
        }
    }

    public static Image[] loadSecurityFrames() {
        try (InputStream stream = SpriteLoader.class.getClassLoader().getResourceAsStream("tiles/security.png")) {
            if (stream == null) {
                throw new IllegalArgumentException("Missing resource: security.png");
            }

            BufferedImage sheet = ImageIO.read(stream);
            int frameW = sheet.getWidth() / 2;
            int frameH = sheet.getHeight() / 2;

            Image[] frames = new Image[4];
            frames[0] = sheet.getSubimage(0, 0, frameW, frameH);
            frames[1] = sheet.getSubimage(frameW, 0, frameW, frameH);
            frames[2] = sheet.getSubimage(frameW, frameH, frameW, frameH);
            frames[3] = sheet.getSubimage(0, frameH, frameW, frameH);

            return frames;
        } catch (Exception e) {
            System.err.println("Failed to load security frames: " + e.getMessage());
            return null;
        }
    }
}