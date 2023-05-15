package com.undeadevs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class MainController {

    @FXML
    private TextFlow textContainer;

    @FXML
    private Text textCase;

    @FXML
    private Text textTrue;

    @FXML
    private Text textFalse;

    @FXML
    private Text wpm;

    private boolean isWrong = false;
    private String rawTextCase = "";
    private long startTime =-1;

    @FXML
    private void initialize() throws MalformedURLException, IOException {
        generateText();
        textCase.setText(rawTextCase);
    }

    private void generateText() throws MalformedURLException, IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL("http://metaphorpsum.com/paragraphs/1/5")
                .openConnection();
        InputStream inputStream = connection.getInputStream();
        rawTextCase = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
    }

    public void checkKey(String keyString) {
        String textCaseString = textCase.getText();
        String textTrueString = textTrue.getText();
        String textFalseString = textFalse.getText();
        if (keyString.equals("")) {
            if (textTrueString.length() > 1 || textFalseString.length() > 1) {
                if (isWrong) {
                    textFalse.setText(textFalseString.substring(0, textFalseString.length() - 1));
                    textCase.setText(textFalseString.charAt(textFalseString.length() - 1) + textCaseString);
                    if (textFalse.getText().length() == 1) {
                        isWrong = false;
                    }
                    // } else {
                    // textTrue.setText(textTrueString.substring(0, textTrueString.length() - 1));
                    // textCase.setText(textTrueString.charAt(textTrueString.length() - 1) +
                    // textCaseString);
                }
            }
        } else {
            String expectedCharString = textCaseString.charAt(0) + "";
            if (isWrong) {
                textFalse.setText(textFalseString + expectedCharString);
                textCase.setText(textCaseString.substring(1, textCaseString.length()));
            } else {
                if (expectedCharString.equals(keyString)) {
                    if(textTrueString.length() == 1 && textFalseString.length() == 1){
                        startTime = System.currentTimeMillis();
                    }
                    textTrue.setText(textTrueString + expectedCharString);
                    textCase.setText(textCaseString.substring(1, textCaseString.length()));
                    if(textCase.getText().equals("")){
                        try {
                            onFinish();
                        } catch (MalformedURLException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                } else {
                    textFalse.setText(textFalseString + expectedCharString);
                    textCase.setText(textCaseString.substring(1, textCaseString.length()));
                    isWrong = true;
                }
            }
        }
    }

    private void onFinish() throws MalformedURLException, IOException{
        String textTrueString = textTrue.getText();
        int wordCount = textTrueString.split(" ").length;
        long deltaTime = System.currentTimeMillis() - startTime;
        double wpmVal = ((double) wordCount) / (deltaTime / 1000.0 / 60.0 );
        wpm.setText(""+((int) wpmVal));

        generateText();
        textTrue.setText("");
        textCase.setText(rawTextCase);
    }
}
