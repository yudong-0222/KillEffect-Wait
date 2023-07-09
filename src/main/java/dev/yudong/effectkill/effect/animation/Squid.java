package dev.yudong.effectkill.effect.animation;

import java.util.ArrayList;
import java.util.Arrays;

import dev.yudong.effectkill.utils.ParticleEffect;
import net.minecraft.server.v1_8_R3.*;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

import dev.yudong.effectkill.Main;
import dev.yudong.effectkill.effect.MainEffectKill;
import dev.yudong.effectkill.utils.User;
import dev.yudong.effectkill.utils.Utils;
import dev.yudong.effectkill.utils.config.YAMLUtils;
import dev.yudong.effectkill.utils.inventory.Heads;

public class Squid extends MainEffectKill{

	public Squid() {
		super("squid",YAMLUtils.get("messages").getFile().exists()?((String) Utils.gfc("messages", "effectKill.squid.name")):("§3魷魚火箭"), new ArrayList<>(Arrays.asList("&f在原地生成&2魷魚火箭 &e由於魷魚 起飛!",  "&8左鍵點擊來套用特效")), Heads.SQUID.getTexture());
	}
	@Override
	public void update(User user) {
		Location loc = user.getPlayer().getLocation().add(0,-0.3,0);
		World world = ((CraftWorld) loc.getWorld()).getHandle();

		new BukkitRunnable() {
			EntitySquid squid;
			int i = 0;
			Location lastPos = loc.clone();
			double verticalSpeed = 0.2; // 上升的垂直速度

			@Override
			public void run() {
				loc.getWorld().playSound(loc,Sound.CHICKEN_EGG_POP,1f,1f);
				if (i == 0) {
					squid = new EntitySquid(world);
					double initialHeight = loc.getY() + 1.5; // squid出生位置調高
					squid.setLocation(loc.getX(), initialHeight, loc.getZ(), loc.getYaw() + 90, loc.getPitch());
					world.addEntity(squid, CreatureSpawnEvent.SpawnReason.CUSTOM);
				}

				Location pos = new Location(loc.getWorld(), squid.locX, squid.locY, squid.locZ);
				if (pos.getBlock().getType() == Material.AIR) {
					pos.subtract(0, 0.2, 0); // 起飛? xD
					ParticleEffect.CLOUD.display(0, 0, 0, 0, 1, pos, 256f); // 显示粒子效果
					lastPos = pos;
					squid.motY = verticalSpeed; // 設定往上飛的速度
					i++;
					if (i >= 25) { // 控制上升的时间
						squid.dead = true;
						PacketPlayOutEntityDestroy packetPlayOutEntityDestroy = new PacketPlayOutEntityDestroy(squid.getId()); // 销毁魷魚的包
						for (Player all : Bukkit.getOnlinePlayers()) {
							((CraftPlayer) all).getHandle().playerConnection.sendPacket(packetPlayOutEntityDestroy);
							all.playEffect(lastPos, Effect.EXPLOSION_LARGE, 15);
						}
						cancel();
					}
				} else {
					squid.dead = true;
					cancel();
				}
			}

		}.runTaskTimer(Main.getInstance(), 1, 1);
	}

}
