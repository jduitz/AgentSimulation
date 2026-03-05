package edu.yu.cs.intro.secretOps;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeSet;

public class OperationExecutorDemo {
    public static void main(String[] args) {
        OperationExecutorDemo demo = new OperationExecutorDemo();
        demo.executeAndReport(demo.createSingleAgentSuccess(),"Single Agent Success");
        demo.executeAndReport(demo.createSingleAgentFailure(),"Single Agent Failure");
        demo.executeAndReport(demo.createMultiAgentSuccess(),"Multi-Agent Success");
        demo.executeAndReport(demo.createMultiAgentFailure(),"Multi-Agent Failure");
    }

    private void executeAndReport(OperationExecutor bot, String operationName){
        System.out.println("******************************************************************************");
        System.out.println("******************************************************************************");
        System.out.println("*********************OPERATION: " + operationName +  "***************************");
        System.out.println("******************************************************************************");
        System.out.println("******************************************************************************");
        boolean success = bot.execute();
        String message = success ? "WAS COMPLETED SUCCESSFULLY" : "COULD NOT BE COMPLETED";
        System.out.println("*****OPERATION " + operationName + " " + message + "*****");
        System.out.println("STATE OF ALL MISSIONS IN THIS OPERATION:");
        for(Mission mission : bot.getAllMissions()){
            System.out.println("**********************************************");
            System.out.println("Mission name: " + mission.getName());
            System.out.println("Mission completed: " + mission.isCompleted());
            System.out.println("State of Adversaries in Mission:");
            for(Adversary m : mission.getAdversaries()){
                System.out.println(m.getAdversaryType().name() + ": is dead - " + m.isDead());
            }
        }
    }

    public OperationExecutor createMultiAgentFailure(){
        TreeSet<Mission> missions = new TreeSet<>();
        missions.add(this.createEasyJobniksMission());
        missions.add(this.createDoubleInfantryMission());
        missions.add(this.createFearsomeFoursomeMission());
        Agent agent1 = new Agent("Agent 1",5);
        Agent agent2 = new Agent("Agent 2",10);
        agent2.addWeapon(Weapon.HAND_GUN);
        agent2.addWeapon(Weapon.MACHINE_GUN);
        agent2.addWeapon(Weapon.RPG);
        agent2.addAmmunition(Weapon.HAND_GUN,1);
        agent2.addAmmunition(Weapon.MACHINE_GUN,6);

        TreeSet<Agent> agents = new TreeSet<>();
        agents.add(agent1);
        agents.add(agent2);
        return new OperationExecutor(missions, agents);
    }


    public OperationExecutor createSingleAgentFailure(){
        TreeSet<Mission> missions = new TreeSet<>();
        missions.add(this.createEasyJobniksMission());
        missions.add(this.createDoubleInfantryMission());
        missions.add(this.createFearsomeFoursomeMission());
        Agent agent1 = new Agent("Agent 1",100);
        TreeSet<Agent> agents = new TreeSet<>();
        agents.add(agent1);
        return new OperationExecutor(missions, agents);
    }


    public OperationExecutor createMultiAgentSuccess(){
        TreeSet<Mission> missions = new TreeSet<>();
        missions.add(this.createEasyJobniksMission());
        missions.add(this.createDoubleInfantryMission());
        missions.add(this.createFearsomeFoursomeMission());
        Agent agent1 = new Agent("Agent 1",9);
        Agent agent2 = new Agent("Agent 2",100);
        agent2.addWeapon(Weapon.HAND_GUN);
        agent2.addWeapon(Weapon.MACHINE_GUN);
        agent2.addWeapon(Weapon.RPG);
        agent2.addAmmunition(Weapon.HAND_GUN,2);
        agent2.addAmmunition(Weapon.MACHINE_GUN,7);
        agent2.addAmmunition(Weapon.RPG,13);

        TreeSet<Agent> agents = new TreeSet<>();
        agents.add(agent1);
        agents.add(agent2);
        return new OperationExecutor(missions, agents);
    }

    public OperationExecutor createSingleAgentSuccess(){
        TreeSet<Mission> missions = new TreeSet<>();
        missions.add(this.createEasyJobniksMission());
        missions.add(this.createDoubleInfantryMission());
        missions.add(this.createJobnikInfantryMission());
        missions.add(this.createTripleThreatMission());
        missions.add(this.createInfantryQuadMission());
        missions.add(this.createFearsomeFoursomeMission());
        Agent agent1 = new Agent("Agent 1",100);
        TreeSet<Agent> agents = new TreeSet<>();
        agents.add(agent1);
        return new OperationExecutor(missions, agents);
    }

    private Mission createEasyJobniksMission(){
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
        return new Mission(group1,weapons,ammoWonUponCompletion,10,"Easy Jobniks");
    }

    private Mission createDoubleInfantryMission(){
        Adversary infantry1 = new Adversary(AdversaryType.INFANTRY);
        Adversary infantry2 = new Adversary(AdversaryType.INFANTRY);
        TreeSet<Adversary> group1 = new TreeSet<>();
        group1.add(infantry1);
        group1.add(infantry2);

        HashSet<Weapon> weapons = new HashSet<>();
        weapons.add(Weapon.MACHINE_GUN);
        Map<Weapon,Integer> ammoWonUponCompletion = new HashMap<>();
        ammoWonUponCompletion.put(Weapon.MACHINE_GUN,6);
        ammoWonUponCompletion.put(Weapon.HAND_GUN,2);
        return new Mission(group1,weapons,ammoWonUponCompletion,0,"Double Infantry");
    }

    private Mission createJobnikInfantryMission(){
        Adversary jobnik1 = new Adversary(AdversaryType.JOBNIK);
        Adversary jobnik2 = new Adversary(AdversaryType.JOBNIK);
        Adversary jobnik3 = new Adversary(AdversaryType.JOBNIK);
        Adversary infantry = new Adversary(AdversaryType.INFANTRY);
        TreeSet<Adversary> group1 = new TreeSet<>();
        group1.add(jobnik1);
        group1.add(jobnik2);
        group1.add(jobnik3);
        group1.add(infantry);

        HashSet<Weapon> weapons = new HashSet<>();
        weapons.add(Weapon.HAND_GUN);
        Map<Weapon,Integer> ammoWonUponCompletion = new HashMap<>();
        ammoWonUponCompletion.put(Weapon.MACHINE_GUN,6);
        return new Mission(group1,weapons,ammoWonUponCompletion,0,"J&I");
    }

    private Mission createTripleThreatMission(){
        Adversary jobnik = new Adversary(AdversaryType.JOBNIK);
        Adversary infantry = new Adversary(AdversaryType.INFANTRY);
        Adversary ninja = new Adversary(AdversaryType.NINJA);
        TreeSet<Adversary> group = new TreeSet<>();
        group.add(jobnik);
        group.add(infantry);
        group.add(ninja);

        HashSet<Weapon> weapons = new HashSet<>();
        weapons.add(Weapon.RPG);
        Map<Weapon,Integer> ammoWonUponCompletion = new HashMap<>();
        ammoWonUponCompletion.put(Weapon.MACHINE_GUN,6);
        ammoWonUponCompletion.put(Weapon.RPG,4);
        ammoWonUponCompletion.put(Weapon.HAND_GUN,6);
        return new Mission(group,weapons,ammoWonUponCompletion,0,"Triple Threat");
    }

    private Mission createInfantryQuadMission(){
        Adversary infantry = new Adversary(AdversaryType.INFANTRY);
        Adversary infantry1 = new Adversary(AdversaryType.INFANTRY);
        Adversary infantry2 = new Adversary(AdversaryType.INFANTRY);
        Adversary infantry3 = new Adversary(AdversaryType.INFANTRY);
        TreeSet<Adversary> group = new TreeSet<>();
        group.add(infantry);
        group.add(infantry1);
        group.add(infantry2);
        group.add(infantry3);

        HashSet<Weapon> weapons = new HashSet<>();
        weapons.add(Weapon.RPG);
        Map<Weapon,Integer> ammoWonUponCompletion = new HashMap<>();
        ammoWonUponCompletion.put(Weapon.RPG,16);
        ammoWonUponCompletion.put(Weapon.HAND_GUN,1);
        return new Mission(group,weapons,ammoWonUponCompletion,0,"Infantry Quad");
    }


    private Mission createFearsomeFoursomeMission(){
        Adversary jobnik = new Adversary(AdversaryType.JOBNIK);
        Adversary infantry = new Adversary(AdversaryType.INFANTRY);
        Adversary ninja = new Adversary(AdversaryType.NINJA);
        Adversary superSoldier = new Adversary(AdversaryType.SUPER_SOLDIER);
        TreeSet<Adversary> group = new TreeSet<>();
        group.add(superSoldier);
        group.add(jobnik);
        group.add(ninja);
        group.add(infantry);
        HashSet<Weapon> weapons = new HashSet<>();
        Map<Weapon,Integer> ammoWonUponCompletion = new HashMap<>();
        return new Mission(group,weapons,ammoWonUponCompletion,0,"Fearsome Foursome");
    }
}
