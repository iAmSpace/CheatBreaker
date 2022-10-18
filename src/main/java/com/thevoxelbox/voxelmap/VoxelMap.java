package com.thevoxelbox.voxelmap;

import com.thevoxelbox.voxelmap.interfaces.*;
import com.thevoxelbox.voxelmap.util.AddonResourcePack;
import com.thevoxelbox.voxelmap.util.DimensionManager;
import com.thevoxelbox.voxelmap.util.ObservableChunkChangeNotifier;
import com.thevoxelbox.voxelmap.util.ReflectionUtils;
import net.minecraft.client.Minecraft;

import java.util.List;

public class VoxelMap
		extends AbstractVoxelMap {
	private MapSettingsManager mapOptions = null;
	private RadarSettingsManager radarOptions = null;
	private IMap map = null;
	private IRadar radar = null;
	private IColorManager colorManager = null;
	private IWaypointManager waypointManager = null;
	private IDimensionManager dimensionManager = null;
	private ObservableChunkChangeNotifier chunkChangeNotifier = new ObservableChunkChangeNotifier();

	public VoxelMap(boolean showUnderMenus, boolean isFair) {
		AbstractVoxelMap.instance = this;
		if (!Minecraft.getMinecraft().getResourceManager().getResourceDomains().contains("voxelmap")) {
			AddonResourcePack voxelMapResourcePack = new AddonResourcePack("voxelmap");
			List defaultResourcePacks = (List) ReflectionUtils
					.getPrivateFieldValueByType(Minecraft.getMinecraft(), Minecraft.class, List.class, 1);
			defaultResourcePacks.add(voxelMapResourcePack);
			Minecraft.getMinecraft().refreshResources();
		}
		this.mapOptions = new MapSettingsManager();
		this.mapOptions.showUnderMenus = showUnderMenus;
		this.radarOptions = new RadarSettingsManager();
		this.mapOptions.setRadarSettings(this.radarOptions);

		this.colorManager = new ColorManager(this);
		this.waypointManager = new WaypointManager(this);
		this.dimensionManager = new DimensionManager(this);
		this.map = new Map(this);
		this.mapOptions.loadAll();
	}

	public void onTickInGame(Minecraft mc) {
		this.map.onTickInGame(mc);
	}

	public IObservableChunkChangeNotifier getNotifier() {
		return this.chunkChangeNotifier;
	}

	public MapSettingsManager getMapOptions() {
		return this.mapOptions;
	}

	public RadarSettingsManager getRadarOptions() {
		return this.radarOptions;
	}

	public IMap getMap() {
		return this.map;
	}

	public IRadar getRadar() {
		return this.radar;
	}

	public IColorManager getColorManager() {
		return this.colorManager;
	}

	public IWaypointManager getWaypointManager() {
		return this.waypointManager;
	}

	public IDimensionManager getDimensionManager() {
		return this.dimensionManager;
	}

	public void setPermissions(boolean hasRadarPermission, boolean hasCavemodePermission) {
		this.map.setPermissions(hasRadarPermission, hasCavemodePermission);
	}

	public void newSubWorldName(String name) {
		this.waypointManager.newSubWorldName(name);
	}

	public void newSubWorldHash(String hash) {
		this.waypointManager.newSubWorldHash(hash);
	}
}
