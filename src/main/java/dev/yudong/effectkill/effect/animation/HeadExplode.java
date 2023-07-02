package dev.yudong.effectkill.effect.animation;

import java.util.ArrayList;
import java.util.Arrays;

import dev.yudong.effectkill.Main;
import dev.yudong.effectkill.effect.MainEffectKill;
import dev.yudong.effectkill.utils.Particle;
import dev.yudong.effectkill.utils.ParticleEffect;
import dev.yudong.effectkill.utils.User;
import dev.yudong.effectkill.utils.Utils;
import dev.yudong.effectkill.utils.config.YAMLUtils;
import dev.yudong.effectkill.utils.inventory.Heads;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
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
        super("headexplode", YAMLUtils.get("messages").getFile().exists() ? ((String) Utils.gfc("messages", "effectKill.headexplose.name")) : ("§e火箭頭顱"), new ArrayList<>(Arrays.asList("&8Your text here.", "&8Left-click to have this effect")), Heads.ANGRY.getTexture());
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
                        ParticleEffect.CLOUD.display(0, 0, 0, 0, 1, pos, 256f);
                        lastPos = pos;
                        i--;
                    } else {
                        i = 0;
                    }
                    if (i == 0) {
                        PacketPlayOutEntityDestroy packetPlayOutEntityDestroy = new PacketPlayOutEntityDestroy(entityArmorStand.getId());
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            ((CraftPlayer) all).getHandle().playerConnection.sendPacket(packetPlayOutEntityDestroy);
                            all.playEffect(lastPos, Effect.STEP_SOUND, 152);
                        }
                        cancel();
                    }
                }
            }

        };
        runnable.runTaskTimer(Main.getInstance(), 1, 1);
    }
}


//    @Override
//    public void update(User user) {
//        Location loc = user.getPlayer().getLocation();
//        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
//        SkullMeta skullMeta = (SkullMeta)skull.getItemMeta();
//        skullMeta.setOwner(user.getPlayer().getName());
//        skull.setItemMeta(skullMeta);
//        ArmorStand armor = (ArmorStand)loc.getWorld().spawnEntity(loc.add(0, -1, 0), EntityType.ARMOR_STAND);
//        armor.setVisible(false);
//        armor.setCustomName("§c§l" + user.getPlayer().getName());
//        armor.setCustomNameVisible(true);
//        armor.setHelmet(skull);
//        armor.setGravity(false);
//        as.add(armor);
//        new BukkitRunnable() {
//            int i = 0;
//            @Override
//            public void run() {
//                i++;
//                armor.teleport(armor.getLocation().add(0,0.5,0));
//                armor.setHeadPose(armor.getHeadPose().add(0.0, 1, 0.0));
//                Particle.play(armor.getLocation().add(0.0, -0.2, 0.0), Effect.FLAME);
//                if(i == 20) {
//                    as.remove(armor);
//                    armor.remove();
//                    Particle.play(armor.getLocation().add(0.0, 0.5, 0.0), Effect.EXPLOSION_HUGE, 1);
//                    cancel();
//                }
//            }
//        }.runTaskTimer(Main.getInstance(), 1, 0);
//    }