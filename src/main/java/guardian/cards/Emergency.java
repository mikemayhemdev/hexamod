package guardian.cards;



import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.defect.DiscardPileToHandAction;
import com.megacrit.cardcrawl.actions.defect.EvokeAllOrbsAction;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import guardian.GuardianMod;
import guardian.orbs.StasisOrb;
import guardian.patches.AbstractCardEnum;

public class Emergency extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("Emergency");
    public static final String NAME;
    public static final String DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;
    public static final String IMG_PATH = "cards/emergency.png";

    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    //TUNING CONSTANTS

    private static final int COST = 1;
    private static final int UPGRADECOST = 0;
    private static final int SOCKETS = 0;
    private static final boolean SOCKETSAREAFTER = true;

    //END TUNING CONSTANTS

    public Emergency() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);

this.exhaust = true;
        this.socketCount = SOCKETS;  updateDescription();  loadGemMisc();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p,m);

        for (AbstractOrb o : p.orbs){
            if (o instanceof StasisOrb){
                int stasisCount = o.passiveAmount;
                ((StasisOrb) o).stasisCard.superFlash(Color.GOLDENROD);
                for (int i = 0; i < stasisCount; i++) {
                    o.onStartOfTurn();
                }
                break;
            }
        }

        super.useGems(p,m);
    }

    public AbstractCard makeCopy() {
        return new Emergency();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();

            this.upgradeBaseCost(UPGRADECOST);
        }
    }


    public void updateDescription(){

        if (this.socketCount > 0) {
            if (upgraded && UPGRADED_DESCRIPTION != null) {
                this.rawDescription = this.updateGemDescription(UPGRADED_DESCRIPTION,true);
            } else {
                this.rawDescription = this.updateGemDescription(DESCRIPTION,true);
            }
        }
        this.initializeDescription();
    }
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }
}


