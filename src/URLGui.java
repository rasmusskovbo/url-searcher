import javax.swing.*;
import java.util.ArrayList;

public class URLGui {

    private JTextField txtResult;
    private JTextField txtURL;
    private ArrayList<String> list;
    private JDBCWriter jbdcWriter;
    private JButton pbSaveToDB;
    private JButton pbSearchDB;
    private JLabel labelCount;

    public URLGui() {
        jbdcWriter = new JDBCWriter();
        createGui();
    }

    public void createGui() {
        // Create frame + components
        final JFrame frame = new JFrame("Database Uploader");
        JPanel panelTop = new JPanel();
        JButton pbConnect = new JButton("Connect");
        JButton pbRetrieveURL = new JButton("Get URL");
        pbSearchDB = new JButton("Search Website");
        pbSaveToDB = new JButton("Save To Database");
        txtURL = new JTextField("", 50);
        txtResult = new JTextField("", 20);

        labelCount = new JLabel("-1");

        // Add components to panelTop (holder)
        frame.add(panelTop);
        panelTop.add(pbConnect);
        panelTop.add(pbRetrieveURL);
        panelTop.add(pbSaveToDB);
        panelTop.add(pbSearchDB);
        panelTop.add(txtURL);
        panelTop.add(txtResult);
        panelTop.add(labelCount);

        // Disable buttons at start before connection is made
        pbSaveToDB.setEnabled(false);
        pbSearchDB.setEnabled(false);

        // Add actions
        pbRetrieveURL.addActionListener(a -> retrieveUrl());
        pbConnect.addActionListener(a -> connect());
        pbSaveToDB.addActionListener(a -> saveToDB());
        pbSearchDB.addActionListener(a -> searchDB());

        // Pack, set and show window
        frame.pack();
        frame.setBounds(100,100,600,200);
        frame.setVisible(true);
    }

    public void retrieveUrl() {
        String url = txtURL.getText();
        System.out.println(url);

        URLReader urlReader = new URLReader();
        list = urlReader.readUrl(url);

        String[] strArr;
        ArrayList<String> list2 = new ArrayList<>();
        for (String line : list) {
            if (line.length() > 1) {
                strArr = line.split("<");
                for (String ss : strArr) {
                    list2.add("<" + ss);
                }
            }
        }

        list.addAll(list2);
        txtResult.setText("" + list.size());
    }

    public void connect() {
        boolean isConnected = jbdcWriter.setConnection();
        System.out.println("Got connection: " + isConnected);
        pbSaveToDB.setEnabled(isConnected);
        pbSearchDB.setEnabled(isConnected);
    }

    public void saveToDB () {
        int lineSize = jbdcWriter.writeLines(txtURL.getText(), list);
        txtResult.setText("Lines saved to database: " + lineSize);
    }

    public void searchDB () {
        String url = txtURL.getText();
        String search = txtResult.getText();
        int count = jbdcWriter.searchDB(url, search);
        labelCount.setText("" + count);

    }
}
