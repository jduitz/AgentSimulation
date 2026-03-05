package edu.yu.cs.intro.secretOps;
import java.util.*;
/**
 * An agent whose health is <= 0 is dead.
 */
public class Agent implements Comparable<Agent> {
    private String name;
    private int health;
    private Map<Weapon,Integer> weapons;
    private Map<Weapon,Integer> weaponsAmmo;
    /**
     * An Agent is created with a knife and infinite ammunition for it.
     * @param name the agent's name
     * @param health the agent's starting health level
     */
    public Agent(String name, int health){
        this.name = name;
        this.health = health;
        this.weapons = new HashMap<>();
        this.weapons.put(Weapon.KNIFE, Integer.MAX_VALUE);
        this.weaponsAmmo = new HashMap<>();
        this.weaponsAmmo.put(Weapon.HAND_GUN, 0);
        this.weaponsAmmo.put(Weapon.MACHINE_GUN, 0);
        this.weaponsAmmo.put(Weapon.RPG, 0);
    }

    public String getName(){
        return this.name;
    }

    /**
     * does this agent have the given weapon?
     * @param w
     * @return
     */
    public boolean hasWeapon(Weapon w){
        if (w == null){
            return false;
        }
        return this.weapons.keySet().contains(w);
    }

    //My Method
    protected Map<Weapon,Integer> getWeapons(){
        return this.weapons;
    }

    //My Method
    protected void setWeapons(Map<Weapon,Integer> weapons){
        this.weapons = weapons;
        return;
    }

    /**
     * how much ammunition does this agent have for the given weapon?
     * @param w
     * @return
     */
    public int getAmmunitionRoundsForWeapon(Weapon w){
        if (this.hasWeapon(w) == true){
            return this.weapons.get(w);
        }
        else{
            return this.weaponsAmmo.get(w);
        }
    }

    /**
     * Change the ammunition amount by a positive or negative amount
     * @param weapon weapon whose ammunition count is to be changed
     * @param change amount to change ammunition count for that weapon by
     * @return the new total amount of ammunition the agent has for the weapon.
     */
    public int changeAmmunitionRoundsForWeapon(Weapon weapon, int change){
        //If he doesn't have the weapon, don't change the ammunition.
        if (this.hasWeapon(weapon) == false){
            return 0;
        }
        //If the weapon is a knife, don't change the ammunition.
        if (weapon == Weapon.KNIFE){
            return this.getAmmunitionRoundsForWeapon(weapon);
        }
        //Change the ammunition.
        int newAmmoCount = this.getAmmunitionRoundsForWeapon(weapon) + change;
        this.weapons.put(weapon,newAmmoCount);
        return newAmmoCount;
    }

    /**
     * An agent can have ammunition for a weapon even without having the weapon itself.
     * @param weapon weapon for which we are adding ammunition
     * @param rounds number of rounds of ammunition to add
     * @return the new total amount of ammunition the agent has for the weapon
     * @throws IllegalStateException if the agent is dead
     * @throws IllegalArgumentException if rounds < 0 or if the weapon parameter is null
     */
    protected int addAmmunition(Weapon weapon, int rounds){
        if (this.isDead()){
            throw new IllegalStateException("Agent is dead");
        }
        else if (rounds < 0){
            throw new IllegalArgumentException("Rounds are negative");
        }
        else if (weapon == null){
            throw new IllegalArgumentException("Weapon is null");
        }
        //If the weapon is a knife, don't change the ammunition.
        else if (weapon == Weapon.KNIFE){
            return this.getAmmunitionRoundsForWeapon(weapon);
        }
        //If he has the weapon and it's not a knife, then add ammo to the weapon.
        else if (this.hasWeapon(weapon)){
            int newAmmoCount = this.getAmmunitionRoundsForWeapon(weapon) + rounds;
            this.weapons.put(weapon, newAmmoCount);
            return this.getAmmunitionRoundsForWeapon(weapon);
        }
        //If he doesn't have the weapon, add ammo to the weapon.
        else{
            int newAmmoCount = this.weaponsAmmo.get(weapon) + rounds;
            this.weaponsAmmo.put(weapon, newAmmoCount);
            return this.weaponsAmmo.get(weapon);
        }
    }

    /**
     * When a weapon is first added to an agent, this method should automatically give the agent 5 rounds of ammunition.
     * If the agent already has the weapon before this method is called, this method has no effect at all.
     * @param weapon
     * @return true if the weapon was added, false if the agent already had it
     * @throws IllegalStateException if the agent is dead
     * @throws IllegalArgumentException if weapon is null
     */
    protected boolean addWeapon(Weapon weapon){
        if (this.isDead()){
            throw new IllegalStateException("Agent is dead");
        }
        if (weapon == null){
            throw new IllegalArgumentException("Weapon is null");
        }
        if (this.hasWeapon(weapon)){
            return false;
        }
        int currentAmmo = this.weaponsAmmo.get(weapon);
        this.weapons.put(weapon, currentAmmo + 5);
        return true;
    }

    /**
     * Change the agent's health level
     * @param amount a positive or negative number, to increase or decrease the agent's health
     * @return the agent's health level after the change
     * @throws IllegalStateException if the agent is dead
     */
    public int changeHealth(int amount){
        if (this.isDead()){
            throw new IllegalStateException("Agent is dead");
        }
        this.health = this.health + amount;
        return this.health;
    }

    /**
     * set agent's current health level to the given level
     * @param amount
     */
    protected void setHealth(int amount){
        this.health = amount;
        return;
    }

    /**
     * @return the agent's current health level
     */
    public int getHealth(){
        return this.health;
    }

    /**
     * @return true if the agent is dead, i.e. his health is <= 0
     */
    public boolean isDead(){
        if (this.health <= 0){
            return true;
        }
        return false;
    }

    /**
     * Compare criteria, in order:
     * Does one have a greater weapon?
     * If they have the same greatest weapon, who has more ammunition for it?
     * If they are the same on weapon and ammunition, who has more health?
     * If they are the same on greatest weapon, the same ammunition for it, and the same health, they are equal.
     * Recall that all enums have a built-in implementation of Comparable, and they compare based on ordinal()
     *
     * @param other
     * @return
     */
    @Override
    public int compareTo(Agent other){
        //Who has the greater weapon?
        Weapon greatestWeaponOne = Weapon.KNIFE;
        for (Weapon w : this.weapons.keySet()){
            if (w.ordinal() > greatestWeaponOne.ordinal()){
                greatestWeaponOne = w;
            }
        }
        Weapon greatestWeaponTwo = Weapon.KNIFE;
        for (Weapon w : other.getWeapons().keySet()){
            if (w.ordinal() > greatestWeaponTwo.ordinal()){
                greatestWeaponTwo = w;
            }
        }
        if (greatestWeaponOne.ordinal() > greatestWeaponTwo.ordinal()){
            return 1;
        }
        else if (greatestWeaponTwo.ordinal() > greatestWeaponOne.ordinal()){
            return -1;
        }
        //Who has more ammo?
        int ammoOne = this.getAmmunitionRoundsForWeapon(greatestWeaponOne);
        int ammoTwo = other.getAmmunitionRoundsForWeapon(greatestWeaponTwo);
        if (ammoOne > ammoTwo){
            return 1;
        }
        else if (ammoTwo > ammoOne){
            return -1;
        }
        //Who has more health?
        if (this.getHealth() > other.getHealth()){
            return 1;
        }
        else if (other.getHealth() > this.getHealth()){
            return -1;
        }
        //Whose name comes first?
        if (this.getName().compareTo(other.getName()) < 0){
            return -1;
        }
        else if (this.getName().compareTo(other.getName()) > 0){
            return 1;
        }
        return 0;
    }

    /**
     * Only equal if it is literally the same agent in memory
     * @param o Object to compare to
     * @return
     */
    @Override
    public boolean equals(Object o){
        if (this == o){
            return true;
        }
        return false;
    }

    /**
     * @return the hash code of the agent's name
     */
    @Override
    public int hashCode(){
        return this.name.hashCode();
    }
}