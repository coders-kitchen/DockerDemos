package com.coders_kitchen.pherousa;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

@Service("pollTimerService")
public class PollTimerService {

	public static final String LAST_POLL = "/tmp/last.poll";

	public Long getLastPollTimestamp() {
		Long lastPoll = 0L;
		try {
			Resource resource = new FileSystemResource(LAST_POLL);
			if (!resource.exists()) {
				setNextPollTimestamp(lastPoll);
			} else {
				BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
				lastPoll = Long.valueOf(reader.readLine());
				reader.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lastPoll;
	}

	public void setNextPollTimestamp(Long nextPollTimestamp) {
		try {
			Resource resource = new FileSystemResource(LAST_POLL);
			File file = resource.getFile();
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(String.valueOf(nextPollTimestamp));
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
