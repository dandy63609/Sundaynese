/**
 * Ordered 1-day story progression steps for Sundaynese.
 */
public enum GameStep
{
    STEP_0_WAKEUP("Bangun tidur dulu..."),
    STEP_1_COFFEE("Misi 1/8: Ngopi hitam & ngerokok dulu"),
    STEP_2_CHAT_WIFE("Misi 2/8: Chat istri minta kiriman uang"),
    STEP_3_JUDOL("Misi 3/8: Cek nasib di aplikasi gacor"),
    STEP_4_FRIEND_INVITE("Misi 4/8: Balas ajakan mancing di HP"),
    STEP_5_CHANGE_SHIRT("Misi 5/8: Pakai kaos andalan mancing"),
    STEP_6_BIKE_RIDE("Misi 6/8: Gas motor klasik ke empang"),
    STEP_7_FISHING("Misi 7/8: Mancing sambil udud & ngobrol"),
    STEP_8_SLEEP("Misi 8/8: Pulang & tidur (Selesai!)"),
    COMPLETED("Hari selesai, Sundaynese sejati!");

    private final String missionLabel;

    GameStep(String missionLabel)
    {
        this.missionLabel = missionLabel;
    }

    public String getMissionLabel()
    {
        return missionLabel;
    }

    /**
     * Returns the next step in the linear 1-day loop.
     */
    public GameStep next()
    {
        GameStep[] all = values();
        int idx = ordinal() + 1;
        return idx < all.length ? all[idx] : COMPLETED;
    }
}
