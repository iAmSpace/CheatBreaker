package com.thevoxelbox.voxelmap.interfaces;

import net.minecraft.world.chunk.Chunk;

import java.util.Observer;

public abstract interface IObservableChunkChangeNotifier {
	public abstract void chunkChanged(Chunk paramChunk);

	public abstract void addObserver(Observer paramObserver);
}
