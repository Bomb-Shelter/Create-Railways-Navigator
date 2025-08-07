package de.mrjulsen.crn.fabric;

import fuzs.forgeconfigapiport.fabric.impl.core.ForgeConfigRegistryImpl;
import fuzs.forgeconfigapiport.fabric.impl.core.NeoForgeConfigRegistryImpl;
import io.github.fabricators_of_create.porting_lib.core.util.ServerLifecycleHooks;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.fml.config.ModConfig;

import java.nio.file.Path;

import com.simibubi.create.content.trains.station.GlobalStation;
import com.simibubi.create.content.trains.station.StationBlockEntity;

import de.mrjulsen.crn.CRNPlatformSpecific;
import de.mrjulsen.crn.CreateRailwaysNavigator;
import de.mrjulsen.crn.config.ModClientConfig;
import de.mrjulsen.crn.config.ModCommonConfig;
import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;

public class CRNPlatformSpecificImpl {
    /**
     * This is our actual method to {@link CRNPlatformSpecific#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }

    public static MinecraftServer getServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }

    public static void registerConfig() {        
        if (Platform.getEnvironment() == Env.CLIENT) {
            NeoForgeConfigRegistryImpl.INSTANCE.register(CreateRailwaysNavigator.MOD_ID, ModConfig.Type.CLIENT, ModClientConfig.SPEC, CreateRailwaysNavigator.MOD_ID + "-client.toml");
        }
        NeoForgeConfigRegistryImpl.INSTANCE.register(CreateRailwaysNavigator.MOD_ID, ModConfig.Type.COMMON, ModCommonConfig.SPEC, CreateRailwaysNavigator.MOD_ID + "-common.toml");
    }
    
    public static GlobalStation getStationFromBlockEntity(BlockEntity be) {
        if (!(be instanceof StationBlockEntity stationBe))
			return null;
		
        return stationBe.getStation();
    }
}
