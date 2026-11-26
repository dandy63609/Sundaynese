import greenfoot.Color;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.MouseInfo;
import java.util.ArrayList;
import java.util.List;

/**
 * FR-6, FR-7, FR-8: Smartphone POV world handling Wife Chat, Judol 3-Reel Slot Spin,
 * and Friends' Empang Invite inside a 340x480 handheld frame.
 */
public class PhoneWorld extends ActivityWorld
{
    public static final int JUDOL_SPINS_REQUIRED = 4;

    private static final int PHONE_W = 340;
    private static final int PHONE_H = 472;
    private static final int PHONE_X = (Theme.WORLD_WIDTH - PHONE_W) / 2;
    private static final int PHONE_Y = 64;
    private static final int BTN_X = PHONE_X + 26;
    private static final int BTN_Y = PHONE_Y + PHONE_H - 64;
    private static final int BTN_W = PHONE_W - 52;
    private static final int BTN_H = 44;

    private enum PhoneMode
    {
        WIFE_CHAT,
        JUDOL_SLOTS,
        FRIEND_CHAT
    }

    private static class ChatMsg
    {
        final boolean fromMe;
        final String sender;
        final String text;

        ChatMsg(boolean fromMe, String sender, String text)
        {
            this.fromMe = fromMe;
            this.sender = sender;
            this.text = text;
        }
    }

    private final GreenfootImage bgHouseDimmed;
    private final GreenfootImage[] slotIcons;
    private final String[] slotNames = {"7", "Kopi", "Ikan", "Zonk"};
    private final KeyEdgeTracker keys = new KeyEdgeTracker("space", "e", "enter");

    private PhoneMode mode;
    private int chatBeat = 0;
    private boolean waitingWifeReply = false;
    private int replyDelay = 0;
    private final List<ChatMsg> chatLog = new ArrayList<>();

    // Judol slot state
    private int spinsCompleted = 0;
    private boolean spinning = false;
    private int spinTick = 0;
    private final int[] reelIndices = {0, 1, 2};
    private String slotStatus = "Modal Rp 520.000 siap gacor! Spin 4x!";

    private boolean dirty = true;

    public PhoneWorld()
    {
        super(true);
        bgHouseDimmed = Theme.loadNearest("images/bg_rumah.png", Theme.WORLD_WIDTH, Theme.WORLD_HEIGHT);
        bgHouseDimmed.setColor(Theme.SCRIM_DARK);
        bgHouseDimmed.fillRect(0, 0, Theme.WORLD_WIDTH, Theme.WORLD_HEIGHT);

        slotIcons = new GreenfootImage[] {
            Theme.loadNearest("images/slot_icons/slot_7.png", 64, 64),
            Theme.loadNearest("images/slot_icons/slot_kopi.png", 64, 64),
            Theme.loadNearest("images/slot_icons/slot_ikan.png", 64, 64),
            Theme.loadNearest("images/slot_icons/slot_zonk.png", 64, 64)
        };

        reset();
    }

    @Override
    public void reset()
    {
        GameStep step = GameState.getInstance().getCurrentStep();
        if (step == GameStep.STEP_3_JUDOL)
        {
            enterJudolMode();
        }
        else if (step == GameStep.STEP_4_FRIEND_INVITE)
        {
            enterFriendChatMode();
        }
        else
        {
            enterWifeChatMode();
        }
        miniGameState = MiniGameState.ACTIVE;
        stateTimer = 0;
        dirty = true;
    }

    private void enterWifeChatMode()
    {
        mode = PhoneMode.WIFE_CHAT;
        chatBeat = 0;
        waitingWifeReply = false;
        replyDelay = 0;
        chatLog.clear();
        chatLog.add(new ChatMsg(false, "Neng Siti (HK)", "Assalamualaikum Kang, kumaha di lembur? Sehat?"));
        dirty = true;
    }

    private void enterJudolMode()
    {
        mode = PhoneMode.JUDOL_SLOTS;
        spinsCompleted = 0;
        spinning = false;
        spinTick = 0;
        reelIndices[0] = 0;
        reelIndices[1] = 2;
        reelIndices[2] = 1;
        slotStatus = "Tekan [SPACE] atau klik SPIN! (0/" + JUDOL_SPINS_REQUIRED + ")";
        dirty = true;
    }

    private void enterFriendChatMode()
    {
        mode = PhoneMode.FRIEND_CHAT;
        chatBeat = 0;
        waitingWifeReply = false;
        chatLog.clear();
        chatLog.add(new ChatMsg(false, "Mang Asep", "Mang Ujang! Empang Mang Jaja lagi rame nih, hayu merapat!"));
        SoundFX.play(SoundFX.CHAT);
        dirty = true;
    }

    @Override
    protected void actIntroPov()
    {
        start();
    }

    @Override
    protected void actActive()
    {
        if (mode == PhoneMode.WIFE_CHAT && waitingWifeReply)
        {
            replyDelay--;
            if (replyDelay <= 0)
            {
                waitingWifeReply = false;
                if (chatBeat == 1)
                {
                    chatLog.add(new ChatMsg(false, "Neng Siti (HK)",
                            "Iya Kang, ini baru ditransfer Rp 500rb. Jangan dipake aneh-aneh ya, inget anak di pesantren."));
                    GameState.getInstance().addMoney(GameState.WIFE_TRANSFER_AMOUNT);
                    SoundFX.play(SoundFX.COIN);
                    chatBeat = 2;
                }
                dirty = true;
            }
        }
        else if (mode == PhoneMode.JUDOL_SLOTS && spinning)
        {
            tickSlotSpin();
        }

        if (keys.justPressedAny("space", "enter", "e") || isActionButtonClicked())
        {
            handlePrimaryAction();
        }

        if (dirty)
        {
            redraw();
            dirty = false;
        }
    }

    @Override
    protected void actRetryFlash()
    {
        transitionState(MiniGameState.ACTIVE);
    }

    @Override
    protected void actComplete()
    {
        if (stateTimer >= 60 || keys.justPressedAny("space", "enter", "e"))
        {
            Greenfoot.setWorld(new HouseWorld(HouseWorld.SpawnPoint.BESIDE_PHONE));
        }
    }

    private boolean isActionButtonClicked()
    {
        if (!Greenfoot.mouseClicked(null))
        {
            return false;
        }
        MouseInfo mi = Greenfoot.getMouseInfo();
        return mi != null && mi.getX() >= BTN_X && mi.getX() <= BTN_X + BTN_W
                && mi.getY() >= BTN_Y && mi.getY() <= BTN_Y + BTN_H;
    }

    /**
     * Advances the active smartphone mode on click or SPACE/E press.
     */
    public void handlePrimaryAction()
    {
        if (mode == PhoneMode.WIFE_CHAT)
        {
            if (waitingWifeReply)
            {
                return;
            }
            if (chatBeat == 0)
            {
                chatLog.add(new ChatMsg(true, "Mang Ujang",
                        "Neng, punten transfer heula 500rb, buat bayar token listrik sama bensin..."));
                SoundFX.play(SoundFX.CHAT);
                chatBeat = 1;
                waitingWifeReply = true;
                replyDelay = 36;
            }
            else if (chatBeat == 2)
            {
                chatLog.add(new ChatMsg(true, "Mang Ujang", "Siap Neng, aman! Nuhun pisan!"));
                SoundFX.play(SoundFX.CHAT);
                chatBeat = 3;
            }
            else if (chatBeat >= 3)
            {
                GameState.getInstance().completeAndAdvance(GameStep.STEP_2_CHAT_WIFE, GameStep.STEP_3_JUDOL);
                enterJudolMode();
            }
            dirty = true;
        }
        else if (mode == PhoneMode.JUDOL_SLOTS)
        {
            if (spinning)
            {
                return;
            }
            if (spinsCompleted < JUDOL_SPINS_REQUIRED)
            {
                startSlotSpin();
            }
            else
            {
                GameState.getInstance().completeAndAdvance(GameStep.STEP_3_JUDOL, GameStep.STEP_4_FRIEND_INVITE);
                enterFriendChatMode();
            }
            dirty = true;
        }
        else if (mode == PhoneMode.FRIEND_CHAT)
        {
            if (chatBeat == 0)
            {
                chatLog.add(new ChatMsg(true, "Mang Ujang", "OTW euy! Tungguin, bawa kopi!"));
                SoundFX.play(SoundFX.CHAT);
                chatBeat = 1;
                GameState.getInstance().completeAndAdvance(GameStep.STEP_4_FRIEND_INVITE, GameStep.STEP_5_CHANGE_SHIRT);
                transitionState(MiniGameState.COMPLETE);
                dirty = true;
                redraw();
            }
        }
    }

    public void startSlotSpin()
    {
        spinning = true;
        spinTick = 0;
        GameState.getInstance().spendMoney(GameState.JUDOL_BET_AMOUNT);
        SoundFX.play(SoundFX.SLOT);
        slotStatus = "Memutar keberuntungan... (-Rp 100.000)";
        dirty = true;
    }

    private void tickSlotSpin()
    {
        spinTick++;
        if (spinTick % 4 == 0)
        {
            if (spinTick < 28)
            {
                reelIndices[0] = (reelIndices[0] + 1) % 4;
            }
            if (spinTick < 44)
            {
                reelIndices[1] = (reelIndices[1] + 1) % 4;
            }
            if (spinTick < 62)
            {
                reelIndices[2] = (reelIndices[2] + 1) % 4;
            }
            dirty = true;
        }
        if (spinTick >= 64)
        {
            finishSlotSpin();
        }
    }

    /**
     * Resolves a spin into a comedic near-miss loss (callable directly in tests).
     */
    public void finishSlotSpin()
    {
        spinning = false;
        spinsCompleted++;
        // Scripted comedic near-misses so players experience classic judol bait
        int[][] nearMisses = {
            {0, 0, 3}, // 7 - 7 - Zonk
            {2, 2, 3}, // Ikan - Ikan - Zonk
            {1, 0, 3}, // Kopi - 7 - Zonk
            {0, 0, 1}  // 7 - 7 - Kopi
        };
        int[] res = nearMisses[(spinsCompleted - 1) % nearMisses.length];
        reelIndices[0] = res[0];
        reelIndices[1] = res[1];
        reelIndices[2] = res[2];
        SoundFX.play(SoundFX.MISS);

        if (spinsCompleted < JUDOL_SPINS_REQUIRED)
        {
            slotStatus = "Nyaris JP! (" + slotNames[res[0]] + "-" + slotNames[res[1]] + "-" + slotNames[res[2]]
                    + ") Rungkad euy!";
        }
        else
        {
            slotStatus = "Saldo tipis! Eh ada notif WA dari Mang Asep!";
            SoundFX.play(SoundFX.CHAT);
        }
        dirty = true;
    }

    public int getSpinsCompleted()
    {
        return spinsCompleted;
    }

    private void redraw()
    {
        GreenfootImage bg = new GreenfootImage(bgHouseDimmed);

        // Phone bezel
        Theme.fillRoundRect(bg, PHONE_X - 6, PHONE_Y - 6, PHONE_W + 12, PHONE_H + 12, 20,
                Theme.INK_PRIMARY, Theme.BORDER_WOOD, 2);

        if (mode == PhoneMode.JUDOL_SLOTS)
        {
            drawJudolScreen(bg);
            showPrompt("SPACE / Klik", spinsCompleted < JUDOL_SPINS_REQUIRED ? "Putar Slot!" : "Buka WA Masuk");
        }
        else
        {
            drawChatScreen(bg);
            String verb = (mode == PhoneMode.WIFE_CHAT && chatBeat >= 3) ? "Buka Aplikasi Gacor"
                    : (mode == PhoneMode.FRIEND_CHAT ? "Balas OTW" : "Kirim Pesan");
            showPrompt("SPACE / Klik", verb);
        }

        setBackground(bg);
    }

    private void drawChatScreen(GreenfootImage bg)
    {
        Theme.fillRoundRect(bg, PHONE_X, PHONE_Y, PHONE_W, PHONE_H, 14, Theme.SURFACE_PHONE, Theme.INK_PRIMARY, 2);

        // WhatsApp-style header
        Theme.fillRoundRect(bg, PHONE_X + 2, PHONE_Y + 2, PHONE_W - 4, 48, 12, Theme.WHATSAPP_HEADER, null, 0);
        bg.setColor(Theme.WHATSAPP_HEADER);
        bg.fillRect(PHONE_X + 2, PHONE_Y + 34, PHONE_W - 4, 18);

        String title = mode == PhoneMode.WIFE_CHAT ? "Neng Siti (Istri - HK)" : "Grup Baraya Empang";
        bg.setFont(Theme.FONT_PROMPT);
        bg.setColor(Theme.INK_LIGHT);
        bg.drawString(title, PHONE_X + 16, PHONE_Y + 28);
        bg.setFont(Theme.FONT_SMALL);
        bg.setColor(Theme.WHATSAPP_SENT);
        bg.drawString("online", PHONE_X + 16, PHONE_Y + 44);

        // Chat bubbles
        int y = PHONE_Y + 66;
        for (ChatMsg msg : chatLog)
        {
            List<String> lines = Theme.wrapText(bg, Theme.FONT_PHONE_MSG, msg.text, 224);
            int bh = 28 + lines.size() * 17;
            int bw = 244;
            int bx = msg.fromMe ? (PHONE_X + PHONE_W - bw - 14) : (PHONE_X + 14);
            Color fill = msg.fromMe ? Theme.WHATSAPP_SENT : Theme.SURFACE_BUBBLE;
            Theme.fillRoundRect(bg, bx, y, bw, bh, 10, fill, Theme.INK_MUTED, 1);

            bg.setFont(Theme.FONT_SMALL);
            bg.setColor(msg.fromMe ? Theme.ACCENT_SARUNG : Theme.WHATSAPP_HEADER);
            bg.drawString(msg.sender, bx + 10, y + 15);

            bg.setFont(Theme.FONT_PHONE_MSG);
            bg.setColor(Theme.INK_PRIMARY);
            for (int i = 0; i < lines.size(); i++)
            {
                bg.drawString(lines.get(i), bx + 10, y + 32 + i * 17);
            }
            y += bh + 10;
        }

        // Action button
        String btnLabel;
        if (mode == PhoneMode.WIFE_CHAT)
        {
            if (waitingWifeReply)
            {
                btnLabel = "Neng Siti sedang mengetik...";
            }
            else if (chatBeat == 0)
            {
                btnLabel = "Kirim Alasan Minta 500rb [SPACE]";
            }
            else if (chatBeat == 2)
            {
                btnLabel = "Balas Terima Kasih [SPACE]";
            }
            else
            {
                btnLabel = "Buka Aplikasi Gacor! [SPACE]";
            }
        }
        else
        {
            btnLabel = chatBeat == 0 ? "Balas: OTW euy! [SPACE]" : "Berangkat Ganti Kaos...";
        }

        Color btnColor = (mode == PhoneMode.WIFE_CHAT && chatBeat >= 3) ? Theme.ACCENT_JUDOL : Theme.ACCENT_UANG;
        Theme.fillRoundRect(bg, BTN_X, BTN_Y, BTN_W, BTN_H, 10, btnColor, Theme.INK_PRIMARY, 2);
        Theme.drawCenteredText(bg, btnLabel, BTN_X + BTN_W / 2, BTN_Y + 27, Theme.FONT_HUD_LABEL, Theme.INK_LIGHT);
    }

    private void drawJudolScreen(GreenfootImage bg)
    {
        Theme.fillRoundRect(bg, PHONE_X, PHONE_Y, PHONE_W, PHONE_H, 14, new Color(34, 10, 42),
                Theme.ACCENT_JUDOL, 3);

        // Neon header
        Theme.fillRoundRect(bg, PHONE_X + 12, PHONE_Y + 12, PHONE_W - 24, 46, 8, Theme.ACCENT_JUDOL,
                Theme.ACCENT_WARNING, 2);
        Theme.drawCenteredText(bg, "GACOR777 - PASTI JP*", PHONE_X + PHONE_W / 2, PHONE_Y + 36,
                Theme.FONT_PROMPT, Theme.INK_LIGHT);
        Theme.drawCenteredText(bg, "*Simulasi Satire (Uang Palsu)", PHONE_X + PHONE_W / 2, PHONE_Y + 52,
                Theme.FONT_SMALL, Theme.ACCENT_WARNING);

        // Spin counter
        Theme.drawCenteredText(bg, "Putaran: " + spinsCompleted + " / " + JUDOL_SPINS_REQUIRED
                + "   |   Taruhan: Rp 100.000", PHONE_X + PHONE_W / 2, PHONE_Y + 86,
                Theme.FONT_HUD_LABEL, Theme.ACCENT_WARNING);

        // 3 Slot Reels
        int reelStartX = PHONE_X + 34;
        int reelY = PHONE_Y + 114;
        for (int i = 0; i < 3; i++)
        {
            int rx = reelStartX + i * 94;
            Theme.fillRoundRect(bg, rx, reelY, 84, 110, 6, Theme.SURFACE_BUBBLE, Theme.ACCENT_JUDOL, 3);
            bg.drawImage(slotIcons[reelIndices[i]], rx + 10, reelY + 12);
            Theme.drawCenteredText(bg, slotNames[reelIndices[i]], rx + 42, reelY + 98,
                    Theme.FONT_HUD_LABEL, Theme.INK_PRIMARY);
        }

        // Status box
        Theme.fillRoundRect(bg, PHONE_X + 20, PHONE_Y + 244, PHONE_W - 40, 64, 8,
                Theme.SURFACE_HUD, Theme.ACCENT_WARNING, 1);
        List<String> lines = Theme.wrapText(bg, Theme.FONT_PHONE_BOLD, slotStatus, PHONE_W - 64);
        for (int i = 0; i < lines.size(); i++)
        {
            Theme.drawCenteredText(bg, lines.get(i), PHONE_X + PHONE_W / 2, PHONE_Y + 270 + i * 20,
                    Theme.FONT_PHONE_BOLD, Theme.INK_LIGHT);
        }

        if (spinsCompleted >= JUDOL_SPINS_REQUIRED && !spinning)
        {
            Theme.fillRoundRect(bg, PHONE_X + 20, PHONE_Y + 322, PHONE_W - 40, 56, 8,
                    Theme.WHATSAPP_HEADER, Theme.INK_LIGHT, 2);
            Theme.drawCenteredText(bg, "WA Masuk: Mang Asep", PHONE_X + PHONE_W / 2, PHONE_Y + 344,
                    Theme.FONT_HUD_LABEL, Theme.ACCENT_WARNING);
            Theme.drawCenteredText(bg, "\"Hayu merapat ke empang!\"", PHONE_X + PHONE_W / 2, PHONE_Y + 364,
                    Theme.FONT_PHONE_MSG, Theme.INK_LIGHT);
        }

        String btnLabel = spinning ? "SPINNING..."
                : (spinsCompleted < JUDOL_SPINS_REQUIRED ? "SPIN! (-Rp 100rb) [SPACE]" : "Cek WA Mang Asep [SPACE]");
        Color btnColor = spinsCompleted < JUDOL_SPINS_REQUIRED ? Theme.ACCENT_JUDOL : Theme.ACCENT_UANG;
        Theme.fillRoundRect(bg, BTN_X, BTN_Y, BTN_W, BTN_H, 10, btnColor, Theme.ACCENT_WARNING, 2);
        Theme.drawCenteredText(bg, btnLabel, BTN_X + BTN_W / 2, BTN_Y + 27, Theme.FONT_PROMPT, Theme.INK_LIGHT);
    }
}
