package edu.yu.cs.intro.secretOps;
import java.util.*;

public class Adversary implements Comparable<Adversary>{
    private AdversaryType type;
    private AdversaryType customProtectedBy;
    private Mission mission;
    private int health;
    /**
     * create an adversary with no custom protectors; its protectors will be determined by its AdversaryType
     * @param type the type of adversary to create
     */
    protected Adversary(AdversaryType type){
        this.type = type;
        this.health = type.ammunitionCountNeededToKill;
    }
    
    /**
     * create an adversary with a custom protector, i.e. a different protector than the one specified in its AdversaryType
     * @param type
     * @param customProtectedBy
     */
    public Adversary(AdversaryType type, AdversaryType customProtectedBy){
        this.type = type;
        this.health = type.ammunitionCountNeededToKill;
        this.customProtectedBy = customProtectedBy;
    }

    /**
     * set the mission that the Adversary appears in
     * @param mission
     */
    protected void setMission(Mission mission){
        this.mission = mission;
        return;
    }

    public AdversaryType getAdversaryType(){
        return this.type;
    }

    /**
     * Attack this adversary with the given weapon, firing the given number of rounds at it.
     * If this adversary is killed by this attack, it must inform its Mission that it has been killed.
     * @param weapon
     * @param rounds
     * @return indicates if the adversary is dead after this attack
     * @throws IllegalArgumentException if the weapon is one that doesn't hurt this adversary, if the weapon is null, or if rounds < 1
     * @throws IllegalStateException if the adversary is already dead
     * @see Mission#adversaryKilled(Adversary)
     */
    protected boolean attack(Weapon weapon, int rounds){
        if (weapon.ordinal() < this.getAdversaryType().weaponNeededToKill.ordinal()){
            throw new IllegalArgumentException("Weapon doesn't hurt adversary");
        }
        if (weapon == null){
            throw new IllegalArgumentException("Weapon is null");
        }
        if (rounds < 1){
            throw new IllegalArgumentException("Rounds are less than 1");
        }
        if (this.isDead()){
            throw new IllegalStateException("Adversary is already dead");
        }
        this.health = this.health - rounds;
        if (this.isDead()){
            return true;
        }
        return false;
    }

    /**
     * @return is this adversary dead?
     */
    public boolean isDead(){
        if (this.health <= 0){
            return true;
        }
        return false;
    }

    /**
     * @return if this adversary has its customProtectedBy set, return it. Otherwise, return the protectedBy of this adversary's type
     * @see AdversaryType#getProtectedBy()
     */
    public AdversaryType getProtectedBy(){
        if (this.customProtectedBy != null){
            return this.customProtectedBy;
        }
        return this.getAdversaryType().getProtectedBy();
    }

    /**
     * This method is used elsewhere when sorting a set of adversaries into the order in which they must be killed, assuming they are in the same mission.
     *
     * If the parameter refers to this adversary, return 0
     * else if this adversary is protected by the other adversary's type, return 1
     * else if this adversary's type protects the other adversary, return -1
     * else if this adversary's ordinal is < the other's, return -1
     * else if this adversary's ordinal is > the other's, return 1
     * else if(this.hashCode() < other.hashCode()), then return -1
     * Otherwise, return 1.
     * @param other the other adversary
     * @return see above
     */
    @Override
    public int compareTo(Adversary other){
        if (this == other){
            return 0;
        }
        else if (this.getProtectedBy() == other.getAdversaryType()){
            return 1;
        }
        else if (other.getProtectedBy() == this.getAdversaryType()){
            return -1;
        }
        else if (this.getAdversaryType().ordinal() < other.getAdversaryType().ordinal()){
            return -1;
        }
        else if (this.getAdversaryType().ordinal() > other.getAdversaryType().ordinal()){
            return 1;
        }
        else if (this.hashCode() < other.hashCode()){
            return -1;
        }
        return 1;
    }

    /**
     * @param obj
     * @return true if it is the same exact object in memory, otherwise false
     */
    public boolean equals(Object obj){
        if (this == obj){
            return true;
        }
        return false;
    }
}