package com.Octavian0.Lottery;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;


/**
 * Po8collect for Bukkit
 *
 * @author Kwinno
 */
public class Lottery extends JavaPlugin {
	
	private LotteryWorker worker;

    public void onEnable() {
    	PluginDescriptionFile pdfFile = this.getDescription();
    	// create plugin directory if it doesn't exist
    	File dir = getDataFolder();
		if (!dir.exists()) {
			dir.mkdir();
			if (!dir.exists()) {
				System.out.println("Cannot create plugin directory for " + pdfFile.getName() + "!");
				return;
			}
		}
    	worker = new LotteryWorker(getServer(), getDataFolder());
        System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
    }
    
    public void onDisable() {
    	PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " stopping...");
    }
   
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
  
    	String cmd = command.getName();
    	
    	if (cmd.equalsIgnoreCase("po8collect")) {
    		worker.telehelp(sender);
    		return true;
    	}
		if(args.length == 1 && args[0].equalsIgnoreCase("help")) {
			worker.telehelp(sender);
				return true;
			}
    	if (cmd.equalsIgnoreCase("telelist")) {
    		if(sender.hasPermission("po8collect.list") || sender.isOp()){
    		worker.teleList(sender);
    		return true;
    		}
    		else
    		{
    			sender.sendMessage(ChatColor.RED + "You don't have permission for this command!");
    			return false;
    		}
    		
    	}
    	if (cmd.equalsIgnoreCase("teleto")) {
    		if(sender.hasPermission("po8collect.teleport") || sender.isOp()){
    		if(args.length == 0){
    		worker.teleTo(sender,sender.getName().toString());
    		return true;}
    		else
    		worker.teleTo(sender,args[0]);
    		return true;
    		}
    		else
    		{
    			sender.sendMessage(ChatColor.RED + "You don't have permission for this command!");
    			return false;
    		}
    		
    	}
    	if (args.length > 2)
    		return false;
    	
    	if(args.length == 0){
    		worker.telehelp(sender);
    	}
    		
    	if (cmd.equalsIgnoreCase("teleadd") && args.length > 0){
    		if(sender.hasPermission("po8collect.add") || sender.isOp()){
    		if(args.length == 2 && args[0].equalsIgnoreCase("-o")){
      		worker.teleAdd(sender, args[1]);
      		return true;
    		}
    		else
    		{
    			if(args.length == 1 && worker.teleExists(args[0]) == false){
    	      		worker.teleAdd(sender, args[0]);
    	      		return true;
    	    		}
    	    		else
    	    		{
    	    			sender.sendMessage(ChatColor.RED + "This teleports exists. Use \"-o\" to override");
    	    			return false;
    	    		}
    		}
    		}
       		else
        		{
        			sender.sendMessage(ChatColor.RED + "You don't have permission for this command!");
        			return false;
        		}
    	}

    	if (cmd.equalsIgnoreCase("teleremove") && args.length > 0) {
    		if(sender.hasPermission("po8collect.remove") || sender.isOp()){
    			if(worker.teleExists(args[0]) == true){
    				worker.teleRemove(sender, args[0]);
    				return true;
    			}
    			else
    			{
    				sender.sendMessage(ChatColor.RED + "This playerchest does not exist");
    				return false;
    			}
    		}
       		else
    		{
    			sender.sendMessage(ChatColor.RED + "You don't have permission for this command!");
    			return false;
    		}
    	}
    	return false; // should not ever get here
    }
}
