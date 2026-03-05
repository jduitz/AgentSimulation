package edu.yu.cs.intro.secretOps;
import java.util.*;

public class HW7Tester{
	public static void main(String[] args){
		//Agent Tester
		
		//Agent myAgent = new Agent("Jack",100);
		//myAgent.addWeapon(Weapon.HAND_GUN);
		//myAgent.addWeapon(Weapon.RPG);
		//myAgent.addAmmunition(Weapon.RPG, 10);
        /*
		Agent otherAgent = new Agent("Jack",100);
		otherAgent.addAmmunition(Weapon.RPG, 10);
		otherAgent.addWeapon(Weapon.RPG);
		System.out.println(myAgent.compareTo(otherAgent));
		System.out.println(otherAgent.compareTo(myAgent));
		*/
		/*
		System.out.println(myAgent.getName());
		System.out.println(myAgent.hasWeapon(Weapon.KNIFE));
		System.out.println(myAgent.hasWeapon(Weapon.RPG));
		System.out.println(myAgent.getAmmunitionRoundsForWeapon(Weapon.KNIFE));
		System.out.println(myAgent.getAmmunitionRoundsForWeapon(Weapon.RPG));
		System.out.println(myAgent.changeAmmunitionRoundsForWeapon(Weapon.KNIFE, -7));
		myAgent.setHealth(50);
		System.out.println(myAgent.getHealth());
		System.out.println(myAgent.isDead());
		*/
		//System.out.println(myAgent.addWeapon(Weapon.KNIFE));
		/*
		System.out.println(myAgent.addWeapon(Weapon.HAND_GUN));
		System.out.println(myAgent.hasWeapon(Weapon.HAND_GUN));
		System.out.println(myAgent.getAmmunitionRoundsForWeapon(Weapon.HAND_GUN));
		System.out.println(myAgent.addAmmunition(Weapon.HAND_GUN, 100));
        System.out.println(myAgent.addAmmunition(Weapon.MACHINE_GUN, 100));
        System.out.println(myAgent.hasWeapon(Weapon.MACHINE_GUN));
        System.out.println(myAgent.getAmmunitionRoundsForWeapon(Weapon.MACHINE_GUN));
        System.out.println(myAgent.addAmmunition(Weapon.RPG, 10));
        System.out.println(myAgent.hasWeapon(Weapon.RPG));
        System.out.println(myAgent.getAmmunitionRoundsForWeapon(Weapon.RPG));
        */
        //System.out.println(myAgent.addAmmunition(Weapon.KNIFE, 10));
		
		//myAgent.setHealth(0);
		//myAgent.addWeapon(null);
		//System.out.println(myAgent.changeHealth(-40));
		//System.out.println(myAgent.addAmmunition(Weapon.KNIFE, 100));
		/*
		System.out.println(myAgent.addAmmunition(Weapon.MACHINE_GUN, 100));
		System.out.println(myAgent.hasWeapon(Weapon.MACHINE_GUN));
		myAgent.addWeapon(Weapon.MACHINE_GUN);
		System.out.println(myAgent.getAmmunitionRoundsForWeapon(Weapon.MACHINE_GUN));
		*/
		//System.out.println(myAgent.addAmmunition(null, 100));
		//System.out.println(myAgent.addAmmunition(Weapon.HAND_GUN, -100));
		//System.out.println(myAgent.equals(myAgent));
		//System.out.println(myAgent.hashCode());

		//Adversary Tester
		/*
		Adversary myAdversary = new Adversary(AdversaryType.INFANTRY, AdversaryType.JOBNIK);
		System.out.println(myAdversary.getAdversaryType());
		System.out.println(myAdversary.isDead());
		System.out.println(myAdversary.getProtectedBy());
		*/

		//Mission Tester
		/*
		Adversary jobnik1 = new Adversary(AdversaryType.JOBNIK);
        Adversary jobnik2 = new Adversary(AdversaryType.JOBNIK);
        Adversary jobnik3 = new Adversary(AdversaryType.JOBNIK);
        TreeSet<Adversary> group1 = new TreeSet<>();
        group1.add(jobnik1);
        group1.add(jobnik2);
        group1.add(jobnik3);
        HashSet<Weapon> weapons = new HashSet<>();
        weapons.add(Weapon.HAND_GUN);
        Map<Weapon,Integer> ammoWonUponCompletion = new HashMap<>();
        ammoWonUponCompletion.put(Weapon.HAND_GUN,2);
        Mission mission = new Mission(group1,weapons,ammoWonUponCompletion,10,"Easy Jobniks");
        */
		/*
        System.out.println(mission.getName());
        System.out.println(mission.getWeaponsWonUponCompletion());
        System.out.println(mission.getAmmoWonUponCompletion());
        System.out.println(mission.getHealthWonUponCompletion());
        SortedSet<Adversary> adv = mission.getAdversaries();
        for (Adversary a : adv){
        	System.out.println(a.getAdversaryType());
        }
        SortedSet<Adversary> adv2 = mission.getLiveAdversaries();
        for (Adversary a : adv2){
        	System.out.println(a.getAdversaryType());
        }
        SortedSet<Adversary> adv3 = mission.getDeadAdversaries();
        System.out.println(adv3);
        System.out.println(mission.getDangerLevel());
        mission.adversaryKilled(jobnik1);
        System.out.println(mission.getDangerLevel());
        */
        /*
        System.out.println(mission.isCompleted());
        System.out.println(mission.getCurrentAgentHealthLoss());
        jobnik1.attack(Weapon.KNIFE, 1);
        SortedSet<Adversary> adv4 = mission.getDeadAdversaries();
        for (Adversary a : adv4){
        	System.out.println(a.getAdversaryType());
        }
        System.out.println(mission.getDangerLevel());
        System.out.println(mission.getCurrentAgentHealthLoss());
        */

        //OperationExecutor Tester
        /*
        Adversary jobnik1 = new Adversary(AdversaryType.INFANTRY);
        Adversary jobnik2 = new Adversary(AdversaryType.SUPER_SOLDIER);
        Adversary jobnik3 = new Adversary(AdversaryType.NINJA);
        TreeSet<Adversary> group1 = new TreeSet<>();
        group1.add(jobnik1);
        group1.add(jobnik2);
        group1.add(jobnik3);
        HashSet<Weapon> weapons = new HashSet<>();
        weapons.add(Weapon.HAND_GUN);
        weapons.add(Weapon.MACHINE_GUN);
        weapons.add(Weapon.RPG);
        Map<Weapon,Integer> ammoWonUponCompletion = new HashMap<>();
        ammoWonUponCompletion.put(Weapon.HAND_GUN,1);
        ammoWonUponCompletion.put(Weapon.MACHINE_GUN,1);
        ammoWonUponCompletion.put(Weapon.RPG,1);
        Mission mission = new Mission(group1,weapons,ammoWonUponCompletion,10,"Test");
        */
        /*
        SortedSet<Adversary> myProtectors = OperationExecutor.getAllProtectorsInMission(jobnik1, mission);
        for (Adversary a : myProtectors){
        	System.out.println(a.getAdversaryType());
        }
        */
        /*
        Agent agent1 = new Agent("agent1", 12);
        agent1.addWeapon(Weapon.HAND_GUN);
        agent1.addWeapon(Weapon.MACHINE_GUN);
        agent1.addWeapon(Weapon.RPG);
        agent1.changeAmmunitionRoundsForWeapon(Weapon.HAND_GUN, -4);
        agent1.changeAmmunitionRoundsForWeapon(Weapon.MACHINE_GUN, 1);
        agent1.changeAmmunitionRoundsForWeapon(Weapon.RPG, 7);
        System.out.println(agent1.getWeapons());
        */
        /*
        for (Map.Entry<Weapon, Integer> entry : agent1.getWeapons().entrySet()){
        	System.out.println(entry.getKey() + " " + entry.getValue());
        }
        */
        /*
        SortedSet<Mission> missions = new TreeSet<>();
        missions.add(mission);
        SortedSet<Agent> agents = new TreeSet<>();
        agents.add(agent1);
        OperationExecutor op = new OperationExecutor(missions, agents);
        //System.out.println(OperationExecutor.canKill(agent1, jobnik1, mission));
        //op.killAdversary(agent1, mission, jobnik1);
        //System.out.println(mission.isCompleted());
        //System.out.println(agent1.isDead());
        Set<Mission> results = op.prosecuteMissions();
        for (Mission m : results){
        	System.out.println(m.getName());
        }
        System.out.println(agent1.getHealth());
        System.out.println(agent1.getWeapons());
        */
	}
}