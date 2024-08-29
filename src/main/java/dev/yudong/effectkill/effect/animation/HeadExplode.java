package dev.yudong.effectkill.effect.animation;

import java.util.ArrayList;
import java.util.Arrays;

import dev.yudong.effectkill.Main;
import dev.yudong.effectkill.effect.MainEffectKill;
import dev.yudong.effectkill.utils.ParticleEffect;
import dev.yudong.effectkill.utils.User;
import dev.yudong.effectkill.utils.Utils;
import dev.yudong.effectkill.utils.config.YAMLUtils;
import dev.yudong.effectkill.utils.inventory.Heads;
import net.minecraft.server.v1_8_R3.*;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;


public class HeadExplode extends MainEffectKill {

    public HeadExplode() {
        super(
                "head",
                YAMLUtils.get("messages").getFile().exists()
                        ? (String) Utils.gfc("messages", "effectKill.head.name")
                        : "§e火箭頭顱",
                new ArrayList<>(Arrays.asList(
                        "&7頭顱將化身火箭，突破天際！",
                        "",
                        "&7稀有度 » &6傳奇"
                )),
                Heads.FIREWORK.getTexture()
        );
    }

    @Override
    public void update(User user) {
        Location loc = user.getPlayer().getLocation();
        World world = ((CraftWorld) loc.getWorld()).getHandle();
        EntityArmorStand entityArmorStand = new EntityArmorStand(world);
        entityArmorStand.setLocation(loc.getX(), loc.getY(), loc.getZ(),
                MathHelper.d((entityArmorStand.pitch * 256F) / 360F),
                MathHelper.d((entityArmorStand.yaw * 256F) / 360F));
        entityArmorStand.setInvisible(true);
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setOwner(user.getPlayer().getName());
        skull.setItemMeta(skullMeta);
        net.minecraft.server.v1_8_R3.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(skull);
        PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(entityArmorStand);
        PacketPlayOutEntityEquipment packetPlayOutEntityEquipment = new PacketPlayOutEntityEquipment(entityArmorStand.getId(), 4,
                nmsItemStack);
        for (Player all : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) all).getHandle().playerConnection.sendPacket(packet);
            ((CraftPlayer) all).getHandle().playerConnection.sendPacket(packetPlayOutEntityEquipment);
        }
        loc.getWorld().playSound(loc, Sound.FIREWORK_LAUNCH, 1f, 1f);

        BukkitRunnable runnable = new BukkitRunnable() {
            int i = 20;
            Location lastPos = new Location(loc.getWorld(), entityArmorStand.locX, entityArmorStand.locY, entityArmorStand.locZ);
            @SuppressWarnings("deprecation")
            @Override
            public void run() {
                if (i > 0) {
                    entityArmorStand.locY += 0.5;
                    Location pos = new Location(loc.getWorld(), entityArmorStand.locX, entityArmorStand.locY, entityArmorStand.locZ);
                    if (pos.getBlock().getType() == Material.AIR) {
                        PacketPlayOutEntityHeadRotation packetPlayOutEntityHeadRotation = new PacketPlayOutEntityHeadRotation(
                                entityArmorStand,
                                (byte) MathHelper.floor(((entityArmorStand.yaw += 10) * 256.0F) / 360.0F));
                        PacketPlayOutEntityTeleport packetPlayOutEntityTeleport = new PacketPlayOutEntityTeleport(entityArmorStand);
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            ((CraftPlayer) all).getHandle().playerConnection.sendPacket(packetPlayOutEntityTeleport);
                            ((CraftPlayer) all).getHandle().playerConnection.sendPacket(packetPlayOutEntityHeadRotation);
                        }
                        ParticleEffect.FLAME.display(0, 0, 0, 0, 1, pos, 256f);
                        lastPos = pos;
                        i--;
                    } else {
                        i = 0;
                    }
                    if (i == 0) {
                        PacketPlayOutEntityDestroy packetPlayOutEntityDestroy = new PacketPlayOutEntityDestroy(entityArmorStand.getId());
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            ((CraftPlayer) all).getHandle().playerConnection.sendPacket(packetPlayOutEntityDestroy);
                            all.playEffect(lastPos, Effect.EXPLOSION_HUGE, 1);
                        }
                        loc.getWorld().playSound(loc, Sound.EXPLODE, 1f, 1f);
                        cancel();
                    }
                }
            }

        };
        runnable.runTaskTimer(Main.getInstance(), 1, 1);
    }
}