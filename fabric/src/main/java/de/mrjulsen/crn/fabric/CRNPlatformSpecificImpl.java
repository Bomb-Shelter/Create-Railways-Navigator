package de.mrjulsen.crn.fabric;

import fuzs.forgeconfigapiport.fabric.impl.core.NeoForgeConfigRegistryImpl;
import io.github.fabricators_of_create.porting_lib.core.util.ServerLifecycleHooks;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.players.GameProfileCache;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.fml.config.ModConfig;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.simibubi.create.content.trains.station.GlobalStation;
import com.simibubi.create.content.trains.station.StationBlockEntity;

import de.mrjulsen.crn.CRNPlatformSpecific;
import de.mrjulsen.crn.CreateRailwaysNavigator;
import de.mrjulsen.crn.config.ModClientConfig;
import de.mrjulsen.crn.config.ModCommonConfig;
import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;

public class CRNPlatformSpecificImpl {
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

    public static Optional<String> getLastKnownPlayerName(UUID uuid) {
        MinecraftServer server = getServer();
        if (server != null) {
            GameProfileCache profileCache = server.getProfileCache();
            if (profileCache != null) {
                return profileCache.get(uuid).map(profile -> profile.getName());
            }
        }
        return Optional.empty();
    }

    public static Map<UUID, String> getAllKnownPlayers() {
        Map<UUID, String> result = new HashMap<>();
        MinecraftServer server = getServer();
        if (server != null) {
            GameProfileCache profileCache = server.getProfileCache();
            if (profileCache != null) {
                server.getPlayerList().getPlayers().forEach(player -> {
                    result.put(player.getUUID(), player.getGameProfile().getName());
                });
            }
        }
        return result;
    }
}
