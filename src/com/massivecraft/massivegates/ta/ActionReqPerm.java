package com.massivecraft.massivegates.ta;

import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

import com.massivecraft.massivegates.Gate;
import com.massivecraft.mcore5.util.Perm;

public class ActionReqPerm extends BaseAction
{
	protected static ActionReqPerm instance = new ActionReqPerm();
	public static ActionReqPerm getInstance() { return instance; }
	
	protected ActionReqPerm()
	{
		super("mgcore_reqperm", "reqperm", "Require that entity has permission node");
	}
	
	@Override
	public void perform(String arg, Gate gate, Entity entity, Cancellable cancellable)
	{
		if ( ! hasPerm(arg, entity))
		{
			cancellable.setCancelled(true);
		}
	}
	
	public final static List<String> errorsRequired = Arrays.asList("<b>Please provide the permission node");
	@Override
	public List<String> checkArg(String arg)
	{
		if (arg == null || arg.trim().length() == 0)
		{
			return errorsRequired;
		}
				
		return null;
	}
	
	public static boolean hasPerm(String perm, Entity entity)
	{
		if (entity == null) return false;
		if ( ! (entity instanceof Player)) return false;
		Player player = (Player)entity;
		
		return Perm.has(player, perm, true);
	}
}