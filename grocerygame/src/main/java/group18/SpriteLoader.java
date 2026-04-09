package group18;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.InputStream;

/**
 * this utility loads sprite sheets and splits them into directional frames.
 * it provides image arrays used by game rendering.
 */
public class SpriteLoader {

    /**
     * this loads and slices the player sprite sheet into four frames.
     * it returns null if loading fails.
     *
     * @return an array of player frames, or null on failure
     */
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

    /**
     * this loads a sprite for bonus items.
     * it returns null if loading fails.
     *
     * @return the bonus item sprite, or null on failure
     */
    public static Image loadBonusSprite() {
        try (InputStream stream = SpriteLoader.class.getClassLoader().getResourceAsStream("tiles/money.png")) {
            if (stream == null) {
                throw new IllegalArgumentException("Missing resource: money.png");
            }

            return ImageIO.read(stream);
        } catch (Exception e) {
            System.err.println("Failed to load bonus sprite: " + e.getMessage());
            return null;
        }
    }

    /**
     * this loads a sprite for penalty items.
     * it returns null if loading fails.
     *
     * @return the penalty item sprite, or null on failure
     */
    public static Image loadPenaltySprite() {
        try (InputStream stream = SpriteLoader.class.getClassLoader().getResourceAsStream("tiles/bad_meat.png")) {
            if (stream == null) {
                throw new IllegalArgumentException("Missing resource: bad_meat.png");
            }

            return ImageIO.read(stream);
        } catch (Exception e) {
            System.err.println("Failed to load bonus sprite: " + e.getMessage());
            return null;
        }
    }

    /**
     * this loads a sprite for main items.
     * it returns null if loading fails.
     *
     * @return the main item sprite, or null on failure
     */
    public static Image loadMainSprite() {
        try (InputStream stream = SpriteLoader.class.getClassLoader().getResourceAsStream("tiles/banana.png")) {
            if (stream == null) {
                throw new IllegalArgumentException("Missing resource: banana.png");
            }

            return ImageIO.read(stream);
        } catch (Exception e) {
            System.err.println("Failed to load bonus sprite: " + e.getMessage());
            return null;
        }
    }

    /**
     * this loads and slices the security sprite sheet into four frames.
     * it returns null if loading fails.
     *
     * @return an array of security frames, or null on failure
     */
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
    public static Image loadStartMenuImage() {
        try (InputStream stream = SpriteLoader.class.getClassLoader().getResourceAsStream("startMenu.jpg")) {
            return ImageIO.read(stream);
        } catch (Exception e) {
            System.err.println("Failed to load start menu image: " + e.getMessage());
            return null;
        }
    }
}
