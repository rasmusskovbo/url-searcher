import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class URLReader {

    public ArrayList<String> readUrl (String aUrl) {
        ArrayList<String> list = new ArrayList<>();
        try {
            URL url = new URL(aUrl);
            try {
                InputStreamReader inread = new InputStreamReader(url.openStream());
                BufferedReader buffr = new BufferedReader(inread);
                String line = buffr.readLine();
                while (line != null) {
                    list.add(line);
                    line = buffr.readLine();
                }
            } catch (IOException e) {
                System.out.print("Issues reading from URL: " + e);
            }
        } catch (MalformedURLException e) {
            System.out.print("Error in URL: " + e);
        }
        return list;
    }
}
