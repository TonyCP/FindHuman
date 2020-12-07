package findHuman.client;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class hidingClientPanel extends JPanel implements ActionListener, ListSelectionListener {

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
    private JList<String> nounArea;
    private DefaultListModel<String> nounList;
    private JScrollPane nounScroll;
    private JList<String> verbArea;
    private DefaultListModel<String> verbList;
    private JScrollPane verbScroll;
    private JList<String> subjectArea;
    private DefaultListModel<String> subjectList;
    private JScrollPane subjectScroll;
    private JButton submitButton;
    private String responseToString;

    public hidingClientPanel(client controller){
        this.controller = controller;
        setupPanel();
        setupListeners();
    }

    public void setupPanel(){
        setBackground(CARD_TABLE_GREEN);
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        // Task label //
        taskLabel = new JLabel();
        taskLabel.setText("TASK: Respond as human.");
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
        responsePanel.setBackground(CARD_TABLE_GREEN);
        submitButton = new JButton();
        submitButton.setEnabled(false);
        submitButton.setVisible(true);

        nounList = new DefaultListModel<>();
        nounArea = new JList<>(nounList);
        nounScroll = new JScrollPane();
        nounScroll.add(nounArea);

        verbList = new DefaultListModel<>();
        verbArea = new JList<>(verbList);
        verbScroll = new JScrollPane();
        verbScroll.add(verbArea);

        subjectList = new DefaultListModel<>();
        subjectArea = new JList<>(subjectList);
        subjectScroll = new JScrollPane();
        subjectScroll.add(subjectArea);

        responsePanel.add(nounScroll);
        responsePanel.add(verbScroll);
        responsePanel.add(subjectScroll);
        responsePanel.add(submitButton);
        constraints.gridx = 0;
        constraints.gridy = 3;
        add(responsePanel, constraints);
    }


    public void setQuestionLabel(String question) {
        questionLabel.setText("QUESTION: " + question);
        showChanges();
    }

    public void messageToHideClient(String text){
        messageTextArea.append(text +"\n");
        showChanges();
    }

    // Set response here and enable submit
    public void setResponsePanel(String[] noun, String[] verb, String[] subject){
        for(int i = 0; i < noun.length; i++){
            subjectList.addElement(noun[i] + "\n");
        }

        for(int i = 0; i < verb.length; i++){
            verbList.addElement(verb[i] + "\n");
        }

        for(int i = 0; i < subject.length; i++){
            subjectList.addElement(noun[i] + "\n");
        }
        submitButton.setEnabled(true);
        showChanges();
    }

    public void setResponseString(String noun, String verb, String subject){
        this.responseToString = noun + " " + verb + " " + subject;
    }

    public void disableSubmitButton(Boolean b) {
        submitButton.setEnabled(b);
    }

    public void showChanges(){
        revalidate();
        repaint();
        setVisible(true);
    }

    public void setupListeners() {
        submitButton.addActionListener(this);
        nounArea.addListSelectionListener(this);
        verbArea.addListSelectionListener(this);
        subjectArea.addListSelectionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object target = e.getSource();
        if(target == submitButton) {
            controller.getClientModel().sendServerMessage(this.responseToString);
        }
        submitButton.setEnabled(false);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        setResponseString(nounArea.getSelectedValue(), verbArea.getSelectedValue(), subjectArea.getSelectedValue());
    }
}
