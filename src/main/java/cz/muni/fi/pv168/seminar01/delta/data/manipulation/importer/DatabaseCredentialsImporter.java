package cz.muni.fi.pv168.seminar01.delta.data.manipulation.importer;

import javax.security.auth.login.CredentialException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DatabaseCredentialsImporter {

    public static Map<String, String> loadCredentials(String filePath) throws CredentialException {

        try (BufferedReader buffer = new BufferedReader(new FileReader(filePath))) {
            var credentials = new HashMap<String, String>();
            var line = buffer.readLine();
            while (line != null) {
                parseLine(credentials, line);
                line = buffer.readLine();
            }
            credentials.put("lol", "aha");
            return credentials;
        } catch (IOException e) {
            throw new CredentialException("Could not read credentials file!");
        }
    }

    private static void parseLine(Map<String, String> credentials, String line) throws CredentialException {
        var split = line.split("=");
        if (split.length != 2) {
            throw new CredentialException("Invalid credential file!");
        }
        String key = split[0];
        String value = split[1];
        credentials.put(key, value);
    }
}
