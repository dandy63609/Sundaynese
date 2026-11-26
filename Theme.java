import greenfoot.Color;
import greenfoot.Font;
import greenfoot.GreenfootImage;
import java.awt.BasicStroke;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Design tokens and shared 2D pixel/UI rendering utilities defined in DESIGN.md.
 */
public final class Theme
{
    public static final int WORLD_WIDTH = 800;
    public static final int WORLD_HEIGHT = 600;
    public static final int HUD_HEIGHT = 52;
    public static final int PROMPT_BAR_TOP = 548;

    // Color Palette Tokens
    public static final Color SURFACE_RUMAH = new Color(0xD9, 0xBA, 0x8C);
    public static final Color SURFACE_EMPANG = new Color(0x4F, 0x8F, 0x62);
    public static final Color SURFACE_ROAD = new Color(0x6E, 0x6A, 0x63);
    public static final Color SURFACE_HUD = new Color(0x1F, 0x1A, 0x17);
    public static final Color SURFACE_PHONE = new Color(0xEC, 0xE5, 0xDD);
    public static final Color SURFACE_BUBBLE = new Color(0xFF, 0xFD, 0xF7);
    public static final Color INK_PRIMARY = new Color(0x1F, 0x1A, 0x17);
    public static final Color INK_MUTED = new Color(0x5E, 0x54, 0x4D);
    public static final Color INK_LIGHT = new Color(0xFF, 0xFD, 0xF7);
    public static final Color ACCENT_SARUNG = new Color(0x24, 0x5C, 0x4B);
    public static final Color ACCENT_UANG = new Color(0x1E, 0x84, 0x49);
    public static final Color ACCENT_JUDOL = new Color(0xFF, 0x2E, 0x93);
    public static final Color ACCENT_WARNING = new Color(0xF4, 0xB4, 0x00);
    public static final Color ACCENT_STRIKE = new Color(0xE5, 0x39, 0x35);
    public static final Color BORDER_WOOD = new Color(0x5A, 0x3E, 0x28);
    public static final Color SCRIM_DARK = new Color(0x10, 0x0C, 0x0A, 185);
    public static final Color SHADOW_GROUND = new Color(0x10, 0x0C, 0x0A, 90);
    public static final Color WHATSAPP_HEADER = new Color(0x07, 0x5E, 0x54);
    public static final Color WHATSAPP_SENT = new Color(0xDC, 0xF8, 0xC6);

    // Typography Tokens
    public static final Font FONT_TITLE = new Font("SansSerif", true, false, 32);
    public static final Font FONT_SUBTITLE = new Font("SansSerif", true, false, 20);
    public static final Font FONT_HUD_LABEL = new Font("SansSerif", true, false, 13);
    public static final Font FONT_HUD_VALUE = new Font("Monospaced", true, false, 16);
    public static final Font FONT_DIALOGUE = new Font("SansSerif", false, false, 14);
    public static final Font FONT_PROMPT = new Font("SansSerif", true, false, 14);
    public static final Font FONT_PHONE_MSG = new Font("SansSerif", false, false, 13);
    public static final Font FONT_PHONE_BOLD = new Font("SansSerif", true, false, 13);
    public static final Font FONT_SMALL = new Font("SansSerif", false, false, 11);

    private Theme()
    {
    }

    private static java.awt.Color toAwt(Color c)
    {
        return new java.awt.Color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
    }

    /**
     * Loads a sprite image and scales it cleanly with nearest-neighbor pixel snapping.
     */
    public static GreenfootImage loadNearest(String path, int targetW, int targetH)
    {
        GreenfootImage raw = new GreenfootImage(path);
        if (raw.getWidth() == targetW && raw.getHeight() == targetH)
        {
            return raw;
        }
        return scaleNearest(raw, targetW, targetH);
    }

    /**
     * Scales an existing GreenfootImage using nearest-neighbor interpolation to keep 16-bit edges crisp.
     */
    public static GreenfootImage scaleNearest(GreenfootImage src, int targetW, int targetH)
    {
        GreenfootImage out = new GreenfootImage(targetW, targetH);
        Graphics2D g = out.getAwtImage().createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g.drawImage(src.getAwtImage(), 0, 0, targetW, targetH, null);
        g.dispose();
        return out;
    }

    /**
     * Draws a filled rounded rectangle with an optional crisp stroke border.
     */
    public static void fillRoundRect(GreenfootImage img, int x, int y, int w, int h, int radius,
                                     Color fill, Color border, int borderWidth)
    {
        Graphics2D g = img.getAwtImage().createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int arc = radius * 2;
        if (fill != null)
        {
            g.setColor(toAwt(fill));
            g.fillRoundRect(x, y, w, h, arc, arc);
        }
        if (border != null && borderWidth > 0)
        {
            g.setColor(toAwt(border));
            g.setStroke(new BasicStroke(borderWidth));
            int half = borderWidth / 2;
            g.drawRoundRect(x + half, y + half, w - borderWidth, h - borderWidth, arc, arc);
        }
        g.dispose();
    }

    /**
     * Measures text width for the provided Greenfoot font.
     */
    public static int measureTextWidth(GreenfootImage img, Font font, String text)
    {
        Graphics2D g = img.getAwtImage().createGraphics();
        g.setFont(new java.awt.Font(font.getName(),
                (font.isBold() ? java.awt.Font.BOLD : 0) | (font.isItalic() ? java.awt.Font.ITALIC : 0),
                font.getSize()));
        FontMetrics fm = g.getFontMetrics();
        int width = fm.stringWidth(text == null ? "" : text);
        g.dispose();
        return width;
    }

    /**
     * Draws text horizontally centered at centerX using baseline y.
     */
    public static void drawCenteredText(GreenfootImage img, String text, int centerX, int y, Font font, Color color)
    {
        int w = measureTextWidth(img, font, text);
        img.setFont(font);
        img.setColor(color);
        img.drawString(text, centerX - w / 2, y);
    }

    /**
     * Draws an underlined or shadowed retro header centered on screen.
     */
    public static void drawShadowedText(GreenfootImage img, String text, int centerX, int y,
                                        Font font, Color fg, Color shadow)
    {
        drawCenteredText(img, text, centerX + 2, y + 2, font, shadow);
        drawCenteredText(img, text, centerX, y, font, fg);
    }

    /**
     * Wraps text into lines fitting within maxWidth pixels.
     */
    public static List<String> wrapText(GreenfootImage canvas, Font font, String text, int maxWidth)
    {
        List<String> lines = new ArrayList<>();
        if (text == null || text.isEmpty())
        {
            return lines;
        }
        String[] words = text.split("\\s+");
        StringBuilder current = new StringBuilder();
        for (String word : words)
        {
            String candidate = current.length() == 0 ? word : current + " " + word;
            if (measureTextWidth(canvas, font, candidate) <= maxWidth)
            {
                current.setLength(0);
                current.append(candidate);
            }
            else
            {
                if (current.length() > 0)
                {
                    lines.add(current.toString());
                    current.setLength(0);
                }
                current.append(word);
            }
        }
        if (current.length() > 0)
        {
            lines.add(current.toString());
        }
        return lines;
    }

    /**
     * Creates a translucent ground shadow oval for standing top-down sprites.
     */
    public static GreenfootImage createShadowOval(int width, int height)
    {
        GreenfootImage shadow = new GreenfootImage(width, height);
        shadow.setColor(SHADOW_GROUND);
        shadow.fillOval(0, 0, width, height);
        return shadow;
    }

    /**
     * Builds a styled PromptPill image with yellow keycap badge and action verb.
     */
    public static GreenfootImage createPromptPillImage(String keyLabel, String actionText)
    {
        GreenfootImage probe = new GreenfootImage(1, 1);
        int keyW = measureTextWidth(probe, FONT_PROMPT, keyLabel) + 16;
        int actW = measureTextWidth(probe, FONT_PROMPT, actionText);
        int totalW = keyW + actW + 34;
        int h = 36;

        GreenfootImage img = new GreenfootImage(totalW, h);
        fillRoundRect(img, 0, 0, totalW, h, 18, SURFACE_HUD, BORDER_WOOD, 2);
        fillRoundRect(img, 8, 6, keyW, h - 12, 8, ACCENT_WARNING, INK_PRIMARY, 1);

        img.setFont(FONT_PROMPT);
        img.setColor(INK_PRIMARY);
        img.drawString(keyLabel, 16, 23);

        img.setColor(INK_LIGHT);
        img.drawString(actionText, keyW + 18, 23);
        return img;
    }

    /**
     * Builds a rounded speech bubble image with wrapped text and bottom pointer tail.
     */
    public static GreenfootImage createSpeechBubbleImage(String speaker, String message, int maxWidth)
    {
        GreenfootImage probe = new GreenfootImage(1, 1);
        List<String> lines = wrapText(probe, FONT_DIALOGUE, message, maxWidth - 20);
        int lineH = 18;
        int headerH = (speaker != null && !speaker.isEmpty()) ? 18 : 0;
        int boxH = Math.max(36, 16 + headerH + lines.size() * lineH);
        int boxW = maxWidth;
        int longest = speaker != null ? measureTextWidth(probe, FONT_HUD_LABEL, speaker) : 0;
        for (String l : lines)
        {
            longest = Math.max(longest, measureTextWidth(probe, FONT_DIALOGUE, l));
        }
        boxW = Math.max(110, Math.min(maxWidth, longest + 24));

        GreenfootImage img = new GreenfootImage(boxW, boxH + 10);
        fillRoundRect(img, 0, 0, boxW, boxH, 12, SURFACE_BUBBLE, INK_PRIMARY, 2);

        // Pointer tail
        int cx = boxW / 2;
        int[] tx = {cx - 6, cx + 6, cx};
        int[] ty = {boxH - 2, boxH - 2, boxH + 9};
        img.setColor(INK_PRIMARY);
        img.fillPolygon(tx, ty, 3);
        int[] itx = {cx - 4, cx + 4, cx};
        int[] ity = {boxH - 3, boxH - 3, boxH + 6};
        img.setColor(SURFACE_BUBBLE);
        img.fillPolygon(itx, ity, 3);

        int textY = 20;
        if (headerH > 0)
        {
            img.setFont(FONT_HUD_LABEL);
            img.setColor(ACCENT_SARUNG);
            img.drawString(speaker, 11, textY);
            textY += 17;
        }
        img.setFont(FONT_DIALOGUE);
        img.setColor(INK_PRIMARY);
        for (String line : lines)
        {
            img.drawString(line, 11, textY);
            textY += lineH;
        }
        return img;
    }
}
