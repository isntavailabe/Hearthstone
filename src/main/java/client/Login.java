package client;

import client.models.Environment;
import client.models.cards.Collection;
import client.models.entities.heroes.Hero;
import client.models.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static client.utils.Converter.*;
import static client.models.Environment.getInstance;
import static client.utils.Logger.logger;

public class Login {
    private JFrame frame;
    private JPanel panel;
    private JLabel user;
    private JLabel pass;
    private JLabel newAcc;
    private JTextField username;
    private JPasswordField password;
    private JButton login;
    private Checkbox register;
    private Environment environment;

    public Login(Environment environment) {
        this.environment = environment;
        frame = new JFrame("login");
        frame.setSize(400, 250);
        frame.setLocationRelativeTo(null);
        setPanel();
        frame.add(panel);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void setPanel() {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 400, 250);
        user = new JLabel("USERNAME : ");
        user.setBounds(70, 55, 200, 15);
        pass = new JLabel("PASSWORD : ");
        pass.setBounds(70, 85, 200, 15);
        newAcc = new JLabel("CREATE A NEW ACCOUNT?");
        newAcc.setBounds(95, 115, 170, 15);
        username = new JTextField(20);
        username.setBounds(180, 53, 110, 20);
        password = new JPasswordField(20);
        password.setBounds(180, 80, 110, 20);
        login = new JButton("LOGIN");
        login.setBounds(220, 140, 70, 30);
        register = new Checkbox();
        register.setBounds(80, 110, 10, 30);
        panel.add(user);
        panel.add(pass);
        panel.add(username);
        panel.add(password);
        panel.add(login);
        panel.add(register);
        panel.add(newAcc);
        addAction();
    }

    private void addAction(){
        login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = username.getText();
                String passwrd = password.getText();
                if (!name.isEmpty() && !passwrd.isEmpty()) {
                    if (!register.getState()) {
                        for (Player p : environment.getPlayers()) {
                            if (name.equals(p.getName()) && passwrd.equals(p.getPass())) {
                                loadCards(p);
                                loadHeroes(p);
                                environment.setCurrentPlayer(p);
                                environment.setKey(p.getName() + p.getId());
                                frame.dispose();
                                Game game = new Game("Client.Buttons.Game", getInstance());
                                logger(p.getName() + p.getId(), "sign-in");
                                game.start();
                                break;
                            }
                        }
                        if(environment.getCurrentPlayer() == null)
                            JOptionPane.showMessageDialog(frame, "WRONG PASSWORD OR USERNAME!");
                    } else {
                        if (validName(name)) {
                            Register(name, passwrd);
                            frame.dispose();
                            Game game = new Game("Client.Buttons.Game", getInstance());
                            game.start();
                        } else {
                            JOptionPane.showMessageDialog(frame, "THIS USERNAME IS TAKEN!");
                        }
                    }
                }
            }
        });
    }

    private boolean validName(String name) {
        for (Player p : environment.getPlayers()) {
            if (p.getName().equals(name)) {
                return false;
            }
        }
        return true;
    }

    private void Register(String name, String password) {
        Player p = new Player(name, password, System.currentTimeMillis());
        p.setCollection(environment.getFirstcollection());
        p.setUnlockedHeroes(environment.getFirstheroes());
        Collection c = new Collection();
        c.setName("default");
        c.setHeroClass("rogue");
        p.getDeck().put("default", c);
        loadHeroes(p);
        environment.setCurrentPlayer(p);
        environment.setKey(p.getName() + p.getId());
        logger(p.getName() + p.getId(), "username : " + p.getName() + "\n" + "password : " + p.getPass() + "\n" + "created at : ");
        playerToJson(p);
    }

    private void loadCards(Player p) {
        ArrayList<String> Name = new ArrayList<String>();
        for (client.models.cards.card card : p.getCollection()) {
            Name.add(card.getName() + ".json");
        }
        p.setCollection(cardFromJson(Name));
        Name.clear();
    }

    private void loadHeroes(Player p) {
        ArrayList<String> Name = new ArrayList<String>();
        for (String key : p.getDeck().keySet()) {
            for (client.models.cards.card card : p.getDeck().get(key).getDeck()) {
                Name.add(card.getName() + ".json");
            }
            p.getDeck().get(key).getDeck().clear();
            p.getDeck().get(key).getDeck().addAll(cardFromJson(Name));
            Name.clear();
        }

        for (Hero hero : p.getUnlockedHeroes()) {
            for (client.models.cards.card card : hero.getCards()) {
                Name.add(card.getName() + ".json");
            }
            hero.setCards(cardFromJson(Name));
            Name.clear();
        }
    }
}
