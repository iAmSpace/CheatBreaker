package com.thevoxelbox.voxelmap.util;

import com.thevoxelbox.voxelmap.interfaces.IObservableChunkChangeNotifier;
import net.minecraft.world.chunk.Chunk;

import java.util.Observable;

public class ObservableChunkChangeNotifier
		extends Observable
		implements IObservableChunkChangeNotifier {
	public void chunkChanged(Chunk chunk) {
		setChanged();
		notifyObservers(chunk);
	}
}
