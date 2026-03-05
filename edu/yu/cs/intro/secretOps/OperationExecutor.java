package edu.yu.cs.intro.secretOps;
import java.util.*;

/**
 * Execute a given operation. Tries to kill all the adversaries in all the Missions and thus complete the operation, using the given set of agents.
 */
public class OperationExecutor {
    private SortedSet<Mission> missions;
    private SortedSet<Agent> agents;
    /**
     * @param missions the set of Missions in this operation
     * @param agents the set of agents the executer can use to try to complete all Missions
     */
    public OperationExecutor(SortedSet<Mission> missions, SortedSet<Agent> agents){
        this.missions = missions;
        this.agents = agents;
    }

    /**
     * Try to complete killing all adversaries in all Missions using the given set of agents.
     * It could take multiple iterations through the set of Missions to complete the task of killing every adversary in every Mission.
     * This method should call #prosecuteMissions in some loop that tracks whether all the Missions have been completed OR we
     * have reached a point at which no progress can be made. If we are "stuck", i.e. we haven't completed all Missions but
     * calls to #prosecuteMissions are no longer increasing the number of Missions that have been completed, return false to
     * indicate that we can't complete the operation. As long as the number of completed Missions continues to rise, keep calling
     * #prosecuteMissions.
     *
     * Because completing a mission can give an agent more ammunition, health, and weapons, it is possible that a mission which 
     * could not be completed in one iteration can be completed in a later iteration.
     *
     * Throughout our attempts to execute the operation, we take advantage of the fact that Mission, Adversary,
     * and Agent all implement Comparable, and the sets we work with are all SortedSets.
     *
     * @return true if all Missions were completed, false if not
     */
    public boolean execute(){
        while (true){
            int newCompletedMissions = this.prosecuteMissions().size();
            if (this.getCompletedMissions().size() == this.getAllMissions().size()){
                break;
            }
            else if (newCompletedMissions == 0){
                return false;
            }
        }
        return true;
    }

    /**
     * Make moves in each Mission, killing any adversaries that can be killed, and thus attempt to complete the Missions
     * @return the set of Missions that were completed in this pass. This set need not be sorted.
     */
    protected Set<Mission> prosecuteMissions(){
        Set<Mission> result = new HashSet<>();
        for (Mission mission : this.missions){
            if (!mission.isCompleted()){
                SortedSet<Adversary> adversaries = mission.getLiveAdversaries();
                Agent currentAgent = this.agents.first();
                for (Adversary adversary : adversaries){
                    if (!adversary.isDead()){
                        for (Agent agent : this.agents){
                            if (canKill(agent, adversary, mission)){
                                currentAgent = agent;
                                killAdversary(agent, mission, adversary);
                                break;
                            }
                        }
                    }
                }
                if (mission.isCompleted()){
                    result.add(mission);
                    this.reapCompletionRewards(currentAgent, mission);
                }
            }
        }
        return result;
    }

    /**
     * Give the agent the weapons, ammunition, and health rewards that come from completing the given Mission.
     * @param agent
     * @param mission
     */
    protected void reapCompletionRewards(Agent agent, Mission mission){
        Set<Weapon> newWeapons = mission.getWeaponsWonUponCompletion();
        for (Weapon w : newWeapons){
            agent.addWeapon(w);
        }
        Map<Weapon,Integer> newAmmo = mission.getAmmoWonUponCompletion();
        for (Map.Entry<Weapon, Integer> entry : newAmmo.entrySet()){
            agent.addAmmunition(entry.getKey(), entry.getValue());
        }
        agent.changeHealth(mission.getHealthWonUponCompletion());
        return;
    }

    /**
     * Have the given agent kill the given adversary in the given Mission.
     * Assume that #canKill was already called to confirm that agent's ability to kill the adversary
     * The agent must kill the protectors before it can kill the adversary, so first kill all the protectors. Call getAllProtectorsInMission 
     * to get a sorted set of all the adversary's protectors in this Mission.
     * Reduce the agent's health by the amount given by [Mission.getCurrentAgentHealthLoss() + 
     * (???)adversaryToKill.getAdversaryType().agentHealthLostPerExposure] AFTER killing EACH Adversary (adversaryToKill and any of his protectors).
     * Attack (and thus kill) the adversary with the kind of weapon, and amount of ammunition, needed to kill it.
     * Reduce the agent's ammunition by the amount needed to kill the adversary.
     *
     * @param agent
     * @param mission
     * @param adversaryToKill
     */
    protected void killAdversary(Agent agent, Mission mission, Adversary adversaryToKill){
        SortedSet<Adversary> targets = getAllProtectorsInMission(adversaryToKill, mission);
        targets.add(adversaryToKill);
        for (Adversary a : targets){
            Weapon weaponNeeded = a.getAdversaryType().weaponNeededToKill;
            int ammoNeeded = a.getAdversaryType().ammunitionCountNeededToKill;
            Weapon weaponUsed = Weapon.KNIFE;
            TreeMap<Weapon, Integer> sortedWeapons = new TreeMap<>(agent.getWeapons());
            for (Map.Entry<Weapon, Integer> entry : sortedWeapons.entrySet()){
                if (entry.getKey().ordinal() >= weaponNeeded.ordinal() && entry.getValue() >= ammoNeeded){
                    weaponUsed = entry.getKey();
                    break;
                }
            }
            //Kill the adversary
            a.attack(weaponUsed, ammoNeeded);
            //Reduce health
            agent.changeHealth(-(mission.getCurrentAgentHealthLoss() + a.getAdversaryType().agentHealthLostPerExposure));
            //Reduce ammo
            agent.changeAmmunitionRoundsForWeapon(weaponUsed, -ammoNeeded);
            System.out.println("Mission: " + mission.getName() + ". Agent " + agent.getName() + " has killed a " + a.getAdversaryType() + ". Health is now " + agent.getHealth());
        }
        return;
    }

    /**
     * @return a set of all the Missions that have been completed
     */
    public Set<Mission> getCompletedMissions(){
        Set<Mission> result = new HashSet<>();
        for (Mission m : this.getAllMissions()){
            if (m.isCompleted()){
                result.add(m);
            }
        }
        return result;
    }

    /**
     * @return a newly created, sorted list of all the Missions in the operation
     * @see java.util.Collections#sort(List)
     */
    public List<Mission> getAllMissions(){
        List<Mission> result = new ArrayList<>();
        for (Mission m : this.missions){
            result.add(m);
        }
        Collections.sort(result);
        return result;
    }

    /**
     * @return a sorted set of all the live agents in the operation
     */
    protected SortedSet<Agent> getLiveAgents(){
        SortedSet<Agent> result = new TreeSet<>();
        for (Agent a : this.agents){
            if (!a.isDead()){
                result.add(a);
            }
        }
        return result;
    }

    /**
     * @param weapon
     * @param ammunition
     * @return a sorted set of all the live agents that have the given weapon with the given amount of ammunition for it
     */
    protected SortedSet<Agent> getLiveAgentsWithWeaponAndAmmunition(Weapon weapon, int ammunition){
        SortedSet<Agent> result = new TreeSet<>();
        for (Agent a : this.agents){
            if (a.hasWeapon(weapon) && a.getAmmunitionRoundsForWeapon(weapon) >= ammunition){
                result.add(a);
            }
        }
        return result;
    }

    /**
     * Get the set of all adversaries that would need to be killed first before you could kill the given adversary.
     * Remember that a protector may itself be protected by other adversaries, so you will have to recursively check for protectors.
     * Also remember that ALL instances of a protector must be killed before the adversary can be killed. So, for example, if NINJA protects SUPER_SOLDIER, 
     * every NINJA in the mission must be killed before you can kill a SUPER_SOLDIER.
     *
     * Note that this method is static because it is a utility method which doesn't use any state of OperationExecutor.
     *
     * @param adversary the adversary whose protectors we want a set of
     * @param mission the mission which we're checking for protectors of adversary
     * @return
     */
    protected static SortedSet<Adversary> getAllProtectorsInMission(Adversary adversary, Mission mission){
        SortedSet<Adversary> result = new TreeSet<>();
        if (adversary.getProtectedBy() == null){
            return result;
        }
        else{
            AdversaryType protectorType = adversary.getProtectedBy();
            for (Adversary a : mission.getLiveAdversaries()){
                if (a.getAdversaryType() == protectorType){
                    result.add(a);
                    result.addAll(getAllProtectorsInMission(a, mission));
                }
            }
            return result;
        }
    }

    /**
     * Can the given agent kill the given adversary in the given Mission?
     * Prosecuting a Mission exposes the agent to all the adversaries in the Mission. If the agent's health is
     * not >= Mission.getCurrentAgentHealthLoss(), the agent can't kill any adversaries in the Mission.
     * Remember that an agent must kill all of an Adverary's protectors before it can kill the adversary.
     * Also remember that an agent needs the right weapon, and enough ammunition and health, to kill any given Adversary.
     *
     * @see #getAllProtectorsInMission(Adversary, Mission)
     * @param agent
     * @param adversary
     * @param mission
     * @return
     * @throws IllegalArgumentException if the adversary is not located in the Mission or is dead
     */
    protected static boolean canKill(Agent agent, Adversary adversary, Mission mission){
        if (adversary.isDead()){
            throw new IllegalArgumentException("Adversary is dead");
        }
        SortedSet<Adversary> adversaries = mission.getAdversaries();
        if (!adversaries.contains(adversary)){
            throw new IllegalArgumentException("Adversary not in mission");
        }
        //Make a copy of the agent
        Agent agentClone = new Agent("agentClone", agent.getHealth());
        Map<Weapon,Integer> agentCloneWeapons = new HashMap<>();
        Map<Weapon,Integer> agentWeapons = agent.getWeapons();
        for (Map.Entry<Weapon, Integer> entry : agentWeapons.entrySet()){
            agentCloneWeapons.put(entry.getKey(), entry.getValue());
        }
        agentClone.setWeapons(agentCloneWeapons);
        //Check if the agent can kill each adversary
        SortedSet<Adversary> targets = getAllProtectorsInMission(adversary, mission);
        targets.add(adversary);
        int healthNeeded = mission.getCurrentAgentHealthLoss();
        for (Adversary a : targets){
            //Does the agent have the right weapon?
            boolean rightWeapon = false;
            Weapon weaponNeeded = a.getAdversaryType().weaponNeededToKill;
            for (Weapon w : agentClone.getWeapons().keySet()){
                if (w.ordinal() >= weaponNeeded.ordinal()){
                    rightWeapon = true;
                    break;
                }
            }
            if (rightWeapon == false){
                return false;
            }
            //Does the agent have enough ammo?
            boolean enoughAmmo = false;
            int ammoNeeded = a.getAdversaryType().ammunitionCountNeededToKill;
            Weapon weaponUsed = Weapon.KNIFE;
            TreeMap<Weapon, Integer> sortedWeapons = new TreeMap<>(agentClone.getWeapons());
            for (Map.Entry<Weapon, Integer> entry : sortedWeapons.entrySet()){
                if (entry.getKey().ordinal() >= weaponNeeded.ordinal() && entry.getValue() >= ammoNeeded){
                    weaponUsed = entry.getKey();
                    enoughAmmo = true;
                    break;
                }
            }
            if (enoughAmmo == false){
                return false;
            }
            //Does the agent have enough health?
            if (agentClone.getHealth() < healthNeeded){
                return false;
            }
            //Reduce agent's ammo
            agentClone.changeAmmunitionRoundsForWeapon(weaponUsed, -ammoNeeded);
            //Reduce the agent's health
            agentClone.changeHealth(-healthNeeded);
            //Reduce the healthNeeded to kill the next adversary by agentHealthLostPerExposure of this adversary
            healthNeeded -= a.getAdversaryType().agentHealthLostPerExposure;
        }
        return true;
    }
}