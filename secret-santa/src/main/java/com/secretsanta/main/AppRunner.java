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

/**
 * @author Sarvesh
 *
 */
public class AppRunner {

	private List<String> participants = new ArrayList<>();
	private Map<String,String> secretSantaMap = new HashMap<>();
	private static final String INPUT_FILE = "participants";
	private Random indexGen = new Random();

	public static void main(String[] args) {
		AppRunner app = new AppRunner();
		app.initParticipants(INPUT_FILE);
		app.mapParticipants();
		app.display();
	}

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

	public void mapParticipants() {
		for (String participant : participants) {
			if(!secretSantaMap.containsKey(participant)) {
				secretSantaMap.put(participant, getSecretSantaForParticipant(participant));
			}
		}
	}

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

	public void display() {
		System.out.printf("%-30.30s  %-30.30s%n", "Participant Name", "Secret Santa");
		System.out.printf("%-30.30s  %-30.30s%n", "-----------------", "-----------");
		for(Entry<String, String> entry : secretSantaMap.entrySet()) {
			System.out.printf("%-30.30s  %-30.30s%n", entry.getKey(), entry.getValue());
		}
		System.out.println("\n***************END***************");
	}

	public List<String> getParticipants() {
		return participants;
	}
	public Map<String, String> getSecretSantaMap() {
		return secretSantaMap;
	}
}