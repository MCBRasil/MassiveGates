package com.massivecraft.massivegates.cmd;

import com.massivecraft.massivegates.Gate;
import com.massivecraft.massivegates.GateCommand;
import com.massivecraft.massivegates.GateColl;
import com.massivecraft.massivegates.Permission;
import com.massivecraft.mcore5.cmd.req.ReqHasPerm;
import com.massivecraft.mcore5.cmd.req.ReqIsPlayer;

public class CmdGateNew extends GateCommand
{
	public CmdGateNew()
	{
		this.addAliases("new", "create");
		this.addOptionalArg("name", "*none*");
		this.setErrorOnToManyArgs(false);
		this.addRequirements(ReqIsPlayer.getInstance());
		this.addRequirements(new ReqHasPerm(Permission.NEW.node));
	}

	@Override
	public void perform()
	{
		Gate gate = GateColl.i.create();
		String name = this.argConcatFrom(0);
		gate.setName(name);
		gme.setSelectedGate(gate);
		this.msg("<i>Created and selected the new gate: "+gate.getIdNameStringLong());
	}
	
}