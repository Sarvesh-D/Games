package com.secretsanta.test;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.secretsanta.main.AppRunner;

import junit.framework.Assert;

public class TestAppRunner {
	
	private static final String INPUT_FILE = "participants_test";
	private AppRunner appRunner;

	@Before
	public void setUp() throws Exception {
		appRunner = new AppRunner();
		appRunner.initParticipants(INPUT_FILE);
	}

	@Test
	public void testInitParticipants() {
		Assert.assertEquals(9, appRunner.getParticipants().size());
	}

	@Test
	public void testMapParticipants() {
		appRunner.mapParticipants();
		Set<String> mappedParticipantSet = new HashSet<>(appRunner.getSecretSantaMap().values());
		Assert.assertEquals(mappedParticipantSet.size(), appRunner.getParticipants().size());
	}

	@Test
	public void testGetSecretSantaForParticipant() {
		String participant = "Rachel";
		String secretSanta = appRunner.getSecretSantaForParticipant(participant);
		Assert.assertNotSame(participant, secretSanta);
	}

}
