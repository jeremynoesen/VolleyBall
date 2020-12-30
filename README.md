<img src="https://raw.githubusercontent.com/jeremynoesen/VolleyBall/main/vblogo.png" align="right"/>

# VolleyBall

## About
VolleyBall is a minigame that emulates volleyball for a Spigot Minecraft server. Players can serve a ball on a volleyball court and punch it to volley it. Volleys are counted when the ball goes over the net. Multiple courts can be created and modified with this plugin.

## Purpose
VolleyBall was originally written for a tropical island themed event on a Minecraft server. This was to add more player interactive content to the build to make it feel more lively. From there, this plugin has grown more complex to allow other servers to enjoy the features of VolleyBall for themselves.

## Usage

### Commands
- `/volleyball help` and `/volleyball court help` - Show the help messages
- `/volleyball reload` - Reload all configs and delete volleyball entities
- `/volleyball court create <name>` - Create a new court using a world edit selection
- `/volleyball court remove <name>` - Delete a court
- `/volleyball court select <name>` - Select a court with WorldEdit
- `/volleyball court info <name>` - Get info about the court
- `/volleyball court <name> set net` - Set the net bounds with WorldEdit
- `/volleyball court <name> set bounds` - Set the court bounds with WorldEdit
- `/volleyball court <name> set restrictions <true/false>` - Enable or disable ball court restrictions
- `/volleyball court <name> set animations <true/false>` - Enable or disable ball animations
- `/volleyball court <name> set enabled <true/false>` - Enable or disable a court
- `/volleyball court <name> set texture <url>` - Set the ball texture based on a Minecraft skin texture url
- `/volleyball court <name> set speed <number>` - Set the ball speed, default is 1.0
- `/volleyball court <name> set name <newname>` - Change a court's name

### Permissions
- `volleyball.help` - Allow using the main help command
- `volleyball.reload` - Allow reloading the plugin
- `volleyball.court.create` - Allow creating a court
- `volleyball.court.remove` - Allow removing a court
- `volleyball.court.info` - Allow seeing info about courts
- `volleyball.court.list` - Allow seeing the list of courts
- `volleyball.court.select` - Allow selecting a court
- `volleyball.court.help` - Allow seeing court help
- `volleyball.court.set.animations` - Allow setting ball animations
- `volleyball.court.set.speed` - Allow setting ball speed
- `volleyball.court.set.texture` - Allow setting ball texture
- `volleyball.court.set.bounds` - Allow setting court bounds
- `volleyball.court.set.enabled` - Allow setting court enabled
- `volleyball.court.set.net` - Allow setting net
- `volleyball.court.set.name` - Allow setting court name
- `volleyball.court.set.restrictions` - Allow ball setting restrictions

### Playing
- Press and release your sneak key to serve the ball when on a court
- Punch when the ball is close to you
- Try to keep the ball from hitting the ground and get it over the net

## Requirements
- Spigot 1.13 - 1.16
- Java 8
- WorldEdit

## Installation
To install the plugin, download the latest release, put it in your server plugins folder, and start or restart your server. This will generate the necessary files for configuration of the plugin, located in `plugins/VolleyBall`.

## Configuration
There is no main configuration, all configuring is done in-game.

- Use the `court create` command to create a court. The court will be created, but not enabled.
- Use WorldEdit to select a cuboid region to set as the bounds. Include the floor and the air above it, not just the floor! run the `court set bounds` command with this selected.
- Select the net with WorldEdit. Do not include the air under the net. Run the ` court set net` command to set this as the net.
- Use the other court commands to customize your court to your liking.
- Once you are finished, run the `court set enabled` command with `true` to enable your court. This court can not have its blocks modified while it is enabled, so make sure you make build edits before enabling!

Do not modify the courts config, as you can possibly break your saved courts!

For the message configuration, you can use color codes. You can also use the placeholders used per message, as shown in the default configuration. The messages and their names should explain what they are used for.

## Demonstration
![Demonstration](demo.gif)

## Troubleshooting
Ball not spawning?
- If you are using world guard, enable armor stand spawning in the region the court is in. 
- Also make sure block-plugin-spawning is set to false in the world guard config. 
- If you are using EssentialsProtect, make sure to not block armor stand spawning.

## Notice
This project is no longer in development.


