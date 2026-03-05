package edu.yu.cs.intro.secretOps;
import java.util.*;

/**
 * A Mission in the operation. A mission contains both adversaries, as well as rewards for the agent that completes the mission.
 * The agent who kills the last living adversary in the mission is considered to have completed the mission.
 */
public class Mission implements Comparable<Mission>{
    private SortedSet<Adversary> adversaries;
    private Set<Weapon> weaponsWonUponCompletion;
    private Map<Weapon,Integer> ammoWonUponCompletion;
    private int healthWonUponCompletion;
    private String name;
    private int dangerLevel;
    /**
     *
     * @param adversaries the adversaries present in this mission. This is a set of Adversary instances, NOT AdversaryTypes; there can be multiple adversaries of the same type in a mission.
     * @param weaponsWonUponCompletion weapons an agent gains when killing the last adversary in this mission.
     * @param ammoWonUponCompletion ammunition an agent gains when killing the last adversary in this mission.
     * @param healthWonUponCompletion health an agent gains when killing the last adversary in this mission.
     * @param name the mission's name
     */
    public Mission(SortedSet<Adversary> adversaries, Set<Weapon> weaponsWonUponCompletion, Map<Weapon,Integer> ammoWonUponCompletion, int healthWonUponCompletion, String name){
        this.adversaries = adversaries;
        this.weaponsWonUponCompletion = weaponsWonUponCompletion;
        this.ammoWonUponCompletion = ammoWonUponCompletion;
        this.healthWonUponCompletion = healthWonUponCompletion;
        this.name = name;
        this.dangerLevel = this.setDangerLevel();
    }

    /**
     * Reduce the danger level of this mission by adversary.getAdversaryType().ordinal()+1 IF the adversary is alive and in the set of adversaries in this mission.
     * @param adversary
     */
    protected void adversaryKilled(Adversary adversary){
        if (!adversary.isDead() && this.adversaries.contains(adversary)){
            dangerLevel -= adversary.getAdversaryType().ordinal() + 1;
        }
        return;
    }

    /**
     * The danger level of the mission is defined as the sum of the ordinal+1 value of all living adversaries, i.e. adding up (m.getAdversaryType().ordinal() + 1) of all the living adversaries
     * @return the danger level of this mission
     */
    public int getDangerLevel(){
        return this.dangerLevel;
    }

    //My Method
    private int setDangerLevel(){
        int result = 0;
        for (Adversary adversary : this.getLiveAdversaries()){
            result += adversary.getAdversaryType().ordinal() + 1;
        }
        return result;
    }

    /**
     *
     * @return name of this mission
     */
    public String getName(){
        return this.name;
    }

    /**
     * compares based on danger level
     * @param other
     * @return
     */
    @Override
    public int compareTo(Mission other){
        if (this.dangerLevel > other.dangerLevel){
            return 1;
        }
        else if (this.dangerLevel < other.dangerLevel){
            return -1;
        }
        if (this.getName().compareTo(other.getName()) < 0){
            return -1;
        }
        else if (this.getName().compareTo(other.getName()) > 0){
            return 1;
        }
        return 0;
    }

    /**
     * @return the set of weapons the agent who completes the mission is rewarded with. Make sure you don't allow the caller to modify the actual set!
     * @see java.util.Collections#unmodifiableSet(Set)
     */
    public Set<Weapon> getWeaponsWonUponCompletion(){
        return Collections.unmodifiableSet(this.weaponsWonUponCompletion);
    }

    /**
     * @return A per-weapon map of ammunition the agent who completes the mission is rewarded with. Make sure you don't allow the caller to modify the actual map!
     * @see java.util.Collections#unmodifiableMap(Map) 
     */
    public Map<Weapon,Integer> getAmmoWonUponCompletion(){
        return Collections.unmodifiableMap(this.ammoWonUponCompletion);
    }

    /**
     *
     * @return The amount of health rewarded to the agent who completes this mission.
     */
    public int getHealthWonUponCompletion(){
        return this.healthWonUponCompletion;
    }

    /**
     * @return indicates if all the adversaries in the mission are dead
     */
    public boolean isCompleted(){
        if (this.getDeadAdversaries().size() == this.adversaries.size()){
            return true;
        }
        return false;
    }

    /**
     * @return The SortedSet of all adversaries in the mission
     * @see java.util.Collections#unmodifiableSortedSet(SortedSet)
     */
    public SortedSet<Adversary> getAdversaries(){
        return Collections.unmodifiableSortedSet(this.adversaries);
    }

    /**
     * @return a modifiable SortedSet of adversaries in this mission that are alive
     */
    public SortedSet<Adversary> getLiveAdversaries(){
        SortedSet<Adversary> result = new TreeSet<>();
        for (Adversary adversary : this.adversaries){
            if (!adversary.isDead()){
                result.add(adversary);
            }
        }
        return result;
    }

    /**
     * Every time an agent enters a mission, he loses health points based on the adversaries in the mission.
     * The amount lost is the sum of the values of agentHealthLostPerExposure of all the *live* adversaries in the mission
     * @return the amount of health lost
     * @see AdversaryType#agentHealthLostPerExposure
     */
    public int getCurrentAgentHealthLoss(){
        int result = 0;
        for (Adversary adversary : this.getLiveAdversaries()){
            result += adversary.getAdversaryType().agentHealthLostPerExposure;
        }
        return result;
    }

    /**
     * @return the SortedSet of adversaries in this mission that are dead
     */
    public SortedSet<Adversary> getDeadAdversaries(){
        SortedSet<Adversary> result = new TreeSet<>();
        for (Adversary adversary : this.adversaries){
            if (adversary.isDead()){
                result.add(adversary);
            }
        }
        return result;
    }
}