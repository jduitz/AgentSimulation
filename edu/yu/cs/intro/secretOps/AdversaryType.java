package edu.yu.cs.intro.secretOps;

public enum AdversaryType {
    JOBNIK(Weapon.KNIFE,1,1),
    NINJA(Weapon.MACHINE_GUN,6,2),
    SUPER_SOLDIER(Weapon.RPG,12,3),
    INFANTRY(Weapon.HAND_GUN,1,1);

    /**what weapon is needed to kill this adversary?*/
    protected final Weapon weaponNeededToKill;
    /**how many rounds of ammunition must be fired at the adversary from the required weapon to kill it?*/
    protected final int ammunitionCountNeededToKill;
    /**how much health does an agent lose when he is in the same room as this type of adversary?*/
    protected final int agentHealthLostPerExposure;

    /**
     *
     * @param weaponNeededToKill
     * @param ammunitionCountNeededToKill
     * @param agentHealthLostPerExposure
     */
    AdversaryType(Weapon weaponNeededToKill, int ammunitionCountNeededToKill, int agentHealthLostPerExposure) {
        this.weaponNeededToKill = weaponNeededToKill;
        this.ammunitionCountNeededToKill = ammunitionCountNeededToKill;
        this.agentHealthLostPerExposure = agentHealthLostPerExposure;
    }

    /**
     * if this adversary is in the same room as other adversaries, what type of other adversary would protect this one?
     */
    public AdversaryType getProtectedBy(){
        switch (this){
            case JOBNIK:
                return null;
            case INFANTRY:
                return SUPER_SOLDIER;
            case SUPER_SOLDIER:
                return NINJA;
            case NINJA:
                return null;
        }
        return null;
    }
}