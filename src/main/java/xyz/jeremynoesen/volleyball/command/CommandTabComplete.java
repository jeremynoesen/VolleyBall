package xyz.jeremynoesen.volleyball.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import xyz.jeremynoesen.volleyball.court.Court;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * class to enable tab complete functionality for the volleyball commands
 *
 * @author Jeremy Noesen
 */
public class CommandTabComplete implements TabCompleter {

    /**
     * method to implement the tab list for volleyball command
     *
     * @param sender  command sender
     * @param command command
     * @param label   command label followed after the /
     * @param args    command arguments
     * @return tab list
     */
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        ArrayList<String> tabList = new ArrayList<>();

        Set<String> courts = Court.getCourts().keySet();

        if (sender instanceof Player && (label.equalsIgnoreCase("volleyball") || label.equalsIgnoreCase("vb"))) {

            Player player = (Player) sender;

            if (args.length == 1) {

                if (args[0].equalsIgnoreCase("")) {

                    if (player.hasPermission("volleyball.help")) tabList.add("help");
                    if (player.hasPermission("volleyball.reload")) tabList.add("reload");
                    if (player.hasPermission("volleyball.list")) tabList.add("list");
                    if (player.hasPermission("volleyball.create")) tabList.add("create");
                    if (player.hasPermission("volleyball.info")) tabList.add("info");
                    if (player.hasPermission("volleyball.remove")) tabList.add("remove");
                    if (player.hasPermission("volleyball.animations")) tabList.add("animations");
                    if (player.hasPermission("volleyball.bounds")) tabList.add("bounds");
                    if (player.hasPermission("volleyball.editable")) tabList.add("editable");
                    if (player.hasPermission("volleyball.enabled")) tabList.add("enabled");
                    if (player.hasPermission("volleyball.hints")) tabList.add("hints");
                    if (player.hasPermission("volleyball.hitradius")) tabList.add("hitradius");
                    if (player.hasPermission("volleyball.name")) tabList.add("name");
                    if (player.hasPermission("volleyball.net")) tabList.add("net");
                    if (player.hasPermission("volleyball.particles")) tabList.add("particles");
                    if (player.hasPermission("volleyball.restrictions")) tabList.add("restrictions");
                    if (player.hasPermission("volleyball.scoring")) tabList.add("scoring");
                    if (player.hasPermission("volleyball.sounds")) tabList.add("sounds");
                    if (player.hasPermission("volleyball.speed")) tabList.add("speed");
                    if (player.hasPermission("volleyball.teams")) tabList.add("teams");
                    if (player.hasPermission("volleyball.texture")) tabList.add("texture");

                } else if (args[0].startsWith("a") && player.hasPermission("volleyball.animations")) {

                    tabList.add("animations");

                } else if (args[0].startsWith("b") && player.hasPermission("volleyball.bounds")) {

                    tabList.add("bounds");

                } else if (args[0].startsWith("c") && player.hasPermission("volleyball.create")) {

                    tabList.add("create");

                } else if (args[0].startsWith("e") && player.hasPermission("volleyball.enabled")) {

                    if (player.hasPermission("volleyball.editable")) tabList.add("editable");
                    if (player.hasPermission("volleyball.enabled")) tabList.add("enabled");

                    if (args[0].startsWith("ed") && player.hasPermission("volleyball.editable"))
                        tabList.remove("enabled");
                    else if (args[0].startsWith("en") && player.hasPermission("volleyball.enabled"))
                        tabList.remove("editable");

                } else if (args[0].startsWith("h")) {

                    if (player.hasPermission("volleyball.help")) tabList.add("help");
                    if (player.hasPermission("volleyball.hints")) tabList.add("hints");
                    if (player.hasPermission("volleyball.hitradius")) tabList.add("hitradius");

                    if (args[0].startsWith("he") && player.hasPermission("volleyball.help")) {

                        tabList.remove("hints");
                        tabList.remove("hitradius");

                    } else if (args[0].startsWith("hi") &&
                            (player.hasPermission("volleyball.hints") ||
                                    player.hasPermission("volleyball.hitradius"))) {

                        tabList.remove("help");

                        if (args[0].startsWith("hin") && player.hasPermission("volleyball.hints")) {
                            tabList.remove("hitradius");
                        } else if (args[0].startsWith("hit") && player.hasPermission("volleyball.hitradius")) {
                            tabList.remove("hints");
                        }

                    }

                } else if (args[0].startsWith("i") && player.hasPermission("volleyball.info")) {

                    tabList.add("info");

                } else if (args[0].startsWith("l") && player.hasPermission("volleyball.list")) {

                    tabList.add("list");

                } else if (args[0].startsWith("n")) {

                    if (player.hasPermission("volleyball.name")) tabList.add("name");
                    if (player.hasPermission("volleyball.net")) tabList.add("net");

                    if (args[0].startsWith("na") && player.hasPermission("volleyball.name"))
                        tabList.remove("net");
                    else if (args[0].startsWith("ne") && player.hasPermission("volleyball.net"))
                        tabList.remove("name");

                } else if (args[0].startsWith("p") && player.hasPermission("volleyball.particles")) {

                    tabList.add("particles");

                } else if (args[0].startsWith("r") && player.hasPermission("volleyball.restrictions")) {

                    if (player.hasPermission("volleyball.remove")) tabList.add("remove");
                    if (player.hasPermission("volleyball.reload")) tabList.add("reload");
                    if (player.hasPermission("volleyball.restrictions")) tabList.add("restrictions");

                    if (args[0].startsWith("rem") && player.hasPermission("volleyball.remove")) {
                        tabList.remove("restrictions");
                        tabList.remove("reload");
                    } else if (args[0].startsWith("rel") && player.hasPermission("volleyball.reload")) {
                        tabList.remove("remove");
                        tabList.remove("restrictions");
                    } else if (args[0].startsWith("res") && player.hasPermission("volleyball.restrictions")) {
                        tabList.remove("remove");
                        tabList.remove("reload");
                    }

                } else if (args[0].startsWith("s")) {

                    if (player.hasPermission("volleyball.scoring")) tabList.add("scoring");
                    if (player.hasPermission("volleyball.sounds")) tabList.add("sounds");
                    if (player.hasPermission("volleyball.speed")) tabList.add("speed");

                    if (args[0].startsWith("sc") && player.hasPermission("volleyball.scoring")) {
                        tabList.remove("speed");
                        tabList.remove("sounds");
                    } else if (args[0].startsWith("so") && player.hasPermission("volleyball.sounds")) {
                        tabList.remove("speed");
                        tabList.remove("scoring");
                    } else if (args[0].startsWith("sp") && player.hasPermission("volleyball.speed")) {
                        tabList.remove("sounds");
                        tabList.remove("scoring");
                    }

                } else if (args[0].startsWith("t")) {

                    if (player.hasPermission("volleyball.teams")) tabList.add("teams");
                    if (player.hasPermission("volleyball.texture")) tabList.add("texture");

                    if (args[0].startsWith("tea") && player.hasPermission("volleyball.teams"))
                        tabList.remove("texture");
                    else if (args[0].startsWith("tex") && player.hasPermission("volleyball.texture"))
                        tabList.remove("teams");

                }

            } else if (args.length == 2) {

                if ((args[0].equalsIgnoreCase("info") && player.hasPermission("volleyball.info")) ||
                        (args[0].equalsIgnoreCase("remove") && player.hasPermission("volleyball.remove")) ||
                        (args[0].equalsIgnoreCase("animations") && player.hasPermission("volleyball.animations")) ||
                        (args[0].equalsIgnoreCase("bounds") && player.hasPermission("volleyball.bounds")) ||
                        (args[0].equalsIgnoreCase("editable") && player.hasPermission("volleyball.editable")) ||
                        (args[0].equalsIgnoreCase("enabled") && player.hasPermission("volleyball.enabled")) ||
                        (args[0].equalsIgnoreCase("hints") && player.hasPermission("volleyball.hints")) ||
                        (args[0].equalsIgnoreCase("hitradius") && player.hasPermission("volleyball.hitradius")) ||
                        (args[0].equalsIgnoreCase("name") && player.hasPermission("volleyball.name")) ||
                        (args[0].equalsIgnoreCase("net") && player.hasPermission("volleyball.net")) ||
                        (args[0].equalsIgnoreCase("particles") && player.hasPermission("volleyball.particles")) ||
                        (args[0].equalsIgnoreCase("restrictions") && player.hasPermission("volleyball.restrictions")) ||
                        (args[0].equalsIgnoreCase("scoring") && player.hasPermission("volleyball.scoring")) ||
                        (args[0].equalsIgnoreCase("sounds") && player.hasPermission("volleyball.sounds")) ||
                        (args[0].equalsIgnoreCase("speed") && player.hasPermission("volleyball.speed")) ||
                        (args[0].equalsIgnoreCase("teams") && player.hasPermission("volleyball.teams")) ||
                        (args[0].equalsIgnoreCase("texture") && player.hasPermission("volleyball.texture"))) {

                    for (String court : courts) {
                        if (court.startsWith(args[1])) {
                            tabList.add(court);
                        }
                    }
                }

            } else if (args.length == 3) {

                if ((args[0].equalsIgnoreCase("animations") && player.hasPermission("volleyball.animations")) ||
                        (args[0].equalsIgnoreCase("editable") && player.hasPermission("volleyball.editable")) ||
                        (args[0].equalsIgnoreCase("enabled") && player.hasPermission("volleyball.enabled")) ||
                        (args[0].equalsIgnoreCase("hints") && player.hasPermission("volleyball.hints")) ||
                        (args[0].equalsIgnoreCase("particles") && player.hasPermission("volleyball.particles")) ||
                        (args[0].equalsIgnoreCase("restrictions") && player.hasPermission("volleyball.restrictions")) ||
                        (args[0].equalsIgnoreCase("scoring") && player.hasPermission("volleyball.scoring")) ||
                        (args[0].equalsIgnoreCase("sounds") && player.hasPermission("volleyball.sounds")) ||
                        (args[0].equalsIgnoreCase("teams") && player.hasPermission("volleyball.teams"))) {

                    if (args[2].equalsIgnoreCase("")) {

                        tabList.add("false");
                        tabList.add("true");

                    } else if (args[2].startsWith("f")) {

                        tabList.add("false");

                    } else if (args[2].startsWith("t")) {

                        tabList.add("true");

                    }

                }

                if ((args[0].equalsIgnoreCase("bounds") && player.hasPermission("volleyball.bounds")) ||
                        (args[0].equalsIgnoreCase("net") && player.hasPermission("volleyball.net"))) {

                    if (args[2].equalsIgnoreCase("")) {
                        tabList.add("pos1");
                        tabList.add("pos2");
                        tabList.add("set");
                    } else if (args[2].startsWith("p")) {
                        tabList.add("pos1");
                        tabList.add("pos2");
                    } else if (args[2].startsWith("s")) {
                        tabList.add("set");
                    }

                }

            }

            return tabList;

        }

        return null;

    }

}
