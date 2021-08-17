package io.github.codeutilities.mod.mixin.render;

import io.github.codeutilities.CodeUtilities;
import io.github.codeutilities.mod.config.Config;
import io.github.codeutilities.sys.player.chat.ChatType;
import io.github.codeutilities.sys.player.chat.ChatUtil;
import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Objects;
import net.minecraft.client.WindowSettings;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.RandomStringUtils;
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
            if (!Config.getBoolean("itemTextures")) return;

            CompoundTag vals = stack.getSubTag("CodeUtilitiesGui");
            if (vals == null) return;

            double scale = CodeUtilities.MC.getWindow().getScaleFactor()*8;
            String id = vals.getString("id");
            int width = (int) (vals.getDouble("width")*scale);
            int height = (int) (vals.getDouble("height")*scale);
            int offx = (int) -(vals.getDouble("offx")*scale);
            int offy = (int) -(vals.getDouble("offy")*scale);

            if (id.startsWith("#")) {
                ScreenDrawing.texturedRect(x-offx, y-offy, width, height, new Identifier("codeutilities:gui/"+id.substring(1)+".png"), 0xffffff);
                ci.cancel();
                return;
            }

            if (!id.matches("\\w+") || height <= 0 || width <= 0 || width/scale/8 > 10 || height/scale/8 > 10) return;

            if (!cache.containsKey(id)) {
                cache.put(id, null);
                CodeUtilities.EXECUTOR.submit(() -> {
                    try {
                        URLConnection con = new URL("https://i.imgur.com/" + id + ".png").openConnection();
                        InputStream stream = con.getInputStream();

                        if (con.getContentLength()>1048576) {//1mb Debug Img: b7ZSGE3
                            ChatUtil.sendMessage("[CodeUtilitiesGuis] Image with id " + id + " is over 1mb!", ChatType.FAIL);
                            stream.close();
                            cache.put(id,new Identifier("codeutilities:gui/error.png"));
                            return;
                        }

                        Identifier identifier = CodeUtilities.MC.getTextureManager()
                            .registerDynamicTexture(RandomStringUtils.randomNumeric(10), new NativeImageBackedTexture(NativeImage.read(stream)));

                        cache.put(id, identifier);
                    } catch (Exception err) {
                        err.printStackTrace();
                    }
                });

            } else if (cache.get(id) != null) {
                ScreenDrawing.texturedRect(x-offx, y-offy, width, height, cache.get(id), 0xffffff);
                ci.cancel();
            }
        } catch (Exception err) {
            err.printStackTrace();
        }

    }

}
