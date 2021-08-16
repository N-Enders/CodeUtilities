package io.github.codeutilities.mod.mixin.render;

import io.github.codeutilities.CodeUtilities;
import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Objects;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public class MItemRenderer {

    HashMap<String, Identifier> cache = new HashMap<>();

    @Inject(method = "renderGuiItemModel", at = @At("HEAD"), cancellable = true)
    private void renderItem(ItemStack stack, int x, int y, BakedModel model, CallbackInfo ci) {

        try {
            CompoundTag vals = stack.getOrCreateSubTag("PublicBukkitValues");

            int scale = 18;//todo: set based on gui scale
            //cug -> CodeUtilitiesGui
            String url = vals.getString("hypercube:cug_url");
            int width = (int) (vals.getDouble("hypercube:cug_width")*scale);
            int height = (int) (vals.getDouble("hypercube:cug_height")*scale);
            int offx = (int) (vals.getDouble("hypercube:cug_offx")*scale);
            int offy = (int) (vals.getDouble("hypercube:cug_offy")*scale);

            byte[] bhash = MessageDigest.getInstance("MD5").digest(url.getBytes());
            String hash = new BigInteger(1,bhash).toString(36);

            if (Objects.equals(url, "") || height == 0 || width == 0) return;

            if (!cache.containsKey(hash)) {
                cache.put(hash, null);
                CodeUtilities.EXECUTOR.submit(() -> {
                    try {
                        InputStream s = new URL(url).openStream();
                        Identifier identifier = CodeUtilities.MC.getTextureManager()
                            .registerDynamicTexture(hash,
                                new NativeImageBackedTexture(NativeImage.read(s)));

                        cache.put(hash, identifier);
                    } catch (Exception err) {
                        err.printStackTrace();
                    }
                });

            } else if (cache.get(hash) != null) {
                ScreenDrawing.texturedRect(x-1-offx, y-1-offy, width, height, cache.get(hash), 0xffffff);
                ci.cancel();
            }
        } catch (Exception err) {
            err.printStackTrace();
        }

    }

}
