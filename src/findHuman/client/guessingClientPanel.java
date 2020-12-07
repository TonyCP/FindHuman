package findHuman.client;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class guessingClientPanel extends JPanel implements ActionListener {

    private static final Color CARD_TABLE_GREEN = new Color(37, 93, 54);
    private static final Color TEXT_COLOR = new Color(230, 230, 230);
    //private static final Dimension BUTTONS_DIMENSION = new Dimension(110, 25);
    private client controller;
    private JLabel taskLabel;
    private JLabel questionLabel;
    private JPanel messageServerPanel;
    private ScrollPane pane;
    private JTextArea messageTextArea;
    private JPanel responsePanel;
    private JButton responseButton1;
    private JButton responseButton2;
    private JButton responseButton3;


    public guessingClientPanel(client controller){
        this.controller = controller;
        setupPanel();
        setupActionListeners();
    }

    private void setupPanel() {
        setBackground(CARD_TABLE_GREEN);
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        // Task label //
        taskLabel = new JLabel();
        taskLabel.setText("TASK: Identify the human response.");
        taskLabel.setForeground(TEXT_COLOR);
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(taskLabel, constraints);

        // Question from server //
        questionLabel = new JLabel();
        questionLabel.setForeground(TEXT_COLOR);
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(questionLabel, constraints);

        // Messages from server //
        Border border = BorderFactory.createTitledBorder("MESSAGES: ");
        messageServerPanel = new JPanel();
        messageServerPanel.setBackground(CARD_TABLE_GREEN);
        pane = new ScrollPane();
        messageTextArea = new JTextArea();
        pane.add(messageTextArea);
        messageServerPanel.setBorder(border);
        messageServerPanel.add(pane);
        constraints.gridx = 0;
        constraints.gridy = 2;
        add(messageServerPanel, constraints);

        // Response //
        responsePanel = new JPanel();
        responseButton1 = new JButton();
        responseButton2 = new JButton();
        responseButton3 = new JButton();
        responsePanel.setBackground(CARD_TABLE_GREEN);
        responsePanel.add(responseButton1);
        responsePanel.add(responseButton2);
        responsePanel.add(responseButton3);
        responseButton1.setEnabled(false);
        responseButton1.setVisible(true);
        responseButton2.setEnabled(false);
        responseButton2.setVisible(true);
        responseButton3.setEnabled(false);
        responseButton3.setVisible(true);
        constraints.gridx = 0;
        constraints.gridy = 3;
        add(responsePanel, constraints);

    }

    public void setQuestionLabel(String question) {
        questionLabel.setText("QUESTION: " + question);
        showChanges();
    }

    public void messageToGuessClient(String text){
        messageTextArea.append(text +"\n");
        showChanges();
    }

    public void setGuessResponseButtons(ArrayList<String> response) {
        this.responseButton1.setText(response.get(0));
        this.responseButton2.setText(response.get(1));
        this.responseButton3.setText(response.get(2));
        // Re-enable buttons on text reset //
        responseButton1.setEnabled(true);
        responseButton2.setEnabled(true);
        responseButton3.setEnabled(true);
        showChanges();
    }

    public void disableResponseButtons(Boolean b) {
        responseButton1.setEnabled(b);
        responseButton2.setEnabled(b);
        responseButton3.setEnabled(b);
    }

    private void setupActionListeners() {
        responseButton1.addActionListener(this);
        responseButton2.addActionListener(this);
        responseButton3.addActionListener(this);
    }

    private void showChanges() {
        revalidate();
        repaint();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object target = e.getSource();
        if(target == responseButton1) {
            controller.getClientModel().sendServerMessage(responseButton1.getText().substring(responseButton1.getText().lastIndexOf(":") + 2));
        } else if (target == responseButton2) {
            controller.getClientModel().sendServerMessage(responseButton2.getText().substring(responseButton2.getText().lastIndexOf(":") + 2));
        } else if (target == responseButton3) {
            controller.getClientModel().sendServerMessage(responseButton3.getText().substring(responseButton3.getText().lastIndexOf(":") + 2));
        }

        // Disable buttons after response click
        responseButton1.setEnabled(false);
        responseButton2.setEnabled(false);
        responseButton3.setEnabled(false);
    }


}
