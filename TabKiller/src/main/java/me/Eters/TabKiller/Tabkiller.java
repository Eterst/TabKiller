package me.Eters.TabKiller;

import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Text.Builder;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
// Imports for logger
import com.google.inject.Inject;

import org.slf4j.Logger;

@Plugin(id = "tabkiller", name = "TabKiller", version = "1.0", description = "A tab modifier plugin")
public class Tabkiller {
	
	@Inject
    private Logger logger;

	public TextColor aplyColor(char color) {
		switch(color)
		{
			case '0':
				return TextColors.BLACK;
			case '1':
				return TextColors.DARK_BLUE;
			case '2':
				return TextColors.DARK_GREEN;
			case '3':
				return TextColors.DARK_AQUA;
			case '4':
				return TextColors.DARK_RED;
			case '5':
				return TextColors.DARK_PURPLE;
			case '6':
				return TextColors.GOLD;
			case '7':
				return TextColors.GRAY;
			case '8':
				return TextColors.DARK_GRAY;
			case '9':
				return TextColors.BLUE;
			case 'a':
				return TextColors.GREEN;
			case 'b':
				return TextColors.AQUA;
			case 'c':
				return TextColors.RED;
			case 'd':
				return TextColors.LIGHT_PURPLE;
			case 'e':
				return TextColors.YELLOW;
			case 'f':
				return TextColors.WHITE;
				/*
			case 'l':
				text.toBuilder().append(Text.builder().style(TextStyles.BOLD).build()).build();;
				break;
			case 'n':
				text.toBuilder().append(Text.builder().style(TextStyles.UNDERLINE).build()).build();;
				break;
			case 'o':
				text.toBuilder().append(Text.builder().style(TextStyles.ITALIC).build()).build();;
				break;
			case 'm':
				text.toBuilder().append(Text.builder().style(TextStyles.STRIKETHROUGH).build()).build();;
				break;
			case 'r':
				text.toBuilder().append(Text.builder().style(TextStyles.RESET).append(Text.builder().color(TextColors.RESET).build()).build()).build();
				break;
				*/
		}
		return null;
	}
	
	public void tabPrefix(Player p, String newPrefix) {
		if(newPrefix.contains("&")) {
			String[] prefix = newPrefix.split("&(?=[\\dA-Fa-f])");
			Builder textB = Text.builder();
			for(int i = 1;i<prefix.length;i++) {
				char hexa = prefix[i].charAt(0);
				Text newText = Text.builder(prefix[i].substring(1)).color(aplyColor(hexa)).build();
				textB.append(newText);
			}
			Text prefixx = textB.append(Text.of(p.getName())).build();
			p.sendMessage(prefixx);
			p.getTabList().getEntry(p.getUniqueId()).ifPresent(s -> s.setDisplayName(prefixx));
		}
	}
	
    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        logger.info("Successfully running TabKiller!!!");
        /*
        Player p = Sponge.getServer().getPlayer("Eters").get();
        TabList tablist = p.getTabList();
       
        tablist.getEntry(p.getUniqueId()).get().setDisplayName(Text.builder("Admin").color(TextColors.DARK_RED).style(TextStyles.ITALIC).build());
        TabListEntry entry = TabListEntry.builder()
        	    .list(tablist)
        	    .gameMode(GameModes.SURVIVAL)
        	    .profile(p.getProfile())
        	    .build();
        	tablist.addEntry(entry);
        */
        	
        	
    	PluginContainer plugin = Sponge.getPluginManager().getPlugin("tabkiller").get();

    	CommandSpec myCommandSpec = CommandSpec.builder()
    	    .description(Text.of("Tab Prefix Command"))//.permission("myplugin.command.helloworld")
    	    .arguments(
                    GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
                    GenericArguments.remainingJoinedStrings(Text.of("prefix")))
    	    
    	    .executor((CommandSource src, CommandContext args) -> {

                Player player = args.<Player>getOne("player").get();
                String prefix = args.<String>getOne("prefix").get();

                tabPrefix(player,prefix);
                player.sendMessage(Text.of(prefix));

                return CommandResult.success();
            })
    	    .build();

    	Sponge.getCommandManager().register(plugin, myCommandSpec, "tkprefix", "tabkillerprefix","tkp");
        /* Ejemplo de Programar un task con 20 ticks
        Sponge.getScheduler().createTaskBuilder().execute(() -> {
            //Aqui va lo que se programa
        }).delayTicks(20).async();
        */
    }
}
