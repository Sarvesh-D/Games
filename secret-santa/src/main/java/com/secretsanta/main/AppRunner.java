package com.secretsanta.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main Class that runs the secret santa game
 * @author Sarvesh
 * @version 2.0
 * @since 12.12.2015
 */
public class AppRunner {

	private List<String> participants = new ArrayList<>();
	
	private Map<String,String> secretSantaMap = new HashMap<>();
	
	private Map<String,String> playedParticpants = new HashMap<>();
	
	private Random indexGen = new Random();
	
	private static final String INPUT_FILE = "participants";
	
	private static final Logger LOG = LoggerFactory.getLogger(AppRunner.class);
	
	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		AppRunner app = new AppRunner();
		app.initParticipants(INPUT_FILE);
		app.mapParticipants();
		//app.display();
		while(!app.gameOver()) {
			app.askForInput();
		}
		sc.close();
	}

	/**
	 * Checks if all the participants have played their turn in the game
	 * @return true if all the participants have played the game
	 */
	public boolean gameOver() {
		return playedParticpants.size() == participants.size();
	}

	/**
	 * Ask the player their name and show them whose secret santa they are.
	 * If the player had already finished their turn, they will remain secret santa
	 * for the one they were earlier.
	 */
	public void askForInput() {
		LOG.info("Enter Your Name");
		String input = sc.nextLine();
		if(playedParticpants.containsKey(input)) {
			LOG.error("You have already played. You are Secret Santa for {}",playedParticpants.get(input).toUpperCase());
		} else {
			String secretSantaFor = whoseSecretSantaAmI(input);
			if(null != secretSantaFor) {
				LOG.info("You are Secret Santa for {}",secretSantaFor.toUpperCase());
				// add the participant and secret santa to the played participants map
				playedParticpants.put(input, secretSantaFor);
			} else {
				LOG.error("You are not added as participant to play the game");
			}
		}
	}

	/**
	 * Reads the input file and adds the participants to the participants list
	 * @param inputFile containing names of participants
	 */
	public void initParticipants(String inputFile) {
		try (InputStreamReader input = new InputStreamReader(getClass().getClassLoader().getResourceAsStream(inputFile));
				BufferedReader reader = new BufferedReader(input);
				) {
			String name;
			while((name = reader.readLine()) != null) {
				participants.add(name);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Maps participants with their secret santa and stores them in the secretSantaMap 
	 */
	public void mapParticipants() {
		for (String participant : participants) {
			if(!secretSantaMap.containsKey(participant)) {
				secretSantaMap.put(participant, getSecretSantaForParticipant(participant));
			}
		}
	}
	
	/**
	 * Gets the secret santa for supplied participant
	 * @param participantName for whom secret santa is to be mapped
	 * @return secret santa for the participant
	 */
	public String getSecretSantaForParticipant(String participantName) {
		String secretSanta = participants.get(indexGen.nextInt(participants.size()));
		/*
		 * Secret Santa obtained above should not be secret santa of someone else.
		 * i.e one secret santa should not serve two or more participants
		 */
		while(participantName.equalsIgnoreCase(secretSanta) || secretSantaMap.containsValue(secretSanta)) {
			secretSanta = participants.get(indexGen.nextInt(participants.size()));
		}
		return secretSanta;
	}

	/**
	 * Displays participant and their corresponding secret santa
	 */
	public void display() {
		System.out.printf("%-30.30s  %-30.30s%n", "Participant Name", "Secret Santa");
		System.out.printf("%-30.30s  %-30.30s%n", "-----------------", "-----------");
		for(Entry<String, String> entry : secretSantaMap.entrySet()) {
			System.out.printf("%-30.30s  %-30.30s%n", entry.getKey(), entry.getValue());
		}
		System.out.println("\n***************END***************");
	}

	/**
	 * Gets participant name for the supplied secret santa
	 * @param secretSanta for whom the participant is to be fetched
	 * @return participant corresponding to supplied secret santa
	 */
	public String whoseSecretSantaAmI(String secretSanta) {
		return secretSantaMap.get(secretSanta);
	}

	public List<String> getParticipants() {
		return participants;
	}
	public Map<String, String> getSecretSantaMap() {
		return secretSantaMap;
	}
}