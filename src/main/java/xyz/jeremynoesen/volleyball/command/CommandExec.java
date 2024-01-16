package xyz.jeremynoesen.volleyball.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import xyz.jeremynoesen.volleyball.Config;
import xyz.jeremynoesen.volleyball.Message;
import xyz.jeremynoesen.volleyball.ball.Ball;
import xyz.jeremynoesen.volleyball.court.Court;
import xyz.jeremynoesen.volleyball.court.CourtIO;

/**
 * Command class, listens for volleyball command
 *
 * @author Jeremy Noesen
 */
public class CommandExec implements CommandExecutor {

    /**
     * method to make the volleyball commands work
     *
     * @param sender  command sender
     * @param command command
     * @param label   part of command immediately followed by /
     * @param args    command arguments
     * @return true
     */
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (label.equalsIgnoreCase("volleyball") || label.equalsIgnoreCase("vb")) {
                if (args.length == 1) {
                    switch (args[0].toLowerCase()) {
                        case "help":
                            if (player.hasPermission("volleyball.help"))
                                player.sendMessage(Message.getHelpMessage(player));
                            else
                                player.sendMessage(Message.ERROR_NO_PERMS);
                            break;
                        case "reload":
                            if (player.hasPermission("volleyball.reload")) {
                                CourtIO.saveAll();
                                Config.getCourtConfig().reloadConfig();
                                Config.getMessageConfig().reloadConfig();
                                CourtIO.loadAll();
                                for (Entity ball : Ball.getBalls()) {
                                    ball.remove();
                                    Ball.getBalls().remove(ball);
                                }
                                player.sendMessage(Message.SUCCESS_RELOADED);
                            } else player.sendMessage(Message.ERROR_NO_PERMS);
                            break;
                        case "list":
                            if (player.hasPermission("volleyball.list"))
                                player.sendMessage(Message.COURT_LIST.replace("$COURTS$",
                                        Court.getCourts().keySet().toString()
                                                .replace("[", "").replace("]", "")));
                            else player.sendMessage(Message.ERROR_NO_PERMS);
                            break;
                        default:
                            player.sendMessage(Message.ERROR_UNKNOWN_ARGS);
                    }
                } else if (args.length == 2) {
                    switch (args[0].toLowerCase()) {
                        case "create":
                            if (player.hasPermission("volleyball.create")) {
                                if (Court.getCourts().get(args[1]) == null) {
                                    new Court(args[1]);
                                    player.sendMessage(Message.SUCCESS_CREATED.replace("$COURT$", args[1]));
                                } else {
                                    player.sendMessage(Message.ERROR_COURT_EXISTS);
                                }
                            } else player.sendMessage(Message.ERROR_NO_PERMS);
                            break;
                        case "info":
                            if (player.hasPermission("volleyball.info")) {
                                if (Court.getCourts().get(args[1]) != null) {
                                    player.sendMessage(Court.getCourts().get(args[1]).toString());
                                } else {
                                    player.sendMessage(Message.ERROR_UNKNOWN_COURT);
                                }
                            } else player.sendMessage(Message.ERROR_NO_PERMS);
                            break;
                        case "remove":
                            if (player.hasPermission("volleyball.remove")) {
                                if (Court.getCourts().get(args[1]) != null) {
                                    Court.getCourts().remove(args[1]);
                                    player.sendMessage(Message.SUCCESS_REMOVED
                                            .replace("$COURT$", args[1]));
                                } else {
                                    player.sendMessage(Message.ERROR_UNKNOWN_COURT);
                                }
                            } else player.sendMessage(Message.ERROR_NO_PERMS);
                            break;
                        default:
                            player.sendMessage(Message.ERROR_UNKNOWN_ARGS);
                    }
                } else if (args.length == 3) {
                    Court court = Court.getCourts().get(args[1]);
                    if (court != null) {
                        switch (args[0].toLowerCase()) {
                            case "animations":
                                if (player.hasPermission("volleyball.animations")) {
                                    boolean animations = Boolean.parseBoolean(args[2]);
                                    court.setAnimations(animations);
                                    player.sendMessage(Message.SUCCESS_ANIMATIONS
                                            .replace("$COURT$", args[1])
                                            .replace("$BOOL$", Boolean.toString(animations)));
                                } else player.sendMessage(Message.ERROR_NO_PERMS);
                                break;
                            case "bounds":
                                if (player.hasPermission("volleyball.bounds")) {
                                    if (args[2].equalsIgnoreCase("pos1")) {
                                        court.selectBounds(player, 1);
                                        player.sendMessage(Message.SUCCESS_BOUNDS_SELECTED
                                                .replace("$COURT$", args[1])
                                                .replace("$POS$", "1"));
                                    } else if (args[2].equalsIgnoreCase("pos2")) {
                                        court.selectBounds(player, 2);
                                        player.sendMessage(Message.SUCCESS_BOUNDS_SELECTED
                                                .replace("$COURT$", args[1])
                                                .replace("$POS$", "2"));
                                    } else if (args[2].equalsIgnoreCase("set")) {
                                        court.setBounds(player);
                                        court.setWorld(player.getWorld());
                                        player.sendMessage(Message.SUCCESS_BOUNDS_SET
                                                .replace("$COURT$", args[1]));
                                    } else {
                                        player.sendMessage(Message.ERROR_UNKNOWN_ARGS);
                                    }
                                } else {
                                    player.sendMessage(Message.ERROR_NO_PERMS);
                                }
                                break;
                            case "editable":
                                if (player.hasPermission("volleyball.editable")) {
                                    boolean editable = Boolean.parseBoolean(args[2]);
                                    court.setEditable(editable);
                                    player.sendMessage(Message.SUCCESS_EDITABLE
                                            .replace("$COURT$", args[1])
                                            .replace("$BOOL$", Boolean.toString(editable)));
                                } else player.sendMessage(Message.ERROR_NO_PERMS);
                                break;
                            case "enabled":
                                if (player.hasPermission("volleyball.enabled")) {
                                    boolean enabled = Boolean.parseBoolean(args[2]);
                                    court.setEnabled(enabled);
                                    player.sendMessage(Message.SUCCESS_ENABLED
                                            .replace("$COURT$", args[1])
                                            .replace("$BOOL$", Boolean.toString(enabled)));
                                } else player.sendMessage(Message.ERROR_NO_PERMS);
                                break;
                            case "hints":
                                if (player.hasPermission("volleyball.hints")) {
                                    boolean hints = Boolean.parseBoolean(args[2]);
                                    court.setHints(hints);
                                    player.sendMessage(Message.SUCCESS_HINTS
                                            .replace("$COURT$", args[1])
                                            .replace("$BOOL$", Boolean.toString(hints)));
                                } else player.sendMessage(Message.ERROR_NO_PERMS);
                                break;
                            case "hitradius":
                                if (player.hasPermission("volleyball.hitradius")) {
                                    try {
                                        double hitRadius = Double.parseDouble(args[2]);
                                        court.setHitRadius(hitRadius);
                                        player.sendMessage(Message.SUCCESS_HITRADIUS
                                                .replace("$COURT$", args[1])
                                                .replace("$RADIUS$", args[2]));
                                    } catch (NumberFormatException e) {
                                        player.sendMessage(Message.ERROR_UNKNOWN_ARGS);
                                    }
                                } else player.sendMessage(Message.ERROR_NO_PERMS);
                                break;
                            case "name":
                                if (player.hasPermission("volleyball.name")) {
                                    court.setName(args[2]);
                                    player.sendMessage(Message.SUCCESS_NAME
                                            .replace("$OLD$", args[1])
                                            .replace("$NEW$", args[2]));
                                } else player.sendMessage(Message.ERROR_NO_PERMS);
                                break;
                            case "net":
                                if (player.hasPermission("volleyball.net")) {
                                    if (args[2].equalsIgnoreCase("pos1")) {
                                        court.selectNet(player, 1);
                                        player.sendMessage(Message.SUCCESS_NET_SELECTED
                                                .replace("$COURT$", args[1])
                                                .replace("$POS$", "1"));
                                    } else if (args[2].equalsIgnoreCase("pos2")) {
                                        court.selectNet(player, 2);
                                        player.sendMessage(Message.SUCCESS_NET_SELECTED
                                                .replace("$COURT$", args[1])
                                                .replace("$POS$", "2"));
                                    } else if (args[2].equalsIgnoreCase("set")) {
                                        court.setNet(player);
                                        player.sendMessage(Message.SUCCESS_NET_SET
                                                .replace("$COURT$", args[1]));
                                    } else {
                                        player.sendMessage(Message.ERROR_UNKNOWN_ARGS);
                                    }
                                } else {
                                    player.sendMessage(Message.ERROR_NO_PERMS);
                                }
                                break;
                            case "particles":
                                if (player.hasPermission("volleyball.particles")) {
                                    boolean particles = Boolean.parseBoolean(args[2]);
                                    court.setParticles(particles);
                                    player.sendMessage(Message.SUCCESS_PARTICLES
                                            .replace("$COURT$", args[1])
                                            .replace("$BOOL$", Boolean.toString(particles)));
                                } else player.sendMessage(Message.ERROR_NO_PERMS);
                                break;
                            case "restrictions":
                                if (player.hasPermission("volleyball.restrictions")) {
                                    boolean restrictions = Boolean.parseBoolean(args[2]);
                                    court.setRestrictions(restrictions);
                                    player.sendMessage(Message.SUCCESS_RESTRICTIONS
                                            .replace("$COURT$", args[1])
                                            .replace("$BOOL$", Boolean.toString(restrictions)));
                                } else player.sendMessage(Message.ERROR_NO_PERMS);
                                break;
                            case "scoring":
                                if (player.hasPermission("volleyball.scoring")) {
                                    boolean scoring = Boolean.parseBoolean(args[2]);
                                    court.setScoring(scoring);
                                    player.sendMessage(Message.SUCCESS_SCORING
                                            .replace("$COURT$", args[1])
                                            .replace("$BOOL$", Boolean.toString(scoring)));
                                } else player.sendMessage(Message.ERROR_NO_PERMS);
                                break;
                            case "sounds":
                                if (player.hasPermission("volleyball.sounds")) {
                                    boolean sounds = Boolean.parseBoolean(args[2]);
                                    court.setSounds(sounds);
                                    player.sendMessage(Message.SUCCESS_SOUNDS
                                            .replace("$COURT$", args[1])
                                            .replace("$BOOL$", Boolean.toString(sounds)));
                                } else player.sendMessage(Message.ERROR_NO_PERMS);
                                break;
                            case "speed":
                                if (player.hasPermission("volleyball.speed")) {
                                    try {
                                        double speed = Double.parseDouble(args[2]);
                                        court.setSpeed(speed);
                                        player.sendMessage(Message.SUCCESS_SPEED
                                                .replace("$COURT$", args[1])
                                                .replace("$SPEED$", args[2]));
                                    } catch (NumberFormatException e) {
                                        player.sendMessage(Message.ERROR_UNKNOWN_ARGS);
                                    }
                                } else player.sendMessage(Message.ERROR_NO_PERMS);
                                break;
                            case "teams":
                                if (player.hasPermission("volleyball.teams")) {
                                    boolean teams = Boolean.parseBoolean(args[2]);
                                    court.setTeams(teams);
                                    player.sendMessage(Message.SUCCESS_TEAMS
                                            .replace("$COURT$", args[1])
                                            .replace("$BOOL$", Boolean.toString(teams)));
                                } else player.sendMessage(Message.ERROR_NO_PERMS);
                                break;
                            case "texture":
                                if (player.hasPermission("volleyball.texture")) {
                                    court.setTexture(args[2]);
                                    player.sendMessage(Message.SUCCESS_TEXTURE
                                            .replace("$COURT$", args[1])
                                            .replace("$URL$", args[2]));
                                } else player.sendMessage(Message.ERROR_NO_PERMS);
                                break;
                            default:
                                player.sendMessage(Message.ERROR_UNKNOWN_ARGS);
                        }
                    } else player.sendMessage(Message.ERROR_UNKNOWN_COURT);
                } else {
                    player.sendMessage(Message.ERROR_UNKNOWN_ARGS);
                }
            }
        }
        return true;
    }
}
