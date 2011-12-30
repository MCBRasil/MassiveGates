package com.massivecraft.massivegates.flood;

import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import com.massivecraft.massivegates.Conf;

public class FloodUtil
{
	//----------------------------------------------//
	// THE BASE FLOOD ALGORITHM
	//----------------------------------------------//
	
	public static EnumSet<Material> airSet = EnumSet.of(Material.AIR);
	public static Set<Block> getFloodBlocks(Block startBlock, Set<BlockFace> expandFaces, Set<Material> allowedMaterials, int limit, Set<Block> foundBlocks)
	{
		if (foundBlocks == null) return null;
		if (foundBlocks.size() > limit) return null;
		if (foundBlocks.contains(startBlock)) return foundBlocks;
		
		if (allowedMaterials.contains(startBlock.getType()))
		{
			// ... We found a block :D ...
			foundBlocks.add(startBlock);
			
			// ... And flood away !
			for (BlockFace face : expandFaces)
			{
				Block potentialBlock = startBlock.getRelative(face);
				foundBlocks = getFloodBlocks(potentialBlock, expandFaces, allowedMaterials, limit, foundBlocks);
			}
		}
		
		return foundBlocks;
	}
	public static Set<Block> getFloodBlocks(Block startBlock, Set<BlockFace> expandFaces, Set<Material> allowedMaterials, int limit)
	{
		return getFloodBlocks(startBlock, expandFaces, allowedMaterials, limit, new HashSet<Block>());
	}
	public static Set<Block> getFloodBlocks(Block startBlock, Set<BlockFace> expandFaces, Set<Material> allowedMaterials)
	{
		return getFloodBlocks(startBlock, expandFaces, allowedMaterials, Conf.maxarea);
	}
	public static Set<Block> getFloodBlocks(Block startBlock, Set<BlockFace> expandFaces)
	{
		return getFloodBlocks(startBlock, expandFaces, airSet);
	}
	
	//----------------------------------------------//
	// MULTI-FLOOD
	//----------------------------------------------//
	
	public static Map<FloodOrientation, Set<Block>> getFloods(Block startBlock, Collection<FloodOrientation> orientations, Set<Material> allowedMaterials, int limit)
	{
		Map<FloodOrientation, Set<Block>> ret = new HashMap<FloodOrientation, Set<Block>>();
		for (FloodOrientation orientation : orientations)
		{
			ret.put(orientation, getFloodBlocks(startBlock, orientation.getDirections(), allowedMaterials, limit));
		}
		return ret;
	}
	public static Map<FloodOrientation, Set<Block>> getFloods(Block startBlock, Collection<FloodOrientation> orientations, Set<Material> allowedMaterials)
	{
		return getFloods(startBlock, orientations, allowedMaterials, Conf.maxarea);
	}
	public static Map<FloodOrientation, Set<Block>> getFloods(Block startBlock, Collection<FloodOrientation> orientations)
	{
		return getFloods(startBlock, orientations, airSet);
	}
	public static Map<FloodOrientation, Set<Block>> getFloods(Block startBlock)
	{
		return getFloods(startBlock, EnumSet.allOf(FloodOrientation.class));
	}
	
	//----------------------------------------------//
	// GETTING A FRAME
	//----------------------------------------------//
	
	// TODO: There is for sure room for more advanced frame finding algorithms...
	// Detect thicker frames using some sort of additional flood
	
	public static Set<Block> getFrameFor(Set<Block> content, Set<BlockFace> expandFaces)
	{
		Set<Block> frame = new HashSet<Block>();
		for (Block currentBlock : content)
		{
			for (BlockFace face : expandFaces)
			{
				Block potentialBlock = currentBlock.getRelative(face);
				if ( ! content.contains(potentialBlock))
				{
					frame.add(potentialBlock);
				}
			}
		}
		return frame;
	}
}