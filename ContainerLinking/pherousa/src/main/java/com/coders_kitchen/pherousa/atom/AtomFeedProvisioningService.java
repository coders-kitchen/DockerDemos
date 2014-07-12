package com.coders_kitchen.pherousa.atom;

import com.coders_kitchen.pherousa.entity.BookOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Service("atomFeedProvisioningService")
@PropertySource({"classpath:atomfeed.properties"})
public class AtomFeedProvisioningService {

    public static final String POST = "POST";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String ATOM_XML = "application/atom+xml";

    @Value("${feed.location}")
    private String feedLocation;

    @Autowired
    private AtomFeedEntryBuilder atomFeedEntryBuilder;

    public void provisionAtomFeed(BookOrder bookOrder) {
        String id = bookOrder.getId();
        String atomFeedEntry = atomFeedEntryBuilder.buildAtomFeedEntry(id);

        try {
            HttpURLConnection connection = prepareConnection();
            sendRequest(connection, atomFeedEntry);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private HttpURLConnection prepareConnection() throws IOException {
        URL url = new URL(feedLocation);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod(POST);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setUseCaches(false);
        connection.setRequestProperty(CONTENT_TYPE, ATOM_XML);

        return connection;
    }

    private void sendRequest(HttpURLConnection connection, String atomFeedEntry) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
        writer.write(atomFeedEntry);
        writer.flush();

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        writer.close();
        reader.close();
    }
}
